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
 * DAO de estudiantes, versión con manejo de errores.
 *
 * Cambios respecto al demo 4:
 *  - Los métodos ya NO declaran "throws SQLException".
 *  - Cada SQLException se traduce con traducir() a una DatosException con un mensaje claro.
 *  - eliminar() usa una TRANSACCIÓN: borra las asignaciones y luego al estudiante, todo o nada.
 */
public class EstudianteDAO {

    /** Guarda un estudiante nuevo y le asigna el id que generó PostgreSQL. */
    public void insertar(Estudiante estudiante) {
        String sql = "INSERT INTO estudiante (carne, nombre, correo, semestre) VALUES (?, ?, ?, ?)";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, estudiante.getCarne());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());
            ps.setInt(4, estudiante.getSemestre());
            ps.executeUpdate();

            try (ResultSet llaves = ps.getGeneratedKeys()) {
                if (llaves.next()) {
                    estudiante.setId(llaves.getInt("id"));
                }
            }
        } catch (SQLException ex) {
            throw traducir(ex);
        }
    }

    /** Devuelve todos los estudiantes ordenados por nombre. */
    public List<Estudiante> listar() {
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante ORDER BY nombre";
        List<Estudiante> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            throw traducir(ex);
        }
        return lista;
    }

    /** Busca por carné exacto. Devuelve null si no existe. */
    public Estudiante buscarPorCarne(String carne) {
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante WHERE carne = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, carne);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        } catch (SQLException ex) {
            throw traducir(ex);
        }
    }

    /** Busca estudiantes cuyo nombre contenga el texto (sin importar mayúsculas). */
    public List<Estudiante> buscarPorNombre(String texto) {
        String sql = "SELECT id, carne, nombre, correo, semestre FROM estudiante "
                + "WHERE nombre ILIKE ? ORDER BY nombre";
        List<Estudiante> lista = new ArrayList<>();

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, "%" + texto + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException ex) {
            throw traducir(ex);
        }
        return lista;
    }

    /** Actualiza los datos del estudiante con ese id. Devuelve false si el id no existe. */
    public boolean actualizar(Estudiante estudiante) {
        String sql = "UPDATE estudiante SET carne = ?, nombre = ?, correo = ?, semestre = ? WHERE id = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, estudiante.getCarne());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getCorreo());
            ps.setInt(4, estudiante.getSemestre());
            ps.setInt(5, estudiante.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw traducir(ex);
        }
    }

    /**
     * Elimina al estudiante y sus asignaciones en UNA transacción.
     *
     * Son dos DELETE. Si el segundo falla, el primero no debe quedar aplicado
     * (no queremos un estudiante sin sus cursos "a medias"). Por eso:
     *   setAutoCommit(false) -> los cambios quedan pendientes
     *   commit()             -> si todo salió bien, se confirman juntos
     *   rollback()           -> si algo falló, se deshace todo
     */
    public boolean eliminar(int id) {
        String sqlAsignaciones = "DELETE FROM asignacion WHERE estudiante_id = ?";
        String sqlEstudiante = "DELETE FROM estudiante WHERE id = ?";

        try (Connection cn = ConexionBD.obtenerConexion()) {
            cn.setAutoCommit(false);
            try (PreparedStatement psAsignaciones = cn.prepareStatement(sqlAsignaciones);
                 PreparedStatement psEstudiante = cn.prepareStatement(sqlEstudiante)) {
                psAsignaciones.setInt(1, id);
                int asignacionesBorradas = psAsignaciones.executeUpdate();

                psEstudiante.setInt(1, id);
                boolean eliminado = psEstudiante.executeUpdate() > 0;

                cn.commit();
                System.err.println("[EstudianteDAO] commit: estudiante " + id
                        + " eliminado junto con " + asignacionesBorradas + " asignación(es).");
                return eliminado;
            } catch (SQLException ex) {
                cn.rollback();
                System.err.println("[EstudianteDAO] rollback: no se eliminó nada del estudiante " + id + ".");
                throw ex; // lo atrapa el catch de afuera, que lo traduce
            }
        } catch (SQLException ex) {
            throw traducir(ex);
        }
    }

    /**
     * SOLO PARA COMPARAR EN CLASE: borra al estudiante sin tocar sus asignaciones.
     * Si tiene cursos asignados, la llave foránea lo impide (SQLState 23503).
     */
    public boolean eliminarSinTransaccion(int id) {
        String sql = "DELETE FROM estudiante WHERE id = ?";

        try (Connection cn = ConexionBD.obtenerConexion();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw traducir(ex);
        }
    }

    private Estudiante mapear(ResultSet rs) throws SQLException {
        Estudiante estudiante = new Estudiante(
                rs.getString("carne"),
                rs.getString("nombre"),
                rs.getString("correo"),
                rs.getInt("semestre"));
        estudiante.setId(rs.getInt("id"));
        return estudiante;
    }

    /**
     * Convierte un error técnico de JDBC en un mensaje que el usuario entiende.
     *
     * Se decide con el SQLState (estándar) y no con el texto del mensaje,
     * porque el texto cambia según el idioma y la versión del servidor.
     */
    private DatosException traducir(SQLException ex) {
        // El detalle técnico es para el programador: va a la consola, no a la ventana.
        System.err.println("[EstudianteDAO] SQLState=" + ex.getSQLState()
                + " código=" + ex.getErrorCode() + " -> " + ex.getMessage());

        String estado = ex.getSQLState() == null ? "" : ex.getSQLState();

        // Clase 08 = problemas de conexión (servidor apagado, puerto equivocado, red...).
        if (estado.startsWith("08")) {
            return new DatosException("No hay conexión con la base de datos. "
                    + "Verifique que el servidor esté encendido.", ex);
        }

        String mensaje = switch (estado) {
            case "23505" -> "Ya existe un estudiante con ese carné.";               // UNIQUE
            case "23502" -> "Falta un dato obligatorio (carné o nombre).";          // NOT NULL
            case "23514" -> "El semestre debe estar entre 1 y 10.";                 // CHECK
            case "23503" -> "No se puede eliminar: el estudiante tiene cursos asignados."; // FOREIGN KEY
            default -> "Ocurrió un error inesperado en la base de datos. Consulte al administrador.";
        };
        return new DatosException(mensaje, ex);
    }
}
