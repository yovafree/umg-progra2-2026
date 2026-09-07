/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.umg.ejemplopoo;

import edu.umg.ejemplopoo.herencia.Manzana;
import edu.umg.ejemplopoo.herencia.Pera;

/**
 *
 * @author erwin
 */
public class EjemploPOO {

    public static void main(String[] args) {
        
        System.out.println("-------------------");
        Manzana obj1 = new Manzana();
        obj1.MostrarInformacion();
        System.out.println("-------------------");
        Pera obj2 = new Pera();
        obj2.MostrarInformacion();
    }
}
