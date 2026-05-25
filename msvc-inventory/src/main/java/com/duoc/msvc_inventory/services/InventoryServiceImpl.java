package com.duoc.msvc_inventory.services;

import com.duoc.msvc_inventory.exceptions.InventoryExceptions;
import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public List<Inventario> findAll() {
        return this.inventoryRepository.findAll();
    }

    @Override
    public Inventario findById(Long id) {
        return this.inventoryRepository.findById(id).orElseThrow(
                () -> new InventoryExceptions("Objeto con id: " +  id + " no encontrado")
        );
    }

    @Override
    public Inventario save(Inventario inventario) {
        return this.inventoryRepository.save(inventario);
    }

    @Override
    public void deleteById(Long id) {
        this.inventoryRepository.deleteById(id);
    }

    @Override
    public Inventario update(Inventario inventario) {
        return null;
    }
}
