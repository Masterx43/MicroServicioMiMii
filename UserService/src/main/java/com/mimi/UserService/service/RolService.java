package com.mimi.UserService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mimi.UserService.dto.RoleDTO;
import com.mimi.UserService.model.Rol;
import com.mimi.UserService.repository.RoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RoleRepository repo;

    public List<RoleDTO> listar() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public RoleDTO obtener(Long id) {
        Rol rol = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return toDTO(rol);
    }

    public RoleDTO crear(String nombre) {
        Rol nuevo = Rol.builder()
                .nombre(nombre)
                .build();

        return toDTO(repo.save(nuevo));
    }

    public RoleDTO actualizar(Long id, String nombre) {
        Rol rol = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        rol.setNombre(nombre);

        return toDTO(repo.save(rol));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }

    private RoleDTO toDTO(Rol rol) {
        return new RoleDTO(
                rol.getIdRol(),
                rol.getNombre()
        );
    }
}

