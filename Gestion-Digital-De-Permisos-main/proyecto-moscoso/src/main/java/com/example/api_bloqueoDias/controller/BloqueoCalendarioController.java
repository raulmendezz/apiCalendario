package com.example.api_bloqueoDias.controller;

import com.example.api_bloqueoDias.model.BloqueoCalendario;
import com.example.api_bloqueoDias.service.BloqueoCalendarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bloqueos-calendario")
public class BloqueoCalendarioController {
    private final BloqueoCalendarioService service;

    public BloqueoCalendarioController(BloqueoCalendarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BloqueoCalendario>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloqueoCalendario> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<BloqueoCalendario> crear(@Valid @RequestBody BloqueoCalendario entidad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(entidad));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloqueoCalendario> actualizar(@PathVariable Long id, @Valid @RequestBody BloqueoCalendario entidad) {
        return ResponseEntity.ok(service.actualizar(id, entidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
