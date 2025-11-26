package ProductService.ProductService.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    private Long idProduct;
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String imagen;
    private Long categoriaId;
}
