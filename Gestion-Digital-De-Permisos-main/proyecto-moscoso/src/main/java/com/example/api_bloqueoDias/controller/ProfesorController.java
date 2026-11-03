package com.example.api_bloqueoDias.controller;
import com.example.api_bloqueoDias.model.Profesor;
import com.example.api_bloqueoDias.service.ProfesorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {
 private final ProfesorService service;
 public ProfesorController(ProfesorService service) { this.service=service; }
 @GetMapping public ResponseEntity<List<Profesor>> listar() { return ResponseEntity.ok(service.listar()); }
 @GetMapping("/{id}") public ResponseEntity<Profesor> obtener(@PathVariable Long id) { return ResponseEntity.ok(service.obtener(id)); }
 @PostMapping public ResponseEntity<Profesor> crear(@Valid @RequestBody Profesor entidad) { return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(entidad)); }
 @PutMapping("/{id}") public ResponseEntity<Profesor> actualizar(@PathVariable Long id, @Valid @RequestBody Profesor entidad) { return ResponseEntity.ok(service.actualizar(id, entidad)); }
 @DeleteMapping("/{id}") public ResponseEntity<Void> eliminar(@PathVariable Long id) { service.eliminar(id); return ResponseEntity.noContent().build(); }
}
