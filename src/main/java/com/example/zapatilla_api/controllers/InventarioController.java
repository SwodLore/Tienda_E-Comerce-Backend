package com.example.zapatilla_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.zapatilla_api.models.Inventario;
import com.example.zapatilla_api.models.Producto;
import com.example.zapatilla_api.models.Talla;
import com.example.zapatilla_api.repositories.InventarioRepository;
import com.example.zapatilla_api.repositories.ProductoRepository;
import com.example.zapatilla_api.repositories.TallaRepository;


@RestController
@RequestMapping("/api/inventario")
public class InventarioController {
    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final TallaRepository tallaRepository;

    public InventarioController(InventarioRepository inventarioRepository, 
                                ProductoRepository productoRepository, 
                                TallaRepository tallaRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
        this.tallaRepository = tallaRepository;
    }

    // Obtener todo el inventario
    @GetMapping
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    // Obtener inventario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Long id) {
        return inventarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Agregar stock para un producto y talla específica
    @PostMapping
    public ResponseEntity<Inventario> createInventario(@RequestParam Long productoId,
                                                       @RequestParam Long tallaId,
                                                       @RequestParam int stock) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        Talla talla = tallaRepository.findById(tallaId)
                .orElseThrow(() -> new RuntimeException("Talla no encontrada"));

        Inventario inventario = new Inventario();
        inventario.setProducto(producto);
        inventario.setTalla(talla);
        inventario.setStock(stock);

        return ResponseEntity.ok(inventarioRepository.save(inventario));
    }

    // Actualizar cantidad de stock en el inventario
    @PutMapping("/{id}")
    public ResponseEntity<Inventario> updateInventario(@PathVariable Long id, @RequestParam int stock) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setStock(stock);
            return ResponseEntity.ok(inventarioRepository.save(inventario));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un registro de inventario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
