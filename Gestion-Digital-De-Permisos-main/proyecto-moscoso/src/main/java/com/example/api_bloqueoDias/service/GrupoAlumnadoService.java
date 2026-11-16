package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.GrupoAlumnado;
import com.example.api_bloqueoDias.repository.GrupoAlumnadoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class GrupoAlumnadoService {
 private final GrupoAlumnadoRepository repository;
 public GrupoAlumnadoService(GrupoAlumnadoRepository repository) { this.repository=repository; }
 public List<GrupoAlumnado> listar() { return repository.findAll(); }
 public GrupoAlumnado obtener(String id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("GrupoAlumnado no encontrado: "+id)); }
 public GrupoAlumnado crear(GrupoAlumnado entidad) { return repository.save(entidad); }
 public GrupoAlumnado actualizar(String id, GrupoAlumnado datos) {
  GrupoAlumnado actual=obtener(id);
  copiar(actual, datos);
  return repository.save(actual);
 }
 public void eliminar(String id) { if(!repository.existsById(id)) throw new EntityNotFoundException("GrupoAlumnado no encontrado: "+id); repository.deleteById(id); }
 private void copiar(GrupoAlumnado a, GrupoAlumnado d) {
  if(d.getProfesor()!=null) a.setProfesor(d.getProfesor());
 }
}
