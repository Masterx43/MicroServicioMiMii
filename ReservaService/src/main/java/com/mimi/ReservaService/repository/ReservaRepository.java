package com.mimi.ReservaService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mimi.ReservaService.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Optional<Reserva> findByFechaAndHoraAndIdTrabajador(String fecha, String hora, Long trabajador);

    List<Reserva> findByIdUsuario(Long idUsuario);

    List<Reserva> findByIdTrabajador(Long idTrabajador);

}

