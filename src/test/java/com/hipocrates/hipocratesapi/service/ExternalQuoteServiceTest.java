package com.hipocrates.hipocratesapi.service;

import com.hipocrates.hipocratesapi.dto.ZenQuoteExternalDTO;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import com.hipocrates.hipocratesapi.exception.ReglaNegocioException;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Tests unitarios para nuestro servicio de frases.
class ExternalQuoteServiceTest {

    // Comprueba que se devuelve una frase alternativa
    // cuando la API externa falla.
    @Test
    void debeDevolverFallbackCuandoZenQuotesFalla() {

        // Creamos un RestClient simulado.
        RestClient restClient = mock(RestClient.class);

        // Simulamos una petición GET.
        RestClient.RequestHeadersUriSpec uriSpec =
                mock(RestClient.RequestHeadersUriSpec.class);

        // Cuando se llama a get(), devolvemos la petición simulada.
        when(restClient.get()).thenReturn(uriSpec);

        // Simulamos un fallo de conexión con ZenQuotes.
        when(uriSpec.uri("/random"))
                .thenThrow(new RestClientException(
                        "Error de conexión"
                ));

        // Creamos el servicio usando el cliente simulado.
        ExternalQuoteService service =
                new ExternalQuoteService(restClient);

        // Ejecutamos el método que queremos probar.
        ZenQuoteExternalDTO resultado =
                service.obtenerFraseAleatoria();

        // Comprobamos que se devuelve una frase.
        assertNotNull(resultado);

        // Comprobamos que se utiliza nuestro autor alternativo.
        assertEquals("Hipócrates", resultado.author());

        // Comprobamos que la frase alternativa no está vacía.
        assertFalse(resultado.quote().isBlank());
    }



    @Test
    void debeDevolverFraseCuandoZenQuotesFunciona() {

        // Creamos un RestClient simulado.
        RestClient restClient = mock(RestClient.class);

        // Simulamos los pasos de una petición HTTP.
        RestClient.RequestHeadersUriSpec uriSpec =
                mock(RestClient.RequestHeadersUriSpec.class);

        RestClient.ResponseSpec responseSpec =
                mock(RestClient.ResponseSpec.class);

        // Preparamos una respuesta ficticia de ZenQuotes.
        ZenQuoteExternalDTO frase =
                new ZenQuoteExternalDTO(
                        "Believe in yourself",
                        "Test Author"
                );

        // Simulamos la llamada GET.
        when(restClient.get()).thenReturn(uriSpec);

        // Simulamos la ruta /random.
        when(uriSpec.uri("/random")).thenReturn(uriSpec);

        // Simulamos la recepción de la respuesta HTTP.
        when(uriSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos el JSON convertido a un array de DTO.
        when(responseSpec.body(ZenQuoteExternalDTO[].class))
                .thenReturn(new ZenQuoteExternalDTO[]{frase});

        // Creamos el servicio con el cliente simulado.
        ExternalQuoteService service =
                new ExternalQuoteService(restClient);

        // Ejecutamos el método que queremos probar.
        ZenQuoteExternalDTO resultado =
                service.obtenerFraseAleatoria();

        // Comprobamos que recibimos una frase.
        assertNotNull(resultado);

        // Comprobamos que el texto es el esperado.
        assertEquals("Believe in yourself", resultado.quote());

        // Comprobamos que el autor es correcto.
        assertEquals("Test Author", resultado.author());
    }


    @Test
    void debeDevolverFallbackCuandoZenQuotesRespondeVacio() {

        // Creamos un cliente HTTP simulado.
        RestClient restClient = mock(RestClient.class);

        // Simulamos la petición GET.
        RestClient.RequestHeadersUriSpec uriSpec =
                mock(RestClient.RequestHeadersUriSpec.class);

        // Simulamos la respuesta de la API.
        RestClient.ResponseSpec responseSpec =
                mock(RestClient.ResponseSpec.class);

        // Configuramos la petición a ZenQuotes.
        when(restClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri("/random")).thenReturn(uriSpec);
        when(uriSpec.retrieve()).thenReturn(responseSpec);

        // Simulamos que ZenQuotes devuelve un array vacío.
        when(responseSpec.body(ZenQuoteExternalDTO[].class))
                .thenReturn(new ZenQuoteExternalDTO[]{});

        // Creamos nuestro servicio con el cliente simulado.
        ExternalQuoteService service =
                new ExternalQuoteService(restClient);

        // Ejecutamos el método.
        ZenQuoteExternalDTO resultado =
                service.obtenerFraseAleatoria();

        // Comprobamos que devuelve una respuesta.
        assertNotNull(resultado);

        // Comprobamos que utiliza nuestra frase alternativa.
        assertEquals("Hipócrates", resultado.author());

        // Comprobamos que la frase no está vacía.
        assertFalse(resultado.quote().isBlank());
    }




}
