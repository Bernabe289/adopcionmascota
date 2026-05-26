package com.example.evaluacionadoptanteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EvaluacionadoptanteserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvaluacionadoptanteserviceApplication.class, args);
    }

}
