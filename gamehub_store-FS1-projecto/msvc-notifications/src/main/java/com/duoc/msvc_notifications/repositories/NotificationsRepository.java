package com.duoc.msvc_notifications.repositories;

import com.duoc.msvc_notifications.models.Notificaciones;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationsRepository extends CrudRepository<Notificaciones, Long> {

    // para obtener el historial
    List<Notificaciones> findByUsuarioId(Long usuarioId);
}
