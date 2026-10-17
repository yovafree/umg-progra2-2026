package com.umg.registro.vista;

import com.umg.registro.modelo.Estudiante;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

/**
 * VISTA: formulario del Sistema de Registro de Estudiantes.
 *
 * Solo arma la pantalla y ofrece métodos para mostrar cosas.
 * No contiene SQL ni decisiones: todo eso lo hace el controlador.
 *
 * Los componentes son public para que el controlador pueda leerlos
 * y registrar sus eventos.
 */
public class FrmEstudiante extends JFrame {

    // ----- Campos del formulario -----
    public final JTextField txtId = new JTextField(6);
    public final JTextField txtCarne = new JTextField(15);
    public final JTextField txtNombre = new JTextField(28);
    public final JTextField txtCorreo = new JTextField(28);
    // El modelo del spinner limita el semestre de 1 a 10 (valor inicial 1, salto de 1).
    public final JSpinner spnSemestre = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

    // ----- Botones -----
    public final JButton btnNuevo = new JButton("Nuevo");
    public final JButton btnGuardar = new JButton("Guardar");
    public final JButton btnActualizar = new JButton("Actualizar");
    public final JButton btnEliminar = new JButton("Eliminar");
    public final JButton btnBuscar = new JButton("Buscar");

    // ----- Tabla -----
    public final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"ID", "Carné", "Nombre", "Correo", "Semestre"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false; // la tabla solo muestra; los cambios se hacen en el formulario
        }
    };
    public final JTable tblEstudiantes = new JTable(modeloTabla);

    public FrmEstudiante() {
        super("Registro de Estudiantes");
        txtId.setEditable(false); // el id lo genera la base de datos, el usuario no lo escribe
        tblEstudiantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(crearPanelFormulario(), BorderLayout.CENTER);
        panelSuperior.add(crearPanelBotones(), BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(tblEstudiantes), BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(780, 560);
        setLocationRelativeTo(null);
    }

    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Datos del estudiante"));
        agregarFila(panel, 0, "ID:", txtId);
        agregarFila(panel, 1, "Carné:", txtCarne);
        agregarFila(panel, 2, "Nombre:", txtNombre);
        agregarFila(panel, 3, "Correo:", txtCorreo);
        agregarFila(panel, 4, "Semestre:", spnSemestre);
        return panel;
    }

    /** Coloca una etiqueta a la izquierda y su campo a la derecha, en la fila indicada. */
    private void agregarFila(JPanel panel, int fila, String etiqueta, JComponent campo) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 8, 4, 8);
        c.gridy = fila;

        c.gridx = 0;
        c.anchor = GridBagConstraints.LINE_END;
        panel.add(new JLabel(etiqueta), c);

        c.gridx = 1;
        c.weightx = 1; // la columna de campos ocupa el espacio sobrante
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(campo, c);
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 8, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(8, 8, 0, 8));
        panel.add(btnNuevo);
        panel.add(btnGuardar);
        panel.add(btnActualizar);
        panel.add(btnEliminar);
        panel.add(btnBuscar);
        return panel;
    }

    // ================= Métodos de ayuda que usa el controlador =================

    /** Deja el formulario vacío, listo para un estudiante nuevo. */
    public void limpiar() {
        txtId.setText("");
        txtCarne.setText("");
        txtNombre.setText("");
        txtCorreo.setText("");
        spnSemestre.setValue(1);
        tblEstudiantes.clearSelection();
        txtCarne.requestFocus();
    }

    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /** Reemplaza el contenido de la tabla con la lista recibida. */
    public void cargarTabla(List<Estudiante> estudiantes) {
        modeloTabla.setRowCount(0); // borra las filas anteriores
        for (Estudiante e : estudiantes) {
            modeloTabla.addRow(new Object[]{
                e.getId(), e.getCarne(), e.getNombre(), e.getCorreo(), e.getSemestre()
            });
        }
    }
}
