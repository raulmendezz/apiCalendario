package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.SesionHorario;
import com.example.api_bloqueoDias.repository.SesionHorarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SesionHorarioService {
 private final SesionHorarioRepository repository;
 public SesionHorarioService(SesionHorarioRepository repository) { this.repository=repository; }
 public List<SesionHorario> listar() { return repository.findAll(); }
 public SesionHorario obtener(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("SesionHorario no encontrado: "+id)); }
 public SesionHorario crear(SesionHorario entidad) { validar(entidad); return repository.save(entidad); }
 public SesionHorario actualizar(Long id, SesionHorario datos) {
  SesionHorario actual=obtener(id);
  copiar(actual, datos);
  validar(actual);
  return repository.save(actual);
 }
 public void eliminar(Long id) { if(!repository.existsById(id)) throw new EntityNotFoundException("SesionHorario no encontrado: "+id); repository.deleteById(id); }
 private void validar(SesionHorario e) {
  if(e.getHoraInicio()!=null && e.getHoraFin()!=null && !e.getHoraInicio().isBefore(e.getHoraFin())) throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora de fin.");
 }
 private void copiar(SesionHorario a, SesionHorario d) {
  if(d.getGrupo()!=null) a.setGrupo(d.getGrupo());
  if(d.getProfesor()!=null) a.setProfesor(d.getProfesor());
  if(d.getHoraInicio()!=null) a.setHoraInicio(d.getHoraInicio());
  if(d.getHoraFin()!=null) a.setHoraFin(d.getHoraFin());
  if(d.getDiaSemana()!=null) a.setDiaSemana(d.getDiaSemana());
 }
}
