package com.duoc.msvc_notifications.assemblers;

import com.duoc.msvc_notifications.controllers.NotificationsController;
import com.duoc.msvc_notifications.dtos.NotificacionDTO;
import com.duoc.msvc_notifications.models.Notificaciones;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.stereotype.Component;

@Component
public class NotificacionAssembler implements RepresentationModelAssembler<Notificaciones, EntityModel<NotificacionDTO>> {

    @Override
    public EntityModel<NotificacionDTO> toModel(Notificaciones notification) {
        NotificacionDTO dto = new NotificacionDTO(
                notification.getId(),
                notification.getMensaje(),
                notification.getUsuarioId(),
                notification.getOrdenId(),
                notification.getFechaEnvio()
        );

        // links dinámicos
        return EntityModel.of(dto,
                linkTo(methodOn(NotificationsController.class).findById(notification.getId())).withSelfRel(),
                linkTo(methodOn(NotificationsController.class).findAll()).withRel("todas-las-notificaciones")
        );
    }
}
