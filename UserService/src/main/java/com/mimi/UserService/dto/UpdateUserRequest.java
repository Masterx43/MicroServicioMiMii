package com.mimi.UserService.dto;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String nombre;
    private String apellido;
    private String phone;
}