package com.duoc.msvc_orders.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ordenes")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Ordenes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_id")
    private Long id;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    @Column(nullable = false)
    private Long usuarioId;

    // Lo asigna el servicio ("creada"); no debe exigirse en el cuerpo de la peticion.
    @Column(nullable = false)
    private String estado; // creada, pagada, enviada, cancelada

    @NotNull(message = "El método de pago es obligatorio")
    @Column(nullable = false)
    private String metodoPago;

    @NotNull(message = "El total no puede ser nulo")
    @Min(value = 0, message = "El total debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Double total;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;


    // Cada aparicion de un id equivale a 1 unidad (ej: [3,3,5] = 2 del juego 3 y 1 del juego 5).
    @NotEmpty(message = "La orden debe incluir al menos un juego")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "orden_juegos", joinColumns = @JoinColumn(name = "id_orden"))
    @Column(name = "id_juego")
    private List<Long> juegosIds;



}
