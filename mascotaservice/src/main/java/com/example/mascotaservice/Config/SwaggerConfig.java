package com.example.mascotaservice.Config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI mascotaServiceOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Mascota Service API")
                        .version("1.0")
                        .description("Documentacion del microservicio de Mascotas del sistema"));
    }
}
