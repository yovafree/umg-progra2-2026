/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplos;

import java.util.ArrayList;

/**
 *
 * @author erwin
 */
public class Ejemplos2 {
    static ArrayList<Persona> personas;
    
    public static void main(String[] args) {
        personas = new ArrayList<Persona>();
        
        personas.add(new Persona("Juan Alberto", "Perez",28,"564565665"));
        personas.add(new Persona("Hugo", "Sanchez",63,"65698755233"));
        personas.add(new Persona("Sebastian", "Veron",45,"654654688/99"));
        personas.add(new Persona("Luisa", "Veron"));
        personas.add(new Persona("Gloria", "Estefan"));
        
        for(Persona persona : personas){
            System.out.println("----------");
            System.out.println("Nombre completo: " + persona.getNombres() + " " + persona.getApellidos());
            System.out.println("Edad: " + persona.getEdad());
            System.out.println("DPI: " + persona.getDpi());
        }
    }
    
}
