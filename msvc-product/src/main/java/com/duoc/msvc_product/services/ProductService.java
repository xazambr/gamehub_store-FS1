package com.duoc.msvc_product.services;

import com.duoc.msvc_product.models.Producto;

import java.util.List;

public interface ProductService {
    List<Producto> findAll();
    Producto findById(Long id);
    Producto save(Producto producto);
    void deleteById(Long id);
    Producto UpdateById(Long id, Producto producto);

}
