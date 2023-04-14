import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ac1p2.model.Curso;
import com.example.ac1p2.model.Especializacao;
import com.example.ac1p2.model.Professor;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    
    Optional<Professor> findByNome(String nome);
    
    List<Professor> findByEspecialidadesContaining(Especializacao especialidade);
    
    List<Curso> findByCursosAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(List<Curso> cursos, LocalDate dataFim, LocalDate dataInicio);
    
}
