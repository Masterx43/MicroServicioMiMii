package ProductService.ProductService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ProductService.ProductService.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
