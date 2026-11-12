
package com.hipocrates.hipocratesapi.exception;

// Excepción personalizada para recursos que no existen.
public class ResourceNotFoundException extends RuntimeException {

    // Constructor que recibe el mensaje del error.
    public ResourceNotFoundException(String mensaje) {

        // Envía el mensaje a la clase RuntimeException.
        super(mensaje);
    }
}
