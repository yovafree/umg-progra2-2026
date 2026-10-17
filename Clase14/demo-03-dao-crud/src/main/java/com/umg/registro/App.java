package com.umg.registro;

import com.umg.registro.modelo.Estudiante;
import com.umg.registro.modelo.EstudianteDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Demo 3: probar cada método del DAO desde un menú de consola.
 *
 * Fíjese en que esta clase NO tiene SQL: solo le pide cosas al DAO.
 */
public class App {

    private static final Scanner teclado = new Scanner(System.in);
    private static final EstudianteDAO dao = new EstudianteDAO();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Opción: ");
            try {
                ejecutar(opcion);
            } catch (SQLException ex) {
                // En el demo 3 mostramos el error "crudo" para conocer sus partes.
                System.out.println("ERROR de base de datos: " + ex.getMessage());
                System.out.println("SQLState: " + ex.getSQLState());
            }
        } while (opcion != 0);
        System.out.println("Hasta luego.");
    }

    private static void mostrarMenu() {
        System.out.println();
        System.out.println("===== Registro de Estudiantes (consola) =====");
        System.out.println("1. Listar");
        System.out.println("2. Insertar");
        System.out.println("3. Buscar por carné");
        System.out.println("4. Buscar por nombre");
        System.out.println("5. Actualizar");
        System.out.println("6. Eliminar");
        System.out.println("0. Salir");
    }

    private static void ejecutar(int opcion) throws SQLException {
        switch (opcion) {
            case 1 -> imprimir(dao.listar());
            case 2 -> insertar();
            case 3 -> buscarPorCarne();
            case 4 -> imprimir(dao.buscarPorNombre(leerTexto("Parte del nombre: ")));
            case 5 -> actualizar();
            case 6 -> eliminar();
            case 0 -> { }
            default -> System.out.println("Opción no válida.");
        }
    }

    private static void insertar() throws SQLException {
        String carne = leerTexto("Carné (ej. 0907-25-11111): ");
        String nombre = leerTexto("Nombre: ");
        String correo = leerTexto("Correo (Enter si no tiene): ");
        int semestre = leerEntero("Semestre (1-10): ");

        Estudiante nuevo = new Estudiante(carne, nombre, correo.isEmpty() ? null : correo, semestre);
        dao.insertar(nuevo);
        // El DAO llenó el id con el valor que generó PostgreSQL.
        System.out.println("Guardado con id " + nuevo.getId());
    }

    private static void buscarPorCarne() throws SQLException {
        Estudiante encontrado = dao.buscarPorCarne(leerTexto("Carné: "));
        // buscarPorCarne devuelve null cuando no hay resultados: siempre hay que revisarlo.
        System.out.println(encontrado == null ? "No existe un estudiante con ese carné." : encontrado);
    }

    private static void actualizar() throws SQLException {
        Estudiante estudiante = dao.buscarPorCarne(leerTexto("Carné del estudiante a modificar: "));
        if (estudiante == null) {
            System.out.println("No existe un estudiante con ese carné.");
            return;
        }
        System.out.println("Actual: " + estudiante);
        System.out.println("(Presione Enter para conservar el valor actual)");

        String nombre = leerTexto("Nuevo nombre: ");
        String correo = leerTexto("Nuevo correo: ");
        int semestre = leerEntero("Nuevo semestre (0 = conservar): ");
        if (!nombre.isEmpty()) {
            estudiante.setNombre(nombre);
        }
        if (!correo.isEmpty()) {
            estudiante.setCorreo(correo);
        }
        if (semestre != 0) {
            estudiante.setSemestre(semestre);
        }

        System.out.println(dao.actualizar(estudiante) ? "Actualizado." : "No se encontró el id.");
    }

    private static void eliminar() throws SQLException {
        int id = leerEntero("Id del estudiante a eliminar: ");
        if (!leerTexto("¿Seguro? (s/n): ").equalsIgnoreCase("s")) {
            System.out.println("Cancelado.");
            return;
        }
        System.out.println(dao.eliminar(id) ? "Eliminado." : "No existe un estudiante con ese id.");
    }

    private static void imprimir(List<Estudiante> lista) {
        if (lista.isEmpty()) {
            System.out.println("(sin resultados)");
        }
        for (Estudiante e : lista) {
            System.out.println(e);
        }
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException ex) {
                System.out.println("Escriba un número entero.");
            }
        }
    }
}
