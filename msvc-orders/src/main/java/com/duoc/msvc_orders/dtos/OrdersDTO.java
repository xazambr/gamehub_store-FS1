package com.duoc.msvc_orders.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO extends RepresentationModel<OrdersDTO> {
    private Long id;
    private Long usuarioId;
    private String estado;
    private Double total;
    private List<Long> juegosIds;
}
