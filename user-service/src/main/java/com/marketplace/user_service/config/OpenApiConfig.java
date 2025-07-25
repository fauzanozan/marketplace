package com.marketplace.user_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Marketplace User Service API",
                version = "1.0",
                description = "API untuk mengelola user service di microservices marketplace")
)
@Configuration
public class OpenApiConfig {
}
