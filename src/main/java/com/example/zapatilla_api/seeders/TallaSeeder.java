package com.example.zapatilla_api.seeders;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.zapatilla_api.models.Talla;
import com.example.zapatilla_api.repositories.TallaRepository;

@Component
public class TallaSeeder implements CommandLineRunner {
    private final TallaRepository tallaRepository;

    public TallaSeeder(TallaRepository tallaRepository) {
        this.tallaRepository = tallaRepository;
    }
    @Override
    public void run(String... args) {
        if (tallaRepository.count() == 0) {
            List<Talla> tallas = Arrays.asList(
                crearTalla("7", "40"),
                crearTalla("7.5", "40.5"),
                crearTalla("8", "41.5"),
                crearTalla("8.5", "42"),
                crearTalla("9", "42.5"),
                crearTalla("9.5", "43"),
                crearTalla("10", "44")
            );
            tallaRepository.saveAll(tallas);
            System.out.println("Tallas iniciales insertadas correctamente.");
        }
    }
    private Talla crearTalla(String talla_US, String talla_EUR) {
        Talla talla = new Talla();
        talla.setTalla_US(talla_US);
        talla.setTalla_EUR(talla_EUR);
        return talla;
    }
}
