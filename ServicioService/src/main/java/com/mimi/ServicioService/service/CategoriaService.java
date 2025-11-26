package com.mimi.ServicioService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mimi.ServicioService.dto.CategoriaDTO;
import com.mimi.ServicioService.model.Categoria;
import com.mimi.ServicioService.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repo;

    public List<CategoriaDTO> listar() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<CategoriaDTO> obtener(Long id) {
        return repo.findById(id)
                .map(this::toDTO);
    }

    public CategoriaDTO crear(CategoriaDTO dto) {
        Categoria nueva = Categoria.builder()
                .nombre(dto.getNombre())
                .build();

        return toDTO(repo.save(nueva));
    }

    public Optional<CategoriaDTO> actualizar(Long id, CategoriaDTO dto) {

        Optional<Categoria> opt = repo.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Categoria original = opt.get();
        original.setNombre(dto.getNombre());

        return Optional.of(toDTO(repo.save(original)));
    }

    public boolean eliminar(Long id) {
        if (!repo.existsById(id)) return false;

        repo.deleteById(id);
        return true;
    }

    private CategoriaDTO toDTO(Categoria categoria) {
        return new CategoriaDTO(
                categoria.getIdCategoria(),
                categoria.getNombre()
        );
    }
}



