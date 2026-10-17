package com.umg.registro.controlador;

import com.umg.registro.modelo.DatosException;
import com.umg.registro.modelo.Estudiante;
import com.umg.registro.modelo.EstudianteDAO;
import com.umg.registro.vista.FrmEstudiante;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * CONTROLADOR (versión demo 5).
 *
 * Diferencia con el demo 4: ya no atrapa SQLException (ni siquiera importa java.sql).
 * Solo atrapa DatosException, cuyo mensaje ya viene listo para mostrarse al usuario.
 */
public class EstudianteControlador {

    // Cambie a false para comparar en clase: sin transacción, eliminar a un estudiante
    // que tiene cursos asignados falla por la llave foránea (SQLState 23503).
    private static final boolean USAR_TRANSACCION = true;

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
        // Para provocar el error 23502 (NOT NULL), comente estas tres líneas y guarde sin nombre.
        if (!camposValidos()) {
            return;
        }
        // Nota: el semestre NO se valida aquí a propósito, para que lo rechace el CHECK de la BD.
        try {
            Estudiante estudiante = leerFormulario();
            dao.insertar(estudiante);
            vista.mostrarMensaje("Estudiante guardado con id " + estudiante.getId() + ".");
            vista.limpiar();
            recargarTabla();
        } catch (DatosException ex) {
            vista.mostrarError(ex.getMessage());
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
        } catch (DatosException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void eliminar() {
        if (vista.txtId.getText().isEmpty()) {
            vista.mostrarError("Primero seleccione un estudiante de la tabla.");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(vista,
                "¿Desea eliminar a " + vista.txtNombre.getText() + " y sus cursos asignados?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            int id = Integer.parseInt(vista.txtId.getText());
            boolean eliminado = USAR_TRANSACCION ? dao.eliminar(id) : dao.eliminarSinTransaccion(id);
            if (eliminado) {
                vista.mostrarMensaje("Estudiante eliminado.");
            } else {
                vista.mostrarError("El estudiante ya no existe en la base de datos.");
            }
            vista.limpiar();
            recargarTabla();
        } catch (DatosException ex) {
            vista.mostrarError(ex.getMessage());
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
        } catch (DatosException ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    private void buscarPorCarne(String carne) {
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
        } catch (DatosException ex) {
            vista.mostrarError(ex.getMessage());
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
}
