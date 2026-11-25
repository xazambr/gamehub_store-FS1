package com.duoc.msvc_orders.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class OrdenesDTO {

    private String estado; // creada, pagada, enviada, cancelada
    private String metodoPago;
    private Double total;
}
