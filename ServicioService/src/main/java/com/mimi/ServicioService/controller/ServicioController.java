package com.mimi.ServicioService.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mimi.ServicioService.dto.ServicioCreateRequest;
import com.mimi.ServicioService.dto.ServicioDTO;
import com.mimi.ServicioService.dto.ServicioUpdateRequest;
import com.mimi.ServicioService.service.ServicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
@Tag(name = "Servicios", description = "Gestión de servicios del sistema")
public class ServicioController {

    private final ServicioService servicioService;

    // ==========================================================
    // 1. LISTAR TODOS
    // ==========================================================
    @Operation(
        summary = "Listar todos los servicios",
        description = "Obtiene una lista de todos los servicios disponibles."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de servicios",
        content = @Content(
            mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ServicioDTO.class)),
            examples = @ExampleObject(
                value = """
                [
                  {
                    "idServicio": 1,
                    "nombre": "Corte de Cabello",
                    "descripcion": "Corte profesional con asesoría",
                    "precio": 7000,
                    "categoriaId": 2,
                    "imagenUrl": "corte-cabello.png"
                  },
                  {
                    "idServicio": 2,
                    "nombre": "Peinado",
                    "descripcion": "Peinado para eventos",
                    "precio": 8500,
                    "categoriaId": 3,
                    "imagenUrl": "peinado-evento.png"
                  }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<List<ServicioDTO>> getAll() {
        return ResponseEntity.ok(servicioService.getAll());
    }

    // ==========================================================
    // 2. BUSCAR POR ID
    // ==========================================================
    @Operation(
        summary = "Obtener servicio por ID",
        description = "Busca un servicio usando su identificador."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Servicio encontrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ServicioDTO.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "idServicio": 1,
                      "nombre": "Corte de Cabello",
                      "descripcion": "Corte profesional con asesoría",
                      "precio": 7000,
                      "categoriaId": 2,
                      "imagenUrl": "corte-cabello.png"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Servicio no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{ \"error\": \"Servicio no encontrado\" }")
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioDTO> getById(@PathVariable Long id) {
        Optional<ServicioDTO> servicio = servicioService.getById(id);

        if (servicio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(servicio.get());
    }

    // ==========================================================
    // 3. CREAR SERVICIO
    // ==========================================================
    @Operation(
        summary = "Crear un nuevo servicio",
        description = "Registra un nuevo servicio en el sistema."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "201",
            description = "Servicio creado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ServicioDTO.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "idServicio": 10,
                      "nombre": "Tintura Premium",
                      "descripcion": "Servicio de tintura profesional",
                      "precio": 15000,
                      "categoriaId": 1,
                      "imagenUrl": "tintura-premium.jpg"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(name = "PrecioNegativo", value = "\"El precio no puede ser negativo.\""),
                    @ExampleObject(name = "NombreObligatorio", value = "\"Nombre es obligatorio.\"")
                }
            )
        )
    })
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ServicioCreateRequest req) {

        if (req.getPrecio() < 0) {
            return ResponseEntity.badRequest().body("El precio no puede ser negativo.");
        }
        if (req.getNombre() == null || req.getNombre().isBlank()) {
            return ResponseEntity.badRequest().body("Nombre es obligatorio.");
        }

        ServicioDTO nuevo = servicioService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    // ==========================================================
    // 4. ACTUALIZAR SERVICIO
    // ==========================================================
    @Operation(
        summary = "Actualizar un servicio",
        description = "Modifica un servicio existente por ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Servicio actualizado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ServicioDTO.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "idServicio": 10,
                      "nombre": "Tintura Premium",
                      "descripcion": "Servicio actualizado con nueva descripción",
                      "precio": 18000,
                      "categoriaId": 1,
                      "imagenUrl": "tintura-premium.jpg"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "\"Nombre es obligatorio.\"")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Servicio no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{ \"error\": \"Servicio no encontrado\" }")
            )
        )
    })
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

    // ==========================================================
    // 5. ELIMINAR SERVICIO
    // ==========================================================
    @Operation(
        summary = "Eliminar servicio",
        description = "Elimina un servicio por su ID."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "204",
            description = "Eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Servicio no encontrado",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(value = "{ \"error\": \"Servicio no encontrado\" }")
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        boolean eliminado = servicioService.delete(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
