package com.example.api_bloqueoDias.security;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
 @Value("${app.security.jwt-secret}") private String jwtSecret;

 @Bean public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

 @Bean
 public JwtDecoder jwtDecoder() {
  return NimbusJwtDecoder.withSecretKey(
   new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256")
  ).build();
 }

 @Bean
 public JwtEncoder jwtEncoder() {
  return new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecret.getBytes(StandardCharsets.UTF_8)));
 }

 @Bean
 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
  JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
  jwtConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
   List<String> roles = jwt.getClaimAsStringList("roles");
   if (roles == null) return List.of();
   return roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
  });

  http
   .csrf(c -> c.disable())
   .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/auth/login", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/error")
     .permitAll()
    .anyRequest().authenticated()
   )
   .oauth2ResourceServer(oauth2 -> oauth2
    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtConverter))
    .authenticationEntryPoint((req, res, ex) -> jsonError(res, 401, "Unauthorized",
     "No autenticado. Incluye un token Bearer válido.", req.getRequestURI()))
    .accessDeniedHandler((req, res, ex) -> jsonError(res, 403, "Forbidden",
     "No tienes permisos para acceder a este recurso.", req.getRequestURI()))
   );

  return http.build();
 }

 private void jsonError(jakarta.servlet.http.HttpServletResponse res, int status, String error, String message, String path) throws java.io.IOException {
  res.setStatus(status);
  res.setContentType(MediaType.APPLICATION_JSON_VALUE);
  res.setCharacterEncoding("UTF-8");
  res.getWriter().write(String.format(
   "{\"timestamp\":\"%s\",\"status\":%d,\"error\":\"%s\",\"message\":\"%s\",\"path\":\"%s\"}",
   LocalDateTime.now(), status, error, message, path));
 }
}
