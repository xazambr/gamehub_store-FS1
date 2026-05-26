package com.duoc.msvc_notifications.services;

import com.duoc.msvc_notifications.models.Notificaciones;
import com.duoc.msvc_notifications.repositories.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationsServiceImpl implements NotificationsService {

    @Autowired
    private NotificationsRepository repository;

    @Transactional
    @Override
    public Notificaciones enviarNotificacion(Notificaciones notification) {
        notification.setFechaEnvio(LocalDateTime.now());
        return this.repository.save(notification);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notificaciones> listarPorUsuario(Long usuarioId) {
        return this.repository.findByUsuarioId(usuarioId);
    }
}