package com.duoc.msvc_orders.services;

import com.duoc.msvc_orders.clients.InventoryClient;
import com.duoc.msvc_orders.exceptions.OrderException;
import com.duoc.msvc_orders.models.Ordenes;
import com.duoc.msvc_orders.repositories.OrdersRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class OrdenesServiceImpl implements OrdersService {

    // Flujo valido: creada -> pagada -> enviada. Se puede cancelar mientras no este enviada.
    private static final Map<String, Set<String>> TRANSICIONES = Map.of(
            "creada", Set.of("pagada", "cancelada"),
            "pagada", Set.of("enviada", "cancelada"),
            "enviada", Set.of(),
            "cancelada", Set.of());

    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private InventoryClient inventoryClient;

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarTodas() {
        return this.ordersRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Ordenes> buscarPorId(Long id) {
        return this.ordersRepository.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarPorEstado(String estado) {
        return this.ordersRepository.findByEstado(estado.toLowerCase());
    }

    @Transactional(readOnly = true)
    @Override
    public List<Ordenes> listarPorUsuario(Long usuarioId) {
        return this.ordersRepository.findByUsuarioId(usuarioId);
    }

    // Al crear la orden se RESERVA el stock de cada juego en msvc-inventory. Si algun juego falla
    // (sin inventario o sin stock) se deshacen las reservas ya hechas y no se guarda la orden.
    @Transactional
    @Override
    public Ordenes crear(Ordenes orden) {
        Map<Long, Integer> cantidades = contarUnidades(orden.getJuegosIds());
        Map<Long, Integer> reservados = new LinkedHashMap<>();

        try {
            for (Map.Entry<Long, Integer> e : cantidades.entrySet()) {
                this.inventoryClient.reservar(e.getKey(), e.getValue());
                reservados.put(e.getKey(), e.getValue());
            }
        } catch (FeignException e) {
            liberarSilencioso(reservados);
            throw traducir(e, "reservar stock");
        }

        orden.setId(null);
        orden.setEstado("creada");
        orden.setFechaCreacion(LocalDateTime.now());
        if (orden.getTotal() == null) {
            orden.setTotal(0.0);
        }

        try {
            return this.ordersRepository.save(orden);
        } catch (RuntimeException e) {
            liberarSilencioso(reservados);
            throw e;
        }
    }

    @Transactional
    @Override
    public Ordenes actualizarEstado(Long id, String nuevoEstado) {
        Ordenes orden = obtener(id);
        String destino = normalizarEstado(nuevoEstado);

        if ("cancelada".equals(destino)) {
            return cancelarOrden(id, null);
        }
        validarTransicion(orden, destino);

        if ("enviada".equals(destino)) {
            // El producto sale del almacen: lo reservado se descuenta definitivamente.
            try {
                for (Map.Entry<Long, Integer> e : contarUnidades(orden.getJuegosIds()).entrySet()) {
                    this.inventoryClient.confirmar(e.getKey(), e.getValue());
                }
            } catch (FeignException e) {
                throw traducir(e, "confirmar la salida de stock");
            }
        }

        orden.setEstado(destino);
        orden.setFechaActualizacion(LocalDateTime.now());
        return this.ordersRepository.save(orden);
    }

    // Cancelar devuelve el stock reservado al disponible.
    @Transactional
    @Override
    public Ordenes cancelarOrden(Long id, String motivo) {
        Ordenes orden = obtener(id);
        validarTransicion(orden, "cancelada");

        try {
            for (Map.Entry<Long, Integer> e : contarUnidades(orden.getJuegosIds()).entrySet()) {
                this.inventoryClient.liberar(e.getKey(), e.getValue());
            }
        } catch (FeignException e) {
            throw traducir(e, "liberar el stock");
        }

        orden.setEstado("cancelada");
        orden.setFechaActualizacion(LocalDateTime.now());
        return this.ordersRepository.save(orden);
    }

    // ---------- auxiliares ----------

    private Ordenes obtener(Long id) {
        return this.ordersRepository.findById(id).orElseThrow(
                () -> new OrderException("Orden no encontrada con ID: " + id, HttpStatus.NOT_FOUND));
    }

    private String normalizarEstado(String estado) {
        String e = estado == null ? "" : estado.trim().toLowerCase();
        if (!TRANSICIONES.containsKey(e)) {
            throw new OrderException("Estado invalido: '" + estado + "'. Validos: creada, pagada, enviada, cancelada");
        }
        return e;
    }

    private void validarTransicion(Ordenes orden, String destino) {
        String actual = orden.getEstado() == null ? "" : orden.getEstado().toLowerCase();
        if (!TRANSICIONES.getOrDefault(actual, Set.of()).contains(destino)) {
            throw new OrderException("No se puede pasar de '" + actual + "' a '" + destino + "'", HttpStatus.CONFLICT);
        }
    }

    // [3,3,5] -> {3=2, 5=1}
    private Map<Long, Integer> contarUnidades(List<Long> juegosIds) {
        Map<Long, Integer> cantidades = new LinkedHashMap<>();
        if (juegosIds != null) {
            for (Long idJuego : juegosIds) {
                cantidades.merge(idJuego, 1, Integer::sum);
            }
        }
        return cantidades;
    }

    private void liberarSilencioso(Map<Long, Integer> reservados) {
        for (Map.Entry<Long, Integer> e : new ArrayList<>(reservados.entrySet())) {
            try {
                this.inventoryClient.liberar(e.getKey(), e.getValue());
            } catch (RuntimeException ignorado) {
                // mejor esfuerzo: no ocultar el error original
            }
        }
    }

    private OrderException traducir(FeignException e, String accion) {
        return switch (e.status()) {
            case 404 -> new OrderException("Un juego de la orden no tiene inventario registrado", HttpStatus.CONFLICT);
            case 409 -> new OrderException("Stock insuficiente en inventario", HttpStatus.CONFLICT);
            case 401, 403 -> new OrderException("Sin permiso para " + accion + " en inventario", HttpStatus.FORBIDDEN);
            default -> new OrderException("msvc-inventory no disponible al " + accion, HttpStatus.BAD_GATEWAY);
        };
    }
}
