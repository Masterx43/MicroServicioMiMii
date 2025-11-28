package com.mimi.ServicioService.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServicioDTO {
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private Long categoriaId;
    private String imagenUrl;
}