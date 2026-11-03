# Gestión Digital de Permisos - API REST

Proyecto Spring Boot 4, Java 17, Maven, Spring Data JPA, MySQL, Lombok y Swagger/OpenAPI.

## Base de datos

1. Instala e inicia MySQL.
2. Ejecuta `database/gestion_digital_permisos.sql` desde MySQL Workbench o phpMyAdmin.
3. Si tu usuario o contraseña no son `root` y vacía, configura las variables `DB_USERNAME` y `DB_PASSWORD`, o modifica `src/main/resources/application.properties`.

La aplicación también puede crear la base de datos si el usuario MySQL tiene permisos, por la opción `createDatabaseIfNotExist=true`.

## Ejecutar

Desde la carpeta `proyecto-moscoso`:

```bash
./mvnw spring-boot:run
```

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html

## Endpoints

- `/api/bloqueos-calendario`
- `/api/profesores`
- `/api/grupos-alumnado`
- `/api/sesiones-horario`
- `/api/moscosos-solicitudes`
- `/api/consultas/sesiones-por-dia` (consulta SQL nativa mediante EntityManager)

Cada recurso CRUD dispone de GET de colección, GET por ID, POST, PUT por ID y DELETE por ID.

## Relaciones modeladas a partir del diagrama

- Un profesor puede estar asociado a varios grupos.
- Un grupo puede tener varias sesiones de horario.
- Un profesor puede impartir varias sesiones.
- Un profesor puede registrar varias solicitudes de moscoso.

## Decisiones pendientes de confirmar del diagrama

- `CONFIGURACION` aparece sin atributos ni relaciones legibles. No se crea una tabla ficticia hasta conocer sus campos.
- El diagrama no muestra identificadores independientes para PROFESOR, SESION HORARIO ni MOSCOSOS SOLICITUDES. Se han añadido claves autoincrementales para permitir referencias estables; `dni` queda como único y `cod_grupo` como clave primaria natural.
- Se interpreta `tipo lectivo / no lectivo` como el campo `tipoLectivo` con valores `LECTIVO` o `NO LECTIVO`.
- Los tipos SQL se han inferido de los nombres y uso de los atributos; deben validarse con los requisitos funcionales del centro.
