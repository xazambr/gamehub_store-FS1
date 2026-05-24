package com.duoc.msvc_inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "inventario")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="categoria:_id")
    private Long id;

    @NotBlank(message = "El campo stock disponible no puede ser vacio")
    @Column
    private int stockDisponible;

    @NotBlank(message = "El campo stock reservado no puede ser vacio")
    @Column(nullable = false)
    private int stockReservado;

    @NotBlank(message = "El campo stock minimo no puede ser vacio")
    @Column(nullable = false)
    private int stockMinimo;

    @NotBlank(message = "El campo ubicacion no puede ser vacio")
    @Column(nullable = false)
    private int ubicacion;

    @Embedded
    Audit audit = new Audit();



}
