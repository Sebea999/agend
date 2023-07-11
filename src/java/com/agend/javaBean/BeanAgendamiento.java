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
public class BeanAgendamiento extends BeanAgendamientoDet {
    
    
    // tabla:  ope_agendamiento 
    String OA_IDAGENDAMIENTO;
    String OA_IDCLINICA;
    String OA_IDMEDICO;
    String OA_IDESPECIALIDAD;
    String OA_IDPERSONA;
    String OA_FECHA;
    String OA_TURNO;
    String OA_DESDE;
    String OA_HASTA;
    String OA_ESTADO;
    String OA_USU_ALTA;
    String OA_FEC_ALTA;
    
    
    public BeanAgendamiento() {
    }

    public BeanAgendamiento(String OA_IDAGENDAMIENTO, String OA_IDCLINICA, String OA_IDMEDICO, String OA_IDESPECIALIDAD, String OA_ESTADO, String OA_USU_ALTA, String OA_FEC_ALTA) {
        this.OA_IDAGENDAMIENTO = OA_IDAGENDAMIENTO;
        this.OA_IDCLINICA = OA_IDCLINICA;
        this.OA_IDMEDICO = OA_IDMEDICO;
        this.OA_IDESPECIALIDAD = OA_IDESPECIALIDAD;
        this.OA_ESTADO = OA_ESTADO;
        this.OA_USU_ALTA = OA_USU_ALTA;
        this.OA_FEC_ALTA = OA_FEC_ALTA;
    }

    public BeanAgendamiento(String OA_IDAGENDAMIENTO, String OA_IDCLINICA, String OA_IDMEDICO, String OA_IDESPECIALIDAD, String OA_IDPERSONA, String OA_FECHA, String OA_TURNO, String OA_DESDE, String OA_HASTA, String OA_ESTADO, String OA_USU_ALTA, String OA_FEC_ALTA, String OAD_IDAGENDAMIENTO, String OAD_ITEM, String OAD_FECHA_AGEN, String OAD_HORA, String OAD_IDPACIENTE, String OAD_NROPACIENTEFICHA, String OAD_MOTIVO, String OAD_SEGUROMED, String OAD_IDSEGUROMED, String OAD_VISITATIPO, String OAD_COMENTARIO, String OAD_ESTADO, String OAD_USU_ALTA, String OAD_FEC_ALTA, String OAD_FEC_ATENCION, String OAD_IDFACTURA, String OAD_AGENDADO, String OAD_IDCONFIGAGEND, String OAD_MOTIVO_CONSULTA) {
        super(OAD_IDAGENDAMIENTO, OAD_ITEM, OAD_FECHA_AGEN, OAD_HORA, OAD_IDPACIENTE, OAD_NROPACIENTEFICHA, OAD_MOTIVO, OAD_SEGUROMED, OAD_IDSEGUROMED, OAD_VISITATIPO, OAD_COMENTARIO, OAD_ESTADO, OAD_USU_ALTA, OAD_FEC_ALTA, OAD_FEC_ATENCION, OAD_IDFACTURA, OAD_AGENDADO, OAD_IDCONFIGAGEND, OAD_MOTIVO_CONSULTA);
        this.OA_IDAGENDAMIENTO = OA_IDAGENDAMIENTO;
        this.OA_IDCLINICA = OA_IDCLINICA;
        this.OA_IDMEDICO = OA_IDMEDICO;
        this.OA_IDESPECIALIDAD = OA_IDESPECIALIDAD;
        this.OA_IDPERSONA = OA_IDPERSONA;
        this.OA_FECHA = OA_FECHA;
        this.OA_TURNO = OA_TURNO;
        this.OA_DESDE = OA_DESDE;
        this.OA_HASTA = OA_HASTA;
        this.OA_ESTADO = OA_ESTADO;
        this.OA_USU_ALTA = OA_USU_ALTA;
        this.OA_FEC_ALTA = OA_FEC_ALTA;
    }

    

    public String getOA_IDAGENDAMIENTO() {
        return OA_IDAGENDAMIENTO;
    }

    public void setOA_IDAGENDAMIENTO(String OA_IDAGENDAMIENTO) {
        this.OA_IDAGENDAMIENTO = OA_IDAGENDAMIENTO;
    }

    public String getOA_IDCLINICA() {
        return OA_IDCLINICA;
    }

    public void setOA_IDCLINICA(String OA_IDCLINICA) {
        this.OA_IDCLINICA = OA_IDCLINICA;
    }

    public String getOA_IDMEDICO() {
        return OA_IDMEDICO;
    }

    public void setOA_IDMEDICO(String OA_IDMEDICO) {
        this.OA_IDMEDICO = OA_IDMEDICO;
    }

    public String getOA_IDESPECIALIDAD() {
        return OA_IDESPECIALIDAD;
    }

    public void setOA_IDESPECIALIDAD(String OA_IDESPECIALIDAD) {
        this.OA_IDESPECIALIDAD = OA_IDESPECIALIDAD;
    }

    public String getOA_IDPERSONA() {
        return OA_IDPERSONA;
    }

    public void setOA_IDPERSONA(String OA_IDPERSONA) {
        this.OA_IDPERSONA = OA_IDPERSONA;
    }

    public String getOA_FECHA() {
        return OA_FECHA;
    }

    public void setOA_FECHA(String OA_FECHA) {
        this.OA_FECHA = OA_FECHA;
    }

    public String getOA_TURNO() {
        return OA_TURNO;
    }

    public void setOA_TURNO(String OA_TURNO) {
        this.OA_TURNO = OA_TURNO;
    }

    public String getOA_DESDE() {
        return OA_DESDE;
    }

    public void setOA_DESDE(String OA_DESDE) {
        this.OA_DESDE = OA_DESDE;
    }

    public String getOA_HASTA() {
        return OA_HASTA;
    }

    public void setOA_HASTA(String OA_HASTA) {
        this.OA_HASTA = OA_HASTA;
    }

    public String getOA_ESTADO() {
        return OA_ESTADO;
    }

    public void setOA_ESTADO(String OA_ESTADO) {
        this.OA_ESTADO = OA_ESTADO;
    }

    public String getOA_USU_ALTA() {
        return OA_USU_ALTA;
    }

    public void setOA_USU_ALTA(String OA_USU_ALTA) {
        this.OA_USU_ALTA = OA_USU_ALTA;
    }

    public String getOA_FEC_ALTA() {
        return OA_FEC_ALTA;
    }

    public void setOA_FEC_ALTA(String OA_FEC_ALTA) {
        this.OA_FEC_ALTA = OA_FEC_ALTA;
    }

    
}