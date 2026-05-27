package com.duoc.msvc_product.clients;

import com.duoc.msvc_product.models.dtos.ExtCategoriaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//conexion con msvc-category usando FeignClient

@FeignClient(name = "msvc-category", url = "localhost:8083/api/v1/categorias" )
public interface CategoryClient {

    @GetMapping("/{id}")
    ExtCategoriaDTO findById(@PathVariable Long id);

}
