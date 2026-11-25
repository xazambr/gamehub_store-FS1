package com.duoc.msvc_orders.assemblers;

import com.duoc.msvc_orders.controllers.OrdersControllerV2;
import com.duoc.msvc_orders.dtos.OrdersDTO;
import com.duoc.msvc_orders.models.Ordenes;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Antes: la clase se llamaba OrdenesResourceAssembler dentro de OrdenesAssembler.java (no compilaba)
// y llamaba a un metodo inexistente (buscarPorId).
@Component
public class OrdenesAssembler implements RepresentationModelAssembler<Ordenes, EntityModel<OrdersDTO>> {

    @Override
    public EntityModel<OrdersDTO> toModel(Ordenes orden) {
        OrdersDTO dto = new OrdersDTO(orden.getId(), orden.getUsuarioId(), orden.getEstado(),
                orden.getTotal(), orden.getJuegosIds());

        return EntityModel.of(dto,
                linkTo(methodOn(OrdersControllerV2.class).obtenerPorId(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdersControllerV2.class).listarTodas()).withRel("ordenes"));
    }
}
