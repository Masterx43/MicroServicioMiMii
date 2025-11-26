package com.mimi.ProductService.dto;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String imagen;
    private Long categoriaId;
}
