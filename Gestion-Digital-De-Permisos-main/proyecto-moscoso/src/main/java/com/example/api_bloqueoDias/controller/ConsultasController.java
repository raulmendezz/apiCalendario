package com.example.api_bloqueoDias.controller;
import com.example.api_bloqueoDias.service.SesionHorarioConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {
 private final SesionHorarioConsultaService service;
 public ConsultasController(SesionHorarioConsultaService service) { this.service=service; }
 @GetMapping("/sesiones-por-dia")
 public ResponseEntity<List<Map<String,Object>>> sesionesPorDia() { return ResponseEntity.ok(service.resumenPorDia()); }
}
