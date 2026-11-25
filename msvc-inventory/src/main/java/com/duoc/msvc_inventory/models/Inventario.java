package com.duoc.msvc_inventory.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @Column(name = "inventario_id")
    private Long id;

    // Un producto tiene un unico registro de inventario. Se valida en el service (400 si falta).
    @Column(name = "producto_id", nullable = false, unique = true)
    private Long productoId;

    @NotNull(message = "El campo stock disponible no puede ser vacio")
    @Min(value = 0, message = "el stock disponible no puede ser negativo")
    @Column(nullable = false)
    private Integer stockDisponible;

    @NotNull(message = "El campo stock reservado no puede ser vacio")
    @Min(value = 0, message = "el stock reservado no puede ser negativo")
    @Column(nullable = false)
    private Integer stockReservado;

    @NotNull(message = "El campo stock minimo no puede ser vacio")
    @Min(value = 0, message = "el stock minimo no puede ser negativo")
    @Column(nullable = false)
    private Integer stockMinimo;

    @NotBlank(message = "El campo ubicacion no puede ser vacio")
    @Column(nullable = false)
    private String ubicacion;

    @Embedded
    private Audit audit = new Audit();

    @PrePersist
    void prePersist() {
        if (audit == null) audit = new Audit();
        audit.prePersist();
    }

    @PreUpdate
    void preUpdate() {
        if (audit == null) audit = new Audit();
        audit.preUpdate();
    }
}
