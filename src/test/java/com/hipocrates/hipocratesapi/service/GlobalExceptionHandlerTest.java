package com.hipocrates.hipocratesapi.service;

import com.hipocrates.hipocratesapi.dto.ErrorResponse;
import com.hipocrates.hipocratesapi.exception.GlobalExceptionHandler;
import com.hipocrates.hipocratesapi.exception.ReglaNegocioException;
import com.hipocrates.hipocratesapi.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.core.MethodParameter;



import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

// Esta clase comprueba que los errores de nuestra API
// se gestionan correctamente.
class GlobalExceptionHandlerTest {

    // Creamos una instancia del gestor global de errores.
    private final GlobalExceptionHandler handler =
            new GlobalExceptionHandler();

    // TEST 1: Comprobamos que un registro inexistente
    // produce una respuesta HTTP 404.
    @Test
    void debeDevolver404CuandoRecursoNoExiste() {

        // Simulamos una excepción porque no existe el registro.
        ResourceNotFoundException excepcion =
                new ResourceNotFoundException(
                        "Registro no encontrado"
                );

        // Llamamos al método real de GlobalExceptionHandler.
        ResponseEntity<ErrorResponse> respuesta =
                handler.manejarNoEncontrado(excepcion);

        // Comprobamos que el código HTTP es 404.
        assertEquals(
                HttpStatus.NOT_FOUND,
                respuesta.getStatusCode()
        );

        // Comprobamos que la respuesta contiene información.
        assertNotNull(respuesta.getBody());

        // Comprobamos que el mensaje es correcto.
        assertEquals(
                "Registro no encontrado",
                respuesta.getBody().mensaje()
        );

        // Comprobamos que el estado numérico es 404.
        assertEquals(
                404,
                respuesta.getBody().status()
        );

        // Comprobamos que se incluye la fecha del error.
        assertNotNull(respuesta.getBody().timestamp());

        // Comprobamos que no hay errores de campos.
        assertTrue(respuesta.getBody().erroresCampo().isEmpty());
    }

    @Test
    void debeDevolver409CuandoHayConflictoEnBaseDeDatos() {

        // Simulamos un error de integridad en la base de datos.
        DataIntegrityViolationException excepcion =
                new DataIntegrityViolationException("Dato duplicado");

        // Llamamos al método del controlador global de excepciones.
        ResponseEntity<ErrorResponse> respuesta =
                handler.manejarConflicto(excepcion);

        // Comprobamos que devuelve el código HTTP 409.
        assertEquals(HttpStatus.CONFLICT, respuesta.getStatusCode());

        // Comprobamos que la respuesta contiene información.
        assertNotNull(respuesta.getBody());

        // Comprobamos que el código de error del cuerpo es 409.
        assertEquals(409, respuesta.getBody().status());

        // Comprobamos que devuelve el mensaje esperado.
        assertEquals(
                "Conflicto con los datos de la base de datos",
                respuesta.getBody().mensaje()
        );

        // Comprobamos que incluye la fecha y hora del error.
        assertNotNull(respuesta.getBody().timestamp());

        // Comprobamos que no hay errores de validación de campos.
        assertTrue(respuesta.getBody().erroresCampo().isEmpty());
    }



    @Test
    void debeDevolver400CuandoSeIncumpleReglaDeNegocio() {

        // Simulamos una excepción porque no se cumple una regla de negocio.
        ReglaNegocioException excepcion =
                new ReglaNegocioException("La intensidad debe estar entre 1 y 10");

        // Llamamos al manejador global de excepciones.
        ResponseEntity<ErrorResponse> respuesta =
                handler.manejarReglaNegocio(excepcion);

        // Comprobamos que devuelve el código HTTP 400.
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        // Comprobamos que la respuesta tiene contenido.
        assertNotNull(respuesta.getBody());

        // Comprobamos que el cuerpo contiene el código 400.
        assertEquals(400, respuesta.getBody().status());

        // Comprobamos que devuelve el mensaje correcto.
        assertEquals(
                "La intensidad debe estar entre 1 y 10",
                respuesta.getBody().mensaje()
        );

        // Comprobamos que incluye la fecha y hora.
        assertNotNull(respuesta.getBody().timestamp());

        // Comprobamos que no hay errores individuales de campos.
        assertTrue(respuesta.getBody().erroresCampo().isEmpty());
    }


    @Test
    void debeDevolver500CuandoOcurreErrorInesperado() {

        // Simulamos un error inesperado del servidor.
        Exception excepcion = new Exception("Error inesperado");

        // Llamamos al manejador global de excepciones.
        ResponseEntity<ErrorResponse> respuesta =
                handler.manejarErrorGeneral(excepcion);

        // Comprobamos que devuelve el código HTTP 500.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, respuesta.getStatusCode());

        // Comprobamos que la respuesta contiene información.
        assertNotNull(respuesta.getBody());

        // Comprobamos que el código del cuerpo es 500.
        assertEquals(500, respuesta.getBody().status());

        // Comprobamos que incluye una fecha y hora.
        assertNotNull(respuesta.getBody().timestamp());

        // Comprobamos que no hay errores de campos.
        assertTrue(respuesta.getBody().erroresCampo().isEmpty());
    }

    @Test
    void debeDevolver400CuandoHayErroresDeValidacion() throws NoSuchMethodException {

        // Creamos un objeto para simular los datos recibidos.
        Object datosInvalidos = new Object();

        // Creamos el resultado de validación de esos datos.
        BeanPropertyBindingResult errores =
                new BeanPropertyBindingResult(datosInvalidos, "registro");

        // Simulamos un error en el campo intensidad.
        errores.addError(
                new FieldError(
                        "registro",
                        "intensidad",
                        "La intensidad debe estar entre 1 y 10"
                )
        );

        // Obtenemos un método de ejemplo para construir la excepción de Spring.
        MethodParameter parametro = new MethodParameter(
                Object.class.getMethod("toString"),
                -1
        );

        // Creamos la excepción que Spring genera cuando falla una validación.
        MethodArgumentNotValidException excepcion =
                new MethodArgumentNotValidException(parametro, errores);

        // Ejecutamos nuestro manejador global de excepciones.
        ResponseEntity<ErrorResponse> respuesta =
                handler.manejarValidacion(excepcion);

        // Comprobamos que el código HTTP es 400.
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());

        // Comprobamos que la respuesta contiene datos.
        assertNotNull(respuesta.getBody());

        // Comprobamos que el cuerpo también indica el error 400.
        assertEquals(400, respuesta.getBody().status());

        // Comprobamos que incluye el mensaje del campo incorrecto.
        assertEquals(
                "La intensidad debe estar entre 1 y 10",
                respuesta.getBody().erroresCampo().get("intensidad")
        );

        // Comprobamos que incluye la fecha y hora del error.
        assertNotNull(respuesta.getBody().timestamp());
    }

}
