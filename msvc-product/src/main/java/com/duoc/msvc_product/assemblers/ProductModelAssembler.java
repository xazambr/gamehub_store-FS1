package com.duoc.msvc_product.assemblers;

import com.duoc.msvc_product.controllers.ProductControllerV2;
import com.duoc.msvc_product.models.Producto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {

    @Override
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(
                producto,
                linkTo(methodOn(ProductControllerV2.class).findById(producto.getId())).withSelfRel(),
                linkTo(methodOn(ProductControllerV2.class).findAll()).withRel("productos"));

    }
}
