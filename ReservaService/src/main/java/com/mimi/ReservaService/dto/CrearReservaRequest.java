package com.mimi.ReservaService.dto;

import lombok.Data;

@Data
public class CrearReservaRequest {
    private Long idUsuario;
    private Long idServicio;
    private Long idTrabajador;
    private String fecha;
    private String hora;
}
