package edu.umg.clase7;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio en memoria para administrar una lista de objetos PersonaFormulario.
 */
public class RepositorioPersonas {
    // Lista protected para demostrar acceso en la clase y posibles subclases.
    protected List<PersonaFormulario> personas;

    // Inicializa la lista vacia al crear el repositorio.
    public RepositorioPersonas() {
        this.personas = new ArrayList<>();
    }

    // Agrega un registro nuevo si es valido y no existe otro con el mismo DPI.
    public boolean agregar(PersonaFormulario persona) {
        if (!validarPersona(persona) || buscar(persona.getDpi()) != null) {
            return false;
        }
        return personas.add(persona);
    }

    // Modifica nombre, correo y telefono del registro encontrado por DPI.
    public boolean modificar(String dpi, PersonaFormulario nuevosDatos) {
        int indice = buscarIndicePorDpi(dpi);
        if (indice < 0 || !validarPersona(nuevosDatos)) {
            return false;
        }

        PersonaFormulario actual = personas.get(indice);
        actual.setNombre(nuevosDatos.getNombre());
        actual.setCorreo(nuevosDatos.getCorreo());
        actual.setTelefono(nuevosDatos.getTelefono());
        return true;
    }

    // Elimina un registro por DPI si existe en la lista.
    public boolean eliminar(String dpi) {
        int indice = buscarIndicePorDpi(dpi);
        if (indice < 0) {
            return false;
        }
        personas.remove(indice);
        return true;
    }

    // Busca una persona por DPI y devuelve null si no encuentra coincidencia.
    public PersonaFormulario buscar(String dpi) {
        int indice = buscarIndicePorDpi(dpi);
        if (indice < 0) {
            return null;
        }
        return personas.get(indice);
    }

    // Devuelve una copia para evitar exponer la lista interna directamente.
    public List<PersonaFormulario> listarTodos() {
        return new ArrayList<>(personas);
    }

    // Metodo private de apoyo para encontrar la posicion del registro.
    private int buscarIndicePorDpi(String dpi) {
        for (int i = 0; i < personas.size(); i++) {
            PersonaFormulario persona = personas.get(i);
            if (persona.getDpi().equals(dpi)) {
                return i;
            }
        }
        return -1;
    }

    // Metodo private de validacion basica de datos obligatorios.
    private boolean validarPersona(PersonaFormulario persona) {
        return persona != null
                && persona.getDpi() != null && !persona.getDpi().isBlank()
                && persona.getNombre() != null && !persona.getNombre().isBlank();
    }
}
