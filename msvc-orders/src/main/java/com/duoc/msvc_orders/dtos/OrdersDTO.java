package com.duoc.msvc_orders.dtos;

import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends RepresentationModel<OrdersDTO> {
    private Long id;
    private Long clienteId;
    private String estado;
}
