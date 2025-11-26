package com.mimi.ServicioService.dto;

import lombok.Data;

@Data
public class ServicioUpdateRequest {
    private String nombre;
    private String descripcion;
    private int precio;
    private Long categoriaId;
}