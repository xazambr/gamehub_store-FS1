package com.duoc.msvc_shipping.services;

import com.duoc.msvc_shipping.models.Envio;
import com.duoc.msvc_shipping.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository repository;

    @Transactional
    @Override
    public Envio registrarDespacho(Envio envio) {
        envio.setEstadoEnvio("PENDIENTE");
        envio.setFechaDespacho(LocalDateTime.now());

        // primero guardamos
        Envio envioGuardadoInicial = this.repository.save(envio);

        // creamos un código de seguimiento: "TRACK-" + el ID de la tabla
        String codigoSimple = "TRACK-" + envioGuardadoInicial.getId();
        envioGuardadoInicial.setNumeroSeguimiento(codigoSimple);

        // vuelve a guardar
        return this.repository.save(envioGuardadoInicial);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Envio> buscarPorOrden(Long ordenId) {
        return this.repository.findByOrdenId(ordenId);
    }

    @Transactional
    @Override
    public Envio actualizarEstado(Long id, String nuevoEstado) {
        Envio envioExistente = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Error: No existe el envío con ID " + id));

        envioExistente.setEstadoEnvio(nuevoEstado);

        return this.repository.save(envioExistente);
    }
}
