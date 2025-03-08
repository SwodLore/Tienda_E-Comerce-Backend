package com.example.zapatilla_api.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zapatilla_api.models.Talla;
import com.example.zapatilla_api.repositories.TallaRepository;

@RestController
@RequestMapping("/api/tallas")
public class TallaController {
    private final TallaRepository tallaRepository;

    public TallaController(TallaRepository tallaRepository) {
        this.tallaRepository = tallaRepository;
    }

    // Obtener todas las tallas
    @GetMapping
    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> getTallaById(@PathVariable Long id) {
        return tallaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Talla> createTalla(@RequestBody Talla talla) {
        return ResponseEntity.ok(tallaRepository.save(talla));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talla> updateTalla(@PathVariable Long id, @RequestBody Talla updatedTalla) {
        return tallaRepository.findById(id).map(talla -> {
            talla.setTalla_US(updatedTalla.getTalla_US());
            talla.setTalla_EUR(updatedTalla.getTalla_EUR());
            return ResponseEntity.ok(tallaRepository.save(talla));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTalla(@PathVariable Long id) {
        if (tallaRepository.existsById(id)) {
            tallaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
