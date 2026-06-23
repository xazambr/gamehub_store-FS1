package com.duoc.msvc_promotion.dtos;

import org.springframework.hateoas.RepresentationModel;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromocionDTO extends RepresentationModel<PromocionDTO> {
    private Long id;
    private String codigo;
    private Double porcentajeDescuento;
    private LocalDateTime fechaExpiracion;
    private Boolean activo;
}
