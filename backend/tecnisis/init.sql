-- Crear ENUM de roles de usuario
CREATE TYPE rol_usuario AS ENUM ('Artista', 'Especialista', 'Gerente');
-- Crear ENUM de tipos de reporte
CREATE TYPE reporte_gerente AS ENUM ('Ventas', 'Obras aceptadas');

-- Crear tabla de usuarios
CREATE TABLE usuarios (
                          id SERIAL PRIMARY KEY,
                          email VARCHAR(255) UNIQUE NOT NULL,
                          password VARCHAR(255) NOT NULL
);

-- Crear tabla de personas, que referencia a usuarios y guarda datos comunes
CREATE TABLE personas (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          dni VARCHAR(20) NOT NULL UNIQUE,
                          direccion VARCHAR(255),
                          sexo CHAR(1) CHECK (sexo IN ('M', 'F')),
                          telefono VARCHAR(20),
                          tipo rol_usuario NOT NULL,
                          user_id INT UNIQUE,  -- Relación con la tabla usuarios
                          CONSTRAINT fk_persona_user_id FOREIGN KEY (user_id) REFERENCES usuarios(id)
);

-- Crear tabla de artistas
CREATE TABLE artistas (
                          id SERIAL PRIMARY KEY,
                          persona_id INT UNIQUE NOT NULL,
                          CONSTRAINT fk_artista_persona_id FOREIGN KEY (persona_id) REFERENCES personas(id)
);

-- Crear tabla de gerentes
CREATE TABLE gerentes (
                          id SERIAL PRIMARY KEY,
                          persona_id INT UNIQUE NOT NULL,
                          CONSTRAINT fk_gerente_persona_id FOREIGN KEY (persona_id) REFERENCES personas(id)
);

-- Crear tabla de especialistas
CREATE TABLE especialistas (
                               id SERIAL PRIMARY KEY,
                               persona_id INT UNIQUE NOT NULL,
                               disponibilidad BOOLEAN,
                               CONSTRAINT fk_especialista_persona_id FOREIGN KEY (persona_id) REFERENCES personas(id)
);


-- Crear tabla de técnicas
CREATE TABLE tecnicas (
                          id SERIAL PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          descripcion TEXT
);

-- Crear tabla de obras
CREATE TABLE obras (
                       id SERIAL PRIMARY KEY,
                       titulo VARCHAR(255) NOT NULL,
                       fecha_realizacion DATE NOT NULL,
                       imagen VARCHAR(255),
                       alto DECIMAL(10, 2),
                       ancho DECIMAL(10, 2),
                       tecnica_id INT NOT NULL,
                       artista_id INT NOT NULL,
                       CONSTRAINT fk_obra_tecnica_id FOREIGN KEY (tecnica_id) REFERENCES tecnicas(id),
                       CONSTRAINT fk_obra_artista_id FOREIGN KEY (artista_id) REFERENCES artistas(id)
);

-- Crear tabla de solicitudes
CREATE TABLE solicitudes (
                             id SERIAL PRIMARY KEY,
                             fecha_solicitud DATE NOT NULL,
                             estado VARCHAR(50) CHECK (estado IN ('Pendiente', 'Aprobada', 'Rechazada')) NOT NULL,
                             obra_id INT NOT NULL,
                             artista_id INT NOT NULL,
                             CONSTRAINT fk_solicitud_obra_id FOREIGN KEY (obra_id) REFERENCES obras(id),
                             CONSTRAINT fk_solicitud_artista_id FOREIGN KEY (artista_id) REFERENCES artistas(id)
);

-- Crear tabla de evaluación económica
CREATE TABLE evaluacion_economica (
                                      id SERIAL PRIMARY KEY,
                                      fecha_evaluacion DATE NOT NULL,
                                      precio_venta DECIMAL(10, 2) NOT NULL,
                                      porcentaje_galeria DECIMAL(5, 2) NOT NULL,
                                      especialista_id INT NOT NULL,
                                      obra_id INT NOT NULL,
                                      solicitud_id INT NOT NULL,
                                      id_documento INT,  -- Para relacionar el documento generado
                                      CONSTRAINT fk_evaluacion_economica_especialista_id FOREIGN KEY (especialista_id) REFERENCES especialistas(id),
                                      CONSTRAINT fk_evaluacion_economica_obra_id FOREIGN KEY (obra_id) REFERENCES obras(id),
                                      CONSTRAINT fk_evaluacion_economica_solicitud_id FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id)
);

-- Crear tabla de evaluación artística
CREATE TABLE evaluacion_artistica (
                                      id SERIAL PRIMARY KEY,
                                      fecha_evaluacion DATE NOT NULL,
                                      resultado VARCHAR(50) CHECK (resultado IN ('Aceptada', 'Rechazada')) NOT NULL,
                                      calificacion DECIMAL(3, 1),
                                      especialista_id INT NOT NULL,
                                      obra_id INT NOT NULL,
                                      solicitud_id INT NOT NULL,
                                      id_documento INT,  -- Para relacionar el documento generado
                                      CONSTRAINT fk_evaluacion_artistica_especialista_id FOREIGN KEY (especialista_id) REFERENCES especialistas(id),
                                      CONSTRAINT fk_evaluacion_artistica_obra_id FOREIGN KEY (obra_id) REFERENCES obras(id),
                                      CONSTRAINT fk_evaluacion_artistica_solicitud_id FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id)
);

-- Crear tabla de documentos
CREATE TABLE documentos (
                            id SERIAL PRIMARY KEY,
                            tipo_documento VARCHAR(100) NOT NULL,
                            ruta_documento VARCHAR(255) NOT NULL,
                            solicitud_id INT,
                            evaluacion_artistica_id INT,
                            evaluacion_economica_id INT,
                            CONSTRAINT fk_documento_solicitud_id FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id),
                            CONSTRAINT fk_documento_evaluacion_artistica_id FOREIGN KEY (evaluacion_artistica_id) REFERENCES evaluacion_artistica(id),
                            CONSTRAINT fk_documento_evaluacion_economica_id FOREIGN KEY (evaluacion_economica_id) REFERENCES evaluacion_economica(id)
);
