/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.config;

/**
 *
 * @author RYUU
 */
public class ConfigCookie {
    
    String CC_DOMAIN="localhost";
//    String CC_DOMAIN="zulmiullon.com"; // vps 
    String CC_PATH="/agend";
    int CC_MAX_AGE=50;

    public ConfigCookie() {
    }

    public String getCC_DOMAIN() {
        return CC_DOMAIN;
    }

    public void setCC_DOMAIN(String CC_DOMAIN) {
        this.CC_DOMAIN = CC_DOMAIN;
    }

    public String getCC_PATH() {
        return CC_PATH;
    }

    public void setCC_PATH(String CC_PATH) {
        this.CC_PATH = CC_PATH;
    }

    public int getCC_MAX_AGE() {
        return CC_MAX_AGE;
    }

    public void setCC_MAX_AGE(int CC_MAX_AGE) {
        this.CC_MAX_AGE = CC_MAX_AGE;
    }
    
    
}