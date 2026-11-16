package com.example.api_bloqueoDias.dto;

import com.example.api_bloqueoDias.model.Profesor;

public record ProfesorResumenDto(Long id, String nombre, String apellidos, String departamento, String especialidad) {
 public static ProfesorResumenDto from(Profesor p) {
  return new ProfesorResumenDto(p.getIdProfesor(), p.getNombre(), p.getApellidos(), p.getDepartamento(), p.getEspecialidad());
 }
}
