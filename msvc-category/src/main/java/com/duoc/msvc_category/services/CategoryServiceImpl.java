package com.duoc.msvc_category.services;

import com.duoc.msvc_category.exceptions.CategoryExceptions;
import com.duoc.msvc_category.models.Categoria;
import com.duoc.msvc_category.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Categoria> findAll() {
        return this.categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Categoria findById(Long id) {
        return this.categoryRepository.findById(id).orElseThrow(
                () -> new CategoryExceptions("el usuario de id: " + id + " no existe")
        );
    }

    @Transactional
    @Override
    public Categoria save(Categoria categoria) {
        if(this.categoryRepository.findById(categoria.getId()).isPresent()) {
            throw new CategoryExceptions("el usuario de id: " + categoria.getId() + " ya  existe");
        }
        return this.categoryRepository.save(categoria);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.categoryRepository.deleteById(id);
    }

    @Override
    public Categoria updateById(Long id, Categoria categoria) {
        return null;
    }
}
