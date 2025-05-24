package com.perfunlandia.gestiondecliente.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

//http://localhost:8080/swagger-ui/index.html 

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .servers(List.of(
                new Server().url("http://localhost:8080").description("Servidor de desarrollo")
            ))
            .info(new Info()
                .title("API gestión de clientes")
                .description("Documentación de la API para la gestión de clientes")
                .version("1.0")
                .contact(new Contact()
                    .name("Soporte API")
                    .email("soporte@example.com")
                    .url("https://example.com")));
    }
}