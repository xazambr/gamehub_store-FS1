package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.clients.InventoryClient;
import com.duoc.msvc_orders.exceptions.OrderException;
import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.repositories.OrdersRepository;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrdenesServiceImplTest {

    @Mock private OrdersRepository repository;
    @Mock private InventoryClient inventoryClient;
    @InjectMocks private OrdenesServiceImpl service;

    private Ordenes orden(String estado, Long... juegos) {
        Ordenes o = new Ordenes();
        o.setId(1L);
        o.setUsuarioId(1L);
        o.setEstado(estado);
        o.setMetodoPago("tarjeta");
        o.setTotal(5000.0);
        o.setJuegosIds(List.of(juegos));
        return o;
    }

    @Test
    void buscarPorId_existente() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden("creada", 3L)));

        Optional<Ordenes> r = service.buscarPorId(1L);

        assertTrue(r.isPresent());
        assertEquals(5000.0, r.get().getTotal());
    }

    @Test
    void crear_reservaStockPorCadaJuegoAgrupado() {
        when(repository.save(any(Ordenes.class))).thenAnswer(i -> i.getArgument(0));

        Ordenes r = service.crear(orden("lo-que-sea", 3L, 3L, 5L));

        assertEquals("creada", r.getEstado());
        assertNotNull(r.getFechaCreacion());
        verify(inventoryClient).reservar(3L, 2);
        verify(inventoryClient).reservar(5L, 1);
    }

    @Test
    void crear_sinStock_liberaLoReservadoYNoGuarda() {
        FeignException conflicto = mock(FeignException.class);
        when(conflicto.status()).thenReturn(409);
        doThrow(conflicto).when(inventoryClient).reservar(5L, 1);

        OrderException ex = assertThrows(OrderException.class, () -> service.crear(orden("creada", 3L, 5L)));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        verify(inventoryClient).liberar(3L, 1);
        verify(repository, never()).save(any());
    }

    @Test
    void cancelar_liberaStockYMarcaCancelada() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden("creada", 3L, 3L)));
        when(repository.save(any(Ordenes.class))).thenAnswer(i -> i.getArgument(0));

        Ordenes r = service.cancelarOrden(1L, "ya no lo quiero");

        assertEquals("cancelada", r.getEstado());
        verify(inventoryClient).liberar(3L, 2);
    }

    @Test
    void cancelar_ordenYaCancelada_lanzaConflict() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden("cancelada", 3L)));

        OrderException ex = assertThrows(OrderException.class, () -> service.cancelarOrden(1L, "x"));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
        verifyNoInteractions(inventoryClient);
    }

    @Test
    void actualizarEstado_enviada_confirmaSalidaDeStock() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden("pagada", 3L)));
        when(repository.save(any(Ordenes.class))).thenAnswer(i -> i.getArgument(0));

        Ordenes r = service.actualizarEstado(1L, "ENVIADA");

        assertEquals("enviada", r.getEstado());
        verify(inventoryClient).confirmar(3L, 1);
    }

    @Test
    void actualizarEstado_saltoInvalido_lanzaConflict() {
        when(repository.findById(1L)).thenReturn(Optional.of(orden("creada", 3L)));

        OrderException ex = assertThrows(OrderException.class, () -> service.actualizarEstado(1L, "enviada"));

        assertEquals(HttpStatus.CONFLICT, ex.getStatus());
    }

    @Test
    void actualizarEstado_ordenInexistente_lanzaNotFound() {
        when(repository.findById(9L)).thenReturn(Optional.empty());

        OrderException ex = assertThrows(OrderException.class, () -> service.actualizarEstado(9L, "pagada"));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
    }
}
