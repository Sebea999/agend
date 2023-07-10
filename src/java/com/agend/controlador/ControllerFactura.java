/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanCuentaCliente;
import com.agend.javaBean.BeanFacturaCab;
import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanServicio;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelAgendamiento;
import com.agend.modelo.ModelFactura;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPerfil;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelRef_Clinica;
import com.agend.modelo.ModelRef_Servicio;
import com.agend.modelo.pdf.obtener_letra_num;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerFactura", urlPatterns="/CFSrv")
public class ControllerFactura extends HttpServlet {
    
    
    ModelInicioSesion modeloIniSes = new ModelInicioSesion();
    ModelPerfil metodosPerfil = new ModelPerfil();
    
    
    
    @RequestMapping("/factura")
    public ModelAndView factura(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _1_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _1_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/crear_factura")
    public ModelAndView crear_factura(HttpServletRequest request) { // CABECERA 
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__2__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _2_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _2_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _2_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CobrarCab", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CobrarCab", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/crear_factura_cta")
    public ModelAndView crear_factura_cta(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__3__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _3_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _3_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _3_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CtaCliente", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CtaCliente", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/crear_factura_serv")
    public ModelAndView crear_factura_servicio(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__4__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _4_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _4_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _4_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CargarServicio", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CargarServicio", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/crear_factura_pro")
    public ModelAndView crear_factura_producto(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__4__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _4_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _4_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _4_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CargarProd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CargarProd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/crear_factura_det")
    public ModelAndView crear_factura_det(HttpServletRequest request) { // DETALLE 
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__5__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _5_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _5_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _5_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CobrarDet", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_CobrarDet", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/ver_factura")
    public ModelAndView ver_factura(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__6__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _6_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _6_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _6_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_VerFact", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_VerFact", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/factura_add_int")
    public ModelAndView factura_add_int(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__7__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _7_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _7_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _7_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_AddInteresDet", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_AddInteresDet", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/factura_add_desc")
    public ModelAndView factura_add_descuento(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__8__---------CONTROLLER__FACTURA--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _8_CF__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
        System.out.println(".   _8_CF__ID_FACTURA:     :"+ID_FACTURA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _8_CF__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (metodosPerfil.isMenuFactura(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_AddDescuentoDet", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFactura_AddDescuentoDet", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA);
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
            throws IOException, ServletException {
        
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        obtener_letra_num obtener_letra = new obtener_letra_num();
        
        HttpSession sesionDatosUsuario = request.getSession(); // RECUPERO LA SESION POR MEDIO DEL REQUEST PARA RECUPERAR LOS DATOS DEL USUARIO QUE VOY CARGANDO EN DISTINTOS CONTROLADORES 
        ModelFactura metodosFactura = new ModelFactura();
        ModelPersona metodosPersona = new ModelPersona();
        ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        DecimalFormat formatear = new DecimalFormat("###,###,###");
        
        int var_redireccionar = 0;
        String acceso = "factura.htm";
        String accion = request.getParameter("accion");
//        if (accion == null) {
//            accion = "";
//        }
        String accionPag = request.getParameter("accionPag");
//        if (accionPag == null) {
//            accionPag = "";
//        }
        String accionFCC = request.getParameter("accionFCC"); // FACTURA CUENTA CLIENTE  / pagFactura_CtaCliente.jsp 
//        if (accionFCC == null) {
//            accionFCC = "";
//        }
        String accionFCP = request.getParameter("accionFCP"); // FACTURA CARGAR PRODUCTO  / pagFactura_CargarProducto.jsp 
//        if (accionFCP == null) {
//            accionFCP = "";
//        }
        String accionFCS = request.getParameter("accionFCS"); // FACTURA CARGAR SERVICIO  / pagFactura_CargarServicio.jsp 
//        if (accionFCS == null) {
//            accionFCS = "";
//        }
        String accionFCI = request.getParameter("accionFCI"); // FACTURA CARGAR INTERES  / pagFactura_AddInteresDet.jsp 
//        if (accionFCI == null) {
//            accionFCI = "";
//        }
        String accionFCD = request.getParameter("accionFCD"); // FACTURA CARGAR DESCUENTO  / pagFactura_AddDescuentoDet.jsp 
//        if (accionFCD == null) {
//            accionFCD = "";
//        }
        String idPersona;
        
        try {
            System.out.println("__ACCION:   "+accion);
            System.out.println("__ACCION_PAG: (PAGINACION)       :"+accionPag);
            System.out.println("__ACCION_FCC: (CUENTA_CLIENTE)   :"+accionFCC);
            System.out.println("__ACCION_FCS: (CARGAR_SERVICIO)  :"+accionFCS);
            System.out.println("__ACCION_FCP: (CARGAR_PRODUCTO)  :"+accionFCP);
            System.out.println("__ACCION_FCI: (CARGAR_INTERES)   :"+accionFCI);
            System.out.println("__ACCION_FCD: (CARGAR_DESCUENTO) :"+accionFCD);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("_   _   _doPost__ID_PERSONA:    "+idPersona);
            // PARA ENCONTRAR INFORMACION DE ESTA VARIABLE IR AL JSP "pagFactura_CobrarCab.jsp" HAY DEJE NOTAS Y OBSERVACIONES DE LA VARIABLE 
            String CF_VALI_MOSTRAR_BTN_DETALLE = (String) sesionDatosUsuario.getAttribute("CF_VALI_MOSTRAR_BTN_DETALLE");
            System.out.println("__1__CONTROLADOR____CF_VALI_MOSTRAR_BTN_DETALLE:  "+CF_VALI_MOSTRAR_BTN_DETALLE);
            if (CF_VALI_MOSTRAR_BTN_DETALLE == null || CF_VALI_MOSTRAR_BTN_DETALLE.equals("1")) { CF_VALI_MOSTRAR_BTN_DETALLE = "0"; }
            
            
            /*
                EVENTOS.---------------------------
            */
            if(accionPag != null) { //  -----------     PAGINACION DE LA PAGINA DE FACTURA    ------------ 
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__ACCION_PAGINACION__--------------------------");
                System.out.println(".");System.out.println(".");
                if (esNumero(accionPag) == true) {
                    System.out.println("---------------------__PAGINACION_NUMBER_: "+accionPag+" :__--------------------------");
                    // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                    sesionDatosUsuario.setAttribute("PAG_FACT_LISTA_ACTUAL", accionPag);
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodosFactura);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    
                } else if (accionPag.equalsIgnoreCase(">>")) {
                    System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accionPag+" :__--------------------------");
                    String PAG_FACT_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_FACT_CANT_NRO_CLIC == null) {
                        PAG_FACT_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_FACT_CANT_NRO_CLIC);
                    String PAG_FACT_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_FACT_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_FACT_LISTA_ACTUAL);
                    
                    PAG_FACT_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_FACT_CANT_NRO_CLIC)+1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_FACT_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_FACT_LISTA_ACTUAL", PAG_FACT_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_FACT_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_FACT_CANT_NRO_CLIC)+1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodosFactura);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    
                } else if (accionPag.equalsIgnoreCase("<<")) {
                    System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accionPag+" :__--------------------------");
                    String PAG_FACT_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_FACT_CANT_NRO_CLIC == null) {
                        PAG_FACT_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_FACT_CANT_NRO_CLIC);
                    String PAG_FACT_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_FACT_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_FACT_LISTA_ACTUAL);
                    
                    PAG_FACT_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_FACT_CANT_NRO_CLIC)-1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_FACT_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_FACT_LISTA_ACTUAL", PAG_FACT_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_FACT_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_FACT_CANT_NRO_CLIC)-1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CF_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodosFactura);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                }
                
                
            } else if (accion != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__ACCION__--------------------------");
                System.out.println(".");System.out.println(".");
                if (accion.equalsIgnoreCase("Ver")) {
                    var_redireccionar = 1;
                    acceso = "ver_factura.htm";
                    ver_factura(request, sesionDatosUsuario, metodosFactura, metodosPersona, formatear, idPersona, var_redireccionar, acceso);
//                    sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "0"); // VARIABLE QUE UTILIZO PARA CONFIGURAR EL REDIRECCIONAR DEL BOTON "VOLVER ATRAS" DE LA PAGINA DE VER_FACTURA 
                    /*
                     * BLOQUE DE CODIGO PARA IDENTIFICAR DE QUE PAGINA VIENE A VER LA FACTURA Y ASI CARGAR 
                        LA VARIABLE AUXILIAR QUE UTILIZO EN LA PAGINA DE VER FACTURA PARA CONFIGURAR EL BOTON DE VOLVER ATRAS 
                    */
                    String band_volverAtras = (String) request.getParameter("bvaf");
                    System.out.println("_    __CTRL_FACT_(VF)____band_volver_atras:   :"+band_volverAtras);
                    if (band_volverAtras == null || band_volverAtras.isEmpty() || band_volverAtras.equals("1")) { // factura 
                        sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "0");
                    } else if (band_volverAtras.equals("3")) { // cuenta cliente 
                        sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "3");
                    } else if (band_volverAtras.equals("2")) { // anular factura 
                        sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "2");
                    }  else { // factura 
                        sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "0");
                    }
            /*
            * OBSERVACION_DEL_PORQUE_AGREGUE_EL_CODIGO_EN_UN_METODO: 
                EL CODIGO SIGUIENTE ES EL QUE SE ENCUENTRA EN EL METODO, LO PUSE EN UN METODO 
                PORQUE EN RESUMEN FACTURA UTILIZO TAMBIEN EL BOTON PARA VER LA FACTURA, 
                Y PARA NO CREAR OTRA PAGINA NI COPIAR Y PEGAR TODO EL CODIGO, ENTONCES DESDE EL CONTROLADOR 
                DE RESUMEN FACTURA, LLAMO A ESTE METODO QUE CARGA TODOS LOS DATOS Y CON OTRA VARIABLE DE LA SESION 
                CONFIGURO EL REDIRECCIONAR DE LA PAGINA DEL BOTON DE VOLVER ATRAS... Y INGRESE EL CODIGO EN UN METODO 
                PARA EVITAR TENER QUE ESTAR ACTUALIZANDO PEQUEÑAS PARTES DE CODIGO EN UN LUGAR O EN OTRO Y ASI SIEMPRE EDITAR 
                EL CODIGO DESDE EL METODO Y ASI NO TENER QUE ESTAR EDITANDO CODIGO DESDE OTRO CONTROLADOR, LAS VARIABLES 
                DE VAR_REDIRECCIONAR Y ACCESO, SE DEBEN DE CARGAR POR FUERA DEL METODO, PORQUE NO SE CARGAN LOS VALORES O NO 
                SE MANTIENEN SI LOS COLOCO DENTRO DEL METODO.
            */
    //                System.out.println("---------------------__VER_FACTURA__--------------------------");
    //                String idFactura = (String) request.getParameter("tIF"); // tIF : txt id factura 
    //                System.out.println("_doPost__ID_FACTURA:    "+idFactura);
    //                
    //                // OBTENGO LOS DATOS DE LA FACTURA POR MEDIO DEL ID Y CARGO LAS VARIABLES 
    //                List<BeanFacturaCab> listaFactura = metodosFactura.traer_datos(idFactura);
    //                Iterator<BeanFacturaCab> iterVerFact = listaFactura.iterator();
    //                BeanFacturaCab datos = new BeanFacturaCab();
    //                
    //                // ESTABLECER LAS VARIABLES PARA CABECERA Y AL RECORRER CARGAR Y LUEGO PASAR A LA SESION 
    //                int nroItem = 0;
    //                String TXT_CLIENTE_ID="", TXT_CLIENTE_CINRO="", TXT_CLIENTE_NAME="", TXT_CLIENTE_RS="", TXT_CLIENTE_RUC="", TXT_NRO_FACTURA="", CBX_TIPO_FACTURA="", TXT_FECHA_FACTURA="", TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="";
    //                // INSTANCIO UNA NUEVA LISTA PARA CARGAR LOS DATOS DEL DETALLE DE LA FACTURA 
    //                List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<>();
    //                
    //                while(iterVerFact.hasNext()) {
    //                    datos = iterVerFact.next();
    //                    
    //                    // CARGO LAS VARIABLES 
    //                    nroItem = nroItem + 1;
    //                    
    //                    // CARGO LA CABECERA 
    //                    TXT_CLIENTE_ID = String.valueOf(datos.getOF_IDCLIENTE());
    //                    TXT_NRO_FACTURA = datos.getOF_NROFACTURA();
    //                    CBX_TIPO_FACTURA = datos.getOF_TIPOFACTURA();
    //                    TXT_FECHA_FACTURA = datos.getOF_FECHAFACTURA();
    //                    TXT_TOTAL_IVA5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA5()));
    //                    TXT_TOTAL_IVA10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA10()));
    //                    TXT_TOTAL_GRAV5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV5()));
    //                    TXT_TOTAL_GRAV10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV10()));
    //                    TXT_TOTAL_IVA = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA()));
    //                    TXT_TOTAL = formatear.format(Double.parseDouble(datos.getOF_TOTAL_FACTURA()));
    //                    
    //                    // CARGO EL DETALLE CF_LISTA_DETALLE
    //                    BeanFacturaCab datoss = new BeanFacturaCab();
    //                        datoss.setOFD_ITEM(datos.getOFD_ITEM());
    //                        datoss.setOFD_IDCONCEPTO(datos.getOFD_IDCONCEPTO());
    //                        datoss.setOFD_IDTIPOCONCEPTO(datos.getOFD_IDTIPOCONCEPTO());
    //                        datoss.setOFD_DESCRIPCION(datos.getOFD_DESCRIPCION());
    //                        datoss.setOFD_CANTIDAD(datos.getOFD_CANTIDAD());
    //                        datoss.setOFD_PRECIO(formatear.format(Double.parseDouble(datos.getOFD_PRECIO())));
    //                        datoss.setOFD_IDIMPUESTO(datos.getOFD_IDIMPUESTO());
    //                        datoss.setOFD_EXENTO(formatear.format(Double.parseDouble(datos.getOFD_EXENTO())));
    //                        datoss.setOFD_IVA5(formatear.format(Double.parseDouble(datos.getOFD_IVA5())));
    //                        datoss.setOFD_IVA10(formatear.format(Double.parseDouble(datos.getOFD_IVA10())));
    //                        datoss.setOFD_GRAV5(formatear.format(Double.parseDouble(datos.getOFD_GRAV5())));
    //                        datoss.setOFD_GRAV10(formatear.format(Double.parseDouble(datos.getOFD_GRAV10())));
    //                        datoss.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(datos.getOFD_SUBTOTAL())));
    //                    CF_LISTA_DETALLE.add(datoss);
    //                } // end while 
    //                
    //                // OBTENGO LOS DATOS DEL CLIENTE 
    //                BeanPersona datosCliente = new BeanPersona();
    //                datosCliente = metodosPersona.datosBasicosPersona(datosCliente, TXT_CLIENTE_ID);
    //                TXT_CLIENTE_CINRO = datosCliente.getRP_CINRO();
    //                TXT_CLIENTE_NAME = datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
    //                TXT_CLIENTE_RS = datosCliente.getRP_RAZON_SOCIAL();
    //                TXT_CLIENTE_RUC = datosCliente.getRP_RUC();
    //                
    //                var_redireccionar = 1;
    //                acceso = "ver_factura.htm";
    //                sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
    //                sesionDatosUsuario.setAttribute("ID_FACTURA", idFactura);
    //                // VARIABLES QUE USO -
    //                sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
    //                sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
    //                sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
    //                sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RS);
    //                sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
    //                sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
    //                sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
    //                sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
    //                // DETALLE - 
    ////                sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(nroItem));
    //                sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
    //                // TOTALES - 
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", TXT_TOTAL_IVA5);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", TXT_TOTAL_GRAV5);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", TXT_TOTAL_IVA10);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", TXT_TOTAL_GRAV10);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", TXT_TOTAL_IVA);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", TXT_TOTAL);
    //                
                } else if (accion.equalsIgnoreCase("Volver Atras Cta")) { // EVENTO QUE SOLO UTILIZO PARA EL BOTON DE "VOLVER ATRAS" AL VER UNA FACTURA PARA PODER REDIRECCIONAR A LA PAGINA DE "VER CUENTA CLIENTE" 
                    System.out.println("---------------------__VOLVER_ATRAS_CUENTA_CLIENTE__--------------------------");
                    String idCliente = (String) request.getParameter("tIC"); // tIC : txt id cliente 
                    System.out.println("_   _ID_CLIENTE:    "+idCliente);
                    var_redireccionar = 1;
                    acceso = "cuenta_cliente_ver.htm";
                    sesionDatosUsuario.setAttribute("ID_CLIENTE", idCliente);
//                    sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "0"); // OBS.: CREO QUE NO HAY NECESIDAD DE REINICIAR YA QUE AL VOLVER A LA PAGINA DE CUENTA CLIENTE VOLVERIA A CAMBIAR EL VALOR DE ESTA VARIABLE, POR ESO LO COMENTO, PERO POR SI LO NECESITE LO DEJO COMENTADO 
                    
                } else if (accion.equalsIgnoreCase("Crear Factura")) {
                    System.out.println("---------------------__CREAR_FACTURA__--------------------------");
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> listaDetalle = new ArrayList<>();
                    
                    var_redireccionar = 1;
                    acceso = "crear_factura.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", "1");
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", listaDetalle);
                    // CABECERA 
                    CF_VALI_MOSTRAR_BTN_DETALLE = "0";
                    sesionDatosUsuario.setAttribute("CF_VALI_MOSTRAR_BTN_DETALLE", CF_VALI_MOSTRAR_BTN_DETALLE);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", "");
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", "");
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", "");
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", "0");
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", "0");
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", "0");
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", "0");
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", "0");
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", "0");
                    
                /*
                    OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accion.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "factura.htm";
                    sesionDatosUsuario.setAttribute("PAG_FACT_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_FACT_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CF_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accion.equalsIgnoreCase("Cargar Cuenta del Cliente")) { // BTN PARA REDIRECCIONAR A LA PAGINA DE FACTURA CUENTA CLIENTE 
                    System.out.println("---------------------__CARGAR_CUENTA_DEL_CLIENTE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF"); // tANF : TXT AUXILIAR NRO FACTURA 
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF"); // cATF : COMBOBOX AUXILIAR TIPO FACTURA 
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF"); // tAFF : TXT AUXILIAR FECHA FACTURA 
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ID CLIENTE 
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC"); // tACNC : TXT AUXILIAR CLIENTE NRO CI 
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN"); // tACN : TXT AUXILIAR CLIENTE NAME 
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS"); // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL 
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR"); // tACR : TXT AUXILIAR CLIENTE RUC 
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
    //                if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
    //                    System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
    //                    CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
    //                }

                    var_redireccionar = 1;
                    acceso = "crear_factura_cta.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    int CF_LISTA_ITEM = 1;
    //                if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
    //                    CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
    //                }
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    
                }  else if (accion.equalsIgnoreCase("Cargar Servicio")) { // BTN PARA REDIRECCIONAR A LA PAGINA 
                    System.out.println("---------------------__CARGAR_SERVICIOS__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF"); // tANF : TXT AUXILIAR NRO FACTURA 
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF"); // cATF : COMBOBOX AUXILIAR TIPO FACTURA 
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF"); // tAFF : TXT AUXILIAR FECHA FACTURA 
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ID CLIENTE 
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC"); // tACNC : TXT AUXILIAR CLIENTE NRO CI 
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN"); // tACN : TXT AUXILIAR CLIENTE NAME 
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS"); // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL 
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR"); // tACR : TXT AUXILIAR CLIENTE RUC 
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    int CF_LISTA_ITEM = 1;
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
    //                if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
    //                    System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
    //                    CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
    //                }

                    var_redireccionar = 1;
                    acceso = "crear_factura_serv.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
                    request.setAttribute("CF_FCS_TXT_ID_STOCK", "");
                    request.setAttribute("CF_FCS_TXT_LOTE", "");
                    request.setAttribute("CF_FCS_TXT_STOCK", "");
                    request.setAttribute("CF_FCS_TXT_ID_SERVICIO", "");
                    request.setAttribute("CF_FCS_TXT_DESCRIPCION", "");
                    request.setAttribute("CF_FCS_TXT_MARCA", "");
                    request.setAttribute("CF_FCS_TXT_CANTIDAD", "1");
                    request.setAttribute("CF_FCS_CBX_IVA", "");
                    request.setAttribute("CF_FCS_TXT_PRECIO", "");
                    
                }  else if (accion.equalsIgnoreCase("Cargar Productos")) { // BTN PARA REDIRECCIONAR A LA PAGINA 
                    System.out.println("---------------------__CARGAR_PRODUCTOS__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF"); // tANF : TXT AUXILIAR NRO FACTURA 
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF"); // cATF : COMBOBOX AUXILIAR TIPO FACTURA 
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF"); // tAFF : TXT AUXILIAR FECHA FACTURA 
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ID CLIENTE 
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC"); // tACNC : TXT AUXILIAR CLIENTE NRO CI 
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN"); // tACN : TXT AUXILIAR CLIENTE NAME 
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS"); // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL 
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR"); // tACR : TXT AUXILIAR CLIENTE RUC 
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    int CF_LISTA_ITEM = 1;
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
    //                if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
    //                    System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
    //                    CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
    //                }

                    var_redireccionar = 1;
                    acceso = "crear_factura_pro.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
                    request.setAttribute("CF_FCP_TXT_ID_STOCK", "");
                    request.setAttribute("CF_FCP_TXT_LOTE", "");
                    request.setAttribute("CF_FCP_TXT_STOCK", "");
                    request.setAttribute("CF_FCP_TXT_ID_PRODUCTO", "");
                    request.setAttribute("CF_FCP_TXT_DESCRIPCION", "");
                    request.setAttribute("CF_FCP_TXT_MARCA", "");
                    request.setAttribute("CF_FCP_TXT_CANTIDAD", "1");
                    request.setAttribute("CF_FCP_CBX_IVA", "");
                    request.setAttribute("CF_FCP_TXT_PRECIO", "");
                    
                }  else if (accion.equalsIgnoreCase("Volver a la Cabecera")) {
                    System.out.println("---------------------__VOLVER_A_LA_CABECERA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = "0";
                    String CF_TXT_TOTAL_IVA10 = "0";
                    String CF_TXT_TOTAL_GRAV5 = "0";
                    String CF_TXT_TOTAL_GRAV10 = "0";
                    String CF_TXT_TOTAL_IVA = "0";
                    String CF_TXT_TOTAL = "0";
                    /*
                    OBSERVACION: ESTA VARIABLE SE CARGA EN EL CONTROLADOR DE PANEL DE CONTROL, PARA LAS PAGINAS QUE QUIEREN CANCELAR LAS CUENTAS, ENTONCES SI FUESE PORQUE EL USUARIO VIENE DESDE AHI, ENTONCES AHI SI LE PERMITO DEVOLVERLE LOS DATOS DEL DETALLE 
                    */
                    String RES_EMP_HBLT_BTN_ADD_IMPU = (String) sesionDatosUsuario.getAttribute("RES_EMP_HBLT_BTN_ADD_IMPU");
                    System.out.println("__JSP___RES_EMP_HBLT_BTN_ADD_IMPU:     :"+RES_EMP_HBLT_BTN_ADD_IMPU);
                    if (RES_EMP_HBLT_BTN_ADD_IMPU == null || RES_EMP_HBLT_BTN_ADD_IMPU.isEmpty() == true || RES_EMP_HBLT_BTN_ADD_IMPU.equals("0")) {
                        // REINICIO LOS VALORES DE LOS TXTS TOTALES EN CASO DE QUE NO PROVENGA DE ALGUNA PAGINA DE EMPEÑO DEL PANEL DE CONTROL 
                        CF_TXT_TOTAL_IVA5 = "0";
                        CF_TXT_TOTAL_IVA10 = "0";
                        CF_TXT_TOTAL_GRAV5 = "0";
                        CF_TXT_TOTAL_GRAV10 = "0";
                        CF_TXT_TOTAL_IVA = "0";
                        CF_TXT_TOTAL = "0";
                    } else {
                        CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                        CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                        CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                        CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                        CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                        CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    }

                    System.out.println("------------------__PARAMETER__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("-------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    // OBSERVACION: COMENTO EL CODIGO QUE RECUPERA DE LA SESION LA LISTA DEL DETALLE PARA QUE NO ME CARGUE LA MISMA LISTA SINO QUE ME LIMPIE LA LISTA CADA VEZ QUE SE VAYA A LA CABECERA PORQUE DESDE LA CABECERA PARA TENER QUE LLEGAR AL DETALLE VA A TENER QUE VOLVER A CARGAR LOS DATOS 
                    int CF_LISTA_ITEM = 1;
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if (RES_EMP_HBLT_BTN_ADD_IMPU == null || RES_EMP_HBLT_BTN_ADD_IMPU.isEmpty() == true || RES_EMP_HBLT_BTN_ADD_IMPU.equals("0")) {
                        //
                    } else {
                        // SI EL BOTON ESTA CARGADO ENTONCES AHI SI INTENTARIA RECUPERAR LA LISTA Y SU ITEM, SINO LE REINICIARIA POR LA OBSERVACION 
                        if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                            CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                        }
                        if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                            System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                            CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                        }
                    }

                    var_redireccionar = 1;
                    acceso = "crear_factura.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // REINICIO LOS VALORES DE LOS TOTALES YA QUE VA A VOLVER A LA CABECERA Y PARA REGRESAR AL DETALLE VA A TENER QUE VOLVER A CARGAR TODO DE NUEVO Y AHI VOY A VOLVER A CALCULAR LOS TOTALES, Y PARA EVITAR QUE UN DATO SE MANTENGA LOS PASO CON CERO 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);

                } else if(accion.equalsIgnoreCase("Cargar Cliente") || accion.equalsIgnoreCase("Add Cliente")) {
                    System.out.println("---------------------__CARGAR_CLIENTE__--------------------------");
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN CLIENTE 
                    // DATOS DE LA CABECERA 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tNF"); // tNF : TXT NRO FACTURA 
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cTF"); // cTF : COMBOBOX TIPO FACTURA 
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tFF"); // tFF : TXT FECHA FACTURA 
                    String TXT_CLIENTE_ID = (String) request.getParameter("tIC"); // tIC : TXT ID CLIENTE 
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tCNC"); // tCNC : TXT CLIENTE NRO CI 
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tCN"); // tCN : TXT CLIENTE NAME 
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tCRS"); // tCRS : TXT CLIENTE RAZON SOCIAL 
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tCR"); // tCR : TXT CLIENTE RUC 

                    System.out.println("------------------__PARAMETER__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("-------------------------------------------------");
                    // MODAL 
                    // ID CLIENTE SELECCIONADO 
                    String CBX_IDCLIENTE = (String) request.getParameter("cbxAddNewCli");
                    // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
                    List<BeanPersona> listaDatosCliente;
                    listaDatosCliente = metodosPersona.traerDatosPersona(CBX_IDCLIENTE);
                    Iterator<BeanPersona> iterCliente = listaDatosCliente.iterator();
                    BeanPersona datosClienteNew = null;
                    String CLIENTE_NAME="", CLIENTE_CINRO="", CLIENTE_RAZONSOCIAL="", CLIENTE_RUC="";
                    while(iterCliente.hasNext()) {
                        datosClienteNew = iterCliente.next();

                        CLIENTE_NAME = datosClienteNew.getRP_APELLIDO()+" "+datosClienteNew.getRP_NOMBRE();
                        CLIENTE_CINRO = datosClienteNew.getRP_CINRO();
                        CLIENTE_RAZONSOCIAL = datosClienteNew.getRP_RAZON_SOCIAL();
                        CLIENTE_RUC = datosClienteNew.getRP_RUC();
                    }

                    // IMPRIMO PARAMETROS RECIBIDOS PARA VISUALIZAR POR CONSOLA LOS DATOS 
                    System.out.println("-------------------------------------------");
                    System.out.println("_   _ID_CLIENTE:   :"+CBX_IDCLIENTE);
                    System.out.println("_   _CLIENTE_NAME :  :"+CLIENTE_NAME);
                    System.out.println("_   _CLIENTE_CINRO:  :"+CLIENTE_CINRO);
                    System.out.println("_   _RAZONSOCIAL:    :"+CLIENTE_RAZONSOCIAL);
                    System.out.println("_   _CLIENTE_RUC:    :"+CLIENTE_RUC);
                    System.out.println("-------------------------------------------");

                    var_redireccionar = 1;
                    acceso = "crear_factura.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    // CABECERA 
                    request.setAttribute("CF_TXT_CLIENTE_ID", CBX_IDCLIENTE);
                    request.setAttribute("CF_TXT_CLIENTE_CINRO", CLIENTE_CINRO);
                    request.setAttribute("CF_TXT_CLIENTE_NAME", CLIENTE_NAME);
                    request.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", CLIENTE_RAZONSOCIAL);
                    request.setAttribute("CF_TXT_CLIENTE_RUC", CLIENTE_RUC);
                    request.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    request.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    request.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);

                } else if(accion.equalsIgnoreCase("Eliminar Fila") || accion.equalsIgnoreCase("Borrar Fila")) {
                    System.out.println("---------------------__ELIMINAR_FILA__--------------------------");
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    // DATOS DE LA CABECERA ---- 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DE LA GRILLA ---- 
                    String TXT_TOTAL_IVA5 = "0";
                    String TXT_TOTAL_IVA10 = "0";
                    String TXT_TOTAL_GRAV5 = "0";
                    String TXT_TOTAL_GRAV10 = "0";
                    String TXT_TOTAL_IVA = "0";
                    String TXT_TOTAL = "0";
                    System.out.println("------------------__PARAMETER__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("-------------------");
                    System.out.println("TOTAL_IVA5      :"+TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA10     :"+TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV5     :"+TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV10    :"+TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+TXT_TOTAL);
                    System.out.println("-------------------------------------------------");

                    // ELIMINO EL ITEM DE LA LISTA 
                    // OBTENGO EL VALOR DEL ULTIMO ITEM SIGUIENTE PERO NO PARA ELIMINAR SINO PARA PASAR NOMAS A LA SESION, NO UTILIZARE ESTA VARIABLE PARA ELIMINAR O PARA OTRA COSA MAS QUE PARA PASAR A LA SESION
                    int CF_ITEM_LISTA = 0;
                    try {
                        CF_ITEM_LISTA = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")); // VARIABLE DE LA NUMERACION DEL ITEM 
                    } catch(NullPointerException e) {
                        CF_ITEM_LISTA = 1;
                    }
                    System.out.println("_   __ITEM_NRO_LIST:    "+CF_ITEM_LISTA);
                    int CF_ITEM_LISTA_ELIMINAR = Integer.parseInt((String)request.getParameter("tDFI"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0  // tDFI : txt detalle factura item 
                    System.out.println("_   __ELIMINAR_ITEM_NRO_LIST:    :"+CF_ITEM_LISTA_ELIMINAR);

                    // ITEM ELIMINADO ANTERIORMENTE 
                    String sesionItemEliminado = (String)sesionDatosUsuario.getAttribute("CF_ITEM_LISTA_ELIMINAR_ANTES");
                    if (sesionItemEliminado == null) {
                        sesionItemEliminado = "9999"; // LE CARGO ESTE VALOR, PORQUE ES UN VALOR QUE NO VA A SER ALCANZADO JAMASL, Y CON ESTO VOY A IDENTIFICAR CUANDO ESTE NULO Y NO DEBO DE COMPARARLO, QUE LA PRIMERA VEZ NOMAS CREO QUE SE VA A ENCONTRAR NULO 
                    }
                    int CF_ITEM_LISTA_ELIMINAR_ANTES = Integer.parseInt(sesionItemEliminado); // item anterior eliminado 
                    System.out.println("_   __ITEM_LISTA_ELIMINAR_ANTES:    :"+CF_ITEM_LISTA_ELIMINAR_ANTES);

                    List<BeanFacturaCab> CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        int varErrorEliminar = 0; // VARIABLE QUE UTILIZARE PARA CONTINUAR Y HACER EL PROCESO DE ELIMINAR 
                        if (CF_ITEM_LISTA_ELIMINAR_ANTES == 9999) { // SI SE ENCUENTRA NULO ENTONCES LE DEJO HACER NORMAL 
                            varErrorEliminar = 0;
                        } else if (CF_ITEM_LISTA_ELIMINAR_ANTES == CF_ITEM_LISTA_ELIMINAR) { // SI SON IGUALES ENTONCES NO HAGO EL PROCESO DE ELIMINAR PORQUE NO SELECIONO NINGUNA FILA PARA ELIMINAR SINO QUE RECARGO LA PAGINA Y POR ESO DEVUELVE EL MISMO ITEM QUE ELIMINO ANTERIORMENTE 
                            varErrorEliminar = 1;
                        }

                        // SI ES IGUAL A SU VALOR INICIAL NOMAS VOY A EFECTUAR LAS OPERACIONES PARA ELIMINAR LA FILA PERO SI NO ES PORQUE SE ACTIVO ALGUNA VALIDACION 
                        if (varErrorEliminar == 0) {

                            // LE CARGO EL VALOR DEL ITEM QUE SE QUIERE ELIMINAR PARA LUEGO CARGA A LA SESION 
                            CF_ITEM_LISTA_ELIMINAR_ANTES = CF_ITEM_LISTA_ELIMINAR;


                            // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                            CF_LISTA_DETALLE.remove(CF_ITEM_LISTA_ELIMINAR);

                            System.out.println("_   ____ORDENAR_NRO_ITEM_NEW_____    _");
                            // ORDENAR LOS ITEMS 
                            for (BeanFacturaCab LISTA_DETALLE : CF_LISTA_DETALLE) {
                                // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA LISTA QUE SE QUIERE ELIMINAR 
                                BeanFacturaCab datosNew = new BeanFacturaCab();
                                    datosNew.setOFD_ITEM(itemNew); // LE CARGO TODOS LOS MISMOS DATOS DE LA LISTA SOLO QUE AQUI EN EL ITEM LE CAMBIO Y LE AGREGO EL NUEVO ITEM RESETEADO 
                                    datosNew.setOFD_IDCONCEPTO(LISTA_DETALLE.getOFD_IDCONCEPTO());
                                    datosNew.setOFD_IDTIPOCONCEPTO(LISTA_DETALLE.getOFD_IDTIPOCONCEPTO());
                                    datosNew.setOFD_DESCRIPCION_AUX(LISTA_DETALLE.getOFD_DESCRIPCION_AUX());
                                    datosNew.setOFD_CANTIDAD(LISTA_DETALLE.getOFD_CANTIDAD());
                                    datosNew.setOFD_IDIMPUESTO(LISTA_DETALLE.getOFD_IDIMPUESTO());
                                    datosNew.setOFD_IVA5(LISTA_DETALLE.getOFD_IVA5());
                                    datosNew.setOFD_IVA10(LISTA_DETALLE.getOFD_IVA10());
                                    datosNew.setOFD_EXENTO(LISTA_DETALLE.getOFD_EXENTO());
                                    datosNew.setOFD_PRECIO(LISTA_DETALLE.getOFD_PRECIO());
                                    datosNew.setOFD_SUBTOTAL(LISTA_DETALLE.getOFD_SUBTOTAL());

                                    System.out.println("-   -----   VOLVIENDO A CALCULAR LOS TOTALES    ------   -");
                                    System.out.println("-   -   _IVA:     :"+LISTA_DETALLE.getOFD_IDIMPUESTO());
                                    System.out.println("-   -   _IVA5:    :"+LISTA_DETALLE.getOFD_IVA5());
                                    System.out.println("-   -   _IVA10:   :"+LISTA_DETALLE.getOFD_IVA10());
                                    System.out.println("-   -   _SUBTO:   :"+LISTA_DETALLE.getOFD_SUBTOTAL());
                                    System.out.println("-   -   -   -   -   -   -   -   ");
                                    if (LISTA_DETALLE.getOFD_IDIMPUESTO().equals("5") || LISTA_DETALLE.getOFD_IDIMPUESTO().equals("5%")) {
                                        System.out.println("-   -   ___IF___    IVA_5   ______");
                                        TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(TXT_TOTAL_IVA5) + Double.parseDouble(LISTA_DETALLE.getOFD_IVA5().replace(".","")));
                                        double montoGrav5 = Double.parseDouble(LISTA_DETALLE.getOFD_SUBTOTAL().replace(".","")) - Double.parseDouble(LISTA_DETALLE.getOFD_IVA5().replace(".",""));
                                        TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(TXT_TOTAL_GRAV5) + ((montoGrav5)));
                                        TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(TXT_TOTAL_IVA) + Double.parseDouble(LISTA_DETALLE.getOFD_IVA5().replace(".","")));
                                        System.out.println("-   -   ___TOTAL_IVA5:  :"+TXT_TOTAL_IVA5);
                                        System.out.println("-   -   ___TOTAL_GRAV5: :"+TXT_TOTAL_GRAV5);
                                        System.out.println("-   -   ___TOTAL_IVA:   :"+TXT_TOTAL_IVA);
                                    } else if(LISTA_DETALLE.getOFD_IDIMPUESTO().equals("10") || LISTA_DETALLE.getOFD_IDIMPUESTO().equals("10%")) {
                                        System.out.println("-   -   ___IF___    IVA_10   ______");
                                        TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(TXT_TOTAL_IVA10) + Double.parseDouble(LISTA_DETALLE.getOFD_IVA10().replace(".","")));
                                        double montoGrav10 = Double.parseDouble(LISTA_DETALLE.getOFD_SUBTOTAL().replace(".","")) - Double.parseDouble(LISTA_DETALLE.getOFD_IVA10().replace(".",""));
                                        TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(TXT_TOTAL_GRAV10) + (montoGrav10));
                                        TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(TXT_TOTAL_IVA) + Double.parseDouble(LISTA_DETALLE.getOFD_IVA10().replace(".","")));
                                        System.out.println("-   -   ___TOTAL_IVA10: :"+TXT_TOTAL_IVA10);
                                        System.out.println("-   -   ___TOTAL_GRAV10::"+TXT_TOTAL_GRAV10);
                                        System.out.println("-   -   ___TOTAL_IVA:   :"+TXT_TOTAL_IVA);
    //                                } else if(LISTA_DETALLE.getOFD_IDIMPUESTO().equals("0") || LISTA_DETALLE.getOFD_IDIMPUESTO().equalsIgnoreCase("exento")) {
    //                                    //
                                    }
                                    System.out.println("-   -   _TXT_TOTAL:     :"+TXT_TOTAL);
                                    TXT_TOTAL = String.valueOf(Double.parseDouble(TXT_TOTAL) + Double.parseDouble(LISTA_DETALLE.getOFD_SUBTOTAL().replace(".","")));
    //                                TXT_TOTAL = formatear.format(Double.parseDouble(TXT_TOTAL) + Double.parseDouble(LISTA_DETALLE.getOFD_SUBTOTAL().replace(".","")));


                                CF_LISTA_NEW.add(datosNew);
                                // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                                itemNew = itemNew + 1;
                            } // end for 

                        } // end IF --- 
                    } catch(IndexOutOfBoundsException e) {
                        System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
                        itemNew = CF_ITEM_LISTA; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("__FACTURA______ERROR_POR_NRO_ITEM____");
                        System.out.println(e.getMessage());
                        Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
                    }
                    TXT_TOTAL = formatear.format(Double.parseDouble(TXT_TOTAL));
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println("---------------__FINAL_DATOS__-----------------");
                    System.out.println(".   _TOTAL_IVA5:       :"+TXT_TOTAL_IVA5);
                    System.out.println(".   _TOTAL_GRAV5:      :"+TXT_TOTAL_GRAV5);
                    System.out.println(".   _TOTAL_IVA10:      :"+TXT_TOTAL_IVA10);
                    System.out.println(".   _TOTAL_GRAV10:     :"+TXT_TOTAL_GRAV10);
                    System.out.println(".   _TOTAL_IVA:        :"+TXT_TOTAL_IVA);
                    System.out.println(".   _TOTAL:        :"+TXT_TOTAL);
                    System.out.println("---------------__-----------__-----------------");

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
    //                acceso = "crear_factura.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(itemNew));
    //                sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_ITEM_LISTA));
                    /*
                    OBSERVACION: VARIABLE QUE UTILIZARE PARA CONTROLAR CUANDO SE ELIMINE 
                    PORQUE CUANDO ELIMINO Y RECARGO LA PAGINA SE VUELVE A EFECTUAR LA MISMA ACCION Y ME SALTA UN ERROR 
                    Y ME VACIA LA LISTA, PERO EL ERROR SALTA PORQUE SE VUELVE A ENVIAR EL ULTIMO ITEM Y COMO AL ELIMINAR LA LISTA SE REDUCE 
                    ENTONCES INTENTA ELIMINAR EL TOTAL DE LA LISTA, Y SALTA EL ERROR 
                    (ESTO PASA CUANDO LA LISTA TIENE MAS DE UNA FILA Y SE ELIMINA EL ULTIMO DE ELLAS Y SE RECARGA DE NUEVO LA PAGINA POR MEDIO DE CONTROL+R O POR EL BOTON DE LA PAGINA)
                    */
                    sesionDatosUsuario.setAttribute("CF_ITEM_LISTA_ELIMINAR_ANTES", String.valueOf(CF_ITEM_LISTA_ELIMINAR_ANTES));
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
    //                sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // PASO DE VUELTA LOS PARAMETROS PARA QUE SE CARGUE CON LOS VALORES QUE EL USUARIO HABIA DEJADO EN TODO CASO DE QUE HAYA CARGADO // NO LOS UTILIZO EN ESTA FRACCION DE CODIGO PERO SI NO LES VUELVO A PASAR ENTONCES ME VA A DEJAR VACIOS ESOS CAMPOS, NO ME VA A DEJAR CON LOS VALORES QUE SE DEJO EN CASO DE QUE SE HAYA CARGADO 
                    // DATOS CABECERA 
                    request.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    request.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    request.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    request.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    request.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    request.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    request.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    request.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // DATOS DETALLE 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", TXT_TOTAL);
                    
                } else if(accion.equalsIgnoreCase("Cobrar")) {
                    System.out.println("------------------------__COBRAR__------------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                    String VAR_ID_CLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");
                    System.out.println("_   _VAR_ID_CLINICA:    :"+VAR_ID_CLINICA);
                    
                    // CONTROLO SI EL ID SE ENCUENTRA VACIO, ENTONCES VOY A GUARDAR, PERO SI NO SE ENCUENTRA VACIO ENTONCES LE REDIRECCIONO A LA PAGINA PRINCIPAL 
                    if (ID_FACTURA.isEmpty() || ID_FACTURA == null || ID_FACTURA.equals("")) {
                        System.out.println("________ID_FACTURA_VACIO______________INSERT______________");
                        String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                        String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                        String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                        String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                        String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                        String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                        String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                        String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                        // TOTALES DEL DETALLE 
                        String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                        String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                        String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                        String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                        String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                        String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                        System.out.println("------------------__PARAMETER_CABECERA__------------------");
                        System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                        System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                        System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                        System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                        System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                        System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                        System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                        System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                        System.out.println("- - - - - - - - -");
                        System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                        System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                        System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                        System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                        System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                        System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                        System.out.println("----------------------------------------------------------");
                        
                        // OBTENGO LA NUMERACION DE ITEM DE LA LISTA 
                        int CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                        // RECUPERO LA LISTA DEL DETALLE DE LA FACTURA 
                        List<BeanFacturaCab> CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                        
                        int varParamError = 0; // VARIABLE QUE UTILIZO PARA SABER QUE DATOS LE TENGO QUE DEVOLVER AL JSP YA QUE ALGUNO SON INNECESARIO QUE LOS VUELVA A PASAR, DEPENDIENDO SI SALTO ALGUNA VALIDACION O SI TODO FUE EXITOSO PASARE UNAS VARIABLES O OTRAS
                        String TIPO_MENSAJE="", MENSAJE=null;
                        // VALIDACIONES 
                        if (metodosFactura.controlCamposVacios(TXT_NRO_FACTURA, TXT_FECHA_FACTURA, CBX_TIPO_FACTURA, TXT_CLIENTE_ID) == true) {
                            System.out.println("---------   VALIDACION__01   ---------");
                            varParamError = 1;
                            CF_VALI_MOSTRAR_BTN_DETALLE = "1"; // LE CARGO UNO PARA QUE EN LA CABECERA MUESTRE EL BOTON PARA REGRESAR AL DETALLE 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            acceso = "crear_factura.htm";

                        } else if (CF_LISTA_DETALLE == null || CF_LISTA_DETALLE.size() <= 0 || CF_LISTA_DETALLE.isEmpty()) {
                            System.out.println("---------   VALIDACION__02   ---------");
                            varParamError = 1;
                            CF_VALI_MOSTRAR_BTN_DETALLE = "0"; // DEJO ESTO ACA CON UN VALOR QUE TIENE LUEGO LA VARIABLE AL ENTRAR EN EL CONTROLADOR CON EL FIN DE NO COLOCARLO EN EL FUTURO CON 1 COMO EN LAS OTRAS VALIDACIONES SOLO PORQUE VA A LA CABECERA, COMO EL DETALLE NO ESTA CARGADO Y EL USUARIO DEBE DE CARGAR, NO TIENE SENTIDO QUE LE MUESTRE EL BOTON PARA REGRESAR AL DETALLE Y DARLE LA POSIBLIDAD DE QUE SE SALTE LOS DOS BOTONES PARA CARGAR EL DETALLE CUANDO ESO DEBE DE HACER 
                            MENSAJE = "Debe de cargar el detalle de la factura.";
                            TIPO_MENSAJE = "2";
                            acceso = "crear_factura.htm";

                        } else if (metodosFactura.ctrlNroFactRepetir(sesionDatosUsuario, TXT_NRO_FACTURA) == true) {
                            System.out.println("---------   VALIDACION__04   ---------");
                            varParamError = 1;
                            CF_VALI_MOSTRAR_BTN_DETALLE = "1";  // LE CARGO UNO PARA QUE EN LA CABECERA MUESTRE EL BOTON PARA REGRESAR AL DETALLE 
                            MENSAJE = "No puede repetir el nro. de factura.";
                            TIPO_MENSAJE = "2";
                            acceso = "crear_factura.htm";

                        } else if (metodosFactura.ctrlValoresNegativos(CF_TXT_TOTAL_IVA5, CF_TXT_TOTAL_IVA10, CF_TXT_TOTAL_IVA, CF_TXT_TOTAL) == true) { // validacion para controlar que las variables pasadas como parametros no sean valores negativos 
                            System.out.println("---------   VALIDACION__05   ---------");
                            varParamError = 1;
                            CF_VALI_MOSTRAR_BTN_DETALLE = "1";  // LE CARGO UNO PARA QUE EN LA CABECERA MUESTRE EL BOTON PARA REGRESAR AL DETALLE 
                            MENSAJE = "No puede tener valores negativos.";
                            TIPO_MENSAJE = "2";
                            acceso = "crear_factura_det.htm";
                            
                        } else {
                            CF_VALI_MOSTRAR_BTN_DETALLE = "0"; // LE CARGO CON CERO PARA QUE AL GUARDAR NO SE QUEDE CON EL VALOR UNO EN CASO QUE SE HAYA ACTIVADO ANTES 
                            // VARIABLES PARA GUARDAR LA CABECERA 
                            String CAB_IDFACTURA = metodosFactura.traerNewIdFactura();
                            String CAB_IDCLINICA = VAR_ID_CLINICA;
                            String CAB_NROFACTURA = TXT_NRO_FACTURA;
                            String CAB_TIPOFACTURA = CBX_TIPO_FACTURA;
                            String CAB_IDCLIENTE = TXT_CLIENTE_ID;
                            String CAB_FECHAFACTURA = TXT_FECHA_FACTURA;
                            String CAB_IDARQUEO = "0";
                            String CAB_ESTADO = "C";
                            String CAB_TOTAL_FACTURA = CF_TXT_TOTAL.replace(".", "").replace(",",".");
                            String CAB_TOTAL_DCTO = "0";
                            String CAB_TOTAL_IVA10 = CF_TXT_TOTAL_IVA10.replace(".", "").replace(",",".");
                            String CAB_TOTAL_IVA5 = CF_TXT_TOTAL_IVA5.replace(".", "").replace(",",".");
                            String CAB_TOTAL_GRAV10 = CF_TXT_TOTAL_GRAV10.replace(".", "").replace(",",".");
                            String CAB_TOTAL_GRAV5 = CF_TXT_TOTAL_GRAV5.replace(".", "").replace(",",".");
                            String CAB_TOTAL_EXENTA = "0";
                            String CAB_TOTAL_IVA = CF_TXT_TOTAL_IVA.replace(".", "").replace(",",".");
                            String CAB_IDFORMACOBRO = "1";
                            String CAB_LETRAS = obtener_letra.nletra(Integer.parseInt(CF_TXT_TOTAL.replace(".","")));
                            String CAB_OBSERVACION = "";
                            String CAB_USU_ALTA = idPersona;
                            String CAB_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();
                            
                            TIPO_MENSAJE = metodosFactura.guardar(new BeanFacturaCab(Integer.parseInt(CAB_IDFACTURA), Integer.parseInt(CAB_IDCLINICA), CAB_NROFACTURA, CAB_TIPOFACTURA, Integer.parseInt(CAB_IDCLIENTE), CAB_FECHAFACTURA, CAB_IDARQUEO, CAB_ESTADO, CAB_TOTAL_FACTURA, CAB_TOTAL_DCTO, CAB_TOTAL_IVA10, CAB_TOTAL_IVA5, CAB_TOTAL_GRAV10, CAB_TOTAL_GRAV5, CAB_TOTAL_EXENTA, CAB_TOTAL_IVA, CAB_IDFORMACOBRO, CAB_LETRAS, CAB_OBSERVACION, CAB_USU_ALTA, CAB_FEC_ALTA));
                            if (TIPO_MENSAJE.equals("1")) { // se grabo la cabecera 
                                ID_FACTURA = CAB_IDFACTURA;
                                MENSAJE = "Se ha facturado correctamente.";
                                // SI SE GUARDO CORRECTAMENTE LA CABECERA ENTONCES PASARIA A GUARDAR EL DETALLE 
                                metodosFactura.guardarDetalle(CF_LISTA_DETALLE, CAB_IDFACTURA, CAB_IDCLIENTE);
                                
                            } else if (TIPO_MENSAJE.equals("2")) { // dio algun error y no se guardo la cabecera 
                                MENSAJE = "Se genero un error, vuelva a intentarlo mas tarde.";
                            }
                            acceso = "factura.htm";
                            var_redireccionar = 1;
                        } // end else 
                        
                        // DEVUELVO LAS VARIABLES QUE SON NECESARIAS PARA CUALQUIER JSP DE FACTURA 
                        sesionDatosUsuario.setAttribute("ID_FACTURA", ID_FACTURA); // LE PASO EL IDFACTURA PARA EVITAR QUE SE VUELVA A REPETIR LA ACCION AL RECARGAR LA PAGINA, Y QUIERA VOLVER A GUARDAR Y ME DE ERROR EN BASE POR INTENTAR GUARDAR UN REGISTRO YA EXISTENTE, ENTONCES POR ESO EN VEZ DE PASARLO VACIO LE CARGO EL ID Y ASI NO VA A INGRESAR A HACER LA MISMA ACCION Y PARA VOLVER A GUARDAR TENDRIA QUE CARGAR UN NUEVO REGISTRO, ASI SE LIMPIARIA EL CAMPO EN LA SESION 
                        request.setAttribute("CF_MENSAJE", MENSAJE);
                        request.setAttribute("CF_TIPO_MENSAJE", TIPO_MENSAJE);
                        // DEPENDIENDO DE SI DIO ERROR O NO LE PASO OTRAS VARIABLES QUE PARA ALGUNOS JSP SON NECESARIOS Y PARA OTROS ESTARIAN REDUNDANTE QUE LES PASE COMO POR EJEMPLO LA PAGINA PRINCIPAL DE FACTURA 
                        if (varParamError > 0) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("____SE_GENERO_UN_ERROR______VAR_PARAM_ERROR___________");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            var_redireccionar = 1; // COMO DIO ERROR ENTONCES SI QUIERO MOSTRARLE EL MENSAJE AL USUARIO Y POR ESO LE CAMBIO EL VALOR 
                            System.out.println("___var_redireccionar:       "+var_redireccionar);
                            sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM));
                            sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                            sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                            sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                            sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                            sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                            sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                            sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                            sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                            sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                            sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                        }
                    } else { // SI EL IDFACTURA DE LA SESION SE ENCUENTRA CARGADA ENTONCES YA SE GUARDO Y EL NAVEGADOR ESTA VOLVIENDO A REPETIR LA ULTIMA ACCION Y PARA EVITAR ESTO LE REDIRECCIONO A LA PAGINA PRINCIPAL DE FACTURA 
                        System.out.println("____ID_FACTURA_CARGADO___________________  ELSE  __________________________");
                        System.out.println("___ID_FACTURA:  "+ID_FACTURA);
                        var_redireccionar = 0;
                        acceso = "factura.htm";
                    }

                } else if(accion.equalsIgnoreCase("Volver al Detalle")) {
                    System.out.println("------------------------__VOLVER_AL_DETALLE__------------------------------");
                    // RECIBO LOS PARAMETROS  --------------  -------------- 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE  --------------  -------------- 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    if (CF_TXT_TOTAL_IVA5 == null) { CF_TXT_TOTAL_IVA5 = "0"; }
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    if (CF_TXT_TOTAL_IVA10 == null) { CF_TXT_TOTAL_IVA10 = "0"; }
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    if (CF_TXT_TOTAL_GRAV5 == null) { CF_TXT_TOTAL_GRAV5 = "0"; }
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    if (CF_TXT_TOTAL_GRAV10 == null) { CF_TXT_TOTAL_GRAV10 = "0"; }
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    if (CF_TXT_TOTAL_IVA == null) { CF_TXT_TOTAL_IVA = "0"; }
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("--- --- --- --  --  --- --  --  ---");
                    System.out.println("TOTAL_IVA5:     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA10:    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV5:    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV10:   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA:      :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL:          :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");

                    // RECUPERO EL NRO DE LA LISTA Y LA LISTA PARA PASARLE OTRA VEZ AL DETALLE 
                    String CF_LISTA_ITEM = String.valueOf(sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", (CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);

                } else if(accion.equalsIgnoreCase("Filtrar")) {
                    System.out.println("------------------------__FILTRAR__------------------------------");
                    // OBTENER VALORES DE LA CAJA DE FILTRO 
                    String cbxMostrar = (String) request.getParameter("cM");
                    String txtBuscarNroFact = (String) request.getParameter("tFNF"); // tFNF : txt filtrar nro factura 
                    if (txtBuscarNroFact == null || txtBuscarNroFact.isEmpty()) {
                        txtBuscarNroFact = "";
                    }
                    System.out.println("-----------__VAR_FILTRAR__-----------");
                    System.out.println("_  _cbxMostrar:   "+cbxMostrar);
                    System.out.println("_  _Buscar_Nro_Fact:   "+txtBuscarNroFact);
                    System.out.println("-------------------------------------");
                    
                    // CARGAR LISTA 
                    List<BeanFacturaCab> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosFactura.filtrar(sesionDatosUsuario, cbxMostrar, txtBuscarNroFact);
                    
                    var_redireccionar = 1;
                    acceso = "factura.htm";
                    // PASAR DATOS AL JSP 
                    request.setAttribute("CF_CBX_MOSTRAR", cbxMostrar);
                    request.setAttribute("CF_TXT_FILTRAR_NRO_FACT", txtBuscarNroFact);
                    request.setAttribute("CF_LISTA_FILTRO", listaFiltro);
                    sesionDatosUsuario.setAttribute("CF_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accion.equalsIgnoreCase("Cargar Descuento")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN DESCUENTO CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FD__CARGAR_DESCUENTO__------------- FACTURA CARGAR DETALLE -------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDDETCONCEPTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        int DET_IDTIPOCONCEPTO = Integer.parseInt(String.valueOf(datosLista.getOFD_IDTIPOCONCEPTO()));
                        String CONCEPTO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String DET_CONCEPTO = datosLista.getOFD_DESCRIPCION_AUX();
                        String DET_CONC_PRECIO = (datosLista.getOFD_PRECIO()).replace(".","");
    //                    String DET_CONC_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int DET_CONC_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(DET_CONC_PRECIO)*Double.parseDouble(String.valueOf(DET_CONC_CANTIDAD))).replace(".","");
                        System.out.println("-------------FOR_PASAR_DATOS_DET-----------------");
                        System.out.println("-   ___LISTA_ITEM:    "+LISTA_ITEM);
                        System.out.println("-   __ID_PRODUCTO:    "+IDDETCONCEPTO);
                        System.out.println("-   _ID_TIPO_CONC:    "+IDDETCONCEPTO);
                        System.out.println("-   _CONCEPTO_IVA:    "+CONCEPTO_IVA);
                        System.out.println("-   _DET_CONCEPTO:    "+DET_CONCEPTO);
                        System.out.println("-   __CONC_PRECIO:    "+DET_CONC_PRECIO);
                        System.out.println("-   _PROD_PRECIO_2:   "+datosLista.getOFD_PRECIO());
                        System.out.println("-   _CONC_CANTIDAD:   "+DET_CONC_CANTIDAD);
                        System.out.println("-   ___PRECIO_FACT:   "+PRECIO_FACT);
                        System.out.println("-------------------------------------------------");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDDETCONCEPTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(DET_IDTIPOCONCEPTO); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES AGREGADO / 4 : DESCUENTO AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(DET_CONCEPTO);
                            datosNew.setOFD_CANTIDAD(DET_CONC_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(CONCEPTO_IVA);
                            if (CONCEPTO_IVA.equalsIgnoreCase("5") || CONCEPTO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (CONCEPTO_IVA.equalsIgnoreCase("10") || CONCEPTO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(DET_CONC_PRECIO);
    //                        datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(DET_CONC_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(PRECIO_FACT);
    //                        datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "factura_add_desc.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
    //                sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", ((CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", ((CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", ((CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", ((CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", ((CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", ((CF_TXT_TOTAL)));

                } else if (accion.equalsIgnoreCase("Cargar Interes") || accion.equalsIgnoreCase("Cargar Interés")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FD__CARGAR_INTERES__------------- FACTURA CARGAR DETALLE -------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
    //                String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDDETCONCEPTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        int DET_IDTIPOCONCEPTO = Integer.parseInt(String.valueOf(datosLista.getOFD_IDTIPOCONCEPTO()));
                        String CONCEPTO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String DET_CONCEPTO = datosLista.getOFD_DESCRIPCION_AUX();
                        String DET_CONC_PRECIO = (datosLista.getOFD_PRECIO()).replace(".","");
    //                    String DET_CONC_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int DET_CONC_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(DET_CONC_PRECIO)*Double.parseDouble(String.valueOf(DET_CONC_CANTIDAD))).replace(".","");
                        System.out.println("-------------FOR_PASAR_DATOS_DET-----------------");
                        System.out.println("-   ___LISTA_ITEM:    "+LISTA_ITEM);
                        System.out.println("-   __ID_PRODUCTO:    "+IDDETCONCEPTO);
                        System.out.println("-   _ID_TIPO_CONC:    "+IDDETCONCEPTO);
                        System.out.println("-   _CONCEPTO_IVA:    "+CONCEPTO_IVA);
                        System.out.println("-   _DET_CONCEPTO:    "+DET_CONCEPTO);
                        System.out.println("-   __CONC_PRECIO:    "+DET_CONC_PRECIO);
                        System.out.println("-   _PROD_PRECIO_2:   "+datosLista.getOFD_PRECIO());
                        System.out.println("-   _CONC_CANTIDAD:   "+DET_CONC_CANTIDAD);
                        System.out.println("-   ___PRECIO_FACT:   "+PRECIO_FACT);
                        System.out.println("-------------------------------------------------");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDDETCONCEPTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(DET_IDTIPOCONCEPTO); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(DET_CONCEPTO);
                            datosNew.setOFD_CANTIDAD(DET_CONC_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(CONCEPTO_IVA);
                            if (CONCEPTO_IVA.equalsIgnoreCase("5") || CONCEPTO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (CONCEPTO_IVA.equalsIgnoreCase("10") || CONCEPTO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(DET_CONC_PRECIO);
    //                        datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(DET_CONC_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(PRECIO_FACT);
    //                        datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
    //                sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", ((CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", ((CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", ((CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", ((CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", ((CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", ((CF_TXT_TOTAL)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                }
                
                
            } else if(accionFCC != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__ACCION_FACTURA_CUENTA_CLIENTE__--------------------------");
                System.out.println(".");System.out.println(".");
                if (accionFCC.equalsIgnoreCase("Cargar Detalle")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CUENTA CLIENTE  */
                    System.out.println("---------------------__FCC_CARGAR_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    // OBSERVACION: LE CARGO CON CERO YA QUE VA A VOLVER A SELECCIONAR LAS CUENTAS PARA CARGAR EL DETALLE DE LA FACTURA Y PARA EVITAR QUE MANTENGA ALGUN DATO (EJEMPLO SI CARGA ALGUN PRODUCTO DE 5% Y LUEGO ELIMINA Y CARGA UNA CUENTA DE 10% EL DETALLE LE VA A MOSTRAR EL VALOR ANTERIOR DE 5% CUANDO YA NO HAYA EN EL DETALLE NADA DE 5%)
                    String CF_TXT_TOTAL_IVA5 = "0";
                    String CF_TXT_TOTAL_IVA10 = "0";
                    String CF_TXT_TOTAL_GRAV5 = "0";
                    String CF_TXT_TOTAL_GRAV10 = "0";
                    String CF_TXT_TOTAL_IVA = "0";
                    String CF_TXT_TOTAL = "0";
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");

                    // OBTENGO LA NUMERACION DE ITEM DE LA LISTA 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    /*
                    LA IDEA VA A SER TRAER LA LISTA QUE CARGUE EN LA GRILLA 
                    Y EN UN CAMPO OCULTO VA A TENER UN NOMBRE ESTATICO MAS EL IDCUENTACLIENTE 
                    Y ENTONCES AL RECUPERAR LA MISMA LISTA VOY TRAYENDO POR EL FOR EL CAMPO DE TEXTO 
                    Y SI TIENE UN VALOR BINARIO 
                    */
                    List<BeanCuentaCliente> listaCtaCliente = null;
                    listaCtaCliente = metodosFactura.cargarCuentaCliente(TXT_CLIENTE_ID, sesionDatosUsuario);
                    Iterator<BeanCuentaCliente> iterCta = listaCtaCliente.iterator();
                    BeanCuentaCliente datosCta = null;

                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println("-   -   -   CONTROL     CHECK     -   -   -");
                    String varCheckForm1 = (String) request.getParameter("checkCta1");
                    System.out.println("__1___VAR_CHECK_FORM:       "+varCheckForm1);
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    String CC_IDAGENDAMIENTO;
                    String CC_ITEM_AGEN;
                    int item = 0;
                    while(iterCta.hasNext()) {
                        System.out.println(".");System.out.println(".");System.out.println(".");
                        datosCta = iterCta.next();

                        item = item + 1;
                        System.out.println("___WHILE___ "+item+" ___");

                        // VARIABLES DE LA LISTA 
                        String IDCUENTACLIENTE = datosCta.getCC_IDCUENTACLIENTE();
    //                    String varCheck = "checkCta"+IDCUENTACLIENTE;
    //                    System.out.println("__CHECK:        "+varCheck);
                        System.out.println("__ID_CUENTA_CLIENTE:        "+IDCUENTACLIENTE);

                        CC_IDAGENDAMIENTO = datosCta.getCC_IDAGENDAMIENTO();
                        CC_ITEM_AGEN = datosCta.getCC_ITEM_AGEN();
                        System.out.println("__CC_ID_AGENDAMIENTO:        :"+CC_IDAGENDAMIENTO);
                        System.out.println("__CC_ITEM_AGENDAMIEN:        :"+CC_ITEM_AGEN);

                        // OBTENGO DEL JSP 
                        String varCheckForm = (String) request.getParameter("checkCta"+IDCUENTACLIENTE+"");
                        System.out.println("__"+item+"___VAR_CHECK_FORM:       "+varCheckForm);

                        if (varCheckForm == null) {
                            System.out.println("-   "+item+"_______IF___CHECK_NULL_____-");

                        } else if (varCheckForm.equalsIgnoreCase("ON")) { // SI LA VARIABLE DEVUELVE EL VALOR "ON" ENTONCES ES PORQUE SE ENCUENTRA MARCADO EL CHECK, SINO SE ENCUENTRA MARCADO DEVUELVE EL VALOR NULL 
                            System.out.println("-   "+item+"________IF_______CHECK_ON_________");
    //                        // VALIDAR PRIMERAMENTE QUE LA CUENTA QUE MARCO TENGA LA FECHA DE VENCIMIENTO MENOR ENTRE LAS CUENTAS PARA EVITAR QUE QUIERA PAGAR UNA CUENTA CON FECHA SUPERIOR A OTRAS PROXIMAS 
    //                        if () {// llamar a un metodo y pasarle el idempeño, entonces filtrar las cuentas de ese empeño y controlar que la cuenta seleccionada sea la menor entre los nro empeño que haya 
    //                            11
    //                        }

                            String CONCEPTO_CUENTA = datosCta.getCC_COMENTARIO();
                            System.out.println("_   _   _CONEPTO_CTA:    "+CONCEPTO_CUENTA);
                            String CONCEPTO_PRECIO = datosCta.getCC_SALDO().replace(".","");
                            System.out.println("_   _   _PRECIO_SALDO:   "+CONCEPTO_PRECIO);

                            // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES CARGO A LA LISTA DEL DETALLE 
                            BeanFacturaCab datos = new BeanFacturaCab();
                                datos.setOFD_ITEM(CF_LISTA_ITEM);
                                datos.setOFD_IDCONCEPTO(Integer.parseInt(IDCUENTACLIENTE));
                                datos.setOFD_IDTIPOCONCEPTO(1); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                                datos.setOFD_DESCRIPCION_AUX(CONCEPTO_CUENTA);
                                datos.setOFD_CANTIDAD(1); // valdor por defecto " 1 "

                                /*
                                * OBSERVACION: 
                                    COMO LAS CUENTAS DEL CLIENTES SON EL EMPEÑO QUE REALIZA, ENTONCES EL IVA DEPENDE DEL DETALLE DEL EMPEÑO, 
                                    Y PUEDE HABER MAS DE UN IVA, ENTONCES PARA ESO HAGO UN SELECT POR EL DETALLE DEL EMPEÑO DE LA CUENTA Y ASI 
                                    VEO CUANTOS IVA TIENE Y REALIZO LOS CALCULOS POR LOS IVA QUE VAYA RECORRIENDO, YA QUE NO SE VAN A REPETIR, 
                                    Y CONCATENO EN UN STRING LOS IVA PARA LUEGO CARGARLO AL CAMPO QUE CORRESPONDE 
                                */
                                String PRECIO_FACT = CONCEPTO_PRECIO;
                                System.out.println("_   _PRECIO_FACT:   "+PRECIO_FACT);

                                // Y FILTRO POR EL DETALLE DE EMPEÑO DE ESAS CUENTAS 
                                List<BeanFacturaCab> listaIva = new ArrayList<>();
                                listaIva = metodosFactura.traerIvaAgendamiento(CC_IDAGENDAMIENTO, CC_ITEM_AGEN);
                                Iterator<BeanFacturaCab> iterListIva = listaIva.iterator();
                                BeanFacturaCab datosIva = new BeanFacturaCab();
                                String totalIvas = "";

                                while(iterListIva.hasNext()) {
                                    System.out.println(".");
                                    System.out.println(".");
                                    System.out.println("________WHILE___IVA_______");
                                    datosIva = iterListIva.next();
                                    // RECUPERO EL IVA 
                                    String iva_detalle = datosIva.getOFD_IDIMPUESTO();
                                    System.out.println("_   _   _IVA:   "+iva_detalle);
                                    // PREGUNTO CUAL ES EL IVA RECUPERADO PARA REALIZAR LOS CALCULOS Y CARGAR LOS CAMPOS 
                                    if (iva_detalle.equalsIgnoreCase("5") || iva_detalle.equals("5%")) {
                                        double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                        System.out.println("_   _   _   _iva5:      "+iva5);
                                        double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                        System.out.println("_   _   _   _grav5:     "+grav5);
                                        CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                        CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                        CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva5);
                                        datos.setOFD_IVA5(formatear.format(iva5));
                                        datos.setOFD_GRAV5(formatear.format(grav5));

                                    } else if (iva_detalle.equalsIgnoreCase("10") || iva_detalle.equals("10%")) {
                                        double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                        System.out.println("_   _   _   _iva10:      "+iva10);
                                        double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                        System.out.println("_   _   _   _grav10:     "+grav10);
                                        CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                        CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                        CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva10);
    //                                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + Double.parseDouble(CF_TXT_TOTAL_IVA10));
                                        datos.setOFD_IVA10(formatear.format(iva10));
                                        datos.setOFD_GRAV10(formatear.format(grav10));
                                    }

                                    // CONDICIONAL PARA PODER CONCATENAR EL IVA EN CASO DE QUE HAYA MAS DE UNO 
                                    if (totalIvas.isEmpty()) {
                                        totalIvas = iva_detalle;
                                    } else {
                                        totalIvas = totalIvas+" / "+iva_detalle;
                                    }
                                } // end while 

                                datos.setOFD_IDIMPUESTO(totalIvas);
                                datos.setOFD_EXENTO("0");
                                datos.setOFD_PRECIO(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
                                datos.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
                            CF_LISTA_DETALLE.add(datos);
                            CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                            // LE SUMO A LAS VARIABLES TOTALES 
                            CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(CONCEPTO_PRECIO));
                        } // end else-if check 
                    } // end while cuentas cliente 

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));

                } else if (accionFCC.equalsIgnoreCase("Cargar Interes") || accionFCC.equalsIgnoreCase("Cargar Interés")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCC__CARGAR_INTERES__------------- FACTURA CUENTA CLIENTE -------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");

                    // OBTENGO LA NUMERACION DE ITEM DE LA LISTA 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    /*
                    LA IDEA VA A SER TRAER LA LISTA QUE CARGUE EN LA GRILLA 
                    Y EN UN CAMPO OCULTO VA A TENER UN NOMBRE ESTATICO MAS EL IDCUENTACLIENTE 
                    Y ENTONCES AL RECUPERAR LA MISMA LISTA VOY TRAYENDO POR EL FOR EL CAMPO DE TEXTO 
                    Y SI TIENE UN VALOR BINARIO 
                    */
                    List<BeanCuentaCliente> listaCtaCliente = null;
    //                listaCtaCliente = metodosFactura.cargarCuentaCliente(TXT_CLIENTE_ID);
                    Iterator<BeanCuentaCliente> iterCta = listaCtaCliente.iterator();
                    BeanCuentaCliente datosCta = null;

                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println("-   -   -   CONTROL     CHECK     -   -   -");
                    String varCheckForm1 = (String) request.getParameter("checkCta1");
                    System.out.println("__1___VAR_CHECK_FORM:       "+varCheckForm1);
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    String CC_IDEMPENHO;
                    int item = 0;
                    while(iterCta.hasNext()) {
                        System.out.println(".");System.out.println(".");System.out.println(".");
                        datosCta = iterCta.next();

                        item = item + 1;
                        System.out.println("___WHILE___ "+item+" ___");

                        // VARIABLES DE LA LISTA 
                        String IDCUENTACLIENTE = datosCta.getCC_IDCUENTACLIENTE();
    //                    String varCheck = "checkCta"+IDCUENTACLIENTE;
    //                    System.out.println("__CHECK:        "+varCheck);
                        System.out.println("__ID_CUENTA_CLIENTE:        "+IDCUENTACLIENTE);

                        CC_IDEMPENHO = datosCta.getCC_IDAGENDAMIENTO();
                        System.out.println("__CC_IDEMPENHO:        :"+CC_IDEMPENHO);

                        // OBTENGO DEL JSP 
                        String varCheckForm = (String) request.getParameter("checkCta"+IDCUENTACLIENTE+"");
                        System.out.println("__"+item+"___VAR_CHECK_FORM:       "+varCheckForm);

                        if (varCheckForm == null) {
                            System.out.println("-   "+item+"_______IF___CHECK_NULL_____-");

                        } else if (varCheckForm.equalsIgnoreCase("ON")) { // SI LA VARIABLE DEVUELVE EL VALOR "ON" ENTONCES ES PORQUE SE ENCUENTRA MARCADO EL CHECK, SINO SE ENCUENTRA MARCADO DEVUELVE EL VALOR NULL 
                            System.out.println("-   "+item+"________IF_______CHECK_ON_________");

                            String CONCEPTO_EMPENHO = datosCta.getCC_COMENTARIO();
    //                        String CONCEPTO_EMPENHO = "Empeño nro. "+datosCta.getOEC_NRO_EMPENHO()+", cuota nro. "+datosCta.getCC_NROCUOTA()+".";
                            System.out.println("_   _   _CONEPTO:   "+CONCEPTO_EMPENHO);
                            String CONCEPTO_PRECIO = datosCta.getCC_SALDO().replace(".","");
                            System.out.println("_   _   _PRECIO :   "+CONCEPTO_PRECIO);

                            // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES CARGO A LA LISTA DEL DETALLE 
                            BeanFacturaCab datos = new BeanFacturaCab();
                                datos.setOFD_ITEM(CF_LISTA_ITEM);
                                datos.setOFD_IDCONCEPTO(Integer.parseInt(IDCUENTACLIENTE));
                                datos.setOFD_IDTIPOCONCEPTO(1); // 2 : PRODUCTO / 1 : CUENTA CLIENTE 
                                datos.setOFD_DESCRIPCION_AUX(CONCEPTO_EMPENHO);
                                datos.setOFD_CANTIDAD(1); // valdor por defecto " 1 "

                                /*
                                * OBSERVACION: 
                                    COMO LAS CUENTAS DEL CLIENTES SON EL EMPEÑO QUE REALIZA, ENTONCES EL IVA DEPENDE DEL DETALLE DEL EMPEÑO, 
                                    Y PUEDE HABER MAS DE UN IVA, ENTONCES PARA ESO HAGO UN SELECT POR EL DETALLE DEL EMPEÑO DE LA CUENTA Y ASI 
                                    VEO CUANTOS IVA TIENE Y REALIZO LOS CALCULOS POR LOS IVA QUE VAYA RECORRIENDO, YA QUE NO SE VAN A REPETIR, 
                                    Y CONCATENO EN UN STRING LOS IVA PARA LUEGO CARGARLO AL CAMPO QUE CORRESPONDE 
                                */
                                String PRECIO_FACT = CONCEPTO_PRECIO;
                                System.out.println("_   _PRECIO_FACT:   "+PRECIO_FACT);

                                // Y FILTRO POR EL DETALLE DE EMPEÑO DE ESAS CUENTAS 
                                List<BeanFacturaCab> listaIva = new ArrayList<>();
    //                            listaIva = metodosFactura.traerIvaEmpenho(CC_IDEMPENHO);
                                Iterator<BeanFacturaCab> iterListIva = listaIva.iterator();
                                BeanFacturaCab datosIva = new BeanFacturaCab();
                                String totalIvas = "";

                                while(iterListIva.hasNext()) {
                                    System.out.println(".");
                                    System.out.println(".");
                                    System.out.println("________WHILE___IVA_______");
                                    datosIva = iterListIva.next();
                                    // RECUPERO EL IVA 
                                    String iva_detalle = datosIva.getOFD_IDIMPUESTO();
                                    System.out.println("_   _   _IVA:   "+iva_detalle);
                                    // PREGUNTO CUAL ES EL IVA RECUPERADO PARA REALIZAR LOS CALCULOS Y CARGAR LOS CAMPOS 
                                    if (iva_detalle.equalsIgnoreCase("5") || iva_detalle.equals("5%")) {
                                        double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                        System.out.println("_   _   _   _iva5:      "+iva5);
                                        double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                        System.out.println("_   _   _   _grav5:     "+grav5);
                                        CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                        CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                        CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva5);
                                        datos.setOFD_IVA5(formatear.format(iva5));
                                        datos.setOFD_GRAV5(formatear.format(grav5));

                                    } else if (iva_detalle.equalsIgnoreCase("10") || iva_detalle.equals("10%")) {
                                        double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                        System.out.println("_   _   _   _iva10:      "+iva10);
                                        double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                        System.out.println("_   _   _   _grav10:     "+grav10);
                                        CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                        CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                        CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + iva10);
    //                                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA) + Double.parseDouble(CF_TXT_TOTAL_IVA10));
                                        datos.setOFD_IVA10(formatear.format(iva10));
                                        datos.setOFD_GRAV10(formatear.format(grav10));
                                    }

                                    // CONDICIONAL PARA PODER CONCATENAR EL IVA EN CASO DE QUE HAYA MAS DE UNO 
                                    if (totalIvas.isEmpty()) {
                                        totalIvas = iva_detalle;
                                    } else {
                                        totalIvas = totalIvas+" / "+iva_detalle;
                                    }
                                } // end while 

                                datos.setOFD_IDIMPUESTO(totalIvas);
                                datos.setOFD_EXENTO("0");
                                datos.setOFD_PRECIO(CONCEPTO_PRECIO);
    //                            datos.setOFD_PRECIO(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
                                datos.setOFD_SUBTOTAL(CONCEPTO_PRECIO);
    //                            datos.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(CONCEPTO_PRECIO)));
                            CF_LISTA_DETALLE.add(datos);
                            CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                            // LE SUMO A LAS VARIABLES TOTALES 
                            CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(CONCEPTO_PRECIO));
                        } // end else-if check 
                    } // end while cuentas cliente 

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                }
                
                
            } else if(accionFCS != null) {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println("---------------------__ACCION_FACTURA___CARGAR_SERVICIO__--------------------------");
                ModelRef_Servicio metodosRefServicio = new ModelRef_Servicio();
                System.out.println(".");
                System.out.println(".");
                if (accionFCS.equalsIgnoreCase("Cargar Servicio")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__BTN_CARGAR_SERVICIO__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");
                    
                    // RECUPERO DE LA SESION EL ITEM Y LA LISTA QUE VOY A UTILIZAR EN EL DETALLE DE LA FACTURA COMO PARA CARGAR LOS PRODUCTOS PORQUE AL FINAL ESTA LISTA Y ITEM LUEGO LES VOY A PASAR AL DETALLE DE LA FACTURA 
                    // LAS RECUPERO DE LA SESION PORQUE PUEDE DARSE EL CASO DE QUE PRODUCTO A PRODUCTO VAYA CARGANDOSE A LA LISTA Y SI ACA LE PASO VACIO, ENTONCES AL CARGAR TODO UN PRODUCTO, AL QUERER CARGAR EL OTRO LA LISTA Y ITEM YA SE VAN A RESETEAR 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }
                    
                    // RECUPERO LOS DATOS DEL SERVICIO SELECCIONADO 
                    String CBX_IDSERVICIO = (String) request.getParameter("cbxAddNewProd"); // CONTROLAR EL METODO QUE CARGA EL COMBO BOX DE PRODUCTO PARA SABER SI ES IDSTOCK O IDPRODUCTO LO QUE CARGA COMO KEY 
                    System.out.println("__  ____CBX_ID_SERVICIO:   "+CBX_IDSERVICIO);
                    // RECUPERO LOS OTROS DATOS DEL PRODUCTO SELECCIONADO POR MEDIO DE SU ID 
                    List<BeanServicio> listaDatosServ = new ArrayList<>();
                    listaDatosServ = metodosRefServicio.traerDatosServicios(CBX_IDSERVICIO);
                    Iterator<BeanServicio> iterProducto = listaDatosServ.iterator();
                    BeanServicio datosProdNew = null;
                    
                    String SERVICIO_DESC="", SERVICIO_IVA="", SERVICIO_PRECIO="";
//                    String PROD_LOTE="", PROD_STOCK="";
                    
                    while(iterProducto.hasNext()) {
                        datosProdNew = iterProducto.next();
                        
//                        PROD_LOTE = datosProdNew.getOS_LOTE();
//                        PROD_STOCK = String.valueOf(datosProdNew.getOS_STOCK());
                        CBX_IDSERVICIO = datosProdNew.getRHS_IDSERVICIO();
                        SERVICIO_DESC = datosProdNew.getRHS_DESC_SERVICIO();
                        SERVICIO_PRECIO = datosProdNew.getRHS_MONTO();
                        SERVICIO_IVA = String.valueOf(datosProdNew.getRHS_IVA());
                    } // end while 
                    
                    var_redireccionar = 1;
                    acceso = "crear_factura_serv.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
//                    request.setAttribute("CF_FCP_TXT_ID_STOCK", CBX_IDSTOCK);
//                    request.setAttribute("CF_FCP_TXT_LOTE", PROD_LOTE);
//                    request.setAttribute("CF_FCP_TXT_STOCK", PROD_STOCK);
                    request.setAttribute("CF_FCS_TXT_ID_SERVICIO", CBX_IDSERVICIO);
                    request.setAttribute("CF_FCS_TXT_DESCRIPCION", SERVICIO_DESC);
//                    request.setAttribute("CF_FCP_TXT_MARCA", PRODUCTO_MARCA);
                    request.setAttribute("CF_FCS_TXT_CANTIDAD", "1");
                    request.setAttribute("CF_FCS_CBX_IVA", SERVICIO_IVA);
                    request.setAttribute("CF_FCS_TXT_PRECIO", SERVICIO_PRECIO);
                    
                } else if (accionFCS.equalsIgnoreCase("Cargar Servicio a la Lista")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__CARGAR_SERVICIO_A_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");
                    
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }
                    
                    // RECUPERO LOS DATOS DEL SERVICIO ------------------------------------ 
//                    String IDSTOCK = (String) request.getParameter("tIS");
//                    String LOTE = (String) request.getParameter("tLo");
//                    String STOCK = (String) request.getParameter("tSP");
                    String IDSERVICIO = (String) request.getParameter("tI");
                    String SERVICIO_DESC = (String) request.getParameter("tD");
//                    String PROD_MARCA = (String) request.getParameter("tM");
                    String SERV_IVA = (String) request.getParameter("cI");
                    String SERV_PRECIO = (String) request.getParameter("tP");
                    if (SERV_PRECIO == null || SERV_PRECIO.isEmpty()) {
                        //
                    } else {
                        SERV_PRECIO = formatear.format(Double.parseDouble(SERV_PRECIO.replace(".","").replace(",","."))).replace(".","");
                    }
                    String SERV_CANTIDAD = (String) request.getParameter("tC");
                    if (SERV_CANTIDAD == null || SERV_CANTIDAD.equals("") || Integer.parseInt(SERV_CANTIDAD) <= 0) {
                        SERV_CANTIDAD = "1";
                    }
                    String SERV_SUBTOTAL = "0";
                    System.out.println("-------------__SERVICIO__------------------");
//                    System.out.println("_   _______ID_STOCK:   "+IDSTOCK);
//                    System.out.println("_   ___________LOTE:   "+LOTE);
//                    System.out.println("_   __________STOCK:   "+STOCK);
                    System.out.println("_   ____ID_SERVICIO:   "+IDSERVICIO);
                    System.out.println("_   __SERVICIO_DESC:   "+SERVICIO_DESC);
//                    System.out.println("_   _____PROD_MARCA:   "+PROD_MARCA);
                    System.out.println("_   ___PRODUCTO_IVA:   "+SERV_IVA);
                    System.out.println("_   ____SERV_PRECIO:   "+SERV_PRECIO);
                    System.out.println("_   __SERV_CANTIDAD:   "+SERV_CANTIDAD);
                    System.out.println("-------------------------------------------");
                    
                    // VALIDACIONES 
                    String TIPO_MENSAJE="", MENSAJE=null;
                    // CONTROLO PRIMERAMENTE QUE NO SE HAYAN DEJADOS CAMPOS VACIOS PARA AÑADIR A LA LISTA 
                    if (IDSERVICIO == null || IDSERVICIO.isEmpty()) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el servicio para añadirlo a la lista.";

                    } else if(SERV_PRECIO == null || SERV_PRECIO.isEmpty() || Double.parseDouble(SERV_PRECIO) <= 0) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el precio al servicio.";

//                    } else if(STOCK == null || Double.parseDouble(STOCK) <= 0) { // VALIDACION PARA CONTROLAR EL SOTCK DEL PRODUCTO  
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "El producto no cuenta con stock suficiente.";
//
//                    } else if(Double.parseDouble(STOCK) < Double.parseDouble(PROD_CANTIDAD)) { // VALIDACION PARA EVITAR QUERER AGREGAR UN PRODUCTO CON LA CANTIDAD POR ENCIMA DEL STOCK DEL PRODUCTO 
//                        TIPO_MENSAJE = "2";
//                        MENSAJE = "La cantidad no puede ser mayor a la que se tiene en stock("+STOCK+").";

                    } else {
                        // SI LA CANTIDAD ES DIFERENTE A 1 ENTONCES CALCULO EL SUBTOTAL 
                        if (SERV_CANTIDAD.equals("1")) {
                            SERV_SUBTOTAL = SERV_PRECIO;
                        } else {
                            double precio = Double.parseDouble(SERV_PRECIO);
                            double cantidad = Double.parseDouble(SERV_CANTIDAD);
                            SERV_SUBTOTAL = formatear.format(precio*cantidad);
                        }
                        System.out.println("___SUB_TOTAL:    "+SERV_SUBTOTAL);
                        // INGRESO A LA LISTA  
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(CF_LISTA_ITEM);
                            datosNew.setOFD_IDTIPOCONCEPTO(5); // 5 : SERVICIO / 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDSERVICIO)); // LE CARGO AL IDCONCEPTO EL IDSTOCK PARA PODER OBTENER DEL STOCK NOMAS LOS DATOS DEL PRODUCTO Y NO AGREGAR CAMPOS A LA TABLA DE FACTURA (DET) 
                            datosNew.setOFD_DESCRIPCION_AUX(SERVICIO_DESC);
                            datosNew.setOF_OBSERVACION(""); // MIENTRAS TANTO PARA QUE EL USUARIO PUEDA OBSERVAR EN PANTALLA LA MARCA DEL PRODUCTO, LO CARGO EN OBSERVACION DE LA CABECERA, ESTE CAMPO NORMALMENTE SE LE LANZA VACIO O NULO A LA BASE, ASI QUE POR ESO LO UTILIZO BREVEMENTE PARA VISUALIZAR LA MARCA 
                            datosNew.setOFD_IDIMPUESTO(SERV_IVA);
                            datosNew.setOFD_CANTIDAD(Integer.parseInt(SERV_CANTIDAD));
                            datosNew.setOFD_PRECIO(SERV_PRECIO);
                            datosNew.setOFD_SUBTOTAL(SERV_SUBTOTAL.replace(".", ""));
                        CF_LISTA_DETALLE.add(datosNew);
                        CF_LISTA_ITEM = CF_LISTA_ITEM + 1; // LE SUMO AL ITEM PARA QUE SEA INCREMENTABLE Y NO PASE SIEMPRE EL MISMO NUMERO 
                    } // END IF VALIDACION.-
                    
                    var_redireccionar = 1;
                    acceso = "crear_factura_serv.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    request.setAttribute("CF_CS_TIPO_MENSAJE", TIPO_MENSAJE);
                    request.setAttribute("CF_CS_MENSAJE", MENSAJE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS.-
                    // SI EL MENSAJE ESTA CARGADO, ES PORQUE SE ACTIVO ALGUNA VALIDACION, Y ENTONCES LE VUELVO A PASAR LOS CAMPOS QUE SE CARGO Y NO LOS LIMPIO 
                    if (MENSAJE != null) {
//                        request.setAttribute("CF_FCP_TXT_ID_STOCK", IDSTOCK);
//                        request.setAttribute("CF_FCP_TXT_LOTE", LOTE);
//                        request.setAttribute("CF_FCP_TXT_STOCK", STOCK);
                        request.setAttribute("CF_FCS_TXT_ID_SERVICIO", IDSERVICIO);
                        request.setAttribute("CF_FCS_TXT_DESCRIPCION", SERVICIO_DESC);
//                        request.setAttribute("CF_FCP_TXT_MARCA", PROD_MARCA);
                        request.setAttribute("CF_FCS_TXT_CANTIDAD", SERV_CANTIDAD);
                        request.setAttribute("CF_FCS_CBX_IVA", SERV_IVA);
                        request.setAttribute("CF_FCS_TXT_PRECIO", SERV_PRECIO);
                    } else { // EN CASO DE QUE MENSAJE SE MANTENGA NULL ENTONCES SE SUPONE QUE NO SE ACTIVO ALGUNA VALIDACION Y SE CARGO CORRECTAMENTE LA LISTA Y LE PASO VACIO LOS CAMPOS PARA LIMPIARLE 
                        request.setAttribute("CF_FCS_TXT_ID_PRODUCTO", "");
                        request.setAttribute("CF_FCS_TXT_DESCRIPCION", "");
//                        request.setAttribute("CF_FCP_TXT_MARCA", "");
                        request.setAttribute("CF_FCS_TXT_CANTIDAD", "1");
                        request.setAttribute("CF_FCS_CBX_IVA", "");
                        request.setAttribute("CF_FCS_TXT_PRECIO", "");
                    }
                    
                } else if (accionFCS.equalsIgnoreCase("Eliminar")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__ELIMINAR_SERVICIO_DE_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");
                    
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }
                    
                    // RECUPERO LOS DATOS DEL SERVICIO A ELIMINAR 
                    String IDSERVICIO = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ITEM CONCEPTO 
                    int CF_ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAI"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0 // tAI : TXT AUXILIAR ITEM 
                    System.out.println("-------------__SERVICIO__------------------");
                    System.out.println("_   _______ID_SERVICIO:  "+IDSERVICIO);
                    System.out.println("_   __ITEM_ELIMINAR:  "+CF_ITEM_ELIMINAR);
                    System.out.println("-------------------------------------------");
                    
                    // ELIMINO DE LA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        CF_LISTA_DETALLE.remove(CF_ITEM_ELIMINAR);

                        // ORDENAR LOS ITEMS 
                        for (BeanFacturaCab LISTA_DETALLE : CF_LISTA_DETALLE) {
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanFacturaCab datosNew = new BeanFacturaCab();
                                datosNew.setOFD_ITEM(itemNew);
                                datosNew.setOFD_IDTIPOCONCEPTO(LISTA_DETALLE.getOFD_IDTIPOCONCEPTO()); // 5 : SERVICIO / 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                                datosNew.setOFD_IDCONCEPTO(LISTA_DETALLE.getOFD_IDCONCEPTO()); // IDSTOCK (ANTES ESTABA IDPRODUCTO) 
                                datosNew.setOFD_DESCRIPCION_AUX(LISTA_DETALLE.getOFD_DESCRIPCION_AUX());
                                datosNew.setOF_OBSERVACION(LISTA_DETALLE.getOF_OBSERVACION()); // MIENTRAS TANTO PARA QUE EL USUARIO PUEDA OBSERVAR EN PANTALLA LA MARCA DEL PRODUCTO, LO CARGO EN OBSERVACION DE LA CABECERA, ESTE CAMPO NORMALMENTE SE LE LANZA VACIO O NULO A LA BASE, ASI QUE POR ESO LO UTILIZO BREVEMENTE PARA VISUALIZAR LA MARCA 
                                datosNew.setOFD_IDIMPUESTO(LISTA_DETALLE.getOFD_IDIMPUESTO());
                                datosNew.setOFD_CANTIDAD(LISTA_DETALLE.getOFD_CANTIDAD());
                                datosNew.setOFD_PRECIO(LISTA_DETALLE.getOFD_PRECIO());
                                datosNew.setOFD_SUBTOTAL(LISTA_DETALLE.getOFD_SUBTOTAL());
                            CF_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".");System.out.println(".");
                        itemNew = CF_LISTA_ITEM; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FACTURA_____ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");
                    } // end catch 
                    
                    var_redireccionar = 1;
                    acceso = "crear_factura_serv.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(itemNew)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
//                    request.setAttribute("CF_FCP_TXT_ID_STOCK", "");
//                    request.setAttribute("CF_FCP_TXT_LOTE", "");
//                    request.setAttribute("CF_FCP_TXT_STOCK", "");
                    request.setAttribute("CF_FCS_TXT_ID_SERVICIO", "");
                    request.setAttribute("CF_FCS_TXT_DESCRIPCION", "");
//                    request.setAttribute("CF_FCP_TXT_MARCA", "");
                    request.setAttribute("CF_FCS_TXT_CANTIDAD", "1");
                    request.setAttribute("CF_FCS_CBX_IVA", "");
                    request.setAttribute("CF_FCS_TXT_PRECIO", "");

                } else if (accionFCS.equalsIgnoreCase("Cargar Interes") || accionFCS.equalsIgnoreCase("Cargar Interés")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCS__CARGAR_INTERES__------------- FACTURA CARGAR SERVICIO -------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDSERVICIO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String SERVICIO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String SERVICIO = datosLista.getOFD_DESCRIPCION_AUX();
                        String SERVICIO_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int SERVICIO_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(SERVICIO_PRECIO)*Double.parseDouble(String.valueOf(SERVICIO_CANTIDAD))).replace(".","");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDSERVICIO));
                            datosNew.setOFD_IDTIPOCONCEPTO(5); // 5 : SERVICIO / 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_DESCRIPCION_AUX(SERVICIO);
                            datosNew.setOFD_CANTIDAD(SERVICIO_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(SERVICIO_IVA);
                            if (SERVICIO_IVA.equalsIgnoreCase("5") || SERVICIO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (SERVICIO_IVA.equalsIgnoreCase("10") || SERVICIO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(SERVICIO_PRECIO);
    //                        datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(PRODUCTO_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(PRECIO_FACT);
    //                        datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                    
                } else if (accionFCS.equalsIgnoreCase("Cargar Detalle")) { /* BOTON QUE SE ENCUENTRA EN FACTURA CARGAR PRODUCTO */
                    System.out.println("---------------------__FCS__CARGAR_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    // OBSERVACION: LE CARGO CON CERO YA QUE VA A VOLVER A SELECCIONAR LAS CUENTAS PARA CARGAR EL DETALLE DE LA FACTURA Y PARA EVITAR QUE MANTENGA ALGUN DATO (EJEMPLO SI CARGA ALGUN PRODUCTO DE 5% Y LUEGO ELIMINA Y CARGA UNA CUENTA DE 10% EL DETALLE LE VA A MOSTRAR EL VALOR ANTERIOR DE 5% CUANDO YA NO HAYA EN EL DETALLE NADA DE 5%)
                    String CF_TXT_TOTAL_IVA5 = "0";
                    String CF_TXT_TOTAL_IVA10 = "0";
                    String CF_TXT_TOTAL_GRAV5 = "0";
                    String CF_TXT_TOTAL_GRAV10 = "0";
                    String CF_TXT_TOTAL_IVA = "0";
                    String CF_TXT_TOTAL = "0";
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDSERVICIO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String SERVICIO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String SERVICIO = datosLista.getOFD_DESCRIPCION_AUX();
                        String SERVICIO_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int SERVICIO_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(SERVICIO_PRECIO)*Double.parseDouble(String.valueOf(SERVICIO_CANTIDAD))).replace(".","");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDSERVICIO));
                            datosNew.setOFD_IDTIPOCONCEPTO(5); // 5 : SERVICIO / 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_DESCRIPCION_AUX(SERVICIO);
                            datosNew.setOFD_CANTIDAD(SERVICIO_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(SERVICIO_IVA);
                            if (SERVICIO_IVA.equalsIgnoreCase("5") || SERVICIO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (SERVICIO_IVA.equalsIgnoreCase("10") || SERVICIO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(SERVICIO_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                }
                
                
                
            } else if(accionFCP != null) {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println("---------------------__ACCION_FACTURA_CUENTA_PRODUCTO__--------------------------");
                System.out.println(".");
                System.out.println(".");
                if (accionFCP.equalsIgnoreCase("Cargar Producto")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__BTN_CARGAR_PRODUCTO__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // RECUPERO DE LA SESION EL ITEM Y LA LISTA QUE VOY A UTILIZAR EN EL DETALLE DE LA FACTURA COMO PARA CARGAR LOS PRODUCTOS PORQUE AL FINAL ESTA LISTA Y ITEM LUEGO LES VOY A PASAR AL DETALLE DE LA FACTURA 
                    // LAS RECUPERO DE LA SESION PORQUE PUEDE DARSE EL CASO DE QUE PRODUCTO A PRODUCTO VAYA CARGANDOSE A LA LISTA Y SI ACA LE PASO VACIO, ENTONCES AL CARGAR TODO UN PRODUCTO, AL QUERER CARGAR EL OTRO LA LISTA Y ITEM YA SE VAN A RESETEAR 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

    //            // RECUPERO LOS DATOS DEL PRODUCTO SELECCIONADO 
    //                String CBX_IDSTOCK = (String) request.getParameter("cbxAddNewProd"); // CONTROLAR EL METODO QUE CARGA EL COMBO BOX DE PRODUCTO PARA SABER SI ES IDSTOCK O IDPRODUCTO LO QUE CARGA COMO KEY 
    //                System.out.println("__  ____CBX_ID_STOCK:   "+CBX_IDSTOCK);
    //            // RECUPERO LOS OTROS DATOS DEL PRODUCTO SELECCIONADO POR MEDIO DE SU ID 
    //                List<BeanStock> listaDatosProd = new ArrayList<>();
    //                listaDatosProd = metodosEmpenho.traerDatosProductoStock(CBX_IDSTOCK);
    //                Iterator<BeanStock> iterProducto = listaDatosProd.iterator();
    //                BeanStock datosProdNew = null;
    //                String CBX_IDPRODUCTO="", PRODUCTO_DESC="", PRODUCTO_MARCA="", PRODUCTO_IVA="", PRODUCTO_PRECIO="", PRODUCTO_IDMARCA="";
    //                String PROD_LOTE="", PROD_STOCK="";
    //                while(iterProducto.hasNext()) {
    //                    datosProdNew = iterProducto.next();
    //                    
    //                    PROD_LOTE = datosProdNew.getOS_LOTE();
    //                    PROD_STOCK = String.valueOf(datosProdNew.getOS_STOCK());
    //                    CBX_IDPRODUCTO = String.valueOf(datosProdNew.getOS_IDPRODUCTO());
    //                    PRODUCTO_DESC = datosProdNew.getOP_DESCRIPCION();
    //                    PRODUCTO_PRECIO = datosProdNew.getOP_PRECIO();
    //                    PRODUCTO_IVA = String.valueOf(datosProdNew.getOP_IVA());
    //                    PRODUCTO_IDMARCA = String.valueOf(datosProdNew.getOP_IDMARCA());
    //                    PRODUCTO_MARCA = datosProdNew.getOM_MARCA();
    //                } // end while 

                    var_redireccionar = 1;
                    acceso = "crear_factura_pro.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
    //                request.setAttribute("CF_FCP_TXT_ID_STOCK", CBX_IDSTOCK);
    //                request.setAttribute("CF_FCP_TXT_LOTE", PROD_LOTE);
    //                request.setAttribute("CF_FCP_TXT_STOCK", PROD_STOCK);
    //                request.setAttribute("CF_FCP_TXT_ID_PRODUCTO", CBX_IDPRODUCTO);
    //                request.setAttribute("CF_FCP_TXT_DESCRIPCION", PRODUCTO_DESC);
    //                request.setAttribute("CF_FCP_TXT_MARCA", PRODUCTO_MARCA);
    //                request.setAttribute("CF_FCP_TXT_CANTIDAD", "1");
    //                request.setAttribute("CF_FCP_CBX_IVA", PRODUCTO_IVA);
    //                request.setAttribute("CF_FCP_TXT_PRECIO", PRODUCTO_PRECIO);

                } else if (accionFCP.equalsIgnoreCase("Cargar Producto a la Lista")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__CARGAR_PRODUCTO_A_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO 
                    String IDSTOCK = (String) request.getParameter("tIS");
                    String LOTE = (String) request.getParameter("tLo");
                    String STOCK = (String) request.getParameter("tSP");
                    String IDPRODUCTO = (String) request.getParameter("tI");
                    String PRODUCTO_DESC = (String) request.getParameter("tD");
                    String PROD_MARCA = (String) request.getParameter("tM");
                    String PROD_IVA = (String) request.getParameter("cI");
                    String PROD_PRECIO = (String) request.getParameter("tP");
                    if (PROD_PRECIO == null || PROD_PRECIO.isEmpty()) {
                        //
                    } else {
                        PROD_PRECIO = formatear.format(Double.parseDouble(PROD_PRECIO.replace(".","").replace(",","."))).replace(".","");
                    }
                    String PROD_CANTIDAD = (String) request.getParameter("tC");
                    if (PROD_CANTIDAD == null || PROD_CANTIDAD.equals("") || Integer.parseInt(PROD_CANTIDAD) <= 0) {
                        PROD_CANTIDAD = "1";
                    }
                    String PROD_SUBTOTAL = "0";
                    System.out.println("-------------__PRODUCTO__------------------");
                    System.out.println("_   _______ID_STOCK:   "+IDSTOCK);
                    System.out.println("_   ___________LOTE:   "+LOTE);
                    System.out.println("_   __________STOCK:   "+STOCK);
                    System.out.println("_   ____ID_PRODUCTO:   "+IDPRODUCTO);
                    System.out.println("_   __PRODUCTO_DESC:   "+PRODUCTO_DESC);
                    System.out.println("_   _____PROD_MARCA:   "+PROD_MARCA);
                    System.out.println("_   ___PRODUCTO_IVA:   "+PROD_IVA);
                    System.out.println("_   ____PROD_PRECIO:   "+PROD_PRECIO);
                    System.out.println("_   __PROD_CANTIDAD:   "+PROD_CANTIDAD);
                    System.out.println("-------------------------------------------");

                    // VALIDACIONES 
                    String TIPO_MENSAJE="", MENSAJE=null;
                    // CONTROLO PRIMERAMENTE QUE NO SE HAYAN DEJADOS CAMPOS VACIOS PARA AÑADIR A LA LISTA 
                    if (IDPRODUCTO == null || IDPRODUCTO.isEmpty()) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el producto para añadirlo a la lista.";

                    } else if(PROD_PRECIO == null || PROD_PRECIO.isEmpty() || Double.parseDouble(PROD_PRECIO) <= 0) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el precio al producto.";

                    } else if(STOCK == null || Double.parseDouble(STOCK) <= 0) { // VALIDACION PARA CONTROLAR EL SOTCK DEL PRODUCTO  
                        TIPO_MENSAJE = "2";
                        MENSAJE = "El producto no cuenta con stock suficiente.";

                    } else if(Double.parseDouble(STOCK) < Double.parseDouble(PROD_CANTIDAD)) { // VALIDACION PARA EVITAR QUERER AGREGAR UN PRODUCTO CON LA CANTIDAD POR ENCIMA DEL STOCK DEL PRODUCTO 
                        TIPO_MENSAJE = "2";
                        MENSAJE = "La cantidad no puede ser mayor a la que se tiene en stock("+STOCK+").";

                    } else {
                        // SI LA CANTIDAD ES DIFERENTE A 1 ENTONCES CALCULO EL SUBTOTAL 
                        if (PROD_CANTIDAD.equals("1")) {
                            PROD_SUBTOTAL = PROD_PRECIO;
                        } else {
                            double precio = Double.parseDouble(PROD_PRECIO);
                            double cantidad = Double.parseDouble(PROD_CANTIDAD);
                            PROD_SUBTOTAL = formatear.format(precio*cantidad);
                        }
                        System.out.println("___SUB_TOTAL:    "+PROD_SUBTOTAL);
                        // INGRESO A LA LISTA  
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(CF_LISTA_ITEM);
                            datosNew.setOFD_IDTIPOCONCEPTO(2); // 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDSTOCK)); // LE CARGO AL IDCONCEPTO EL IDSTOCK PARA PODER OBTENER DEL STOCK NOMAS LOS DATOS DEL PRODUCTO Y NO AGREGAR CAMPOS A LA TABLA DE FACTURA (DET) 
                            datosNew.setOFD_DESCRIPCION_AUX(PRODUCTO_DESC);
                            datosNew.setOF_OBSERVACION(PROD_MARCA); // MIENTRAS TANTO PARA QUE EL USUARIO PUEDA OBSERVAR EN PANTALLA LA MARCA DEL PRODUCTO, LO CARGO EN OBSERVACION DE LA CABECERA, ESTE CAMPO NORMALMENTE SE LE LANZA VACIO O NULO A LA BASE, ASI QUE POR ESO LO UTILIZO BREVEMENTE PARA VISUALIZAR LA MARCA 
                            datosNew.setOFD_IDIMPUESTO(PROD_IVA);
                            datosNew.setOFD_CANTIDAD(Integer.parseInt(PROD_CANTIDAD));
                            datosNew.setOFD_PRECIO(PROD_PRECIO);
                            datosNew.setOFD_SUBTOTAL(PROD_SUBTOTAL.replace(".", ""));
                        CF_LISTA_DETALLE.add(datosNew);
                        CF_LISTA_ITEM = CF_LISTA_ITEM + 1; // LE SUMO AL ITEM PARA QUE SEA INCREMENTABLE Y NO PASE SIEMPRE EL MISMO NUMERO 
                    } // END IF VALIDACION.-

                    var_redireccionar = 1;
                    acceso = "crear_factura_pro.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    request.setAttribute("CF_CP_TIPO_MENSAJE", TIPO_MENSAJE);
                    request.setAttribute("CF_CP_MENSAJE", MENSAJE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS.-
                    // SI EL MENSAJE ESTA CARGADO, ES PORQUE SE ACTIVO ALGUNA VALIDACION, Y ENTONCES LE VUELVO A PASAR LOS CAMPOS QUE SE CARGO Y NO LOS LIMPIO 
                    if (MENSAJE != null) {
                        request.setAttribute("CF_FCP_TXT_ID_STOCK", IDSTOCK);
                        request.setAttribute("CF_FCP_TXT_LOTE", LOTE);
                        request.setAttribute("CF_FCP_TXT_STOCK", STOCK);
                        request.setAttribute("CF_FCP_TXT_ID_PRODUCTO", IDPRODUCTO);
                        request.setAttribute("CF_FCP_TXT_DESCRIPCION", PRODUCTO_DESC);
                        request.setAttribute("CF_FCP_TXT_MARCA", PROD_MARCA);
                        request.setAttribute("CF_FCP_TXT_CANTIDAD", PROD_CANTIDAD);
                        request.setAttribute("CF_FCP_CBX_IVA", PROD_IVA);
                        request.setAttribute("CF_FCP_TXT_PRECIO", PROD_PRECIO);
                    } else { // EN CASO DE QUE MENSAJE SE MANTENGA NULL ENTONCES SE SUPONE QUE NO SE ACTIVO ALGUNA VALIDACION Y SE CARGO CORRECTAMENTE LA LISTA Y LE PASO VACIO LOS CAMPOS PARA LIMPIARLE 

                        request.setAttribute("CF_FCP_TXT_ID_PRODUCTO", "");
                        request.setAttribute("CF_FCP_TXT_DESCRIPCION", "");
                        request.setAttribute("CF_FCP_TXT_MARCA", "");
                        request.setAttribute("CF_FCP_TXT_CANTIDAD", "1");
                        request.setAttribute("CF_FCP_CBX_IVA", "");
                        request.setAttribute("CF_FCP_TXT_PRECIO", "");
                    }

                } else if (accionFCP.equalsIgnoreCase("Eliminar")) { /* BOTON QUE SE ENCUENTRA EN EL JSP DE FACTURA CARGAR PRODUCTO  */ 
                    System.out.println("---------------------__ELIMINAR_PRODUCTO_DE_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO A ELIMINAR 
                    String IDSTOCK = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ITEM CONCEPTO 
    //                String IDPRODUCTO = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ITEM CONCEPTO 
                    int CF_ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAI"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0 // tAI : TXT AUXILIAR ITEM 
                    System.out.println("-------------__PRODUCTO__------------------");
                    System.out.println("_   _______ID_STOCK:  "+IDSTOCK);
    //                System.out.println("_   ____ID_PRODUCTO:  "+IDPRODUCTO);
                    System.out.println("_   __ITEM_ELIMINAR:  "+CF_ITEM_ELIMINAR);
                    System.out.println("-------------------------------------------");

                    // ELIMINO DE LA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        CF_LISTA_DETALLE.remove(CF_ITEM_ELIMINAR);

                        // ORDENAR LOS ITEMS 
                        for (BeanFacturaCab LISTA_DETALLE : CF_LISTA_DETALLE) {
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanFacturaCab datosNew = new BeanFacturaCab();
                                datosNew.setOFD_ITEM(itemNew);
                                datosNew.setOFD_IDTIPOCONCEPTO(LISTA_DETALLE.getOFD_IDTIPOCONCEPTO()); // 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                                datosNew.setOFD_IDCONCEPTO(LISTA_DETALLE.getOFD_IDCONCEPTO()); // IDSTOCK (ANTES ESTABA IDPRODUCTO) 
                                datosNew.setOFD_DESCRIPCION_AUX(LISTA_DETALLE.getOFD_DESCRIPCION_AUX());
                                datosNew.setOF_OBSERVACION(LISTA_DETALLE.getOF_OBSERVACION()); // MIENTRAS TANTO PARA QUE EL USUARIO PUEDA OBSERVAR EN PANTALLA LA MARCA DEL PRODUCTO, LO CARGO EN OBSERVACION DE LA CABECERA, ESTE CAMPO NORMALMENTE SE LE LANZA VACIO O NULO A LA BASE, ASI QUE POR ESO LO UTILIZO BREVEMENTE PARA VISUALIZAR LA MARCA 
                                datosNew.setOFD_IDIMPUESTO(LISTA_DETALLE.getOFD_IDIMPUESTO());
                                datosNew.setOFD_CANTIDAD(LISTA_DETALLE.getOFD_CANTIDAD());
                                datosNew.setOFD_PRECIO(LISTA_DETALLE.getOFD_PRECIO());
                                datosNew.setOFD_SUBTOTAL(LISTA_DETALLE.getOFD_SUBTOTAL());
                            CF_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".");System.out.println(".");
                        itemNew = CF_LISTA_ITEM; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FACTURA_____ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");
                    } // end catch 

                    var_redireccionar = 1;
                    acceso = "crear_factura_pro.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(itemNew)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS 
                    request.setAttribute("CF_FCP_TXT_ID_STOCK", "");
                    request.setAttribute("CF_FCP_TXT_LOTE", "");
                    request.setAttribute("CF_FCP_TXT_STOCK", "");
                    request.setAttribute("CF_FCP_TXT_ID_PRODUCTO", "");
                    request.setAttribute("CF_FCP_TXT_DESCRIPCION", "");
                    request.setAttribute("CF_FCP_TXT_MARCA", "");
                    request.setAttribute("CF_FCP_TXT_CANTIDAD", "1");
                    request.setAttribute("CF_FCP_CBX_IVA", "");
                    request.setAttribute("CF_FCP_TXT_PRECIO", "");

                } else if (accionFCP.equalsIgnoreCase("Cargar Interes") || accionFCP.equalsIgnoreCase("Cargar Interés")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCP__CARGAR_INTERES__------------- FACTURA CARGAR PRODUCTO -------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDPRODUCTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String PRODUCTO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String PRODUCTO = datosLista.getOFD_DESCRIPCION_AUX();
                        String PRODUCTO_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int PRODUCTO_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(PRODUCTO_PRECIO)*Double.parseDouble(String.valueOf(PRODUCTO_CANTIDAD))).replace(".","");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDPRODUCTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(2); // 2 : PRODUCTO (DEL STOCK) / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_DESCRIPCION_AUX(PRODUCTO);
                            datosNew.setOFD_CANTIDAD(PRODUCTO_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(PRODUCTO_IVA);
                            if (PRODUCTO_IVA.equalsIgnoreCase("5") || PRODUCTO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (PRODUCTO_IVA.equalsIgnoreCase("10") || PRODUCTO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(PRODUCTO_PRECIO);
    //                        datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(PRODUCTO_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(PRECIO_FACT);
    //                        datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                    
                } else if (accionFCP.equalsIgnoreCase("Cargar Detalle")) { /* BOTON QUE SE ENCUENTRA EN FACTURA CARGAR PRODUCTO */
                    System.out.println("---------------------__FCP_CARGAR_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    // OBSERVACION: LE CARGO CON CERO YA QUE VA A VOLVER A SELECCIONAR LAS CUENTAS PARA CARGAR EL DETALLE DE LA FACTURA Y PARA EVITAR QUE MANTENGA ALGUN DATO (EJEMPLO SI CARGA ALGUN PRODUCTO DE 5% Y LUEGO ELIMINA Y CARGA UNA CUENTA DE 10% EL DETALLE LE VA A MOSTRAR EL VALOR ANTERIOR DE 5% CUANDO YA NO HAYA EN EL DETALLE NADA DE 5%)
                    String CF_TXT_TOTAL_IVA5 = "0";
                    String CF_TXT_TOTAL_IVA10 = "0";
                    String CF_TXT_TOTAL_GRAV5 = "0";
                    String CF_TXT_TOTAL_GRAV10 = "0";
                    String CF_TXT_TOTAL_IVA = "0";
                    String CF_TXT_TOTAL = "0";
    //                String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
    //                String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
    //                String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
    //                String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
    //                String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
    //                String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("- - - - - - - - -");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA       :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT      :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDPRODUCTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String PRODUCTO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String PRODUCTO = datosLista.getOFD_DESCRIPCION_AUX();
                        String PRODUCTO_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int PRODUCTO_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(PRODUCTO_PRECIO)*Double.parseDouble(String.valueOf(PRODUCTO_CANTIDAD))).replace(".","");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDPRODUCTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(2); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES / 4 : DESCUENTO 
                            datosNew.setOFD_DESCRIPCION_AUX(PRODUCTO);
                            datosNew.setOFD_CANTIDAD(PRODUCTO_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(PRODUCTO_IVA);
                            if (PRODUCTO_IVA.equalsIgnoreCase("5") || PRODUCTO_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (PRODUCTO_IVA.equalsIgnoreCase("10") || PRODUCTO_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(PRODUCTO_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
    //                sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                }
                
                
            } else if(accionFCI != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__ACCION_FACTURA_CUENTA_INTERES__--------------------------");
                System.out.println(".");System.out.println(".");
                if (accionFCI.equalsIgnoreCase("Cargar Interés a la Lista")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCI__CARGAR_INTERES_A_LA_LISTA__--------------------------");
                    System.out.println("---------------------__CARGAR_PRODUCTO_A_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO 
                    String INTERES_DESC = (String) request.getParameter("tD");
                    String INTERES_IVA = (String) request.getParameter("cI");
                    String INTERES_PRECIO = (String) request.getParameter("tP");
                    if (INTERES_PRECIO == null || INTERES_PRECIO.isEmpty()) {
                        //
                    } else {
                        INTERES_PRECIO = formatear.format(Double.parseDouble(INTERES_PRECIO.replace(".","").replace(",","."))).replace(".","");
                    }
                    String INTERES_CANTIDAD = "1"; // SI LA CANTIDAD SE CARGARA ENTONCES AHI SI SE TIENE QUE CALCULAR EL SUBTOTAL MULTIPLICANDOLO POR EL PRECIO COMO EN LA VENTANA DE CARGAR PRODUCTO, PERO COMO EL VALOR ES 1, ENTONCES NO HAY NECESIDAD 
                    String INTERES_SUBTOTAL = INTERES_PRECIO; // SI LA CANTIDAD TIENE LA POSIBILIDAD DE SER DIFERENTE A UNO, ENTONCES HAY SI DEBERIA DE CALCULAR EL SUBTOTAL Y NO SERIA EL PRECIO DIRECTAMENTE 
                    System.out.println("-------------__INTERES__------------------");
                    System.out.println("_   ____INTERES_DESC:  "+INTERES_DESC);
                    System.out.println("_   _____INTERES_IVA:  "+INTERES_IVA);
                    System.out.println("_   __INTERES_PRECIO:  "+INTERES_PRECIO);
                    System.out.println("_   _INTERES_CANTIDAD:  "+INTERES_CANTIDAD);
                    System.out.println("-------------------------------------------");

                    // VALIDACIONES 
                    String TIPO_MENSAJE="", MENSAJE=null;
                    // CONTROLO PRIMERAMENTE QUE NO SE HAYAN DEJADOS CAMPOS VACIOS PARA AÑADIR A LA LISTA 
                    if (INTERES_DESC == null || INTERES_DESC.isEmpty()) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar la descripción para añadirlo a la lista.";

                    } else if(INTERES_PRECIO == null || INTERES_PRECIO.isEmpty() || Double.parseDouble(INTERES_PRECIO) <= 0) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el monto del interés.";

                    } else {
                        // INGRESO A LA LISTA  
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(CF_LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt("1"));
                            datosNew.setOFD_IDTIPOCONCEPTO(3); // 1 : CUENTA / 2 : PRODUCTO / 3 : INTERES AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(INTERES_DESC);
                            datosNew.setOFD_IDIMPUESTO(INTERES_IVA);
                            datosNew.setOFD_CANTIDAD(Integer.parseInt(INTERES_CANTIDAD));
                            datosNew.setOFD_PRECIO(INTERES_PRECIO);
                            datosNew.setOFD_SUBTOTAL(INTERES_SUBTOTAL.replace(".", ""));
                        CF_LISTA_DETALLE.add(datosNew);
                        CF_LISTA_ITEM = CF_LISTA_ITEM + 1; // LE SUMO AL ITEM PARA QUE SEA INCREMENTABLE Y NO PASE SIEMPRE EL MISMO NUMERO 
                    } // END IF VALIDACION.-

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    request.setAttribute("CF_CI_TIPO_MENSAJE", TIPO_MENSAJE);
                    request.setAttribute("CF_CI_MENSAJE", MENSAJE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DE LOS PRODUCTOS.-
                    // SI EL MENSAJE ESTA CARGADO, ES PORQUE SE ACTIVO ALGUNA VALIDACION, Y ENTONCES LE VUELVO A PASAR LOS CAMPOS QUE SE CARGO Y NO LOS LIMPIO 
                    if (MENSAJE != null) {
                        request.setAttribute("CF_FCI_TXT_DESCRIPCION", INTERES_DESC);
                        request.setAttribute("CF_FCI_CBX_IVA", INTERES_IVA);
                        request.setAttribute("CF_FCI_TXT_PRECIO", INTERES_PRECIO);
                    } else { // EN CASO DE QUE MENSAJE SE MANTENGA NULL ENTONCES SE SUPONE QUE NO SE ACTIVO ALGUNA VALIDACION Y SE CARGO CORRECTAMENTE LA LISTA Y LE PASO VACIO LOS CAMPOS PARA LIMPIARLE 
                        request.setAttribute("CF_FCI_TXT_DESCRIPCION", "");
                        request.setAttribute("CF_FCI_CBX_IVA", "");
                        request.setAttribute("CF_FCI_TXT_PRECIO", "");
                    }

                } else if (accionFCI.equalsIgnoreCase("Eliminar")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCI__ELIMINAR_ITEM_DEL_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO A ELIMINAR 
                    String IDCONCEPTO = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ITEM CONCEPTO 
                    int CF_ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAI"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0 // tAI : TXT AUXILIAR ITEM 
                    System.out.println("-------------__PRODUCTO__------------------");
                    System.out.println("_   ____ID_CONCEPTO:  "+IDCONCEPTO);
                    System.out.println("_   __ITEM_ELIMINAR:  "+CF_ITEM_ELIMINAR);
                    System.out.println("-------------------------------------------");

                    // ELIMINO DE LA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        CF_LISTA_DETALLE.remove(CF_ITEM_ELIMINAR);
                        
                        // ORDENAR LOS ITEMS 
                        for (BeanFacturaCab LISTA_DETALLE : CF_LISTA_DETALLE) {
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanFacturaCab datosNew = new BeanFacturaCab();
                                datosNew.setOFD_ITEM(itemNew);
                                datosNew.setOFD_IDCONCEPTO(LISTA_DETALLE.getOFD_IDCONCEPTO());
                                datosNew.setOFD_IDTIPOCONCEPTO(LISTA_DETALLE.getOFD_IDTIPOCONCEPTO());
                                datosNew.setOFD_DESCRIPCION_AUX(LISTA_DETALLE.getOFD_DESCRIPCION_AUX());
                                datosNew.setOFD_IDIMPUESTO(LISTA_DETALLE.getOFD_IDIMPUESTO());
                                datosNew.setOFD_CANTIDAD(LISTA_DETALLE.getOFD_CANTIDAD());
                                datosNew.setOFD_PRECIO(LISTA_DETALLE.getOFD_PRECIO());
                                datosNew.setOFD_SUBTOTAL(LISTA_DETALLE.getOFD_SUBTOTAL());
                            CF_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".");System.out.println(".");
                        itemNew = CF_LISTA_ITEM; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FACTURA_____ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");
                    } // end catch 

                    var_redireccionar = 1;
                    acceso = "factura_add_int.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(itemNew)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DEL INTERES 
                    request.setAttribute("CF_FCI_TXT_DESCRIPCION", "");
                    request.setAttribute("CF_FCI_CBX_IVA", "");
                    request.setAttribute("CF_FCI_TXT_PRECIO", "");

                } else if (accionFCI.equalsIgnoreCase("Cargar Detalle")) { // BTN QUE ES PARA AMBAS PAGINAS (CUENTA CLIENTE Y PRODUCTO) PARA QUE EL USUARIO PUEDA CARGAR UN INTERES CON EL MONTO QUE EL QUIERA Y SE LE AGREGUE AL DETALLE 
                    System.out.println("---------------------__FCI__CARGAR_INTERES_AL_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    /*
                    OBSERVACION_DEL_PORQUE_REINICIO:
                    REINICIO LOS VALORES PORQUE AL DARLE AL BOTON DE "CARGAR INTERES" YA SE CALCULAN ESTOS CAMPOS Y POR ESO 
                    LOS CARGO CON VALORES DE CERO PARA QUE NO ME SUME VALORES ANTERIORES 
                    */
                    CF_TXT_TOTAL_IVA5 = "0";
                    CF_TXT_TOTAL_GRAV5 = "0";
                    CF_TXT_TOTAL_IVA10 = "0";
                    CF_TXT_TOTAL_GRAV10 = "0";
                    CF_TXT_TOTAL_IVA = "0";
                    CF_TXT_TOTAL = "0";
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("-   -   -   -   -   -   -   -   ");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA      :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT     :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDCONCEPTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String IDTIPOCONCEPTO = String.valueOf(datosLista.getOFD_IDTIPOCONCEPTO());
                        String INTERES_IVA = datosLista.getOFD_IDIMPUESTO();
                        String INTERES_DESC = datosLista.getOFD_DESCRIPCION_AUX();
                        String INTERES_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int INTERES_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(INTERES_PRECIO)*Double.parseDouble(String.valueOf(INTERES_CANTIDAD))).replace(".","");
                        System.out.println("---------DETALLE_LISTA_CARGANDO_AL_DETALLE_FACT-----------------");
                        System.out.println("-   -   ______LISTA_ITEM:       :"+LISTA_ITEM);
                        System.out.println("-   -   ______ID_CONCEPTO:       :"+IDCONCEPTO);
                        System.out.println("-   -   _ID_TIPO_CONCEPTO:       :"+IDTIPOCONCEPTO);
                        System.out.println("-   -   ______INTERES_IVA:       :"+INTERES_IVA);
                        System.out.println("-   -   _____INTERES_DESC:       :"+INTERES_DESC);
                        System.out.println("-   -   ___INTERES_PRECIO:       :"+INTERES_PRECIO);
                        System.out.println("-   -   _INTERES_CANTIDAD:       :"+INTERES_CANTIDAD);
                        System.out.println("----------------------------------------------------------------");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDCONCEPTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(Integer.parseInt(IDTIPOCONCEPTO)); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(INTERES_DESC);
                            datosNew.setOFD_CANTIDAD(INTERES_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(INTERES_IVA);
                            if (INTERES_IVA.equalsIgnoreCase("5") || INTERES_IVA.equals("5%")) {
                                double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                datosNew.setOFD_IVA5(formatear.format(iva5));
                                datosNew.setOFD_GRAV5(formatear.format(grav5));
                            } else if (INTERES_IVA.equalsIgnoreCase("10") || INTERES_IVA.equals("10%")) {
                                double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                datosNew.setOFD_IVA10(formatear.format(iva10));
                                datosNew.setOFD_GRAV10(formatear.format(grav10));
                            }
                            datosNew.setOFD_EXENTO("0");
                            datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(INTERES_PRECIO)));
                            datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                }
                
                
            } else if(accionFCD != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("---------------------__ACCION_FACTURA_CUENTA_DESCUENTO__--------------------------");
                System.out.println(".");System.out.println(".");
                if (accionFCD.equalsIgnoreCase("Cargar Descuento a la Lista")) {
                    System.out.println("---------------------__FCI__CARGAR_DESCUENTO_A_LA_LISTA__--------------------------");
                    System.out.println("---------------------__CARGAR_DESCUENTO_A_LA_LISTA__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO 
                    String DESCUENTO_DESC = (String) request.getParameter("tD");
                    String DESCUENTO_IVA = (String) request.getParameter("cI");
                    String DESCUENTO_PRECIO = (String) request.getParameter("tP");
                    if (DESCUENTO_PRECIO == null || DESCUENTO_PRECIO.isEmpty()) {
                        //
                    } else {
                        DESCUENTO_PRECIO = formatear.format(Double.parseDouble(DESCUENTO_PRECIO.replace(".","").replace(",","."))).replace(".","");
                    }
                    String DESCUENTO_CANTIDAD = "1"; // SI LA CANTIDAD SE CARGARA ENTONCES AHI SI SE TIENE QUE CALCULAR EL SUBTOTAL MULTIPLICANDOLO POR EL PRECIO COMO EN LA VENTANA DE CARGAR PRODUCTO, PERO COMO EL VALOR ES 1, ENTONCES NO HAY NECESIDAD 
                    String DESCUENTO_SUBTOTAL = DESCUENTO_PRECIO; // SI LA CANTIDAD TIENE LA POSIBILIDAD DE SER DIFERENTE A UNO, ENTONCES HAY SI DEBERIA DE CALCULAR EL SUBTOTAL Y NO SERIA EL PRECIO DIRECTAMENTE 
                    System.out.println("-------------__DESCUENTO_VARS__------------------");
                    System.out.println("_   ____DESCUENTO_DESC:  "+DESCUENTO_DESC);
                    System.out.println("_   _____DESCUENTO_IVA:  "+DESCUENTO_IVA);
                    System.out.println("_   __DESCUENTO_PRECIO:  "+DESCUENTO_PRECIO);
                    System.out.println("_   _DESCUENTO_CANTIDAD: "+DESCUENTO_CANTIDAD);
                    System.out.println("-------------------------------------------------");

                    // VALIDACIONES 
                    String TIPO_MENSAJE="", MENSAJE=null;
                    // CONTROLO PRIMERAMENTE QUE NO SE HAYAN DEJADOS CAMPOS VACIOS PARA AÑADIR A LA LISTA 
                    if (DESCUENTO_DESC == null || DESCUENTO_DESC.isEmpty()) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar la descripción para añadirlo a la lista.";

                    } else if(DESCUENTO_PRECIO == null || DESCUENTO_PRECIO.isEmpty() || Double.parseDouble(DESCUENTO_PRECIO) <= 0) {
                        TIPO_MENSAJE = "2";
                        MENSAJE = "Debe de cargar el monto del descuento.";

                    } else {
                        // INGRESO A LA LISTA  
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(CF_LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt("1"));
                            datosNew.setOFD_IDTIPOCONCEPTO(4); // 1 : CUENTA / 2 : PRODUCTO / 3 : INTERES AGREGADO / 4 : DESCUENTO AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(DESCUENTO_DESC);
                            datosNew.setOFD_IDIMPUESTO(DESCUENTO_IVA);
                            datosNew.setOFD_CANTIDAD(Integer.parseInt(DESCUENTO_CANTIDAD));
                            datosNew.setOFD_PRECIO(DESCUENTO_PRECIO);
                            datosNew.setOFD_SUBTOTAL(DESCUENTO_SUBTOTAL.replace(".", ""));
                        CF_LISTA_DETALLE.add(datosNew);
                        CF_LISTA_ITEM = CF_LISTA_ITEM + 1; // LE SUMO AL ITEM PARA QUE SEA INCREMENTABLE Y NO PASE SIEMPRE EL MISMO NUMERO 
                    } // END IF VALIDACION.-

                    var_redireccionar = 1;
                    acceso = "factura_add_desc.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
                    request.setAttribute("CF_CD_TIPO_MENSAJE", TIPO_MENSAJE);
                    request.setAttribute("CF_CD_MENSAJE", MENSAJE);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS.-
                    // SI EL MENSAJE ESTA CARGADO, ES PORQUE SE ACTIVO ALGUNA VALIDACION, Y ENTONCES LE VUELVO A PASAR LOS CAMPOS QUE SE CARGO Y NO LOS LIMPIO 
                    if (MENSAJE != null) {
                        request.setAttribute("CF_FCD_TXT_DESCRIPCION", DESCUENTO_DESC);
                        request.setAttribute("CF_FCD_CBX_IVA", DESCUENTO_IVA);
                        request.setAttribute("CF_FCD_TXT_PRECIO", DESCUENTO_PRECIO);
                    } else { // EN CASO DE QUE MENSAJE SE MANTENGA NULL ENTONCES SE SUPONE QUE NO SE ACTIVO ALGUNA VALIDACION Y SE CARGO CORRECTAMENTE LA LISTA Y LE PASO VACIO LOS CAMPOS PARA LIMPIARLE 
                        request.setAttribute("CF_FCD_TXT_DESCRIPCION", "");
                        request.setAttribute("CF_FCD_CBX_IVA", "");
                        request.setAttribute("CF_FCD_TXT_PRECIO", "");
                    }

                } else if (accionFCD.equalsIgnoreCase("Eliminar")) {
                    System.out.println("---------------------__FCD__ELIMINAR_ITEM_DEL_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    // RECIBO TAMBIEN LOS OTROS PARAMETROS PORQUE AL ENTRAR EN EL CONTROLADOR Y LE REDIRECCIONO A LA MISMA PAGINA ESTA VA A VOLVER A RECARGAR Y SI NO LE PASO LOS PARAMETROS DE LOS CAMPOS DE TEXTO ME VA A LIMPIAR ESOS CAMPOS CADA VEZ QUE AÑADA O ELIMINE UN PRODUCTO 
                    String TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    String TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5");
                    String CF_TXT_TOTAL_IVA10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10");
                    String CF_TXT_TOTAL_GRAV5 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5");
                    String CF_TXT_TOTAL_GRAV10 = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10");
                    String CF_TXT_TOTAL_IVA = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA");
                    String CF_TXT_TOTAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_TOTAL");
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_CINRO   :"+TXT_CLIENTE_CINRO);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("----------------------------------------------------------");

                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION PARA MANTENERLA ACTIVA, PERO NO LA VOY A UTILIZAR AL IGUAL QUE AL ITEM 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // CREO LA LISTA DEL DETALLE DE LA FACTURA Y LA INSTANCIO VACIO PARA PASARLA A LA SESION 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // RECUPERO LOS DATOS DEL PRODUCTO A ELIMINAR 
                    String IDCONCEPTO = (String) request.getParameter("tAIC"); // tAIC : TXT AUXILIAR ITEM CONCEPTO 
                    int CF_ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAI"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0 // tAI : TXT AUXILIAR ITEM 
                    System.out.println("-------------__DESCUENTO__------------------");
                    System.out.println("_   ____ID_CONCEPTO:  "+IDCONCEPTO);
                    System.out.println("_   __ITEM_ELIMINAR:  "+CF_ITEM_ELIMINAR);
                    System.out.println("--------------------------------------------");

                    // ELIMINO DE LA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        CF_LISTA_DETALLE.remove(CF_ITEM_ELIMINAR);

                        // ORDENAR LOS ITEMS 
                        for (BeanFacturaCab LISTA_DETALLE : CF_LISTA_DETALLE) {
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanFacturaCab datosNew = new BeanFacturaCab();
                                datosNew.setOFD_ITEM(itemNew);
                                datosNew.setOFD_IDCONCEPTO(LISTA_DETALLE.getOFD_IDCONCEPTO());
                                datosNew.setOFD_IDTIPOCONCEPTO(LISTA_DETALLE.getOFD_IDTIPOCONCEPTO());
                                datosNew.setOFD_DESCRIPCION_AUX(LISTA_DETALLE.getOFD_DESCRIPCION_AUX());
                                datosNew.setOFD_IDIMPUESTO(LISTA_DETALLE.getOFD_IDIMPUESTO());
                                datosNew.setOFD_CANTIDAD(LISTA_DETALLE.getOFD_CANTIDAD());
                                datosNew.setOFD_PRECIO(LISTA_DETALLE.getOFD_PRECIO());
                                datosNew.setOFD_SUBTOTAL(LISTA_DETALLE.getOFD_SUBTOTAL());
                            CF_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".");System.out.println(".");
                        itemNew = CF_LISTA_ITEM; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FACTURA_____ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");
                    } // end catch 

                    var_redireccionar = 1;
                    acceso = "factura_add_desc.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(itemNew)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", CF_TXT_TOTAL_IVA5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", CF_TXT_TOTAL_IVA10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", CF_TXT_TOTAL_GRAV5);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", CF_TXT_TOTAL_GRAV10);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", CF_TXT_TOTAL_IVA);
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", CF_TXT_TOTAL);
                    // VARIABLES QUE USO PARA CARGAR LOS DATOS DEL INTERES 
                    request.setAttribute("CF_FCD_TXT_DESCRIPCION", "");
                    request.setAttribute("CF_FCD_CBX_IVA", "");
                    request.setAttribute("CF_FCD_TXT_PRECIO", "");

                } else if (accionFCD.equalsIgnoreCase("Cargar Detalle")) {
                    System.out.println("---------------------__FCD__CARGAR_DESCUENTO_AL_DETALLE__--------------------------");
                    // RECIBIR LOS PARAMETROS Y CARGAR A LA SESION 
                    String TXT_NRO_FACTURA = (String) request.getParameter("tANF");
                    if (TXT_NRO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_NRO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_NRO_FACTURA");
                    }
                    String CBX_TIPO_FACTURA = (String) request.getParameter("cATF");
                    if (CBX_TIPO_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        CBX_TIPO_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_CBX_TIPO_FACTURA");
                    }
                    String TXT_FECHA_FACTURA = (String) request.getParameter("tAFF");
                    if (TXT_FECHA_FACTURA == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_FECHA_FACTURA = (String) sesionDatosUsuario.getAttribute("CF_TXT_FECHA_FACTURA");
                    }
                    String TXT_CLIENTE_ID = (String) request.getParameter("tAIC");
                    if (TXT_CLIENTE_ID == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_ID = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                    }
                    String TXT_CLIENTE_CINRO = (String) request.getParameter("tACNC");
                    if (TXT_CLIENTE_CINRO == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_CINRO = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_CINRO");
                    }
                    String TXT_CLIENTE_NAME = (String) request.getParameter("tACN");
                    if (TXT_CLIENTE_NAME == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_NAME = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_NAME");
                    }
                    String TXT_CLIENTE_RAZONSOCIAL = (String) request.getParameter("tACRS");
                    if (TXT_CLIENTE_RAZONSOCIAL == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RAZONSOCIAL = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    }
                    String TXT_CLIENTE_RUC = (String) request.getParameter("tACR");
                    if (TXT_CLIENTE_RUC == null) { // EN CASO DE QUE EL CAMPO DE TEXTO ME DEVUELVA NULL ENTONCES VOY A INTENTAR OBTENER EL DATO DE LA SESION 
                        TXT_CLIENTE_RUC = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_RUC");
                    }
                    // TOTALES DEL DETALLE 
                    String CF_TXT_TOTAL_IVA5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA5")).replace(".","");
                    String CF_TXT_TOTAL_GRAV5 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV5")).replace(".","");
                    String CF_TXT_TOTAL_IVA10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA10")).replace(".","");
                    String CF_TXT_TOTAL_GRAV10 = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_GRAV10")).replace(".","");
                    String CF_TXT_TOTAL_IVA = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL_IVA")).replace(".","");
                    String CF_TXT_TOTAL = String.valueOf(sesionDatosUsuario.getAttribute("CF_TXT_TOTAL")).replace(".","");
                    /*
                    OBSERVACION_DEL_PORQUE_REINICIO:
                    REINICIO LOS VALORES PORQUE AL DARLE AL BOTON DE "CARGAR INTERES" YA SE CALCULAN ESTOS CAMPOS Y POR ESO 
                    LOS CARGO CON VALORES DE CERO PARA QUE NO ME SUME VALORES ANTERIORES 
                    */
                    CF_TXT_TOTAL_IVA5 = "0";
                    CF_TXT_TOTAL_GRAV5 = "0";
                    CF_TXT_TOTAL_IVA10 = "0";
                    CF_TXT_TOTAL_GRAV10 = "0";
                    CF_TXT_TOTAL_IVA = "0";
                    CF_TXT_TOTAL = "0";
                    System.out.println("------------------__PARAMETER_CABECERA__------------------");
                    System.out.println("NRO_FACTURA     :"+TXT_NRO_FACTURA);
                    System.out.println("TIPO_FACTURA    :"+CBX_TIPO_FACTURA);
                    System.out.println("FECHA_FACTURA   :"+TXT_FECHA_FACTURA);
                    System.out.println("CLIENTE_ID      :"+TXT_CLIENTE_ID);
                    System.out.println("CLIENTE_NAME    :"+TXT_CLIENTE_NAME);
                    System.out.println("RAZON_SOCIAL    :"+TXT_CLIENTE_RAZONSOCIAL);
                    System.out.println("CLIENTE_RUC     :"+TXT_CLIENTE_RUC);
                    System.out.println("-   -   -   -   -   -   -   -   ");
                    System.out.println("TOTAL_IVA_5     :"+CF_TXT_TOTAL_IVA5);
                    System.out.println("TOTAL_GRAV_5    :"+CF_TXT_TOTAL_GRAV5);
                    System.out.println("TOTAL_IVA_10    :"+CF_TXT_TOTAL_IVA10);
                    System.out.println("TOTAL_GRAV_10   :"+CF_TXT_TOTAL_GRAV10);
                    System.out.println("TOTAL_IVA      :"+CF_TXT_TOTAL_IVA);
                    System.out.println("TOTAL_FACT     :"+CF_TXT_TOTAL);
                    System.out.println("----------------------------------------------------------");
                    // RECUPERO EL ITEM DE LA LISTA DE LA SESION 
                    int CF_LISTA_ITEM = 1;
                    if (Integer.parseInt((String)sesionDatosUsuario.getAttribute("CF_LISTA_ITEM")) != 1) {
                        CF_LISTA_ITEM = Integer.parseInt((String) sesionDatosUsuario.getAttribute("CF_LISTA_ITEM"));
                    }
                    // RECUPERO LA LISTA DE LA SESION, ESTA LA ESTOY UTILIZANDO PARA CARGAR LOS PRODUCTOS 
                    List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<BeanFacturaCab>();
                    if ((sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE")) != null) {
                        System.out.println("_____IF_______LISTA_DETALLE_CARGADO_______");
                        CF_LISTA_DETALLE = (List<BeanFacturaCab>) sesionDatosUsuario.getAttribute("CF_LISTA_DETALLE");
                    }

                    // CREO UNA LISTA NUEVA PARA PASAR LOS DATOS DE LA ANTERIOR LISTA A ESTA Y HACER LOS CALCULOS Y PASARLE A LA SESION COMO NUEVA LISTA 
                    List<BeanFacturaCab> CF_LISTA_NEW = new ArrayList<BeanFacturaCab>();
                    // RECORRO LA LISTA PARA PASARLE LOS DATOS A LA NUEVA LISTA LOS DATOS DEL DETALLE 
                    for (BeanFacturaCab datosLista : CF_LISTA_DETALLE) {
                        // recupero los datos de la lista para cargar al bean 
                        int LISTA_ITEM = datosLista.getOFD_ITEM();
                        String IDCONCEPTO = String.valueOf(datosLista.getOFD_IDCONCEPTO());
                        String IDTIPOCONCEPTO = String.valueOf(datosLista.getOFD_IDTIPOCONCEPTO());
                        String DESCUENTO_IVA = datosLista.getOFD_IDIMPUESTO();
                        String DESCUENTO_DESC = datosLista.getOFD_DESCRIPCION_AUX();
                        String DESCUENTO_PRECIO = formatear.format(Double.parseDouble(datosLista.getOFD_PRECIO())).replace(".","");
                        int DESCUENTO_CANTIDAD = datosLista.getOFD_CANTIDAD();
                        String PRECIO_FACT = formatear.format(Double.parseDouble(DESCUENTO_PRECIO)*Double.parseDouble(String.valueOf(DESCUENTO_CANTIDAD))).replace(".","");
                        System.out.println("---------DETALLE_LISTA_CARGANDO_AL_DETALLE_FACT-----------------");
                        System.out.println("-   -   ______LISTA_ITEM:       :"+LISTA_ITEM);
                        System.out.println("-   -   ______ID_CONCEPTO:       :"+IDCONCEPTO);
                        System.out.println("-   -   _ID_TIPO_CONCEPTO:       :"+IDTIPOCONCEPTO);
                        System.out.println("-   -   ____DESCUENTO_IVA:       :"+DESCUENTO_IVA);
                        System.out.println("-   -   ___DESCUENTO_DESC:       :"+DESCUENTO_DESC);
                        System.out.println("-   -   ___DESCUENTO_PRECIO:     :"+DESCUENTO_PRECIO);
                        System.out.println("-   -   _DESCUENTO_CANTIDAD:     :"+DESCUENTO_CANTIDAD);
                        System.out.println("----------------------------------------------------------------");

                        // cargo los datos al bean y luego a la nueva lista 
                        BeanFacturaCab datosNew = new BeanFacturaCab();
                            datosNew.setOFD_ITEM(LISTA_ITEM);
                            datosNew.setOFD_IDCONCEPTO(Integer.parseInt(IDCONCEPTO));
                            datosNew.setOFD_IDTIPOCONCEPTO(Integer.parseInt(IDTIPOCONCEPTO)); // 2 : PRODUCTO / 1 : CUENTA CLIENTE / 3 : INTERES AGREGADO / 4 : DESCUENTO AGREGADO 
                            datosNew.setOFD_DESCRIPCION_AUX(DESCUENTO_DESC);
                            datosNew.setOFD_CANTIDAD(DESCUENTO_CANTIDAD);
                            datosNew.setOFD_IDIMPUESTO(DESCUENTO_IVA);
                            if (IDTIPOCONCEPTO.equals("4")) { // SI EL IDTIPOCONCEPTO ES IGUAL A 4, ENTONCES RESTARIA LOS VALORES A LOS TOTALES YA QUE LA LINEA ES UN DESCUENTO 
                                System.out.println("_*____ID_TIPO_CONCEPTO____DESCUENTO_______");
                                if (DESCUENTO_IVA.equalsIgnoreCase("5") || DESCUENTO_IVA.equals("5%")) {
                                    double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                    double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                    CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) - iva5);
                                    CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) - grav5);
                                    datosNew.setOFD_IVA5(formatear.format(iva5));
                                    datosNew.setOFD_GRAV5(formatear.format(grav5));
                                } else if (DESCUENTO_IVA.equalsIgnoreCase("10") || DESCUENTO_IVA.equals("10%")) {
                                    double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                    double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                    CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) - iva10);
                                    CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) - grav10);
                                    datosNew.setOFD_IVA10(formatear.format(iva10));
                                    datosNew.setOFD_GRAV10(formatear.format(grav10));
                                }
                                datosNew.setOFD_EXENTO("0");
                                datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(DESCUENTO_PRECIO)));
                                datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                            } else { // SI NO ES DESCUENTO ENTONCES CARGO NORMAL 
                                System.out.println("_*______OTRO_ID_TIPO_CONCEPTO________");
                                if (DESCUENTO_IVA.equalsIgnoreCase("5") || DESCUENTO_IVA.equals("5%")) {
                                    double iva5 = Double.parseDouble(PRECIO_FACT)/21;
                                    double grav5 = Double.parseDouble(PRECIO_FACT) - iva5;
                                    CF_TXT_TOTAL_IVA5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA5) + iva5);
                                    CF_TXT_TOTAL_GRAV5 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV5) + grav5);
                                    datosNew.setOFD_IVA5(formatear.format(iva5));
                                    datosNew.setOFD_GRAV5(formatear.format(grav5));
                                } else if (DESCUENTO_IVA.equalsIgnoreCase("10") || DESCUENTO_IVA.equals("10%")) {
                                    double iva10 = Double.parseDouble(PRECIO_FACT)/11;
                                    double grav10 = Double.parseDouble(PRECIO_FACT) - iva10;
                                    CF_TXT_TOTAL_IVA10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + iva10);
                                    CF_TXT_TOTAL_GRAV10 = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_GRAV10) + grav10);
                                    datosNew.setOFD_IVA10(formatear.format(iva10));
                                    datosNew.setOFD_GRAV10(formatear.format(grav10));
                                }
                                datosNew.setOFD_EXENTO("0");
                                datosNew.setOFD_PRECIO(formatear.format(Double.parseDouble(DESCUENTO_PRECIO)));
                                datosNew.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(PRECIO_FACT)));
                            }
                        CF_LISTA_NEW.add(datosNew);
    //                    CF_LISTA_ITEM++; // LE SUMO UNO AL ITEM PARA QUE NO REPITA EL NUMERO YA QUE SE INGRESO A LA LISTA 
                        // LE SUMO A LAS VARIABLES TOTALES 
                        if (IDTIPOCONCEPTO.equals("4")) { // descuento 
                            CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) - Double.parseDouble(PRECIO_FACT));
                        } else {
                            CF_TXT_TOTAL = String.valueOf(Double.parseDouble(CF_TXT_TOTAL) + Double.parseDouble(PRECIO_FACT));
                        }
                    } // end for 

                    CF_TXT_TOTAL_IVA = String.valueOf(Double.parseDouble(CF_TXT_TOTAL_IVA10) + Double.parseDouble(CF_TXT_TOTAL_IVA5));

                    var_redireccionar = 1;
                    acceso = "crear_factura_det.htm";
                    sesionDatosUsuario.setAttribute("ID_FACTURA", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UNA FACTURA Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO UNA FACTURA, LA SESION VA A MANTENER ESE IDFACTURA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UNA NUEVA FACTURA 
                    sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(CF_LISTA_ITEM)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                    sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_NEW);
                    // CABECERA 
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RAZONSOCIAL);
                    sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
                    sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
                    sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
                    // AÑADO LOS TOTALES A LA SESION PARA ASI EN EL DETALLE PODER RECUPERARLOS 
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV5)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", formatear.format(Double.parseDouble(CF_TXT_TOTAL_GRAV10)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", formatear.format(Double.parseDouble(CF_TXT_TOTAL_IVA)));
                    sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", formatear.format(Double.parseDouble(CF_TXT_TOTAL)));
                }
            }
            
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
            System.out.println("____CONTROLADOR_____VALI_MOSTRAR_BTN_DETALLE =  "+CF_VALI_MOSTRAR_BTN_DETALLE);
            sesionDatosUsuario.setAttribute("CF_VALI_MOSTRAR_BTN_DETALLE", CF_VALI_MOSTRAR_BTN_DETALLE);
        } catch (Throwable e) {
            System.out.println("-----------------ERROR-FACTURA--------------------");
            var_redireccionar = 1;
            String MENSAJE = "Se ha generado un error, intentelo mas tarde.";
            String TIPO_MENSAJE = "2";
            request.setAttribute("CF_MENSAJE", MENSAJE);
            request.setAttribute("CF_TIPO_MENSAJE", TIPO_MENSAJE);
            acceso = "factura.htm";
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerFactura.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("---------------------------------------------------");
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
    * PARA LEER MAS SOBRE EL METODO IR AL IF DE ESTE CONTROLADOR (ControllerFactura) DONDE SE LLAMA A ESTE METODO; 
    * OBSERVACION_02: NO AGREGO AL MODELO DE FACTURA ESTE METODO, PORQUE NO LO VEO NECESARIO, APARTE DE QUE ESTE CODIGO 
        ES EL QUE UTILIZO PARA LLAMAR A LOS METODOS DE SU MODELO Y ESTE METODO NO HACE NINGUNA INSTANCIA PARA LLAMAR A LA BASE (SI A LOS METODOS QUE SE ENCUENTRAN EN EL MODELO) 
    */
    public void ver_factura(HttpServletRequest request, 
            HttpSession sesionDatosUsuario, 
            ModelFactura metodosFactura, 
            ModelPersona metodosPersona, 
            DecimalFormat formatear, 
            String idPersona, 
            int var_redireccionar, 
            String acceso) {
        System.out.println("---------------------__VER_FACTURA__--------------------------");
        String idFactura = (String) request.getParameter("tIF"); // tIF : txt id factura 
        System.out.println("_doPost__ID_FACTURA:    "+idFactura);
        
        ModelRef_Clinica metodosRefCli = new ModelRef_Clinica();
        
        // OBTENGO LOS DATOS DE LA FACTURA POR MEDIO DEL ID Y CARGO LAS VARIABLES 
        List<BeanFacturaCab> listaFactura = metodosFactura.traer_datos(idFactura, sesionDatosUsuario);
        Iterator<BeanFacturaCab> iterVerFact = listaFactura.iterator();
        BeanFacturaCab datos = new BeanFacturaCab();
        
        // ESTABLECER LAS VARIABLES PARA CABECERA Y AL RECORRER CARGAR Y LUEGO PASAR A LA SESION 
        int nroItem = 0;
        String TXT_IDCLINICA_PERS="", TXT_CLINICA_NAME_PERS="", TXT_IDCLINICA_FACT="", TXT_CLINICA_NAME_FACT="", TXT_CLIENTE_ID="", TXT_CLIENTE_CINRO="", TXT_CLIENTE_NAME="", TXT_CLIENTE_RS="", TXT_CLIENTE_RUC="", TXT_NRO_FACTURA="", CBX_TIPO_FACTURA="", TXT_FECHA_FACTURA="", TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="", TXT_ESTADO_FACTURA="";
        // INSTANCIO UNA NUEVA LISTA PARA CARGAR LOS DATOS DEL DETALLE DE LA FACTURA 
        List<BeanFacturaCab> CF_LISTA_DETALLE = new ArrayList<>();
        
        while(iterVerFact.hasNext()) {
            datos = iterVerFact.next();
            
            // CARGO LAS VARIABLES 
            nroItem = nroItem + 1;
            
            // CARGO LA CABECERA 
            TXT_CLIENTE_ID = String.valueOf(datos.getOF_IDCLIENTE());
            TXT_NRO_FACTURA = datos.getOF_NROFACTURA();
            TXT_IDCLINICA_FACT = String.valueOf(datos.getOF_IDCLINICA());
            TXT_IDCLINICA_PERS = datos.getOF_IDCLINICA_AUX();
            TXT_ESTADO_FACTURA = metodosFactura.devolverTextEstado(datos.getOF_ESTADO());
            CBX_TIPO_FACTURA = datos.getOF_TIPOFACTURA();
            TXT_FECHA_FACTURA = datos.getOF_FECHAFACTURA();
            TXT_TOTAL_IVA5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA5()));
            TXT_TOTAL_IVA10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA10()));
            TXT_TOTAL_GRAV5 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV5()));
            TXT_TOTAL_GRAV10 = formatear.format(Double.parseDouble(datos.getOF_TOTAL_GRAV10()));
            TXT_TOTAL_IVA = formatear.format(Double.parseDouble(datos.getOF_TOTAL_IVA()));
            TXT_TOTAL = formatear.format(Double.parseDouble(datos.getOF_TOTAL_FACTURA()));
            
            // CARGO EL DETALLE CF_LISTA_DETALLE 
            BeanFacturaCab datoss = new BeanFacturaCab();
                datoss.setOFD_ITEM(datos.getOFD_ITEM());
                datoss.setOFD_IDCONCEPTO(datos.getOFD_IDCONCEPTO());
                datoss.setOFD_IDTIPOCONCEPTO(datos.getOFD_IDTIPOCONCEPTO());
                datoss.setOFD_DESCRIPCION_AUX(datos.getOFD_DESCRIPCION_AUX());
                datoss.setOFD_CANTIDAD(datos.getOFD_CANTIDAD());
                datoss.setOFD_PRECIO(formatear.format(Double.parseDouble(datos.getOFD_PRECIO())));
                datoss.setOFD_IDIMPUESTO(datos.getOFD_IDIMPUESTO());
                datoss.setOFD_EXENTO(formatear.format(Double.parseDouble(datos.getOFD_EXENTO())));
                datoss.setOFD_IVA5(formatear.format(Double.parseDouble(datos.getOFD_IVA5())));
                datoss.setOFD_IVA10(formatear.format(Double.parseDouble(datos.getOFD_IVA10())));
                datoss.setOFD_GRAV5(formatear.format(Double.parseDouble(datos.getOFD_GRAV5())));
                datoss.setOFD_GRAV10(formatear.format(Double.parseDouble(datos.getOFD_GRAV10())));
                datoss.setOFD_SUBTOTAL(formatear.format(Double.parseDouble(datos.getOFD_SUBTOTAL())));
            CF_LISTA_DETALLE.add(datoss);
        } // end while 
        
        // OBTENGO LOS DATOS DEL CLIENTE 
        BeanPersona datosCliente = new BeanPersona();
        datosCliente = metodosPersona.datosBasicosPersona(datosCliente, TXT_CLIENTE_ID);
        TXT_CLIENTE_CINRO = datosCliente.getRP_CINRO();
        TXT_CLIENTE_NAME = datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
        TXT_CLIENTE_RS = datosCliente.getRP_RAZON_SOCIAL();
        TXT_CLIENTE_RUC = datosCliente.getRP_RUC();
        
        // OBTENGO LA DESCRIPCION DE LA CLINICA DE LA FACTURA - 
        TXT_CLINICA_NAME_FACT = metodosRefCli.traerDescClinica(TXT_IDCLINICA_FACT);
        // OBTENGO LA DESCRIPCION DE LA CLINICA DE LA PERSONA - 
        TXT_CLINICA_NAME_PERS = metodosRefCli.traerDescClinica(TXT_IDCLINICA_PERS);
        
//        var_redireccionar = 1;
//        acceso = "ver_factura.htm";
        sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        sesionDatosUsuario.setAttribute("ID_FACTURA", idFactura);
        // VARIABLES QUE USO -
        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_ID", TXT_CLIENTE_ID);
        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_CINRO", TXT_CLIENTE_CINRO);
        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_NAME", TXT_CLIENTE_NAME);
        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RAZONSOCIAL", TXT_CLIENTE_RS);
        sesionDatosUsuario.setAttribute("CF_TXT_CLIENTE_RUC", TXT_CLIENTE_RUC);
        sesionDatosUsuario.setAttribute("CF_TXT_NRO_FACTURA", TXT_NRO_FACTURA);
        sesionDatosUsuario.setAttribute("CF_CBX_TIPO_FACTURA", CBX_TIPO_FACTURA);
        sesionDatosUsuario.setAttribute("CF_TXT_FECHA_FACTURA", TXT_FECHA_FACTURA);
        sesionDatosUsuario.setAttribute("CF_TXT_ESTADO_FACTURA", TXT_ESTADO_FACTURA);
        sesionDatosUsuario.setAttribute("CF_TXT_IDCLINICA_FACT", TXT_IDCLINICA_FACT);
        sesionDatosUsuario.setAttribute("CF_TXT_NAME_CLINICA_FACT", TXT_CLINICA_NAME_FACT);
        sesionDatosUsuario.setAttribute("CF_TXT_IDCLINICA_PERS", TXT_IDCLINICA_PERS);
        sesionDatosUsuario.setAttribute("CF_TXT_NAME_CLINICA_PERS", TXT_CLINICA_NAME_PERS);
        // DETALLE - 
//        sesionDatosUsuario.setAttribute("CF_LISTA_ITEM", String.valueOf(nroItem));
        sesionDatosUsuario.setAttribute("CF_LISTA_DETALLE", CF_LISTA_DETALLE);
        // TOTALES - 
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA5", TXT_TOTAL_IVA5);
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV5", TXT_TOTAL_GRAV5);
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA10", TXT_TOTAL_IVA10);
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_GRAV10", TXT_TOTAL_GRAV10);
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL_IVA", TXT_TOTAL_IVA);
        sesionDatosUsuario.setAttribute("CF_TXT_TOTAL", TXT_TOTAL);
    } // END METHOD 
    
    
    
    
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
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFactura metodosFactura) {
        System.out.println("------------------------__FILTRAR__------------------------------");
        // OBTENER VALORES DE LA CAJA DE FILTRO 
        String cbxMostrar = (String) request.getParameter("cM");
        String txtBuscarNroFact = (String) request.getParameter("tFNF"); // tFNF : txt filtrar nro factura 
        if (txtBuscarNroFact == null || txtBuscarNroFact.isEmpty()) {
            txtBuscarNroFact = "";
        }
        System.out.println("-----------__VAR_FILTRAR__-----------");
        System.out.println("_  _cbxMostrar:   "+cbxMostrar);
        System.out.println("_  _Buscar_Nro_Fact:   "+txtBuscarNroFact);
        System.out.println("-------------------------------------");
        
        // CARGAR LISTA 
        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
        listaFiltro = metodosFactura.filtrar(sesionDatosUsuario, cbxMostrar, txtBuscarNroFact);
        
//        var_redireccionar = 1;
//        acceso = "factura.htm";
        // PASAR DATOS AL JSP 
        request.setAttribute("CF_CBX_MOSTRAR", cbxMostrar);
        request.setAttribute("CF_TXT_FILTRAR_NRO_FACT", txtBuscarNroFact);
        request.setAttribute("CF_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("CF_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
} // END CLASS 