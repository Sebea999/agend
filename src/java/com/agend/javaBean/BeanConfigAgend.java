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
public class BeanConfigAgend {
    
    // tabla:   sys_config_agend 
    String SCA_IDCONFIGAGEND;
    String SCA_DESC_CONFIG;
    String SCA_MAX_AGEND;
    String SCA_DESC_AGEND;
    String SCA_ESTADO;
    String SCA_USU_ALTA;
    String SCA_FEC_ALTA;
    String SCA_IDCLINICA;
    String SCA_INTERV_MIN_ENTRE_AGEND; // INTERVALO DE MINUTOS ENTRE UN AGENDAMIENTO Y OTRO, A LA HORA DE CARGAR EL AGENDAMIENTO 
//    String SCA_MODO_PAGO; // MODO DE PAGO DEL AGENDAMIENTO, POR MEDICO O POR ESPECIALIDAD 
    
    public BeanConfigAgend() {
    }
    
    public BeanConfigAgend(String SCA_IDCONFIGAGEND, String SCA_DESC_CONFIG, String SCA_MAX_AGEND, String SCA_DESC_AGEND, String SCA_ESTADO, String SCA_USU_ALTA, String SCA_FEC_ALTA, String SCA_IDCLINICA, String SCA_INTERV_MIN_ENTRE_AGEND) {
        this.SCA_IDCONFIGAGEND = SCA_IDCONFIGAGEND;
        this.SCA_DESC_CONFIG = SCA_DESC_CONFIG;
        this.SCA_MAX_AGEND = SCA_MAX_AGEND;
        this.SCA_DESC_AGEND = SCA_DESC_AGEND;
        this.SCA_ESTADO = SCA_ESTADO;
        this.SCA_USU_ALTA = SCA_USU_ALTA;
        this.SCA_FEC_ALTA = SCA_FEC_ALTA;
        this.SCA_IDCLINICA = SCA_IDCLINICA;
        this.SCA_INTERV_MIN_ENTRE_AGEND = SCA_INTERV_MIN_ENTRE_AGEND;
    }

    public String getSCA_IDCONFIGAGEND() {
        return SCA_IDCONFIGAGEND;
    }

    public void setSCA_IDCONFIGAGEND(String SCA_IDCONFIGAGEND) {
        this.SCA_IDCONFIGAGEND = SCA_IDCONFIGAGEND;
    }

    public String getSCA_DESC_CONFIG() {
        return SCA_DESC_CONFIG;
    }

    public void setSCA_DESC_CONFIG(String SCA_DESC_CONFIG) {
        this.SCA_DESC_CONFIG = SCA_DESC_CONFIG;
    }

    public String getSCA_MAX_AGEND() {
        return SCA_MAX_AGEND;
    }

    public void setSCA_MAX_AGEND(String SCA_MAX_AGEND) {
        this.SCA_MAX_AGEND = SCA_MAX_AGEND;
    }

    public String getSCA_DESC_AGEND() {
        return SCA_DESC_AGEND;
    }

    public void setSCA_DESC_AGEND(String SCA_DESC_AGEND) {
        this.SCA_DESC_AGEND = SCA_DESC_AGEND;
    }

    public String getSCA_ESTADO() {
        return SCA_ESTADO;
    }

    public void setSCA_ESTADO(String SCA_ESTADO) {
        this.SCA_ESTADO = SCA_ESTADO;
    }

    public String getSCA_USU_ALTA() {
        return SCA_USU_ALTA;
    }

    public void setSCA_USU_ALTA(String SCA_USU_ALTA) {
        this.SCA_USU_ALTA = SCA_USU_ALTA;
    }

    public String getSCA_FEC_ALTA() {
        return SCA_FEC_ALTA;
    }

    public void setSCA_FEC_ALTA(String SCA_FEC_ALTA) {
        this.SCA_FEC_ALTA = SCA_FEC_ALTA;
    }

    public String getSCA_IDCLINICA() {
        return SCA_IDCLINICA;
    }

    public void setSCA_IDCLINICA(String SCA_IDCLINICA) {
        this.SCA_IDCLINICA = SCA_IDCLINICA;
    }

    public String getSCA_INTERV_MIN_ENTRE_AGEND() {
        return SCA_INTERV_MIN_ENTRE_AGEND;
    }

    public void setSCA_INTERV_MIN_ENTRE_AGEND(String SCA_INTERV_MIN_ENTRE_AGEND) {
        this.SCA_INTERV_MIN_ENTRE_AGEND = SCA_INTERV_MIN_ENTRE_AGEND;
    }
    
}