package com.duoc.msvc_users.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityScheme;
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenApi() {
        // Registra el esquema "bearer-jwt": agrega el boton Authorize en Swagger para pegar el token.
        return new OpenAPI()
                .info(new Info()
                        .title("API Usuarios / Auth")
                        .version("1.0")
                        .description("Registro, login y emision de JWT"))
                .components(new Components().addSecuritySchemes("bearer-jwt",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}