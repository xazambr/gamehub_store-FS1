package com.duoc.msvc_users.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsuarioDTO {
    String nombre;
    String email;
    String telefono;
    String rol;
    String estado;
}
