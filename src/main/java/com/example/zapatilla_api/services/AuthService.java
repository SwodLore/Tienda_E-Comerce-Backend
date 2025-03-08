package com.example.zapatilla_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.zapatilla_api.models.Trabajador;
import com.example.zapatilla_api.models.Usuario;
import com.example.zapatilla_api.repositories.TrabajadorRepository;
import com.example.zapatilla_api.repositories.UsuarioRepository;
import com.example.zapatilla_api.security.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) {
        // Buscar en usuarios
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return jwtUtil.generateToken(email, usuario.getRol().name());
            }
        }

        // Buscar en trabajadores
        Optional<Trabajador> trabajadorOpt = trabajadorRepository.findByEmail(email);
        if (trabajadorOpt.isPresent()) {
            Trabajador trabajador = trabajadorOpt.get();
            if (passwordEncoder.matches(password, trabajador.getPassword())) {
                return jwtUtil.generateToken(email, trabajador.getRol().name());
            }
        }

        return null; // Credenciales incorrectas
    }
}
