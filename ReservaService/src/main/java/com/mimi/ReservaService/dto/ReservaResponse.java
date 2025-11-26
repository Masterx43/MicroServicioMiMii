package com.mimi.ReservaService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaResponse {
    private Long idReserva;
    private Long idUsuario;
    private Long idServicio;
    private Long idTrabajador;
    private String fecha;
    private String hora;
    private String estado;
}
