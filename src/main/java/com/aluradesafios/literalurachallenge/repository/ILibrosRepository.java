package com.aluradesafios.literalurachallenge.repository;

//import com.aluracursos.desafio_literalura.models.Libros;
import com.aluradesafios.literalurachallenge.models.Autores;
import com.aluradesafios.literalurachallenge.models.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ILibrosRepository extends JpaRepository<Libros, Long> {
    Libros findByTitulo(String titulo);

    List<Libros> findByLenguajesContaining(String lenguaje);
}
