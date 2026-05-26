package com.example.usuarioservice.Client;

import com.example.usuarioservice.Dto.RolUsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rolservice")
public interface UsuarioClient {

    @GetMapping("/api/v1/roles/{id}")
    RolUsuarioDTO getRolById(@PathVariable("id") Integer id);
}