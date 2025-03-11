package com.example.zapatilla_api.config;


import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Zapatillas")
                        .version("1.0")
                        .description("Documentación de la API para la tienda de zapatillas"));
    }
}
