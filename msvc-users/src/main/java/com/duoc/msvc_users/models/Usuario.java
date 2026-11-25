package com.duoc.msvc_users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Long usuarioId;

    @NotBlank
    @Column(unique = true, nullable = false)
    private String username;

    // Se guarda CIFRADA con BCrypt, nunca en texto plano.
    @NotBlank
    @Column(nullable = false)
    private String password;

    // Relacion muchos-a-muchos: un usuario tiene varios roles y un rol lo comparten varios usuarios.
    // Se crea una tabla intermedia 'usuario_roles' con las dos llaves foraneas.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    private Set<Rol> roles = new HashSet<>();

    @Embedded
    Audit audit = new Audit();

}
