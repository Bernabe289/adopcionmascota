package com.example.razaservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI razaServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Raza Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de razas del sistema de adopcion de mascotas."));
    }
}
