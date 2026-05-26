package com.example.vacunaservice.Client;

import com.example.vacunaservice.Dto.HistorialVetDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "historialvetservice")
public interface HistorialVetClient {

    @GetMapping("/api/v1/historiales/{id}")
    HistorialVetDTO getHistorialById(@PathVariable("id") Integer id);
}
