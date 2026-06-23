package com.duoc.msvc_inventory.assemblers;

import com.duoc.msvc_inventory.controllers.InventoryControllerV2;
import com.duoc.msvc_inventory.models.Inventario;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

public class InventoryModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(
                inventario,
                linkTo(methodOn(InventoryControllerV2.class).findById(inventario.getId())).withSelfRel(),
                linkTo(methodOn(InventoryControllerV2.class).findAll()).withRel("inventario"));

    }
}

