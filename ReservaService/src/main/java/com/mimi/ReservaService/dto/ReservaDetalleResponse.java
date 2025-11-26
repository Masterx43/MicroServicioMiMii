package com.mimi.ReservaService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDetalleResponse {
    private Long idReserva;
    private String fecha;
    private String hora;
    private String estado;

    private String usuario;
    private String trabajador;
    private String servicio;
}

