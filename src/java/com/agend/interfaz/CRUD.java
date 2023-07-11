/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.interfaz;

import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public interface CRUD {
    
    public List cargar_grilla(HttpSession PARAM_SESION);
    public List traer_datos(String idTraer, HttpSession PARAM_SESION);
    
    public String guardar(Object obj);
    public String eliminar(Object obj);
    
    // NO LE PONGO A LA INTERFAZ UN METODO PARA FILTRAR, PORQUE ESTE PUEDE TENER MAS DE UNA VARIABLE DEPENDIENDO DE LA CANTIDAD DE FILTROS QUE TENGA 
    
}