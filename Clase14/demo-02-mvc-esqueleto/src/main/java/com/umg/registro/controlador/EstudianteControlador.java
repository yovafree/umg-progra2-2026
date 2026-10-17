package com.umg.registro.controlador;

import com.umg.registro.modelo.Estudiante;
import com.umg.registro.vista.FrmEstudiante;
import javax.swing.JOptionPane;

/**
 * CONTROLADOR: el intermediario entre la vista y el modelo.
 *
 * Responsabilidades:
 *  - Escuchar los eventos de la vista (clic en un botón, selección en la tabla).
 *  - Validar lo que escribió el usuario.
 *  - Pedirle al modelo (DAO) que guarde o consulte datos.
 *  - Decirle a la vista qué mostrar con el resultado.
 *
 * Gracias a esta separación, la vista no conoce la base de datos
 * y el modelo no conoce las ventanas.
 */
public class EstudianteControlador {

    private final FrmEstudiante vista;

    public EstudianteControlador(FrmEstudiante vista) {
        this.vista = vista;
        // El controlador "se suscribe" a los eventos de la vista.
        this.vista.btnSaludar.addActionListener(e -> probar());
    }

    public void iniciar() {
        vista.setVisible(true);
    }

    private void probar() {
        // Por ahora solo creamos un objeto del modelo; en el demo 4 aquí se llamará al DAO.
        Estudiante ejemplo = new Estudiante("0907-23-12345", "María José Caal Pop", "mcaalp@miumg.edu.gt", 3);
        JOptionPane.showMessageDialog(vista, "El controlador recibió el clic.\n" + ejemplo);
    }
}
