package com.duoc.msvc_category.assemblers;

import com.duoc.msvc_category.controllers.CategoryControllerV2;
import com.duoc.msvc_category.models.Categoria;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategoryModelAssembler implements RepresentationModelAssembler<Categoria, EntityModel<Categoria>> {

    @Override
    public EntityModel<Categoria> toModel(Categoria categoria) {
        return EntityModel.of(
                categoria,
                linkTo(methodOn(CategoryControllerV2.class).findById(categoria.getId())).withSelfRel(),
                linkTo(methodOn(CategoryControllerV2.class).findAll()).withRel("productos"));

    }
}