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
public class BeanFacturaCab extends BeanFacturaDet {
    
    // TABLA:   ope_factura 
    int OF_IDFACTURA;
    int OF_IDCLINICA;
    String OF_NROFACTURA;
    String OF_TIPOFACTURA;
    int OF_IDCLIENTE;
    String OF_FECHAFACTURA;
    String OF_IDARQUEO;
    String OF_ESTADO;
    String OF_TOTAL_FACTURA;
    String OF_TOTAL_DCTO;
    String OF_TOTAL_IVA10;
    String OF_TOTAL_IVA5;
    String OF_TOTAL_GRAV10;
    String OF_TOTAL_GRAV5;
    String OF_TOTAL_EXENTA;
    String OF_TOTAL_IVA;
    String OF_IDFORMACOBRO;
    String OF_LETRAS;
    String OF_OBSERVACION;
    String OF_USU_ALTA;
    String OF_FEC_ALTA;
    String OF_IDCLINICA_AUX; // VARIABLE AUXILIAR PARA LA PAGINA DE VER FACTURA PARA GUARDAR EL IDCLINICA DEL IDPERSONA PARA MOSTRAR EL IDCLINICA DE LA FACTURA Y DE LA PERSONA 

    public BeanFacturaCab() {
    }

    public BeanFacturaCab(int OF_IDFACTURA, int OF_IDCLINICA, String OF_NROFACTURA, String OF_TIPOFACTURA, int OF_IDCLIENTE, String OF_FECHAFACTURA, String OF_IDARQUEO, String OF_ESTADO, String OF_TOTAL_FACTURA, String OF_TOTAL_DCTO, String OF_TOTAL_IVA10, String OF_TOTAL_IVA5, String OF_TOTAL_GRAV10, String OF_TOTAL_GRAV5, String OF_TOTAL_EXENTA, String OF_TOTAL_IVA, String OF_IDFORMACOBRO, String OF_LETRAS, String OF_OBSERVACION, String OF_USU_ALTA, String OF_FEC_ALTA) {
        this.OF_IDFACTURA = OF_IDFACTURA;
        this.OF_IDCLINICA = OF_IDCLINICA;
        this.OF_NROFACTURA = OF_NROFACTURA;
        this.OF_TIPOFACTURA = OF_TIPOFACTURA;
        this.OF_IDCLIENTE = OF_IDCLIENTE;
        this.OF_FECHAFACTURA = OF_FECHAFACTURA;
        this.OF_IDARQUEO = OF_IDARQUEO;
        this.OF_ESTADO = OF_ESTADO;
        this.OF_TOTAL_FACTURA = OF_TOTAL_FACTURA;
        this.OF_TOTAL_DCTO = OF_TOTAL_DCTO;
        this.OF_TOTAL_IVA10 = OF_TOTAL_IVA10;
        this.OF_TOTAL_IVA5 = OF_TOTAL_IVA5;
        this.OF_TOTAL_GRAV10 = OF_TOTAL_GRAV10;
        this.OF_TOTAL_GRAV5 = OF_TOTAL_GRAV5;
        this.OF_TOTAL_EXENTA = OF_TOTAL_EXENTA;
        this.OF_TOTAL_IVA = OF_TOTAL_IVA;
        this.OF_IDFORMACOBRO = OF_IDFORMACOBRO;
        this.OF_LETRAS = OF_LETRAS;
        this.OF_OBSERVACION = OF_OBSERVACION;
        this.OF_USU_ALTA = OF_USU_ALTA;
        this.OF_FEC_ALTA = OF_FEC_ALTA;
    }

    
    
    
    public String getOF_IDCLINICA_AUX() {
        return OF_IDCLINICA_AUX;
    }

    public void setOF_IDCLINICA_AUX(String OF_IDCLINICA_AUX) {
        this.OF_IDCLINICA_AUX = OF_IDCLINICA_AUX;
    }
    
    
    
    public int getOF_IDFACTURA() {
        return OF_IDFACTURA;
    }

    public void setOF_IDFACTURA(int OF_IDFACTURA) {
        this.OF_IDFACTURA = OF_IDFACTURA;
    }

    public int getOF_IDCLINICA() {
        return OF_IDCLINICA;
    }

    public void setOF_IDCLINICA(int OF_IDCLINICA) {
        this.OF_IDCLINICA = OF_IDCLINICA;
    }

    public String getOF_NROFACTURA() {
        return OF_NROFACTURA;
    }

    public void setOF_NROFACTURA(String OF_NROFACTURA) {
        this.OF_NROFACTURA = OF_NROFACTURA;
    }

    public String getOF_TIPOFACTURA() {
        return OF_TIPOFACTURA;
    }

    public void setOF_TIPOFACTURA(String OF_TIPOFACTURA) {
        this.OF_TIPOFACTURA = OF_TIPOFACTURA;
    }

    public int getOF_IDCLIENTE() {
        return OF_IDCLIENTE;
    }

    public void setOF_IDCLIENTE(int OF_IDCLIENTE) {
        this.OF_IDCLIENTE = OF_IDCLIENTE;
    }

    public String getOF_FECHAFACTURA() {
        return OF_FECHAFACTURA;
    }

    public void setOF_FECHAFACTURA(String OF_FECHAFACTURA) {
        this.OF_FECHAFACTURA = OF_FECHAFACTURA;
    }

    public String getOF_IDARQUEO() {
        return OF_IDARQUEO;
    }

    public void setOF_IDARQUEO(String OF_IDARQUEO) {
        this.OF_IDARQUEO = OF_IDARQUEO;
    }

    public String getOF_ESTADO() {
        return OF_ESTADO;
    }

    public void setOF_ESTADO(String OF_ESTADO) {
        this.OF_ESTADO = OF_ESTADO;
    }

    public String getOF_TOTAL_FACTURA() {
        return OF_TOTAL_FACTURA;
    }

    public void setOF_TOTAL_FACTURA(String OF_TOTAL_FACTURA) {
        this.OF_TOTAL_FACTURA = OF_TOTAL_FACTURA;
    }

    public String getOF_TOTAL_DCTO() {
        return OF_TOTAL_DCTO;
    }

    public void setOF_TOTAL_DCTO(String OF_TOTAL_DCTO) {
        this.OF_TOTAL_DCTO = OF_TOTAL_DCTO;
    }

    public String getOF_TOTAL_IVA10() {
        return OF_TOTAL_IVA10;
    }

    public void setOF_TOTAL_IVA10(String OF_TOTAL_IVA10) {
        this.OF_TOTAL_IVA10 = OF_TOTAL_IVA10;
    }

    public String getOF_TOTAL_IVA5() {
        return OF_TOTAL_IVA5;
    }

    public void setOF_TOTAL_IVA5(String OF_TOTAL_IVA5) {
        this.OF_TOTAL_IVA5 = OF_TOTAL_IVA5;
    }

    public String getOF_TOTAL_GRAV10() {
        return OF_TOTAL_GRAV10;
    }

    public void setOF_TOTAL_GRAV10(String OF_TOTAL_GRAV10) {
        this.OF_TOTAL_GRAV10 = OF_TOTAL_GRAV10;
    }

    public String getOF_TOTAL_GRAV5() {
        return OF_TOTAL_GRAV5;
    }

    public void setOF_TOTAL_GRAV5(String OF_TOTAL_GRAV5) {
        this.OF_TOTAL_GRAV5 = OF_TOTAL_GRAV5;
    }

    public String getOF_TOTAL_EXENTA() {
        return OF_TOTAL_EXENTA;
    }

    public void setOF_TOTAL_EXENTA(String OF_TOTAL_EXENTA) {
        this.OF_TOTAL_EXENTA = OF_TOTAL_EXENTA;
    }

    public String getOF_TOTAL_IVA() {
        return OF_TOTAL_IVA;
    }

    public void setOF_TOTAL_IVA(String OF_TOTAL_IVA) {
        this.OF_TOTAL_IVA = OF_TOTAL_IVA;
    }

    public String getOF_IDFORMACOBRO() {
        return OF_IDFORMACOBRO;
    }

    public void setOF_IDFORMACOBRO(String OF_IDFORMACOBRO) {
        this.OF_IDFORMACOBRO = OF_IDFORMACOBRO;
    }

    public String getOF_LETRAS() {
        return OF_LETRAS;
    }

    public void setOF_LETRAS(String OF_LETRAS) {
        this.OF_LETRAS = OF_LETRAS;
    }

    public String getOF_OBSERVACION() {
        return OF_OBSERVACION;
    }

    public void setOF_OBSERVACION(String OF_OBSERVACION) {
        this.OF_OBSERVACION = OF_OBSERVACION;
    }

    public String getOF_USU_ALTA() {
        return OF_USU_ALTA;
    }

    public void setOF_USU_ALTA(String OF_USU_ALTA) {
        this.OF_USU_ALTA = OF_USU_ALTA;
    }

    public String getOF_FEC_ALTA() {
        return OF_FEC_ALTA;
    }

    public void setOF_FEC_ALTA(String OF_FEC_ALTA) {
        this.OF_FEC_ALTA = OF_FEC_ALTA;
    }

    
    
} // END CLASS 