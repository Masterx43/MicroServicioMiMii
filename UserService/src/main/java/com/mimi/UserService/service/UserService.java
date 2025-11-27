package com.mimi.UserService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mimi.UserService.dto.RegisterRequest;
import com.mimi.UserService.dto.UpdateUserRequest;
import com.mimi.UserService.dto.UserAuthDTO;
import com.mimi.UserService.dto.UserDTO;
import com.mimi.UserService.model.Rol;
import com.mimi.UserService.model.User;
import com.mimi.UserService.repository.RoleRepository;
import com.mimi.UserService.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final RoleRepository rolRepo;
    private final PasswordEncoder passwordEncoder;

    public UserDTO registrar(RegisterRequest req) {

        if (userRepo.existsByCorreo(req.getCorreo()))
            throw new RuntimeException("El correo ya está registrado");

        Rol rol = rolRepo.findById(req.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        User user = User.builder()
                .nombre(req.getNombre())
                .apellido(req.getApellido())
                .correo(req.getCorreo())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .rol(rol)
                .build();

        userRepo.save(user);

        return toDTO(user);
    }

    public UserAuthDTO validarLogin(String email, String password) {

        Optional<User> optUser = userRepo.findByCorreo(email);

        if (optUser.isEmpty()) {
            return null; // App recibirá 401
        }

        User user = optUser.get();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }

        return new UserAuthDTO(
                user.getIdUser(),
                user.getNombre(),
                user.getApellido(),
                user.getCorreo(),
                user.getRol().getIdRol());
    }

    public UserDTO obtener(Long id) {
        User u = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return toDTO(u);
    }

    public List<UserDTO> obtenerTrabajadores() {
        return userRepo.findByRol_IdRol(3L) // rol 3 = TRABAJADOR
                .stream().map(this::toDTO).toList();
    }

    public List<UserDTO> obtenerUsuarios() {
        return userRepo.findAll().stream().map(this::toDTO).toList();
    }

    public boolean existeEmail(String correo) {
        return userRepo.existsByCorreo(correo);
    }

    public UserDTO actualizarUsuario(Long id, UpdateUserRequest req) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualizar solo campos permitidos
        if (req.getNombre() != null)
            user.setNombre(req.getNombre());

        if (req.getApellido() != null)
            user.setApellido(req.getApellido());

        if (req.getPhone() != null)
            user.setPhone(req.getPhone());

        userRepo.save(user);

        return toDTO(user);
    }

    public Optional<UserDTO> obtenerPorCorreo(String correo) {
        return userRepo.findByCorreo(correo)
                .map(this::toDTO);
    }

    private UserDTO toDTO(User u) {
        return new UserDTO(
                u.getIdUser(),
                u.getNombre(),
                u.getApellido(),
                u.getCorreo(),
                u.getPhone(),
                u.getRol().getIdRol());
    }
}
