package com.example.api_bloqueoDias.controller;
import com.example.api_bloqueoDias.model.Configuracion;
import com.example.api_bloqueoDias.service.ConfiguracionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/configuracion")
public class ConfiguracionController {
 private final ConfiguracionService service;
 public ConfiguracionController(ConfiguracionService service) { this.service=service; }
 @GetMapping public ResponseEntity<List<Configuracion>> listar() { return ResponseEntity.ok(service.listar()); }
 @GetMapping("/{clave}") public ResponseEntity<Configuracion> obtener(@PathVariable String clave) { return ResponseEntity.ok(service.obtener(clave)); }
 @PutMapping("/{clave}") public ResponseEntity<Configuracion> guardar(@PathVariable String clave, @Valid @RequestBody Configuracion entidad) { entidad.setClave(clave); return ResponseEntity.ok(service.guardar(entidad)); }
}
