package com.duoc.msvc_product.repositories;

import com.duoc.msvc_product.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Producto,Long> {

}
