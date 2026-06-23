package com.duoc.msvc_orders.dtos;

import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO extends RepresentationModel<OrderDTO> {
    private Long id;
    private Long clienteId;
    private Double montoTotal;
    private String estado;
    private LocalDateTime fechaCreacion;
}
