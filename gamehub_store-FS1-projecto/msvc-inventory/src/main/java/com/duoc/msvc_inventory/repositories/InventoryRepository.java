package com.duoc.msvc_inventory.repositories;

import com.duoc.msvc_inventory.models.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventario, Long>{
}
