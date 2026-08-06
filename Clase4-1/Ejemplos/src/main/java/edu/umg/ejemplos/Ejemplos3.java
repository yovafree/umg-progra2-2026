/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author erwin
 */
public class Ejemplos3 {
    public static void main(String[] args) {
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("datos.txt"));
            
            bw.write("Hola mundo");
            bw.newLine();
            bw.write("Hola mundo, esto es otra línea");
            
            bw.newLine();
            bw.write("Hola Programación II");
            
            bw.newLine();
            bw.write("Hola Mundo, clase de Programación II");
            
            bw.close();
            
            try{
                BufferedReader br = new BufferedReader(new FileReader("datos.txt"));

                String linea;

                while((linea = br.readLine()) != null){
                    System.out.println(linea);
                }
            }catch(Exception ex){
                throw ex;
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
