package com.mimi.ProductService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mimi.ProductService.service.ProductService;
import com.mimi.ProductService.dto.ProductCreateRequest;
import com.mimi.ProductService.dto.ProductDTO;
import com.mimi.ProductService.dto.ProductUpdateRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos del sistema")
public class ProductController {

    private final ProductService service;

    // ==========================================================
    // 1. LISTAR TODOS
    // ==========================================================
    @Operation(
        summary = "Listar todos los productos",
        description = "Devuelve la lista completa de productos registrados."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Lista de productos",
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)),
            examples = @ExampleObject(
                value = """
                [
                  {
                    "idProduct": 1,
                    "nombre": "Shampoo Nutritivo",
                    "descripcion": "Ideal para cabello seco",
                    "precio": 7990,
                    "imagen": "shampoo_nutritivo.png",
                    "categoriaId": 2
                  },
                  {
                    "idProduct": 2,
                    "nombre": "Acondicionador Suave",
                    "descripcion": "Hidratación profunda",
                    "precio": 6990,
                    "imagen": "acondicionador_suave.png",
                    "categoriaId": 2
                  }
                ]
                """
            )
        )
    )
    @GetMapping
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(service.getAll());
    }

    // ==========================================================
    // 2. OBTENER POR ID
    // ==========================================================
    @Operation(
        summary = "Obtener producto por ID",
        description = "Devuelve los datos de un producto específico."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(
                schema = @Schema(implementation = ProductDTO.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "idProduct": 5,
                      "nombre": "Serum Reparador",
                      "descripcion": "Repara puntas abiertas",
                      "precio": 9990,
                      "imagen": "serum_reparador.png",
                      "categoriaId": 3
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                examples = @ExampleObject(value = "\"Producto no encontrado\"")
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================================
    // 3. CREAR PRODUCTO
    // ==========================================================
    @Operation(
        summary = "Crear un producto",
        description = "Registra un nuevo producto en el sistema."
    )
    @ApiResponse(
        responseCode = "200",
        description = "Producto creado",
        content = @Content(
            schema = @Schema(implementation = ProductDTO.class),
            examples = @ExampleObject(
                value = """
                {
                  "idProduct": 10,
                  "nombre": "Aceite Capilar",
                  "descripcion": "Aceite nutritivo para cabello dañado",
                  "precio": 12990,
                  "imagen": "aceite_capilar.png",
                  "categoriaId": 4
                }
                """
            )
        )
    )
    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    // ==========================================================
    // 4. ACTUALIZAR PRODUCTO
    // ==========================================================
    @Operation(
        summary = "Actualizar un producto",
        description = "Modifica los datos de un producto existente."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado",
            content = @Content(
                schema = @Schema(implementation = ProductDTO.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "idProduct": 10,
                      "nombre": "Aceite Capilar Renew",
                      "descripcion": "Fórmula mejorada",
                      "precio": 13990,
                      "imagen": "aceite_renew.png",
                      "categoriaId": 4
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                examples = @ExampleObject(value = "\"Producto no encontrado\"")
            )
        )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody ProductUpdateRequest req) {
        return service.update(id, req)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==========================================================
    // 5. ELIMINAR PRODUCTO
    // ==========================================================
    @Operation(
        summary = "Eliminar producto",
        description = "Elimina un producto utilizando su ID."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Producto eliminado correctamente",
            content = @Content(
                examples = @ExampleObject(value = "\"Eliminado\"")
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Producto no encontrado",
            content = @Content(
                examples = @ExampleObject(value = "\"Producto no encontrado\"")
            )
        )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.ok("Eliminado")
                : ResponseEntity.notFound().build();
    }
}
