package com.example.zapatilla_api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.zapatilla_api.models.Trabajador;

public interface TrabajadorRepository extends JpaRepository<Trabajador, Long> {
    Optional<Trabajador> findByEmail(String email);
}
