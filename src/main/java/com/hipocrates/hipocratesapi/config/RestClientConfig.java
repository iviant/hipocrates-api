
package com.hipocrates.hipocratesapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

// Indica que esta clase contiene configuraciones de Spring.
@Configuration
public class RestClientConfig {

    // Spring crea y gestiona este objeto RestClient.
    @Bean
    public RestClient zenQuotesRestClient() {

        // Configuramos los tiempos máximos de espera.
        SimpleClientHttpRequestFactory factory =
                new SimpleClientHttpRequestFactory();

        // Máximo 5 segundos para establecer conexión.
        factory.setConnectTimeout(Duration.ofSeconds(5));

        // Máximo 5 segundos esperando la respuesta.
        factory.setReadTimeout(Duration.ofSeconds(5));

        // Creamos el cliente para comunicarnos con ZenQuotes.
        return RestClient.builder()
                .baseUrl("https://zenquotes.io/api")
                .requestFactory(factory)
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
