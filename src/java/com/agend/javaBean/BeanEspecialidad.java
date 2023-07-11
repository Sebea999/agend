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
public class BeanEspecialidad {
    
    // tabla: rh_especialidad 
    String RHE_IDESPECIALIDAD;
    String RHE_DESC_ESPECIALIDAD;
    String RHE_ESTADO;
    String RHE_MONTO;
    String RHE_IVA;

    public BeanEspecialidad() {
    }

    public BeanEspecialidad(String RHE_IDESPECIALIDAD, String RHE_DESC_ESPECIALIDAD, String RHE_ESTADO, String RHE_MONTO, String RHE_IVA) {
        this.RHE_IDESPECIALIDAD = RHE_IDESPECIALIDAD;
        this.RHE_DESC_ESPECIALIDAD = RHE_DESC_ESPECIALIDAD;
        this.RHE_ESTADO = RHE_ESTADO;
        this.RHE_MONTO = RHE_MONTO;
        this.RHE_IVA = RHE_IVA;
    }

    public String getRHE_IDESPECIALIDAD() {
        return RHE_IDESPECIALIDAD;
    }

    public void setRHE_IDESPECIALIDAD(String RHE_IDESPECIALIDAD) {
        this.RHE_IDESPECIALIDAD = RHE_IDESPECIALIDAD;
    }

    public String getRHE_DESC_ESPECIALIDAD() {
        return RHE_DESC_ESPECIALIDAD;
    }

    public void setRHE_DESC_ESPECIALIDAD(String RHE_DESC_ESPECIALIDAD) {
        this.RHE_DESC_ESPECIALIDAD = RHE_DESC_ESPECIALIDAD;
    }

    public String getRHE_ESTADO() {
        return RHE_ESTADO;
    }

    public void setRHE_ESTADO(String RHE_ESTADO) {
        this.RHE_ESTADO = RHE_ESTADO;
    }

    public String getRHE_MONTO() {
        return RHE_MONTO;
    }

    public void setRHE_MONTO(String RHE_MONTO) {
        this.RHE_MONTO = RHE_MONTO;
    }

    public String getRHE_IVA() {
        return RHE_IVA;
    }

    public void setRHE_IVA(String RHE_IVA) {
        this.RHE_IVA = RHE_IVA;
    }
    
    
}