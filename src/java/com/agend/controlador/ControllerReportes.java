/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanCuentaCliente;
import com.agend.javaBean.BeanFacturaCab;
import com.agend.javaBean.BeanFichaAtePaciente;
import com.agend.javaBean.BeanPersona;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelAgendamiento;
import com.agend.modelo.ModelCuentaCliente;
import com.agend.modelo.ModelFactura;
import com.agend.modelo.ModelFichaAtencionPacNutri;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelRef_Clinica;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerReportes", urlPatterns="/CRSrv")
public class ControllerReportes extends HttpServlet {
    
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/reportes") 
    public ModelAndView reportes(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__REPORTES--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _1_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _1_CR__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuReportes(IDPERFIL_USER)==true) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagReportes", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagReportes", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_paciente") 
    public ModelAndView rpt_paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__2__---------CONTROLLER__REPORTES-------___REPORTE_PACIENTE___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _2_CR__ID_USUARIO_PERSONA:    :"+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _2_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReportePaciente(IDPERFIL_USER)) {
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Paciente_Cliente", request);
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Paciente", request);
        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Cliente", request);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_cta_paciente") 
    public ModelAndView rpt_cuenta_paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__4__---------CONTROLLER__REPORTES-------___REPORTE_CUENTA_PACIENTE___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _4_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _4_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_CuentaCliente_Pac", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_CuentaCliente", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_CuentaCliente", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CR_RCC_CHECK_FILTRAR_CLI", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_factura") 
    public ModelAndView rpt_factura(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__5__---------CONTROLLER__REPORTES-------___REPORTE_FACTURAS___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _5_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _5_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Factura_Pac", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Factura", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Factura", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CR_RF_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_mis_agen") 
    public ModelAndView rpt_mis_agendamientos(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__6__---------CONTROLLER__REPORTES-------___REPORTE_MIS_AGENDAMIENTOS___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _6_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _6_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteMisAgendamientos(IDPERFIL_USER)) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CR_RMA_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RMA_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESPECIALIDAD Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RMA_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RMA_CHECK_FILTRAR_PACIENTE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE PACIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RMA_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_est") 
    public ModelAndView rpt_estadistica(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__7__---------CONTROLLER__REPORTES-------___REPORTE_ESTADISTICA___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _7_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _7_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteEstadistica(IDPERFIL_USER)) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_Estadisticas", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CR_RE_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESPECIALIDAD Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_PACIENTE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE PACIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        // [OBS] AL INGREAR A LA PAGINA PRINCIPAL VOY A LIMPIAR LAS VARIABLES QUE TENGO EN SESION QUE LAS UTILIZO EN LA PAGINA DE ESTADISTICA-DATOS Y NO EN LA PRINCIPAL POR ESO LO HAGO ACA EN EL MAV SINO LO HARIA EN EL CONTROLADOR 
        sesionDatosUsuario.removeAttribute("CR_RFAN_LISTA_HISTORICO_PAC");
        sesionDatosUsuario.removeAttribute("CR_RFAN_NAME_EXCEL");
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_ver_est") 
    public ModelAndView ver_estadisticas(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__7.1__---------CONTROLLER__REPORTES-------___REPORTE_VER_ESTADISTICA___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _7.1_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _7.1_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteEstadistica(IDPERFIL_USER)) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_EstadisticaDatos", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        request.setAttribute("CR_RE_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESPECIALIDAD Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_PACIENTE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE PACIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CR_RE_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
//    @RequestMapping("/rpt_his_fic") 
//    public ModelAndView rpt_historico_ficha_atencion(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
//        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
//        
//        System.out.println("-----__8__---------CONTROLLER__REPORTES-------___REPORTE_HISTORICO_FICHA_ATENCION___-------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _8_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _8_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
//        
//        String paginaMav = "menu_principal";
//        if (modeloPerfil.isReporteHistoricoFichaAtencion(IDPERFIL_USER)==true) { // 4 : PACIENTE 
////        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
//////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_HistFichaAtencion", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
//        System.out.println("pagina_mav:     "+paginaMav);
//        
//        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_PACIENTE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE PACIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(paginaMav);
//        return mav;
//    }
    
    
    @RequestMapping("/ver_his_fic") 
    public ModelAndView rpt_ver_historico_ficha_atencion(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__8.1__---------CONTROLLER__REPORTES-------___REPORTE_HISTORICO_FICHA_ATENCION__VER___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _8.1_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _8.1_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteHistoricoFichaAtencion(IDPERFIL_USER)==true) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_HistFichaAtencionView", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_MisAgendamientos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_CLINICA", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_PACIENTE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE PACIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CR_RFAN_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/ver_his_fic_det") 
    public ModelAndView rpt_ver_historico_ficha_atencion_det(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        System.out.println("-----__8.2__---------CONTROLLER__REPORTES-------___REPORTE_HISTORICO_FICHA_ATENCION_DET_(ARCHIVOS-ADJUNTOS)__VER___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _8.2_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _8.2_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteHistoricoFichaAtencion(IDPERFIL_USER)==true) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_HistFichaAtencionViewDet", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/rpt_ficha") 
    public ModelAndView rpt_ficha_ate_nutri(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        System.out.println("-----__9__---------CONTROLLER__REPORTES-------___REPORTE_FICHA-DE-ATENCION-_(NUTRI)_-___-------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _9_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _9_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        String paginaMav = "menu_principal";
        if (modeloPerfil.isReporteFichaAteNutri(IDPERFIL_USER)==true) { // 4 : PACIENTE 
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagRpt_FichaAtencion", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        request.setAttribute("CR_RFAPN_CHECK_FILTRAR_CLIE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    // PAGINA QUE USO COMO EXPORTADOR PARA EXCEL CAMBIANDO LA RESPUESTA DE PAGINA JSP A UN ARCHIVO EXCEL POR ESO ADENTRO DEL MAV NO TIENE PARAMETROS, NO LO UTILIZO PORQUE ESTOY UTILIZANDO UN EVENTO EN EL CONTROLADOR PARA CREAR EL ARCHIVO EXCEL.-
    @RequestMapping("/rptexcel") 
    public ModelAndView rptexcel(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
//        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
//        System.out.println("-----__9__---------CONTROLLER__REPORTES-------___REPORTE_EXCEL___-------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _9_CR__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _9_CR__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        String paginaMav = "pagRpt_EstadisticaDatos_excel";
        System.out.println("pagina_mav:     "+paginaMav);
//        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ModelPersona metodosPersona = new ModelPersona();
        ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
        ModelCuentaCliente metodosCuentaCliente = new ModelCuentaCliente();
        ModelFactura metodosFactura = new ModelFactura();
        ModelFichaAtencionPacNutri metodosFichaAtencionPacNutri = new ModelFichaAtencionPacNutri();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USUARIO 
        
        int var_redireccionar = 0;
        String acceso = "reportes.htm";
        // OBSERVACION: UTILIZO ACCIONES DIFERENTE PARA IDENTIFICAR CADA BOTON DE CADA PAGINA Y ASI REDIRECCIONARLA CORRECTAMENTE, PODRIA UTILIZAR UNA BANDERA PARA PODER IDENTIFICAR LA PAGINA O PARA DEVOLVER LA PAGINA, PERO CREO QUE ESTA IDEA ES MEJOR, CREANDO ACCIONES DIFERENTE PARA CADA PAGINA Y ASI LAS QUE NO SE UTILICEN SE ENCONTRARAN NULL 
        String accionRptPac = request.getParameter("accionRptPac"); // ACCIONES DEL REPORTE PACIENTE 
        String accionRptCtaPac = request.getParameter("accionRptCtaPac"); // ACCIONES DEL REPORTE CUENTA PACIENTE 
        String accionRptFact = request.getParameter("accionRptFact"); // ACCIONES DEL REPORTE FACTURA 
        String accionRptAjuStock = request.getParameter("accionRptAjuStock"); // ACCIONES DEL REPORTE AJUSTE STOCK 
        String accionRptStock = request.getParameter("accionRptStock"); // ACCIONES DEL REPORTE STOCK 
        String accionRptMisAgen = request.getParameter("accionRptMisAgend"); // ACCIONES DEL REPORTE DE MIS AGENDAMIENTOS 
        String accionRptEst = request.getParameter("accionRptEst"); // ACCIONES DEL REPORTE DE ESTADISTICAS 
        String accionRptHisFAN = request.getParameter("accionRptHFAN"); // ACCIONES DEL REPORTE / HISTORICO FICHA ATENCION NUTRI
        String accionRptFichaNutri = request.getParameter("accionRptFichaAPN"); // ACCIONES DEL REPORTE / FICHA DE ATENCION DEL PACIENTE (NUTRI) 
        String idPersona;
        
        try {
            System.out.println("[*].");
            System.out.println("[*].");
            System.out.println("[*].");
            System.out.println("[*].");
            System.out.println("[*].      ___________________CONTROLADOR DE EVENTOS DE LOS REPORTES____________________");
            System.out.println("[*].");
            System.out.println("[*].");
            System.out.println("[*]__ACCION_RPT_PAC:   "+accionRptPac);
            System.out.println("[*]__ACCION_RPT_CTA_PAC:   "+accionRptCtaPac);
            System.out.println("[*]__ACCION_RPT_AJU_STOCK: "+accionRptAjuStock);
            System.out.println("[*]__ACCION_RPT_MIS_AGEND: "+accionRptMisAgen);
            System.out.println("[*]__ACCION_RPT_ESTADISTICA:  "+accionRptEst);
            System.out.println("[*]__ACCION_RPT_HIST_FIC_ATE: "+accionRptHisFAN);
            System.out.println("[*]__ACCION_RPT_FICHAS_PAC_N: "+accionRptFichaNutri);
            // COMO EN CADA ACCION UTILIZO EL IDPERSONA, ENTONCES LA RECUPERO ACA NOMAS PARA NO IR RECUPERANDO EN CADA IF 
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("[*]___doPost___ID_PERSONA:  :"+idPersona);
            
            
            // CONTROLO PRIMERAMENTE CUAL ACCION SE ACTIVO, DE QUE PAGINA, Y LUEGO VERIA EL BOTON QUE SE HAYA ACTIVADO DE ESA PAGINA 
            // --------     REPORTE DE FICHAS DE ATENCION DEL PACIENTE (NUTRI)     -------------
            if (accionRptFichaNutri != null) {
                System.out.println("[*].");System.out.println("[*].");System.out.println("[*].");
                System.out.println("[*]---------______ACCION__REPORTE__FICHAS-DE-ATENCION-DEL-PACIENTE-NUTRI_______---------");
                System.out.println("[*].");System.out.println("[*].");
                acceso = "rpt_ficha.htm";
                if (accionRptFichaNutri.equalsIgnoreCase("")) {
//                    var_redireccionar = 1;
//                    acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
//                    // variable que uso en el controlador de la ficha para saber a que pagina redireccionar cuando se presione el boton para volver atras 
//                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "REPORTE_PACIENTE"); // OBS.: NO LE COLOCO DENTRO DEL METODO PORQUE FICHA PUEDE LLAMAR A ESTE METODO Y EN ESE CASO TENDRIA QUE VOLVER ATRAS EN FICHA O EN EXPEDIENTE Y NO EN EL REPORTE DE PACIENTE 
                    
                } else if (accionRptFichaNutri.equalsIgnoreCase("Filtrar")) {
                    System.out.println("[*]---------------------__FILTRAR__--------------------------");
//                    String idFicha = (String) request.getParameter("tRFPNIF");
//                    System.out.println("[+] id_ficha: "+idFicha);
//                    String idPaciente = (String) request.getParameter("tRFPNIP");
//                    System.out.println("[+] id_paciente: "+idPaciente);
                    
                    // recupero el valor de las variables.-
                    String cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    if (cbxMostrar == null) { cbxMostrar = ""; }
                    String txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    if (txtBuscar == null) { txtBuscar = ""; }
                    String txtFechaIni = (String) request.getParameter("tFFI");
                    if (txtFechaIni == null || txtFechaIni.isEmpty()) { txtFechaIni = ""; }
                    String txtFechaFin = (String) request.getParameter("tFFF");
                    if (txtFechaFin == null || txtFechaFin.isEmpty()) { txtFechaFin = ""; }
                    String txtCbxEstado = (String) request.getParameter("cE"); // ESTADO  
                    if (txtCbxEstado == null || txtCbxEstado.isEmpty()) { txtCbxEstado = ""; }
                    String checkPacienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtPacienteId = (String) request.getParameter("cbxAddNewCli"); // cliente id 
                    if (txtPacienteId == null || txtPacienteId.isEmpty()) {
                        txtPacienteId = "";
                    }
                    // IMPRESIONES.-
                    System.out.println("[*]---------------__VAR_FILTRAR__----------------");
                    System.out.println("[*]_  _CBX_MOSTRAR:   "+cbxMostrar);
                    System.out.println("[*]_  _BUSCAR_TXT :   "+txtBuscar);
                    System.out.println("[*]_  _FECHA_INI  :   "+txtFechaIni);
                    System.out.println("[*]_  _FECHA_FIN  :   "+txtFechaFin);
                    System.out.println("[*]_  _ESTADO     :   "+txtCbxEstado);
                    System.out.println("[*]_  _CHECK_PAC  :   "+checkPacienteFiltro);
                    System.out.println("[*]_  _ID_PACIENTE:   "+txtPacienteId);
                    System.out.println("[*]----------------------------------------------");
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                    if (checkPacienteFiltro == null || checkPacienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                        checkPacienteFiltro = "OFF";
                    } else if (checkPacienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtPacienteId = "";
                    }
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosFichaAtencionPacNutri.filtrarFichasAtePac(sesionDatosUsuario, txtPacienteId, cbxMostrar, txtBuscar, txtFechaIni, txtFechaFin, txtCbxEstado);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_ficha.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RFAPN_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CR_RFAPN_TXT_BUSCAR", txtBuscar);
                    request.setAttribute("CR_RFAPN_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CR_RFAPN_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CR_RFAPN_TXT_FILTRAR_ESTADO", txtCbxEstado);
                    request.setAttribute("CR_RFAPN_LISTA_DE_FICHAS", listaFiltro);
                    request.setAttribute("CR_RFAPN_CHECK_FILTRAR_CLIE_01", checkPacienteFiltro);
                    request.setAttribute("CR_RFAPN_TXT_FILTRAR_CLIE_ID", txtPacienteId);
//                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
//                    
//                } else if (isNumber(accionRptHisFAN) || accionRptHisFAN.equalsIgnoreCase("<<") || accionRptHisFAN.equalsIgnoreCase(">>")) {
//                    acceso = "rpt_his_fic.htm";
//                    var_redireccionar = 1;
//                    acceso = paginacion("PAG_RPT_HIST_FAN", request, response, sesionDatosUsuario, acceso, accionRptHisFAN, 5, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptFichaNutri.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
//                    acceso = "ver_his_fic.htm";
//                    sesionDatosUsuario.setAttribute("PAG_RPT_HIST_FAN_CANT_BTN_DERE_CLIC", "1");
//                    sesionDatosUsuario.setAttribute("PAG_RPT_HIST_FAN_LISTA_ACTUAL", "1");
//                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accionRptFichaNutri.equalsIgnoreCase("Volver Atras")) {
                    System.out.println("[*]------------------------__VOLVER_ATRAS__------------------------------");
                    var_redireccionar = 0;
                    String varVolverAtras = (String) sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS");
                    System.out.println("[*]__VAR-VOLVER-ATRAS:   :"+varVolverAtras);
                    if (varVolverAtras.equals("REPORTE_PACIENTE")) {
                        acceso = "rpt_paciente.htm";
                    } else if (varVolverAtras.equals("FICHA_ATENCION_PAC_NUTRI")) {
                        acceso = "add_atencion.htm";
                        String IDFICHA = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                        System.out.println("_   _CR___ID_FICHA_ATENCION:  :"+IDFICHA);
                        if (IDFICHA.isEmpty()) {
                            String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                            if (sesionDatosUsuario.getAttribute("CFAP_LAST_FICHA_VALUES") == null) { // SI LA LISTA ESTA VACIA ENTONCES LE VUELVO A CARGAR.- 
                                List<BeanFichaAtePaciente> datosUltFichaPac = metodosFichaAtencionPacNutri.getUltimaFicha(IDPACIENTE);
                                sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
                            }
                        }
                        List<String> datosPac = (List) sesionDatosUsuario.getAttribute("CFAP_PAC_DATOS");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        if (datosPac == null) {
                            System.out.println("___IS_NULL____");
                        } else {
                            System.out.println("___IS_NOT_NULL___");
                        }
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                            List<String> datosFichaCab01 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_01");
                            List<String> datosFichaCab02 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_02");
                            List<String> datosFichaCab03 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_03");
                            List<String> datosFichaCab04 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_04");
                            request.setAttribute("CFAP_PAC_DATOS", datosPac);
                            request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
                            request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
                            request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
                            request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
                            
                    } else if (varVolverAtras.equals("VER_PACIENTE")) {
                        acceso = "ver_paciente.htm"; // [OBS] AL FINAL DEL CONTROLADOR LE CARGO LOS DATOS NECESARIOS PARA VER EL EXPEDIENTE DEL PACIENTE COMO LO HAGO EN EL CONTROLADOR DE FICHA DE ATENCION PACIENTE (NUTRI).-
                    } else if (varVolverAtras.equals("HISTORICO_FICHA")) {
                        var_redireccionar = 1;
                        acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
                        String volverAtrasAuxValue = (String) sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS_AUX");
                        System.out.println("_____VOLVER_ATRAS_AUX:   :"+volverAtrasAuxValue);
                        sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS",volverAtrasAuxValue); // OBS.: LE VUELVO A CARGAR LA VARIABLE DE VOLVER ATRAS Y ES PARA QUE SI UTILIZA EL BOTON DE "VOLVER ATRAS" DEL HISTORICO DE FICHAS, ENTONCES LE DEVUELVA A LA PAGINA QUE CORRESPONDA.-
                    } else {
                        acceso = "rpt_paciente.htm";
                    }
                    
//                } else if (accionRptFichaNutri.equalsIgnoreCase("VerArchivosAdj")) {
//                    System.out.println("------------------------__VER_ARCHIVOS_ADJUNTOS__------------------------------");
//                    acceso = "ver_his_fic_det.htm";
//                    var_redireccionar = 1;
//                    String ID_FICHA = (String) request.getParameter("tI");
//                    System.out.println("-   _VAR____ID_FICHA:  :"+ID_FICHA);
//                    String txtIdPac = (String) request.getParameter("tIP");
//                    String NAME_PACIENTE = (String) request.getParameter("tCRPNC");
//                    System.out.println("_  ___ID_PACIENTE:     :"+txtIdPac);
//                    System.out.println("_  ___PACIENTE_NAME:   :"+NAME_PACIENTE);
//                    
//                    // cargo la lista 
//                    List<BeanFichaAtePaciente> LISTA_DET_FICHAS = new ArrayList<>();
//                    LISTA_DET_FICHAS = metodosFichaAtencionPacNutri.getDatosFichasDet(ID_FICHA);
//                    
//                    request.setAttribute("CR_RFAN_TXT_IDFICHA", ID_FICHA);
//                    request.setAttribute("CR_RFAN_TXT_IDPACIENTE", txtIdPac);
//                    request.setAttribute("CR_RFAN_TXT_NAME_PACIENTE", NAME_PACIENTE);
//                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_DET_FICHA_AA", LISTA_DET_FICHAS);
//                    String volverAtrasValue = String.valueOf(sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS"));
//                    System.out.println("_   _VAA____VOLVER_ATRAS:   :"+volverAtrasValue);
//                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS_AUX", volverAtrasValue); // CARGO EN UNA VARIABLE AUXILIAR EL DATO QUE TENIA SOBRE LA VARIABLE DE "VOLVER ATRAS" PARA DESPUES AL VOLVER ATRAS AL HISTORICO DE FICHA Y VUELVA ATRAS ENTONCES USE ESTA VARIABLE AUXILIAR PARA SABER A QUE PAGINA TENGO QUE VOLVER ATRAS 
//                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "HISTORICO_FICHA"); // VARIABLE QUE UTILIZO PARA EL EVENTO DE VOLVER ATRAS 
                }
                
                
            } else 
            // --------     REPORTE HISTORICO DE FICHAS DE ATENCION (NUTRI)     -------------
            if (accionRptHisFAN != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------______ACCION__REPORTE__HISTORICO-FICHA-ATENCION-NUTRI_______---------");
                System.out.println(".");System.out.println(".");
                acceso = "ver_his_fic.htm";
                if (accionRptHisFAN.equalsIgnoreCase("Ver Historico")) {
                    var_redireccionar = 1;
                    acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
                    // variable que uso en el controlador de la ficha para saber a que pagina redireccionar cuando se presione el boton para volver atras 
                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "REPORTE_PACIENTE"); // OBS.: NO LE COLOCO DENTRO DEL METODO PORQUE FICHA PUEDE LLAMAR A ESTE METODO Y EN ESE CASO TENDRIA QUE VOLVER ATRAS EN FICHA O EN EXPEDIENTE Y NO EN EL REPORTE DE PACIENTE 
                    
                } else if (accionRptHisFAN.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String idPaciente = (String) request.getParameter("tIP");
                    String NamePaciente = (String) request.getParameter("tCRPNC");
                    String cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    if (cbxMostrar == null) { cbxMostrar = ""; }
                    String txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    if (txtBuscar == null) { txtBuscar = ""; }
                    String txtFechaIni = (String) request.getParameter("tRHFAFI");
                    if (txtFechaIni == null || txtFechaIni.isEmpty()) { txtFechaIni = ""; }
                    String txtFechaFin = (String) request.getParameter("tRHFAFF");
                    if (txtFechaFin == null || txtFechaFin.isEmpty()) { txtFechaFin = ""; }
                    String txtCbxEstado = (String) request.getParameter("cE"); // ESTADO  
                    if (txtCbxEstado == null || txtCbxEstado.isEmpty()) { txtCbxEstado = ""; }
                    // IMPRESIONES.-
                    System.out.println("---------------__VAR_FILTRAR__----------------");
                    System.out.println("_  _CBX_MOSTRAR:   "+cbxMostrar);
                    System.out.println("_  _BUSCAR_TXT :   "+txtBuscar);
                    System.out.println("_  _FECHA_INI  :   "+txtFechaIni);
                    System.out.println("_  _FECHA_FIN  :   "+txtFechaFin);
                    System.out.println("_  _ESTADO     :   "+txtCbxEstado);
                    System.out.println("----------------------------------------------");
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosFichaAtencionPacNutri.filtrarFichasAtePac(sesionDatosUsuario, idPaciente, cbxMostrar, txtBuscar, txtFechaIni, txtFechaFin, txtCbxEstado);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "ver_his_fic.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RE_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CR_RE_TXT_BUSCAR", txtBuscar);
                    request.setAttribute("CR_RFAN_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CR_RFAN_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CR_RFAN_TXT_FIL_ESTADO", txtCbxEstado);
                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFiltro);
                    request.setAttribute("CR_RFAN_TXT_IDPACIENTE", idPaciente);
                    request.setAttribute("CR_RFAN_TXT_NAME_PACIENTE", NamePaciente);
//                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
//                    
//                } else if (isNumber(accionRptHisFAN) || accionRptHisFAN.equalsIgnoreCase("<<") || accionRptHisFAN.equalsIgnoreCase(">>")) {
//                    acceso = "rpt_his_fic.htm";
//                    var_redireccionar = 1;
//                    acceso = paginacion("PAG_RPT_HIST_FAN", request, response, sesionDatosUsuario, acceso, accionRptHisFAN, 5, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptHisFAN.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
//                    acceso = "ver_his_fic.htm";
//                    sesionDatosUsuario.setAttribute("PAG_RPT_HIST_FAN_CANT_BTN_DERE_CLIC", "1");
//                    sesionDatosUsuario.setAttribute("PAG_RPT_HIST_FAN_LISTA_ACTUAL", "1");
//                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accionRptHisFAN.equalsIgnoreCase("Volver Atras")) {
                    System.out.println("------------------------__VOLVER_ATRAS__------------------------------");
                    var_redireccionar = 0;
                    String varVolverAtras = (String) sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS");
                    System.out.println("_   _VAR-VOLVER-ATRAS:   :"+varVolverAtras);
                    if (varVolverAtras.equals("REPORTE_PACIENTE")) {
                        acceso = "rpt_paciente.htm";
                    } else if (varVolverAtras.equals("FICHA_ATENCION_PAC_NUTRI")) {
                        acceso = "add_atencion.htm";
                        String IDFICHA = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                        System.out.println("_   _CR___ID_FICHA_ATENCION:  :"+IDFICHA);
                        if (IDFICHA.isEmpty()) {
                            String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                            if (sesionDatosUsuario.getAttribute("CFAP_LAST_FICHA_VALUES") == null) { // SI LA LISTA ESTA VACIA ENTONCES LE VUELVO A CARGAR.- 
                                List<BeanFichaAtePaciente> datosUltFichaPac = metodosFichaAtencionPacNutri.getUltimaFicha(IDPACIENTE);
                                sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
                            }
                            
                        }
                        List<String> datosPac = (List) sesionDatosUsuario.getAttribute("CFAP_PAC_DATOS");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        if (datosPac == null) {
                            System.out.println("___IS_NULL____");
                        } else {
                            System.out.println("___IS_NOT_NULL___");
                        }
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                            List<String> datosFichaCab01 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_01");
                            List<String> datosFichaCab02 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_02");
                            List<String> datosFichaCab03 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_03");
                            List<String> datosFichaCab04 = (List) sesionDatosUsuario.getAttribute("CFAP_FICHA_CAB_04");
                            request.setAttribute("CFAP_PAC_DATOS", datosPac);
                            request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
                            request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
                            request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
                            request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
                            
                    } else if (varVolverAtras.equals("VER_PACIENTE")) {
                        acceso = "ver_paciente.htm"; // [OBS] AL FINAL DEL CONTROLADOR LE CARGO LOS DATOS NECESARIOS PARA VER EL EXPEDIENTE DEL PACIENTE COMO LO HAGO EN EL CONTROLADOR DE FICHA DE ATENCION PACIENTE (NUTRI).-
                    } else if (varVolverAtras.equals("HISTORICO_FICHA")) {
                        var_redireccionar = 1;
                        acceso = goVerHistoricoFichasPaciente(request, metodosFichaAtencionPacNutri);
                        String volverAtrasAuxValue = (String) sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS_AUX");
                        System.out.println("_____VOLVER_ATRAS_AUX:   :"+volverAtrasAuxValue);
                        sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS",volverAtrasAuxValue); // OBS.: LE VUELVO A CARGAR LA VARIABLE DE VOLVER ATRAS Y ES PARA QUE SI UTILIZA EL BOTON DE "VOLVER ATRAS" DEL HISTORICO DE FICHAS, ENTONCES LE DEVUELVA A LA PAGINA QUE CORRESPONDA.-
                    } else {
                        acceso = "rpt_paciente.htm";
                    }
                    
                } else if (accionRptHisFAN.equalsIgnoreCase("VerArchivosAdj")) {
                    System.out.println("------------------------__VER_ARCHIVOS_ADJUNTOS__------------------------------");
                    acceso = "ver_his_fic_det.htm";
                    var_redireccionar = 1;
                    String ID_FICHA = (String) request.getParameter("tI");
                    System.out.println("-   _VAR____ID_FICHA:  :"+ID_FICHA);
                    String txtIdPac = (String) request.getParameter("tIP");
                    String NAME_PACIENTE = (String) request.getParameter("tCRPNC");
                    System.out.println("_  ___ID_PACIENTE:     :"+txtIdPac);
                    System.out.println("_  ___PACIENTE_NAME:   :"+NAME_PACIENTE);
                    
                    // cargo la lista 
                    List<BeanFichaAtePaciente> LISTA_DET_FICHAS = new ArrayList<>();
                    LISTA_DET_FICHAS = metodosFichaAtencionPacNutri.getDatosFichasDet(ID_FICHA);
                    
                    request.setAttribute("CR_RFAN_TXT_IDFICHA", ID_FICHA);
                    request.setAttribute("CR_RFAN_TXT_IDPACIENTE", txtIdPac);
                    request.setAttribute("CR_RFAN_TXT_NAME_PACIENTE", NAME_PACIENTE);
                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_DET_FICHA_AA", LISTA_DET_FICHAS);
                    String volverAtrasValue = String.valueOf(sesionDatosUsuario.getAttribute("CR_RFAN_BTN_VOLVER_ATRAS"));
                    System.out.println("_   _VAA____VOLVER_ATRAS:   :"+volverAtrasValue);
                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS_AUX", volverAtrasValue); // CARGO EN UNA VARIABLE AUXILIAR EL DATO QUE TENIA SOBRE LA VARIABLE DE "VOLVER ATRAS" PARA DESPUES AL VOLVER ATRAS AL HISTORICO DE FICHA Y VUELVA ATRAS ENTONCES USE ESTA VARIABLE AUXILIAR PARA SABER A QUE PAGINA TENGO QUE VOLVER ATRAS 
                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "HISTORICO_FICHA"); // VARIABLE QUE UTILIZO PARA EL EVENTO DE VOLVER ATRAS 
                }
                
                
            } else 
            // --------     REPORTE ESTADISTICAS     -------------
            if (accionRptEst != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------______ACCION__REPORTE__ESTADISTICAS_______---------");
                System.out.println(".");System.out.println(".");
                acceso = "rpt_est.htm";
                if (accionRptEst.equalsIgnoreCase("Exportar a Excel")) {
                    System.out.println("---------------------__EXPORTAR-A-EXCEL__--------------------------");
                    // RECUPERO LAS VARIABLES 
                    System.out.println("---------------------____DATOS____---------------------------");
                    String filtro_txtIdPac = (String) request.getParameter("tIC");
                    System.out.println("_   FILTRO_TXT_ID_PACIENTE:      "+filtro_txtIdPac);
                    String txtPacNombre = (String) request.getParameter("tICN");
                    System.out.println("_     FILTRO_TXT_NOMBRE_PAC:     :"+txtPacNombre);
                    String txtPacApellido = (String) request.getParameter("tICA");
                    System.out.println("_   FILTRO_TXT_APELLIDO_PAC:     :"+txtPacApellido);
                    String TXT_TIPO_DE_EST = (String) request.getParameter("RECTE");
                    System.out.println("_   _CTRL____TIPO_ESTADISTICA:   :"+TXT_TIPO_DE_EST);
                    System.out.println("-------------------------------------------------------------");
                    String fileNameExcel = filtro_txtIdPac+"_"+txtPacNombre;
                    acceso = "rpt_ver_est.htm";
                    var_redireccionar = 2;
                    
                    // establesco la manera de responder al usuario, instanciamos que para esta respuesta se descargue un archivo excel que en los siguientes codigos vamos a crear 
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition", "attachment;filename="+fileNameExcel+".xlsx");
                    // recupero la lista del historico de fichas 
                    List<BeanFichaAtePaciente> listaFichas = new ArrayList<>();
                    if (request.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC") != null) {
                        System.out.println("___IF___");
                        listaFichas = (List<BeanFichaAtePaciente>)request.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC");
                    } else {
                        System.out.println("___ELSE___");
                        listaFichas = metodosFichaAtencionPacNutri.getAllListasFichasPac(filtro_txtIdPac);
                    }
                    
                    String VAR_MENSAJE=null, VAR_TIPO_MENSAJE="";
                    try {
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        XSSFSheet sheet1 = workbook.createSheet("Historico de Fichas");
                        // fila 0 - para el titulo - 
                        Row rowTitle = sheet1.createRow(0);
                        Cell cellTitle = rowTitle.createCell(0);
                        // creo los estilos 
                        CellStyle cellStyleTitle = workbook.createCellStyle();
                        Font fontTitle = workbook.createFont();
                        fontTitle.setBold(true);
//                        fontTitle.setItalic(true);
//                        fontTitle.setColor(IndexedColors.ROYAL_BLUE.getIndex());
                        fontTitle.setFontHeight((short) 300); // font size: 15pt
                        fontTitle.setFontName("Arial");
                        cellStyleTitle.setFont(fontTitle);
                        cellTitle.setCellValue("Historico de Ficha de: "+txtPacNombre+" "+txtPacApellido);
                        cellTitle.setCellStyle(cellStyleTitle); // le cargo los estilos a la celda 
                        // fila 1 - para la cabecera - 
                        Row r0 = sheet1.createRow(1);
                        // font para la cabecera 
                        CellStyle cellStyleCabs = workbook.createCellStyle();
                        Font fontCabs = workbook.createFont();
                        fontCabs.setBold(true);
                        cellStyleCabs.setFont(fontCabs);
                        // columnas de la fila 1: 
                        Cell r0c0 = r0.createCell(0);
                        r0c0.setCellValue("Cód.");
                        r0c0.setCellStyle(cellStyleCabs);
                        Cell r0c1 = r0.createCell(1);
                        r0c1.setCellValue("Clinica");
                        r0c1.setCellStyle(cellStyleCabs);
                        Cell r0c2 = r0.createCell(2);
                        r0c2.setCellValue("Fecha y Hora");
                        r0c2.setCellStyle(cellStyleCabs);
                        Cell r0c3 = r0.createCell(3);
                        r0c3.setCellValue("Motivo de la Consulta");
                        r0c3.getCellStyle().setWrapText(true);
                        r0c3.setCellStyle(cellStyleCabs);
                        Cell r0c4 = r0.createCell(4);
                        r0c4.setCellValue("Estatura");
                        r0c4.setCellStyle(cellStyleCabs);
                        Cell r0c5 = r0.createCell(5);
                        r0c5.setCellValue("Peso");
                        r0c5.setCellStyle(cellStyleCabs);
                        Cell r0c6 = r0.createCell(6);
                        r0c6.setCellValue("Porc. Grasa");
                        r0c6.setCellStyle(cellStyleCabs);
                        Cell r0c7 = r0.createCell(7);
                        r0c7.setCellValue("Edad M.");
                        r0c7.setCellStyle(cellStyleCabs);
                        Cell r0c8 = r0.createCell(8);
                        r0c8.setCellValue("Porc. Músculo");
                        r0c8.setCellStyle(cellStyleCabs);
                        Cell r0c9 = r0.createCell(9);
                        r0c9.setCellValue("RM");
                        r0c9.setCellStyle(cellStyleCabs);
                        Cell r0c10 = r0.createCell(10);
                        r0c10.setCellValue("IMC");
                        r0c10.setCellStyle(cellStyleCabs);
                        Cell r0c11 = r0.createCell(11);
                        r0c11.setCellValue("Visceral");
                        r0c11.setCellStyle(cellStyleCabs);
                        Cell r0c12 = r0.createCell(12);
                        r0c12.setCellValue("Balanza");
                        r0c12.setCellStyle(cellStyleCabs);
                        Cell r0c13 = r0.createCell(13);
                        r0c13.setCellValue("Estado");
                        r0c13.setCellStyle(cellStyleCabs);
                        // DETALLE 
                        ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
                        System.out.println("___ANTES_DEL_FOR_____");
                        System.out.println("___SIXE:  :"+listaFichas.size());
                        int rowNum = 2; // el row 0 son las columnas, por eso los datos empiezan con la fila 1 
                        for (BeanFichaAtePaciente ficha : listaFichas) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("_   _____FOR_____("+rowNum+")____");
                            System.out.println(".   _");
//                            BeanFichaAtePaciente beanFicha = listaFichas.get(i);
                            Row r1 = sheet1.createRow(rowNum);
                            for (int i = 0; i < 14; i++) { // 13 columnas 
                                System.out.println("-   ___COL_NUM:  :"+i);
                                Cell r1c1 = r1.createCell(i);
                                String valueR1C1 = "";
                                switch (i) {
                                    case 0:
                                        valueR1C1 = ficha.getOFPN_IDFICHAPAC();
                                        sheet1.setColumnWidth(i, 2000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 1:
//                                        String DESC_CLINICA = metodosRefClinica.traerDescClinica(ficha.getOFPN_IDCLINICA());
//                                        valueR1C1 = DESC_CLINICA;
                                        valueR1C1 = ficha.getOFPN_DESC_CLINICA();
                                        sheet1.setColumnWidth(i, 4000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 2:
                                        valueR1C1 = ficha.getOFPN_FECHA_FICHA_ATE();
                                        sheet1.setColumnWidth(i, 5000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 3:
                                        valueR1C1 = ficha.getOFPN_MOTIVO_DE_LA_CONSULTA();
                                        sheet1.setColumnWidth(i, 8000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 4:
                                        valueR1C1 = ficha.getOFPN_ESTATURA();
                                        sheet1.setColumnWidth(i, 2200); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 5:
                                        valueR1C1 = ficha.getOFPN_PESO();
                                        sheet1.setColumnWidth(i, 2000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 6:
                                        valueR1C1 = ficha.getOFPN_PORC_GRASA();
                                        sheet1.setColumnWidth(i, 2800); // [OBS] LE AGREGO MAS VALOR AL TAMAÑO DE LA COLUMNA PARA PODER MOSTRAR EL DATO DE LA COLUMNA //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 7:
                                        valueR1C1 = ficha.getOFPN_EDAD_METABOLICA();
                                        sheet1.setColumnWidth(i, 2200); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 8:
                                        valueR1C1 = ficha.getOFPN_PORC_MUSCULO();
                                        sheet1.setColumnWidth(i, 3400); // [OBS] LE AGREGO MAS VALOR AL TAMAÑO DE LA COLUMNA PARA PODER MOSTRAR EL DATO DE LA COLUMNA //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 9:
                                        valueR1C1 = ficha.getOFPN_RM();
                                        sheet1.setColumnWidth(i, 2000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 10:
                                        valueR1C1 = ficha.getOFPN_IMC();
                                        sheet1.setColumnWidth(i, 2000); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 11:
                                        valueR1C1 = ficha.getOFPN_VISCERAL();
                                        sheet1.setColumnWidth(i, 2200); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 12:
                                        valueR1C1 = ficha.getOFPN_TIPO_DE_BALANZA();
                                        sheet1.setColumnWidth(i, 2500); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    case 13:
                                        valueR1C1 = modeloIniSes.devolverTextEstadoAgend(ficha.getOFPN_ESTADO());
                                        sheet1.setColumnWidth(i, 2500); //Set column width, you'll probably want to tweak the second int
                                        break;
                                    default:
                                        break;
                                }
//                                sheet1.setColumnWidth(i, 10000); //Set column width, you'll probably want to tweak the second int
                                CellStyle style = workbook.createCellStyle(); //Create new style
                                style.setWrapText(true); //Set wordwrap
                                r1c1.setCellStyle(style); //Apply style to cell
                                r1c1.setCellValue(valueR1C1); //Write header 
                            }
                            rowNum++;
                            System.out.println("............................");
                        }
//                        // carga mas elaborada 
//                        String valueR1C1 = "lorem ipsum is simply dummy text";
//                        sheet1.setColumnWidth(3, 10000); //Set column width, you'll probably want to tweak the second int
//                        CellStyle style = workbook.createCellStyle(); //Create new style
//                        style.setWrapText(true); //Set wordwrap
//                        r1c1.setCellStyle(style); //Apply style to cell
//                        r1c1.setCellValue(valueR1C1); //Write header 
//                        // carga sencilla 
//                        Cell r1c4 = r1.createCell(4);
//                        r1c4.setCellValue("lorem-");
//                        // carga sencilla modificando unos estilos 
//                        Cell r1c2 = r1.createCell(8);
//                        r1c2.getCellStyle().setWrapText(false);
    //                    r1c2.setCellValue("lorem ipsum is simply dummy text");
                        // cerrando el archivo.-
//                        File f = new File("C:\\Users\\USER\\Desktop\\"+fileNameExcel+".xlsx");
//                        FileOutputStream fos = new FileOutputStream(f);
//                        workbook.write(fos);
//                        fos.close();
                        workbook.write(response.getOutputStream());
                        workbook.close();
                        System.out.println("File is written successfully!");
                        // cargo el mensaje para la pagina 
                        VAR_MENSAJE = "Se ha exportado a excel correctamente el archivo("+fileNameExcel+").";
                        VAR_TIPO_MENSAJE = "1";
                    } catch(FileNotFoundException e) {
                        // java.io.FileNotFoundException    // (El proceso no tiene acceso al archivo porque está siendo utilizado por otro proceso).-
                        System.out.println("-   -------------__FILE_NOT_FOUND_EXCEPTION__-----------------");
                        System.out.println("------- error");
                        VAR_TIPO_MENSAJE = "2";
                        VAR_MENSAJE = "Ya existe el excel, elimine/cierre el archivo \""+fileNameExcel+"\" para poder volver a exportarlo.";
                        System.out.println("_   ----------------------------------------------------------");
                    }
                    // PASO LAS VARIABLES QUE USO AL JSP.-
                    request.setAttribute("CR_RE_MENSAJE", VAR_MENSAJE);
                    request.setAttribute("CR_RE_TIPO_MENSAJE", VAR_TIPO_MENSAJE);
                    request.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    request.setAttribute("CR_RE_TXT_PAC_NOM", txtPacNombre);
                    request.setAttribute("CR_RE_TXT_PAC_APE", txtPacApellido);
                    request.setAttribute("CR_RE_TIPO_ESTADISTICA", TXT_TIPO_DE_EST);
                    // RECUPERAR LA LISTA CON LAS FICHAS DEL PACIENTE 
//                    List<BeanFichaAtePaciente> listaFichasPac = new ArrayList<>();
//                    listaFichasPac = metodosFichaAtencionPacNutri.getAllListasFichasPac(filtro_txtIdPac);
                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichas);
                    // variables que las utilizo en la pagina: pagRpt_EstadisticaDatos_excel.jsp : que uso como archivo excel para descargar con el boton comentado en la pagina de: pagRpt_EstadisticaDatos.jsp.-
                    sesionDatosUsuario.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    sesionDatosUsuario.setAttribute("PARAM_PACIENTE_NAME", (txtPacNombre+" "+txtPacApellido));
                    sesionDatosUsuario.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichas);
                    // le paso el name para el archivo excel en caso de que se quiera exportar a excel
                    sesionDatosUsuario.setAttribute("CR_RFAN_NAME_EXCEL", (filtro_txtIdPac+txtPacApellido));
                    
                } else if (accionRptEst.equalsIgnoreCase("Ver Estadísticas")) {
                    System.out.println("---------------------__VER_ESTADISTICAS__--------------------------");
                    String filtro_txtIdPac = (String) request.getParameter("tIC");
                    System.out.println("_   FILTRO_TXT_ID_PACIENTE:      "+filtro_txtIdPac);
                    String txtPacNombre = (String) request.getParameter("tICN");
                    System.out.println("_     FILTRO_TXT_NOMBRE_PAC:     :"+txtPacNombre);
                    String txtPacApellido = (String) request.getParameter("tICA");
                    System.out.println("_   FILTRO_TXT_APELLIDO_PAC:     :"+txtPacApellido);
//                    String TXT_TIPO_DE_EST = (String) request.getParameter("RECTE"); // [OBS]: COMENTE ESTO Y CREE EL EVENTO "RECARGAR GRAFICOS" PORQUE NO SE PUEDE RECUPERAR EN ESTE EVENTO EL TIPO DE GRAFICO YA QUE NO SE ENCUENTRA EL COMBO DE TIPO DE ESTADISTICA DENTRO DEL FORM DE LA TABLA QUE ACTIVA ESTE EVENTO.-
//                    System.out.println("_   _CTRL____TIPO_ESTADISTICA:   :"+TXT_TIPO_DE_EST);
                    
                    acceso = "rpt_ver_est.htm";
                    var_redireccionar = 1;
                    sesionDatosUsuario.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    request.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    request.setAttribute("CR_RE_TXT_PAC_NOM", txtPacNombre);
                    request.setAttribute("CR_RE_TXT_PAC_APE", txtPacApellido);
                    request.setAttribute("CR_RE_TIPO_ESTADISTICA", "0"); // COMO DESDE LA PAGINA DE VER ESTADISTICA SE ENCUENTRA EL COMBO ENTONCES POR DEFECTO LE DEJO PARA QUE VEA LOS GRAFICOS EN LINEAS PORQUE SI NO LE PASO NINGUN VALOR, NO SE VERA NINGUN GRAFICO.-
//                    request.setAttribute("CR_RE_TIPO_ESTADISTICA", TXT_TIPO_DE_EST);
                    sesionDatosUsuario.setAttribute("PARAM_PACIENTE_NAME", (txtPacNombre+" "+txtPacApellido));
                    
                    // RECUPERAR LA LISTA CON LAS FICHAS DEL PACIENTE 
                    List<BeanFichaAtePaciente> listaFichasPac = new ArrayList<>();
                    listaFichasPac = metodosFichaAtencionPacNutri.getAllListasFichasPac(filtro_txtIdPac);
                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichasPac);
                    sesionDatosUsuario.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichasPac);
                    // le paso el name para el archivo excel en caso de que se quiera exportar a excel
                    sesionDatosUsuario.setAttribute("CR_RFAN_NAME_EXCEL", (filtro_txtIdPac+txtPacApellido));
                    
                } else if (accionRptEst.equalsIgnoreCase("Recargar los Gráficos")) {
                    System.out.println("---------------------__RECARGAR_LOS_GRAFICOS__--------------------------");
                    String filtro_txtIdPac = (String) request.getParameter("tIC");
                    System.out.println("_   FILTRO_TXT_ID_PACIENTE:      "+filtro_txtIdPac);
                    String txtPacNombre = (String) request.getParameter("tICN");
                    System.out.println("_     FILTRO_TXT_NOMBRE_PAC:     :"+txtPacNombre);
                    String txtPacApellido = (String) request.getParameter("tICA");
                    System.out.println("_   FILTRO_TXT_APELLIDO_PAC:     :"+txtPacApellido);
                    String TXT_TIPO_DE_EST = (String) request.getParameter("RECTE");
                    System.out.println("_   _CTRL____TIPO_ESTADISTICA:   :"+TXT_TIPO_DE_EST);
                    
                    acceso = "rpt_ver_est.htm";
                    var_redireccionar = 1;
                    sesionDatosUsuario.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    request.setAttribute("CR_RE_TXT_IDPACIENTE", filtro_txtIdPac);
                    request.setAttribute("CR_RE_TXT_PAC_NOM", txtPacNombre);
                    request.setAttribute("CR_RE_TXT_PAC_APE", txtPacApellido);
                    request.setAttribute("CR_RE_TIPO_ESTADISTICA", TXT_TIPO_DE_EST);
                    sesionDatosUsuario.setAttribute("PARAM_PACIENTE_NAME", (txtPacNombre+" "+txtPacApellido));
                    // RECUPERAR LA LISTA CON LAS FICHAS DEL PACIENTE 
                    List<BeanFichaAtePaciente> listaFichasPac = new ArrayList<>();
                    listaFichasPac = (List<BeanFichaAtePaciente>)sesionDatosUsuario.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC");
                    request.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichasPac);
                    sesionDatosUsuario.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichasPac);
//                    // le paso el name para el archivo excel en caso de que se quiera exportar a excel
//                    sesionDatosUsuario.setAttribute("CR_RFAN_NAME_EXCEL", (filtro_txtIdPac+txtPacApellido));
                    
                } else if (accionRptEst.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosFichaAtencionPacNutri.filtrar_rpt_est(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_est.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RE_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CR_RE_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CR_RE_LISTA_FILTRO", listaFiltro);
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (isNumber(accionRptEst) || accionRptEst.equalsIgnoreCase("<<") || accionRptEst.equalsIgnoreCase(">>")) {
                    acceso = "rpt_est.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_RPT_EST", request, response, sesionDatosUsuario, acceso, accionRptEst, 5, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptEst.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "rpt_est.htm";
                    sesionDatosUsuario.setAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_RPT_EST_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            } else 
            // --------     REPORTE PACIENTE     -------------
            if (accionRptPac != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------______ACCION__REPORTE__PACIENTE_______---------");
                System.out.println(".");System.out.println(".");
                acceso = "rpt_paciente.htm";
                if (accionRptPac.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanPersona> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosPersona.filtrar_rpt_pac(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_paciente.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RP_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CR_RP_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CR_RP_LISTA_FILTRO", listaFiltro);
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (isNumber(accionRptPac) || accionRptPac.equalsIgnoreCase("<<") || accionRptPac.equalsIgnoreCase(">>")) {
                    acceso = "rpt_paciente.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_RPT_PAC", request, response, sesionDatosUsuario, acceso, accionRptPac, 1, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptPac.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "rpt_paciente.htm";
                    sesionDatosUsuario.setAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_RPT_PAC_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            // --------     REPORTE CUENTA PACIENTE     -------------
            } else if(accionRptCtaPac != null) {
                System.out.println("---------______ACCION__REPORTE_CUENTA_PACIENTE_______---------");
                acceso = "rpt_cta_paciente.htm";
                if (accionRptCtaPac.equalsIgnoreCase("Filtrar")) {
                    System.out.println("-----------------------__FILTRAR__---------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // clinica id 
                    if (txtClinicaId == null || txtClinicaId.isEmpty()) {
                        txtClinicaId = "";
                    }
                    System.out.println("_   CHECK_CLINICA:   "+checkClinicaFiltro);
                    System.out.println("_   CLINICA_ID   :   "+txtClinicaId);
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                    if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                        checkClinicaFiltro = "OFF";
                    } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClinicaId = "";
                    }
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanCuentaCliente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosCuentaCliente.filtrar_reporte_cta_pac(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtClinicaId);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_cta_paciente.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RCC_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CR_RCC_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CR_RCC_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CR_RCC_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
                    request.setAttribute("CR_RCC_TXT_FIL_ID_CLI", txtClinicaId);
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (isNumber(accionRptCtaPac) || accionRptCtaPac.equalsIgnoreCase("<<") || accionRptCtaPac.equalsIgnoreCase(">>")) {
                    acceso = "rpt_cta_paciente.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_RPT_CTAPAC", request, response, sesionDatosUsuario, acceso, accionRptCtaPac, 2, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                    ESTAS SON ACCIONES DE LA PAGINA DEL REPORTE DEL PACIENTE 
                */
                } else if (accionRptCtaPac.equalsIgnoreCase("Filtrar Cuenta")) {
                    System.out.println("------------------------__FILTRAR_CUENTA__------------------------------");
                    String txtFiltrarCta = (String) request.getParameter("tFC"); // tFC: TXT FILTRAR CUENTA 
                    System.out.println("_   _TXT_FILTRAR_CTA:    :"+txtFiltrarCta);
                    String IDCLIENTE = idPersona; // EL IDPERSONA SERIA EL IDCLIENTE YA QUE EL PACIENTE LOGEADO QUIERE VER SU PROPIA CUENTAS 
                    System.out.println("_   _TXT_ID_CLIENTE:     :"+IDCLIENTE);
                    String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // clinica id 
                    if (txtClinicaId == null || txtClinicaId.isEmpty()) {
                        txtClinicaId = "";
                    }
                    System.out.println("_   CHECK_CLINICA:   "+checkClinicaFiltro);
                    System.out.println("_   CLINICA_ID   :   "+txtClinicaId);
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                    if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                        checkClinicaFiltro = "OFF";
                    } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClinicaId = "";
                    }
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanCuentaCliente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosCuentaCliente.filtrarCtaEmp(sesionDatosUsuario, IDCLIENTE, txtFiltrarCta, txtClinicaId);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_cta_paciente.htm";
                    var_redireccionar = 1;
//                    sesionDatosUsuario.setAttribute("ID_CLIENTE", IDCLIENTE);
                    request.setAttribute("CR_RCC_FILTRAR_CUENTA", txtFiltrarCta);
                    request.setAttribute("CR_RCC_LISTA_FILTRO_CTA", listaFiltro);
                    sesionDatosUsuario.setAttribute("CR_RCC_LISTA_FILTRO_CTA", listaFiltro); // PARA EL PDF AGREGO A LA SESION LA LISTA, NO LO UTILIZO PARA LA PAGINA JSP 
                    request.setAttribute("CR_RCC_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
                    request.setAttribute("CR_RCC_TXT_FIL_ID_CLI", txtClinicaId);
                    
                } else if(accionRptCtaPac.equalsIgnoreCase("Limpiar Filtro")) {
                    System.out.println("------------------------__LIMPIAR_FILTRAR__------------------------------");
                    String IDCLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                    System.out.println("_   _TXT_ID_CLIENTE:    :"+IDCLIENTE);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_cta_paciente.htm";
                    var_redireccionar = 0;
//                    sesionDatosUsuario.setAttribute("ID_CLIENTE", IDCLIENTE);
                    request.setAttribute("CR_RCC_FILTRAR_CUENTA", "");
                    request.setAttribute("CR_RCC_LISTA_FILTRO_CTA", null);
                    sesionDatosUsuario.setAttribute("CR_RCC_LISTA_FILTRO_CTA", null); // LIMPIO LA LISTA SESION DEL PDF 
                    request.setAttribute("CR_RCC_CHECK_FILTRAR_CLI_01", "ON");
                    request.setAttribute("CR_RCC_TXT_FIL_ID_CLI", "");
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptCtaPac.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "rpt_cta_paciente.htm";
                    sesionDatosUsuario.setAttribute("PAG_RPT_CTAPAC_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_RPT_CTAPAC_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            // --------     REPORTE FACTURA     -------------
            } else if(accionRptFact != null) {
                System.out.println("[*]---------______ACCION__REPORTE_FACTURA_______---------");
                acceso = "rpt_factura.htm";
                if (accionRptFact.equalsIgnoreCase("Filtrar")) {
                    System.out.println("[*]-----------------------__FILTRAR__---------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    
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
                    System.out.println("[*]------------__VAR_FILTRAR__-----------");
                    System.out.println("[*]_  _CBX_MOSTRAR    :  "+filtro_cbxMostrar);
                    System.out.println("[*]_  _BUSCAR_NRO_FACT:  "+filtro_txtBuscar);
                    System.out.println("[*]_  _FECHA_INICIO :    "+txtFechaIni);
                    System.out.println("[*]_  _FECHA_FIN    :    "+txtFechaFin);
                    System.out.println("[*]_  _CHECK_CLIENTE:    "+checkClienteFiltro);
                    System.out.println("[*]_  _CLIENTE_ID   :    "+txtClienteId);
                    System.out.println("[*]--------------------------------------");
                    
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
                    
                    String varFiltroEstadoCabFact = ""; // LO DEJO VACIO YA QUE QUIERO QUE EL FILTRO ME MUESTRE TODOS LOS ESTADO, PERO SI QUISIESE QUE ME MUESTRE SOLO LOS COBRADOS, ENTONCES AHI SI CARGARIA EL ESTADO 
                    String txt_IDCLINICA = ""; // VARIABLE QUE LA DEJO VACIO PARA QUE EN EL METODO SE CARGUE CON EL IDCLINICA DE LOGEO, PERO EN CASO DE QUE HAYA UN FILTRO EN LA PAGINA, ENTONCES AHI SE CARGARIA ESTA VARIABLE Y EL CODIGO PARA HACERLO, Y EN EL METODO FILTRARIA POR ESA IDCLINICA Y NO POR LA DE LOGEO 
                    
                    // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
                    String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                    System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
                    String IDPACIENTE = "";
                    // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
                    if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                        IDPACIENTE = idPersona;
                    }
                    System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);
                    
                    // CARGAR LISTA 
                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosFactura.filtrar_paginacion_rpt_fact(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtFechaIni, txtFechaFin, txtClienteId, varFiltroEstadoCabFact, txt_IDCLINICA, IDPACIENTE);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_factura.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RF_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CR_RF_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CR_RF_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CR_RF_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CR_RF_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CR_RF_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
                    request.setAttribute("CR_RF_TXT_FILTRAR_CLIE_ID", txtClienteId);
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (isNumber(accionRptFact) || accionRptFact.equalsIgnoreCase("<<") || accionRptFact.equalsIgnoreCase(">>")) {
                    acceso = "rpt_factura.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_RPT_FACT", request, response, sesionDatosUsuario, acceso, accionRptFact, 3, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptFact.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "rpt_factura.htm";
                    sesionDatosUsuario.setAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_RPT_FACT_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            // --------     REPORTE DE MIS AGENDAMIENTOS     -------------
            } else if(accionRptMisAgen != null) {
                System.out.println("---------______ACCION__REPORTE_MIS_AGENDAMIENTOS_______---------");
                acceso = "rpt_mis_agen.htm";
                if (accionRptMisAgen.equalsIgnoreCase("Filtrar")) {
                    System.out.println("-----------------------__FILTRAR__---------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    String txtFechaIni = (String) request.getParameter("tRMAFI"); // tRMAFI : txt reporte mis agendamientos fecha inicio 
                    if (txtFechaIni == null || txtFechaIni.isEmpty()) { txtFechaIni = ""; }
                    String txtFechaFin = (String) request.getParameter("tRMAFF"); // tRMAFF : txt reporte mis agendamientos fecha fin 
                    if (txtFechaFin == null || txtFechaFin.isEmpty()) { txtFechaFin = ""; }
                    String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtMedicoId = (String) request.getParameter("cbxAddNewMed"); // MEDICO id 
                    if (txtMedicoId == null || txtMedicoId.isEmpty()) { txtMedicoId = ""; }
                    String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtEspeId = (String) request.getParameter("cbxAddNewEspe"); // ESPECIALIDAD id 
                    if (txtEspeId == null || txtEspeId.isEmpty()) { txtEspeId = ""; }
                    String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // CLINICA id 
                    if (txtClinicaId == null || txtClinicaId.isEmpty()) { txtClinicaId = ""; }
                    String checkPacienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
                    String txtPacienteId = (String) request.getParameter("cbxAddNewCli"); // PACIENTE id 
                    if (txtPacienteId == null || txtPacienteId.isEmpty()) { txtPacienteId = ""; }
                    // COMBO ESTADO 
                    String checkEstadoFiltro = (String) request.getParameter("check_estado"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_ESTADO = (String) request.getParameter("cE"); // ESTADO  
                    if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = ""; }
                    System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
                    System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
                    System.out.println("---------------__VAR_FILTRAR__----------------");
                    System.out.println("_  _CBX_MOSTRAR    : "+filtro_cbxMostrar);
                    System.out.println("_  _BUSCAR_NRO_FACT: "+filtro_txtBuscar);
                    System.out.println("_  _FECHA_INICIO:    "+txtFechaIni);
                    System.out.println("_  _FECHA_FIN   :    "+txtFechaFin);
                    System.out.println("_  _CHECK_MEDICO:    "+checkMedicoFiltro);
                    System.out.println("_  _MEDICO_ID   :    "+txtMedicoId);
                    System.out.println("_  _CHECK_ESPECIA:   "+checkEspeFiltro);
                    System.out.println("_  _ESPECIALIDAD_ID: "+txtEspeId);
                    System.out.println("_  _CHECK_CLINICA:   "+checkClinicaFiltro);
                    System.out.println("_  _CLINICA_ID   :   "+txtClinicaId);
                    System.out.println("_  _CHECK_PACIENTE:  "+checkPacienteFiltro);
                    System.out.println("_  _PACIENTE_ID   :  "+txtPacienteId);
                    System.out.println("----------------------------------------------");
                    
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR MEDICO 
                    if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO 
                        checkMedicoFiltro = "OFF";
                    } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtMedicoId = "";
                    }
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR LA ESPECILAIDAD 
                    if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD 
                        checkEspeFiltro = "OFF";
                    } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtEspeId = "";
                    }
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLINICA, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLINICA PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLINICA 
                    if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA CLINICA Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLINICA 
                        checkClinicaFiltro = "OFF";
                    } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtClinicaId = "";
                    }
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR PACIENTE 
                    if (checkPacienteFiltro == null || checkPacienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPERSONA DEL PACIENTE 
                        checkPacienteFiltro = "OFF";
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
                    } else if (checkPacienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        txtPacienteId = "";
                    }
                    // CONTROL DEL CHECK DE ESTADO 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
                    if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                        checkEstadoFiltro = "OFF";
                    } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_ESTADO = "";
                    }
                    
                    // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
                    String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                    System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
                    String IDPACIENTE = "";
                    // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
                    if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                        IDPACIENTE = idPersona; // SI FUERA EL PERFIL PACIENTE, ENTONCES NO LE VOY A MOSTRAR EL FILTRO DE PACIENTE Y DIRECTAMENTE UTILIZARE EL IDPERSONA DE LOGEO 
                    } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) {
                        IDPACIENTE = txtPacienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
                    }
                    System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);
                    
                    // CARGAR LISTA 
                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosAgendamiento.filtrar_pagi_rpt_misAgen(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtFechaIni, txtFechaFin, txtEspeId, txtMedicoId, txtClinicaId, IDPACIENTE, IDPERFIL_USER, PARAM_TXT_ESTADO);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "rpt_mis_agen.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CR_RMA_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CR_RMA_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CR_RMA_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_FEC_INI", txtFechaIni);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_FEC_FIN", txtFechaFin);
                    request.setAttribute("CR_RMA_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_MED_ID", txtMedicoId);
                    request.setAttribute("CR_RMA_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_ESPE_ID", txtEspeId);
                    request.setAttribute("CR_RMA_CHECK_FILTRAR_CLINICA_01", checkClinicaFiltro);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_CLINICA_ID", txtClinicaId);
                    request.setAttribute("CR_RMA_CHECK_FILTRAR_PACIENTE_01", checkPacienteFiltro);
                    request.setAttribute("CR_RMA_TXT_FILTRAR_PACIENTE_ID", txtPacienteId);
                    request.setAttribute("CR_RMA_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
                    request.setAttribute("CR_RMA_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                
                } else if (isNumber(accionRptMisAgen) || accionRptMisAgen.equalsIgnoreCase("<<") || accionRptMisAgen.equalsIgnoreCase(">>")) {
                    acceso = "rpt_mis_agen.htm";
                    var_redireccionar = 1;
                    acceso = paginacion("PAG_RPT_MIS_AGEN", request, response, sesionDatosUsuario, acceso, accionRptMisAgen, 4, metodosPersona, metodosAgendamiento, metodosCuentaCliente, metodosFactura, metodosFichaAtencionPacNutri);
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accionRptMisAgen.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "rpt_mis_agen.htm";
                    sesionDatosUsuario.setAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
            } // END ELSE-IF ACCIONES 
            
            
            
            /*
            // [OBS] SAQUE ESTE BLOQUE DEL CONTROLADOR DE FICHA ATENCION (NUTRI) YA QUE ACA NECESITO VOLVER ATRAS AL EXPEDIENTE DEL PACIENTE COMO LO HAGO EN EL CONTROLADOR DE FICHA.-
             * COLOCO ACA AL FINAL DE LOS EVENTOS PARA NO IR COLOCANDO EN CADA EVENTO ESTE BLOQUE DE CODIGO 
                Y CONTROLARIA LA VARIABLE QUE REDIRECCIONA A LAS PAGINAS PARA DETECTAR CUANDO VA A VOLVER AL EXPEDIENTE DEL PACIENTE Y EN ESE CASO CARGARIA LAS VARIABLES PARA NO PASAR VACIO LOS DATOS.-
             * SI LA PAGINA A REDIRECCIONAR ES LA DEL EXPEDIENTE DEL PACIENTE 
                ENTONCES LE VOY A PASAR LA LISTA CON LOS DATOS DEL PACIENTE PARA QUE NO REDIRECCIONE Y SE ENCUENTREN VACIOS LOS DATOS 
            */
            System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   ------------CONTROL-FINAL-EN-EL-CONTROLADOR-DE-REPORTES-------VER_EXPEDIENTE_PAC---------------");
            System.out.println(".");
            System.out.println(".   ____acceso: "+acceso);
            if (acceso.equalsIgnoreCase("ver_paciente.htm")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
                var_redireccionar = 1;
                System.out.println("_   _____IF_______");
                String ID_PACIENTE_EXP = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                // VER SI PUEDO RECUPERAR DE LA SESION O SI DEBO VOLVER A INVOCAR AL METODO 
                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                if (request.getAttribute("CP_PAC_LISTA_DATOS") != null) {
                    System.out.println("_   _1___if_not_null__");
                    LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                } else {
                    System.out.println("_   _1___else_null__");
                    LISTA_DATOS = metodosPersona.traerDatosPersona(ID_PACIENTE_EXP);
                }
                System.out.println(".");
                System.out.println(".");
                List<BeanFichaAtePaciente> LISTA_ULTIMOS_DATOS_FICHA = new ArrayList<>(); // los datos de la ultima consulta 
                if (request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA") != null) {
                    System.out.println("_   _2___if_not_null__");
                    LISTA_ULTIMOS_DATOS_FICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                } else {
                    System.out.println("_   _2___else_null__");
                    LISTA_ULTIMOS_DATOS_FICHA = metodosFichaAtencionPacNutri.getUltimaFicha(ID_PACIENTE_EXP);
                }
                // PASAR LA LISTA AL JSP 
                sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "VER_PAC");
                request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_ULTIMOS_DATOS_FICHA);
            } else {
                System.out.println("_   _____ELSE_______");
            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");System.out.println(".");System.out.println(".");
            
            
            // COMO EN CADA ACCION LE VUELVO A PASAR EL IDPERSONA, ENTONCES PARA NO VOLVERLO REDUNDANTE LO COLOCO FUERA DE LAS CONDICIONALES 
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println("_   ______VAR_REDIRECIONAR:   :"+var_redireccionar+"_______________");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
        } catch (Exception e) {
            System.out.println("-----------------ERROR-REPORTES--------------------");
            var_redireccionar = 0;
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerReportes.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("---------------------------------------------------");
        }
        
// OBSERVACION: 
// UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if (var_redireccionar == 2) {
            // [OBS]: AGREGO ESTE IF (CON VALOR 2 CUANDO SOLO USO 0 Y 1) PARA EVITAR QUE EJECUTE CODIGO DE RESPUESTA PUES AL EXPORTAR AL EXCEL YA AGREGO UN RESPONSE Y SALTA UN ERROR AL EJECUTAR LUEGO OTRA RESPUESTA POR ESO PARA ESA CLASE DE CASO LE AGREGO EL VALOR 2.-
        } else {
            if (var_redireccionar == 0) {
                response.sendRedirect(acceso);
            } else {
                RequestDispatcher vista = request.getRequestDispatcher(acceso);
                vista.forward(request, response);
            }
        }
    } // END doPost() 
    
    
    
    
    
    
    
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
        String accion, 
        int PARAM_PAG, // VARIABLE QUE UTILIZARE PARA INDIVIDUALIZAR / 1: REPORTE PACIENTE - 2: REPORTE CUENTA PACIENTE - 3: REPORTE FACTURA - 4: REPORTE MIS AGENDAMIENTO 
        ModelPersona metodosPersona, 
        ModelAgendamiento metodosAgendamiento, 
        ModelCuentaCliente metodosCuentaCliente, 
        ModelFactura metodosFactura, 
        ModelFichaAtencionPacNutri metodosFichaPacNutri 
    ) {
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("-------------------------______METODO_PAGINACION______--------------------------------");
        System.out.println("_   _PARAM_PAG_NRO:   :"+PARAM_PAG);
        try {
            if (isNumber(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute(""+PARAM_INI_PAG+"_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_rpt_pac(sesionDatosUsuario, request, metodosPersona);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_rpt_cta_pac(sesionDatosUsuario, request, metodosCuentaCliente);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_rpt_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 5) { // REPORTE DE ESTADISTICA 
                        filtrarFichaAtencionPacNutri(sesionDatosUsuario, request, metodosFichaPacNutri);
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
                String band_filtro = (String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_rpt_pac(sesionDatosUsuario, request, metodosPersona);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_rpt_cta_pac(sesionDatosUsuario, request, metodosCuentaCliente);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_rpt_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 5) { // REPORTE DE ESTADISTICA 
                        filtrarFichaAtencionPacNutri(sesionDatosUsuario, request, metodosFichaPacNutri);
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
                String band_filtro = (String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("VAR_PAGI_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    if (PARAM_PAG == 1) { // REPORTE PACIENTE 
                        filtrar_pag_rpt_pac(sesionDatosUsuario, request, metodosPersona);
                    } else if (PARAM_PAG == 2) { // REPORTE CUENTA PACIENTE 
                        filtrar_pag_rpt_cta_pac(sesionDatosUsuario, request, metodosCuentaCliente);
                    } else if (PARAM_PAG == 3) { // REPORTE FACTURA 
                        filtrar_pag_rpt_fact(sesionDatosUsuario, request, metodosFactura);
                    } else if (PARAM_PAG == 4) { // REPORTE MIS AGENDAMIENTOS 
                        filtrar_pag_rpt_mis_agend(sesionDatosUsuario, request, metodosAgendamiento);
                    } else if (PARAM_PAG == 5) { // REPORTE DE ESTADISTICA 
                        filtrarFichaAtencionPacNutri(sesionDatosUsuario, request, metodosFichaPacNutri);
                    }
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
            }
        } catch (Exception e) {
            System.out.println("-----------------ERROR-REPORTES-------___paginacion("+PARAM_INI_PAG+")___-------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerReportes.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------------------------------------------");
        }
        return acceso;
    } // END METHOD 
    
    
    
    private void filtrar_pag_rpt_pac(HttpSession sesionDatosUsuario, 
        HttpServletRequest request, 
        ModelPersona metodosPersona
    ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".     _    FILTRAR_PAG_RPT_PACIENTE     _       _");
        System.out.println(".");
        System.out.println(".");
        // PAGINA:   REPORTE PACIENTE 
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("tAcM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("tAtxB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
        
        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanPersona> listaFiltro = new ArrayList<>();
        listaFiltro = metodosPersona.filtrar_rpt_pac(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
        
        // PASAR LA LISTA Y LOS DATOS AL JSP 
//            acceso = "rpt_paciente.htm";
//            var_redireccionar = 1;
        request.setAttribute("CR_RP_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CR_RP_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CR_RP_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    } // END METHOD 
    
    
    
    private void filtrar_pag_rpt_mis_agend(HttpSession sesionDatosUsuario, 
        HttpServletRequest request, 
        ModelAgendamiento metodosAgendamiento 
    ) {
        String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("_   _   _filtrar_pag()____ID_PERSONA:  "+idPersona);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        // PAGINA:   REPORTE MIS AGENDAMIENTO 
            System.out.println("-----------------------__FILTRAR__---------------------------");
            String filtro_cbxMostrar = (String) request.getParameter("tAcM"); // cM: combo Mostrar 
            System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
            String filtro_txtBuscar = (String) request.getParameter("tAtxB"); // txB: txt Buscar 
            System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
            String txtFechaIni = (String) request.getParameter("tAtRMAFI"); // tRMAFI : txt reporte mis agendamientos fecha inicio 
            if (txtFechaIni == null || txtFechaIni.isEmpty()) { txtFechaIni = ""; }
            String txtFechaFin = (String) request.getParameter("tAtRMAFF"); // tRMAFF : txt reporte mis agendamientos fecha fin 
            if (txtFechaFin == null || txtFechaFin.isEmpty()) { txtFechaFin = ""; }
//            String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
            String txtMedicoId = (String) request.getParameter("tAcbxAddNewMed"); // MEDICO id 
            if (txtMedicoId == null || txtMedicoId.isEmpty()) { txtMedicoId = ""; }
//            String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
            String txtEspeId = (String) request.getParameter("tAcbxAddNewEspe"); // ESPECIALIDAD id 
            if (txtEspeId == null || txtEspeId.isEmpty()) { txtEspeId = ""; }
//            String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
            String txtClinicaId = (String) request.getParameter("tAcbxAddNewClinica"); // CLINICA id 
            if (txtClinicaId == null || txtClinicaId.isEmpty()) { txtClinicaId = ""; }
//            String checkPacienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
            String txtPacienteId = (String) request.getParameter("tAcbxAddNewCli"); // PACIENTE id 
            if (txtPacienteId == null || txtPacienteId.isEmpty()) { txtPacienteId = ""; }
            // COMBO ESTADO 
            String checkEstadoFiltro = (String) request.getParameter("tAcheck_estado"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
            String PARAM_TXT_ESTADO = (String) request.getParameter("tAcE"); // ESTADO  
            if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = ""; }
            System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
            System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
            System.out.println("---------------__VAR_FILTRAR__----------------");
            System.out.println("_  _CBX_MOSTRAR    : "+filtro_cbxMostrar);
            System.out.println("_  _BUSCAR_NRO_FACT: "+filtro_txtBuscar);
            System.out.println("_  _FECHA_INICIO:    "+txtFechaIni);
            System.out.println("_  _FECHA_FIN   :    "+txtFechaFin);
//            System.out.println("_  _CHECK_MEDICO:    "+checkMedicoFiltro);
            System.out.println("_  _MEDICO_ID   :    "+txtMedicoId);
//            System.out.println("_  _CHECK_ESPECIA:   "+checkEspeFiltro);
            System.out.println("_  _ESPECIALIDAD_ID: "+txtEspeId);
//            System.out.println("_  _CHECK_CLINICA:   "+checkClinicaFiltro);
            System.out.println("_  _CLINICA_ID   :   "+txtClinicaId);
//            System.out.println("_  _CHECK_PACIENTE:  "+checkPacienteFiltro);
            System.out.println("_  _PACIENTE_ID   :  "+txtPacienteId);
            System.out.println("_  _ESTADO_FILTER :  "+PARAM_TXT_ESTADO);
            System.out.println("----------------------------------------------");

//            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR MEDICO 
//            if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO 
//                checkMedicoFiltro = "OFF";
//            } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                txtMedicoId = "";
//            }
            String checkMedicoFiltro = "";
            if (txtMedicoId.isEmpty() || txtMedicoId.equalsIgnoreCase("")) {
                checkMedicoFiltro = "ON";
            } else {
                checkMedicoFiltro = "OFF";
            }
            
//            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR LA ESPECILAIDAD 
//            if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD 
//                checkEspeFiltro = "OFF";
//            } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                txtEspeId = "";
//            }
            String checkEspeFiltro = "";
            if (txtEspeId.isEmpty() || txtEspeId.equalsIgnoreCase("")) {
                checkEspeFiltro = "ON";
            } else {
                checkEspeFiltro = "OFF";
            }
            
//            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLINICA, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLINICA PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLINICA 
//            if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA CLINICA Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLINICA 
//                checkClinicaFiltro = "OFF";
//            } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                txtClinicaId = "";
//            }
            String checkClinicaFiltro = "";
            if (txtClinicaId.isEmpty() || txtClinicaId.equalsIgnoreCase("")) {
                checkClinicaFiltro = "ON";
            } else {
                checkClinicaFiltro = "OFF";
            }
            
//            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR PACIENTE 
//            if (checkPacienteFiltro == null || checkPacienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPERSONA DEL PACIENTE 
//                checkPacienteFiltro = "OFF";
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
//            } else if (checkPacienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                txtPacienteId = "";
//            }
            String checkPacienteFiltro = "";
            if (txtPacienteId.isEmpty() || txtPacienteId.equalsIgnoreCase("")) {
                checkPacienteFiltro = "ON";
            } else {
                checkPacienteFiltro = "OFF";
            }
            // CONTROL DEL CHECK DE ESTADO 
            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
            if (PARAM_TXT_ESTADO.isEmpty() || PARAM_TXT_ESTADO.equalsIgnoreCase("")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
//            if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                checkEstadoFiltro = "ON";
            } else { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//            } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                checkEstadoFiltro = "OFF";
            }
            
            // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
            String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
            System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
            String IDPACIENTE = "";
            // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
            if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                IDPACIENTE = idPersona; // SI FUERA EL PERFIL PACIENTE, ENTONCES NO LE VOY A MOSTRAR EL FILTRO DE PACIENTE Y DIRECTAMENTE UTILIZARE EL IDPERSONA DE LOGEO 
            } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) {
                IDPACIENTE = txtPacienteId; // SI FUERA EL PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES DESDE LA PAGINA VA A FILTRAR POR UN PACIENTE PARA PODER VER LOS AGENDAMIENTOS DE EL, Y EN CASO DE QUE NO FILTRE, ENTONCES LE VA A TRAER VACIO LA GRILLA 
            }
            System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);
            
            // CARGAR LISTA 
            List<BeanFacturaCab> listaFiltro = new ArrayList<>();
            listaFiltro = metodosAgendamiento.filtrar_pagi_rpt_misAgen(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtFechaIni, txtFechaFin, txtEspeId, txtMedicoId, txtClinicaId, IDPACIENTE, IDPERFIL_USER, PARAM_TXT_ESTADO);
            
            // PASAR LA LISTA Y LOS DATOS AL JSP 
//            acceso = "rpt_mis_agen.htm";
//            var_redireccionar = 1;
            request.setAttribute("CR_RMA_CBX_MOSTRAR", filtro_cbxMostrar);
            request.setAttribute("CR_RMA_TXT_BUSCAR", filtro_txtBuscar);
            request.setAttribute("CR_RMA_LISTA_FILTRO", listaFiltro);
            request.setAttribute("CR_RMA_TXT_FILTRAR_FEC_INI", txtFechaIni);
            request.setAttribute("CR_RMA_TXT_FILTRAR_FEC_FIN", txtFechaFin);
            request.setAttribute("CR_RMA_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
            request.setAttribute("CR_RMA_TXT_FILTRAR_MED_ID", txtMedicoId);
            request.setAttribute("CR_RMA_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
            request.setAttribute("CR_RMA_TXT_FILTRAR_ESPE_ID", txtEspeId);
            request.setAttribute("CR_RMA_CHECK_FILTRAR_CLINICA_01", checkClinicaFiltro);
            request.setAttribute("CR_RMA_TXT_FILTRAR_CLINICA_ID", txtClinicaId);
            request.setAttribute("CR_RMA_CHECK_FILTRAR_PACIENTE_01", checkPacienteFiltro);
            request.setAttribute("CR_RMA_TXT_FILTRAR_PACIENTE_ID", txtPacienteId);
            request.setAttribute("CR_RMA_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
            request.setAttribute("CR_RMA_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
            sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
        
        // VARIABLE QUE SE UTILIZA EN CADA CONDICION 
        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    } // END METHOD 
    
    
    
    private void filtrar_pag_rpt_fact(HttpSession sesionDatosUsuario, 
        HttpServletRequest request, 
        ModelFactura metodosFactura 
    ) {
        String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("_   _   _filtrar_pag()____ID_PERSONA:  "+idPersona);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        // PAGINA:   REPORTE FACTURA 
            System.out.println("-----------------------__FILTRAR__---------------------------");
            String filtro_cbxMostrar = (String) request.getParameter("tAcM"); // cM: combo Mostrar 
            System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
            String filtro_txtBuscar = (String) request.getParameter("tAtxB"); // txB: txt Buscar 
            System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

            String txtFechaIni = (String) request.getParameter("tAtFFI"); // tFFI : txt filtrar fecha inicio 
            if (txtFechaIni == null || txtFechaIni.isEmpty()) {
                txtFechaIni = "";
            }
            String txtFechaFin = (String) request.getParameter("tAtFFF"); // tFFF : txt filtrar fecha fin 
            if (txtFechaFin == null || txtFechaFin.isEmpty()) {
                txtFechaFin = "";
            }
//            String checkClienteFiltro = (String) request.getParameter("check_cli"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL CLIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
            String txtClienteId = (String) request.getParameter("tAcbxAddNewCli"); // cliente id 
            if (txtClienteId == null || txtClienteId.isEmpty()) {
                txtClienteId = "";
            }
            System.out.println("------------__VAR_FILTRAR__-----------");
            System.out.println("_  _CBX_MOSTRAR    :   "+filtro_cbxMostrar);
            System.out.println("_  _BUSCAR_NRO_FACT:   "+filtro_txtBuscar);
            System.out.println("_  _FECHA_INICIO :   "+txtFechaIni);
            System.out.println("_  _FECHA_FIN    :   "+txtFechaFin);
//            System.out.println("_  _CHECK_CLIENTE:   "+checkClienteFiltro);
            System.out.println("_  _CLIENTE_ID   :   "+txtClienteId);
            System.out.println("--------------------------------------");

//            // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//            if (checkClienteFiltro == null || checkClienteFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//                checkClienteFiltro = "OFF";
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
//            } else if (checkClienteFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//                txtClienteId = "";
//            }
            String checkClienteFiltro = "";
            if (txtClienteId.isEmpty() || txtClienteId.equalsIgnoreCase("")) {
                checkClienteFiltro = "ON";
            } else {
                checkClienteFiltro = "OFF";
            }

            String varFiltroEstadoCabFact = ""; // LO DEJO VACIO YA QUE QUIERO QUE EL FILTRO ME MUESTRE TODOS LOS ESTADO, PERO SI QUISIESE QUE ME MUESTRE SOLO LOS COBRADOS, ENTONCES AHI SI CARGARIA EL ESTADO 
            String txt_IDCLINICA = ""; // VARIABLE QUE LA DEJO VACIO PARA QUE EN EL METODO SE CARGUE CON EL IDCLINICA DE LOGEO, PERO EN CASO DE QUE HAYA UN FILTRO EN LA PAGINA, ENTONCES AHI SE CARGARIA ESTA VARIABLE Y EL CODIGO PARA HACERLO, Y EN EL METODO FILTRARIA POR ESA IDCLINICA Y NO POR LA DE LOGEO 

            // CONTROL DEL PERFIL PARA FILTRAR POR LAS FACTURAS DEL PACIENTE O PARA FILTRAR POR TODAS LAS FACTURAS 
            String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
            System.out.println(".   ____ID_PERFIL_USER:        :"+IDPERFIL_USER);
            String IDPACIENTE = "";
            // CONTROLO EL PERFIL PARA PASARLE VACIO LA VARIABLE O LE CARGO CON EL IDPERSONA DE LOGEO, SI FUERA PACIENTE EL PERFIL ENTONCES SI LE CARGARIA LA VARIABLE CON EL IDPERSONA DE LOGEO 
            if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) {
                IDPACIENTE = idPersona;
            }
            System.out.println(".   ____ID_IDPACIENTE:        :"+IDPACIENTE);

            // CARGAR LISTA 
            List<BeanFacturaCab> listaFiltro = new ArrayList<>();
            listaFiltro = metodosFactura.filtrar_paginacion_rpt_fact(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtFechaIni, txtFechaFin, txtClienteId, varFiltroEstadoCabFact, txt_IDCLINICA, IDPACIENTE);

            // PASAR LA LISTA Y LOS DATOS AL JSP 
//            acceso = "rpt_factura.htm";
//            var_redireccionar = 1;
            request.setAttribute("CR_RF_CBX_MOSTRAR", filtro_cbxMostrar);
            request.setAttribute("CR_RF_TXT_BUSCAR", filtro_txtBuscar);
            request.setAttribute("CR_RF_LISTA_FILTRO", listaFiltro);
            request.setAttribute("CR_RF_TXT_FILTRAR_FEC_INI", txtFechaIni);
            request.setAttribute("CR_RF_TXT_FILTRAR_FEC_FIN", txtFechaFin);
            request.setAttribute("CR_RF_CHECK_FILTRAR_CLIE_01", checkClienteFiltro);
            request.setAttribute("CR_RF_TXT_FILTRAR_CLIE_ID", txtClienteId);
        
        // VARIABLE QUE SE UTILIZA EN CADA CONDICION 
        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    } // END METHOD 
    
    
    
    private void filtrar_pag_rpt_cta_pac(HttpSession sesionDatosUsuario, 
        HttpServletRequest request, 
        ModelCuentaCliente metodosCuentaCliente 
    ) {
        // PAGINA:   REPORTE CUENTA CLIENTE / PACIENTE 
        System.out.println("-----------------------__FILTRAR__---------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("tAcM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("tAtxB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
//        String checkClinicaFiltro = (String) request.getParameter("tAcheck_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtClinicaId = (String) request.getParameter("tAcbxAddNewClinica"); // clinica id 
        if (txtClinicaId == null || txtClinicaId.isEmpty()) {
            txtClinicaId = "";
        }
//        System.out.println("_   CHECK_CLINICA:   "+checkClinicaFiltro);
        System.out.println("_   CLINICA_ID   :   "+txtClinicaId);

//        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
//        if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
//              checkClinicaFiltro = "OFF";
//        } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
//              txtClinicaId = "";
//        }
        String checkClinicaFiltro = "";
        if (txtClinicaId.isEmpty() || txtClinicaId.equalsIgnoreCase("")) {
            checkClinicaFiltro = "ON";
        } else {
            checkClinicaFiltro = "OFF";
        }

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanCuentaCliente> listaFiltro = new ArrayList<>();
        listaFiltro = metodosCuentaCliente.filtrar_reporte_cta_pac(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtClinicaId);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "rpt_cta_paciente.htm";
//        var_redireccionar = 1;
        request.setAttribute("CR_RCC_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CR_RCC_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CR_RCC_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CR_RCC_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
        request.setAttribute("CR_RCC_TXT_FIL_ID_CLI", txtClinicaId);
        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    } // END METHOD 
    
    
    
    private void filtrarFichaAtencionPacNutri(HttpSession sesionDatosUsuario, 
        HttpServletRequest request, 
        ModelFichaAtencionPacNutri metodosFichaAtencionPacNutri 
    ) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
        listaFiltro = metodosFichaAtencionPacNutri.filtrar_rpt_est(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "rpt_est.htm";
//        var_redireccionar = 1;
        request.setAttribute("CR_RE_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CR_RE_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CR_RE_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    
    public String goVerHistoricoFichasPaciente(HttpServletRequest request, ModelFichaAtencionPacNutri metodosFicAtePacNutri) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("---------------------__VER_HISTORICO__--------------------------");
        String acceso = "";
        String txtIdPac = (String) request.getParameter("tIP");
        if (txtIdPac == null || txtIdPac.isEmpty()) {
            txtIdPac = (String) request.getAttribute("TXT_IDPACIENTE");
        }
        String NAME_PACIENTE = (String) request.getParameter("tCRPNC");
        if (NAME_PACIENTE.isEmpty()) {
            NAME_PACIENTE = (String) request.getAttribute("TXT_PAC_NAME");
        }
        System.out.println("_  __TXT_ID_PACIENTE:     :"+txtIdPac);
        System.out.println("_  __TXT_PACIENTE_NAME:   :"+NAME_PACIENTE);

        // RECUPERAR LA LISTA CON LAS FICHAS DEL PACIENTE 
        List<BeanFichaAtePaciente> listaFichasPac = new ArrayList<>();
        listaFichasPac = metodosFicAtePacNutri.getAllListasFichasPac(txtIdPac);

        acceso = "ver_his_fic.htm";
//        var_redireccionar = 1;
        request.setAttribute("CR_RFAN_TXT_IDPACIENTE", txtIdPac);
        request.setAttribute("CR_RFAN_TXT_NAME_PACIENTE", NAME_PACIENTE);
//                    request.setAttribute("CR_RFAN_TXT_PAC_NOM", txtPacNombre);
//                    request.setAttribute("CR_RFAN_TXT_PAC_APE", txtPacApellido);
        request.setAttribute("CR_RFAN_LISTA_HISTORICO_PAC", listaFichasPac);
        // variable de los filtros 
        request.setAttribute("CR_RFAN_TXT_FILTRAR_FEC_INI", "");
        request.setAttribute("CR_RFAN_TXT_FILTRAR_FEC_FIN", "");
        request.setAttribute("CR_RFAN_TXT_FIL_ESTADO", "");
//        // variable que uso en el controlador de la ficha para saber a que pagina redireccionar cuando se presione el boton para volver atras 
//        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "HISTORICO_PACIENTE");
        
        return acceso;
    }
    
    
    
} // END CLASS 