package com.duoc.msvc_inventory.services;

import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.models.MovimientoInventario;

import java.util.List;

public interface InventoryService {
    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario findByProductoId(Long productoId);
    Inventario save(Inventario inventario);
    void deleteById(Long id);
    Inventario updateById(Long id, Inventario inventario);

    // Flujo de stock usado por msvc-orders
    Inventario reservar(Long productoId, int cantidad);    // disponible -> reservado
    Inventario liberar(Long productoId, int cantidad);     // reservado -> disponible (orden cancelada)
    Inventario confirmar(Long productoId, int cantidad);   // reservado -> sale (orden enviada)

    List<MovimientoInventario> movimientos(Long productoId);
}
