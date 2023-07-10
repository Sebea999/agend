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
public class BeanPlanHorario extends BeanPlanHorarioDet {
    
    // tabla:  ope_plan_horario
    String OPH_IDPLANHORARIO;
    String OPH_IDCLINICA;
    String OPH_IDMEDICO;
    String OPH_USU_ALTA;
    String OPH_FEC_ALTA;
    String OPH_ESTADO;

    
    public BeanPlanHorario() {
    }

    public BeanPlanHorario(String OPH_IDPLANHORARIO, String OPH_IDCLINICA, String OPH_IDMEDICO, String OPH_USU_ALTA, String OPH_FEC_ALTA, String OPH_ESTADO) {
        this.OPH_IDPLANHORARIO = OPH_IDPLANHORARIO;
        this.OPH_IDCLINICA = OPH_IDCLINICA;
        this.OPH_IDMEDICO = OPH_IDMEDICO;
        this.OPH_USU_ALTA = OPH_USU_ALTA;
        this.OPH_FEC_ALTA = OPH_FEC_ALTA;
        this.OPH_ESTADO = OPH_ESTADO;
    }

    public String getOPH_IDPLANHORARIO() {
        return OPH_IDPLANHORARIO;
    }

    public void setOPH_IDPLANHORARIO(String OPH_IDPLANHORARIO) {
        this.OPH_IDPLANHORARIO = OPH_IDPLANHORARIO;
    }

    public String getOPH_IDCLINICA() {
        return OPH_IDCLINICA;
    }

    public void setOPH_IDCLINICA(String OPH_IDCLINICA) {
        this.OPH_IDCLINICA = OPH_IDCLINICA;
    }

    public String getOPH_IDMEDICO() {
        return OPH_IDMEDICO;
    }

    public void setOPH_IDMEDICO(String OPH_IDMEDICO) {
        this.OPH_IDMEDICO = OPH_IDMEDICO;
    }

    public String getOPH_USU_ALTA() {
        return OPH_USU_ALTA;
    }

    public void setOPH_USU_ALTA(String OPH_USU_ALTA) {
        this.OPH_USU_ALTA = OPH_USU_ALTA;
    }

    public String getOPH_FEC_ALTA() {
        return OPH_FEC_ALTA;
    }

    public void setOPH_FEC_ALTA(String OPH_FEC_ALTA) {
        this.OPH_FEC_ALTA = OPH_FEC_ALTA;
    }

    public String getOPH_ESTADO() {
        return OPH_ESTADO;
    }

    public void setOPH_ESTADO(String OPH_ESTADO) {
        this.OPH_ESTADO = OPH_ESTADO;
    }

    
}