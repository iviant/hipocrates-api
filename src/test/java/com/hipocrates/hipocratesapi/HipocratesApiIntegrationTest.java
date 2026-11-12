
package com.hipocrates.hipocratesapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;

// Inicia el contexto completo de Spring Boot para comprobar
// que la configuración de la aplicación funciona correctamente.
@SpringBootTest
@ActiveProfiles("test")
class HipocratesApiIntegrationTest {

    // Comprueba que Spring Boot puede cargar el contexto
    // utilizando la configuración del perfil test.
    @Test
    void debeIniciarAplicacionConPerfilTest() {
        assertTrue(true);
    }
}
