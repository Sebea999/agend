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
public class BeanServicio {
    
    // tabla:    rh_servicio 
    String RHS_IDSERVICIO;
    String RHS_DESC_SERVICIO;
    String RHS_MONTO;
    String RHS_ESTADO;
    String RHS_IVA;

    public BeanServicio() {
    }

    public BeanServicio(String RHS_IDSERVICIO, String RHS_DESC_SERVICIO, String RHS_MONTO, String RHS_ESTADO, String RHS_IVA) {
        this.RHS_IDSERVICIO = RHS_IDSERVICIO;
        this.RHS_DESC_SERVICIO = RHS_DESC_SERVICIO;
        this.RHS_MONTO = RHS_MONTO;
        this.RHS_ESTADO = RHS_ESTADO;
        this.RHS_IVA = RHS_IVA;
    }

    public String getRHS_IDSERVICIO() {
        return RHS_IDSERVICIO;
    }

    public void setRHS_IDSERVICIO(String RHS_IDSERVICIO) {
        this.RHS_IDSERVICIO = RHS_IDSERVICIO;
    }

    public String getRHS_DESC_SERVICIO() {
        return RHS_DESC_SERVICIO;
    }

    public void setRHS_DESC_SERVICIO(String RHS_DESC_SERVICIO) {
        this.RHS_DESC_SERVICIO = RHS_DESC_SERVICIO;
    }

    public String getRHS_MONTO() {
        return RHS_MONTO;
    }

    public void setRHS_MONTO(String RHS_MONTO) {
        this.RHS_MONTO = RHS_MONTO;
    }

    public String getRHS_ESTADO() {
        return RHS_ESTADO;
    }

    public void setRHS_ESTADO(String RHS_ESTADO) {
        this.RHS_ESTADO = RHS_ESTADO;
    }

    public String getRHS_IVA() {
        return RHS_IVA;
    }

    public void setRHS_IVA(String RHS_IVA) {
        this.RHS_IVA = RHS_IVA;
    }
    
    
}