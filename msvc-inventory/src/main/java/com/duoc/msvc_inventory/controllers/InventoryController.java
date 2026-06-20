package com.duoc.msvc_inventory.controllers;

import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.models.dtos.InventarioDTO;
import com.duoc.msvc_inventory.services.InventoryService;
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
@RequestMapping("/api/v1/inventario")
@Validated
@Tag(name = "Inventario V1", description = "Metodos CRUD para la gestion de inventario")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    @Operation(
            summary = "Listado de inventario",
            description = "Se devuelve una lista el inventario"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<List<Inventario>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inventoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca de inventario usando un id",
            description = "Devuelve el inventario, en caso contrario devuelve una excepcion"

    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Inventario encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = InventarioDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Inventario no se encuentra en la BD")
    })
    public ResponseEntity<Inventario> findById(
            @Parameter(description = "Id del inventario a buscar", required = true, example = "1")
            @PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.inventoryService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Guardado de inventario", description = "Esta es la forma de guardar un inventario")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "inventario a crear", required = true,
            content = @Content(schema = @Schema(implementation =InventarioDTO.class))
    )
    @ApiResponse(responseCode = "201",
            description = "inventario creado"
    )
    public ResponseEntity<Inventario> save(@RequestBody Inventario inventario) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.inventoryService.save(inventario));
    }

    @DeleteMapping
    @Operation(summary = "Eliminacion de inventario", description = "Se elimina un inventario")
    @ApiResponse(responseCode = "204", description = "inventario eliminado")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id del inventario a eliminar", required = true, example = "1")
            @RequestParam Long id) {
        this.inventoryService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion de inventario", description = "Se actualizan los datos de un inventario existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "inventario actualizado"),
            @ApiResponse(responseCode = "404", description = "inventario no se encuentra en la BD")
    })
    public ResponseEntity<Inventario> updateById(
            @Parameter(description = "Id del inventario a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Inventario inventario) {
        return ResponseEntity.status(HttpStatus.OK).body(this.inventoryService.updateById(id, inventario));
    }

}
