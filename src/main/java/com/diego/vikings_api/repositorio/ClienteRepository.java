package com.diego.vikings_api.repositorio;

import com.diego.vikings_api.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByCorreo(String correo);
}
