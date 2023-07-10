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
import com.agend.javaBean.BeanServicio;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelRef_Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerRefServicio", urlPatterns="/CRSSrv") // Controller Referencial Servicio Servlet 
public class ControllerRef_Servicio extends HttpServlet {
    
    
    ModelInicioSesion modeloIniSes = new ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/ref_servicios")
    public ModelAndView ref_servicios(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER_REF__SERVICIO--------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _1_CRS__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _1_CRS__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuRef_Servicio(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagRef_Servicio", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagRef_Servicio", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/datos_servicio")
    public ModelAndView datos_servicios(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__2__---------CONTROLLER_REF__SERVICIO--------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _2_CRS__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _2_CRS__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuRef_Servicio(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagRef_ServicioDatos", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagRef_ServicioDatos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        // CARGO LOS DATOS A LA SESION PARA RECUPERAR DESDE EL JSP 
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA);
        
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
        ModelRef_Servicio metodos = new ModelRef_Servicio();
        
        int var_redireccionar = 0;
        String acceso = "ref_servicios.htm";
        String accion = request.getParameter("accion");
        String accionPag = request.getParameter("accionPag");
        String idPersona, idServicio;
        
        try {
            System.out.println("__ACCION:       "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("_doPost__ID_PERSONA:    "+idPersona);
            
            if(accion != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_        ___ACCION___        _");
                System.out.println(".");System.out.println(".");
                if (accion.equalsIgnoreCase("Add Servicio")) { // BOTON PARA AÑADIR UN NUEVO PRODUCTO 
                    System.out.println("---------------------__AÑADIR_SERVICIO__--------------------------");
                    idServicio = "";
                    var_redireccionar = 0;
                    acceso = "datos_servicio.htm";
                    sesionDatosUsuario.setAttribute("ID_SERVICIO", idServicio); // LE PASO EL VALOR VACIO, YA QUE QUIERO CREAR UN NUEVO REGISTRO Y SI NO PASO VACIO ESTE VALOR PUEDE MANTENERSE EL VALOR DE OTRO REGISTRO RECIEN CREADO O MODIFICADO 
                    
                } else if (accion.equalsIgnoreCase("Editar")) { // BOTON PARA EDITAR O MODIFICAR LOS DATOS DEL REGISTRO 
                    System.out.println("---------------------__EDITAR__--------------------------");
                    idServicio = (String) request.getParameter("tI"); // tI : txt Id 
                    System.out.println("_doPost__ID_SERVICIO:   "+idServicio);
                    
                    var_redireccionar = 1;
                    acceso = "datos_servicio.htm";
                    sesionDatosUsuario.setAttribute("ID_SERVICIO", idServicio);
                    
                } else if (accion.equalsIgnoreCase("GRABAR")) {
                    System.out.println("---------------------__GUARDAR__--------------------------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idServicio = (String) sesionDatosUsuario.getAttribute("ID_SERVICIO");
                    System.out.println("__ID_SERVICIO:   "+idServicio);
                    
                    if (idServicio.isEmpty() || idServicio == null || idServicio.equals("")) { // CONTROLO SI EL ID DE LA SESION ESTA CARGADO, SI ES ASI ENTONCES NO GUARDARE YA QUE ESO SIGNIFICARIA QUE SE GUARDO RECIEN NOMAS UN REGISTRO Y AL GUARDAR TODO CARGUE EL ID PARA PODER EVITAR QUE AL RECARGAR LA PAGINA SE VUELVA A REALIAR LA OPERACION DE GUARDAR CON EL MISMO REGISTRO 
                        System.out.println("____ID_SERVICIO_VACIO_______________________INSERT_______________________");
                        idServicio = ""; // COMO SE ENCUENTRA NULO O VACIO, ENTONCES LE CARGO UN VALOR VACIO PARA EVITAR ERRORES POR SI TENGA VALOR NULO 
                        // RECUPERO LOS DATOS PARA GUARDAR 
                        String DESC_SERVICIO = (String) request.getParameter("tDC");
                        if (DESC_SERVICIO == null || DESC_SERVICIO.isEmpty()) { varValiVacio++; DESC_SERVICIO=""; }
                        String MONTO = (String) request.getParameter("tM");
                        if (MONTO == null || MONTO.isEmpty()) { varValiVacio++; MONTO=""; }
                        String ESTADO = (String) request.getParameter("cE");
                        if (ESTADO == null || ESTADO.isEmpty()) { varValiVacio++; ESTADO=""; }
                        String IVA = (String) request.getParameter("cI");
                        if (IVA == null || IVA.isEmpty()) { varValiVacio++; IVA=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("--------------__DATOS_GUARDAR__--------------");
                        System.out.println("-     DESC_SERVICIO:  :"+DESC_SERVICIO);
                        System.out.println("-     MONTO :         :"+MONTO);
                        System.out.println("-     ESTADO:         :"+ESTADO);
                        System.out.println("-        IVA:         :"+IVA);
                        System.out.println("---------------------------------------------");
                        
                        // VALIDACIONES 
                        String MENSAJE, TIPO_MENSAJE; // 1 : exito / 2 : error 
                        if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR QUE NO DEJO CAMPOS VACIOS 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            
                        } else if(metodos.ctrlExisteServicio(DESC_SERVICIO, idServicio) == true) { // VALIDACION PARA CONTROLAR QUE NO EXISTA EL MISMO REGISTRO 
                            MENSAJE = "El servicio ya existe.";
                            TIPO_MENSAJE = "2";
                            
                        } else if(Integer.parseInt((MONTO.replace(",",".").replace(".",""))) < 0) { // VALIDACION PARA CONTROLAR QUE NO EXISTA EL MISMO REGISTRO 
                            MENSAJE = "El monto no puede ser menor a cero.";
                            TIPO_MENSAJE = "2";
                            
                        } else { // SI NO HA ENTRADO EN NINGUN IF ENTONCES NO SALTO NINGUNA VALIDACION Y PODEMOS GUARDAR NOMAS YA 
                            // CARGO LAS VARIABLES QUE PASARE AL CONSTRUCTOR PARA GUARDAR 
                            idServicio = metodos.traerNewIdServicio();
                            String RHS_IDSERVICIO = idServicio;
                            String RHS_DESCRIPCION = DESC_SERVICIO;
                            String RHS_MONTO = MONTO.replace(".","").replace(",",".");
                            String RHS_ESTADO = ESTADO;
                            String RHS_IVA = IVA;
    //                        String OC_USU_ALTA = idPersona;
    //                        String OC_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();
                            
                            TIPO_MENSAJE = metodos.guardar(new BeanServicio(RHS_IDSERVICIO, RHS_DESCRIPCION, RHS_MONTO, RHS_ESTADO, RHS_IVA));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL PRODUCTO 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                                acceso = "datos_servicio.htm";
                            } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                                MENSAJE = "Se ha grabado correctamente el servicio.";
    //                            MENSAJE = "Se ha realizado correctamente la operación.";
                                acceso = "ref_servicios.htm";
                            }
                        }
                        
                        if (TIPO_MENSAJE.equals("2")) {
                            acceso = "datos_servicio.htm";
                            // DEVUELVO LOS DATOS AL JSP 
                            request.setAttribute("CRS_SERVICIO_DESC", DESC_SERVICIO);
                            request.setAttribute("CRS_MONTO", MONTO);
                            request.setAttribute("CRS_ESTADO", ESTADO);
                            request.setAttribute("CRS_IVA", IVA);
                        }
                        var_redireccionar = 1;
                        sesionDatosUsuario.setAttribute("ID_SERVICIO", idServicio);
                        request.setAttribute("CRS_MENSAJE", MENSAJE);
                        request.setAttribute("CRS_TIPO_MENSAJE", TIPO_MENSAJE);
                    } else { // EN CASO DE QUE ESTE CARGADO EL IDPRODUCTO ENTONCES SE GUARDO RECIEN NOMAS EL REGISTRO 
                        System.out.println("____ID_SERVICIO_CARGADO_____________________________________________");
                        System.out.println("___ID_SERVICIO:  "+idServicio);
                        var_redireccionar = 1;
                        acceso = "ref_servicios.htm";
                    }
                    
                } else if (accion.equalsIgnoreCase("MODIFICAR")) {
                    System.out.println("---------------------__MODIFICAR__--------------------------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idServicio = (String) sesionDatosUsuario.getAttribute("ID_SERVICIO");
                    System.out.println("__ID_SERVICIO:   "+idServicio);
                    
                    if (idServicio.isEmpty() || idServicio == null || idServicio.equals("")) { // CONTROLO SI EL ID DE LA SESION ESTA CARGADO, SI ESTA VACIO ES PORQUE YO VACIE YA QUE HACE UN MOMENTO EL USUARIO MODIFICO EL REGISTRO, YA QUE SI SE INGRESA AL BOTON DE MODIFICAR ESTA VARIABLE DE LA SESION DEBE DE ESTAR CARGADA LA UNICA FORMA DE QUE ESTE VACIA ES QUE YO LO HAYA CARGADO VACIO AL TERMINAR DE MODIFICAR EL REGISTRO Y LA RAZON DE QUE VUELVE A ENTRAR A ESTE METODO ES PORQUE RECARGO LA PAGINA 
                        System.out.println("____ID_SERVICIO_VACIO_______________________INSERT_______________________");
                        var_redireccionar = 1;
                        acceso = "ref_servicios.htm";
                    } else { // EN CASO DE QUE ESTE CARGADO EL ID DE LA SESION, ENTONCES EDITARIA EL REGISTRO 
                        System.out.println("____ID_SERVICIO_CARGADO_____________________________________________");
                        System.out.println("___ID_SERVICIO:  "+idServicio);
                        // RECUPERO LOS DATOS PARA GUARDAR 
                        String DESC_SERVICIO = (String) request.getParameter("tDC");
                        if (DESC_SERVICIO == null || DESC_SERVICIO.isEmpty()) { varValiVacio++; DESC_SERVICIO=""; }
                        String MONTO = (String) request.getParameter("tM");
                        if (MONTO == null || MONTO.isEmpty()) { varValiVacio++; MONTO=""; }
                        String ESTADO = (String) request.getParameter("cE");
                        if (ESTADO == null || ESTADO.isEmpty()) { varValiVacio++; ESTADO=""; }
                        String IVA = (String) request.getParameter("cI");
                        if (IVA == null || IVA.isEmpty()) { varValiVacio++; IVA=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("--------------__DATOS_MODIFICAR__--------------");
                        System.out.println("-     DESC_SERVICIO:  :"+DESC_SERVICIO);
                        System.out.println("-             MONTO:  :"+MONTO);
                        System.out.println("-            ESTADO:  :"+ESTADO);
                        System.out.println("-               IVA:  :"+IVA);
                        System.out.println("-----------------------------------------------");
                        
                        // VALIDACIONES 
                        String MENSAJE, TIPO_MENSAJE; // 1 : exito / 2 : error 
                        if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR QUE NO DEJO CAMPOS VACIOS 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            
                        } else if(metodos.ctrlExisteServicio(DESC_SERVICIO, idServicio) == true) { // VALIDACION PARA CONTROLAR QUE NO EXISTA EL MISMO REGISTRO 
                            MENSAJE = "El servicio ya existe.";
                            TIPO_MENSAJE = "2";
                            
                        } else if(Integer.parseInt((MONTO.replace(".",""))) < 0) { // VALIDACION PARA CONTROLAR QUE NO EXISTA EL MISMO REGISTRO 
                            MENSAJE = "El monto no puede ser menor a cero.";
                            TIPO_MENSAJE = "2";
                            
                        } else { // SI NO HA ENTRADO EN NINGUN IF ENTONCES NO SALTO NINGUNA VALIDACION Y PODEMOS GUARDAR NOMAS YA 
                            // CARGO LAS VARIABLES QUE PASARE AL CONSTRUCTOR PARA GUARDAR 
                            String RHS_IDSERVICIO = (idServicio);
                            String RHS_DESC = DESC_SERVICIO;
                            String RHS_MONTO = MONTO.replace(".", "").replace(",",".");
                            String RHS_ESTADO = ESTADO;
                            String RHS_IVA = IVA;
    //                        String RHE_USU_ALTA = idPersona;
    //                        String RHE_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();

                            TIPO_MENSAJE = metodos.modificar(new BeanServicio(RHS_IDSERVICIO, RHS_DESC, RHS_MONTO, RHS_ESTADO, RHS_IVA));
                            if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL PRODUCTO 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                                acceso = "datos_servicio.htm";
                            } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                                MENSAJE = "Se actualizo correctamente el servicio.";
                                acceso = "ref_servicios.htm";
                            }
                        }
                        // CONTROLO EL TIPO MENSAJE EN CASO DE QUE HAYA ACTIVADO UNA VALIDACION ANTES DE ENTRAR AL ELSE 
                        if (TIPO_MENSAJE.equals("2")) {
                            acceso = "datos_servicio.htm";
                            sesionDatosUsuario.setAttribute("ID_SERVICIO", idServicio); // SI DIO ALGUN ERROR O SE ACTIVO ALGUNA VALIDACION ENTONCES SI NO VOY A VACIAR ESTE DATO YA QUE POSIBLEMENTE VUELVA A INTENTAR MODIFICAR 
                            // DEVUELVO LOS DATOS AL JSP 
                            request.setAttribute("CRS_SERVICIO_DESC", DESC_SERVICIO);
                            request.setAttribute("CRS_MONTO", MONTO);
                            request.setAttribute("CRS_ESTADO", ESTADO);
                        } else if (TIPO_MENSAJE.equals("1")) {
                            sesionDatosUsuario.setAttribute("ID_SERVICIO", ""); // EN CASO DE QUE SE HAYA MODIFICADO CORRECTAMENTE ENTONCES DEJO VACIO ESTO PARA QUE EN CASO DE QUE SE RECARGUE LA PAGINA NO PUEDA VOLVER A MODIFICAR 
                        }
                        var_redireccionar = 1;
                        request.setAttribute("CRS_MENSAJE", MENSAJE);
                        request.setAttribute("CRS_TIPO_MENSAJE", TIPO_MENSAJE);
                    }
                    
                } else if (accion.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                    
                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanServicio> listaFiltro = new ArrayList<>();
                    listaFiltro = metodos.filtrar_paginacion(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
                    
                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "ref_servicios.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CRS_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CRS_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CRS_LISTA_FILTRO", listaFiltro);
                    sesionDatosUsuario.setAttribute("CRS_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                    
                } else if(accion.equalsIgnoreCase("Eliminar")) {
                    System.out.println("---------------------__ELIMINAR__--------------------------");
                    idServicio = (String) request.getParameter("tI"); // tI : txt Id 
                    System.out.println("_doPost__ID_SERVICIO:   "+idServicio);
                    
                    var_redireccionar = 1;
                    acceso = "datos_servicio.htm";
                    sesionDatosUsuario.setAttribute("ID_SERVICIO", idServicio);
                    request.setAttribute("REFSERV_BTN_ELIMINAR", "1");
                    
                } else if(accion.equalsIgnoreCase("EliminarSeg")) {
                    System.out.println("---------------------__ELIMINAR_SEGURO__--------------------------");
                    idServicio = (String) request.getParameter("tI"); // tI : txt Id 
                    System.out.println("_doPost__ID_SERVICIO:   "+idServicio);
                    String MENSAJE = null, TIPO_MENSAJE = "";
                    
                    // VALIDACION PARA CONTROLAR QUE TENGA EXISTENCIA EL ID QUE DESEA ELIMINAR DE LA TABLA (ESTO HAGO PARA EVITAR REPETIR LA ACCION EN CASO DE QUE SE RECARGUE LA PAGINA JUNTO A LA ULTIMA ACCION) 
                    if (metodos.ctrlExisteIdServicio(idServicio) == true) {
                        // VALIDACION PARA CONTROLAR SI ES QUE SE ESTA USANDO EN OTRA TABLA EL IDCLINICA PARA ALGO 
                        if (metodos.ctrlUsoIdServicio(idServicio) == true) { // SI DEVUELVE TRUE, LE MUESTRO UN MENSAJE DE QUE NO PUEDE SER ELIMINADO ESA CLINICA 
                            TIPO_MENSAJE = "2";
                            MENSAJE = "No se puede eliminar el servicio porque otra página esta utilizando este dato.";
                        } else { // SI DEVUELVE FALSO EL METODO ENTONCES LE DEJO ELIMINAR YA QUE NO TIENE USO EN NINGUNA OTRA TABLA DE MANERA ACTIVA 
                            BeanServicio datoIdEliminar = new BeanServicio();
                            datoIdEliminar.setRHS_IDSERVICIO(idServicio);
                            
                            TIPO_MENSAJE = metodos.eliminar(datoIdEliminar);
                            if (TIPO_MENSAJE.equals("1")) { // EXITO 
                                MENSAJE = "Se ha eliminado correctamente el registro.";
                            } else if (TIPO_MENSAJE.equals("2")) { // ERROR 
                                MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                            }
                        }
                    } else {
                        // 
                    }
                    
                    var_redireccionar = 1;
                    acceso = "ref_servicios.htm";
                    sesionDatosUsuario.setAttribute("ID_SERVICIO", "");
                    request.setAttribute("CRS_MENSAJE", MENSAJE);
                    request.setAttribute("CRS_TIPO_MENSAJE", TIPO_MENSAJE);
                    
                    /*
                     * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                        Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                        UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                    */
                } else if (accion.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "ref_servicios.htm";
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CRS_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                }
                
                
            } else if (accionPag != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_    ___ACCION_PARA_LA_PAGINACION___    _");
                System.out.println(".");System.out.println(".");
                if (esNumero(accionPag) == true) {
                    System.out.println("---------------------__PAGINACION_NUMBER_: "+accionPag+" :__--------------------------");
                    // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_LISTA_ACTUAL", accionPag);
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")).equals("1")) {
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
                    String PAG_REFSERV_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_REFSERV_CANT_NRO_CLIC == null) {
                        PAG_REFSERV_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_REFSERV_CANT_NRO_CLIC);
                    String PAG_REFSERV_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_REFSERV_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_REFSERV_LISTA_ACTUAL);
                    
                    PAG_REFSERV_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_REFSERV_CANT_NRO_CLIC)+1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_REFSERV_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_LISTA_ACTUAL", PAG_REFSERV_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_REFSERV_CANT_NRO_CLIC)+1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")).equals("1")) {
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
                    String PAG_REFSERV_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_REFSERV_CANT_NRO_CLIC == null) {
                        PAG_REFSERV_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_REFSERV_CANT_NRO_CLIC);
                    String PAG_REFSERV_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_REFSERV_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_REFSERV_LISTA_ACTUAL);
                    
                    PAG_REFSERV_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_REFSERV_CANT_NRO_CLIC)-1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_REFSERV_LISTA_ACTUAL);
                    
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_LISTA_ACTUAL", PAG_REFSERV_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_REFSERV_CANT_NRO_CLIC)-1));
                    
                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CRS_BAND_FILTRO")).equals("1")) {
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
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-------------------__ERROR-REF-SERVICIO__----------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("---------------------------------------------------------------");
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
    
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelRef_Servicio metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanServicio> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar_paginacion(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "ref_servicios.htm";
//        var_redireccionar = 1;
        request.setAttribute("CRS_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CRS_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CRS_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("CRS_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
} // END CLASS 