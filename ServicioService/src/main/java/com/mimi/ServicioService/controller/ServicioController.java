package com.mimi.ServicioService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.ServicioService.dto.ServicioCreateRequest;
import com.mimi.ServicioService.dto.ServicioDTO;
import com.mimi.ServicioService.dto.ServicioUpdateRequest;
import com.mimi.ServicioService.service.ServicioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    // -------------------------------
    // 1. LISTAR TODOS
    // -------------------------------
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAll() {
        return ResponseEntity.ok(servicioService.getAll());
    }

    // -------------------------------
    // 2. BUSCAR POR ID
    // -------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getById(@PathVariable Long id) {
        Optional<ServicioDTO> servicio = servicioService.getById(id);

        if (servicio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicio.get());
    }

    // -------------------------------
    // 3. CREAR SERVICIO
    // -------------------------------
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServicioCreateRequest req) {

        // Validaciones básicas (igual que Kotlin)
        if (req.getPrecio() < 0) {
            return ResponseEntity.badRequest().body("El precio no puede ser negativo.");
        }
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("Nombre es obligatorio.");
        }

        ServicioDTO nuevo = servicioService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // -------------------------------
    // 4. ACTUALIZAR SERVICIO
    // -------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ServicioUpdateRequest req) {

        if (req.getPrecio() < 0) {
            return ResponseEntity.badRequest().body("El precio no puede ser negativo.");
        }
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("Nombre es obligatorio.");
        }

        Optional<ServicioDTO> actualizado = servicioService.update(id, req);

        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado.get());
    }

    // -------------------------------
    // 5. ELIMINAR SERVICIO
    // -------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean eliminado = servicioService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}



