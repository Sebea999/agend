/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanAgendamiento;
import com.agend.javaBean.BeanPlanHorario;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelAgendamiento;
import com.agend.modelo.ModelFichaAtencionPac;
import com.agend.modelo.ModelFichaAtencionPacNutri;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelPlanHorario;
import com.agend.modelo.ModelRef_Clinica;
import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="AgendamientoController", urlPatterns="/CASrv") // CONTROLLER AGENDAMIENTO Servlet 
public class ControllerAgendamiento extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/agend")
    public ModelAndView agendamiento(HttpServletRequest request, HttpServletResponse response) {
//    public ModelAndView agendamiento(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        System.out.println("[+]-----__1__---------CONTROLLER__AGENDAMIENTO--------------MODEL_AND_VIEW-------");
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("[*]_CA____ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        if (SESION_IDUSER == null) {
            System.out.println("[-] sesion-id-user var is null - ");
//            SESION_IDUSER = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
//            System.out.println("[*] cookie value return the id-user is = "+SESION_IDUSER);
        } else {
            System.out.println("[+] sesion-id-user var is not null - it value is = "+SESION_IDUSER);
        }
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println("[*]_CA____ID_PERFIL_USER:    :"+IDPERFIL_USER);
        if (IDPERFIL_USER == null) {
            System.out.println("[-] id-perfil-user var is null - ");
//            IDPERFIL_USER = modeloIniSes.getCookie(request, "IDPERFIL").getValue();
//            System.out.println("[*] cookie value return the id-perfil is = "+IDPERFIL_USER);
        } else {
            System.out.println("[+] id-perfil-user var is not null - it value is = "+IDPERFIL_USER);
        }
        
//        String varMenuMostrar="0"; // USO ESTA VARIABLE PARA CARGAR EL VALOR DE: "JSP_MOSTRAR_BARRA_MENU" COMO LO HAGO EN EL IF PERO SI INSTANCIAR EL setCookie COMO LO HAGO CON LA SESION EN CADA IF 
        String paginaMav = "menu_principal";
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
//        if (IDPERFIL_USER.equals("4")) { // 4 : PACIENTE 
            sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", "");
//            response = modeloIniSes.setCookie(response, "IDAGENDAMIENTO", "");
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgendAdd_Paciente", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            varMenuMostrar = "1";
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
//        } else if (IDPERFIL_USER.equals("1") || IDPERFIL_USER.equals("3")) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgend_Secre", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            varMenuMostrar = "0";
//        } else { // PARA CUALQUIER OTRO USUARIO CON OTRO PERFIL QUE NO ESTE DECLARADO EN LOS IF LE VOY A REDIRECCIONAR AL MENU PRINCIPAL NOMAS 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "menu_principal", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgend_Secre", request);
        System.out.println("pagina_mav:     "+paginaMav);
//        response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", varMenuMostrar);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        // COMBO BOX DE MEDICO Y ESPECIALIDAD 
        request.setAttribute("CA_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CA_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESPECIALIDAD Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CA_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        // VARIABLE DE LA SESION QUE LA UTILIZO COMO BANDERA PARA LA PAGINACION 
        
        return mav;
    }
    
    // [OBS] METODO ANTERIOR QUE LO TENIA A MODO DE EJEMPLO UTILIZANDO SESSION Y COOKIES - SESSION DE PRIMERA INSTANCIA Y CONTROLANDO CADA VALOR PARA QUE SI DEVUELVE NULL BUSQUE EL MISMO VALOR PERO EN LA COOKIE - COMENTO ESTE METODO PORQUE EN LA VPS ME DABA ERROR LA SESION Y EN LOCAL ME DABA ERROR LA COOKIE (Y VICEVERSA, EN LA VPS ME ANDABA LA COOKIE Y EN LOCAL LA SESSION) ACA INTENTA IMPLEMENTAR LAS COOKIES A DUO DE LA SESION 
//    @RequestMapping("/agend")
//    public ModelAndView agendamiento(@CookieValue("JCOOKIEIDU") String IDUSERLOGIN, HttpServletRequest request, HttpServletResponse response) {
////    public ModelAndView agendamiento(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        System.out.println("------------------CONTROLER-AGENDAMIENTO-------------(cookie)--------");
//        System.out.println("-----ID_USER_LOGIN =' "+IDUSERLOGIN+" '=");
//        System.out.println(".....................................................................");
//        
//        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
//        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
//        
//        System.out.println("-----__1__---------CONTROLLER__AGENDAMIENTO--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _CA__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        if (SESION_IDUSER == null) {
//            System.out.println("[-] sesion-id-user var is null - ");
//            SESION_IDUSER = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
//            System.out.println("[*] cookie value return the id-user is = "+SESION_IDUSER);
//        } else {
//            System.out.println("[+] sesion-id-user var is not null - it value is = "+SESION_IDUSER);
//        }
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
//        if (IDPERFIL_USER == null) {
//            System.out.println("[-] id-perfil-user var is null - ");
//            IDPERFIL_USER = modeloIniSes.getCookie(request, "IDPERFIL").getValue();
//            System.out.println("[*] cookie value return the id-perfil is = "+IDPERFIL_USER);
//        } else {
//            System.out.println("[+] id-perfil-user var is not null - it value is = "+IDPERFIL_USER);
//        }
//        
//        String varMenuMostrar="0";
//        String paginaMav = "menu_principal";
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // 4 : PACIENTE 
////        if (IDPERFIL_USER.equals("4")) { // 4 : PACIENTE 
//            sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", "");
//            response = modeloIniSes.setCookie(response, "IDAGENDAMIENTO", "");
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgendAdd_Paciente", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            varMenuMostrar = "1";
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
////        } else if (IDPERFIL_USER.equals("1") || IDPERFIL_USER.equals("3")) { //  1 : ADMIN  -  3 : SECRETARIO 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgend_Secre", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            varMenuMostrar = "0";
////        } else { // PARA CUALQUIER OTRO USUARIO CON OTRO PERFIL QUE NO ESTE DECLARADO EN LOS IF LE VOY A REDIRECCIONAR AL MENU PRINCIPAL NOMAS 
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "menu_principal", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagAgend_Secre", request);
//        System.out.println("pagina_mav:     "+paginaMav);
//        response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", varMenuMostrar);
//        
//        mav.setViewName(paginaMav);
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        // COMBO BOX DE MEDICO Y ESPECIALIDAD 
//        request.setAttribute("CA_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CA_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESPECIALIDAD Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CA_CHECK_FILTRAR_ESTADO", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE ESTADO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        // VARIABLE DE LA SESION QUE LA UTILIZO COMO BANDERA PARA LA PAGINACION 
//        
//        return mav;
//    }
    
    
    
    @RequestMapping("/add_agend")
    public ModelAndView add_agendamiento(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__2__MAV___ID_AGENDAMIENTO:     "+ID_AGENDAMIENTO);
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_AGENDAMIENTO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CA__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        // OBSERVACION: 
        // LA PAGINA add_agend SOLO VAN A INGRESAR EL SECRETARIO Y EL ADMINISTRADOR, PERO LE COLOCO EL CONTROL PORQUE EN LA "accion" DEL CALENDARIO AL SELECCIONAR UN DIA, EN EL DO_POST TENGO PARA QUE REDIRECCIONE A "agend" PERO SI ES ADMIN O SECRETARIO ME MANDA A LA PAGINA PRINCIPAL Y PARA EVITAR ESO VOY A REDIRECCIONAR A ESTA PAGINA Y DESDE ACA NOMAS LE AGREGO ESTE CONTROL PARA QUE SI ES PACIENTE LE REDIRECCIONO A LA PAGINA QUE CORRESPONDE Y NO LE LLEGUE A MOSTRAR LA PAGINA QUE SOLO DEBERIA DE VER EL SECRETARIO Y EL ADMIN 
        String paginaMav = "menu_principal";
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
//        if (IDPERFIL_USER.equals("4")) { // 4 : PACIENTE 
            sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", "");
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendAdd_Paciente", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
//        } else if (IDPERFIL_USER.equals("1") || IDPERFIL_USER.equals("3")) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendAdd_Secre", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendAdd_Secre", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/ver_agend")
    public ModelAndView ver_agendamiento(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__3__MAV___ID_AGENDAMIENTO:     "+ID_AGENDAMIENTO);
        
        System.out.println("-----__3__--------CONTROLLER__VER_AGENDAMIENTO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CA__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        // SI EL PERFIL ES PACIENTE ENTONCES LE MUESTRO DATOS DE SU AGENDAMIENTO NOMAS, Y SI FUERA ADMINISTRADOR O SECRETARIO, ENTONCES LE MOSTRARIA LA PAGINA DONDE SE MUESTRA LOS AGENDAMIENTOS DEL MEDICO 
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer_AnularView", request);
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/ver_agen") // DIRECCION ESPECIAL PARA VER EL AGENDAMIENTO QUE SE QUIERE ANULAR 
    public ModelAndView ver_agendamiento_anular(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__4__MAV___ID_AGENDAMIENTO:     "+ID_AGENDAMIENTO);
        
        System.out.println("-----__4__--------CONTROLLER__VER_AGENDAMIENTO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CA__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        // SI EL PERFIL ES PACIENTE ENTONCES LE MUESTRO DATOS DE SU AGENDAMIENTO NOMAS, Y SI FUERA ADMINISTRADOR O SECRETARIO, ENTONCES LE MOSTRARIA LA PAGINA DONDE SE MUESTRA LOS AGENDAMIENTOS DEL MEDICO 
        if (modeloPerfil.isPCAnularAgendamiento(IDPERFIL_USER) == true) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer_Anular", request);
        }
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer_Anular", request);
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer_AnularView", request);
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER)) { //  1 : ADMIN  -  3 : SECRETARIO 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer_Anular", request);
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendVer", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/add_agend_fph")
    public ModelAndView add_agend_filter_ph(HttpServletRequest request) { // PAGINA PARA FILTRAR Y VER LOS PLANES HORARIO 
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__4__MAV___ID_AGENDAMIENTO:     "+ID_AGENDAMIENTO);
        
        System.out.println("-----__4__--------CONTROLLER__ADD_AGENDAMIENTO_FILTER_PHM------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CA__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuAgendamiento(IDPERFIL_USER) == true) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendAdd_SecreFilter", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagAgendAdd_SecreFilter", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
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
        ModelAgendamiento metodos = new ModelAgendamiento();
        ModelPlanHorario metodosPH = new ModelPlanHorario();
        ModelRef_Clinica metodosClinica = new ModelRef_Clinica();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int var_redireccionar = 0;
        String acceso = "agend.htm";
        String accion = request.getParameter("accion");
        String accionPag = request.getParameter("accionPag"); // accion para la paginacion 
        String accionSecre = request.getParameter("accionSec"); // accion para la accion que haga el secretario  
        String idPersona, idAgend="";
        
        try {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   --------------CONTROLADOR DE EVENTOS DE AGENDAMIENTO---------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__ACCION:       "+accion);
            System.out.println("__ACCION_PAGINACION:       "+accionPag);
            System.out.println("__ACCION_SECRETARIO:       "+accionSecre);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if(accion != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_        ___ACCION___        _");
                System.out.println(".");System.out.println(".");
                if( accion.equalsIgnoreCase("NO_AGENDAR") ) {
                    System.out.println("---------------------____NO_AGENDAR____--------------------------");
                    // SI NO QUIERE AGENDAR ENTONCES LE REDIRECCIONO A LA PAGINA PARA QUE VUELVA A CARGAR LOS DATOS PARA PODER AGENDAR A ALGUIEN 
                    acceso = "add_agend.htm";
                    var_redireccionar = 1;
                    System.out.println("_   _____VERIFY__AGEND__EXIST__:    :"+String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")));
                    
//                    String MENSAJE=null,TIPO_MENSAJE="1";
//                    if(metodos.verifyAgendExist(sesionDatosUsuario, "18", "1", "5") == true 
//                        && (
//                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND"))==null) || 
//                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("null"))// || 
////                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("0"))
//                            )
//                      ) { // VALIDACION PARA CONTROLAR SI ES QUE LA CABECERA DEL AGENDAMIENTO SOBRE EL MEDICO YA EXISTE, Y SI FUERA ASI CONTROLAR QUE NO SE PASE EL LIMITE DE AGENDAMIENTO POR MEDICO, Y EN CASO DE QUE YA ALCANZO EL LIMITE LE DOY LA OPCIÓN DE ABRIR OTRA CABECERA PARA EL MEDICO 
//                        MENSAJE = "Ya se alcanzo el limite de agendamientos disponibles para este médico.";
//                        TIPO_MENSAJE = "2";
//                        sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND","1"); // PASO UNA VARIABLE CON UN VALOR DISTINTO AL NULL Y CERO PARA NO VOLVER A ENTRAR A ESTA VALIDACION UNA VEZ QUE EL SECRETARIO O EL USUARIO HAYA VUELTO A CONFIRMAR QUE QUIERE ABRIR UNA NUEVA CABECERA PARA EL MEDICO Y AL FINAL DEL EVENTO LE CARGO NULL A ESTA VARIABLE PARA QUE NO SE MANTENGA CON EL VALOR ANTERIOR 
//                    } // AL CONFIRMAR EL GUARDAR REINICIAR LA VARIABLE "CA_VAR_VERIFY_CAB_AGEND" 
//                        else if (
//                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("1"))
//                        ) {
//                            MENSAJE = "PRUEBA APROBADA";
//                            TIPO_MENSAJE = "1";
//                            sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND",null);
//                    }
                    sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND",null);
//                    System.out.println(".");System.out.println(".");
//                    System.out.println(".");System.out.println(".");
//                    request.setAttribute("CA_MENSAJE", MENSAJE);
//                    request.setAttribute("CA_TIPO_MENSAJE", TIPO_MENSAJE);
                    
                } else if (accion.equalsIgnoreCase("Ver Atención") || accion.equalsIgnoreCase("Ver Atencion")) {
                    System.out.println("---------------------__VER_ATENCION__--------------------------");
                    // FICHA DE ATENCION MODIFICADO - NUTRI - ----------------------------------------------------------------------
                    ModelFichaAtencionPacNutri metodosAtencion = new ModelFichaAtencionPacNutri();
                    ControllerFichaAtencionPacNutri controlador_atencion = new ControllerFichaAtencionPacNutri();
                    acceso = controlador_atencion.ver_ficha_atencion_paciente(sesionDatosUsuario, request, metodosAtencion, accionPag, var_redireccionar, acceso);
                    // FICHA DE ATENCION PRIMERO -----------------------------------------------------------------------------------
//                    ModelFichaAtencionPac metodosAtencion = new ModelFichaAtencionPac();
//                    ControllerFichaAtencionPac controlador_atencion = new ControllerFichaAtencionPac();
//                    acceso = controlador_atencion.ver_ficha_atencion_paciente(sesionDatosUsuario, request, metodosAtencion, accionPag, var_redireccionar, acceso);
                    var_redireccionar = 1;
                    
                } else if (accion.equalsIgnoreCase("Cerrar Agendamiento")) {
                    System.out.println("---------------------__CERRAR_AGENDAMIENTO__--------------------------");
                    var_redireccionar = 1;
                    acceso = "agend.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    String idAgendamiento = request.getParameter("tI");
                    
                    String MENSAJE = null, TIPO_MENSAJE="";
                    TIPO_MENSAJE = metodos.cerrarAgendamiento(idAgendamiento);
                    if (TIPO_MENSAJE.equals("2")) {
                        MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                    } else {
                        MENSAJE = "Se ha cerrado correctamente la operación.";
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    request.setAttribute("CA_MENSAJE", MENSAJE);
                    request.setAttribute("CA_TIPO_MENSAJE", TIPO_MENSAJE);
                    
                } else if (accion.equalsIgnoreCase("Add Agendamiento")) {
                    System.out.println("---------------------__AÑADIR_AGENDAMIENTO__--------------------------");
                    var_redireccionar = 1;
                    acceso = "add_agend.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    
                } else if (accion.equalsIgnoreCase("Ver")) {
                    System.out.println("---------------------__VER__--------------------------");
                    var_redireccionar = 1;
                    acceso = "ver_agend.htm";
                    idAgend = ver_agendamiento(sesionDatosUsuario, request, acceso, var_redireccionar, idAgend);
                    sesionDatosUsuario.setAttribute("AGEND_BOTON_VOLVER_ATRAS", "0"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOLVER ATRAS" DE LA PAGINA DE VER_AGENDAMIENTO 
//                    System.out.println("---------------------__VER_AGENDAMIENTO__--------------------------");
//                    idAgend = (String) request.getParameter("tI");
//                    System.out.println("_doPost__ID_AGENDAMIENTO:     "+idAgend);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_agend.htm";
//                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
                    
                } else if(accion.equalsIgnoreCase("AGENDAR") || accion.equalsIgnoreCase("Agendar") || accion.equalsIgnoreCase("AgendarSi")) {
                    System.out.println("---------------------__AGENDAR / GUARDAR__------------------- __PACIENTE__ -------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    System.out.println("__ID_AGENDAMIENTO :   "+idAgend);
                    int verify_new_vali_limit = 0; // OBS.: VARIABLE QUE UTILIZO PARA PODER ACTIVAR TAMBIEN EL CONTROL DE LA CANT MAX DE AGENDAMIENTO, YA QUE SE PUEDE INGRESAR A ESTE IF A TRAVES DE "SI" Y "AGENDAR" Y SI ENTRA POR MEDIO DEL BTN DE AGENDAR ENTOCES HAY SI VOLVERIA A CONTROLAR 
                    if (accion.equalsIgnoreCase("AgendarSi")) {
                        System.out.println(".");System.out.println(".");
                        System.out.println("_   _   _BTN_SI_AGENDAR_    _   __PACIENTE__");
                        System.out.println(".");System.out.println(".");
                        verify_new_vali_limit = 1;
                    }
                    
                    // RECUPERAR LOS DATOS 
//                    String PACIENTE_ID = ;
//                    String PAC_APELLIDO = ;
//                    String PAC_NOMBRE = ;
//                    String PAC_CINRO = ;
                    String AGEN_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    if (AGEN_IDESPECIALIDAD == null || AGEN_IDESPECIALIDAD.isEmpty()) { varValiVacio++; AGEN_IDESPECIALIDAD=""; }
                    String AGEN_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    if (AGEN_MOTIVO_CONSULTA == null || AGEN_MOTIVO_CONSULTA.isEmpty()) { varValiVacio++; AGEN_MOTIVO_CONSULTA=""; }
                    String AGEN_IDMEDICO = (String) request.getParameter("cMe");
                    if (AGEN_IDMEDICO == null || AGEN_IDMEDICO.isEmpty()) { varValiVacio++; AGEN_IDMEDICO=""; }
                    String AGEN_FECHA = (String) request.getParameter("tFA");
                    if (AGEN_FECHA == null || AGEN_FECHA.isEmpty()) { varValiVacio++; AGEN_FECHA=""; }
                    String AGEN_HORA = (String) request.getParameter("tHA");
                    if (AGEN_HORA == null || AGEN_HORA.isEmpty()) { varValiVacio++; AGEN_HORA=""; }
                    String AGEN_IDCLINICA = (String) request.getParameter("cCli"); // COMBO BOX 
                    if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                        AGEN_IDCLINICA = (String) request.getParameter("tAIC"); // CAMPO DE TEXTO 
                        if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                            varValiVacio++;
                            AGEN_IDCLINICA="";
                        }
                    }
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    String AGEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
    //                String PAC_USU_ESTADO = (String) request.getParameter("cEs");
                    if (AGEN_ESTADO == null || AGEN_ESTADO.isEmpty()) { varValiVacio++; AGEN_ESTADO=""; }
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-       AGEN_IDCLINICA: "+AGEN_IDCLINICA);
                    System.out.println("-       CANT_CLINICA:   "+CANT_IDCLINICA);
                    System.out.println("-       AGEN_ID_ESPE:   "+AGEN_IDESPECIALIDAD);
                    System.out.println("-       AGEN_ID_MEDI:   "+AGEN_IDMEDICO);
                    System.out.println("-       AGEN_MOT_CON:   "+AGEN_MOTIVO_CONSULTA);
                    System.out.println("-       AGEN_FECHA:     "+AGEN_FECHA);
                    System.out.println("-       AGEN_HORA:      "+AGEN_HORA);
                    System.out.println("-       AGEN_ESTADO:    "+AGEN_ESTADO);
                    System.out.println("-   -----------------------------------");
                    System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                    
                    // VALIDACIONES 
                    String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
                    if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                        MENSAJE = "No puede dejar campos vacios.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlFechaAgenDia(modeloIniSes, AGEN_FECHA) == true) { // VALIDACION PARA CONTROLAR LA FECHA DE AGENDAMIENTO PARA QUE ESTA NO SEA MENOR A LA FECHA DE HOY 
                        MENSAJE = "No puede agendarse en una fecha pasada.";
//                        MENSAJE = "No puede agendarse con una fecha menor a la fecha de hoy.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlConfigAgendCli(AGEN_IDCLINICA) == true) { // VALIDACION PARA CONTROLAR QUE LA CLINICA TENGA UNA CONFIGURACIÓN PARA LOS AGENDAMIENTOS 
                        MENSAJE = "La clínica no cuenta con una configuración de agendamiento activa, por lo tanto no se puede hacer agendamientos.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlDiaPHMedico(sesionDatosUsuario, modeloIniSes, AGEN_IDMEDICO, AGEN_FECHA, AGEN_HORA, AGEN_IDCLINICA) == true) { // VALIDACION PARA CONTROLAR SI LA FECHA Y LA HORA DE AGENDAMIENTO ENTRAN DENTRO DEL HORARIO DEL MEDICO 
                        metodos.obtenerHorarioDiaMed(sesionDatosUsuario, modeloIniSes, AGEN_IDMEDICO, AGEN_FECHA, AGEN_IDCLINICA); // METODO PARA CARGAR LAS VARIABLES DE LA SESION QUE UTILIZARE PARA CARGAR EL MENSAJE Y DARLE INFORMACION DEL HORARIO DEL MEDICO 
                        String PARAM_DIAS="", PARAM_DESDE="", PARAM_HASTA="";
                        String SEGUNDO_MENSAJE = "";
                        // VALIDACION PARA MOSTRAR EL SEGUNDO MENSAJE 
                        if (sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS") == null) { // SI SE ENCUENTRA VACIO ENTONCES ES PORQUE ESE DIA NO ESTA DISPONIBLE 
                            SEGUNDO_MENSAJE="";
                        } else { // EN CASO DE QUE NO DEVUELVA NULL ES PORQUE ESTA PERO DENTRO DE OTRO HORARIO Y CARGARE ESOS DATOS PARA MOSTRAR EN EL MENSAJE 
                            PARAM_DIAS = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS");
                            PARAM_DESDE = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DESDE");
                            PARAM_HASTA = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_HASTA");
                            // DEPENDIENDO DE SI SE OBTUVO UN VALOR VOY A MOSTRAR EL MENSAJE DE UNA FORMA O DE OTRA PARA NO MOSTRARLE EL MENSAJE CON DATOS VACIOS 
                            if ((PARAM_DESDE == null || PARAM_DESDE.isEmpty()) || (PARAM_HASTA == null || PARAM_HASTA.isEmpty())) {
//                                metodos.obtenerDiasAteMed(sesionDatosUsuario, AGEN_IDMEDICO); // METODO QUE UTILIZO PARA CARGAR LA VARIABLE DE PARAM_DIAS CON LOS DIAS DISPONIBLES QUE TIENE EL MEDICO YA QUE EL DIA QUE SELECCIONO NO SE ENCUENTRA DENTRO DEL HORARIO DE ATENCION DEL MEDICO 
//                                PARAM_DIAS = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS"); // VUELVO A INVOCARLE YA QUE EL METODO VOLVIO A CARGAR LA VARIABLE EN LA SESION 
//                                SEGUNDO_MENSAJE = "El médico se encuentra disponible los días ("+PARAM_DIAS+").";
                                SEGUNDO_MENSAJE = null;
                            } else {
                                SEGUNDO_MENSAJE = "(Los días "+PARAM_DIAS+" el médico se encuentra de "+PARAM_DESDE+" hasta "+PARAM_HASTA+")";
                            }
                            sesionDatosUsuario.setAttribute("CA_SEGUNDO_MENSAJE", SEGUNDO_MENSAJE);
                        }
                        // OBSERVACION: SI LE CONCATENO EL SEGUNDO MENSAJE AL MENSAJE, JAVA ME VA A COLOCAR UNO ALADO DE OTRO Y ASI EL MENSAJE SE HACE LARGO Y NO LE COLOCA DEBAJO O NO LE DA UN SALTO DE LINEA POR ESO LO HAGO DE ESTA MANERA Y LE PASO EL SEGUNDO MENSAJE A LA SESION PARA DESDE EL JSP RECIBIR Y CARGAR EN OTRA ETIQUETA HTML PARA DARLE UN SALTO DE LINEA Y SE MUESTRE ESTETICAMENTE EN LA PAGINA 
                        MENSAJE = "El horario seleccionado no se encuentra dentro del horario de atención del médico.\n";
//                        MENSAJE = "El día seleccionado no se encuentra dentro del horario de atención del médico.\n";
                        TIPO_MENSAJE = "2";
                        
                    } else if (metodos.verifyAgendExist(sesionDatosUsuario, AGEN_IDMEDICO, AGEN_IDESPECIALIDAD, AGEN_IDCLINICA, modeloIniSes.convertirFechaYMD(AGEN_FECHA)) == true 
                        && (
                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND"))==null) || 
                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("null")) || 
//                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("0"))
                            (verify_new_vali_limit == 0)
                            )
                        ) { // VALIDACION PARA CONTROLAR SI ES QUE LA CABECERA DEL AGENDAMIENTO SOBRE EL MEDICO YA EXISTE, Y SI FUERA ASI CONTROLAR QUE NO SE PASE EL LIMITE DE AGENDAMIENTO POR MEDICO, Y EN CASO DE QUE YA ALCANZO EL LIMITE LE DOY LA OPCIÓN DE ABRIR OTRA CABECERA PARA EL MEDICO 
                        MENSAJE = "Ya se alcanzo el limite de agendamientos disponibles para este médico.";
                        TIPO_MENSAJE = "2";
                        sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND","1"); // PASO UNA VARIABLE CON UN VALOR DISTINTO AL NULL Y CERO PARA NO VOLVER A ENTRAR A ESTA VALIDACION UNA VEZ QUE EL SECRETARIO O EL USUARIO HAYA VUELTO A CONFIRMAR QUE QUIERE ABRIR UNA NUEVA CABECERA PARA EL MEDICO Y AL FINAL DEL EVENTO LE CARGO NULL A ESTA VARIABLE PARA QUE NO SE MANTENGA CON EL VALOR ANTERIOR 
                        
                    } else if (metodos.ctrlIntervMinEntreAgend(sesionDatosUsuario, AGEN_IDCLINICA, AGEN_IDMEDICO, AGEN_IDESPECIALIDAD, modeloIniSes.convertirFechaYMD(AGEN_FECHA), AGEN_HORA) == true) { // VALIDACION PARA CONTROLAR EL INTERVALO DE TIEMPO QUE HAY ENTRE LOS AGENDAMIENTOS Y QUE SE RESPETE LA CONFIGURACION, EN CASO DE QUE TENGA UNA CANTIDAD EN MINUTOS PARA DIVIDIR LOS AGENDAMIENTOS 
                        TIPO_MENSAJE = "2";
//                        String DIFERENCIA_MIN = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_DIFERENCIA_MIN");
                        String INTERVALO_MIN = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_MIN_ENTRE_AGEND");
                        String HORA_AGEN_DET = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_HORA_AGEND_DET");
                        MENSAJE = "Ya existe un agendamiento a las "+HORA_AGEN_DET+"hs., y la diferencia de tiempo debe de ser de "+INTERVALO_MIN+"min. para poder agendarse.";
                        
                    } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                        // CABECERA 
                        String OA_IDAGENDAMIENTO = idAgend;
                        String OA_IDCLINICA = AGEN_IDCLINICA;
//                        String OA_IDCLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");
                        String OA_IDMEDICO = AGEN_IDMEDICO;
                        String OA_IDESPECIALIDAD = AGEN_IDESPECIALIDAD;
                        String OA_IDPERSONA = ""; // no uso 
                        String OA_FECHA = ""; // no uso 
                        String OA_TURNO = ""; // no uso 
                        String OA_DESDE = ""; // no uso 
                        String OA_HASTA = ""; // no uso 
                        String OA_ESTADO = "A";
                        String OA_USU_ALTA = idPersona;
                        String OA_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                        // DETALLE 
                        String OAD_ITEM = "";
                            String FECHA_AGEN_FORM = modeloIniSes.convertirFechaYMD(AGEN_FECHA);
                            System.out.println("_   _FECHA:  :"+FECHA_AGEN_FORM);
                            System.out.println("_       _FECHA_ANHO: :"+ modeloIniSes.getDatoFecha(1, FECHA_AGEN_FORM));
                            System.out.println("_       _FECHA_MES:  :"+ modeloIniSes.getDatoFecha(2, FECHA_AGEN_FORM));
                            System.out.println("_       _FECHA_DIA:  :"+ modeloIniSes.getDatoFecha(3, FECHA_AGEN_FORM));
                        String ANHO_FORM = modeloIniSes.getDatoFecha(1, FECHA_AGEN_FORM);
                        String MES_FORM = modeloIniSes.getDatoFecha(2, FECHA_AGEN_FORM);
                        String DIA_FORM = modeloIniSes.getDatoFecha(3, FECHA_AGEN_FORM);
                        String OAD_FECHA_AGEN = (ANHO_FORM+"-"+MES_FORM+"-"+DIA_FORM);
                        String OAD_HORA = AGEN_HORA;
                        String OAD_IDPACIENTE = idPersona;
                        String OAD_NROPACIENTEFICHA = "";
                        String OAD_MOTIVO = "";
                        String OAD_SEGUROMED = "";
                        String OAD_IDSEGUROMED = "0";
                        String OAD_VISITATIPO = "";
                        String OAD_COMENTARIO = metodos.traer_desc_agend(modeloIniSes, OA_IDCLINICA, OAD_FECHA_AGEN, OAD_HORA);
                            if (OAD_COMENTARIO == null || OAD_COMENTARIO.isEmpty()) { // EN CASO DE QUE ESTE NULO, YA SEA PORQUE NO HAY UNA CONFIGURACION PARA EL IDCLINICA O NO HAYA NINGUNO ACTIVO, ENTONCES LE CARGARE POR DEFECTO ESTO 
                                System.out.println("_   _if__:   :___DESCRIPCION_VACIA_____NO_SE_RECUPERO_DEL_CONFIG_AGEND____");
                                String DESC_CLINICA = metodosClinica.traerDescClinica(OA_IDCLINICA);
                                OAD_COMENTARIO = "Agendamiento para el "+modeloIniSes.getDatoFecha(0, OAD_FECHA_AGEN)+" a las "+OAD_HORA+" en la clínica "+DESC_CLINICA+".";
                            }
                            System.out.println("_   _guardar_ctrl______DESC_AGEND:    :"+OAD_COMENTARIO);
                        String OAD_ESTADO = "A";
                        String OAD_USU_ALTA = idPersona;
                        String OAD_FEC_ALTA = ""; // en el metodo de guardar nomas cargo 
                        String OAD_FEC_ATENCION = null;
                        String OAD_IDFACTURA = "0";
                        String OAD_AGENDADO = "NO";
                        String OAD_IDCONFIGAGEND = metodos.traer_idconfig_agend(OA_IDCLINICA);
                        String OAD_MOTIVO_CONSULTA = AGEN_MOTIVO_CONSULTA;
                        
                        // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                        TIPO_MENSAJE = metodos.guardar_cab(sesionDatosUsuario, new BeanAgendamiento(OA_IDAGENDAMIENTO, OA_IDCLINICA, OA_IDMEDICO, OA_IDESPECIALIDAD, OA_IDPERSONA, OA_FECHA, OA_TURNO, OA_DESDE, OA_HASTA, OA_ESTADO, OA_USU_ALTA, OA_FEC_ALTA, OA_IDAGENDAMIENTO, OAD_ITEM, OAD_FECHA_AGEN, OAD_HORA, OAD_IDPACIENTE, OAD_NROPACIENTEFICHA, OAD_MOTIVO, OAD_SEGUROMED, OAD_IDSEGUROMED, OAD_VISITATIPO, OAD_COMENTARIO, OAD_ESTADO, OAD_USU_ALTA, OAD_FEC_ALTA, OAD_FEC_ATENCION, OAD_IDFACTURA, OAD_AGENDADO, OAD_IDCONFIGAGEND, OAD_MOTIVO_CONSULTA));
                        if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR 
                            MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
    //                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
    //                        if (idAgend == null || idAgend.isEmpty() || idAgend.equals("")) {
    //                            acceso = "add_agend.htm";
    //                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
    //                            acceso = "edit_agend.htm";
    //                        }
                        } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                            MENSAJE = "Se ha realizado correctamente la operación.";
    //                        acceso = "agend.htm";
                        }
                        sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND",null); // LIMPIO LA VARIABLE PARA QUE NO LE VUELVA A INGRESAR ACA EN CASO DE QUE SELECCIONE "SI" 
                    } // end else validacion 
                    // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                    if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                        // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                        request.setAttribute("CA_AGEN_IDESPECIALIDAD", AGEN_IDESPECIALIDAD);
                        request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", AGEN_MOTIVO_CONSULTA);
                        request.setAttribute("CA_AGEN_IDMEDICO", AGEN_IDMEDICO);
                        request.setAttribute("CA_AGEN_FECHA", AGEN_FECHA);
                        request.setAttribute("CA_AGEN_HORA", AGEN_HORA);
                        request.setAttribute("CA_AGEN_IDCLINICA", AGEN_IDCLINICA);
                        request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                    }
                    var_redireccionar = 1;
                    acceso = "agend.htm";
                    // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                    /*
                    _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                        ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                        PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                    */
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
    //                sesionDatosUsuario.setAttribute("ID_USUARIO_AGENDAMIENTO", idUsuarioAgend);
                    request.setAttribute("CA_MENSAJE", MENSAJE);
                    request.setAttribute("CA_TIPO_MENSAJE", TIPO_MENSAJE);
                    
                } else if(accion.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    // COMBO MEDICO 
                    String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_IDMED = (String) request.getParameter("cbxAddNewMed"); // medico id 
                    if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty()) { PARAM_TXT_IDMED = ""; }
                    System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
                    System.out.println("_   MEDICO_ID   :   "+PARAM_TXT_IDMED);
                    // COMBO ESPECIALIDAD 
                    String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_IDESPE = (String) request.getParameter("cbxAddNewEspe"); // especialidad id 
                    if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty()) { PARAM_TXT_IDESPE = ""; }
                    System.out.println("_   CHECK_ESPECIALIDAD:   "+checkEspeFiltro);
                    System.out.println("_   ESPECIALIDAD_ID   :   "+PARAM_TXT_IDESPE);
                    // COMBO ESTADO 
                    String checkEstadoFiltro = (String) request.getParameter("check_estado"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_ESTADO = (String) request.getParameter("cE"); // ESTADO  
                    if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = ""; }
                    System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
                    System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
                    // CONTROL DEL CHECK DE MEDICO 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN MEDICO  
                    if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO  
                        checkMedicoFiltro = "OFF";
                    } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_IDMED = "";
                    }
                    // CONTROL DEL CHECK DE ESPECILIDAD 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR LA ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUNA ESPECIALIDAD  
                    if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                        checkEspeFiltro = "OFF";
                    } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_IDESPE = "";
                    }
                    // CONTROL DEL CHECK DE ESTADO 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
                    if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                        checkEstadoFiltro = "OFF";
                    } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_ESTADO = "";
                    }
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanAgendamiento> listaFiltro = new ArrayList<>();
                    listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDESPE, PARAM_TXT_ESTADO);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "agend.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CA_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CA_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CA_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CA_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
                    request.setAttribute("CA_TXT_FIL_ID_MED", PARAM_TXT_IDMED);
                    request.setAttribute("CA_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
                    request.setAttribute("CA_TXT_FIL_ID_ESPE", PARAM_TXT_IDESPE);
                    request.setAttribute("CA_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
                    request.setAttribute("CA_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
                    sesionDatosUsuario.setAttribute("CA_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                /*
                    COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accion.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "agend.htm";
                    sesionDatosUsuario.setAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_AGEN_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CA_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (esNumero(accion) == true) {
                    System.out.println("---------------------__CALENDAR_DAY_NUMBER_: "+accion+" :__--------------------------");
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    String TXT_IDPACIENTE = (String) request.getParameter("cPac");
                    String TXT_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    String TXT_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    String TXT_IDMEDICO = (String) request.getParameter("cMe");
                    String TXT_FECHA_DIA = accion;
                    if (Integer.parseInt(TXT_FECHA_DIA) < 10) {
                        TXT_FECHA_DIA = "0"+TXT_FECHA_DIA;
                    }
                    String TXT_FECHA_MES = (String) request.getParameter("txt_fec_calM"); // txt fecha mes del calendario que esta en el modal 
                    if (Integer.parseInt(TXT_FECHA_MES) < 10) {
                        TXT_FECHA_MES = "0"+TXT_FECHA_MES;
                    }
                    String TXT_FECHA_ANHO = (String) request.getParameter("txt_fec_calY"); // txt fecha año del calendario que esta en el modal 
                    String TXT_HORA = (String) request.getParameter("tHA");
                    
                    System.out.println("______________DATOS_TXTS_______________");
                    System.out.println("__ID_AGENDAMIENTO:  :"+idAgend);
                    System.out.println("_   _TXT_IDPACIENTE:   :"+TXT_IDPACIENTE);
                    System.out.println("_   _TXT_ID_ESPE:   :"+TXT_IDESPECIALIDAD);
                    System.out.println("_   _TXT_ID_MED:    :"+TXT_IDMEDICO);
                    System.out.println("_   _TXT_FECHA_D:   :"+TXT_FECHA_DIA);
                    System.out.println("_   _TXT_FECHA_M:   :"+TXT_FECHA_MES);
                    System.out.println("_   _TXT_FECHA_Y:   :"+TXT_FECHA_ANHO);
                    System.out.println("_   _TXT_HORA:      :"+TXT_HORA);
                    
                    String FECHA_FORMATEADA = (TXT_FECHA_DIA+"-"+TXT_FECHA_MES+"-"+TXT_FECHA_ANHO);
                    System.out.println("_   __FECHA_FORMATEADA:     :"+FECHA_FORMATEADA);
                    
                    String FEC_AGEN_DIA = metodos.devolverDia(modeloIniSes, FECHA_FORMATEADA);
                    System.out.println("_   _1__DIA_FECHA:     :"+FEC_AGEN_DIA);
                    
                    String MSN_DISP_FEC_MED = metodos.ctrlDiaSelecMedDisp(sesionDatosUsuario, TXT_IDMEDICO, FEC_AGEN_DIA);
                    System.out.println("_   _2__MENSAJE_DISPONIBILIDAD_FECHA_SELEC_MEDICO:     :"+MSN_DISP_FEC_MED);
                    
                    String CANT_IDCLINICAS = metodos.cantidadClinicaPHMed(sesionDatosUsuario, TXT_IDMEDICO, FEC_AGEN_DIA);
                    System.out.println("_   _3__CANTIDAD_CLINICAS_DISPONIBLES:   :"+CANT_IDCLINICAS);
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".       _--------BUSQUEDA_-DE-OTROS-AGENDAMIENTO----------_");
                    System.out.println(".");
                    /*
                    CONDICIONAL QUE UTILIZO PARA FILTRAR Y SOLAMENTE SI EL PERFIL DE SECRE Y ADMIN PODRA EJECUTAR EL BLOQUE DE CODIGO QUE BUSCA LOS AGENDAMIENTOS QUE HAYA EN LA FECHA QUE SELECCIONE EL USUARIO 
                    */
                    String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                    System.out.println(".   _CA__ID_PERFIL_USER:    :"+IDPERFIL_USER);
                    if(modeloPerfil.isPerfilAdmin(IDPERFIL_USER)==true || modeloPerfil.isPerfilSecre(IDPERFIL_USER)==true || modeloPerfil.isPerfilPaciente(IDPERFIL_USER)==true) {
                        /*
                        BLOQUE DE CODIGO EN EL QUE BUSCO LOS AGENDAMIENTOS QUE HAY EN LA FECHA SELECCIONADA, PARA QUE EL SECRETARIO PUEDA VER POR DEBAJO 
                        */
                        String FEC_AGEND_SELEC = TXT_FECHA_ANHO+"-"+TXT_FECHA_MES+"-"+TXT_FECHA_DIA;
                        List<BeanAgendamiento> listaDeAgendamientos = new ArrayList<>();
                        listaDeAgendamientos = metodos.traerAgendamientosFecha(sesionDatosUsuario, FEC_AGEND_SELEC);
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".   LISTA_DE_AGENDAMIENTOS_DE_LA_FECHA:  CANT: "+listaDeAgendamientos.size());
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        if (listaDeAgendamientos == null || listaDeAgendamientos.size()<=0) {
                            request.setAttribute("CA_LISTA_AGENDS_DELAFECHA_MSN", "No hay pacientes agendados.-");
                            request.setAttribute("CA_LISTA_AGENDS_DELAFECHA", null);
                        } else {
                            request.setAttribute("CA_LISTA_AGENDS_DELAFECHA", listaDeAgendamientos);
                        }
                        request.setAttribute("CA_VALI_MOSTRAR_GRILLA_AGEND_FECS", "1");
                    }
                    var_redireccionar = 1;
                    acceso = "add_agend.htm";
//                    acceso = "agend.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
                    request.setAttribute("CA_MSN_DISP_FEC_MED", MSN_DISP_FEC_MED); // VARIABLE QUE UTILIZO PARA MOSTRAR UN MENSAJE AL USUARIO EN CASO DE QUE EL MEDICO NO SE ENCUENTRE DISPONIBLE EL DIA QUE SELECCIONO 
                    request.setAttribute("CA_AGEN_IDPACIENTE", TXT_IDPACIENTE);
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", TXT_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", TXT_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", TXT_IDMEDICO);
                    request.setAttribute("CA_AGEN_FECHA", FECHA_FORMATEADA);
                    request.setAttribute("CA_AGEN_HORA", TXT_HORA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICAS);
                    
                } else if(accion.equalsIgnoreCase("Ver Plan de Horario del Médico")) { // BOTON QUE NO ESTOY UTILIZANDO PERO SIRVE PARA REDIRECCIONAR A LA PAGINA DE PLAN HORARIO PARA VER LOS DATOS DEL MEDICO 
                    System.out.println("---------------------__VER_PLAN_DE_HORARIO_DEL_MEDICO__--------------------------");
                    String IDMEDICO = (String) request.getParameter("tIM");
                    System.out.println("_   _ID_MEDICO:     :"+IDMEDICO);
                    // RECUPERO EL IDPLANHORARIO PARA PODER PASARLE AL JSP, YA QUE NECESITA PARA PODER RECUPERAR LOS DATOS 
                    String idPlanHorario = metodosPH.traerIdPlanHorario(sesionDatosUsuario, IDMEDICO);
                    System.out.println("_   _ID_PLAN_HORARIO:   :"+idPlanHorario);
                    
                    var_redireccionar = 1;
                    acceso = "ph_ver.htm";
                    sesionDatosUsuario.setAttribute("CPH_BTN_VOLVER_ATRAS", "AGENDAMIENTO"); // VARIABLE QUE VOY A UTILIZAR PARA EL BOTON DE ATRAS SEPA A QUE PAGINA DEBE DE DEVOLVER 
                    sesionDatosUsuario.setAttribute("ID_PLAN_HORARIO", idPlanHorario);
                    // LIMPIO LAS VARIABLES QUE MANTENGO EN LA SESION PARA QUE NO SE RECARGEN ESOS DATOS ANTERIORMENTE UTILIZADOS 
    //                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(listaDetalle.size()+1)); // LE CARGO EL VALOR DE LA GRILLA PARA SABER EL ULTIMO ITEM 
    //                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle); // LE PASO EL VALOR VACIO, PARA QUE NO SE MANTENGA LOS DATOS DEL ANTERIOR DETALLE 
                    sesionDatosUsuario.setAttribute("CPH_IDCLINICA", null);
                    sesionDatosUsuario.setAttribute("CPH_IDMEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_ESTADO", null);
                    
                } else if(accion.equalsIgnoreCase("Ver Horario del Médico") || accion.equalsIgnoreCase("Cargar Plan Horario")) {
                    System.out.println("---------------------__VER_PLAN_HORARIO_MEDICO__--------------------------");
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    String TXT_IDPACIENTE = (String) request.getParameter("cPac");
                    String TXT_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    String TXT_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    String TXT_IDMEDICO = (String) request.getParameter("cMe");
                    String FECHA_AGEN = (String) request.getParameter("tFA");
                    String TXT_HORA = (String) request.getParameter("tHA");
                    String TXT_IDCLINICA = (String) request.getParameter("tAIC");
                    System.out.println("_  _1__ID_CLINICA:    :"+TXT_IDCLINICA);
                    if (TXT_IDCLINICA == null || TXT_IDCLINICA.isEmpty()) {
                        TXT_IDCLINICA = (String) request.getParameter("cCli");
                        System.out.println("_  _2__ID_CLINICA:    :"+TXT_IDCLINICA);
                    }
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    
                    System.out.println("______________DATOS_TXTS_______________");
                    System.out.println("_  __ID_AGENDAMIENTO: :"+idAgend);
                    System.out.println("_  _TXT_IDPACIENTE:   :"+TXT_IDPACIENTE);
                    System.out.println("_  _TXT_ID_ESPE:   :"+TXT_IDESPECIALIDAD);
                    System.out.println("_  _TXT-MOT-CON:   :"+TXT_MOTIVO_CONSULTA);
                    System.out.println("_  _TXT_ID_MED:    :"+TXT_IDMEDICO);
                    System.out.println("_  __TXT_FECHA:    :"+FECHA_AGEN);
                    System.out.println("_  ___TXT_HORA:    :"+TXT_HORA);
                    System.out.println("_  _ID_CLINICA:    :"+TXT_IDCLINICA);
                    System.out.println("_  _CANT_IDCLINICA: :"+CANT_IDCLINICA);
                    System.out.println("---------------------------------------");
                    String txtModal_IDMEDICO = (String) request.getParameter("txt_idMed");
                    String txtModal_IDCLINICA = (String) request.getParameter("txt_idCli");
                    System.out.println(".");
                    System.out.println("_______________________________________");
                    
                    List<BeanPlanHorario> listaPHMed = new ArrayList<>();
                    
                    var_redireccionar = 1;
                    acceso = "add_agend_fph.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
                    request.setAttribute("CA_LISTA_PH_MEDICO", listaPHMed);
                    // DATOS DE LA CABECERA 
                    request.setAttribute("CA_AGEN_IDPACIENTE", TXT_IDPACIENTE);
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", TXT_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", TXT_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", TXT_IDMEDICO);
                    request.setAttribute("CA_AGEN_FECHA", FECHA_AGEN);
                    request.setAttribute("CA_AGEN_HORA", TXT_HORA);
                    request.setAttribute("CA_AGEN_IDCLINICA", TXT_IDCLINICA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                    
                } else if(accion.equalsIgnoreCase("Ver Plan Horario")) {
                    System.out.println("---------------------__VER_PLAN_HORARIO_DEL_MEDICO__--------------------------");
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    // CAMPOS AUXILIARES DE LA PAGINA DONDE GUARDO LOS DATOS DE LA CABECERA 
                    String TXT_IDPACIENTE = (String) request.getParameter("tAIP");
                    String TXT_IDESPECIALIDAD = (String) request.getParameter("tAIE");
                    String TXT_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    String TXT_IDMEDICO = (String) request.getParameter("tAIM");
                    String FECHA_AGEN = (String) request.getParameter("tAFA");
                    String TXT_HORA = (String) request.getParameter("tAHA");
                    String TXT_IDCLINICA = (String) request.getParameter("tAAIC");
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    
                    System.out.println("______________DATOS_TXTS_______________");
                    System.out.println("_  __ID_AGENDAMIENTO: :"+idAgend);
                    System.out.println("_  _TXT_IDPACIENTE:   :"+TXT_IDPACIENTE);
                    System.out.println("_  _TXT_ID_ESPE:   :"+TXT_IDESPECIALIDAD);
                    System.out.println("_  _TXT_MOT_CON:   :"+TXT_MOTIVO_CONSULTA);
                    System.out.println("_  _TXT_ID_MED:    :"+TXT_IDMEDICO);
                    System.out.println("_  __TXT_FECHA:    :"+FECHA_AGEN);
                    System.out.println("_  ___TXT_HORA:    :"+TXT_HORA);
                    System.out.println("_  _ID_CLINICA:    :"+TXT_IDCLINICA);
                    System.out.println("_  _CANT_IDCLINICA: :"+CANT_IDCLINICA);
                    System.out.println("---------------------------------------");
                    String txtModal_IDMEDICO = (String) request.getParameter("cbxAddNewMed");
                    String txtModal_IDCLINICA = (String) request.getParameter("cbxAddNewClinica");
                    System.out.println(".   _MODAL___ID_MEDICO:     :"+txtModal_IDMEDICO);
                    System.out.println(".   _MODAL___ID_CLINICA:    :"+txtModal_IDCLINICA);
                    System.out.println(".");
                    System.out.println("_______________________________________");
                    
                    List<BeanPlanHorario> listaPHMed = metodosPH.traer_ph_medico(txtModal_IDMEDICO, txtModal_IDCLINICA, sesionDatosUsuario);
                    
                    var_redireccionar = 1;
                    acceso = "add_agend_fph.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
                    request.setAttribute("CA_LISTA_PH_MEDICO", listaPHMed);
                    request.setAttribute("CA_FILTER_IDMEDICO", txtModal_IDMEDICO);
                    request.setAttribute("CA_FILTER_IDCLINICA", txtModal_IDCLINICA);
                    // DATOS DE LA CABECERA 
                    request.setAttribute("CA_AGEN_IDPACIENTE", TXT_IDPACIENTE);
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", TXT_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", TXT_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", TXT_IDMEDICO);
                    request.setAttribute("CA_AGEN_FECHA", FECHA_AGEN);
                    request.setAttribute("CA_AGEN_HORA", TXT_HORA);
                    request.setAttribute("CA_AGEN_IDCLINICA", TXT_IDCLINICA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                    
                } else if (accion.equalsIgnoreCase("Volver a la cabecera")) {
                    System.out.println("---------------------__VOLVER_A_LA_CABECERA__--------------------------");
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    // CAMPOS AUXILIARES DE LA PAGINA DONDE GUARDO LOS DATOS DE LA CABECERA 
                    String TXT_IDPACIENTE = (String) request.getParameter("tAIP");
                    String TXT_IDESPECIALIDAD = (String) request.getParameter("tAIE");
                    String TXT_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    String TXT_IDMEDICO = (String) request.getParameter("tAIM");
                    String FECHA_AGEN = (String) request.getParameter("tAFA");
                    String TXT_HORA = (String) request.getParameter("tAHA");
                    String TXT_IDCLINICA = (String) request.getParameter("tAAIC");
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    
                    System.out.println("______________DATOS_TXTS_______________");
                    System.out.println("_  __ID_AGENDAMIENTO: :"+idAgend);
                    System.out.println("_  _TXT_IDPACIENTE:   :"+TXT_IDPACIENTE);
                    System.out.println("_  _TXT_ID_ESPE:   :"+TXT_IDESPECIALIDAD);
                    System.out.println("_  _TXT_ID_MED:    :"+TXT_IDMEDICO);
                    System.out.println("_  __TXT_FECHA:    :"+FECHA_AGEN);
                    System.out.println("_  ___TXT_HORA:    :"+TXT_HORA);
                    System.out.println("_  _ID_CLINICA:    :"+TXT_IDCLINICA);
                    System.out.println("_  _CANT_IDCLINICA: :"+CANT_IDCLINICA);
                    System.out.println("---------------------------------------");
                    var_redireccionar = 1;
                    acceso = "add_agend.htm";
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
                    // DATOS DE LA CABECERA 
                    request.setAttribute("CA_AGEN_IDPACIENTE", TXT_IDPACIENTE);
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", TXT_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", TXT_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", TXT_IDMEDICO);
                    request.setAttribute("CA_AGEN_FECHA", FECHA_AGEN);
                    request.setAttribute("CA_AGEN_HORA", TXT_HORA);
                    request.setAttribute("CA_AGEN_IDCLINICA", TXT_IDCLINICA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                }
                
                
            } else if (accionPag != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_    ___ACCION_PARA_LA_PAGINACION___    _");
                System.out.println(".");System.out.println(".");
//                } else 
                if (esNumero(accionPag) == true) {
                    System.out.println("---------------------__PAGINACION_NUMBER_: "+accionPag+" :__--------------------------");
                    // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                    sesionDatosUsuario.setAttribute("PAG_AGEN_LISTA_ACTUAL", accionPag);
                    
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodos);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    
                } else if (accionPag.equalsIgnoreCase(">>")) {
                    System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accionPag+" :__--------------------------");
                    String PAG_AGEN_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_AGEN_CANT_NRO_CLIC == null) {
                        PAG_AGEN_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_AGEN_CANT_NRO_CLIC);
                    String PAG_AGEN_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_AGEN_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_AGEN_LISTA_ACTUAL);
                    
                    PAG_AGEN_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_AGEN_CANT_NRO_CLIC)+1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_AGEN_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_AGEN_LISTA_ACTUAL", PAG_AGEN_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_AGEN_CANT_NRO_CLIC)+1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodos);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    
                } else if (accionPag.equalsIgnoreCase("<<")) {
                    System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accionPag+" :__--------------------------");
                    String PAG_AGEN_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_AGEN_CANT_NRO_CLIC == null) {
                        PAG_AGEN_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_AGEN_CANT_NRO_CLIC);
                    String PAG_AGEN_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_AGEN_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_AGEN_LISTA_ACTUAL);
                    
                    PAG_AGEN_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_AGEN_CANT_NRO_CLIC)-1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_AGEN_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_AGEN_LISTA_ACTUAL", PAG_AGEN_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_AGEN_CANT_NRO_CLIC)-1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CA_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodos);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                }
                
                
            } else if (accionSecre != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_    ___ACCION_PARA_EL_SECRETARIO___    _");
                System.out.println(".");System.out.println(".");
                if (accionSecre.equalsIgnoreCase("Agregar Nuevo Paciente")) {
                    System.out.println("---------------------__AGREGAR_NUEVO_PACIENTE__--------------------------");
                    String AGEN_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    String AGEN_IDPACIENTE = (String) request.getParameter("cPac");
                    String AGEN_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    String AGEN_IDMEDICO = (String) request.getParameter("cMe");
                    String AGEN_FECHA = (String) request.getParameter("tFA");
                    String AGEN_HORA = (String) request.getParameter("tHA");
                    String AGEN_IDCLINICA = (String) request.getParameter("cCli"); // COMBO BOX 
                    if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                        AGEN_IDCLINICA = (String) request.getParameter("tAIC"); // CAMPO DE TEXTO 
                        if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                            AGEN_IDCLINICA="";
                        }
                    }
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-       AGEN_ID_ESPE:   "+AGEN_IDESPECIALIDAD);
                    System.out.println("-       AGEN_MOT_CONS:  "+AGEN_MOTIVO_CONSULTA);
                    System.out.println("-       AGEN_ID_MEDI:   "+AGEN_IDMEDICO);
                    System.out.println("-       AGEN_ID_PACI:   "+AGEN_IDPACIENTE);
                    System.out.println("-       AGEN_FECHA:     "+AGEN_FECHA);
                    System.out.println("-       AGEN_HORA:      "+AGEN_HORA);
                    System.out.println("-       AGEN_ID_CLIN:   "+AGEN_IDCLINICA);
                    System.out.println("-       CANT_ID_CLIN:   "+CANT_IDCLINICA);
                    System.out.println("-   -----------------------------------");
                    
                    var_redireccionar = 1;
                    acceso = "add_paciente.htm";
//                    acceso = "ver_agend.htm";
                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "AGEN"); // VARIABLE QUE UTILIZO EN PACIENTE PARA PODER SABER DONDE TIENE QUE VOLVER ATRAS Y NO LO MANDE EN CUALQUIER OTRO LADO 
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", AGEN_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", AGEN_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", AGEN_IDMEDICO);
                    request.setAttribute("CA_AGEN_IDPACIENTE", AGEN_IDPACIENTE);
                    request.setAttribute("CA_AGEN_FECHA", AGEN_FECHA);
                    request.setAttribute("CA_AGEN_HORA", AGEN_HORA);
                    request.setAttribute("CA_AGEN_IDCLINICA", AGEN_IDCLINICA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                    
                } else if (accionSecre.equalsIgnoreCase("Agendar") || accionSecre.equalsIgnoreCase("Guardar") || accionSecre.equalsIgnoreCase("AgendarSi")) {
                    System.out.println("---------------------__AGENDAR / GUARDAR_2__------------------ __SECRETARIO__ --------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idAgend = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    System.out.println("__ID_AGENDAMIENTO :   "+idAgend);
                    int verify_new_vali_limit = 0; // OBS.: VARIABLE QUE UTILIZO PARA PODER ACTIVAR TAMBIEN EL CONTROL DE LA CANT MAX DE AGENDAMIENTO, YA QUE SE PUEDE INGRESAR A ESTE IF A TRAVES DE "SI" Y "AGENDAR" Y SI ENTRA POR MEDIO DEL BTN DE AGENDAR ENTOCES HAY SI VOLVERIA A CONTROLAR 
                    if (accionSecre.equalsIgnoreCase("AgendarSi")) {
                        System.out.println(".");System.out.println(".");
                        System.out.println("_   _   _BTN_SI_AGENDAR_    _   __SECRETARIO__");
                        System.out.println(".");System.out.println(".");
                        verify_new_vali_limit = 1;
                    }
                    
                    // RECUPERAR LOS DATOS 
                    String AGEN_IDPACIENTE = (String) request.getParameter("cPac");
                    if (AGEN_IDPACIENTE == null || AGEN_IDPACIENTE.isEmpty()) { varValiVacio++; AGEN_IDPACIENTE=""; }
                    String AGEN_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    if (AGEN_IDESPECIALIDAD == null || AGEN_IDESPECIALIDAD.isEmpty()) { varValiVacio++; AGEN_IDESPECIALIDAD=""; }
                    String AGEN_IDMEDICO = (String) request.getParameter("cMe");
                    if (AGEN_IDMEDICO == null || AGEN_IDMEDICO.isEmpty()) { varValiVacio++; AGEN_IDMEDICO=""; }
                    String AGEN_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    if (AGEN_MOTIVO_CONSULTA == null || AGEN_MOTIVO_CONSULTA.isEmpty()) { varValiVacio++; AGEN_MOTIVO_CONSULTA=""; }
                    String AGEN_FECHA = (String) request.getParameter("tFA");
                    if (AGEN_FECHA == null || AGEN_FECHA.isEmpty()) { varValiVacio++; AGEN_FECHA=""; }
                    String AGEN_HORA = (String) request.getParameter("tHA");
                    if (AGEN_HORA == null || AGEN_HORA.isEmpty()) { varValiVacio++; AGEN_HORA=""; }
                    String AGEN_IDCLINICA = (String) request.getParameter("cCli"); // COMBO BOX 
                    if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                        AGEN_IDCLINICA = (String) request.getParameter("tAIC"); // CAMPO DE TEXTO 
                        if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                            varValiVacio++;
                            AGEN_IDCLINICA="";
                        }
                    }
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    String AGEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
    //                String PAC_USU_ESTADO = (String) request.getParameter("cEs");
                    if (AGEN_ESTADO == null || AGEN_ESTADO.isEmpty()) { varValiVacio++; AGEN_ESTADO=""; }
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-       AGEN_ID_PACI:   "+AGEN_IDPACIENTE);
                    System.out.println("-       AGEN_ID_ESPE:   "+AGEN_IDESPECIALIDAD);
                    System.out.println("-       AGEN_ID_MEDI:   "+AGEN_IDMEDICO);
                    System.out.println("-       MOTIVO_CONSULTA:   "+AGEN_MOTIVO_CONSULTA);
                    System.out.println("-       AGEN_FECHA:     "+AGEN_FECHA);
                    System.out.println("-       AGEN_HORA:      "+AGEN_HORA);
                    System.out.println("-       AGEN_IDCLINICA: "+AGEN_IDCLINICA);
                    System.out.println("-       CANT_IDCLINICA: "+CANT_IDCLINICA);
                    System.out.println("-       AGEN_ESTADO:    "+AGEN_ESTADO);
                    System.out.println("-   -----------------------------------");
                    System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                    
                    // VALIDACIONES 
                    String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
                    if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                        MENSAJE = "No puede dejar campos vacios.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlFechaAgenDia(modeloIniSes, AGEN_FECHA) == true) { // VALIDACION PARA CONTROLAR LA FECHA DE AGENDAMIENTO PARA QUE ESTA NO SEA MENOR A LA FECHA DE HOY 
                        MENSAJE = "No puede agendarse en una fecha pasada.";
//                        MENSAJE = "No puede agendarse con una fecha menor a la fecha de hoy.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlConfigAgendCli(AGEN_IDCLINICA) == true) { // VALIDACION PARA CONTROLAR QUE LA CLINICA TENGA UNA CONFIGURACIÓN PARA LOS AGENDAMIENTOS 
                        MENSAJE = "La clínica no cuenta con una configuración de agendamiento activa, por lo tanto no se puede hacer agendamientos.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(metodos.ctrlDiaPHMedico(sesionDatosUsuario, modeloIniSes, AGEN_IDMEDICO, AGEN_FECHA, AGEN_HORA, AGEN_IDCLINICA) == true) { // VALIDACION PARA CONTROLAR SI LA FECHA Y LA HORA DE AGENDAMIENTO ENTRAN DENTRO DEL HORARIO DEL MEDICO 
                        metodos.obtenerHorarioDiaMed(sesionDatosUsuario, modeloIniSes, AGEN_IDMEDICO, AGEN_FECHA, AGEN_IDCLINICA); // METODO PARA CARGAR LAS VARIABLES DE LA SESION QUE UTILIZARE PARA CARGAR EL MENSAJE Y DARLE INFORMACION DEL HORARIO DEL MEDICO 
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        String PARAM_DIAS="", PARAM_DESDE="", PARAM_HASTA="";
                        String SEGUNDO_MENSAJE = "";
                        System.out.println("_   _ctrl___VALI_PARAM_DIAS:    :"+((String)sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS")));                        // VALIDACION PARA MOSTRAR EL SEGUNDO MENSAJE 
                        if (sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS") == null) { // SI SE ENCUENTRA VACIO ENTONCES ES PORQUE ESE DIA NO ESTA DISPONIBLE 
                            System.out.println("__-_______IF_________");
                            SEGUNDO_MENSAJE="";
                        } else { // EN CASO DE QUE NO DEVUELVA NULL ES PORQUE ESTA PERO DENTRO DE OTRO HORARIO Y CARGARE ESOS DATOS PARA MOSTRAR EN EL MENSAJE 
                            System.out.println("__-_______ELSE_________");
                            PARAM_DIAS = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS");
                            PARAM_DESDE = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DESDE");
                            PARAM_HASTA = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_HASTA");
                            // DEPENDIENDO DE SI SE OBTUVO UN VALOR VOY A MOSTRAR EL MENSAJE DE UNA FORMA O DE OTRA PARA NO MOSTRARLE EL MENSAJE CON DATOS VACIOS 
                            if ((PARAM_DESDE == null || PARAM_DESDE.isEmpty()) || (PARAM_HASTA == null || PARAM_HASTA.isEmpty())) {
//                                metodos.obtenerDiasAteMed(sesionDatosUsuario, AGEN_IDMEDICO); // METODO QUE UTILIZO PARA CARGAR LA VARIABLE DE PARAM_DIAS CON LOS DIAS DISPONIBLES QUE TIENE EL MEDICO YA QUE EL DIA QUE SELECCIONO NO SE ENCUENTRA DENTRO DEL HORARIO DE ATENCION DEL MEDICO 
//                                PARAM_DIAS = (String) sesionDatosUsuario.getAttribute("VALI_PARAM_DIAS"); // VUELVO A INVOCARLE YA QUE EL METODO VOLVIO A CARGAR LA VARIABLE EN LA SESION 
//                                SEGUNDO_MENSAJE = "El médico se encuentra disponible los días ("+PARAM_DIAS+").";
                                SEGUNDO_MENSAJE = null;
                            } else {
                                SEGUNDO_MENSAJE = "(Los días "+PARAM_DIAS+" el médico se encuentra de "+PARAM_DESDE+" hasta "+PARAM_HASTA+")";
                            }
                            sesionDatosUsuario.setAttribute("CA_SEGUNDO_MENSAJE", SEGUNDO_MENSAJE);
                        }
                        // OBSERVACION: SI LE CONCATENO EL SEGUNDO MENSAJE AL MENSAJE, JAVA ME VA A COLOCAR UNO ALADO DE OTRO Y ASI EL MENSAJE SE HACE LARGO Y NO LE COLOCA DEBAJO O NO LE DA UN SALTO DE LINEA POR ESO LO HAGO DE ESTA MANERA Y LE PASO EL SEGUNDO MENSAJE A LA SESION PARA DESDE EL JSP RECIBIR Y CARGAR EN OTRA ETIQUETA HTML PARA DARLE UN SALTO DE LINEA Y SE MUESTRE ESTETICAMENTE EN LA PAGINA 
                        MENSAJE = "El Horario seleccionado no se encuentra dentro del horario de atención del médico.\n";
//                        MENSAJE = "El día seleccionado no se encuentra dentro del horario de atención del médico.\n";
                        TIPO_MENSAJE = "2";
                        
                    } else if (metodos.verifyAgendExist(sesionDatosUsuario, AGEN_IDMEDICO, AGEN_IDESPECIALIDAD, AGEN_IDCLINICA, modeloIniSes.convertirFechaYMD(AGEN_FECHA)) == true 
                        && (
                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND"))==null) || 
                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("null")) || 
//                            (String.valueOf(sesionDatosUsuario.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("0"))
                            (verify_new_vali_limit == 0)
                            )
                        ) { // VALIDACION PARA CONTROLAR SI ES QUE LA CABECERA DEL AGENDAMIENTO SOBRE EL MEDICO YA EXISTE, Y SI FUERA ASI CONTROLAR QUE NO SE PASE EL LIMITE DE AGENDAMIENTO POR MEDICO, Y EN CASO DE QUE YA ALCANZO EL LIMITE LE DOY LA OPCIÓN DE ABRIR OTRA CABECERA PARA EL MEDICO 
                        MENSAJE = "Ya se alcanzo el limite de agendamientos disponibles para este médico.";
                        TIPO_MENSAJE = "2";
                        sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND","1"); // PASO UNA VARIABLE CON UN VALOR DISTINTO AL NULL Y CERO PARA NO VOLVER A ENTRAR A ESTA VALIDACION UNA VEZ QUE EL SECRETARIO O EL USUARIO HAYA VUELTO A CONFIRMAR QUE QUIERE ABRIR UNA NUEVA CABECERA PARA EL MEDICO Y AL FINAL DEL EVENTO LE CARGO NULL A ESTA VARIABLE PARA QUE NO SE MANTENGA CON EL VALOR ANTERIOR 
                        
                    } else if (metodos.ctrlIntervMinEntreAgend(sesionDatosUsuario, AGEN_IDCLINICA, AGEN_IDMEDICO, AGEN_IDESPECIALIDAD, modeloIniSes.convertirFechaYMD(AGEN_FECHA), AGEN_HORA) == true) { // VALIDACION PARA CONTROLAR EL INTERVALO DE TIEMPO QUE HAY ENTRE LOS AGENDAMIENTOS Y QUE SE RESPETE LA CONFIGURACION, EN CASO DE QUE TENGA UNA CANTIDAD EN MINUTOS PARA DIVIDIR LOS AGENDAMIENTOS 
                        TIPO_MENSAJE = "2";
//                        String DIFERENCIA_MIN = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_DIFERENCIA_MIN");
                        String INTERVALO_MIN = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_MIN_ENTRE_AGEND");
                        String HORA_AGEN_DET = (String) sesionDatosUsuario.getAttribute("VALI_INTERV_HORA_AGEND_DET");
                        MENSAJE = "Ya existe un agendamiento a las "+HORA_AGEN_DET+"hs., y la diferencia de tiempo debe de ser de "+INTERVALO_MIN+"min. para poder agendarse.";
                        
                    } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                        // CABECERA 
                        String OA_IDAGENDAMIENTO = idAgend;
                        String OA_IDCLINICA = AGEN_IDCLINICA;
//                        String OA_IDCLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");
                        String OA_IDMEDICO = AGEN_IDMEDICO;
                        String OA_IDESPECIALIDAD = AGEN_IDESPECIALIDAD;
                        String OA_IDPERSONA = ""; // no uso 
                        String OA_FECHA = ""; // no uso 
                        String OA_TURNO = ""; // no uso 
                        String OA_DESDE = ""; // no uso 
                        String OA_HASTA = ""; // no uso 
                        String OA_ESTADO = "A";
                        String OA_USU_ALTA = idPersona;
                        String OA_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                        // DETALLE 
                        String OAD_ITEM = "";
                            String FECHA_AGEN_FORM = modeloIniSes.convertirFechaYMD(AGEN_FECHA);
                            System.out.println("_   _FECHA:  :"+FECHA_AGEN_FORM);
                            System.out.println("_       _FECHA_ANHO: :"+ modeloIniSes.getDatoFecha(1, FECHA_AGEN_FORM));
                            System.out.println("_       _FECHA_MES:  :"+ modeloIniSes.getDatoFecha(2, FECHA_AGEN_FORM));
                            System.out.println("_       _FECHA_DIA:  :"+ modeloIniSes.getDatoFecha(3, FECHA_AGEN_FORM));
                        String ANHO_FORM = modeloIniSes.getDatoFecha(1, FECHA_AGEN_FORM);
                        String MES_FORM = modeloIniSes.getDatoFecha(2, FECHA_AGEN_FORM);
                        String DIA_FORM = modeloIniSes.getDatoFecha(3, FECHA_AGEN_FORM);
                        String OAD_FECHA_AGEN = (ANHO_FORM+"-"+MES_FORM+"-"+DIA_FORM);
                        String OAD_HORA = AGEN_HORA;
                        String OAD_IDPACIENTE = AGEN_IDPACIENTE;
                        String OAD_NROPACIENTEFICHA = "";
                        String OAD_MOTIVO = "";
                        String OAD_SEGUROMED = "";
                        String OAD_IDSEGUROMED = "0";
                        String OAD_VISITATIPO = "";
                        String OAD_COMENTARIO = metodos.traer_desc_agend(modeloIniSes, OA_IDCLINICA, OAD_FECHA_AGEN, OAD_HORA);
                            if (OAD_COMENTARIO == null || OAD_COMENTARIO.isEmpty()) { // EN CASO DE QUE ESTE NULO, YA SEA PORQUE NO HAY UNA CONFIGURACION PARA EL IDCLINICA O NO HAYA NINGUNO ACTIVO, ENTONCES LE CARGARE POR DEFECTO ESTO 
                                System.out.println("_   _if__:   :___DESCRIPCION_VACIA_____NO_SE_RECUPERO_DEL_CONFIG_AGEND____");
                                String DESC_CLINICA = metodosClinica.traerDescClinica(OA_IDCLINICA);
                                OAD_COMENTARIO = "Agendamiento para el "+modeloIniSes.getDatoFecha(0, OAD_FECHA_AGEN)+" a las "+OAD_HORA+" en la clínica "+DESC_CLINICA+".";
                            }
                            System.out.println("_   _guardar_ctrl______DESC_AGEND:    :"+OAD_COMENTARIO);
                        String OAD_ESTADO = "A";
                        String OAD_USU_ALTA = idPersona;
                        String OAD_FEC_ALTA = ""; // en el metodo de guardar nomas cargo 
                        String OAD_FEC_ATENCION = null;
                        String OAD_IDFACTURA = "0";
                        String OAD_AGENDADO = "NO";
                        String OAD_IDCONFIGAGEND = metodos.traer_idconfig_agend(OA_IDCLINICA);
                        String OAD_MOTIVO_CONSULTA = AGEN_MOTIVO_CONSULTA;
                        
                        // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                        TIPO_MENSAJE = metodos.guardar_cab(sesionDatosUsuario, new BeanAgendamiento(OA_IDAGENDAMIENTO, OA_IDCLINICA, OA_IDMEDICO, OA_IDESPECIALIDAD, OA_IDPERSONA, OA_FECHA, OA_TURNO, OA_DESDE, OA_HASTA, OA_ESTADO, OA_USU_ALTA, OA_FEC_ALTA, OA_IDAGENDAMIENTO, OAD_ITEM, OAD_FECHA_AGEN, OAD_HORA, OAD_IDPACIENTE, OAD_NROPACIENTEFICHA, OAD_MOTIVO, OAD_SEGUROMED, OAD_IDSEGUROMED, OAD_VISITATIPO, OAD_COMENTARIO, OAD_ESTADO, OAD_USU_ALTA, OAD_FEC_ALTA, OAD_FEC_ATENCION, OAD_IDFACTURA, OAD_AGENDADO, OAD_IDCONFIGAGEND, OAD_MOTIVO_CONSULTA));
                        
                        if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR 
                            MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
    //                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
    //                        if (idAgend == null || idAgend.isEmpty() || idAgend.equals("")) {
    //                            acceso = "add_agend.htm";
    //                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
    //                            acceso = "edit_agend.htm";
    //                        }
                        } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                            MENSAJE = "Se ha realizado correctamente la operación.";
//                            acceso = "agend.htm";
                        }
                        acceso = "agend.htm";
                        sesionDatosUsuario.setAttribute("CA_VAR_VERIFY_CAB_AGEND",null); // LIMPIO LA VARIABLE PARA QUE NO LE VUELVA A INGRESAR ACA EN CASO DE QUE SELECCIONE "SI" 
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "LLEGO AL GUARDAR";
                    } // end else validacion
                    
                    // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                    if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                        // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                        request.setAttribute("CA_AGEN_IDPACIENTE", AGEN_IDPACIENTE);
                        request.setAttribute("CA_AGEN_IDESPECIALIDAD", AGEN_IDESPECIALIDAD);
                        request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", AGEN_MOTIVO_CONSULTA);
                        request.setAttribute("CA_AGEN_IDMEDICO", AGEN_IDMEDICO);
                        request.setAttribute("CA_AGEN_FECHA", AGEN_FECHA);
                        request.setAttribute("CA_AGEN_HORA", AGEN_HORA);
                        request.setAttribute("CA_AGEN_IDCLINICA", AGEN_IDCLINICA);
                        request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                        acceso = "add_agend.htm";
                    }
                    var_redireccionar = 1;
//                    acceso = "agend.htm";
                    // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                    /*
                    _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                        ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                        PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                    */
                    sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
    //                sesionDatosUsuario.setAttribute("ID_USUARIO_AGENDAMIENTO", idUsuarioAgend);
                    request.setAttribute("CA_MENSAJE", MENSAJE);
                    request.setAttribute("CA_TIPO_MENSAJE", TIPO_MENSAJE);
                    
                } else if(accionSecre.equalsIgnoreCase("Buscar por CI")) {
                    System.out.println("---------------------__BUSCAR_PACIENTE_POR_NRO_CI__--------------------------");
                    String filtro_txtNroCi = (String) request.getParameter("tACIF"); // tACIF: txt auxiliar ci filter 
                    System.out.println("_  _FILTRO_TXT_NRO_CI:     :"+filtro_txtNroCi);
                    // DATOS DE LOS OTROS CAMPOS DE TEXTO POR SI ESTEN CARGADOS 
                    String AGEN_IDPACIENTE = (String) request.getParameter("cPac");
                    if (AGEN_IDPACIENTE == null || AGEN_IDPACIENTE.isEmpty()) { AGEN_IDPACIENTE=""; }
                    String AGEN_IDESPECIALIDAD = (String) request.getParameter("cEs");
                    if (AGEN_IDESPECIALIDAD == null || AGEN_IDESPECIALIDAD.isEmpty()) { AGEN_IDESPECIALIDAD=""; }
                    String AGEN_IDMEDICO = (String) request.getParameter("cMe");
                    if (AGEN_IDMEDICO == null || AGEN_IDMEDICO.isEmpty()) { AGEN_IDMEDICO=""; }
                    String AGEN_MOTIVO_CONSULTA = (String) request.getParameter("TAMCA");
                    if (AGEN_MOTIVO_CONSULTA == null || AGEN_MOTIVO_CONSULTA.isEmpty()) { AGEN_MOTIVO_CONSULTA=""; }
                    String AGEN_FECHA = (String) request.getParameter("tFA");
                    if (AGEN_FECHA == null || AGEN_FECHA.isEmpty()) { AGEN_FECHA=""; }
                    String AGEN_HORA = (String) request.getParameter("tHA");
                    if (AGEN_HORA == null || AGEN_HORA.isEmpty()) { AGEN_HORA=""; }
                    String AGEN_IDCLINICA = (String) request.getParameter("cCli"); // COMBO BOX 
                    if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                        AGEN_IDCLINICA = (String) request.getParameter("tAIC"); // CAMPO DE TEXTO 
                        if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                            AGEN_IDCLINICA="";
                        }
                    }
                    String CANT_IDCLINICA = (String) request.getParameter("tAACIC");
                    String AGEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
    //                String PAC_USU_ESTADO = (String) request.getParameter("cEs");
                    if (AGEN_ESTADO == null || AGEN_ESTADO.isEmpty()) { AGEN_ESTADO=""; }
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-       AGEN_ID_PACI:   "+AGEN_IDPACIENTE);
                    System.out.println("-       AGEN_ID_ESPE:   "+AGEN_IDESPECIALIDAD);
                    System.out.println("-       AGEN_ID_MEDI:   "+AGEN_IDMEDICO);
                    System.out.println("-       AGEN_MOT_CON:   "+AGEN_MOTIVO_CONSULTA);
                    System.out.println("-       AGEN_FECHA:     "+AGEN_FECHA);
                    System.out.println("-       AGEN_HORA:      "+AGEN_HORA);
                    System.out.println("-       AGEN_IDCLINICA: "+AGEN_IDCLINICA);
                    System.out.println("-       CANT_IDCLINICA: "+CANT_IDCLINICA);
                    System.out.println("-       AGEN_ESTADO:    "+AGEN_ESTADO);
                    System.out.println("-   -----------------------------------");
                    
                    // FILTRO LA TABLA DE PACIENTE POR EL NRO DE CI QUE SE CARGA 
                    String TXT_MENSAJE_NOT_FOUND = "";
                    String VAR_IDPACIENTE_FILTER = metodosPersona.filtrarPorNroCi(sesionDatosUsuario, filtro_txtNroCi);
                    if (VAR_IDPACIENTE_FILTER == null || VAR_IDPACIENTE_FILTER.isEmpty()) {
                        TXT_MENSAJE_NOT_FOUND = "No se encontro a ningun paciente en esta clinica con ese Nro. de C.I.";
                    } else { // EN CASO DE QUE 
                        filtro_txtNroCi = "";
                        AGEN_IDPACIENTE = VAR_IDPACIENTE_FILTER;
                    }
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "add_agend.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CM_FILTER_NRO_CI", filtro_txtNroCi);
                    request.setAttribute("CA_MSN_NOT_FOUND_PAC_CINRO", TXT_MENSAJE_NOT_FOUND); // VARIABLE QUE UTILIZO PARA MOSTRAR UN MENSAJE AL USUARIO EN CASO DE QUE EL CINRO QUE FILTRO NO ES DE NINGUN PACIENTE 
                    // PASO LAS VARIABLES PARA MANTENERLAS Y AL FILTRAR NO SE CARGEN VALORES VACIOS 
                    request.setAttribute("CA_AGEN_IDPACIENTE", AGEN_IDPACIENTE);
                    request.setAttribute("CA_AGEN_IDESPECIALIDAD", AGEN_IDESPECIALIDAD);
                    request.setAttribute("CA_AGEN_MOTIVO_CONSULTA", AGEN_MOTIVO_CONSULTA);
                    request.setAttribute("CA_AGEN_IDMEDICO", AGEN_IDMEDICO);
                    request.setAttribute("CA_AGEN_FECHA", AGEN_FECHA);
                    request.setAttribute("CA_AGEN_HORA", AGEN_HORA);
                    request.setAttribute("CA_AGEN_IDCLINICA", AGEN_IDCLINICA);
                    request.setAttribute("CA_AGEN_CANT_IDCLINICA", CANT_IDCLINICA);
                }
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-----------------ERROR-AGENDAMIENTO--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerAgendamiento.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("-------------------------------------------------------");
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
    
    
    public String ver_agendamiento(HttpSession sesionDatosUsuario, HttpServletRequest request, String acceso, int var_redireccionar, String idAgend) {
        System.out.println("---------------------__VER_AGENDAMIENTO__--------------------------");
        idAgend = (String) request.getParameter("tI");
        System.out.println("_doPost__ID_AGENDAMIENTO:     "+idAgend);
        
        var_redireccionar = 1;
        acceso = "ver_agend.htm";
        sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
        return idAgend;
    }
    
    
    public String ver_agendamiento_anular(HttpSession sesionDatosUsuario, HttpServletRequest request, String acceso) {
        System.out.println("---------------------__VER_AGENDAMIENTO__-__BOTON_PREVIO_ANULAR__--------------------------");
        String idAgend = (String) request.getParameter("tI");
        System.out.println("_doPost__ID_AGENDAMIENTO:   "+idAgend);
        String itemAgend = (String) request.getParameter("tIAD");
        System.out.println("_doPost__ITEM_AGENDA_DET:   "+itemAgend);
        acceso = "ver_agen.htm";
        sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend);
        request.setAttribute("ID_AGENDAMIENTO", idAgend);
        sesionDatosUsuario.setAttribute("ITEM_AGENDAMIENTO", itemAgend);
        request.setAttribute("ITEM_AGENDAMIENTO", itemAgend);
        return acceso;
    }
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelAgendamiento metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
        // COMBO MEDICO 
        String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_IDMED = (String) request.getParameter("cbxAddNewMed"); // medico id 
        if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty()) { PARAM_TXT_IDMED = ""; }
        System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
        System.out.println("_   MEDICO_ID   :   "+PARAM_TXT_IDMED);
        // COMBO ESPECIALIDAD 
        String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_IDESPE = (String) request.getParameter("cbxAddNewEspe"); // especialidad id 
        if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty()) { PARAM_TXT_IDESPE = ""; }
        System.out.println("_   CHECK_ESPECIALIDAD:   "+checkEspeFiltro);
        System.out.println("_   ESPECIALIDAD_ID   :   "+PARAM_TXT_IDESPE);
        // COMBO ESTADO  
        String checkEstadoFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL ESTADO QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_ESTADO = (String) request.getParameter("cE"); // ESTADO  
        if (PARAM_TXT_ESTADO == null || PARAM_TXT_ESTADO.isEmpty()) { PARAM_TXT_ESTADO = "A"; }
        System.out.println("_   CHECK_ESTADO:   "+checkEstadoFiltro);
        System.out.println("_   ESTADO   :   "+PARAM_TXT_ESTADO);
        // CONTROL DEL CHECK DE MEDICO 
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN MEDICO  
        if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO  
            checkMedicoFiltro = "OFF";
        } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_IDMED = "";
        }
        // CONTROL DEL CHECK DE ESPECILIDAD
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR LA ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESPECIALIDAD  
        if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
            checkEspeFiltro = "OFF";
        } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_IDESPE = "";
        }
        // CONTROL DEL CHECK DE ESTADO 
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL ESTADO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL ESTADO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESTADO  
        if (checkEstadoFiltro == null || checkEstadoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
            checkEstadoFiltro = "OFF";
        } else if (checkEstadoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_ESTADO = "";
        }

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDESPE, PARAM_TXT_ESTADO);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "agend.htm";
//        var_redireccionar = 1;
        request.setAttribute("CA_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CA_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CA_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CA_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
        request.setAttribute("CA_TXT_FIL_ID_MED", PARAM_TXT_IDMED);
        request.setAttribute("CA_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
        request.setAttribute("CA_TXT_FIL_ID_ESPE", PARAM_TXT_IDESPE);
        request.setAttribute("CA_CHECK_FILTRAR_ESTADO_01", checkEstadoFiltro);
        request.setAttribute("CA_TXT_FIL_ESTADO", PARAM_TXT_ESTADO);
        sesionDatosUsuario.setAttribute("CA_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    
} // END CLASS 