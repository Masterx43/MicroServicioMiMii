package com.mimi.ReservaService.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Long idUser;
    private String nombre;
    private String apellido;
    private Long rolId;
}

