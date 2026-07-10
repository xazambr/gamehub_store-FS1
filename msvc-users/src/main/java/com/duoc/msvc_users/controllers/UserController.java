package com.duoc.msvc_users.controllers;

import com.duoc.msvc_users.dtos.UsuarioDTO;
import com.duoc.msvc_users.models.Rol;
import com.duoc.msvc_users.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// Endpoint PROTEGIDO: solo un ADMIN puede listar los usuarios. Demuestra el control por rol.
@RestController
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuarios", description = "Gestion de usuarios (requiere token)")
@SecurityRequirement(name = "bearer-jwt")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository usuarioRepository) {
        this.userRepository = usuarioRepository;
    }

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Solo ADMIN. Devuelve usuarios sin la contrasena.")
    // @PreAuthorize evalua la expresion ANTES de ejecutar el metodo. hasRole('ADMIN') exige el authority ROLE_ADMIN.
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> usuarios = this.userRepository.findAll().stream()
                .map(u -> new UsuarioDTO(
                        u.getUsuarioId(),
                        u.getUsername(),
                        u.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet())))
                .toList();
        return ResponseEntity.ok(usuarios);
    }
}