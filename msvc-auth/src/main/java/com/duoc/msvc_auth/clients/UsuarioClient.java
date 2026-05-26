package com.duoc.msvc_auth.clients;

import com.duoc.msvc_auth.models.dtos.ExtUsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="msvc-users", url = "localhost:8081/api/v1/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    ExtUsuarioDTO findById(@PathVariable Long id);

    @GetMapping
    ExtUsuarioDTO findByEmail(@RequestParam String email);
}
