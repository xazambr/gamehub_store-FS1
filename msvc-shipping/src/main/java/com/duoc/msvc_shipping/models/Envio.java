package com.duoc.msvc_shipping.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Getter
@Setter
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "envio_id")
    private Long id;

    @NotNull(message = "El ID de la orden no puede ser nulo")
    @Column(name = "orden_id", nullable = false)
    private Long ordenId;

    @NotBlank(message = "La dirección de destino es obligatoria")
    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @NotBlank(message = "La empresa de transporte es obligatoria")
    @Column(name = "empresa_transporte", nullable = false)
    private String empresaTransporte;

    @Column(name = "numero_seguimiento")
    private String numeroSeguimiento;

    @Column(name = "estado_envio")
    private String estadoEnvio;

    @Column(name = "fecha_despacho")
    private LocalDateTime fechaDespacho;
}
