package com.example.historialvetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class HistorialvetserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistorialvetserviceApplication.class, args);
    }

}
