package com.mimi.ServicioService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    private String nombre;
    private String descripcion;
    private Integer precio;
    private String imagenUrl;
    
    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;
    
}
