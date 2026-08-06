/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.umg.ejemplos;

import java.util.ArrayList;

/**
 *
 * @author erwin
 */
public class Ejemplos {
    static ArrayList<String> nombres = new ArrayList<>();
    
    public static void main(String[] args) {
        nombres.add("Teddy");
        nombres.add("Rafael");
        nombres.add("Jimmy");
        nombres.add("Eduardo");
        nombres.add("Carlos");
        nombres.add("Gloria");
        
        System.out.println("Valor en posición 0: " + nombres.get(3));
        
        System.out.println("----------");
        for(String nombre : nombres){
            System.out.println("El nombre es: " + nombre);
        }
        
        System.out.println("El tamaño de la lista es: " + nombres.size());
        
        nombres.add("Gabriel");
        
        System.out.println("El tamaño de la lista es: " + nombres.size());
    }
}
