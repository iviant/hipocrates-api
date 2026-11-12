
package com.hipocrates.hipocratesapi.dto;

// DTO que combina información de nuestra BD con ZenQuotes.
public record RegistroMashupDTO(

        // Identificador del registro en nuestra base de datos.
        Long id,

        // Nombre de la persona asociada al registro.
        String pacienteNombre,

        // Tipo de registro: FISICO o EMOCIONAL.
        String tipoRegistro,

        // Síntoma físico o emoción registrada.
        String sintomaOEmocion,

        // Intensidad del síntoma o emoción (1-10).
        Integer intensidad,

        // Frase motivacional obtenida de ZenQuotes.
        String fraseApoyo,

        // Autor de la frase.
        String autorFrase,

        // Indica de dónde procede la frase.
        String origen

) {}
