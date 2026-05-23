package com.duoc.msvc_product.services;

import com.duoc.msvc_product.exceptions.ProductExceptions;
import com.duoc.msvc_product.models.Producto;
import com.duoc.msvc_product.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Producto> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Producto findById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductExceptions("Producto con id: " + id + " no encontrado")
        );
    }

    @Transactional
    @Override
    public Producto save(Producto producto) {
        if(this.productRepository.findById(producto.getId()).isPresent()) {
            throw new ProductExceptions("Producto con id: " + producto.getId() + " ya existe");
        }
        return productRepository.save(producto);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }

    @Override
    public Producto UpdateById(Long id, Producto producto) {
        return null;
    }
}
