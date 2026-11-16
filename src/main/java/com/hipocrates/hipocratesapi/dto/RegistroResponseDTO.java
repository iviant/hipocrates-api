package com.hipocrates.hipocratesapi.dto;

import java.time.LocalDate;
import java.util.List;

public record RegistroResponseDTO(
        Long id,
        String pacienteNombre,
        String tipoRegistro,
        String sintomaOEmocion,
        Integer intensidad,
        Double temperatura,
        LocalDate fechaRegistro,
        List<String> etiquetas
) {}