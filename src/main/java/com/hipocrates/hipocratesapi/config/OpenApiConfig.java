
package com.hipocrates.hipocratesapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Clase que configura la documentación de Swagger.
@Configuration
public class OpenApiConfig {

    // Crea la configuración principal de OpenAPI.
    @Bean
    public OpenAPI hipocratesOpenAPI() {

        return new OpenAPI()
                .info(new Info()

                        // Nombre que aparecerá en Swagger.
                        .title("Hipócrates API")

                        // Explica para qué sirve nuestra API.
                        .description(
                                "API REST para gestionar registros " +
                                        "de salud y bienestar emocional, " +
                                        "con integración de frases motivacionales."
                        )

                        // Versión de nuestra API.
                        .version("1.0.0")
                );
    }
}
