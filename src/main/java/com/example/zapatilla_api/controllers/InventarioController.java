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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Endpoints para la gestión del inventario")
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
    @Operation(
        summary = "Obtener el inventario completo",
        description = "Devuelve una lista con todos los registros del inventario.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de inventario obtenida exitosamente")
        }
    )
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    // Obtener inventario por ID
    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener inventario por ID",
        description = "Busca un registro de inventario por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Inventario encontrado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
        }
    )
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Long id) {
        return inventarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Agregar stock para un producto y talla específica
    @PostMapping
    @Operation(
        summary = "Agregar stock a un producto",
        description = "Añade una cantidad de stock para un producto y una talla específica.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Stock agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados")
        }
    )
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
    @Operation(
        summary = "Actualizar stock",
        description = "Modifica la cantidad de stock de un producto en el inventario.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Stock actualizado correctamente"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
        }
    )
    public ResponseEntity<Inventario> updateInventario(@PathVariable Long id, @RequestParam int stock) {
        return inventarioRepository.findById(id).map(inventario -> {
            inventario.setStock(stock);
            return ResponseEntity.ok(inventarioRepository.save(inventario));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un registro de inventario
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un inventario",
        description = "Elimina un registro del inventario por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Inventario eliminado"),
            @ApiResponse(responseCode = "404", description = "Inventario no encontrado")
        }
    )
    public ResponseEntity<Void> deleteInventario(@PathVariable Long id) {
        if (inventarioRepository.existsById(id)) {
            inventarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
