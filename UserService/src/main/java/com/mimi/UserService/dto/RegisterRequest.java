package com.mimi.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nombre;
    private String apellido;
    private String correo;
    private String phone;
    private String password;
    private Long rolId;
}

