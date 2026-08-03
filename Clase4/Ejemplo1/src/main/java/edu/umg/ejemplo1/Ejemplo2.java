/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplo1;

/**
 *
 * @author erwin
 */
public class Ejemplo2 {
    public static void main(String[] args){
        System.out.println("Hola Mundo Ejemplo 2");
        
        Persona persona1 = new Persona();
        
        persona1.setDpi("6465464");
        persona1.setNombres("Juan");
        persona1.setApellidos("Pérez");
        persona1.setPeso(85);
        persona1.setGenero("Masculino");
        persona1.setEdad(19);
        
        persona1.MostrarInfo();
        
        Persona persona2 = new Persona("123456","Juan",
        "Pan",
        21,
        75);
        
        persona2.MostrarInfo();
    }
}
