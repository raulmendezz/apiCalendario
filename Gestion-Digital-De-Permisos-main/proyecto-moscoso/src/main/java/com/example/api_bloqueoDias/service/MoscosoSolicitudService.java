package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import com.example.api_bloqueoDias.repository.MoscosoSolicitudRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class MoscosoSolicitudService {
 private final MoscosoSolicitudRepository repository;
 public MoscosoSolicitudService(MoscosoSolicitudRepository repository) { this.repository=repository; }
 public List<MoscosoSolicitud> listar() { return repository.findAll(); }
 public MoscosoSolicitud obtener(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("MoscosoSolicitud no encontrado: "+id)); }
 public MoscosoSolicitud crear(MoscosoSolicitud entidad) { return repository.save(entidad); }
 public MoscosoSolicitud actualizar(Long id, MoscosoSolicitud datos) {
  MoscosoSolicitud actual=obtener(id);
  copiar(actual, datos);
  return repository.save(actual);
 }
 public void eliminar(Long id) { if(!repository.existsById(id)) throw new EntityNotFoundException("MoscosoSolicitud no encontrado: "+id); repository.deleteById(id); }
 private void copiar(MoscosoSolicitud a, MoscosoSolicitud d) {
  if(d.getProfesor()!=null) a.setProfesor(d.getProfesor());
  if(d.getFecha()!=null) a.setFecha(d.getFecha());
  if(d.getEstado()!=null) a.setEstado(d.getEstado());
  if(d.getTipoLectivo()!=null) a.setTipoLectivo(d.getTipoLectivo());
 }
}
