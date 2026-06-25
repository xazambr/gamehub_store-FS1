package com.duoc.msvc_users.services;

import com.duoc.msvc_users.dtos.AuthResponse;
import com.duoc.msvc_users.dtos.LoginRequest;
import com.duoc.msvc_users.dtos.RegisterRequest;
import com.duoc.msvc_users.models.Rol;
import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.repositories.RolRepository;
import com.duoc.msvc_users.repositories.UserRepository;
import com.duoc.msvc_users.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RolRepository rolRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if(this.userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El Usuario ya existe");
        }

        Set<String> nombresRoles = (request.getRoles() == null || request.getRoles().isEmpty())
                ? Set.of("ROLE_CLIENTE")
                : request.getRoles();

        Set<Rol> roles = nombresRoles.stream()
                .map(nombre -> this.rolRepository.findByNombre(nombre).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no existe: " + nombre)))
                .collect(Collectors.toCollection(HashSet::new));


        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(this.passwordEncoder.encode(request.getPassword()));
        usuario.setRoles(roles);
        this.userRepository.save(usuario);

        return construirRespuesta(usuario);
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // Buscar el usuario. Si no existe, se responde el MISMO error que clave mala,
        // para no revelar si el problema fue el usuario o la contrasena.
        Usuario usuario = this.userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas"));

        // Comparar la clave enviada contra el hash BCrypt guardado. matches() vuelve a cifrar y compara.
        if (!this.passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales invalidas");
        }

        return construirRespuesta(usuario);
    }



    private AuthResponse construirRespuesta(Usuario usuario) {
        String token = this.jwtService.generarToken(usuario);
        Set<String> roles = usuario.getRoles().stream().map(Rol::getNombre).collect(Collectors.toSet());
        return new AuthResponse(token, "Bearer", usuario.getUsername(), roles);
    }
}
