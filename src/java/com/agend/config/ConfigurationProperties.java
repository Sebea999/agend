/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.config;

//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;

/**
 * uso esta clase java como un archivo properties (al querer usar un archivo properties me saltaba mucho el error de (El sistema no puede encontrar el archivo especificado), probe varios metodos pero al final esto me parecio mas rapido [no daba para ponerme investigar]) 
 * @author RYUU
 */
//@Configuration
//@PropertySource("classpath:configuration.properties")
public class ConfigurationProperties {
    
//    @Value("${database.user}") // [OBS] :se supone que deberia de recuperar los valores del archivo properties, pero me devuelve null al querer usarlo, por eso lo comente.-
//    public String DB_USER;
//    String DB_USER="zagendco_admin"; // free vps - cpanel 
    String DB_USER="root"; // local 
//    String DB_USER="user5"; // vps - linux-centos9 
    
//    @Value("${database.pass}")
//    public String DB_PASS;
//    String DB_PASS="vW6s11;0XWr%"; // free vps - cpanel  
    String DB_PASS="admin"; // local 
//    String DB_PASS="agend123"; // vps - linux-centos9 
    
//    @Value("${database.host}")
//    public String DB_HOST;
//    String DB_HOST="jdbc:mysql://37.59.254.146:3306/"; // free vps - cpanel 
    String DB_HOST="jdbc:mysql://localhost:3306/"; // local
//    String DB_HOST="jdbc:mysql://198.199.88.44:3306/"; // vps - linux-centos9 
    
//    @Value("${database.db_name}")
//    public String DB_NAME;
//    String DB_NAME="zagendco_odonto"; // free vps - cpanel 
    String DB_NAME="odonto"; // local / vps - linux-centos9 
    
//    @Value("${database.db_config}")
//    public String DB_CONFIG; // configuracion adicional que agrego al host.-
    String DB_CONFIG="?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"; // configuracion adicional que agrego al host.-

    public ConfigurationProperties() {
    }

    public String getDB_USER() {
        return DB_USER;
    }

    public String getDB_PASS() {
        return DB_PASS;
    }

    public String getDB_HOST() {
        return DB_HOST;
    }

    public String getDB_NAME() {
        return DB_NAME;
    }
    
    public String getDB_CONFIG() {
        return DB_CONFIG;
    }
    
}