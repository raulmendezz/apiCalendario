package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.Profesor;
import com.example.api_bloqueoDias.repository.ProfesorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ProfesorService {
 private final ProfesorRepository repository;
 public ProfesorService(ProfesorRepository repository) { this.repository=repository; }
 public List<Profesor> listar() { return repository.findAll(); }
 public Profesor obtener(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Profesor no encontrado: "+id)); }
 public Profesor crear(Profesor entidad) { validar(entidad); return repository.save(entidad); }
 public Profesor actualizar(Long id, Profesor datos) {
  Profesor actual=obtener(id);
  copiar(actual, datos);
  validar(actual);
  return repository.save(actual);
 }
 public void eliminar(Long id) { if(!repository.existsById(id)) throw new EntityNotFoundException("Profesor no encontrado: "+id); repository.deleteById(id); }
 private void validar(Profesor e) {



 }
 private void copiar(Profesor a, Profesor d) {
  if(d.getNombre()!=null) a.setNombre(d.getNombre());
  if(d.getApellidos()!=null) a.setApellidos(d.getApellidos());
  if(d.getDni()!=null) a.setDni(d.getDni());
  if(d.getCp()!=null) a.setCp(d.getCp());
  if(d.getLocalidad()!=null) a.setLocalidad(d.getLocalidad());
  if(d.getTelefono()!=null) a.setTelefono(d.getTelefono());
  if(d.getDireccion()!=null) a.setDireccion(d.getDireccion());
  if(d.getCuerpo()!=null) a.setCuerpo(d.getCuerpo());
  if(d.getEspecialidad()!=null) a.setEspecialidad(d.getEspecialidad());
  if(d.getDepartamento()!=null) a.setDepartamento(d.getDepartamento());
 }
}
