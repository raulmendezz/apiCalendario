package com.example.api_bloqueoDias.controller;
import com.example.api_bloqueoDias.model.SesionHorario;
import com.example.api_bloqueoDias.service.SesionHorarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/sesiones-horario")
public class SesionHorarioController {
 private final SesionHorarioService service;
 public SesionHorarioController(SesionHorarioService service) { this.service=service; }
 @GetMapping public ResponseEntity<List<SesionHorario>> listar() { return ResponseEntity.ok(service.listar()); }
 @GetMapping("/{id}") public ResponseEntity<SesionHorario> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }
 @PostMapping public ResponseEntity<SesionHorario> crear(@Valid @RequestBody SesionHorario entidad) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(entidad)); }
 @PutMapping("/{id}") public ResponseEntity<SesionHorario> actualizar(@PathVariable Long id, @Valid @RequestBody SesionHorario entidad) { return ResponseEntity.ok(service.actualizar(id, entidad)); }
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
