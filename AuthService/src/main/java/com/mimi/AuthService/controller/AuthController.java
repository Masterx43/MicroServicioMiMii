package com.mimi.AuthService.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mimi.AuthService.dto.LoginRequest;
import com.mimi.AuthService.dto.LoginResponse;
import com.mimi.AuthService.security.JwtUtil;
import com.mimi.AuthService.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {

        LoginResponse response = authService.login(request.getEmail(), request.getPassword());

        if (!response.isSuccess()) {
            return ResponseEntity.badRequest().body(response);
        }

        if (response.getUser() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin-only")
    public ResponseEntity<?> adminOnly(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token faltante");
        }

        String token = authHeader.substring(7);

        Long rolId;
        try {
            rolId = jwtUtil.extractRole(token);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Token inválido");
        }

        if (rolId != 2L) { // asumiendo que 2 es ADMIN
            return ResponseEntity.status(403).body("Acceso denegado: solo ADMIN");
        }

        return ResponseEntity.ok("Bienvenido ADMIN ✔");
    }

}
