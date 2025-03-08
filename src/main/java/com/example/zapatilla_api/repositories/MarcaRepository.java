package com.example.zapatilla_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zapatilla_api.models.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    
    // Buscar una marca por nombre (opcional)
    Optional<Marca> findByNombre(String nombre);
}

