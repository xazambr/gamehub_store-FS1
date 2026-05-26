package com.duoc.msvc_notifications.services;

import com.duoc.msvc_notifications.models.Notificaciones;

import java.util.List;

public interface NotificationsService {
    // Para registrar una alerta
    Notificaciones enviarNotificacion(Notificaciones notification);

    // Para el historial
    List<Notificaciones> listarPorUsuario(Long usuarioId);
}
