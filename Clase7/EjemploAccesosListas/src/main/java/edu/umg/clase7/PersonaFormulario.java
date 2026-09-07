package edu.umg.clase7;

/**
 * Modelo de datos que representa la informacion capturada en un formulario.
 */
public class PersonaFormulario extends EntidadBase {
    // Atributos private: se acceden por medio de getters y setters.
    private String dpi;
    private String nombre;
    private String correo;
    private String telefono;

    // Constructor public para crear objetos con los datos iniciales del formulario.
    public PersonaFormulario(String dpi, String nombre, String correo, String telefono) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Muestra la informacion en un formato legible para consola.
    // Aqui se usa un metodo protected heredado de EntidadBase.
    public String resumenPublico() {
        return construirEtiquetaInterna() + " | DPI: " + dpi + " | Nombre: " + nombre
                + " | Correo: " + correo + " | Telefono: " + telefono;
    }
}
