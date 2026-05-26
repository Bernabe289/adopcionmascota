package com.example.visitaservice.Client;

import com.example.visitaservice.Dto.SolicitudDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "solicitudservice")
public interface SolicitudClient {

    @GetMapping("/api/v1/solicitudes/{id}")
    SolicitudDTO getSolicitudById(@PathVariable("id") Integer id);
}
