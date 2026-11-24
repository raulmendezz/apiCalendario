package com.example.api_bloqueoDias.controller;

import com.example.api_bloqueoDias.dto.CambioEstadoDto;
import com.example.api_bloqueoDias.model.MoscosoSolicitud;
import com.example.api_bloqueoDias.service.MoscosoSolicitudService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moscosos-solicitudes")
public class MoscosoSolicitudController {
    private final MoscosoSolicitudService service;

    public MoscosoSolicitudController(MoscosoSolicitudService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MoscosoSolicitud>> listar(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.listar(jwt));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MoscosoSolicitud> obtener(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.obtener(id, jwt));
    }

    @PostMapping
    public ResponseEntity<MoscosoSolicitud> crear(@Valid @RequestBody MoscosoSolicitud entidad, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(entidad, jwt));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MoscosoSolicitud> actualizar(@PathVariable Long id, @Valid @RequestBody MoscosoSolicitud entidad,
                                                       @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(service.actualizar(id, entidad, jwt));
    }

    // Solo ADMIN (regla en SecurityConfig)
    @PatchMapping("/{id}/estado")
    public ResponseEntity<MoscosoSolicitud> cambiarEstado(@PathVariable Long id, @Valid @RequestBody CambioEstadoDto dto) {
        return ResponseEntity.ok(service.cambiarEstado(id, dto.estado()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        service.eliminar(id, jwt);
        return ResponseEntity.noContent().build();
    }
}
