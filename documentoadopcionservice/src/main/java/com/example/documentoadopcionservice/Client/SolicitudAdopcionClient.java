package com.example.documentoadopcionservice.Client;

import com.example.documentoadopcionservice.Dto.SolicitudAdopcionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "solicitudservice")
public interface SolicitudAdopcionClient {

    @GetMapping("/api/v1/solicitudes/{id}")
    SolicitudAdopcionDTO getSolicitudById(@PathVariable("id") Integer id);

}
