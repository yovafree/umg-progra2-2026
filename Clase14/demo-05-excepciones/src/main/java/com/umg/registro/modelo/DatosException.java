package com.umg.registro.modelo;

/**
 * Excepción PROPIA de la capa de datos.
 *
 * - Su mensaje ya está escrito para el usuario final ("Ya existe un estudiante con ese carné").
 * - Guarda la SQLException original como "causa", para no perder el detalle técnico.
 * - Extiende RuntimeException (no verificada): el controlador no está obligado a declarar
 *   "throws", y ninguna clase fuera del modelo necesita importar java.sql.
 */
public class DatosException extends RuntimeException {

    public DatosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
