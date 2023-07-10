/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanPlanHorario;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelPlanHorario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerPlanHorario", urlPatterns="/CPHSrv") // controller plan horario servlet 
public class ControllerPlanHorario extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/plan_hor")
    public ModelAndView plan_horario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__PLAN_HORARIO--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
//        if ((modeloPerfil.isPerfilAdmin(IDPERFIL_USER))==true || (modeloPerfil.isPerfilFuncio(IDPERFIL_USER))==true || (modeloPerfil.isPerfilSecre(IDPERFIL_USER))==true) {
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPlanHorario", request);
//        } else if ((modeloPerfil.isPerfilMedico(IDPERFIL_USER))==true) {
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPlanHorario", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPlanHorario", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        request.setAttribute("CPH_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE MEDICO Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CPH_CHECK_FILTRAR_CLI", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLINICA Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        
        return mav;
    }
    
    
    
    @RequestMapping("/add_ph")
    public ModelAndView add_plan_horario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__2__MAV___ID_PLAN_HORARIO:     "+ID_PLAN_HORARIO);
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_PLAN_HORARIO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAdd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/add_phd")
    public ModelAndView add_plan_horario_det(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__3__MAV___ID_PLAN_HORARIO:     "+ID_PLAN_HORARIO);
        
        System.out.println("-----__3__--------CONTROLLER__AÑADIR_PLAN_HORARIO_DET------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAddDet", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAddDet", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/edit_ph")
    public ModelAndView edit_plan_horario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__4__MAV___ID_PLAN_HORARIO:     "+ID_PLAN_HORARIO);
        
        System.out.println("-----__4__--------CONTROLLER__EDITAR_PLAN_HORARIO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAdd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/edit_phd")
    public ModelAndView edit_plan_horario_det(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__5__MAV___ID_PLAN_HORARIO:     "+ID_PLAN_HORARIO);
        
        System.out.println("-----__5__--------CONTROLLER__EDITAR_PLAN_HORARIO_DET------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAddDet", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioAddDet", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/ph_ver")
    public ModelAndView plan_horario_ver(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDAGENDAMIENTO QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__6__MAV___ID_PLAN_HORARIO:     "+ID_PLAN_HORARIO);
        
        System.out.println("-----__6__--------CONTROLLER__PLAN_HORARIO-------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CPH__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPlanHorario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioVer", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPlanHorarioVer", request);
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
        
        ModelPlanHorario metodos = new ModelPlanHorario();
        ModelPersona metodosPersona = new ModelPersona();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int var_redireccionar = 0;
        String acceso = "plan_hor.htm";
        String accion = (String) request.getParameter("accion");
        String idPersona, idPlanHorario;
        
        try {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__ACCION:       "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if(accion.equalsIgnoreCase("Add Nuevo Plan")) {
                System.out.println("---------------------__AÑADIR_NUEVO_PLAN__--------------------------");
                var_redireccionar = 1;
                acceso = "add_ph.htm";
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                sesionDatosUsuario.setAttribute("ID_PLAN_HORARIO", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN REGISTRO  
                // LIMPIO LAS VARIABLES QUE MANTENGO EN LA SESION PARA QUE NO SE RECARGEN ESOS DATOS ANTERIORMENTE UTILIZADOS 
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", "1"); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN REGISTRO  
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle); // LE PASO EL VALOR VACIO, PARA QUE NO SE MANTENGA LOS DATOS DEL ANTERIOR DETALLE 
                sesionDatosUsuario.setAttribute("CPH_IDCLINICA", null);
                sesionDatosUsuario.setAttribute("CPH_ESTADO", null);
                /*
                 * OBSERVACION: 
                    AL AÑADIR UN NUEVO PLAN HORARIO CONTROLO QUE PERFIL ES EL QUE QUIERE AÑADIR UN NUEVO REGISTRO 
                    SI FUESE EL ADMINISTRADOR, FUNCIONARIO O SECRETARIO, ENTONCES LE DEJARIA CON LOS DATOS NULL DEL MEDICO PARA QUE EL PUEDA ELEGIR A QUIEN CARGARLE SU PLAN HORARIO O EDITARLOS 
                    PERO SI EL PERFIL FUESE MEDICO, ENTONCES NO LE DARIA LA OPCION DE ELEGIR AL MEDICO, YA QUE EL MEDICO NO PUEDE CARGAR O EDITAR EL PLAN HORARIO DE OTRO MEDICO, SOLO EL SUYO, POR ESO SI FUERA MEDICO ENTONCES LE PASO YA SUS DATOS PARA QUE SE CARGUEN EN LA CABECERA Y DESDE AHI NO LE MOSTRARIA EL BOTON PARA CARGAR OTRO MEDICO 
                */
                String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                System.out.println(".   _CPH__ID_PERFIL_USER:    :"+IDPERFIL_USER);
                if (modeloPerfil.isPerfilMedico(IDPERFIL_USER)==true) {
                    List<BeanPersona> listaDatosMedico;
                    listaDatosMedico = metodosPersona.traerDatosPersona(idPersona);
                    Iterator<BeanPersona> iterMedico = listaDatosMedico.iterator();
                    BeanPersona datosMedNew = null;
                    String MEDICO_NOM="", MEDICO_APE="", MEDICO_CINRO="";
                    while(iterMedico.hasNext()) {
                        datosMedNew = iterMedico.next();

                        MEDICO_NOM = datosMedNew.getRP_NOMBRE();
                        MEDICO_APE = datosMedNew.getRP_APELLIDO();
                        MEDICO_CINRO = datosMedNew.getRP_CINRO();
                    }
                    sesionDatosUsuario.setAttribute("CPH_IDMEDICO", idPersona);
                    sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", MEDICO_NOM);
                    sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", MEDICO_APE);
                    sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", MEDICO_CINRO);
                } else {
                    sesionDatosUsuario.setAttribute("CPH_IDMEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", null);
                    sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", null);
                }
                
            } else if(accion.equalsIgnoreCase("Editar")) {
                System.out.println("---------------------__EDITAR__--------------------------");
                idPlanHorario = (String) request.getParameter("tI");
                System.out.println("_doPost__ID_PLAN_HORARIO:     "+idPlanHorario);
                // LIMPIO ANTES DE INGRESAR AL METODO PORQUE EN EL METODO CARGO ESTOS DATOS 
                sesionDatosUsuario.setAttribute("CPH_IDCLINICA", null);
                sesionDatosUsuario.setAttribute("CPH_IDMEDICO", null);
                sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", null);
                sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", null);
                sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", null);
                sesionDatosUsuario.setAttribute("CPH_ESTADO", null);
                
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                listaDetalle = metodos.traer_datos(idPlanHorario, sesionDatosUsuario);
                
                var_redireccionar = 1;
                acceso = "edit_ph.htm";
                sesionDatosUsuario.setAttribute("ID_PLAN_HORARIO", idPlanHorario);
                // LIMPIO LAS VARIABLES QUE MANTENGO EN LA SESION PARA QUE NO SE RECARGEN ESOS DATOS ANTERIORMENTE UTILIZADOS 
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(listaDetalle.size()+1)); // LE CARGO EL VALOR DE LA GRILLA PARA SABER EL ULTIMO ITEM 
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle); // LE PASO EL VALOR VACIO, PARA QUE NO SE MANTENGA LOS DATOS DEL ANTERIOR DETALLE 
                
            } else if(accion.equalsIgnoreCase("Ver")) { // VER EL PLAN HORARIO CREADO 
                System.out.println("---------------------__VER_PLAN_HORARIO__--------------------------");
                idPlanHorario = (String) request.getParameter("tI");
                System.out.println("_doPost__ID_PLAN_HORARIO:     "+idPlanHorario);
                
//                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
//                listaDetalle = metodos.traer_datos(idPlanHorario, sesionDatosUsuario);
                
                var_redireccionar = 1;
                acceso = "ph_ver.htm";
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
                
            } else if(accion.equalsIgnoreCase("GRABAR") || accion.equalsIgnoreCase("Guardar")) {
                System.out.println("---------------------__GUARDAR__--------------------------");
                int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                idPlanHorario = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO");
                System.out.println("__ID_PLAN_HORARIO:   "+idPlanHorario);
                
                // RECUPERAR LOS DATOS ----------------------------------------- 
                String IDCLINICA = (String) sesionDatosUsuario.getAttribute("CPH_IDCLINICA");
                if (IDCLINICA == null || IDCLINICA.isEmpty()) {
                    IDCLINICA = (String) request.getParameter("cCli");
//                    IDCLINICA = (String) request.getParameter("tAIC");
                    // VUELVO A CONTROLAR POR SI AUN ASI ANDE VACIO O NULO 
                    if (IDCLINICA == null || IDCLINICA.isEmpty()) {
                        varValiVacio++;
                        IDCLINICA="";
                    }
                }
                String IDMEDICO = (String) sesionDatosUsuario.getAttribute("CPH_IDMEDICO");
                if (IDMEDICO == null || IDMEDICO.isEmpty()) {
                    IDMEDICO = (String) request.getParameter("tAIM");
                    // VUELVO A CONTROLAR POR SI AUN ASI ANDE VACIO O NULO 
                    if (IDMEDICO == null || IDMEDICO.isEmpty()) {
                        varValiVacio++;
                        IDMEDICO="";
                    }
                }
                String PH_MED_NOM = (String) sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO");
                String PH_MED_APE = (String) sesionDatosUsuario.getAttribute("CPH_APE_MEDICO");
                String PH_MED_CINRO = (String) sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO");
                String ESTADO = (String) sesionDatosUsuario.getAttribute("CPH_ESTADO");
                if (ESTADO == null || ESTADO.isEmpty()) {
                    ESTADO = (String) request.getParameter("tAPHE");
                    // VUELVO A CONTROLAR POR SI AUN ASI ANDE VACIO O NULO 
                    if (ESTADO == null || ESTADO.isEmpty()) {
                        varValiVacio++;
                        ESTADO="";
                    }
                }
                // LISTA Y ITEM DEL DETALLE DEL PLAN HORARIO 
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                if ((sesionDatosUsuario.getAttribute("CPH_LISTA_DET") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                    listaDetalle = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                }
                int item_lista = 1;
                if ((sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET") == null) == false) {
                    item_lista = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET"));
                }
                
                // IMPRESION DE DATOS 
                System.out.println("- -  ------------__DATOS__--------------");
                System.out.println("-     _ID_CLINICA:    :"+IDCLINICA);
                System.out.println("-     __ID_MEDICO:    :"+IDMEDICO);
                System.out.println("-     _NOM_MEDICO:    :"+PH_MED_NOM);
                System.out.println("-     _APE_MEDICO:    :"+PH_MED_APE);
                System.out.println("-     _CINRO_MEDI:    :"+PH_MED_CINRO);
                System.out.println("-     _____ESTADO:    :"+ESTADO);
                System.out.println("- -  -----------------------------------");
                System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                
                // VALIDACIONES 
                String MENSAJE, TIPO_MENSAJE; // 1 : exito / 2 : error 
                if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                    MENSAJE = "No puede dejar campos vacios.";
                    TIPO_MENSAJE = "2";
                    acceso = "add_ph.htm";
                    
                } else if (listaDetalle == null || listaDetalle.size() <= 0 || listaDetalle.isEmpty()) {
                    MENSAJE = "No puede dejar vacio el detalle.";
                    TIPO_MENSAJE = "2";
                    acceso = "add_phd.htm";
                    
                } else if (metodos.ctrlPlanHorMedCli(sesionDatosUsuario, IDCLINICA, IDMEDICO, idPlanHorario) == true && (ESTADO.equals("A") || ESTADO.equalsIgnoreCase("Activo"))) {
                    MENSAJE = "No puede tener dos planes horarios activos en una misma clínica.";
                    TIPO_MENSAJE = "2";
                    acceso = "add_ph.htm";
                    
                } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                    String OPH_IDPLANHORARIO = idPlanHorario;
                    String OPH_IDCLINICA = IDCLINICA;
                    String OPH_IDMEDICO = IDMEDICO;
                    String OPH_FECHAALTA = modeloIniSes.traerFechaHoraHoy();
                    String OPH_USU_ALTA = idPersona;
                    String OPH_ESTADO = ESTADO;
                    
                    // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL PACIENTE 
                    TIPO_MENSAJE = metodos.guardar_ph_cab(listaDetalle, new BeanPlanHorario(OPH_IDPLANHORARIO, OPH_IDCLINICA, OPH_IDMEDICO, OPH_USU_ALTA, OPH_FECHAALTA, OPH_ESTADO));
                    if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                        MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                        if (idPlanHorario == null || idPlanHorario.isEmpty() || idPlanHorario.equals("")) {
                            acceso = "add_phd.htm";
                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
                            acceso = "edit_ph.htm";
                        }
                    } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                        MENSAJE = "Se ha realizado correctamente la operación.";
                        acceso = "plan_hor.htm";
                    }
                }
                // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                    // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO REGISTRO, ENTONCES CONTROLANDO EL ID SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                    if (idPlanHorario == null || idPlanHorario.isEmpty() || idPlanHorario.equals("")) {
//                        acceso = "add_phd.htm"; cargo arriba nomas el url en la validacion en caso de que se este añadiendo un registro y si no le cargo la url de editar ph 
                    } else { // SI EL ID NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL REGISTRO 
                        acceso = "edit_ph.htm";
                    }
                    // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                    sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(item_lista));
                    sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle);
                    sesionDatosUsuario.setAttribute("CPH_IDCLINICA", IDCLINICA);
                    sesionDatosUsuario.setAttribute("CPH_IDMEDICO", IDMEDICO);
                    sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", PH_MED_NOM);
                    sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", PH_MED_APE);
                    sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", PH_MED_CINRO);
                    sesionDatosUsuario.setAttribute("CPH_ESTADO", ESTADO);
                }
                var_redireccionar = 1;
                // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                /*
                _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                    ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                    PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                */
                sesionDatosUsuario.setAttribute("ID_PLAN_HORARIO", idPlanHorario);
                request.setAttribute("CPH_MENSAJE", MENSAJE);
                request.setAttribute("CPH_TIPO_MENSAJE", TIPO_MENSAJE);
                
            } else if(accion.equalsIgnoreCase("Filtrar")) {
                System.out.println("---------------------__FILTRAR__--------------------------");
                String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
                String txtMedicoId = (String) request.getParameter("cbxAddNewMed"); // medico id 
                if (txtMedicoId == null || txtMedicoId.isEmpty()) {
                    txtMedicoId = "";
                }
                System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
                System.out.println("_   MEDICO_ID   :   "+txtMedicoId);
                String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
                String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // clinica id 
                if (txtClinicaId == null || txtClinicaId.isEmpty()) {
                    txtClinicaId = "";
                }
                System.out.println("_   CHECK_CLINICA:   "+checkClinicaFiltro);
                System.out.println("_   CLINICA_ID   :   "+txtClinicaId);
                int var_btn_editar = 0; // VARIABLE QUE UTILIZARE EN EL JSP PARA SABER SI SE ACTIVO EL FILTRO DE CLINICA Y ASI EVITAR ACTIVAR O MOSTRAR EL BOTON DE EDITAR EL PLAN HORARIO, PARA QUE NO PUEDA EDITAR LOS DATOS DE OTRAS CLINICAS 
                
                // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
                if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
                    checkMedicoFiltro = "OFF";
                } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                    txtMedicoId = "";
                }
                
                // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR LA CLINICA, SI SE SELECCIONO, ENTONCES LIMPIARIA EL TXT DE CLINICA PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLINICA 
                if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA CLINICA Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLINICA 
                    checkClinicaFiltro = "OFF";
                    String idClinicaLogeo = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");// VARIABLE QUE GUARDO EN LA SESION AL MOMENTO DE LOGEARME 
                    // EN CASO DE QUE SE ESTE FILTRANDO POR LA MISMA CLINICA Y NO POR OTRA ENTONCES SI LE PERMITIRIA VER EL BTN DE EDITAR 
                    if (txtClinicaId.equals(idClinicaLogeo)) {
                        var_btn_editar = 0; // MANTENGO EN CERO Y LE HABILITARIA EL BTN DE EDITAR 
                    } else {
                        var_btn_editar = 1; // LE CARGO UNO PARA INABILITARLE EL BTN DE EDITAR 
                    }
                } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                    txtClinicaId = "";
                    var_btn_editar = 0; // MANTENGO EN CERO Y LE MOSTRARIA EL BTN YA QUE NO FILTRO POR CLINICA 
                }
                
                // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                List<BeanPlanHorario> listaFiltro = new ArrayList<>();
                listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtMedicoId, txtClinicaId);
                
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "plan_hor.htm";
                var_redireccionar = 1;
                request.setAttribute("CPH_CBX_MOSTRAR", filtro_cbxMostrar);
                request.setAttribute("CPH_TXT_BUSCAR", filtro_txtBuscar);
                request.setAttribute("CPH_LISTA_FILTRO", listaFiltro);
                request.setAttribute("CPH_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
                request.setAttribute("CPH_TXT_FIL_ID_MED", txtMedicoId);
                request.setAttribute("CPH_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
                request.setAttribute("CPH_TXT_FIL_ID_CLI", txtClinicaId);
                request.setAttribute("CPH_BTN_EDITAR_VALI", String.valueOf(var_btn_editar));
                sesionDatosUsuario.setAttribute("CPH_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                
            } else if (esNumero(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute("PAG_PLANHO_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodos);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase(">>")) {
                System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accion+" :__--------------------------");
                String PAG_PLANHO_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_PLANHO_CANT_NRO_CLIC == null) {
                    PAG_PLANHO_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_PLANHO_CANT_NRO_CLIC);
                String PAG_PLANHO_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_PLANHO_LISTA_ACTUAL);
                
                PAG_PLANHO_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_PLANHO_CANT_NRO_CLIC)+1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_PLANHO_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_PLANHO_LISTA_ACTUAL", PAG_PLANHO_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_PLANHO_CANT_NRO_CLIC)+1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodos);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase("<<")) {
                System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accion+" :__--------------------------");
                String PAG_PLANHO_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_PLANHO_CANT_NRO_CLIC == null) {
                    PAG_PLANHO_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_PLANHO_CANT_NRO_CLIC);
                String PAG_PLANHO_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_PLANHO_LISTA_ACTUAL);
                
                PAG_PLANHO_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_PLANHO_CANT_NRO_CLIC)-1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_PLANHO_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_PLANHO_LISTA_ACTUAL", PAG_PLANHO_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_PLANHO_CANT_NRO_CLIC)-1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CPH_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodos);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase("Cargar Medico")) {
                System.out.println("---------------------__CARGAR_MEDICO__--------------------------");
                String IDCLINICA = (String) request.getParameter("cCli");
                String IDMEDICO = (String) request.getParameter("tIM");
                String NOM_MEDICO = (String) request.getParameter("tNM");
                String APE_MEDICO = (String) request.getParameter("tAM");
                String CINRO_MEDICO = (String) request.getParameter("tCM");
                String ESTADO = (String) request.getParameter("cE");
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _IDCLINICA:    "+IDCLINICA);
                System.out.println("_   _ID_MEDICO:    "+IDMEDICO);
                System.out.println("_   _NOM_MEDIC:    "+NOM_MEDICO);
                System.out.println("_   _APE_MEDIC:    "+APE_MEDICO);
                System.out.println("_   _CINRO_MED:    "+CINRO_MEDICO);
                System.out.println("_   ____ESTADO:    "+ESTADO);
                System.out.println("-------------------------------");
                
                // MODAL 
                // ID MEDICO SELECCIONADO 
                String CBX_IDMEDICO = (String) request.getParameter("cbxAddNewCli");
                // RECUPERO LOS OTROS DATOS DEL MEDICO SELECCIONADO POR MEDIO DE SU ID 
                List<BeanPersona> listaDatosMedico;
                listaDatosMedico = metodosPersona.traerDatosPersona(CBX_IDMEDICO);
                Iterator<BeanPersona> iterMedico = listaDatosMedico.iterator();
                BeanPersona datosMedNew = null;
                String MEDICO_NOM="", MEDICO_APE="", MEDICO_CINRO="", MEDICO_RAZONSOCIAL="", MEDICO_RUC="";
                while(iterMedico.hasNext()) {
                    datosMedNew = iterMedico.next();
                    
                    MEDICO_NOM = datosMedNew.getRP_NOMBRE();
                    MEDICO_APE = datosMedNew.getRP_APELLIDO();
                    MEDICO_CINRO = datosMedNew.getRP_CINRO();
                    MEDICO_RAZONSOCIAL = datosMedNew.getRP_RAZON_SOCIAL();
                    MEDICO_RUC = datosMedNew.getRP_RUC();
                }
                
//                // VALIDACION PARA EL MOMENTO DE CARGAR EL MEDICO CONTROLAR SI ESTE MEDICO YA TIENE UN PLAN HORARIO O NO / Y SI YA TUVIESE ENTONCES LE CARGO PARA QUE LO VEA Y MODIFIQUE ESE 
//                if (metodos.controlarIdMedPH(CBX_IDMEDICO) == true) {
//                    
//                }
                
                var_redireccionar = 1;
                acceso = "add_ph.htm";
                request.setAttribute("CPH_IDCLINICA", IDCLINICA);
                request.setAttribute("CPH_IDMEDICO", CBX_IDMEDICO);
                request.setAttribute("CPH_NOM_MEDICO", MEDICO_NOM);
                request.setAttribute("CPH_APE_MEDICO", MEDICO_APE);
                request.setAttribute("CPH_CINRO_MEDICO", MEDICO_CINRO);
                request.setAttribute("CPH_ESTADO", ESTADO);
                
            } else if (accion.equalsIgnoreCase("Cargar Detalle")) {
                System.out.println("---------------------__CARGAR_DETALLE__--------------------------");
                String IDCLINICA = (String) request.getParameter("cCli");
                String IDMEDICO = (String) request.getParameter("tIM");
                String NOM_MEDICO = (String) request.getParameter("tNM");
                String APE_MEDICO = (String) request.getParameter("tAM");
                String CINRO_MEDICO = (String) request.getParameter("tCM");
//                String DESDE = (String) request.getParameter("tPHFD");
//                String HASTA = (String) request.getParameter("tPHFH");
//                String TURNO = (String) request.getParameter("cPHT");
//                String DIA = (String) request.getParameter("tPHD");
                String ESTADO = (String) request.getParameter("cE");
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _IDCLINICA:    "+IDCLINICA);
                System.out.println("_   _ID_MEDICO:    "+IDMEDICO);
                System.out.println("_   _NOM_MEDIC:    "+NOM_MEDICO);
                System.out.println("_   _APE_MEDIC:    "+APE_MEDICO);
                System.out.println("_   _CINRO_MED:    "+CINRO_MEDICO);
//                System.out.println("_   _____DESDE:    "+DESDE);
//                System.out.println("_   _____HASTA:    "+HASTA);
//                System.out.println("_   _____TURNO:    "+TURNO);
//                System.out.println("_   _______DIA:    "+DIA);
                System.out.println("_   ____ESTADO:    "+ESTADO);
                System.out.println("-------------------------------");
                
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                if ((sesionDatosUsuario.getAttribute("CPH_LISTA_DET") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                    listaDetalle = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                }
                int item_lista = 1;
                if ((sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET") == null) == false) {
                    item_lista = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET"));
                }
                
                var_redireccionar = 1;
                acceso = "add_phd.htm";
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(item_lista));
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle);
                sesionDatosUsuario.setAttribute("CPH_IDCLINICA", IDCLINICA);
                sesionDatosUsuario.setAttribute("CPH_IDMEDICO", IDMEDICO);
                sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", NOM_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", APE_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", CINRO_MEDICO);
//                request.setAttribute("CPH_DESDE", DESDE);
//                request.setAttribute("CPH_HASTA", HASTA);
//                request.setAttribute("CPH_TURNO", TURNO);
//                request.setAttribute("CPH_DIA", DIA);
                sesionDatosUsuario.setAttribute("CPH_ESTADO", ESTADO);
                
            } else if (accion.equalsIgnoreCase("Volver a la cabecera")) {
                System.out.println("---------------------__VOLVER_A_LA_CABECERA__--------------------------");
                String IDCLINICA = (String) sesionDatosUsuario.getAttribute("CPH_IDCLINICA");
                if (IDCLINICA == null) {
                    IDCLINICA = (String) request.getParameter("tAIC");
                }
                String IDMEDICO = (String) sesionDatosUsuario.getAttribute("CPH_IDMEDICO");
                if (IDMEDICO == null) {
                    IDMEDICO = (String) request.getParameter("tAIM");
                }
                String NOM_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO");
                if (NOM_MEDICO == null) {
                    NOM_MEDICO = (String) request.getParameter("tAMN");
                }
                String APE_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_APE_MEDICO");
                if (APE_MEDICO == null) {
                    APE_MEDICO = (String) request.getParameter("tAMA");
                }
                String CINRO_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO");
                if (CINRO_MEDICO == null) {
                    CINRO_MEDICO = (String) request.getParameter("tAMC");
                }
                String ESTADO = (String) sesionDatosUsuario.getAttribute("CPH_ESTADO");
                if (ESTADO == null) {
                    ESTADO = (String) request.getParameter("tAPHE");
                }
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _IDCLINICA:    "+IDCLINICA);
                System.out.println("_   _ID_MEDICO:    "+IDMEDICO);
                System.out.println("_   _NOM_MEDIC:    "+NOM_MEDICO);
                System.out.println("_   _APE_MEDIC:    "+APE_MEDICO);
                System.out.println("_   _CINRO_MED:    "+CINRO_MEDICO);
                System.out.println("_   ____ESTADO:    "+ESTADO);
                System.out.println("-------------------------------");
                
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                if ((sesionDatosUsuario.getAttribute("CPH_LISTA_DET") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                    listaDetalle = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                }
                int item_lista = 1;
                if ((sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET") == null) == false) {
                    item_lista = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET"));
                }
                
                var_redireccionar = 1;
                acceso = "add_ph.htm";
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(item_lista));
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle);
                sesionDatosUsuario.setAttribute("CPH_IDCLINICA", IDCLINICA);
                sesionDatosUsuario.setAttribute("CPH_IDMEDICO", IDMEDICO);
                sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", NOM_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", APE_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", CINRO_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_ESTADO", ESTADO);
                
            } else if (accion.equalsIgnoreCase("Cargar a la lista")) {
                System.out.println("---------------------__CARGAR_A_LA_LISTA__--------------------------");
                // RECUPERO LOS DATOS 
                idPlanHorario = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO");
                String DESDE = (String) request.getParameter("tPHFD");
                String HASTA = (String) request.getParameter("tPHFH");
                String TURNO = (String) request.getParameter("cPHT");
                String DIA = (String) request.getParameter("tPHD");
                // LOS IMPRIMO EN EN LA CONSOLA PARA VERIFICARLOS 
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _ID_PLAN_HO:   "+idPlanHorario);
                System.out.println("_   _____DESDE:    "+DESDE);
                System.out.println("_   _____HASTA:    "+HASTA);
                System.out.println("_   _____TURNO:    "+TURNO);
                System.out.println("_   _______DIA:    "+DIA);
                System.out.println("-------------------------------");
                // RECUPERO LA LISTA Y EL ITEM DE LA LISTA 
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                if ((sesionDatosUsuario.getAttribute("CPH_LISTA_DET") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                    listaDetalle = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                }
                int item_lista = 1;
                if ((sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET") == null) == false) {
                    item_lista = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET"));
                }
                
                // VALIDACIONES 
                String TIPO_MENSAJE="", MENSAJE=null;
                // CONTROLO PRIMERAMENTE QUE NO SE HAYAN DEJADOS CAMPOS VACIOS PARA AÑADIR A LA LISTA 
                if (DESDE == null || DESDE.isEmpty()) {
                    TIPO_MENSAJE = "2";
                    MENSAJE = "No puede dejar ningun campo vacio.";
                } else if(HASTA == null || HASTA.isEmpty()) {
                    TIPO_MENSAJE = "2";
                    MENSAJE = "No puede dejar ningun campo vacio.";
                } else if(TURNO == null || TURNO.isEmpty()) {
                    TIPO_MENSAJE = "2";
                    MENSAJE = "No puede dejar ningun campo vacio.";
                } else if(DIA == null || DIA.isEmpty()) {
                    TIPO_MENSAJE = "2";
                    MENSAJE = "No puede dejar ningun campo vacio.";
                } else {
                    // CARGO A LA LISTA EL NUEVO HORARIO 
                    BeanPlanHorario datosNew = new BeanPlanHorario();
                        datosNew.setOPHD_ITEM("");
//                        datosNew.setOPHD_ITEM(String.valueOf(item_lista));
                        datosNew.setOPHD_IDPLANHORARIO(""); // LE CARGO VACIO PARA QUE DESDE EL MODELO PUEDA INTERPRETAR QUE ESTA LINEA ES AÑADIDA Y SE DEBE DE INSERTAR Y NO FUE OBTENIDA A TRAVES DE UN SELECT 
                        datosNew.setOPH_IDPLANHORARIO(""); // LE CARGO VACIO PARA QUE DESDE EL MODELO PUEDA INTERPRETAR QUE ESTA LINEA ES AÑADIDA Y SE DEBE DE INSERTAR Y NO FUE OBTENIDA A TRAVES DE UN SELECT 
//                        datosNew.setOPHD_IDPLANHORARIO(String.valueOf(idPlanHorario));
                        datosNew.setOPHD_TURNO(TURNO);
                        datosNew.setOPHD_DIA(DIA);
                        datosNew.setOPHD_DESDE(DESDE);
                        datosNew.setOPHD_HASTA(HASTA);
                        datosNew.setOPHD_USU_ALTA(idPersona); // LE CARGO YA EL USUARIO QUE CREA EL PLAN HORARIO PARA QUE NO DE NULL AL MOMENTO DE OBTENERLO DESDE EL MODELO 
                    listaDetalle.add(datosNew);
                    item_lista = item_lista + 1; // LE SUMO AL ITEM PARA QUE SEA INCREMENTABLE Y NO PASE SIEMPRE EL MISMO NUMERO 
                } // END IF VALIDACION.-
                
                acceso = "add_phd.htm";
                var_redireccionar = 1;
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(item_lista));
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", listaDetalle);
                // SI EL MENSAJE ESTA CARGADO, ES PORQUE SE ACTIVO ALGUNA VALIDACION, Y ENTONCES LE VUELVO A PASAR LOS CAMPOS QUE SE CARGO Y NO LOS LIMPIO 
                if (MENSAJE != null) {
                    request.setAttribute("CPH_DESDE", DESDE);
                    request.setAttribute("CPH_HASTA", HASTA);
                    request.setAttribute("CPH_TURNO", TURNO);
                    request.setAttribute("CPH_DIA", DIA);
                } else { // EN CASO DE QUE MENSAJE SE MANTENGA NULL ENTONCES SE SUPONE QUE NO SE ACTIVO ALGUNA VALIDACION Y SE CARGO CORRECTAMENTE LA LISTA Y LE PASO VACIO LOS CAMPOS PARA LIMPIARLE 
                    request.setAttribute("CPH_DESDE", "");
                    request.setAttribute("CPH_HASTA", "");
                    request.setAttribute("CPH_TURNO", "");
                    request.setAttribute("CPH_DIA", "");
                }
                
            } else if (accion.equalsIgnoreCase("Eliminar Fila")) {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println("---------------------__ELIMINAR_FILA_DE_LA_LISTA__--------------------------");
                // RECIBIR LOS PARAMETROS 
                idPlanHorario = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO");
                String IDCLINICA = (String) sesionDatosUsuario.getAttribute("CPH_IDCLINICA");
                if (IDCLINICA == null) { IDCLINICA = (String) request.getParameter("tAIC"); }
                String IDMEDICO = (String) sesionDatosUsuario.getAttribute("CPH_IDMEDICO");
                if (IDMEDICO == null) { IDMEDICO = (String) request.getParameter("tAIM"); }
                String NOM_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO");
                if (NOM_MEDICO == null) { NOM_MEDICO = (String) request.getParameter("tAMN"); }
                String APE_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_APE_MEDICO");
                if (APE_MEDICO == null) { APE_MEDICO = (String) request.getParameter("tAMA"); }
                String CINRO_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO");
                if (CINRO_MEDICO == null) { CINRO_MEDICO = (String) request.getParameter("tAMC"); }
                String ESTADO = (String) sesionDatosUsuario.getAttribute("CPH_ESTADO");
                if (ESTADO == null) { ESTADO = (String) request.getParameter("tAPHE"); }
                // IMPRESION DE LAS VARIABLES OBTENIDAS 
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _ID_PLAN_HO:   "+idPlanHorario);
                System.out.println("_   _IDCLINICA:    "+IDCLINICA);
                System.out.println("_   _ID_MEDICO:    "+IDMEDICO);
                System.out.println("_   _NOM_MEDIC:    "+NOM_MEDICO);
                System.out.println("_   _APE_MEDIC:    "+APE_MEDICO);
                System.out.println("_   _CINRO_MED:    "+CINRO_MEDICO);
                System.out.println("_   ____ESTADO:    "+ESTADO);
                System.out.println("-------------------------------");
                
                // RECUPERO LA LISTA Y EL ITEM DE LA LISTA 
                List<BeanPlanHorario> listaDetalle = new ArrayList<>();
                if ((sesionDatosUsuario.getAttribute("CPH_LISTA_DET") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                    listaDetalle = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                }
                int item_lista = 1;
                if ((sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET") == null) == false) {
                    item_lista = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET"));
                }
                
                // RECUPERO LOS DATOS DEL PLAN HORARIO A ELIMINAR 
                String IDPLANHORA = (String) request.getParameter("tAIPH"); // tAIPH : TXT AUXILIAR ID PLAN HORARIO  
                int ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAIPHD"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0 // tAIPHD : TXT AUXILIAR ITEM PLAN HORARIO DETALLE 
                String ITEM_ELIM_BD = (String) request.getParameter("tAIPHDdb"); // VARIABLE QUE GUARDA EL ITEM DE LA BASE, YA QUE EL ITEM DE LA BASE NO SIEMPRE ES EL MISMO QUE EL ITEM QUE SE MUESTRA, A VECES SE PUEDE DAR EL CASO DE QUE EL ITEM DE LA BASE ES 15 PERO VISUALMENTE SE VE COMO 8; QUE PUEDE PASAR SI ES QUE SE ANULA VARIOS ITEMS DEL DETALLE 
                String MENSAJE = null, TIPO_MENSAJE = "";
                System.out.println("----------__ITEM_A_ELIMINAR__-------------");
                System.out.println("_   ____ID_PLAN_HOR:  "+IDPLANHORA);
                System.out.println("_   __ITEM_ELIMINAR:  "+ITEM_ELIMINAR);
                System.out.println("_   __ITEM_ELIMI_BD:  "+ITEM_ELIM_BD);
                System.out.println("------------------------------------------");
                System.out.println("_   _____LISTA_ITEM:   :"+String.valueOf(listaDetalle.size()));
                System.out.println("_   _____item_lista:   :"+item_lista);
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                
                // ELIMINO DE LA LISTA 
                List<BeanPlanHorario> CPH_LISTA_NEW = new ArrayList<BeanPlanHorario>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                try {
                    System.out.println("______#1________________ELIMINAR_FILA_DE_LA_LISTA___________________________");
                    // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                    if (ITEM_ELIMINAR == listaDetalle.size()) { // OBSERVACION: AL GUARDAR Y EDITAR LOS DATOS DE UN PLAN HORARIO, EN EL DETALLE, AL QUERER ELIMINAR LA ULTIMA FILA DE LA GRILLA ME SALTABA ERROR, PERO SOLO EN FICHAS GUARDADAS, ENTONCES LE HICE ESTE IF Y CONSULTO SI ERA LA ULTIMA FILA LA QUE SE QUIERE ELIMINAR, SI ES ASI ENTONCES LE RESTO UNO MAS Y EN PRACTICA ME SOLUCIONO RESTARLE UNO CUANDO LA ULTIMA FILA ES LA QUE SE QUIERE ELIMINAR; YA QUE EL ERROR QUE ME SALTABA ERA LA INDEXEXCEPTION 
                        ITEM_ELIMINAR = ITEM_ELIMINAR-1;
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".   ________IF___________");
                        System.out.println(".   .   __ITEM:  :"+ITEM_ELIMINAR);
                        System.out.println(".");
                        System.out.println(".");
                        listaDetalle.remove(ITEM_ELIMINAR);
                    } else {
                        System.out.println("_   _________ELSE____________");
                        listaDetalle.remove(ITEM_ELIMINAR);
                    }
                    System.out.println("______#2________________ELIMINAR_FILA_DE_LA_BASE___________________________");
                    // VALIDACION PARA CONTROLAR SI ES QUE EL IDPLANHORARIO SE ENCUENTRA CARGADO PARA PODER ELIMINAR (INACTIVAR) LA LINEA DEL DETALLE DE LA BASE   
                    if (!(IDPLANHORA == null) || !(IDPLANHORA.isEmpty())) {
                        System.out.println("___IF___");
                        BeanPlanHorario datos_eli = new BeanPlanHorario();
                            datos_eli.setOPHD_IDPLANHORARIO(idPlanHorario);
                            datos_eli.setOPHD_ITEM(ITEM_ELIM_BD);
                            datos_eli.setOPHD_USU_ALTA(idPersona);
                        // LLAMO AL METODO PARA ELIMINAR LA FILA DE LA BASE Y CARGO EL RESULTADO DE LA EJECUCION DE LA SENTENCIA PARA SABER QUE SI REALIZO CORRECTAMENTE O DIO ALGUN ERROR 
                        TIPO_MENSAJE = metodos.eliminar(datos_eli);
                        if (TIPO_MENSAJE.equals("1")) {
                            MENSAJE = "Se ha eliminado correctamente la fila.";
                        } else if(TIPO_MENSAJE.equals("2")) {
                            MENSAJE = "Ocurrio un error mientras se ejecutaba la sentencia.\n Vuelva a intentarlo mas tarde.";
                        }
                        System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                        System.out.println("_   _CTRL_ELIMINAR:     MENSAJE("+TIPO_MENSAJE+"):      :"+MENSAJE);
                        System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                    } else {
                        System.out.println("____ELSE____");
                    }
                    System.out.println("______#3________________FOR_PARA_AGREGAR_NUEVA_LISTA___________________________");
                    // ORDENAR LOS ITEMS 
                    for (BeanPlanHorario LISTA_DETALLE : listaDetalle) {
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println("___     _ITEM_NEW:      "+itemNew);
                        System.out.println(".");
                        System.out.println(".");
                        // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                        BeanPlanHorario datosNew = new BeanPlanHorario();
                            datosNew.setOPHD_ITEM(LISTA_DETALLE.getOPHD_ITEM());
//                            datosNew.setOPHD_ITEM(String.valueOf(itemNew));
                            datosNew.setOPH_IDPLANHORARIO(LISTA_DETALLE.getOPHD_IDPLANHORARIO());
                            datosNew.setOPHD_IDPLANHORARIO(LISTA_DETALLE.getOPHD_IDPLANHORARIO());
                            datosNew.setOPHD_TURNO(LISTA_DETALLE.getOPHD_TURNO());
                            datosNew.setOPHD_DIA(LISTA_DETALLE.getOPHD_DIA());
                            datosNew.setOPHD_DESDE(LISTA_DETALLE.getOPHD_DESDE());
                            datosNew.setOPHD_HASTA(LISTA_DETALLE.getOPHD_HASTA());
                        datosNew.setOPHD_USU_ALTA(LISTA_DETALLE.getOPHD_USU_ALTA()); // LE CARGO YA EL USUARIO QUE CREA EL PLAN HORARIO PARA QUE NO DE NULL AL MOMENTO DE OBTENERLO DESDE EL MODELO 
                        CPH_LISTA_NEW.add(datosNew);
                        // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                        itemNew = itemNew + 1;
                    } // end for 
                } catch (Exception e) {
                    System.out.println(".");System.out.println(".");
                    itemNew = item_lista; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                    System.out.println("___PLAN_HORARIO_____ERROR_POR_NRO_ITEM____");
                    Logger.getLogger(ControllerPlanHorario.class.getName()).log(Level.SEVERE, null, e);
                    System.out.println(".");System.out.println(".");
                } // end catch 
                System.out.println("______#4________________PASAR_PARAMETROS_AL_JSP___________________________");
                var_redireccionar = 1;
                acceso = "add_phd.htm";
                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", String.valueOf(itemNew)); // NUMERACION DEL ITEM PARA LUEGO PODER CONTROLAR Y TENER EL NUMERO SIGUIENTE Y ACTUAL DEL ITEM 
                sesionDatosUsuario.setAttribute("CPH_LISTA_DET", CPH_LISTA_NEW);
                // CABECERA 
                sesionDatosUsuario.setAttribute("CPH_IDCLINICA", IDCLINICA);
                sesionDatosUsuario.setAttribute("CPH_IDMEDICO", IDMEDICO);
                sesionDatosUsuario.setAttribute("CPH_NOM_MEDICO", NOM_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_APE_MEDICO", APE_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_CINRO_MEDICO", CINRO_MEDICO);
                sesionDatosUsuario.setAttribute("CPH_ESTADO", ESTADO);
                // VARIABLES QUE USO PARA CARGAR LOS DATOS  
                request.setAttribute("CPH_DESDE", "");
                request.setAttribute("CPH_HASTA", "");
                request.setAttribute("CPH_TURNO", "");
                request.setAttribute("CPH_DIA", "");
                
                
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
            } else if (accion.equalsIgnoreCase("Limpiar")) {
                System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                var_redireccionar = 1;
                acceso = "plan_hor.htm";
                sesionDatosUsuario.setAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC", "1");
                sesionDatosUsuario.setAttribute("PAG_PLANHO_LISTA_ACTUAL", "1");
                sesionDatosUsuario.setAttribute("CPH_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-----------------ERROR-PLAN_HORARIO--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            var_redireccionar = 0;
            Logger.getLogger(ControllerPlanHorario.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------------");
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
    
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelPlanHorario metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
        String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtMedicoId = (String) request.getParameter("cbxAddNewMed"); // medico id 
        if (txtMedicoId == null || txtMedicoId.isEmpty()) {
            txtMedicoId = "";
        }
        System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
        System.out.println("_   MEDICO_ID   :   "+txtMedicoId);
        String checkClinicaFiltro = (String) request.getParameter("check_clinica"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA CLINICA QUE SE ENCUENTRA EN EL COMBO O NO 
        String txtClinicaId = (String) request.getParameter("cbxAddNewClinica"); // clinica id 
        if (txtClinicaId == null || txtClinicaId.isEmpty()) {
            txtClinicaId = "";
        }
        System.out.println("_   CHECK_CLINICA:   "+checkClinicaFiltro);
        System.out.println("_   CLINICA_ID   :   "+txtClinicaId);
        int var_btn_editar = 0; // VARIABLE QUE UTILIZARE EN EL JSP PARA SABER SI SE ACTIVO EL FILTRO DE CLINICA Y ASI EVITAR ACTIVAR O MOSTRAR EL BOTON DE EDITAR EL PLAN HORARIO, PARA QUE NO PUEDA EDITAR LOS DATOS DE OTRAS CLINICAS 

        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
        if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
            checkMedicoFiltro = "OFF";
        } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            txtMedicoId = "";
        }

        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR CLIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE CLIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR CLIENTE 
        if (checkClinicaFiltro == null || checkClinicaFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL CLIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDCLIENTE 
            checkClinicaFiltro = "OFF";
            var_btn_editar = 1; // LE CARGO UNO PARA INABILITARLE EL BTN DE EDITAR 
        } else if (checkClinicaFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            txtClinicaId = "";
            var_btn_editar = 0; // MANTENGO EN CERO Y LE MOSTRARIA EL BTN YA QUE NO FILTRO POR CLINICA 
        }

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanPlanHorario> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar, txtMedicoId, txtClinicaId);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "plan_hor.htm";
//        var_redireccionar = 1;
        request.setAttribute("CPH_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CPH_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CPH_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CPH_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
        request.setAttribute("CPH_TXT_FIL_ID_MED", txtMedicoId);
        request.setAttribute("CPH_CHECK_FILTRAR_CLI_01", checkClinicaFiltro);
        request.setAttribute("CPH_TXT_FIL_ID_CLI", txtClinicaId);
        request.setAttribute("CPH_BTN_EDITAR_VALI", String.valueOf(var_btn_editar));
        sesionDatosUsuario.setAttribute("CPH_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
} // END CLASS 