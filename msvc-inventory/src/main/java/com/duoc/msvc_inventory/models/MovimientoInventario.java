package com.duoc.msvc_inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// Bitacora de cada cambio de stock (INGRESO, RESERVA, LIBERACION, SALIDA).
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

    @NotNull
    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    @NotBlank(message = "el campo tipo no puede ser vacio")
    @Column(nullable = false)
    private String tipo;

    @NotNull
    @Min(value = 1, message = "la cantidad debe ser mayor a cero")
    @Column(nullable = false)
    private Integer cantidad;

    @NotNull(message = "el campo fecha no puede ser vacio")
    @Column(nullable = false)
    private LocalDateTime fecha;

    public MovimientoInventario(Long productoId, String tipo, Integer cantidad) {
        this.productoId = productoId;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = LocalDateTime.now();
    }
}
