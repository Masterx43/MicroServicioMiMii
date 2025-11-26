package ProductService.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ProductService.ProductService.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}