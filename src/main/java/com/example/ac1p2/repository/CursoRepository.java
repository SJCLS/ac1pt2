package com.example.ac1p2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac1p2.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{
    List<Curso> findByName(String nome);
    

}
