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
public class BeanAtencionPacienteDet extends BeanServicio {

    // tabla :  ope_atencion_paciente 
    String OAPD_IDATENCIONPAC;
    String OAPD_ITEM;
    String OAPD_IDSERVICIO;
    String OAPD_MONTO;
    String OAPD_ESTADO;
    String OAPD_USU_ALTA;
    String OAPD_FEC_ALTA;
    

    public BeanAtencionPacienteDet() {
    }

    public BeanAtencionPacienteDet(String OAPD_IDATENCIONPAC, String OAPD_ITEM, String OAPD_IDSERVICIO, String OAPD_MONTO, String OAPD_ESTADO, String OAPD_USU_ALTA, String OAPD_FEC_ALTA) {
        this.OAPD_IDATENCIONPAC = OAPD_IDATENCIONPAC;
        this.OAPD_ITEM = OAPD_ITEM;
        this.OAPD_IDSERVICIO = OAPD_IDSERVICIO;
        this.OAPD_MONTO = OAPD_MONTO;
        this.OAPD_ESTADO = OAPD_ESTADO;
        this.OAPD_USU_ALTA = OAPD_USU_ALTA;
        this.OAPD_FEC_ALTA = OAPD_FEC_ALTA;
    }

    
    public String getOAPD_IDATENCIONPAC() {
        return OAPD_IDATENCIONPAC;
    }

    public void setOAPD_IDATENCIONPAC(String OAPD_IDATENCIONPAC) {
        this.OAPD_IDATENCIONPAC = OAPD_IDATENCIONPAC;
    }

    public String getOAPD_ITEM() {
        return OAPD_ITEM;
    }

    public void setOAPD_ITEM(String OAPD_ITEM) {
        this.OAPD_ITEM = OAPD_ITEM;
    }

    public String getOAPD_IDSERVICIO() {
        return OAPD_IDSERVICIO;
    }

    public void setOAPD_IDSERVICIO(String OAPD_IDSERVICIO) {
        this.OAPD_IDSERVICIO = OAPD_IDSERVICIO;
    }

    public String getOAPD_MONTO() {
        return OAPD_MONTO;
    }

    public void setOAPD_MONTO(String OAPD_MONTO) {
        this.OAPD_MONTO = OAPD_MONTO;
    }

    public String getOAPD_ESTADO() {
        return OAPD_ESTADO;
    }

    public void setOAPD_ESTADO(String OAPD_ESTADO) {
        this.OAPD_ESTADO = OAPD_ESTADO;
    }

    public String getOAPD_USU_ALTA() {
        return OAPD_USU_ALTA;
    }

    public void setOAPD_USU_ALTA(String OAPD_USU_ALTA) {
        this.OAPD_USU_ALTA = OAPD_USU_ALTA;
    }

    public String getOAPD_FEC_ALTA() {
        return OAPD_FEC_ALTA;
    }

    public void setOAPD_FEC_ALTA(String OAPD_FEC_ALTA) {
        this.OAPD_FEC_ALTA = OAPD_FEC_ALTA;
    }
    
    
}