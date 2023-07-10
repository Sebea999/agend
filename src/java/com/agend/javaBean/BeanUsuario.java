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
public class BeanUsuario extends BeanPerfil {
    
    // SU : sys_usuario 
    private String SU_IDUSUARIO;
    private String SU_IDPERSONA;
    private String SU_USUARIO;
    private String SU_CLAVE;
    private String SU_ESTADO;
    private String SU_IDPERFIL;
    private String SU_DESC_PERFIL;
    private String SU_ESTADO_MIGRAR;
    private String SU_WEB;
    private String SU_EMAIL;
    private String SU_CONFIR_EMAIL;
    
    
    
    public BeanUsuario() {
        //
    }
    
    public BeanUsuario(String SU_IDUSUARIO, String SU_IDPERSONA, String SU_USUARIO, String SU_CLAVE, String SU_ESTADO, String SU_IDPERFIL, String SU_DESC_PERFIL, String SU_ESTADO_MIGRAR, String SU_WEB, String SU_EMAIL, String SU_CONFIR_EMAIL) {
        this.SU_IDUSUARIO = SU_IDUSUARIO;
        this.SU_IDPERSONA = SU_IDPERSONA;
        this.SU_USUARIO = SU_USUARIO;
        this.SU_CLAVE = SU_CLAVE;
        this.SU_ESTADO = SU_ESTADO;
        this.SU_IDPERFIL = SU_IDPERFIL;
        this.SU_DESC_PERFIL = SU_DESC_PERFIL;
        this.SU_ESTADO_MIGRAR = SU_ESTADO_MIGRAR;
        this.SU_WEB = SU_WEB;
        this.SU_EMAIL = SU_EMAIL;
        this.SU_CONFIR_EMAIL = SU_CONFIR_EMAIL;
    }
    
    
    public String getSU_IDUSUARIO() {
        return SU_IDUSUARIO;
    }

    public void setSU_IDUSUARIO(String SU_IDUSUARIO) {
        this.SU_IDUSUARIO = SU_IDUSUARIO;
    }

    public String getSU_IDPERSONA() {
        return SU_IDPERSONA;
    }

    public void setSU_IDPERSONA(String SU_IDPERSONA) {
        this.SU_IDPERSONA = SU_IDPERSONA;
    }

    public String getSU_USUARIO() {
        return SU_USUARIO;
    }

    public void setSU_USUARIO(String SU_USUARIO) {
        this.SU_USUARIO = SU_USUARIO;
    }

    public String getSU_CLAVE() {
        return SU_CLAVE;
    }

    public void setSU_CLAVE(String SU_CLAVE) {
        this.SU_CLAVE = SU_CLAVE;
    }

    public String getSU_ESTADO() {
        return SU_ESTADO;
    }

    public void setSU_ESTADO(String SU_ESTADO) {
        this.SU_ESTADO = SU_ESTADO;
    }

    public String getSU_IDPERFIL() {
        return SU_IDPERFIL;
    }

    public void setSU_IDPERFIL(String SU_IDPERFIL) {
        this.SU_IDPERFIL = SU_IDPERFIL;
    }

    public String getSU_DESC_PERFIL() {
        return SU_DESC_PERFIL;
    }

    public void setSU_DESC_PERFIL(String SU_DESC_PERFIL) {
        this.SU_DESC_PERFIL = SU_DESC_PERFIL;
    }

    public String getSU_ESTADO_MIGRAR() {
        return SU_ESTADO_MIGRAR;
    }

    public void setSU_ESTADO_MIGRAR(String SU_ESTADO_MIGRAR) {
        this.SU_ESTADO_MIGRAR = SU_ESTADO_MIGRAR;
    }

    public String getSU_WEB() {
        return SU_WEB;
    }

    public void setSU_WEB(String SU_WEB) {
        this.SU_WEB = SU_WEB;
    }

    public String getSU_EMAIL() {
        return SU_EMAIL;
    }

    public void setSU_EMAIL(String SU_EMAIL) {
        this.SU_EMAIL = SU_EMAIL;
    }

    public String getSU_CONFIR_EMAIL() {
        return SU_CONFIR_EMAIL;
    }

    public void setSU_CONFIR_EMAIL(String SU_CONFIR_EMAIL) {
        this.SU_CONFIR_EMAIL = SU_CONFIR_EMAIL;
    }
    
    
} // END CLASS 