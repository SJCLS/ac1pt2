package com.example.ac1p2.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ac1p2.model.Curso;
import com.example.ac1p2.repository.CursoRepository;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCurso(@RequestBody Curso curso) {
        // Verifica se o curso já está cadastrado
        Optional<Curso> cursoExistente = cursoRepository.findByNome(curso.getNome());
        if (cursoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Já existe um curso cadastrado com o mesmo nome.");
        }
        
        // Verifica se o professor informado tem a especialização necessária
        Optional<Professor> professor = professorRepository.findById(curso.getProfessor().getId());
        if (!professor.isPresent() || !professor.get().getEspecialidades().contains(curso.getEspecialidade())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O professor informado não tem a especialidade necessária para ministrar este curso.");
        }
        
        // Verifica se o professor já está ministrando outro curso na mesma data
        List<Curso> cursosDoProfessorNaData = cursoRepository.findByProfessorAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(professor.get(), curso.getDataFim(), curso.getDataInicio());
        if (!cursosDoProfessorNaData.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O professor informado já está ministrando outro curso na mesma data.");
        }
        
        // Cadastra o curso
        Curso cursoCadastrado = cursoRepository.save(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoCadastrado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);

        if (curso.isPresent()) {
            return ResponseEntity.ok(curso.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody Curso curso) {
        Optional<Curso> cursoExistente = cursoRepository.findById(id);

        if (cursoExistente.isPresent()) {
            Curso cursoAtualizado = cursoExistente.get();
            cursoAtualizado.setDescricao(curso.getDescricao());
            cursoAtualizado.setCargaHoraria(curso.getCargaHoraria());
            cursoAtualizado.setObjetivos(curso.getObjetivos());
            cursoAtualizado.setEmenta(curso.getEmenta());

            cursoRepository.save(cursoAtualizado);

            return ResponseEntity.ok(cursoAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCurso(@PathVariable Long id) {
        Optional<Curso> curso = cursoRepository.findById(id);

        if (curso.isPresent()) {
            cursoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
