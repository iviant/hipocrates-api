
package com.hipocrates.hipocratesapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

// DTO que representa una frase recibida de ZenQuotes.
public record ZenQuoteExternalDTO(

        // Recibe el campo "q" del JSON y lo guarda como quote.
        @JsonProperty("q") String quote,

        // Recibe el campo "a" del JSON y lo guarda como author.
        @JsonProperty("a") String author

) {}
