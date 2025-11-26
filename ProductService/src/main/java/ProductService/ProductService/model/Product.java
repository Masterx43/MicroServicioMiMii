package ProductService.ProductService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduct;

    private String nombre;
    private String descripcion;
    private Integer precio;
    private String imagen;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Category categoria;
}
