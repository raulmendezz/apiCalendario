package com.example.api_bloqueoDias.config;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
 @Bean
 public OpenAPI openAPI() {
  return new OpenAPI()
   .info(new Info().title("Gestión Digital de Permisos API").version("1.0.0")
    .description("API REST para la gestión de permisos y horarios (IES)"))
   .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
   .components(new Components().addSecuritySchemes("bearerAuth",
    new SecurityScheme()
     .type(SecurityScheme.Type.HTTP)
     .scheme("bearer")
     .bearerFormat("JWT")
     .description("Token JWT obtenido en POST /api/auth/login")));
 }
}
