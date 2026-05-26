package com.duoc.msvc_promotion.controller;

import com.duoc.msvc_promotion.models.Promociones;
import com.duoc.msvc_promotion.services.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    @Autowired
    private PromotionService service;

    // Crea un nuevo cupón
    @PostMapping
    public ResponseEntity<Promociones> guardarCupon(@Valid @RequestBody Promociones promotion) {
        Promociones creado = this.service.crearPromocion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Valida y aplica el descuento
    @GetMapping("/validar/{codigo}")
    public ResponseEntity<?> validarCupon(@PathVariable String codigo, @RequestParam Double total) {
        Double totalConDescuento = this.service.aplicarDescuento(codigo, total);

        // Estructuramos una respuesta
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigoCupon", codigo);
        respuesta.put("totalOriginal", total);
        respuesta.put("totalConDescuento", totalConDescuento);
        respuesta.put("mensaje", "Cupón aplicado exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }
}
