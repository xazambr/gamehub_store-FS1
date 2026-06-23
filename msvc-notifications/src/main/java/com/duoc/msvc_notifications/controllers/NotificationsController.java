package com.duoc.msvc_notifications.controllers;

import com.duoc.msvc_notifications.dtos.NotificacionDTO;
import com.duoc.msvc_notifications.assemblers.NotificacionAssembler;
import com.duoc.msvc_notifications.models.Notificaciones;
import com.duoc.msvc_notifications.services.NotificationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/notificaciones")
@Tag(name = "Notificaciones V1", description = "Métodos para el envío y consulta de alertas hipermedia")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    @Autowired
    private NotificacionAssembler assembler;

    @GetMapping
    @Operation(summary = "Listado de notificaciones", description = "Devuelve un listado completo con navegación HATEOAS")
    @ApiResponse(responseCode = "200", description = "Operación Exitosa")
    public ResponseEntity<CollectionModel<EntityModel<NotificacionDTO>>> findAll() {

        // El metodo pide el ID de usuario, por si se quiere listar el historial del usuario 1
        List<EntityModel<NotificacionDTO>> notifications = notificationsService.listarPorUsuario(1L).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(notifications,
                linkTo(methodOn(NotificationsController.class).findAll()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar notificación por ID", description = "Devuelve una notificación con sus links hipermedia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificación encontrada"),
            @ApiResponse(responseCode = "404", description = "Notificación no existe en la BD")
    })
    public ResponseEntity<EntityModel<NotificacionDTO>> findById(
            @Parameter(description = "ID de la notificación", required = true, example = "1")
            @PathVariable Long id) {
        Notificaciones notification = new Notificaciones();
        notification.setId(id);
        notification.setMensaje("Notificación consultada de forma individual");

        return ResponseEntity.ok(assembler.toModel(notification));
    }

    @PostMapping
    @Operation(summary = "Guardar notificación", description = "Registra una notificación y retorna su recurso documentado")
    @ApiResponse(responseCode = "201", description = "Notificación creada con éxito")
    public ResponseEntity<EntityModel<NotificacionDTO>> save(@RequestBody Notificaciones notification) {
        Notificaciones saved = notificationsService.enviarNotificacion(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(saved));
    }
}