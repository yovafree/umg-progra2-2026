-- ============================================================
-- Clase 10: Modelos entidad-relación e introducción a SQL
-- Archivo 3: Tablas maestro-detalle (factura / detalle_factura)
-- Dominio: tienda
-- ============================================================

USE tienda;

-- --------------------------------------------------------------
-- Tabla maestro: factura
-- --------------------------------------------------------------
-- Cada fila representa "el encabezado" de una compra: quién la
-- realizó y cuándo. El detalle de qué productos incluye se guarda
-- en la tabla detalle_factura (ver más abajo).
CREATE TABLE factura (
    id_factura INT AUTO_INCREMENT PRIMARY KEY,

    -- Llave foránea: relaciona la factura con el cliente que la generó.
    id_cliente INT NOT NULL,

    -- Fecha en la que se emitió la factura.
    fecha DATE NOT NULL,

    -- FOREIGN KEY: obliga a que todo id_cliente en factura exista
    -- previamente en la tabla cliente (integridad referencial).
    FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- --------------------------------------------------------------
-- Tabla detalle: detalle_factura
-- --------------------------------------------------------------
-- Cada fila representa UN producto dentro de UNA factura.
-- Una factura puede tener muchas filas en detalle_factura.
CREATE TABLE detalle_factura (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,

    -- Llave foránea hacia la factura a la que pertenece este detalle.
    id_factura INT NOT NULL,

    -- Llave foránea hacia el producto vendido en esta línea.
    id_producto INT NOT NULL,

    -- Cantidad de unidades vendidas de ese producto en esa factura.
    cantidad INT NOT NULL,

    -- Precio del producto AL MOMENTO de la venta. Se guarda aparte del
    -- precio en la tabla producto porque los precios pueden cambiar
    -- con el tiempo y la factura debe reflejar el precio histórico.
    precio_unitario DECIMAL(10, 2) NOT NULL,

    FOREIGN KEY (id_factura) REFERENCES factura(id_factura),
    FOREIGN KEY (id_producto) REFERENCES producto(id_producto)
);

-- Relación representada por esta estructura:
-- factura (1) ----------- (N) detalle_factura
-- Es una relación 1:N (uno a muchos): UNA factura puede tener
-- VARIOS detalles (líneas de productos), pero cada detalle
-- pertenece a UNA sola factura. Lo mismo ocurre entre producto
-- y detalle_factura: un producto puede aparecer en muchos detalles,
-- pero cada detalle apunta a un único producto.

-- Preguntas de reflexión / variantes para practicar:
-- 1) ¿Por qué detalle_factura guarda "precio_unitario" en lugar de
--    simplemente consultar el precio actual en la tabla producto?
-- 2) Dibuja el diagrama entidad-relación (cliente, factura,
--    detalle_factura, producto) indicando la cardinalidad de cada
--    relación (1:N) y las llaves primarias/foráneas.
