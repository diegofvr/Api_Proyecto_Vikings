package com.diego.vikings_api.servicio;

import com.diego.vikings_api.excepcion.CredencialesInvalidasException;
import com.diego.vikings_api.modelo.Cliente;
import com.diego.vikings_api.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente iniciarSesion(String correo, String contrasena) {
        Optional<Cliente> resultado = clienteRepository.findByCorreo(correo);

        if (resultado.isEmpty()) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }

        Cliente cliente = resultado.get();

        if (cliente.getContrasena() == null || !cliente.getContrasena().equals(contrasena)) {
            throw new CredencialesInvalidasException("Correo o contraseña incorrectos");
        }

        return cliente;
    }
}