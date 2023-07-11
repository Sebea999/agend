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
import com.agend.javaBean.BeanFichaAtePaciente;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelFichaAtencionPacNutri;
import com.agend.modelo.ModelRef_Servicio;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.io.*;
import java.nio.file.*;
import com.agend.javaBean.BeanFichaAtePacienteDet;
import com.agend.javaBean.BeanPersona;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileItemFactory;
//import org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.fileupload.util.Streams;
//import org.apache.commons.io.FileUtils;

/**
 *
 * @author RYUU
 */
@Controller
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
@WebServlet(name="controllerFichaAtencionPacNutri", urlPatterns="/CFAPSrvN") // CFAPSrvN : controller ficha atencion paciente servlet nutri 
public class ControllerFichaAtencionPacNutri extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/atencion")
    public ModelAndView ficha_atencion_pacienteNutri(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__1__---------CONTROLLER__ATENCION_FICHA-PACIENTE--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "aaaaaaa_leer_coment_→__", request); //crear la pagina para el paciente / OBS.: CREO QUE NO ES NECESARIO YA QUE EL PACIENTE DESDE SU PERFIL VA A PODER VER LAS CONSULTAS Y SUS FICHAS QUE TIENE Y DESDE AHI NOMAS LE CREO UN BOTON PARA QUE PUEDA "VER" Y DESDE ESE BOTON LE REDIRECCIONO Y VA A UTILIZAR LA PAGINA DE VER_FICHA_ATENCION 
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else 
        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER) == true) { //  1 : ADMIN  -  3 : SECRETARIO - 5 : MEDICO 
//          if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER) == true || modeloPerfil.isPerfilMedico(IDPERFIL_USER) || modeloPerfil.isPerfilSecre(IDPERFIL_USER) == true) { //  1 : ADMIN  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFichaAtencionPac_nutri", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagFichaAtencionPac", request);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        // COMBO BOX DE MEDICO Y ESPECIALIDAD 
        request.setAttribute("CFAP_CHECK_FILTRAR_MED", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CFAP_CHECK_FILTRAR_ESPE", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        request.setAttribute("CFAP_CHECK_FILTRAR_PAC", "ON"); // VARIABLE QUE UTILIZO PARA QUE AL ENTRAR A LA PAGINA, LE MARQUE EL CHECK DE "TODOS" DEL FILTRO DE CLIENTE Y NO LO DEJE DESMARCADO PORQUE ESTA VARIABLE SE INICIALIZARA CON NULL SI NO LE CARGO SU VALOR ACA  // OBS_2: PASO POR REQUEST PORQUE QUIERO PASARLE AL PRINCIPIO NOMAS Y QUE LA SESION NO TENGA MANTENIENDO EL DATO EN MEMORIA  
        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", ""); // CONFIGURACION DEL BOTON DE "VOLVER ATRAS" DE LA PAGINA DE VER FICHA DE ATENCION DEL PACIENTE PARA PODER VOLVER A LA PAGINA DE PACIENTE Y NO A LA PAGINA PRINCIPAL DE FICHA DE ATENCION / EN ESTE CASO LO UTILIZO PARA CUANDO ENTRE DESDE LA PAGINA DE FICHA DE ATENCION RETORNE A ESTA MISMA PAGINA Y NO A LA PAGINA ANTERIOR QUE ACTIVO ESTE BOTON 
        
        return mav;
    }
    
    
    
    @RequestMapping("/add_atencion")
    public ModelAndView add_atencion_ficha_pacNutri(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_ATENCION_(FICHA-PACIENTE)------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        // OBSERVACION: 
        // LA PAGINA add_agend SOLO VAN A INGRESAR EL SECRETARIO Y EL ADMINISTRADOR, PERO LE COLOCO EL CONTROL PORQUE EN LA "accion" DEL CALENDARIO AL SELECCIONAR UN DIA, EN EL DO_POST TENGO PARA QUE REDIRECCIONE A "agend" PERO SI ES ADMIN O SECRETARIO ME MANDA A LA PAGINA PRINCIPAL Y PARA EVITAR ESO VOY A REDIRECCIONAR A ESTA PAGINA Y DESDE ACA NOMAS LE AGREGO ESTE CONTROL PARA QUE SI ES PACIENTE LE REDIRECCIONO A LA PAGINA QUE CORRESPONDE Y NO LE LLEGUE A MOSTRAR LA PAGINA QUE SOLO DEBERIA DE VER EL SECRETARIO Y EL ADMIN 
        String paginaMav = "menu_principal";
//        if (modeloPerfil.isPerfilPaciente(IDPERFIL_USER) == true) { // 4 : PACIENTE 
//            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "", request);
//            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        } else if (modeloPerfil.isPerfilAdmin(IDPERFIL_USER)==true || modeloPerfil.isPerfilMedico(IDPERFIL_USER)==true || modeloPerfil.isPerfilSecre(IDPERFIL_USER)==true) { //  1 : ADMIN  -  5 : MEDICO  -  3 : SECRETARIO 
        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER)==true) { //  1 : ADMIN  -  5 : MEDICO  -  3 : SECRETARIO 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_Datos_nutri", request);
            sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_Datos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/ver_atencion")
    public ModelAndView ver_atencion_ficha_pacNutri(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__3__--------CONTROLLER__VER_ATENCION_(FICHA-PACIENTE)------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER)==true || modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // LE AGREGO LA CONSULTA DE QUE SI EL PERFIL ES PACIENTE, PORQUE LA PAGINA DE ATENCION NO ESTA HABILITADA PARA EL PACIENTE PERO SI EL VER_FICHA 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_VerDatos_nutri", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_VerDatos", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    @RequestMapping("/add_atencion_af")
    public ModelAndView add_atencion_addFilesNutri(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // INSTANCIO LA SESION A TRAVES DEL REQUEST PARA PODER RECUPERAR DATOS QUE HAYA CARGADO EN LA SESION 
        HttpSession sesionDatosUsuario = request.getSession();
        
        System.out.println("-----__4__--------CONTROLLER__ADD_ATENCION_(FICHA-PACIENTE)-ADJUNTAR_FILES------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CFAP__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CFAP__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuAtencionFichaPaciente(IDPERFIL_USER)==true || modeloPerfil.isPerfilPaciente(IDPERFIL_USER)) { // LE AGREGO LA CONSULTA DE QUE SI EL PERFIL ES PACIENTE, PORQUE LA PAGINA DE ATENCION NO ESTA HABILITADA PARA EL PACIENTE PERO SI EL VER_FICHA 
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagFichaAtencionPac_Datos_nutri_adfiles", request);
        }
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
    
    
    // METODO PARA LIMPIAR LAS VARIABLES DE LA SESION ANTES DE VOLVER A UTILIZARLAS 
    public void limpiarVarsSession(HttpSession PARAM_SESION) {
        PARAM_SESION.removeAttribute("CFAP_LISTA_FILES");
        PARAM_SESION.removeAttribute("CFAP_ITEM_LISTA_SERVICIOS");
        PARAM_SESION.removeAttribute("ID_ATENCION_PAC");
        PARAM_SESION.removeAttribute("CFAP_IDAGENDAMIENTO");
        PARAM_SESION.removeAttribute("CFAP_ITEM_AGEND");
        PARAM_SESION.removeAttribute("CFAP_IDPACIENTE");
        PARAM_SESION.removeAttribute("CFAP_LAST_FICHA_VALUES");
        PARAM_SESION.removeAttribute("CFAP_BAND_FILTRO");
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
            throws IOException, ServletException {
        
        // ESTAS DOS LINEAS DE CODIGO SIRVE PARA PODER RECONOCER LAS PALABRAS CON EÑES Y ACENTOS 
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        
        ModelFichaAtencionPacNutri metodosNutri = new ModelFichaAtencionPacNutri();
//        ModelFichaAtencionPac metodos = new ModelFichaAtencionPac();
        com.agend.modelo.ModelPersona metodosPaciente = new com.agend.modelo.ModelPersona();
//        ModelRef_Servicio metodosServ = new ModelRef_Servicio();
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA QUE UTILIZARE PARA RECUPERAR LOS DATOS QUE VAYA AGREGANDO 
        
        int var_redireccionar = 0;
        int var_no_redireccionar = 1; // VARIABLE QUE UTILIZO PARA NO QUERER REDIRECCIONAR DESDE ESTE CONTROLADOR YA QUE DESDE OTRO CONTROLADOR YA LE ESTOY CARGANDO LA PAGINA / LO INSTANCIO CON UNO PARA QUE SIEMPRE REDIRECCIONE Y EN EL EVENTO QUE LLAME A OTRO CONTROLADOR ENTONCES LE CAMBIO A CERO Y AHI EVITO LLAMAR AL DISPATCHER 
        String acceso = "atencion.htm";
        String accion = request.getParameter("accion");
        String accionAF = request.getParameter("accionAF"); // accion para la pagina de adjuntar archivos 
        String accionPag = request.getParameter("accionPag");
        String idPersona, idAtencionPac="";
        String ID_PACIENTE_EXP = ""; // VARIABLE QUE SOLO UTILIZARE PARA ALMACENAR EL IDPERSONA PARA CUANDO VENGA DEL EXPEDIENTE DE PACIENTE.-
        String FECHA_DE_HOY = modeloIniSes.traerFechaHoy();
        String HOUR_THIS_MOMENT = modeloIniSes.getHoraMinHoy();
        
        try {
            System.out.println(".");System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   -------------______CONTROLADOR_DE_EVENTOS_DE_LA_PAGINA_DE_FICHA-DE-ATENCION_______----------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__ACCION:      : "+accion);
            System.out.println("__ACCION_ADJUNTAR_FILES:  : "+accionAF);
            System.out.println("__ACCION_PAGINACION:      : "+accionPag);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if (accionAF != null) {
                System.out.println(".");System.out.println(".");System.out.println(".");
                System.out.println("_        ___ACCION - Adjuntar_Files -___        _");
                System.out.println(".");System.out.println(".");
                
                if (accionAF.equalsIgnoreCase("Add Archivo a la Ficha")) {
                    System.out.println("---------------------__CARGAR_ARCHIVO_A_LA_FICHA__--------------------------");
                    var_redireccionar = 1;
                    acceso = "add_atencion_af.htm";
                    
                    List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
                    if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) == false) {
                        listaArchivos = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                    }
                    int listaItem = 1;
                    if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES") == null) == false) {
                        listaItem = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
                    }
                    
                    String file_separador =  File.separator;
                    String path_destino_aux = request.getServletContext().getRealPath("//recursos//download//"); // [OBS] :cargando de esta manera le estoy pasando siempre la direccion base del contexto y con esta sentencia se termina guardando los archivos en la carpeta de la app dentro del servidor.-
//                    String path_destino_aux = modeloIniSes.getPathFileLocationAux();
                    File PATH_DESTINO_AUX = new File(path_destino_aux.replace("*", file_separador));
                    System.out.println("__PATH_DESTINO:   :"+PATH_DESTINO_AUX);
                    
                    Part part = request.getPart("PacFile");
                    String file_name = part.getSubmittedFileName();
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("__  ___FILE_NAME:    :"+file_name);
                    System.out.println("__  ___FILE_PATH:    :"+path_destino_aux+file_name);
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    
                    String MENSAJE=null, TIPO_MENSAJE="";
                    String PATH_FILE = "";
                    if (part == null || file_name.isEmpty()) {
                        System.out.println("No ha seleccionado ninguna archivo para agregar a la lista.");
                        MENSAJE = "No ha seleccionado ninguna archivo para agregar a la lista.";
                        TIPO_MENSAJE = "2";
                        request.setAttribute("CFAP_AF_MENSAJE", MENSAJE);
                        request.setAttribute("CFAP_AF_TIPO_MENSAJE", TIPO_MENSAJE);
//                        return;
                        
                    } else if(request.getPart("PacFile") != null && ctrlNameFile(sesionDatosUsuario, path_destino_aux, (request.getPart("PacFile").getSubmittedFileName())) == true) {
                        MENSAJE = "Ya existe una archivo con el mismo nombre guardado, debería de cambiar el nombre del archivo para poder agregar a la lista.";
                        TIPO_MENSAJE = "2";
                        request.setAttribute("CFAP_AF_MENSAJE", MENSAJE);
                        request.setAttribute("CFAP_AF_TIPO_MENSAJE", TIPO_MENSAJE);
                        
                    } else {
                        System.out.println(".");
                        System.out.println(".   -   -   else    -   -");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println("__  ___FILE_NAME:    :"+file_name);
                        if ((file_name == null || file_name.isEmpty())) {
                            System.out.println("_________IF_________IMG_DB_==_IMG_JSP________");
                        } else {
                            System.out.println("_________ELSE_________IMG_DB_!=_IMG_JSP________");
                            File pathNewCarp = new File((path_destino_aux.replace("*", file_separador)));
                            PATH_FILE = saveFile(part, PATH_DESTINO_AUX, pathNewCarp);
                            System.out.println("__________SAVE____________");
                            System.out.println("_FILE_PATH:   :"+PATH_FILE);
                            System.out.println("_FILE_PATH:   :"+PATH_FILE.replace("\\\\","*"));
                            System.out.println("__________________________");
                            System.out.println("_FILE_NAME:   :"+file_name);
                            System.out.println("__________________________");
//                            NAME_IMAGEN = file_name;
                        }
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        
                        // AGREGO A LA LISTA SI ES QUE NO SALTO NINGUNA VALIDACION
                        BeanFichaAtePacienteDet datos = new BeanFichaAtePacienteDet();
                            datos.setOFPND_ITEM("");
                            datos.setOFPND_IDFICHAPAC("");
                            datos.setOFPND_DIR_ARCHIVO(path_destino_aux+file_name);
                            datos.setOFPND_NAME_ARCHIVO(file_name);
                            datos.setOFPND_ESTADO("");
                        listaArchivos.add(datos);
                        listaItem = listaItem + 1;
                    }
                    //-----------------------------------------------------------------------------------------------
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
                    
                } else if (accionAF.equalsIgnoreCase("Volver a la Ficha")) {
                    System.out.println("---------AF------------__VOLVER_A_LA_FICHA__--------------------------");
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    
                } else if (accionAF.equalsIgnoreCase("Eliminar Fila") || accionAF.equalsIgnoreCase("Eliminar") || accionAF.equalsIgnoreCase("X")) {
                    System.out.println("---------------------__ELIMINAR_ARCHIVOS_ADJUNTO__--------------------------");
                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
//                    String IDAGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
//                    String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
//                    String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
//                    System.out.println("_   __ID_FICHA_ATEN_PAC: :"+idAtencionPac);
//                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
//                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
                    // RECUPERAR LOS DATOS -- 
                    // LISTA DE ARCHIVOS -- 
                    List<BeanFichaAtePacienteDet> LISTA_ARCHIVOS = new ArrayList<>();
                    if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                        LISTA_ARCHIVOS = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                    }
                    int ITEM_LIST_FILES = 1;
                    if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES") == null) == false) {
                        ITEM_LIST_FILES = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
                    }
                    
                    // RECUPERO LOS DATOS DEL SERVICIO A ELIMINAR -- 
                    String IDFICHAATENCION = (String) request.getParameter("tAFIFA"); // tAFIFA : TXT ADJUNTAR FILE - ID FICHA ATENCION 
                    int ITEM_ELIMINAR = Integer.parseInt((String)request.getParameter("tAFIDV"))-1; // VARIABLE DE LA NUMERACION DEL ITEM Y LE RESTO UNO PORQUE LA GRILLA EMPIEZA CON 1 PERO LA LISTA EMPIEZA CON EL VALOR 0   // tAIAD : TXT AUXILIAR ITEM ATENCION DETALLE  
                    String ITEM_ELIM_BD = (String) request.getParameter("tAFIDBD");
                    String DIR_ARCHIVO_DELE = (String) request.getParameter("tAFDA"); // path del archivo para poder eliminarlo de la carpeta download 
                    String MENSAJE = null, TIPO_MENSAJE = "";
                    System.out.println("--------------__ITEM_A_ELIMINAR__------------------");
                    System.out.println("_   __ID_FICHA_ATEN:  "+IDFICHAATENCION);
                    System.out.println("_   __ITEM_ELIMINAR:  "+ITEM_ELIMINAR);
                    System.out.println("_   __ITEM_ELIMI_BD:  "+ITEM_ELIM_BD);
                    System.out.println("---------------------------------------------------");
                    
                    // ELIMINO DE LA LISTA -- 
                    List<BeanFichaAtePacienteDet> CFAP_LISTA_NEW = new ArrayList<BeanFichaAtePacienteDet>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                    int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                    try {
                        // ELIMINO DE LA LISTA EL ITEM DE LA FILA SELECCIONADA 
                        LISTA_ARCHIVOS.remove(ITEM_ELIMINAR);
                        // VALIDACION PARA CONTROLAR SI ES QUE EL IDATENCIONPACIENTE SE ENCUENTRA CARGADO PARA PODER ELIMINAR (INACTIVAR) LA LINEA DEL DETALLE DE LA BASE   
                        if ((IDFICHAATENCION == null)==false && (IDFICHAATENCION.isEmpty())==false && (IDFICHAATENCION.equals(""))==false) {
                            System.out.println(".");System.out.println(".");
                            System.out.println(".");
                            System.out.println(".       __________IF___________ELIMINAR_DE_LA_BASE______________");
                            System.out.println(".");
                            System.out.println(".");System.out.println(".");
                            BeanFichaAtePacienteDet datos_eli = new BeanFichaAtePacienteDet();
                                datos_eli.setOFPND_IDFICHAPAC(IDFICHAATENCION); // PODRIA PASARLE LA VARIABLE DE LA SESION TAMBIEN YA QUE VENDRIAN A SER EL MISMO ID 
                                datos_eli.setOFPND_ITEM(ITEM_ELIM_BD);
                                datos_eli.setOFPND_USU_ALTA(idPersona);
                            // LLAMO AL METODO PARA ELIMINAR LA FILA DE LA BASE Y CARGO EL RESULTADO DE LA EJECUCION DE LA SENTENCIA PARA SABER QUE SI REALIZO CORRECTAMENTE O DIO ALGUN ERROR 
                            TIPO_MENSAJE = metodosNutri.eliminar(datos_eli);
                            if (TIPO_MENSAJE.equals("1")) {
                                MENSAJE = "Se ha eliminado correctamente la fila.";
                            } else if(TIPO_MENSAJE.equals("2")) {
                                MENSAJE = "Ocurrio un error mientras se ejecutaba la sentencia.\n Vuelva a intentarlo mas tarde.";
                            }
                            System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                            System.out.println("_   _CTRL_ELIMINAR:     MENSAJE("+TIPO_MENSAJE+"):      :"+MENSAJE);
                            System.out.println(".   .   .");System.out.println(".   .   .");System.out.println(".   .   .");
                        } else {
                            System.out.println(".");System.out.println(".");
                            System.out.println(".");
                            System.out.println(".       __________ELSE___________NO-TIENE-ID-ATENCION______________");
                            System.out.println(".");
                            System.out.println(".");System.out.println(".");
                        }
                        
                        // ELIMINO EL ARCHIVO DE LA CARPETA "DOWNLOAD" 
                        File fichero = new File(DIR_ARCHIVO_DELE.replace("*",File.separator));
                        if (!fichero.exists()) {
                            System.out.println("El archivo "+fichero.getName()+" no existe.");
                        } else {
                            fichero.delete();
                            System.out.println("El archivo "+fichero.getName()+" fue eliminado.");
                        }
                        
                        // ORDENAR LOS ITEMS -- 
                        for (BeanFichaAtePacienteDet LISTA_DETALLE : LISTA_ARCHIVOS) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("___     _ITEM_NEW:      "+itemNew);
                            System.out.println(".");
                            System.out.println(".");
                            // CARGO LOS DATOS DE LA LISTA EN EL BEAN QUE CARGARE EN OTRA LISTA QUE TENDRA LOS MISMO DATOS SOLO QUE LE RESETEARE LOS NRO DEL ITEM PARA QUE NO HAYA ERROR AL ELIMINAR LA FILA YA QUE EL ITEM YO LO UTILIZO COMO FORMA PARA ENCONTRAR LA FILA QUE SE QUIERE ELIMINAR 
                            BeanFichaAtePacienteDet datosNew = new BeanFichaAtePacienteDet();
                                datosNew.setOFPND_ITEM(LISTA_DETALLE.getOFPND_ITEM());
                                datosNew.setOFPND_IDFICHAPAC(LISTA_DETALLE.getOFPND_IDFICHAPAC());
                                datosNew.setOFPND_DIR_ARCHIVO(LISTA_DETALLE.getOFPND_DIR_ARCHIVO());
                                datosNew.setOFPND_NAME_ARCHIVO(LISTA_DETALLE.getOFPND_NAME_ARCHIVO());
                                datosNew.setOFPND_ESTADO(LISTA_DETALLE.getOFPND_ESTADO());
                            CFAP_LISTA_NEW.add(datosNew);
                            // LE SUMO UNO A LA VARIABLE PARA QUE SEA ASCENDENTE Y NO TODAS LAS FILAS TENGAN EL MISMO NRO 
                            itemNew = itemNew + 1;
                        } // end for 
                    } catch (Exception e) {
                        System.out.println(".INIT-ERROR-");System.out.println(".");System.out.println(".");
                        itemNew = ITEM_LIST_FILES; // SI DA ERROR AL ELIMINAR ENTONCES LE VOY A DEJAR NOMAS CON EL VALOR QUE TENIA PARA NO CARGAR POR ERROR EL VALOR 1 CUANDO DEBEIA SER 'X' NRO 
                        System.out.println("___FICHA_ATENCION_PACIENTE / ADJUNTAR FILES____________ERROR_POR_NRO_ITEM____");
                        Logger.getLogger(ControllerFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
                        System.out.println(".");System.out.println(".");System.out.println(".END-ERROR-");
                    } // end catch  // end catch 
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion_af.htm";
//                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
//                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEND);
//                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", CFAP_LISTA_NEW);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(itemNew));
                }
                
            } else
            if (accion != null) {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println("_        ____ACTION_EVENTS____        _");
                System.out.println(".");System.out.println(".");
//                if (accion.equalsIgnoreCase("Add Archivo a la Ficha")) {
//                    System.out.println("---------------------__CARGAR-ARCHIVO-A-LA-LISTA__--------------------------");
//                    idAtencionPac = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
//                    System.out.println("__ID_FICHA_ATENCION_PACIENTE :   :"+idAtencionPac);
//                    
//                    List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
//                    if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) == false) {
//                        listaArchivos = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
//                    }
//                    int listaItem = 1;
//                    if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES") == null) == false) {
//                        listaItem = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
//                    }
//                    // RECUPERO LOS DATOS
//                    String nameArchivo = (String) request.getParameter("tADA");
//                    System.out.println("_   _NAME_ARCHIVO:     :"+nameArchivo);
//                    String dirArchivo = (String) request.getParameter("tADAP");
//                    System.out.println("_   _DIR_ARCHIVO:      :"+dirArchivo);
//                    
//                    // CONTROLO QUE NO ESTEN VACIOS PARA PODER AGREGAR A LA LISTA
//                    int valiError = 0;
//                    String varMensaje = null;
//                    if (nameArchivo.isEmpty() || nameArchivo == null) {
//                        valiError = 1;
//                        varMensaje = "No debe de dejar campos vacíos.";
//                    } else if (dirArchivo.isEmpty() || dirArchivo == null) {
//                        valiError = 1;
//                        varMensaje = "No debe de dejar campos vacíos.";
//                    }
//                    
//                    // AGREGO A LA LISTA SI ES QUE NO SALTO NINGUNA VALIDACION
//                    if (valiError == 0) {
//                        BeanFichaAtePacienteDet datos = new BeanFichaAtePacienteDet();
//                            datos.setOFPND_ITEM("");
//                            datos.setOFPND_IDFICHAPAC("");
//                            datos.setOFPND_DIR_ARCHIVO(dirArchivo);
//                            datos.setOFPND_NAME_ARCHIVO(nameArchivo);
//                            datos.setOFPND_ESTADO("");
//                        listaArchivos.add(datos);
//                        listaItem = listaItem + 1;
//                    } else if (valiError > 0) { // PASO EL MENSAJE EN CASO DE QUE SE HAYA ACTIVADO ALGUNA VALIDACION
//                        request.setAttribute("CFAP_AF_MENSAJE",varMensaje);
//                        request.setAttribute("CFAP_AF_TIPO_MENSAJE","2");
//                    }
//                    
//                    var_redireccionar = 1;
//                    acceso = "add_atencion_af.htm";
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
//                    
//                    
//                } else 
                if (accion.equalsIgnoreCase("Adjuntar Archivos a la Ficha")) {
                    System.out.println("---------------------__ADJUNTAR-ARCHIVOS-A-LA-FICHA__--------------------------");
                    var_redireccionar = 1;
                    acceso = "add_atencion_af.htm";
                    recuperarDatosPag(request, metodosNutri, sesionDatosUsuario);
                    int listaItem = 1;
                    List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
                    // LE PASO LA LISTA VACIA EN CASO DE QUE ESTE NULL LA LISTA EN LA SESION 
                    if (sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) {
                        // [OBS] COMO INSTANCIO LA LISTA Y EL ITEM NO HAY NECESIDAD DE DECLARAR EN ESTE IF PERO SI NO FUERAN INSTANCIADAS ENTONCES SI TENDRIA QUE DARLES UN VALOR VACIO PARA QUE NO SALTE ERROR 
                    } else {
                        listaArchivos = (List) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                        listaItem = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
                    }
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
                    
                } else if (accion.equalsIgnoreCase("Ver Historial de Fichas Anteriores") || accion.equalsIgnoreCase("Ver Historico de Fichas Anteriores")) {
                    System.out.println("---------------------__VER-HISTORIAL-DE-FICHAS-ANTERIORES__--------------------------");
                    var_redireccionar = 1;
                    ControllerReportes controllerReportes = new ControllerReportes();
                    acceso = controllerReportes.goVerHistoricoFichasPaciente(request, metodosNutri);
                    System.out.println("___ CFA:  :acceso:  :"+acceso);
                    // variable que uso en el controlador de la ficha para saber a que pagina redireccionar cuando se presione el boton para volver atras 
//                    if (String.valueOf(sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS")).isEmpty()) {
//                        sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "FICHA_ATENCION_PAC_NUTRI"); // OBS.: NO LE COLOCO DENTRO DEL METODO PORQUE FICHA PUEDE LLAMAR A ESTE METODO Y EN ESE CASO TENDRIA QUE VOLVER ATRAS EN FICHA O EN EXPEDIENTE Y NO EN EL REPORTE DE PACIENTE 
//                    }
                    recuperarDatosPag(request, metodosNutri, sesionDatosUsuario);
                    sesionDatosUsuario.setAttribute("CR_RFAN_BTN_VOLVER_ATRAS", "FICHA_ATENCION_PAC_NUTRI");
                    
                } else if (accion.equalsIgnoreCase("Cargar Atencion") || accion.equalsIgnoreCase("Cargar Atención")) {
                    System.out.println("---------------------__CARGAR_ATENCION__--------------------------");
                    limpiarVarsSession(sesionDatosUsuario);
                    String IDAGENDAMIENTO = (String) request.getParameter("tIA");
                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                    String ITEM_AGEN_DET = (String) request.getParameter("tAID");
                    System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
                    String IDPACIENTE = (String) request.getParameter("tIP");
                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
//                    List<BeanFichaAtePaciente> LISTA_SERVICIOS = new ArrayList<>();
                    // DATOS DEL PACIENTE 
                    List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
//                    // DATOS DEL PACIENTE 
//                    String TXT_PAC_NOMAPE, TXT_PAC_FECHA_NAC, TXT_PAC_EDAD, TXT_PAC_SEXO, TXT_PAC_PROFESION, TXT_PAC_TELEFONO, TXT_PAC_CIUDAD, TXT_PAC_CORREO;
//                    // TRAER DATOS DEL PACIENTE QUE SE LE VA A CARGAR LA FICHA DE ATENCION 
//                    BeanPersona datosBPaciente = new BeanPersona();
//                    datosBPaciente = metodosPaciente.datosBasicosPersona(datosBPaciente, IDPACIENTE);
//                    TXT_PAC_NOMAPE = datosBPaciente.getRP_NOMBRE()+" "+datosBPaciente.getRP_APELLIDO();
//                    TXT_PAC_FECHA_NAC = datosBPaciente.getRP_FECHANAC();
//                    TXT_PAC_EDAD = String.valueOf(modeloIniSes.getPacEdad(TXT_PAC_FECHA_NAC));
//                    TXT_PAC_SEXO = datosBPaciente.getRP_SEXO();
//                    TXT_PAC_PROFESION = datosBPaciente.getRP_PROFESION();
//                    TXT_PAC_TELEFONO = datosBPaciente.getRP_TELFPARTICULAR();
//                    TXT_PAC_CIUDAD = datosBPaciente.getRP_DESC_CIUDAD();
//                    TXT_PAC_CORREO = datosBPaciente.getRP_EMAIL();
//                    // INGRESO A UN ARRAY Y EL ARRAY NOMAS LE PASO POR PARAMETRO AL JSP PARA IR DEJANDO DE PASAR VARIABLE POR VARIABLE 
//                    List<String> datosPac = new ArrayList<>();
//                        datosPac.add(TXT_PAC_NOMAPE);
//                        datosPac.add(TXT_PAC_FECHA_NAC);
//                        datosPac.add(TXT_PAC_EDAD);
//                        datosPac.add(TXT_PAC_SEXO);
//                        datosPac.add(TXT_PAC_PROFESION);
//                        datosPac.add(TXT_PAC_TELEFONO);
//                        datosPac.add(TXT_PAC_CIUDAD);
//                        datosPac.add(TXT_PAC_CORREO);
                    List<String> datosFichaCab01 = new ArrayList<>();
                        datosFichaCab01.add(FECHA_DE_HOY); // FECHA 
                        datosFichaCab01.add(HOUR_THIS_MOMENT); // HORA 
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                        datosFichaCab01.add("");
                    List<String> datosFichaCab02 = new ArrayList<>();
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                        datosFichaCab02.add("");
                    List<String> datosFichaCab03 = new ArrayList<>();
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                        datosFichaCab03.add("");
                    List<String> datosFichaCab04 = new ArrayList<>();
                        datosFichaCab04.add("");
                        datosFichaCab04.add("");
                        datosFichaCab04.add("");
                        datosFichaCab04.add("");
                    List<BeanFichaAtePaciente> datosUltFichaPac = metodosNutri.getUltimaFicha(IDPACIENTE);
                    
                    var_redireccionar = 1;
                    acceso = "add_atencion.htm";
                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN REGISTRO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN REGISTRO, LA SESION VA A MANTENER ESE ID Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN NUEVO REGISTRO   
                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", "1");
                    // NUEVAS VARIABLES 
                    request.setAttribute("CFAP_PAC_DATOS", datosPac);
                    request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
                    request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
                    request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
                    request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
                    sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
                    // LE LIMPIO LAS MISMAS VARIABLES POR SI QUEDARON DE UNA ANTERIOR FICHA EN SESION (YA QUE LO UTILIZO PARA CARGAR LAS LISTAS AL QUERER VER EL HISTORIAL DE FICHAS) 
                    sesionDatosUsuario.removeAttribute("CFAP_PAC_DATOS");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_01");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_02");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_03");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_04");
                    // LISTA DE ARCHIVOS QUE USO PARA ADJUNTAR - LA CARGO VACIO PORQUE EN CASO DE QUE NO SE REDIRIJA A LA PAGINA PARA ADJUNTAR ENTONCES POSIBLEMENTE QUIERA SALTAR UN ERROR EN EL METODO DE GUARDAR PORQUE NO SE ENCONTRARIA A LA LISTA EN LA SESION.-
                    List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
                    int listaItem = 1;
                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
                    
                } else if(accion.equalsIgnoreCase("Editar Atención") || accion.equalsIgnoreCase("Editar Ficha")) {
                    System.out.println("---------------------__(PRE_CARGO)___EDITAR_ATENCION__--------------------------");
                    limpiarVarsSession(sesionDatosUsuario);
                    var_redireccionar = 1;
                    acceso = editarFichaAtencion(sesionDatosUsuario, request, metodosNutri, acceso);
//                    System.out.println("---------------------__EDITAR_ATENCION__--------------------------");
//                    idAtencionPac = (String) request.getParameter("tIAP");
//                    System.out.println("_   __ID_ATENCION_PAC:   :"+idAtencionPac);
//                    String IDAGENDAMIENTO = (String) request.getParameter("tIA");
//                    System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
//                    String ITEM_AGEN_DET = (String) request.getParameter("tAID");
//                    System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
//                    String IDPACIENTE = (String) request.getParameter("tIP");
//                    System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);
//                    
////                    // CARGAR LISTA SERVICIOS 
////                    List<BeanFichaAtePaciente> LISTA_SERV = new ArrayList<>();
////                    int ITEM_LIST_SERV = 0;
//                    
//                    // CARGAR_GRILLA_DETALLE 
//                    List<BeanFichaAtePaciente> FICHA_DATOS = new ArrayList<>();
//                    FICHA_DATOS = metodosNutri.traer_datos(idAtencionPac, sesionDatosUsuario);
//                    Iterator<BeanFichaAtePaciente> iListDet = FICHA_DATOS.iterator();
//                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//                    
//                    List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
//                    List<String> datosFichaCab01 = new ArrayList<>();
//                    List<String> datosFichaCab02 = new ArrayList<>();
//                    List<String> datosFichaCab03 = new ArrayList<>();
//                    List<String> datosFichaCab04 = new ArrayList<>();
//                    
//                    while(iListDet.hasNext()) {
//                        datos = iListDet.next();
//                        System.out.println("____WHILE____");
//                        String[] FECHA_HORA_FICHA = datos.getOFPN_FECHA_FICHA_ATE().split(" ");
//                        System.out.println("___________________________FECHA_FICHA:  :"+FECHA_HORA_FICHA[0]);
//                        System.out.println("___________________________HORA__FICHA:  :"+FECHA_HORA_FICHA[1]);
//                        // BLOQUE#1
//                            datosFichaCab01.add(FECHA_HORA_FICHA[0]); // FECHA 
//                            datosFichaCab01.add(FECHA_HORA_FICHA[1]); // HORA 
//                            datosFichaCab01.add(datos.getOFPN_MOTIVO_DE_LA_CONSULTA());
//                            datosFichaCab01.add(datos.getOFPN_ALIMENTOS_DE_PREFERENCIA());
//                            datosFichaCab01.add(datos.getOFPN_ALI_QUE_SUELE_COMER_GRL());
//                            datosFichaCab01.add(datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA());
//                            datosFichaCab01.add(datos.getOFPN_ALERGIAS_A_ALGO());
//                            datosFichaCab01.add(datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD());
//                            datosFichaCab01.add(datos.getOFPN_CIRUGIAS());
//                            datosFichaCab01.add(datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO());
//                            datosFichaCab01.add(datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA());
//                            datosFichaCab01.add(datos.getOFPN_CONSUMO_ALCOHOL());
//                            datosFichaCab01.add(datos.getOFPN_CONSUMO_CIGARRILLO());
//                        // BLOQUE#2
//                            datosFichaCab02.add(datos.getOFPN_REALIZA_ACTIVIDAD_FISICA());
//                            datosFichaCab02.add(datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA());
//                            datosFichaCab02.add(datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM());
//                            datosFichaCab02.add(datos.getOFPN_DBLCR());
//                            datosFichaCab02.add(datos.getOFPN_ESTRENHIMIENTO());
//                            datosFichaCab02.add(datos.getOFPN_CALAMBRES_Y_HORMIGUEOS());
//                            datosFichaCab02.add(datos.getOFPN_LGSLCM());
//                            datosFichaCab02.add(datos.getOFPN_CANSANCIO_FATIGA());
//                            datosFichaCab02.add(datos.getOFPN_ZUMBIDOS_EN_EL_OIDO());
//                            datosFichaCab02.add(datos.getOFPN_TBDALN());
//                            datosFichaCab02.add(datos.getOFPN_HICHAZON_ABDOMINAL());
//                            datosFichaCab02.add(datos.getOFPN_CAIDA_DE_CABELLO());
//                            datosFichaCab02.add(datos.getOFPN_DPALN());
//                            datosFichaCab02.add(datos.getOFPN_INSOMNIO());
//                            datosFichaCab02.add(datos.getOFPN_UNHAS_QUEBRADIZAS());
//                            datosFichaCab02.add(datos.getOFPN_DDCCF());
//                            datosFichaCab02.add(datos.getOFPN_MUCOSIDAD_Y_CATARRO());
//                            datosFichaCab02.add(datos.getOFPN_PIEL_SECA());
//                            datosFichaCab02.add(datos.getOFPN_TIPO_DE_METABOLISMO());
//                            datosFichaCab02.add(datos.getOFPN_TDEDBU());
//                        // BLOQUE#3
//                            datosFichaCab03.add(datos.getOFPN_ESTATURA());
//                            datosFichaCab03.add(datos.getOFPN_PORC_GRASA());
//                            datosFichaCab03.add(datos.getOFPN_EDAD_METABOLICA());
//                            datosFichaCab03.add(datos.getOFPN_PESO());
//                            datosFichaCab03.add(datos.getOFPN_PORC_MUSCULO());
//                            datosFichaCab03.add(datos.getOFPN_RM());
//                            datosFichaCab03.add(datos.getOFPN_IMC());
//                            datosFichaCab03.add(datos.getOFPN_VISCERAL());
//                            datosFichaCab03.add(datos.getOFPN_TIPO_DE_BALANZA());
//                        // BLOQUE#4
//                            datosFichaCab04.add(datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA());
//                            datosFichaCab04.add(datos.getOFPN_SUPLEMENTACION_RECETADA());
//                            datosFichaCab04.add(datos.getOFPN_LABORATORIO());
//                            datosFichaCab04.add(datos.getOFPN_COMENTARIOS_GENERALES());
//                    }
//                    
//                    
//                    var_redireccionar = 1;
//                    acceso = "add_atencion.htm";
//                    sesionDatosUsuario.setAttribute("CFAP_BAND_REPEAT_ONE", "1"); // VARIABLE QUE UTILIZO COMO BANDERA PARA EVITAR QUE AL RECARGAR LA PAGINA EN EL EXPEDIENTE DE PACIENTE SE REPITA LA ULTIMA ACCION (ELIMINAR FICHA O GUARDAR FICHA) 
//                    sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR QUE RECUPERO PARA QUE NO PIENSE QUE ESTOY GUARDANDO UNA NUEVA FICHA SINO SEPA A CUAL FICHA ACTUALIZAR 
//                    sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
//                    sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
////                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
//////                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
////                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
//////                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", NRO_ITEM);
//                    // BLOQUES DEL JSP 
//                    request.setAttribute("CFAP_PAC_DATOS", datosPac);
//                    request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
//                    request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
//                    request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
//                    request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
//                    // LE LIMPIO LAS MISMAS VARIABLES POR SI QUEDARON DE UNA ANTERIOR FICHA EN SESION (YA QUE LO UTILIZO PARA CARGAR LAS LISTAS AL QUERER VER EL HISTORIAL DE FICHAS) 
//                    sesionDatosUsuario.removeAttribute("CFAP_PAC_DATOS");
//                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_01");
//                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_02");
//                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_03");
//                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_04");
//                    // [OBS] CARGO LA LISTA DE ARCHIVOS ADJUNTOS A LA FICHA DESDE UN METODO DISTINTO PARA NO RECORRER EL WHILE CON GRANDES CANTIDADES DE DATOS
//                    List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
//                    listaArchivos = metodosNutri.getDatosArchivosAdjuntos(idAtencionPac);
//                    int listaItem = 1;
//                    if (listaArchivos.size() > 0) { // [OBS] si la lista es mayor a cero entonces tiene filas dentro y no esta vacia, en ese caso el item de la lista seria el size completo mas uno para la siguiente linea.-
//                        listaItem = listaArchivos.size()+1;
//                    }
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
                    
                } else if(accion.equalsIgnoreCase("Ver Atención") || accion.equalsIgnoreCase("Ver Ficha")) {
                    var_redireccionar = 1;
                    limpiarVarsSession(sesionDatosUsuario);
                    acceso = ver_ficha_atencion_paciente(sesionDatosUsuario, request, metodosNutri, idAtencionPac, var_redireccionar, acceso);
                    // LE LIMPIO LAS MISMAS VARIABLES POR SI QUEDARON DE UNA ANTERIOR FICHA EN SESION (YA QUE LO UTILIZO PARA CARGAR LAS LISTAS AL QUERER VER EL HISTORIAL DE FICHAS) 
                    sesionDatosUsuario.removeAttribute("CFAP_PAC_DATOS");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_01");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_02");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_03");
                    sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_04");
                    
                } else if(accion.equalsIgnoreCase("GUARDAR") || accion.equalsIgnoreCase("GRABAR")) {
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println(".");
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
                    System.out.println("__ID_CLINICA :    :"+ID_CLINICA);
                    ID_PACIENTE_EXP = IDPACIENTE;
                    // VARIABLES PARA COPIAR EL FILE.-
                    String PATH_FILE = "", NAME_FILE = "";
                    String file_separador =  File.separator;
                    String path_destino = request.getServletContext().getRealPath("//recursos//download//");
//                    String path_destino = modeloIniSes.getPathFileLocation();
                    
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
                        // DATOS DEL PACIENTE 
                        List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
//                        String TXT_PAC_NOMAPE, TXT_PAC_FECHA_NAC, TXT_PAC_EDAD, TXT_PAC_SEXO, TXT_PAC_PROFESION, TXT_PAC_TELEFONO, TXT_PAC_CIUDAD, TXT_PAC_CORREO;
//                        // TRAER DATOS DEL PACIENTE QUE SE LE VA A CARGAR LA FICHA DE ATENCION 
//                        BeanPersona datosBPaciente = new BeanPersona();
//                        datosBPaciente = metodosPaciente.datosBasicosPersona(datosBPaciente, IDPACIENTE);
//                        TXT_PAC_NOMAPE = datosBPaciente.getRP_NOMBRE()+" "+datosBPaciente.getRP_APELLIDO();
//                        TXT_PAC_FECHA_NAC = datosBPaciente.getRP_FECHANAC();
//                        TXT_PAC_EDAD = String.valueOf(modeloIniSes.getPacEdad(TXT_PAC_FECHA_NAC));
//                        TXT_PAC_SEXO = datosBPaciente.getRP_SEXO();
//                        TXT_PAC_PROFESION = datosBPaciente.getRP_PROFESION();
//                        TXT_PAC_TELEFONO = datosBPaciente.getRP_TELFPARTICULAR();
//                        TXT_PAC_CIUDAD = datosBPaciente.getRP_DESC_CIUDAD();
//                        TXT_PAC_CORREO = datosBPaciente.getRP_EMAIL();
//                        // INGRESO A UN ARRAY Y EL ARRAY NOMAS LE PASO POR PARAMETRO AL JSP PARA IR DEJANDO DE PASAR VARIABLE POR VARIABLE 
//                        List<String> datosPac = new ArrayList<>();
//                            datosPac.add(TXT_PAC_NOMAPE);
//                            datosPac.add(TXT_PAC_FECHA_NAC);
//                            datosPac.add(TXT_PAC_EDAD);
//                            datosPac.add(TXT_PAC_SEXO);
//                            datosPac.add(TXT_PAC_PROFESION);
//                            datosPac.add(TXT_PAC_TELEFONO);
//                            datosPac.add(TXT_PAC_CIUDAD);
//                            datosPac.add(TXT_PAC_CORREO);
                        List<String> datosFichaCab01 = new ArrayList<>();
                        List<String> datosFichaCab02 = new ArrayList<>();
                        List<String> datosFichaCab03 = new ArrayList<>();
                        List<String> datosFichaCab04 = new ArrayList<>();
                        // RECUPERAR LOS DATOS 
                        // BLOQUE #1 / 
                        String ATEN_B1TDF = (String) request.getParameter("B1TDF"); // FECHA FICHA 
                        if (ATEN_B1TDF==null || ATEN_B1TDF.isEmpty()) {
                            System.out.println("____CTRL__GUARDAR______NULL_FECHA_FICHA____");
                            ATEN_B1TDF = modeloIniSes.traerFechaHoy();
                        }
                        String ATEN_B1TDFH = (String) request.getParameter("B1TDFH"); // HORA FICHA 
                        if (ATEN_B1TDFH==null || ATEN_B1TDFH.isEmpty()) {
                            System.out.println("____CTRL__GUARDAR______NULL_HORA_FICHA____");
                            ATEN_B1TDFH = modeloIniSes.getHoraHoy();
                        }
                        String ATEN_B1TMDLC = (String) request.getParameter("B1TMDLC");
                        String ATEN_B1TAP = (String) request.getParameter("B1TAP");
                        String ATEN_B1TACG = (String) request.getParameter("B1TACG");
                        String ATEN_B1TANT = (String) request.getParameter("B1TANT");
                        String ATEN_B1TCA = (String) request.getParameter("B1TCA"); // CONSUMO ALCOHOL 
                        String ATEN_B1TCC = (String) request.getParameter("B1TCC"); // CONSUMO CIGARRILLO 
                        String ATEN_B1TA = (String) request.getParameter("B1TA");
                        String ATEN_B1TE = (String) request.getParameter("B1TE");
                        String ATEN_B1TC = (String) request.getParameter("B1TC");
                        String ATEN_B1TM = (String) request.getParameter("B1TM");
                        String ATEN_B1TOD = (String) request.getParameter("B1TOD");
                        // BLOQUE #2 / 
                        String ATEN_B2CRAF = (String) request.getParameter("B2CRAF");
                        String ATEN_B2TTE = (String) request.getParameter("B2TTE");
                        String ATEN_B2TEF = (String) request.getParameter("B2TEF");
                        String ATEN_B2CDCR = (String) request.getParameter("B2CDCR");
                        String ATEN_B2CE = (String) request.getParameter("B2CE");
                        String ATEN_B2CCOH = (String) request.getParameter("B2CCOH");
                        String ATEN_B2CGS = (String) request.getParameter("B2CGS");
                        String ATEN_B2CCF = (String) request.getParameter("B2CCF");
                        String ATEN_B2CZO = (String) request.getParameter("B2CZO");
                        String ATEN_B2CBD = (String) request.getParameter("B2CBD");
                        String ATEN_B2CHA = (String) request.getParameter("B2CHA");
                        String ATEN_B2CCDC = (String) request.getParameter("B2CCDC");
                        String ATEN_B2CDP = (String) request.getParameter("B2CDP");
                        String ATEN_B2CI = (String) request.getParameter("B2CI");
                        String ATEN_B2CUQ = (String) request.getParameter("B2CUQ");
                        String ATEN_B2CDDC = (String) request.getParameter("B2CDDC");
                        String ATEN_B2CMC = (String) request.getParameter("B2CMC");
                        String ATEN_B2CPS = (String) request.getParameter("B2CPS");
                        String ATEN_B2TM = (String) request.getParameter("B2TM");
                        String ATEN_B2CTDEDBU = (String) request.getParameter("B2CTDEDBU");
                        // BLOQUE #3 / 
                        String ATEN_B3TE = (String) request.getParameter("B3TE");
                        String ATEN_B3TPG = (String) request.getParameter("B3TPG");
                        String ATEN_B3TEM = (String) request.getParameter("B3TEM");
                        String ATEN_B3TP = (String) request.getParameter("B3TP");
                        String ATEN_B3TPM = (String) request.getParameter("B3TPM");
                        String ATEN_B3TRM = (String) request.getParameter("B3TRM");
                        String ATEN_B3TI = (String) request.getParameter("B3TI");
                        String ATEN_B3TV = (String) request.getParameter("B3TV");
                        String ATEN_B3TB = (String) request.getParameter("B3TB");
                        // BLOQUE #4 / 
                        String ATEN_B4TRT = (String) request.getParameter("B4TRT");
                        String ATEN_B4TSR = (String) request.getParameter("B4TSR");
                        String ATEN_B4TL = (String) request.getParameter("B4TL");
                        String ATEN_B4TCG = (String) request.getParameter("B4TCG");
                        String ATEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
        //                String ATEN_ESTADO = (String) request.getParameter("cEs");
//                        if (ATEN_ESTADO == null || ATEN_ESTADO.isEmpty()) { varValiVacio++; ATEN_ESTADO=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #1    ------");
                        System.out.println(".");
                        System.out.println("-       _FECHA-DE-LA-FICHA:-----------------:"+ATEN_B1TDF);
                        System.out.println("-       _HORA-DE-LA-FICHA:------------------:"+ATEN_B1TDFH);
                        System.out.println("-       _MOTIVO_CONSULTA:-------------------:"+ATEN_B1TMDLC);
                        System.out.println("-       _ALIMENTOS_DE_PREFERENCIA:----------:"+ATEN_B1TAP);
                        System.out.println("-       _ALIMENTOS-QUE-COMES-GENERALMENTE:--:"+ATEN_B1TACG);
                        System.out.println("-       _ALIMENTOS-QUE-NO-TOLERA:-----------:"+ATEN_B1TANT);
                        System.out.println("-       _CONSUMO_ALCOHOL:-------------------:"+ATEN_B1TCA);
                        System.out.println("-       _CONSUMO_CIGARRILLO:----------------:"+ATEN_B1TCC);
                        System.out.println("-       _ALERGIAS:--------------------------:"+ATEN_B1TA);
                        System.out.println("-       _ENFERMEDAD:------------------------:"+ATEN_B1TE);
                        System.out.println("-       _CIRUGIAS:--------------------------:"+ATEN_B1TC);
                        System.out.println("-       _MEDICAMENTOS:----------------------:"+ATEN_B1TM);
                        System.out.println("-       _OTROS-DATOS-A-TENER-EN-CUENTA:-----:"+ATEN_B1TOD);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #2    ------");
                        System.out.println(".");
                        System.out.println("-       _REALIZA-ACTIVIDAD-FISICA:---------:"+ATEN_B2CRAF);
                        System.out.println("-       _TIPO-EJERCICIO:-------------------:"+ATEN_B2TTE);
                        System.out.println("-       _EJERCICIO-FRECUENCIA:-------------:"+ATEN_B2TEF);
                        System.out.println("-       _DIRIGE-BIEN-LAS-CARNES-ROJAS:-----:"+ATEN_B2CDCR);
                        System.out.println("-       _ESTRENHIMIENTO:-------------------:"+ATEN_B2CE);
                        System.out.println("-       _CALAMBRES-Y/O-HORMIGUEOS:---------:"+ATEN_B2CCOH);
                        System.out.println("-       _GRASAS-SATURADAS:-----------------:"+ATEN_B2CGS);
                        System.out.println("-       _CANSANCIO-Y-FATIGA:---------------:"+ATEN_B2CCF);
                        System.out.println("-       _ZUMBIDOS-EN-EL-OIDO:--------------:"+ATEN_B2CZO);
                        System.out.println("-       _TIENE-BUENA-DIGESTION-A-LA-NOCHE:-:"+ATEN_B2CBD);
                        System.out.println("-       _HINCHAZON-ABDOMINAL:--------------:"+ATEN_B2CHA);
                        System.out.println("-       _CAIDA-DEL-CABELLO:----------------:"+ATEN_B2CCDC);
                        System.out.println("-       _DUERME-PROFUNDAMENTE:-------------:"+ATEN_B2CDP);
                        System.out.println("-       _INSOMNIO:-------------------------:"+ATEN_B2CI);
                        System.out.println("-       _UNHAS-QUEBRADIZAS:----------------:"+ATEN_B2CUQ);
                        System.out.println("-       _DOLORES-DE-CABEZA-CON-FRECUENCIA:-:"+ATEN_B2CDDC);
                        System.out.println("-       _MUCOSIDAD-Y-CATARRO:--------------:"+ATEN_B2CMC);
                        System.out.println("-       _PIEL-SECA:------------------------:"+ATEN_B2CPS);
                        System.out.println("-       _METABOLISMO:----------------------:"+ATEN_B2TM);
                        System.out.println("-       _TIPO-ESCALA-BRISTOL-USUAL:--------:"+ATEN_B2CTDEDBU);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #3    ------");
                        System.out.println(".");
                        System.out.println("-       _ESTATURA:-------------------------:"+ATEN_B3TE);
                        System.out.println("-       _PORCENTAJE-GRASA:-----------------:"+ATEN_B3TPG);
                        System.out.println("-       _EDAD-M:---------------------------:"+ATEN_B3TEM);
                        System.out.println("-       _PESO:-----------------------------:"+ATEN_B3TP);
                        System.out.println("-       _PORCENTAJE-MUSCULO:---------------:"+ATEN_B3TPM);
                        System.out.println("-       _RM:-------------------------------:"+ATEN_B3TRM);
                        System.out.println("-       _IMC:------------------------------:"+ATEN_B3TI);
                        System.out.println("-       _VISCERAL:-------------------------:"+ATEN_B3TV);
                        System.out.println("-       _BALANZA:--------------------------:"+ATEN_B3TB);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #4    ------");
                        System.out.println(".");
                        System.out.println("-       _RESULTADOS-TEST:------------------:"+ATEN_B4TRT);
                        System.out.println("-       _SUPLEMENTACION-RECETADA:----------:"+ATEN_B4TSR);
                        System.out.println("-       _LABORATORIO:----------------------:"+ATEN_B4TL);
                        System.out.println("-       _COMENTARIOS-GENERALES:------------:"+ATEN_B4TCG);
                        System.out.println(".");
                        System.out.println("-       ATEN_ESTADO:-----------------------:"+ATEN_ESTADO);
                        System.out.println("-       ATEN_IDCLINICA:--------------------:"+ID_CLINICA);
                        System.out.println("-   -----------------------------------------------------------------------------");
                        System.out.println("___     ___var_vali_vacio:     :"+varValiVacio);
                        
                        // LISTA DE ARCHIVOS -- 
                        List<BeanFichaAtePacienteDet> LISTA_ARCHIVOS = new ArrayList<>();
                        if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                            LISTA_ARCHIVOS = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                        }
//                            int ITEM_LIST_FILES = 1;
//                            if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES") == null) == false) {
//                                ITEM_LIST_FILES = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
//                            }
                        
//                        // BLOQUE 1 
//                        datosFichaCab01.add(ATEN_B1TDF);
//                        datosFichaCab01.add(ATEN_B1TDFH);
//                        datosFichaCab01.add(ATEN_B1TMDLC);
//                        datosFichaCab01.add(ATEN_B1TAP);
//                        datosFichaCab01.add(ATEN_B1TACG);
//                        datosFichaCab01.add(ATEN_B1TANT);
//                        datosFichaCab01.add(ATEN_B1TCA);
//                        datosFichaCab01.add(ATEN_B1TCC);
//                        datosFichaCab01.add(ATEN_B1TA);
//                        datosFichaCab01.add(ATEN_B1TE);
//                        datosFichaCab01.add(ATEN_B1TC);
//                        datosFichaCab01.add(ATEN_B1TM);
//                        datosFichaCab01.add(ATEN_B1TOD);
//                        datosFichaCab01 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab01, "");
//                        // BLOQUE 2 
//                        datosFichaCab02.add(ATEN_B2CRAF);
//                        datosFichaCab02.add(ATEN_B2TTE);
//                        datosFichaCab02.add(ATEN_B2TEF);
//                        datosFichaCab02.add(ATEN_B2CDCR);
//                        datosFichaCab02.add(ATEN_B2CE);
//                        datosFichaCab02.add(ATEN_B2CCOH);
//                        datosFichaCab02.add(ATEN_B2CGS);
//                        datosFichaCab02.add(ATEN_B2CCF);
//                        datosFichaCab02.add(ATEN_B2CZO);
//                        datosFichaCab02.add(ATEN_B2CBD);
//                        datosFichaCab02.add(ATEN_B2CHA);
//                        datosFichaCab02.add(ATEN_B2CCDC);
//                        datosFichaCab02.add(ATEN_B2CDP);
//                        datosFichaCab02.add(ATEN_B2CI);
//                        datosFichaCab02.add(ATEN_B2CUQ);
//                        datosFichaCab02.add(ATEN_B2CDDC);
//                        datosFichaCab02.add(ATEN_B2CMC);
//                        datosFichaCab02.add(ATEN_B2CPS);
//                        datosFichaCab02.add(ATEN_B2TM);
//                        datosFichaCab02.add(ATEN_B2CTDEDBU);
//                        datosFichaCab02 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab02, "");
//                        // BLOQUE 3 
//                        datosFichaCab03.add(ATEN_B3TE);
//                        datosFichaCab03.add(ATEN_B3TPG);
//                        datosFichaCab03.add(ATEN_B3TEM);
//                        datosFichaCab03.add(ATEN_B3TP);
//                        datosFichaCab03.add(ATEN_B3TPM);
//                        datosFichaCab03.add(ATEN_B3TRM);
//                        datosFichaCab03.add(ATEN_B3TI);
//                        datosFichaCab03.add(ATEN_B3TV);
//                        datosFichaCab03.add(ATEN_B3TB);
//                        datosFichaCab03 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab03, "");
//                        // BLOQUE 4 
//                        datosFichaCab04.add(ATEN_B4TRT);
//                        datosFichaCab04.add(ATEN_B4TSR);
//                        datosFichaCab04.add(ATEN_B4TL);
//                        datosFichaCab04.add(ATEN_B4TCG);
//                        datosFichaCab04 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab04, "");
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
        /*OBS.: LE PONGO MAYOR A 99 PORQUE NO VA A LLEGAR A ESE MONTO Y LA VALIDACION NO SE VA A ACTIVAR, 
                YA QUE SE PUEDE GUARDAR CON CAMPOS VACIOS Y NO QUIERO COMENTAR ESTA VALIDACION */                
//                        if (metodosNutri.validateNull(datosFichaCab01) == true) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                        //  OBSERVACION: "VALIDAR SI ES NULL"
                        //      * SI QUISIERA IMPLEMENTAR LA VALIDACION PARA EVITAR QUE SE DEJEN CAMPOS VACIOS ENTONCES DESCOMENTARIA EL IF DE ARRIBA ↑ Y LO HARIA POR BLOQUE (POR LISTA) 
                        //      * Y ASI SABRIA QUE BLOQUE TIENE DENTRO SUYO EL CAMPO VACIO Y LUEGO DESDE EL JSP PODRIA DETECTAR Y MARCARLA 
                        if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                acceso = "add_atencion.htm";
                            } else {
                                acceso = return_pag;
                            }
                            
//                        } else if (metodosNutri.ctrlArchivosAdjuntos(request, LISTA_ARCHIVOS, new File(path_destino.replace("*", file_separador))) == true) {
//                            String valiArchivoAdjunto = (String) request.getAttribute("VALI_CTRL_ADD_FILE_VARNAME");
//                            MENSAJE = "Debe de cambiar el nombre del archivo adjunto \""+valiArchivoAdjunto+"\" para poder vincularla con la ficha porque ya existe un archivo con el mismo nombre.";
//                            TIPO_MENSAJE = "2";
//                            acceso = "add_atencion_af.htm";
//                            request.setAttribute("CFAP_AF_MENSAJE", MENSAJE);
//                            request.setAttribute("CFAP_AF_TIPO_MENSAJE", TIPO_MENSAJE);
                            
                        } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                            // CABECERA 
                            String OFPN_IDFICHAPAC = metodosNutri.traerNewIdAtencion();
                            idAtencionPac = OFPN_IDFICHAPAC; // CARGO PARA LUEGO PASAR ESTA VARIABLE A LA SESION Y ASI SI SE VUELVE A REPETIR LA ULTIMA ACCION NO LE DEJE DUPLICAR LA FICHA DE ATENCION 
                            String OFPN_IDAGENDAMIENTO = IDAGENDAMIENTO;
                            String OFPN_ITEM_AGEND_DET = ITEM_AGEND;
                            String OFPN_IDPACIENTE = IDPACIENTE;
                            String OFPN_USU_ALTA = idPersona;
                            String OFPN_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                            String OFPN_ESTADO = ATEN_ESTADO;
                            String OFPN_IDCLINICA = ID_CLINICA;
                            String OFPN_USU_MODI = "";
                            String OFPN_FEC_MODI = "CURRENT_TIMESTAMP";
                            // B1
                            String OFPN_FECHA_FICHA_ATE = ATEN_B1TDF+" "+ATEN_B1TDFH;
                            String OFPN_MOTIVO_DE_LA_CONSULTA = ATEN_B1TMDLC;
                            String OFPN_ALIMENTOS_DE_PREFERENCIA = ATEN_B1TAP;
                            String OFPN_ALIMENTOS_QUE_NO_TOLERA = ATEN_B1TANT;
                            String OFPN_ALI_QUE_SUELE_COMER_GRL = ATEN_B1TACG;
                            String OFPN_CONSUMO_ALCOHOL = ATEN_B1TCA;
                            String OFPN_CONSUMO_CIGARRILLO = ATEN_B1TCC;
                            String OFPN_ALERGIAS_A_ALGO = ATEN_B1TA;
                            String OFPN_CIRUGIAS = ATEN_B1TC;
                            String OFPN_PADECE_ALGUNA_ENFERMEDAD = ATEN_B1TE;
                            String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = ATEN_B1TM;
                            String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = ATEN_B1TOD;
                            // B2
                            String OFPN_REALIZA_ACTIVIDAD_FISICA = ATEN_B2CRAF;
                            String OFPN_TIPO_DE_ACTIVIDAD_FISICA = ATEN_B2TTE;
                            String OFPN_FRECUENCIA_ACT_FISICA_SEM = ATEN_B2TEF;
                            String OFPN_DBLCR = ATEN_B2CDCR; // DIGIERE BIEN LAS CARNES ROJAS 
                            String OFPN_LGSLCM = ATEN_B2CGS; // LA GRASA SATURADA LE CAE MAL 
                            String OFPN_TBDALN = ATEN_B2CBD; // TIENE BUENA DIGESTION A LA NOCHE 
                            String OFPN_DPALN = ATEN_B2CDP; // DUERMEN PROFUNDAMENTE A LA NOCHE 
                            String OFPN_DDCCF = ATEN_B2CDDC; // DOLORES DE CABEZA CON FRECUENCIA 
                            String OFPN_ESTRENHIMIENTO = ATEN_B2CE;
                            String OFPN_TDEDBU = ATEN_B2CTDEDBU; // TIPO DE ESCALA DE BRISTOL USUAL 
                            String OFPN_CANSANCIO_FATIGA = ATEN_B2CCF;
                            String OFPN_HICHAZON_ABDOMINAL = ATEN_B2CHA;
                            String OFPN_INSOMNIO = ATEN_B2CI;
                            String OFPN_MUCOSIDAD_Y_CATARRO = ATEN_B2CMC;
                            String OFPN_CALAMBRES_Y_HORMIGUEOS = ATEN_B2CCOH;
                            String OFPN_ZUMBIDOS_EN_EL_OIDO = ATEN_B2CZO;
                            String OFPN_CAIDA_DE_CABELLO = ATEN_B2CCDC;
                            String OFPN_UNHAS_QUEBRADIZAS = ATEN_B2CUQ;
                            String OFPN_PIEL_SECA = ATEN_B2CPS;
                            String OFPN_TIPO_DE_METABOLISMO = ATEN_B2TM;
                            // B3
                            String OFPN_ESTATURA = ATEN_B3TE;
                            String OFPN_PESO = ATEN_B3TP;
                            String OFPN_IMC = ATEN_B3TI;
                            String OFPN_PORC_GRASA = ATEN_B3TPG;
                            String OFPN_PORC_MUSCULO = ATEN_B3TPM;
                            String OFPN_VISCERAL = ATEN_B3TV;
                            String OFPN_EDAD_METABOLICA = ATEN_B3TEM;
                            String OFPN_RM = ATEN_B3TRM;
                            String OFPN_TIPO_DE_BALANZA = ATEN_B3TB;
                            // B4
                            String OFPN_RESULTADOS_TEST_BIORESONANCIA = ATEN_B4TRT;
                            String OFPN_SUPLEMENTACION_RECETADA = ATEN_B4TSR;
                            String OFPN_LABORATORIO = ATEN_B4TL;
                            String OFPN_COMENTARIOS_GENERALES = ATEN_B4TCG;
                            
                            // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                            TIPO_MENSAJE = metodosNutri.guardar_cab(new BeanFichaAtePaciente(OFPN_IDFICHAPAC, OFPN_IDAGENDAMIENTO, OFPN_ITEM_AGEND_DET, OFPN_IDPACIENTE, OFPN_FECHA_FICHA_ATE, OFPN_MOTIVO_DE_LA_CONSULTA, OFPN_ALIMENTOS_DE_PREFERENCIA, OFPN_ALIMENTOS_QUE_NO_TOLERA, OFPN_ALI_QUE_SUELE_COMER_GRL, OFPN_CONSUMO_ALCOHOL, OFPN_CONSUMO_CIGARRILLO, OFPN_ALERGIAS_A_ALGO, OFPN_CIRUGIAS, OFPN_PADECE_ALGUNA_ENFERMEDAD, OFPN_MEDICAMENTE_Q_E_CONSUMIENDO, OFPN_OTROS_DATOS_A_TENER_EN_CUENTA, OFPN_REALIZA_ACTIVIDAD_FISICA, OFPN_TIPO_DE_ACTIVIDAD_FISICA, OFPN_FRECUENCIA_ACT_FISICA_SEM, OFPN_DBLCR, OFPN_LGSLCM, OFPN_TBDALN, OFPN_DPALN, OFPN_DDCCF, OFPN_ESTRENHIMIENTO, OFPN_TDEDBU, OFPN_CANSANCIO_FATIGA, OFPN_HICHAZON_ABDOMINAL, OFPN_INSOMNIO, OFPN_MUCOSIDAD_Y_CATARRO, OFPN_CALAMBRES_Y_HORMIGUEOS, OFPN_ZUMBIDOS_EN_EL_OIDO, OFPN_CAIDA_DE_CABELLO, OFPN_UNHAS_QUEBRADIZAS, OFPN_PIEL_SECA, OFPN_TIPO_DE_METABOLISMO, OFPN_ESTATURA, OFPN_PESO, OFPN_IMC, OFPN_PORC_GRASA, OFPN_PORC_MUSCULO, OFPN_VISCERAL, OFPN_EDAD_METABOLICA, OFPN_RM, OFPN_TIPO_DE_BALANZA, OFPN_RESULTADOS_TEST_BIORESONANCIA, OFPN_SUPLEMENTACION_RECETADA, OFPN_LABORATORIO, OFPN_COMENTARIOS_GENERALES, OFPN_USU_ALTA, OFPN_FEC_ALTA, OFPN_ESTADO, OFPN_IDCLINICA, OFPN_USU_MODI, OFPN_FEC_MODI, ""), LISTA_ARCHIVOS);
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
                            request.setAttribute("CFAP_PAC_DATOS", datosPac);
                            // BLOQUE 1 
                            datosFichaCab01.add(ATEN_B1TDF);
                            datosFichaCab01.add(ATEN_B1TDFH);
                            datosFichaCab01.add(ATEN_B1TMDLC);
                            datosFichaCab01.add(ATEN_B1TAP);
                            datosFichaCab01.add(ATEN_B1TACG);
                            datosFichaCab01.add(ATEN_B1TANT);
                            datosFichaCab01.add(ATEN_B1TCA);
                            datosFichaCab01.add(ATEN_B1TCC);
                            datosFichaCab01.add(ATEN_B1TA);
                            datosFichaCab01.add(ATEN_B1TE);
                            datosFichaCab01.add(ATEN_B1TC);
                            datosFichaCab01.add(ATEN_B1TM);
                            datosFichaCab01.add(ATEN_B1TOD);
                            datosFichaCab01 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab01, "");
                            // BLOQUE 2 
                            datosFichaCab02.add(ATEN_B2CRAF);
                            datosFichaCab02.add(ATEN_B2TTE);
                            datosFichaCab02.add(ATEN_B2TEF);
                            datosFichaCab02.add(ATEN_B2CDCR);
                            datosFichaCab02.add(ATEN_B2CE);
                            datosFichaCab02.add(ATEN_B2CCOH);
                            datosFichaCab02.add(ATEN_B2CGS);
                            datosFichaCab02.add(ATEN_B2CCF);
                            datosFichaCab02.add(ATEN_B2CZO);
                            datosFichaCab02.add(ATEN_B2CBD);
                            datosFichaCab02.add(ATEN_B2CHA);
                            datosFichaCab02.add(ATEN_B2CCDC);
                            datosFichaCab02.add(ATEN_B2CDP);
                            datosFichaCab02.add(ATEN_B2CI);
                            datosFichaCab02.add(ATEN_B2CUQ);
                            datosFichaCab02.add(ATEN_B2CDDC);
                            datosFichaCab02.add(ATEN_B2CMC);
                            datosFichaCab02.add(ATEN_B2CPS);
                            datosFichaCab02.add(ATEN_B2TM);
                            datosFichaCab02.add(ATEN_B2CTDEDBU);
                            datosFichaCab02 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab02, "");
                            // BLOQUE 3 
                            datosFichaCab03.add(ATEN_B3TE);
                            datosFichaCab03.add(ATEN_B3TPG);
                            datosFichaCab03.add(ATEN_B3TEM);
                            datosFichaCab03.add(ATEN_B3TP);
                            datosFichaCab03.add(ATEN_B3TPM);
                            datosFichaCab03.add(ATEN_B3TRM);
                            datosFichaCab03.add(ATEN_B3TI);
                            datosFichaCab03.add(ATEN_B3TV);
                            datosFichaCab03.add(ATEN_B3TB);
                            datosFichaCab03 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab03, "");
                            // BLOQUE 4 
                            datosFichaCab04.add(ATEN_B4TRT);
                            datosFichaCab04.add(ATEN_B4TSR);
                            datosFichaCab04.add(ATEN_B4TL);
                            datosFichaCab04.add(ATEN_B4TCG);
                            datosFichaCab04 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab04, "");
                            // LES DEVUELVO LAS VARS AL JSP.-
                            request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
                            request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
                            request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
                            request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
                            // RECUPERO LA LISTA Y LA VUELVO A PASAR 
                            List<BeanFichaAtePaciente> datosUltFichaPac = (List) sesionDatosUsuario.getAttribute("CFAP_LAST_FICHA_VALUES");
//                            List<BeanFichaAtePaciente> datosUltFichaPac = metodosNutri.getUltimaFicha(IDPACIENTE);
                            sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
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
                    } // end-if-idatencion.-
                    
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
                    ID_PACIENTE_EXP = IDPACIENTE;
                    String file_separador =  File.separator;
                    String path_destino = request.getServletContext().getRealPath("//recursos//download//");
//                    String path_destino = modeloIniSes.getPathFileLocation();
                    
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
                    
                    if (idAtencionPac.isEmpty() || idAtencionPac.equals("") || idAtencionPac==null) { // agregar if para poder identificar si el idatencionpaciente esta vacio asi para guardar y si esta cargado luego de guardar ya no me vuelva a guardar y crear nomas una segunda accion para poder modificar para no arriesgar el guardar 
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
                        // DATOS DEL PACIENTE Y DE LOS BLOQUES 
                        List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
                        List<String> datosFichaCab01 = new ArrayList<>();
                        List<String> datosFichaCab02 = new ArrayList<>();
                        List<String> datosFichaCab03 = new ArrayList<>();
                        List<String> datosFichaCab04 = new ArrayList<>();
                        // RECUPERAR LOS DATOS 
                        // BLOQUE #1 / 
                        String ATEN_B1TDF = (String) request.getParameter("B1TDF"); // FECHA FICHA 
                        if (ATEN_B1TDF==null || ATEN_B1TDF.isEmpty()) {
                            System.out.println("____CTRL__GUARDAR______NULL_FECHA_FICHA____");
                            ATEN_B1TDF = modeloIniSes.traerFechaHoy();
                        }
                        String ATEN_B1TDFH = (String) request.getParameter("B1TDFH"); // HORA FICHA 
                        if (ATEN_B1TDFH==null || ATEN_B1TDFH.isEmpty()) {
                            System.out.println("____CTRL__GUARDAR______NULL_HORA_FICHA____");
                            ATEN_B1TDFH = modeloIniSes.getHoraHoy();
                        }
                        String ATEN_B1TMDLC = (String) request.getParameter("B1TMDLC");
                        String ATEN_B1TAP = (String) request.getParameter("B1TAP");
                        String ATEN_B1TACG = (String) request.getParameter("B1TACG");
                        String ATEN_B1TANT = (String) request.getParameter("B1TANT");
                        String ATEN_B1TCA = (String) request.getParameter("B1TCA"); // CONSUMO ALCOHOL 
                        String ATEN_B1TCC = (String) request.getParameter("B1TCC"); // CONSUMO CIGARRILLO 
                        String ATEN_B1TA = (String) request.getParameter("B1TA");
                        String ATEN_B1TE = (String) request.getParameter("B1TE");
                        String ATEN_B1TC = (String) request.getParameter("B1TC");
                        String ATEN_B1TM = (String) request.getParameter("B1TM");
                        String ATEN_B1TOD = (String) request.getParameter("B1TOD");
                        // BLOQUE #2 / 
                        String ATEN_B2CRAF = (String) request.getParameter("B2CRAF");
                        String ATEN_B2TTE = (String) request.getParameter("B2TTE");
                        String ATEN_B2TEF = (String) request.getParameter("B2TEF");
                        String ATEN_B2CDCR = (String) request.getParameter("B2CDCR");
                        String ATEN_B2CE = (String) request.getParameter("B2CE");
                        String ATEN_B2CCOH = (String) request.getParameter("B2CCOH");
                        String ATEN_B2CGS = (String) request.getParameter("B2CGS");
                        String ATEN_B2CCF = (String) request.getParameter("B2CCF");
                        String ATEN_B2CZO = (String) request.getParameter("B2CZO");
                        String ATEN_B2CBD = (String) request.getParameter("B2CBD");
                        String ATEN_B2CHA = (String) request.getParameter("B2CHA");
                        String ATEN_B2CCDC = (String) request.getParameter("B2CCDC");
                        String ATEN_B2CDP = (String) request.getParameter("B2CDP");
                        String ATEN_B2CI = (String) request.getParameter("B2CI");
                        String ATEN_B2CUQ = (String) request.getParameter("B2CUQ");
                        String ATEN_B2CDDC = (String) request.getParameter("B2CDDC");
                        String ATEN_B2CMC = (String) request.getParameter("B2CMC");
                        String ATEN_B2CPS = (String) request.getParameter("B2CPS");
                        String ATEN_B2TM = (String) request.getParameter("B2TM");
                        String ATEN_B2CTDEDBU = (String) request.getParameter("B2CTDEDBU");
                        // BLOQUE #3 / 
                        String ATEN_B3TE = (String) request.getParameter("B3TE");
                        String ATEN_B3TPG = (String) request.getParameter("B3TPG");
                        String ATEN_B3TEM = (String) request.getParameter("B3TEM");
                        String ATEN_B3TP = (String) request.getParameter("B3TP");
                        String ATEN_B3TPM = (String) request.getParameter("B3TPM");
                        String ATEN_B3TRM = (String) request.getParameter("B3TRM");
                        String ATEN_B3TI = (String) request.getParameter("B3TI");
                        String ATEN_B3TV = (String) request.getParameter("B3TV");
                        String ATEN_B3TB = (String) request.getParameter("B3TB");
                        // BLOQUE #4 / 
                        String ATEN_B4TRT = (String) request.getParameter("B4TRT");
                        String ATEN_B4TSR = (String) request.getParameter("B4TSR");
                        String ATEN_B4TL = (String) request.getParameter("B4TL");
                        String ATEN_B4TCG = (String) request.getParameter("B4TCG");
                        String ATEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
        //                String ATEN_ESTADO = (String) request.getParameter("cEs");
//                        if (ATEN_ESTADO == null || ATEN_ESTADO.isEmpty()) { varValiVacio++; ATEN_ESTADO=""; }
                        
                        // IMPRESION DE DATOS 
                        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #1    ------");
                        System.out.println(".");
                        System.out.println("-       _FECHA-DE-LA-FICHA:-----------------:"+ATEN_B1TDF);
                        System.out.println("-       _HORA-DE-LA-FICHA:------------------:"+ATEN_B1TDFH);
                        System.out.println("-       _MOTIVO_CONSULTA:-------------------:"+ATEN_B1TMDLC);
                        System.out.println("-       _ALIMENTOS_DE_PREFERENCIA:----------:"+ATEN_B1TAP);
                        System.out.println("-       _ALIMENTOS-QUE-COMES-GENERALMENTE:--:"+ATEN_B1TACG);
                        System.out.println("-       _ALIMENTOS-QUE-NO-TOLERA:-----------:"+ATEN_B1TANT);
                        System.out.println("-       _CONSUMO_ALCOHOL:-------------------:"+ATEN_B1TCA);
                        System.out.println("-       _CONSUMO_CIGARRILLO:----------------:"+ATEN_B1TCC);
                        System.out.println("-       _ALERGIAS:--------------------------:"+ATEN_B1TA);
                        System.out.println("-       _ENFERMEDAD:------------------------:"+ATEN_B1TE);
                        System.out.println("-       _CIRUGIAS:--------------------------:"+ATEN_B1TC);
                        System.out.println("-       _MEDICAMENTOS:----------------------:"+ATEN_B1TM);
                        System.out.println("-       _OTROS-DATOS-A-TENER-EN-CUENTA:-----:"+ATEN_B1TOD);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #2    ------");
                        System.out.println(".");
                        System.out.println("-       _REALIZA-ACTIVIDAD-FISICA:---------:"+ATEN_B2CRAF);
                        System.out.println("-       _TIPO-EJERCICIO:-------------------:"+ATEN_B2TTE);
                        System.out.println("-       _EJERCICIO-FRECUENCIA:-------------:"+ATEN_B2TEF);
                        System.out.println("-       _DIRIGE-BIEN-LAS-CARNES-ROJAS:-----:"+ATEN_B2CDCR);
                        System.out.println("-       _ESTRENHIMIENTO:-------------------:"+ATEN_B2CE);
                        System.out.println("-       _CALAMBRES-Y/O-HORMIGUEOS:---------:"+ATEN_B2CCOH);
                        System.out.println("-       _GRASAS-SATURADAS:-----------------:"+ATEN_B2CGS);
                        System.out.println("-       _CANSANCIO-Y-FATIGA:---------------:"+ATEN_B2CCF);
                        System.out.println("-       _ZUMBIDOS-EN-EL-OIDO:--------------:"+ATEN_B2CZO);
                        System.out.println("-       _TIENE-BUENA-DIGESTION-A-LA-NOCHE:-:"+ATEN_B2CBD);
                        System.out.println("-       _HINCHAZON-ABDOMINAL:--------------:"+ATEN_B2CHA);
                        System.out.println("-       _CAIDA-DEL-CABELLO:----------------:"+ATEN_B2CCDC);
                        System.out.println("-       _DUERME-PROFUNDAMENTE:-------------:"+ATEN_B2CDP);
                        System.out.println("-       _INSOMNIO:-------------------------:"+ATEN_B2CI);
                        System.out.println("-       _UNHAS-QUEBRADIZAS:----------------:"+ATEN_B2CUQ);
                        System.out.println("-       _DOLORES-DE-CABEZA-CON-FRECUENCIA:-:"+ATEN_B2CDDC);
                        System.out.println("-       _MUCOSIDAD-Y-CATARRO:--------------:"+ATEN_B2CMC);
                        System.out.println("-       _PIEL-SECA:------------------------:"+ATEN_B2CPS);
                        System.out.println("-       _METABOLISMO:----------------------:"+ATEN_B2TM);
                        System.out.println("-       _TIPO-ESCALA-BRISTOL-USUAL:--------:"+ATEN_B2CTDEDBU);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #3    ------");
                        System.out.println(".");
                        System.out.println("-       _ESTATURA:-------------------------:"+ATEN_B3TE);
                        System.out.println("-       _PORCENTAJE-GRASA:-----------------:"+ATEN_B3TPG);
                        System.out.println("-       _EDAD-M:---------------------------:"+ATEN_B3TEM);
                        System.out.println("-       _PESO:-----------------------------:"+ATEN_B3TP);
                        System.out.println("-       _PORCENTAJE-MUSCULO:---------------:"+ATEN_B3TPM);
                        System.out.println("-       _RM:-------------------------------:"+ATEN_B3TRM);
                        System.out.println("-       _IMC:------------------------------:"+ATEN_B3TI);
                        System.out.println("-       _VISCERAL:-------------------------:"+ATEN_B3TV);
                        System.out.println("-       _BALANZA:--------------------------:"+ATEN_B3TB);
                        System.out.println(".");
                        System.out.println(".------     BLOQUE #4    ------");
                        System.out.println(".");
                        System.out.println("-       _RESULTADOS-TEST:------------------:"+ATEN_B4TRT);
                        System.out.println("-       _SUPLEMENTACION-RECETADA:----------:"+ATEN_B4TSR);
                        System.out.println("-       _LABORATORIO:----------------------:"+ATEN_B4TL);
                        System.out.println("-       _COMENTARIOS-GENERALES:------------:"+ATEN_B4TCG);
                        System.out.println(".");
                        System.out.println("-       ATEN_ESTADO:-----------------------:"+ATEN_ESTADO);
                        System.out.println("-       ATEN_IDCLINICA:--------------------:"+ID_CLINICA);
                        System.out.println("-   -----------------------------------------------------------------------------");
                        System.out.println("___     ___var_vali_vacio:     :"+varValiVacio);
                        
                        // LISTA DE ARCHIVOS -- 
                        List<BeanFichaAtePacienteDet> LISTA_ARCHIVOS = new ArrayList<>();
                        if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                            LISTA_ARCHIVOS = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                        }
//                            int ITEM_LIST_FILES = 1;
//                            if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES") == null) == false) {
//                                ITEM_LIST_FILES = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES"));
//                            }
                        
                        // VALIDACIONES 
                        String MENSAJE=null, TIPO_MENSAJE=""; // 1 : exito / 2 : error 
        /*OBS.: LE PONGO MAYOR A 99 PORQUE NO VA A LLEGAR A ESE MONTO Y LA VALIDACION NO SE VA A ACTIVAR, 
                YA QUE SE PUEDE GUARDAR CON CAMPOS VACIOS Y NO QUIERO COMENTAR ESTA VALIDACION */                
                        if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                            MENSAJE = "No puede dejar campos vacios.";
                            TIPO_MENSAJE = "2";
                            if (return_pag.isEmpty()) { // validacion para controlar donde retornara, en caso de que la variable este vacio le retornare a la pagina de atencion pero en caso de que se encuentre cargado, le retornara a la pagina donde le redirecciono a la ficha de atencion 
                                acceso = "add_atencion.htm";
                            } else {
                                acceso = return_pag;
                            }
                            
//                        } else if (metodosNutri.ctrlArchivosAdjuntos(request, LISTA_ARCHIVOS, new File(path_destino.replace("*", file_separador))) == true) {
//                            String valiArchivoAdjunto = (String) request.getAttribute("VALI_CTRL_ADD_FILE_VARNAME");
//                            MENSAJE = "Debe de cambiar el nombre del archivo adjunto \""+valiArchivoAdjunto+"\" para poder vincularla con la ficha porque ya existe un archivo con el mismo nombre.";
//                            TIPO_MENSAJE = "2";
//                            acceso = "add_atencion_af.htm";
//                            request.setAttribute("CFAP_AF_MENSAJE", MENSAJE);
//                            request.setAttribute("CFAP_AF_TIPO_MENSAJE", TIPO_MENSAJE);
                            
                        } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                            // CABECERA 
                            String OFPN_IDFICHAPAC = idAtencionPac;
                            String OFPN_IDAGENDAMIENTO = IDAGENDAMIENTO;
                            String OFPN_ITEM_AGEND_DET = ITEM_AGEND;
                            String OFPN_IDPACIENTE = IDPACIENTE;
                            String OFPN_USU_ALTA = idPersona;
                            String OFPN_FEC_ALTA = modeloIniSes.traerFechaHoraHoy();
                            String OFPN_ESTADO = ATEN_ESTADO;
                            String OFPN_IDCLINICA = ID_CLINICA;
                            String OFPN_USU_MODI = "";
                            String OFPN_FEC_MODI = "CURRENT_TIMESTAMP";
                            // B1
                            String OFPN_FECHA_FICHA_ATE = ATEN_B1TDF+" "+ATEN_B1TDFH;
                            String OFPN_MOTIVO_DE_LA_CONSULTA = ATEN_B1TMDLC;
                            String OFPN_ALIMENTOS_DE_PREFERENCIA = ATEN_B1TAP;
                            String OFPN_ALIMENTOS_QUE_NO_TOLERA = ATEN_B1TANT;
                            String OFPN_ALI_QUE_SUELE_COMER_GRL = ATEN_B1TACG;
                            String OFPN_CONSUMO_ALCOHOL = ATEN_B1TCA;
                            String OFPN_CONSUMO_CIGARRILLO = ATEN_B1TCC;
                            String OFPN_ALERGIAS_A_ALGO = ATEN_B1TA;
                            String OFPN_CIRUGIAS = ATEN_B1TC;
                            String OFPN_PADECE_ALGUNA_ENFERMEDAD = ATEN_B1TE;
                            String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = ATEN_B1TM;
                            String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = ATEN_B1TOD;
                            // B2
                            String OFPN_REALIZA_ACTIVIDAD_FISICA = ATEN_B2CRAF;
                            String OFPN_TIPO_DE_ACTIVIDAD_FISICA = ATEN_B2TTE;
                            String OFPN_FRECUENCIA_ACT_FISICA_SEM = ATEN_B2TEF;
                            String OFPN_DBLCR = ATEN_B2CDCR; // DIGIERE BIEN LAS CARNES ROJAS 
                            String OFPN_LGSLCM = ATEN_B2CGS; // LA GRASA SATURADA LE CAE MAL 
                            String OFPN_TBDALN = ATEN_B2CBD; // TIENE BUENA DIGESTION A LA NOCHE 
                            String OFPN_DPALN = ATEN_B2CDP; // DUERMEN PROFUNDAMENTE A LA NOCHE 
                            String OFPN_DDCCF = ATEN_B2CDDC; // DOLORES DE CABEZA CON FRECUENCIA 
                            String OFPN_ESTRENHIMIENTO = ATEN_B2CE;
                            String OFPN_TDEDBU = ATEN_B2CTDEDBU; // TIPO DE ESCALA DE BRISTOL USUAL 
                            String OFPN_CANSANCIO_FATIGA = ATEN_B2CCF;
                            String OFPN_HICHAZON_ABDOMINAL = ATEN_B2CHA;
                            String OFPN_INSOMNIO = ATEN_B2CI;
                            String OFPN_MUCOSIDAD_Y_CATARRO = ATEN_B2CMC;
                            String OFPN_CALAMBRES_Y_HORMIGUEOS = ATEN_B2CCOH;
                            String OFPN_ZUMBIDOS_EN_EL_OIDO = ATEN_B2CZO;
                            String OFPN_CAIDA_DE_CABELLO = ATEN_B2CCDC;
                            String OFPN_UNHAS_QUEBRADIZAS = ATEN_B2CUQ;
                            String OFPN_PIEL_SECA = ATEN_B2CPS;
                            String OFPN_TIPO_DE_METABOLISMO = ATEN_B2TM;
                            // B3
                            String OFPN_ESTATURA = ATEN_B3TE;
                            String OFPN_PESO = ATEN_B3TP;
                            String OFPN_IMC = ATEN_B3TI;
                            String OFPN_PORC_GRASA = ATEN_B3TPG;
                            String OFPN_PORC_MUSCULO = ATEN_B3TPM;
                            String OFPN_VISCERAL = ATEN_B3TV;
                            String OFPN_EDAD_METABOLICA = ATEN_B3TEM;
                            String OFPN_RM = ATEN_B3TRM;
                            String OFPN_TIPO_DE_BALANZA = ATEN_B3TB;
                            // B4
                            String OFPN_RESULTADOS_TEST_BIORESONANCIA = ATEN_B4TRT;
                            String OFPN_SUPLEMENTACION_RECETADA = ATEN_B4TSR;
                            String OFPN_LABORATORIO = ATEN_B4TL;
                            String OFPN_COMENTARIOS_GENERALES = ATEN_B4TCG;
                            
                            // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL AGENDAMIENTO 
                            TIPO_MENSAJE = metodosNutri.modificar_cab(new BeanFichaAtePaciente(OFPN_IDFICHAPAC, OFPN_IDAGENDAMIENTO, OFPN_ITEM_AGEND_DET, OFPN_IDPACIENTE, OFPN_FECHA_FICHA_ATE, OFPN_MOTIVO_DE_LA_CONSULTA, OFPN_ALIMENTOS_DE_PREFERENCIA, OFPN_ALIMENTOS_QUE_NO_TOLERA, OFPN_ALI_QUE_SUELE_COMER_GRL, OFPN_CONSUMO_ALCOHOL, OFPN_CONSUMO_CIGARRILLO, OFPN_ALERGIAS_A_ALGO, OFPN_CIRUGIAS, OFPN_PADECE_ALGUNA_ENFERMEDAD, OFPN_MEDICAMENTE_Q_E_CONSUMIENDO, OFPN_OTROS_DATOS_A_TENER_EN_CUENTA, OFPN_REALIZA_ACTIVIDAD_FISICA, OFPN_TIPO_DE_ACTIVIDAD_FISICA, OFPN_FRECUENCIA_ACT_FISICA_SEM, OFPN_DBLCR, OFPN_LGSLCM, OFPN_TBDALN, OFPN_DPALN, OFPN_DDCCF, OFPN_ESTRENHIMIENTO, OFPN_TDEDBU, OFPN_CANSANCIO_FATIGA, OFPN_HICHAZON_ABDOMINAL, OFPN_INSOMNIO, OFPN_MUCOSIDAD_Y_CATARRO, OFPN_CALAMBRES_Y_HORMIGUEOS, OFPN_ZUMBIDOS_EN_EL_OIDO, OFPN_CAIDA_DE_CABELLO, OFPN_UNHAS_QUEBRADIZAS, OFPN_PIEL_SECA, OFPN_TIPO_DE_METABOLISMO, OFPN_ESTATURA, OFPN_PESO, OFPN_IMC, OFPN_PORC_GRASA, OFPN_PORC_MUSCULO, OFPN_VISCERAL, OFPN_EDAD_METABOLICA, OFPN_RM, OFPN_TIPO_DE_BALANZA, OFPN_RESULTADOS_TEST_BIORESONANCIA, OFPN_SUPLEMENTACION_RECETADA, OFPN_LABORATORIO, OFPN_COMENTARIOS_GENERALES, OFPN_USU_ALTA, OFPN_FEC_ALTA, OFPN_ESTADO, OFPN_IDCLINICA, OFPN_USU_MODI, OFPN_FEC_MODI, ""), LISTA_ARCHIVOS);
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
                            request.setAttribute("CFAP_PAC_DATOS", datosPac);
                            // BLOQUE 1 
                            datosFichaCab01.add(ATEN_B1TDF);
                            datosFichaCab01.add(ATEN_B1TDFH);
                            datosFichaCab01.add(ATEN_B1TMDLC);
                            datosFichaCab01.add(ATEN_B1TAP);
                            datosFichaCab01.add(ATEN_B1TACG);
                            datosFichaCab01.add(ATEN_B1TANT);
                            datosFichaCab01.add(ATEN_B1TCA);
                            datosFichaCab01.add(ATEN_B1TCC);
                            datosFichaCab01.add(ATEN_B1TA);
                            datosFichaCab01.add(ATEN_B1TE);
                            datosFichaCab01.add(ATEN_B1TC);
                            datosFichaCab01.add(ATEN_B1TM);
                            datosFichaCab01.add(ATEN_B1TOD);
                            datosFichaCab01 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab01, "");
                            // BLOQUE 2 
                            datosFichaCab02.add(ATEN_B2CRAF);
                            datosFichaCab02.add(ATEN_B2TTE);
                            datosFichaCab02.add(ATEN_B2TEF);
                            datosFichaCab02.add(ATEN_B2CDCR);
                            datosFichaCab02.add(ATEN_B2CE);
                            datosFichaCab02.add(ATEN_B2CCOH);
                            datosFichaCab02.add(ATEN_B2CGS);
                            datosFichaCab02.add(ATEN_B2CCF);
                            datosFichaCab02.add(ATEN_B2CZO);
                            datosFichaCab02.add(ATEN_B2CBD);
                            datosFichaCab02.add(ATEN_B2CHA);
                            datosFichaCab02.add(ATEN_B2CCDC);
                            datosFichaCab02.add(ATEN_B2CDP);
                            datosFichaCab02.add(ATEN_B2CI);
                            datosFichaCab02.add(ATEN_B2CUQ);
                            datosFichaCab02.add(ATEN_B2CDDC);
                            datosFichaCab02.add(ATEN_B2CMC);
                            datosFichaCab02.add(ATEN_B2CPS);
                            datosFichaCab02.add(ATEN_B2TM);
                            datosFichaCab02.add(ATEN_B2CTDEDBU);
                            datosFichaCab02 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab02, "");
                            // BLOQUE 3 
                            datosFichaCab03.add(ATEN_B3TE);
                            datosFichaCab03.add(ATEN_B3TPG);
                            datosFichaCab03.add(ATEN_B3TEM);
                            datosFichaCab03.add(ATEN_B3TP);
                            datosFichaCab03.add(ATEN_B3TPM);
                            datosFichaCab03.add(ATEN_B3TRM);
                            datosFichaCab03.add(ATEN_B3TI);
                            datosFichaCab03.add(ATEN_B3TV);
                            datosFichaCab03.add(ATEN_B3TB);
                            datosFichaCab03 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab03, "");
                            // BLOQUE 4 
                            datosFichaCab04.add(ATEN_B4TRT);
                            datosFichaCab04.add(ATEN_B4TSR);
                            datosFichaCab04.add(ATEN_B4TL);
                            datosFichaCab04.add(ATEN_B4TCG);
                            datosFichaCab04 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab04, "");
                            // LES DEVUELVO LAS VARS AL JSP.-
                            request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
                            request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
                            request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
                            request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
                            // RECUPERO LA LISTA Y LA VUELVO A PASAR 
                            List<BeanFichaAtePaciente> datosUltFichaPac = (List) sesionDatosUsuario.getAttribute("CFAP_LAST_FICHA_VALUES");
//                            List<BeanFichaAtePaciente> datosUltFichaPac = metodosNutri.getUltimaFicha(IDPACIENTE);
                            sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
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
                    List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
                    listaFiltro = metodosNutri.filtrar_paginacion(sesionDatosUsuario, filtro_cbxMostrar, filtro_fecha, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDPACIENTE);
                    
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
                    ID_PACIENTE_EXP = IDPACIENTE;
                    
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
                        List<BeanFichaAtePaciente> LISTA_SERVICIOS = new ArrayList<>();
                        if ((sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS") == null) == false) { // si la lista del detalle no esta null entonces cargo de la sesion a la variable 
                            LISTA_SERVICIOS = (List<BeanFichaAtePaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                        }
                        int ITEM_LIST_SERV = 1;
                        if ((sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS") == null) == false) {
                            ITEM_LIST_SERV = Integer.parseInt((String)sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS"));
                        }
                        
                        String MENSAJE = null, TIPO_MENSAJE = "";
                        // ELIMINO DE LA LISTA -- 
                        List<BeanFichaAtePaciente> CFAP_LISTA_NEW = new ArrayList<BeanFichaAtePaciente>(); // LISTA NUEVA QUE TENDRA TODOS LOS DATOS DE LA ANTERIOR LISTA SOLO QUE TENDRA RESETEADO EL NRO DE ITEM 
                        int itemNew = 1; // VARIABLE QUE UTILIZARE PARA CARGAR EL NUEVO NRO DE ITEM 
                        try {
                            // VALIDACION PARA CONTROLAR QUE EL IDATENCIONPACIENTE ESTE CARGADO PARA PODER ENVIAR EL UPDATE A LA TABLA 
                            if (!(idAtencionPac == null) || !(idAtencionPac.isEmpty())) {
                                BeanFichaAtePaciente datos_eli = new BeanFichaAtePaciente();
                                    datos_eli.setOFPN_IDFICHAPAC(idAtencionPac);
                                    datos_eli.setOFPN_IDAGENDAMIENTO(IDAGENDAMIENTO);
                                    datos_eli.setOFPN_ITEM_AGEND_DET(ITEM_AGEND);
                                    datos_eli.setOFPN_USU_ALTA(idPersona);
                                // LLAMO AL METODO PARA ELIMINAR LA FILA DE LA BASE Y CARGO EL RESULTADO DE LA EJECUCION DE LA SENTENCIA PARA SABER QUE SI REALIZO CORRECTAMENTE O DIO ALGUN ERROR 
                                TIPO_MENSAJE = metodosNutri.eliminar_cab(datos_eli);
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
                            Logger.getLogger(ControllerFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
                            System.out.println(".");System.out.println(".");
                        } // end catch  // end catch 
                        
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
                    
                } else if(accion.equalsIgnoreCase("Volver Atras")) {
                    System.out.println("----------------------------__VOLVER_ATRAS__--------------------------------");
                    String volver_atras = acceso;
                    String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS");
                    System.out.println("_   __JSP___BTN_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                    if (BTN_VOLVER_ATRAS == null) {
                        String idPerfil = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
                        System.out.println(".   ___ID_PERFIL:    :"+idPerfil);
                        System.out.println("_   _______IS__NULL_______");
                        if (modeloPerfil.isPerfilAdmin(idPerfil)==true || modeloPerfil.isPerfilSecre(idPerfil)==true || modeloPerfil.isPerfilFuncio(idPerfil)==true || modeloPerfil.isPerfilMedico(idPerfil)==true) {
                            System.out.println("_   _   __IF____");
                            volver_atras = acceso;
                        } else {
                            System.out.println("_   _   __ELSE____");
                            volver_atras = "menu.htm";
                        }
                    }
                    System.out.println("_   __BTN_VOLVER_ATRAS:     :"+BTN_VOLVER_ATRAS);
                    
                    if (BTN_VOLVER_ATRAS.equals("VER_PACIENTE")) { // VIENE DE LA VENTANA DE "VER EXPEDIENTE" DE PACIENTE 
                        volver_atras = "ver_paciente.htm";
                    } else if (BTN_VOLVER_ATRAS.equals("PERFIL_PACIENTE")) { // VIENE DE LA PAGINA DE PERFIL SIENDO EL USUARIO UN PERFIL PACIENTE 
                        volver_atras = "perfil_pac.htm";
                    } else if (BTN_VOLVER_ATRAS.equals("VER_CAB_AGENDAMIENTO")) { // VIENE DE LA PAGINA DE VER AGENDAMIENTO 
                        volver_atras = "ver_agend.htm";
//                    } else if (BTN_VOLVER_ATRAS.equals("HISTORICO_PACIENTE")) { // VIENE DE LA PAGINA DE VER AGENDAMIENTO 
//                        volver_atras = "ver_his_fic.htm";
//                        String IDPAC = (String) request.getParameter("tIP");
//                        request.setAttribute("TXT_IDPACIENTE", IDPAC);
////                        request.setAttribute("TXT_PAC_NOMBRE", );
////                        request.setAttribute("TXT_PAC_APELLIDO", );
                    } // end else-if 
                    
                    var_redireccionar = 1;
                    acceso = volver_atras;
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
                        filtrar_pag(sesionDatosUsuario, request, metodosNutri);
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
                        filtrar_pag(sesionDatosUsuario, request, metodosNutri);
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
                        filtrar_pag(sesionDatosUsuario, request, metodosNutri);
                    } else {
                        System.out.println("_   _ELSE_________________");
                    }
                    System.out.println(".");System.out.println(".");
                    System.out.println(".");System.out.println(".");
                }
            }
            
            
            /*
             * COLOCO ACA AL FINAL DE LOS EVENTOS PARA NO IR COLOCANDO EN CADA EVENTO ESTE BLOQUE DE CODIGO 
                Y CONTROLARIA LA VARIABLE QUE REDIRECCIONA A LAS PAGINAS PARA DETECTAR CUANDO VA A VOLVER AL EXPEDIENTE DEL PACIENTE Y EN ESE CASO CARGARIA LAS VARIABLES PARA NO PASAR VACIO LOS DATOS.-
             * SI LA PAGINA A REDIRECCIONAR ES LA DEL EXPEDIENTE DEL PACIENTE 
                ENTONCES LE VOY A PASAR LA LISTA CON LOS DATOS DEL PACIENTE PARA QUE NO REDIRECCIONE Y SE ENCUENTREN VACIOS LOS DATOS 
            */
            System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   ------------CONTROL-DE-FICHA-----EXPEDIENTE---------------");
            System.out.println(".");
            System.out.println(".   ____acceso: "+acceso);
            if (acceso.equalsIgnoreCase("ver_paciente.htm")) { // PAG:   :pagPacienteVer  // en el mav del controlador se carga este dato 
                System.out.println("_   _____IF_______");
                if (ID_PACIENTE_EXP.isEmpty()) {
                    System.out.println("_           _if___id-pac-null-___");
                    ID_PACIENTE_EXP = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                }
                // VER SI PUEDO RECUPERAR DE LA SESION O SI DEBO VOLVER A INVOCAR AL METODO 
                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                if (request.getAttribute("CP_PAC_LISTA_DATOS") != null) {
                    System.out.println("_   _1___if_not_null__");
                    LISTA_DATOS = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                } else {
                    System.out.println("_   _1___else_null__");
                    LISTA_DATOS = metodosPaciente.traerDatosPersona(ID_PACIENTE_EXP);
                }
                System.out.println(".");
                System.out.println(".");
                List<BeanFichaAtePaciente> LISTA_ULTIMOS_DATOS_FICHA = new ArrayList<>(); // los datos de la ultima consulta 
                if (request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA") != null) {
                    System.out.println("_   _2___if_not_null__");
                    LISTA_ULTIMOS_DATOS_FICHA = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
                } else {
                    System.out.println("_   _2___else_null__");
                    LISTA_ULTIMOS_DATOS_FICHA = metodosNutri.getUltimaFicha(ID_PACIENTE_EXP);
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
            
            
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona); // RECUPERO AL INICIO DEL CONTROLADOR Y PARA NO AGREGAR AL FINAL DE CADA EVENTO ENTONCES LE AGREGO AL TERMINAR LAS INSTANCIAS DE LOS EVENTOS / RECUPERO Y AUNQUE PUEDA LLEGAR A PASAR DE QUE EN ALGUN EVENTO NO SE UTILICE ESTA VARIABLE LA PASO PARA MANTENERLA EN SESION Y NO SE LLEGUE A BORRAR POR INACTIVIDAD DE LA VARIABLE 
        } catch (Exception e) {
            System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println("-----------------ERROR-FICHA_ATENCION_PACIENTE--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            Logger.getLogger(ControllerFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
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
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFichaAtencionPacNutri metodos) {
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
        List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar_paginacion(sesionDatosUsuario, filtro_cbxMostrar, filtro_fecha, filtro_txtBuscar, PARAM_TXT_IDMED, PARAM_TXT_IDPACIENTE);

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
    
    
    
    public String ver_ficha_atencion_paciente(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFichaAtencionPacNutri metodosNutri, String idAtencionPac, int var_redireccionar, String acceso) {
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
        
//        // CARGAR LISTA SERVICIOS 
//        List<BeanFichaAtePaciente> LISTA_SERV = new ArrayList<>();
//        int ITEM_LIST_SERV = 0;
        
        // CARGAR_GRILLA_DETALLE 
        List<BeanFichaAtePaciente> FICHA_DATOS = new ArrayList<>();
        FICHA_DATOS = metodosNutri.traer_datos(idAtencionPac, sesionDatosUsuario);
        Iterator<BeanFichaAtePaciente> iListDet = FICHA_DATOS.iterator();
        BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//        String ATEN_PESO="", ATEN_TALLA="", ATEN_IMC="", ATEN_PCEFALICO="", ATEN_FC="", ATEN_TEMP="", ATEN_PA="", ATEN_FRESP="", ATEN_MOTIVO_CONSULTA="", ATEN_EXPLORACION_FISICA="", ATEN_DIAGNOSTICO="", ATEN_TRATAMIENTO="", ATEN_RECETA="", ATEN_ESTUDIOS_SOLICITADOS="";
//        while(iListDet.hasNext()) {
//            datos = iListDet.next();
//            System.out.println("____WHILE____");
//            ATEN_PESO = datos.getOAP_PESO();
//            ATEN_TALLA = datos.getOAP_TALLA();
//            ATEN_IMC = datos.getOAP_IMC();
//            ATEN_PCEFALICO = datos.getOAP_P_CEFALICO();
//            ATEN_FC = datos.getOAP_FC();
//            ATEN_TEMP = datos.getOAP_TEMP();
//            ATEN_PA = datos.getOAP_PA();
//            ATEN_FRESP = datos.getOAP_F_RESP();
//            ATEN_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA();
//            ATEN_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA();
//            ATEN_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO();
//            ATEN_TRATAMIENTO = datos.getOAP_TRATAMIENTO();
//            ATEN_RECETA = datos.getOAP_RECETA();
//            ATEN_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS();
//            System.out.println("_   ITEM_DETALLE:     :"+datos.getOAPD_ITEM());
//            // AGREGO A LA LISTA 
//            if (!(datos.getOAPD_ITEM() == null)) {
//                System.out.println("__      if_____");
//                BeanFichaAtePaciente datosServ = new BeanFichaAtePaciente();
//                    datosServ.setOAPD_IDATENCIONPAC(datos.getOAPD_IDATENCIONPAC());
//                    datosServ.setOAPD_ITEM(datos.getOAPD_ITEM());
//                    datosServ.setOAPD_IDSERVICIO(datos.getOAPD_IDSERVICIO());
//                    datosServ.setRHS_IDSERVICIO(datos.getOAPD_IDSERVICIO());
//                    datosServ.setRHS_DESC_SERVICIO("");
//                    datosServ.setRHS_MONTO(datos.getOAPD_MONTO());
//                    datosServ.setOAPD_MONTO(datos.getOAPD_MONTO());
//                    datosServ.setOAPD_ESTADO(datos.getOAPD_ESTADO());
//                    datosServ.setRHS_ESTADO(datos.getOAPD_ESTADO());
//                LISTA_SERV.add(datosServ);
//                ITEM_LIST_SERV = ITEM_LIST_SERV + 1;
//            } else {
//                System.out.println("__      else____");
//            }
//        }
        
        List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
        List<String> datosFichaCab01 = new ArrayList<>();
        List<String> datosFichaCab02 = new ArrayList<>();
        List<String> datosFichaCab03 = new ArrayList<>();
        List<String> datosFichaCab04 = new ArrayList<>();
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        while(iListDet.hasNext()) {
            datos = iListDet.next();
            System.out.println("____WHILE____");
            String[] FECHA_HORA_FICHA = datos.getOFPN_FECHA_FICHA_ATE().split(" ");
            System.out.println("___________________________FECHA_FICHA:  :"+FECHA_HORA_FICHA[0]);
            System.out.println("___________________________HORA__FICHA:  :"+FECHA_HORA_FICHA[1]);
            // BLOQUE#1
                datosFichaCab01.add(FECHA_HORA_FICHA[0]); // FECHA 
                datosFichaCab01.add(FECHA_HORA_FICHA[1]); // HORA 
                datosFichaCab01.add(datos.getOFPN_MOTIVO_DE_LA_CONSULTA());
                datosFichaCab01.add(datos.getOFPN_ALIMENTOS_DE_PREFERENCIA());
                datosFichaCab01.add(datos.getOFPN_ALI_QUE_SUELE_COMER_GRL());
                datosFichaCab01.add(datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA());
                datosFichaCab01.add(datos.getOFPN_ALERGIAS_A_ALGO());
                datosFichaCab01.add(datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD());
                datosFichaCab01.add(datos.getOFPN_CIRUGIAS());
                datosFichaCab01.add(datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO());
                datosFichaCab01.add(datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA());
                datosFichaCab01.add(datos.getOFPN_CONSUMO_ALCOHOL());
                datosFichaCab01.add(datos.getOFPN_CONSUMO_CIGARRILLO());
            // BLOQUE#2
                datosFichaCab02.add(datos.getOFPN_REALIZA_ACTIVIDAD_FISICA());
                datosFichaCab02.add(datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA());
                datosFichaCab02.add(datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM());
                datosFichaCab02.add(datos.getOFPN_DBLCR());
                datosFichaCab02.add(datos.getOFPN_ESTRENHIMIENTO());
                datosFichaCab02.add(datos.getOFPN_CALAMBRES_Y_HORMIGUEOS());
                datosFichaCab02.add(datos.getOFPN_LGSLCM());
                datosFichaCab02.add(datos.getOFPN_CANSANCIO_FATIGA());
                datosFichaCab02.add(datos.getOFPN_ZUMBIDOS_EN_EL_OIDO());
                datosFichaCab02.add(datos.getOFPN_TBDALN());
                datosFichaCab02.add(datos.getOFPN_HICHAZON_ABDOMINAL());
                datosFichaCab02.add(datos.getOFPN_CAIDA_DE_CABELLO());
                datosFichaCab02.add(datos.getOFPN_DPALN());
                datosFichaCab02.add(datos.getOFPN_INSOMNIO());
                datosFichaCab02.add(datos.getOFPN_UNHAS_QUEBRADIZAS());
                datosFichaCab02.add(datos.getOFPN_DDCCF());
                datosFichaCab02.add(datos.getOFPN_MUCOSIDAD_Y_CATARRO());
                datosFichaCab02.add(datos.getOFPN_PIEL_SECA());
                datosFichaCab02.add(datos.getOFPN_TIPO_DE_METABOLISMO());
                datosFichaCab02.add(datos.getOFPN_TDEDBU());
            // BLOQUE#3
                datosFichaCab03.add(datos.getOFPN_ESTATURA());
                datosFichaCab03.add(datos.getOFPN_PORC_GRASA());
                datosFichaCab03.add(datos.getOFPN_EDAD_METABOLICA());
                datosFichaCab03.add(datos.getOFPN_PESO());
                datosFichaCab03.add(datos.getOFPN_PORC_MUSCULO());
                datosFichaCab03.add(datos.getOFPN_RM());
                datosFichaCab03.add(datos.getOFPN_IMC());
                datosFichaCab03.add(datos.getOFPN_VISCERAL());
                datosFichaCab03.add(datos.getOFPN_TIPO_DE_BALANZA());
            // BLOQUE#4
                datosFichaCab04.add(datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA());
                datosFichaCab04.add(datos.getOFPN_SUPLEMENTACION_RECETADA());
                datosFichaCab04.add(datos.getOFPN_LABORATORIO());
                datosFichaCab04.add(datos.getOFPN_COMENTARIOS_GENERALES());
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
//        sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
//        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
        // CAMPOS DE TEXTO 
//        request.setAttribute("CFAP_TXT_PESO", ATEN_PESO);
//        request.setAttribute("CFAP_TXT_TALLA", ATEN_TALLA);
//        request.setAttribute("CFAP_TXT_IMC", ATEN_IMC);
//        request.setAttribute("CFAP_TXT_PCEFALICO", ATEN_PCEFALICO);
//        request.setAttribute("CFAP_TXT_FC", ATEN_FC);
//        request.setAttribute("CFAP_TXT_TEMP", ATEN_TEMP);
//        request.setAttribute("CFAP_TXT_PA", ATEN_PA);
//        request.setAttribute("CFAP_TXT_FRESP", ATEN_FRESP);
//        request.setAttribute("CFAP_TXT_MOTIVO_CONSULTA", ATEN_MOTIVO_CONSULTA);
//        request.setAttribute("CFAP_TXT_EXPLORACION_FISICA", ATEN_EXPLORACION_FISICA);
//        request.setAttribute("CFAP_TXT_DIAGNOSTICO", ATEN_DIAGNOSTICO);
//        request.setAttribute("CFAP_TXT_TRATAMIENTO", ATEN_TRATAMIENTO);
//        request.setAttribute("CFAP_TXT_RECETA", ATEN_RECETA);
//        request.setAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS", ATEN_ESTUDIOS_SOLICITADOS);
        // BLOQUES DEL JSP 
        request.setAttribute("CFAP_PAC_DATOS", datosPac);
        request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
        request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
        request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
        request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
        // [OBS] CARGO LA LISTA DE ARCHIVOS ADJUNTOS A LA FICHA DESDE UN METODO DISTINTO PARA NO RECORRER EL WHILE CON GRANDES CANTIDADES DE DATOS
        List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
        listaArchivos = metodosNutri.getDatosArchivosAdjuntos(idAtencionPac);
        int listaItem = 1;
        if (listaArchivos.size() > 0) { // [OBS] si la lista es mayor a cero entonces tiene filas dentro y no esta vacia, en ese caso el item de la lista seria el size completo mas uno para la siguiente linea.-
            listaItem = listaArchivos.size()+1;
        }
        sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
        
        return acceso;
    }
    
    
    
    public void recuperarDatosPag(HttpServletRequest request, ModelFichaAtencionPacNutri metodosNutri, HttpSession sesionDatosUsuario) {
        // RECUPERO LOS VALORES DE LOS CAMPOS DE TEXTO Y LE AGREGO A UNA LISTA PARA AL VOLVER ATRAS ME DEVUELVA LOS VALORES QUE TENIA 
        // BLOQUE #1 / 
        String ATEN_B1TDF = (String) request.getParameter("B1TDF"); // FECHA FICHA 
        if (ATEN_B1TDF==null || ATEN_B1TDF.isEmpty()) {
            System.out.println("____CTRL__GUARDAR______NULL_FECHA_FICHA____");
            ATEN_B1TDF = modeloIniSes.traerFechaHoy();
        }
        String ATEN_B1TDFH = (String) request.getParameter("B1TDFH"); // HORA FICHA 
        if (ATEN_B1TDFH==null || ATEN_B1TDFH.isEmpty()) {
            System.out.println("____CTRL__GUARDAR______NULL_HORA_FICHA____");
            ATEN_B1TDFH = modeloIniSes.getHoraHoy();
        }
        String ATEN_B1TMDLC = (String) request.getParameter("B1TMDLC");
        String ATEN_B1TAP = (String) request.getParameter("B1TAP");
        String ATEN_B1TACG = (String) request.getParameter("B1TACG");
        String ATEN_B1TANT = (String) request.getParameter("B1TANT");
        String ATEN_B1TCA = (String) request.getParameter("B1TCA"); // CONSUMO ALCOHOL 
        String ATEN_B1TCC = (String) request.getParameter("B1TCC"); // CONSUMO CIGARRILLO 
        String ATEN_B1TA = (String) request.getParameter("B1TA");
        String ATEN_B1TE = (String) request.getParameter("B1TE");
        String ATEN_B1TC = (String) request.getParameter("B1TC");
        String ATEN_B1TM = (String) request.getParameter("B1TM");
        String ATEN_B1TOD = (String) request.getParameter("B1TOD");
        // BLOQUE #2 / 
        String ATEN_B2CRAF = (String) request.getParameter("B2CRAF");
        String ATEN_B2TTE = (String) request.getParameter("B2TTE");
        String ATEN_B2TEF = (String) request.getParameter("B2TEF");
        String ATEN_B2CDCR = (String) request.getParameter("B2CDCR");
        String ATEN_B2CE = (String) request.getParameter("B2CE");
        String ATEN_B2CCOH = (String) request.getParameter("B2CCOH");
        String ATEN_B2CGS = (String) request.getParameter("B2CGS");
        String ATEN_B2CCF = (String) request.getParameter("B2CCF");
        String ATEN_B2CZO = (String) request.getParameter("B2CZO");
        String ATEN_B2CBD = (String) request.getParameter("B2CBD");
        String ATEN_B2CHA = (String) request.getParameter("B2CHA");
        String ATEN_B2CCDC = (String) request.getParameter("B2CCDC");
        String ATEN_B2CDP = (String) request.getParameter("B2CDP");
        String ATEN_B2CI = (String) request.getParameter("B2CI");
        String ATEN_B2CUQ = (String) request.getParameter("B2CUQ");
        String ATEN_B2CDDC = (String) request.getParameter("B2CDDC");
        String ATEN_B2CMC = (String) request.getParameter("B2CMC");
        String ATEN_B2CPS = (String) request.getParameter("B2CPS");
        String ATEN_B2TM = (String) request.getParameter("B2TM");
        String ATEN_B2CTDEDBU = (String) request.getParameter("B2CTDEDBU");
        // BLOQUE #3 / 
        String ATEN_B3TE = (String) request.getParameter("B3TE");
        String ATEN_B3TPG = (String) request.getParameter("B3TPG");
        String ATEN_B3TEM = (String) request.getParameter("B3TEM");
        String ATEN_B3TP = (String) request.getParameter("B3TP");
        String ATEN_B3TPM = (String) request.getParameter("B3TPM");
        String ATEN_B3TRM = (String) request.getParameter("B3TRM");
        String ATEN_B3TI = (String) request.getParameter("B3TI");
        String ATEN_B3TV = (String) request.getParameter("B3TV");
        String ATEN_B3TB = (String) request.getParameter("B3TB");
        // BLOQUE #4 / 
        String ATEN_B4TRT = (String) request.getParameter("B4TRT");
        String ATEN_B4TSR = (String) request.getParameter("B4TSR");
        String ATEN_B4TL = (String) request.getParameter("B4TL");
        String ATEN_B4TCG = (String) request.getParameter("B4TCG");
        String ATEN_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 

        // IMPRESION DE DATOS 
        System.out.println("-   ------------__DATOS__--------------------------------------------------------");
        System.out.println(".");
        System.out.println(".------     BLOQUE #1    ------");
        System.out.println(".");
        System.out.println("-       _FECHA-DE-LA-FICHA:-----------------:"+ATEN_B1TDF);
        System.out.println("-       _HORA-DE-LA-FICHA:------------------:"+ATEN_B1TDFH);
        System.out.println("-       _MOTIVO_CONSULTA:-------------------:"+ATEN_B1TMDLC);
        System.out.println("-       _ALIMENTOS_DE_PREFERENCIA:----------:"+ATEN_B1TAP);
        System.out.println("-       _ALIMENTOS-QUE-COMES-GENERALMENTE:--:"+ATEN_B1TACG);
        System.out.println("-       _ALIMENTOS-QUE-NO-TOLERA:-----------:"+ATEN_B1TANT);
        System.out.println("-       _CONSUMO_ALCOHOL:-------------------:"+ATEN_B1TCA);
        System.out.println("-       _CONSUMO_CIGARRILLO:----------------:"+ATEN_B1TCC);
        System.out.println("-       _ALERGIAS:--------------------------:"+ATEN_B1TA);
        System.out.println("-       _ENFERMEDAD:------------------------:"+ATEN_B1TE);
        System.out.println("-       _CIRUGIAS:--------------------------:"+ATEN_B1TC);
        System.out.println("-       _MEDICAMENTOS:----------------------:"+ATEN_B1TM);
        System.out.println("-       _OTROS-DATOS-A-TENER-EN-CUENTA:-----:"+ATEN_B1TOD);
        System.out.println(".");
        System.out.println(".------     BLOQUE #2    ------");
        System.out.println(".");
        System.out.println("-       _REALIZA-ACTIVIDAD-FISICA:---------:"+ATEN_B2CRAF);
        System.out.println("-       _TIPO-EJERCICIO:-------------------:"+ATEN_B2TTE);
        System.out.println("-       _EJERCICIO-FRECUENCIA:-------------:"+ATEN_B2TEF);
        System.out.println("-       _DIRIGE-BIEN-LAS-CARNES-ROJAS:-----:"+ATEN_B2CDCR);
        System.out.println("-       _ESTRENHIMIENTO:-------------------:"+ATEN_B2CE);
        System.out.println("-       _CALAMBRES-Y/O-HORMIGUEOS:---------:"+ATEN_B2CCOH);
        System.out.println("-       _GRASAS-SATURADAS:-----------------:"+ATEN_B2CGS);
        System.out.println("-       _CANSANCIO-Y-FATIGA:---------------:"+ATEN_B2CCF);
        System.out.println("-       _ZUMBIDOS-EN-EL-OIDO:--------------:"+ATEN_B2CZO);
        System.out.println("-       _TIENE-BUENA-DIGESTION-A-LA-NOCHE:-:"+ATEN_B2CBD);
        System.out.println("-       _HINCHAZON-ABDOMINAL:--------------:"+ATEN_B2CHA);
        System.out.println("-       _CAIDA-DEL-CABELLO:----------------:"+ATEN_B2CCDC);
        System.out.println("-       _DUERME-PROFUNDAMENTE:-------------:"+ATEN_B2CDP);
        System.out.println("-       _INSOMNIO:-------------------------:"+ATEN_B2CI);
        System.out.println("-       _UNHAS-QUEBRADIZAS:----------------:"+ATEN_B2CUQ);
        System.out.println("-       _DOLORES-DE-CABEZA-CON-FRECUENCIA:-:"+ATEN_B2CDDC);
        System.out.println("-       _MUCOSIDAD-Y-CATARRO:--------------:"+ATEN_B2CMC);
        System.out.println("-       _PIEL-SECA:------------------------:"+ATEN_B2CPS);
        System.out.println("-       _METABOLISMO:----------------------:"+ATEN_B2TM);
        System.out.println("-       _TIPO-ESCALA-BRISTOL-USUAL:--------:"+ATEN_B2CTDEDBU);
        System.out.println(".");
        System.out.println(".------     BLOQUE #3    ------");
        System.out.println(".");
        System.out.println("-       _ESTATURA:-------------------------:"+ATEN_B3TE);
        System.out.println("-       _PORCENTAJE-GRASA:-----------------:"+ATEN_B3TPG);
        System.out.println("-       _EDAD-M:---------------------------:"+ATEN_B3TEM);
        System.out.println("-       _PESO:-----------------------------:"+ATEN_B3TP);
        System.out.println("-       _PORCENTAJE-MUSCULO:---------------:"+ATEN_B3TPM);
        System.out.println("-       _RM:-------------------------------:"+ATEN_B3TRM);
        System.out.println("-       _IMC:------------------------------:"+ATEN_B3TI);
        System.out.println("-       _VISCERAL:-------------------------:"+ATEN_B3TV);
        System.out.println("-       _BALANZA:--------------------------:"+ATEN_B3TB);
        System.out.println(".");
        System.out.println(".------     BLOQUE #4    ------");
        System.out.println(".");
        System.out.println("-       _RESULTADOS-TEST:------------------:"+ATEN_B4TRT);
        System.out.println("-       _SUPLEMENTACION-RECETADA:----------:"+ATEN_B4TSR);
        System.out.println("-       _LABORATORIO:----------------------:"+ATEN_B4TL);
        System.out.println("-       _COMENTARIOS-GENERALES:------------:"+ATEN_B4TCG);
        System.out.println(".");
        System.out.println("-       ATEN_ESTADO:-----------------------:"+ATEN_ESTADO);
        System.out.println("-   -----------------------------------------------------------------------------");
        String IDPACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
        System.out.println("_   _ID_PACIENTE :   :"+IDPACIENTE);
        List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
        List<String> datosFichaCab01 = new ArrayList<>();
        List<String> datosFichaCab02 = new ArrayList<>();
        List<String> datosFichaCab03 = new ArrayList<>();
        List<String> datosFichaCab04 = new ArrayList<>();
        // BLOQUE 1 
        datosFichaCab01.add(ATEN_B1TDF);
        datosFichaCab01.add(ATEN_B1TDFH);
        datosFichaCab01.add(ATEN_B1TMDLC);
        datosFichaCab01.add(ATEN_B1TAP);
        datosFichaCab01.add(ATEN_B1TACG);
        datosFichaCab01.add(ATEN_B1TANT);
        datosFichaCab01.add(ATEN_B1TCA);
        datosFichaCab01.add(ATEN_B1TCC);
        datosFichaCab01.add(ATEN_B1TA);
        datosFichaCab01.add(ATEN_B1TE);
        datosFichaCab01.add(ATEN_B1TC);
        datosFichaCab01.add(ATEN_B1TM);
        datosFichaCab01.add(ATEN_B1TOD);
        datosFichaCab01 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab01, "");
        // BLOQUE 2 
        datosFichaCab02.add(ATEN_B2CRAF);
        datosFichaCab02.add(ATEN_B2TTE);
        datosFichaCab02.add(ATEN_B2TEF);
        datosFichaCab02.add(ATEN_B2CDCR);
        datosFichaCab02.add(ATEN_B2CE);
        datosFichaCab02.add(ATEN_B2CCOH);
        datosFichaCab02.add(ATEN_B2CGS);
        datosFichaCab02.add(ATEN_B2CCF);
        datosFichaCab02.add(ATEN_B2CZO);
        datosFichaCab02.add(ATEN_B2CBD);
        datosFichaCab02.add(ATEN_B2CHA);
        datosFichaCab02.add(ATEN_B2CCDC);
        datosFichaCab02.add(ATEN_B2CDP);
        datosFichaCab02.add(ATEN_B2CI);
        datosFichaCab02.add(ATEN_B2CUQ);
        datosFichaCab02.add(ATEN_B2CDDC);
        datosFichaCab02.add(ATEN_B2CMC);
        datosFichaCab02.add(ATEN_B2CPS);
        datosFichaCab02.add(ATEN_B2TM);
        datosFichaCab02.add(ATEN_B2CTDEDBU);
        datosFichaCab02 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab02, "");
        // BLOQUE 3 
        datosFichaCab03.add(ATEN_B3TE);
        datosFichaCab03.add(ATEN_B3TPG);
        datosFichaCab03.add(ATEN_B3TEM);
        datosFichaCab03.add(ATEN_B3TP);
        datosFichaCab03.add(ATEN_B3TPM);
        datosFichaCab03.add(ATEN_B3TRM);
        datosFichaCab03.add(ATEN_B3TI);
        datosFichaCab03.add(ATEN_B3TV);
        datosFichaCab03.add(ATEN_B3TB);
        datosFichaCab03 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab03, "");

        // BLOQUE 4 
        datosFichaCab04.add(ATEN_B4TRT);
        datosFichaCab04.add(ATEN_B4TSR);
        datosFichaCab04.add(ATEN_B4TL);
        datosFichaCab04.add(ATEN_B4TCG);
        datosFichaCab04 = metodosNutri.ctrlAndResetListValueNull(datosFichaCab04, "");
        // LES DEVUELVO LAS VARS AL JSP.-
        sesionDatosUsuario.setAttribute("CFAP_PAC_DATOS", datosPac);
        sesionDatosUsuario.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
        sesionDatosUsuario.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
        sesionDatosUsuario.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
        sesionDatosUsuario.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
        // RECUPERO LA LISTA Y LA VUELVO A PASAR 
        List<BeanFichaAtePaciente> datosUltFichaPac = (List) sesionDatosUsuario.getAttribute("CFAP_LAST_FICHA_VALUES");
//                     List<BeanFichaAtePaciente> datosUltFichaPac = metodosNutri.getUltimaFicha(IDPACIENTE);
        sesionDatosUsuario.setAttribute("CFAP_LAST_FICHA_VALUES", datosUltFichaPac);
                    
    }
    
    
    public String editarFichaAtencion(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelFichaAtencionPacNutri metodosNutri, String acceso) {
        System.out.println("---------------------__EDITAR_ATENCION__--------------------------");
        String idAtencionPac = (String) request.getParameter("tIAP");
        System.out.println("_   __ID_ATENCION_PAC:   :"+idAtencionPac);
        String IDAGENDAMIENTO = (String) request.getParameter("tIA");
        System.out.println("_   __ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
        String ITEM_AGEN_DET = (String) request.getParameter("tAID");
        System.out.println("_   __ITEM_AGEN_DET:     :"+ITEM_AGEN_DET);
        String IDPACIENTE = (String) request.getParameter("tIP");
        System.out.println("_   __ID_PACIENTE:       :"+IDPACIENTE);

//                    // CARGAR LISTA SERVICIOS 
//                    List<BeanFichaAtePaciente> LISTA_SERV = new ArrayList<>();
//                    int ITEM_LIST_SERV = 0;

        // CARGAR_GRILLA_DETALLE 
        List<BeanFichaAtePaciente> FICHA_DATOS = new ArrayList<>();
        FICHA_DATOS = metodosNutri.traer_datos(idAtencionPac, sesionDatosUsuario);
        Iterator<BeanFichaAtePaciente> iListDet = FICHA_DATOS.iterator();
        BeanFichaAtePaciente datos = new BeanFichaAtePaciente();

        List<String> datosPac = metodosNutri.getDatosPaciente(IDPACIENTE);
        List<String> datosFichaCab01 = new ArrayList<>();
        List<String> datosFichaCab02 = new ArrayList<>();
        List<String> datosFichaCab03 = new ArrayList<>();
        List<String> datosFichaCab04 = new ArrayList<>();

        while(iListDet.hasNext()) {
            datos = iListDet.next();
            System.out.println("____WHILE____");
            String[] FECHA_HORA_FICHA = datos.getOFPN_FECHA_FICHA_ATE().split(" ");
            System.out.println("___________________________FECHA_FICHA:  :"+FECHA_HORA_FICHA[0]);
            System.out.println("___________________________HORA__FICHA:  :"+FECHA_HORA_FICHA[1]);
            // BLOQUE#1
                datosFichaCab01.add(FECHA_HORA_FICHA[0]); // FECHA 
                datosFichaCab01.add(FECHA_HORA_FICHA[1]); // HORA 
                datosFichaCab01.add(datos.getOFPN_MOTIVO_DE_LA_CONSULTA());
                datosFichaCab01.add(datos.getOFPN_ALIMENTOS_DE_PREFERENCIA());
                datosFichaCab01.add(datos.getOFPN_ALI_QUE_SUELE_COMER_GRL());
                datosFichaCab01.add(datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA());
                datosFichaCab01.add(datos.getOFPN_ALERGIAS_A_ALGO());
                datosFichaCab01.add(datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD());
                datosFichaCab01.add(datos.getOFPN_CIRUGIAS());
                datosFichaCab01.add(datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO());
                datosFichaCab01.add(datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA());
                datosFichaCab01.add(datos.getOFPN_CONSUMO_ALCOHOL());
                datosFichaCab01.add(datos.getOFPN_CONSUMO_CIGARRILLO());
            // BLOQUE#2
                datosFichaCab02.add(datos.getOFPN_REALIZA_ACTIVIDAD_FISICA());
                datosFichaCab02.add(datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA());
                datosFichaCab02.add(datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM());
                datosFichaCab02.add(datos.getOFPN_DBLCR());
                datosFichaCab02.add(datos.getOFPN_ESTRENHIMIENTO());
                datosFichaCab02.add(datos.getOFPN_CALAMBRES_Y_HORMIGUEOS());
                datosFichaCab02.add(datos.getOFPN_LGSLCM());
                datosFichaCab02.add(datos.getOFPN_CANSANCIO_FATIGA());
                datosFichaCab02.add(datos.getOFPN_ZUMBIDOS_EN_EL_OIDO());
                datosFichaCab02.add(datos.getOFPN_TBDALN());
                datosFichaCab02.add(datos.getOFPN_HICHAZON_ABDOMINAL());
                datosFichaCab02.add(datos.getOFPN_CAIDA_DE_CABELLO());
                datosFichaCab02.add(datos.getOFPN_DPALN());
                datosFichaCab02.add(datos.getOFPN_INSOMNIO());
                datosFichaCab02.add(datos.getOFPN_UNHAS_QUEBRADIZAS());
                datosFichaCab02.add(datos.getOFPN_DDCCF());
                datosFichaCab02.add(datos.getOFPN_MUCOSIDAD_Y_CATARRO());
                datosFichaCab02.add(datos.getOFPN_PIEL_SECA());
                datosFichaCab02.add(datos.getOFPN_TIPO_DE_METABOLISMO());
                datosFichaCab02.add(datos.getOFPN_TDEDBU());
            // BLOQUE#3
                datosFichaCab03.add(datos.getOFPN_ESTATURA());
                datosFichaCab03.add(datos.getOFPN_PORC_GRASA());
                datosFichaCab03.add(datos.getOFPN_EDAD_METABOLICA());
                datosFichaCab03.add(datos.getOFPN_PESO());
                datosFichaCab03.add(datos.getOFPN_PORC_MUSCULO());
                datosFichaCab03.add(datos.getOFPN_RM());
                datosFichaCab03.add(datos.getOFPN_IMC());
                datosFichaCab03.add(datos.getOFPN_VISCERAL());
                datosFichaCab03.add(datos.getOFPN_TIPO_DE_BALANZA());
            // BLOQUE#4
                datosFichaCab04.add(datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA());
                datosFichaCab04.add(datos.getOFPN_SUPLEMENTACION_RECETADA());
                datosFichaCab04.add(datos.getOFPN_LABORATORIO());
                datosFichaCab04.add(datos.getOFPN_COMENTARIOS_GENERALES());
        }

//        var_redireccionar = 1;
        acceso = "add_atencion.htm";
        sesionDatosUsuario.setAttribute("CFAP_BAND_REPEAT_ONE", "1"); // VARIABLE QUE UTILIZO COMO BANDERA PARA EVITAR QUE AL RECARGAR LA PAGINA EN EL EXPEDIENTE DE PACIENTE SE REPITA LA ULTIMA ACCION (ELIMINAR FICHA O GUARDAR FICHA) 
        sesionDatosUsuario.setAttribute("ID_ATENCION_PAC", idAtencionPac); // LE PASO EL VALOR QUE RECUPERO PARA QUE NO PIENSE QUE ESTOY GUARDANDO UNA NUEVA FICHA SINO SEPA A CUAL FICHA ACTUALIZAR 
        sesionDatosUsuario.setAttribute("CFAP_IDAGENDAMIENTO", IDAGENDAMIENTO);
        sesionDatosUsuario.setAttribute("CFAP_ITEM_AGEND", ITEM_AGEN_DET);
        sesionDatosUsuario.setAttribute("CFAP_IDPACIENTE", IDPACIENTE);
//                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERV);
////                    sesionDatosUsuario.setAttribute("CFAP_LISTA_SERVICIOS", LISTA_SERVICIOS);
//                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", String.valueOf(ITEM_LIST_SERV));
////                    sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", NRO_ITEM);
        // BLOQUES DEL JSP 
        request.setAttribute("CFAP_PAC_DATOS", datosPac);
        request.setAttribute("CFAP_FICHA_CAB_01", datosFichaCab01);
        request.setAttribute("CFAP_FICHA_CAB_02", datosFichaCab02);
        request.setAttribute("CFAP_FICHA_CAB_03", datosFichaCab03);
        request.setAttribute("CFAP_FICHA_CAB_04", datosFichaCab04);
        // LE LIMPIO LAS MISMAS VARIABLES POR SI QUEDARON DE UNA ANTERIOR FICHA EN SESION (YA QUE LO UTILIZO PARA CARGAR LAS LISTAS AL QUERER VER EL HISTORIAL DE FICHAS) 
        sesionDatosUsuario.removeAttribute("CFAP_PAC_DATOS");
        sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_01");
        sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_02");
        sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_03");
        sesionDatosUsuario.removeAttribute("CFAP_FICHA_CAB_04");
        // [OBS] CARGO LA LISTA DE ARCHIVOS ADJUNTOS A LA FICHA DESDE UN METODO DISTINTO PARA NO RECORRER EL WHILE CON GRANDES CANTIDADES DE DATOS
        List<BeanFichaAtePacienteDet> listaArchivos = new ArrayList<>();
        listaArchivos = metodosNutri.getDatosArchivosAdjuntos(idAtencionPac);
        int listaItem = 1;
        if (listaArchivos.size() > 0) { // [OBS] si la lista es mayor a cero entonces tiene filas dentro y no esta vacia, en ese caso el item de la lista seria el size completo mas uno para la siguiente linea.-
            listaItem = listaArchivos.size()+1;
        }
        sesionDatosUsuario.setAttribute("CFAP_LISTA_FILES", listaArchivos);
        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", String.valueOf(listaItem));
        return acceso;
    }
    
    
    
    // METODO PARA VALIDAR QUE EL NOMBRE DE LA IMAGEN NO SE ENCUENTRE DENTRO DE LA CARPETA PARA EVITAR ERROR Y QUE NO SEA COPIADA A LA CARPETA Y ASI NO PUEDA MOSTRARLA 
    public boolean ctrlNameFile(HttpSession PARAM_SESION, String path_destino, String NAME_FILE) {
        boolean valor = false;
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".---------------------___CTRL_NAME_FILE___--------------------------.");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   --name_file:  :"+NAME_FILE);
        System.out.println(".   --path-destino:  :"+path_destino);
        System.out.println(".");
        System.out.println(".");
        File PATH_DESTINO = new File(path_destino.replace("*", File.separator));
        System.out.println("______PATH_DESTINO:   :"+PATH_DESTINO);
        
        // SI EL IDPERSONA ESTA CARGADA ENTONCES DEBERIA DE CONTROLAR EL CAMPO OCULTO QUE GUARDA LA IMAGEN QUE TENIA ANTES (AL GUARDAR) 
//        String PHOTO_AUX = (String) PARAM_SESION.getAttribute("CP_PAC_AUX_FOTO");
//        if (PHOTO_AUX == null || PHOTO_AUX.isEmpty() || PHOTO_AUX.equals("")) { // si no tiene nada entonces es porque no habia foto cargada al crearse el paciente.-
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println("_____NO_HAY_FOTO_AUXILIAR___________");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            int band = 0; // bandera para usar cuando el idpersona esta cargado 
//            for (final File ficheroEntrada : PATH_DESTINO.listFiles()) {
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println("------------__FOR__------------");
//                System.out.println(".   for:1:__"+ficheroEntrada.getName());
//                System.out.println(".   for:2:__"+ficheroEntrada.getAbsolutePath());
//                System.out.println("_");
//                if (ficheroEntrada.isDirectory()) {
//                    System.out.println("___IS_DIRECTORY_____");
//                    listarFicherosPorCarpeta(ficheroEntrada);
//                } else {
//                    System.out.println("_____FILE_NAME______");
//                    System.out.println(ficheroEntrada.getName());
//                    System.out.println("____________________");
//                    if (NAME_FILE.equals(ficheroEntrada.getName())) {
//                        System.out.println("___IF___        [NOMBRE-DE-LA-IMAGEN-ENCONTRADA] / ["+ficheroEntrada.getName()+"]    ");
//                        valor = true;
//                        break;
//
//                    } else {
//                        System.out.println("___ELSE___");
//                    }
//                } // end-else-ctrl-directory-
//                System.out.println("_   _____________________");
//                System.out.println("_");
//                System.out.println("_");
//                System.out.println("_");
//            } // end-for.-
//            
//        } else {
            System.out.println(".");
            System.out.println(".");
            System.out.println("_____TIENE_VALOR____________");
//            System.out.println("___AUX_PHOTO_VAR:  :"+PHOTO_AUX);
            System.out.println(".");
            System.out.println(".");
            // [OBS] CONTROLO SI ES QUE SON LOS MISMOS, LA IMAGEN QUE OBTENGO DE LA PAGINA Y LA URL DE LA IMAGEN AUXILIAR 
            if (NAME_FILE == null || NAME_FILE.isEmpty() || NAME_FILE.equals("")) { // SI SE ENCUENTRA VACIA ES PORQUE EL USUARIO NO LE CARGO O SELECCIONO NINGUNA IMAGEN NUEVA PARA ACTUALIZAR/GUARDAR EL PACIENTE.-
                System.out.println(".");
                System.out.println(".");
                System.out.println("___IF_____________-NO_FUE_SELECCIONADO_NINGUNA_FILE-_________");
                System.out.println(".");
                System.out.println(".");
//            } else if (PHOTO_AUX.equals(NAME_FILE)) { // SI SON IGUALES ES PORQUE NO MODIFICO LA IMAGEN 
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println("___ELSE-IF_____________-SON_IGUALES-____");
//                System.out.println(".");
//                System.out.println(".");
            } else {
                System.out.println(".");
                System.out.println(".");
                System.out.println("___ELSE________________");
//                System.out.println("___ELSE____________-NO_SON_IGUALES-____");
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
                            valor = true;
                            break;
                        } else {
                            System.out.println("___ELSE___");
                        }
                    } // end-else-ctrl-directory-
                    System.out.println("_   _____________________");
                    System.out.println("_");
                    System.out.println("_");
                    System.out.println("_");
                } // end-for.-
            } // end-else-ctrl-var-photo-aux.-
//        }
        System.out.println(".");
        System.out.println(".");
        System.out.println(".-------------------------------------------------------------------.");
        return valor;
    } // end-method.-
    
    
    
    
} // END CLASS  