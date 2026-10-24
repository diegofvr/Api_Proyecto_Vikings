package com.diego.vikings_api.repositorio;


import com.diego.vikings_api.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
}
