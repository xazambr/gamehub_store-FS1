package com.duoc.msvc_category.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "categorias")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private Long id;

    @NotBlank(message = "El campo nombre no puede ser vacio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El campo descripcion no puede ser vacio")
    @Column(nullable = false)
    private String descripcion;

    @NotBlank(message = "El campo estado no puede ser vacio")
    @Column(nullable = false)
    private String estado;

}
