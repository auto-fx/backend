package com.autofx.autofxbackend.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(title = "AutoFX Platform API", version = "1.0.0"),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfiguration {
    @Value("${openapi.server-url:http://localhost:4040}")
    private String serverUrl;

    @Bean
    public OpenAPI autofxPlatformOpenApi() {

        return new OpenAPI()
                .servers(List.of(
                        new Server().url(serverUrl)
                ))
                .info(new Info().title("AutoFX Platform API")
                        .description(
                                "AutoFX Platform application REST API documentation.")
                        .version("v1.0.0")
                                .license(new License().name("Apache 2.0").url("https://springdoc.org")))
                        .externalDocs(new ExternalDocumentation()
                                .description("AutoFX Platform Wiki Documentation")
                                .url("https://github.com/auto-fx/report"));
    }

}
