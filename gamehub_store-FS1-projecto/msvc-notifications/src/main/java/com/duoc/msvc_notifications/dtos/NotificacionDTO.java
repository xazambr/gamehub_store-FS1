package com.duoc.msvc_notifications.dtos;

import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO extends RepresentationModel<NotificacionDTO> {
    private Long id;
    private String mensaje;
    private Long usuarioId;
    private Long ordenId;
    private LocalDateTime fechaEnvio;
}

