package com.example.api_bloqueoDias.dto;

public record MeDto(String username, String rol, ProfesorDto profesor) {
 public record ProfesorDto(Long idProfesor, String nombre, String apellidos, String departamento,
                           String especialidad, String telefono) {}
}
