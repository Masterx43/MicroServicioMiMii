package com.mimi.ReservaService.dto;

import lombok.Data;

@Data
public class ServicioDTO {
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private int precio;
}
