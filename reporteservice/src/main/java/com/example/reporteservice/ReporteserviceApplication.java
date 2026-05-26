package com.example.reporteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReporteserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReporteserviceApplication.class, args);
    }

}
