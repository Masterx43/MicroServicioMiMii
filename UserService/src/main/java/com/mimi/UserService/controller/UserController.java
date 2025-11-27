package com.mimi.UserService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.UserService.dto.LoginRequest;
import com.mimi.UserService.dto.RegisterRequest;
import com.mimi.UserService.dto.UpdateUserRequest;
import com.mimi.UserService.dto.UserAuthDTO;
import com.mimi.UserService.dto.UserDTO;
import com.mimi.UserService.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegisterRequest req) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.registrar(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validar(@RequestBody LoginRequest req) {

        UserAuthDTO auth = userService.validarLogin(req.getEmail(), req.getPassword());

        if (auth == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");

        return ResponseEntity.ok(auth);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.obtener(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/workers")
    public ResponseEntity<?> trabajadores() {
        List<UserDTO> workers = userService.obtenerTrabajadores();
        if (workers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lista vacia");
        }
        return ResponseEntity.ok(workers);
    }

    @GetMapping("/all")
    public ResponseEntity<?> usuarios() {
        return ResponseEntity.ok(userService.obtenerUsuarios());
    }

    @GetMapping("/exists/email/{correo}")
    public ResponseEntity<Boolean> emailExiste(@PathVariable String correo) {
        return ResponseEntity.ok(userService.existeEmail(correo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest req) {
        try {
            UserDTO actualizado = userService.actualizarUsuario(id, req);
            return ResponseEntity.ok(actualizado);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar usuario");
        }
    }

    @GetMapping("/by-email/{correo}")
    public ResponseEntity<UserDTO> obtenerPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(userService.obtenerPorCorreo(correo).get());
    }

}
