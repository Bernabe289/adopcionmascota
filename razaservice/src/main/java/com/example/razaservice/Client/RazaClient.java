package com.example.razaservice.Client;

import com.example.razaservice.Dto.EspecieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "especieservice")
public interface RazaClient {

    @GetMapping("/api/v1/especies/{id}")
    EspecieDTO getEspecieById(@PathVariable("id") Integer id);
}