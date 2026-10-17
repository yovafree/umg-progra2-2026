package com.umg.registro.controlador;

import com.umg.registro.modelo.Estudiante;
import com.umg.registro.modelo.EstudianteDAO;
import com.umg.registro.vista.FrmEstudiante;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * CONTROLADOR: recibe los eventos del formulario, valida y llama al DAO.
 */
public class EstudianteControlador {

    private final FrmEstudiante vista;
    private final EstudianteDAO dao = new EstudianteDAO();

    public EstudianteControlador(FrmEstudiante vista) {
        this.vista = vista;
        registrarEventos();
    }

    public void iniciar() {
        vista.setVisible(true);
        recargarTabla();
    }

    private void registrarEventos() {
        vista.btnNuevo.addActionListener(e -> nuevo());
        vista.btnGuardar.addActionListener(e -> guardar());
        vista.btnActualizar.addActionListener(e -> actualizar());
        vista.btnEliminar.addActionListener(e -> eliminar());
        vista.btnBuscar.addActionListener(e -> buscar());

        vista.tblEstudiantes.getSelectionModel().addListSelectionListener(e -> {
            // Mientras el usuario arrastra el mouse llegan varios eventos "intermedios".
            // Solo nos interesa el último, cuando la selección ya terminó.
            if (!e.getValueIsAdjusting()) {
                pasarFilaAlFormulario();
            }
        });
    }

    // ============================== Acciones ==============================

    private void nuevo() {
        vista.limpiar();
        recargarTabla();
    }

    private void guardar() {
        if (!vista.txtId.getText().isEmpty()) {
            vista.mostrarError("Este estudiante ya está guardado. Use Actualizar, o presione Nuevo.");
            return;
        }
        if (!camposValidos()) {
            return;
        }
        try {
            Estudiante estudiante = leerFormulario();
            dao.insertar(estudiante);
            vista.mostrarMensaje("Estudiante guardado con id " + estudiante.getId() + ".");
            vista.limpiar();
            recargarTabla();
        } catch (SQLException ex) {
            errorBaseDatos(ex);
        }
    }

    private void actualizar() {
        if (vista.txtId.getText().isEmpty()) {
            vista.mostrarError("Primero seleccione un estudiante de la tabla.");
            return;
        }
        if (!camposValidos()) {
            return;
        }
        try {
            if (dao.actualizar(leerFormulario())) {
                vista.mostrarMensaje("Estudiante actualizado.");
            } else {
                vista.mostrarError("El estudiante ya no existe en la base de datos.");
            }
            vista.limpiar();
            recargarTabla();
        } catch (SQLException ex) {
            errorBaseDatos(ex);
        }
    }

    private void eliminar() {
        if (vista.txtId.getText().isEmpty()) {
            vista.mostrarError("Primero seleccione un estudiante de la tabla.");
            return;
        }
        // Borrar no se puede deshacer: siempre se confirma con el usuario.
        int respuesta = JOptionPane.showConfirmDialog(vista,
                "¿Desea eliminar a " + vista.txtNombre.getText() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            int id = Integer.parseInt(vista.txtId.getText());
            if (dao.eliminar(id)) {
                vista.mostrarMensaje("Estudiante eliminado.");
            } else {
                vista.mostrarError("El estudiante ya no existe en la base de datos.");
            }
            vista.limpiar();
            recargarTabla();
        } catch (SQLException ex) {
            errorBaseDatos(ex);
        }
    }

    /** Busca por carné si se escribió uno; si no, por parte del nombre. */
    private void buscar() {
        String carne = vista.txtCarne.getText().trim();
        String nombre = vista.txtNombre.getText().trim();
        try {
            if (!carne.isEmpty()) {
                buscarPorCarne(carne);
            } else if (!nombre.isEmpty()) {
                List<Estudiante> resultado = dao.buscarPorNombre(nombre);
                vista.cargarTabla(resultado);
                if (resultado.isEmpty()) {
                    vista.mostrarMensaje("Ningún nombre contiene \"" + nombre + "\".");
                }
            } else {
                vista.mostrarError("Escriba un carné, o parte del nombre, para buscar.");
            }
        } catch (SQLException ex) {
            errorBaseDatos(ex);
        }
    }

    private void buscarPorCarne(String carne) throws SQLException {
        Estudiante encontrado = dao.buscarPorCarne(carne);
        if (encontrado == null) {
            vista.mostrarMensaje("No existe un estudiante con el carné " + carne + ".");
            return;
        }
        // Mostramos solo ese estudiante y lo seleccionamos:
        // el ListSelectionListener se encarga de pasarlo al formulario.
        vista.cargarTabla(List.of(encontrado));
        vista.tblEstudiantes.setRowSelectionInterval(0, 0);
    }

    // ============================== Ayudantes ==============================

    private void recargarTabla() {
        try {
            vista.cargarTabla(dao.listar());
        } catch (SQLException ex) {
            errorBaseDatos(ex);
        }
    }

    /** Copia la fila seleccionada de la tabla a los campos del formulario. */
    private void pasarFilaAlFormulario() {
        int fila = vista.tblEstudiantes.getSelectedRow();
        if (fila == -1) {
            return; // no hay fila seleccionada (por ejemplo, después de limpiar)
        }
        vista.txtId.setText(vista.modeloTabla.getValueAt(fila, 0).toString());
        vista.txtCarne.setText(vista.modeloTabla.getValueAt(fila, 1).toString());
        vista.txtNombre.setText(vista.modeloTabla.getValueAt(fila, 2).toString());
        // El correo puede ser NULL en la BD: llamar toString() sobre null lanzaría NullPointerException.
        Object correo = vista.modeloTabla.getValueAt(fila, 3);
        vista.txtCorreo.setText(correo == null ? "" : correo.toString());
        vista.spnSemestre.setValue(vista.modeloTabla.getValueAt(fila, 4));
    }

    /** Revisa los campos obligatorios ANTES de ir a la base de datos. */
    private boolean camposValidos() {
        if (vista.txtCarne.getText().isBlank()) {
            vista.mostrarError("El carné es obligatorio.");
            vista.txtCarne.requestFocus();
            return false;
        }
        if (vista.txtNombre.getText().isBlank()) {
            vista.mostrarError("El nombre es obligatorio.");
            vista.txtNombre.requestFocus();
            return false;
        }
        return true;
    }

    /** Construye un Estudiante con lo que hay escrito en el formulario. */
    private Estudiante leerFormulario() {
        Estudiante estudiante = new Estudiante(
                textoONull(vista.txtCarne),
                textoONull(vista.txtNombre),
                textoONull(vista.txtCorreo),
                (Integer) vista.spnSemestre.getValue());
        if (!vista.txtId.getText().isEmpty()) {
            estudiante.setId(Integer.parseInt(vista.txtId.getText()));
        }
        return estudiante;
    }

    /** Un campo vacío se guarda como NULL en la BD, no como texto vacío "". */
    private String textoONull(JTextField campo) {
        String texto = campo.getText().trim();
        return texto.isEmpty() ? null : texto;
    }

    /**
     * Manejo BÁSICO de errores: sea cual sea el problema, el usuario ve el mismo mensaje.
     * ¿Fue un carné repetido? ¿La BD está apagada? No lo sabe. Esto se mejora en el demo 5.
     */
    private void errorBaseDatos(SQLException ex) {
        vista.mostrarError("Ocurrió un error con la base de datos.");
    }
}
