package com.umg.registro;

import com.umg.registro.config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Demo de INYECCIÓN SQL: la misma búsqueda por carné escrita de dos formas.
 *
 *  1. VULNERABLE: se arma el SQL pegando (concatenando) el texto del usuario.
 *  2. SEGURA:     se usa PreparedStatement con "?".
 *
 * ¡ADVERTENCIA! El método consultaVulnerable() existe SOLO PARA ENSEÑAR.
 * NUNCA concatene datos del usuario dentro de un SQL en un programa real.
 */
public class InyeccionSqlDemo {

    // Lo que "escribe" un usuario malicioso en el campo de carné.
    private static final String ENTRADA_MALICIOSA = "' OR '1'='1";

    public static void main(String[] args) {
        System.out.println("Texto escrito por el usuario en el campo carné:  " + ENTRADA_MALICIOSA);

        try (Connection cn = ConexionBD.obtenerConexion()) {
            consultaVulnerable(cn, ENTRADA_MALICIOSA);
            consultaSegura(cn, ENTRADA_MALICIOSA);
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage() + " (SQLState " + ex.getSQLState() + ")");
        }
    }

    /**
     * ¡¡ SOLO PARA ENSEÑAR — NUNCA LO HAGA ASÍ !!
     *
     * Al concatenar, las comillas del usuario se vuelven parte del SQL:
     *   SELECT ... WHERE carne = '' OR '1'='1'
     * La condición '1'='1' siempre es verdadera, así que devuelve TODA la tabla.
     * Con otra entrada se podría incluso borrar datos.
     */
    private static void consultaVulnerable(Connection cn, String carne) throws SQLException {
        System.out.println();
        System.out.println("=== 1. VERSIÓN VULNERABLE (Statement + concatenación) — NUNCA USAR ===");

        String sql = "SELECT id, carne, nombre FROM estudiante WHERE carne = '" + carne + "'";
        System.out.println("SQL que llega a PostgreSQL: " + sql);

        try (Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            imprimirResultados(rs);
        }
        System.out.println(">> ¡Se filtraron los datos de todos los estudiantes!");
    }

    /**
     * Versión correcta: el SQL y el dato viajan por separado.
     * PostgreSQL trata toda la entrada como UN valor de texto, nunca como código SQL,
     * así que busca un carné que literalmente sea  ' OR '1'='1  (y no existe).
     */
    private static void consultaSegura(Connection cn, String carne) throws SQLException {
        System.out.println();
        System.out.println("=== 2. VERSIÓN SEGURA (PreparedStatement con ?) ===");

        String sql = "SELECT id, carne, nombre FROM estudiante WHERE carne = ?";
        System.out.println("SQL que llega a PostgreSQL: " + sql + "   (parámetro 1 = \"" + carne + "\")");

        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, carne);
            try (ResultSet rs = ps.executeQuery()) {
                imprimirResultados(rs);
            }
        }
        System.out.println(">> La entrada maliciosa se trató como texto: no hubo inyección.");
    }

    private static void imprimirResultados(ResultSet rs) throws SQLException {
        int filas = 0;
        while (rs.next()) {
            System.out.printf("   %d | %s | %s%n", rs.getInt("id"), rs.getString("carne"), rs.getString("nombre"));
            filas++;
        }
        System.out.println("   Filas devueltas: " + filas);
    }
}
