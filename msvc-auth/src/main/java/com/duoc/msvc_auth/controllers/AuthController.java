package com.duoc.msvc_auth.controllers;

import com.duoc.msvc_auth.models.CuentaAcceso;
import com.duoc.msvc_auth.services.AuthService;
import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.models.dtos.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
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
@RequestMapping("/api/v1/auth")
@Validated
@Tag(name = "Auth V1", description = "Metodos CRUD para la gestion de Autenticacion")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    @Operation(
            summary = "Autenticacion",
            description = "Se devuelve una lista con todos los usuarios Autenticados"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<List<CuentaAcceso>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca un usuario autenticado usando un id",
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
    public ResponseEntity<CuentaAcceso> findById(
            @Parameter(description = "Id del usuario a buscar", required = true, example = "1")
            @PathVariable long id
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findById(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<CuentaAcceso> findByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findByEmail(email));
    }

    @PostMapping
    public ResponseEntity<CuentaAcceso> save(@Valid @RequestBody CuentaAcceso usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.authService.save(usuario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        this.authService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaAcceso> updateById(@PathVariable Long id, @Valid @RequestBody CuentaAcceso cuenta) {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.UpdateById(id, cuenta));
    }
}
