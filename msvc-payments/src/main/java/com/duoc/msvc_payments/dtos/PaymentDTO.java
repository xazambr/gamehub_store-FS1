package com.duoc.msvc_payments.dtos;

import org.springframework.hateoas.RepresentationModel;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PaymentDTO extends RepresentationModel<PaymentDTO> {
    private Long id;
    private Long ordenId;
    private Double monto;
    private String estado;
}
