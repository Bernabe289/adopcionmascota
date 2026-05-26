package com.example.mascotaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class MascotaserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MascotaserviceApplication.class, args);
    }

}
