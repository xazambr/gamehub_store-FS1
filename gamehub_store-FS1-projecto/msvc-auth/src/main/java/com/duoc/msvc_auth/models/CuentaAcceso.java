package com.duoc.msvc_auth.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class CuentaAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id")
    private Long id;

    @NotBlank(message = "El campo email no puede ser vacio")
    @Email(message = "El campo de correo tiene que tener el formato de correo")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El campo password no puede ser vacio")
    @Column(nullable = false)
    private String passwordHash;

    @NotBlank(message = "El campo rol no puede ser vacio")
    @Column(nullable = false)
    private String rol;

    @NotBlank(message = "El campo activo no puede ser vacio")
    @Column
    private String estado;

    @Embedded
    Audit audit = new Audit();

}
