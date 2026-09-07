package edu.umg.clase7;

/**
 * Clase principal: simula la captura de formularios y operaciones sobre una lista en memoria.
 */
public class Clase7App {

    public static void main(String[] args) {
        // Repositorio en memoria que almacena objetos PersonaFormulario.
        RepositorioPersonas repositorio = new RepositorioPersonas();

        System.out.println("== REGISTRO DE FORMULARIOS EN MEMORIA ==");

        // 1) AGREGAR: se insertan objetos capturados desde un formulario.
        System.out.println("\n1) Agregar personas desde formulario");
        repositorio.agregar(new PersonaFormulario("1001", "Ana Lopez", "ana@correo.com", "5555-1001"));
        repositorio.agregar(new PersonaFormulario("1002", "Luis Perez", "luis@correo.com", "5555-1002"));
        repositorio.agregar(new PersonaFormulario("1003", "Marta Diaz", "marta@correo.com", "5555-1003"));
        mostrarListado(repositorio);

        // 2) BUSCAR: localiza un objeto por su identificador (DPI).
        System.out.println("\n2) Buscar una persona por DPI (1002)");
        PersonaFormulario encontrada = repositorio.buscar("1002");
        if (encontrada != null) {
            System.out.println("Encontrada: " + encontrada.resumenPublico());
        }

        // 3) MODIFICAR: actualiza campos del objeto encontrado.
        System.out.println("\n3) Modificar datos de la persona con DPI 1003");
        repositorio.modificar("1003", new PersonaFormulario("1003", "Marta Rivera", "mrivera@correo.com", "5555-2003"));
        mostrarListado(repositorio);

        // 4) ELIMINAR: remueve un objeto de la lista por DPI.
        System.out.println("\n4) Eliminar persona con DPI 1001");
        repositorio.eliminar("1001");
        mostrarListado(repositorio);

        System.out.println("\nNota: los datos estan en memoria (List<PersonaFormulario>) y se pierden al cerrar el programa.");
    }

    // Metodo de apoyo para imprimir el contenido actual de la lista.
    private static void mostrarListado(RepositorioPersonas repositorio) {
        for (PersonaFormulario persona : repositorio.listarTodos()) {
            System.out.println(persona.resumenPublico());
        }
    }
}
