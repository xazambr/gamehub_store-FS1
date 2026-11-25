package com.duoc.msvc_payments.assemblers;

import com.duoc.msvc_payments.controllers.PaymentsController;
import com.duoc.msvc_payments.dtos.PaymentDTO;
import com.duoc.msvc_payments.models.Pagos;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class PaymentAssembler implements RepresentationModelAssembler<Pagos, EntityModel<PaymentDTO>> {

    @Override
    public EntityModel<PaymentDTO> toModel(Pagos pagos) {
        PaymentDTO dto = new PaymentDTO();
        dto.setId(pagos.getId());
        dto.setOrdenId(pagos.getOrdenId());
        dto.setMonto(pagos.getMonto());
        dto.setEstado(pagos.getEstado());

        return EntityModel.of(dto,
                linkTo(methodOn(PaymentsController.class).listarTodos()).withSelfRel()
        );
    }
}
