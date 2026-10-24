package com.diego.vikings_api.controlador;

import com.diego.vikings_api.dto.LoginRequest;
import com.diego.vikings_api.modelo.Cliente;
import com.diego.vikings_api.servicio.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest datos) {
        Cliente cliente = authService.iniciarSesion(datos.getCorreo(), datos.getContrasena());

        Map<String, Object> cuerpo = new HashMap<>();
        cuerpo.put("estado", 200);
        cuerpo.put("mensaje", "Autenticación satisfactoria");
        cuerpo.put("cliente", cliente);
        return ResponseEntity.ok(cuerpo);
    }
}