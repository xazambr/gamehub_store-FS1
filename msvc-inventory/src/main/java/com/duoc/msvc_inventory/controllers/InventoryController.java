package com.duoc.msvc_inventory.controllers;

import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventario")
@Validated
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<List<Inventario>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inventoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> findById(@PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inventoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Inventario> save(@RequestBody Inventario inventario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.inventoryService.save(inventario));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        this.inventoryService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventario> updateById(@PathVariable Long id, @Valid @RequestBody Inventario inventario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryService.updateById(id, inventario));
    }

}
