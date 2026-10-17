package com.umg.registro.modelo;

import com.umg.registro.config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO (Data Access Object): la ÚNICA clase que contiene SQL sobre la tabla estudiante.
 *
 * Reglas que se repiten en todos los métodos:
 *  1. PreparedStatement con "?" -> los datos viajan aparte del SQL (evita inyección SQL).
 *  2. try-with-resources        -> Connection, PreparedStatement y ResultSet se cierran solos.
 *  3. throws SQLException        -> en este demo el error se le pasa a quien llamó.
 *                                  (En el demo 5 lo traduciremos a un mensaje amigable.)
 */
public class EstudianteDAO {

    /** Guarda un estudiante nuevo y le asigna el id que generó PostgreSQL. */
    public void insertar(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiante (carne, nombre, correo, semestre) VALUES (?, ?, ?, ?)";

        // RETURN_GENERATED_KEYS le pide al driver que nos devuelva el id creado por IDENTITY.
        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, estudiante.getCarne());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());   // si es null, se guarda NULL
            ps.setInt(4, estudiante.getSemestre());
            ps.executeUpdate();

            try (ResultSet llaves = ps.getGeneratedKeys()) {
                if (llaves.next()) {
                    estudiante.setId(llaves.getInt("id"));
                }
            }
        }
    }

    /** Devuelve todos los estudiantes ordenados por nombre. */
    public List<Estudiante> listar() throws SQLException {
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante ORDER BY nombre";
        List<Estudiante> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /** Busca por carné exacto. Devuelve null si no existe. */
    public Estudiante buscarPorCarne(String carne) throws SQLException {
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante WHERE carne = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, carne);
            try (ResultSet rs = ps.executeQuery()) {
                // El carné es UNIQUE: a lo sumo hay una fila, por eso "if" y no "while".
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /** Busca estudiantes cuyo nombre contenga el texto (sin importar mayúsculas). */
    public List<Estudiante> buscarPorNombre(String texto) throws SQLException {
        // ILIKE es el LIKE de PostgreSQL que no distingue mayúsculas de minúsculas.
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante "
                + "WHERE nombre ILIKE ? ORDER BY nombre";
        List<Estudiante> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            // Los comodines % van en el VALOR del parámetro, no pegados al SQL.
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /** Actualiza los datos del estudiante con ese id. Devuelve false si el id no existe. */
    public boolean actualizar(Estudiante estudiante) throws SQLException {
        String sql = "UPDATE estudiante SET carne = ?, nombre = ?, correo = ?, semestre = ? WHERE id = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estudiante.getCarne());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());
            ps.setInt(4, estudiante.getSemestre());
            ps.setInt(5, estudiante.getId());
            // executeUpdate devuelve cuántas filas cambió: 0 significa que no encontró el id.
            return ps.executeUpdate() > 0;
        }
    }

    /** Elimina el estudiante con ese id. Devuelve false si el id no existe. */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM estudiante WHERE id = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    /** Convierte la fila actual del ResultSet en un objeto Estudiante (se usa en todos los SELECT). */
    private Estudiante mapear(ResultSet rs) throws SQLException {
        Estudiante estudiante = new Estudiante(
                rs.getString("carne"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getInt("semestre"));
        estudiante.setId(rs.getInt("id"));
        return estudiante;
    }
}
