package com.duoc.msvc_product.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ExtCategoriaDTO {
    Long id;
    String nombre;
    String descripcion;
    String estado;
}
