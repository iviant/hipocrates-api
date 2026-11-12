
package com.hipocrates.hipocratesapi.exception;

import com.hipocrates.hipocratesapi.dto.ErrorResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Esta clase gestiona los errores de todos los controladores REST.
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ERROR 400: Los datos enviados no cumplen las validaciones.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidacion(
            MethodArgumentNotValidException ex) {

        // Guardamos los campos que contienen errores.
        Map<String, String> errores = new HashMap<>();

        // Recorremos los errores de validación y guardamos
        // el nombre de cada campo junto a su mensaje.
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errores.put(
                        error.getField(),
                        error.getDefaultMessage()
                )
        );

        // Construimos nuestra respuesta JSON personalizada.
        ErrorResponse respuesta = new ErrorResponse(
                400,
                "Error de validación",
                LocalDateTime.now(),
                errores
        );

        // Devolvemos el código HTTP 400.
        return ResponseEntity.badRequest().body(respuesta);
    }

    // ERROR 404: El registro solicitado no existe.
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNoEncontrado(
            ResourceNotFoundException ex) {

        // Utilizamos el mensaje de nuestra excepción personalizada.
        ErrorResponse respuesta = new ErrorResponse(
                404,
                ex.getMessage(),
                LocalDateTime.now(),
                Map.of()
        );

        // Devolvemos el código HTTP 404.
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }

    // ERROR 404: La URL solicitada no existe en nuestra API.
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> manejarRutaNoEncontrada(
            NoResourceFoundException ex) {

        // Creamos una respuesta para una ruta inexistente.
        ErrorResponse respuesta = new ErrorResponse(
                404,
                "La ruta solicitada no existe",
                LocalDateTime.now(),
                Map.of()
        );

        // Devolvemos 404 en lugar de tratarlo como un error interno.
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(respuesta);
    }

    // ERROR 400: Se incumple una regla de negocio.
    @ExceptionHandler(ReglaNegocioException.class)
    public ResponseEntity<ErrorResponse> manejarReglaNegocio(
            ReglaNegocioException ex) {

        // Mostramos el motivo por el que se incumple la regla.
        ErrorResponse respuesta = new ErrorResponse(
                400,
                ex.getMessage(),
                LocalDateTime.now(),
                Map.of()
        );

        // Devolvemos el código HTTP 400.
        return ResponseEntity.badRequest().body(respuesta);
    }

    // ERROR 409: Existe un conflicto con las restricciones
    // de integridad de la base de datos.
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manejarConflicto(
            DataIntegrityViolationException ex) {

        // Creamos una respuesta sin mostrar detalles internos de la BD.
        ErrorResponse respuesta = new ErrorResponse(
                409,
                "Conflicto con los datos de la base de datos",
                LocalDateTime.now(),
                Map.of()
        );

        // Devolvemos el código HTTP 409.
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(respuesta);
    }

    // ERROR 500: Cualquier otro error inesperado del servidor.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErrorGeneral(
            Exception ex) {

        // Creamos una respuesta genérica para evitar mostrar
        // información interna de la aplicación al usuario.
        ErrorResponse respuesta = new ErrorResponse(
                500,
                "Ha ocurrido un error interno en el servidor",
                LocalDateTime.now(),
                Map.of()
        );

        // Devolvemos el código HTTP 500.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(respuesta);
    }
}
