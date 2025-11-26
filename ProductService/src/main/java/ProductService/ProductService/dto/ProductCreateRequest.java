package ProductService.ProductService.dto;

import lombok.Data;

@Data
public class ProductCreateRequest {
    private String nombre;
    private String descripcion;
    private Integer precio;
    private String imagen;
    private Long categoriaId;
}
