/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplo1;

/**
 *
 * @author erwin
 * Métodos de acceso: public, private
 */
public class Persona {
    private String dpi;
    private String nombres;
    private String apellidos;
    private int peso;
    private String genero;
    private int edad;
    
    public Persona(){
        this.dpi = "";
        this.apellidos = "";
        this.nombres = "";
        this.edad = 0;
        this.peso = 0;
    }
    
    public Persona(String dpi, String nombres, 
            String apellidos, int edad, int peso){
        this.dpi = dpi;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.edad = edad;
        this.peso = peso;
    }
    
    public void setDpi(String dpi){
        this.dpi = dpi;
    }
    
    public String getDpi(){
        return this.dpi;
    }

    /**
     * @return the nombres
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * @param nombres the nombres to set
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * @return the apellidos
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * @param apellidos the apellidos to set
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * @return the peso
     */
    public int getPeso() {
        return peso;
    }

    /**
     * @param peso the peso to set
     */
    public void setPeso(int peso) {
        this.peso = peso;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    public void MostrarInfo(){
        System.out.println("DPI: " + dpi);
        System.out.println("Nombre: " + nombres);
        System.out.println("Apellido: " + apellidos);
        System.out.println("Edad: " + edad);
    }
}
