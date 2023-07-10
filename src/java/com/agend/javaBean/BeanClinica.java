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
public class BeanClinica {
    
    // tabla:   ope_clinica 
    int OC_IDCLINICA;
    String OC_DESC_CLINICA;
    String OC_DIRECCION;
    String OC_TELEFONO;
    String OC_EMAIL;
    String OC_ESTADO;
    String OC_USU_ALTA;
    String OC_FEC_ALTA;

    public BeanClinica() {
    }

    public BeanClinica(int OC_IDCLINICA, String OC_DESC_CLINICA, String OC_DIRECCION, String OC_TELEFONO, String OC_EMAIL, String OC_ESTADO, String OC_USU_ALTA, String OC_FEC_ALTA) {
        this.OC_IDCLINICA = OC_IDCLINICA;
        this.OC_DESC_CLINICA = OC_DESC_CLINICA;
        this.OC_DIRECCION = OC_DIRECCION;
        this.OC_TELEFONO = OC_TELEFONO;
        this.OC_EMAIL = OC_EMAIL;
        this.OC_ESTADO = OC_ESTADO;
        this.OC_USU_ALTA = OC_USU_ALTA;
        this.OC_FEC_ALTA = OC_FEC_ALTA;
    }

    public int getOC_IDCLINICA() {
        return OC_IDCLINICA;
    }

    public void setOC_IDCLINICA(int OC_IDCLINICA) {
        this.OC_IDCLINICA = OC_IDCLINICA;
    }

    public String getOC_DESC_CLINICA() {
        return OC_DESC_CLINICA;
    }

    public void setOC_DESC_CLINICA(String OC_DESC_CLINICA) {
        this.OC_DESC_CLINICA = OC_DESC_CLINICA;
    }

    public String getOC_DIRECCION() {
        return OC_DIRECCION;
    }

    public void setOC_DIRECCION(String OC_DIRECCION) {
        this.OC_DIRECCION = OC_DIRECCION;
    }

    public String getOC_TELEFONO() {
        return OC_TELEFONO;
    }

    public void setOC_TELEFONO(String OC_TELEFONO) {
        this.OC_TELEFONO = OC_TELEFONO;
    }

    public String getOC_EMAIL() {
        return OC_EMAIL;
    }

    public void setOC_EMAIL(String OC_EMAIL) {
        this.OC_EMAIL = OC_EMAIL;
    }

    public String getOC_ESTADO() {
        return OC_ESTADO;
    }

    public void setOC_ESTADO(String OC_ESTADO) {
        this.OC_ESTADO = OC_ESTADO;
    }

    public String getOC_USU_ALTA() {
        return OC_USU_ALTA;
    }

    public void setOC_USU_ALTA(String OC_USU_ALTA) {
        this.OC_USU_ALTA = OC_USU_ALTA;
    }

    public String getOC_FEC_ALTA() {
        return OC_FEC_ALTA;
    }

    public void setOC_FEC_ALTA(String OC_FEC_ALTA) {
        this.OC_FEC_ALTA = OC_FEC_ALTA;
    }

    
}