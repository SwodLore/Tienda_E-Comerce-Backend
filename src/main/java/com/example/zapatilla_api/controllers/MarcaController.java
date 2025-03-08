package com.example.zapatilla_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.zapatilla_api.repositories.MarcaRepository;

import com.example.zapatilla_api.models.Marca;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaRepository.save(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    @GetMapping
    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        return marcaRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        if (!marcaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marcaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
