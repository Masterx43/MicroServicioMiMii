package com.mimi.ServicioService.service;

import com.mimi.ServicioService.dto.ServicioCreateRequest;
import com.mimi.ServicioService.dto.ServicioDTO;
import com.mimi.ServicioService.dto.ServicioUpdateRequest;
import com.mimi.ServicioService.model.Categoria;
import com.mimi.ServicioService.model.Servicio;
import com.mimi.ServicioService.repository.CategoriaRepository;
import com.mimi.ServicioService.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final CategoriaRepository categoriaRepository;

    public List<ServicioDTO> getAll() {
        return servicioRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<ServicioDTO> getById(Long id) {
        return servicioRepository.findById(id)
                .map(this::toDTO);
    }

    public ServicioDTO create(ServicioCreateRequest req) {

        Categoria categoria = categoriaRepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Servicio servicio = new Servicio();
        servicio.setNombre(req.getNombre());
        servicio.setDescripcion(req.getDescripcion());
        servicio.setPrecio(req.getPrecio());
        servicio.setCategoria(categoria);

        return toDTO(servicioRepository.save(servicio));
    }

    public Optional<ServicioDTO> update(Long id, ServicioUpdateRequest req) {

        Optional<Servicio> opt = servicioRepository.findById(id);
        if (opt.isEmpty()) return Optional.empty();

        Servicio servicio = opt.get();

        Categoria categoria = categoriaRepository.findById(req.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        servicio.setNombre(req.getNombre());
        servicio.setDescripcion(req.getDescripcion());
        servicio.setPrecio(req.getPrecio());
        servicio.setCategoria(categoria);

        return Optional.of(toDTO(servicioRepository.save(servicio)));
    }

    public boolean delete(Long id) {
        if (!servicioRepository.existsById(id)) {
            return false;
        }
        servicioRepository.deleteById(id);
        return true;
    }

    private ServicioDTO toDTO(Servicio servicio) {
        return new ServicioDTO(
                servicio.getIdServicio(),
                servicio.getNombre(),
                servicio.getDescripcion(),
                servicio.getPrecio(),
                servicio.getCategoria().getIdCategoria(),
                servicio.getImagenUrl()
        );
    }
}




