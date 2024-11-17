package com.example.msauth.controller;

import com.example.msauth.dto.AuthUserDto;
import com.example.msauth.entity.AuthUser;
import com.example.msauth.entity.TokenDto;
import com.example.msauth.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthUserController {

    private final AuthUserService authUserService;

    // Inyección de dependencia por constructor
    @Autowired
    public AuthUserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    /**
     * Endpoint para el inicio de sesión
     */
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthUserDto authUserDto) {
        return handleResponse(authUserService.login(authUserDto));
    }

    /**
     * Endpoint para validar un token
     */
    @PostMapping("/validate")
    public ResponseEntity<TokenDto> validate(@RequestParam String token) {
        return handleResponse(authUserService.validate(token));
    }

    /**
     * Endpoint para crear un nuevo usuario
     */
    @PostMapping("/create")
    public ResponseEntity<AuthUser> create(@RequestBody AuthUserDto authUserDto) {
        return handleResponse(authUserService.save(authUserDto));
    }

    /**
     * Método genérico para manejar las respuestas de los endpoints
     */
    private <T> ResponseEntity<T> handleResponse(T result) {
        if (result == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(result);
    }
}
