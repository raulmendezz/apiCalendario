package com.example.api_bloqueoDias.repository;
import com.example.api_bloqueoDias.model.EstadoSolicitud;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface MoscosoSolicitudRepository extends JpaRepository<MoscosoSolicitud, Long> {
 List<MoscosoSolicitud> findByProfesorIdProfesor(Long idProfesor);
 boolean existsByProfesorIdProfesorAndFecha(Long idProfesor, LocalDate fecha);
 boolean existsByProfesorIdProfesorAndFechaAndIdSolicitudNot(Long idProfesor, LocalDate fecha, Long idSolicitud);
 int countByFechaAndEstado(LocalDate fecha, EstadoSolicitud estado);
}
