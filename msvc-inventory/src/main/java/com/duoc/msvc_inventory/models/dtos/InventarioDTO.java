package com.duoc.msvc_inventory.models.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InventarioDTO {
    @NotNull(message = "El campo stock disponible no puede ser vacio")
    @Min(value = 1, message = "el stock disponible no puede ser negativo")
    private int stockDisponible;

    @NotNull(message = "El campo stock reservado no puede ser vacio")
    @Min(value = 1, message = "el stock reservado no puede ser negativo")
    private int stockReservado;

    @NotNull(message = "El campo stock minimo no puede ser vacio")
    @Min(value = 1, message = "el stock minimo no puede ser negativo")
    private int stockMinimo;

    @NotBlank(message = "El campo ubicacion no puede ser vacio")
    private String ubicacion;
}
