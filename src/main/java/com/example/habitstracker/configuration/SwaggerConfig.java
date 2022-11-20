package com.example.habitstracker.configuration;

import com.example.habitstracker.constants.OpenApiConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI productApi() {
        // open swagger: http://localhost:8080/swagger-ui/index.html#/
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(OpenApiConstants.SECURITY_SCHEME_NAME))
                .components(
                        new Components()
                                .addSecuritySchemes(OpenApiConstants.SECURITY_SCHEME_NAME,
                                        new SecurityScheme()
                                                .name(OpenApiConstants.SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme(OpenApiConstants.SCHEME_NAME)
                                                .bearerFormat(OpenApiConstants.SECURITY_SCHEME_NAME)
                                )
                )
                .info(new Info().title(OpenApiConstants.APP_NAME));
    }
}
