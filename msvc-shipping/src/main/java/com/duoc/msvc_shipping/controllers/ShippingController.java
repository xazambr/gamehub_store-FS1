package com.duoc.msvc_shipping.controllers;

import com.duoc.msvc_shipping.models.Envio;
import com.duoc.msvc_shipping.services.ShippingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/shipping")
public class ShippingController {

    @Autowired
    private ShippingService service;


    @PostMapping
    public ResponseEntity<Envio> crearDespacho(@Valid @RequestBody Envio envio) {
        Envio creado = this.service.registrarDespacho(envio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // Busca por el ID de la orden
    @GetMapping("/orden/{ordenId}")
    public ResponseEntity<Envio> verPorOrden(@PathVariable Long ordenId) {
        return this.service.buscarPorOrden(ordenId).map(envio -> ResponseEntity.status(HttpStatus.OK).body(envio)).orElse(ResponseEntity.notFound().build());
    }

    // Cambia el estado
    @PutMapping("/{id}/estado")
    public ResponseEntity<Envio> cambiarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        Envio actualizado = this.service.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.status(HttpStatus.OK).body(actualizado);
    }
}
