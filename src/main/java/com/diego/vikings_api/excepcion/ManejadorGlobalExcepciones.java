package com.diego.vikings_api.excepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 404);
        cuerpo.put("mensaje", ex.getMessage());
        return ResponseEntity.status(404).body(cuerpo);
    }

    @ExceptionHandler(SolicitudInvalidaException.class)
    public ResponseEntity<Map<String, Object>> manejarSolicitudInvalida(SolicitudInvalidaException ex) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 400);
        cuerpo.put("mensaje", ex.getMessage());
        return ResponseEntity.badRequest().body(cuerpo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 400);
        cuerpo.put("mensaje", "Hay datos inválidos");
        cuerpo.put("errores", errores);
        return ResponseEntity.badRequest().body(cuerpo);
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<Map<String, Object>> manejarCredencialesInvalidas(CredencialesInvalidasException ex) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 401);
        cuerpo.put("mensaje", ex.getMessage());
        return ResponseEntity.status(401).body(cuerpo);
    }
}