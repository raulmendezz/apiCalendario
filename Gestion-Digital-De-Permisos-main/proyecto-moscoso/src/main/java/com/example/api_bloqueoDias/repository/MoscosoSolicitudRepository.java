package com.example.api_bloqueoDias.repository;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
public interface MoscosoSolicitudRepository extends JpaRepository<MoscosoSolicitud, Long> {
 boolean existsByProfesorIdProfesorAndFecha(Long idProfesor, LocalDate fecha);
 boolean existsByProfesorIdProfesorAndFechaAndIdSolicitudNot(Long idProfesor, LocalDate fecha, Long idSolicitud);
}
