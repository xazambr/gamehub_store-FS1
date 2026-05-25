package com.duoc.msvc_auth.models.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

public class ExtUsuarioDTO {
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
}
