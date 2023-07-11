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
import com.agend.javaBean.BeanCuentaCliente;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelCuentaCliente;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerCuentaCliente", urlPatterns="/CCCSrv")
//@SessionAttributes( "IDUSUARIO_USERLOGIN" ) // si este atributo no existe o no esta habilitado suelta una excepcion 
public class ControllerCuentaCliente extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    @RequestMapping("/cuenta_cliente")
//    public ModelAndView cuenta_cliente(Model model, @ModelAttribute(value="JSESSIONID") String cookie, @ModelAttribute(value="IDUSUARIO_USERLOGIN") String iduser, HttpServletRequest request) {
//    public ModelAndView cuenta_cliente(@CookieValue("JIDUSER") String IDUSERLOGIN, HttpServletRequest request, HttpServletResponse response) {
    public ModelAndView cuenta_cliente(Model model, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        System.out.println("[+]-----__1__---------CONTROLLER__CUENTA_CLIENTE-------------------MODEL_AND_VIEW-------------");
        
//        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(false); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String SESION_IDUSER = (String) request.getSession().getAttribute("IDPERSONA");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        if (SESION_IDUSER == null) {
            System.out.println("[-] sesion-id-user var is null - ");
//            SESION_IDUSER = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
//            System.out.println("[*] cookie value return the id-user is = "+SESION_IDUSER);
        } else {
            System.out.println("[+] sesion-id-user var is not null - it value is = "+SESION_IDUSER);
        }
//        System.out.println(".   _1_CCC__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) request.getSession().getAttribute("IDPERFIL");
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        if (IDPERFIL_USER == null) {
            System.out.println("[-] id-perfil-user var is null - ");
//            IDPERFIL_USER = modeloIniSes.getCookie(request, "IDPERFIL").getValue();
//            System.out.println("[*] cookie value return the id-perfil is = "+IDPERFIL_USER);
        } else {
            System.out.println("[+] id-perfil-user var is not null - it value is = "+IDPERFIL_USER);
        }
//        System.out.println(".   _1_CCC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        int bandParameter=0; // variable para cargar a la sesion variables que usare en el jsp para definir algunos parametros ej.; el menu lateral si estara abierto o no.-
        String paginaMav = "menu_principal";
        /* OBSERVACION: LE AGREGO UNA CONDICIONAL PARA MOSTRARLE DIRECTAMENTE LA PAGINA DONDE SE VE LAS CUENTAS AL PERFIL DE PACIENTE Y SI FUERA OTRO PERFIL ENTONCES LE MOSTRARIA LA PAGINA PRINCIPAL PARA FILTRAR DESDE AHI AL PACIENTE QUE QUISIERA VER SU CUENTA  */
        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)==true) { // 4 : PACIENTE 
            bandParameter = 0;
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente_VerCta", request);
//            sesionDatosUsuario.setAttribute("ID_CLIENTE", SESION_IDUSER);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER)==true || modeloPerfil.isPerfilSecre(IDPERFIL_USER)==true || modeloPerfil.isPerfilFuncio(IDPERFIL_USER)==true /*|| modeloPerfil.isPerfilMedico(IDPERFIL_USER)==true*/) { //  1 : ADMIN  -  3 : SECRETARIO 
            bandParameter = 1;
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else {
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "menu_principal", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // PASO LOS PARAMETROS DEACUERDO A LA BANDERA 
        if (bandParameter == 0) {
//            if (sesionDatosUsuario == null) {
//                response = modeloIniSes.setCookie(response, "ID_CLIENTE", SESION_IDUSER);
//                response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "1");
//            } else {
                sesionDatosUsuario.setAttribute("ID_CLIENTE", SESION_IDUSER);
                sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            }
        } else {
//            if (sesionDatosUsuario == null) {
//                response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "0");
//            } else {
                sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            }
        }
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        request.setAttribute("CCC_CHECK_FILTRAR_CLI", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        mav.setViewName(paginaMav);
        return mav;
    }
    
    // [OBS] :ESTE CONTROLADOR ERA EL QUE MAS UTILIZABA PARA REALIZAR EJEMPLOS DE FUNCIONAMIENTO EN LA VPS AL CONFIGURAR TODO, CON ESTE CONTROLADOR ME DI CUENTA DEL PROBLEMA DE SESIONES QUE TENIAN TODAS LAS PAGINAS.- 
    // [OBS] :METODO ANTERIOR QUE LO TENIA A MODO DE EJEMPLO UTILIZANDO SESSION Y COOKIES - SESSION DE PRIMERA INSTANCIA Y CONTROLANDO CADA VALOR PARA QUE SI DEVUELVE NULL BUSQUE EL MISMO VALOR PERO EN LA COOKIE - COMENTO ESTE METODO PORQUE EN LA VPS ME DABA ERROR LA SESION Y EN LOCAL ME DABA ERROR LA COOKIE (Y VICEVERSA, EN LA VPS ME ANDABA LA COOKIE Y EN LOCAL LA SESSION) ACA INTENTA IMPLEMENTAR LAS COOKIES A DUO DE LA SESION 
//    @RequestMapping("/cuenta_cliente")
//    public ModelAndView cuenta_cliente(@CookieValue("JIDUSER") String IDUSERLOGIN, HttpServletRequest request, HttpServletResponse response) {
//        ModelAndView mav = new ModelAndView();
//        
//        System.out.println("--------------------CONTROLER-CTA-CLIE---------------(cookie)--------");
//        System.out.println("-----ID_USER_LOGIN =' "+IDUSERLOGIN+" '=");
//        System.out.println(".....................................................................");
//        
////        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
//        HttpSession sesionDatosUsuario = request.getSession(true); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
//        
//        System.out.println("-----__1__---------CONTROLLER__CUENTA_CLIENTE-------------------MODEL_AND_VIEW-------------");
//        String SESION_IDUSER = (String) request.getSession().getAttribute("IDPERSONA");
////        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        if (SESION_IDUSER == null) {
//            System.out.println("[-] sesion-id-user var is null - ");
//            SESION_IDUSER = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
//            System.out.println("[*] cookie value return the id-user is = "+SESION_IDUSER);
//        } else {
//            System.out.println("[+] sesion-id-user var is not null - it value is = "+SESION_IDUSER);
//        }
////        System.out.println(".   _1_CCC__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        String IDPERFIL_USER = (String) request.getSession().getAttribute("IDPERFIL");
////        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        if (IDPERFIL_USER == null) {
//            System.out.println("[-] id-perfil-user var is null - ");
//            IDPERFIL_USER = modeloIniSes.getCookie(request, "IDPERFIL").getValue();
//            System.out.println("[*] cookie value return the id-perfil is = "+IDPERFIL_USER);
//        } else {
//            System.out.println("[+] id-perfil-user var is not null - it value is = "+IDPERFIL_USER);
//        }
////        System.out.println(".   _1_CCC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
//        
//        int bandParameter=0;
//        String paginaMav = "menu_principal";
//        /* OBSERVACION: LE AGREGO UNA CONDICIONAL PARA MOSTRARLE DIRECTAMENTE LA PAGINA DONDE SE VE LAS CUENTAS AL PERFIL DE PACIENTE Y SI FUERA OTRO PERFIL ENTONCES LE MOSTRARIA LA PAGINA PRINCIPAL PARA FILTRAR DESDE AHI AL PACIENTE QUE QUISIERA VER SU CUENTA  */
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)==true) { // 4 : PACIENTE 
//            bandParameter = 0;
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente_VerCta", request);
////            sesionDatosUsuario.setAttribute("ID_CLIENTE", SESION_IDUSER);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER)==true || modeloPerfil.isPerfilSecre(IDPERFIL_USER)==true || modeloPerfil.isPerfilFuncio(IDPERFIL_USER)==true /*|| modeloPerfil.isPerfilMedico(IDPERFIL_USER)==true*/) { //  1 : ADMIN  -  3 : SECRETARIO 
//            bandParameter = 1;
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        } else {
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "menu_principal", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente", request);
//        System.out.println("pagina_mav:     "+paginaMav);
//        // PASO LOS PARAMETROS DEACUERDO A LA BANDERA 
//        if (bandParameter == 0) {
//            if (sesionDatosUsuario == null) {
//                response = modeloIniSes.setCookie(response, "ID_CLIENTE", SESION_IDUSER);
//                response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "1");
//            } else {
//                sesionDatosUsuario.setAttribute("ID_CLIENTE", SESION_IDUSER);
//                sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            }
//        } else {
//            if (sesionDatosUsuario == null) {
//                response = modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "0");
//            } else {
//                sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//            }
//        }
//        
//        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
////        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        request.setAttribute("CCC_CHECK_FILTRAR_CLI", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        mav.setViewName(paginaMav);
//        return mav;
//    }
    
    
    
    @RequestMapping("/cuenta_cliente_ver")
    public ModelAndView cuenta_cliente_ver(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        
        System.out.println("[+]-----__2__---------CONTROLLER__CUENTA_CLIENTE-----------------MODEL_AND_VIEW-------------");
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("[*]__2_CCC__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        if (SESION_IDUSER == null) {
            System.out.println("[-] sesion-id-user var is null - ");
        } else {
            System.out.println("[+] sesion-id-user var is not null - it value is = "+SESION_IDUSER);
        }
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println("[*]__2_CCC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        if (IDPERFIL_USER == null) {
            System.out.println("[-] id-perfil-user var is null - ");
        } else {
            System.out.println("[+] id-perfil-user var is not null - it value is = "+IDPERFIL_USER);
        }
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuCuentaCliente(IDPERFIL_USER)==true) { // 4 : PACIENTE 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente_VerCta", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagCuentaCliente_VerCta", request);
        System.out.println("[+] pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USUARIO 
        ModelCuentaCliente metodosCtaCliente = new ModelCuentaCliente();
        
        int var_redireccionar = 0;
        String acceso = "cuenta_cliente.htm";
        String accion = request.getParameter("accion");
        String idPersona, idCuentaCliente;
        
        try {
            System.out.println("__ACCION:   "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("_   _doPost__ID_PERSONA:    "+idPersona);
            
            /*
                EVENTOS.---------------------------
            */
            if (accion.equalsIgnoreCase("Ver")) {// VER SI LA USARE EN EL ANULAR AGEND O SI LA DEVOLVERE AL BLOQUE QUE TENGO COMENTADO Y ELIMINAR EL METODO 
                System.out.println("------------------------__VER_CUENTAS_DEL_CLIENTE__------------------------------");
                acceso = ver_cuenta_paciente(sesionDatosUsuario, request);
//                String idCliente = (String) request.getParameter("tIC"); // tIC : txt id cliente 
//                System.out.println("_   _ID_CLIENTE:    "+idCliente);
                var_redireccionar = 1;
//                acceso = "cuenta_cliente_ver.htm";
//                sesionDatosUsuario.setAttribute("ID_CLIENTE", idCliente);
                
            } else if (accion.equalsIgnoreCase("VER_AGENDAMIENTO_PAC")) {
                System.out.println("------------------------__VER_AGENDAMIENTO_DEL_PACIENTE__------------------------------");
                /*
                * OBSERVACION: PARA NO ESTAR COPIANDO Y PEGANDO EL MISMO CODIGO QUE UTILIZO EN EL CONTROLADOR DE FACTURA 
                    ENTONCES SU CODIGO LO COLOQUE EN UN METODO EN SU MISMO CONTROLADOR, ENTONCES ESTARE LLAMANDO A ESE METODO 
                    PARA EVITAR COPIAR Y PEGAR EL CODIGO Y ASI TENER QUE ESTAR ACTUALIZANDO EN AMBOS LADOS Y MAS SI ES QUE SE LLEGA A UTILIZAR EN MAS LADOS. 
                */
                ControllerAgendamiento controllerAgen = new ControllerAgendamiento();
                var_redireccionar = 1;
                acceso = controllerAgen.ver_agendamiento_anular(sesionDatosUsuario, request, acceso);
//                acceso = "ver_agen.htm";
////                    acceso = "ver_agend.htm";
                sesionDatosUsuario.setAttribute("AGEND_BOTON_VOLVER_ATRAS", "2"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOVLER ATRAS" DE LA PAGINA DE VER_FACTURA Y ASI NO ME VUELVA ATRAS EN FACTURA SINO EN RESUMEN FACTURA 
                String ID_CLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                sesionDatosUsuario.setAttribute("AGEND_IDPACIENTE_CTAVER", ID_CLIENTE);
                sesionDatosUsuario.setAttribute("AGEND_BTN_ANULAR", "0");// OBSERVACION_2: COMO ESTOY REDIRECCIONANDO DESDE CUENTA CLIENTE, ENTONCES YO LE QUIERO MOSTRAR LOS DATOS DEL AGENDAMIENTO NOMAS Y NO MOSTRARLE EL BOTON DE ANULAR, ES POR ESO QUE LE PASO AL JSP CON EL NUMERO 0 PARA EVITAR CARGAR EL BTN DE ANULAR   // OBSERVACION_1: VARIABLE QUE UTILIZO PARA PODER ACTIVARLE EL BOTON DE ANULAR EN LA PAGINA DE "VER_AGENDAMIENTO" 
                request.setAttribute("AGEN_ANULAR_PERMITIR", "1"); // VARIABLE QUE LE CARGO PARA QUE LA PAGINA DE VER AGENDAMIENTO DE ANULAR NO MUESTRE EL BOTON PARA QUE PUEDA ANULAR EL AGENDAMIENTO 
                
            } else if (accion.equalsIgnoreCase("Filtrar")) {
                System.out.println("------------------------__FILTRAR__------------------------------");
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
                listaFiltro = metodosCtaCliente.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtClinicaId);
                
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "cuenta_cliente.htm";
                var_redireccionar = 1;
                request.setAttribute("CCC_CBX_MOSTRAR", filtro_cbxMostrar);
                request.setAttribute("CCC_TXT_BUSCAR", filtro_txtBuscar);
                request.setAttribute("CCC_LISTA_FILTRO", listaFiltro);
                request.setAttribute("CCC_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
                request.setAttribute("CCC_TXT_FIL_ID_CLI", txtClinicaId);
                sesionDatosUsuario.setAttribute("CCC_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                
            } else if(accion.equalsIgnoreCase("Filtrar Cuenta")) {
                System.out.println("------------------------__FILTRAR_CUENTA__------------------------------");
                String txtFiltrarCta = (String) request.getParameter("tFC"); // tFC: TXT FILTRAR CUENTA 
                System.out.println("_   _TXT_FILTRAR_CTA:    :"+txtFiltrarCta);
                String IDCLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                System.out.println("_   _TXT_ID_CLIENTE:     :"+IDCLIENTE);
                
                // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                List<BeanCuentaCliente> listaFiltro = new ArrayList<>();
                listaFiltro = metodosCtaCliente.filtrarCtaEmp(sesionDatosUsuario, IDCLIENTE, txtFiltrarCta, "");
                
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "cuenta_cliente_ver.htm";
                var_redireccionar = 1;
                sesionDatosUsuario.setAttribute("ID_CLIENTE", IDCLIENTE);
                request.setAttribute("CC_FILTRAR_CUENTA", txtFiltrarCta);
                request.setAttribute("CCC_LISTA_FILTRO_CTA", listaFiltro);
                
            } else if(accion.equalsIgnoreCase("Limpiar Filtro")) {
                System.out.println("------------------------__LIMPIAR_FILTRAR__------------------------------");
                String IDCLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                System.out.println("_   _TXT_ID_CLIENTE:    :"+IDCLIENTE);
                
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "cuenta_cliente_ver.htm";
                var_redireccionar = 1;
                sesionDatosUsuario.setAttribute("ID_CLIENTE", IDCLIENTE);
                request.setAttribute("CC_FILTRAR_CUENTA", "");
                request.setAttribute("CCC_LISTA_FILTRO_CTA", null);
                
            } else if (esNumero(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(request, sesionDatosUsuario, metodosCtaCliente);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase(">>")) {
                System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accion+" :__--------------------------");
                var_redireccionar = 1;
                String PAG_CTAPAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_CTAPAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_CTAPAC_CANT_NRO_CLIC == null) {
                    PAG_CTAPAC_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_CTAPAC_CANT_NRO_CLIC);
                String PAG_CTAPAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_CTAPAC_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_CTAPAC_LISTA_ACTUAL);
                
                PAG_CTAPAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_CTAPAC_CANT_NRO_CLIC)+1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_CTAPAC_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_LISTA_ACTUAL", PAG_CTAPAC_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_CTAPAC_CANT_NRO_CLIC)+1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(request, sesionDatosUsuario, metodosCtaCliente);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase("<<")) {
                System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accion+" :__--------------------------");
                var_redireccionar = 1;
                String PAG_CTAPAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_CTAPAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_CTAPAC_CANT_NRO_CLIC == null) {
                    PAG_CTAPAC_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_CTAPAC_CANT_NRO_CLIC);
                String PAG_CTAPAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_CTAPAC_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_CTAPAC_LISTA_ACTUAL);

                PAG_CTAPAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_CTAPAC_CANT_NRO_CLIC)-1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_CTAPAC_LISTA_ACTUAL);

                sesionDatosUsuario.setAttribute("PAG_CTAPAC_LISTA_ACTUAL", PAG_CTAPAC_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_CTAPAC_CANT_NRO_CLIC)-1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CCC_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(request, sesionDatosUsuario, metodosCtaCliente);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
                
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
            } else if (accion.equalsIgnoreCase("Limpiar")) {
                System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                var_redireccionar = 1;
                acceso = "cuenta_cliente.htm";
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_CANT_BTN_DERE_CLIC", "1");
                sesionDatosUsuario.setAttribute("PAG_CTAPAC_LISTA_ACTUAL", "1");
                sesionDatosUsuario.setAttribute("CCC_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-----------------ERROR-CUENTA_CLIENTE--------------------");
            String TIPO_MENSAJE = "2";
            String MENSAJE = "Se ha producido un error, intentelo mas tarde.";
            request.setAttribute("CCC_TIPO_MENSAJE", TIPO_MENSAJE);
            request.setAttribute("CCC_MENSAJE", MENSAJE);
            var_redireccionar = 1;
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerReportes.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("---------------------------------------------------------");
        }
        
// OBSERVACION: 
// UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if (var_redireccionar == 0) {
            response.sendRedirect(acceso);
        } else {
            RequestDispatcher vista = request.getRequestDispatcher(acceso);
            vista.forward(request, response);
        }
    } // END doPost 
    
    
    
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
    
    
    private void filtrar_pag(HttpServletRequest request, HttpSession sesionDatosUsuario, ModelCuentaCliente metodosCtaCliente) {
        System.out.println("------------------------__FILTRAR__------------------------------");
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
        listaFiltro = metodosCtaCliente.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtClinicaId);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "cuenta_cliente.htm";
//        var_redireccionar = 1;
        request.setAttribute("CCC_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CCC_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CCC_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CCC_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
        request.setAttribute("CCC_TXT_FIL_ID_CLI", txtClinicaId);
        sesionDatosUsuario.setAttribute("CCC_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    
    // METODO QUE UTILIZO PARA PODER VER LA CUENTA DEL PACIENTE, LO COLOQUE EN UN METODO ESTE BLOQUE DE CODIGO PORQUE LO UTILIZO EN EL CONTROLADOR DE AGENDAMIENTO TAMBIEN Y NO SOLO EN EL CONTROLADOR DE CUENTA CLIENTE 
    public String ver_cuenta_paciente(HttpSession sesionDatosUsuario, HttpServletRequest request) {
        String idCliente = (String) request.getParameter("tIC"); // tIC : txt id cliente 
        System.out.println("_   _ID_CLIENTE:    "+idCliente);
//        var_redireccionar = 1;
        String acceso = "cuenta_cliente_ver.htm";
        sesionDatosUsuario.setAttribute("ID_CLIENTE", idCliente);
        return acceso;
    }
    
    
} // END CLASS 