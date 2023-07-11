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
public class BeanPlanHorarioDet {
    
    // tabla:   ope_plan_horario_det 
    String OPHD_IDPLANHORARIO;
    String OPHD_ITEM;
    String OPHD_TURNO;
    String OPHD_DIA;
    String OPHD_DESDE;
    String OPHD_HASTA;
    String OPHD_USU_ALTA;
    String OPHD_FEC_ALTA;
    String OPHD_ESTADO;

    
    public BeanPlanHorarioDet() {
    }

    public BeanPlanHorarioDet(String OPHD_IDPLANHORARIO, String OPHD_ITEM, String OPHD_TURNO, String OPHD_DIA, String OPHD_DESDE, String OPHD_HASTA, String OPHD_USU_ALTA, String OPHD_FEC_ALTA, String OPHD_ESTADO) {
        this.OPHD_IDPLANHORARIO = OPHD_IDPLANHORARIO;
        this.OPHD_ITEM = OPHD_ITEM;
        this.OPHD_TURNO = OPHD_TURNO;
        this.OPHD_DIA = OPHD_DIA;
        this.OPHD_DESDE = OPHD_DESDE;
        this.OPHD_HASTA = OPHD_HASTA;
        this.OPHD_USU_ALTA = OPHD_USU_ALTA;
        this.OPHD_FEC_ALTA = OPHD_FEC_ALTA;
        this.OPHD_ESTADO = OPHD_ESTADO;
    }

    
    public String getOPHD_IDPLANHORARIO() {
        return OPHD_IDPLANHORARIO;
    }

    public void setOPHD_IDPLANHORARIO(String OPHD_IDPLANHORARIO) {
        this.OPHD_IDPLANHORARIO = OPHD_IDPLANHORARIO;
    }

    public String getOPHD_ITEM() {
        return OPHD_ITEM;
    }

    public void setOPHD_ITEM(String OPHD_ITEM) {
        this.OPHD_ITEM = OPHD_ITEM;
    }

    public String getOPHD_TURNO() {
        return OPHD_TURNO;
    }

    public void setOPHD_TURNO(String OPHD_TURNO) {
        this.OPHD_TURNO = OPHD_TURNO;
    }

    public String getOPHD_DIA() {
        return OPHD_DIA;
    }

    public void setOPHD_DIA(String OPHD_DIA) {
        this.OPHD_DIA = OPHD_DIA;
    }

    public String getOPHD_DESDE() {
        return OPHD_DESDE;
    }

    public void setOPHD_DESDE(String OPHD_DESDE) {
        this.OPHD_DESDE = OPHD_DESDE;
    }

    public String getOPHD_HASTA() {
        return OPHD_HASTA;
    }

    public void setOPHD_HASTA(String OPHD_HASTA) {
        this.OPHD_HASTA = OPHD_HASTA;
    }

    public String getOPHD_USU_ALTA() {
        return OPHD_USU_ALTA;
    }

    public void setOPHD_USU_ALTA(String OPHD_USU_ALTA) {
        this.OPHD_USU_ALTA = OPHD_USU_ALTA;
    }

    public String getOPHD_FEC_ALTA() {
        return OPHD_FEC_ALTA;
    }

    public void setOPHD_FEC_ALTA(String OPHD_FEC_ALTA) {
        this.OPHD_FEC_ALTA = OPHD_FEC_ALTA;
    }

    public String getOPHD_ESTADO() {
        return OPHD_ESTADO;
    }

    public void setOPHD_ESTADO(String OPHD_ESTADO) {
        this.OPHD_ESTADO = OPHD_ESTADO;
    }
    
    
}