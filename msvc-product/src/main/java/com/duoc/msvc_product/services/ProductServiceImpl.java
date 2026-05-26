package com.duoc.msvc_product.services;

import com.duoc.msvc_product.clients.CategoryClient;
import com.duoc.msvc_product.exceptions.ProductExceptions;
import com.duoc.msvc_product.models.Producto;
import com.duoc.msvc_product.models.dtos.ExtCategoriaDTO;
import com.duoc.msvc_product.repositories.ProductRepository;
import com.duoc.msvc_users.exceptions.UserExceptions;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryClient categoryClient;

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
        try {
            ExtCategoriaDTO categoria = categoryClient.findById(producto.getCategoriaId());

            // Regla de negocio extra: Controlar que la categoría no esté inactiva [cite: 124]
            if ("INACTIVO".equals(categoria.getEstado())) {
                throw new ProductExceptions("La categoría asociada se encuentra inactiva");
            }
        } catch (FeignException.NotFound e) {
            throw new ProductExceptions("La categoría con ID " + producto.getCategoriaId() + " no existe en el sistema.");
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
        return this.productRepository.findById(id).map(element-> {
            element.setNombre(producto.getNombre());
            element.setMarca(producto.getMarca());
            element.setModelo(producto.getModelo());
            element.setPrecio(producto.getPrecio());
            element.setDescripcion(producto.getDescripcion());
            return this.productRepository.save(element);
        }).orElseThrow(
                () -> new UserExceptions("La producto con id: " + id + " no existe")
        );
    }
}
