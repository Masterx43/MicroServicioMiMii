package com.mimi.ServicioService.dto;

import lombok.Data;

@Data
public class ServicioCreateRequest {
    private String nombre;
    private String descripcion;
    private int precio;
    private Long categoriaId;
}
