package com.example.api_bloqueoDias.config;
import com.example.api_bloqueoDias.model.Profesor;
import com.example.api_bloqueoDias.model.Rol;
import com.example.api_bloqueoDias.repository.ProfesorRepository;
import com.example.api_bloqueoDias.model.Usuario;
import com.example.api_bloqueoDias.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder {
 private final UsuarioRepository repo;
 private final ProfesorRepository profesorRepo;
 private final PasswordEncoder encoder;

 @Value("${app.security.admin1-username:admin}") private String adminUsername;
 @Value("${app.security.admin1-password}") private String adminPassword;
 @Value("${app.security.user1-username:user}") private String userUsername;
 @Value("${app.security.user1-password}") private String userPassword;

 public DataSeeder(UsuarioRepository repo, ProfesorRepository profesorRepo, PasswordEncoder encoder) {
  this.profesorRepo = profesorRepo; this.repo = repo; this.encoder = encoder;
 }

 @PostConstruct
 void seed() {
  sembrar(adminUsername, adminPassword, Rol.ADMIN);
  sembrar(userUsername, userPassword, Rol.PROFESOR);
  enlazarProfesor(userUsername);
 }

 // Deja el usuario enlazado al profesor 1 (lo crea con datos de prueba si no existe)
 private void enlazarProfesor(String username) {
  Usuario u = repo.findByUsername(username).orElseThrow();
  if (u.getProfesor() != null) return;
  Profesor p = profesorRepo.findById(1L).orElseGet(() -> {
   Profesor nuevo = new Profesor();
   nuevo.setNombre("Raul"); nuevo.setApellidos("Martinez Lopez"); nuevo.setDni("00000001R");
   nuevo.setCp("28001"); nuevo.setLocalidad("Madrid"); nuevo.setTelefono("600123456");
   nuevo.setDireccion("Calle Falsa 1"); nuevo.setCuerpo("Profesores de Secundaria");
   nuevo.setEspecialidad("Informatica"); nuevo.setDepartamento("Tecnologia");
   return profesorRepo.save(nuevo);
  });
  u.setProfesor(p);
  repo.save(u);
 }

 private void sembrar(String username, String rawOrHash, Rol rol) {
  if (repo.existsByUsername(username)) return;
  String hash = rawOrHash.startsWith("$2") ? rawOrHash : encoder.encode(rawOrHash);
  Usuario u = new Usuario(); u.setUsername(username); u.setPassword(hash); u.setRol(rol);
  repo.save(u);
 }
}
