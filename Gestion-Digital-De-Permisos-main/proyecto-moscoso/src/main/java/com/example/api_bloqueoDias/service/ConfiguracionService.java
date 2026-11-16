package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.model.Configuracion;
import com.example.api_bloqueoDias.repository.ConfiguracionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ConfiguracionService {
 static final String CAPACIDAD_DIARIA = "capacidad_diaria";
 private final ConfiguracionRepository repository;
 public ConfiguracionService(ConfiguracionRepository repository) { this.repository=repository; }

 @PostConstruct
 void inicializar() {
  if(!repository.existsById(CAPACIDAD_DIARIA)) {
   Configuracion c=new Configuracion(); c.setClave(CAPACIDAD_DIARIA); c.setValor("5");
   repository.save(c);
  }
 }

 public int getCapacidadDiaria() {
  return repository.findById(CAPACIDAD_DIARIA)
   .map(c -> Integer.parseInt(c.getValor()))
   .orElse(5);
 }

 public List<Configuracion> listar() { return repository.findAll(); }
 public Configuracion obtener(String clave) { return repository.findById(clave).orElseThrow(() -> new EntityNotFoundException("Configuracion no encontrada: "+clave)); }
 public Configuracion guardar(Configuracion entidad) { return repository.save(entidad); }
}
