package com.diego.vikings_api.servicio;

import com.diego.vikings_api.excepcion.RecursoNoEncontradoException;
import com.diego.vikings_api.excepcion.SolicitudInvalidaException;
import com.diego.vikings_api.modelo.Cliente;
import com.diego.vikings_api.repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return cliente.get();
        }
        throw new RecursoNoEncontradoException("No existe un cliente con id " + id);
    }

    public Cliente crearCliente(Cliente cliente) {
        if (clienteRepository.existsByCorreo(cliente.getCorreo())) {
            throw new SolicitudInvalidaException("Ya existe un cliente con el correo " + cliente.getCorreo());
        }
        cliente.setId(null);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente datosNuevos) {
        Cliente cliente = obtenerClientePorId(id);

        // Solo revisamos el correo si cambió: así un cliente puede conservar el suyo
        boolean correoCambio = !cliente.getCorreo().equals(datosNuevos.getCorreo());
        if (correoCambio && clienteRepository.existsByCorreo(datosNuevos.getCorreo())) {
            throw new SolicitudInvalidaException("Ya existe un cliente con el correo " + datosNuevos.getCorreo());
        }

        cliente.setNombre(datosNuevos.getNombre());
        cliente.setCorreo(datosNuevos.getCorreo());
        cliente.setTelefono(datosNuevos.getTelefono());
        cliente.setDireccion(datosNuevos.getDireccion());

        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un cliente con id " + id);
        }
        clienteRepository.deleteById(id);
    }
}
