package com.duoc.msvc_orders.controllers;

import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.services.OrdersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orden")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping
    public ResponseEntity<Ordenes> crearOrden(@Valid @RequestBody Ordenes orden) {
        Ordenes resultado = this.ordersService.crear(orden);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultado);
    }

    // Listar las órdenes de la base de datos
    @GetMapping
    public ResponseEntity<List<Ordenes>> listarTodas() {
        List<Ordenes> lista = this.ordersService.listarTodas();
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }



    @GetMapping("/{id}")
    public ResponseEntity<Ordenes> obtenerPorId(@PathVariable Long id) {
        return this.ordersService.buscarPorId(id).map(resultado -> ResponseEntity.status(HttpStatus.OK).body(resultado)).orElse(ResponseEntity.notFound().build());
    }

    // filtrar órdenes por Cliente (usuarioId)

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Ordenes>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Ordenes> lista = this.ordersService.listarPorUsuario(usuarioId);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    //Listar órdenes por Estado (creada, pagada, etc.)

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Ordenes>> listarPorEstado(@PathVariable String estado) {
        List<Ordenes> lista = this.ordersService.listarPorEstado(estado);
        return ResponseEntity.status(HttpStatus.OK).body(lista);
    }

    // Actualizar estado de orden

    @PutMapping("/{id}/estado")
    public ResponseEntity<Ordenes> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        Ordenes resultado = this.ordersService.actualizarEstado(id, estado);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

    // Cancelar una orden

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<Ordenes> cancelarOrden(@PathVariable Long id, @RequestParam String motivo) {
        Ordenes resultado = this.ordersService.cancelarOrden(id, motivo);
        return ResponseEntity.status(HttpStatus.OK).body(resultado);
    }

}
