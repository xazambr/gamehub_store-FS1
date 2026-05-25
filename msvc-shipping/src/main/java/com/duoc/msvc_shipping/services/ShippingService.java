package com.duoc.msvc_shipping.services;

import com.duoc.msvc_shipping.models.Envio;

import java.util.Optional;

public interface ShippingService {
    // Para registrar el despacho
    Envio registrarDespacho(Envio envio);

    Optional<Envio> buscarPorOrden(Long ordenId);

    // Para cambiar el estado
    Envio actualizarEstado(Long id, String nuevoEstado);
}
