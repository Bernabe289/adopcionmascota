package com.example.usuarioservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients
public class UsuarioserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsuarioserviceApplication.class, args);
    }

}
