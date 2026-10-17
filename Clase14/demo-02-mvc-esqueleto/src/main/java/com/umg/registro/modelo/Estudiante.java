package com.umg.registro.modelo;

/**
 * POJO (objeto simple de Java) que representa una fila de la tabla "estudiante".
 *
 * No sabe nada de bases de datos ni de ventanas: solo guarda datos.
 * Por eso lo pueden usar sin problema la vista, el controlador y el DAO.
 */
public class Estudiante {

    private int id;          // lo genera PostgreSQL (IDENTITY); vale 0 mientras no se guarde
    private String carne;
    private String nombre;
    private String correo;   // puede ser null: la columna admite NULL
    private int semestre;

    public Estudiante() {
    }

    // Constructor sin id: así se crea un estudiante NUEVO, antes de guardarlo.
    public Estudiante(String carne, String nombre, String correo, int semestre) {
        this.carne = carne;
        this.nombre = nombre;
        this.correo = correo;
        this.semestre = semestre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarne() {
        return carne;
    }

    public void setCarne(String carne) {
        this.carne = carne;
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

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    @Override
    public String toString() {
        return "Estudiante{id=" + id + ", carne=" + carne + ", nombre=" + nombre
                + ", correo=" + correo + ", semestre=" + semestre + "}";
    }
}
