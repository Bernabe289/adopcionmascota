package com.example.historialvetservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI historialvetServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Historial veterinario Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de historial veterinario del sistema de adopcion de mascotas.")
                );
    }
}
