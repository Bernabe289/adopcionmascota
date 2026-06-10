package com.example.usuarioservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI usuarioServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Usuario Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de usuarios del sistema de adopcion de mascotas."));
    }
}
