package com.example.habitstracker.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI productApi() {
        // open swagger: http://localhost:8080/swagger-ui/index.html#/
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("JWT"))
                .components(
                        new Components()
                                .addSecuritySchemes("JWT",
                                        new SecurityScheme()
                                                .name("JWT")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info().title("Habit tracker"));
    }
}
