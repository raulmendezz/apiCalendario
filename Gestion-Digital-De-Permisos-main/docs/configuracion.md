# Variables de Entorno

La aplicación no tiene credenciales hardcodeadas. Configura las siguientes variables antes de arrancar.

## Variables obligatorias

| Variable | Descripción | Ejemplo |
|---|---|---|
| `DB_URL` | URL JDBC de la base de datos | `jdbc:mysql://localhost:3306/gestion_digital_permisos?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=Europe/Madrid` |
| `DB_USERNAME` | Usuario de la base de datos | `root` |
| `DB_PASSWORD` | Contraseña de la base de datos | `miPassword123` |

## Variables de Sprint 3 (JWT)

| Variable | Descripción |
|---|---|
| `JWT_SECRET` | Clave secreta para firmar tokens JWT (mínimo 32 caracteres) |
| `ADMIN1_USERNAME` | Usuario del primer administrador |
| `ADMIN1_PASSWORD` | Contraseña del primer administrador (bcrypt) |

!!! warning "Nunca subas estos valores al repositorio"
    Usa un fichero `.env` o las variables de entorno del servidor. El `.gitignore` ya excluye `.env` y `application-local.properties`.

## Valores por defecto (solo para desarrollo local)

Si no se definen las variables de entorno, la aplicación usa estos valores de fallback definidos en `application.properties`:

```
DB_URL     → jdbc:mysql://localhost:3306/gestion_digital_permisos?...
DB_USERNAME → root
DB_PASSWORD → (vacío)
```

## Configuración dinámica (tabla CONFIGURACION)

Algunos parámetros se pueden ajustar en caliente sin reiniciar la aplicación:

| Clave | Valor por defecto | Descripción |
|---|---|---|
| `capacidad_diaria` | `5` | Número máximo de solicitudes aprobadas por día |

Modifica este valor con:
```
PUT /api/configuracion/capacidad_diaria
{ "clave": "capacidad_diaria", "valor": "10" }
```
