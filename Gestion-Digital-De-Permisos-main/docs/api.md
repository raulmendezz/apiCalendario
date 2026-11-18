# Endpoints de la API

Base URL: `http://localhost:8080`

> **Seguridad (Sprint 3):** todos los endpoints requieren JWT excepto `POST /api/auth/login`. Envía el token como `Authorization: Bearer <token>`.

---

## Autenticación `/api/auth`

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| POST | `/api/auth/login` | Obtiene un token JWT | Público |

### POST /api/auth/login — body
```json
{ "username": "admin", "password": "miPassword" }
```

### POST /api/auth/login — respuesta 200
```json
{
  "token": "eyJhbGc...",
  "tipo": "Bearer",
  "expiraEnSegundos": 3600,
  "username": "admin",
  "rol": "ADMIN"
}
```

Credenciales incorrectas → `401 Unauthorized` con formato `ApiError`.

---

## Profesores `/api/profesores`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/profesores` | Lista todos los profesores (id, nombre, apellidos, departamento, especialidad) | ADMIN |
| GET | `/api/profesores/{id}` | Obtiene un profesor por id | ADMIN |
| POST | `/api/profesores` | Crea un profesor | ADMIN |
| PUT | `/api/profesores/{id}` | Actualiza un profesor | ADMIN |
| DELETE | `/api/profesores/{id}` | Elimina un profesor | ADMIN |

---

## Grupos de alumnado `/api/grupos-alumnado`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/grupos-alumnado` | Lista todos los grupos | ADMIN |
| GET | `/api/grupos-alumnado/{codGrupo}` | Obtiene un grupo | ADMIN |
| POST | `/api/grupos-alumnado` | Crea un grupo | ADMIN |
| PUT | `/api/grupos-alumnado/{codGrupo}` | Actualiza un grupo | ADMIN |
| DELETE | `/api/grupos-alumnado/{codGrupo}` | Elimina un grupo | ADMIN |

---

## Sesiones de horario `/api/sesiones-horario`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/sesiones-horario` | Lista todas las sesiones | ADMIN |
| GET | `/api/sesiones-horario/{id}` | Obtiene una sesión | ADMIN |
| POST | `/api/sesiones-horario` | Crea una sesión (valida solapes) | ADMIN |
| PUT | `/api/sesiones-horario/{id}` | Actualiza una sesión | ADMIN |
| DELETE | `/api/sesiones-horario/{id}` | Elimina una sesión | ADMIN |

**Regla 3.4:** no se permite crear/actualizar una sesión si el profesor ya tiene otra sesión solapada ese día y horario (devuelve `409 Conflict`).

---

## Solicitudes de moscoso `/api/moscosos-solicitudes`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/moscosos-solicitudes` | Lista todas las solicitudes | ADMIN |
| GET | `/api/moscosos-solicitudes/{id}` | Obtiene una solicitud | ADMIN, PROFESOR (propia) |
| POST | `/api/moscosos-solicitudes` | Crea solicitud (estado siempre `PENDIENTE`) | PROFESOR |
| PUT | `/api/moscosos-solicitudes/{id}` | Actualiza datos de una solicitud | PROFESOR (propia) |
| **PATCH** | `/api/moscosos-solicitudes/{id}/estado` | Aprueba o rechaza una solicitud | **ADMIN** |
| DELETE | `/api/moscosos-solicitudes/{id}` | Elimina una solicitud | ADMIN |

### PATCH estado — body

```json
{ "estado": "APROBADA" }
```

Valores válidos: `APROBADA`, `RECHAZADA`.  
Solo se puede resolver una solicitud en estado `PENDIENTE`.

**Regla 3.1:** fecha bloqueada en calendario → `409 Conflict`.  
**Regla 3.2:** mismo profesor, misma fecha → `409 Conflict`.  
**Regla 3.5:** capacidad diaria superada al aprobar → `409 Conflict`.

---

## Bloqueos de calendario `/api/bloqueos-calendario`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/bloqueos-calendario` | Lista todos los bloqueos | ADMIN |
| POST | `/api/bloqueos-calendario` | Crea un bloqueo (tipo, fechaInicio, fechaFin) | ADMIN |
| PUT | `/api/bloqueos-calendario/{id}` | Actualiza un bloqueo | ADMIN |
| DELETE | `/api/bloqueos-calendario/{id}` | Elimina un bloqueo | ADMIN |

---

## Configuración `/api/configuracion`

| Método | Ruta | Descripción | Rol (Sprint 3) |
|---|---|---|---|
| GET | `/api/configuracion` | Lista todos los parámetros | ADMIN |
| GET | `/api/configuracion/{clave}` | Obtiene un parámetro | ADMIN |
| PUT | `/api/configuracion/{clave}` | Actualiza un parámetro | ADMIN |

Clave disponible: `capacidad_diaria` (valor por defecto: `5`).

---

## Consultas `/api/consultas`

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/consultas/sesiones-por-dia` | Número de sesiones agrupadas por día de la semana |

---

## Respuesta de error estándar

```json
{
  "timestamp": "2025-01-15T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "El profesor ya tiene una solicitud para la fecha 2025-03-10.",
  "path": "/api/moscosos-solicitudes"
}
```

| Código | Causa |
|---|---|
| 400 | Datos inválidos o JSON malformado |
| 401 | No autenticado o credenciales inválidas |
| 403 | Sin permisos para el recurso |
| 404 | Recurso no encontrado |
| 409 | Regla de negocio o integridad de datos |
| 500 | Error interno (ver logs) |
