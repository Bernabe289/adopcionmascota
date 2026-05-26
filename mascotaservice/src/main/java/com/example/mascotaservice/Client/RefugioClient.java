package com.example.mascotaservice.Client;

import com.example.mascotaservice.Dto.RefugioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "refugioservice")
public interface RefugioClient {

    @GetMapping("/api/v1/refugios/{id}")
    RefugioDTO getRefugioById(@PathVariable("id") Integer id);
}