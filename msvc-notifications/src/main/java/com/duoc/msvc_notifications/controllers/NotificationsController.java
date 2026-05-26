package com.duoc.msvc_notifications.controllers;

import com.duoc.msvc_notifications.models.Notificaciones;
import com.duoc.msvc_notifications.services.NotificationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationsController {

    @Autowired
    private NotificationsService service;

    // Crea/Envia una notificación
    @PostMapping
    public ResponseEntity<Notificaciones> crearNotificacion(@Valid @RequestBody Notificaciones notification) {
        Notificaciones creada = this.service.enviarNotificacion(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificaciones>> listarPorUsuario(@PathVariable Long usuarioId) {
        List<Notificaciones> historial = this.service.listarPorUsuario(usuarioId);

        // Si usuario no tiene notificaciones, devolvemos una lista vacía con 200 OK
        return ResponseEntity.status(HttpStatus.OK).body(historial);
    }
}
