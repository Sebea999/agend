/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanAgendamiento;
import com.agend.javaBean.BeanConfigAgend;
import com.agend.javaBean.BeanFacturaCab;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelAgendamiento;
import com.agend.modelo.ModelConfigAgend;
import com.agend.modelo.ModelFactura;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPerfil;
import com.agend.modelo.ModelPersona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerPanelControl", urlPatterns="/CPCSrv")
public class ControllerPanelControl extends HttpServlet {
    
    
    
    ModelInicioSesion metodosIniSes = new ModelInicioSesion();
    ModelPerfil metodosPerfil = new ModelPerfil();
    
    
    
    @RequestMapping("/panel_control")
    public ModelAndView panel_control(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__0__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _0_CPC__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _0_CPC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuPanelDeControl(IDPERFIL_USER)==true) {
            paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPanelControl", request);
        }
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPanelControl", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/anular_agend")
    public ModelAndView anular_agendamientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__1__---------CONTROLLER__PANEL_CONTROL---------___ANULAR_AGENDAMIENTOS___--------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _1_CPC_AA__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _1_CPC_AA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isPCAnularAgendamiento(IDPERFIL_USER)==true) {
            paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_AgendAnular", request);
        }
//        String paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_AgendAnular", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        /*
        OBSERVACION: 
        PARA EVITAR CREAR EVENTOS DE MAS (COMO FILTRAR YA QUE AMBAS APUNTAN A LA MISMA TABLA), 
        A ANULAR FACTURA LE DEJO CON LAS INICIALES DE RESUMEN FACTURA 
        */
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CPC_AA_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CPC_AA_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/anular_fact")
    public ModelAndView anular_facturas(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__2__---------CONTROLLER__PANEL_CONTROL---------___ANULAR_FACTURAS___--------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _2_CPC_AF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _2_CPC_AF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isPCAnularFactura(IDPERFIL_USER)==true) {
            paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_FacturaAnular", request);
        }
//        String paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_FacturaAnular", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CPC_AF_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CPC_AF_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
//    @RequestMapping("/emp_resumen")
//    public ModelAndView empenho_resumen(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__3__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _3_CPC_RE__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Resumen", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
    
//    @RequestMapping("/emp_anular")
//    public ModelAndView anular_empenho(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__3__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _3_CPC_AE__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Anular", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_AE_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/emp_vigentes")
//    public ModelAndView empenho_vigentes(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__3__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _3_CPC_EV__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Vigentes", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_EV_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/emp_por_vencer")
//    public ModelAndView empenho_por_vencer(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__4__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _4_CPC_EPV__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_PorVencer", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_EPV_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/emp_vencidos")
//    public ModelAndView empenhos_vencidos(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__5__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _5_CPC_EVe__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Vencidos", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_EVe_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/emp_expirados")
//    public ModelAndView empenhos_expirados(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__6__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _6_CPC_EE__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Expirados", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_EE_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/emp_liquidados")
//    public ModelAndView empenhos_liquidados(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__7__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _7_CPC_EL__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCEmp_Liquidados", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_EL_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
    
//    @RequestMapping("/prendas_emp")
//    public ModelAndView prendas_empeñadas(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__8__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _8_CPC_PE__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCPrendas_Emp", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_PE_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
//    @RequestMapping("/prendas_venta")
//    public ModelAndView prendas_en_venta(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__9__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _9_CPC_PEV__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        
//        String pagina_web = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPCPrendas_EnVenta", request);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CPC_PEV_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(pagina_web);
//        return mav;
//    }
    
    
    
    @RequestMapping("/config_agend")
    public ModelAndView config_agendamiento(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__9__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _9_CPC___ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _9_CPC_CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isPCAnularFactura(IDPERFIL_USER)==true) {
            paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_AgendConfig", request);
        }
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        request.setAttribute("CPC_AC_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/config_agend_add")
    public ModelAndView config_agend_add(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__9__---------CONTROLLER__PANEL_CONTROL--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _9_CPC___ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _9_CPC_CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isPCAnularFactura(IDPERFIL_USER)==true) {
            paginaMav = metodosIniSes.controlValidaciones(SESION_IDUSER, "pagPC_AgendConfigAdd", request);
        }
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION DONDE CARGO DATOS COMO DATOS DEL USUARIO ENTRE OTROS DATOS 
        ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
        ModelFactura metodosFactura = new ModelFactura();
        ModelPersona metodosPersona = new ModelPersona();
        ModelConfigAgend metodosConfigAgend = new ModelConfigAgend();
        DecimalFormat formatear = new DecimalFormat("###,###,###");
        
        int var_redireccionar = 0;
        String acceso = "panel_control.htm";
        String accion = request.getParameter("accion");
        // CAJA 
        String accionAnuAgen = request.getParameter("accionAA"); // ANULAR AGENDAMIENTO 
        String accionAnuFact = request.getParameter("accionAF"); // ANULAR FACTURA 
        // CONFIG 
        String accionConfigAgend = request.getParameter("accionAC"); // CONFIGURACION AGENDAMIENTO 
        // EMPEÑO 
        String accionEmpVig = request.getParameter("accionEV"); // EMPEÑO - EMPEÑOS VIGENTES 
        String accionEmpPorVencer = request.getParameter("accionEPV"); // EMPEÑO - EMPEÑOS POR VENCER 
        String accionEmpVencidos = request.getParameter("accionEVe"); // EMPEÑO - EMPEÑOS VENCIDOS 
        String accionEmpExpirados = request.getParameter("accionEE"); // EMPEÑO - EMPEÑOS EXPIRADOS 
        String accionEmpLiquidados = request.getParameter("accionEL"); // EMPEÑO - EMPEÑOS LIQUIDADOS 
        String accionEmpAnular = request.getParameter("accionAE"); // EMPEÑO - ANULAR EMPEÑO 
        String accionEmpResumen = request.getParameter("accionRE"); // EMPEÑO - RESUMEN EMPEÑO 
        // INVENTARIO 
        String accionPrendasEmp = request.getParameter("accionIPE"); // INVENTARIO - PRENDAS EMPEÑADAS 
        String accionPrendasEnVenta = request.getParameter("accionIPEV"); // INVENTARIO - PRENDAS EN VENTA  
        String idPersona;
        
        try {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("-   -----------------CONTROLADOR_PANEL_CONTROL-----------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   :"+idPersona);
            System.out.println("__ACCION:       :"+accion);
            System.out.println("-   -   -   -   -   -   -   -   -   -   -   -");
            System.out.println("__ACCION_ANULAR_AGEN:      :"+accionAnuAgen);
            System.out.println("__ACCION_ANULAR_FACT:      :"+accionAnuFact);
            System.out.println("-   -   -   -   -   -   -   -   -   -   -   -");
            System.out.println("__ACCION_AGEND_CONFIG  :    :"+accionConfigAgend);
            System.out.println("-   -   -   -   -   -   -   -   -   -   -   -");
            System.out.println("__ACCION_EMP_VIGENTES  :    :"+accionEmpVig);
            System.out.println("__ACCION_EMP_POR_VENCER:    :"+accionEmpPorVencer);
            System.out.println("__ACCION_EMP_VENCIDOS  :    :"+accionEmpVencidos);
            System.out.println("__ACCION_EMP_EXPIRADOS :    :"+accionEmpExpirados);
            System.out.println("__ACCION_EMP_LIQUIDADOS:    :"+accionEmpLiquidados);
            System.out.println("__ACCION_EMP_ANULAR :       :"+accionEmpAnular);
            System.out.println("__ACCION_EMP_RESUMEN:       :"+accionEmpResumen);
            System.out.println("-   -   -   -   -   -   -   -   -   -   -   -");
            System.out.println("__ACCION_PRENDAS_EMPEÑ:     :"+accionPrendasEmp);
            System.out.println("__ACCION_PRENDAS_VENTA:     :"+accionPrendasEnVenta);
            System.out.println("-   -   -   -   -   -   -   -   -   -   -   -");
            
            if (accion != null) { // PAGINA :     PANEL DE CONTROL 
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PANEL_DE_CONTROL__--------------------------");
                System.out.println(".");System.out.println(".");
                if (accion.equalsIgnoreCase("...")) {
                    System.out.println("---------------------__...__--------------------------");
                    //
                }
                
                
            } else if (accionConfigAgend != null) { // PAGINA :     CONFIGURACION AGENDAMIENTO 
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__._-_CONFIGURACION_AGENDAMIENTO__--------------------------");
                System.out.println(".");System.out.println(".");
                if (isNumber(accionConfigAgend) || accionConfigAgend.equalsIgnoreCase("<<") || accionConfigAgend.equalsIgnoreCase(">>")) {
                    acceso = "config_agend.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_PC_AGEND_CONFIG", request, response, sesionDatosUsuario, acceso, var_redireccionar, accionConfigAgend, 3);
                    
                } else if (accionConfigAgend.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    // OBTENER VALORES DE LA CAJA DE FILTRO 
                    String cbxMostrar = (String) request.getParameter("cM");
                    String txtBuscar = (String) request.getParameter("txB");
                    if (txtBuscar == null || txtBuscar.isEmpty()) {
                        txtBuscar = "";
                    }
                    // COMBO ESTADO 
                    String checkEstadoFiltro = (String) request.getParameter("check_estado"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_ESTADO = (String) request.getParameter("cE"); // ESTADO  
                    if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = ""; }
                    System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
                    System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
                    // CONTROL DEL CHECK DE ESTADO 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
                    if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                        checkEstadoFiltro = "OFF";
                    } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_ESTADO = "";
                    }
                    
                    System.out.println("-------------__VAR_FILTRAR__--------------");
                    System.out.println("_  _CBX_MOSTRAR:  :"+cbxMostrar);
                    System.out.println("_  _BUSCAR:       :"+txtBuscar);
                    System.out.println("------------------------------------------");
                    
                    // CARGAR LISTA 
                    List<BeanAgendamiento> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosConfigAgend.filtrar_paginacion(sesionDatosUsuario, cbxMostrar, txtBuscar, PARAM_TXT_ESTADO);
                    
                    var_redireccionar = 1;
                    acceso = "config_agend.htm";
                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AC_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_AC_MENSAJE", mensaje);
                    request.setAttribute("CPC_AC_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CPC_AC_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CPC_AC_TXT_BUSCAR", txtBuscar);
                    request.setAttribute("CPC_AC_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
                    request.setAttribute("CPC_AC_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accionConfigAgend.equalsIgnoreCase("Add Nueva Configuración") || accionConfigAgend.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__ADD_NUEVA_CONFIGURACION__------------------------------");
                    var_redireccionar = 1;
                    acceso = "config_agend_add.htm";
                    sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                    
                } else if (accionConfigAgend.equalsIgnoreCase("Editar") || accionConfigAgend.equalsIgnoreCase("Limpiar1")) {
                    System.out.println("------------------------__EDITAR_CONFIGURACION__------------------------------");
                    String ID_CONFIG_AGEND = (String) request.getParameter("tI");
                    System.out.println("_   __ID_CONFIG_AGEND:  :"+ID_CONFIG_AGEND);
                    var_redireccionar = 1;
                    acceso = "config_agend_add.htm";
                    sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ID_CONFIG_AGEND); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                    
                } else if (accionConfigAgend.equalsIgnoreCase("GUARDAR")) {
                    System.out.println("------------------------__GUARDAR_CONFIGURACION__------------------------------");
                    String pagina="";
                    String ID_CONFIG_AGEND = (String) sesionDatosUsuario.getAttribute("ID_CONFIG_AGEND");
                    System.out.println("_   ___ID_CONFIG_AGEND:   :"+ID_CONFIG_AGEND+":__");
                    // VALIDACION PARA SI RECARGA LA PAGINA NO SE VUELVA A REPETIR LA ULTIMA ACCION DE GUARDAR SINO QUE REDIRECCIONE A LA PAGINA PRINCIPAL O LA RECARGUE 
                    if (/*ID_CONFIG_AGEND != null ||*/ !(ID_CONFIG_AGEND.isEmpty()) || !(ID_CONFIG_AGEND.equals(""))) {
                        System.out.println("---__if__----------__ID_CONFIG_AGEND_:__CARGADO__----------__CTRL_GUARDAR__----");
                        var_redireccionar = 0;
                        acceso = "config_agend.htm";
                    } else {
                        System.out.println("---__else__----------__ID_CONFIG_AGEND_:__VACIO__----------__CTRL_GUARDAR__----");
                        // RECUPERO LAS VARIABLES 
                        String DESC_CONFIG = (String) request.getParameter("tDC");
                        String MAX_AGEND = (String) request.getParameter("tMA");
                        String DESC_AGEND = (String) request.getParameter("tDA");
                        String IDCLINICA = (String) request.getParameter("cCli");
                        String ESTADO = (String) request.getParameter("cE");
                        String INTERV_MIN_ENTRE_AGEND = (String) request.getParameter("tIMAO");
                        
                        // VALIDO QUE NO ESTEN NULAS 
                        int varVacio = 0;
                        if (DESC_CONFIG == null || DESC_CONFIG.isEmpty()) {
                            varVacio = 1;
                            DESC_CONFIG = "";
                        }
                        if (MAX_AGEND == null || MAX_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            MAX_AGEND = "";
                        }
                        if (DESC_AGEND == null || DESC_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            DESC_AGEND = "";
                        }
                        if (IDCLINICA == null || IDCLINICA.isEmpty()) {
                            varVacio = varVacio + 1;
                            IDCLINICA = "";
                        }
                        if (ESTADO == null || ESTADO.isEmpty()) {
                            varVacio = varVacio + 1;
                            ESTADO = "";
                        }
                        if (INTERV_MIN_ENTRE_AGEND == null || INTERV_MIN_ENTRE_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            INTERV_MIN_ENTRE_AGEND = "";
                        }
                        
                        // IMPRIMO LOS DATOS 
                        System.out.println("----------___DATOS___--------------");
                        System.out.println(".    .DESC_CONFIG   :"+DESC_CONFIG);
                        System.out.println(".    .MAX_AGEND     :"+MAX_AGEND);
                        System.out.println(".    .DESC_AGEND    :"+DESC_AGEND);
                        System.out.println(".    .IDCLINICA     :"+IDCLINICA);
                        System.out.println(".    .ESTADO        :"+ESTADO);
                        System.out.println(".    .INTERV_MIN_ENTRE_AGEND :"+INTERV_MIN_ENTRE_AGEND);
                        System.out.println("-----------------------------------");
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE; // 1 : exito / 2 : error 
                        if (varVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(metodosConfigAgend.ctrlDescConfigAgend(ID_CONFIG_AGEND, IDCLINICA, DESC_CONFIG) == true) { // VALIDACION PARA CONTROLAR QUE NO SE REPITA LA DESCRIPCION DE LA CONFIGURACION 
                            MENSAJE = "Ya existe un nombre de configuración de agendamiento igual, cargue otra.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(isNumber(MAX_AGEND)==false) { // VALIDACION PARA EVITAR QUE HAYA ALGUN CARACTER O LETRA EN EL CAMPO DE TEXTO DE "MAX_AGEND" (POR QUE EL CAMPO ES INT) 
                            MENSAJE = "El campo de máximo agendamientos por médico debe de ser solo números.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(isNumber(INTERV_MIN_ENTRE_AGEND)==false) { // VALIDACION PARA EVITAR QUE HAYA ALGUN CARACTER O LETRA EN EL CAMPO DE TEXTO DE "MAX_AGEND" (POR QUE EL CAMPO ES INT) 
                            MENSAJE = "El campo de intervalo de minutos entre un agendamiento y otro, debe de ser solo números.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(metodosConfigAgend.ctrlBaseEstadoActivo(ID_CONFIG_AGEND, IDCLINICA) == true && ESTADO.equals("A")) { // VALIDACION PARA CONTROLAR QUE EN LA IDCLINICA NO EXISTA OTRO REGISTRO ACTIVO 
                            MENSAJE = "Ya existe una configuración activa en la clinica, solo puede haber una configuración activa por clinica.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else {
                            // PREPARO LAS VARIABLES PARA CARGAR AL CONSTRUCTOR 
                            String SCA_IDCONFIGAGEND = ID_CONFIG_AGEND;
                            String SCA_DESC_CONFIG = DESC_CONFIG;
                            String SCA_MAX_AGEND = MAX_AGEND;
                            String SCA_DESC_AGEND = metodosAgendamiento.verificarPuntoFinal(DESC_AGEND); // utilizo el metodo para agregar un punto final que me ayudara a la hora de sustituir las variables por los datos de la fecha de agendamiento a la hora de agendarse 
                            String SCA_ESTADO = ESTADO;
                            String SCA_USU_ALTA = idPersona;
                            String SCA_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();
                            String SCA_IDCLINICA = IDCLINICA;
                            String SCA_INTERV_MIN_ENTRE_AGEND = INTERV_MIN_ENTRE_AGEND;
                            
                            TIPO_MENSAJE = metodosConfigAgend.guardar(new BeanConfigAgend(SCA_IDCONFIGAGEND, SCA_DESC_CONFIG, SCA_MAX_AGEND, SCA_DESC_AGEND, SCA_ESTADO, SCA_USU_ALTA, SCA_FEC_ALTA, SCA_IDCLINICA, SCA_INTERV_MIN_ENTRE_AGEND));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                            } else {
                                MENSAJE = "Se ha realizado correctamente la operación.";
                            }
                            pagina = "config_agend.htm";
                        }
                        // CONDICIONAL PARA CARGAR LOS CAMPOS DE LA PAGINA EN CASO DE QUE HAYA DADO ALGUN ERROR O WARNING 
                        if (TIPO_MENSAJE.equals("2")) {
                            sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ID_CONFIG_AGEND); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                            request.setAttribute("CPC_AC_DESC_CONFIG", DESC_CONFIG);
                            request.setAttribute("CPC_AC_MAX_AGENDAMIENTO", MAX_AGEND);
                            request.setAttribute("CPC_AC_DESC_AGENDAMIENTO", DESC_AGEND);
                            request.setAttribute("CPC_AC_IDCLINICA", IDCLINICA);
                            request.setAttribute("CPC_AC_ESTADO", ESTADO);
                            request.setAttribute("CPC_AC_INTERV_MIN_ENTRE_AGEND", INTERV_MIN_ENTRE_AGEND);
                        } else if (TIPO_MENSAJE.equals("1")) { // si todo salio un exito entonces limpiaria la variable de la sesion para no mantener un id en sesion 
                            sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                        }
                        var_redireccionar = 1;
                        acceso = pagina;
                        request.setAttribute("CPC_AC_MENSAJE",  MENSAJE);
                        request.setAttribute("CPC_AC_TIPO_MENSAJE", TIPO_MENSAJE);
                    } // end else ctrl id_config_agend.- 
                    
                } else if (accionConfigAgend.equalsIgnoreCase("ACTUALIZAR")) {
                    System.out.println("------------------------__ACTUALIZAR_CONFIGURACION__------------------------------");
                    String pagina="";
                    String ID_CONFIG_AGEND = (String) sesionDatosUsuario.getAttribute("ID_CONFIG_AGEND");
                    System.out.println("_   ___ID_CONFIG_AGEND:   :"+ID_CONFIG_AGEND+":__");
                    // VALIDACION PARA SI RECARGA LA PAGINA NO SE VUELVA A REPETIR LA ULTIMA ACCION DE GUARDAR SINO QUE REDIRECCIONE A LA PAGINA PRINCIPAL O LA RECARGUE 
                    if (/*ID_CONFIG_AGEND != null ||*/ (ID_CONFIG_AGEND.isEmpty()) || (ID_CONFIG_AGEND.equals(""))) {
                        System.out.println("---__if__----------__ID_CONFIG_AGEND_:__VACIO__----------__CTRL_ACTUALIZAR__----");
                        var_redireccionar = 0;
                        acceso = "config_agend.htm";
                    } else {
                        System.out.println("---__else__----------__ID_CONFIG_AGEND_:__CARGADO__----------__CTRL_ACTUALIZAR__----");
                        // RECUPERO LAS VARIABLES 
                        String DESC_CONFIG = (String) request.getParameter("tDC");
                        String MAX_AGEND = (String) request.getParameter("tMA");
                        String DESC_AGEND = (String) request.getParameter("tDA");
                        String IDCLINICA = (String) request.getParameter("cCli");
                        String ESTADO = (String) request.getParameter("cE");
                        String INTERV_MIN_ENTRE_AGEND = (String) request.getParameter("tIMAO");
                        
                        // VALIDO QUE NO ESTEN NULAS 
                        int varVacio = 0;
                        if (DESC_CONFIG == null || DESC_CONFIG.isEmpty()) {
                            varVacio = 1;
                            DESC_CONFIG = "";
                        }
                        if (MAX_AGEND == null || MAX_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            MAX_AGEND = "";
                        }
                        if (DESC_AGEND == null || DESC_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            DESC_AGEND = "";
                        }
                        if (IDCLINICA == null || IDCLINICA.isEmpty()) {
                            varVacio = varVacio + 1;
                            IDCLINICA = "";
                        }
                        if (ESTADO == null || ESTADO.isEmpty()) {
                            varVacio = varVacio + 1;
                            ESTADO = "";
                        }
                        if (INTERV_MIN_ENTRE_AGEND == null || INTERV_MIN_ENTRE_AGEND.isEmpty()) {
                            varVacio = varVacio + 1;
                            INTERV_MIN_ENTRE_AGEND = "";
                        }
                        
                        // IMPRIMO LOS DATOS 
                        System.out.println("----------___DATOS___--------------");
                        System.out.println(".    .DESC_CONFIG   :"+DESC_CONFIG);
                        System.out.println(".    .MAX_AGEND     :"+MAX_AGEND);
                        System.out.println(".    .DESC_AGEND    :"+DESC_AGEND);
                        System.out.println(".    .IDCLINICA     :"+IDCLINICA);
                        System.out.println(".    .ESTADO        :"+ESTADO);
                        System.out.println(".    .SCA_INTERV_MIN_ENTRE_AGEND :"+INTERV_MIN_ENTRE_AGEND);
                        System.out.println("-----------------------------------");
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE; // 1 : exito / 2 : error 
                        if (varVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(metodosConfigAgend.ctrlDescConfigAgend(ID_CONFIG_AGEND, IDCLINICA, DESC_CONFIG) == true) { // VALIDACION PARA CONTROLAR QUE NO SE REPITA LA DESCRIPCION DE LA CONFIGURACION 
                            MENSAJE = "Ya existe un nombre de configuración de agendamiento igual, cargue otra.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(isNumber(MAX_AGEND)==false) { // VALIDACION PARA EVITAR QUE HAYA ALGUN CARACTER O LETRA EN EL CAMPO DE TEXTO DE "MAX_AGEND" (POR QUE EL CAMPO ES INT) 
                            MENSAJE = "El campo de máximo agendamientos por médico debe de ser solo números.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(isNumber(INTERV_MIN_ENTRE_AGEND)==false) { // VALIDACION PARA EVITAR QUE HAYA ALGUN CARACTER O LETRA EN EL CAMPO DE TEXTO DE "MAX_AGEND" (POR QUE EL CAMPO ES INT) 
                            MENSAJE = "El campo de intervalo de minutos entre un agendamiento y otro, debe de ser solo números.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else if(metodosConfigAgend.ctrlBaseEstadoActivo(ID_CONFIG_AGEND, IDCLINICA) == true && ESTADO.equals("A")) { // VALIDACION PARA CONTROLAR QUE EN LA IDCLINICA NO EXISTA OTRO REGISTRO ACTIVO 
                            MENSAJE = "Ya existe una configuración activa en la clinica, solo puede haber una configuración activa por clinica.";
                            TIPO_MENSAJE = "2";
                            pagina = "config_agend_add.htm";
                            
                        } else {
                            // PREPARO LAS VARIABLES PARA CARGAR AL CONSTRUCTOR 
                            String SCA_IDCONFIGAGEND = ID_CONFIG_AGEND;
                            String SCA_DESC_CONFIG = DESC_CONFIG;
                            String SCA_MAX_AGEND = MAX_AGEND;
                            String SCA_DESC_AGEND = metodosAgendamiento.verificarPuntoFinal(DESC_AGEND); // utilizo el metodo para agregar un punto final que me ayudara a la hora de sustituir las variables por los datos de la fecha de agendamiento a la hora de agendarse 
                            String SCA_ESTADO = ESTADO;
                            String SCA_USU_ALTA = idPersona;
                            String SCA_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();
                            String SCA_IDCLINICA = IDCLINICA;
                            String SCA_INTERV_MIN_ENTRE_AGEND = INTERV_MIN_ENTRE_AGEND;
                            
                            TIPO_MENSAJE = metodosConfigAgend.guardar(new BeanConfigAgend(SCA_IDCONFIGAGEND, SCA_DESC_CONFIG, SCA_MAX_AGEND, SCA_DESC_AGEND, SCA_ESTADO, SCA_USU_ALTA, SCA_FEC_ALTA, SCA_IDCLINICA, SCA_INTERV_MIN_ENTRE_AGEND));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                            } else {
                                MENSAJE = "Se ha realizado correctamente la operación.";
                            }
                            pagina = "config_agend.htm";
                        }
                        // CONDICIONAL PARA CARGAR LOS CAMPOS DE LA PAGINA EN CASO DE QUE HAYA DADO ALGUN ERROR O WARNING 
                        if (TIPO_MENSAJE.equals("2")) {
                            sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ID_CONFIG_AGEND); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                            request.setAttribute("CPC_AC_DESC_CONFIG", DESC_CONFIG);
                            request.setAttribute("CPC_AC_MAX_AGENDAMIENTO", MAX_AGEND);
                            request.setAttribute("CPC_AC_DESC_AGENDAMIENTO", DESC_AGEND);
                            request.setAttribute("CPC_AC_IDCLINICA", IDCLINICA);
                            request.setAttribute("CPC_AC_ESTADO", ESTADO);
                            request.setAttribute("CPC_AC_INTERV_MIN_ENTRE_AGEND", INTERV_MIN_ENTRE_AGEND);
                        } else if (TIPO_MENSAJE.equals("1")) { // si todo salio un exito entonces limpiaria la variable de la sesion para no mantener un id en sesion 
                            sesionDatosUsuario.setAttribute("ID_CONFIG_AGEND", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                        }
                        var_redireccionar = 1;
                        acceso = pagina;
                        request.setAttribute("CPC_AC_MENSAJE",  MENSAJE);
                        request.setAttribute("CPC_AC_TIPO_MENSAJE", TIPO_MENSAJE);
                    } // end else ctrl id_config_agend.- 
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionConfigAgend.equalsIgnoreCase("LimpiarPag")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "config_agend.htm";
                    sesionDatosUsuario.setAttribute("PAG_PC_AGEND_CONFIG_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_PC_AGEND_CONFIG_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            } else if (accionAnuAgen != null) { // PAGINA :     ANULAR AGENDAMIENTO 
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__._-_ANULAR_AGENDAMIENTO__--------------------------");
                System.out.println(".");System.out.println(".");
                if (isNumber(accionAnuAgen) || accionAnuAgen.equalsIgnoreCase("<<") || accionAnuAgen.equalsIgnoreCase(">>")) {
                    acceso = "anular_agend.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_PC_ANU_AGEN", request, response, sesionDatosUsuario, acceso, var_redireccionar, accionAnuAgen, 1);
                    
                } else if (accionAnuAgen.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    // OBTENER VALORES DE LA CAJA DE FILTRO 
                    String cbxMostrar = (String) request.getParameter("cM");
                    String txtBuscarCodAgen = (String) request.getParameter("tFNF"); // tFNF : txt filtrar nro factura 
                    if (txtBuscarCodAgen == null || txtBuscarCodAgen.isEmpty()) {
                        txtBuscarCodAgen = "";
                    }
                    String txtFechaIni = (String) request.getParameter("tFFI"); // tFFI : txt filtrar fecha inicio 
                    if (txtFechaIni == null || txtFechaIni.isEmpty()) {
                        txtFechaIni = "";
                    }
                    String txtFechaFin = (String) request.getParameter("tFFF"); // tFFF : txt filtrar fecha fin 
                    if (txtFechaFin == null || txtFechaFin.isEmpty()) {
                        txtFechaFin = "";
                    }
                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
                    if (txtClienteId == null || txtClienteId.isEmpty()) {
                        txtClienteId = "";
                    }
                    String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // CLINICA id 
                    if (txtClinicaId == null || txtClinicaId.isEmpty()) { txtClinicaId = ""; }
//                    String checkPacienteFiltro = (String) request.getParameter("check_pac"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtPacienteId = (String) request.getParameter("cbxAddNewPac"); // paciente id 
//                    if (txtPacienteId == null || txtPacienteId.isEmpty()) {
//                        txtPacienteId = "";
//                    }
                    System.out.println("-------------__VAR_FILTRAR__--------------");
                    System.out.println("_  _CBX_MOSTRAR    : "+cbxMostrar);
                    System.out.println("_  _BUSCAR_COD_AGEN: "+txtBuscarCodAgen);
                    System.out.println("_  _FECHA_INICIO :   "+txtFechaIni);
                    System.out.println("_  _FECHA_FIN    :   "+txtFechaFin);
                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
                    System.out.println("_  _CHECK_CLINICA:   "+checkClinicaFiltro);
                    System.out.println("_  _CLINICA_ID   :   "+txtClinicaId);
//                    System.out.println("_  _CHECK_PACIENTE:  "+checkPacienteFiltro);
//                    System.out.println("_  _PACIENTE_ID   :  "+txtPacienteId);
                    System.out.println("------------------------------------------");
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                        checkClienteFiltro = "OFF";
//                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
//                        List<BeanPersona> listaDatosCliente;
//                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
//                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
//                        BeanPersona datosClienteNew = null;
//                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
//                        while(iterCliente.hasNext()) {
//                            datosClienteNew = iterCliente.next();
//
//                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
//                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
//                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
//                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
//                        }
//
//                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
//                        System.out.println("-------------------------------------------");
//                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
//                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
//                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
//                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
//                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
//                        System.out.println("-------------------------------------------");
                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClienteId = "";
                    }
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLINICA, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLINICA PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLINICA 
                    if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA CLINICA Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLINICA 
                        checkClinicaFiltro = "OFF";
                    } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClinicaId = "";
                    }
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR PACIENTE 
//                    if (checkPacienteFiltro == null || checkPacienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPACIENTE 
//                        checkPacienteFiltro = "OFF";
//                    } else if (checkPacienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtPacienteId = "";
//                    }
                    
                    // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
                    String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                    System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
                    String IDPACIENTE = "";
                    // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
                    if (metodosPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                        IDPACIENTE = idPersona; // SI FUERA EL PERFIL PACIENTE, ENTONCES NO LE VOY A MOSTRAR EL FILTRO DE PACIENTE Y DIRECTAMENTE UTILIZARE EL IDPERSONA DE LOGEO 
                    } else if (metodosPerfil.isPerfilAdmin(IDPERFIL_USER) || metodosPerfil.isPerfilSecre(IDPERFIL_USER)) {
                        IDPACIENTE = txtClienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
//                        IDPACIENTE = txtPacienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
                    }
                    System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);
                    
                    // CARGAR LISTA 
                    List<BeanAgendamiento> listaFiltro = new ArrayList<>();
                    String varEstado = ""; // LO DEJO VACIO YA QUE QUIERO QUE EL FILTRO ME MUESTRE TODOS LOS ESTADO, PERO SI QUISIESE QUE ME MUESTRE SOLO LOS COBRADOS, ENTONCES AHI SI CARGARIA EL ESTADO 
                    listaFiltro = metodosAgendamiento.filtrar_anu_agen(sesionDatosUsuario, cbxMostrar, txtBuscarCodAgen, "", "", txtFechaIni, txtFechaFin, IDPACIENTE, txtClinicaId);
                    
                    var_redireccionar = 1;
                    acceso = "anular_agend.htm";
                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_CRF_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_CRF_MENSAJE", mensaje);
                    request.setAttribute("CPC_AA_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CPC_AA_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CPC_AA_TXT_FILTRAR_NRO_FACT", txtBuscarCodAgen);
                    request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CPC_AA_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
                    request.setAttribute("CPC_AA_TXT_FILTRAR_CLIE_ID", txtClienteId);
                    request.setAttribute("CPC_AA_CHECK_FILTRAR_CLINICA_01", checkClinicaFiltro);
                    request.setAttribute("CPC_AA_TXT_FILTRAR_CLINICA_ID", txtClinicaId);
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accionAnuAgen.equalsIgnoreCase("PreAnular")) {
                    System.out.println("---------------------__PRE_ANULAR__--------------------------");
                    /*
                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE FACTURA 
                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
                    */
                    var_redireccionar = 1;
                    ControllerAgendamiento controllerAgen = new ControllerAgendamiento();
                    acceso = controllerAgen.ver_agendamiento_anular(sesionDatosUsuario, request, acceso);
//                    acceso = "ver_agen.htm";
////                    acceso = "ver_agend.htm";
                    /*
                     * OBSERVACION_DEL_IF_SOBRE_LA_LIMPIEZA_DE_LA_VARIABLE_DE_LA_SESION: 
                        COMO DESDE LA PAGINA DE VER CUENTA CLIENTE TIENE LA POSIBLIDAD DE VER EL AGENDAMIENTO DE LA CUENTA 
                        ENTONCES ME AGARRO DE ESTE EVENTO PARA VER EL AGENDAMIENTO PERO COMO ACA SE LIMPIA LA VARIABLE DE LA SESION QUE INDICA A QUE PAGINA HAY QUE VOLVER ATRAS 
                        UTILIZO LA VARIABLE QUE GUARDA EL IDPACIENTE PARA LUEGO DEVOLVERLE AL CONTROLADOR DE CUENTA CLIENTE Y ASI REDIRECCIONAR AL VER CUENTAS DEL PACIENTE 
                        SI ESTUVIERA VACIO ESTA VARIABLE DE LA SESION ENTONCES SÉ QUE NO VIENE DE LA PAGINA DE CUENTA PACIENTE, Y COMO ESTA PAGINA (VER_CUENTA_PACIENTE) ES LA UNICA QUE CARGA ESTA VARIABLE ENTONCES PASARIA A LIMPIAR COMO NORMALMENTE LO HAGO PARA EVITAR QUE SE MANTENGA EN MEMORIA EL ANTERIOR DATO DE ESTA VARIABLE 
                        Y HARIA UN ELSE-IF CON UNA VARIABLE CLAVE DE OTRA PAGINA EN CASO DE QUE UTILICE EL EVENTO DE VER AGENDAMIENTO Y FUERA UNA SUBPAGINA, YA QUE PARA REDIRECCIONAR A UNA SUBPAGINA SE NECESITA TENER UN EVENTO EN EL CONTROLADOR 
                    */
                    if (sesionDatosUsuario.getAttribute("AGEND_IDPACIENTE_CTAVER") == null) { // OBS: ("AGEND_IDPACIENTE_CTAVER" SE CARGA EN EL JSP "pagCuentaCliente_VerCta.jsp") 
                        sesionDatosUsuario.setAttribute("AGEND_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOLVER ATRAS" DE LA PAGINA DE VER AGENDAMIENTO 
                    } // SI NO ESTA CARGADO ESA VARIABLE O OTRA EN CASO DE QUE AGREGUE OTRA VARIABLE, ENTONCES NO LIMPIARIA LA VARIABLE QUE ME INDICA DONDE VOLVER ATRAS 
//                    sesionDatosUsuario.setAttribute("AGEND_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOLVER ATRAS" DE LA PAGINA DE VER AGENDAMIENTOS 
                    sesionDatosUsuario.setAttribute("AGEND_BTN_ANULAR", "2"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE ANULAR EN LA PAGINA DE "VER AGENDAMIENTO" 
                    
                } else if(accionAnuAgen.equalsIgnoreCase("VOLVER_ATRAS_CTAPAC")) { // EVENTO PARA VOLVER ATRAS A LA CUENTA DEL PACIENTE QUE REDIRECCIONO AL AGENDAMIENTO PARA VER LOS DATOS DEL AGEND 
                    System.out.println("---------------------__VOLVER_ATRAS__A__CUENTA_PACIENTE__--------------------------");
                    ControllerCuentaCliente controlador_cta_pac = new ControllerCuentaCliente();
                    acceso = controlador_cta_pac.ver_cuenta_paciente(sesionDatosUsuario, request);
                    var_redireccionar = 1;// ELIMINAR YA QUE USARE EL VER AGENDAMIENTO DEL ANULAR AGEND 
                    sesionDatosUsuario.setAttribute("AGEND_IDPACIENTE_CTAVER", null); // LIMPIO LA VARIABLE DE LA SESION PORQUE LA SESION MANTIENE EL DATO 
                    
                } else if (accionAnuAgen.equalsIgnoreCase("Anular Agendamiento")) {
                    System.out.println("------------------------------__ANULAR__-----------------------------------");
                    String txt_id_agend = (String) request.getParameter("tI");
                    String txt_item_agend = (String) request.getParameter("tIA");
                    String txt_id_paciente = (String) request.getParameter("tIP");
                    System.out.println("------------__VAR_ANULAR__-----------");
                    System.out.println("_  _ID_AGENDAMIENTO:    "+txt_id_agend);
                    System.out.println("_  _ITEM_AGENDAMIENTO:  "+txt_item_agend);
                    System.out.println("_  _ID_PACIENTE_AGEND:  "+txt_id_paciente);
                    System.out.println("-------------------------------------");
                    System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");
                    
                    // ANULAR LA FACTURA 
                    String MENSAJE = null, TIPO_MENSAJE = "";
                    if (metodosAgendamiento.ctrlFacturaActDeAgen(sesionDatosUsuario, txt_id_agend, txt_item_agend) == true) {
                        String VALI_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("VALI_NRO_FACTURA");
//                        if (COD_IDATENCION == null || COD_IDATENCION.isEmpty()) {
//                            MENSAJE = "Debe de anular primero la ficha de atención de esta factura, para poder anular la factura.";
//                        } else {
//                            Debe de anular primero la ficha de atención con código: "+COD_IDATENCION+" para poder anular la factura.";
//                        }
                        MENSAJE = "El agendamiento cuenta con una factura, para anularlo debe de anular primero la factura Nro. "+VALI_NRO_FACTURA+".";
                        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                        System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
                        // SI EL PERFIL ES DEL PACIENTE ENTONCES LE AGREGO UN TEXTO MAS PARA PODER ORIENTAR AL PACIENTE, PERO SI FUERA OTRO PERFIL YA SERIA INNECESARIO POR ESO PREGUNTO QUE SEA EL PERFIL DE PACIENTE NOMAS 
                        if (metodosPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                            MENSAJE = MENSAJE + " <br>\nAcerquése a la clínica para que el secretario pueda anular la factura.";
                        }
                        TIPO_MENSAJE = "2";
                        acceso = "ver_agen.htm";
                        
                    } else {
                        TIPO_MENSAJE = metodosAgendamiento.anular_agendamiento(txt_id_agend, txt_item_agend, txt_id_paciente);
                        if (TIPO_MENSAJE.equals("1")) {
                            MENSAJE = "Se ha anulado correctamente el agendamiento.";
                            acceso = "anular_agend.htm";
                        } else if (TIPO_MENSAJE.equals("2")) {
                            MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                            acceso = "ver_agen.htm";
                        }
                    }
                    System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");
                    
                    // CARGAR LISTA 
//                    List<BeanAgendamiento> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosAgendamiento.cargar_grilla_anular_agen(sesionDatosUsuario, NRO_PAG, cant_min_fija, IDPACIENTE);
                    
                    var_redireccionar = 1;
                    // PASAR DATOS AL JSP 
                    request.setAttribute("CPC_AA_MENSAJE", MENSAJE);
                    request.setAttribute("CPC_AA_TIPO_MENSAJE", TIPO_MENSAJE);
                    request.setAttribute("CPC_AA_LISTA_FILTRO", null);
                    request.setAttribute("CPC_AA_CBX_MOSTRAR", "");
                    request.setAttribute("CPC_AA_TXT_FILTRAR_NRO_FACT", "");
                    request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_INI", "");
                    request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_FIN", "");
                    request.setAttribute("CPC_AA_CHECK_FILTRAR_CLIE_01", "");
                    request.setAttribute("CPC_AA_TXT_FILTRAR_CLIE_ID", "");
                    request.setAttribute("CPC_AA_CHECK_FILTRAR_CLINICA_01", "");
                    request.setAttribute("CPC_AA_TXT_FILTRAR_CLINICA_ID", "");
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionAnuAgen.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "anular_agend.htm";
                    sesionDatosUsuario.setAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            } else if (accionAnuFact != null) { // PAGINA :     ANULAR FACTURA 
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__CAJA_-_ANULAR_FACTURA__--------------------------");
                System.out.println(".");System.out.println(".");
                if (isNumber(accionAnuFact) || accionAnuFact.equalsIgnoreCase("<<") || accionAnuFact.equalsIgnoreCase(">>")) {
                    System.out.println("___IS_NUMBER___");
                    acceso = "anular_fact.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_PC_ANU_FACT", request, response, sesionDatosUsuario, acceso, var_redireccionar, accionAnuFact, 2);
                    
                } else if (accionAnuFact.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    // OBTENER VALORES DE LA CAJA DE FILTRO 
                    String cbxMostrar = (String) request.getParameter("cM");
                    String txtBuscarNroFact = (String) request.getParameter("tFNF"); // tFNF : txt filtrar nro factura 
                    if (txtBuscarNroFact == null || txtBuscarNroFact.isEmpty()) {
                        txtBuscarNroFact = "";
                    }
                    String txtFechaIni = (String) request.getParameter("tFFI"); // tFFI : txt filtrar fecha inicio 
                    if (txtFechaIni == null || txtFechaIni.isEmpty()) {
                        txtFechaIni = "";
                    }
                    String txtFechaFin = (String) request.getParameter("tFFF"); // tFFF : txt filtrar fecha fin 
                    if (txtFechaFin == null || txtFechaFin.isEmpty()) {
                        txtFechaFin = "";
                    }
                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
                    if (txtClienteId == null || txtClienteId.isEmpty()) {
                        txtClienteId = "";
                    }
                    System.out.println("------------__VAR_FILTRAR__-----------");
                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
                    System.out.println("_  _BUSCAR_NRO_FACT:   "+txtBuscarNroFact);
                    System.out.println("_  _FECHA_INICIO :   "+txtFechaIni);
                    System.out.println("_  _FECHA_FIN    :   "+txtFechaFin);
                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
                    System.out.println("--------------------------------------");
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                        checkClienteFiltro = "OFF";
//                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
//                        List<BeanPersona> listaDatosCliente;
//                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
//                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
//                        BeanPersona datosClienteNew = null;
//                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
//                        while(iterCliente.hasNext()) {
//                            datosClienteNew = iterCliente.next();
//
//                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
//                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
//                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
//                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
//                        }
//
//                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
//                        System.out.println("-------------------------------------------");
//                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
//                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
//                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
//                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
//                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
//                        System.out.println("-------------------------------------------");
                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClienteId = "";
                    }
                    
                    // CARGAR LISTA 
                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
                    String varEstado = "C";// VARIABLE QUE UTILIZO PARA FILTRAR EL SELECT POR TODAS LAS FACTURAS CON ESTADO ACTIVO o COBRADO 
                    String txt_IDCLINICA = ""; // VARIABLE QUE LA DEJO VACIO PARA QUE EN EL METODO SE CARGUE CON EL IDCLINICA DE LOGEO, PERO EN CASO DE QUE HAYA UN FILTRO EN LA PAGINA, ENTONCES AHI SE CARGARIA ESTA VARIABLE Y EL CODIGO PARA HACERLO, Y EN EL METODO FILTRARIA POR ESA IDCLINICA Y NO POR LA DE LOGEO 
                    String IDPACIENTE = ""; // DEJO VACIO PORQUE EN ESTE CASO NO UTILIZO 
                    listaFiltro = metodosFactura.filtrar_paginacion_anular_fact(sesionDatosUsuario, cbxMostrar, txtBuscarNroFact, txtFechaIni, txtFechaFin, txtClienteId, varEstado, txt_IDCLINICA, "");
                    
                    var_redireccionar = 1;
                    acceso = "anular_fact.htm";
                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AF_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_AF_MENSAJE", mensaje);
                    request.setAttribute("CPC_AF_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CPC_AF_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CPC_AF_TXT_FILTRAR_NRO_FACT", txtBuscarNroFact);
                    request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CPC_AF_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
                    request.setAttribute("CPC_AF_TXT_FILTRAR_CLIE_ID", txtClienteId);
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accionAnuFact.equalsIgnoreCase("Ver")) {
                    /*
                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE FACTURA 
                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
                    */
                    ControllerFactura controllerFact = new ControllerFactura();
                    controllerFact.ver_factura(request, sesionDatosUsuario, metodosFactura, metodosPersona, formatear, idPersona, var_redireccionar, acceso);
                    var_redireccionar = 1;
                    acceso = "ver_factura.htm";
                    sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_FACTURA Y ASI NO ME VUELVA ATRAS EN FACTURA SINO EN RESUMEN FACTURA 
                    
                } else if (accionAnuFact.equalsIgnoreCase("PreAnular")) {
                    System.out.println("---------------------__PRE_ANULAR__--------------------------");
                    /*
                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE FACTURA 
                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
                    */
                    ControllerFactura controllerFact = new ControllerFactura();
                    controllerFact.ver_factura(request, sesionDatosUsuario, metodosFactura, metodosPersona, formatear, idPersona, var_redireccionar, acceso);
                    var_redireccionar = 1;
                    acceso = "ver_factura.htm";
                    sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_FACTURA Y ASI NO ME VUELVA ATRAS EN FACTURA SINO EN RESUMEN FACTURA 
                    request.setAttribute("FACT_BTN_ANULAR", "2"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE ANULAR EN LA PAGINA DE "VER_FACTURA" 
                    
                } else if (accionAnuFact.equalsIgnoreCase("Anular")) {
                    System.out.println("---------------------__ANULAR__--------------------------");
                    String txt_idfactura = (String) request.getParameter("tIF");
                    String txt_idcliente = (String) request.getParameter("tIC");
                    System.out.println("------------__VAR_ANULAR__-----------");
                    System.out.println("_  _ID_FACTURA:   "+txt_idfactura);
                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
                    System.out.println("-------------------------------------");
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    
                    // ANULAR LA FACTURA 
                    String MENSAJE = null, TIPO_MENSAJE = "";
                    if (metodosFactura.ctrlFichaAtencion(sesionDatosUsuario, txt_idfactura, txt_idcliente) == true) {
                        String COD_IDATENCION = (String) sesionDatosUsuario.getAttribute("VALI_FA_COD_IDATENCION");
                        if (COD_IDATENCION == null || COD_IDATENCION.isEmpty()) {
                            MENSAJE = "Debe de anular primero la ficha de atención de esta factura, para poder anular la factura.";
                        } else {
                            MENSAJE = "Debe de anular primero la ficha de atención con código: "+COD_IDATENCION+" para poder anular la factura.";
                        }
                        TIPO_MENSAJE = "2";
                        
                    } else {
                        TIPO_MENSAJE = metodosFactura.anular_factura(txt_idfactura, txt_idcliente);
                        if (TIPO_MENSAJE.equals("1")) {
                            MENSAJE = "Se ha anulado correctamente la factura.";
                        } else if (TIPO_MENSAJE.equals("2")) {
                            MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                        }
                    }
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    
                    // CARGAR LISTA 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosFactura.cargar_grilla_anular();
                    
                    var_redireccionar = 1;
                    acceso = "anular_fact.htm";
                    // PASAR DATOS AL JSP 
//                    if (TIPO_MENSAJE.equals("1")) {
                        request.setAttribute("CPC_AF_MENSAJE", MENSAJE);
                        request.setAttribute("CPC_AF_TIPO_MENSAJE", TIPO_MENSAJE);
                        request.setAttribute("CPC_AF_LISTA_FILTRO", null);
                        request.setAttribute("CPC_AF_CBX_MOSTRAR", "");
                        request.setAttribute("CPC_AF_TXT_FILTRAR_NRO_FACT", "");
                        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_INI", "");
                        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_FIN", "");
                        request.setAttribute("CPC_AF_CHECK_FILTRAR_CLIE_01", "");
                        request.setAttribute("CPC_AF_TXT_FILTRAR_CLIE_ID", "");
//                    } else {
//                        listaFiltro = (List<BeanFacturaCab>) request.getAttribute("CPC_AF_LISTA_FILTRO");
//                        request.setAttribute("CPC_AF_MENSAJE", MENSAJE);
//                        request.setAttribute("CPC_AF_TIPO_MENSAJE", TIPO_MENSAJE);
//                        request.setAttribute("CPC_AF_LISTA_FILTRO", listaFiltro);
//                        request.setAttribute("CPC_AF_CBX_MOSTRAR", "");
//                        request.setAttribute("CPC_AF_TXT_FILTRAR_NRO_FACT", "");
//                        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_INI", "");
//                        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_FIN", "");
//                        request.setAttribute("CPC_AF_CHECK_FILTRAR_CLIE_01", "");
//                        request.setAttribute("CPC_AF_TXT_FILTRAR_CLIE_ID", "");
//                    }
                    
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionAnuFact.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "anular_fact.htm";
                    sesionDatosUsuario.setAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            } else if (accionEmpResumen != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_RESUMEN_EMPEÑO__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpResumen.equalsIgnoreCase("Anular")) {
//                    System.out.println("---------------------__ANULAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String txt_idempenho = (String) request.getParameter("tIE");
//                    String txt_idcliente = (String) request.getParameter("tIC");
//                    System.out.println("------------__VAR_ANULAR__-----------");
//                    System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
//                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
//                    System.out.println("-------------------------------------");
//                    
//                    String TIPO_MENSAJE="", MENSAJE=null;
//                    // VALIDACIONES
//                    if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente, request) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO YA CUENTA CON PAGOS (CUENTA CLIENTE) 
//                        String NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("VALI_PAGO_EMP_NRO_FACTURA");
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "No puede anular el empeño porque cuenta con facturas cobradas("+NRO_FACTURA+").";
//                        
//                    } else if (metodosEmpenho.ctrlFacturasEmp(txt_idempenho, request) == true) { // VALIDACION PARA CONTROLAR EL DETALLE DEL EMPEÑO, SI ES QUE TIENE ALGUNA FACTURA COBRADA (STOCK)
//                        String NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("VALI_CTRL_FACT_EMP_NRO_FACTURA");
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "No puede anular el empeño porque cuenta con facturas cobradas("+NRO_FACTURA+").";
//                        
//                    } else {
//                        // ANULAR LA FACTURA 
//                        TIPO_MENSAJE = metodosEmpenho.anular_empenho(txt_idempenho, txt_idcliente);
//                        if (TIPO_MENSAJE.equals("1")) {
//                            MENSAJE = "Se ha anulado correctamente el empeño.";
//                        } else {
//                            MENSAJE = "Se genero un error, intentelo mas tarde.";
//                        }
//                    } // end validaciones 
//                    
//                    // CARGAR LISTA 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.re_cargar_grilla();
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_resumen.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_RE_MENSAJE", MENSAJE);
//                    request.setAttribute("CPC_RE_TIPO_MENSAJE", TIPO_MENSAJE);
//                    request.setAttribute("CPC_RE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_RE_CBX_MOSTRAR", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_NRO_EMP", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_INI", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_FIN", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_INI", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_FIN", "");
//                    request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE_01", "");
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_CLIE_ID", "");
//                    
//                }  else if (accionEmpResumen.equalsIgnoreCase("PreAnular")) {
//                    System.out.println("---------------------__PRE_ANULAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "9"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_ANULAR", "9"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE ANULAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                }  else if (accionEmpResumen.equalsIgnoreCase("PreCancelar")) {
//                    System.out.println("---------------------__PRE_CANCELAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "9"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_CANCELAR", "9"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE CANCELAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpResumen.equalsIgnoreCase("Cancelar")) {
//                    String var_inicial_pag = "RE";
//                    String acceso_pag_ini = "emp_resumen.htm";
//                    acceso = cancelar_empenho_ctrl(request, metodosEmpenho, sesionDatosUsuario, acceso, var_inicial_pag, acceso_pag_ini, var_redireccionar);
//                    var_redireccionar = 1;
////                    System.out.println("---------------------__CANCELAR__--------------------------");
////                    // OBTENER VALORES DE LA CAJA DE FILTRO 
////                    String txt_idempenho = (String) request.getParameter("tIE");
////                    String txt_idcliente = (String) request.getParameter("tIC");
////                    System.out.println("------------__VAR_CANCELAR__-----------");
////                    System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
////                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
////                    System.out.println("---------------------------------------");
////                    
////                    int band_resumenEmp = 0; // BANDERA QUE UTILIZO PARA PODER REDIRECCIONAR A LA PAGINA DE RESUMEN EMPEÑO EN CASO DE QUE SALTE ALGUNA VALIDACION 
////                    String TIPO_MENSAJE="", MENSAJE=null;
////                    // VALIDACIONES 
////                    if (metodosEmpenho.controlEstLiqEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA LIQUIDADO 
////                        band_resumenEmp = 1;
////                        acceso = "emp_resumen.htm";
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede cancelar el empeño porque se encuentra liquidado.";
////                    } else if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
////                        band_resumenEmp = 1;
////                        acceso = "emp_resumen.htm";
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede cancelar el empeño porque se encuentra anulado.";
////                    } else {
////                        // REDIRECCIONAR A FACTURA PARA PODER CANCELAR LA CUENTA DEL EMPEÑO 
////                        acceso = cancelar_empenho(request, sesionDatosUsuario, metodosIniSes, txt_idempenho, txt_idcliente, var_redireccionar, acceso);
////                    } // end validaciones 
////                    
////                    var_redireccionar = 1;
////                    if (band_resumenEmp == 1) {
////                        // CARGAR LISTA DE LA PAGINA DE RESUMEN EMPEÑO 
////                        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
////                        listaFiltro = metodosEmpenho.re_cargar_grilla();
////                        
////                        // PASAR DATOS AL JSP 
////                        request.setAttribute("CPC_RE_MENSAJE", MENSAJE);
////                        request.setAttribute("CPC_RE_TIPO_MENSAJE", TIPO_MENSAJE);
////                        request.setAttribute("CPC_RE_LISTA_FILTRO", listaFiltro);
////                        request.setAttribute("CPC_RE_CBX_MOSTRAR", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_NRO_EMP", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_INI", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_FIN", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_INI", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_FIN", "");
////                        request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE_01", "");
////                        request.setAttribute("CPC_RE_TXT_FILTRAR_CLIE_ID", "");
////                    }
//                    
//                }  else if (accionEmpResumen.equalsIgnoreCase("PreExpirar")) {
//                    System.out.println("---------------------__PRE_EXPIRAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "9"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_EXPIRAR", "9"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE EXPIRAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpResumen.equalsIgnoreCase("Expirar")) {
//                    expirar_empenho(request, metodosEmpenho, "RE");
//                    var_redireccionar = 1;
//                    acceso = "emp_resumen.htm";
////                    System.out.println("---------------------__EXPIRAR__--------------------------");
////                    // OBTENER VALORES DE LA CAJA DE FILTRO 
////                    String txt_idempenho = (String) request.getParameter("tIE");
////                    String txt_idcliente = (String) request.getParameter("tIC");
////                    System.out.println("------------__VAR_EXPIRAR__-----------");
////                    System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
////                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
////                    System.out.println("-------------------------------------");
////                    
////                    String TIPO_MENSAJE="", MENSAJE=null;
////                    // VALIDACIONES 
////                    if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede expirar el empeño porque se encuentra Anulado.";
////                    } else if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA FACTURADO 
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede expirar el empeño porque cuenta con facturas.";
////                    } else {
////                        TIPO_MENSAJE = metodosEmpenho.expirar_empenho(txt_idempenho, txt_idcliente);
////                        if (TIPO_MENSAJE.equals("1")) {
////                            MENSAJE = "Se ha marcado como expirado el empeño correctamente.";
////                        } else if (TIPO_MENSAJE.equals("2")) {
////                            MENSAJE = "Se genero un error, intentelo mas tarde.";
////                        }
////                    } // end validaciones 
////                    
////                    // CARGAR LISTA 
////                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
////                    listaFiltro = metodosEmpenho.re_cargar_grilla();
////                    
////                    var_redireccionar = 1;
////                    acceso = "emp_resumen.htm";
////                    // PASAR DATOS AL JSP 
////                    request.setAttribute("CPC_RE_MENSAJE", MENSAJE);
////                    request.setAttribute("CPC_RE_TIPO_MENSAJE", TIPO_MENSAJE);
////                    request.setAttribute("CPC_RE_LISTA_FILTRO", listaFiltro);
////                    request.setAttribute("CPC_RE_CBX_MOSTRAR", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_NRO_EMP", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_INI", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_FIN", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_INI", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_FIN", "");
////                    request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE_01", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_CLIE_ID", "");
//                    
//                }  else if (accionEmpResumen.equalsIgnoreCase("PreLiquidar")) {
//                    System.out.println("---------------------__PRE_LIQUIDAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "9"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_LIQUIDAR", "9"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE LIQUIDAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpResumen.equalsIgnoreCase("Liquidar")) {
//                    liquidar_empenho(request, metodosEmpenho, "RE");
//                    var_redireccionar = 1;
//                    acceso = "emp_resumen.htm";
////                    System.out.println("---------------------__LIQUIDAR__--------------------------");
////                    // OBTENER VALORES DE LA CAJA DE FILTRO 
////                    String txt_idempenho = (String) request.getParameter("tIE");
////                    String txt_idcliente = (String) request.getParameter("tIC");
////                    System.out.println("------------__VAR_LIQUIDAR__-----------");
////                    System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
////                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
////                    System.out.println("---------------------------------------");
////                    
////                    String TIPO_MENSAJE="", MENSAJE=null;
////                    // VALIDACIONES 
////                    if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede liquidar el empeño porque se encuentra Anulado.";
////                    } else if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA FACTURADO 
////                        TIPO_MENSAJE = "2";
////                        MENSAJE = "No se puede liquidar el empeño porque cuenta con facturas.";
////                    } else {
////                        TIPO_MENSAJE = metodosEmpenho.liquidar_empenho(txt_idempenho, txt_idcliente);
////                        if (TIPO_MENSAJE.equals("1")) {
////                            MENSAJE = "Se ha colocado como liquidado correctamente el empeño.";
////                        } else if(TIPO_MENSAJE.equals("2")) {
////                            MENSAJE = "Se genero un error, intentelo mas tarde.";
////                        }
////                    } // end validaciones 
////                    
////                    // CARGAR LISTA 
////                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
////                    listaFiltro = metodosEmpenho.re_cargar_grilla();
////                    
////                    var_redireccionar = 1;
////                    acceso = "emp_resumen.htm";
////                    // PASAR DATOS AL JSP 
////                    request.setAttribute("CPC_RE_MENSAJE", MENSAJE);
////                    request.setAttribute("CPC_RE_TIPO_MENSAJE", TIPO_MENSAJE);
////                    request.setAttribute("CPC_RE_LISTA_FILTRO", listaFiltro);
////                    request.setAttribute("CPC_RE_CBX_MOSTRAR", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_NRO_EMP", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_INI", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_FIN", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_INI", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_FIN", "");
////                    request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE_01", "");
////                    request.setAttribute("CPC_RE_TXT_FILTRAR_CLIE_ID", "");
//                    
//                } else if (accionEmpResumen.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tREFNE"); // tREFNE : txt resumen empeño filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tREFPI"); // tREFPI : txt resumen empeño fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tREFPF"); // tREFPF : txt resumen empeño fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tREFVI"); // tREFVI : txt resumen empeño fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tREFVF"); // tREFVF : txt resumen empeño  fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE emp.IDEMPENHO > 0 "; // NO IMPORTA ESTE WHERE POR IDEMPENHO MAYOR A CERO PERO LE COLOCO YA QUE EN EL METODO SOLO SE HACE "AND" POR LOS FILTROS Y PARA NO EDITARLOS ENTONCES LE PASO ESTE Y LE COMENTO EL QUE WHERE QUE NO TRAE EMPEÑOS ANULADOS YA QUE AHORA MUESTRO (18/11/2021) // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
////                    String varEstadoVentana = "WHERE emp.ESTADO NOT IN ('X') "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Emp", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_resumen.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_RE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_RE_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_RE_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_RE_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                }  else if (accionEmpResumen.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "9"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                }
                
                
            } else if (accionEmpAnular != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_ANULAR_EMPEÑO__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpAnular.equalsIgnoreCase("Anular")) {
//                    System.out.println("---------------------__ANULAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String txt_idempenho = (String) request.getParameter("tIE");
//                    String txt_idcliente = (String) request.getParameter("tIC");
//                    
//                    System.out.println("------------__VAR_ANULAR__-----------");
//                    System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
//                    System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
//                    System.out.println("-------------------------------------");
//                    
//                    String TIPO_MENSAJE="", MENSAJE=null;
//                    // VALIDACIONES
//                    if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente, request) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO YA CUENTA CON PAGOS (CUENTA CLIENTE) 
//                        String NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("VALI_PAGO_EMP_NRO_FACTURA");
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "No puede anular el empeño porque cuenta con facturas cobradas("+NRO_FACTURA+").";
//                        
//                    } else if (metodosEmpenho.ctrlFacturasEmp(txt_idempenho, request) == true) { // VALIDACION PARA CONTROLAR EL DETALLE DEL EMPEÑO, SI ES QUE TIENE ALGUNA FACTURA COBRADA (STOCK)
//                        String NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("VALI_CTRL_FACT_EMP_NRO_FACTURA");
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "No puede anular el empeño porque cuenta con facturas cobradas("+NRO_FACTURA+").";
//                        
//                    } else {
//                        // ANULAR LA FACTURA 
//                        TIPO_MENSAJE = metodosEmpenho.anular_empenho(txt_idempenho, txt_idcliente);
//                        if (TIPO_MENSAJE.equals("1")) {
//                            MENSAJE = "Se ha anulado correctamente el empeño.";
//                        } else {
//                            MENSAJE = "Se genero un error, intentelo mas tarde.";
//                        }
//                    }
//                    
//                    // CARGAR LISTA 
////                    String varEstadoVentana = "WHERE cab.ESTADO NOT IN ('X') "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
////                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados("", varEstadoVentana, "", "", "", "", "", "");
//                    listaFiltro = metodosEmpenho.ae_cargar_grilla();
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_anular.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AE_MENSAJE", MENSAJE);
//                    request.setAttribute("CPC_AE_TIPO_MENSAJE", TIPO_MENSAJE);
//                    request.setAttribute("CPC_AE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_AE_CBX_MOSTRAR", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_NRO_EMP", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_PRES_INI", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_PRES_FIN", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_VENC_INI", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_VENC_FIN", "");
//                    request.setAttribute("CPC_AE_CHECK_FILTRAR_CLIE_01", "");
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_CLIE_ID", "");
//                    
//                } else if (accionEmpAnular.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tAEFNE"); // tAEFNE : txt anular empeño filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tAEFPI"); // tAEFPI : txt anular empeño fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tAEFPF"); // tAEFPF : txt anular empeño fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tAEFVI"); // tAEFVI : txt anular empeño fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tAEFVF"); // tAEFVF : txt anular empeño  fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE emp.ESTADO NOT IN ('X') "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Emp", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_anular.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_AE_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_AE_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_AE_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                }  else if (accionEmpAnular.equalsIgnoreCase("PreAnular")) { // BOTON PARA REDIRECCIONAR A LA PAGINA DE VER_EMPEÑO Y DE AHI QUE EL USUARIO ACTIVE EL BOTON DE ANULAR 
//                    System.out.println("---------------------__PRE_ANULAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "8"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_ANULAR", "8"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                }  else if (accionEmpAnular.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "8"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                }
                
                
            } else if (accionEmpVig != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_EMPEÑO_VIGENTES__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpVig.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tEVFNE"); // tEVFNE : txt empeños vigentes filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tEVFPI"); // tEVFPI : txt empeños vigentes fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tEVFPF"); // tEVFPF : txt empeños vigentes fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tEVFVI"); // tEVFVI : txt empeños vigentes fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tEVFVF"); // tEVFVF : txt empeños vigentes fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE emp.ESTADO = 'A' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Emp", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_vigentes.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_EV_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_EV_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_EV_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_EV_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionEmpVig.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                } else if (accionEmpVig.equalsIgnoreCase("PreLiquidar")) { // BOTON PARA REDIRECCIONAR A LA PAGINA DE VER_EMPEÑO Y DE AHI QUE EL USUARIO ACTIVE EL BOTON DE LIQUIDAR 
//                    System.out.println("---------------------__PRE_LIQUIDAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_LIQUIDAR", "1"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE LIQUIDAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpVig.equalsIgnoreCase("Liquidar")) {
//                    System.out.println("---------------------__LIQUIDAR__--------------------------");
//                    liquidar_empenho(request, metodosEmpenho, "EV");
//                    var_redireccionar = 1;
//                    acceso = "emp_vigentes.htm";
//                    
//                } else if (accionEmpVig.equalsIgnoreCase("PreCancelar")) { // BOTON PARA REDIRECCIONAR A LA PAGINA DE VER_EMPEÑO Y DE AHI QUE EL USUARIO ACTIVE EL BOTON DE CANCELAR  
//                    System.out.println("---------------------__PRE_CANCELAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_CANCELAR", "1"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE CANCELAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpVig.equalsIgnoreCase("Cancelar")) {
//                    System.out.println("---------------------__CANCELAR__--------------------------");
//                    String var_inicial_pag = "EV";
//                    String acceso_pag_ini = "emp_vigentes.htm";
//                    acceso = cancelar_empenho_ctrl(request, metodosEmpenho, sesionDatosUsuario, acceso, var_inicial_pag, acceso_pag_ini, var_redireccionar);
//                    var_redireccionar = 1;
//                }
                
                
            } else if (accionEmpPorVencer != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_EMPEÑO_POR_VENCER__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpPorVencer.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tEPVFNE"); // tEPVFNE : txt empeños por vencer filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tEPVFPI"); // tEPVFPI : txt empeños por vencer fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tEPVFPF"); // tEPVFPF : txt empeños por vencer fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tEPVFVI"); // tEPVFVI : txt empeños por vencer fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tEPVFVF"); // tEPVFVF : txt empeños por vencer fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    // OBTENGO LA FECHA DE HOY Y LUEGO EL MES EXTRAIGO EL MES ACTUAL PARA FILTRAR POR EL PARA SABER LOS EMPEÑOS QUE VENCEN ESTE MES 
//                    // PARA QUE SEA UN EMPEÑO QUE ESTA POR VENCER TIENE QUE TENER EL ESTADO ACTIVO, EL MES DE LA FECHA DE VENCIMIENTO DEBE DE SER IGUAL AL MES ACTUAL Y LA FECHA DE VENCIMIENTO DEBE DE SER MAYOR A LA FECHA ACTUAL O IGUAL, PORQUE SI FUESE MENOR ENTONCES EL EMPEÑO YA SERIA VENCIDO 
//                    String varFechaHoy = metodosIniSes.traerFechaHoy();
//                    System.out.println("_   _FECHA_HOY:     "+varFechaHoy);
//                    String varMesActual = metodosIniSes.getDatoFecha(2, varFechaHoy);
//                    System.out.println("_   __MES_HOY_:     "+varMesActual);
//                    String varEstadoVentana = "WHERE EXTRACT(MONTH FROM cta.FEC_VENCIMIENTO) = '"+varMesActual+"' \n" +
//                        "AND (SELECT DATE_FORMAT(FEC_VENCIMIENTO, '%Y-%m-%d') FROM cc_cuenta_cliente WHERE IDEMPENHO = emp.IDEMPENHO AND ESTADO = 'A' ORDER BY IDCUENTACLIENTE ASC LIMIT 1) >= '"+varFechaHoy+"' \n" + // FILTRO POR EL SUBSELECT PARA EVITAR TRAER OTRA LINEA QUE CUMPLA CON LA CONDICION 
////                        "AND cta.FEC_VENCIMIENTO >= '"+varFechaHoy+"' \n" +
//                        "AND cta.ESTADO = 'A' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Cta", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_por_vencer.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_EPV_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_EPV_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_EPV_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_EPV_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionEmpPorVencer.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                } else if (accionEmpPorVencer.equalsIgnoreCase("PreLiquidar")) {
//                    System.out.println("---------------------__PRE_LIQUIDAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_LIQUIDAR", "2"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE LIQUIDAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpPorVencer.equalsIgnoreCase("Liquidar")) {
//                    System.out.println("---------------------__LIQUIDAR__--------------------------");
//                    liquidar_empenho(request, metodosEmpenho, "EPV");
//                    var_redireccionar = 1;
//                    acceso = "emp_por_vencer.htm";
//                    
//                } else if (accionEmpPorVencer.equalsIgnoreCase("PreCancelar")) {
//                    System.out.println("---------------------__PRE_CANCELAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_CANCELAR", "2"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE CANCELAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpPorVencer.equalsIgnoreCase("Cancelar")) {
//                    System.out.println("---------------------__CANCELAR__--------------------------");
//                    String var_inicial_pag = "EPV";
//                    String acceso_pag_ini = "emp_por_vencer.htm";
//                    acceso = cancelar_empenho_ctrl(request, metodosEmpenho, sesionDatosUsuario, acceso, var_inicial_pag, acceso_pag_ini, var_redireccionar);
//                    var_redireccionar = 1;
//                }
                
                
            } else if (accionEmpVencidos != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_EMPEÑO_VENCIDOS__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpVencidos.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tEVeFNE"); // tEVeFNE : txt empeños vencidos filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tEVeFPI"); // tEVeFPI : txt empeños vencidos fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tEVeFPF"); // tEVeFPF : txt empeños vencidos fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tEVeFVI"); // tEVeFVI : txt empeños vencidos fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tEVeFVF"); // tEVeFVF : txt empeños vencidos fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    // AGREGO AL WHERE UN AND MAS PARA PODER FILTRAR POR LOS EMPEÑOS VENCIDOS, YA QUE ESTOS SERIAN EMPEÑOS ACTIVOS PERO QUE SU FECHA DE VENCIMIENTO YA PASO Y NO FUE LIQUIDADO HASTA EL MOMENTO 
//                    String varFechaHoy = metodosIniSes.traerFechaHoy();
//                    System.out.println("_   _FECHA_HOY:     "+varFechaHoy);
//                    String varEstadoVentana = "WHERE cta.ESTADO = 'A' \n"
//                        + "AND (SELECT DATE_FORMAT(FEC_VENCIMIENTO, '%Y-%m-%d') FROM cc_cuenta_cliente WHERE IDEMPENHO = emp.IDEMPENHO AND ESTADO = 'A' ORDER BY IDCUENTACLIENTE ASC LIMIT 1) < '"+varFechaHoy+"' \n"; // EL SUBSELECT EVITA QUE SE TRAIGA OTRAS LINEAS QUE CUMPLAN CON LA MISMA CONDICION  // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
////                        + "AND cta.FEC_VENCIMIENTO < '"+varFechaHoy+"' \n"; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Cta", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_vencidos.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_EVe_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_EVe_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_EVe_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_EVe_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "3"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("PreExpirar")) {
//                    System.out.println("---------------------__PRE_EXPIRAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "3"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_EXPIRAR", "3"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE EXPIRAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("Expirar")) {
//                    System.out.println("---------------------__EXPIRAR__--------------------------");
//                    expirar_empenho(request, metodosEmpenho, "EVe");
//                    var_redireccionar = 1;
//                    acceso = "emp_vencidos.htm";
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("PreLiquidar")) {
//                    System.out.println("---------------------__PRE_LIQUIDAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "3"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_LIQUIDAR", "3"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE LIQUIDAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("Liquidar")) {
//                    System.out.println("---------------------__LIQUIDAR__--------------------------");
//                    liquidar_empenho(request, metodosEmpenho, "EVe");
//                    var_redireccionar = 1;
//                    acceso = "emp_vencidos.htm";
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("PreCancelar")) {
//                    System.out.println("---------------------__PRE_CANCELAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "3"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_CANCELAR", "3"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE CANCELAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpVencidos.equalsIgnoreCase("Cancelar")) {
//                    System.out.println("---------------------__CANCELAR__--------------------------");
//                    String var_inicial_pag = "EVe";
//                    String acceso_pag_ini = "emp_vencidos.htm";
//                    acceso = cancelar_empenho_ctrl(request, metodosEmpenho, sesionDatosUsuario, acceso, var_inicial_pag, acceso_pag_ini, var_redireccionar);
//                    var_redireccionar = 1;
//                }
                
                
            } else if (accionEmpExpirados != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_EMPEÑOS_EXPIRADOS__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpExpirados.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tEEFNE"); // tEEFNE : txt empeños expirados filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tEEFPI"); // tEEFPI : txt empeños expirados fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tEEFPF"); // tEEFPF : txt empeños expirados fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tEEFVI"); // tEEFVI : txt empeños expirados fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tEEFVF"); // tEEFVF : txt empeños expirados fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE emp.ESTADO = 'E' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Emp", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_expirados.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_EE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_EE_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_EE_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_EE_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionEmpExpirados.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "4"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                } else if (accionEmpExpirados.equalsIgnoreCase("PreLiquidar")) {
//                    System.out.println("---------------------__PRE_LIQUIDAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "4"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_LIQUIDAR", "4"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE LIQUIDAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpExpirados.equalsIgnoreCase("Liquidar")) {
//                    System.out.println("---------------------__LIQUIDAR__--------------------------");
//                    liquidar_empenho(request, metodosEmpenho, "EE");
//                    var_redireccionar = 1;
//                    acceso = "emp_expirados.htm";
//                    
//                } else if (accionEmpExpirados.equalsIgnoreCase("PreCancelar")) {
//                    System.out.println("---------------------__PRE_CANCELAR__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "4"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    request.setAttribute("EMP_BTN_CANCELAR", "4"); // VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE CANCELAR EN LA PAGINA DE "VER_EMPEÑO" 
//                    
//                } else if (accionEmpExpirados.equalsIgnoreCase("Cancelar")) {
//                    System.out.println("---------------------__CANCELAR__--------------------------");
//                    String var_inicial_pag = "EE";
//                    String acceso_pag_ini = "emp_expirados.htm";
//                    acceso = cancelar_empenho_ctrl(request, metodosEmpenho, sesionDatosUsuario, acceso, var_inicial_pag, acceso_pag_ini, var_redireccionar);
//                    var_redireccionar = 1;
//                }
                
                
            } else if (accionEmpLiquidados != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_EMPEÑOS_LIQUIDADOS__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionEmpLiquidados.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tELFNE"); // tELFNE : txt empeños liquidados filtrar nro empeño 
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tELFPI"); // tELFPI : txt empeños liquidados fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tELFPF"); // tELFPF : txt empeños liquidados fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tELFVI"); // tELFVI : txt empeños liquidados fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tELFVF"); // tELFVF : txt empeños liquidados fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE emp.ESTADO = 'L' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoEstados(cbxMostrar, "Emp", varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "emp_liquidados.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_EL_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_EL_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_EL_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_EL_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionEmpLiquidados.equalsIgnoreCase("Ver")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "5"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                }
                
                
            } else if (accionPrendasEmp != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__INVENTARIO_PRENDAS_EMPEÑADAS__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionPrendasEmp.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tPEFNE"); // tPEFNE : txt prendas empeñadas filtrar nro empeño (y desc producto)
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tPEFPI"); // tPEFPI : txt prendas empeñadas fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tPEFPF"); // tPEFPF : txt prendas empeñadas fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tPEFVI"); // tPEFVI : txt prendas empeñadas fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tPEFVF"); // tPEFVF : txt prendas empeñadas fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE cab.ESTADO IN ('A', 'E') "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
////                    String varEstadoVentana = "WHERE cab.ESTADO = 'A' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoDetalle(cbxMostrar, varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "prendas_emp.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_PE_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_PE_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_PE_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_PE_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionPrendasEmp.equalsIgnoreCase("Ver Empenho") || accionPrendasEmp.equalsIgnoreCase("Ver Empeño")) {
//                    System.out.println("---------------------__VER__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "6"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                    
//                } else if (accionPrendasEmp.equalsIgnoreCase("Ver Producto")) {
//                    System.out.println("---------------------__VER_PRODUCTO__--------------------------");
//                    /*
//                    * OBSERVACION: PARA REDIRECCIONAR A LA PAGINA DE PRODUCTOS PARA VER LOS DATOS, SOLO NECESITO PASARLE EL IDPRODUCTO Y LLAMAR A LA VENTANA 
//                     */
//                    String idProducto = (String) request.getParameter("tIDP"); // txt id detalle producto 
//                    
//                    var_redireccionar = 1;
//                    acceso = "datos_producto.htm";
//                    sesionDatosUsuario.setAttribute("ID_PRODUCTO", idProducto);
//                    sesionDatosUsuario.setAttribute("PRODUCTO_BOTON_VOLVER_ATRAS", "1"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_PRODUCTO Y ASI NO ME VUELVA ATRAS EN PRODUCTO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                }
                
                
            } else if (accionPrendasEnVenta != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__PC_PRENDAS_EN_VENTA__--------------------------");
                System.out.println(".");System.out.println(".");
//                if (accionPrendasEnVenta.equalsIgnoreCase("Filtrar")) {
//                    System.out.println("---------------------__FILTRAR__--------------------------");
//                    // OBTENER VALORES DE LA CAJA DE FILTRO 
//                    String cbxMostrar = (String) request.getParameter("cM");
//                    String txtBuscarNroEmp = (String) request.getParameter("tPEVFNE"); // tPEVFNE : txt prendas en venta filtrar nro empeño (y desc producto)
//                    if (txtBuscarNroEmp == null || txtBuscarNroEmp.isEmpty()) {
//                        txtBuscarNroEmp = "";
//                    }
//                    String txtFechaPresIni = (String) request.getParameter("tPEVFPI"); // tPEVFPI : txt prendas en venta fecha prestamo inicio 
//                    if (txtFechaPresIni == null || txtFechaPresIni.isEmpty()) {
//                        txtFechaPresIni = "";
//                    }
//                    String txtFechaPresFin = (String) request.getParameter("tPEVFPF"); // tPEVFPF : txt prendas en venta fecha prestamo fin 
//                    if (txtFechaPresFin == null || txtFechaPresFin.isEmpty()) {
//                        txtFechaPresFin = "";
//                    }
//                    String txtFechaVencIni = (String) request.getParameter("tPEFVI"); // tPEFVI : txt prendas en venta fecha vencimiento inicio 
//                    if (txtFechaVencIni == null || txtFechaVencIni.isEmpty()) {
//                        txtFechaVencIni = "";
//                    }
//                    String txtFechaVencFin = (String) request.getParameter("tPEVFVF"); // tPEVFVF : txt prendas en venta fecha vencimiento fin 
//                    if (txtFechaVencFin == null || txtFechaVencFin.isEmpty()) {
//                        txtFechaVencFin = "";
//                    }
//                    String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtClienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
//                    if (txtClienteId == null || txtClienteId.isEmpty()) {
//                        txtClienteId = "";
//                    }
//                    System.out.println("------------__VAR_FILTRAR__-----------");
//                    System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
//                    System.out.println("_  _BUSCAR_NRO_EMPE:   "+txtBuscarNroEmp);
//                    System.out.println("_  _FECHA_PRESTAMO_INICIO:   "+txtFechaPresIni);
//                    System.out.println("_  _FECHA_PRESTAMO_FIN   :   "+txtFechaPresFin);
//                    System.out.println("_  _FECHA_VENCIMIENTO_INICIO:  "+txtFechaVencIni);
//                    System.out.println("_  _FECHA_VENCIMIENTO_FIN   :  "+txtFechaVencFin);
//                    System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
//                    System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
//                    System.out.println("--------------------------------------");
//                    
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//                    if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                        checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//                    } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtClienteId = "";
//                    }
//                    
//                    // CARGAR LISTA 
//                    String varEstadoVentana = "WHERE det.ESTADO = 'L' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
////                    String varEstadoVentana = "WHERE cab.ESTADO = 'E' \n"
////                            + "AND det.ESTADO = 'A' "; // VARIABLE QUE UTILIZO PARA PASARLE AL METODO ASI EL METODO SABRA POR CUAL ESTADO DEBE DE FILTRAR YA QUE ENTRE LAS PAGINAS DE PANEL DE CONTROL DE EMPEÑO, SE DIFERENCIAN POR LOS ESTADOS, Y PARA NO CREAR UN METODO POR CADA ESTADO, ENTONCES PASARE LUEGO EL ESTADO Y ASI EN UN SOLO METODO TENDRE TODO 
//                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
//                    listaFiltro = metodosEmpenho.filtrar_EmpenhoDetalle(cbxMostrar, varEstadoVentana, txtBuscarNroEmp, txtFechaPresIni, txtFechaPresFin, txtFechaVencIni, txtFechaVencFin, txtClienteId);
//                    
//                    var_redireccionar = 1;
//                    acceso = "prendas_venta.htm";
//                    // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_PEV_LISTA_FILTRO", listaFiltro);
//                    request.setAttribute("CPC_PEV_CBX_MOSTRAR", cbxMostrar);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_NRO_EMP", txtBuscarNroEmp);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_FEC_PRES_INI", txtFechaPresIni);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_FEC_PRES_FIN", txtFechaPresFin);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_FEC_VENC_INI", txtFechaVencIni);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_FEC_VENC_FIN", txtFechaVencFin);
//                    request.setAttribute("CPC_PEV_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
//                    request.setAttribute("CPC_PEV_TXT_FILTRAR_CLIE_ID", txtClienteId);
//                    
//                } else if (accionPrendasEnVenta.equalsIgnoreCase("Ver Empenho") || accionPrendasEnVenta.equalsIgnoreCase("Ver Empeño")) {
//                    System.out.println("---------------------__VER_EMPENHO__--------------------------");
//                    /*
//                    * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE EMPEÑO 
//                        ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
//                        PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
//                    */
//                    ControllerEmpenho controllerEmp = new ControllerEmpenho();
//                    String idEmpenho = (String) request.getParameter("tIE");
//                    
//                    controllerEmp.ver_empenho(request, sesionDatosUsuario, idPersona, idEmpenho, var_redireccionar, acceso);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_empenho.htm";
//                    sesionDatosUsuario.setAttribute("EMPENHO_BOTON_VOLVER_ATRAS", "7"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_EMPENHO Y ASI NO ME VUELVA ATRAS EN EMPEÑO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                
//                } else if (accionPrendasEnVenta.equalsIgnoreCase("Ver Producto")) {
//                    System.out.println("---------------------__VER_PRODUCTO__--------------------------");
//                    /*
//                    * OBSERVACION: PARA REDIRECCIONAR A LA PAGINA DE PRODUCTOS PARA VER LOS DATOS, SOLO NECESITO PASARLE EL IDPRODUCTO Y LLAMAR A LA VENTANA 
//                    */
//                    String idProducto = (String) request.getParameter("tIDP"); // txt id detalle producto 
//                    
//                    var_redireccionar = 1;
//                    acceso = "datos_producto.htm";
//                    sesionDatosUsuario.setAttribute("ID_PRODUCTO", idProducto);
//                    sesionDatosUsuario.setAttribute("PRODUCTO_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_PRODUCTO Y ASI NO ME VUELVA ATRAS EN PRODUCTO SINO EN LA PAGINA QUE YO LE CONFIGURE DEACUERDO AL NUMERO QUE LE PASO 
//                }
            }
            
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_   __________________________________");
            System.out.println("_   ____ACCESO:     :"+acceso);
            System.out.println("_   ____VAR_REDI:   :"+var_redireccionar);
            System.out.println("_   __________________________________");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("------------------ERROR-CONTROLLER-PANEL-CONTROL--------------------");
            System.out.println("_ERROR EN __PANEL_CONTROL__ doPost():      :"+e.getMessage());
            Logger.getLogger(ControllerPanelControl.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------------------------");
        }
        
// OBSERVACION: 
// UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if (var_redireccionar == 0) {
            response.sendRedirect(acceso);
        } else {
            RequestDispatcher vista = request.getRequestDispatcher(acceso);
            vista.forward(request, response);
        }
    } // END doPost() 
    
    
    
    
    
    
    
    
    /*
    OBSERVACION :
        METODO QUE UTILIZO PARA REDIRECCIONAR A LA PAGINA DE FACTURA DETALLE CARGANDO LOS CAMPOS QUE NECESITO 
        LO REDIRECCIONO AL DETALLE DE LA FACTURA PARA PODER CANCELAR EL EMPEÑO 
    */
//    private String cancelar_empenho(HttpServletRequest request, 
//            HttpSession sesionDatosUsuario, 
//            ModelInicioSesion metodosIniSes, 
//            String PARAM_TXT_IDEMPENHO, 
//            String PARAM_TXT_IDCLIENTE, 
//            int var_redireccionar, 
//            String acceso) {
//        System.out.println(".   .   .   .   .");
//        System.out.println(".       .       .       .       .");
//        System.out.println(".           .           .           .           .");
//        System.out.println("---------------------__CANCELAR EMPENHO__--------------------------");
//        ModelFactura metodosFactura = new ModelFactura();
//        ModelPersona metodosPersona = new ModelPersona();
//        DecimalFormat formatear = new DecimalFormat("###,###,###");
//        // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
//        String TXT_NRO_FACTURA = "";
//        String CBX_TIPO_FACTURA = "Co";
//        String TXT_FECHA_FACTURA = metodosIniSes.traerFechaHoy();
//        String TXT_CLIENTE_ID = PARAM_TXT_IDCLIENTE;
//        BeanPersona datosCliente = new BeanPersona();
//        datosCliente = metodosPersona.datosBasicosPersona(datosCliente, PARAM_TXT_IDCLIENTE);
//        String TXT_CLIENTE_CINRO = datosCliente.getRP_CINRO();
//        String TXT_CLIENTE_NAME = datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
//        String TXT_CLIENTE_RAZONSOCIAL = datosCliente.getRP_RAZON_SOCIAL();
//        String TXT_CLIENTE_RUC = datosCliente.getRP_RUC();
//        // TOTALES DEL DETALLE 
//        String CF_TXT_TOTAL_IVA5 = "0";
//        String CF_TXT_TOTAL_IVA10 = "0";
//        String CF_TXT_TOTAL_GRAV5 = "0";
//        String CF_TXT_TOTAL_GRAV10 = "0";
//        String CF_TXT_TOTAL_IVA = "0";
//        String CF_TXT_TOTAL = "0";
//        System.out.println("------------------__PARAMETER_CABECERA__------------------");
//        System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
//        System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
//        System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
//        System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
//        System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
//        System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
//        System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
//        System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
//        System.out.println("- - - - - - - - -");
//        System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
//        System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
//        System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
//        System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
//        System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
//        System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
//        System.out.println("----------------------------------------------------------");
//
//        // OBTENGO LA NUMERACION DE ITEM DE LA LISTA 
//        int CF_LISTA_ITEM = 1;
////        if (sesionDatosUsuario.getAttribute("CF_LISTA_ITEM") == null) {
////            // SI ES IGUAL A NULL ENTONCES NO CARGO NADA NI VERIFICO NADA Y LE DEJO AL ITEM CON SU VALOR POR DEFECTO 
////        } else {
////            if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
////                System.out.println("_____IF_______ITEM_DETALLE_CARGADO_______");
////                CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
////            }
////        }
//        
//        // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
//        List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
////        if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
////            System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
////            CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
////        }
//
//        /*
//        LA IDEA VA A SER TRAER LA LISTA QUE CARGUE EN LA GRILLA 
//        Y EN UN CAMPO OCULTO VA A TENER UN NOMBRE ESTATICO MAS EL IDCUENTACLIENTE 
//        Y ENTONCES AL RECUPERAR LA MISMA LISTA VOY TRAYENDO POR EL FOR EL CAMPO DE TEXTO 
//        Y SI TIENE UN VALOR BINARIO 
//        */
//        List<BeanCuentaCliente> listaCtaCliente;
//        listaCtaCliente = metodosFactura.cargarCtaClieFilEmpenho(TXT_CLIENTE_ID, PARAM_TXT_IDEMPENHO);
//        Iterator<BeanCuentaCliente> iterCta = listaCtaCliente.iterator();
//        BeanCuentaCliente datosCta = null;
//
//        String CC_IDEMPENHO;
//        int item = 0;
//        while(iterCta.hasNext()) {
//            System.out.println(".");System.out.println(".");System.out.println(".");
//            datosCta = iterCta.next();
//
//            item = item + 1;
//            System.out.println("___WHILE___ "+item+" ___");
//
//            // VARIABLES DE LA LISTA 
//            String IDCUENTACLIENTE = datosCta.getCC_IDCUENTACLIENTE();
//            System.out.println("__ID_CUENTA_CLIENTE:        "+IDCUENTACLIENTE);
//
//            CC_IDEMPENHO = datosCta.getCC_IDEMPENHO();
//            System.out.println("__CC_IDEMPENHO:        :"+CC_IDEMPENHO);
//
//                String CONCEPTO_EMPENHO = datosCta.getCC_COMENTARIO();
////                        String CONCEPTO_EMPENHO = "Empeño nro. "+datosCta.getOEC_NRO_EMPENHO()+", cuota nro. "+datosCta.getCC_NROCUOTA()+".";
//                System.out.println("_   _   _CONEPTO:   "+CONCEPTO_EMPENHO);
//                String CONCEPTO_PRECIO = datosCta.getCC_SALDO().replace(".","");
//                System.out.println("_   _   _PRECIO :   "+CONCEPTO_PRECIO);
//
//                // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES CARGO A LA LISTA DEL DETALLE 
//                BeanFacturaCab datos = new BeanFacturaCab();
//                    datos.setOFD_ITEM(CF_LISTA_ITEM);
//                    datos.setOFD_IDCONCEPTO(Integer.parseInt(IDCUENTACLIENTE));
//                    datos.setOFD_IDTIPOCONCEPTO(1); // 2 : PRODUCTO / 1 : CUENTA CLIENTE 
//                    datos.setOFD_DESCRIPCION_AUX(CONCEPTO_EMPENHO);
//                    datos.setOFD_CANTIDAD(1); // valdor por defecto " 1 "
//
//                    /*
//                    * OBSERVACION: 
//                        COMO LAS CUENTAS DEL CLIENTES SON EL EMPEÑO QUE REALIZA, ENTONCES EL IVA DEPENDE DEL DETALLE DEL EMPEÑO, 
//                        Y PUEDE HABER MAS DE UN IVA, ENTONCES PARA ESO HAGO UN SELECT POR EL DETALLE DEL EMPEÑO DE LA CUENTA Y ASI 
//                        VEO CUANTOS IVA TIENE Y REALIZO LOS CALCULOS POR LOS IVA QUE VAYA RECORRIENDO, YA QUE NO SE VAN A REPETIR, 
//                        Y CONCATENO EN UN STRING LOS IVA PARA LUEGO CARGARLO AL CAMPO QUE CORRESPONDE 
//                    */
//                    String PRECIO_FACT = CONCEPTO_PRECIO;
//                    System.out.println("_   _PRECIO_FACT:   "+PRECIO_FACT);
//
//                    // Y FILTRO POR EL DETALLE DE EMPEÑO DE ESAS CUENTAS 
//                    List<BeanFacturaCab> listaIva = new ArrayList<>();
//                    listaIva = metodosFactura.traerIvaEmpenho(CC_IDEMPENHO);
//                    Iterator<BeanFacturaCab> iterListIva = listaIva.iterator();
//                    BeanFacturaCab datosIva = new BeanFacturaCab();
//                    String totalIvas = "";
//
//                    while(iterListIva.hasNext()) {
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("________WHILE___IVA_______");
//                        datosIva = iterListIva.next();
//                        // RECUPERO EL IVA 
//                        String iva_detalle = datosIva.getOFD_IDIMPUESTO();
//                        System.out.println("_   _   _IVA:   "+iva_detalle);
//                        // PREGUNTO CUAL ES EL IVA RECUPERADO PARA REALIZAR LOS CALCULOS Y CARGAR LOS CAMPOS 
//                        if (iva_detalle.equalsIgnoreCase("5") || iva_detalle.equals("5%")) {
//                            double iva5 = Double.parseDouble(PRECIO_FACT)/21;
//                            System.out.println("_   _   _   _iva5:      "+iva5);
//                            double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
//                            System.out.println("_   _   _   _grav5:     "+grav5);
//                            CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
//                            CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
//                            CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva5);
//                            datos.setOFD_IVA5(formatear.format(iva5));
//                            datos.setOFD_GRAV5(formatear.format(grav5));
//
//                        } else if (iva_detalle.equalsIgnoreCase("10") || iva_detalle.equals("10%")) {
//                            double iva10 = Double.parseDouble(PRECIO_FACT)/11;
//                            System.out.println("_   _   _   _iva10:      "+iva10);
//                            double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
//                            System.out.println("_   _   _   _grav10:     "+grav10);
//                            CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
//                            CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
//                            CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva10);
////                                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + Double.parseDouble(CF_TXT_TOTAL_IVA10));
//                            datos.setOFD_IVA10(formatear.format(iva10));
//                            datos.setOFD_GRAV10(formatear.format(grav10));
//                        }
//
//                        // CONDICIONAL PARA PODER CONCATENAR EL IVA EN CASO DE QUE HAYA MAS DE UNO 
//                        if (totalIvas.isEmpty()) {
//                            totalIvas = iva_detalle;
//                        } else {
//                            totalIvas = totalIvas+" / "+iva_detalle;
//                        }
//                    } // end while 
//
//                    datos.setOFD_IDIMPUESTO(totalIvas);
//                    datos.setOFD_EXENTO("0");
//                    datos.setOFD_PRECIO(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
//                    datos.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
//                CF_LISTA_DETALLE.add(datos);
//                CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
//                // LE SUMO A LAS VARIABLES TOTALES 
//                CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(CONCEPTO_PRECIO));
//        } // end while cuentas cliente 
//
//        var_redireccionar = 1;
//        acceso = "crear_factura_det.htm";
//        sesionDatosUsuario.setAttribute("CF_VALI_MOSTRAR_BTN_DETALLE", "1"); // LE CARGO UNO PARA QUE EN LA CABECERA MUESTRE EL BOTON PARA REGRESAR AL DETALLE, YA QUE EL USUARIO DESDE EL DETALLE VA A REGRESAR A LA CABECERA PARA CARGAR EL NRO DE FACTURA PERO VA A TENER QUE VOLVER A CARGAR TODAS LAS CUENTAS QUE DESEA CANCELAR SI ES QUE NO LE MUESTRO ESTE BOTON PARA VOLVER AL DETALLE 
//        sesionDatosUsuario.setAttribute("RES_EMP_HBLT_BTN_ADD_IMPU", "1"); // LE PASO UN VALOR PARA QUE ASI AL REDIRECCIONAR AL DETALLE DE LA FACTURA LE MUESTRE LA OPCION DE PODER CARGAR IMPUESTOS EN CASO DE QUE QUIERA AÑADIRLOS Y NO TENGA QUE VOLVER A LA CABECERA Y LUEGO VOLVER A CARGAR TODAS LAS CUENTAS PARA CARGAR UN IMPUESTO 
//        sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
//        sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
//        sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
//        // CABECERA 
//        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
//        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
//        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
//        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
//        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
//        sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
//        sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
//        sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
//        // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
//        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
//        return acceso;
//    } // end method 
    
    
    
//    public void liquidar_empenho(HttpServletRequest request,
//            ModelEmpenho metodosEmpenho,
//            String INICIAL_PAG) {
//        System.out.println("---------------------__LIQUIDAR__--------------------------");
//        // OBTENER VALORES DE LA CAJA DE FILTRO 
//        String txt_idempenho = (String) request.getParameter("tIE");
//        String txt_idcliente = (String) request.getParameter("tIC");
//        System.out.println("------------__VAR_LIQUIDAR__-----------");
//        System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
//        System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
//        System.out.println("---------------------------------------");
//        
//        String TIPO_MENSAJE="", MENSAJE=null;
//        // VALIDACIONES 
//        if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
//            TIPO_MENSAJE = "2";
//            MENSAJE = "No se puede liquidar el empeño porque se encuentra Anulado.";
//            
////        } else if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente, request) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA FACTURADO 
////            HttpSession sesionDatosUser = request.getSession();
////            String NRO_FACTURA = (String) sesionDatosUser.getAttribute("VALI_PAGO_EMP_NRO_FACTURA");
////            TIPO_MENSAJE = "2";
////            MENSAJE = "No se puede liquidar el empeño porque cuenta con facturas("+NRO_FACTURA+").";
//            
//        } else {
//            TIPO_MENSAJE = metodosEmpenho.liquidar_empenho(txt_idempenho, txt_idcliente);
//            if (TIPO_MENSAJE.equals("1")) {
//                MENSAJE = "Se ha colocado como liquidado correctamente el empeño.";
//            } else if(TIPO_MENSAJE.equals("2")) {
//                MENSAJE = "Se genero un error, intentelo mas tarde.";
//            }
//        } // end validaciones 
//        
//        // OBS: ESTOS DATOS VOY A CARGAR DESDE BLOQUE DE ACCION QUE LO LLAMA AL METODO 
////        var_redireccionar = 1;
////        acceso = "emp_resumen.htm";
//        // PASAR DATOS AL JSP 
//        request.setAttribute("CPC_"+INICIAL_PAG+"_MENSAJE", MENSAJE);
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TIPO_MENSAJE", TIPO_MENSAJE);
//        request.setAttribute("CPC_"+INICIAL_PAG+"_LISTA_FILTRO", null); // LE CARGO CON NULL PARA QUE SE ACTIVE EL CARGA_GRILLA DE LA PAGINA 
//        request.setAttribute("CPC_"+INICIAL_PAG+"_CBX_MOSTRAR", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_NRO_EMP", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_PRES_INI", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_PRES_FIN", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_VENC_INI", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_VENC_FIN", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_CHECK_FILTRAR_CLIE_01", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_CLIE_ID", "");
//    }
    
    
//    public void expirar_empenho(HttpServletRequest request,
//            ModelEmpenho metodosEmpenho,
//            String INICIAL_PAG) {
//        System.out.println("---------------------__EXPIRAR__--------------------------");
//        // OBTENER VALORES DE LA CAJA DE FILTRO 
//        String txt_idempenho = (String) request.getParameter("tIE");
//        String txt_idcliente = (String) request.getParameter("tIC");
//        System.out.println("------------__VAR_EXPIRAR__-----------");
//        System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
//        System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
//        System.out.println("-------------------------------------");
//
//        String TIPO_MENSAJE="", MENSAJE=null;
//        // VALIDACIONES 
//        if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
//            TIPO_MENSAJE = "2";
//            MENSAJE = "No se puede expirar el empeño porque se encuentra Anulado.";
//        } else if (metodosEmpenho.controlEstLiqEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA FACTURADO 
//            TIPO_MENSAJE = "2";
//            MENSAJE = "No se puede expirar el empeño porque se encuentra liquidado.";
////        } else if (metodosEmpenho.controlarPagosEmp(txt_idempenho, txt_idcliente, request) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA FACTURADO 
////            HttpSession sesionDatosUser = request.getSession();
////            String NRO_FACTURA = (String) sesionDatosUser.getAttribute("VALI_PAGO_EMP_NRO_FACTURA");
////            TIPO_MENSAJE = "2";
////            MENSAJE = "No se puede expirar el empeño porque cuenta con facturas("+NRO_FACTURA+").";
//        } else {
//            TIPO_MENSAJE = metodosEmpenho.expirar_empenho(txt_idempenho, txt_idcliente);
//            if (TIPO_MENSAJE.equals("1")) {
//                MENSAJE = "Se ha marcado como expirado el empeño correctamente.";
//            } else if (TIPO_MENSAJE.equals("2")) {
//                MENSAJE = "Se genero un error, intentelo mas tarde.";
//            }
//        } // end validaciones 
//
//        // OBS: ESTOS DATOS VOY A CARGAR DESDE BLOQUE DE ACCION QUE LO LLAMA AL METODO 
////        var_redireccionar = 1;
////        acceso = "emp_resumen.htm";
//        // PASAR DATOS AL JSP 
//        request.setAttribute("CPC_"+INICIAL_PAG+"_MENSAJE", MENSAJE);
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TIPO_MENSAJE", TIPO_MENSAJE);
//        request.setAttribute("CPC_"+INICIAL_PAG+"_LISTA_FILTRO", null); // LE CARGO CON NULL PARA QUE SE ACTIVE EL CARGA_GRILLA DE LA PAGINA 
//        request.setAttribute("CPC_"+INICIAL_PAG+"_CBX_MOSTRAR", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_NRO_EMP", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_PRES_INI", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_PRES_FIN", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_VENC_INI", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_FEC_VENC_FIN", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_CHECK_FILTRAR_CLIE_01", "");
//        request.setAttribute("CPC_"+INICIAL_PAG+"_TXT_FILTRAR_CLIE_ID", "");
//    }
    
    
//    private String cancelar_empenho_ctrl(HttpServletRequest request,
//            ModelEmpenho metodosEmpenho,
//            HttpSession sesionDatosUsuario,
//            String acceso, 
//            String var_inicial_pag, // variable que tendria la inicial que utiliza cada pagina para las variables 
//            String acceso_pag_ini, // variable que tendria la pagina de donde estoy llamando al metodo 
//            int var_redireccionar) {
//        System.out.println("---------------------__CANCELAR__--------------------------");
//        System.out.println("____VAR_INICIAL_PAG:    "+var_inicial_pag);
//        System.out.println("_____ACCESO_PAG_INI:    "+acceso_pag_ini);
//        // OBTENER VALORES DE LA CAJA DE FILTRO 
//        String txt_idempenho = (String) request.getParameter("tIE");
//        String txt_idcliente = (String) request.getParameter("tIC");
//        System.out.println("------------__VAR_CANCELAR__-----------");
//        System.out.println("_  _ID_EMPENHO:   "+txt_idempenho);
//        System.out.println("_  _ID_CLIENTE:   "+txt_idcliente);
//        System.out.println("---------------------------------------");
//
//        int band_resumenEmp = 0; // BANDERA QUE UTILIZO PARA PODER REDIRECCIONAR A LA PAGINA DE RESUMEN EMPEÑO EN CASO DE QUE SALTE ALGUNA VALIDACION 
//        String TIPO_MENSAJE="", MENSAJE=null;
//        // VALIDACIONES 
//        if (metodosEmpenho.controlEstLiqEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA LIQUIDADO 
//            band_resumenEmp = 1;
//            acceso = acceso_pag_ini;
//            TIPO_MENSAJE = "2";
//            MENSAJE = "No se puede cancelar el empeño porque se encuentra liquidado.";
//        } else if (metodosEmpenho.controlEstAnuEmp(txt_idempenho, txt_idcliente) == true) { // VALIDACION PARA CONTROLAR SI ES QUE EL EMPENHO SE ENCUENTRA ANULADO 
//            band_resumenEmp = 1;
//            acceso = acceso_pag_ini;
//            TIPO_MENSAJE = "2";
//            MENSAJE = "No se puede cancelar el empeño porque se encuentra anulado.";
//        } else {
//            // REDIRECCIONAR A FACTURA PARA PODER CANCELAR LA CUENTA DEL EMPEÑO 
//            acceso = cancelar_empenho(request, sesionDatosUsuario, metodosIniSes, txt_idempenho, txt_idcliente, var_redireccionar, acceso_pag_ini);
//        } // end validaciones 
//
////        var_redireccionar = 1;
//        if (band_resumenEmp == 1) {
////            // CARGAR LISTA DE LA PAGINA DE RESUMEN EMPEÑO 
////            List<BeanFacturaCab> listaFiltro = new ArrayList<>();
////            listaFiltro = metodosEmpenho.re_cargar_grilla();
//
//            // PASAR DATOS AL JSP 
//            request.setAttribute("CPC_"+var_inicial_pag+"_MENSAJE", MENSAJE);
//            request.setAttribute("CPC_"+var_inicial_pag+"_TIPO_MENSAJE", TIPO_MENSAJE);
//            request.setAttribute("CPC_"+var_inicial_pag+"_LISTA_FILTRO", null); // LE CARGO CON NULL PARA QUE SE ACTIVE EL CARGA_GRILLA DE LA PAGINA 
////            request.setAttribute("CPC_"+var_inicial_pag+"_LISTA_FILTRO", listaFiltro);
//            request.setAttribute("CPC_"+var_inicial_pag+"_CBX_MOSTRAR", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_NRO_EMP", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_FEC_PRES_INI", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_FEC_PRES_FIN", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_FEC_VENC_INI", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_FEC_VENC_FIN", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_CHECK_FILTRAR_CLIE_01", "");
//            request.setAttribute("CPC_"+var_inicial_pag+"_TXT_FILTRAR_CLIE_ID", "");
//        }
//        return acceso;
//    }
    
    
    
    
    private boolean isNumber(String PARAM_ACCION) {
        boolean valor;
        try {
            Integer.parseInt(PARAM_ACCION);
            valor = true;
        } catch (NumberFormatException e) {
            valor = false;
        }
        return valor;
    }
    
    
    
    private String paginacion(String PARAM_INI_PAG, 
            HttpServletRequest request, 
            HttpServletResponse response, 
            HttpSession sesionDatosUsuario, 
            String acceso, 
            int var_redireccionar, 
            String accion, 
            int PARAM_PAG // VARIABLE QUE UTILIZARE PARA INDIVIDUALIZAR LAS PAGINAS / 1: ANULAR AGENDAMIENTO - 2: ANULAR FACTURA 
        ) {
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("-------------------------______METODO_PAGINACION______--------------------------------");
        ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
        ModelFactura metodosFactura = new ModelFactura();
        ModelPersona metodosPersona = new ModelPersona();
        ModelConfigAgend metodosConfigAgend = new ModelConfigAgend();
        try {
            if (isNumber(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_anu_agen(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_anu_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_config_agen(sesionDatosUsuario, request, metodosConfigAgend);
//                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
//                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    }
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
                
            } else if (accion.equalsIgnoreCase(">>")) {
                System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accion+" :__--------------------------");
                String PAG_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute(""+PARAM_INI_PAG+"_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_CANT_NRO_CLIC == null) {
                    PAG_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_CANT_NRO_CLIC);
                String PAG_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_LISTA_ACTUAL);

                PAG_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_CANT_NRO_CLIC)+1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_LISTA_ACTUAL);

                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL", PAG_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_CANT_NRO_CLIC)+1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_anu_agen(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_anu_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_config_agen(sesionDatosUsuario, request, metodosConfigAgend);
//                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
//                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    }
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
                
            } else if (accion.equalsIgnoreCase("<<")) {
                System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accion+" :__--------------------------");
                String PAG_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute(""+PARAM_INI_PAG+"_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_CANT_NRO_CLIC == null) {
                    PAG_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_CANT_NRO_CLIC);
                String PAG_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_LISTA_ACTUAL);

                PAG_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_CANT_NRO_CLIC)-1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_LISTA_ACTUAL);

                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL", PAG_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_CANT_NRO_CLIC)-1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPC_VAR_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_anu_agen(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_anu_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_config_agen(sesionDatosUsuario, request, metodosConfigAgend);
//                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
//                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    }
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
            }
        } catch (Exception e) {
            System.out.println("-----------------ERROR-REPORTES-------___paginacion("+PARAM_INI_PAG+")___-------------");
            var_redireccionar = 0;
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerReportes.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------------------------------------------");
        }
        return acceso;
    } // END METHOD 
    
    
    
    
    
    private void filtrar_pag_anu_agen(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelAgendamiento metodosAgendamiento) {
        String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("__ID_PERSONA:   :"+idPersona);
        System.out.println("---------------------__FILTRAR__--------------------------");
        // OBTENER VALORES DE LA CAJA DE FILTRO 
        String cbxMostrar = (String) request.getParameter("tAcM");
        String txtBuscarCodAgen = (String) request.getParameter("tAtFNF"); // tFNF : txt filtrar nro factura 
        if (txtBuscarCodAgen == null || txtBuscarCodAgen.isEmpty()) {
            txtBuscarCodAgen = "";
        }
        String txtFechaIni = (String) request.getParameter("tAtFFI"); // tFFI : txt filtrar fecha inicio 
        if (txtFechaIni == null || txtFechaIni.isEmpty()) {
            txtFechaIni = "";
        }
        String txtFechaFin = (String) request.getParameter("tAtFFF"); // tFFF : txt filtrar fecha fin 
        if (txtFechaFin == null || txtFechaFin.isEmpty()) {
            txtFechaFin = "";
        }
//        String checkClienteFiltro = (String) request.getParameter("tAcheck_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtClienteId = (String) request.getParameter("tAcbxAddNewCli"); // cliente id 
        if (txtClienteId == null || txtClienteId.isEmpty()) {
            txtClienteId = "";
        }
        String checkClinicaFiltro = (String) request.getParameter("tAcheck_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtClinicaId = (String) request.getParameter("tAcbxAddNewClinica"); // CLINICA id 
        if (txtClinicaId == null || txtClinicaId.isEmpty()) { txtClinicaId = ""; }
//                    String checkPacienteFiltro = (String) request.getParameter("check_pac"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
//                    String txtPacienteId = (String) request.getParameter("cbxAddNewPac"); // paciente id 
//                    if (txtPacienteId == null || txtPacienteId.isEmpty()) {
//                        txtPacienteId = "";
//                    }
        System.out.println("-------------__VAR_FILTRAR__--------------");
        System.out.println("_  _CBX_MOSTRAR    : "+cbxMostrar);
        System.out.println("_  _BUSCAR_COD_AGEN: "+txtBuscarCodAgen);
        System.out.println("_  _FECHA_INICIO :   "+txtFechaIni);
        System.out.println("_  _FECHA_FIN    :   "+txtFechaFin);
//        System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
        System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
        System.out.println("_  _CHECK_CLINICA:   "+checkClinicaFiltro);
        System.out.println("_  _CLINICA_ID   :   "+txtClinicaId);
//                    System.out.println("_  _CHECK_PACIENTE:  "+checkPacienteFiltro);
//                    System.out.println("_  _PACIENTE_ID   :  "+txtPacienteId);
        System.out.println("------------------------------------------");

        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//        if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//            checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//        } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//            txtClienteId = "";
//        }
        String checkClienteFiltro = "";
        if (txtClienteId.isEmpty() || txtClienteId.equalsIgnoreCase("")) {
            checkClienteFiltro = "ON";
        } else {
            checkClienteFiltro = "OFF";
        }
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLINICA, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLINICA PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLINICA 
        if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA CLINICA Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLINICA 
            checkClinicaFiltro = "OFF";
        } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            txtClinicaId = "";
        }
//                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR PACIENTE 
//                    if (checkPacienteFiltro == null || checkPacienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPACIENTE 
//                        checkPacienteFiltro = "OFF";
//                    } else if (checkPacienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                        txtPacienteId = "";
//                    }

        // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
        String IDPACIENTE = "";
        // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
        if (metodosPerfil.isPerfilPaciente(IDPERFIL_USER)) {
            IDPACIENTE = idPersona; // SI FUERA EL PERFIL PACIENTE, ENTONCES NO LE VOY A MOSTRAR EL FILTRO DE PACIENTE Y DIRECTAMENTE UTILIZARE EL IDPERSONA DE LOGEO 
        } else if (metodosPerfil.isPerfilAdmin(IDPERFIL_USER) || metodosPerfil.isPerfilSecre(IDPERFIL_USER)) {
            IDPACIENTE = txtClienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
//                        IDPACIENTE = txtPacienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
        }
        System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);

        // CARGAR LISTA 
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        String varEstado = ""; // LO DEJO VACIO YA QUE QUIERO QUE EL FILTRO ME MUESTRE TODOS LOS ESTADO, PERO SI QUISIESE QUE ME MUESTRE SOLO LOS COBRADOS, ENTONCES AHI SI CARGARIA EL ESTADO 
        listaFiltro = metodosAgendamiento.filtrar_anu_agen(sesionDatosUsuario, cbxMostrar, txtBuscarCodAgen, "", "", txtFechaIni, txtFechaFin, IDPACIENTE, txtClinicaId);

//        var_redireccionar = 1;
//        acceso = "anular_agend.htm";
        // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_CRF_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_CRF_MENSAJE", mensaje);
        request.setAttribute("CPC_AA_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CPC_AA_CBX_MOSTRAR", cbxMostrar);
        request.setAttribute("CPC_AA_TXT_FILTRAR_NRO_FACT", txtBuscarCodAgen);
        request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_INI", txtFechaIni);
        request.setAttribute("CPC_AA_TXT_FILTRAR_FEC_FIN", txtFechaFin);
        request.setAttribute("CPC_AA_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
        request.setAttribute("CPC_AA_TXT_FILTRAR_CLIE_ID", txtClienteId);
        request.setAttribute("CPC_AA_CHECK_FILTRAR_CLINICA_01", checkClinicaFiltro);
        request.setAttribute("CPC_AA_TXT_FILTRAR_CLINICA_ID", txtClinicaId);
        sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    private void filtrar_pag_anu_fact(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFactura metodosFactura) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        // OBTENER VALORES DE LA CAJA DE FILTRO 
        String cbxMostrar = (String) request.getParameter("tAcM");
        String txtBuscarNroFact = (String) request.getParameter("tAtFNF"); // tFNF : txt filtrar nro factura 
        if (txtBuscarNroFact == null || txtBuscarNroFact.isEmpty()) {
            txtBuscarNroFact = "";
        }
        String txtFechaIni = (String) request.getParameter("tAtFFI"); // tFFI : txt filtrar fecha inicio 
        if (txtFechaIni == null || txtFechaIni.isEmpty()) {
            txtFechaIni = "";
        }
        String txtFechaFin = (String) request.getParameter("tAtFFF"); // tFFF : txt filtrar fecha fin 
        if (txtFechaFin == null || txtFechaFin.isEmpty()) {
            txtFechaFin = "";
        }
//        String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtClienteId = (String) request.getParameter("tAcbxAddNewCli"); // cliente id 
        if (txtClienteId == null || txtClienteId.isEmpty()) {
            txtClienteId = "";
        }
        System.out.println("------------__VAR_FILTRAR__-----------");
        System.out.println("_  _CBX_MOSTRAR    :   "+cbxMostrar);
        System.out.println("_  _BUSCAR_NRO_FACT:   "+txtBuscarNroFact);
        System.out.println("_  _FECHA_INICIO :   "+txtFechaIni);
        System.out.println("_  _FECHA_FIN    :   "+txtFechaFin);
//        System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
        System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
        System.out.println("--------------------------------------");

//        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//        if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//            checkClienteFiltro = "OFF";
////                        // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
////                        List<BeanPersona> listaDatosCliente;
////                        listaDatosCliente = metodosPersona.traerDatosCliente(CBX_IDCLIENTE);
////                        Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
////                        BeanPersona datosClienteNew = null;
////                        String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
////                        while(iterCliente.hasNext()) {
////                            datosClienteNew = iterCliente.next();
////
////                            CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
////                            CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
////                            CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
////                            CLIENTE_RUC = datosClienteNew.getRP_RUC();
////                        }
////
////                        // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
////                        System.out.println("-------------------------------------------");
////                        System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
////                        System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
////                        System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
////                        System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
////                        System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
////                        System.out.println("-------------------------------------------");
//        } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//            txtClienteId = "";
//        }
        String checkClienteFiltro = "";
        if (txtClienteId.isEmpty() || txtClienteId.equalsIgnoreCase("")) {
            checkClienteFiltro = "ON";
        } else {
            checkClienteFiltro = "OFF";
        }

        // CARGAR LISTA 
        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
        String varEstado = "C";// VARIABLE QUE UTILIZO PARA FILTRAR EL SELECT POR TODAS LAS FACTURAS CON ESTADO ACTIVO o COBRADO 
        String txt_IDCLINICA = ""; // VARIABLE QUE LA DEJO VACIO PARA QUE EN EL METODO SE CARGUE CON EL IDCLINICA DE LOGEO, PERO EN CASO DE QUE HAYA UN FILTRO EN LA PAGINA, ENTONCES AHI SE CARGARIA ESTA VARIABLE Y EL CODIGO PARA HACERLO, Y EN EL METODO FILTRARIA POR ESA IDCLINICA Y NO POR LA DE LOGEO 
        String IDPACIENTE = ""; // DEJO VACIO PORQUE EN ESTE CASO NO UTILIZO 
        listaFiltro = metodosFactura.filtrar_paginacion_anular_fact(sesionDatosUsuario, cbxMostrar, txtBuscarNroFact, txtFechaIni, txtFechaFin, txtClienteId, varEstado, txt_IDCLINICA, "");

//        var_redireccionar = 1;
//        acceso = "anular_fact.htm";
        // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AF_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_AF_MENSAJE", mensaje);
        request.setAttribute("CPC_AF_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CPC_AF_CBX_MOSTRAR", cbxMostrar);
        request.setAttribute("CPC_AF_TXT_FILTRAR_NRO_FACT", txtBuscarNroFact);
        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_INI", txtFechaIni);
        request.setAttribute("CPC_AF_TXT_FILTRAR_FEC_FIN", txtFechaFin);
        request.setAttribute("CPC_AF_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
        request.setAttribute("CPC_AF_TXT_FILTRAR_CLIE_ID", txtClienteId);
        sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    private void filtrar_pag_config_agen(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelConfigAgend metodosConfigAgend) {
        System.out.println("---------------------__FILTRAR___CONFIGURACION_AGENDAMIENTO__--------------------------");
        // OBTENER VALORES DE LA CAJA DE FILTRO 
        String cbxMostrar = (String) request.getParameter("cM");
        String txtBuscar = (String) request.getParameter("txB");
        if (txtBuscar == null || txtBuscar.isEmpty()) {
            txtBuscar = "";
        }
        // COMBO ESTADO 
        String checkEstadoFiltro = (String) request.getParameter("check_estado"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_ESTADO = (String) request.getParameter("cE"); // ESTADO  
        if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = ""; }
        System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
        System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
        // CONTROL DEL CHECK DE ESTADO 
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
        if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
            checkEstadoFiltro = "OFF";
        } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_ESTADO = "";
        }
        
        System.out.println("-------------__VAR_FILTRAR__--------------");
        System.out.println("_  _CBX_MOSTRAR:  :"+cbxMostrar);
        System.out.println("_  _BUSCAR:       :"+txtBuscar);
        System.out.println("------------------------------------------");
        
        // CARGAR LISTA 
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        listaFiltro = metodosConfigAgend.filtrar_paginacion(sesionDatosUsuario, cbxMostrar, txtBuscar, PARAM_TXT_ESTADO);
        
//        var_redireccionar = 1;
//        acceso = "config_agend.htm";
        // PASAR DATOS AL JSP 
//                    request.setAttribute("CPC_AC_TIPO_MENSAJE", tipo_mensaje);
//                    request.setAttribute("CPC_AC_MENSAJE", mensaje);
        request.setAttribute("CPC_AC_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CPC_AC_CBX_MOSTRAR", cbxMostrar);
        request.setAttribute("CPC_AC_TXT_BUSCAR", txtBuscar);
        request.setAttribute("CPC_AC_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
        request.setAttribute("CPC_AC_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
        sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    
} // END CLASS 