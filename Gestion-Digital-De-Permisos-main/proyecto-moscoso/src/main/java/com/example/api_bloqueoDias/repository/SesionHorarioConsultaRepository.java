package com.example.api_bloqueoDias.repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public class SesionHorarioConsultaRepository {
 @PersistenceContext private EntityManager entityManager;
 public List<Object[]> contarPorDia() {
  return entityManager.createNativeQuery("SELECT dia_semana, COUNT(*) AS total FROM sesiones_horario GROUP BY dia_semana ORDER BY FIELD(dia_semana,'LUNES','MARTES','MIERCOLES','JUEVES','VIERNES','SABADO','DOMINGO')").getResultList();
 }
}
