package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenesServiceImpl implements OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarTodas() {
        return (List<Ordenes>) this.ordersRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Ordenes> buscarPorId(Long id) {
        return this.ordersRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarPorEstado(String estado) {
        return this.ordersRepository.findByEstado(estado);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarPorUsuario(Long usuarioId) {
        return this.ordersRepository.findByUsuarioId(usuarioId);
    }

    @Transactional
    @Override
    public Ordenes crear(Ordenes orden) {
        orden.setEstado("creada");
        orden.setFechCreacion(LocalDateTime.now());

        if (orden.getTotal() == null) {
            orden.setTotal(0.0);
        }

        return this.ordersRepository.save(orden);
    }

    @Transactional
    @Override
    public Ordenes actualizarEstado(Long id, String nuevoEstado) {
        Ordenes ordenExistente = this.ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));

        if ("cancelada".equalsIgnoreCase(ordenExistente.getEstado())) {
            throw new RuntimeException("La orden ya se encuentra cancelada y no puede ser modificada");
        }

        ordenExistente.setEstado(nuevoEstado);
        ordenExistente.setFechaActualizacion(LocalDateTime.now());
        return this.ordersRepository.save(ordenExistente);
    }

    @Transactional
    @Override
    public Ordenes cancelarOrden(Long id, String motivo) {
        Ordenes ordenExistente = this.ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + id));

        ordenExistente.setEstado("cancelada");
        ordenExistente.setMotivoCancelacion(motivo);
        ordenExistente.setFechaActualizacion(LocalDateTime.now());
        return this.ordersRepository.save(ordenExistente);
    }
}