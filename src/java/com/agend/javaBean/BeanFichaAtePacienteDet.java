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
public class BeanFichaAtePacienteDet extends BeanPersona {

    // tabla :  ope_atencion_paciente 
    String OFPND_IDFICHAPAC;
    String OFPND_ITEM;
    String OFPND_IDSERVICIO;
    String OFPND_MONTO;
    String OFPND_ESTADO;
    String OFPND_USU_ALTA;
    String OFPND_FEC_ALTA;
    String OFPND_USU_MODI;
    String OFPND_FEC_MODI;
    String OFPND_DIR_ARCHIVO;
    String OFPND_NAME_ARCHIVO;
    

    public BeanFichaAtePacienteDet() {
    }

    public BeanFichaAtePacienteDet(String OFPND_IDFICHAPAC, String OFPND_ITEM, String OFPND_IDSERVICIO, String OFPND_MONTO, String OFPND_ESTADO, String OFPND_USU_ALTA, String OFPND_FEC_ALTA, String OFPND_DIR_ARCHIVO, String OFPND_NAME_ARCHIVO) {
        this.OFPND_IDFICHAPAC = OFPND_IDFICHAPAC;
        this.OFPND_ITEM = OFPND_ITEM;
        this.OFPND_IDSERVICIO = OFPND_IDSERVICIO;
        this.OFPND_MONTO = OFPND_MONTO;
        this.OFPND_ESTADO = OFPND_ESTADO;
        this.OFPND_USU_ALTA = OFPND_USU_ALTA;
        this.OFPND_FEC_ALTA = OFPND_FEC_ALTA;
        this.OFPND_DIR_ARCHIVO = OFPND_DIR_ARCHIVO;
        this.OFPND_NAME_ARCHIVO = OFPND_NAME_ARCHIVO;
    }

    public String getOFPND_IDFICHAPAC() {
        return OFPND_IDFICHAPAC;
    }

    public void setOFPND_IDFICHAPAC(String OFPND_IDFICHAPAC) {
        this.OFPND_IDFICHAPAC = OFPND_IDFICHAPAC;
    }

    public String getOFPND_ITEM() {
        return OFPND_ITEM;
    }

    public void setOFPND_ITEM(String OFPND_ITEM) {
        this.OFPND_ITEM = OFPND_ITEM;
    }

    public String getOFPND_IDSERVICIO() {
        return OFPND_IDSERVICIO;
    }

    public void setOFPND_IDSERVICIO(String OFPND_IDSERVICIO) {
        this.OFPND_IDSERVICIO = OFPND_IDSERVICIO;
    }

    public String getOFPND_MONTO() {
        return OFPND_MONTO;
    }

    public void setOFPND_MONTO(String OFPND_MONTO) {
        this.OFPND_MONTO = OFPND_MONTO;
    }

    public String getOFPND_ESTADO() {
        return OFPND_ESTADO;
    }

    public void setOFPND_ESTADO(String OFPND_ESTADO) {
        this.OFPND_ESTADO = OFPND_ESTADO;
    }

    public String getOFPND_USU_ALTA() {
        return OFPND_USU_ALTA;
    }

    public void setOFPND_USU_ALTA(String OFPND_USU_ALTA) {
        this.OFPND_USU_ALTA = OFPND_USU_ALTA;
    }

    public String getOFPND_FEC_ALTA() {
        return OFPND_FEC_ALTA;
    }

    public void setOFPND_FEC_ALTA(String OFPND_FEC_ALTA) {
        this.OFPND_FEC_ALTA = OFPND_FEC_ALTA;
    }

    public String getOFPND_USU_MODI() {
        return OFPND_USU_MODI;
    }

    public void setOFPND_USU_MODI(String OFPND_USU_MODI) {
        this.OFPND_USU_MODI = OFPND_USU_MODI;
    }

    public String getOFPND_FEC_MODI() {
        return OFPND_FEC_MODI;
    }

    public void setOFPND_FEC_MODI(String OFPND_FEC_MODI) {
        this.OFPND_FEC_MODI = OFPND_FEC_MODI;
    }

    public String getOFPND_DIR_ARCHIVO() {
        return OFPND_DIR_ARCHIVO;
    }

    public void setOFPND_DIR_ARCHIVO(String OFPND_DIR_ARCHIVO) {
        this.OFPND_DIR_ARCHIVO = OFPND_DIR_ARCHIVO;
    }

    public String getOFPND_NAME_ARCHIVO() {
        return OFPND_NAME_ARCHIVO;
    }

    public void setOFPND_NAME_ARCHIVO(String OFPND_NAME_ARCHIVO) {
        this.OFPND_NAME_ARCHIVO = OFPND_NAME_ARCHIVO;
    }

    
}