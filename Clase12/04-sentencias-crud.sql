-- ============================================================
-- Clase 10: Modelos entidad-relación e introducción a SQL
-- Archivo 4: Sentencias CRUD (INSERT, SELECT, UPDATE, DELETE)
-- Dominio: tienda
-- ============================================================

USE tienda;

-- --------------------------------------------------------------
-- INSERT: carga de datos de ejemplo
-- --------------------------------------------------------------

-- Insertamos 3 clientes. No indicamos id_cliente porque es
-- AUTO_INCREMENT: MySQL le asignará 1, 2 y 3 automáticamente.
INSERT INTO cliente (nombre, correo, fecha_registro) VALUES
    ('Ana López',      'ana.lopez@correo.com',      '2026-01-10'),
    ('Carlos Pérez',   'carlos.perez@correo.com',   '2026-02-15'),
    ('María Gómez',    'maria.gomez@correo.com',    '2026-03-05');

-- Insertamos 3 productos con su precio y stock inicial.
INSERT INTO producto (nombre, precio, stock) VALUES
    ('Mouse inalámbrico',   75.50,  50),
    ('Teclado mecánico',   350.00,  20),
    ('Monitor 24 pulgadas', 899.99, 10);

-- Insertamos 2 facturas. id_cliente = 1 es Ana López,
-- id_cliente = 2 es Carlos Pérez (deben existir en la tabla cliente).
INSERT INTO factura (id_cliente, fecha) VALUES
    (1, '2026-03-10'),  -- factura 1: comprada por Ana López
    (2, '2026-03-12');  -- factura 2: comprada por Carlos Pérez

-- Insertamos 4 detalles de factura. Cada fila referencia una
-- factura (id_factura) y un producto (id_producto) existentes.
INSERT INTO detalle_factura (id_factura, id_producto, cantidad, precio_unitario) VALUES
    (1, 1, 2, 75.50),   -- factura 1: 2 mouse inalámbricos
    (1, 2, 1, 350.00),  -- factura 1: 1 teclado mecánico
    (2, 3, 1, 899.99),  -- factura 2: 1 monitor de 24 pulgadas
    (2, 1, 1, 75.50);   -- factura 2: 1 mouse inalámbrico

-- --------------------------------------------------------------
-- SELECT: consultas de ejemplo
-- --------------------------------------------------------------

-- 1) SELECT simple: obtiene todas las columnas y filas de cliente.
SELECT * FROM cliente;

-- 2) SELECT con WHERE: busca productos con stock menor a 15 unidades.
SELECT nombre, precio, stock
FROM producto
WHERE stock < 15;

-- 3) SELECT con JOIN: muestra el detalle completo de la factura 1,
-- combinando factura, detalle_factura y producto para ver qué
-- productos, cantidades y precios corresponden a esa factura.
SELECT
    f.id_factura,
    f.fecha,
    p.nombre        AS producto,
    df.cantidad,
    df.precio_unitario,
    (df.cantidad * df.precio_unitario) AS subtotal
FROM factura f
JOIN detalle_factura df ON f.id_factura = df.id_factura
JOIN producto p ON df.id_producto = p.id_producto
WHERE f.id_factura = 1;

-- --------------------------------------------------------------
-- UPDATE: modificación de datos existentes
-- --------------------------------------------------------------

-- Actualizamos el correo del cliente con id_cliente = 3.
-- El WHERE es indispensable: sin él, se actualizarían TODOS los clientes.
UPDATE cliente
SET correo = 'maria.gomez.nueva@correo.com'
WHERE id_cliente = 3;

-- --------------------------------------------------------------
-- DELETE: eliminación de datos
-- --------------------------------------------------------------

-- Eliminamos un producto que todavía no tiene relaciones en
-- detalle_factura (por eso podemos borrarlo sin violar llaves foráneas).
-- Primero lo insertamos para poder mostrar el DELETE en este mismo archivo.
INSERT INTO producto (nombre, precio, stock) VALUES
    ('Mousepad', 25.00, 100);

DELETE FROM producto
WHERE nombre = 'Mousepad';

-- Preguntas de reflexión / variantes para practicar:
-- 1) ¿Qué error aparece si intentas hacer DELETE FROM producto
--    WHERE id_producto = 1? (pista: ese producto sí tiene relaciones
--    en detalle_factura). Explica por qué ocurre ese error.
-- 2) Escribe un SELECT que muestre, para cada cliente, el total
--    gastado sumando (cantidad * precio_unitario) de todas sus
--    facturas. Investiga las funciones SUM() y GROUP BY.
