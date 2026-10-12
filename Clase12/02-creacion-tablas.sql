-- ============================================================
-- Clase 10: Modelos entidad-relación e introducción a SQL
-- Archivo 2: Creación de tablas base (sin relaciones todavía)
-- Dominio: tienda
-- ============================================================

-- Nos aseguramos de estar trabajando dentro de la base "tienda".
USE tienda;

-- --------------------------------------------------------------
-- Tabla: cliente
-- --------------------------------------------------------------
-- Representa a las personas que compran en la tienda.
CREATE TABLE cliente (
    -- INT: número entero, ideal para identificadores.
    -- AUTO_INCREMENT: MySQL genera el valor automáticamente (1, 2, 3, ...).
    -- PRIMARY KEY: identifica de forma única cada fila de la tabla.
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,

    -- VARCHAR(n): texto de longitud variable, hasta n caracteres.
    -- NOT NULL: la columna no puede quedar vacía (obligatoria).
    nombre VARCHAR(100) NOT NULL,

    -- VARCHAR(n) + UNIQUE: no puede haber dos clientes con el mismo correo.
    correo VARCHAR(100) NOT NULL UNIQUE,

    -- DATE: almacena solo fecha (año-mes-día), sin hora.
    fecha_registro DATE NOT NULL
);

-- --------------------------------------------------------------
-- Tabla: producto
-- --------------------------------------------------------------
-- Representa los artículos que la tienda tiene disponibles para la venta.
CREATE TABLE producto (
    -- Identificador único del producto.
    id_producto INT AUTO_INCREMENT PRIMARY KEY,

    -- Nombre del producto, obligatorio.
    nombre VARCHAR(100) NOT NULL,

    -- DECIMAL(p, e): número exacto con "p" dígitos totales y "e" decimales.
    -- Se usa para dinero en lugar de FLOAT/DOUBLE, ya que evita errores
    -- de redondeo propios de los números de punto flotante.
    precio DECIMAL(10, 2) NOT NULL,

    -- INT: cantidad de unidades disponibles en inventario.
    -- Podría llevar un valor por defecto de 0 si no se indica stock inicial.
    stock INT NOT NULL DEFAULT 0
);

-- Preguntas de reflexión / variantes para practicar:
-- 1) ¿Qué pasaría si intentas insertar dos clientes con el mismo correo?
--    Pruébalo en el archivo 04 y observa el mensaje de error.
-- 2) ¿Por qué crees que "precio" usa DECIMAL y no FLOAT? Investiga la
--    diferencia entre precisión decimal exacta y precisión aproximada.
