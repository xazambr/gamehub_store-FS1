package com.duoc.msvc_users.config;

import com.duoc.msvc_users.models.Rol;
import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.repositories.RolRepository;
import com.duoc.msvc_users.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

// Siembra datos al arrancar: los 3 roles y 3 usuarios de prueba (uno por rol).
// Asi la demo tiene credenciales listas sin tener que registrarse a mano.
@Component
public class DataLoader implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(RolRepository rolRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.rolRepository = rolRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        Rol admin = obtenerOCrearRol("ROLE_ADMIN");
        Rol medico = obtenerOCrearRol("ROLE_OPERADOR");
        Rol paciente = obtenerOCrearRol("ROLE_CLIENTE");

        crearUsuarioSiNoExiste("admin", "admin123", Set.of(admin));
        crearUsuarioSiNoExiste("operador1", "operador123", Set.of(medico));
        crearUsuarioSiNoExiste("cliente1", "cliente123", Set.of(paciente));
    }

    private Rol obtenerOCrearRol(String nombre) {
        return this.rolRepository.findByNombre(nombre).orElseGet(() -> this.rolRepository.save(new Rol(nombre)));
    }

    private void crearUsuarioSiNoExiste(String username, String passwordPlano, Set<Rol> roles) {
        if (this.userRepository.existsByUsername(username)) {
            return;
        }
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(this.passwordEncoder.encode(passwordPlano));
        usuario.setRoles(roles);
        this.userRepository.save(usuario);
    }
}