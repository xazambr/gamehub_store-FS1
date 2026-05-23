package com.duoc.msvc_product.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "productos")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producto_id")
    private Long id;

    @NotBlank(message = "El cambo nombre no puede ser vacio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El cambo marca no puede ser vacio")
    @Column(nullable = false)
    private String marca;

    @NotBlank(message = "El cambo modelo no puede ser vacio")
    @Column(nullable = false)
    private String modelo;

    @NotBlank(message = "El cambo precio no puede ser vacio")
    @Column(nullable = false)
    private int precio;

    //conectar despues con categoria service
    //private String categoriaId;

    @Column(nullable = false)
    private String descripcion;

    @NotBlank(message = "El cambo estado no puede ser vacio")
    @Column(nullable = false)
    private  String estado;

}
