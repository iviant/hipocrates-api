
package com.hipocrates.hipocratesapi.dto;

import java.time.LocalDateTime;
import java.util.Map;

// DTO que representa los errores de nuestra API.
public record ErrorResponse(

        // Código HTTP del error (400, 404, 409, 500...).
        int status,

        // Mensaje que explica qué ha ocurrido.
        String mensaje,

        // Fecha y hora en la que se produjo el error.
        LocalDateTime timestamp,

        // Indica qué campos tienen errores de validación.
        Map<String, String> erroresCampo

) {}
