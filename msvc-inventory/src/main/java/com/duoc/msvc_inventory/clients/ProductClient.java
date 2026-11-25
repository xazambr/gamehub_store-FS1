package com.duoc.msvc_inventory.clients;

import com.duoc.msvc_inventory.models.dtos.ExtProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Se resuelve por Eureka (msvc-product, puerto 8002). Antes apuntaba fijo a localhost:8082 (puerto
// del README antiguo), donde no hay nada. Para forzar una URL: clients.product.url=http://localhost:8002
@FeignClient(name = "msvc-product", url = "${clients.product.url:}", path = "/api/v1/productos")
public interface ProductClient {

    @GetMapping("/{id}")
    ExtProductoDTO findById(@PathVariable("id") Long id);

}
