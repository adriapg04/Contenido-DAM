package com.example.akihabara.market.controller;

import com.example.akihabara.market.entity.ProductoOtaku;
import com.example.akihabara.market.repository.ProductoOtakuRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para manejar las operaciones CRUD de productos otaku.
 * Este controlador expone endpoints HTTP para que Maestro Tanaka gestione su inventario.
 */
@RestController
@RequestMapping("/productos")
@CrossOrigin(origins = "*") // Permite CORS para desarrollo
public class ProductoController {

    // Inyeccion del repositorio para acceder a la base de datos
    @Autowired
    private ProductoOtakuRepository productoRepository;

    /**
     * GET /productos - Obtener todos los productos de la tienda
     */
    @GetMapping
    public ResponseEntity<List<ProductoOtaku>> obtenerTodosLosProductos() {
        try {
            List<ProductoOtaku> productos = productoRepository.findAll();
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /productos/{id} - Obtener un producto específico por su ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductoOtaku> obtenerProductoPorId(@PathVariable Long id) {
        try {
            Optional<ProductoOtaku> producto = productoRepository.findById(id);
            return producto.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * POST /productos - Agregar un nuevo producto al inventario
     */
    @PostMapping
    public ResponseEntity<ProductoOtaku> agregarProducto(@Valid @RequestBody ProductoOtaku producto) {
        try {
            ProductoOtaku productoGuardado = productoRepository.save(producto);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * PUT /productos/{id} - Actualizar un producto existente
     */
    @PutMapping("/{id}")
    public ResponseEntity<ProductoOtaku> actualizarProducto(
            @PathVariable Long id,
            @Valid @RequestBody ProductoOtaku productoActualizado) {
        try {
            Optional<ProductoOtaku> opt = productoRepository.findById(id);
            if (opt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            ProductoOtaku producto = opt.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setCategoria(productoActualizado.getCategoria());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());

            ProductoOtaku productoGuardado = productoRepository.save(producto);
            return ResponseEntity.ok(productoGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * DELETE /productos/{id} - Eliminar un producto del inventario
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        try {
            if (!productoRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            productoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /productos/categoria/{categoria} - Buscar productos por categoría
     */
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoOtaku>> buscarPorCategoria(@PathVariable String categoria) {
        try {
            List<ProductoOtaku> productos =
                    productoRepository.findByCategoriaIgnoreCase(categoria);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /productos/buscar?nombre={nombre} - Buscar productos por nombre
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<ProductoOtaku>> buscarPorNombre(@RequestParam String nombre) {
        try {
            List<ProductoOtaku> productos =
                    productoRepository.findByNombreContainingIgnoreCase(nombre);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * GET /productos/stock-bajo/{cantidad} - Productos con stock bajo
     */
    @GetMapping("/stock-bajo/{cantidad}")
    public ResponseEntity<List<ProductoOtaku>> productosConStockBajo(@PathVariable Integer cantidad) {
        try {
            List<ProductoOtaku> productos =
                    productoRepository.findByStockLessThan(cantidad);
            return ResponseEntity.ok(productos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
