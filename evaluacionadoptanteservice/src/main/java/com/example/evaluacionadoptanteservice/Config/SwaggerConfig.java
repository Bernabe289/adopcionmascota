package com.example.evaluacionadoptanteservice.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI evaluacionServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Evaluacion Adoptante Service API")
                        .version("1.0")
                        .description("Documentacion del microservicio de Evaluacion Adoptante de mascotas"));
    }
}
