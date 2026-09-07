/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.umg.ejemplopoo;

import java.util.Date;
import java.util.List;

/**
 *
 * @author erwin
 */
public class Factura {
    private int FacturaID;
    private Date FecCreacion;
    private String Nit;
    private float Total;
    private Cliente Cliente;
    List<DetalleFactura> DetallesFactura;
}
