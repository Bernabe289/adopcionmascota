package com.example.razaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RazaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RazaserviceApplication.class, args);
    }

}
