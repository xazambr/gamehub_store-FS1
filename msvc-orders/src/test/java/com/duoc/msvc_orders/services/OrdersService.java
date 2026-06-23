package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.repositories.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock private OrdersRepository repository;
    @InjectMocks private OrdenesServiceImpl service;

    @Test
    void testFindById_CasoExitoso() {
        Ordenes mockOrder = new Ordenes();
        mockOrder.setId(1L);
        mockOrder.setTotal(5000.0);
        when(repository.findById(1L)).thenReturn(Optional.of(mockOrder));
        Ordenes resultado = service.BuscarPorId(1L);
        assertNotNull(resultado);
        assertEquals(5000.0, resultado.getTotal());
    }
}
