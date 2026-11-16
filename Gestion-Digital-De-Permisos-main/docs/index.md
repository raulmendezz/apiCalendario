# Gestión Digital de Permisos

API REST desarrollada con **Spring Boot** para la gestión de permisos de moscoso y horarios en centros educativos.

## Tecnologías

| Componente | Versión |
|---|---|
| Java | 17 |
| Spring Boot | 4.1.2 |
| Base de datos | MySQL 8 |
| Documentación interactiva | Swagger UI (`/swagger-ui.html`) |

## Cómo ejecutar en local

**Requisitos:** Java 17, MySQL 8 en ejecución.

```bash
# 1. Clona el repositorio
git clone https://github.com/raulmendezz/apiCalendario.git
cd apiCalendario/Gestion-Digital-De-Permisos-main/proyecto-moscoso

# 2. Configura las variables de entorno (ver sección Variables de Entorno)
# 3. Arranca la aplicación
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`.  
Swagger UI: `http://localhost:8080/swagger-ui.html`

## Decisiones de diseño

- **Turnos**: concepto descartado en Sprint 2; no aplica a este dominio.
- **Estado de solicitud**: siempre se crea como `PENDIENTE`. La aprobación o rechazo se realiza mediante `PATCH /api/moscosos-solicitudes/{id}/estado`.
- **Capacidad diaria**: configurable en la tabla `CONFIGURACION` con la clave `capacidad_diaria` (valor por defecto: 5).
- **USUARIO / Seguridad**: planificado para Sprint 3 (autenticación JWT).
