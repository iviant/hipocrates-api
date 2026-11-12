package com.hipocrates.hipocratesapi.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record RegistroRequestDTO(
        @NotBlank(message = "El nombre del paciente es obligatorio")
        String pacienteNombre,

        @NotBlank(message = "El tipo de registro es obligatorio (FISICO o EMOCIONAL)")
        String tipoRegistro,

        @NotBlank(message = "Debe especificar el síntoma o emoción")
        String sintomaOEmocion,

        @Min(value = 1, message = "La intensidad mínima es 1")
        @Max(value = 10, message = "La intensidad máxima es 10")
        Integer intensidad,

        Double temperatura,

        @NotNull(message = "La fecha de registro es obligatoria")
        LocalDate fechaRegistro,

        List<String> etiquetas
) {}