package com.example.reporteservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI reporteServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Reporte Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de reportes del sistema de adopcion de mascotas.")
                );
    }
}