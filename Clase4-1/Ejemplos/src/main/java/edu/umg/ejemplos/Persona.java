/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplos;

/**
 *
 * @author erwin
 */
public class Persona {
    private String Nombres;
    private String Apellidos;
    private int Edad;
    private String Dpi;
    
    public Persona(String nombres, String apellidos){
        this.Nombres = nombres;
        this.Apellidos = apellidos;
    }
    
    public Persona(String nombres, String apellidos, int edad, String dpi){
        this.Nombres = nombres;
        this.Apellidos = apellidos;
        this.Dpi = dpi;
        this.Edad = edad;
    }

    /**
     * @return the Nombres
     */
    public String getNombres() {
        return Nombres;
    }

    /**
     * @param Nombres the Nombres to set
     */
    public void setNombres(String Nombres) {
        this.Nombres = Nombres;
    }

    /**
     * @return the Apellidos
     */
    public String getApellidos() {
        return Apellidos;
    }

    /**
     * @param Apellidos the Apellidos to set
     */
    public void setApellidos(String Apellidos) {
        this.Apellidos = Apellidos;
    }

    /**
     * @return the Edad
     */
    public int getEdad() {
        return Edad;
    }

    /**
     * @param Edad the Edad to set
     */
    public void setEdad(int Edad) {
        this.Edad = Edad;
    }

    /**
     * @return the Dpi
     */
    public String getDpi() {
        return Dpi;
    }

    /**
     * @param Dpi the Dpi to set
     */
    public void setDpi(String Dpi) {
        this.Dpi = Dpi;
    }
    
    
}
