package com.duoc.msvc_payments.controllers;

import com.duoc.msvc_payments.models.Pagos;
import com.duoc.msvc_payments.services.PagosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pago")
public class PaymentsController {

    @Autowired
    private PagosService pagosService;

    @PostMapping
    public ResponseEntity<Pagos> procesarPago(@Valid @RequestBody Pagos pago) {
        Pagos nuevoPago = this.pagosService.procesarPago(pago);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @GetMapping
    public ResponseEntity<List<Pagos>> listarTodos() {
        List<Pagos> lista = this.pagosService.listarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    // Buscar una transacción por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pagos> obtenerPorId(@PathVariable Long id) {
        return this.pagosService.buscarPorId(id).map(pago -> ResponseEntity.status(HttpStatus.OK).body(pago)).orElse(ResponseEntity.notFound().build());
    }

    // Buscar si una orden de compra ya tiene el pago registrado
    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<Pagos> obtenerPorOrdenId(@PathVariable Long ordenId) {
        return this.pagosService.buscarPorOrdenId(ordenId).map(pago -> ResponseEntity.status(HttpStatus.OK).body(pago)).orElse(ResponseEntity.notFound().build());
    }
}
