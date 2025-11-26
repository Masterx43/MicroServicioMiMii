package ProductService.ProductService.Service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ProductService.ProductService.dto.*;
import ProductService.ProductService.repository.*;
import ProductService.ProductService.model.*;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    public List<ProductDTO> getAll() {
        return productRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<ProductDTO> getById(Long id) {
        return productRepo.findById(id)
                .map(this::toDTO);
    }

    public ProductDTO create(ProductCreateRequest req) {

        Category categoria = categoryRepo.findById(req.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no existe"));

        Product p = Product.builder()
                .nombre(req.getNombre())
                .descripcion(req.getDescripcion())
                .precio(req.getPrecio())
                .imagen(req.getImagen())
                .categoria(categoria)
                .build();

        return toDTO(productRepo.save(p));
    }

    public Optional<ProductDTO> update(Long id, ProductUpdateRequest req) {

        Product original = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        Category categoria = categoryRepo.findById(req.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no existe"));

        original.setNombre(req.getNombre());
        original.setDescripcion(req.getDescripcion());
        original.setPrecio(req.getPrecio());
        original.setImagen(req.getImagen());
        original.setCategoria(categoria);

        return Optional.of(toDTO(productRepo.save(original)));
    }

    public boolean delete(Long id) {
        if (!productRepo.existsById(id)) return false;

        productRepo.deleteById(id);
        return true;
    }

    private ProductDTO toDTO(Product p) {
        return new ProductDTO(
                p.getIdProduct(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getImagen(),
                p.getCategoria() != null ? p.getCategoria().getIdCategoria() : null
        );
    }
}
