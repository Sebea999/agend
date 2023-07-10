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
public class BeanInicioSesion {
    
    private String LOGIN_USER; // USUARIO QUE SE LOGEA // USER_NAME 
    private String LOGIN_PASSWORD; // PASSWORD DEL USUARIO QUE SE LOGEA 

    
    public BeanInicioSesion() {
        //
    }
    
    public BeanInicioSesion(String LOGIN_USER, String LOGIN_PASSWORD) {
        this.LOGIN_USER = LOGIN_USER;
        this.LOGIN_PASSWORD = LOGIN_PASSWORD;
    }

    
    
    public String getLOGIN_USER() {
        return LOGIN_USER;
    }

    public void setLOGIN_USER(String LOGIN_USER) {
        this.LOGIN_USER = LOGIN_USER;
    }

    public String getLOGIN_PASSWORD() {
        return LOGIN_PASSWORD;
    }

    public void setLOGIN_PASSWORD(String LOGIN_PASSWORD) {
        this.LOGIN_PASSWORD = LOGIN_PASSWORD;
    }
    
    
} // END CLASS 