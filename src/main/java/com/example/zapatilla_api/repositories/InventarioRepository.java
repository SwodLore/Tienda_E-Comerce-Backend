package com.example.zapatilla_api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zapatilla_api.models.Inventario;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    
}
