package com.mimi.ProductService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private int code;

    private T data;
}