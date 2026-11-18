package com.example.api_bloqueoDias.security;
import com.example.api_bloqueoDias.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
public class JwtTokenService {
 private final JwtEncoder encoder;
 @Value("${app.security.jwt-expiracion-minutos:60}") private long expiracionMinutos;

 public JwtTokenService(JwtEncoder encoder) { this.encoder = encoder; }

 public String generarToken(Usuario usuario) {
  Instant now = Instant.now();
  JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
  JwtClaimsSet claims = JwtClaimsSet.builder()
   .subject(usuario.getUsername())
   .issuedAt(now)
   .expiresAt(now.plusSeconds(expiracionMinutos * 60))
   .claim("roles", List.of(usuario.getRol().name()))
   .build();
  return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
 }

 public long getExpiracionSegundos() { return expiracionMinutos * 60; }
}
