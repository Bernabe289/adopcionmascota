package com.example.visitaservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI visitaServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Visita Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de visitas del sistema de adopcion de mascotas.")
                );
    }
}

