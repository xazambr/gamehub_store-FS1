package com.duoc.msvc_notifications.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Getter
@Setter
@NoArgsConstructor
public class Notificaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notificacion_id")
    private Long id;

    @NotNull(message = "El ID de la orden no puede ser nulo")
    @Column(name = "orden_id", nullable = false)
    private Long ordenId;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @NotBlank(message = "El mensaje de la notificación es obligatorio")
    @Column(name = "mensaje_texto", nullable = false, length = 500)
    private String mensaje;

    @NotBlank(message = "El tipo de notificación es obligatorio")
    @Column(name = "tipo_notificacion", nullable = false)
    private String tipoNotificacion; // Ejemplo: "Email" o "Sms"

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
}
