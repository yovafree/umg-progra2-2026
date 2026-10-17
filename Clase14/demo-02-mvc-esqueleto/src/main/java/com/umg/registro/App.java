package com.umg.registro;

import com.umg.registro.controlador.EstudianteControlador;
import com.umg.registro.modelo.Estudiante;
import com.umg.registro.vista.FrmEstudiante;
import javax.swing.SwingUtilities;

/**
 * Demo 2: solo muestra cómo se organizan las capas. Todavía no usa la base de datos.
 */
public class App {

    public static void main(String[] args) {
        // El modelo se puede usar solo, sin ventanas: es una clase de datos.
        Estudiante estudiante = new Estudiante("0907-24-15602", "Carlos Enrique Pérez Cucul", null, 1);
        System.out.println("Modelo creado: " + estudiante);

        // App solo "conecta las piezas": crea la vista y se la entrega al controlador.
        SwingUtilities.invokeLater(() -> new EstudianteControlador(new FrmEstudiante()).iniciar());
    }
}
