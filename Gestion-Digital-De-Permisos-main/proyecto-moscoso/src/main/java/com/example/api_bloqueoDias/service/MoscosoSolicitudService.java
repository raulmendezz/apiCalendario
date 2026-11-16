package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.exception.ReglaNegocioException;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import com.example.api_bloqueoDias.repository.BloqueoCalendarioRepository;
import com.example.api_bloqueoDias.repository.MoscosoSolicitudRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MoscosoSolicitudService {
 private final MoscosoSolicitudRepository repository;
 private final BloqueoCalendarioRepository bloqueoRepository;
 public MoscosoSolicitudService(MoscosoSolicitudRepository repository, BloqueoCalendarioRepository bloqueoRepository) {
  this.repository=repository; this.bloqueoRepository=bloqueoRepository;
 }
 public List<MoscosoSolicitud> listar() { return repository.findAll(); }
 public MoscosoSolicitud obtener(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("MoscosoSolicitud no encontrado: "+id)); }
 public MoscosoSolicitud crear(MoscosoSolicitud entidad) { validar(entidad, null); return repository.save(entidad); }
 public MoscosoSolicitud actualizar(Long id, MoscosoSolicitud datos) {
  MoscosoSolicitud actual=obtener(id);
  copiar(actual, datos);
  validar(actual, id);
  return repository.save(actual);
 }
 public void eliminar(Long id) { if(!repository.existsById(id)) throw new EntityNotFoundException("MoscosoSolicitud no encontrado: "+id); repository.deleteById(id); }
 private void validar(MoscosoSolicitud e, Long idExcluir) {
  // 3.1: fecha bloqueada en el calendario
  if(e.getFecha()!=null && bloqueoRepository.existsByFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(e.getFecha(), e.getFecha()))
   throw new ReglaNegocioException("La fecha "+e.getFecha()+" está bloqueada en el calendario.");
  // 3.2: mismo profesor, misma fecha
  if(e.getProfesor()!=null && e.getFecha()!=null) {
   boolean duplicado = idExcluir==null
    ? repository.existsByProfesorIdProfesorAndFecha(e.getProfesor().getIdProfesor(), e.getFecha())
    : repository.existsByProfesorIdProfesorAndFechaAndIdSolicitudNot(e.getProfesor().getIdProfesor(), e.getFecha(), idExcluir);
   if(duplicado) throw new ReglaNegocioException("El profesor ya tiene una solicitud para la fecha "+e.getFecha()+".");
  }
 }
 private void copiar(MoscosoSolicitud a, MoscosoSolicitud d) {
  if(d.getProfesor()!=null) a.setProfesor(d.getProfesor());
  if(d.getFecha()!=null) a.setFecha(d.getFecha());
  if(d.getEstado()!=null) a.setEstado(d.getEstado());
  if(d.getTipoLectivo()!=null) a.setTipoLectivo(d.getTipoLectivo());
 }
}
