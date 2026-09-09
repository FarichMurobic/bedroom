package com.bedroom.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Konfigurasi dokumentasi API interaktif (Swagger UI) untuk seluruh
 * endpoint Bedroom, termasuk dukungan input token JWT untuk mencoba
 * endpoint yang terautentikasi langsung dari antarmuka dokumentasi.
 */
@Configuration
public class KonfigurasiOpenApi {

    private static final String SKEMA_BEARER = "bearerAuth";

    @Bean
    OpenAPI openApiBedroom() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bedroom API")
                        .description("Platform kreatif untuk berbagi imajinasi, skill, dan inovasi.")
                        .version("v1"))
                .addSecurityItem(new SecurityRequirement().addList(SKEMA_BEARER))
                .components(new Components()
                        .addSecuritySchemes(SKEMA_BEARER, new SecurityScheme()
                                .name(SKEMA_BEARER)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}