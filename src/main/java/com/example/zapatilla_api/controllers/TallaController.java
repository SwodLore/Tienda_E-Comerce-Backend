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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/tallas")
@Tag(name = "Tallas", description = "Operaciones para gestionar las tallas de zapatillas")
public class TallaController {
    private final TallaRepository tallaRepository;

    public TallaController(TallaRepository tallaRepository) {
        this.tallaRepository = tallaRepository;
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las tallas",
        description = "Devuelve una lista de todas las tallas disponibles.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de tallas obtenida correctamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Talla.class)))
        }
    )
    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener una talla por ID",
        description = "Busca una talla específica por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Talla encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Talla.class))),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada")
        }
    )
    public ResponseEntity<Talla> getTallaById(@PathVariable Long id) {
        return tallaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
        summary = "Crear una nueva talla",
        description = "Registra una nueva talla en la base de datos.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Talla creada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Talla.class))),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud (datos inválidos)")
        }
    )
    public ResponseEntity<Talla> createTalla(@RequestBody Talla talla) {
        return ResponseEntity.ok(tallaRepository.save(talla));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar una talla",
        description = "Modifica los datos de una talla existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Talla actualizada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Talla.class))),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada")
        }
    )
    public ResponseEntity<Talla> updateTalla(@PathVariable Long id, @RequestBody Talla updatedTalla) {
        return tallaRepository.findById(id).map(talla -> {
            talla.setTalla_US(updatedTalla.getTalla_US());
            talla.setTalla_EUR(updatedTalla.getTalla_EUR());
            return ResponseEntity.ok(tallaRepository.save(talla));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar una talla",
        description = "Borra una talla de la base de datos por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Talla eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Talla no encontrada")
        }
    )
    public ResponseEntity<Void> deleteTalla(@PathVariable Long id) {
        if (tallaRepository.existsById(id)) {
            tallaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
