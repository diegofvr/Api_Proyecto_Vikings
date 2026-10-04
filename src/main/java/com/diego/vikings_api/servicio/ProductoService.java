package com.diego.vikings_api.servicio;

import com.diego.vikings_api.excepcion.RecursoNoEncontradoException;
import com.diego.vikings_api.modelo.Producto;
import com.diego.vikings_api.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto obtenerProductoPorId(Long id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            return producto.get();
        }
        throw new RecursoNoEncontradoException("No existe un producto con id " + id);
    }

    public Producto crearProducto(Producto producto) {
        producto.setId(null);
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto datosNuevos) {
        Producto producto = obtenerProductoPorId(id);

        producto.setProducto(datosNuevos.getProducto());
        producto.setMarca(datosNuevos.getMarca());
        producto.setTalla(datosNuevos.getTalla());
        producto.setColor(datosNuevos.getColor());
        producto.setPrecio(datosNuevos.getPrecio());

        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("No existe un producto con id " + id);
        }
        productoRepository.deleteById(id);
    }
}