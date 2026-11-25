package com.duoc.msvc_inventory.services;

import com.duoc.msvc_inventory.clients.ProductClient;
import com.duoc.msvc_inventory.exceptions.InventoryExceptions;
import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.models.MovimientoInventario;
import com.duoc.msvc_inventory.repositories.InventoryRepository;
import com.duoc.msvc_inventory.repositories.MovimientoRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ProductClient productClient;

    @Transactional(readOnly = true)
    @Override
    public List<Inventario> findAll() {
        return this.inventoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Inventario findById(Long id) {
        return this.inventoryRepository.findById(id).orElseThrow(
                () -> new InventoryExceptions("Inventario con id: " + id + " no encontrado", HttpStatus.NOT_FOUND)
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Inventario findByProductoId(Long productoId) {
        return this.inventoryRepository.findByProductoId(productoId).orElseThrow(
                () -> new InventoryExceptions("El producto " + productoId + " no tiene inventario", HttpStatus.NOT_FOUND)
        );
    }

    @Transactional
    @Override
    public Inventario save(Inventario inventario) {
        if (inventario.getProductoId() == null) {
            throw new InventoryExceptions("El producto es obligatorio");
        }
        if (this.inventoryRepository.existsByProductoId(inventario.getProductoId())) {
            throw new InventoryExceptions("El producto " + inventario.getProductoId() + " ya tiene inventario", HttpStatus.CONFLICT);
        }
        try {
            this.productClient.findById(inventario.getProductoId());
        } catch (FeignException.NotFound e) {
            throw new InventoryExceptions("El producto con ID " + inventario.getProductoId() + " no existe en el sistema.");
        }

        inventario.setId(null); // un POST nunca debe sobrescribir un registro existente
        Inventario guardado = this.inventoryRepository.save(inventario);
        if (guardado.getStockDisponible() > 0) {
            registrar(guardado.getProductoId(), "INGRESO", guardado.getStockDisponible());
        }
        return guardado;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        if (!this.inventoryRepository.existsById(id)) {
            throw new InventoryExceptions("Inventario con id: " + id + " no encontrado", HttpStatus.NOT_FOUND);
        }
        this.inventoryRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Inventario updateById(Long id, Inventario inventario) {
        return this.inventoryRepository.findById(id).map(element -> {
            element.setStockDisponible(inventario.getStockDisponible());
            element.setStockMinimo(inventario.getStockMinimo());
            element.setStockReservado(inventario.getStockReservado());
            element.setUbicacion(inventario.getUbicacion());   // antes no se actualizaba
            return this.inventoryRepository.save(element);
        }).orElseThrow(
                () -> new InventoryExceptions("El inventario con id: " + id + " no existe", HttpStatus.NOT_FOUND)
        );
    }

    @Transactional
    @Override
    public Inventario reservar(Long productoId, int cantidad) {
        validarCantidad(cantidad);
        Inventario inv = bloquear(productoId);
        if (inv.getStockDisponible() < cantidad) {
            throw new InventoryExceptions("Stock insuficiente para el producto " + productoId
                    + " (disponible: " + inv.getStockDisponible() + ", solicitado: " + cantidad + ")", HttpStatus.CONFLICT);
        }
        inv.setStockDisponible(inv.getStockDisponible() - cantidad);
        inv.setStockReservado(inv.getStockReservado() + cantidad);
        registrar(productoId, "RESERVA", cantidad);
        return this.inventoryRepository.save(inv);
    }

    @Transactional
    @Override
    public Inventario liberar(Long productoId, int cantidad) {
        validarCantidad(cantidad);
        Inventario inv = bloquear(productoId);
        if (inv.getStockReservado() < cantidad) {
            throw new InventoryExceptions("No hay " + cantidad + " unidades reservadas del producto " + productoId, HttpStatus.CONFLICT);
        }
        inv.setStockReservado(inv.getStockReservado() - cantidad);
        inv.setStockDisponible(inv.getStockDisponible() + cantidad);
        registrar(productoId, "LIBERACION", cantidad);
        return this.inventoryRepository.save(inv);
    }

    @Transactional
    @Override
    public Inventario confirmar(Long productoId, int cantidad) {
        validarCantidad(cantidad);
        Inventario inv = bloquear(productoId);
        if (inv.getStockReservado() < cantidad) {
            throw new InventoryExceptions("No hay " + cantidad + " unidades reservadas del producto " + productoId, HttpStatus.CONFLICT);
        }
        inv.setStockReservado(inv.getStockReservado() - cantidad);
        registrar(productoId, "SALIDA", cantidad);
        return this.inventoryRepository.save(inv);
    }

    @Transactional(readOnly = true)
    @Override
    public List<MovimientoInventario> movimientos(Long productoId) {
        return this.movimientoRepository.findByProductoIdOrderByFechaDesc(productoId);
    }

    private Inventario bloquear(Long productoId) {
        return this.inventoryRepository.findByProductoIdForUpdate(productoId).orElseThrow(
                () -> new InventoryExceptions("El producto " + productoId + " no tiene inventario", HttpStatus.NOT_FOUND)
        );
    }

    private void validarCantidad(int cantidad) {
        if (cantidad < 1) {
            throw new InventoryExceptions("La cantidad debe ser mayor a cero");
        }
    }

    private void registrar(Long productoId, String tipo, int cantidad) {
        this.movimientoRepository.save(new MovimientoInventario(productoId, tipo, cantidad));
    }
}
