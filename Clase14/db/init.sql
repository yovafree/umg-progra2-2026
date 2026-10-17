-- =============================================================
--  Base de datos "universidad" - Sistema de Registro de Estudiantes
--  Programación II - UMG Cobán - Clase 14
--
--  Docker ejecuta este script automáticamente la primera vez que
--  se crea el contenedor (carpeta /docker-entrypoint-initdb.d).
-- =============================================================

CREATE TABLE estudiante (
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    carne     VARCHAR(15)  NOT NULL UNIQUE,
    nombre    VARCHAR(100) NOT NULL,
    correo    VARCHAR(100),
    semestre  INTEGER CHECK (semestre BETWEEN 1 AND 10)
);

CREATE TABLE curso (
    id        INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    codigo    VARCHAR(10)  NOT NULL UNIQUE,
    nombre    VARCHAR(100) NOT NULL,
    creditos  INTEGER      NOT NULL CHECK (creditos > 0)
);

-- Las llaves foráneas NO tienen ON DELETE CASCADE a propósito:
-- borrar un estudiante con asignaciones provoca el error 23503 (demo 5).
CREATE TABLE asignacion (
    estudiante_id  INTEGER NOT NULL REFERENCES estudiante (id),
    curso_id       INTEGER NOT NULL REFERENCES curso (id),
    fecha          DATE    NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (estudiante_id, curso_id)
);

-- ---------------- Datos de ejemplo ----------------

INSERT INTO estudiante (carne, nombre, correo, semestre) VALUES
    ('0907-23-12345', 'María José Caal Pop',         'mcaalp@miumg.edu.gt', 3),
    ('0907-23-10458', 'José Luis Xol Choc',          'jxolc@miumg.edu.gt',  3),
    ('0907-22-08731', 'Ana Lucía Tiul Coc',          'atiulc@miumg.edu.gt', 5),
    ('0907-24-15602', 'Carlos Enrique Pérez Cucul',  'cperezc@miumg.edu.gt', 1),
    ('0907-21-04219', 'Sofía Alejandra Bol Maquín',  NULL,                  7);  -- sin correo: prueba el manejo de NULL

INSERT INTO curso (codigo, nombre, creditos) VALUES
    ('PRG-102', 'Programación II',       5),
    ('BDD-201', 'Bases de Datos I',      5),
    ('MAT-105', 'Matemática Discreta',   4),
    ('FIS-101', 'Física I',              4);

-- En una base recién creada los id empiezan en 1, en el orden de los INSERT anteriores.
-- Carlos Enrique (id 4) queda SIN asignaciones: se puede eliminar sin problemas.
INSERT INTO asignacion (estudiante_id, curso_id, fecha) VALUES
    (1, 1, '2026-07-06'),   -- María José   -> Programación II
    (1, 3, '2026-07-06'),   -- María José   -> Matemática Discreta
    (2, 1, '2026-07-06'),   -- José Luis    -> Programación II
    (3, 2, '2026-07-07'),   -- Ana Lucía    -> Bases de Datos I
    (5, 2, '2026-07-07'),   -- Sofía        -> Bases de Datos I
    (5, 4, '2026-07-08');   -- Sofía        -> Física I
