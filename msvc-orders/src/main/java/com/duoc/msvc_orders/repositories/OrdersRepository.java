package com.duoc.msvc_orders.repositories;

import com.duoc.msvc_orders.models.Ordenes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Ordenes, Long> {

    // Busca filtrando por el estado
    List<Ordenes> findByEstado(String estado);

    // Busca las órdenes por ID
    List<Ordenes> findByUsuarioId(Long usuarioId);
}
