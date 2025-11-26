package com.mimi.UserService.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.UserService.dto.RoleDTO;
import com.mimi.UserService.service.RolService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RolService service;

    @GetMapping
    public List<RoleDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.obtener(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Rol no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RoleDTO dto) {
        try {
            return ResponseEntity.status(201).body(service.crear(dto.getNombre()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear rol");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RoleDTO dto) {
        try {
            return ResponseEntity.ok(service.actualizar(id, dto.getNombre()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar rol");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            service.eliminar(id);
            return ResponseEntity.ok("Rol eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al eliminar rol");
        }
    }
}


