package com.mimi.UserService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mimi.UserService.dto.LoginRequest;
import com.mimi.UserService.dto.RegisterRequest;
import com.mimi.UserService.dto.UpdateUserRequest;
import com.mimi.UserService.dto.UserAuthDTO;
import com.mimi.UserService.dto.UserDTO;
import com.mimi.UserService.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Gestión de usuarios, validación, registro y actualización")
public class UserController {

    private final UserService userService;

    // =======================================================
    // REGISTRO
    // =======================================================
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario registrado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class), examples = @ExampleObject(value = """
                    {
                      "idUser": 12,
                      "nombre": "Camila",
                      "apellido": "Rojas",
                      "correo": "camila.rojas@mimi.cl",
                      "phone": "+56999887766",
                      "rolId": 1
                    }
                    """))),
            @ApiResponse(responseCode = "400", description = "Error al registrar", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    {
                      "error": "El correo ya está registrado"
                    }
                    """)))
    })
    @PostMapping("/register")
    public ResponseEntity<?> registrar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del usuario a registrar", required = true, content = @Content(schema = @Schema(implementation = RegisterRequest.class), examples = @ExampleObject(value = """
                    {
                      "nombre": "Bastián",
                      "apellido": "Gómez",
                      "correo": "bastian@mail.com",
                      "password": "123456",
                      "phone": "+56912345678",
                      "rolId": 1
                    }
                    """))) @RequestBody RegisterRequest req) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userService.registrar(req));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // =======================================================
    // VALIDAR LOGIN
    // =======================================================
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Credenciales válidas", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserAuthDTO.class), examples = @ExampleObject(value = """
                    {
                      "idUser": 1,
                      "nombre": "Camila",
                      "apellido": "Rojas",
                      "correo": "camila.rojas@mimi.cl",
                      "rolId": 1
                    }
                    """))),
            @ApiResponse(responseCode = "401", description = "Credenciales incorrectas", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "error": "Credenciales incorrectas" }
                    """)))
    })

    @PostMapping("/validate")
    public ResponseEntity<?> validar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credenciales del usuario", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class), examples = @ExampleObject(value = """
                    {
                      "email": "cliente@mimi.cl",
                      "password": "1234"
                    }
                    """))) @RequestBody LoginRequest req) {

        UserAuthDTO auth = userService.validarLogin(req.getEmail(), req.getPassword());

        if (auth == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");

        return ResponseEntity.ok(auth);
    }

    // =======================================================
    // OBTENER USUARIO POR ID
    // =======================================================
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class), examples = @ExampleObject(value = """
                    {
                      "idUser": 5,
                      "nombre": "Diego",
                      "apellido": "Muñoz",
                      "correo": "diego.munoz@mimi.cl",
                      "phone": "+56912398745",
                      "rolId": 2
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "error": "Usuario con ID 5 no existe" }
                    """)))
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.obtener(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // =======================================================
    // OBTENER TRABAJADORES
    // =======================================================
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de trabajadores", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)), examples = @ExampleObject(value = """
                    [
                      {
                        "idUser": 7,
                        "nombre": "Laura",
                        "apellido": "Carrasco",
                        "correo": "laura.carrasco@mimi.cl",
                        "phone": "+56911223344",
                        "rolId": 2
                      },
                      {
                        "idUser": 9,
                        "nombre": "Daniel",
                        "apellido": "Pérez",
                        "correo": "daniel.perez@mimi.cl",
                        "phone": "+56955667788",
                        "rolId": 2
                      }
                    ]
                    """))),
            @ApiResponse(responseCode = "204", description = "Lista vacía", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "\"Lista vacía\"")))
    })

    @GetMapping("/workers")
    public ResponseEntity<?> trabajadores() {
        List<UserDTO> workers = userService.obtenerTrabajadores();
        if (workers.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Lista vacia");
        }
        return ResponseEntity.ok(workers);
    }

    // =======================================================
    // OBTENER TODOS LOS USUARIOS
    // =======================================================
    @Operation(summary = "Obtener todos los usuarios", description = "Retorna la lista completa de usuarios del sistema.")
    @ApiResponse(responseCode = "200", description = "Lista retornada correctamente")
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> usuarios() {
        return ResponseEntity.ok(userService.obtenerUsuarios());
    }

    // =======================================================
    // VERIFICAR EMAIL EXISTENTE
    // =======================================================
    @Operation(summary = "Verificar si un email existe", description = "Retorna true si el correo ya está registrado.")
    @ApiResponse(responseCode = "200", description = "Resultado de la verificación")
    @GetMapping("/exists/email/{correo}")
    public ResponseEntity<Boolean> emailExiste(@PathVariable String correo) {
        return ResponseEntity.ok(userService.existeEmail(correo));
    }

    // =======================================================
    // ACTUALIZAR USUARIO
    // =======================================================
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class), examples = @ExampleObject(value = """
                    {
                      "idUser": 12,
                      "nombre": "Camila",
                      "apellido": "Rojas",
                      "correo": "camila.rojas@mimi.cl",
                      "phone": "+56955443322",
                      "rolId": 1
                    }
                    """))),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "error": "Usuario con ID 12 no existe" }
                    """))),
            @ApiResponse(responseCode = "500", description = "Error interno", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    { "error": "Error al actualizar usuario" }
                    """)))
    })
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

    // =======================================================
    // OBTENER USUARIO POR EMAIL
    // =======================================================
    @Operation(summary = "Obtener usuario por correo", description = "Retorna un usuario según su email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/by-email/{correo}")
    public ResponseEntity<UserDTO> obtenerPorCorreo(@PathVariable String correo) {
        return ResponseEntity.ok(userService.obtenerPorCorreo(correo).get());
    }

}
