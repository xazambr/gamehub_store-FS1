package com.duoc.msvc_users.controllers;

import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.dtos.UsuarioDTO;
import com.duoc.msvc_users.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
@Validated
@Tag(name = "Usuarios V1", description = "Metodos CRUD para la gestion de Usuarios")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(
            summary = "Listado de usuarios",
            description = "Se devuelve una lista con todos los usuarios registrados"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca un usuario usando un id",
            description = "Devuelve un Usuario, en caso contrario devuelve una excepcion"

    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Usuario no se encuentra en la BD")
    })
    public ResponseEntity<Usuario> findById(
            @Parameter(description = "Id del usuario a buscar", required = true, example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(this.userService.findById(id));
    }


    @PostMapping
    @Operation(summary = "Guardado de Usuario", description = "Esta es la forma de guardar un usuario")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Usuario a crear", required = true,
            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
    )
    @ApiResponse(responseCode = "201",
                 description = "Usuario creado",
                 content = @Content(
                 examples = @ExampleObject(
                    name = "Creacion de Usuario",
                    value = """
                 {
                    "nombre": "Ejemplo creacion usuario",
                    "email": "Correo@example.com",
                    "telefono": "912345678",
                    "rol": "Cliente",
                    "estado": "ACTIVADO"
                 }
                 """
                 )
            )
    )
    public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok(this.userService.save(usuario));
    }


    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion de usuario", description = "Se actualizan los datos de un usuario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado"),
            @ApiResponse(responseCode = "404", description = "Usuario no se encuentra en la BD")
    })
    public ResponseEntity<Usuario> updateById(
            @Parameter(description = "Id del usuario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Usuario usuario
    ) {
        return ResponseEntity.ok(this.userService.UpdateById(id, usuario));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminacion de usuario", description = "Se elimina un usuario")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id del usuario a eliminar", required = true, example = "1")
            @RequestParam Long id
    ) {
        this.userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
