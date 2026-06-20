package com.duoc.msvc_users.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsuarioDTO {
    @NotBlank(message = "El cambo nombre no puede ser vacio")
    private String nombre;

    @NotBlank(message = "El campo email no puede ser vacio")
    @Email(message = "El campo de correo tiene que tener el formato de correo")
    private String email;

    @NotBlank(message = "El campo telefono no puede ser vacio")
    @Pattern(regexp = "^[0-9]{8,15}$", message = "El teléfono debe contener entre 8 y 15 dígitos")
    private String telefono;

    @NotBlank(message = "El campo rol no puede ser vacio")
    private String rol;

    @NotBlank(message = "El campo estado no puede ser vacio")
    private String estado;

}
