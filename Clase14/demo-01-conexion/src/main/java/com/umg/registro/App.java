package com.umg.registro;

import com.umg.registro.config.ConexionBD;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Demo 1: comprobar que Java puede hablar con PostgreSQL.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("Intentando conectar a PostgreSQL...");

        // try-with-resources: la conexión se cierra sola al salir del bloque,
        // aunque ocurra un error. Una conexión abierta consume recursos del servidor.
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            // Los metadatos describen el servidor al que nos conectamos.
            DatabaseMetaData meta = conexion.getMetaData();

            System.out.println("¡Conexión exitosa!");
            System.out.println("  Motor:    " + meta.getDatabaseProductName());
            System.out.println("  Versión:  " + meta.getDatabaseProductVersion());
            System.out.println("  Driver:   " + meta.getDriverName() + " " + meta.getDriverVersion());
            System.out.println("  URL:      " + meta.getURL());
            System.out.println("  Usuario:  " + meta.getUserName());

        } catch (SQLException ex) {
            // Una SQLException trae tres datos útiles para diagnosticar:
            //  - getMessage():   descripción legible del problema
            //  - getSQLState():  código estándar de 5 caracteres (igual en cualquier motor)
            //  - getErrorCode(): código propio del fabricante (PostgreSQL casi siempre usa 0)
            System.out.println("No se pudo conectar a la base de datos.");
            System.out.println("  Mensaje:  " + ex.getMessage());
            System.out.println("  SQLState: " + ex.getSQLState());
            System.out.println("  Código:   " + ex.getErrorCode());
        }
    }
}
