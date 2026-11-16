package com.example.api_bloqueoDias.repository;
import com.example.api_bloqueoDias.model.BloqueoCalendario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
public interface BloqueoCalendarioRepository extends JpaRepository<BloqueoCalendario, Long> {
 boolean existsByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(LocalDate fechaFin, LocalDate fechaInicio);
}
