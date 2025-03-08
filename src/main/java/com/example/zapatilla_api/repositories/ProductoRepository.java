package com.example.zapatilla_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.zapatilla_api.models.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
}
