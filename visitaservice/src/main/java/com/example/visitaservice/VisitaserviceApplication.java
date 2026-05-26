package com.example.visitaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class VisitaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitaserviceApplication.class, args);
    }

}
