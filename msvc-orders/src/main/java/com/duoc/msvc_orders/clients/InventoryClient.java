package com.duoc.msvc_orders.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Se resuelve por Eureka (msvc-inventory). Para forzar una URL: clients.inventory.url=http://localhost:8004
@FeignClient(name = "msvc-inventory", url = "${clients.inventory.url:}", path = "/api/v1/inventario")
public interface InventoryClient {

    @PostMapping("/reservas/{productoId}/reservar")
    void reservar(@PathVariable("productoId") Long productoId, @RequestParam("cantidad") int cantidad);

    @PostMapping("/reservas/{productoId}/liberar")
    void liberar(@PathVariable("productoId") Long productoId, @RequestParam("cantidad") int cantidad);

    @PostMapping("/reservas/{productoId}/confirmar")
    void confirmar(@PathVariable("productoId") Long productoId, @RequestParam("cantidad") int cantidad);
}
