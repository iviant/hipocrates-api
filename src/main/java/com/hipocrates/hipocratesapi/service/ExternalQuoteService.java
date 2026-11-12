
package com.hipocrates.hipocratesapi.service;

import com.hipocrates.hipocratesapi.dto.ZenQuoteExternalDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class ExternalQuoteService {

    // Cliente HTTP que configuramos en RestClientConfig.
    private final RestClient zenQuotesRestClient;

    // Spring nos proporciona automáticamente el RestClient.
    public ExternalQuoteService(RestClient zenQuotesRestClient) {
        this.zenQuotesRestClient = zenQuotesRestClient;
    }

    // Obtiene una frase motivacional de ZenQuotes.
    public ZenQuoteExternalDTO obtenerFraseAleatoria() {

        try {

            // Realizamos una petición GET a ZenQuotes.
            ZenQuoteExternalDTO[] respuesta = zenQuotesRestClient
                    .get()
                    .uri("/random")
                    .retrieve()
                    .body(ZenQuoteExternalDTO[].class);

            // Comprobamos que la API haya devuelto una frase.
            if (respuesta != null
                    && respuesta.length > 0
                    && respuesta[0] != null
                    && respuesta[0].quote() != null
                    && !respuesta[0].quote().isBlank()) {

                return respuesta[0];
            }

        } catch (RestClientException e) {

            // Si falla la conexión, evitamos que falle la aplicación.
            System.err.println(
                    "No se pudo consultar ZenQuotes: " + e.getMessage()
            );
        }

        // Fallback: frase local si ZenQuotes no responde.
        return new ZenQuoteExternalDTO(
                "Cada pequeño paso cuenta en tu camino hacia el bienestar.",
                "Hipócrates"
        );
    }
}
