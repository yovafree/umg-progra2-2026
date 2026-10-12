-- ============================================================
-- Clase 10: Modelos entidad-relación e introducción a SQL
-- Archivo 1: Creación de la base de datos
-- Dominio: tienda (cliente, factura, detalle_factura, producto)
-- ============================================================

-- Creamos la base de datos que usaremos durante toda la clase.
-- Si ya existe una base con el mismo nombre, esta sentencia
-- lanzará un error; por eso en el aula la ejecutamos una sola vez.
CREATE DATABASE tienda;

-- Seleccionamos la base de datos "tienda" como la base activa
-- para que las siguientes sentencias (CREATE TABLE, INSERT, etc.)
-- se ejecuten dentro de ella.
USE tienda;

-- Convención de nombres utilizada en este ejemplo:
--   * Nombres de bases de datos y tablas: en minúsculas y en singular
--     (tienda, cliente, producto, factura), separando palabras con "_".
--   * Llaves primarias: prefijo "id_" seguido del nombre de la tabla
--     en singular (id_cliente, id_producto, id_factura).
--   * Llaves foráneas: mismo nombre que la columna a la que hacen
--     referencia en la tabla original (id_cliente en factura hace
--     referencia a id_cliente en cliente).
-- Esta convención facilita identificar relaciones entre tablas
-- solo con leer el nombre de las columnas.

-- Preguntas de reflexión / variantes para practicar:
-- 1) ¿Qué ocurre si intentas ejecutar CREATE DATABASE tienda; dos veces
--    seguidas? Investiga la cláusula "IF NOT EXISTS" y prueba usarla.
-- 2) ¿Qué otras convenciones de nombres conoces (camelCase, PascalCase)?
--    ¿Por qué SQL suele preferir snake_case?
