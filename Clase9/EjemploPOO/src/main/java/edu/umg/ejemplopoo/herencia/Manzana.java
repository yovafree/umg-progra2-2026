/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplopoo.herencia;

/**
 *
 * @author erwin
 */
public class Manzana extends Fruta {
    private String textura;
    
    public Manzana(){
        this.color = "rojo";
        this.tamanio = "pequeña";
        this.forma = "semi-redonda";
        this.nombre = "manzana";
        this.sabor="Dulce a veces àcido";
        this.textura = "liza";
    }
    
    @Override
    public void MostrarInformacion(){
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Sabor: " + this.sabor);
        System.out.println("Olor: " + this.olor);
        System.out.println("Tamaño: " + this.tamanio);
        System.out.println("Color: " + this.color);
        System.out.println("Textura: " + this.textura);
    }
    
    
}
