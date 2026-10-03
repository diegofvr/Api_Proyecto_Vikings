package com.diego.vikings_api.controlador;

import com.diego.vikings_api.modelo.Producto;
import com.diego.vikings_api.repositorio.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @RestController: esta clase maneja peticiones HTTP y responde en JSON
@RestController
// Prefijo de la URL para todos los métodos de esta clase
// (ej: http://localhost:8080/api/productos)
@RequestMapping("/api/productos")
public class ProductoController {

    // Spring inyecta automáticamente una instancia de ProductoRepository
    // ya conectada a la base de datos real
    @Autowired
    private ProductoRepository productoRepository;

    // --- GET: ver todos los productos ---
    // @GetMapping sin ruta adicional responde a GET en /api/productos
    @GetMapping
    public List<Producto> listarProductos() {
        // findAll() ya viene incluido gratis por heredar de JpaRepository:
        // hace un SELECT * FROM productos
        return productoRepository.findAll();
    }

    // --- GET: ver un producto por id ---
    // La parte {id} en la ruta es una variable; @PathVariable la captura
    // (ej: GET /api/productos/3 busca el producto con id = 3)
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoRepository.findById(id);

        if (producto.isPresent()) {
            // Si lo encuentra, devuelve 200 OK junto con el producto en JSON
            return ResponseEntity.ok(producto.get());
        } else {
            // Si no existe ese id, devuelve 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    // --- POST: crear un producto nuevo ---
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        // @RequestBody convierte el JSON que envía Postman en un objeto Producto
        // save() hace el INSERT automáticamente y devuelve el objeto ya guardado
        // (con el id que MySQL le asignó)
        Producto productoGuardado = productoRepository.save(producto);

        // 201 Created, junto con el producto creado (incluyendo su nuevo id)
        return ResponseEntity.status(201).body(productoGuardado);
    }

    // --- PUT: actualizar un producto existente ---
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto datosNuevos) {
        Optional<Producto> productoExistente = productoRepository.findById(id);

        if (productoExistente.isPresent()) {
            // Tomamos el producto que ya existe en la base de datos...
            Producto producto = productoExistente.get();

            // ...y le actualizamos cada campo con los datos nuevos que llegaron
            producto.setProducto(datosNuevos.getProducto());
            producto.setMarca(datosNuevos.getMarca());
            producto.setTalla(datosNuevos.getTalla());
            producto.setColor(datosNuevos.getColor());
            producto.setPrecio(datosNuevos.getPrecio());

            // save() con un objeto que ya tiene id hace un UPDATE, no un INSERT nuevo
            Producto productoActualizado = productoRepository.save(producto);

            return ResponseEntity.ok(productoActualizado);
        } else {
            // Si el id no existe, no hay nada que actualizar
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE: eliminar un producto por id ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (productoRepository.existsById(id)) {
            // deleteById() hace el DELETE automáticamente
            productoRepository.deleteById(id);
            // 204 No Content: la operación fue exitosa, pero no hay nada que devolver
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}