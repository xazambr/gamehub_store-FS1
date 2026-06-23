package com.duoc.msvc_product.controllers;

import com.duoc.msvc_product.models.Producto;
import com.duoc.msvc_product.models.dtos.ProductoDTO;
import com.duoc.msvc_product.services.ProductService;
import com.duoc.msvc_users.models.dtos.UsuarioDTO;
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
@RequestMapping("/api/v1/productos")
@Validated
@Tag(name = "Productos V1", description = "Metodos CRUD para la gestion de productps")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(
            summary = "Listado de productos",
            description = "Se devuelve una lista con todos los productos"
    )
    @ApiResponse(responseCode = "200", description = "Operacion Exitosa")
    public ResponseEntity<List<Producto>> findAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca un producto usando un id",
            description = "Devuelve un producto, en caso contrario devuelve una excepcion"

    )
    @ApiResponses(value={
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductoDTO.class)
                    )),
            @ApiResponse(responseCode = "404", description = "Producto no se encuentra en la BD")
    })
    public ResponseEntity<Producto> findById(
            @Parameter(description = "Id del producto a buscar", required = true, example = "1")
            @PathVariable long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Guardado de producti", description = "Esta es la forma de guardar un producto")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "producto a crear", required = true,
            content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
    )
    @ApiResponse(responseCode = "201",
            description = "Usuario creado",
            content = @Content)
    public ResponseEntity<Producto> save(@RequestBody @Validated Producto producto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.save(producto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizacion de producto", description = "Se actualizan los datos de un producto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no se encuentra en la BD")
    })
    public ResponseEntity<Producto> updateById(
            @Parameter(description = "Id del producto a actualizar", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody Producto producto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productService.UpdateById(id, producto));
    }

    @DeleteMapping
    @Operation(summary = "Eliminacion de producto", description = "Se elimina un producto")
    @ApiResponse(responseCode = "204", description = "Producto eliminado")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Id del producto a eliminar", required = true, example = "1")
            @RequestParam Long id) {
        this.productService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }




}
