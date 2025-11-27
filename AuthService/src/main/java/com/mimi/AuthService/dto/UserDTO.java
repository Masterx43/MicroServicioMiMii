package com.mimi.AuthService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long idUser;
    private String nombre;
    private String apellido;
    private String correo;
    private String phone;
    private Long rolId;
}
