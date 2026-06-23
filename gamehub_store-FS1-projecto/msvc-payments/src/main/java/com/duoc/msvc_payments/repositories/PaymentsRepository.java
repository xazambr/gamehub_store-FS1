package com.duoc.msvc_payments.repositories;

import com.duoc.msvc_payments.models.Pagos;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentsRepository extends CrudRepository<Pagos, Long> {
    Optional<Pagos> findByOrdenId(Long ordenId);
}
