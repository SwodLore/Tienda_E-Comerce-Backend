package com.example.zapatilla_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.zapatilla_api.models.Talla;

public interface TallaRepository extends JpaRepository<Talla, Long> {
    
}
