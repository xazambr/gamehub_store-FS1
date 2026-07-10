package com.duoc.msvc_users.repositories;

import com.duoc.msvc_users.models.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    // Busca un rol por su nombre (ej: "ROLE_MEDICO"). Lo usan el registro y el seed inicial.
    Optional<Rol> findByNombre(String nombre);
}