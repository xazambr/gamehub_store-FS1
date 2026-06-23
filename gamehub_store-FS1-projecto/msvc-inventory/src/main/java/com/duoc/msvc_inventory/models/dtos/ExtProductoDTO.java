package com.duoc.msvc_inventory.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ExtProductoDTO {
    Long id;
    String nombre;
    String modelo;
    String marca;
    int precio;
    String descripcion;
    String estado;

}
