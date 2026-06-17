package com.example.notificacionservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI notificacionServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Notificacion Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de notificaciones del sistema de adopcion de mascotas.")
                );
    }
}
