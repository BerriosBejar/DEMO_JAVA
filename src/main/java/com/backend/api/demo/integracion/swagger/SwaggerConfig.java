package com.backend.api.demo.integracion.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@ExternalDocumentation
public class SwaggerConfig {
	
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("API de Integración Demo")
                    .version("1.0")
                    .description("Esta es la documentación de la API de integración demo")
                    .contact(new Contact().name("Tu Nombre").email("tucorreo@example.com")));
    }
}
