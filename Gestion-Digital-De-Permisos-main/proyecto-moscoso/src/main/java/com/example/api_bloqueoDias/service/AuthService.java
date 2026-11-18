package com.example.api_bloqueoDias.service;
import com.example.api_bloqueoDias.dto.LoginRequestDto;
import com.example.api_bloqueoDias.dto.LoginResponseDto;
import com.example.api_bloqueoDias.exception.CredencialesInvalidasException;
import com.example.api_bloqueoDias.repository.UsuarioRepository;
import com.example.api_bloqueoDias.security.JwtTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.api_bloqueoDias.model.Usuario;

@Service
public class AuthService {
 // Hash de relleno para evitar timing attacks cuando el usuario no existe
 private static final String DUMMY_HASH = "$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";

 private final UsuarioRepository repo;
 private final PasswordEncoder encoder;
 private final JwtTokenService jwtService;

 public AuthService(UsuarioRepository repo, PasswordEncoder encoder, JwtTokenService jwtService) {
  this.repo = repo; this.encoder = encoder; this.jwtService = jwtService;
 }

 public LoginResponseDto login(LoginRequestDto dto) {
  Optional<Usuario> opt = repo.findByUsername(dto.username());
  if (opt.isEmpty()) {
   encoder.matches(dto.password(), DUMMY_HASH);
   throw new CredencialesInvalidasException("Credenciales inválidas.");
  }
  Usuario u = opt.get();
  if (!encoder.matches(dto.password(), u.getPassword())) {
   throw new CredencialesInvalidasException("Credenciales inválidas.");
  }
  String token = jwtService.generarToken(u);
  return new LoginResponseDto(token, "Bearer", jwtService.getExpiracionSegundos(), u.getUsername(), u.getRol().name());
 }
}
