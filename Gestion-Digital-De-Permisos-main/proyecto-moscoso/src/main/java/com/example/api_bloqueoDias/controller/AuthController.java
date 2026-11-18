package com.example.api_bloqueoDias.controller;
import com.example.api_bloqueoDias.dto.LoginRequestDto;
import com.example.api_bloqueoDias.dto.LoginResponseDto;
import com.example.api_bloqueoDias.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
 private final AuthService authService;
 public AuthController(AuthService authService) { this.authService = authService; }

 @Operation(summary = "Obtiene un token JWT", security = {})
 @PostMapping("/login")
 public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
  return ResponseEntity.ok(authService.login(dto));
 }
}
