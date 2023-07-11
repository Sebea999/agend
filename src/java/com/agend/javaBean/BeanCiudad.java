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
public class BeanCiudad {
    
    // TABLA:   rh_ciudad 
    String RC_IDCIUDAD;
    String RC_DESC_CIUDAD;
    String RC_IDPAIS;
    String RC_ESTADO;
    String RC_USU_ALTA;
    String RC_FEC_ALTA;
    
    
    @Override
    public String toString() {
        if (getRC_ESTADO().equalsIgnoreCase("A")) {
            return "ACTIVO";
        } else if (getRC_ESTADO().equalsIgnoreCase("I")) {
            return "INACTIVO";
        } else {
            return "ANULADO";
        }
    }

    
    public BeanCiudad() {
    }

    public BeanCiudad(String RC_IDCIUDAD, String RC_DESC_CIUDAD, String RC_IDPAIS, String RC_ESTADO, String RC_USU_ALTA, String RC_FEC_ALTA) {
        this.RC_IDCIUDAD = RC_IDCIUDAD;
        this.RC_DESC_CIUDAD = RC_DESC_CIUDAD;
        this.RC_IDPAIS = RC_IDPAIS;
        this.RC_ESTADO = RC_ESTADO;
        this.RC_USU_ALTA = RC_USU_ALTA;
        this.RC_FEC_ALTA = RC_FEC_ALTA;
    }

    
    
    public String getRC_IDCIUDAD() {
        return RC_IDCIUDAD;
    }

    public void setRC_IDCIUDAD(String RC_IDCIUDAD) {
        this.RC_IDCIUDAD = RC_IDCIUDAD;
    }

    public String getRC_DESC_CIUDAD() {
        return RC_DESC_CIUDAD;
    }

    public void setRC_DESC_CIUDAD(String RC_DESC_CIUDAD) {
        this.RC_DESC_CIUDAD = RC_DESC_CIUDAD;
    }

    public String getRC_IDPAIS() {
        return RC_IDPAIS;
    }

    public void setRC_IDPAIS(String RC_IDPAIS) {
        this.RC_IDPAIS = RC_IDPAIS;
    }

    public String getRC_ESTADO() {
        return RC_ESTADO;
    }

    public void setRC_ESTADO(String RC_ESTADO) {
        this.RC_ESTADO = RC_ESTADO;
    }

    public String getRC_USU_ALTA() {
        return RC_USU_ALTA;
    }

    public void setRC_USU_ALTA(String RC_USU_ALTA) {
        this.RC_USU_ALTA = RC_USU_ALTA;
    }

    public String getRC_FEC_ALTA() {
        return RC_FEC_ALTA;
    }

    public void setRC_FEC_ALTA(String RC_FEC_ALTA) {
        this.RC_FEC_ALTA = RC_FEC_ALTA;
    }
    
    
}