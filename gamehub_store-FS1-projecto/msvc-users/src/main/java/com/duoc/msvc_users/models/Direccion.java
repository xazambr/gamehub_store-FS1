package com.duoc.msvc_users.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direccion_id")
    private long id;

    @NotBlank(message = "El cambo comuna no puede ser vacio")
    @Column(nullable = false)
    private String comuna;

    @NotBlank(message = "El cambo ciudad no puede ser vacio")
    @Column(nullable = false)
    private String ciudad;

    @NotBlank(message = "El cambo calle no puede ser vacio")
    @Column(nullable = false)
    private String calle;

    @NotBlank(message = "El cambo numero no puede ser vacio")
    @Column(nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Embedded
    Audit audit = new Audit();
}
