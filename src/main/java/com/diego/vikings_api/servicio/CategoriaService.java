package com.diego.vikings_api.servicio;

import com.diego.vikings_api.excepcion.RecursoNoEncontradoException;
import com.diego.vikings_api.modelo.Categoria;
import com.diego.vikings_api.repositorio.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerCategoriaPorId(Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);

        if (categoria.isPresent()) {
            return categoria.get();
        }
        throw new RecursoNoEncontradoException("No existe una categoría con id " + id);
    }

    public Categoria crearCategoria(Categoria categoria) {
        categoria.setId(null);
        return categoriaRepository.save(categoria);
    }

    public Categoria actualizarCategoria(Long id, Categoria datosNuevos) {
        Categoria categoria = obtenerCategoriaPorId(id);

        categoria.setNombre(datosNuevos.getNombre());

        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe una categoría con id " + id);
        }
        categoriaRepository.deleteById(id);
    }
}