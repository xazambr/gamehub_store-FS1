package com.duoc.msvc_inventory.repositories;

import com.duoc.msvc_inventory.models.Inventario;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventario, Long> {

    Optional<Inventario> findByProductoId(Long productoId);

    boolean existsByProductoId(Long productoId);

    // Bloqueo de fila: dos reservas simultaneas del mismo producto no pueden pisarse el stock.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Inventario i where i.productoId = :productoId")
    Optional<Inventario> findByProductoIdForUpdate(@Param("productoId") Long productoId);
}
