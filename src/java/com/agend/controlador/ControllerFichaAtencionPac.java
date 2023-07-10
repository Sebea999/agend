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
import com.agend.javaBean.BeanAtencionPaciente;
import com.agend.javaBean.BeanServicio;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelFichaAtencionPac;
import com.agend.modelo.ModelRef_Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
//@Controller
@WebServlet(name="controllerFichaAtencionPac", urlPatterns="/CFAPSrv") // CFAPSrv : controller ficha atencion paciente servlet 
public class ControllerFichaAtencionPac extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    // [OBS] :le cambie el nombre a los RequestMapping y los nombres de los metodos para evitar errores de ambiguedad con el controllerFichaAtencionPacNutri; y lo termino comentando porque persiste el error 
    
//    @RequestMapping("/fatencion")
//    public ModelAndView fficha_atencion_paciente(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__1__---------CONTROLLER__ATENCION_FICHA-PACIENTE--------------MODEL_AND_VIEW-------");
//        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
//        
//        String paginaMav = "menu_principal";
////        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "aaaaaaa_leer_coment_→__", request); //crear la pagina para el paciente / OBS.: CREO QUE NO ES NECESARIO YA QUE EL PACIENTE DESDE SU PERFIL VA A PODER VER LAS CONSULTAS Y SUS FICHAS QUE TIENE Y DESDE AHI NOMAS LE CREO UN BOTON PARA QUE PUEDA "VER" Y DESDE ESE BOTON LE REDIRECCIONO Y VA A UTILIZAR LA PAGINA DE VER_FICHA_ATENCION 
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        } else 
//        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER) == true) { //  1 : ADMIN  -  3 : SECRETARIO - 5 : MEDICO 
////          if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) == true || modeloPerfil.isPerfilMedico(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER) == true) { //  1 : ADMIN  -  3 : SECRETARIO 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFichaAtencionPac", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFichaAtencionPac", request);
//        
//        mav.setViewName(paginaMav);
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
//        // COMBO BOX DE MEDICO Y ESPECIALIDAD 
//        request.setAttribute("CFAP_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CFAP_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        request.setAttribute("CFAP_CHECK_FILTRAR_PAC", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
//        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", ""); // CONFIGURACION DEL BOTON DE "VOLVER ATRAS" DE LA PAGINA DE VER FICHA DE ATENCION DEL PACIENTE PARA PODER VOLVER A LA PAGINA DE PACIENTE Y NO A LA PAGINA PRINCIPAL DE FICHA DE ATENCION / EN ESTE CASO LO UTILIZO PARA CUANDO ENTRE DESDE LA PAGINA DE FICHA DE ATENCION RETORNE A ESTA MISMA PAGINA Y NO A LA PAGINA ANTERIOR QUE ACTIVO ESTE BOTON 
//        
//        return mav;
//    }
//    
//    
//    
//    @RequestMapping("/fadd_atencion")
//    public ModelAndView fadd_atencion_ficha_pac(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__2__--------CONTROLLER__AÑADIR_ATENCION_(FICHA-PACIENTE)------------MODEL_AND_VIEW-------");
//        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
//        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
//        
//        // OBSERVACION: 
//        // LA PAGINA add_agend SOLO VAN A INGRESAR EL SECRETARIO Y EL ADMINISTRADOR, PERO LE COLOCO EL CONTROL PORQUE EN LA "accion" DEL CALENDARIO AL SELECCIONAR UN DIA, EN EL DO_POST TENGO PARA QUE REDIRECCIONE A "agend" PERO SI ES ADMIN O SECRETARIO ME MANDA A LA PAGINA PRINCIPAL Y PARA EVITAR ESO VOY A REDIRECCIONAR A ESTA PAGINA Y DESDE ACA NOMAS LE AGREGO ESTE CONTROL PARA QUE SI ES PACIENTE LE REDIRECCIONO A LA PAGINA QUE CORRESPONDE Y NO LE LLEGUE A MOSTRAR LA PAGINA QUE SOLO DEBERIA DE VER EL SECRETARIO Y EL ADMIN 
//        String paginaMav = "menu_principal";
////        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
////            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "", request);
////            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
////        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER)==true || modeloPerfil.isPerfilMedico(IDPERFIL_USER)==true || modeloPerfil.isPerfilSecre(IDPERFIL_USER)==true) { //  1 : ADMIN  -  5 : MEDICO  -  3 : SECRETARIO 
//        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER)==true) { //  1 : ADMIN  -  5 : MEDICO  -  3 : SECRETARIO 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_Datos", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_Datos", request);
//        System.out.println("pagina_mav:     "+paginaMav);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(paginaMav);
//        return mav;
//    }
//    
//    
//    @RequestMapping("/fver_atencion")
//    public ModelAndView fver_atencion_ficha_pac(HttpServletRequest request) {
//        ModelAndView mav = new ModelAndView();
//        
//        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
//        HttpSession sesionDatosUsuario = request.getSession();
//        
//        System.out.println("-----__3__--------CONTROLLER__VER_ATENCION_(FICHA-PACIENTE)------------MODEL_AND_VIEW-------");
//        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
//        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
//        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
//        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
//        
//        String paginaMav = "menu_principal";
//        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER)==true || modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // LE AGREGO LA CONSULTA DE QUE SI EL PERFIL ES PACIENTE, PORQUE LA PAGINA DE ATENCION NO ESTA HABILITADA PARA EL PACIENTE PERO SI EL VER_FICHA 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_VerDatos", request);
//        }
////        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_VerDatos", request);
//        System.out.println("pagina_mav:     "+paginaMav);
//        
//        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
////        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        
//        mav.setViewName(paginaMav);
//        return mav;
//    }
    
    
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        doPost(request, response);
    }
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException, ServletException {
        
        // ESTAS DOS LINEAS DE CODIGO SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ModelFichaAtencionPac metodos = new ModelFichaAtencionPac();
        ModelRef_Servicio metodosServ = new ModelRef_Servicio();
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA QUE UTILIZARE PARA RECUPERAR LOS DATOS QUE VAYA AGREGANDO 
        
        int var_redireccionar = 0;
        int var_no_redireccionar = 1; // VARIABLE QUE UTILIZO PARA NO QUERER REDIRECCIONAR DESDE ESTE CONTROLADOR YA QUE DESDE OTRO CONTROLADOR YA LE ESTOY CARGANDO LA PAGINA / LO INSTANCIO CON UNO PARA QUE SIEMPRE REDIRECCIONE Y EN EL EVENTO QUE LLAME A OTRO CONTROLADOR ENTONCES LE CAMBIO A CERO Y AHI EVITO LLAMAR AL DISPATCHER 
        String acceso = "atencion.htm";
        String accion = request.getParameter("accion");
        String accionPag = request.getParameter("accionPag");
        String idPersona, idAtencionPac="";
        
        try {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   -------------______CONTROLADOR_DE_EVENTOS_DE_LA_PAGINA_DE_FICHA-DE-ATENCION_______----------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__ACCION:      : "+accion);
            System.out.println("__ACCION_PAGINACION:      : "+accionPag);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if (accion != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_        ___ACCION___        _");
                System.out.println(".");System.out.println(".");
                if (accion.equalsIgnoreCase("Cargar Atencion") || accion.equalsIgnoreCase("Cargar Atención")) {
                    System.out.println("---------------------__CARGAR_ATENCION__--------------------------");
                    String IDAGENDAMIENTO = (String) request.getParameter("tIA");
                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    String ITEM_AGEN_DET = (String) request.getParameter("tAID");
                    System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
                    String IDPACIENTE = (String) request.getParameter("tIP");
                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
                    List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", "1");
                    
                } else if(accion.equalsIgnoreCase("Editar Atención") || accion.equalsIgnoreCase("Editar Ficha")) {
                    System.out.println("---------------------__EDITAR_ATENCION__--------------------------");
                    idAtencionPac = (String) request.getParameter("tIAP");
                    System.out.println("_   __ID_ATENCION_PAC:   :"+idAtencionPac);
                    String IDAGENDAMIENTO = (String) request.getParameter("tIA");
                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    String ITEM_AGEN_DET = (String) request.getParameter("tAID");
                    System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
                    String IDPACIENTE = (String) request.getParameter("tIP");
                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
                    
                    // CARGAR LISTA SERVICIOS 
                    List<BeanAtencionPaciente> LISTA_SERV = new ArrayList<>();
                    int ITEM_LIST_SERV = 0;
                    
                    // CARGAR_GRILLA_DETALLE 
                    List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                    LISTA_SERVICIOS = metodos.traer_datos(idAtencionPac, sesionDatosUsuario);
                    Iterator<BeanAtencionPaciente> iListDet = LISTA_SERVICIOS.iterator();
                    BeanAtencionPaciente datos = new BeanAtencionPaciente();
                    String ATEN_PESO="", ATEN_TALLA="", ATEN_IMC="", ATEN_PCEFALICO="", ATEN_FC="", ATEN_TEMP="", ATEN_PA="", ATEN_FRESP="", ATEN_MOTIVO_CONSULTA="", ATEN_EXPLORACION_FISICA="", ATEN_DIAGNOSTICO="", ATEN_TRATAMIENTO="", ATEN_RECETA="", ATEN_ESTUDIOS_SOLICITADOS="";
                    while(iListDet.hasNext()) {
                        datos = iListDet.next();
                        ATEN_PESO = datos.getOAP_PESO();
                        ATEN_TALLA = datos.getOAP_TALLA();
                        ATEN_IMC = datos.getOAP_IMC();
                        ATEN_PCEFALICO = datos.getOAP_P_CEFALICO();
                        ATEN_FC = datos.getOAP_FC();
                        ATEN_TEMP = datos.getOAP_TEMP();
                        ATEN_PA = datos.getOAP_PA();
                        ATEN_FRESP = datos.getOAP_F_RESP();
                        ATEN_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA();
                        ATEN_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA();
                        ATEN_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO();
                        ATEN_TRATAMIENTO = datos.getOAP_TRATAMIENTO();
                        ATEN_RECETA = datos.getOAP_RECETA();
                        ATEN_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS();
                        
                        System.out.println("_   ITEM_DETALLE:     :"+datos.getOAPD_ITEM());
                        // AGREGO A LA LISTA 
                        if (!(datos.getOAPD_ITEM() == null)) {
                            System.out.println("__      if_____");
                            BeanAtencionPaciente datosServ = new BeanAtencionPaciente();
                                datosServ.setOAPD_IDATENCIONPAC(datos.getOAPD_IDATENCIONPAC());
                                datosServ.setOAPD_ITEM(datos.getOAPD_ITEM());
//                                datosServ.setOAPD_ITEM(String.valueOf(ITEM_LIST_SERV));
                                datosServ.setOAPD_IDSERVICIO(datos.getOAPD_IDSERVICIO());
                                datosServ.setRHS_IDSERVICIO(datos.getOAPD_IDSERVICIO());
                                datosServ.setRHS_DESC_SERVICIO("");
                                datosServ.setRHS_MONTO(datos.getOAPD_MONTO());
                                datosServ.setOAPD_MONTO(datos.getOAPD_MONTO());
                                datosServ.setOAPD_ESTADO(datos.getOAPD_ESTADO());
                                datosServ.setRHS_ESTADO(datos.getOAPD_ESTADO());
                            LISTA_SERV.add(datosServ);
                            ITEM_LIST_SERV = ITEM_LIST_SERV + 1;
                        } else {
                            System.out.println("__      else____");
                        }
                    }
                    
//                    // CARGAR_ITEM_DETALLE
//                    String NRO_ITEM = String.valueOf(LISTA_SERVICIOS.size()+1);
//                    System.out.println("_   _NRO_ITEM_LISTA:     :"+NRO_ITEM);
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    sesionDatosUsuario.setAttribute("CFAP_BAND_REPEAT_ONE", "1"); // VARIABLE QUE UTILIZO COMO BANDERA PARA EVITAR QUE AL RECARGAR LA PAGINA EN EL EXPEDIENTE DE PACIENTE SE REPITA LA ULTIMA ACCION (ELIMINAR FICHA O GUARDAR FICHA) 
                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR QUE RECUPERO PARA QUE NO PIENSE QUE ESTOY GUARDANDO UNA NUEVA FICHA SINO SEPA A CUAL FICHA ACTUALIZAR 
                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", NRO_ITEM);
                    // CAMPOS DE TEXTO 
                    request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                    request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                    request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                    request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                    request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                    request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                    request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                    request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                    request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                    request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                    request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                    request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                    request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                    request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                    
                } else if(accion.equalsIgnoreCase("Ver Atención") || accion.equalsIgnoreCase("Ver Ficha")) {
                    var_redireccionar = 1;
                    acceso = ver_ficha_atencion_paciente(sesionDatosUsuario, request, metodos, idAtencionPac, var_redireccionar, acceso);
//                    System.out.println("---------------------__VER_ATENCION__--------------------------");
//                    idAtencionPac = (String) request.getParameter("tIAP");
//                    System.out.println("_   __ID_ATENCION_PAC:   :"+idAtencionPac);
//                    String IDAGENDAMIENTO = (String) request.getParameter("tIA");
//                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
//                    String ITEM_AGEN_DET = (String) request.getParameter("tAID");
//                    System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
//                    String IDPACIENTE = (String) request.getParameter("tIP");
//                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
//                    
//                    // CARGAR LISTA SERVICIOS 
//                    List<BeanAtencionPaciente> LISTA_SERV = new ArrayList<>();
//                    int ITEM_LIST_SERV = 0;
//                    
//                    // CARGAR_GRILLA_DETALLE 
//                    List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
//                    LISTA_SERVICIOS = metodos.traer_datos(idAtencionPac, sesionDatosUsuario);
//                    Iterator<BeanAtencionPaciente> iListDet = LISTA_SERVICIOS.iterator();
//                    BeanAtencionPaciente datos = new BeanAtencionPaciente();
//                    String ATEN_PESO="", ATEN_TALLA="", ATEN_IMC="", ATEN_PCEFALICO="", ATEN_FC="", ATEN_TEMP="", ATEN_PA="", ATEN_FRESP="", ATEN_MOTIVO_CONSULTA="", ATEN_EXPLORACION_FISICA="", ATEN_DIAGNOSTICO="", ATEN_TRATAMIENTO="", ATEN_RECETA="", ATEN_ESTUDIOS_SOLICITADOS="";
//                    while(iListDet.hasNext()) {
//                        datos = iListDet.next();
//                        System.out.println("____WHILE____");
//                        ATEN_PESO = datos.getOAP_PESO();
//                        ATEN_TALLA = datos.getOAP_TALLA();
//                        ATEN_IMC = datos.getOAP_IMC();
//                        ATEN_PCEFALICO = datos.getOAP_P_CEFALICO();
//                        ATEN_FC = datos.getOAP_FC();
//                        ATEN_TEMP = datos.getOAP_TEMP();
//                        ATEN_PA = datos.getOAP_PA();
//                        ATEN_FRESP = datos.getOAP_F_RESP();
//                        ATEN_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA();
//                        ATEN_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA();
//                        ATEN_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO();
//                        ATEN_TRATAMIENTO = datos.getOAP_TRATAMIENTO();
//                        ATEN_RECETA = datos.getOAP_RECETA();
//                        ATEN_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS();
//                        System.out.println("_   ITEM_DETALLE:     :"+datos.getOAPD_ITEM());
//                        // AGREGO A LA LISTA 
//                        if (!(datos.getOAPD_ITEM() == null)) {
//                            System.out.println("__      if_____");
//                            BeanAtencionPaciente datosServ = new BeanAtencionPaciente();
//                                datosServ.setOAPD_IDATENCIONPAC(datos.getOAPD_IDATENCIONPAC());
//                                datosServ.setOAPD_ITEM(datos.getOAPD_ITEM());
//                                datosServ.setOAPD_IDSERVICIO(datos.getOAPD_IDSERVICIO());
//                                datosServ.setRHS_IDSERVICIO(datos.getOAPD_IDSERVICIO());
//                                datosServ.setRHS_DESC_SERVICIO("");
//                                datosServ.setRHS_MONTO(datos.getOAPD_MONTO());
//                                datosServ.setOAPD_MONTO(datos.getOAPD_MONTO());
//                                datosServ.setOAPD_ESTADO(datos.getOAPD_ESTADO());
//                                datosServ.setRHS_ESTADO(datos.getOAPD_ESTADO());
//                            LISTA_SERV.add(datosServ);
//                            ITEM_LIST_SERV = ITEM_LIST_SERV + 1;
//                        } else {
//                            System.out.println("__      else____");
//                        }
//                    }
//                    
//                    // CARGAR_ITEM_DETALLE
////                    String ITEM_LIST_SERV = String.valueOf(LISTA_SERVICIOS.size()+1);
////                    System.out.println("_   _NRO_ITEM_LISTA:     :"+ITEM_LIST_SERV);
//                    
//                    var_redireccionar = 1;
//                    acceso = "ver_atencion.htm";
//                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR QUE RECUPERO PARA QUE NO PIENSE QUE ESTOY GUARDANDO UNA NUEVA FICHA SINO SEPA A CUAL FICHA ACTUALIZAR 
//                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
//                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
////                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
//                    // CAMPOS DE TEXTO 
//                    request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
//                    request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
//                    request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
//                    request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
//                    request.setAttribute("CFAP_TXT_FC", ATEN_FC);
//                    request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
//                    request.setAttribute("CFAP_TXT_PA", ATEN_PA);
//                    request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
//                    request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
//                    request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
//                    request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
//                    request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
//                    request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
//                    request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                    
                } else if(accion.equalsIgnoreCase("GUARDAR") || accion.equalsIgnoreCase("GRABAR")) {
                    System.out.println("------------------------__GUARDAR__--------------------------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                    System.out.println("__ID_FICHA_ATENCION_PACIENTE :   :"+idAtencionPac);
                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                    System.out.println("__ID_FAGENDAMIENTO :   :"+IDAGENDAMIENTO);
                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                    System.out.println("__ITEM_AGEND_DETALL:   :"+ITEM_AGEND);
                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                    System.out.println("__ID_PACIENTE :   :"+IDPACIENTE);
                    String ID_CLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER"); // GUARDO EL IDCLINICA DONDE SE ESTA CARGANDO LA FICHA DE ATENCION 
                    System.out.println("__ID_CLINICA :   :"+ID_CLINICA);
                    
                    // VARIABLE QUE UTILIZO PARA SABER A DONDE TENDRA QUE RETORNAR YA QUE PUEDE SER REDIRECCIONADO DESDE "VER PACIENTE" 
                    String return_pag;
                    String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS"); // VARIABLE QUE UTILIZO PARA SABER A QUE PAGINA TENGO QUE RETORNAR LUEGO DE ANULAR LA FICHA, YA QUE SE PUEDE REDIRECCIONAR DESDE EL EXPEDIENTE DE UN PACIENTE O DESDE 
                    System.out.println("_   _CTRL__BTN_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                    if (BTN_VOLVER_ATRAS == null) {
                        return_pag = "";
                    } else if (BTN_VOLVER_ATRAS.equalsIgnoreCase("VER_PACIENTE")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
                        return_pag = "ver_paciente.htm";
                    } else {
                        return_pag = "";
                    }
                    
                    if (idAtencionPac.isEmpty() || idAtencionPac.equals("")) { // agregar if para poder identificar si el idatencionpaciente esta vacio asi para guardar y si esta cargado luego de guardar ya no me vuelva a guardar y crear nomas una segunda accion para poder modificar para no arriesgar el guardar 
                    System.out.println("------------    ---------------    __GUARDAR _ IF __    ---------------    ---------------");    
                        // RECUPERAR LOS DATOS 
                        // PANEL /  PARAMETROS OPCIONALES 
                        String ATEN_PESO = (String) request.getParameter("tPe");
                        if (ATEN_PESO == null || ATEN_PESO.isEmpty()) { varValiVacio++; ATEN_PESO=""; }
                        String ATEN_TALLA = (String) request.getParameter("tTa");
                        if (ATEN_TALLA == null || ATEN_TALLA.isEmpty()) { varValiVacio++; ATEN_TALLA=""; }
                        String ATEN_IMC = (String) request.getParameter("tIMC");
                        if (ATEN_IMC == null || ATEN_IMC.isEmpty()) { varValiVacio++; ATEN_IMC=""; }
                        String ATEN_PCEFALICO = (String) request.getParameter("tPC");
                        if (ATEN_PCEFALICO == null || ATEN_PCEFALICO.isEmpty()) { varValiVacio++; ATEN_PCEFALICO=""; }
                        String ATEN_FC = (String) request.getParameter("tFC");
                        if (ATEN_FC == null || ATEN_FC.isEmpty()) { varValiVacio++; ATEN_FC=""; }
                        String ATEN_TEMP = (String) request.getParameter("tTe");
                        if (ATEN_TEMP == null || ATEN_TEMP.isEmpty()) { varValiVacio++; ATEN_TEMP=""; }
                        String ATEN_PA = (String) request.getParameter("tPA");
                        if (ATEN_PA == null || ATEN_PA.isEmpty()) { varValiVacio++; ATEN_PA=""; }
                        String ATEN_FRESP = (String) request.getParameter("tFR");
                        if (ATEN_FRESP == null || ATEN_FRESP.isEmpty()) { varValiVacio++; ATEN_FRESP=""; }
                        // PANEL /  MOTIVO DE CONSULTA 
                        String ATEN_MOTIVO_CONSULTA = (String) request.getParameter("tAMC");
                        if (ATEN_MOTIVO_CONSULTA == null || ATEN_MOTIVO_CONSULTA.isEmpty()) { varValiVacio++; ATEN_MOTIVO_CONSULTA=""; }
                        // PANEL /  EXPLORACION FISICA 
                        String ATEN_EXPLORACION_FISICA = (String) request.getParameter("tAEF");
                        if (ATEN_EXPLORACION_FISICA == null || ATEN_EXPLORACION_FISICA.isEmpty()) { varValiVacio++; ATEN_EXPLORACION_FISICA=""; }
                        // PANEL /  DIAGNOSTICO 
                        String ATEN_DIAGNOSTICO = (String) request.getParameter("tAD");
                        if (ATEN_DIAGNOSTICO == null || ATEN_DIAGNOSTICO.isEmpty()) { varValiVacio++; ATEN_DIAGNOSTICO=""; }
                        // PANEL /  TRATAMIENTO 
                        String ATEN_TRATAMIENTO = (String) request.getParameter("tAT");
                        if (ATEN_TRATAMIENTO == null || ATEN_TRATAMIENTO.isEmpty()) { varValiVacio++; ATEN_TRATAMIENTO=""; }
                        // PANEL /  RECETA 
                        String ATEN_RECETA = (String) request.getParameter("tAR");
                        if (ATEN_RECETA == null || ATEN_RECETA.isEmpty()) { varValiVacio++; ATEN_RECETA=""; }
                        // PANEL /  ESTUDIOS SOLICITADOS 
                        String ATEN_ESTUDIOS_SOLICITADOS = (String) request.getParameter("tAES");
                        if (ATEN_ESTUDIOS_SOLICITADOS == null || ATEN_ESTUDIOS_SOLICITADOS.isEmpty()) { varValiVacio++; ATEN_ESTUDIOS_SOLICITADOS=""; }
                        String ATEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
        //                String ATEN_ESTADO = (String) request.getParameter("cEs");
                        if (ATEN_ESTADO == null || ATEN_ESTADO.isEmpty()) { varValiVacio++; ATEN_ESTADO=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                        System.out.println("-       ATEN_PESO:     :"+ATEN_PESO);
                        System.out.println("-       ATEN_TALLA:    :"+ATEN_TALLA);
                        System.out.println("-       ATEN_IMC:      :"+ATEN_IMC);
                        System.out.println("-       ATEN_PCEFALICO:      :"+ATEN_PCEFALICO);
                        System.out.println("-       ATEN_FC:       :"+ATEN_FC);
                        System.out.println("-       ATEN_TEMP:     :"+ATEN_TEMP);
                        System.out.println("-       ATEN_PA:       :"+ATEN_PA);
                        System.out.println("-       ATEN_FRESP:    :"+ATEN_FRESP);
                        System.out.println("-       ATEN_MOTIVO_CONSULTA:      :"+ATEN_MOTIVO_CONSULTA);
                        System.out.println("-       ATEN_EXPLORACION_FISICA:   :"+ATEN_EXPLORACION_FISICA);
                        System.out.println("-       ATEN_DIAGNOSTICO:   :"+ATEN_DIAGNOSTICO);
                        System.out.println("-       ATEN_TRATAMIENTO:   :"+ATEN_TRATAMIENTO);
                        System.out.println("-       ATEN_RECETA:        :"+ATEN_RECETA);
                        System.out.println("-       ATEN_ESTUDIOS_SOLICITADOS: :"+ATEN_ESTUDIOS_SOLICITADOS);
                        System.out.println("-       ATEN_ESTADO:   :"+ATEN_ESTADO);
                        System.out.println("-       ATEN_IDCLINICA::"+ID_CLINICA);
                        System.out.println("-   -----------------------------------------------------------------------------");
                        System.out.println("___     ___var_vali_vacio:     :"+varValiVacio);
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
        /*OBS.: LE PONGO MAYOR A 99 PORQUE NO VA A LLEGAR A ESE MONTO Y LA VALIDACION NO SE VA A ACTIVAR, 
                YA QUE SE PUEDE GUARDAR CON CAMPOS VACIOS Y NO QUIERO COMENTAR ESTA VALIDACION */                
                        if (varValiVacio > 999) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                acceso = "add_atencion.htm";
                            } else {
                                acceso = return_pag;
                            }
                            
                        } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                            // CABECERA 
                            String OAP_IDATENCIONPAC = metodos.traerNewIdAtencion();
                            idAtencionPac = OAP_IDATENCIONPAC; // CARGO PARA LUEGO PASAR ESTA VARIABLE A LA SESION Y ASI SI SE VUELVE A REPETIR LA ULTIMA ACCION NO LE DEJE DUPLICAR LA FICHA DE ATENCION 
                            String OAP_IDAGENDAMIENTO = IDAGENDAMIENTO;
                            String OAP_ITEM_AGEND_DET = ITEM_AGEND;
                            String OAP_IDPACIENTE = IDPACIENTE;
                            String OAP_PESO = ATEN_PESO;
                            String OAP_TALLA = ATEN_TALLA;
                            String OAP_IMC = ATEN_IMC;
                            String OAP_P_CEFALICO = ATEN_PCEFALICO;
                            String OAP_FC = ATEN_FC;
                            String OAP_TEMP = ATEN_TEMP;
                            String OAP_PA = ATEN_PA;
                            String OAP_F_RESP = ATEN_FRESP;
                            String OAP_MOTIVO_CONSULTA = ATEN_MOTIVO_CONSULTA;
                            String OAP_EXPLORACION_FISICA = ATEN_EXPLORACION_FISICA;
                            String OAP_DIAGNOSTICO = ATEN_DIAGNOSTICO;
                            String OAP_TRATAMIENTO = ATEN_TRATAMIENTO;
                            String OAP_RECETA = ATEN_RECETA;
                            String OAP_ESTUDIOS_SOLICITADOS = ATEN_ESTUDIOS_SOLICITADOS;
                            String OAP_USU_ALTA = idPersona;
                            String OAP_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                            String OAP_ESTADO = ATEN_ESTADO;
                            String OAP_IDCLINICA = ID_CLINICA;
                            
                            // LISTA DE SERVICIOS -- 
                            List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                            if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                                LISTA_SERVICIOS = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                            }
                            int ITEM_LIST_SERV = 1;
                            if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                                ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                            }
                            
                            // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                            TIPO_MENSAJE = metodos.guardar_cab(LISTA_SERVICIOS, new BeanAtencionPaciente(OAP_IDATENCIONPAC, OAP_IDAGENDAMIENTO, OAP_ITEM_AGEND_DET, OAP_IDPACIENTE, OAP_PESO, OAP_TALLA, OAP_IMC, OAP_P_CEFALICO, OAP_FC, OAP_TEMP, OAP_PA, OAP_F_RESP, OAP_MOTIVO_CONSULTA, OAP_EXPLORACION_FISICA, OAP_DIAGNOSTICO, OAP_TRATAMIENTO, OAP_RECETA, OAP_ESTUDIOS_SOLICITADOS, OAP_USU_ALTA, OAP_FEC_ALTA, OAP_ESTADO, OAP_IDATENCIONPAC, OAP_TEMP, OAP_P_CEFALICO, OAP_PESO, OAP_ESTADO, OAP_USU_ALTA, OAP_FEC_ALTA, OAP_IDCLINICA));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
        //                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
        //                        if (idAgend == null || idAgend.isEmpty() || idAgend.equals("")) {
                                    if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                        acceso = "add_atencion.htm";
                                    } else {
                                        request.setAttribute("CP_MENSAJE", MENSAJE);
                                        request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                                        acceso = return_pag;
                                    }
//                                    acceso = "add_atencion.htm";
        //                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
        //                            acceso = "edit_agend.htm";
        //                        }
                            } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                                MENSAJE = "Se ha realizado correctamente la operación.";
//                                acceso = "atencion.htm";
                                if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                    acceso = "atencion.htm";
                                } else {
                                    request.setAttribute("CP_MENSAJE", "Se guardo correctamente la consulta");
                                    request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                                    acceso = return_pag;
                                }
                            }
                        } // end else validacion
                        // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                        if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                            // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                            sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                            sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
                            sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                            request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                            request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                            request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                            request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                            request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                            request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                            request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                            request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                            request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                            request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                            request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                            request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                            request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                            request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                        }
                        var_redireccionar = 1;
                        // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                        /*
                        _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                            ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                            PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                        */
                        sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac);
                        request.setAttribute("CFAP_MENSAJE", MENSAJE);
                        request.setAttribute("CFAP_TIPO_MENSAJE", TIPO_MENSAJE);
                    }
                    
                } else if(accion.equalsIgnoreCase("Modificar Atención") || accion.equalsIgnoreCase("Modificar") || accion.equalsIgnoreCase("Modificar Atencion")) {
                    System.out.println("------------------------__MODIFICAR__--------------------------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                    System.out.println("__ID_FICHA_ATENCION_PACIENTE :   :"+idAtencionPac);
                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                    System.out.println("__ID_FAGENDAMIENTO :   :"+IDAGENDAMIENTO);
                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                    System.out.println("__ITEM_AGEND_DETALL:   :"+ITEM_AGEND);
                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                    System.out.println("__ID_PACIENTE :   :"+IDPACIENTE);
                    String ID_CLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER"); // GUARDO EL IDCLINICA DONDE SE ESTA CARGANDO LA FICHA DE ATENCION 
                    System.out.println("__ID_CLINICA :   :"+ID_CLINICA);
                    
                    // VARIABLE QUE UTILIZO PARA SABER A DONDE TENDRA QUE RETORNAR YA QUE PUEDE SER REDIRECCIONADO DESDE "VER PACIENTE" 
                    String return_pag;
                    String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS"); // VARIABLE QUE UTILIZO PARA SABER A QUE PAGINA TENGO QUE RETORNAR LUEGO DE ANULAR LA FICHA, YA QUE SE PUEDE REDIRECCIONAR DESDE EL EXPEDIENTE DE UN PACIENTE O DESDE 
                    System.out.println("_   _CTRL__BTN_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                    if (BTN_VOLVER_ATRAS == null) {
                        return_pag = "";
                    } else if (BTN_VOLVER_ATRAS.equalsIgnoreCase("VER_PACIENTE")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
                        return_pag = "ver_paciente.htm";
                    } else {
                        return_pag = "";
                    }
                    
                    if (idAtencionPac.isEmpty() || idAtencionPac.equals("")) { // agregar if para poder identificar si el idatencionpaciente esta vacio asi para guardar y si esta cargado luego de guardar ya no me vuelva a guardar y crear nomas una segunda accion para poder modificar para no arriesgar el guardar 
                        System.out.println("____ID_ATENCION_PAC_VACIO_______________________INSERT_______________________");
                        var_redireccionar = 1;
                        if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                            acceso = "atencion.htm";
                        } else {
                            acceso = return_pag;
                        }
                        
                    } else {
                    System.out.println("------------    ---------------    __ACTUALIZAR _ IF __    ---------------    ---------------");    
                    System.out.println("____ID_ATENCION_PAC_CARGADO_____________________________________________");
                        // RECUPERAR LOS DATOS 
                        // PANEL /  PARAMETROS OPCIONALES 
                        String ATEN_PESO = (String) request.getParameter("tPe");
                        if (ATEN_PESO == null || ATEN_PESO.isEmpty()) { varValiVacio++; ATEN_PESO=""; }
                        String ATEN_TALLA = (String) request.getParameter("tTa");
                        if (ATEN_TALLA == null || ATEN_TALLA.isEmpty()) { varValiVacio++; ATEN_TALLA=""; }
                        String ATEN_IMC = (String) request.getParameter("tIMC");
                        if (ATEN_IMC == null || ATEN_IMC.isEmpty()) { varValiVacio++; ATEN_IMC=""; }
                        String ATEN_PCEFALICO = (String) request.getParameter("tPC");
                        if (ATEN_PCEFALICO == null || ATEN_PCEFALICO.isEmpty()) { varValiVacio++; ATEN_PCEFALICO=""; }
                        String ATEN_FC = (String) request.getParameter("tFC");
                        if (ATEN_FC == null || ATEN_FC.isEmpty()) { varValiVacio++; ATEN_FC=""; }
                        String ATEN_TEMP = (String) request.getParameter("tTe");
                        if (ATEN_TEMP == null || ATEN_TEMP.isEmpty()) { varValiVacio++; ATEN_TEMP=""; }
                        String ATEN_PA = (String) request.getParameter("tPA");
                        if (ATEN_PA == null || ATEN_PA.isEmpty()) { varValiVacio++; ATEN_PA=""; }
                        String ATEN_FRESP = (String) request.getParameter("tFR");
                        if (ATEN_FRESP == null || ATEN_FRESP.isEmpty()) { varValiVacio++; ATEN_FRESP=""; }
                        // PANEL /  MOTIVO DE CONSULTA 
                        String ATEN_MOTIVO_CONSULTA = (String) request.getParameter("tAMC");
                        if (ATEN_MOTIVO_CONSULTA == null || ATEN_MOTIVO_CONSULTA.isEmpty()) { varValiVacio++; ATEN_MOTIVO_CONSULTA=""; }
                        // PANEL /  EXPLORACION FISICA 
                        String ATEN_EXPLORACION_FISICA = (String) request.getParameter("tAEF");
                        if (ATEN_EXPLORACION_FISICA == null || ATEN_EXPLORACION_FISICA.isEmpty()) { varValiVacio++; ATEN_EXPLORACION_FISICA=""; }
                        // PANEL /  DIAGNOSTICO 
                        String ATEN_DIAGNOSTICO = (String) request.getParameter("tAD");
                        if (ATEN_DIAGNOSTICO == null || ATEN_DIAGNOSTICO.isEmpty()) { varValiVacio++; ATEN_DIAGNOSTICO=""; }
                        // PANEL /  TRATAMIENTO 
                        String ATEN_TRATAMIENTO = (String) request.getParameter("tAT");
                        if (ATEN_TRATAMIENTO == null || ATEN_TRATAMIENTO.isEmpty()) { varValiVacio++; ATEN_TRATAMIENTO=""; }
                        // PANEL /  RECETA 
                        String ATEN_RECETA = (String) request.getParameter("tAR");
                        if (ATEN_RECETA == null || ATEN_RECETA.isEmpty()) { varValiVacio++; ATEN_RECETA=""; }
                        // PANEL /  ESTUDIOS SOLICITADOS 
                        String ATEN_ESTUDIOS_SOLICITADOS = (String) request.getParameter("tAES");
                        if (ATEN_ESTUDIOS_SOLICITADOS == null || ATEN_ESTUDIOS_SOLICITADOS.isEmpty()) { varValiVacio++; ATEN_ESTUDIOS_SOLICITADOS=""; }
                        String ATEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
        //                String ATEN_ESTADO = (String) request.getParameter("cEs");
                        if (ATEN_ESTADO == null || ATEN_ESTADO.isEmpty()) { varValiVacio++; ATEN_ESTADO=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                        System.out.println("-       ATEN_PESO:     :"+ATEN_PESO);
                        System.out.println("-       ATEN_TALLA:    :"+ATEN_TALLA);
                        System.out.println("-       ATEN_IMC:      :"+ATEN_IMC);
                        System.out.println("-       ATEN_PCEFALICO:      :"+ATEN_PCEFALICO);
                        System.out.println("-       ATEN_FC:       :"+ATEN_FC);
                        System.out.println("-       ATEN_TEMP:     :"+ATEN_TEMP);
                        System.out.println("-       ATEN_PA:       :"+ATEN_PA);
                        System.out.println("-       ATEN_FRESP:    :"+ATEN_FRESP);
                        System.out.println("-       ATEN_MOTIVO_CONSULTA:      :"+ATEN_MOTIVO_CONSULTA);
                        System.out.println("-       ATEN_EXPLORACION_FISICA:   :"+ATEN_EXPLORACION_FISICA);
                        System.out.println("-       ATEN_DIAGNOSTICO:   :"+ATEN_DIAGNOSTICO);
                        System.out.println("-       ATEN_TRATAMIENTO:   :"+ATEN_TRATAMIENTO);
                        System.out.println("-       ATEN_RECETA:        :"+ATEN_RECETA);
                        System.out.println("-       ATEN_ESTUDIOS_SOLICITADOS: :"+ATEN_ESTUDIOS_SOLICITADOS);
                        System.out.println("-       ATEN_ESTADO:   :"+ATEN_ESTADO);
                        System.out.println("-       ID_CLINICA:    :"+ID_CLINICA);
                        System.out.println("-   -----------------------------------------------------------------------------");
                        System.out.println("___     ___var_vali_vacio:     :"+varValiVacio);
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
        /*OBS.: LE PONGO MAYOR A 99 PORQUE NO VA A LLEGAR A ESE MONTO Y LA VALIDACION NO SE VA A ACTIVAR, 
                YA QUE SE PUEDE GUARDAR CON CAMPOS VACIOS Y NO QUIERO COMENTAR ESTA VALIDACION */                
                        if (varValiVacio > 999) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                acceso = "add_atencion.htm";
                            } else {
                                acceso = return_pag;
                            }
                            
                        } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                            // CABECERA 
                            String OAP_IDATENCIONPAC = idAtencionPac;
                            String OAP_IDAGENDAMIENTO = IDAGENDAMIENTO;
                            String OAP_ITEM_AGEND_DET = ITEM_AGEND;
                            String OAP_IDPACIENTE = IDPACIENTE;
                            String OAP_PESO = ATEN_PESO;
                            String OAP_TALLA = ATEN_TALLA;
                            String OAP_IMC = ATEN_IMC;
                            String OAP_P_CEFALICO = ATEN_PCEFALICO;
                            String OAP_FC = ATEN_FC;
                            String OAP_TEMP = ATEN_TEMP;
                            String OAP_PA = ATEN_PA;
                            String OAP_F_RESP = ATEN_FRESP;
                            String OAP_MOTIVO_CONSULTA = ATEN_MOTIVO_CONSULTA;
                            String OAP_EXPLORACION_FISICA = ATEN_EXPLORACION_FISICA;
                            String OAP_DIAGNOSTICO = ATEN_DIAGNOSTICO;
                            String OAP_TRATAMIENTO = ATEN_TRATAMIENTO;
                            String OAP_RECETA = ATEN_RECETA;
                            String OAP_ESTUDIOS_SOLICITADOS = ATEN_ESTUDIOS_SOLICITADOS;
                            String OAP_USU_ALTA = idPersona;
                            String OAP_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                            String OAP_ESTADO = ATEN_ESTADO;
                            String OAP_IDCLINICA = ID_CLINICA;
                            
                            // LISTA DE SERVICIOS -- 
                            List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                            if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                                LISTA_SERVICIOS = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                            }
                            int ITEM_LIST_SERV = 1;
                            if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                                ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                            }
                            
                            // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                            TIPO_MENSAJE = metodos.modificar_cab(LISTA_SERVICIOS, new BeanAtencionPaciente(OAP_IDATENCIONPAC, OAP_IDAGENDAMIENTO, OAP_ITEM_AGEND_DET, OAP_IDPACIENTE, OAP_PESO, OAP_TALLA, OAP_IMC, OAP_P_CEFALICO, OAP_FC, OAP_TEMP, OAP_PA, OAP_F_RESP, OAP_MOTIVO_CONSULTA, OAP_EXPLORACION_FISICA, OAP_DIAGNOSTICO, OAP_TRATAMIENTO, OAP_RECETA, OAP_ESTUDIOS_SOLICITADOS, OAP_USU_ALTA, OAP_FEC_ALTA, OAP_ESTADO, OAP_IDATENCIONPAC, OAP_TEMP, OAP_P_CEFALICO, OAP_PESO, OAP_ESTADO, OAP_USU_ALTA, OAP_FEC_ALTA, OAP_IDCLINICA));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
        //                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
        //                        if (idAgend == null || idAgend.isEmpty() || idAgend.equals("")) {
                                    if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                        acceso = "add_atencion.htm";
                                    } else {
                                        request.setAttribute("CP_MENSAJE", MENSAJE);
                                        request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                                        acceso = return_pag;
                                    }
        //                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
        //                            acceso = "edit_agend.htm";
        //                        }
                            } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                                MENSAJE = "Se ha realizado correctamente la operación.";
                                if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                    acceso = "atencion.htm";
                                } else {
                                    request.setAttribute("CP_MENSAJE", "Se modifico correctamente la consulta");
                                    request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                                    acceso = return_pag;
                                }
                            }
                        } // end else validacion
                        // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                        if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                            // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                            sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                            sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
                            sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                            request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                            request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                            request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                            request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                            request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                            request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                            request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                            request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                            request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                            request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                            request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                            request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                            request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                            request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                        }
                        var_redireccionar = 1;
                        // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                        /*
                        _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                            ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                            PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                        */
                        sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac);
                        request.setAttribute("CFAP_MENSAJE", MENSAJE);
                        request.setAttribute("CFAP_TIPO_MENSAJE", TIPO_MENSAJE);
                    }
                    
                } else if(accion.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_fecha = (String) request.getParameter("tFF"); // tFF: txt filtro fecha 
                    System.out.println("_   FILTRO_TXT_FECHA :      "+filtro_fecha);
                    if (filtro_fecha == null || filtro_fecha.isEmpty()) {
                        filtro_fecha = modeloIniSes.traerFechaHoy();
                        System.out.println("_   _2__FILTRO_TXT_FECHA:     :"+filtro_fecha);
                    }
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    // COMBO MEDICO 
                    String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_IDMED = (String) request.getParameter("cbxAddNewMed"); // medico id 
                    if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty()) { PARAM_TXT_IDMED = ""; }
                    System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
                    System.out.println("_   MEDICO_ID   :   "+PARAM_TXT_IDMED);
                    // COMBO PACIENTE 
                    String checkPacFiltro = (String) request.getParameter("check_pac"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_IDPACIENTE = (String) request.getParameter("cbxAddNewPac"); // paciente id 
                    if (PARAM_TXT_IDPACIENTE == null || PARAM_TXT_IDPACIENTE.isEmpty()) { PARAM_TXT_IDPACIENTE = ""; }
                    System.out.println("_   CHECK_PACIENTE:   "+checkPacFiltro);
                    System.out.println("_   PACIENTE_ID   :   "+PARAM_TXT_IDPACIENTE);
                    // COMBO ESPECIALIDAD 
                    String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
                    String PARAM_TXT_IDESPE = (String) request.getParameter("cbxAddNewEspe"); // especialidad id 
                    if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty()) { PARAM_TXT_IDESPE = ""; }
                    System.out.println("_   CHECK_ESPECIALIDAD:   "+checkEspeFiltro);
                    System.out.println("_   ESPECIALIDAD_ID   :   "+PARAM_TXT_IDESPE);
                    // CONTROL DEL CHECK DE MEDICO 
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN MEDICO  
                    if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO  
                        System.out.println("_IF___CHECK__");
                        checkMedicoFiltro = "OFF";
                    } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        System.out.println("_ELSE___CHECK__");
                        PARAM_TXT_IDMED = "";
                    }
                    System.out.println("____CHECK_ID_MEDICO:  :"+PARAM_TXT_IDMED);
                    // CONTROL DEL CHECK DE PACIENTE
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN PACIENTE  
                    if (checkPacFiltro == null || checkPacFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPACIENTE  
                        checkPacFiltro = "OFF";
                    } else if (checkPacFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_IDPACIENTE = "";
                    }
                    // CONTROL DEL CHECK DE ESPECILIDAD
                    // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR LA ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESPECIALIDAD  
                    if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
                        checkEspeFiltro = "OFF";
                    } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
                        PARAM_TXT_IDESPE = "";
                    }
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanAtencionPaciente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_fecha, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDPACIENTE);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "atencion.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CFAP_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CFAP_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CFAP_TXT_FILTRAR_FECHA", filtro_fecha);
                    request.setAttribute("CFAP_LISTA_FILTRO", listaFiltro);
                    request.setAttribute("CFAP_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
                    request.setAttribute("CFAP_TXT_FIL_ID_MED", PARAM_TXT_IDMED);
                    request.setAttribute("CFAP_CHECK_FILTRAR_PAC_01", checkPacFiltro);
                    request.setAttribute("CFAP_TXT_FIL_ID_PACIENTE", PARAM_TXT_IDPACIENTE);
                    request.setAttribute("CFAP_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
                    request.setAttribute("CFAP_TXT_FIL_ID_ESPE", PARAM_TXT_IDESPE);
                    sesionDatosUsuario.setAttribute("CFAP_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
                } else if (accion.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "atencion.htm";
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CFAP_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if (accion.equalsIgnoreCase("Agregar Servicio")) {
                    System.out.println("---------------------__AGREGAR_SERVICIO__--------------------------");
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                    System.out.println("_   __ID_FICHA_ATEN_PAC: :"+idAtencionPac);
                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
                    // RECUPERAR LOS DATOS 
                    // PANEL /  PARAMETROS OPCIONALES 
                    String ATEN_PESO = (String) request.getParameter("tPe");
                    if (ATEN_PESO == null || ATEN_PESO.isEmpty()) { ATEN_PESO=""; }
                    String ATEN_TALLA = (String) request.getParameter("tTa");
                    if (ATEN_TALLA == null || ATEN_TALLA.isEmpty()) { ATEN_TALLA=""; }
                    String ATEN_IMC = (String) request.getParameter("tIMC");
                    if (ATEN_IMC == null || ATEN_IMC.isEmpty()) { ATEN_IMC=""; }
                    String ATEN_PCEFALICO = (String) request.getParameter("tPC");
                    if (ATEN_PCEFALICO == null || ATEN_PCEFALICO.isEmpty()) { ATEN_PCEFALICO=""; }
                    String ATEN_FC = (String) request.getParameter("tFC");
                    if (ATEN_FC == null || ATEN_FC.isEmpty()) { ATEN_FC=""; }
                    String ATEN_TEMP = (String) request.getParameter("tTe");
                    if (ATEN_TEMP == null || ATEN_TEMP.isEmpty()) { ATEN_TEMP=""; }
                    String ATEN_PA = (String) request.getParameter("tPA");
                    if (ATEN_PA == null || ATEN_PA.isEmpty()) { ATEN_PA=""; }
                    String ATEN_FRESP = (String) request.getParameter("tFR");
                    if (ATEN_FRESP == null || ATEN_FRESP.isEmpty()) { ATEN_FRESP=""; }
                    // PANEL /  MOTIVO DE CONSULTA 
                    String ATEN_MOTIVO_CONSULTA = (String) request.getParameter("tAMC");
                    if (ATEN_MOTIVO_CONSULTA == null || ATEN_MOTIVO_CONSULTA.isEmpty()) { ATEN_MOTIVO_CONSULTA=""; }
                    // PANEL /  EXPLORACION FISICA 
                    String ATEN_EXPLORACION_FISICA = (String) request.getParameter("tAEF");
                    if (ATEN_EXPLORACION_FISICA == null || ATEN_EXPLORACION_FISICA.isEmpty()) { ATEN_EXPLORACION_FISICA=""; }
                    // PANEL /  DIAGNOSTICO 
                    String ATEN_DIAGNOSTICO = (String) request.getParameter("tAD");
                    if (ATEN_DIAGNOSTICO == null || ATEN_DIAGNOSTICO.isEmpty()) { ATEN_DIAGNOSTICO=""; }
                    // PANEL /  TRATAMIENTO 
                    String ATEN_TRATAMIENTO = (String) request.getParameter("tAT");
                    if (ATEN_TRATAMIENTO == null || ATEN_TRATAMIENTO.isEmpty()) { ATEN_TRATAMIENTO=""; }
                    // PANEL /  RECETA 
                    String ATEN_RECETA = (String) request.getParameter("tAR");
                    if (ATEN_RECETA == null || ATEN_RECETA.isEmpty()) { ATEN_RECETA=""; }
                    // PANEL /  ESTUDIOS SOLICITADOS 
                    String ATEN_ESTUDIOS_SOLICITADOS = (String) request.getParameter("tAES");
                    if (ATEN_ESTUDIOS_SOLICITADOS == null || ATEN_ESTUDIOS_SOLICITADOS.isEmpty()) { ATEN_ESTUDIOS_SOLICITADOS=""; }
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                    System.out.println("-       ATEN_PESO:     :"+ATEN_PESO);
                    System.out.println("-       ATEN_TALLA:    :"+ATEN_TALLA);
                    System.out.println("-       ATEN_IMC:      :"+ATEN_IMC);
                    System.out.println("-       ATEN_PCEFALICO:      :"+ATEN_PCEFALICO);
                    System.out.println("-       ATEN_FC:       :"+ATEN_FC);
                    System.out.println("-       ATEN_TEMP:     :"+ATEN_TEMP);
                    System.out.println("-       ATEN_PA:       :"+ATEN_PA);
                    System.out.println("-       ATEN_FRESP:    :"+ATEN_FRESP);
                    System.out.println("-       ATEN_MOTIVO_CONSULTA:      :"+ATEN_MOTIVO_CONSULTA);
                    System.out.println("-       ATEN_EXPLORACION_FISICA:   :"+ATEN_EXPLORACION_FISICA);
                    System.out.println("-       ATEN_DIAGNOSTICO:   :"+ATEN_DIAGNOSTICO);
                    System.out.println("-       ATEN_TRATAMIENTO:   :"+ATEN_TRATAMIENTO);
                    System.out.println("-       ATEN_RECETA:        :"+ATEN_RECETA);
                    System.out.println("-       ATEN_ESTUDIOS_SOLICITADOS: :"+ATEN_ESTUDIOS_SOLICITADOS);
                    System.out.println("-   -----------------------------------------------------------------------------");
                    
                    // LISTA DE SERVICIOS -- 
                    List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                    if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                        LISTA_SERVICIOS = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                    }
                    int ITEM_LIST_SERV = 1;
                    if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                        ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                    }
                    
                    // RECUPERO LOS DATOS DEL COMBOBOX SERVICIO 
                    String idServicio = (String) request.getParameter("cbServ");
                    System.out.println("_   _ID_SERVICIO:     :"+idServicio);
                    BeanServicio beanServ = new BeanServicio();
                    beanServ = metodosServ.devolverDatosServicios(idServicio);
                    // AGREGO A LA LISTA 
                    BeanAtencionPaciente datos = new BeanAtencionPaciente();
                        datos.setOAPD_ITEM(String.valueOf(ITEM_LIST_SERV));
                        datos.setOAPD_IDSERVICIO(idServicio);
                        datos.setRHS_IDSERVICIO(idServicio);
                        datos.setRHS_DESC_SERVICIO(beanServ.getRHS_DESC_SERVICIO());
                        datos.setRHS_MONTO(beanServ.getRHS_MONTO());
                        datos.setOAPD_MONTO(beanServ.getRHS_MONTO());
                        datos.setOAPD_ESTADO("A");
                        datos.setRHS_ESTADO("A");
                    LISTA_SERVICIOS.add(datos);
                    ITEM_LIST_SERV = ITEM_LIST_SERV + 1;
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
                    // CAMPOS DE TEXTOS VISIBLES 
                    request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                    request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                    request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                    request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                    request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                    request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                    request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                    request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                    request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                    request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                    request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                    request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                    request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                    request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                    
                } else if (accion.equalsIgnoreCase("Eliminar Fila") || accion.equalsIgnoreCase("Eliminar") || accion.equalsIgnoreCase("X")) {
                    System.out.println("---------------------__ELIMINAR_SERVICIO__--------------------------");
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                    System.out.println("_   __ID_FICHA_ATEN_PAC: :"+idAtencionPac);
                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
                    // RECUPERAR LOS DATOS -- 
                    /*
                     * OBSERVACION:
                        RECUPERO LOS DATOS DE LOS CAMPOS DE TEXTO AUXILIARES YA QUE ESTE ACCION SE ENCUENTRA DENTRO DEL SEGUNDO FORM 
                        Y NO DENTRO DEL PRIMERO, ENTONCES EL SEGUNDO NO RECUPERA LOS DATOS DE CAMPOS DE TEXTO QUE ESTEN FUERA DE SU ETIQUETA Y POR ESO LE IMPLEMENTE LOS CAMPOS DE TEXTO AUXILIARES 
                    */
                    // PANEL /  PARAMETROS OPCIONALES 
                    String ATEN_PESO = (String) request.getParameter("tPeA");
                    if (ATEN_PESO == null || ATEN_PESO.isEmpty()) { ATEN_PESO=""; }
                    String ATEN_TALLA = (String) request.getParameter("tTaA");
                    if (ATEN_TALLA == null || ATEN_TALLA.isEmpty()) { ATEN_TALLA=""; }
                    String ATEN_IMC = (String) request.getParameter("tIMCA");
                    if (ATEN_IMC == null || ATEN_IMC.isEmpty()) { ATEN_IMC=""; }
                    String ATEN_PCEFALICO = (String) request.getParameter("tPCA");
                    if (ATEN_PCEFALICO == null || ATEN_PCEFALICO.isEmpty()) { ATEN_PCEFALICO=""; }
                    String ATEN_FC = (String) request.getParameter("tFCA");
                    if (ATEN_FC == null || ATEN_FC.isEmpty()) { ATEN_FC=""; }
                    String ATEN_TEMP = (String) request.getParameter("tTeA");
                    if (ATEN_TEMP == null || ATEN_TEMP.isEmpty()) { ATEN_TEMP=""; }
                    String ATEN_PA = (String) request.getParameter("tPAA");
                    if (ATEN_PA == null || ATEN_PA.isEmpty()) { ATEN_PA=""; }
                    String ATEN_FRESP = (String) request.getParameter("tFRA");
                    if (ATEN_FRESP == null || ATEN_FRESP.isEmpty()) { ATEN_FRESP=""; }
                    // PANEL /  MOTIVO DE CONSULTA 
                    String ATEN_MOTIVO_CONSULTA = (String) request.getParameter("tAMCA");
                    if (ATEN_MOTIVO_CONSULTA == null || ATEN_MOTIVO_CONSULTA.isEmpty()) { ATEN_MOTIVO_CONSULTA=""; }
                    // PANEL /  EXPLORACION FISICA 
                    String ATEN_EXPLORACION_FISICA = (String) request.getParameter("tAEFA");
                    if (ATEN_EXPLORACION_FISICA == null || ATEN_EXPLORACION_FISICA.isEmpty()) { ATEN_EXPLORACION_FISICA=""; }
                    // PANEL /  DIAGNOSTICO 
                    String ATEN_DIAGNOSTICO = (String) request.getParameter("tADA");
                    if (ATEN_DIAGNOSTICO == null || ATEN_DIAGNOSTICO.isEmpty()) { ATEN_DIAGNOSTICO=""; }
                    // PANEL /  TRATAMIENTO 
                    String ATEN_TRATAMIENTO = (String) request.getParameter("tATA");
                    if (ATEN_TRATAMIENTO == null || ATEN_TRATAMIENTO.isEmpty()) { ATEN_TRATAMIENTO=""; }
                    // PANEL /  RECETA 
                    String ATEN_RECETA = (String) request.getParameter("tARA");
                    if (ATEN_RECETA == null || ATEN_RECETA.isEmpty()) { ATEN_RECETA=""; }
                    // PANEL /  ESTUDIOS SOLICITADOS 
                    String ATEN_ESTUDIOS_SOLICITADOS = (String) request.getParameter("tAESA");
                    if (ATEN_ESTUDIOS_SOLICITADOS == null || ATEN_ESTUDIOS_SOLICITADOS.isEmpty()) { ATEN_ESTUDIOS_SOLICITADOS=""; }
                    
                    // IMPRESION DE DATOS -- 
                    System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                    System.out.println("-       ATEN_PESO:     :"+ATEN_PESO);
                    System.out.println("-       ATEN_TALLA:    :"+ATEN_TALLA);
                    System.out.println("-       ATEN_IMC:      :"+ATEN_IMC);
                    System.out.println("-       ATEN_PCEFALICO:      :"+ATEN_PCEFALICO);
                    System.out.println("-       ATEN_FC:       :"+ATEN_FC);
                    System.out.println("-       ATEN_TEMP:     :"+ATEN_TEMP);
                    System.out.println("-       ATEN_PA:       :"+ATEN_PA);
                    System.out.println("-       ATEN_FRESP:    :"+ATEN_FRESP);
                    System.out.println("-       ATEN_MOTIVO_CONSULTA:      :"+ATEN_MOTIVO_CONSULTA);
                    System.out.println("-       ATEN_EXPLORACION_FISICA:   :"+ATEN_EXPLORACION_FISICA);
                    System.out.println("-       ATEN_DIAGNOSTICO:   :"+ATEN_DIAGNOSTICO);
                    System.out.println("-       ATEN_TRATAMIENTO:   :"+ATEN_TRATAMIENTO);
                    System.out.println("-       ATEN_RECETA:        :"+ATEN_RECETA);
                    System.out.println("-       ATEN_ESTUDIOS_SOLICITADOS: :"+ATEN_ESTUDIOS_SOLICITADOS);
                    System.out.println("-   -----------------------------------------------------------------------------");
                    
                    // LISTA DE SERVICIOS -- 
                    List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                    if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                        LISTA_SERVICIOS = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                    }
                    int ITEM_LIST_SERV = 1;
                    if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                        ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                    }
                    
                    // RECUPERO LOS DATOS DEL SERVICIO A ELIMINAR -- 
                    String IDSERVICIO = (String) request.getParameter("tAISAD"); // tAISAD : TXT AUXILIAR ID SERVICIO ATENCION DETALLE  
                    int ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAIAD"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0   // tAIAD : TXT AUXILIAR ITEM ATENCION DETALLE  
                    String ITEM_ELIM_BD = (String) request.getParameter("tAIAD");
                    String MENSAJE = null, TIPO_MENSAJE = "";
                    System.out.println("----------__ITEM_A_ELIMINAR__-------------");
                    System.out.println("_   ____ID_SERVICIO:  "+IDSERVICIO);
                    System.out.println("_   __ITEM_ELIMINAR:  "+ITEM_ELIMINAR);
                    System.out.println("_   __ITEM_ELIMI_BD:  "+ITEM_ELIM_BD);
                    System.out.println("------------------------------------------");
                    
                    // ELIMINO DE LA LISTA -- 
                    List<BeanAtencionPaciente> CFAP_LISTA_NEW = new ArrayList<BeanAtencionPaciente>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        LISTA_SERVICIOS.remove(ITEM_ELIMINAR);
                        // VALIDACION PARA CONTROLAR SI ES QUE EL IDATENCIONPACIENTE SE ENCUENTRA CARGADO PARA PODER ELIMINAR (INACTIVAR) LA LINEA DEL DETALLE DE LA BASE   
                        if (!(idAtencionPac == null) || !(idAtencionPac.isEmpty())) {
                            BeanAtencionPaciente datos_eli = new BeanAtencionPaciente();
                                datos_eli.setOAPD_IDATENCIONPAC(idAtencionPac);
                                datos_eli.setOAPD_IDSERVICIO(IDSERVICIO);
                                datos_eli.setOAPD_ITEM(ITEM_ELIM_BD);
                                datos_eli.setOAPD_USU_ALTA(idPersona);
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
                        }
                        
                        // ORDENAR LOS ITEMS -- 
                        for (BeanAtencionPaciente LISTA_DETALLE : LISTA_SERVICIOS) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("___     _ITEM_NEW:      "+itemNew);
                            System.out.println(".");
                            System.out.println(".");
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanAtencionPaciente datosNew = new BeanAtencionPaciente();
                                datosNew.setOAPD_IDATENCIONPAC(LISTA_DETALLE.getOAPD_IDATENCIONPAC());
                                datosNew.setOAPD_ITEM(String.valueOf(itemNew));
                                datosNew.setOAPD_IDSERVICIO(LISTA_DETALLE.getOAPD_IDSERVICIO());
                                datosNew.setRHS_IDSERVICIO(LISTA_DETALLE.getRHS_IDSERVICIO());
                                datosNew.setRHS_DESC_SERVICIO(LISTA_DETALLE.getRHS_DESC_SERVICIO());
                                datosNew.setRHS_MONTO(LISTA_DETALLE.getRHS_MONTO());
                                datosNew.setOAPD_MONTO(LISTA_DETALLE.getOAPD_MONTO());
                                datosNew.setRHS_ESTADO(LISTA_DETALLE.getRHS_ESTADO());
                                datosNew.setOAPD_ESTADO(LISTA_DETALLE.getOAPD_ESTADO());
                            CFAP_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".");System.out.println(".");
                        itemNew = ITEM_LIST_SERV; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FICHA_ATENCION_PACIENTE_____ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");
                    } // end catch 
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", CFAP_LISTA_NEW);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(itemNew));
                    // CAMPOS DE TEXTOS VISIBLES 
                    request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                    request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                    request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                    request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                    request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                    request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                    request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                    request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                    request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                    request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                    request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                    request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                    request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                    request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                    
                } else if(accion.equalsIgnoreCase("Eliminar Consulta")) {
                    System.out.println("---------------------__ELIMINAR_CONSULTA__--------------------------");
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                    System.out.println("_   _1__ID_FICHA_ATEN_PAC: :"+idAtencionPac);
                    System.out.println("_   _2__ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    System.out.println("_   _3__ITEM_AGENDAMIEN:   :"+ITEM_AGEND);
                    System.out.println("_   _4__ID_PACIENTE:       :"+IDPACIENTE);
                    
                    // CONTROLO LA BANDERA PARA EVITAR QUE SE REPITA LA ACCION 
                    if (String.valueOf(sesionDatosUsuario.getAttribute("CFAP_BAND_REPEAT_ONE")).equals("1")) {
                        // RECUPERAR LOS DATOS -- 
                        // PANEL /  PARAMETROS OPCIONALES 
                        String ATEN_PESO = (String) request.getParameter("tPe");
                        if (ATEN_PESO == null || ATEN_PESO.isEmpty()) { ATEN_PESO=""; }
                        String ATEN_TALLA = (String) request.getParameter("tTa");
                        if (ATEN_TALLA == null || ATEN_TALLA.isEmpty()) { ATEN_TALLA=""; }
                        String ATEN_IMC = (String) request.getParameter("tIMC");
                        if (ATEN_IMC == null || ATEN_IMC.isEmpty()) { ATEN_IMC=""; }
                        String ATEN_PCEFALICO = (String) request.getParameter("tPC");
                        if (ATEN_PCEFALICO == null || ATEN_PCEFALICO.isEmpty()) { ATEN_PCEFALICO=""; }
                        String ATEN_FC = (String) request.getParameter("tFC");
                        if (ATEN_FC == null || ATEN_FC.isEmpty()) { ATEN_FC=""; }
                        String ATEN_TEMP = (String) request.getParameter("tTe");
                        if (ATEN_TEMP == null || ATEN_TEMP.isEmpty()) { ATEN_TEMP=""; }
                        String ATEN_PA = (String) request.getParameter("tPA");
                        if (ATEN_PA == null || ATEN_PA.isEmpty()) { ATEN_PA=""; }
                        String ATEN_FRESP = (String) request.getParameter("tFR");
                        if (ATEN_FRESP == null || ATEN_FRESP.isEmpty()) { ATEN_FRESP=""; }
                        // PANEL /  MOTIVO DE CONSULTA 
                        String ATEN_MOTIVO_CONSULTA = (String) request.getParameter("tAMC");
                        if (ATEN_MOTIVO_CONSULTA == null || ATEN_MOTIVO_CONSULTA.isEmpty()) { ATEN_MOTIVO_CONSULTA=""; }
                        // PANEL /  EXPLORACION FISICA 
                        String ATEN_EXPLORACION_FISICA = (String) request.getParameter("tAEF");
                        if (ATEN_EXPLORACION_FISICA == null || ATEN_EXPLORACION_FISICA.isEmpty()) { ATEN_EXPLORACION_FISICA=""; }
                        // PANEL /  DIAGNOSTICO 
                        String ATEN_DIAGNOSTICO = (String) request.getParameter("tAD");
                        if (ATEN_DIAGNOSTICO == null || ATEN_DIAGNOSTICO.isEmpty()) { ATEN_DIAGNOSTICO=""; }
                        // PANEL /  TRATAMIENTO 
                        String ATEN_TRATAMIENTO = (String) request.getParameter("tAT");
                        if (ATEN_TRATAMIENTO == null || ATEN_TRATAMIENTO.isEmpty()) { ATEN_TRATAMIENTO=""; }
                        // PANEL /  RECETA 
                        String ATEN_RECETA = (String) request.getParameter("tAR");
                        if (ATEN_RECETA == null || ATEN_RECETA.isEmpty()) { ATEN_RECETA=""; }
                        // PANEL /  ESTUDIOS SOLICITADOS 
                        String ATEN_ESTUDIOS_SOLICITADOS = (String) request.getParameter("tAES");
                        if (ATEN_ESTUDIOS_SOLICITADOS == null || ATEN_ESTUDIOS_SOLICITADOS.isEmpty()) { ATEN_ESTUDIOS_SOLICITADOS=""; }
                        
                        // IMPRESION DE DATOS -- 
                        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                        System.out.println("-       ATEN_PESO:     :"+ATEN_PESO);
                        System.out.println("-       ATEN_TALLA:    :"+ATEN_TALLA);
                        System.out.println("-       ATEN_IMC:      :"+ATEN_IMC);
                        System.out.println("-       ATEN_PCEFALICO:      :"+ATEN_PCEFALICO);
                        System.out.println("-       ATEN_FC:       :"+ATEN_FC);
                        System.out.println("-       ATEN_TEMP:     :"+ATEN_TEMP);
                        System.out.println("-       ATEN_PA:       :"+ATEN_PA);
                        System.out.println("-       ATEN_FRESP:    :"+ATEN_FRESP);
                        System.out.println("-       ATEN_MOTIVO_CONSULTA:      :"+ATEN_MOTIVO_CONSULTA);
                        System.out.println("-       ATEN_EXPLORACION_FISICA:   :"+ATEN_EXPLORACION_FISICA);
                        System.out.println("-       ATEN_DIAGNOSTICO:   :"+ATEN_DIAGNOSTICO);
                        System.out.println("-       ATEN_TRATAMIENTO:   :"+ATEN_TRATAMIENTO);
                        System.out.println("-       ATEN_RECETA:        :"+ATEN_RECETA);
                        System.out.println("-       ATEN_ESTUDIOS_SOLICITADOS: :"+ATEN_ESTUDIOS_SOLICITADOS);
                        System.out.println("-   -----------------------------------------------------------------------------");
                        
                        // LISTA DE SERVICIOS -- 
                        List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
                        if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                            LISTA_SERVICIOS = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                        }
                        int ITEM_LIST_SERV = 1;
                        if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                            ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                        }
                        
                        String MENSAJE = null, TIPO_MENSAJE = "";
                        // ELIMINO DE LA LISTA -- 
                        List<BeanAtencionPaciente> CFAP_LISTA_NEW = new ArrayList<BeanAtencionPaciente>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                        int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                        try {
                            // VALIDACION PARA CONTROLAR QUE EL IDATENCIONPACIENTE ESTE CARGADO PARA PODER ENVIAR EL UPDATE A LA TABLA 
                            if (!(idAtencionPac == null) || !(idAtencionPac.isEmpty())) {
                                BeanAtencionPaciente datos_eli = new BeanAtencionPaciente();
                                    datos_eli.setOAP_IDATENCIONPAC(idAtencionPac);
                                    datos_eli.setOAP_IDAGENDAMIENTO(IDAGENDAMIENTO);
                                    datos_eli.setOAP_ITEM_AGEND_DET(ITEM_AGEND);
                                    datos_eli.setOAP_USU_ALTA(idPersona);
                                // LLAMO AL METODO PARA ELIMINAR LA FILA DE LA BASE Y CARGO EL RESULTADO DE LA EJECUCION DE LA SENTENCIA PARA SABER QUE SI REALIZO CORRECTAMENTE O DIO ALGUN ERROR 
                                TIPO_MENSAJE = metodos.eliminar_cab(datos_eli);
                                if (TIPO_MENSAJE.equals("1")) {
                                    MENSAJE = "Se ha eliminado correctamente la Ficha.";
                                    // LE CAMBIO EL VALOR A LA BANDERA PARA EVITAR LA REPETICION DE LA ACCION 
                                    sesionDatosUsuario.setAttribute("CFAP_BAND_REPEAT_ONE", "0");
                                } else if(TIPO_MENSAJE.equals("2")) {
                                    MENSAJE = "Ocurrio un error mientras se ejecutaba la sentencia.\n Vuelva a intentarlo mas tarde.";
                                }
                                System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                                System.out.println("_   _CTRL_ELIMINAR:     MENSAJE("+TIPO_MENSAJE+"):      :"+MENSAJE);
                                System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                            }
                        } catch (Exception e) {
                            System.out.println(".");System.out.println(".");
                            itemNew = ITEM_LIST_SERV; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                            System.out.println("___FICHA_ATENCION_PACIENTE_____ERROR_POR_NRO_ITEM____");
                            Logger.getLogger(ControllerFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println(".");System.out.println(".");
                        } // end catch 
                        
                        var_redireccionar = 1;
                        String RETURN_PAG = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS"); // VARIABLE QUE UTILIZO PARA SABER A QUE PAGINA TENGO QUE RETORNAR LUEGO DE ANULAR LA FICHA, YA QUE SE PUEDE REDIRECCIONAR DESDE EL EXPEDIENTE DE UN PACIENTE O DESDE 
                        System.out.println("_   _CTRL__RETURN_PAG:   :"+RETURN_PAG);
                        if (RETURN_PAG.equalsIgnoreCase("VER_PACIENTE")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
                            request.setAttribute("CP_MENSAJE", MENSAJE);
                            request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                            acceso = "ver_paciente.htm";
                        } else {
                            acceso = "atencion.htm";
                            sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                            sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                            sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
                            sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                            sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", CFAP_LISTA_NEW);
                            sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(itemNew));
                            // CAMPOS DE TEXTOS VISIBLES 
                            request.setAttribute("CFAP_MENSAJE", MENSAJE);
                            request.setAttribute("CFAP_TIPO_MENSAJE", TIPO_MENSAJE);
                            request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
                            request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
                            request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
                            request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
                            request.setAttribute("CFAP_TXT_FC", ATEN_FC);
                            request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
                            request.setAttribute("CFAP_TXT_PA", ATEN_PA);
                            request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
                            request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
                            request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
                            request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
                            request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
                            request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
                            request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
                        }
                        
                    } else {
                        var_redireccionar = 1;
                        String RETURN_PAG = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS"); // VARIABLE QUE UTILIZO PARA SABER A QUE PAGINA TENGO QUE RETORNAR LUEGO DE ANULAR LA FICHA, YA QUE SE PUEDE REDIRECCIONAR DESDE EL EXPEDIENTE DE UN PACIENTE O DESDE 
                        System.out.println("_   _CTRL__RETURN_PAG:   :"+RETURN_PAG);
                        if (RETURN_PAG.equalsIgnoreCase("VER_PACIENTE")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
//                            request.setAttribute("CP_MENSAJE", MENSAJE);
//                            request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                            acceso = "ver_paciente.htm";
                        } else {
                            acceso = "atencion.htm";
                        }
                    } // end else-if de la bandera.- 
                    
                } else if(accion.equalsIgnoreCase("Imprimir Tratamiento")) {
                    System.out.println("---------------------__IMPRIMIR_TRATAMIENTO__--------------------------");
                    com.agend.modelo.pdf.PdfAtencion_Tratamiento pdf_tratamiento = new com.agend.modelo.pdf.PdfAtencion_Tratamiento();
                    pdf_tratamiento.imprimir_pdf(request, response);
                    var_no_redireccionar = 0;
                    
                } else if(accion.equalsIgnoreCase("Imprimir Receta")) {
                    System.out.println("---------------------__IMPRIMIR_RECETA__--------------------------");
                    com.agend.modelo.pdf.PdfAtencion_Receta pdf_receta = new com.agend.modelo.pdf.PdfAtencion_Receta();
                    pdf_receta.imprimir_pdf(request, response);
                    var_no_redireccionar = 0;
                    
                } else if(accion.equalsIgnoreCase("Imprimir Estudios Solicitados")) {
                    System.out.println("---------------------__IMPRIMIR_ESTUDIOS_SOLICITADOS_REALIZADOS__--------------------------");
                    com.agend.modelo.pdf.PdfAtencion_EstudiosSoli pdf_estudios = new com.agend.modelo.pdf.PdfAtencion_EstudiosSoli();
                    pdf_estudios.imprimir_pdf(request, response);
                    var_no_redireccionar = 0;
                }
                
                
            } else if (accionPag != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_    ___ACCION_PARA_LA_PAGINACION___    _");
                System.out.println(".");System.out.println(".");
                if (esNumero(accionPag) == true) {
                    System.out.println("---------------------__PAGINACION_NUMBER_: "+accionPag+" :__--------------------------");
                    // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", accionPag);
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")).equals("1")) {
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
                    String PAG_FICATEPAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_FICATEPAC_CANT_NRO_CLIC == null) {
                        PAG_FICATEPAC_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_FICATEPAC_CANT_NRO_CLIC);
                    String PAG_FICATEPAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_FICATEPAC_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_FICATEPAC_LISTA_ACTUAL);
                    
                    PAG_FICATEPAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_FICATEPAC_CANT_NRO_CLIC)+1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_FICATEPAC_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", PAG_FICATEPAC_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_FICATEPAC_CANT_NRO_CLIC)+1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")).equals("1")) {
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
                    String PAG_FICATEPAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_FICATEPAC_CANT_NRO_CLIC == null) {
                        PAG_FICATEPAC_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_FICATEPAC_CANT_NRO_CLIC);
                    String PAG_FICATEPAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_FICATEPAC_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_FICATEPAC_LISTA_ACTUAL);
                    
                    PAG_FICATEPAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_FICATEPAC_CANT_NRO_CLIC)-1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_FICATEPAC_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", PAG_FICATEPAC_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_FICATEPAC_CANT_NRO_CLIC)-1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CFAP_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodos);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                }
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona); // RECUPERO AL INICIO DEL CONTROLADOR Y PARA NO AGREGAR AL FINAL DE CADA EVENTO ENTONCES LE AGREGO AL TERMINAR LAS INSTANCIAS DE LOS EVENTOS / RECUPERO Y AUNQUE PUEDA LLEGAR A PASAR DE QUE EN ALGUN EVENTO NO SE UTILICE ESTA VARIABLE LA PASO PARA MANTENERLA EN SESION Y NO SE LLEGUE A BORRAR POR INACTIVIDAD DE LA VARIABLE 
        } catch (Exception e) {
            System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println("-----------------ERROR-FICHA_ATENCION_PACIENTE--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("------------------------------------------------------------------");
        }
        
    // OBSERVACION: 
    // UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if(var_no_redireccionar == 0) {
            System.out.println("_____SE_REDIRECCIONA_DESDE_OTRO_CONTROLADOR_____");
        } else {
            if (var_redireccionar == 0) {
                response.sendRedirect(acceso);
            } else {
                RequestDispatcher vista = request.getRequestDispatcher(acceso);
                vista.forward(request, response);
            }
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
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFichaAtencionPac metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_fecha = (String) request.getParameter("tFF"); // tFF: txt filtro fecha 
        System.out.println("_   FILTRO_TXT_FECHA :      "+filtro_fecha);
        if (filtro_fecha == null || filtro_fecha.isEmpty()) {
            filtro_fecha = modeloIniSes.traerFechaHoy();
            System.out.println("_   _2__FILTRO_TXT_FECHA:     :"+filtro_fecha);
        }
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
        // COMBO MEDICO 
        String checkMedicoFiltro = (String) request.getParameter("check_med"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR EL MEDICO QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_IDMED = (String) request.getParameter("cbxAddNewMed"); // medico id 
        if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty()) { PARAM_TXT_IDMED = ""; }
        System.out.println("_   CHECK_MEDICO:   "+checkMedicoFiltro);
        System.out.println("_   MEDICO_ID   :   "+PARAM_TXT_IDMED);
        // COMBO PACIENTE 
        String checkPacFiltro = (String) request.getParameter("check_pac"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR PACIENTE QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_IDPACIENTE = (String) request.getParameter("cbxAddNewPac"); // paciente id 
        if (PARAM_TXT_IDPACIENTE == null || PARAM_TXT_IDPACIENTE.isEmpty()) { PARAM_TXT_IDPACIENTE = ""; }
        System.out.println("_   CHECK_PACIENTE:   "+checkPacFiltro);
        System.out.println("_   PACIENTE_ID   :   "+PARAM_TXT_IDPACIENTE);
        // COMBO ESPECIALIDAD 
        String checkEspeFiltro = (String) request.getParameter("check_espe"); // CHECK QUE ME INDICA SI DEBO DE FILTRAR POR LA ESPECIALIDAD QUE SE ENCUENTRA EN EL COMBO O NO 
        String PARAM_TXT_IDESPE = (String) request.getParameter("cbxAddNewEspe"); // especialidad id 
        if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty()) { PARAM_TXT_IDESPE = ""; }
        System.out.println("_   CHECK_ESPECIALIDAD:   "+checkEspeFiltro);
        System.out.println("_   ESPECIALIDAD_ID   :   "+PARAM_TXT_IDESPE);
        // CONTROL DEL CHECK DE MEDICO 
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR EL MEDICO, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DEL MEDICO PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN MEDICO  
        if (checkMedicoFiltro == null || checkMedicoFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL MEDICO Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDMEDICO  
            checkMedicoFiltro = "OFF";
        } else if (checkMedicoFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_IDMED = "";
        }
        // CONTROL DEL CHECK DE PACIENTE
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR PACIENTE, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA PACIENTE PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN PACIENTE  
        if (checkPacFiltro == null || checkPacFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DEL PACIENTE Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDPACIENTE  
            checkPacFiltro = "OFF";
        } else if (checkPacFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_IDPACIENTE = "";
        }
        // CONTROL DEL CHECK DE ESPECILIDAD
        // CONTROLO SI ES QUE SE SELECCIONO EL CHECK PARA FILTRAR POR LA ESPECIALIDAD, SI SE SELECCIONO, ENTONCES LIMPIARIA LOS TXT DE LA ESPECIALIDAD PARA PASARLOS VACIO Y QUE EL METODO DETECTE QUE NO SE ESTA FILTRANDO POR NINGUN ESPECIALIDAD  
        if (checkEspeFiltro == null || checkEspeFiltro.equalsIgnoreCase("OFF")) { // SI EL CHECK SE ENCUENTRA DESMARCADO ENTONCES RECUPERO LOS DATOS DE LA ESPECIALIDAD Y LE DEJO SU VALOR EN LA VARIBLE PARA QUE EL METODO FILTRE POR ESE IDESPECIALIDAD  
            checkEspeFiltro = "OFF";
        } else if (checkEspeFiltro.equalsIgnoreCase("ON")) { // SI EL CHECK SE ENCUENTRA MARCADO ENTONCES 
            PARAM_TXT_IDESPE = "";
        }

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanAtencionPaciente> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_fecha, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDPACIENTE);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "atencion.htm";
//        var_redireccionar = 1;
        request.setAttribute("CFAP_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CFAP_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CFAP_TXT_FILTRAR_FECHA", filtro_fecha);
        request.setAttribute("CFAP_LISTA_FILTRO", listaFiltro);
        request.setAttribute("CFAP_CHECK_FILTRAR_MED_01", checkMedicoFiltro);
        request.setAttribute("CFAP_TXT_FIL_ID_MED", PARAM_TXT_IDMED);
        request.setAttribute("CFAP_CHECK_FILTRAR_PAC_01", checkPacFiltro);
        request.setAttribute("CFAP_TXT_FIL_ID_PACIENTE", PARAM_TXT_IDPACIENTE);
        request.setAttribute("CFAP_CHECK_FILTRAR_ESPE_01", checkEspeFiltro);
        request.setAttribute("CFAP_TXT_FIL_ID_ESPE", PARAM_TXT_IDESPE);
        sesionDatosUsuario.setAttribute("CFAP_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    
    public String ver_ficha_atencion_paciente(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFichaAtencionPac metodos, String idAtencionPac, int var_redireccionar, String acceso) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("---------------------__VER_ATENCION__--------------------------");
        idAtencionPac = (String) request.getParameter("tIAP");
        System.out.println("_   __ID_ATENCION_PAC:   :"+idAtencionPac);
        String IDAGENDAMIENTO = (String) request.getParameter("tIA");
        System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
        String ITEM_AGEN_DET = (String) request.getParameter("tAID");
        System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
        String IDPACIENTE = (String) request.getParameter("tIP");
        System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
        System.out.println("----------------------------------------------------------------");
        
        // CARGAR LISTA SERVICIOS 
        List<BeanAtencionPaciente> LISTA_SERV = new ArrayList<>();
        int ITEM_LIST_SERV = 0;
        
        // CARGAR_GRILLA_DETALLE 
        List<BeanAtencionPaciente> LISTA_SERVICIOS = new ArrayList<>();
        LISTA_SERVICIOS = metodos.traer_datos(idAtencionPac, sesionDatosUsuario);
        Iterator<BeanAtencionPaciente> iListDet = LISTA_SERVICIOS.iterator();
        BeanAtencionPaciente datos = new BeanAtencionPaciente();
        String ATEN_PESO="", ATEN_TALLA="", ATEN_IMC="", ATEN_PCEFALICO="", ATEN_FC="", ATEN_TEMP="", ATEN_PA="", ATEN_FRESP="", ATEN_MOTIVO_CONSULTA="", ATEN_EXPLORACION_FISICA="", ATEN_DIAGNOSTICO="", ATEN_TRATAMIENTO="", ATEN_RECETA="", ATEN_ESTUDIOS_SOLICITADOS="";
        while(iListDet.hasNext()) {
            datos = iListDet.next();
            System.out.println("____WHILE____");
            ATEN_PESO = datos.getOAP_PESO();
            ATEN_TALLA = datos.getOAP_TALLA();
            ATEN_IMC = datos.getOAP_IMC();
            ATEN_PCEFALICO = datos.getOAP_P_CEFALICO();
            ATEN_FC = datos.getOAP_FC();
            ATEN_TEMP = datos.getOAP_TEMP();
            ATEN_PA = datos.getOAP_PA();
            ATEN_FRESP = datos.getOAP_F_RESP();
            ATEN_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA();
            ATEN_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA();
            ATEN_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO();
            ATEN_TRATAMIENTO = datos.getOAP_TRATAMIENTO();
            ATEN_RECETA = datos.getOAP_RECETA();
            ATEN_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS();
            System.out.println("_   ITEM_DETALLE:     :"+datos.getOAPD_ITEM());
            // AGREGO A LA LISTA 
            if (!(datos.getOAPD_ITEM() == null)) {
                System.out.println("__      if_____");
                BeanAtencionPaciente datosServ = new BeanAtencionPaciente();
                    datosServ.setOAPD_IDATENCIONPAC(datos.getOAPD_IDATENCIONPAC());
                    datosServ.setOAPD_ITEM(datos.getOAPD_ITEM());
                    datosServ.setOAPD_IDSERVICIO(datos.getOAPD_IDSERVICIO());
                    datosServ.setRHS_IDSERVICIO(datos.getOAPD_IDSERVICIO());
                    datosServ.setRHS_DESC_SERVICIO("");
                    datosServ.setRHS_MONTO(datos.getOAPD_MONTO());
                    datosServ.setOAPD_MONTO(datos.getOAPD_MONTO());
                    datosServ.setOAPD_ESTADO(datos.getOAPD_ESTADO());
                    datosServ.setRHS_ESTADO(datos.getOAPD_ESTADO());
                LISTA_SERV.add(datosServ);
                ITEM_LIST_SERV = ITEM_LIST_SERV + 1;
            } else {
                System.out.println("__      else____");
            }
        }
        
        // CARGAR_ITEM_DETALLE
//                    String ITEM_LIST_SERV = String.valueOf(LISTA_SERVICIOS.size()+1);
//                    System.out.println("_   _NRO_ITEM_LISTA:     :"+ITEM_LIST_SERV);
        
        var_redireccionar = 1;
        acceso = "ver_atencion.htm";
        sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR QUE RECUPERO PARA QUE NO PIENSE QUE ESTOY GUARDANDO UNA NUEVA FICHA SINO SEPA A CUAL FICHA ACTUALIZAR 
        sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
        sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
        sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
        sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
        // CAMPOS DE TEXTO 
        request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
        request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
        request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
        request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
        request.setAttribute("CFAP_TXT_FC", ATEN_FC);
        request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
        request.setAttribute("CFAP_TXT_PA", ATEN_PA);
        request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
        request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
        request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
        request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
        request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
        request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
        request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
        return acceso;
    }
    
    
    
} // END CLASS  