package com.duoc.msvc_orders.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "El estado no puede ser nulo")
    @Column(nullable = false)
    private String estado; // creada, pagada, enviada, cancelada

    @NotNull(message = "El método de pago es obligatorio")
    @Column(nullable = false)
    private String metodoPago;

    @NotNull(message = "El total no puede ser nulo")
    @Min(value = 0, message = "El total debe ser mayor o igual a 0")
    @Column(nullable = false)
    private Double total;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column
    private LocalDateTime fechaActualizacion;

    @Column
    private String empresaTransporte; // Starken, Chilexpress

    @Column
    private String numeroSeguimiento; // Número de seguimiento

    @Column
    private String motivoCancelacion;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "orden_juegos", joinColumns = @JoinColumn(name = "orden_id"))
    @Column(name = "juego_id")
    private List<Long> juegosIds; // Lista de juegos seleccionados

    public Boolean esEditable() {
        return estado.equalsIgnoreCase("creada");
    }
}
