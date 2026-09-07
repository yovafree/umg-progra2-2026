/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplopoo;

/**
 *
 * @author erwin
 */
public class Cliente {
    private String Nit;
    private String Nombre;
    private String Apellido;
    private String Direccion;
    private String NoTelefono;
    
    public Cliente(String nit, String Nombre, String Apellido, String Direccion, String NoTelefono){
        this.Nit = nit;
        this.Nombre=Nombre;
        this.Apellido = Apellido;
        this.Direccion = Direccion;
        this.NoTelefono = NoTelefono;
    }
}
