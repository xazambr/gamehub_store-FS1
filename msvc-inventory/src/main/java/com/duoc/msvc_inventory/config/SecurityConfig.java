package com.duoc.msvc_inventory.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

// Seguridad del microservicio de inventario (Resource Server JWT).
// Valida el token emitido por msvc-users y autoriza segun el rol.
@Configuration
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKey key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }

    // Convierte el claim "roles" (ej: ["ROLE_ADMIN"]) en authorities para hasRole(...).
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter authorities = new JwtGrantedAuthoritiesConverter();
        authorities.setAuthoritiesClaimName("roles");
        authorities.setAuthorityPrefix("");
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(authorities);
        return converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationConverter converter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // Se evaluan EN ORDEN; la primera que coincide gana.
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/docs/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()
                        // Consultar inventario: cualquier rol autenticado.
                        .requestMatchers(HttpMethod.GET, "/api/v1/inventario/**", "/api/v2/inventario/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "CLIENTE")
                        // Reservar/liberar/confirmar stock: lo invoca msvc-orders con el token de quien
                        // crea o cancela la orden (incluye CLIENTE).
                        .requestMatchers(HttpMethod.POST, "/api/v1/inventario/reservas/**")
                        .hasAnyRole("ADMIN", "OPERADOR", "CLIENTE")
                        // Crear, editar y borrar inventario: solo personal.
                        .requestMatchers("/api/v1/inventario/**", "/api/v2/inventario/**")
                        .hasAnyRole("ADMIN", "OPERADOR")
                        .anyRequest().authenticated())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth -> oauth.jwt(jwt -> jwt.jwtAuthenticationConverter(converter)))
                .headers(h -> h.frameOptions(f -> f.disable()));
        return http.build();
    }
}
