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

import com.mimi.ServicioService.dto.CategoriaDTO;
import com.mimi.ServicioService.service.CategoriaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService service;

    // ------------------------------------------
    // 1. LISTAR TODAS LAS CATEGORÍAS
    // ------------------------------------------
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // ------------------------------------------
    // 2. OBTENER UNA CATEGORÍA POR ID
    // ------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Optional<CategoriaDTO> categoria = service.obtener(id);

        if (categoria.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada");
        }

        return ResponseEntity.ok(categoria.get());
    }

    // ------------------------------------------
    // 3. CREAR CATEGORÍA
    // ------------------------------------------
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody CategoriaDTO dto) {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        CategoriaDTO nueva = service.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    // ------------------------------------------
    // 4. ACTUALIZAR CATEGORÍA
    // ------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody CategoriaDTO dto
    ) {

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio.");
        }

        Optional<CategoriaDTO> actualizada = service.actualizar(id, dto);

        if (actualizada.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada");
        }

        return ResponseEntity.ok(actualizada.get());
    }

    // ------------------------------------------
    // 5. ELIMINAR CATEGORÍA
    // ------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        boolean eliminado = service.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Categoría no encontrada");
        }

        return ResponseEntity.noContent().build();
    }
}



