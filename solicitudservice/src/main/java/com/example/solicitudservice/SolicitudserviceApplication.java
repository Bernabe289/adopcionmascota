package com.example.solicitudservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SolicitudserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SolicitudserviceApplication.class, args);
    }

}
