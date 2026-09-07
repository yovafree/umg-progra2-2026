/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplopoo.herencia;

/**
 *
 * @author erwin
 */
public class Fruta {
    protected String forma;
    protected String sabor;
    protected String color;
    protected String olor;
    protected String tamanio;
    protected String nombre;
    
    public void MostrarInformacion(){
        System.out.println("Nombre: " + this.nombre);
        System.out.println("Sabor: " + this.sabor);
        System.out.println("Olor: " + this.olor);
        System.out.println("Tamaño: " + this.tamanio);
        System.out.println("Color: " + this.color);
    }
}
