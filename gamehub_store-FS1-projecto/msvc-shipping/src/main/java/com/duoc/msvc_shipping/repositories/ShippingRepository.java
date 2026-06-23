package com.duoc.msvc_shipping.repositories;

import com.duoc.msvc_shipping.models.Envio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShippingRepository extends CrudRepository<Envio, Long> {

    // Para  consultar el estado de un despacho usando el ID de la Orden
    Optional<Envio> findByOrdenId(Long ordenId);
}
