package com.example.zapatilla_api.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.zapatilla_api.models.Marca;
import com.example.zapatilla_api.repositories.MarcaRepository;

@Component
public class MarcaSeeder implements CommandLineRunner {
    private final MarcaRepository marcaRepository;

    public MarcaSeeder(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public void run(String... args) {
        if (marcaRepository.count() == 0) { 
            List<Marca> marcas = Arrays.asList(
                crearMarca("Nike", 10),
                crearMarca("Adidas", 15),
                crearMarca("Puma", 5),
                crearMarca("Reebok", 20)
            );
            marcaRepository.saveAll(marcas);
            System.out.println("Marcas insertadas en la base de datos.");
        } else {
            System.out.println("Las marcas ya existen en la base de datos.");
        }
    }
    private Marca crearMarca(String nombre, int descuento) {
        Marca marca = new Marca();
        marca.setNombre(nombre);
        marca.setDescuento(descuento);
        return marca;
    }
}
