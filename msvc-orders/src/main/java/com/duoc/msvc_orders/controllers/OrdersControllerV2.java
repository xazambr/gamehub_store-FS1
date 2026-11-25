package com.duoc.msvc_orders.controllers;

import com.duoc.msvc_orders.assemblers.OrdenesAssembler;
import com.duoc.msvc_orders.dtos.OrdersDTO;
import com.duoc.msvc_orders.exceptions.OrderException;
import com.duoc.msvc_orders.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Version HATEOAS (el gateway ya enrutaba /api/v2/orden pero no existia el controller).
@RestController
@RequestMapping("/api/v2/orden")
public class OrdersControllerV2 {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrdenesAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<OrdersDTO>>> listarTodas() {
        List<EntityModel<OrdersDTO>> modelos = this.ordersService.listarTodas().stream()
                .map(assembler::toModel)
                .toList();
        return ResponseEntity.ok(CollectionModel.of(modelos,
                linkTo(methodOn(OrdersControllerV2.class).listarTodas()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<OrdersDTO>> obtenerPorId(@PathVariable Long id) {
        return this.ordersService.buscarPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new OrderException("Orden no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    }
}
