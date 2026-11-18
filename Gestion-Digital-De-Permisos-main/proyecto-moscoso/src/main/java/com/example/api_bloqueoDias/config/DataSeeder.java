package com.example.api_bloqueoDias.config;
import com.example.api_bloqueoDias.model.Rol;
import com.example.api_bloqueoDias.model.Usuario;
import com.example.api_bloqueoDias.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
 private final UsuarioRepository repo;
 private final PasswordEncoder encoder;

 @Value("${app.security.admin1-username:admin}") private String adminUsername;
 @Value("${app.security.admin1-password}") private String adminPassword;
 @Value("${app.security.user1-username:user}") private String userUsername;
 @Value("${app.security.user1-password}") private String userPassword;

 public DataSeeder(UsuarioRepository repo, PasswordEncoder encoder) {
  this.repo = repo; this.encoder = encoder;
 }

 @PostConstruct
 void seed() {
  sembrar(adminUsername, adminPassword, Rol.ADMIN);
  sembrar(userUsername, userPassword, Rol.PROFESOR);
 }

 private void sembrar(String username, String rawOrHash, Rol rol) {
  if (repo.existsByUsername(username)) return;
  String hash = rawOrHash.startsWith("$2") ? rawOrHash : encoder.encode(rawOrHash);
  Usuario u = new Usuario(); u.setUsername(username); u.setPassword(hash); u.setRol(rol);
  repo.save(u);
 }
}
