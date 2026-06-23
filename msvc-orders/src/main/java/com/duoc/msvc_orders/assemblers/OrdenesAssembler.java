package com.duoc.msvc_orders.assemblers;

import com.duoc.msvc_orders.controllers.OrdersController;
import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.dtos.OrdersDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class OrdenesResourceAssembler implements RepresentationModelAssembler<Ordenes, EntityModel<OrdersDTO>> {

    @Override
    public EntityModel<OrdersDTO> toModel(Ordenes orden) {
        OrdersDTO dto = new OrdersDTO();
        dto.setId(orden.getId());
        dto.setEstado(orden.getEstado());

        return EntityModel.of(dto,
                linkTo(methodOn(OrdersController.class).buscarPorId(orden.getId())).withSelfRel(),
                linkTo(methodOn(OrdersController.class).listarTodas()).withSelfRel()
        );
    }
}
