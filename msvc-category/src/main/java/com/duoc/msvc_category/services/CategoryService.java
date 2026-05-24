package com.duoc.msvc_category.services;

import com.duoc.msvc_category.models.Categoria;

import java.util.List;

public interface CategoryService {
    List<Categoria> findAll();
    Categoria findById(Long id);
    Categoria save(Categoria categoria);
    void deleteById(Long id);
    Categoria updateById(Long id, Categoria categoria);

}
