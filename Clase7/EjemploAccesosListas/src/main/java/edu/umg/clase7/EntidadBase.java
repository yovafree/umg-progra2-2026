package edu.umg.clase7;

/**
 * Clase base para demostrar el acceso protected en herencia.
 */
public class EntidadBase {
    // Atributo private: solo se puede usar dentro de esta clase.
    private static int correlativo = 1;
    // Atributo protected: disponible en esta clase y en sus clases hijas.
    protected int idInterno;

    // Constructor public: cualquier clase puede crear la entidad base.
    public EntidadBase() {
        this.idInterno = correlativo++;
    }

    // Metodo public: permite consultar el id desde fuera de la clase.
    public int getIdInterno() {
        return idInterno;
    }

    // Metodo protected: solo la clase y sus hijas pueden usar esta etiqueta.
    protected String construirEtiquetaInterna() {
        return "REG-" + idInterno;
    }
}
