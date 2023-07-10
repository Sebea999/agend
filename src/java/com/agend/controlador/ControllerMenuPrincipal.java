/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerMenuPrincipal", urlPatterns="/MPSrv")
public class ControllerMenuPrincipal extends HttpServlet {
    
    
//    ControllerInicioSesion controladorIniSes = new ControllerInicioSesion();
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    
    
    
    @RequestMapping("/menu")
    public ModelAndView menu_principal(Model model, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView();
        
        System.out.println("[+]-----__1__---------CONTROLLER__MENU_PRINCIPAL----------------MODEL_AND_VIEW------------");
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(false); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        // RECUPERO EL IDPERSONA DEL USUARIO-LOGIN.-
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        if (SESION_IDPERSONA == null) {
            System.out.println("[-] el id-persona de la sesion tiene valor null.-");
//            SESION_IDPERSONA = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
        }
        System.out.println("[+]___ID_USUARIO_PERSONA:   :"+SESION_IDPERSONA);
        
        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "menu_principal", request);
        
        mav.setViewName(paginaMav);
        
        // [OBS] :PASO LOS PARAMETROS A LA SESION Y EN CASO DE QUE ESTE NULA LA SESION SE LOS PASO POR LAS COOKIES.-
//        if (sesionDatosUsuario == null) {
            sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else {
//            modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "0");
//            modeloIniSes.setCookie(response, "IDPERSONA", SESION_IDPERSONA);
//        }
        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        return mav;
    }
    
    
    // [OBS] METODO ANTERIOR QUE LO TENIA A MODO DE EJEMPLO UTILIZANDO SESSION Y COOKIES - SESSION DE PRIMERA INSTANCIA Y CONTROLANDO CADA VALOR PARA QUE SI DEVUELVE NULL BUSQUE EL MISMO VALOR PERO EN LA COOKIE - COMENTO ESTE METODO PORQUE EN LA VPS ME DABA ERROR LA SESION Y EN LOCAL ME DABA ERROR LA COOKIE (Y VICEVERSA, EN LA VPS ME ANDABA LA COOKIE Y EN LOCAL LA SESSION) 
//    @RequestMapping("/menu")
//    public ModelAndView menu_principal(Model model, /*@CookieValue("JCOOKIEIDU") String IDUSERLOGIN,*/ HttpServletRequest request, HttpServletResponse response) {
//        ModelAndView mav = new ModelAndView();
//        
//        System.out.println("--------------------CONTROLER-MENU_PRINCIPAL-----------------------(cookie)----------------");
//        System.out.println("[*] cantidad de cookies:   :"+request.getCookies().length);
////        String IDUSERLOGIN = modeloIniSes.getCookie(request, "JIDUSER").getValue();
////        System.out.println("-----ID_USER_LOGIN =' "+IDUSERLOGIN+" '=");
////        Cookie[] cokies = request.getCookies();
//        for (Cookie cooki : request.getCookies()) {
//            System.out.println("[+] cookies:  :"+cooki.getName()+" __value:  :"+cooki.getValue()+"  __domain:  :"+cooki.getDomain()+"  __path:  :"+cooki.getPath());
//        }
//        System.out.println("...........................................................................................");
//        
//        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
//        HttpSession sesionDatosUsuario = request.getSession(false); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
//        
//        System.out.println(".");
//        System.out.println("[.]___CONTROLER_MENU_VALIDATE_________");
//        if (request.getSession().getAttribute("IDUSUARIO_USERLOGIN") == null) {
//            System.out.println("[-]...........vali..session.........");
//            System.out.println("[-]___SESION_IS_NULL____");
//            System.out.println("[-].................................");
////            mav.setViewName("inicio_sesion");
////            return mav;
//        } else {
//            System.out.println("[+]______SESION_ACTIVA____MENU_PRINCIPAL___");
//            System.out.println("[+]___ID_USUARIO_LOGIN:   :"+String.valueOf(sesionDatosUsuario.getAttribute("IDUSUARIO_USERLOGIN")));
//            System.out.println("[+]___SESION_CREADA:      :"+sesionDatosUsuario.isNew());
//            System.out.println("[+]----------------------------------------");
//        }
//        System.out.println(".");
//        
//        System.out.println("[+]-----__1__---------CONTROLLER__MENU_PRINCIPAL----------------MODEL_AND_VIEW------------");
//        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        if (SESION_IDPERSONA == null) {
//            SESION_IDPERSONA = modeloIniSes.getCookie(request, "IDPERSONA").getValue();
//        }
//        System.out.println("[*]___ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
//        
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "menu_principal", request);
//        
//        mav.setViewName(paginaMav);
//        // [OBS] :PASO LOS PARAMETROS A LA SESION Y EN CASO DE QUE ESTE NULA LA SESION SE LOS PASO POR LAS COOKIES.-
//        if (sesionDatosUsuario == null) {
//            sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else {
//            modeloIniSes.setCookie(response, "JSP_MOSTRAR_BARRA_MENU", "0");
//            modeloIniSes.setCookie(response, "IDPERSONA", SESION_IDPERSONA);
//        }
////        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        return mav;
//    }
    
    
    
} // END CLASS 