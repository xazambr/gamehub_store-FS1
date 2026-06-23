package com.duoc.msvc_promotion.assemblers;

import com.duoc.msvc_promotion.controller.PromotionController;
import com.duoc.msvc_promotion.dtos.PromocionDTO;
import com.duoc.msvc_promotion.models.Promociones;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class PromocionAssembler implements RepresentationModelAssembler<Promociones, EntityModel<PromocionDTO>> {

    @Override
    public EntityModel<PromocionDTO> toModel(Promociones promocion) {
        PromocionDTO dto = new PromocionDTO(
                promocion.getId(),
                promocion.getCodigo(),
                promocion.getPorcentajeDescuento(),
                promocion.getFechaExpiracion(),
                promocion.getActivo()
        );

        return EntityModel.of(dto,
                linkTo(methodOn(PromotionController.class).validarCupon(promocion.getCodigo(), 0.0)).withSelfRel(),
                linkTo(methodOn(PromotionController.class).listarTodas()).withRel("todas-las-promociones")
        );
    }
}

