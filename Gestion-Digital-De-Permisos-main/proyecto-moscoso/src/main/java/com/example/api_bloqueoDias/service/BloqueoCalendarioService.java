package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.BloqueoCalendario;
import com.example.api_bloqueoDias.repository.BloqueoCalendarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BloqueoCalendarioService {
 private final BloqueoCalendarioRepository repository;
 public BloqueoCalendarioService(BloqueoCalendarioRepository repository) { this.repository=repository; }
 public List<BloqueoCalendario> listar() { return repository.findAll(); }
 public BloqueoCalendario obtener(Long id) { return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("BloqueoCalendario no encontrado: "+id)); }
 public BloqueoCalendario crear(BloqueoCalendario entidad) { validar(entidad); return repository.save(entidad); }
 public BloqueoCalendario actualizar(Long id, BloqueoCalendario datos) {
  BloqueoCalendario actual=obtener(id);
  copiar(actual, datos);
  validar(actual);
  return repository.save(actual);
 }
 public void eliminar(Long id) { if(!repository.existsById(id)) throw new EntityNotFoundException("BloqueoCalendario no encontrado: "+id); repository.deleteById(id); }
 private void validar(BloqueoCalendario e) {
  if(e.getFechaInicio()!=null && e.getFechaFin()!=null && e.getFechaInicio().isAfter(e.getFechaFin())) throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin.");


 }
 private void copiar(BloqueoCalendario a, BloqueoCalendario d) {
  if(d.getFechaInicio()!=null) a.setFechaInicio(d.getFechaInicio());
  if(d.getFechaFin()!=null) a.setFechaFin(d.getFechaFin());
  if(d.getTipo()!=null) a.setTipo(d.getTipo());
 }
}
