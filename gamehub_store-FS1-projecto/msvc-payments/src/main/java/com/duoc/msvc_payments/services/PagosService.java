package com.duoc.msvc_payments.services;

import com.duoc.msvc_payments.models.Pagos;

import java.util.List;
import java.util.Optional;

public interface PagosService {
    List<Pagos> listarTodos();

    Optional<Pagos> buscarPorId(Long id);

    Optional<Pagos> buscarPorOrdenId(Long ordenId);

    Pagos procesarPago(Pagos pago);
}
