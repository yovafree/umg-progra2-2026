package com.umg.registro.vista;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * VISTA: lo que el usuario ve y toca.
 *
 * Responsabilidades:
 *  - Crear y acomodar los componentes (campos, botones, tabla).
 *  - Mostrar datos y mensajes que le entrega el controlador.
 *
 * Lo que NUNCA hace:
 *  - Escribir SQL ni abrir conexiones.
 *  - Decidir reglas del negocio (por ejemplo, si un carné es válido).
 *
 * En el demo 4 esta clase se convierte en el formulario completo.
 */
public class FrmEstudiante extends JFrame {

    // public para que el controlador pueda escuchar sus eventos
    public final JButton btnSaludar = new JButton("Probar controlador");

    public FrmEstudiante() {
        super("Registro de Estudiantes - esqueleto MVC");
        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Aquí irá el formulario del estudiante (demo 4)", SwingConstants.CENTER),
                BorderLayout.CENTER);
        add(btnSaludar, BorderLayout.SOUTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(420, 160);
        setLocationRelativeTo(null);
    }
}
