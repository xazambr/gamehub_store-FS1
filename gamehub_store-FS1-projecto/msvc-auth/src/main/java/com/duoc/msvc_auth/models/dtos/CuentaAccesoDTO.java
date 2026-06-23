package com.duoc.msvc_auth.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CuentaAccesoDTO {
    @NotBlank(message = "El campo email no puede ser vacio")
    @Email(message = "El campo de correo tiene que tener el formato de correo")
    private String email;

    @NotBlank(message = "El campo password no puede ser vacio")
    private String passwordHash;

    @NotBlank(message = "El campo rol no puede ser vacio")
    private String rol;

    @NotBlank(message = "El campo activo no puede ser vacio")
    private String estado;
}
