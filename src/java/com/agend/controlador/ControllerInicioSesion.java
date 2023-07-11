/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import com.agend.modelo.ModelPerfil;
import com.agend.modelo.ModelRef_Clinica;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;
import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanInicioSesion;
import com.agend.javaBean.BeanPerfil;
import com.agend.javaBean.BeanUsuario;
import java.io.IOException;
import java.util.*;
import java.util.logging.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="controllerInicioSesion", urlPatterns="/ISSrv") // nombre de como lo tenia antes:inicioSesionController (asi lo usaba en el dispatcher servlet y lo cambie para evitar un error de ambiguedad que saltaba sobre las urls del controlador)
public class ControllerInicioSesion extends HttpServlet {
    
    
    // VARIABLES PARA ALMACENAR MENSAJES O BANDERAS PARA EVITAR REPETIRLAS O VOLVER A CREARLAS EN CADA INSTANCIA QUE LAS NECESITE PARA QUE NO SE VUELVAN REDUNDANTES 
    public static int varValidacionesEstado = 0; // VARIABLE QUE CAMBIO SU VALOR EN LOS CONTROLADORES DE OTRAS PAGINAS CONTROLANDO SI SE CAMBIO EL VALOR DEL CAMPO DE ESTADO_MIGRAR DE LA TABLA DE USUARIO, ESTA VARIABLE LA CONTROLO AQUI PARA SABER SI LE MUESTRO EL MENSAJE YA QUE EN OTROS CONTROLADORES SE CAMBIA EL VALOR Y TAMBIEN SE LE DIRIGE A LA PAGINA DE INICIO SESION // OBSERVACION: LE RESTABLESCO EL VALOR PERO EN LAS PAGINAS NOMAS, CUANDO RECUPERO EL MENSAJE, YA QUE SI CAMBIO ACA EN EL CONTROLADOR CUANDO INTENTE RECUPERAR EN LA PAGINA NO PODRE POR EL CAMBIO DE VALOR 
    public static String varValidacionMensaje = ""; // VARIABLE QUE UTILIZO PARA CARGAR EL MENSAJE QUE AGARRO EN LA PAGINA DE inicioSesion.jsp PARA MOSTRAR, EN CASO DE QUE SE ACTIVE LA BANDERA QUE SE CARGA AL CONTROLAR EL ESTADO_MIGRAR 
    public static String varMigrarMensajeError = "Intente ingresar mas tarde, ahora mismo estamos Actualizando la Base de Datos."; // VARIABLE QUE ALMACENA EL MENSAJE - ESTA VARIABLE SOLO UTILIZARE PARA CARGAR LA OTRA VARIABLE GLOBAL QUE ES TOMADA Y MOSTRADA EN EL PANEL DE INICIAR SESION - A LA VARIABLE GLOBAL LE PUDE HABER COLOCADO EL MENSAJE PERO CREO QUE ES MEJOR COLOCARLA EN UNA VARIABLE PARA ASI SI NECESITO CAMBIAR ALGO, SOLO CAMBIARIA UNA VEZ Y NO EN TODOS LOS CONTROLADORES QUE USEN LA VARIABLE 
    public static String varLogeoInactivoMensajeError = "Sesion cerrada o expirada, vuelva a ingresar."; // VARIABLE QUE ALMACENA EL MENSAJE - ESTA VARIABLE SOLO UTILIZARE PARA CARGAR LA OTRA VARIABLE GLOBAL QUE SE LA PASARE A LOS CONTROLADORES/PAGINAS PARA MOSTRAR EN LOS PANELES CUANDO SE ACTIVE LA VALIDACION QUE CONTROLA SI EL USUARIO CUENTA CON UN LOGEO ACTIVO/ABIERTO 
    
    
    
    
    @RequestMapping("/inicio_sesion")
    public ModelAndView inicio_sesionMAV(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        String pagina_web = "inicio_sesion";
        
        System.out.println("_____________INVALIDAR_SESION______________");
        HttpSession sesionDatosUsuario = request.getSession();
        if (sesionDatosUsuario.getAttribute("IDPERSONA") == null || sesionDatosUsuario.getAttribute("IDPERSONA").equals("null") || sesionDatosUsuario.getAttribute("IDPERSONA").equals("")) {
            System.out.println("_   __IF____ID_PERSONA_NULL______");
            sesionDatosUsuario.invalidate();
        } else {
            System.out.println("_   __ELSE___ID_PERSONA_SESION______");
            System.out.println(".       __ISC____RESPUESTA_DIFERENTE_NULL___");
            // -------------------------------------------------------------
            ModelInicioSesion metodosIniSes = new ModelInicioSesion();
            ModelPerfil metodosPerfil = new ModelPerfil();
            //-------
            String idDocente = ((String)sesionDatosUsuario.getAttribute("IDPERSONA"));
            System.out.println(".       __ISC___ ID_PERSONA:        "+idDocente);
            String idUsuario = (String) sesionDatosUsuario.getAttribute("IDUSUARIO");
            System.out.println(".       __ISC___ ID_USUARIO:        "+idUsuario);
            
//            String idPerfilUsuario = (String) sesionDatosUsuario.getAttribute("idPerfilUsuarioLogeo");
//            if (idPerfilUsuario == null || idPerfilUsuario.equals("null") || idPerfilUsuario.isEmpty()) { // RECUPERO DE LA SESION EL IDPERFIL Y EN CASO DE QUE SE PIERDA EL DATO Y ME DEVUELVA VACIO O NULO ENTONCES AHI OBTENDRIA EL IDPERFIL DE LA BASE POR MEDIO DEL IDUSUARIO DEL IDDOCENTE 
//                idPerfilUsuario = metodosIniSes.traerIdPerfilUsu(idUsuario);
//            }
//            System.out.println(".       __ISC___ idPerfil:       "+idPerfilUsuario);
            
            // CONTROLO EL IDMENU DEL IDPERFIL DEL USUARIO PARA SABER A QUE MENU HAY QUE REDIRECCIONAR 
            BeanPerfil beanPerfil = new BeanPerfil();
            beanPerfil = metodosPerfil.traerDatosPerfil(idUsuario, beanPerfil);
            String idPerfilUsuario = beanPerfil.getSP_IDPERFIL();
            System.out.println(".       _CIS__idPerfil:       :"+idPerfilUsuario);
            String descPerfilUsuario = beanPerfil.getSP_NOMBRE();
            System.out.println(".       _CIS__descPerfil:     :"+descPerfilUsuario);
            String idMenuPerfil = beanPerfil.getSP_IDMENU();
            System.out.println(".       _CIS__idMenuPerfil:   :"+idMenuPerfil);
            
            if(idMenuPerfil.equals("0") || idMenuPerfil.equals("1")){ // CONTROLO EL CAMPO IDMENU QUE RECUPERO DE LA TABLA DE PERFIL QUE ME DIRA QUE MENU CORRESPONDE AL PERFIL DEL USUARIO, SI FUESE DIFERENTE A CERO (ROOT) Y 1 (ADMINISTRADOR) ENTONCES REDIRECCIONO AL USUARIO AL MENU DE CLIENTES PERO SI FUESE 0 Y 1 ENTONCES LO REDIRIJO AL MENU DE ADMINISTRATIVO 
                pagina_web = "menu_principal";
//                pagina_web = "menu_adm"; // MENU ADMINISTRATIVO PARA LOS FUNCIONARIOS / ADMINISTRADORES 
            } else {
                pagina_web = "menu_principal"; // MENU PARA LOS USUARIO / CLIENTES 
            }
            // -------------------------------------------------------------
        } // end else 
        
        System.out.println("PAGINA_MAV:     "+pagina_web);
        
        mav.setViewName(pagina_web);
        return mav;
    }
    
    
    
    @RequestMapping("/registrar")
    public ModelAndView registrarMAV(HttpServletRequest request) { // PAGINA PARA REGISTRAR NUEVO USUARIO 
        ModelAndView mav = new ModelAndView();
        String pagina_web = "is_registrar";
        
        
        mav.setViewName(pagina_web);
        return mav;
    }
    
    
    @RequestMapping("/recover_pass")
    public ModelAndView recover_passwordMAV(HttpServletRequest request) { // PAGINA PARA RECUPERAR CONTRASEÑA 
        ModelAndView mav = new ModelAndView();
        String pagina_web = "is_recover_pass";
        
        
        mav.setViewName(pagina_web);
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
        
        ModelInicioSesion metodos = new ModelInicioSesion();
        ModelPerfil metodosPerfil = new ModelPerfil();
        ModelRef_Clinica metodosRefCli = new ModelRef_Clinica();
        ModelPersona metodosPers = new ModelPersona();
//        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int varRedireccionar = 0, varError = 0;
        String acceso = "inicio_sesion.htm";
        String accion = request.getParameter("accion");
        String accionRecoverPass = request.getParameter("accionRP"); // accion de la pagina de recuperar contraseña 
        String respuesta = "";
        String login_user="", login_pass="", idPersona, idUsuario, nameUsuario, descPerfilUsuario, idMenuPerfil, userNombre="", userApellido="", userCinro="", userIdClinica="", userDescClinica;
        
        List<BeanUsuario> ctrlUsuario = null; // uso para el cierre y lo comente en el "Ingresar"
        BeanUsuario datosUsuario = new BeanUsuario(); // uso para el cierre y lo comente en el "Ingresar"
        BeanPersona datosPersona = new BeanPersona();
        
        try {
            System.out.println("__ACCION:       "+accion);
            System.out.println("__ACCION_RECOVER_PASSWORD:       "+accionRecoverPass);
            
            if (accion != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println(".   _________  ACCION  ________");
                System.out.println(".");System.out.println(".");System.out.println(".");
                if (accion.equalsIgnoreCase("Ingresar")) {
                    System.out.println("---------------------__INGRESAR__---------------------------");

                    if (metodos.controlLogeoUsuarioActivo(request) == false) { // SI RETORNA FALSE ES PORQUE EL USUARIO NO TIENE UN LOGEO ACTIVO 
                        System.out.println("____IF____LOGEO_INACTIVO______________");
                        // RECUPERAMOS EL USUARIO Y PASSWORD QUE CARGO 
                        login_user = (String) request.getParameter("txtU");
                        login_pass = (String) request.getParameter("txtP");
                        System.out.println("login_user:     "+login_user);
                        System.out.println("login_pass:     "+login_pass);
                        
                        // hago un control del usuario - en donde el metodo retornara en caso de que exista usuario el IDALUMNO 
//                        ctrlUsuario = metodos.controlUsuario(new BeanInicioSesion(login_user, login_pass));
                        datosPersona = metodos.controlUsuarioPer(new BeanInicioSesion(login_user, login_pass));

                        // VALIDACIONES 
                        if ((login_user.equalsIgnoreCase("") || login_user == null) || (login_pass.equalsIgnoreCase("") || login_pass == null)) { // CONTROLAMOS QUE NO SE ENCUENTREN VACIOS LOS CAMPOS 
                            respuesta = "No puede dejar campos Vacios";
                            acceso = "inicio_sesion.htm";
                            varError = 1;
                            varRedireccionar = 1;
                            
                        } else if (datosPersona == null) { // CONTROLAMOS EXISTA
//                        } else if (ctrlUsuario == null) { // CONTROLAMOS EXISTA
                            String PASS = metodos.pass_clave();
                            respuesta = "El Usuario o Contraseña son Incorrectos. P("+PASS+")";
                            acceso = "inicio_sesion.htm";
                            varError = 1;
                            varRedireccionar = 1;
                            
                        } else if (metodos.ctrlEstadoMigrar(login_user, login_pass) == true) { // CONTROLAMOS EL ESTADO DE MIGRAR EN LA BASE 
                            respuesta = varMigrarMensajeError;
                            acceso = "inicio_sesion.htm";
                            varError = 1;
                            varRedireccionar = 1;
                            
                        } else {
                            // SI ENTRA EN EL ELSE ENTONCES NO DIO ERROR Y PASARIA A RECUPERAR LOS DATOS DEL USUARIO 
//                            Iterator<BeanUsuario> iterUsu = ctrlUsuario.iterator();
//                            while(iterUsu.hasNext()) {
//                                datosUsuario = iterUsu.next();
//                            }
//                            idPersona = datosUsuario.getSU_IDPERSONA();
//                            idUsuario = datosUsuario.getSU_IDUSUARIO();
//                            nameUsuario = datosUsuario.getSU_USUARIO();
                            idPersona = datosPersona.getSU_IDPERSONA();
                            idUsuario = datosPersona.getSU_IDUSUARIO();
                            nameUsuario = datosPersona.getSU_USUARIO();
                            
                            boolean webValor = metodos.updateWebLogeo(1, idPersona, idUsuario); // actualizamos la columna de "WEB" para saber que esta activo // valor 1 para saber que se logeo 
                            if (webValor == true) {
                                System.out.println("[+]-1----Se ha guardado-----*-_TRUE_-*--------");
                            } else {
                                System.out.println("[-]-1----No se ha podido guardar------_FALSE_---------");
                            }
                            
                            // UNA VEZ RECUPERADOS LOS DATOS PRIMORDIALES DEL USUARIO PASARIA A CONTROLAR EL IDMENU DEL IDPERFIL DEL USUARIO PARA SABER A QUE MENU HAY QUE REDIRECCIONAR 
//                            BeanPerfil beanPerfil = new BeanPerfil();
//                            beanPerfil = metodosPerfil.traerDatosPerfil(idUsuario, beanPerfil);
                            String idPerfilUsuario = datosPersona.getSP_IDPERFIL();
//                            String idPerfilUsuario = beanPerfil.getSP_IDPERFIL();
                            System.out.println("idPerfil:       "+idPerfilUsuario);
                            descPerfilUsuario = datosPersona.getSP_NOMBRE();
//                            descPerfilUsuario = beanPerfil.getSP_NOMBRE();
                            System.out.println("descPerfil:     "+descPerfilUsuario);
                            idMenuPerfil = datosPersona.getSP_IDMENU();
//                            idMenuPerfil = beanPerfil.getSP_IDMENU();
                            System.out.println("idMenuPerfil:   "+idMenuPerfil);
                            // CONTROLO EL CAMPO IDMENU QUE RECUPERO DE LA TABLA DE PERFIL QUE ME DIRA QUE MENU CORRESPONDE AL PERFIL DEL USUARIO, SI FUESE DIFERENTE A CERO (ROOT) Y 1 (ADMINISTRADOR) ENTONCES REDIRECCIONO AL USUARIO AL MENU DE CLIENTES PERO SI FUESE 0 Y 1 ENTONCES LO REDIRIJO AL MENU DE ADMINISTRATIVO 
                            if(idMenuPerfil.equals("0") || idMenuPerfil.equals("1")){
    //                        if(idPerfilUsuario.equals("0") || idPerfilUsuario.equals("1")){ // CONTROLO EL IDPERFIL PARA ABRIR EL MENU ADMINISTRATIVO O EL MENU ACADEMICO 
                                acceso = "menu.htm";
    //                            acceso = "menu_adm.htm"; // MENU ADMINISTRATIVO PARA LOS FUNCIONARIOS / ADMINISTRADORES 
                            } else {
                                acceso = "menu.htm"; // MENU PARA LOS USUARIO / CLIENTES (menu_principal)
                            }
                            varRedireccionar = 1; // es necesario que habra la vista por medio del servlet para asi poder recibir el atributo de userIS 

                            // DESPUES DE SABER EL MENU QUE TENGO QUE CARGAR ENTONCES CARGARE LOS DATOS DEL USUARIO 
//                            BeanPersona beanPersona = new BeanPersona();
//                            beanPersona = metodosPers.datosBasicosPersona(beanPersona, idPersona);
//                            userNombre = beanPersona.getRP_NOMBRE();
//                            userApellido = beanPersona.getRP_APELLIDO();
//                            userCinro = beanPersona.getRP_CINRO();
//                            userIdClinica = beanPersona.getRP_IDCLINICA();
                            userNombre = datosPersona.getRP_NOMBRE();
                            userApellido = datosPersona.getRP_APELLIDO();
                            userCinro = datosPersona.getRP_CINRO();
                            userIdClinica = datosPersona.getRP_IDCLINICA();
//                            userDescClinica = metodosRefCli.traerDescClinica(beanPersona.getRP_IDCLINICA());
                            userDescClinica = datosPersona.getRP_AUX_DESC_CLINICA();

                            System.out.println("-----------_CARGAR_DATOS_A_LA_SESION_---------------");
                            System.out.println("ID_PERSONA:   :"+idPersona);
                            System.out.println("ID_USUARIO:   :"+idUsuario);
                            System.out.println("NAME_USUARIO: :"+nameUsuario);
                            System.out.println("ID_PERFIL:    :"+idPerfilUsuario);
                            System.out.println("IDCLINICA:    :"+userIdClinica);
                            System.out.println("DESCCLINICA:  :"+userDescClinica);
                            System.out.println("DESC_PERFIL:  :"+descPerfilUsuario);
                            System.out.println("NOMBRE:    :"+userNombre);
                            System.out.println("APELLIDO:  :"+userApellido);
                            System.out.println("CINRO:     :"+userCinro);
//                            Cookie newCookie = new Cookie("ID_USER_LOGIN",idUsuario);
//                            Cookie idPersonaCoo = new Cookie("IDPERSONA_LOGIN",idPersona);
//                            Cookie IDPERFIL_LOGIN = new Cookie("IDPERFIL_LOGIN",idPerfilUsuario);
//                            newCookie.setMaxAge(60*60); // 1 hora 
//                            newCookie.setHttpOnly(true);
//                            newCookie.setDomain("zulmiullon.com");
//                            newCookie.setPath("/agend");
//                            response.addCookie(newCookie);
//                            //
//                            idPersonaCoo.setMaxAge(60*60); // 1 hora 
//                            idPersonaCoo.setHttpOnly(true);
//                            idPersonaCoo.setDomain("zulmiullon.com");
//                            idPersonaCoo.setPath("/agend");
//                            response.addCookie(idPersonaCoo);
//                            //
//                            IDPERFIL_LOGIN.setMaxAge(60*60); // 1 hora 
//                            IDPERFIL_LOGIN.setHttpOnly(true);
//                            IDPERFIL_LOGIN.setDomain("zulmiullon.com");
//                            IDPERFIL_LOGIN.setPath("/agend");
//                            response.addCookie(IDPERFIL_LOGIN);

//                            Cookie newCookie = new Cookie("JCOOKIEIDU",datosPersona.getSU_IDUSUARIO());
//                            newCookie.setMaxAge(60*60); // 1 hora 
//                            newCookie.setHttpOnly(true);
//                            newCookie.setDomain("zulmiullon.com");
//                            newCookie.setPath("/agend");
//                            response.addCookie(newCookie);
                            // 
//                            metodos.setCookie(response, "JSESSIONIDU", datosPersona.getSU_IDUSUARIO(), (60*60), "zulmiullon.com", "/agend");
                            // datos normales que cargaba a la session.-
//                            response = metodos.setCookie(response, "IDPERSONA", datosPersona.getSU_IDPERSONA());
//                            response = metodos.setCookie(response, "IDUSUARIO", datosPersona.getSU_IDUSUARIO());
//                            response = metodos.setCookie(response, "USUARIO", datosPersona.getSU_USUARIO());
//                            response = metodos.setCookie(response, "IDPERFIL", datosPersona.getSP_IDPERFIL());
//                            response = metodos.setCookie(response, "ID_CLINICA_USER", datosPersona.getRP_IDCLINICA());
//                            response = metodos.setCookie(response, "DESC_CLINICA_USER", datosPersona.getRP_AUX_DESC_CLINICA());
//                            response = metodos.setCookie(response, "DESC_PERFIL", datosPersona.getSP_NOMBRE());
//                            response = metodos.setCookie(response, "RP_NOMBRE", datosPersona.getRP_NOMBRE());
//                            response = metodos.setCookie(response, "RP_APELLIDO", datosPersona.getRP_APELLIDO());
//                            response = metodos.setCookie(response, "RP_CINRO", datosPersona.getRP_CINRO());
                            // ID-USUARIO PARA TODOS LOS FORMS.- [OBS] :LO COMENTO PORQUE NO ME GUARDA LA COOKIE Y AL INTENTAR RECUPERAR DESDE EL CONTROLADOR DE MENU ME DA NULL PORQUE NO EXISTE.- 
//                            sesionDatosUsuario.setAttribute("JIDUSER", idUsuario);
//                            response = metodos.setCookie(response, "JIDUSER", datosPersona.getSU_IDUSUARIO());
                            System.out.println("----------------------------------------------------");
                            
                            // invalido la sesion por si haya alguna 
                            request.getSession(true).invalidate();
                            // CREO UNA SESION 
                            HttpSession sesionDatosUsuario = request.getSession(true);
                            
                            // AGREGO LAS VARIABLES QUE RECUPERARÉ PARA UTILIZAR EN ALGUNAS OPERACIONES EN LAS PAGINAS WEB 
                            request.setAttribute("respuesta", respuesta); // LA PASO POR MEDIO DEL REQUEST Y NO DE LA SESION PORQUE NO QUIERO MANTENER ESTE DATO EN LA SESION (YA SÉ QUE PUEDO LIMPIAR LUEGO DE UTILIZARLA PERO NO QUIERO COLOCAR LINEAS DE CODIGO PARA ESO CUANDO SE QUE EL REQUEST SE LIMPIARA O SE ELIMINARA AL REALIZAR OTRA ACCION) 
                            // PASO LOS DATOS COMUNES DEL USUARIO A LA SESION 
                            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
                            sesionDatosUsuario.setAttribute("IDUSUARIO", idUsuario);
                            sesionDatosUsuario.setAttribute("USUARIO", nameUsuario);
                            sesionDatosUsuario.setAttribute("IDPERFIL", idPerfilUsuario);
                            sesionDatosUsuario.setAttribute("ID_CLINICA_USER", userIdClinica);
                            sesionDatosUsuario.setAttribute("DESC_CLINICA_USER", userDescClinica);
                            sesionDatosUsuario.setAttribute("DESC_PERFIL", descPerfilUsuario);
                            sesionDatosUsuario.setAttribute("RP_NOMBRE", userNombre);
                            sesionDatosUsuario.setAttribute("RP_APELLIDO", userApellido);
                            sesionDatosUsuario.setAttribute("RP_CINRO", userCinro);
                            
//                            sesionDatosUsuario.setAttribute("B_D_PERSONA", datosPersona); probar pasar un objeto por la sesion pero para eso hay que hacer que ambos sean serielizables
                            
                            // control de datos de la session 
                            System.out.println(".");
                            if (sesionDatosUsuario.isNew()==true) {
                                System.out.println("[+]________MIS_________SESION_IS_NEW = true.-");
                            } else {
                                System.out.println("[-]________MIS_________SESION_IS_NEW = false.-");
                            }
//                            System.out.println("______SESION_SERVLET_CONTEXT:  :"+sesionDatosUsuario.getServletContext());
//                            System.out.println("______SESION_GET_ID_REQUEST:   :"+request.getRequestedSessionId());
                            System.out.println("[+]__SESION_GET_ID:           :"+sesionDatosUsuario.getId());
                            // [OBS] :COMO EN LA VPS NO ME CREABA LA JSESSIONID (QUE ES EL VALOR DEL ID DE LA SESION) INTENTE CARGARLO YO MISMO, Y AUNQUE NO ME SALTE ERROR EN CODIGO, ESTO NO ME SIRVE PORQUE NO ME CREA LA COOKIE.- 
                            // carga de prueba de sesion 
//                            Cookie JIDSESSION = new Cookie("JSESSIONID",sesionDatosUsuario.getId());
//                            JIDSESSION.setMaxAge(60*60);
//                            JIDSESSION.setHttpOnly(true);
//                            JIDSESSION.setDomain("zulmiullon.com");
//                            JIDSESSION.setPath("/agend");
//                            response.addCookie(JIDSESSION);
                            System.out.println("[+].--------------------END_____LOGEO______-------------------------");
                        } // END ELSE VALIS 
                    } // END IF 

                } else if(accion.equalsIgnoreCase("Cerrar Sesion")) {
                    System.out.println("[+]----------___INIT___-------------------------__SALIR__--------------------------------");
                    int varAccion = 0;

                    HttpSession sesionDatosUsuario = request.getSession(false); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
                    
                    if (varValidacionesEstado == 2 || metodos.controlLogeoUsuarioActivo(request) == true) { // SI ESTA ACTIVO ENTONCES LIMPIAREMOS LOS CAMPOS DE USUARIO 
                        System.out.println("____IF____LOGEO_ACTIVO______________");
                        idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
                        login_user = metodos.traerUsuario(idPersona);
                        login_pass = metodos.traerPassword(idPersona);
                        System.out.println("-USER:--"+login_user+"---");
                        System.out.println("-PASS:--"+login_pass+"---");

                    // hago un control del usuario - en donde el metodo retornara en caso de que exista usuario el IDALUMNO 
//                        datosPersona = metodos.controlUsuarioPer(new BeanInicioSesion(login_user, login_pass));
                        ctrlUsuario = metodos.controlUsuario(new BeanInicioSesion(login_user, login_pass));
                        Iterator<BeanUsuario> iterUsu = ctrlUsuario.iterator();
                        while(iterUsu.hasNext()) {
                            datosUsuario = iterUsu.next();
                        }
                        System.out.println("__IDPERSONA____"+idPersona+"__");
                        idUsuario = datosUsuario.getSU_IDUSUARIO();
                        System.out.println("__IDUSUARIO____"+idUsuario+"__");

                        boolean webValor = metodos.updateWebLogeo(varAccion, idPersona, idUsuario); // actualizamos la columna 
                        if (webValor == true) {
                            System.out.println("-2---Se ha guardado-----*-_TRUE_-*--------");
                        } else {
                            System.out.println("-2---No se ha podido guardar------_FALSE_---------");
                        }

                        acceso = "inicio_sesion.htm";
                        varRedireccionar = 1;

                        // AL CERRAR LA SESION DEL USUARIO, DEBEMOS DE CERRAR TAMBIEN LA SESION PARA QUE ESTA NO SE QUEDE ABIERTA Y NO SIGA MANTENIENDO EL DATO DEL ID DEL USUARIO QUE SE HABIA CONECTADO 
                        sesionDatosUsuario.invalidate();
                        
                        // borramos las cookies 
//                        System.out.println("[+] cantidad de cookies al momento de cerrar sesion:  :"+request.getCookies().length);
//                        Cookie[] cookies = request.getCookies();
//                        if (cookies != null) {
//                            for (Cookie cookie : cookies) {
//                                cookie.setValue("");
//                                cookie.setDomain("");
//                                cookie.setPath("/");
//                                cookie.setMaxAge(0);
//                                response.addCookie(cookie);
//                            }
//                        }
                        
                        System.out.println("[+]----------___END___-------------------------__SALIR__--------------------------------");
                    } // END IF CONTROL MAC_ADDRESS ACTIVE 

                } else if(accion.equalsIgnoreCase("Registrar")) {
                    System.out.println("---------------------__REGISTRAR__---------------------------");
                    String TIPO_MENSAJE="2"/*, MENSAJE=""*/;
                    int var_vacio = 0;
                    // OBTENGO LOS DATOS DE LA PAGINA 
                    String TXT_NOMBRE = request.getParameter("txtN");
                    if (TXT_NOMBRE == null || TXT_NOMBRE.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_APELLIDO = request.getParameter("txtA");
                    if (TXT_APELLIDO == null || TXT_APELLIDO.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_CINRO = request.getParameter("txtCINRO");
                    if (TXT_CINRO == null || TXT_CINRO.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_EMAIL = request.getParameter("txtE");
                    if (TXT_EMAIL == null || TXT_EMAIL.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_TELEFONO = request.getParameter("txtT");
                    if (TXT_TELEFONO == null || TXT_TELEFONO.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_DIRECCION = request.getParameter("txtD");
                    if (TXT_DIRECCION == null || TXT_DIRECCION.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_USUARIO = request.getParameter("txtUsu"); // usuario 
                    if (TXT_USUARIO == null || TXT_USUARIO.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_CLAVE = request.getParameter("txtPU"); // password user 
                    if (TXT_CLAVE == null || TXT_CLAVE.isEmpty()) { var_vacio = var_vacio+1; }
                    String TXT_IDCLINICA = request.getParameter("cCli");
                    if (TXT_IDCLINICA == null || TXT_IDCLINICA.isEmpty()) { var_vacio = var_vacio+1; }

                    int var_creado = 0;
                    if (var_vacio > 0) {
                        respuesta = "No puede dejar campos sin completar.";

                    } else if (metodos.ctrlExisteCinro(TXT_CINRO, TXT_IDCLINICA) == true) { // VALIDACION PARA CONTROLAR SI EXISTE YA EL NRO DE CI QUE CARGUE 
                        String name_clinica = metodosRefCli.traerDescClinica(TXT_IDCLINICA);
                        respuesta = "El Nro. de CI ya existe en la clinica "+name_clinica+".";

                    } else if (metodos.ctrlExisteUsuario(TXT_USUARIO) == true) { // VALIDACION PARA CONTROLAR SI EXISTE EL MISMO USUARIO ACTIVO 
                        respuesta = "Ya existe un usuario con el mismo nombre, elija otro.";

                    } else {
                        System.out.println(".");System.out.println(".");
                        System.out.println(".");System.out.println(".");
                        System.out.println("_   ___ ELSE ____       __ENVIAR_MENSAJE__         __");
                        
                        String RP_IDPERSONA = "";
                        String RP_NOMBRE = TXT_NOMBRE;
                        String RP_APELLIDO = TXT_APELLIDO;
                        String RP_CINRO = TXT_CINRO;
                        String RP_TIPODOC = "CI";
                        String RP_IDCATEGORIA_PERSONA = metodosPerfil.getIdPerfilPaciente();
                        String RP_DESC_CATEG_PERSONA = metodosPerfil.getDescPerfilPaciente();
//                        String RP_IDCATEGORIA_PERSONA = metodosPerfil.recuperarIdPerfil("PACIENTE");
//                        String RP_DESC_CATEG_PERSONA = metodosPerfil.recuperarDescPerfil("4");
                        String RP_RAZON_SOCIAL = TXT_NOMBRE+" "+TXT_APELLIDO;
                        String RP_RUC = TXT_CINRO;
                        String RP_IDBARRIO = "0";
                        String RP_DESC_BARRIO = "(NO/DEFINIDO)"; 
                        String RP_IDCIUDAD = "0";
                        String RP_DESC_CIUDAD = "(NO/DEFINIDO)";
                        String RP_DIRECCION = TXT_DIRECCION;
                        String RP_EMAIL = TXT_EMAIL;
                        String RP_TELFPARTICULAR = "";
                        String RP_TELFMOVIL = TXT_TELEFONO;
                        String RP_IDCIUDADNAC = "0";
                        String RP_DESC_CIUDADNAC = "(NO/DEFINIDO)";
                        String RP_FECHANAC = metodos.traerFechaHoy();
                        String RP_SEXO = "N";
                        String RP_RELIGION = "";
                        String RP_ESTADOCIVIL = "N";
                        String RP_GRUPOSANGUINEO = "";
                        String RP_OBSERVACION = "";
                        String RP_FECHAALTA = metodos.traerFechaHoraHoy();
                        String RP_FECULTIMOMOV = null;
                        String RP_FOTO = "";
                        String RP_USU_ALTA = "1"; // 1 : ROOT 
                        String RP_USU_MOD = "0";
                        String RP_IDPAIS = "0";
                        String RP_DESC_PAIS = "(NO/DEFINIDO)";
                        String RP_TELEFPARTICULAR = "";
                        String RP_IDCLINICA = TXT_IDCLINICA;
                        String RP_OCUPACION = "";
                        String RP_ANTECEDENTES = "";
                        String RP_EXPEDIENTE_CLINICO = "";
                        String RP_SEGURO_MEDICO = "";
                        String RP_TIENE_HIJOS = "0";
                        String RP_CANT_HIJOS = "0";
                        String RP_FOTO_PATH_ABS = "";
                        String RP_IDPERSONA_NEW = "";
                        // USUARIO 
                        String SU_IDUSUARIO = "";
                        String SU_IDPERSONA = RP_IDPERSONA;
                        String SU_USUARIO = TXT_USUARIO;
                        String SU_CLAVE = TXT_CLAVE;
                        String SU_ESTADO = "A";
                        String SU_IDPERFIL = metodosPerfil.getIdPerfilPaciente(); // PERFIL PACIENTE PARA EL QUE SE REGISTRE 
//                        String SU_IDPERFIL = metodosPerfil.recuperarIdPerfil("PACIENTE");
                        String SU_DESC_PERFIL = metodosPerfil.getDescPerfilPaciente();
//                        String SU_DESC_PERFIL = metodosPerfil.recuperarDescPerfil("4"); // POR EL MOMENTO NO LE CARGO NADA PORQUE EN LA TABLA DE USUARIO YA NO TENGO LA COLUMNA QUE MUESTRA LA DESCRIPCION DEL PERFIL / PERO EN CASO DE QUE VUELVA A TENER, HAY SI UTILIZARE ESTE CMAPO PARA CARGAR ESA COLUMNA 
                        String SU_EMAIL = TXT_EMAIL;
                        String SU_ESTADO_MIGRAR = "NO";
                        String SU_WEB = "0";
                        String SU_CONFIR_EMAIL = "NO";
                        
                        TIPO_MENSAJE = metodosPers.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS, RP_FOTO_PATH_ABS, RP_IDPERSONA_NEW, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
//                        TIPO_MENSAJE = metodosPers.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL, RP_IDCLINICA, RP_SEGURO_MEDICO));
                        if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                            respuesta = "Salto un error, vuelva a intentarlo mas tarde.";
                        } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                            respuesta = "Se ha realizado correctamente la operación.";
                            TIPO_MENSAJE = "1";
                            acceso = "inicio_sesion.htm";
                            var_creado = 1; // si se crea correctamente entonces le cambio su valor 
                            varRedireccionar = 1;
                        }
                        // llamo al metodo que enviara un mail a la cuenta con los datos de logeo que cargo 
                        metodos.sendMailRegister(request);
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   _CTRL__RESPUESTA:    :"+respuesta);
                    
                    request.setAttribute("respuesta", respuesta);
                    request.setAttribute("TIPO_MENSAJE", TIPO_MENSAJE);
                    if((respuesta.isEmpty()==false || respuesta.equals("")==false) && var_creado == 0) { // si la respuesta esta cargada y la bandera sigue igual a cero, entonces le redirecciono a la pagina de registro ya que salto alguna validacion, pero en caso de que la respuesta este cargada pero la bandera este igual a 1, entonces es porque se guardo correctamente y le tendria que redireccionar a la pagina de inicio de sesion 
                        acceso = "registrar.htm";
                        varRedireccionar = 1;
                        request.setAttribute("VAR_NOMBRE", TXT_NOMBRE);
                        request.setAttribute("VAR_APELLIDO", TXT_APELLIDO);
                        request.setAttribute("VAR_CINRO", TXT_CINRO);
                        request.setAttribute("VAR_EMAIL", TXT_EMAIL);
                        request.setAttribute("VAR_TELEFONO", TXT_TELEFONO);
                        request.setAttribute("VAR_DIRECCION", TXT_DIRECCION);
                        request.setAttribute("usuarioDato", TXT_USUARIO);
                        request.setAttribute("passwordDato", TXT_CLAVE);
                        request.setAttribute("VAR_IDCLINICA", TXT_IDCLINICA);
                    }
                }
                
                
            } else if (accionRecoverPass != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println(".   _________  ACCION_RECOVER_PASSWORD  ________");
                System.out.println(".");System.out.println(".");System.out.println(".");
                acceso = "recover_pass.htm";
                varRedireccionar = 1;
                if(accionRecoverPass.equalsIgnoreCase("Buscar NroCi")) {
                    System.out.println("---------------------__BUSCAR_NRO_CI__---------------------------");
                    String TXT_NRO_CI = (String) request.getParameter("txtCINRO");
                    System.out.println("_   _TXT_NRO_CI:  :"+TXT_NRO_CI);
                    
                    if (TXT_NRO_CI == null || TXT_NRO_CI.isEmpty()) {
                        varError = 1;
                        respuesta = "Debe de cargar el Nro. de CI para poder devolverle su clave.";
                    } else {
                        System.out.println("________________ELSE________________");
                        BeanPersona beanUsu = null;
                        beanUsu = metodos.recuperarUsuarioNroCI(beanUsu, TXT_NRO_CI);
                        if (beanUsu == null) {
                            System.out.println("________BEAN_NULL______");
                            varError = 1;
                            respuesta = "No se encontro ningun usuario con el Nro. de CI que ingreso.\n Vuelva a intentarlo.";
                            request.setAttribute("VAR_CINRO", TXT_NRO_CI);
                        } else {
                            System.out.println("_______BEAN_CARGADO_______");
                            varRedireccionar = 1;
                            request.setAttribute("VAR_CINRO", TXT_NRO_CI);
                            request.setAttribute("BEAN_USUARIO_FOUND", beanUsu);
                            request.setAttribute("TIPO_MENSAJE", "1");
                        }
                        System.out.println("______________END_ELSE______________");
                    }
                    
                } else if(accionRecoverPass.equalsIgnoreCase("EnviarDatosEmail")) {
                    System.out.println("---------------------__ENVIAR_CLAVE_AL_EMAIL__---------------------------");
                    String TXT_CINRO = (String) request.getParameter("txtCINROFound");
                    BeanPersona beanPersonaRecu = null;
                    beanPersonaRecu = metodos.recuperarUsuarioNroCI(beanPersonaRecu, TXT_CINRO);
                    String VAR_EMAIL = beanPersonaRecu.getRP_EMAIL();
                    String VAR_USUARIO = beanPersonaRecu.getSU_USUARIO();
                    String VAR_CLAVE = beanPersonaRecu.getSU_CLAVE();
                    // llamo al metodo que enviara un mail a la cuenta de gmail con los datos de logeo 
                    metodos.sendMailRecoverPass(VAR_EMAIL, VAR_USUARIO, VAR_CLAVE);
                    acceso = "inicio_sesion.htm";
                    varRedireccionar = 1;
                    respuesta = "Se ha enviado un mail con sus datos al email: "+VAR_EMAIL+".";
                    request.setAttribute("respuesta", respuesta);
                    request.setAttribute("TIPO_MENSAJE", "1");
                }
            }
        } catch (Exception e) {
            System.out.println("-------------ERROR-INICIO_SESION------------------");
            System.out.println("__ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerInicioSesion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------");
        }
        
// OBSERVACION: 
// UTILIZO EL RESPONSE PARA PODER REDIRECCIONAR A LA PAGINA SIN PASAR DATOS EN MEMORIA POR MEDIO DEL REQUEST, YA QUE EL REQUEST NO MANTIENE LOS DATOS CUANDO SE REDIRECCIONA POR MEDIO DEL RESPONSE, PERO SI QUISIESE RECUPERAR DATOS POR MEDIO DEL REQUEST ENTONCES UTILIZARIA EL DISPATCHER PARA REDIRECCIONAR A LOS JSP 
        if (varRedireccionar == 0) {
            response.sendRedirect(acceso);
        } else {
            //OBS.: preguntamos por esta variable para saber si debemos enviar atributos para que reciba la vista que se mostrara
            if (varError > 0) {
                request.setAttribute("respuesta", respuesta);
                request.setAttribute("usuarioDato", login_user);
                request.setAttribute("passwordDato", login_pass);
            }
            RequestDispatcher vista = request.getRequestDispatcher(acceso);
            vista.forward(request, response);
        }
    } // END doPost() 
    
    
    
} // END CLASS 