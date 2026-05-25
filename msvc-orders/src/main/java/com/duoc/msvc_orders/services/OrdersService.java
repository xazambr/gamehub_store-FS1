package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.models.Ordenes;

import java.util.List;
import java.util.Optional;

public interface OrdersService {
    List<Ordenes> listarTodas();

    Optional<Ordenes> buscarPorId(Long id);

    List<Ordenes> listarPorEstado(String estado);

    List<Ordenes> listarPorUsuario(Long usuarioId);

    Ordenes crear(Ordenes orden);

    Ordenes actualizarEstado(Long id, String nuevoEstado);

    Ordenes cancelarOrden(Long id, String motivo);
}
