package com.mimi.ReservaService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponsess<T> {
    private boolean success;
    private int code;

    private T data;
}
