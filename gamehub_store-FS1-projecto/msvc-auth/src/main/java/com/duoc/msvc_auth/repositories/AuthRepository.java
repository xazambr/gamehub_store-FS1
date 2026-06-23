package com.duoc.msvc_auth.repositories;

import com.duoc.msvc_auth.models.CuentaAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<CuentaAcceso, Long> {
    Optional<CuentaAcceso> findByEmail(String email);
}
