
package com.hipocrates.hipocratesapi.exception;

// Excepción para operaciones que incumplen las reglas de negocio.
public class ReglaNegocioException extends RuntimeException {

    // Recibe un mensaje explicando qué regla se ha incumplido.
    public ReglaNegocioException(String mensaje) {

        // Guarda el mensaje en la excepción.
        super(mensaje);
    }
}
