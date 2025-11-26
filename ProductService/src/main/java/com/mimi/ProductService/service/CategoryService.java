package com.mimi.ProductService.service;

import com.mimi.ProductService.dto.CategoryDTO;
import com.mimi.ProductService.model.Category;
import com.mimi.ProductService.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepo;

    // Listar todas las categorías
    public List<CategoryDTO> listar() {
        return categoryRepo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Obtener una categoría por ID
    public Optional<CategoryDTO> obtener(Long id) {
        return categoryRepo.findById(id)
                .map(this::toDTO);
    }

    // Crear categoría
    public CategoryDTO crear(CategoryDTO dto) {
        Category nueva = Category.builder()
                .nombre(dto.getNombre())
                .build();

        return toDTO(categoryRepo.save(nueva));
    }

    // Actualizar categoría
    public Optional<CategoryDTO> actualizar(Long id, CategoryDTO dto) {

        Optional<Category> opt = categoryRepo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Category original = opt.get();
        original.setNombre(dto.getNombre());

        return Optional.of(toDTO(categoryRepo.save(original)));
    }

    // Eliminar categoría
    public boolean eliminar(Long id) {
        if (!categoryRepo.existsById(id)) return false;

        categoryRepo.deleteById(id);
        return true;
    }

    // Mapper entidad → DTO
    private CategoryDTO toDTO(Category categoria) {
        return new CategoryDTO(
                categoria.getIdCategoria(),
                categoria.getNombre()
        );
    }
}

