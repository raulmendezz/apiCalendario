# Modelo de Datos

## Diagrama de entidades

```
PROFESOR
  idProfesor (PK)  nombre  apellidos  dni  cp  localidad
  telefono  direccion  cuerpo  especialidad  departamento

GRUPO_ALUMNADO
  cod_grupo (PK)  id_profesor (FK → PROFESOR)

SESION_HORARIO
  idSesion (PK)  cod_grupo (FK)  id_profesor (FK)
  hora_inicio  hora_fin  dia_semana (enum)

MOSCOSO_SOLICITUD
  idSolicitud (PK)  id_profesor (FK)
  fecha  estado (enum)  tipo_lectivo (enum)

BLOQUEO_CALENDARIO
  id (PK)  fecha_inicio  fecha_fin  tipo

CONFIGURACION
  clave (PK)  valor
```

## Enumerados

### `EstadoSolicitud`
| Valor | Descripción |
|---|---|
| `PENDIENTE` | Recién creada, pendiente de resolución |
| `APROBADA` | Aprobada por un administrador |
| `RECHAZADA` | Rechazada por un administrador |

### `TipoLectivo`
| Valor | Descripción |
|---|---|
| `LECTIVO` | Día lectivo del calendario escolar |
| `NO_LECTIVO` | Día no lectivo (guardia, sustitución…) |

### `DiaSemana`
`LUNES`, `MARTES`, `MIERCOLES`, `JUEVES`, `VIERNES`

## Reglas de negocio implementadas

| Regla | Entidad | Descripción |
|---|---|---|
| 3.1 | MoscosoSolicitud | No se puede crear solicitud en fecha bloqueada en calendario |
| 3.2 | MoscosoSolicitud | Un profesor no puede tener dos solicitudes el mismo día |
| 3.4 | SesionHorario | Un profesor no puede tener dos sesiones solapadas el mismo día |
| 3.5 | MoscosoSolicitud | No se puede aprobar si ya se alcanzó la capacidad diaria |
| 3.6 | MoscosoSolicitud | Solo PENDIENTE → APROBADA/RECHAZADA (vía PATCH /estado) |
