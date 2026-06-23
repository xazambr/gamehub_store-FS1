package com.duoc.msvc_promotion.services;

import com.duoc.msvc_promotion.models.Promociones;
import com.duoc.msvc_promotion.repositories.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository repository;

    @Transactional
    @Override
    public Promociones crearPromocion(Promociones promotion) {
        // Guarda el cupón de Postman
        return this.repository.save(promotion);
    }

    @Transactional(readOnly = true)
    @Override
    public Double aplicarDescuento(String codigo, Double totalOrden) {
        // Busca el cupón
        Promociones promocion = this.repository.findByCodigo(codigo).orElseThrow(() -> new RuntimeException("El código de cupón '" + codigo + "' no existe"));

        // Está el cupón activo?
        if (!promocion.getActivo()) {
            throw new RuntimeException("El cupón '" + codigo + "' se encuentra deshabilitado");
        }

        // expiró el cupón?
        if (LocalDateTime.now().isAfter(promocion.getFechaExpiracion())) {
            throw new RuntimeException("El cupón '" + codigo + "' ya expiró el " + promocion.getFechaExpiracion());
        }

        //  calculamos el descuento matemáticamente
        Double descuento = totalOrden * (promocion.getPorcentajeDescuento() / 100.0);
        Double totalFinal = totalOrden - descuento;

        // Retornamos el nuevo valor redondeado
        return Math.round(totalFinal * 100.0) / 100.0;
    }
}
