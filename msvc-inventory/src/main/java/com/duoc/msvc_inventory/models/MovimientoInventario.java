package com.duoc.msvc_inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "movimiento_inventario")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Long id;

    @Column
    @NotBlank(message = "el campo tipo no puede ser vacio")
    private String tipo;

    @Column
    @NotBlank
    @Min(value = 0, message = "la cantidad no puede ser menor a cero")
    private int cantidad;

    @Column
    @NotBlank(message = "el campo fecha no puede ser vacio")
    private Date fecha;


}
