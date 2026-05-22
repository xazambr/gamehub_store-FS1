package com.duoc.msvc_users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private Long id;

    @NotBlank(message = "El cambo nombre no puede ser vacio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El campo email no puede ser vacio")
    @Email(message = "El campo de correo tiene que tener el formato de correo")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El campo telefono no puede ser vacio")
    @Column(nullable = false)
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El teléfono debe contener entre 8 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "El campo rol no puede ser vacio")
    @Column(nullable = false)
    private String rol;

    @NotBlank(message = "El campo estado no puede ser vacio")
    @Column(nullable = false)
    private String estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Direccion> direcciones;

    @Embedded
    Audit audit = new Audit();

}
