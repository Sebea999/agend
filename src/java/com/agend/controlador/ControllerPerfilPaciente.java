/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelPersona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerPerfilPaciente", urlPatterns="/CPPSrv") // CPPSrv : Controller Perfil Paciente Servlet 
//@WebServlet(name="controllerPerfilPac", urlPatterns="/CPPSrv") // CPPSrv : Controller Perfil Paciente Servlet 
public class ControllerPerfilPaciente extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    @RequestMapping("/perfil_pac")
    public ModelAndView perfil_paciente(HttpServletRequest request) {
        ModelAndView mav_name = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__PERFIL_PACIENTE--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CPP__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPerfil(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPerfil_Paciente", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPerfil_Paciente", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        mav_name.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_PACIENTE", SESION_IDUSER);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "PERFIL_PACIENTE"); // CONFIGURACION DEL BOTON DE "VOLVER ATRAS" DE LA PAGINA DE VER FICHA DE ATENCION DEL PACIENTE PARA PODER VOLVER A LA PAGINA DE PERFIL DEL PACIENTE Y NO A LA PAGINA PRINCIPAL DE FICHA DE ATENCION 
        
        return mav_name;
    }
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ModelPersona metodosPersona = new ModelPersona();
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS A PARTIR DEL LOGEO 
        
        int var_redireccionar = 0;
        String acceso = "perfil_pac.htm";
        String accion = request.getParameter("accion");
        String idPersona;
        
        try {
            System.out.println("__ACCION:       "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if (accion.equalsIgnoreCase("")) {
                System.out.println("---------------------__AAA__--------------------------");
                
            } else if (accion.equalsIgnoreCase("")) {
                System.out.println("---------------------__Aaaa__--------------------------");
                
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("--------------------------ERROR-PERFIL_PACIENTE--------------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerPerfilPaciente.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("-------------------------------------------------------------------------");
        }
        
// OBSERVACION: 
// UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if (var_redireccionar == 0) {
            response.sendRedirect(acceso);
        } else {
            RequestDispatcher vista = request.getRequestDispatcher(acceso);
            vista.forward(request, response);
        }
    } // end doPost() 
    
    
    private boolean esNumero(String PARAM_ACCION) {
        boolean valor;
        try {
            Integer.parseInt(PARAM_ACCION);
            valor = true;
        } catch (NumberFormatException e) {
            valor = false;
        }
        return valor;
    }
    
    
} // END CLASS 