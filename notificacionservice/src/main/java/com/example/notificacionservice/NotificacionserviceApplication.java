package com.example.notificacionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class NotificacionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificacionserviceApplication.class, args);
    }

}
