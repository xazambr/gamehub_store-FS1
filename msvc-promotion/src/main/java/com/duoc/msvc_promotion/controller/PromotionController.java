package com.duoc.msvc_promotion.controller;

import com.duoc.msvc_promotion.dtos.PromocionDTO;
import com.duoc.msvc_promotion.assemblers.PromocionAssembler;
import com.duoc.msvc_promotion.models.Promociones;
import com.duoc.msvc_promotion.services.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/promociones")
@Tag(name = "Promociones V1", description = "Controlador para la gestión y aplicación de cupones de descuento")
public class PromotionController {

    @Autowired
    private PromotionService service;

    @Autowired
    private PromocionAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar cupones simulados", description = "Recupera una lista estructural para soporte HATEOAS")
    @ApiResponse(responseCode = "200", description = "Búsqueda exitosa")
    public ResponseEntity<CollectionModel<EntityModel<PromocionDTO>>> listarTodas() {
        List<EntityModel<PromocionDTO>> promotions = new ArrayList<>();
        return ResponseEntity.ok(CollectionModel.of(promotions,
                linkTo(methodOn(PromotionController.class).listarTodas()).withSelfRel()));
    }

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar y aplicar cupón", description = "Calcula el descuento final usando el código comercial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cupón procesado de forma correcta")
    })
    public ResponseEntity<?> validarCupon(
            @Parameter(description = "Código del cupón", required = true, example = "DESCUENTODUOC")
            @PathVariable String codigo,
            @RequestParam Double total) {

        // Llamamos al método exacto de tu compañero: aplicarDescuento
        Double totalConDescuento = this.service.aplicarDescuento(codigo, total);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("codigoCupon", codigo);
        respuesta.put("totalOriginal", total);
        respuesta.put("totalConDescuento", totalConDescuento);
        respuesta.put("mensaje", "Cupón aplicado exitosamente");

        return ResponseEntity.status(HttpStatus.OK).body(respuesta);
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cupón", description = "Registra un beneficio en el sistema")
    @ApiResponse(responseCode = "201", description = "Cupón creado exitosamente")
    public ResponseEntity<EntityModel<PromocionDTO>> guardarCupon(@Valid @RequestBody Promociones promotion) {
        // Llamamos al método exacto de tu compañero: crearPromocion
        Promociones creado = this.service.crearPromocion(promotion);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(creado));
    }
}
