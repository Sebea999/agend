/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.javaBean;

/**
 *
 * @author RYUU
 */
public class BeanFacturaDet {
    
    // TABLA:   ope_factura_det
    int OFD_IDFACTURA;
    int OFD_ITEM;
    int OFD_IDCONCEPTO;
    int OFD_IDTIPOCONCEPTO;
    int OFD_CANTIDAD;
    String OFD_PRECIO;
    String OFD_IDIMPUESTO;
    String OFD_EXENTO;
    String OFD_IVA5;
    String OFD_IVA10;
    String OFD_GRAV5;
    String OFD_GRAV10;
    String OFD_SUBTOTAL;
    String OFD_DESCRIPCION_AUX; // CARGO LA DESCRIPCION DEL CONCEPTO, AUNQUE NO LO UTILICE PARA LOS CONCEPTO DE CUENTA CLIENTE Y PRODUCTO LO NECESITO MAS PARA EL CONCEPTO DE AGREGAR INTERES PARA GUARDAR SU DESCRIPCION 

    public BeanFacturaDet() {
    }

    public BeanFacturaDet(int OFD_IDFACTURA, int OFD_ITEM, int OFD_IDCONCEPTO, int OFD_IDTIPOCONCEPTO, String OFD_DESCRIPCION_AUX, int OFD_CANTIDAD, String OFD_PRECIO, String OFD_IDIMPUESTO, String OFD_EXENTO, String OFD_IVA5, String OFD_IVA10, String OFD_GRAV5, String OFD_GRAV10, String OFD_SUBTOTAL) {
        this.OFD_IDFACTURA = OFD_IDFACTURA;
        this.OFD_ITEM = OFD_ITEM;
        this.OFD_IDCONCEPTO = OFD_IDCONCEPTO;
        this.OFD_IDTIPOCONCEPTO = OFD_IDTIPOCONCEPTO;
        this.OFD_DESCRIPCION_AUX = OFD_DESCRIPCION_AUX;
        this.OFD_CANTIDAD = OFD_CANTIDAD;
        this.OFD_PRECIO = OFD_PRECIO;
        this.OFD_IDIMPUESTO = OFD_IDIMPUESTO;
        this.OFD_EXENTO = OFD_EXENTO;
        this.OFD_IVA5 = OFD_IVA5;
        this.OFD_IVA10 = OFD_IVA10;
        this.OFD_GRAV5 = OFD_GRAV5;
        this.OFD_GRAV10 = OFD_GRAV10;
        this.OFD_SUBTOTAL = OFD_SUBTOTAL;
    }

    public int getOFD_IDFACTURA() {
        return OFD_IDFACTURA;
    }

    public void setOFD_IDFACTURA(int OFD_IDFACTURA) {
        this.OFD_IDFACTURA = OFD_IDFACTURA;
    }

    public int getOFD_ITEM() {
        return OFD_ITEM;
    }

    public void setOFD_ITEM(int OFD_ITEM) {
        this.OFD_ITEM = OFD_ITEM;
    }

    public int getOFD_IDCONCEPTO() {
        return OFD_IDCONCEPTO;
    }

    public void setOFD_IDCONCEPTO(int OFD_IDCONCEPTO) {
        this.OFD_IDCONCEPTO = OFD_IDCONCEPTO;
    }

    public int getOFD_IDTIPOCONCEPTO() {
        return OFD_IDTIPOCONCEPTO;
    }

    public void setOFD_IDTIPOCONCEPTO(int OFD_IDTIPOCONCEPTO) {
        this.OFD_IDTIPOCONCEPTO = OFD_IDTIPOCONCEPTO;
    }

    public String getOFD_DESCRIPCION_AUX() {
        return OFD_DESCRIPCION_AUX;
    }

    public void setOFD_DESCRIPCION_AUX(String OFD_DESCRIPCION_AUX) {
        this.OFD_DESCRIPCION_AUX = OFD_DESCRIPCION_AUX;
    }

    public int getOFD_CANTIDAD() {
        return OFD_CANTIDAD;
    }

    public void setOFD_CANTIDAD(int OFD_CANTIDAD) {
        this.OFD_CANTIDAD = OFD_CANTIDAD;
    }

    public String getOFD_PRECIO() {
        return OFD_PRECIO;
    }

    public void setOFD_PRECIO(String OFD_PRECIO) {
        this.OFD_PRECIO = OFD_PRECIO;
    }

    public String getOFD_IDIMPUESTO() {
        return OFD_IDIMPUESTO;
    }

    public void setOFD_IDIMPUESTO(String OFD_IDIMPUESTO) {
        this.OFD_IDIMPUESTO = OFD_IDIMPUESTO;
    }

    public String getOFD_EXENTO() {
        return OFD_EXENTO;
    }

    public void setOFD_EXENTO(String OFD_EXENTO) {
        this.OFD_EXENTO = OFD_EXENTO;
    }

    public String getOFD_IVA5() {
        return OFD_IVA5;
    }

    public void setOFD_IVA5(String OFD_IVA5) {
        this.OFD_IVA5 = OFD_IVA5;
    }

    public String getOFD_IVA10() {
        return OFD_IVA10;
    }

    public void setOFD_IVA10(String OFD_IVA10) {
        this.OFD_IVA10 = OFD_IVA10;
    }

    public String getOFD_GRAV5() {
        return OFD_GRAV5;
    }

    public void setOFD_GRAV5(String OFD_GRAV5) {
        this.OFD_GRAV5 = OFD_GRAV5;
    }

    public String getOFD_GRAV10() {
        return OFD_GRAV10;
    }

    public void setOFD_GRAV10(String OFD_GRAV10) {
        this.OFD_GRAV10 = OFD_GRAV10;
    }

    public String getOFD_SUBTOTAL() {
        return OFD_SUBTOTAL;
    }

    public void setOFD_SUBTOTAL(String OFD_SUBTOTAL) {
        this.OFD_SUBTOTAL = OFD_SUBTOTAL;
    }
    
    
} // END CLASS 