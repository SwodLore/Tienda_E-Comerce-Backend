package com.example.zapatilla_api.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.zapatilla_api.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints para autenticación de usuarios")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(
        summary = "Autenticar usuario",
        description = "Verifica las credenciales y devuelve un token JWT si son correctas.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Autenticación exitosa, devuelve un token",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"token\": \"jwt_token_aqui\" }"))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{ \"error\": \"Credenciales incorrectas\" }")))
        }
    )
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String token = authService.login(email, password);

        if (token != null) {
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Credenciales incorrectas"));
        }
    }
}
