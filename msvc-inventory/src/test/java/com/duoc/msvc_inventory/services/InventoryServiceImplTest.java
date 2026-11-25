package com.duoc.msvc_inventory.services;

import com.duoc.msvc_inventory.exceptions.InventoryExceptions;
import com.duoc.msvc_inventory.models.Inventario;
import com.duoc.msvc_inventory.repositories.InventoryRepository;
import com.duoc.msvc_inventory.repositories.MovimientoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock private InventoryRepository repository;
    @Mock private MovimientoRepository movimientoRepository;
    @InjectMocks private InventoryServiceImpl service;

    private Inventario inventario(int disponible, int reservado) {
        Inventario inv = new Inventario();
        inv.setId(1L);
        inv.setProductoId(10L);
        inv.setStockDisponible(disponible);
        inv.setStockReservado(reservado);
        inv.setStockMinimo(1);
        inv.setUbicacion("A1");
        return inv;
    }

    @Test
    void reservar_mueveStockDeDisponibleAReservado() {
        when(repository.findByProductoIdForUpdate(10L)).thenReturn(Optional.of(inventario(5, 0)));
        when(repository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));

        Inventario r = service.reservar(10L, 2);

        assertEquals(3, r.getStockDisponible());
        assertEquals(2, r.getStockReservado());
    }

    @Test
    void reservar_sinStockSuficiente_lanzaConflict() {
        when(repository.findByProductoIdForUpdate(10L)).thenReturn(Optional.of(inventario(1, 0)));

        InventoryExceptions ex = assertThrows(InventoryExceptions.class, () -> service.reservar(10L, 2));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        verify(repository, never()).save(any());
    }

    @Test
    void liberar_devuelveStockADisponible() {
        when(repository.findByProductoIdForUpdate(10L)).thenReturn(Optional.of(inventario(3, 2)));
        when(repository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));

        Inventario r = service.liberar(10L, 2);

        assertEquals(5, r.getStockDisponible());
        assertEquals(0, r.getStockReservado());
    }

    @Test
    void confirmar_descuentaReservado() {
        when(repository.findByProductoIdForUpdate(10L)).thenReturn(Optional.of(inventario(3, 2)));
        when(repository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));

        Inventario r = service.confirmar(10L, 2);

        assertEquals(3, r.getStockDisponible());
        assertEquals(0, r.getStockReservado());
    }

    @Test
    void reservar_productoSinInventario_lanzaNotFound() {
        when(repository.findByProductoIdForUpdate(99L)).thenReturn(Optional.empty());

        InventoryExceptions ex = assertThrows(InventoryExceptions.class, () -> service.reservar(99L, 1));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }

    @Test
    void findById_inexistente_lanzaNotFound() {
        when(repository.findById(7L)).thenReturn(Optional.empty());

        InventoryExceptions ex = assertThrows(InventoryExceptions.class, () -> service.findById(7L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }
}
