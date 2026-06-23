package com.duoc.msvc_inventory.clients;

import com.duoc.msvc_inventory.models.dtos.ExtProductoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-product", url= "localhost:8082/api/v1/productos")
public interface ProductClient {

    @GetMapping("/{id}")
    ExtProductoDTO findById(@PathVariable Long id);

}
