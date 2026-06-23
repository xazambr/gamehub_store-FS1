package com.duoc.msvc_promotion.services;

import com.duoc.msvc_promotion.models.Promociones;

public interface PromotionService {
    // Guarda un nuevo cupón
    Promociones crearPromocion(Promociones promotion);

    // Aplicar el cupón
    Double aplicarDescuento(String codigo, Double totalOrden);
}
