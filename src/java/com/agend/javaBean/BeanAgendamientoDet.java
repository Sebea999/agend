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
public class BeanAgendamientoDet extends BeanFacturaCab {
    
    // tabla:  ope_agendamiento_det 
    String OAD_IDAGENDAMIENTO;
    String OAD_ITEM;
    String OAD_FECHA_AGEN;
    String OAD_HORA;
    String OAD_IDPACIENTE;
    String OAD_NROPACIENTEFICHA;
    String OAD_MOTIVO;
    String OAD_SEGUROMED;
    String OAD_IDSEGUROMED;
    String OAD_VISITATIPO;
    String OAD_COMENTARIO;
    String OAD_ESTADO;
    String OAD_USU_ALTA;
    String OAD_FEC_ALTA;
    String OAD_FEC_ATENCION;
    String OAD_IDFACTURA;
    String OAD_AGENDADO;
    String OAD_IDATENCION_PAC; // VARIABLE AUXILIAR QUE UTILIZO PARA PODER EN LA PAGINA DE FICHA ATENCION PACIENTE PARA SABER LOS PACIENTES DE UN AGENDAMIENTO QUE YA TIENEN LA FICHA DE ATENCION Y ASI PODER MOSTRARLE OTRO BOTON 
    String OAD_IDCONFIGAGEND; // CAMPO AUXILIAR QUE AGREGUE PARA SABER CUAL ID_CONFIG UTILIZE PARA AGARRAR EL COMENTARIO 
    String OAD_MOTIVO_CONSULTA;

    public BeanAgendamientoDet() {
    }

    public BeanAgendamientoDet(String OAD_IDAGENDAMIENTO, String OAD_ITEM, String OAD_FECHA_AGEN, String OAD_HORA, String OAD_IDPACIENTE, String OAD_NROPACIENTEFICHA, String OAD_MOTIVO, String OAD_SEGUROMED, String OAD_IDSEGUROMED, String OAD_VISITATIPO, String OAD_COMENTARIO, String OAD_ESTADO, String OAD_USU_ALTA, String OAD_FEC_ALTA, String OAD_FEC_ATENCION, String OAD_IDFACTURA, String OAD_AGENDADO, String OAD_IDCONFIGAGEND, String OAD_MOTIVO_CONSULTA) {
        this.OAD_IDAGENDAMIENTO = OAD_IDAGENDAMIENTO;
        this.OAD_ITEM = OAD_ITEM;
        this.OAD_FECHA_AGEN = OAD_FECHA_AGEN;
        this.OAD_HORA = OAD_HORA;
        this.OAD_IDPACIENTE = OAD_IDPACIENTE;
        this.OAD_NROPACIENTEFICHA = OAD_NROPACIENTEFICHA;
        this.OAD_MOTIVO = OAD_MOTIVO;
        this.OAD_SEGUROMED = OAD_SEGUROMED;
        this.OAD_IDSEGUROMED = OAD_IDSEGUROMED;
        this.OAD_VISITATIPO = OAD_VISITATIPO;
        this.OAD_COMENTARIO = OAD_COMENTARIO;
        this.OAD_ESTADO = OAD_ESTADO;
        this.OAD_USU_ALTA = OAD_USU_ALTA;
        this.OAD_FEC_ALTA = OAD_FEC_ALTA;
        this.OAD_FEC_ATENCION = OAD_FEC_ATENCION;
        this.OAD_IDFACTURA = OAD_IDFACTURA;
        this.OAD_AGENDADO = OAD_AGENDADO;
        this.OAD_IDCONFIGAGEND = OAD_IDCONFIGAGEND;
        this.OAD_MOTIVO_CONSULTA = OAD_MOTIVO_CONSULTA;
    }

    public String getOAD_IDAGENDAMIENTO() {
        return OAD_IDAGENDAMIENTO;
    }

    public void setOAD_IDAGENDAMIENTO(String OAD_IDAGENDAMIENTO) {
        this.OAD_IDAGENDAMIENTO = OAD_IDAGENDAMIENTO;
    }

    public String getOAD_ITEM() {
        return OAD_ITEM;
    }

    public void setOAD_ITEM(String OAD_ITEM) {
        this.OAD_ITEM = OAD_ITEM;
    }

    public String getOAD_IDCONFIGAGEND() {
        return OAD_IDCONFIGAGEND;
    }

    public void setOAD_IDCONFIGAGEND(String OAD_IDCONFIGAGEND) {
        this.OAD_IDCONFIGAGEND = OAD_IDCONFIGAGEND;
    }

    public String getOAD_FECHA_AGEN() {
        return OAD_FECHA_AGEN;
    }

    public void setOAD_FECHA_AGEN(String OAD_FECHA_AGEN) {
        this.OAD_FECHA_AGEN = OAD_FECHA_AGEN;
    }

    public String getOAD_HORA() {
        return OAD_HORA;
    }

    public void setOAD_HORA(String OAD_HORA) {
        this.OAD_HORA = OAD_HORA;
    }

    public String getOAD_IDPACIENTE() {
        return OAD_IDPACIENTE;
    }

    public void setOAD_IDPACIENTE(String OAD_IDPACIENTE) {
        this.OAD_IDPACIENTE = OAD_IDPACIENTE;
    }

    public String getOAD_NROPACIENTEFICHA() {
        return OAD_NROPACIENTEFICHA;
    }

    public void setOAD_NROPACIENTEFICHA(String OAD_NROPACIENTEFICHA) {
        this.OAD_NROPACIENTEFICHA = OAD_NROPACIENTEFICHA;
    }

    public String getOAD_MOTIVO() {
        return OAD_MOTIVO;
    }

    public void setOAD_MOTIVO(String OAD_MOTIVO) {
        this.OAD_MOTIVO = OAD_MOTIVO;
    }

    public String getOAD_SEGUROMED() {
        return OAD_SEGUROMED;
    }

    public void setOAD_SEGUROMED(String OAD_SEGUROMED) {
        this.OAD_SEGUROMED = OAD_SEGUROMED;
    }

    public String getOAD_IDSEGUROMED() {
        return OAD_IDSEGUROMED;
    }

    public void setOAD_IDSEGUROMED(String OAD_IDSEGUROMED) {
        this.OAD_IDSEGUROMED = OAD_IDSEGUROMED;
    }

    public String getOAD_VISITATIPO() {
        return OAD_VISITATIPO;
    }

    public void setOAD_VISITATIPO(String OAD_VISITATIPO) {
        this.OAD_VISITATIPO = OAD_VISITATIPO;
    }

    public String getOAD_COMENTARIO() {
        return OAD_COMENTARIO;
    }

    public void setOAD_COMENTARIO(String OAD_COMENTARIO) {
        this.OAD_COMENTARIO = OAD_COMENTARIO;
    }

    public String getOAD_ESTADO() {
        return OAD_ESTADO;
    }

    public void setOAD_ESTADO(String OAD_ESTADO) {
        this.OAD_ESTADO = OAD_ESTADO;
    }

    public String getOAD_USU_ALTA() {
        return OAD_USU_ALTA;
    }

    public void setOAD_USU_ALTA(String OAD_USU_ALTA) {
        this.OAD_USU_ALTA = OAD_USU_ALTA;
    }

    public String getOAD_FEC_ALTA() {
        return OAD_FEC_ALTA;
    }

    public void setOAD_FEC_ALTA(String OAD_FEC_ALTA) {
        this.OAD_FEC_ALTA = OAD_FEC_ALTA;
    }

    public String getOAD_FEC_ATENCION() {
        return OAD_FEC_ATENCION;
    }

    public void setOAD_FEC_ATENCION(String OAD_FEC_ATENCION) {
        this.OAD_FEC_ATENCION = OAD_FEC_ATENCION;
    }

    public String getOAD_IDFACTURA() {
        return OAD_IDFACTURA;
    }

    public void setOAD_IDFACTURA(String OAD_IDFACTURA) {
        this.OAD_IDFACTURA = OAD_IDFACTURA;
    }

    public String getOAD_AGENDADO() {
        return OAD_AGENDADO;
    }

    public void setOAD_AGENDADO(String OAD_AGENDADO) {
        this.OAD_AGENDADO = OAD_AGENDADO;
    }
    
    public String getOAD_IDATENCION_PAC() {
        return OAD_IDATENCION_PAC;
    }

    public void setOAD_IDATENCION_PAC(String OAD_IDATENCION_PAC) {
        this.OAD_IDATENCION_PAC = OAD_IDATENCION_PAC;
    }

    public String getOAD_MOTIVO_CONSULTA() {
        return OAD_MOTIVO_CONSULTA;
    }

    public void setOAD_MOTIVO_CONSULTA(String OAD_MOTIVO_CONSULTA) {
        this.OAD_MOTIVO_CONSULTA = OAD_MOTIVO_CONSULTA;
    }
    
    
}