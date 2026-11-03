package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.repository.SesionHorarioConsultaRepository;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class SesionHorarioConsultaService {
 private final SesionHorarioConsultaRepository repository;
 public SesionHorarioConsultaService(SesionHorarioConsultaRepository repository) { this.repository=repository; }
 public List<Map<String,Object>> resumenPorDia() {
  List<Map<String,Object>> resultado=new ArrayList<>();
  for(Object[] fila: repository.contarPorDia()) {
   Map<String,Object> item=new LinkedHashMap<>(); item.put("diaSemana",fila[0]); item.put("totalSesiones",fila[1]); resultado.add(item);
  }
  return resultado;
 }
}
