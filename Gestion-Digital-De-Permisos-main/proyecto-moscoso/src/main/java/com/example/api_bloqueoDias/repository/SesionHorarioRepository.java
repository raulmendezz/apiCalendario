package com.example.api_bloqueoDias.repository;
import com.example.api_bloqueoDias.model.DiaSemana;
import com.example.api_bloqueoDias.model.SesionHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalTime;
public interface SesionHorarioRepository extends JpaRepository<SesionHorario, Long> {
 @Query("""
  select count(s) > 0 from SesionHorario s
  where s.profesor.idProfesor = :prof and s.diaSemana = :dia
  and s.horaInicio < :fin and s.horaFin > :ini
  and (:id is null or s.idSesion <> :id)""")
 boolean haySolape(@Param("prof") Long prof, @Param("dia") DiaSemana dia,
                   @Param("ini") LocalTime ini, @Param("fin") LocalTime fin,
                   @Param("id") Long id);
}
