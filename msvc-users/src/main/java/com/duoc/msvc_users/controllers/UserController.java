package com.duoc.msvc_users.controllers;

import com.duoc.msvc_users.models.Usuario;
import com.duoc.msvc_users.services.UserService;
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
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.userService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Usuario> save(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.userService.save(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateById(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.UpdateById(id, usuario));
    }


}
