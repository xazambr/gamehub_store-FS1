package com.duoc.msvc_auth.controllers;

import com.duoc.msvc_auth.models.CuentaAcceso;
import com.duoc.msvc_auth.services.AuthService;
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
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping
    public ResponseEntity<List<CuentaAcceso>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findAll());
    }

    @GetMapping("{/id}")
    public ResponseEntity<CuentaAcceso> findById(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findById(id));
    }

    @GetMapping("{/email}")
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

}
