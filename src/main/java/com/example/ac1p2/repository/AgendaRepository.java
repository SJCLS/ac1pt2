package com.example.ac1p2.repository;

//import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac1p2.model.Agenda;
import com.example.ac1p2.model.Curso;
import com.example.ac1p2.model.Professor;

public interface AgendaRepository extends JpaRepository<Agenda, Long>{
    List<Agenda> findByName(Curso curso);
    List<Agenda> findByName(Professor professor);

}
