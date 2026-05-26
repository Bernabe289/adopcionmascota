package com.example.solicitudservice.Client;

import com.example.solicitudservice.Dto.MascotaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="mascotaservice")
public interface MascotaClient {

    @GetMapping("/api/v1/mascotas/{id}")
    MascotaDTO getMascotaById(@PathVariable("id") Integer id);
}
