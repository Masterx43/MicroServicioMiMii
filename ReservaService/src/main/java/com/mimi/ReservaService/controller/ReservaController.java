package com.mimi.ReservaService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mimi.ReservaService.dto.CrearReservaRequest;
import com.mimi.ReservaService.dto.ReservaDetalleResponse;
import com.mimi.ReservaService.dto.ReservaResponse;
import com.mimi.ReservaService.service.ReservaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    // Crear reserva
    @PostMapping("/crear")
    public ResponseEntity<?> crearReserva(@RequestBody CrearReservaRequest req) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservaService.crearReserva(req));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/detalles")
    public ResponseEntity<?> obtenerTodas() {
        try {
            List<ReservaDetalleResponse> detalles = reservaService.obtenerTodasLasReservasConDetalles();
            return ResponseEntity.ok(detalles);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener reservas");
        }
    }

    // Obtener reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerReserva(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(reservaService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Listar todas las reservas (Admin)
    @GetMapping("/all")
    public ResponseEntity<?> listarTodas() {
        List<ReservaResponse> lista = reservaService.listarTodas();
        if (lista.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(lista);
    }

    // Reservas por usuario
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> obtenerPorUsuario(@PathVariable Long id) {
        try {
            List<ReservaResponse> r = reservaService.obtenerReservasUsuario(id);
            if (r.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(r);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener reservas del usuario");
        }
    }



    @GetMapping("/usuario/detalle/{id}")
    public ResponseEntity<?> obtenerDetallePorUsuario(@PathVariable Long id) {
        try {
            List<ReservaDetalleResponse> r = reservaService.obtenerReservasDetalleUsuario(id);
            if (r.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(r);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener reservas del usuario");
        }
    }

    // Reservas por trabajador
    @GetMapping("/trabajador/{id}")
    public ResponseEntity<?> obtenerPorTrabajador(@PathVariable Long id) {
        try {
            List<ReservaResponse> r = reservaService.obtenerReservasTrabajador(id);
            if (r.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(r);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener reservas del trabajador");
        }
    }

    // Actualizar estado (Confirmar, Completada, Cancelada)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestBody String nuevoEstado) {
        try {
            return ResponseEntity.ok(reservaService.actualizarEstado(id, nuevoEstado));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/trabajador/detalle/{id}")
    public ResponseEntity<?> obtenerDetallePorTrabajador(@PathVariable Long id) {
        try {
            List<ReservaDetalleResponse> r = reservaService.obtenerReservasDetalleTrabajador(id);
            if (r.isEmpty())
                return ResponseEntity.noContent().build();
            return ResponseEntity.ok(r);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al obtener reservas del trabajador");
        }
    }
}
