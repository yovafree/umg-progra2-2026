/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package edu.umg.ejemplo1;

/**
 * Esto es un comentario
 * @author erwin
 */
public class Ejemplo1 {
    // Tipo dato
    static int num1 = 10;
    static int num2 = 5;
    
    public static void main(String[] args) {
        int res = num1+num2;
        System.out.println("La suma es: " + res);
        
        if (num1>num2){
            System.out.println("El mayor es: " + num1);
        }else{
            System.out.println("El menor es: " + num2);
        }
        int op = 3;
        switch(op)
        {
            case 1:
                System.out.println("Opción 1");
                break;
            case 2:
                System.out.println("Opción 2");
                break;
            default:
                System.out.println("Opción Default");
                break;
        }
        
        System.out.println("\n For");
        for (int x = 1; x<=10;x++){
            System.out.println(x);
        }
        System.out.println("\nDo While");
        int n=1;
        do{
            System.out.println(n);
            n++;
        }while(n<10);
        
        System.out.println("\n While");
        n = 0;
        while(n<10){
            n++;
            System.out.println(n);
        }
    }
}
