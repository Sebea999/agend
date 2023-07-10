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
public class BeanAtencionPaciente extends BeanAtencionPacienteDet {
    
    // tabla :  ope_atencion_paciente 
    String OAP_IDATENCIONPAC;
    String OAP_IDAGENDAMIENTO;
    String OAP_ITEM_AGEND_DET;
    String OAP_IDPACIENTE;
    String OAP_PESO;
    String OAP_TALLA;
    String OAP_IMC;
    String OAP_P_CEFALICO;
    String OAP_FC;
    String OAP_TEMP;
    String OAP_PA;
    String OAP_F_RESP;
    String OAP_MOTIVO_CONSULTA;
    String OAP_EXPLORACION_FISICA;
    String OAP_DIAGNOSTICO;
    String OAP_TRATAMIENTO;
    String OAP_RECETA;
    String OAP_ESTUDIOS_SOLICITADOS;
    String OAP_USU_ALTA;
    String OAP_FEC_ALTA;
    String OAP_ESTADO;
    String OAP_IDCLINICA;
    String OAP_FEC_AGEN_AUX; // CAMPO AUXILIAR QUE NO EXISTE EN LA TABLA PERO LA AGREGO PARA MOSTRAR LA FECHA DE AGENDAMIENTO SIN TENER QUE HACER UN SELECT APARTE Y CARGAR A UN BEAN PARA TRAER LOS DATOS DEL AGENDAMIENTO YA QUE QUIERO MOSTRAR LA FECHA DE AGEN EN EL PERFIL DEL PACIENTE 

    public BeanAtencionPaciente() {
    }

    public BeanAtencionPaciente(String OAP_IDATENCIONPAC, String OAP_IDAGENDAMIENTO, String OAP_ITEM_AGEND_DET, String OAP_IDPACIENTE, String OAP_PESO, String OAP_TALLA, String OAP_IMC, String OAP_P_CEFALICO, String OAP_FC, String OAP_TEMP, String OAP_PA, String OAP_F_RESP, String OAP_MOTIVO_CONSULTA, String OAP_EXPLORACION_FISICA, String OAP_DIAGNOSTICO, String OAP_TRATAMIENTO, String OAP_RECETA, String OAP_ESTUDIOS_SOLICITADOS, String OAP_USU_ALTA, String OAP_FEC_ALTA, String OAP_ESTADO, String OAP_IDCLINICA) {
        this.OAP_IDATENCIONPAC = OAP_IDATENCIONPAC;
        this.OAP_IDAGENDAMIENTO = OAP_IDAGENDAMIENTO;
        this.OAP_ITEM_AGEND_DET = OAP_ITEM_AGEND_DET;
        this.OAP_IDPACIENTE = OAP_IDPACIENTE;
        this.OAP_PESO = OAP_PESO;
        this.OAP_TALLA = OAP_TALLA;
        this.OAP_IMC = OAP_IMC;
        this.OAP_P_CEFALICO = OAP_P_CEFALICO;
        this.OAP_FC = OAP_FC;
        this.OAP_TEMP = OAP_TEMP;
        this.OAP_PA = OAP_PA;
        this.OAP_F_RESP = OAP_F_RESP;
        this.OAP_MOTIVO_CONSULTA = OAP_MOTIVO_CONSULTA;
        this.OAP_EXPLORACION_FISICA = OAP_EXPLORACION_FISICA;
        this.OAP_DIAGNOSTICO = OAP_DIAGNOSTICO;
        this.OAP_TRATAMIENTO = OAP_TRATAMIENTO;
        this.OAP_RECETA = OAP_RECETA;
        this.OAP_ESTUDIOS_SOLICITADOS = OAP_ESTUDIOS_SOLICITADOS;
        this.OAP_USU_ALTA = OAP_USU_ALTA;
        this.OAP_FEC_ALTA = OAP_FEC_ALTA;
        this.OAP_ESTADO = OAP_ESTADO;
        this.OAP_IDCLINICA = OAP_IDCLINICA;
    }

    public BeanAtencionPaciente(String OAP_IDATENCIONPAC, String OAP_IDAGENDAMIENTO, String OAP_ITEM_AGEND_DET, String OAP_IDPACIENTE, String OAP_PESO, String OAP_TALLA, String OAP_IMC, String OAP_P_CEFALICO, String OAP_FC, String OAP_TEMP, String OAP_PA, String OAP_F_RESP, String OAP_MOTIVO_CONSULTA, String OAP_EXPLORACION_FISICA, String OAP_DIAGNOSTICO, String OAP_TRATAMIENTO, String OAP_RECETA, String OAP_ESTUDIOS_SOLICITADOS, String OAP_USU_ALTA, String OAP_FEC_ALTA, String OAP_ESTADO, String OAPD_IDATENCIONPAC, String OAPD_ITEM, String OAPD_IDSERVICIO, String OAPD_MONTO, String OAPD_ESTADO, String OAPD_USU_ALTA, String OAPD_FEC_ALTA, String OAP_IDCLINICA) {
        super(OAPD_IDATENCIONPAC, OAPD_ITEM, OAPD_IDSERVICIO, OAPD_MONTO, OAPD_ESTADO, OAPD_USU_ALTA, OAPD_FEC_ALTA);
        this.OAP_IDATENCIONPAC = OAP_IDATENCIONPAC;
        this.OAP_IDAGENDAMIENTO = OAP_IDAGENDAMIENTO;
        this.OAP_ITEM_AGEND_DET = OAP_ITEM_AGEND_DET;
        this.OAP_IDPACIENTE = OAP_IDPACIENTE;
        this.OAP_PESO = OAP_PESO;
        this.OAP_TALLA = OAP_TALLA;
        this.OAP_IMC = OAP_IMC;
        this.OAP_P_CEFALICO = OAP_P_CEFALICO;
        this.OAP_FC = OAP_FC;
        this.OAP_TEMP = OAP_TEMP;
        this.OAP_PA = OAP_PA;
        this.OAP_F_RESP = OAP_F_RESP;
        this.OAP_MOTIVO_CONSULTA = OAP_MOTIVO_CONSULTA;
        this.OAP_EXPLORACION_FISICA = OAP_EXPLORACION_FISICA;
        this.OAP_DIAGNOSTICO = OAP_DIAGNOSTICO;
        this.OAP_TRATAMIENTO = OAP_TRATAMIENTO;
        this.OAP_RECETA = OAP_RECETA;
        this.OAP_ESTUDIOS_SOLICITADOS = OAP_ESTUDIOS_SOLICITADOS;
        this.OAP_USU_ALTA = OAP_USU_ALTA;
        this.OAP_FEC_ALTA = OAP_FEC_ALTA;
        this.OAP_ESTADO = OAP_ESTADO;
        this.OAP_IDCLINICA = OAP_IDCLINICA;
    }
    
    
    
    public String getOAP_FEC_AGEN_AUX() {
        return OAP_FEC_AGEN_AUX;
    }

    public void setOAP_FEC_AGEN_AUX(String OAP_FEC_AGEN_AUX) {
        this.OAP_FEC_AGEN_AUX = OAP_FEC_AGEN_AUX;
    }
    
    
    public String getOAP_IDATENCIONPAC() {
        return OAP_IDATENCIONPAC;
    }

    public void setOAP_IDATENCIONPAC(String OAP_IDATENCIONPAC) {
        this.OAP_IDATENCIONPAC = OAP_IDATENCIONPAC;
    }

    public String getOAP_IDAGENDAMIENTO() {
        return OAP_IDAGENDAMIENTO;
    }

    public void setOAP_IDAGENDAMIENTO(String OAP_IDAGENDAMIENTO) {
        this.OAP_IDAGENDAMIENTO = OAP_IDAGENDAMIENTO;
    }
    
    public String getOAP_ITEM_AGEND_DET() {
        return OAP_ITEM_AGEND_DET;
    }

    public void setOAP_ITEM_AGEND_DET(String OAP_ITEM_AGEND_DET) {
        this.OAP_ITEM_AGEND_DET = OAP_ITEM_AGEND_DET;
    }

    public String getOAP_IDPACIENTE() {
        return OAP_IDPACIENTE;
    }

    public void setOAP_IDPACIENTE(String OAP_IDPACIENTE) {
        this.OAP_IDPACIENTE = OAP_IDPACIENTE;
    }

    public String getOAP_PESO() {
        return OAP_PESO;
    }

    public void setOAP_PESO(String OAP_PESO) {
        this.OAP_PESO = OAP_PESO;
    }

    public String getOAP_TALLA() {
        return OAP_TALLA;
    }

    public void setOAP_TALLA(String OAP_TALLA) {
        this.OAP_TALLA = OAP_TALLA;
    }

    public String getOAP_IMC() {
        return OAP_IMC;
    }

    public void setOAP_IMC(String OAP_IMC) {
        this.OAP_IMC = OAP_IMC;
    }

    public String getOAP_P_CEFALICO() {
        return OAP_P_CEFALICO;
    }

    public void setOAP_P_CEFALICO(String OAP_P_CEFALICO) {
        this.OAP_P_CEFALICO = OAP_P_CEFALICO;
    }

    public String getOAP_FC() {
        return OAP_FC;
    }

    public void setOAP_FC(String OAP_FC) {
        this.OAP_FC = OAP_FC;
    }

    public String getOAP_TEMP() {
        return OAP_TEMP;
    }

    public void setOAP_TEMP(String OAP_TEMP) {
        this.OAP_TEMP = OAP_TEMP;
    }

    public String getOAP_PA() {
        return OAP_PA;
    }

    public void setOAP_PA(String OAP_PA) {
        this.OAP_PA = OAP_PA;
    }

    public String getOAP_F_RESP() {
        return OAP_F_RESP;
    }

    public void setOAP_F_RESP(String OAP_F_RESP) {
        this.OAP_F_RESP = OAP_F_RESP;
    }

    public String getOAP_MOTIVO_CONSULTA() {
        return OAP_MOTIVO_CONSULTA;
    }

    public void setOAP_MOTIVO_CONSULTA(String OAP_MOTIVO_CONSULTA) {
        this.OAP_MOTIVO_CONSULTA = OAP_MOTIVO_CONSULTA;
    }

    public String getOAP_EXPLORACION_FISICA() {
        return OAP_EXPLORACION_FISICA;
    }

    public void setOAP_EXPLORACION_FISICA(String OAP_EXPLORACION_FISICA) {
        this.OAP_EXPLORACION_FISICA = OAP_EXPLORACION_FISICA;
    }

    public String getOAP_DIAGNOSTICO() {
        return OAP_DIAGNOSTICO;
    }

    public void setOAP_DIAGNOSTICO(String OAP_DIAGNOSTICO) {
        this.OAP_DIAGNOSTICO = OAP_DIAGNOSTICO;
    }

    public String getOAP_TRATAMIENTO() {
        return OAP_TRATAMIENTO;
    }

    public void setOAP_TRATAMIENTO(String OAP_TRATAMIENTO) {
        this.OAP_TRATAMIENTO = OAP_TRATAMIENTO;
    }

    public String getOAP_RECETA() {
        return OAP_RECETA;
    }

    public void setOAP_RECETA(String OAP_RECETA) {
        this.OAP_RECETA = OAP_RECETA;
    }

    public String getOAP_ESTUDIOS_SOLICITADOS() {
        return OAP_ESTUDIOS_SOLICITADOS;
    }

    public void setOAP_ESTUDIOS_SOLICITADOS(String OAP_ESTUDIOS_SOLICITADOS) {
        this.OAP_ESTUDIOS_SOLICITADOS = OAP_ESTUDIOS_SOLICITADOS;
    }

    public String getOAP_USU_ALTA() {
        return OAP_USU_ALTA;
    }

    public void setOAP_USU_ALTA(String OAP_USU_ALTA) {
        this.OAP_USU_ALTA = OAP_USU_ALTA;
    }

    public String getOAP_FEC_ALTA() {
        return OAP_FEC_ALTA;
    }

    public void setOAP_FEC_ALTA(String OAP_FEC_ALTA) {
        this.OAP_FEC_ALTA = OAP_FEC_ALTA;
    }

    public String getOAP_ESTADO() {
        return OAP_ESTADO;
    }

    public void setOAP_ESTADO(String OAP_ESTADO) {
        this.OAP_ESTADO = OAP_ESTADO;
    }
    
    public String getOAP_IDCLINICA() {
        return OAP_IDCLINICA;
    }

    public void setOAP_IDCLINICA(String OAP_IDCLINICA) {
        this.OAP_IDCLINICA = OAP_IDCLINICA;
    }
    
    
}