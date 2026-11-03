CREATE DATABASE IF NOT EXISTS gestion_digital_permisos CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE gestion_digital_permisos;

CREATE TABLE IF NOT EXISTS profesores (
 id_profesor BIGINT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(80) NOT NULL,
 apellidos VARCHAR(100) NOT NULL,
 dni VARCHAR(20) NOT NULL UNIQUE,
 cp VARCHAR(10) NOT NULL,
 localidad VARCHAR(100) NOT NULL,
 telefono VARCHAR(20) NOT NULL,
 direccion VARCHAR(160) NOT NULL,
 cuerpo VARCHAR(80) NOT NULL,
 especialidad VARCHAR(100) NOT NULL,
 departamento VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS grupos_alumnado (
 cod_grupo VARCHAR(30) PRIMARY KEY,
 id_profesor BIGINT NOT NULL,
 CONSTRAINT fk_grupo_profesor FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor)
);

CREATE TABLE IF NOT EXISTS bloqueos_calendario (
 id BIGINT AUTO_INCREMENT PRIMARY KEY,
 fecha_inicio DATE NOT NULL,
 fecha_fin DATE NOT NULL,
 tipo VARCHAR(60) NOT NULL,
 CONSTRAINT chk_fechas_bloqueo CHECK (fecha_inicio <= fecha_fin)
);

CREATE TABLE IF NOT EXISTS sesiones_horario (
 id_sesion BIGINT AUTO_INCREMENT PRIMARY KEY,
 cod_grupo VARCHAR(30) NOT NULL,
 id_profesor BIGINT NOT NULL,
 hora_inicio TIME NOT NULL,
 hora_fin TIME NOT NULL,
 dia_semana VARCHAR(15) NOT NULL,
 CONSTRAINT fk_sesion_grupo FOREIGN KEY (cod_grupo) REFERENCES grupos_alumnado(cod_grupo),
 CONSTRAINT fk_sesion_profesor FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),
 CONSTRAINT chk_horas_sesion CHECK (hora_inicio < hora_fin)
);

CREATE TABLE IF NOT EXISTS moscosos_solicitudes (
 id_solicitud BIGINT AUTO_INCREMENT PRIMARY KEY,
 id_profesor BIGINT NOT NULL,
 fecha DATE NOT NULL,
 estado VARCHAR(30) NOT NULL,
 tipo_lectivo VARCHAR(20) NOT NULL,
 CONSTRAINT fk_moscoso_profesor FOREIGN KEY (id_profesor) REFERENCES profesores(id_profesor),
 CONSTRAINT chk_tipo_lectivo CHECK (tipo_lectivo IN ('LECTIVO','NO LECTIVO'))
);
