package com.example.zapatilla_api.controllers;

import com.example.zapatilla_api.models.Marca;
import com.example.zapatilla_api.models.Producto;
import com.example.zapatilla_api.repositories.ProductoRepository;
import com.example.zapatilla_api.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository repository;

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public List<Producto> getAllZapatillas() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getZapatillaById(@PathVariable Long id) {
        Optional<Producto> zapatilla = repository.findById(id);
        return zapatilla.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Marca marca = marcaRepository.findById(producto.getMarca().getId())
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        
        producto.setMarca(marca);
        Producto nuevoProducto = repository.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // Actualizar una zapatilla
    @PutMapping("/{id}")
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
    public ResponseEntity<Void> deleteZapatilla(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
