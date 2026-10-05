package com.diego.vikings_api.excepcion;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> manejarIntegridad(DataIntegrityViolationException ex) {
        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 409);
        cuerpo.put("mensaje", "La operación no es posible porque hay datos relacionados (por ejemplo, una categoría que tiene productos)");
        return ResponseEntity.status(409).body(cuerpo);
    }
}