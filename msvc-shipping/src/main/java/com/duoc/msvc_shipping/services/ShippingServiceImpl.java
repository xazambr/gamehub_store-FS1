package com.duoc.msvc_shipping.services;

import com.duoc.msvc_shipping.models.Envio;
import com.duoc.msvc_shipping.repositories.ShippingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingRepository repository;

    @Transactional(readOnly = true)
    @Override
    public List<Envio> findAll() {
        List<Envio> lista = new ArrayList<>();
        this.repository.findAll().forEach(lista::add);
        return lista;
    }

    @Transactional
    @Override
    public Envio registrarDespacho(Envio envio) {
        envio.setEstadoEnvio("PENDIENTE");
        envio.setFechaDespacho(LocalDateTime.now());

        Envio envioGuardadoInicial = this.repository.save(envio);

        String codigoSimple = "TRACK-" + envioGuardadoInicial.getId();
        envioGuardadoInicial.setNumeroSeguimiento(codigoSimple);

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
        Envio envioExistente = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: No existe el envío con ID " + id));

        envioExistente.setEstadoEnvio(nuevoEstado);

        return this.repository.save(envioExistente);
    }
}