package com.mimi.UserService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mimi.UserService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<User> findByRol_IdRol(Long idRol);
}

