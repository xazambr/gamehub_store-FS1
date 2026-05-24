package com.duoc.msvc_category.controllers;

import com.duoc.msvc_category.models.Categoria;
import com.duoc.msvc_category.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorias")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.categoryService.save(categoria));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
