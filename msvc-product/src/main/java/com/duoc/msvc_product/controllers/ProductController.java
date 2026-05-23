package com.duoc.msvc_product.controllers;

import com.duoc.msvc_product.models.Producto;
import com.duoc.msvc_product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/productos")
@Validated
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> findById(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Producto> save(@RequestBody @Validated Producto producto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.save(producto));
    }




}
