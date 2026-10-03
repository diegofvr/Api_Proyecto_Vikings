package com.diego.vikings_api.repositorio;

import com.diego.vikings_api.modelo.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Producto, Long> le dice a Spring:
// "dame automáticamente los métodos CRUD para la entidad Producto, cuyo id es de tipo Long"
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // Por ahora no necesitamos métodos personalizados: con heredar de JpaRepository
    // ya obtenemos gratis save(), findAll(), findById(), deleteById(), etc.
}