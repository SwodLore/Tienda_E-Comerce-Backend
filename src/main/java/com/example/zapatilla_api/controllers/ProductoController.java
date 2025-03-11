package com.example.zapatilla_api.controllers;

import com.example.zapatilla_api.models.Marca;
import com.example.zapatilla_api.models.Producto;
import com.example.zapatilla_api.repositories.ProductoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.zapatilla_api.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones relacionadas con productos")
public class ProductoController {
    @Autowired
    private ProductoRepository repository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    @Operation(
        summary = "Obtener todos los productos",
        description = "Devuelve una lista de todos los productos disponibles",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de productos obtenida correctamente")
        }
    )
    public List<Producto> getAllZapatillas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener un producto por ID",
        description = "Devuelve los detalles de un producto específico",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
        }
    )
    public ResponseEntity<Producto> getZapatillaById(@PathVariable Long id) {
        Optional<Producto> zapatilla = repository.findById(id);
        return zapatilla.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en la base de datos.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud (datos inválidos)")
        }
    )
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Marca marca = marcaRepository.findById(producto.getMarca().getId())
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        
        producto.setMarca(marca);
        Producto nuevoProducto = repository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // Actualizar una zapatilla
    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar un producto",
        description = "Modifica los datos de un producto existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Producto.class))),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
        }
    )
    public ResponseEntity<Producto> updateZapatilla(@PathVariable Long id, @RequestBody Producto updatedZapatilla) {
        return repository.findById(id).map(zapatilla -> {
            zapatilla.setNombre(updatedZapatilla.getNombre());
            
            Marca nuevaMarca = marcaRepository.findById(updatedZapatilla.getMarca().getId())
                    .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            zapatilla.setMarca(nuevaMarca);

            zapatilla.setPrecio(updatedZapatilla.getPrecio());
            zapatilla.setColor(updatedZapatilla.getColor());

            repository.save(zapatilla);
            return ResponseEntity.ok(zapatilla);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Eliminar una zapatilla
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar un producto",
        description = "Borra un producto de la base de datos por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
        }
    )
    public ResponseEntity<Void> deleteZapatilla(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
