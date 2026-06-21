package com.duoc.msvc_category.controllers;

import com.duoc.msvc_category.models.Categoria;
import com.duoc.msvc_category.models.dtos.CategoriaDTO;
import com.duoc.msvc_category.services.CategoryService;
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
@RequestMapping("/api/v1/categorias")
@Validated
@Tag(name = "Categoria V1", description = "Metodos CRUD para la gestion de Categoria")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @Operation(
            summary = "Listado de categorias",
            description = "Se devuelve una lista con todas las categorias registradas"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<List<Categoria>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca una Categoria usando un id",
            description = "Devuelve una categoria, en caso contrario devuelve una excepcion"

    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Categoria no se encuentra en la BD")
    })
    public ResponseEntity<Categoria> findById(
            @Parameter(description = "Id de la categoria a buscar", required = true, example = "1")
            @PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.categoryService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Guardado de Categoria", description = "Esta es la forma de guardar una categoria")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Categoria a crear", required = true,
            content = @Content(schema = @Schema(implementation = CategoriaDTO.class))
    )
    @ApiResponse(responseCode = "201",
            description = "Categoria creada"
    )
    public ResponseEntity<Categoria> save(@Valid @RequestBody Categoria categoria){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.categoryService.save(categoria));
    }

    @DeleteMapping
    @Operation(summary = "Eliminacion de Categoria", description = "Se elimina una Categoria")
    @ApiResponse(responseCode = "204", description = "Categoria eliminada")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id de la categoria a eliminar", required = true, example = "1")
            @RequestParam Long id) {
        this.categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion de categoria", description = "Se actualizan los datos de una categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria actualizada"),
            @ApiResponse(responseCode = "404", description = "Categoria no se encuentra en la BD")
    })
    public ResponseEntity<Categoria> updateById(
            @Parameter(description = "Id de la categoria a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.OK).body(this.categoryService.updateById(id, categoria));
    }

}
