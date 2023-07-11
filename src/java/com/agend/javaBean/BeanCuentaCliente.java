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
public class BeanCuentaCliente extends BeanAgendamiento {
    
    // TABLA : cc_cuenta_cliente 
    String CC_IDCUENTACLIENTE;
    String CC_IDPERSONA;
    String CC_IDAGENDAMIENTO;
    String CC_ITEM_AGEN;
    String CC_NROCUOTA;
    String CC_FEC_VENCIMIENTO;
//    String CC_INTERES;
    String CC_MONTO;
    String CC_SALDO;
    String CC_COMENTARIO;
    String CC_ESTADO;
    String CC_USU_ALTA;
    String CC_FEC_ALTA;

    public BeanCuentaCliente() {
    }

    public BeanCuentaCliente(String CC_IDCUENTACLIENTE, String CC_IDPERSONA, String CC_IDAGENDAMIENTO, String CC_ITEM_AGEN, String CC_NROCUOTA, String CC_FEC_VENCIMIENTO, /*String CC_INTERES,*/ String CC_MONTO, String CC_SALDO, String CC_COMENTARIO, String CC_ESTADO, String CC_USU_ALTA, String CC_FEC_ALTA) {
        this.CC_IDCUENTACLIENTE = CC_IDCUENTACLIENTE;
        this.CC_IDPERSONA = CC_IDPERSONA;
        this.CC_IDAGENDAMIENTO = CC_IDAGENDAMIENTO;
        this.CC_ITEM_AGEN = CC_ITEM_AGEN;
        this.CC_NROCUOTA = CC_NROCUOTA;
        this.CC_FEC_VENCIMIENTO = CC_FEC_VENCIMIENTO;
//        this.CC_INTERES = CC_INTERES;
        this.CC_MONTO = CC_MONTO;
        this.CC_SALDO = CC_SALDO;
        this.CC_COMENTARIO = CC_COMENTARIO;
        this.CC_ESTADO = CC_ESTADO;
        this.CC_USU_ALTA = CC_USU_ALTA;
        this.CC_FEC_ALTA = CC_FEC_ALTA;
    }
    
    

    public String getCC_IDCUENTACLIENTE() {
        return CC_IDCUENTACLIENTE;
    }

    public void setCC_IDCUENTACLIENTE(String CC_IDCUENTACLIENTE) {
        this.CC_IDCUENTACLIENTE = CC_IDCUENTACLIENTE;
    }

    public String getCC_IDPERSONA() {
        return CC_IDPERSONA;
    }

    public void setCC_IDPERSONA(String CC_IDPERSONA) {
        this.CC_IDPERSONA = CC_IDPERSONA;
    }

    public String getCC_IDAGENDAMIENTO() {
        return CC_IDAGENDAMIENTO;
    }

    public void setCC_IDAGENDAMIENTO(String CC_IDAGENDAMIENTO) {
        this.CC_IDAGENDAMIENTO = CC_IDAGENDAMIENTO;
    }

    public String getCC_ITEM_AGEN() {
        return CC_ITEM_AGEN;
    }

    public void setCC_ITEM_AGEN(String CC_ITEM_AGEN) {
        this.CC_ITEM_AGEN = CC_ITEM_AGEN;
    }

    public String getCC_NROCUOTA() {
        return CC_NROCUOTA;
    }

    public void setCC_NROCUOTA(String CC_NROCUOTA) {
        this.CC_NROCUOTA = CC_NROCUOTA;
    }

    public String getCC_FEC_VENCIMIENTO() {
        return CC_FEC_VENCIMIENTO;
    }

    public void setCC_FEC_VENCIMIENTO(String CC_FEC_VENCIMIENTO) {
        this.CC_FEC_VENCIMIENTO = CC_FEC_VENCIMIENTO;
    }

//    public String getCC_INTERES() {
//        return CC_INTERES;
//    }
//
//    public void setCC_INTERES(String CC_INTERES) {
//        this.CC_INTERES = CC_INTERES;
//    }

    public String getCC_MONTO() {
        return CC_MONTO;
    }

    public void setCC_MONTO(String CC_MONTO) {
        this.CC_MONTO = CC_MONTO;
    }

    public String getCC_SALDO() {
        return CC_SALDO;
    }

    public void setCC_SALDO(String CC_SALDO) {
        this.CC_SALDO = CC_SALDO;
    }

    public String getCC_COMENTARIO() {
        return CC_COMENTARIO;
    }

    public void setCC_COMENTARIO(String CC_COMENTARIO) {
        this.CC_COMENTARIO = CC_COMENTARIO;
    }

    public String getCC_ESTADO() {
        return CC_ESTADO;
    }

    public void setCC_ESTADO(String CC_ESTADO) {
        this.CC_ESTADO = CC_ESTADO;
    }

    public String getCC_USU_ALTA() {
        return CC_USU_ALTA;
    }

    public void setCC_USU_ALTA(String CC_USU_ALTA) {
        this.CC_USU_ALTA = CC_USU_ALTA;
    }

    public String getCC_FEC_ALTA() {
        return CC_FEC_ALTA;
    }

    public void setCC_FEC_ALTA(String CC_FEC_ALTA) {
        this.CC_FEC_ALTA = CC_FEC_ALTA;
    }
    
    
} // end class 