-- ============================================================
-- Clase 10: Modelos entidad-relación e introducción a SQL
-- Archivo 5: DROP y limpieza de la base de datos
-- Dominio: tienda
-- ============================================================

-- ADVERTENCIA: las sentencias DROP son IRREVERSIBLES. Al eliminar
-- una tabla o una base de datos se borran también todos los datos
-- que contienen, sin posibilidad de deshacer la operación (no hay
-- "Ctrl+Z" en SQL). Ejecuta este archivo solo cuando estés seguro
-- de que ya no necesitas la información, o cuando quieras reiniciar
-- el ejercicio desde cero.

USE tienda;

-- --------------------------------------------------------------
-- DROP TABLE: eliminamos primero las tablas "detalle", porque
-- tienen llaves foráneas que dependen de las tablas "maestro".
-- Si intentáramos eliminar primero cliente/factura/producto,
-- MySQL lanzaría un error de restricción de llave foránea.
-- --------------------------------------------------------------

-- detalle_factura depende de factura y de producto: se elimina primero.
DROP TABLE detalle_factura;

-- factura depende de cliente: se elimina antes que cliente.
DROP TABLE factura;

-- producto ya no tiene tablas que dependan de ella (detalle_factura
-- ya fue eliminada), por lo que ahora se puede borrar sin problema.
DROP TABLE producto;

-- cliente ya no tiene tablas que dependan de ella (factura ya fue
-- eliminada), por lo que se elimina al final.
DROP TABLE cliente;

-- --------------------------------------------------------------
-- DROP DATABASE: elimina la base de datos completa, junto con
-- cualquier tabla que aún quedara dentro de ella.
-- --------------------------------------------------------------
DROP DATABASE tienda;

-- Preguntas de reflexión / variantes para practicar:
-- 1) ¿Qué ocurre si ejecutas DROP TABLE cliente; antes que
--    DROP TABLE factura;? Prueba el error y explica, en tus propias
--    palabras, qué protección te está dando MySQL en ese momento.
-- 2) Investiga la opción "ON DELETE CASCADE" en una FOREIGN KEY.
--    ¿Cómo cambiaría el orden necesario para eliminar las tablas
--    si la hubiéramos usado en el archivo 03?
