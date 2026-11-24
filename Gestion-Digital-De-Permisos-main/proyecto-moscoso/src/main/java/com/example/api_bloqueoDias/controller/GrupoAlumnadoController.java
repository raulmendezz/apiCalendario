package com.example.api_bloqueoDias.controller;

import com.example.api_bloqueoDias.model.GrupoAlumnado;
import com.example.api_bloqueoDias.service.GrupoAlumnadoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos-alumnado")
public class GrupoAlumnadoController {
    private final GrupoAlumnadoService service;

    public GrupoAlumnadoController(GrupoAlumnadoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GrupoAlumnado>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoAlumnado> obtener(@PathVariable String id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<GrupoAlumnado> crear(@Valid @RequestBody GrupoAlumnado entidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(entidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoAlumnado> actualizar(@PathVariable String id, @Valid @RequestBody GrupoAlumnado entidad) {
        return ResponseEntity.ok(service.actualizar(id, entidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
