package com.example.documentoadopcionservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI documentoadopcionServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Documento Adopción API")
                        .version("1.0")
                        .description("Documentación del microservicio de documento de adopción.")
                );
    }
}
