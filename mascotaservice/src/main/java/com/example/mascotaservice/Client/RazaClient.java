package com.example.mascotaservice.Client;

import com.example.mascotaservice.Dto.RazaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "razaservice")
public interface RazaClient {

    @GetMapping("/api/v1/razas/{id}")
    RazaDTO getRazaById(@PathVariable("id") Integer id);
}
