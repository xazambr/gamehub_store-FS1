package com.duoc.msvc_users.repositories;

import com.duoc.msvc_users.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
    // findBy + Username  ->  SELECT * FROM usuarios WHERE username = ?  (lo usa el login)
    Optional<Usuario> findByUsername(String username);
    // existsBy + Username  ->  devuelve true/false (lo usa el registro para evitar duplicados)
    boolean existsByUsername(String username);
}
