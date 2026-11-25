package com.duoc.msvc_payments.controllers;

import com.duoc.msvc_payments.dtos.PaymentDTO;
import com.duoc.msvc_payments.assemblers.PaymentAssembler;
import com.duoc.msvc_payments.models.Pagos;
import com.duoc.msvc_payments.services.PagosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/pago")
@Tag(name = "Pagos V1", description = "Endpoints para la gestión y procesamiento de transacciones")
public class PaymentsController {

    @Autowired
    private PagosService pagosService;

    @Autowired
    private PaymentAssembler assembler;

    @PostMapping
    @Operation(summary = "Procesar un nuevo pago")
    public ResponseEntity<EntityModel<PaymentDTO>> procesarPago(@Valid @RequestBody Pagos pago) {
        Pagos nuevoPago = this.pagosService.procesarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(nuevoPago));
    }

    @GetMapping
    @Operation(summary = "Ver historial de todos los pagos")
    public ResponseEntity<CollectionModel<EntityModel<PaymentDTO>>> listarTodos() {
        List<EntityModel<PaymentDTO>> lista = this.pagosService.listarTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(lista,
                linkTo(methodOn(PaymentsController.class).listarTodos()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar una transacción por ID")
    public ResponseEntity<EntityModel<PaymentDTO>> obtenerPorId(@PathVariable Long id) {
        return this.pagosService.buscarPorId(id)
                .map(pago -> ResponseEntity.ok(assembler.toModel(pago)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orden/{ordenId}")
    @Operation(summary = "Buscar pago por ID de la orden")
    public ResponseEntity<EntityModel<PaymentDTO>> obtenerPorOrdenId(@PathVariable Long ordenId) {
        return this.pagosService.buscarPorOrdenId(ordenId)
                .map(pago -> ResponseEntity.ok(assembler.toModel(pago)))
                .orElse(ResponseEntity.notFound().build());
    }
}
