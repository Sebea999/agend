/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.controlador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanFichaAtePaciente;
import com.agend.javaBean.BeanInicioSesion;
import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanUsuario;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import com.agend.modelo.ModelFichaAtencionPacNutri;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelRef_Ciudad;
import com.agend.modelo.ModelRef_Clinica;
import com.agend.modelo.ModelUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@CrossOrigin(maxAge = 3600)
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@WebServlet(name="PacienteController", urlPatterns="/CPSrv") // CONTROLLER PACIENTE Servlet
public class ControllerPaciente extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/paciente")
    public ModelAndView paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__PACIENTE--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CC__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPaciente(IDPERFIL_USER)) {
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPaciente", request);
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPaciente_img", request);
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPacienteCarousel", request);
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPacienteSliderExample", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagPaciente", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "PAC"); // VARIABLE QUE USO PARA "VOLVER ATRAS" 
        
        return mav;
    }
    
    
    
    @RequestMapping("/add_paciente")
    public ModelAndView add_paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__2__MAV___ID_PACIENTE:     "+ID_PERSONA_PACIENTE);
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_PACIENTE------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CC__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CC__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPaciente(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd_img", request);
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/edit_paciente")
    public ModelAndView edit_paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__3__MAV___ID_PACIENTE:     "+ID_PERSONA_PACIENTE);
//        String IDCLIENTE = (String) request.getParameter("tI");
//        System.out.println("__2__MAV___ID_CLIENTE:       "+IDCLIENTE);
        
        System.out.println("-----__3__--------CONTROLLER__AÑADIR_PACIENTE------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPaciente(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd_img", request);
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteAdd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/ver_paciente")
    public ModelAndView ver_paciente(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__4__MAV___ID_PACIENTE:     "+ID_PERSONA_PACIENTE);
//        String IDCLIENTE = (String) request.getParameter("tI");
//        System.out.println("__2__MAV___ID_CLIENTE:       "+IDCLIENTE);
        
        System.out.println("-----__4__--------CONTROLLER__VER_PACIENTE------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CP__ID_USUARIO_PERSONA:    :"+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CP__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPaciente(IDPERFIL_USER)==true) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteVer", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteVer", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "VER_PACIENTE"); // CONFIGURACION DEL BOTON DE "VOLVER ATRAS" DE LA PAGINA DE VER FICHA DE ATENCION DEL PACIENTE PARA PODER VOLVER A LA PAGINA DE PACIENTE Y NO A LA PAGINA PRINCIPAL DE FICHA DE ATENCION 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/pac_updpass")
    public ModelAndView paciente_cambiar_contra(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__5__MAV___ID_PACIENTE:     "+ID_PERSONA_PACIENTE);
//        String IDCLIENTE = (String) request.getParameter("tI");
//        System.out.println("__2__MAV___ID_CLIENTE:       "+IDCLIENTE);
        
        System.out.println("-----__5__--------CONTROLLER__VER_PACIENTE------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CP__ID_USUARIO_PERSONA:    :"+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CP__ID_PERFIL_USER:        :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuPaciente(IDPERFIL_USER)==true) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacNewPass", request);
        } else if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)==true) { // COMO EL PERFIL DE PACIENTE NO PUEDE INGRESAR AL MENU DE PACIENTE (PARA EVITAR QUE PUEDA INGRESAR A TRAVES DE LA URL) ENTONCES LE AGREGO ESTE ELSE-IF PARA QUE PUEDA ENTRAR A LA PAGINA DONDE LE HABILITO LA ACTUALIZACION DE SU CONTRASEÑA 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacNewPass", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagPacienteVer", request);
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
    
    
    
    
    
    public void listarFicherosPorCarpeta(final File carpeta) {
        System.out.println(".-------------------------------__INIT__-------------------------------------");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   ___________listarFicherosPorCarpeta()___________");
        for (final File ficheroEntrada : carpeta.listFiles()) {
            System.out.println("_f-");
            System.out.println("_");
            System.out.println(".   for:1:__"+ficheroEntrada.getName());
            System.out.println(".   for:2:__"+ficheroEntrada.getAbsolutePath());
            System.out.println("_");
            System.out.println("_");
            if (ficheroEntrada.isDirectory()) {
                System.out.println("___IS_DIRECTORY_____");
                listarFicherosPorCarpeta(ficheroEntrada);
            } else {
                System.out.println("____FILE_NAME______");
                System.out.println(ficheroEntrada.getName());
            }
            System.out.println("_");
            System.out.println("_");
            System.out.println("_");
        }
        System.out.println(".");
        System.out.println(".");
        System.out.println(".-------------------------------__END__-------------------------------------");
    }
    
    private String saveFile(Part part, File pathUploads, File pathNewCarp) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   ____________saveFile_______________");
        System.out.println(".");
        System.out.println(".");
        String pathAbsolute="";
        
        try {
//            /*
//            OBSERVACIONES:
//                * FUNCIONALIDADES PARA AGREGAR O PENSAR:
//                    * SI SE ACTUALIZA EL IDPACIENTE ENTONCES DEBO BORRAR EL CONTENIDO DE LA CARPETA DEL IDPACIENTE Y AGREGAR SOLO LA FOTO (A MENOS QUE EN LA CARPETA SE MANEJE ARCHIVOS Y NO SOLO SEAN FOTOS PORQUE SI NO SE ELIMINA EL CONTENIDO Y SOLO SON FOTOS, LAS FOTOS SE VAN A ACUMULAR) 
//                    * SE DEBERIAN DE REEMPLAZAR LAS FOTOS CUANDO SE ENCUENTRE YA UNA QUE TENGA EL MISMO NOMBRE 
//            */
//            System.out.println("____PATH_NEW_CARPETA:   :"+pathNewCarp.getName());
//            System.out.println("____PATH_NEW_CARPETA:   :"+pathNewCarp.getAbsoluteFile());
//            // CONTROLO SI YA EXISTE EL DIRECTORIO CON EL ID DEL PACIENTE 
//            if(pathNewCarp.isDirectory() == false) {
//                // SI NO EXISTE ENTONCES CREO LA CARPETA 
//                System.out.println("-------------------------------------------------------------------------------------------");
//                System.out.println("_______IF________EL_NEW_PATH_NO_ES_UN_DIRECTORIO_____");
//                boolean crearCarpetaPac = pathNewCarp.mkdir();  
//                System.out.println("-------------------------------------------------------------------------------------------");
//                if(crearCarpetaPac == true){
//                    // SI SE CREA EXITOSAMENTE ENTONCES EL NUEVO PATH VA A SER EL DE LA CARPETA 
//                    System.out.println(".");System.out.println(".");System.out.println(".");
//                    System.out.println(".   -----Directory is created successfully-------");
//                    System.out.println(".");System.out.println(".");System.out.println(".");
//                    pathUploads = pathNewCarp;
//                }else{
//                    // SI SALTA ALGUN ERROR ENTONCES LE DEJO NOMAS CON LA CARPETA DE STATIC QUE USO COMO BASE 
//                    System.out.println(".");System.out.println(".");System.out.println(".");
//                    System.out.println("Error ! no se creo directory --");
//                    System.out.println(".");System.out.println(".");System.out.println(".");
//                }
//                System.out.println("-------------------------------------------------------------------------------------------");
//            } else {
//                // SI YA EXISTE LA CARPETA CON EL ID DEL PACIENTE ENTONCES NO CREO NADA Y LE CARGO LA DIRECCION DEL PATH CON EL IDPACIENTE COMO SUBCARPETA 
//                System.out.println("-------------------------------------------------------------------------------------------");
//                System.out.println("_______ELSE________EL_NEW_PATH_ES_UN_DIRECTORIO____");
//                pathUploads = pathNewCarp;
//                System.out.println("-------------------------------------------------------------------------------------------");
//            }
            
            Path path = Paths.get(part.getSubmittedFileName());
            String fileName = path.getFileName().toString();
            System.out.println("___FILE_NAME:  :"+fileName);
            InputStream input = part.getInputStream();
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__  ______METODO:   :"+pathUploads.getAbsolutePath());
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // leer los ficheros 
//            final File carpeta = new File("/home/usuario/Escritorio");
            listarFicherosPorCarpeta(pathUploads);
            
            if (input != null) {
                File file = new File(pathUploads, fileName);
                pathAbsolute = file.getAbsolutePath();
                System.out.println("____file.to.path:  :"+file.toPath());
                System.out.println("____file.getAbsolutePath:  :"+pathAbsolute);
                System.out.println("____file.getAbsoluteFile:  :"+file.getAbsoluteFile());
                Files.copy(input, file.toPath()); // guardamos el archivo en la carpeta designada 
            }
        } catch (IOException e) {
            System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println("_______saveFiles()___ERROR________________");
            Logger.getLogger(ControllerFichaAtencionPacNutri.class.getName()).log(Level.SEVERE,null,e);
        }
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        return pathAbsolute;
    }
    
    
    
    
    
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // ESTAS DOS LINEAS DE CODIGOS SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
        
        ModelPersona metodos = new ModelPersona();
        ModelRef_Ciudad metodosRefCiudad = new ModelRef_Ciudad();
        ModelFichaAtencionPacNutri metodosFicha = new ModelFichaAtencionPacNutri();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int var_redireccionar = 0;
        String acceso = "paciente.htm";
        String accion = request.getParameter("accion");
        String accionExpediente = request.getParameter("accionExp");
        String idPersona, idPersonaPaciente, idUsuarioPaciente;
        
        try {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   -----------------___CONTROLADOR-DE-EVENTOS-DE-LA-PAGINA-___PACIENTE___-----------------");
            System.out.println(".");System.out.println(".");
            System.out.println("__ACCION:       "+accion);
            System.out.println("__ACCION_EXPEDIENTE:       "+accionExpediente);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            System.out.println(".");System.out.println(".");
            
            // [OBS] LOS EVENTOS DE accionExp SON EVENTOS PARA LAS ACCIONES DEL EXPEDIENTE QUE INTERACTUAN CON LAS FICHAS COMO EL "VER FICHA" PARA PODER CARGARLE EL VOLVER ATRAS DE FICHA A LA PAGINA DE EXPEDIENTE PACIENTE 
            if (accionExpediente != null) {
                System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println(".   -----------------ACTION_EVENTS__FOR_EXPEDIENTE__----------------");
                System.out.println(".");System.out.println(".");
                System.out.println(".");
                ControllerFichaAtencionPacNutri controllerFicha = new ControllerFichaAtencionPacNutri();
                if (accionExpediente.equalsIgnoreCase("Ver Atención")) {
                    System.out.println("---------------------__VER_FICHA_DE_ATENCION__--------------------------");
                    String idAtencionPac = (String) request.getParameter("tIAP");
                    System.out.println("_   ___ID_ATENCION_PAC:     :"+idAtencionPac);
                    var_redireccionar = 1;
                    acceso = controllerFicha.ver_ficha_atencion_paciente(sesionDatosUsuario, request, metodosFicha, idAtencionPac, var_redireccionar, acceso);
                    // LE LIMPIO LAS MISMAS VARIABLES POR SI QUEDARON DE UNA ANTERIOR FICHA EN SESION (YA QUE LO UTILIZO PARA CARGAR LAS LISTAS AL QUERER VER EL HISTORIAL DE FICHAS) 
                    sesionDatosUsuario.removeAttribute("CFAP_PAC_DATOS");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_01");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_02");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_03");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_04");
                    // cargo la variable del boton de volver atras de la ficha.
                    sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "VER_PACIENTE");
                    
                } else if (accionExpediente.equalsIgnoreCase("Editar Atención")) {
                    System.out.println("---------------------__EDITAR_FICHA_DE_ATENCION__--------------------------");
                    var_redireccionar = 1;
                    acceso = controllerFicha.editarFichaAtencion(sesionDatosUsuario, request, metodosFicha, acceso);
                    // cargo la variable del boton de volver atras de la ficha.
                    sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "VER_PACIENTE");
                    
                } else if (accionExpediente.equalsIgnoreCase("Ver Historico")) {
                    System.out.println("---------------------__VER_HISTORICO_DEL_PACIENTE__--------------------------");
                    var_redireccionar = 1;
                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "VER_PACIENTE");
                    ControllerReportes controllerRpt = new ControllerReportes();
                    acceso = controllerRpt.goVerHistoricoFichasPaciente(request, metodosFicha);
                }
                
            } else 
            if (accion != null) {
                System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println(".   -----------------ACTION_EVENTS----------------");
                System.out.println(".");System.out.println(".");
                System.out.println(".");
//            // [OBS] PRUEBA PARA COPIAR UN ARCHIVO SELECCIONADO Y PEGAR EN UNA CARPETA.-
//            if (accion.equalsIgnoreCase("Upload File")) {
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".   --------------------upload-file---------------------");
//                System.out.println(".");
//                String pathFiles = "C:\\OdontoAppWeb\\web\\recursos\\static\\";
//                File uploads = new File(pathFiles);
//                String name = request.getParameter("file_name");
//                Part part = request.getPart("PacFile");
//                String photo = saveFile(part, uploads);
//                String MENSAJE = "Se ha guardado correctamente en el id: "+name+" ( "+photo+" )";
//                String TIPO_MENSAJE = "1";
//                request.setAttribute("CP_MENSAJE", MENSAJE);
//                request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
//                
//            } else 
                if (accion.equalsIgnoreCase("Add Paciente")) { // BOTON PARA AÑADIR UN NUEVO PACIENTE 
                    System.out.println("---------------------__AÑADIR_PACIENTE__--------------------------");
                    String ID_CLINICA = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");

                    List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                    String FECHA_HOY = modeloIniSes.traerFechaHoy();
                    LISTA_DATOS.add(new BeanPersona("", "", "", "", "CI", modeloPerfil.getIdPerfilPaciente(), modeloPerfil.getDescPerfilPaciente(), "", "", "", "", "0", "", "", "", FECHA_HOY, "", "", "", "", "", "", "0", "0", "", "", ""));

                    var_redireccionar = 1;
                    acceso = "add_paciente.htm";
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                    request.setAttribute("CP_PAC_IDCLINICA", ID_CLINICA);
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                    sesionDatosUsuario.setAttribute("CP_PAC_AUX_FOTO",""); // [OBS] LE AGREGO CON VALOR VACIO PARA QUE NO ME RECUPERE EL VALOR DE LA VAR CON NULO - // [OBS] CARGO A LA SESION LA VARIABLE PARA LUEGO USARLO EN EL EVENTO DE MODIFICAR.-

                } else if (accion.equalsIgnoreCase("Editar")) { // BOTON PARA EDITAR O MODIFICAR LOS DATOS DEL CLIENTE 
                    System.out.println("---------------------__EDITAR__--------------------------");
                    idPersonaPaciente = (String) request.getParameter("tI");
                    System.out.println("_doPost__ID_PERSONA_PACIENTE:     "+idPersonaPaciente);
                    String FOTO_AUX = ""; // [OBS] VARIABLE QUE UTILIZARE PARA GUARDAR EL NAME DE LA FOTO EN CASO DE QUE SE RECUPERE Y UTILIZARLO EN EL CONTROLADOR PARA VALIDAR SI ES QUE LA IMAGEN CON LA QUE SE CARGO A SIDO MODIFICADA.-
                    
                    List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                    List<BeanPersona> LISTA_DATOS_AUX = new ArrayList<>();
                    if (idPersonaPaciente != null) {
                        LISTA_DATOS = metodos.traerDatosPersona(idPersonaPaciente);
                        LISTA_DATOS_AUX = metodos.traerDatosPersona(idPersonaPaciente);
                        
                        if (LISTA_DATOS != null) { // [OBS]: CONTROLO SI LA LISTA TIENE DATOS PARA EVITAR RECUPERAR INNECESARIAMENTE, Y SI TIENE DATOS ENTONCES RECUPERO EL VALOR DE LA FOTO.-
                            BeanPersona datos = new BeanPersona();
                            datos = LISTA_DATOS.get(0);
                            FOTO_AUX = datos.getRP_FOTO();
                        }
                    }
                    
                    var_redireccionar = 1;
                    acceso = "edit_paciente.htm";
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", idPersonaPaciente);
                    String btn_volver_atras = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
                    System.out.println("__BTN_EDITAR:       :PARAM_PAC_BTN_VOLVER_ATRAS:   :"+btn_volver_atras);
                    if (btn_volver_atras==null || btn_volver_atras.isEmpty()) {
                        sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "");
                    } else if (btn_volver_atras.equals("VER_PAC")) {
                        // le dejo con el mismo dato para que retorne al ver_expediente
                    }
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                    request.setAttribute("CP_PAC_LISTA_DATOS_AUX", LISTA_DATOS_AUX);
                    sesionDatosUsuario.setAttribute("CP_PAC_AUX_FOTO",FOTO_AUX); // CARGO A LA SESION LA VARIABLE PARA LUEGO USARLO EN EL EVENTO DE MODIFICAR.-

                } else if (accion.equalsIgnoreCase("Ver Expediente")) {
                    System.out.println("---------------------__VER_EXPEDIENTE__--------------------------");
                    idPersonaPaciente = (String) request.getParameter("tI");
                    System.out.println("_doPost__ID_PERSONA_PACIENTE:     "+idPersonaPaciente);

                    List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                    if (idPersonaPaciente != null) {
                        LISTA_DATOS = metodos.traerDatosPersona(idPersonaPaciente);
                    }
                    List<BeanFichaAtePaciente> LISTA_ULTIMOS_DATOS_FICHA = new ArrayList<>(); // los datos de la ultima consulta 
                    if (idPersonaPaciente != null) {
                        LISTA_ULTIMOS_DATOS_FICHA = metodosFicha.getUltimaFicha(idPersonaPaciente);
                    }

                    var_redireccionar = 1;
                    acceso = "ver_paciente.htm";
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", idPersonaPaciente);
                    String btn_volver_atras = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
                    System.out.println("__BTN_VER_EXPEDIENTE:       :PARAM_PAC_BTN_VOLVER_ATRAS:   :"+btn_volver_atras);
                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "VER_PAC");
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                    request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_ULTIMOS_DATOS_FICHA);

                } else if (accion.equalsIgnoreCase("Agregar Consulta")) {
                    System.out.println("---------------------__AGREGAR_CONSULTA__--------------------------");
                    idPersonaPaciente = (String) request.getParameter("tI");
                    System.out.println("_doPost__ID_PERSONA_PACIENTE:     "+idPersonaPaciente);

                    var_redireccionar = 1;
                    acceso = "ver_paciente.htm";
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", idPersonaPaciente);

                } else if(accion.equalsIgnoreCase("GRABAR") || accion.equalsIgnoreCase("Guardar")) {
                    System.out.println("---------------------__GUARDAR__--------------------------");
                    int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                    idPersonaPaciente = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                    System.out.println("__ID_PACIENTE:   :"+idPersonaPaciente);
                    idUsuarioPaciente = (String) request.getParameter("ID_USUARIO_PACIENTE");
    //                idUsuarioPaciente = (String) sesionDatosUsuario.getAttribute("ID_USUARIO_PACIENTE");
                    System.out.println("__ID_USUARIO_PACIENTE:   :"+idUsuarioPaciente);
                    // ESTA VARIABLE ME SERVIRA PARA PODER REDIRECCIONAR OTRA VEZ A LA PAGINA PRINCIPAL EN CASO DE QUE LA PAGINA DE AÑADIR PACIENTE FUERA REDIRECCIONADA DESDE OTRA PAGINA 
                    String btn_volver_atras = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
                    System.out.println("__PARAM_PAC_BTN_VOLVER_ATRAS:   :"+btn_volver_atras);
                    String PATH_IMAGEN = "", NAME_IMAGEN = "";
                    String file_separador =  File.separator;
                    String path_destino = request.getServletContext().getRealPath("//recursos//static//"); // [OBS] :cargando de esta manera le estoy pasando siempre la direccion base del contexto y con esta sentencia se termina guardando los archivos en la carpeta de la app dentro del servidor.-
//                    String path_destino = metodosIniSes.getPathImgLocation();
                    
                    // VARIABLES PARA SABER QUE CAMPOS SE DEJO VACIO 
                    int varValiVacioTxtNom = 0;
                    int varValiVacioTxtApe = 0;
                    int varValiVacioTxtCinro = 0;
                    int varValiVacioTxtTel = 0;
                    int varValiVacioTxtCel = 0;
                    int varValiVacioTxtMail = 0;
                    int varValiVacioTxtDire = 0;
                    
                    // RECUPERAR LOS DATOS 
                    String PAC_NOMBRE = (String) request.getParameter("tN");
                    if (PAC_NOMBRE == null || PAC_NOMBRE.isEmpty()) { varValiVacio++; varValiVacioTxtNom++; PAC_NOMBRE=""; }
                    String PAC_APELLIDO = (String) request.getParameter("tA");
                    if (PAC_APELLIDO == null || PAC_APELLIDO.isEmpty()) { varValiVacio++; varValiVacioTxtApe++; PAC_APELLIDO=""; }
                    String PAC_CINRO = (String) request.getParameter("tNC");
                    if (PAC_CINRO == null || PAC_CINRO.isEmpty()) { varValiVacio++; varValiVacioTxtCinro++; PAC_CINRO=""; }
                    String PAC_RAZON_SOCIAL = "";
    //                String PAC_RAZON_SOCIAL = (String) request.getParameter("tRS");
    //                if (PAC_RAZON_SOCIAL == null || PAC_RAZON_SOCIAL.isEmpty()) { /*varValiVacio++;*/ PAC_RAZON_SOCIAL=""; }
                    String PAC_RUC = "";
    //                String PAC_RUC = (String) request.getParameter("tR");
    //                if (PAC_RUC == null || PAC_RUC.isEmpty()) { /*varValiVacio++;*/ PAC_RUC=""; }
                    String PAC_TELEFONO = (String) request.getParameter("tTP");
                    if (PAC_TELEFONO == null || PAC_TELEFONO.isEmpty()) { varValiVacio++; varValiVacioTxtTel++; PAC_TELEFONO=""; }
                    String PAC_CELULAR = (String) request.getParameter("tCP");
                    if (PAC_CELULAR == null || PAC_CELULAR.isEmpty()) { varValiVacio++; varValiVacioTxtCel++; PAC_CELULAR=""; }
                    String PAC_DIRECCION = (String) request.getParameter("tD");
                    if (PAC_DIRECCION == null || PAC_DIRECCION.isEmpty()) { varValiVacio++; varValiVacioTxtDire++; PAC_DIRECCION=""; }
                    String PAC_EMAIL = (String) request.getParameter("tE");
                    if (PAC_EMAIL == null || PAC_EMAIL.isEmpty()) { /*varValiVacio++;*/ varValiVacioTxtMail++; PAC_EMAIL=""; }
                    String PAC_FECHANAC = (String) request.getParameter("tFN");
                    if (PAC_FECHANAC == null || PAC_FECHANAC.isEmpty()) { /*varValiVacio++;*/ PAC_FECHANAC=""; }
                    String PAC_SEXO = (String) request.getParameter("cSe");
                    if (PAC_SEXO == null || PAC_SEXO.isEmpty()) { /*varValiVacio++;*/ PAC_SEXO=""; }
                    String PAC_IDCLINICA = (String) request.getParameter("cCli");
                    if (PAC_IDCLINICA == null || PAC_IDCLINICA.isEmpty()) { /*varValiVacio++;*/ PAC_IDCLINICA=""; }
                    String PAC_OCUPACION = (String) request.getParameter("tPP"); // OCUPACION VENDRIA A SER LA PROFESION 
                    if (PAC_OCUPACION == null || PAC_OCUPACION.isEmpty()) { /*varValiVacio++;*/ PAC_OCUPACION=""; }
                    String PAC_SEGURO_MEDICO = (String) request.getParameter("tSM");
                    if (PAC_SEGURO_MEDICO == null || PAC_SEGURO_MEDICO.isEmpty()) { /*varValiVacio++;*/ PAC_SEGURO_MEDICO=""; }
                    String PAC_ESTADOCIVIL = (String) request.getParameter("cECP");
                    if (PAC_ESTADOCIVIL == null || PAC_ESTADOCIVIL.isEmpty()) { /*varValiVacio++;*/ PAC_ESTADOCIVIL="ND"; }
                    String PAC_TIENE_HIJOS = (String) request.getParameter("cbxTHP");
                    if (PAC_TIENE_HIJOS == null || PAC_TIENE_HIJOS.isEmpty()) { /*varValiVacio++;*/ PAC_TIENE_HIJOS="0"; } // 0 = NO  - 1 = SI 
                    String PAC_CANT_HIJOS = (String) request.getParameter("TCHP");
                    if (PAC_CANT_HIJOS == null || PAC_CANT_HIJOS.isEmpty()) { /*varValiVacio++;*/ PAC_CANT_HIJOS="0"; }
                    String PAC_OTROS_DATOS = (String) request.getParameter("TPOD");
                    if (PAC_OTROS_DATOS == null || PAC_OTROS_DATOS.isEmpty()) { /*varValiVacio++;*/ PAC_OTROS_DATOS=""; }
                    String PAC_IDCIUDAD = (String) request.getParameter("cCI");
                    if (PAC_IDCIUDAD == null || PAC_IDCIUDAD.isEmpty()) { /*varValiVacio++;*/ PAC_IDCIUDAD="0"; }
                    String PAC_LOGIN;
    //                try {
    //                    PAC_LOGIN = (String) request.getParameter("tL");
    //                } catch(NullPointerException e) {
                        PAC_LOGIN = "";
    //                }
    //                if (PAC_LOGIN.isEmpty() || PAC_LOGIN.equals("")) {
    ////                    varValiVacio = varValiVacio + 1;
    //                    PAC_LOGIN="";
    //                }
                    String PAC_CLAVE;
    //                try {
    //                    PAC_CLAVE = (String) request.getParameter("tC");
    //                } catch(NullPointerException e) {
                        PAC_CLAVE = "";
    //                }
    //                if (PAC_CLAVE.isEmpty() || PAC_CLAVE.equals("")) {
    ////                    varValiVacio = varValiVacio + 1;
    //                    PAC_CLAVE="";
    //                }
                    String PAC_USU_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
    //                String PAC_USU_ESTADO = (String) request.getParameter("cE");
                    if (PAC_USU_ESTADO == null || PAC_USU_ESTADO.isEmpty()) { /*varValiVacio++;*/ PAC_USU_ESTADO="A"; }
                    String PAC_FOTO = (String) request.getParameter("TAPI");
                    String PAC_FOTO_ABS = (String) request.getParameter("TAPIPA");
                    
                    // IMPRESION DE DATOS 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-       PAC_NOMBRE:     "+PAC_NOMBRE);
                    System.out.println("-       PAC_APELLIDO:   "+PAC_APELLIDO);
                    System.out.println("-       PAC_NRO_CI:     "+PAC_CINRO);
                    System.out.println("-       PAC_RAZON_SOCIAL:  "+PAC_RAZON_SOCIAL);
                    System.out.println("-       PAC_RUC:        "+PAC_RUC);
                    System.out.println("-       PAC_TELEFONO:   "+PAC_TELEFONO);
                    System.out.println("-       PAC_CELULAR :   "+PAC_CELULAR);
                    System.out.println("-       PAC_DIRECCION:  "+PAC_DIRECCION);
                    System.out.println("-       PAC_EMAIL:      "+PAC_EMAIL);
                    System.out.println("-       PAC_FEC_NAC:    "+PAC_FECHANAC);
                    System.out.println("-       PAC_SEXO:       "+PAC_SEXO);
                    System.out.println("-       PAC_IDCLINICA:  "+PAC_IDCLINICA);
                    System.out.println("-       PAC_OCUPACION:  "+PAC_OCUPACION);
                    System.out.println("-       PAC_SEGURO_MED: "+PAC_SEGURO_MEDICO);
                    System.out.println("-       PAC_ESTADOCIVIL:"+PAC_ESTADOCIVIL);
                    System.out.println("-       PAC_TIENE_HIJOS:"+PAC_TIENE_HIJOS);
                    System.out.println("-       PAC_CANT_HIJOS: "+PAC_CANT_HIJOS);
                    System.out.println("-       PAC_OTROS_DATOS:"+PAC_OTROS_DATOS);
                    System.out.println("-       PAC_IDCIUDAD:   "+PAC_IDCIUDAD);
                    System.out.println("-       PAC_FOTO:       "+PAC_FOTO);
                    System.out.println("-       PAC_FOTO_ABS:   "+PAC_FOTO_ABS);
                    System.out.println("-       -----------------------");
                    System.out.println("-       PAC_LOGIN:      "+PAC_LOGIN);
                    System.out.println("-       PAC_CLAVE:      "+PAC_CLAVE);
                    System.out.println("-       PAC_ESTADO:     "+PAC_USU_ESTADO);
                    System.out.println("-   -----------------------------------");
                    System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                    
                    // VALIDACIONES 
                    String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
                    if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                        MENSAJE = "No puede dejar campos vacios.";
                        TIPO_MENSAJE = "2";
                        // CONDICIONAL PARA SABER QUE CAMPO SE ENCUENTRA VACIO PARA ESPECIFICARLO AL USUARIO 
                        if(varValiVacioTxtDire > 0) {
                            MENSAJE = "No puede dejar el campo de Dirección vacío.";
                            TIPO_MENSAJE = "2";
                        }
                        if(varValiVacioTxtMail > 0) {
                            MENSAJE = "No puede dejar el campo de Email vacío.";
                            TIPO_MENSAJE = "2";
                        }
    //                    if(varValiVacioTxtTel > 0) {
    //                        MENSAJE = "No puede dejar el campo de Teléfono vacío.";
    //                        TIPO_MENSAJE = "2";
    //                    }
    //                    if(varValiVacioTxtTel > 0) {
    //                        MENSAJE = "No puede dejar el campo de Celular vacío.";
    //                        TIPO_MENSAJE = "2";
    //                    }
                        if(varValiVacioTxtApe > 0) {
                            MENSAJE = "No puede dejar el campo de Apellido vacío.";
                            TIPO_MENSAJE = "2";
                        }
                        if(varValiVacioTxtNom > 0) {
                            MENSAJE = "No puede dejar el campo de Nombre vacío.";
                            TIPO_MENSAJE = "2";
                        }
                        if(varValiVacioTxtCinro > 0) {
                            MENSAJE = "No puede dejar el campo de Nro. Cédula vacío.";
                            TIPO_MENSAJE = "2";
                        }

                    } else if ((PAC_CANT_HIJOS != null || !(PAC_CANT_HIJOS.isEmpty())) && (Integer.parseInt(PAC_CANT_HIJOS) < 0)) {
                        MENSAJE = "No puede tener la cantidad de hijos en números negativos.";
                        TIPO_MENSAJE = "2";

                    } else if(metodos.ctrlExistePersona(PAC_CINRO, PAC_IDCLINICA, "") == true && idPersonaPaciente.isEmpty()) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT A UN CLIENTE QUE YA EXISTE - PARA SABER QUE SE QUIERE HACER UN INSERT EL IDPERSONA DEL CLIENTE DEBE ESTAR VACIO Y NO CARGADO 
                        MENSAJE = "El Paciente con Nro. de CI "+PAC_CINRO+" ya existe.";
                        TIPO_MENSAJE = "2";
                        
                    } else if(request.getPart("PacFile") != null && ctrlNameFile(sesionDatosUsuario, idPersonaPaciente, path_destino, (request.getPart("PacFile").getSubmittedFileName())) == true
                      ) { // VALIDACION PARA EVITAR QUE SE QUIERA GUARDAR UNA IMAGEN CON EL MISMO NOMBRE YA QUE NO SE REEMPLAZAN LAS IMAGENES EXISTENTES EN LA CARPETA // OBSERVACION: HAY CODIGO COMENTADO ABAJO Y EN EL METODO AGREGANDO EL IDPERSONA PARA CREAR UNA CARPETA CON EL IDPERSONA Y ADENTRO GUARDAR LA IMAGEN ASI PARA EVITAR CONFLICTOS CON OTRAS IMAGENES PERO AL HACER UN DEPLOY Y CREAR UNA NUEVA CARPETA EN EL PROYECTO EL SERVER NO ME CARGA LA IMAGEN PARA MOSTRAR AUNQUE LA IMAGEN EXISTA, SALTA UN ERROR 404 
                        MENSAJE = "Ya existe una imagen con el mismo nombre, debería de cambiar el nombre de la imagen para poder guardar.";
                        TIPO_MENSAJE = "2";
                        
//                        sesionDatosUsuario.setAttribute("CP_PAC_SEGUNDO_MENSAJE", "");
    //                    
    //                } else if(metodos.ctrlExisteUsuCli(CLI_LOGIN, idPersonaPaciente) == true) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT O UPDATE CON EL NOMBRE DE UN USUARIO QUE YA EXISTE PARA EVITAR QUE DOS CLIENTES TENGAN EL MISMO USUARIO 
    //                    MENSAJE = "Ya existe otro usuario con el mismo nombre de usuario, seleccione otro.";
    //                    TIPO_MENSAJE = "2";
    //                    
                    } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".       ----ELSE----CONTROLADOR--------GUARDAR-------------");
                        System.out.println(".");
                        System.out.println(".");
//                        acceso = "add_paciente.htm";
//                        MENSAJE = "Se guardo.";
//                        TIPO_MENSAJE = "1";
                        //--------------------------------------------------------------
                        // CONTROLO EL IDPERSONA DESDE ACA Y SI YA TIENE NO RECUPERO NADA Y SI ESTA VACIO ENTONCES YA RECUPERO CON EL METODO QUE USO EN EL MODELO Y AL MODELO LE PASARIA EN OTRA VARIABLE (IDPERSONA_NEW) DEL BEAN PARA QUE NO SE CONFUNDA Y EN VEZ DE HACER UN INSERT HAGA UN UPDATE 
                        //--------------------------------------------------------------
                        String IDPERSONA_NEW = "", IDPERSONA_PATH;
                        if (idPersonaPaciente == null || idPersonaPaciente.isEmpty() || idPersonaPaciente.equals("")) {
                            IDPERSONA_NEW = metodos.cargarNewIdPersona();
                            IDPERSONA_PATH = IDPERSONA_NEW;
                        } else {
                            IDPERSONA_PATH = idPersonaPaciente;
                            IDPERSONA_NEW = idPersonaPaciente; // LE CARGO CON EL VALOR DEL IDPERSONA PARA PODER RECUPERAR LOS DATOS DEL PACIENTE PARA VER EL EXPEDIENTE EN CASO DE QUE TODO SE EJECUTE CORRECTAMENTE.-
                        }
                        System.out.println("_   _CTRL___ID_PERSONA_NEW:   :"+IDPERSONA_NEW);
                        //-----------------------------------------------------------------------------------------------
                        // TRASLADO LA IMAGEN A LA CARPETA PARA RECUPERAR EL PATH Y DE AHI GUARDAR EN LA VARIABLE 
                        //-----------------------------------------------------------------------------------------------
    //                    String PATH_PHOTO = "";
    //                    String file_separador =  File.separator;
    //                    String path_destino = metodosIniSes.getPathImgLocation();
    ////                    String path_destino = "C:*OdontoAppWeb*web*recursos*static*";
                        File PATH_DESTINO = new File(path_destino.replace("*", file_separador));
                        System.out.println("__PATH_DESTINO:   :"+PATH_DESTINO);
                        // recupero el file 
                        Part part = request.getPart("PacFile");
                        String file_name = part.getSubmittedFileName();
                        if (part == null) {
                            System.out.println("No ha seleccionado ninguna imagen.");
                            return;
                        } else {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("__  ___FILE_NAME:    :"+file_name);
                            String IMAGEN_JSP = String.valueOf(request.getParameter("TAPI"));
                            String IMAGEN_PATH_ABS_JSP = String.valueOf(request.getParameter("TAPIPA"));
                            System.out.println("__  ___IMAGEN_JSP:   :"+IMAGEN_JSP);
                            System.out.println("__  ___IMAGEN_PATH_ABS_JSP:   :"+IMAGEN_PATH_ABS_JSP);
                            if (file_name.equals(IMAGEN_JSP) || (file_name == null || file_name.isEmpty())) {
                                System.out.println("_________IF_________IMG_DB_==_IMG_JSP________");
                                NAME_IMAGEN = IMAGEN_JSP;
                                PATH_IMAGEN = IMAGEN_PATH_ABS_JSP;
                            } else {
                                System.out.println("_________ELSE_________IMG_DB_!=_IMG_JSP________");
                                File pathNewCarp = new File((path_destino.replace("*", file_separador)));
        //                        File pathNewCarp = new File((path_destino.replace("*", file_separador))+IDPERSONA_PATH+file_separador);
                                PATH_IMAGEN = saveFile(part, PATH_DESTINO, pathNewCarp);
                                System.out.println("__________SAVE____________");
                                System.out.println("_FILE_PATH:   :"+PATH_IMAGEN);
                                System.out.println("_FILE_PATH:   :"+PATH_IMAGEN.replace("\\\\","*"));
        //                        System.out.println("_FILE_PATH:   :"+PATH_PHOTO.replace("\\","*"));
                                System.out.println("__________________________");
                                System.out.println("_FILE_NAME:   :"+file_name);
                                System.out.println("__________________________");
                                NAME_IMAGEN = file_name;
                            }
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                        }
                        //-----------------------------------------------------------------------------------------------

                        String RP_IDPERSONA = idPersonaPaciente;
                        String RP_NOMBRE = PAC_NOMBRE;
                        String RP_APELLIDO = PAC_APELLIDO;
                        String RP_CINRO = PAC_CINRO;
                        String RP_TIPODOC = "CI";
                        String RP_IDCATEGORIA_PERSONA = modeloPerfil.getIdPerfilPaciente(); // 1 : ADMINISTRADOR / 2 : FUNCIONARIO / 3 : SECRETARIO / 4 : PACIENTE / 5 : MEDICO  
                        String RP_DESC_CATEG_PERSONA = modeloPerfil.getDescPerfilPaciente();
    //                    String RP_DESC_CATEG_PERSONA = metodosIniSes.recuperarDescPerfil(RP_IDCATEGORIA_PERSONA);
                        String RP_RAZON_SOCIAL = PAC_RAZON_SOCIAL;
                        String RP_RUC = PAC_RUC;
                        String RP_IDBARRIO = "0";
                        String RP_DESC_BARRIO = "(NO/DEFINIDO)";
                        String RP_IDCIUDAD = PAC_IDCIUDAD;
                        String RP_DESC_CIUDAD = metodosRefCiudad.traerDescCiudad(PAC_IDCIUDAD);
                        String RP_DIRECCION = PAC_DIRECCION;
                        String RP_EMAIL = PAC_EMAIL;
                        String RP_TELFPARTICULAR = PAC_TELEFONO;
                        String RP_TELFMOVIL = PAC_CELULAR;
                        String RP_IDCIUDADNAC = "0";
                        String RP_DESC_CIUDADNAC = "(NO/DEFINIDO)";
                        String RP_FECHANAC = PAC_FECHANAC;
                        String RP_SEXO = PAC_SEXO;
                        String RP_RELIGION = "N";
                        String RP_ESTADOCIVIL = PAC_ESTADOCIVIL;
                        String RP_GRUPOSANGUINEO = "";
                        String RP_OBSERVACION = PAC_OTROS_DATOS;
                        String RP_FECHAALTA = metodosIniSes.traerFechaHoraHoy();
                        String RP_FECULTIMOMOV = null;
    //                    String RP_FOTO = "";
                        String RP_FOTO = NAME_IMAGEN;
                        String RP_USU_ALTA = idPersona;
                        String RP_USU_MOD = "0";
                        String RP_IDPAIS = "0";
                        String RP_DESC_PAIS = "(NO/DEFINIDO)";
                        String RP_TELEFPARTICULAR = "";
                        String RP_ANTECEDENTES = "";
                        String RP_EXPEDIENTE_CLINICO = "";
                        String RP_IDCLINICA = PAC_IDCLINICA;
                        String RP_OCUPACION = PAC_OCUPACION;
                        String RP_SEGURO_MEDICO = PAC_SEGURO_MEDICO;
                        String RP_TIENE_HIJOS = PAC_TIENE_HIJOS;
                        String RP_CANT_HIJOS = PAC_CANT_HIJOS;
    //                    String RP_FOTO_PATH_ABS = (path_destino+file_name); // no guarda la subcarpeta de idpaciente ya que en el metodo le creo o controlo si ya existe 
                        String RP_FOTO_PATH_ABS = PATH_IMAGEN.replace("\\\\","*");
                        String RP_IDPERSONA_NEW = IDPERSONA_NEW;
                        // USUARIO 
                        String SU_IDUSUARIO = idUsuarioPaciente;
                        String SU_IDPERSONA = RP_IDPERSONA;
                        String SU_USUARIO = PAC_LOGIN;
                        String SU_CLAVE = PAC_CLAVE;
                        String SU_ESTADO = PAC_USU_ESTADO;
                        String SU_IDPERFIL = RP_IDCATEGORIA_PERSONA; // idperfil = 4 (PACIENTE)
                        String SU_DESC_PERFIL = RP_DESC_CATEG_PERSONA;
                        String SU_EMAIL = PAC_EMAIL;
                        String SU_ESTADO_MIGRAR = "NO";
                        String SU_WEB = "0";
                        String SU_CONFIR_EMAIL = "NO";

                        // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL PACIENTE 
                        TIPO_MENSAJE = metodos.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS, RP_FOTO_PATH_ABS, RP_IDPERSONA_NEW, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
                        if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                            MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                            // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                            if (idPersonaPaciente == null || idPersonaPaciente.isEmpty() || idPersonaPaciente.equals("")) {
                                acceso = "add_paciente.htm";
                            } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
                                acceso = "edit_paciente.htm";
                            }
                        } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                            // COLOQUE ESTE IF PARA QUE EN CASO DE QUE LA PAGINA DE AGENDAMIENTO REDIRECCIONE A "AÑADIR PACIENTE" ENTONCES AL GUARDAR LE VUELVA A REDIRECCIONA A LA PAGINA DE AGENDAMIENTO PARA QUE EL USUARIO PUEDA CONTINUAR CARGANDO LOS DATOS 
                            if (btn_volver_atras == null) {
                                MENSAJE = "Se ha realizado correctamente la operación.";
                                acceso = "paciente.htm";

                            } else if (btn_volver_atras.equals("AGEN")) {
                                acceso = "add_agend.htm";
                                sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", ""); // LE PASO VACIO PARA QUE NO MANTENGA EN LA SESION EL ULTIMO DATO CARGADO 
                                ModelRef_Clinica metodosClinica = new ModelRef_Clinica();
                                String desc_clinica = metodosClinica.traerDescClinica(PAC_IDCLINICA);
                                System.out.println("_   _DESC_CLINICA:    :"+desc_clinica);
                                request.setAttribute("CA_MENSAJE", "Se ha registrado correctamente el paciente en la clinica "+desc_clinica+".");
                                request.setAttribute("CA_TIPO_MENSAJE", "1");
                                String IDPACIENTE_RECIENTE = metodos.traerIdPacCINRO(sesionDatosUsuario, RP_CINRO);
                                if ((IDPACIENTE_RECIENTE.isEmpty() || IDPACIENTE_RECIENTE.equals("")) == false) { // si esta cargado entonces le paso el idpaciente y sino entonces es porque se cargo en otra clinica 
                                    request.setAttribute("CA_AGEN_IDPACIENTE", IDPACIENTE_RECIENTE);
    //                                request.setAttribute("CM_FILTER_NRO_CI", PAC_CINRO);
                                }

                            } else {
                                MENSAJE = "Se ha realizado correctamente la operación.";
                                acceso = "paciente.htm";
                            }
                            idPersonaPaciente = IDPERSONA_NEW;
                            // REDIRIJIR A LA PAGINA DE "VER EXPEDIENTE" PARA RECARGAR LA IMAGEN CUANDO LE DE CLIC AL BOTON DE "VOLVER ATRAS" PARA DIRIJIRSE A LA PAGINA PRINCIPAL DE PACIENTE.-
                            if (acceso.equalsIgnoreCase("paciente.htm")) {
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println("____IF_________PACIENTE_PAG__IS_TRUE_____");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".   ______ID_PERSONA_PACIENTE:  :"+idPersonaPaciente);
                                System.out.println(".");
                                System.out.println(".");
                                // LISTA PARA MOSTRAR LOS DATOS DEL PACIENTE EN EL EXPEDIENTE.-
                                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                                if (request.getAttribute("CP_PAC_LISTA_DATOS") == null) {
                                    LISTA_DATOS = metodos.traerDatosPersona(idPersonaPaciente);
                                } else {
                                    LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                                }
                                // LISTA PARA MOSTRAR LOS DATOS DE LA ULTIMA CONSULTA.-
                                List<BeanFichaAtePaciente> LISTA_ULTIMOS_DATOS_FICHA = new ArrayList<>();
                                if (request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA") == null) {
                                    LISTA_ULTIMOS_DATOS_FICHA = metodosFicha.getUltimaFicha(idPersonaPaciente);
                                } else {
                                    LISTA_ULTIMOS_DATOS_FICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                                }
                                acceso = "ver_paciente.htm";
                                sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "VER_PAC");
                                request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                                request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_ULTIMOS_DATOS_FICHA);
                            }
                        }
                    }
                    // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                    if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                        if (idPersonaPaciente == null || idPersonaPaciente.isEmpty() || idPersonaPaciente.equals("")) {
                            acceso = "add_paciente.htm";
                        } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
                            acceso = "edit_paciente.htm";
                        }
                        // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                        List<BeanPersona> LISTA_DATOS_PAC = new ArrayList<>();
                        BeanPersona bean_persona = new BeanPersona();
                            bean_persona.setRP_CINRO(PAC_CINRO);
                            bean_persona.setRP_NOMBRE(PAC_NOMBRE);
                            bean_persona.setRP_APELLIDO(PAC_APELLIDO);
                            bean_persona.setRP_RAZON_SOCIAL(PAC_RAZON_SOCIAL);
                            bean_persona.setRP_RUC(PAC_RUC);
                            bean_persona.setRP_TELFPARTICULAR(PAC_TELEFONO);
                            bean_persona.setRP_TELFMOVIL(PAC_CELULAR);
                            bean_persona.setRP_DIRECCION(PAC_DIRECCION);
                            bean_persona.setRP_EMAIL(PAC_EMAIL);
                            bean_persona.setRP_IDCIUDAD(PAC_IDCIUDAD);
                            bean_persona.setSU_IDUSUARIO(idUsuarioPaciente);
                            bean_persona.setRP_OCUPACION(PAC_OCUPACION);
                            bean_persona.setRP_FECHANAC(PAC_FECHANAC);
                            bean_persona.setRP_SEXO(PAC_SEXO);
                            bean_persona.setRP_ESTADOCIVIL(PAC_ESTADOCIVIL);
                            bean_persona.setRP_TIENE_HIJOS(PAC_TIENE_HIJOS);
                            bean_persona.setRP_CANT_HIJOS(PAC_CANT_HIJOS);
                            bean_persona.setRP_IDCLINICA(PAC_IDCLINICA);
                            bean_persona.setRP_OBSERVACION(PAC_OTROS_DATOS);
                            if (NAME_IMAGEN.isEmpty() || PATH_IMAGEN.isEmpty()) {
                                bean_persona.setRP_FOTO(PAC_FOTO);
                                bean_persona.setRP_FOTO_PATH_ABS(PAC_FOTO_ABS);
                            } else {
                                bean_persona.setRP_FOTO(NAME_IMAGEN);
                                bean_persona.setRP_FOTO_PATH_ABS(PATH_IMAGEN);
                            }
                        LISTA_DATOS_PAC.add(bean_persona);
                        request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS_PAC);
                        // LE VUELVO A PASAR LA LISTA AUXILIAR POR SI ESTE EDITANDO Y AL DAR UN ERROR LA LISTA SE VAYA CON VALORES NULOS, EN CASO DE QUE IGUAL SALTE UN ERROR O CARGUE LOS DATOS CON NULL, SE TENDRIA QUE VOLVER A CARGAR LAS VARIABLES DE LA LISTA COMO ARRIBA PERO DESDE EL METODO DE TRAER DATOS COMO EN EL EVENTO DE "EDITAR" 
                        List<BeanPersona> LISTA_DATOS_AUX = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS_AUX");
                        request.setAttribute("CP_PAC_LISTA_DATOS_AUX", LISTA_DATOS_AUX);
    //                    request.setAttribute("CP_PAC_NOMBRE", PAC_NOMBRE);
    //                    request.setAttribute("CP_PAC_APELLIDO", PAC_APELLIDO);
    //                    request.setAttribute("CP_PAC_CINRO", PAC_CINRO);
    //                    request.setAttribute("CP_PAC_RAZON_SOCIAL", PAC_RAZON_SOCIAL);
    //                    request.setAttribute("CP_PAC_RUC", PAC_RUC);
    //                    request.setAttribute("CP_PAC_TELEFONO", PAC_TELEFONO);
    //                    request.setAttribute("CP_PAC_DIRECCION", PAC_DIRECCION);
    //                    request.setAttribute("CP_PAC_EMAIL", PAC_EMAIL);
    //                    request.setAttribute("CP_PAC_FECHA_NAC", PAC_FECHANAC);
    //                    request.setAttribute("CP_PAC_SEXO", PAC_SEXO);
    //                    request.setAttribute("CP_PAC_IDCLINICA", PAC_IDCLINICA);
    //                    request.setAttribute("CP_PAC_OCUPACION", PAC_OCUPACION);
    //                    request.setAttribute("CP_PAC_SEGURO_MEDICO", PAC_SEGURO_MEDICO);
    //                    request.setAttribute("CP_PAC_USUARIO", PAC_LOGIN);
    //                    request.setAttribute("CP_PAC_CLAVE", PAC_CLAVE);
    //                    request.setAttribute("CP_PAC_ESTADO", PAC_USU_ESTADO);
                    } else {
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println("___________ELSE_________NO_ERROR___________");
                        System.out.println("______________________VARS_DELETE__________");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        sesionDatosUsuario.removeAttribute("CP_PAC_AUX_FOTO"); // [OBS] ELIMINO LA VARIABLE QUE INGRESO A LA SESION AL AGREGAR O EDITAR UN PACIENTE PARA CONTROLAR EL ARCHIVO.-
                    }
                    var_redireccionar = 1;
                    // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                    /*
                    _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                        ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                        PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                    */
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", idPersonaPaciente);
    //                sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", idUsuarioPaciente);
                    request.setAttribute("CP_MENSAJE", MENSAJE);
                    request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);

                } else if(accion.equalsIgnoreCase("Guardar Antecedentes")) {
                    System.out.println("---------------------__GUARDAR_ANTECEDENTES__--------------------------");
                    idPersonaPaciente = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                    System.out.println("__ID_PACIENTE:   :"+idPersonaPaciente);
                    idUsuarioPaciente = (String) request.getParameter("ID_USUARIO_PACIENTE");
    //                idUsuarioPaciente = (String) sesionDatosUsuario.getAttribute("ID_USUARIO_PACIENTE");
                    System.out.println("__ID_USUARIO_PACIENTE:   :"+idUsuarioPaciente);
                    // ESTA VARIABLE ME SERVIRA PARA PODER REDIRECCIONAR OTRA VEZ A LA PAGINA PRINCIPAL EN CASO DE QUE LA PAGINA DE AÑADIR PACIENTE FUERA REDIRECCIONADA DESDE OTRA PAGINA 
                    String btn_volver_atras = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
                    System.out.println("__PARAM_PAC_BTN_VOLVER_ATRAS:   :"+btn_volver_atras);

                    // RECUPERAR LOS DATOS --- 
                    String PAC_ATENCEDENTES = (String) request.getParameter("tPAF");
                    if (PAC_ATENCEDENTES == null || PAC_ATENCEDENTES.isEmpty()) { PAC_ATENCEDENTES=""; }

                    // IMPRESION DE DATOS --- 
                    System.out.println("-   ------------__DATOS__--------------");
                    System.out.println("-     _PAC_ATENCEDENTES:    :"+PAC_ATENCEDENTES);
                    System.out.println("-   -----------------------------------");

                    // VARIABLES QUE LE CARGO VACIO PARA PASARLE AL OBJETO Y EN EL FUTURO SI QUISIERA EDITAR OTRO CAMPO MAS, TENDRIA QUE CRGAR EL DATO DE ESE CAMPO DENTRO DE UNA DE LAS VARIABLES Y EDITAR EL METODO Y YA ESTARIA YA QUE LE PASO AHORA DATOS VACIOS AL OBJETO  
                    String RP_IDPERSONA = idPersonaPaciente;
                    String RP_NOMBRE = "";
                    String RP_APELLIDO = "";
                    String RP_CINRO = "";
                    String RP_TIPODOC = "CI";
                    String RP_IDCATEGORIA_PERSONA = ""; // 1 : ADMINISTRADOR / 2 : FUNCIONARIO / 3 : SECRETARIO / 4 : PACIENTE / 5 : MEDICO  
                    String RP_DESC_CATEG_PERSONA = "";
                    String RP_RAZON_SOCIAL = "";
                    String RP_RUC = "";
                    String RP_IDBARRIO = "0";
                    String RP_DESC_BARRIO = "(NO/DEFINIDO)";
                    String RP_IDCIUDAD = "0";
                    String RP_DESC_CIUDAD = "(NO/DEFINIDO)";
                    String RP_DIRECCION = "";
                    String RP_EMAIL = "";
                    String RP_TELFPARTICULAR = "";
                    String RP_TELFMOVIL = "";
                    String RP_IDCIUDADNAC = "0";
                    String RP_DESC_CIUDADNAC = "(NO/DEFINIDO)";
                    String RP_FECHANAC = "";
                    String RP_SEXO = "";
                    String RP_RELIGION = "";
                    String RP_ESTADOCIVIL = "N";
                    String RP_GRUPOSANGUINEO = "";
                    String RP_OBSERVACION = "";
                    String RP_FECHAALTA = metodosIniSes.traerFechaHoraHoy();
                    String RP_FECULTIMOMOV = metodosIniSes.traerFechaHoraHoy();
                    String RP_FOTO = "";
                    String RP_USU_ALTA = idPersona;
                    String RP_USU_MOD = idPersona;
                    String RP_IDPAIS = "0";
                    String RP_DESC_PAIS = "(NO/DEFINIDO)";
                    String RP_TELEFPARTICULAR = "";
                    String RP_ANTECEDENTES = PAC_ATENCEDENTES;
                    String RP_EXPEDIENTE_CLINICO = "";
                    String RP_IDCLINICA = "";
                    String RP_OCUPACION = "";
                    String RP_SEGURO_MEDICO = "";
                    String RP_TIENE_HIJOS = "0";
                    String RP_CANT_HIJOS = "0";

                    String MENSAJE=null, TIPO_MENSAJE; // 1 : exito / 2 : error 
                    TIPO_MENSAJE = metodos.guardar_antecedentes(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_DIRECCION, RP_EMAIL, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS));
    //                TIPO_MENSAJE = metodos.guardar_antecedentes(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS));
                    if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                        MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                    } else {
                        MENSAJE = "Se ha actualizado correctamente los antecedentes.";
                    }

                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "ver_paciente.htm";
                    var_redireccionar = 1;
                    sesionDatosUsuario.setAttribute("ID_PACIENTE", idPersonaPaciente);
                    request.setAttribute("CP_MENSAJE", MENSAJE);
                    request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                    // RECUPERO LA LISTA DEL REQUEST Y LA VUELVO A PASAR POR EL REQUEST PARA MANTENERLA EN SESION 
                    List<BeanPersona> LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                    if (LISTA_DATOS == null) {
                        System.out.println("_   ___IF_____LISTA_EXP_NULL____");
                        LISTA_DATOS = metodos.traerDatosPersona(idPersonaPaciente);
                    }
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                    List<BeanFichaAtePaciente> LISTA_DATOS_ULTFICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                    if (LISTA_DATOS_ULTFICHA == null) {
                        System.out.println("_   ___IF_____LISTA_ULT-FICHA_NULL____");
                        LISTA_DATOS_ULTFICHA = metodosFicha.getUltimaFicha(idPersonaPaciente);
                    }
                    request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_DATOS_ULTFICHA);


                } else if(accion.equalsIgnoreCase("Filtrar")) {
                    System.out.println("---------------------__FILTRAR__--------------------------");
                    String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                    System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                    String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                    System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

                    // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                    List<BeanPersona> listaFiltro = new ArrayList<>();
                    listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);

                    // PASAR LA LISTA Y LOS DATOS AL JSP 
                    acceso = "paciente.htm";
                    var_redireccionar = 1;
                    request.setAttribute("CP_CBX_MOSTRAR", filtro_cbxMostrar);
                    request.setAttribute("CP_TXT_BUSCAR", filtro_txtBuscar);
                    request.setAttribute("CP_LISTA_FILTRO", listaFiltro);
                    sesionDatosUsuario.setAttribute("CP_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 

                } else if (esNumero(accion) == true) {
                    System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                    // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                    sesionDatosUsuario.setAttribute("PAG_PAC_LISTA_ACTUAL", accion);
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".   ____NRO_PAGINA_ACTUAL:    :"+accion);
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");

                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")).equals("1")) {
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
                    String PAG_PAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_PAC_CANT_NRO_CLIC == null) {
                        PAG_PAC_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_PAC_CANT_NRO_CLIC);
                    String PAG_PAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_PAC_LISTA_ACTUAL);

                    PAG_PAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_PAC_CANT_NRO_CLIC)+1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_PAC_LISTA_ACTUAL);

                    sesionDatosUsuario.setAttribute("PAG_PAC_LISTA_ACTUAL", PAG_PAC_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_PAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_PAC_CANT_NRO_CLIC)+1));

                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")).equals("1")) {
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
                    String PAG_PAC_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                    if (PAG_PAC_CANT_NRO_CLIC == null) {
                        PAG_PAC_CANT_NRO_CLIC = "1";
                    }
                    System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_PAC_CANT_NRO_CLIC);
                    String PAG_PAC_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL");
                    System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_PAC_LISTA_ACTUAL);

                    PAG_PAC_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_PAC_CANT_NRO_CLIC)-1)*3)-2);
                    System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_PAC_LISTA_ACTUAL);

                    sesionDatosUsuario.setAttribute("PAG_PAC_LISTA_ACTUAL", PAG_PAC_LISTA_ACTUAL);
                    sesionDatosUsuario.setAttribute("PAG_PAC_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_PAC_CANT_NRO_CLIC)-1));

                    // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                    System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                    String band_filtro = (String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO");
                    System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                    // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                    if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")) == null) {
                        System.out.println("_   _ELSE_________null________");
                    } else if (((String)sesionDatosUsuario.getAttribute("CP_BAND_FILTRO")).equals("1")) {
                        System.out.println("_   _IF_____LISTA_CARGADA_____");
                        var_redireccionar = 1;
                        filtrar_pag(sesionDatosUsuario, request, metodos);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");

                } else if (accion.equalsIgnoreCase("Generar Nro. Temporal")) {
                    System.out.println("---------------------__GENERAR-NRO-TEMPORAL-CI__--------------------------");
                    String TXT_CINRO;
                    idUsuarioPaciente = (String) request.getParameter("ID_USUARIO_PACIENTE");
                    String TXT_NOMBRE = (String) request.getParameter("tN");
                    String TXT_APELLIDO = (String) request.getParameter("tA");
    //                String TXT_RAZON_SOCIAL = (String) request.getParameter("tRS");
    //                String TXT_RUC = (String) request.getParameter("tR");
                    String TXT_TELEFONO = (String) request.getParameter("tTP");
                    String TXT_CELULAR = (String) request.getParameter("tCP");
                    String TXT_DIRECCION = (String) request.getParameter("tD");
                    String TXT_EMAIL = (String) request.getParameter("tE");
                    String TXT_ESTADOCIVIL = (String) request.getParameter("cECP");
                    String TXT_IDCIUDAD = (String) request.getParameter("cCI");
                    String TXT_IDCLINICA = (String) request.getParameter("cCli");
                    String TXT_FECHA_NAC = (String) request.getParameter("tFN");
                    String TXT_SEXO = (String) request.getParameter("cSe");
                    String TXT_OCUPACION = (String) request.getParameter("tPP");
    //                String TXT_SEGURO_MEDICO = (String) request.getParameter("tSM");
                    String TXT_TIENE_HIJOS = (String) request.getParameter("cbxTHP");
                    String TXT_CANT_HIJOS = (String) request.getParameter("TCHP");
                    String TXT_OTROS_DATOS = (String) request.getParameter("TPOD");

                    System.out.println("-     ------_VARIABLES_----------------------");
                    System.out.println("-     _idUsuarioPaciente:   :"+idUsuarioPaciente);
                    System.out.println("-     _      NOMBRE:   :"+TXT_NOMBRE);
                    System.out.println("-     _    APELLIDO:   :"+TXT_APELLIDO);
    //                System.out.println("-     _RAZON_SOCIAL:   :"+TXT_RAZON_SOCIAL);
    //                System.out.println("-     _         RUC:   :"+TXT_RUC);
                    System.out.println("-     _    TELEFONO:   :"+TXT_TELEFONO);
                    System.out.println("-     _   DIRECCION:   :"+TXT_DIRECCION);
                    System.out.println("-     _       EMAIL:   :"+TXT_EMAIL);
                    System.out.println("-     _   FECHA_NAC:   :"+TXT_FECHA_NAC);
                    System.out.println("-     _        SEXO:   :"+TXT_SEXO);
                    System.out.println("-     ---------------------------------------");

                    // GENERO EL NUMERO ALEATORIO CON LA CLASE RANDOM 
                    TXT_CINRO = metodos.generar_nro_random();
                    System.out.println("_   _NRO_CI_RANDOM:   :"+TXT_CINRO);

                    // PASO LAS VARIABLES AL JSP
                    acceso = "add_paciente.htm"; // OBS.: SOLO AL AÑADIR UN NUEVO PACIENTE SE PODRA UTILIZAR EL BOTON DE GENERAR NRO TEMPORAL PORQUE NO TIENE SENTIDO QUE EL USUARIO SIGA VIENDO EL BOTON CUANDO EDITE LOS DATOS DE UN PACIENTE 
                    var_redireccionar = 1;
                    sesionDatosUsuario.setAttribute("CP_PAC_AUX_FOTO", String.valueOf(request.getAttribute("CP_PAC_AUX_FOTO"))); // [OBS] PARA NO PERDER LA VARIABLE DE LA SESION LA RECUPERO Y LA VUELVO PASAR - // [OBS] CARGO A LA SESION LA VARIABLE PARA LUEGO USARLO EN EL EVENTO DE MODIFICAR.-
                    List<BeanPersona> LISTA_DATOS_PAC = new ArrayList<>();
                    BeanPersona bean_persona = new BeanPersona();
                        bean_persona.setRP_CINRO(TXT_CINRO);
                        bean_persona.setRP_NOMBRE(TXT_NOMBRE);
                        bean_persona.setRP_APELLIDO(TXT_APELLIDO);
                        bean_persona.setRP_RAZON_SOCIAL(""); // COMO NO LO UTILIZO EN LA PAG ENTONCES LE PASO VALORES VACIOS NOMAS PERO SI LO UTILIZARA ENTONCES DESCOMENTAR LA VARIABLE DE ARRIBA Y BORRAR ESTAS DOS LINEAS.-
                        bean_persona.setRP_RUC("");
    //                    bean_persona.setRP_RAZON_SOCIAL(TXT_RAZON_SOCIAL);
    //                    bean_persona.setRP_RUC(TXT_RUC);
                        bean_persona.setRP_TELFPARTICULAR(TXT_TELEFONO);
                        bean_persona.setRP_TELFMOVIL(TXT_CELULAR);
                        bean_persona.setRP_DIRECCION(TXT_DIRECCION);
                        bean_persona.setRP_EMAIL(TXT_EMAIL);
                        bean_persona.setRP_IDCIUDAD(TXT_IDCIUDAD);
                        bean_persona.setSU_IDUSUARIO(idUsuarioPaciente);
                        bean_persona.setRP_OCUPACION(TXT_OCUPACION);
                        bean_persona.setRP_FECHANAC(TXT_FECHA_NAC);
                        bean_persona.setRP_SEXO(TXT_SEXO);
                        bean_persona.setRP_ESTADOCIVIL(TXT_ESTADOCIVIL);
                        bean_persona.setRP_TIENE_HIJOS(TXT_TIENE_HIJOS);
                        bean_persona.setRP_CANT_HIJOS(TXT_CANT_HIJOS);
                        bean_persona.setRP_IDCLINICA(TXT_IDCLINICA);
                        bean_persona.setRP_OBSERVACION(TXT_OTROS_DATOS);
                        bean_persona.setRP_FOTO("");
                        bean_persona.setRP_FOTO_PATH_ABS("");
                    LISTA_DATOS_PAC.add(bean_persona);
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS_PAC);
    //                request.setAttribute("CP_PAC_NOMBRE", TXT_NOMBRE);
    //                request.setAttribute("CP_PAC_APELLIDO", TXT_APELLIDO);
    //                request.setAttribute("CP_PAC_CINRO", TXT_CINRO);
    //                request.setAttribute("CP_PAC_RAZON_SOCIAL", TXT_RAZON_SOCIAL);
    //                request.setAttribute("CP_PAC_RUC", TXT_RUC);
    //                request.setAttribute("CP_PAC_TELEFONO", TXT_TELEFONO);
    //                request.setAttribute("CP_PAC_DIRECCION", TXT_DIRECCION);
    //                request.setAttribute("CP_PAC_EMAIL", TXT_EMAIL);
    //                request.setAttribute("CP_PAC_FECHA_NAC", TXT_FECHA_NAC);
    //                request.setAttribute("CP_PAC_SEXO", TXT_SEXO);
    //                request.setAttribute("CP_PAC_OCUPACION", TXT_OCUPACION);
    //                request.setAttribute("CP_PAC_SEGURO_MEDICO", TXT_SEGURO_MEDICO);
    ////                request.setAttribute("CP_PAC_USUARIO", TXT_LOGIN);
    ////                request.setAttribute("CP_PAC_CLAVE", TXT_CLAVE);
    ////                request.setAttribute("CP_PAC_ESTADO", TXT_USU_ESTADO);


                    /*
                     * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                        Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                        UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                    */
                } else if (accion.equalsIgnoreCase("Limpiar")) {
                    System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                    var_redireccionar = 1;
                    acceso = "paciente.htm";
                    sesionDatosUsuario.setAttribute("PAG_PAC_CANT_BTN_DERE_CLIC", "1");
                    sesionDatosUsuario.setAttribute("PAG_PAC_LISTA_ACTUAL", "1");
                    sesionDatosUsuario.setAttribute("CP_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 

                } else if (accion.equalsIgnoreCase("Cambiar Contraseña") || accion.equalsIgnoreCase("Cambiar Contrasenha")) {
                    System.out.println("------------------------__CAMBIAR_CONTRASEÑA__------------------------------");
                    var_redireccionar = 1;
                    acceso = "pac_updpass.htm";

                    String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                    System.out.println("_jsp___ID_PACIENTE:  "+ID_PACIENTE);
                    List<BeanPersona> listaDatos;
                    String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_SEGURO_MEDICO="", PAC_OCUPACION="", PAC_ANTECEDENTES_FAMILIARES="";
                    String PAC_USER_ACTUAL="", ACTUAL_PASS="", VAR_NEW_PASS="", VAR_REP_NEW_PASS="";

                    ModelUsuario metodosUsuario = new ModelUsuario();
                    listaDatos = metodosUsuario.traer_datos_idpac(ID_PACIENTE, sesionDatosUsuario);
                    Iterator<BeanPersona> iterCliente = listaDatos.iterator();
                    BeanPersona usuario = null;

                    while(iterCliente.hasNext()) {
                        usuario = iterCliente.next();
    //                            USU_IDPERSONA = usuario.getRP_IDPERSONA();
                        PAC_NOMBRE = usuario.getRP_NOMBRE();
                        PAC_APELLIDO = usuario.getRP_APELLIDO();
                        PAC_CINRO = usuario.getRP_CINRO();
    //                            PAC_RAZON_SOCIAL = usuario.getRP_RAZON_SOCIAL();
    //                            PAC_RUC = usuario.getRP_RUC();
    //                            PAC_TELEFONO = usuario.getRP_TELFMOVIL();
    //                            PAC_DIRECCION = usuario.getRP_DIRECCION();
    //                            PAC_EMAIL = usuario.getRP_EMAIL();
                        PAC_IDUSUARIO = usuario.getSU_IDUSUARIO();
                        PAC_USER_ACTUAL = usuario.getSU_USUARIO();
                        ACTUAL_PASS = usuario.getSU_CLAVE();
    //                            USU_ESTADO = usuario.getSU_ESTADO();
    //                            USU_PERFIL = usuario.getSU_DESC_PERFIL();
    //                            USU_IDCLINICA = usuario.getRP_IDCLINICA();
    //                            // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
    //                            USU_NOMBRE_ORI = usuario.getRP_NOMBRE();
    //                            USU_APELLIDO_ORI = usuario.getRP_APELLIDO();
    //                            USU_CINRO_ORI = usuario.getRP_CINRO();
    //                            USU_RAZON_SOCIAL_ORI = usuario.getRP_RAZON_SOCIAL();
    //                            USU_RUC_ORI = usuario.getRP_RUC();
    //                            USU_TELEFONO_ORI = usuario.getRP_TELFMOVIL();
    //                            USU_DIRECCION_ORI = usuario.getRP_DIRECCION();
    //                            USU_EMAIL_ORI = usuario.getRP_EMAIL();
    //                            USU_IDUSUARIO_ORI = usuario.getSU_IDUSUARIO();
    //                            USU_USUARIO_ORI = usuario.getSU_USUARIO();
    //                            USU_CLAVE_ORI = usuario.getSU_CLAVE();
    //                            USU_ESTADO_ORI = usuario.getSU_ESTADO();
    //                            USU_PERFIL_ORI = usuario.getSU_DESC_PERFIL();
                    } // end while 

                    // LE PASO ESTA VARIABLE PARA UTILIZARLO COMO BANDERA, PARA QUE SE LE HABILITE UN CAMBIO A LA CONTRASEÑA CADA VEZ QUE PRESIONE EL BOTON "CAMBIAR CONTRASEÑA" QUE INGRESA A ESTE EVENTO 
                    sesionDatosUsuario.setAttribute("CP_UPD_PASS_BAND_GUARDAR", "1");
                    // PASO LOS CAMPOS QUE RECUPERO AL REQUEST 
                    request.setAttribute("CP_PAC_IDUSUARIO", PAC_IDUSUARIO);
                    request.setAttribute("CP_PAC_NOMBRE", PAC_NOMBRE);
                    request.setAttribute("CP_PAC_APELLIDO", PAC_APELLIDO);
                    request.setAttribute("CP_PAC_CINRO", PAC_CINRO);
                    request.setAttribute("CP_PAC_USUARIO_ACTUAL", PAC_USER_ACTUAL);
                    request.setAttribute("CP_PAC_ACTUAL_PASSWORD", "");// LE PASO VACIO Y NO CON LA CONTRASEÑA ACTUAL YA QUE EL USUARIO DEBERA DE CARGAR PRIMERAMENTE SU CONTRASEÑA ACTUAL PARA CONFIRMAR QUE ES EL PROPIETARIO 
    //                request.setAttribute("CP_PAC_ACTUAL_PASSWORD", ACTUAL_PASS);
                    request.setAttribute("CP_PAC_NEW_PASSWORD", ""); // VACIO PORQUE ESTA INGRESANDO A LA PAGINA NO SE ESTA RECUPERANDO NADA 
                    request.setAttribute("CP_PAC_REPEAT_NEW_PASSWORD", "");

                } else if (accion.equalsIgnoreCase("Guardar CambioPass")) {
                    System.out.println("------------------------__GUARDAR_CAMBIOS_DE_PASSWORD__------------------------------");
                    String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                    System.out.println("_jsp___ID_PACIENTE:  "+ID_PACIENTE);

                    // OBSERVACION: CONDICIONAL QUE UTILIZO PARA EVITAR QUE AL GUARDAR Y RECARGAR LA PAGINA SALTE LA VALIDACION DE QUE LA CONTRASEÑA ACTUAL NO SEA LA MISMA POR ACTUALIZARLA Y YA NO SER LA ANTERIOR / POR ESO AGREGUE ESTA CONDICIONAL PARA EVITAR UNA CONFUCION PARA EL USUARIO, Y PERMITIRLE QUE CUANDO PRESIONE EL BOTON PARA CAMBIAR LA CONTRASEÑA SE HABILITE ESTA BANDERA PARA CAMBIAR LA CONTRASEÑA Y LUEGO REFRESCAR LA PAGINA DESDE ESTA CONDICIONAL CUANDO EL USUARIO ACTUALICE LA PAGINA (ESTA SITUACION SE DA CUANDO SE RECARGA LA PAGINA LUEGO DE ACTUALIZAR LA CONTRASEÑA)  
                    if (String.valueOf(sesionDatosUsuario.getAttribute("CP_UPD_PASS_BAND_GUARDAR")).equals("1")) {
                        acceso = "pac_updpass.htm";
                        int var_vali_vacio_tap  = 0;
                        int var_vali_vacio_tnp  = 0;
                        int var_vali_vacio_trnp  = 0;

                        // RECUPERAMOS LOS CAMPOS DE TEXTOS Y LOS DATOS DEL PACIENTE 
                        String PAC_IDUSUARIO = request.getParameter("tIUP");
                        String PAC_IDPACIENTE = request.getParameter("tI");
                        String PAC_NOMBRE = request.getParameter("tPN");
                        String PAC_APELLIDO = request.getParameter("tPA");
                        String PAC_CINRO = request.getParameter("tPC");
                        String TXT_USER_ACTUAL = request.getParameter("tUA");
                        if (TXT_USER_ACTUAL == null || TXT_USER_ACTUAL.isEmpty()) {TXT_USER_ACTUAL = "";}
                        String TXT_PASS_ACTUAL = request.getParameter("tAP");
                        if (TXT_PASS_ACTUAL == null || TXT_PASS_ACTUAL.isEmpty()) { var_vali_vacio_tap = 1; TXT_PASS_ACTUAL = ""; }
                        String TXT_PASS_NEW = request.getParameter("tNP");
                        if (TXT_PASS_NEW == null || TXT_PASS_NEW.isEmpty()) { var_vali_vacio_tnp = 1; TXT_PASS_NEW = ""; }
                        String TXT_PASS_NEW_REP = request.getParameter("tRNP");
                        if (TXT_PASS_NEW_REP == null || TXT_PASS_NEW_REP.isEmpty()) { var_vali_vacio_trnp = 1; TXT_PASS_NEW_REP = ""; }

                        // IMPRESION DE LOS CAMPOS 
                        System.out.println("-     ------_VARIABLES_----------------------");
                        System.out.println("-     _      ID_PAC:   :"+PAC_IDPACIENTE);
                        System.out.println("-     _      ID_USU:   :"+PAC_IDUSUARIO);
                        System.out.println("-     _      NRO_CI:   :"+PAC_CINRO);
                        System.out.println("-     _      NOMBRE:   :"+PAC_NOMBRE);
                        System.out.println("-     _    APELLIDO:   :"+PAC_APELLIDO);
                        System.out.println("-     _TXT_USER_ACTUAL:   :"+TXT_USER_ACTUAL);
                        System.out.println("-     _TXT_PASS_ACTUAL:   :"+TXT_PASS_ACTUAL);
                        System.out.println("-     _   TXT_PASS_NEW:   :"+TXT_PASS_NEW);
                        System.out.println("-     _TXT_PASS_NEW_REP:  :"+TXT_PASS_NEW_REP);
                        System.out.println(".   .   .   .__ var_vali_vacio_tap;    :"+var_vali_vacio_tap);
                        System.out.println(".   .   .   .__ var_vali_vacio_tnp;    :"+var_vali_vacio_tnp);
                        System.out.println(".   .   .   .__ var_vali_vacio_trnp;   :"+var_vali_vacio_trnp);
                        System.out.println("-     ---------------------------------------");

                        List<BeanUsuario> ctrlUsuario = null;
                        String MENSAJE=null, TIPO_MENSAJE;
                        if (var_vali_vacio_tap > 0) {
                            MENSAJE = "No puede dejar vacío el campo de \"Contraseña Actual\".";
                            TIPO_MENSAJE = "2";
                        } else if (var_vali_vacio_tnp > 0) {
                            MENSAJE = "No puede dejar vacío el campo de \"Nueva Contraseña\".";
                            TIPO_MENSAJE = "2";
                        } else if (var_vali_vacio_trnp > 0) {
                            MENSAJE = "No puede dejar vacío el campo de \"Repetir Contraseña\".";
                            TIPO_MENSAJE = "2";
                        } else {
                            // hago un control del usuario - en donde el metodo retornara en caso de que exista usuario 
                            ctrlUsuario = metodosIniSes.controlUsuario(new BeanInicioSesion(TXT_USER_ACTUAL, TXT_PASS_ACTUAL));
                            if (ctrlUsuario == null) {
                                MENSAJE = "La contaseña actual no es correcta.";
                                TIPO_MENSAJE = "2";
                            } else if ((TXT_PASS_NEW.equals(TXT_PASS_NEW_REP))==false) {
                                MENSAJE = "La repetición de la nueva contraseña no coinciden entre ellas.";
                                TIPO_MENSAJE = "2";
                            } else {
                                TIPO_MENSAJE = metodos.updatePasswordActual(TXT_PASS_NEW, PAC_IDUSUARIO, PAC_IDPACIENTE);
                                if (TIPO_MENSAJE.equals("2")) { // 2 : error 
                                    MENSAJE = "Salto un error, vuelva a intentarlo más tarde.";
                                } else {
                                    MENSAJE = "Se ha cambiado correctamente su contraseña.";
                                    String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                                    System.out.println(".   _CP__ID_PERFIL_USER:        :"+IDPERFIL_USER);
                                    // SI EL PERFIL ES DE PACIENTE, ENTONCES LE CARGO OTRO ACCESO YA QUE EL PACIENTE NO TIENE ACCESO EN UNA URL 
                                    if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // PAC 
                                        acceso = "perfil_pac.htm";
                                    } else { // ADMIN / SECRE 
                                        acceso = "ver_paciente.htm";
                                    }
    //                                acceso = "ver_paciente.htm";
                                    // SI O SI AL CAMBIAR LA CONTRASEÑA VA A INGRESAR A ESTE METODO, ENTONCES AL ACTUALIZAR LA CONTRASEÑA LE CAMBIO EL VALOR A LA BANDERA QUE UTILIZO EN LA SESION 
                                    sesionDatosUsuario.setAttribute("CP_UPD_PASS_BAND_GUARDAR", "0");
                                }
                            }
                        }

                        // LE PASO LAS VARIABLES AL JSP 
                        var_redireccionar = 1;
        //                acceso = "ver_paciente.htm";
                        sesionDatosUsuario.setAttribute("ID_PACIENTE", ID_PACIENTE);
                        // LE PASO LAS VARIABLES QUE USA EL JSP SOLO SI DIO ERROR PORQUE SEGUIRA EN LA PAGINA PERO SI NO SALTO NINGUN ERROR O VALIDACION PUES NO ES NECESARIO PASARLE LAS VARIABLES 
                        if (TIPO_MENSAJE.equals("2")) {
                            request.setAttribute("CP_UP_MENSAJE", MENSAJE);
                            request.setAttribute("CP_UP_TIPO_MENSAJE", TIPO_MENSAJE);
                            request.setAttribute("CP_PAC_IDUSUARIO", PAC_IDUSUARIO);
                            request.setAttribute("CP_PAC_NOMBRE", PAC_NOMBRE);
                            request.setAttribute("CP_PAC_APELLIDO", PAC_APELLIDO);
                            request.setAttribute("CP_PAC_CINRO", PAC_CINRO);
                            request.setAttribute("CP_PAC_USUARIO_ACTUAL", TXT_USER_ACTUAL);
                            request.setAttribute("CP_PAC_ACTUAL_PASSWORD", TXT_PASS_ACTUAL);
                            request.setAttribute("CP_PAC_NEW_PASSWORD", TXT_PASS_NEW);
                            request.setAttribute("CP_PAC_REPEAT_NEW_PASSWORD", TXT_PASS_NEW_REP);
                        } else if (TIPO_MENSAJE.equals("1")) {
                            request.setAttribute("CP_MENSAJE", MENSAJE);
                            request.setAttribute("CP_TIPO_MENSAJE", TIPO_MENSAJE);
                            // RECUPERO LA LISTA DEL REQUEST Y LA VUELVO A PASAR POR EL REQUEST PARA MANTENERLA EN SESION 
                            List<BeanPersona> LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                            if (LISTA_DATOS == null) {
                                System.out.println("_   ___IF_____LISTA_EXP_NULL____");
                                LISTA_DATOS = metodos.traerDatosPersona(ID_PACIENTE);
                            }
                            request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                            List<BeanFichaAtePaciente> LISTA_DATOS_ULTFICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                            if (LISTA_DATOS_ULTFICHA == null) {
                                System.out.println("_   ___IF_____LISTA_ULT-FICHA_NULL____");
                                LISTA_DATOS_ULTFICHA = metodosFicha.getUltimaFicha(ID_PACIENTE);
                            }
                            request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_DATOS_ULTFICHA);
                        }
                    } // END IF BAND_UPDATE_PASS 
                    else {
                        var_redireccionar = 1;
                        // RECUPERO EL IDPERFIL PARA DETERMINAR QUE PAGINA DEBERIA VER EL USUARIO 
                        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                        System.out.println(".   _CP__ID_PERFIL_USER:        :"+IDPERFIL_USER);
                        // SI EL PERFIL ES DE PACIENTE, ENTONCES LE CARGO OTRO ACCESO YA QUE EL PACIENTE NO TIENE ACCESO EN UNA URL 
                        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // PAC 
                            acceso = "perfil_pac.htm";
                        } else { // ADMIN / SECRE 
                            acceso = "ver_paciente.htm";
                        }
    //                    acceso = "ver_paciente.htm";
                    }

                } else if (accion.equalsIgnoreCase("Volver Atras")) {
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println(".       __________VOLVER_ATRAS____________");
                    System.out.println(".");System.out.println(".");
                    /*
                        * OBSERVACION: 
                        - COMO LA PAGINA DE AGENDAMIENTO TIENE UN BOTON PARA REDIRECCIONAR A LA PAGINA DE PACIENTE, CUAQUIER OTRA PAGINA TAMBIEN PUEDE TENER ESTA CLASE DE BOTON, 
                          Y POR ESO LE CREE ESTA VARIABLE "PAC_BTN_VOLVER_ATRAS" PARA PODER SABER QUE PAGINA LE REDIRECCIONO A LA PAGINA DE PACIENTE Y ASI PODER VOLVER A ESA PAGINA 
                    */
                    String var_volver_atras = "paciente.htm";
                    String PARAM_BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
                    if (PARAM_BTN_VOLVER_ATRAS == null || PARAM_BTN_VOLVER_ATRAS.isEmpty() || PARAM_BTN_VOLVER_ATRAS.equals("")) { PARAM_BTN_VOLVER_ATRAS = "PAC"; }
                    System.out.println(".    _JSP___PAC_BTN_VOLVER_ATRAS:     :"+PARAM_BTN_VOLVER_ATRAS);

                    if (PARAM_BTN_VOLVER_ATRAS.equals("PAC")) { // CONTROLO QUE VARIABLE TIENE CARGADO EL PARAMETRO QUE RECIBO POR LA SESION PARA PODER SABER QUE PAGINA FUE LA QUE REDIRECCIONO A LA PAGINA DE PACIENTE 
                        var_volver_atras = "paciente.htm";
                    } else if (PARAM_BTN_VOLVER_ATRAS.equals("AGEN")) {
    //                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
                        var_volver_atras = "add_agend.htm";
    //                                        var_volver_atras = urlAgendamiento;
                    } else if (PARAM_BTN_VOLVER_ATRAS.equals("VER_PAC")) {
    //                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
                        var_volver_atras = "ver_paciente.htm";
                        // [OBS] AGREGUE ESTE BLOQUE PARA PODER PASAR LA LISTA CON LOS DATOS PARA EL EXPEDIENTE.-
                        idPersonaPaciente = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                        // RECUPERO LA LISTA DEL REQUEST Y LA VUELVO A PASAR POR EL REQUEST PARA MANTENERLA EN SESION 
                        List<BeanPersona> LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                        if (LISTA_DATOS == null) {
                            System.out.println("_   ___IF_____LISTA_EXP_NULL____");
                            LISTA_DATOS = metodos.traerDatosPersona(idPersonaPaciente);
                        }
                        request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS);
                        List<BeanFichaAtePaciente> LISTA_DATOS_ULTFICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                        if (LISTA_DATOS_ULTFICHA == null) {
                            System.out.println("_   ___IF_____LISTA_ULT-FICHA_NULL____");
                            LISTA_DATOS_ULTFICHA = metodosFicha.getUltimaFicha(idPersonaPaciente);
                        }
                        request.setAttribute("CP_PAC_LISTA_DATOS_ULTFICHA", LISTA_DATOS_ULTFICHA);
                    }
                    var_redireccionar = 1;
                    acceso = var_volver_atras;
    //                sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "PAC");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                }
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("--------------------------ERROR-PACIENTE--------------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            acceso = "paciente.htm";
            var_redireccionar = 0;
            Logger.getLogger(ControllerPaciente.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("------------------------------------------------------------------");
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
    
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelPersona metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
        
        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanPersona> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
        
        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "paciente.htm";
//        var_redireccionar = 1;
        request.setAttribute("CP_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CP_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CP_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("CP_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
    // METODO PARA VALIDAR QUE EL NOMBRE DE LA IMAGEN NO SE ENCUENTRE DENTRO DE LA CARPETA PARA EVITAR ERROR Y QUE NO SEA COPIADA A LA CARPETA Y ASI NO PUEDA MOSTRARLA 
    public boolean ctrlNameFile(HttpSession PARAM_SESION, String IDPERSONA, String path_destino, String NAME_FILE) {
        boolean valor = false;
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("."); //CONTROLAR TAMBIEN LOS EVENTOS PARA QUE NO SE PIERDA LA VARIABLE AUXILIAR QUE USO PARA GUARDAR LA FOTO AUX AL CARGAR UN PACIENTE 
        System.out.println("."); //CONTROLAR EL EVENTO DE NUEVO NRO RANDOM DE CEDULA 
        System.out.println(".");
        System.out.println(".---------------------___CTRL_NAME_FILE___--------------------------.");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   --name_file:  :"+NAME_FILE);
        System.out.println(".   --path-destino:  :"+path_destino);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   __ID_PERSONA:     :"+IDPERSONA);
        
            File PATH_DESTINO = new File(path_destino.replace("*", File.separator));
            System.out.println("______PATH_DESTINO:   :"+PATH_DESTINO);
            
        // SI EL IDPERSONA ESTA CARGADA ENTONCES DEBERIA DE CONTROLAR EL CAMPO OCULTO QUE GUARDA LA IMAGEN QUE TENIA ANTES (AL GUARDAR) 
        String PHOTO_AUX = (String) PARAM_SESION.getAttribute("CP_PAC_AUX_FOTO");
        if (PHOTO_AUX == null || PHOTO_AUX.isEmpty() || PHOTO_AUX.equals("")) { // si no tiene nada entonces es porque no habia foto cargada al crearse el paciente.-
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_____NO_HAY_FOTO_AUXILIAR___________");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            //en este control ya deberia de controlar 
            //        de una forma si es que no tiene foto aux o idpersona porque hay tiene que controlar la carpeta
            //
            int band = 0; // bandera para usar cuando el idpersona esta cargado 
            for (final File ficheroEntrada : PATH_DESTINO.listFiles()) {
                System.out.println(".");
                System.out.println(".");
                System.out.println("------------__FOR__------------");
                System.out.println(".   for:1:__"+ficheroEntrada.getName());
                System.out.println(".   for:2:__"+ficheroEntrada.getAbsolutePath());
                System.out.println("_");
                if (ficheroEntrada.isDirectory()) {
                    System.out.println("___IS_DIRECTORY_____");
                    listarFicherosPorCarpeta(ficheroEntrada);
                } else {
                    System.out.println("_____FILE_NAME______");
                    System.out.println(ficheroEntrada.getName());
                    System.out.println("____________________");
                    if (NAME_FILE.equals(ficheroEntrada.getName())) {
                        System.out.println("___IF___        [NOMBRE-DE-LA-IMAGEN-ENCONTRADA] / ["+ficheroEntrada.getName()+"]    ");
    //                    if (IDPERSONA.isEmpty()) { // SI EL IDPERSONA ESTA VACIO ENTONCES ESTA GUARDANDO UN PACIENTE Y EN ESE CASO DEBE DE HABER "UN ENCUENTRO" DE UN CASO NOMAS DE IGUALDAD DE NOMBRE... 
    //                        band = 2;
    //                    } else if (IDPERSONA != "" && band < 2) { // PERO SI IDPERSONA ES DISTINTO AL IDPERSONA VACIO ENTONCES DEBERIA DE HABER "DOS ENCUENTROS" DE IMAGENES PARECIDAS PARA PODER SALTAR LA VALIDACION, PORQUE UNA SERA POR EL MISMO IDPACIENTE Y OTRA SERIA POR OTRO QUE EN ESE CASO SI TENDRIAMOS QUE SOLTAR LA VALIDACION.-
    //                        band = band + 1;
    //                    }
                        valor = true;
                        break;

                    } else {
                        System.out.println("___ELSE___");
                    }
                } // end-else-ctrl-directory-
    //            if (band == 2) {
    //                valor = true;
    //                break;
    //            }
                System.out.println("_   _____________________");
                System.out.println("_");
                System.out.println("_");
                System.out.println("_");
            } // end-for.-
            
        } else {
            System.out.println(".");
            System.out.println(".");
            System.out.println("_____TIENE_VALOR____________");
            System.out.println("___AUX_PHOTO_VAR:  :"+PHOTO_AUX);
            System.out.println(".");
            System.out.println(".");
            //pero en caso de que tenga idpersona y foto aux entonces controlar primeramente la foto aux con la foto de la pag 
            //        y si no son iguales entonces ahi controlar la carpeta para ver qu eno este repetida 
            //
            // [OBS] CONTROLO SI ES QUE SON LOS MISMOS, LA IMAGEN QUE OBTENGO DE LA PAGINA Y LA URL DE LA IMAGEN AUXILIAR 
            if (NAME_FILE == null || NAME_FILE.isEmpty() || NAME_FILE.equals("")) { // SI SE ENCUENTRA VACIA ES PORQUE EL USUARIO NO LE CARGO O SELECCIONO NINGUNA IMAGEN NUEVA PARA ACTUALIZAR/GUARDAR EL PACIENTE.-
                System.out.println(".");
                System.out.println(".");
                System.out.println("___IF_____________-NO_FUE_SELECCIONADA_NINGUNA_IMAGEN_AL_GUARDAR/MODI_EL_PAC-____");
                System.out.println(".");
                System.out.println(".");
            } else if (PHOTO_AUX.equals(NAME_FILE)) { // SI SON IGUALES ES PORQUE NO MODIFICO LA IMAGEN 
                System.out.println(".");
                System.out.println(".");
                System.out.println("___ELSE-IF_____________-SON_IGUALES-____");
                System.out.println(".");
                System.out.println(".");
            } else {
                System.out.println(".");
                System.out.println(".");
                System.out.println("___ELSE____________-NO_SON_IGUALES-____");
                System.out.println(".");
                System.out.println(".");
                
                int band = 0; // bandera para usar cuando el idpersona esta cargado 
                for (final File ficheroEntrada : PATH_DESTINO.listFiles()) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("------------__FOR__------------");
                    System.out.println(".   for:1:__"+ficheroEntrada.getName());
                    System.out.println(".   for:2:__"+ficheroEntrada.getAbsolutePath());
                    System.out.println("_");
                    if (ficheroEntrada.isDirectory()) {
                        System.out.println("___IS_DIRECTORY_____");
                        listarFicherosPorCarpeta(ficheroEntrada);
                    } else {
                        System.out.println("_____FILE_NAME______");
                        System.out.println(ficheroEntrada.getName());
                        System.out.println("____________________");
                        if (NAME_FILE.equals(ficheroEntrada.getName())) {
                            System.out.println("___IF___        [NOMBRE-DE-LA-IMAGEN-ENCONTRADA] / ["+ficheroEntrada.getName()+"]    ");
        //                    if (IDPERSONA.isEmpty()) { // SI EL IDPERSONA ESTA VACIO ENTONCES ESTA GUARDANDO UN PACIENTE Y EN ESE CASO DEBE DE HABER "UN ENCUENTRO" DE UN CASO NOMAS DE IGUALDAD DE NOMBRE... 
        //                        band = 2;
        //                    } else if (IDPERSONA != "" && band < 2) { // PERO SI IDPERSONA ES DISTINTO AL IDPERSONA VACIO ENTONCES DEBERIA DE HABER "DOS ENCUENTROS" DE IMAGENES PARECIDAS PARA PODER SALTAR LA VALIDACION, PORQUE UNA SERA POR EL MISMO IDPACIENTE Y OTRA SERIA POR OTRO QUE EN ESE CASO SI TENDRIAMOS QUE SOLTAR LA VALIDACION.-
        //                        band = band + 1;
        //                    }
                            valor = true;
                            break;

                        } else {
                            System.out.println("___ELSE___");
                        }
                    } // end-else-ctrl-directory-
        //            if (band == 2) {
        //                valor = true;
        //                break;
        //            }
                    System.out.println("_   _____________________");
                    System.out.println("_");
                    System.out.println("_");
                    System.out.println("_");
                } // end-for.-
            } // end-else-ctrl-var-photo-aux.-
        }
        
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".-------------------------------------------------------------------.");
        return valor;
    }
    
    
    
    
} // END CLASS 