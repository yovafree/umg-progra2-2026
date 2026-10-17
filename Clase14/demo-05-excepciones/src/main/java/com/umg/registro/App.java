package com.umg.registro;

import com.umg.registro.controlador.EstudianteControlador;
import com.umg.registro.vista.FrmEstudiante;
import javax.swing.SwingUtilities;

/**
 * Punto de entrada: crea la vista, se la entrega al controlador y muestra la ventana.
 */
public class App {

    public static void main(String[] args) {
        // Swing no es "thread-safe": las ventanas se deben crear y modificar
        // en el hilo de eventos (EDT). invokeLater pone este código en ese hilo.
        SwingUtilities.invokeLater(() -> new EstudianteControlador(new FrmEstudiante()).iniciar());
    }
}
