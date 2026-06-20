package com.duoc.msvc_product.models.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class ProductoDTO {

        @NotBlank(message = "El cambo nombre no puede ser vacio")
        private String nombre;

        @NotBlank(message = "El cambo marca no puede ser vacio")
        private String marca;

        @NotBlank(message = "El cambo modelo no puede ser vacio")
        private String modelo;

        @NotNull(message = "El cambo precio no puede ser nulo")
        @Min(value = 1, message = "El valor debe ser mayor a cero")
        private int precio;

        @Column(nullable = false)
        private String descripcion;

        @NotBlank(message = "El cambo estado no puede ser vacio")
        private  String estado; // "ACTIVADO", "DESACTIVADO"

}
