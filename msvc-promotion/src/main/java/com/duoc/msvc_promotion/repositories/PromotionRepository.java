package com.duoc.msvc_promotion.repositories;

import com.duoc.msvc_promotion.models.Promociones;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends CrudRepository<Promociones, Long> {

    // Busca el cupón por su texto ej: msvc2026
    // Optional por si el usuario escribe un código que no existe
    Optional<Promociones> findByCodigo(String codigo);
}
