package com.mimi.ServicioService.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mimi.ServicioService.model.Categoria;
import com.mimi.ServicioService.model.Servicio;
import com.mimi.ServicioService.repository.CategoriaRepository;
import com.mimi.ServicioService.repository.ServicioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class LoadToDatabase {

    @Bean
    CommandLineRunner initDatabase(
            CategoriaRepository categoriaRepo,
            ServicioRepository servicioRepo
    ) {
        return args -> {

            if (categoriaRepo.count() == 0 && servicioRepo.count() == 0) {

                // ------------------------------
                // CREAR CATEGORÍAS
                // ------------------------------

                Categoria coloracion = categoriaRepo.save(
                        Categoria.builder()
                                .nombre("Coloración")
                                .build()
                );

                Categoria cortes = categoriaRepo.save(
                        Categoria.builder()
                                .nombre("Cortes")
                                .build()
                );

                Categoria tratamiento = categoriaRepo.save(
                        Categoria.builder()
                                .nombre("Tratamientos")
                                .build()
                );

                Categoria peinados = categoriaRepo.save(
                        Categoria.builder()
                                .nombre("Peinados")
                                .build()
                );

                // ------------------------------
                // CREAR SERVICIOS
                // ------------------------------

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Coloración Profesional")
                                .descripcion("Renueva tu look con tonos vibrantes y duraderos.")
                                .precio(24990)
                                .categoria(coloracion)
                                .build()
                );

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Mechas Babylight")
                                .descripcion("Mechas finas para un look natural y luminoso.")
                                .precio(32990)
                                .categoria(coloracion)
                                .build()
                );

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Corte Mujer")
                                .descripcion("Corte profesional con asesoría personalizada.")
                                .precio(14990)
                                .categoria(cortes)
                                .build()
                );

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Corte Hombre")
                                .descripcion("Corte moderno y prolijo, incluye perfilado.")
                                .precio(9990)
                                .categoria(cortes)
                                .build()
                );

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Tratamiento Hidratación Profunda")
                                .descripcion("Repara y nutre el cabello dañado.")
                                .precio(19990)
                                .categoria(tratamiento)
                                .build()
                );

                servicioRepo.save(
                        Servicio.builder()
                                .nombre("Peinado Simple")
                                .descripcion("Peinado casual para el día a día.")
                                .precio(7990)
                                .categoria(peinados)
                                .build()
                );

                System.out.println("Datos iniciales cargados correctamente (categorías y servicios)");

            } else {
                System.out.println("Datos existentes — no se insertaron datos nuevos.");
            }
        };
    }
}
