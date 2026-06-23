package com.duoc.msvc_payments.services;

import com.duoc.msvc_payments.models.Pagos;
import com.duoc.msvc_payments.repositories.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagosServiceImpl implements PagosService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    @Transactional
    @Override
    public Pagos procesarPago(Pagos pago) {
        // Validación básica de negocio exigida por la asignatura
        if (pago.getMonto() == null || pago.getMonto() <= 0) {
            throw new RuntimeException("El monto del pago debe ser mayor a cero");
        }

        // Configuración de campos iniciales
        pago.setEstado("COMPLETADO");
        pago.setFechaTransaccion(LocalDateTime.now());

        return this.paymentsRepository.save(pago);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Pagos> listarTodos() {
        return (List<Pagos>) this.paymentsRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pagos> buscarPorId(Long id) {
        return this.paymentsRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Pagos> buscarPorOrdenId(Long ordenId) {
        return this.paymentsRepository.findByOrdenId(ordenId);
    }
}
