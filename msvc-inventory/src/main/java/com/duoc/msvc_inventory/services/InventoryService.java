package com.duoc.msvc_inventory.services;

import com.duoc.msvc_inventory.models.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    List<Inventario> findAll();
    Inventario findById(Long id);
    Inventario save(Inventario inventario);
    void deleteById(Long id);
    Inventario update(Inventario inventario);

}
