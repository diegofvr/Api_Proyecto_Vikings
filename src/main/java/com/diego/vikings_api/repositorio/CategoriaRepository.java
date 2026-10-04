package com.diego.vikings_api.repositorio;

import com.diego.vikings_api.modelo.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}