package com.duoc.msvc_category.controllers;

import com.duoc.msvc_category.assemblers.CategoryModelAssembler;
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
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/categorias")
@Validated
@Tag(name = "Categoria V2", description = "Metodos CRUD para la gestion de Categoria")
public class CategoryControllerV2 {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryModelAssembler categoryModelAssembler;

    @GetMapping
    @Operation(
            summary = "Listado de categorias",
            description = "Se devuelve una lista con todas las categorias registradas"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<CollectionModel<EntityModel<Categoria>>> findAll() {
        List<EntityModel<Categoria>> entityModels = this.categoryService.findAll()
                .stream()
                .map(categoryModelAssembler::toModel)
                .toList();

        CollectionModel<EntityModel<Categoria>> collectionModel = CollectionModel.of(
                entityModels,
                linkTo(methodOn(CategoryControllerV2.class).findAll()).withSelfRel()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(collectionModel);
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
    public ResponseEntity<EntityModel<Categoria>> findById(
            @Parameter(description = "Id de la categoria a buscar", required = true, example = "1")
            @PathVariable Long id){

        EntityModel<Categoria> entityModel = this.categoryModelAssembler.toModel(this.categoryService.findById(id));


        return ResponseEntity.ok(entityModel);
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
    public ResponseEntity<EntityModel<Categoria>> save(@Valid @RequestBody Categoria categoria){
        Categoria categoriaCreate = this.categoryService.save(categoria);
        EntityModel<Categoria> entityModel = this.categoryModelAssembler.toModel(categoriaCreate);

        return ResponseEntity.ok(entityModel);
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
    public ResponseEntity<EntityModel<Categoria>> updateById(
            @Parameter(description = "Id de la categoria a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Categoria categoria) {
        Categoria categoriaUpdate = this.categoryService.updateById(id, categoria);
        EntityModel<Categoria> entityModel = this.categoryModelAssembler.toModel(categoriaUpdate);
        return ResponseEntity.ok(entityModel);
    }

}
