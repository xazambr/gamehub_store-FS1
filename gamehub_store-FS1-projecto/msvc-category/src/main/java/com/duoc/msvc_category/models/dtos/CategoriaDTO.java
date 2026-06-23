package com.duoc.msvc_category.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CategoriaDTO {
    @NotBlank(message = "El campo nombre no puede ser vacio")
    private String nombre;

    @NotBlank(message = "El campo descripcion no puede ser vacio")
    private String descripcion;

    @NotBlank(message = "El campo estado no puede ser vacio")
    private String estado;
}
