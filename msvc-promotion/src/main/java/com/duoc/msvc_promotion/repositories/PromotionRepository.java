package com.duoc.msvc_promotion.repositories;

import com.duoc.msvc_promotion.models.Promociones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promociones, Long> {
    Optional<Promociones> findByCodigo(String codigo);
}