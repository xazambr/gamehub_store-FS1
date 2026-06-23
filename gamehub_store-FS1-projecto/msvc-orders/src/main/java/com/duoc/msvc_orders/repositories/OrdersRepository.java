package com.duoc.msvc_orders.repositories;

import com.duoc.msvc_orders.models.Ordenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Ordenes, Long> {

    // Buscar filtrando el estado
    List<Ordenes> findByEstado(String estado);

    // Buscar órdenes por ID
    List<Ordenes> findByUsuarioId(Long usuarioId);
}
