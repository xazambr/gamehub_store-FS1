package com.duoc.msvc_promotion.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "promociones")
@Getter
@Setter
@NoArgsConstructor
public class Promociones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promocion_id")
    private Long id;

    @NotBlank(message = "El código de la promoción es obligatorio")
    @Column(name = "codigo_cupon", unique = true, nullable = false, length = 50)
    private String codigo; // Ejemplo: Msvc2026

    @NotNull(message = "El porcentaje de descuento no puede ser nulo")
    @Min(value = 1, message = "El descuento mínimo es de 1%")
    @Max(value = 100, message = "El descuento no puede superar el 100%")
    @Column(name = "porcentaje_descuento", nullable = false)
    private Double porcentajeDescuento;

    @NotNull(message = "El estado activo/inactivo es obligatorio")
    @Column(name = "is_activo", nullable = false)
    private Boolean activo; // true = disponible / false = deshabilitado

    @NotNull(message = "La fecha de expiración es obligatoria")
    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;
}
