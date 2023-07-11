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
public class BeanPerfil {
    
    // sp : sys_perfil 
    private String SP_IDPERFIL;
    private String SP_NOMBRE;
    private String SP_IDMENU;
    private String SP_ESTADO;
    
    
    
    public BeanPerfil() {
    }

    public BeanPerfil(String SP_IDPERFIL, String SP_NOMBRE, String SP_IDMENU, String SP_ESTADO) {
        this.SP_IDPERFIL = SP_IDPERFIL;
        this.SP_NOMBRE = SP_NOMBRE;
        this.SP_IDMENU = SP_IDMENU;
        this.SP_ESTADO = SP_ESTADO;
    }

    
    
    public String getSP_IDPERFIL() {
        return SP_IDPERFIL;
    }

    public void setSP_IDPERFIL(String SP_IDPERFIL) {
        this.SP_IDPERFIL = SP_IDPERFIL;
    }

    public String getSP_NOMBRE() {
        return SP_NOMBRE;
    }

    public void setSP_NOMBRE(String SP_NOMBRE) {
        this.SP_NOMBRE = SP_NOMBRE;
    }

    public String getSP_IDMENU() {
        return SP_IDMENU;
    }

    public void setSP_IDMENU(String SP_IDMENU) {
        this.SP_IDMENU = SP_IDMENU;
    }

    public String getSP_ESTADO() {
        return SP_ESTADO;
    }

    public void setSP_ESTADO(String SP_ESTADO) {
        this.SP_ESTADO = SP_ESTADO;
    }
    
    
    
    
} // END CLASS 