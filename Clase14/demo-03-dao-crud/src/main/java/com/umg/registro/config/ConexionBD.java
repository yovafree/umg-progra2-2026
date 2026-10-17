package com.umg.registro.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Único lugar del proyecto que sabe CÓMO conectarse a la base de datos.
 *
 * Si mañana cambia el servidor o la contraseña, solo se modifica esta clase
 * (o las variables de entorno), no todo el programa.
 */
public class ConexionBD {

    // Los datos se leen de variables de entorno para no dejar contraseñas fijas en el código.
    // Si no existen, se usan los valores del docker-compose.yml.
    private static final String URL = leerVariable("DB_URL", "jdbc:postgresql://localhost:5432/universidad");
    private static final String USUARIO = leerVariable("DB_USER", "postgres");
    private static final String CLAVE = leerVariable("DB_PASS", "postgres");

    // Constructor privado: nadie necesita crear objetos ConexionBD, solo pedir conexiones.
    private ConexionBD() {
    }

    /**
     * Abre una conexión NUEVA cada vez que se llama.
     * Quien la pide es responsable de cerrarla (idealmente con try-with-resources).
     */
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    private static String leerVariable(String nombre, String valorPorDefecto) {
        String valor = System.getenv(nombre);
        return (valor == null || valor.isBlank()) ? valorPorDefecto : valor;
    }
}
