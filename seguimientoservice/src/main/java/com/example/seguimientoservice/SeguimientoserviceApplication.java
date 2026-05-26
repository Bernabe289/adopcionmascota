package com.example.seguimientoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class SeguimientoserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeguimientoserviceApplication.class, args);
    }

}
