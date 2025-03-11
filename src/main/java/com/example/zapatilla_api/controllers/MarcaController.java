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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.zapatilla_api.models.Marca;

@RestController
@RequestMapping("/api/marcas")
@Tag(name = "Marcas", description = "Operaciones para gestionar las marcas de zapatillas")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;

    @PostMapping
    @Operation(
        summary = "Crear una nueva marca",
        description = "Registra una nueva marca en la base de datos.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Marca creada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Marca.class))),
            @ApiResponse(responseCode = "400", description = "Error en la solicitud (datos inválidos)")
        }
    )
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca) {
        Marca nuevaMarca = marcaRepository.save(marca);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMarca);
    }

    @GetMapping
    @Operation(
        summary = "Obtener todas las marcas",
        description = "Devuelve una lista de todas las marcas registradas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida correctamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Marca.class)))
        }
    )
    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener una marca por ID",
        description = "Busca una marca específica por su ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Marca encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Marca.class))),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
        }
    )
    public ResponseEntity<Marca> getMarcaById(@PathVariable Long id) {
        return marcaRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar una marca",
        description = "Borra una marca de la base de datos por su ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Marca eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "Marca no encontrada")
        }
    )
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        if (!marcaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marcaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
