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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelPerfil;
import com.agend.modelo.ModelPersona;
import com.agend.modelo.ModelUsuario;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="UsuarioController", urlPatterns="/CUSrv") // CONTROLLER USUARIO Servlet
public class ControllerUsuario extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    ModelPerfil modeloPerfil = new ModelPerfil();
    
    
    @RequestMapping("/usuario")
    public ModelAndView usuario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__USUARIO--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CU__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CU__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuUsuario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagUsuario", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagUsuario", request);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        return mav;
    }
    
    
    
    @RequestMapping("/add_usuario")
    public ModelAndView add_usuario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_USUARIO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CU__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CU__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuUsuario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagUsuarioReg", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagUsuarioReg", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/edit_usuario")
    public ModelAndView edit_usuario(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__3__--------CONTROLLER__AÑADIR_USUARIO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CU__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CU__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuUsuario(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagUsuarioReg", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagUsuarioReg", request);
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
        
        ModelUsuario metodosUsuario = new ModelUsuario();
        ModelPersona metodosPersona = new ModelPersona();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int var_redireccionar = 0;
        String acceso = "usuario.htm";
        String accion = request.getParameter("accion");
        String idPersona, idUsuario;
        
        try {
            System.out.println("__ACCION:       "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("_doPost__ID_PERSONA:     "+idPersona);
            
            if (accion.equalsIgnoreCase("Add Usuario")) { // BOTON PARA AÑADIR UN NUEVO USUARIO 
                System.out.println("---------------------__AÑADIR_USUARIO__--------------------------");
                
                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                String FECHA_HOY = modeloIniSes.traerFechaHoy();
                LISTA_DATOS.add(new BeanPersona("", "", "", "", "CI", modeloPerfil.getIdPerfilPaciente(), modeloPerfil.getDescPerfilPaciente(), "", "", "", "", "0", "", "", "", FECHA_HOY, "", "", "", "", "", "", "0", "0", "", /*usuario-vars->*/"", "", "", "", "A", "4", "", "NO", "0", "", "NO"));
                
                var_redireccionar = 1;
                acceso = "add_usuario.htm";
                sesionDatosUsuario.setAttribute("ID_USUARIO", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN USUARIO Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN USUARIO, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN USUARIO 
                sesionDatosUsuario.setAttribute("ID_USU_PERSONA", "");
                request.setAttribute("CU_PAC_LISTA_DATOS", LISTA_DATOS);
                
            } else if (accion.equalsIgnoreCase("Editar")) { // BOTON PARA EDITAR O MODIFICAR LOS DATOS DEL CLIENTE 
                System.out.println("---------------------__EDITAR__--------------------------");
                idUsuario = (String) request.getParameter("tIU");
                System.out.println("_01__doPost__ID_USUARIO:     "+idUsuario);
                if (idUsuario == null || idUsuario.isEmpty()) {
                    idUsuario = String.valueOf(sesionDatosUsuario.getAttribute("ID_USUARIO"));
                    System.out.println("_02__doPost__ID_USUARIO:     "+idUsuario);
                }
                
                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                List<BeanPersona> LISTA_DATOS_AUX = new ArrayList<>();
                if (idUsuario != null) {
                    idPersona = metodosUsuario.getIdUsuario(idUsuario);
                    LISTA_DATOS = metodosUsuario.traer_datos(idUsuario, sesionDatosUsuario);
                    LISTA_DATOS_AUX = metodosUsuario.traer_datos(idUsuario, sesionDatosUsuario);
                }
                
                var_redireccionar = 1;
                acceso = "edit_usuario.htm";
                sesionDatosUsuario.setAttribute("ID_USUARIO", idUsuario);
                sesionDatosUsuario.setAttribute("ID_USU_PERSONA", idPersona);
                request.setAttribute("CU_PAC_LISTA_DATOS", LISTA_DATOS);
                request.setAttribute("CU_PAC_LISTA_DATOS_AUX", LISTA_DATOS_AUX);
                
            } else if(accion.equalsIgnoreCase("GRABAR") || accion.equalsIgnoreCase("Guardar")) {
                System.out.println("---------------------__GUARDAR__--------------------------");
                int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                System.out.println("_   _ID_PERSONA:   "+idPersona);
                idUsuario = (String) sesionDatosUsuario.getAttribute("ID_USUARIO");
                System.out.println("_   _ID_USUARIO:   "+idUsuario);
                
                // RECUPERAR LOS DATOS 
                String USU_IDPERSONA = (String) request.getParameter("tIUP");
                if (USU_IDPERSONA == null || USU_IDPERSONA.isEmpty()) {
                    USU_IDPERSONA = (String) sesionDatosUsuario.getAttribute("ID_USU_PERSONA");
                }
                String USU_NOMBRE = (String) request.getParameter("tN");
                if (USU_NOMBRE == null || USU_NOMBRE.isEmpty()) { varValiVacio++; USU_NOMBRE=""; }
                String USU_APELLIDO = (String) request.getParameter("tA");
                if (USU_APELLIDO == null || USU_APELLIDO.isEmpty()) { varValiVacio++; USU_APELLIDO=""; }
                String USU_CINRO = (String) request.getParameter("tNC");
                if (USU_CINRO == null || USU_CINRO.isEmpty()) { varValiVacio++; USU_CINRO=""; }
                String USU_RAZON_SOCIAL = (String) request.getParameter("tRS");
                if (USU_RAZON_SOCIAL == null || USU_RAZON_SOCIAL.isEmpty()) { /*varValiVacio++;*/ USU_RAZON_SOCIAL=""; }
                String USU_RUC = (String) request.getParameter("tR");
                if (USU_RUC == null || USU_RUC.isEmpty()) { /*varValiVacio++;*/ USU_RUC=""; }
                String USU_TELEFONO = (String) request.getParameter("tT");
                if (USU_TELEFONO == null || USU_TELEFONO.isEmpty()) { /*varValiVacio++;*/ USU_TELEFONO=""; }
                String USU_CELULAR = (String) request.getParameter("tCP");
                if (USU_CELULAR == null || USU_CELULAR.isEmpty()) { /*varValiVacio++;*/ USU_CELULAR=""; }
                String USU_DIRECCION = (String) request.getParameter("tD");
                if (USU_DIRECCION == null || USU_DIRECCION.isEmpty()) { /*varValiVacio++;*/ USU_DIRECCION=""; }
                String USU_EMAIL = (String) request.getParameter("tE");
                if (USU_EMAIL == null || USU_EMAIL.isEmpty()) { varValiVacio++; USU_EMAIL=""; }
                String USU_PROFESION = (String) request.getParameter("tPP");
                if (USU_PROFESION == null || USU_PROFESION.isEmpty()) { /*varValiVacio++;*/ USU_PROFESION=""; }
                String USU_FECHA_NAC = (String) request.getParameter("tFN");
                if (USU_FECHA_NAC == null || USU_FECHA_NAC.isEmpty()) { /*varValiVacio++;*/ USU_FECHA_NAC=""; }
                String USU_SEXO = (String) request.getParameter("cSe");
                if (USU_SEXO == null || USU_SEXO.isEmpty()) { /*varValiVacioSexo++;*/ USU_SEXO="N"; }
                String USU_IDCIUDAD = (String) request.getParameter("cCI");
                if (USU_IDCIUDAD == null || USU_IDCIUDAD.isEmpty()) { /*varValiVacio++;*/ USU_IDCIUDAD="0"; }
                String USU_LOGIN;
                try {
                    USU_LOGIN = (String) request.getParameter("tL");
                } catch(NullPointerException e) {
                    USU_LOGIN = "";
                }
                if (USU_LOGIN.isEmpty() || USU_LOGIN.equals("")) { varValiVacio = varValiVacio + 1; USU_LOGIN=""; }
                String USU_CLAVE;
                try {
                    USU_CLAVE = (String) request.getParameter("tC");
                } catch(NullPointerException e) {
                    USU_CLAVE = "";
                }
                if (USU_CLAVE.isEmpty() || USU_CLAVE.equals("")) { varValiVacio = varValiVacio + 1; USU_CLAVE=""; }
                String USU_PERFIL = (String) request.getParameter("cP");
                if (USU_PERFIL == null || USU_PERFIL.isEmpty()) {
                    USU_PERFIL = (String) request.getParameter("tUP"); // txt usuario perfil 
                    if (USU_PERFIL == null || USU_PERFIL.isEmpty()) {
                        varValiVacio++; 
                        USU_PERFIL="";
                    }
                }
                String USU_ESTADO = (String) request.getParameter("cE");
                if (USU_ESTADO == null || USU_ESTADO.isEmpty()) { varValiVacio++; USU_ESTADO=""; }
                String USU_IDCLINICA = (String) request.getParameter("cCli");
                if (USU_IDCLINICA == null || USU_IDCLINICA.isEmpty()) {
                    USU_IDCLINICA = (String) request.getParameter("tUIC"); // txt usuario id clinica 
                    if (USU_IDCLINICA == null || USU_IDCLINICA.isEmpty()) {
                        varValiVacio++;
                        USU_IDCLINICA="";
                    }
                }
                
                
                // IMPRESION DE DATOS 
                System.out.println("-   ------------__DATOS__--------------");
                System.out.println("-       USU_IDPERSONA:  "+USU_IDPERSONA);
                System.out.println("-       USU_NOMBRE:     "+USU_NOMBRE);
                System.out.println("-       USU_APELLIDO:   "+USU_APELLIDO);
                System.out.println("-       USU_NRO_CI:     "+USU_CINRO);
                System.out.println("-       USU_RAZON_SOCIAL:  "+USU_RAZON_SOCIAL);
                System.out.println("-       USU_RUC:        "+USU_RUC);
                System.out.println("-       USU_TELEFONO:   "+USU_TELEFONO);
                System.out.println("-       USU_DIRECCION:  "+USU_DIRECCION);
                System.out.println("-       USU_EMAIL:      "+USU_EMAIL);
                System.out.println("-       USU_CELULAR:    "+USU_CELULAR);
                System.out.println("-       USU_PROFESION:  "+USU_PROFESION);
                System.out.println("-       USU_FECHA_NAC:  "+USU_FECHA_NAC);
                System.out.println("-       USU_SEXO:       "+USU_SEXO);
                System.out.println("-       USU_CIUDAD:     "+USU_IDCIUDAD);
                System.out.println("-       USU_IDCLINICA:  "+USU_IDCLINICA);
                System.out.println("-       -----------------------");
                System.out.println("-       USU_LOGIN:      "+USU_LOGIN);
                System.out.println("-       USU_CLAVE:      "+USU_CLAVE);
                System.out.println("-       USU_PERFIL:     "+USU_PERFIL);
                System.out.println("-       USU_ESTADO:     "+USU_ESTADO);
                System.out.println("-   -----------------------------------");
                System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                
                // VALIDACIONES 
                String MENSAJE, TIPO_MENSAJE; // 1 : exito / 2 : error 
                if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                    MENSAJE = "No puede dejar campos vacios.";
                    TIPO_MENSAJE = "2";
                    
                } else if(metodosPersona.ctrlExistePersona(USU_CINRO, USU_IDCLINICA, USU_IDPERSONA) == true && idUsuario.isEmpty()) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT A UN USUARIO QUE YA EXISTE - PARA SABER QUE SE QUIERE HACER UN INSERT EL IDPERSONA DEL USUARIO DEBE ESTAR VACIO Y NO CARGADO 
                    MENSAJE = "Ya existe la persona con Nro. de CI "+USU_CINRO+".";
                    TIPO_MENSAJE = "2";
                    
                } else if(metodosPersona.ctrlExisteUsuCli(USU_LOGIN, USU_IDPERSONA) == true) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT O UPDATE CON EL NOMBRE DE UN USUARIO QUE YA EXISTE PARA EVITAR QUE DOS CLIENTES TENGAN EL MISMO USUARIO 
                    MENSAJE = "Ya existe otro usuario con el mismo nombre de usuario, seleccione otro.";
                    TIPO_MENSAJE = "2";
                    
                } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL CLIENTE 
                    String RP_IDPERSONA = USU_IDPERSONA;
                    String RP_NOMBRE = USU_NOMBRE;
                    String RP_APELLIDO = USU_APELLIDO;
                    String RP_CINRO = USU_CINRO;
                    String RP_TIPODOC = "CI";
                    String RP_IDCATEGORIA_PERSONA = modeloPerfil.recuperarIdPerfil(USU_PERFIL);
                    String RP_DESC_CATEG_PERSONA = USU_PERFIL;
                    String RP_RAZON_SOCIAL = USU_RAZON_SOCIAL;
                    String RP_RUC = USU_RUC;
                    String RP_IDBARRIO = "0";
                    String RP_DESC_BARRIO = "(NO/DEFINIDO)";
                    String RP_IDCIUDAD = USU_IDCIUDAD;
                    String RP_DESC_CIUDAD = "";
                    String RP_DIRECCION = USU_DIRECCION;
                    String RP_EMAIL = USU_EMAIL;
                    String RP_TELFPARTICULAR = USU_TELEFONO;
                    String RP_TELFMOVIL = USU_CELULAR;
                    String RP_IDCIUDADNAC = "0";
                    String RP_DESC_CIUDADNAC = "(NO/DEFINIDO)";
                    String RP_FECHANAC = USU_FECHA_NAC;
                    String RP_SEXO = USU_SEXO;
                    String RP_RELIGION = "";
                    String RP_ESTADOCIVIL = ""; // USU_ESTADO_CIVIL;
                    String RP_GRUPOSANGUINEO = "";
                    String RP_OBSERVACION = ""; // USU_OBSERVACION;
                    String RP_FECHAALTA = metodosIniSes.traerFechaHoraHoy();
                    String RP_FECULTIMOMOV = null;
                    String RP_FOTO = "";
                    String RP_USU_ALTA = idPersona;
                    String RP_USU_MOD = "0";
                    String RP_IDPAIS = "0";
                    String RP_DESC_PAIS = "(NO/DEFINIDO)";
                    String RP_TELEFPARTICULAR = "";
                    String RP_IDCLINICA = USU_IDCLINICA;
                    String RP_OCUPACION = USU_PROFESION;
                    String RP_ANTECEDENTES = "";
                    String RP_EXPEDIENTE_CLINICO = "";
                    String RP_SEGURO_MEDICO = "";
                    String RP_TIENE_HIJOS = "0"; // USU_TIENE_HIJOS;
                    String RP_CANT_HIJOS= "0"; // USU_CANT_HIJOS;
                    String RP_FOTO_PATH_ABS = "";
                    String RP_IDPERSONA_NEW = "";
                    // USUARIO 
                    String SU_IDUSUARIO = idUsuario;
                    String SU_IDPERSONA = RP_IDPERSONA;
                    String SU_USUARIO = USU_LOGIN;
                    String SU_CLAVE = USU_CLAVE;
                    String SU_ESTADO = USU_ESTADO;
                    String SU_IDPERFIL = modeloPerfil.recuperarIdPerfil(USU_PERFIL);
                    String SU_DESC_PERFIL = USU_PERFIL; // POR EL MOMENTO NO LE CARGO NADA PORQUE EN LA TABLA DE USUARIO YA NO TENGO LA COLUMNA QUE MUESTRA LA DESCRIPCION DEL PERFIL / PERO EN CASO DE QUE VUELVA A TENER, HAY SI UTILIZARE ESTE CMAPO PARA CARGAR ESA COLUMNA 
//                    String SU_IDPERFIL = "2";
//                    String SU_DESC_PERFIL = ""; // POR EL MOMENTO NO LE CARGO NADA PORQUE EN LA TABLA DE USUARIO YA NO TENGO LA COLUMNA QUE MUESTRA LA DESCRIPCION DEL PERFIL / PERO EN CASO DE QUE VUELVA A TENER, HAY SI UTILIZARE ESTE CMAPO PARA CARGAR ESA COLUMNA 
                    String SU_EMAIL = USU_EMAIL;
                    String SU_ESTADO_MIGRAR = "NO";
                    String SU_WEB = "0";
                    String SU_CONFIR_EMAIL = "NO";
                    
                    TIPO_MENSAJE = metodosUsuario.guardar_usuario_per(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS, RP_FOTO_PATH_ABS, RP_IDPERSONA_NEW, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL), metodosPersona);
//                    TIPO_MENSAJE = metodosPersona.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
////                    TIPO_MENSAJE = metodosPersona.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL, RP_IDCLINICA, RP_SEGURO_MEDICO));
                    if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                        MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                        if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equals("")) {
                            acceso = "add_usuario.htm";
                        } else { // SI EL ID USUARIO NO SE ENCONTRASE VACIO, ENTONCES EL ADMINISTRADOR ESTARIA EDITAN AL USUARIO 
                            acceso = "edit_usuario.htm";
                        }
                    } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                        MENSAJE = "Se ha realizado correctamente la operación.";
                        acceso = "usuario.htm";
                    }
                }
                // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                    // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                    if (idUsuario == null || idUsuario.isEmpty() || idUsuario.equals("")) {
                        acceso = "add_usuario.htm";
                    } else { // SI EL ID USUARIO NO SE ENCONTRASE VACIO, ENTONCES EL ADMINISTRADOR ESTARIA EDITAN AL USUARIO  
                        acceso = "edit_usuario.htm";
                    }
//                    // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
//                    request.setAttribute("CU_CLI_NOMBRE", USU_NOMBRE);
//                    request.setAttribute("CU_CLI_APELLIDO", USU_APELLIDO);
//                    request.setAttribute("CU_CLI_CINRO", USU_CINRO);
//                    request.setAttribute("CU_CLI_RAZON_SOCIAL", USU_RAZON_SOCIAL);
//                    request.setAttribute("CU_CLI_RUC", USU_RUC);
//                    request.setAttribute("CU_CLI_TELEFONO", USU_TELEFONO);
//                    request.setAttribute("CU_CLI_DIRECCION", USU_DIRECCION);
//                    request.setAttribute("CU_CLI_EMAIL", USU_EMAIL);
//                    request.setAttribute("CU_CLI_USUARIO", USU_LOGIN);
//                    request.setAttribute("CU_CLI_CLAVE", USU_CLAVE);
//                    request.setAttribute("CU_CLI_PERFIL", USU_PERFIL);
//                    request.setAttribute("CU_CLI_ESTADO", USU_ESTADO);
//                    request.setAttribute("CU_CLI_IDCLINICA", USU_IDCLINICA);
                    // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                    List<BeanPersona> LISTA_DATOS_PAC = new ArrayList<>();
                    BeanPersona bean_persona = new BeanPersona();
                        bean_persona.setRP_CINRO(USU_CINRO);
                        bean_persona.setRP_NOMBRE(USU_NOMBRE);
                        bean_persona.setRP_APELLIDO(USU_APELLIDO);
                        bean_persona.setRP_RAZON_SOCIAL(USU_RAZON_SOCIAL);
                        bean_persona.setRP_RUC(USU_RUC);
                        bean_persona.setRP_TELFPARTICULAR(USU_TELEFONO);
                        bean_persona.setRP_TELFMOVIL(USU_CELULAR);
                        bean_persona.setRP_DIRECCION(USU_DIRECCION);
                        bean_persona.setRP_EMAIL(USU_EMAIL);
                        bean_persona.setRP_IDCIUDAD(USU_IDCIUDAD);
                        bean_persona.setSU_IDUSUARIO(idUsuario);
                        bean_persona.setSU_USUARIO(USU_LOGIN);
                        bean_persona.setSU_CLAVE(USU_CLAVE);
                        bean_persona.setSU_ESTADO(USU_ESTADO);
                        bean_persona.setSU_DESC_PERFIL(USU_PERFIL);
                        bean_persona.setRP_OCUPACION(USU_PROFESION);
                        bean_persona.setRP_FECHANAC(USU_FECHA_NAC);
                        bean_persona.setRP_SEXO(USU_SEXO);
                        bean_persona.setRP_ESTADOCIVIL("");
                        bean_persona.setRP_TIENE_HIJOS("");
                        bean_persona.setRP_CANT_HIJOS("");
                        bean_persona.setRP_IDCLINICA(USU_IDCLINICA);
                        bean_persona.setRP_OBSERVACION("");
                    LISTA_DATOS_PAC.add(bean_persona);
                    request.setAttribute("CP_PAC_LISTA_DATOS", LISTA_DATOS_PAC);
                    // LE VUELVO A PASAR LA LISTA AUXILIAR POR SI ESTE EDITANDO Y AL DAR UN ERROR LA LISTA SE VAYA CON VALORES NULOS, EN CASO DE QUE IGUAL SALTE UN ERROR O CARGUE LOS DATOS CON NULL, SE TENDRIA QUE VOLVER A CARGAR LAS VARIABLES DE LA LISTA COMO ARRIBA PERO DESDE EL METODO DE TRAER DATOS COMO EN EL EVENTO DE "EDITAR" 
                    List<BeanPersona> LISTA_DATOS_AUX = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS_AUX");
                    request.setAttribute("CP_PAC_LISTA_DATOS_AUX", LISTA_DATOS_AUX);
                }
                var_redireccionar = 1;
                /*
                _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                    ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                    PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                */
                sesionDatosUsuario.setAttribute("ID_USUARIO", idUsuario);
                request.setAttribute("CU_MENSAJE", MENSAJE);
                request.setAttribute("CU_TIPO_MENSAJE", TIPO_MENSAJE);
                
            } else if(accion.equalsIgnoreCase("Filtrar")) {
                System.out.println("---------------------__FILTRAR__--------------------------");
                String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
                System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
                String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
                System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);
                
                // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
                List<BeanPersona> listaFiltro = new ArrayList<>();
                listaFiltro = metodosUsuario.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);
                
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "usuario.htm";
                var_redireccionar = 1;
                request.setAttribute("CU_CBX_MOSTRAR", filtro_cbxMostrar);
                request.setAttribute("CU_TXT_BUSCAR", filtro_txtBuscar);
                request.setAttribute("CU_LISTA_FILTRO", listaFiltro);
                sesionDatosUsuario.setAttribute("CU_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
            } else if(accion.equalsIgnoreCase("Limpiar")) {
                System.out.println("---------------------__LIMPIAR_PAGINA_PRINCIPAL__--------------------------");
                // PASAR LA LISTA Y LOS DATOS AL JSP 
                acceso = "usuario.htm";
                var_redireccionar = 1;
                
                // LE PASO LOS DATOS VACIOS PARA QUE CARGUE LOS FILTROS CON LOS DATOS POR DEFECTO CUANDO SE ENTRA A LA PAGINA Y LE PASO LA LISTA DEL FILTRO NULL PARA QUE EN LA PAGINA RECONOZCA QUE ESTA NULL Y LE CARGUE LA LISTA DE CARGA_GRILLA 
                request.setAttribute("CU_CBX_MOSTRAR", "");
                request.setAttribute("CU_TXT_BUSCAR", "");
                request.setAttribute("CU_LISTA_FILTRO", null);
                sesionDatosUsuario.setAttribute("PAG_USU_CANT_BTN_DERE_CLIC", "1");
                sesionDatosUsuario.setAttribute("PAG_USU_LISTA_ACTUAL", "1");
                sesionDatosUsuario.setAttribute("CU_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
            
            } else if (esNumero(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute("PAG_USU_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodosUsuario);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase(">>")) {
                System.out.println("---------------------__PAGINACION__SIGUIENTE_BTN___: "+accion+" :__--------------------------");
                String PAG_USU_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_USU_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_USU_CANT_NRO_CLIC == null) {
                    PAG_USU_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_USU_CANT_NRO_CLIC);
                String PAG_USU_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_USU_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_USU_LISTA_ACTUAL);
                
                PAG_USU_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_USU_CANT_NRO_CLIC)+1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_USU_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_USU_LISTA_ACTUAL", PAG_USU_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_USU_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_USU_CANT_NRO_CLIC)+1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodosUsuario);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase("<<")) {
                System.out.println("---------------------__PAGINACION__ATRAS_BTN___: "+accion+" :__--------------------------");
                String PAG_USU_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_USU_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_USU_CANT_NRO_CLIC == null) {
                    PAG_USU_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_USU_CANT_NRO_CLIC);
                String PAG_USU_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_USU_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_USU_LISTA_ACTUAL);
                
                PAG_USU_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_USU_CANT_NRO_CLIC)-1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_USU_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_USU_LISTA_ACTUAL", PAG_USU_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_USU_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_USU_CANT_NRO_CLIC)-1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CU_BAND_FILTRO")).equals("1")) {
                    System.out.println("_   _IF_____LISTA_CARGADA_____");
                    var_redireccionar = 1;
                    filtrar_pag(sesionDatosUsuario, request, metodosUsuario);
                } else {
                    System.out.println("_   _ELSE_________________");
                }
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                
            } else if (accion.equalsIgnoreCase("Cargar Persona")) {
                System.out.println("---------------------__CARGAR_PERSONA__--------------------------");
                String IDCLINICA = (String) request.getParameter("cCli");
                String IDPERSONA = (String) request.getParameter("tIM");
                String NOM_PERSONA = (String) request.getParameter("tNM");
                String APE_PERSONA = (String) request.getParameter("tAM");
                String CINRO_PERSONA = (String) request.getParameter("tCM");
                String ESTADO = (String) request.getParameter("cE");
                System.out.println("---------___DATOS___-----------");
                System.out.println("_   _ID_CLINICA:   :"+IDCLINICA);
                System.out.println("_   _ID_PERSONA:   :"+IDPERSONA);
                System.out.println("_   _NOM_PERSONA:  :"+NOM_PERSONA);
                System.out.println("_   _APE_PERSONA:  :"+APE_PERSONA);
                System.out.println("_   _CINRO_PERSO:  :"+CINRO_PERSONA);
                System.out.println("_   ____ESTADO:    :"+ESTADO);
                System.out.println("-------------------------------");
                
                List<BeanPersona> LISTA_DATOS = new ArrayList<>();
                List<BeanPersona> LISTA_DATOS_AUX = new ArrayList<>();
                
                // MODAL 
                // ID PERSONA SELECCIONADO 
                String CBX_IDPERSONA = (String) request.getParameter("cbxAddNewCli");
                // RECUPERO LOS OTROS DATOS DEL CLIENTE SELECCIONADO POR MEDIO DE SU ID 
                List<BeanPersona> listaDatosMedico;
                listaDatosMedico = metodosPersona.traerDatosPersona(CBX_IDPERSONA);
                if (listaDatosMedico != null) {
                    LISTA_DATOS = listaDatosMedico;
                    LISTA_DATOS_AUX = listaDatosMedico;
                }
                Iterator<BeanPersona> iterMedico = listaDatosMedico.iterator();
                BeanPersona datosMedNew = null;
                String ID_USUARIO="";
//                        , DESC_PERFIL="", PERSONA_NOM="", PERSONA_APE="", PERSONA_CINRO="", PERSONA_RAZONSOCIAL="", PERSONA_RUC="", PERS_TELEF="", PERS_DIRECCION="", PERS_EMAIL="", PERS_USUARIO="", PERS_CLAVE="", PERS_IDPERFIL="", PERS_IDCLINICA="";
                while(iterMedico.hasNext()) {
                    datosMedNew = iterMedico.next();
                    
//                    PERSONA_NOM = datosMedNew.getRP_NOMBRE();
//                    PERSONA_APE = datosMedNew.getRP_APELLIDO();
//                    PERSONA_CINRO = datosMedNew.getRP_CINRO();
//                    PERSONA_RAZONSOCIAL = datosMedNew.getRP_RAZON_SOCIAL();
//                    PERSONA_RUC = datosMedNew.getRP_RUC();
//                    PERS_TELEF = datosMedNew.getRP_TELFMOVIL();
//                    PERS_DIRECCION = datosMedNew.getRP_DIRECCION();
//                    PERS_EMAIL = datosMedNew.getRP_EMAIL();
                    ID_USUARIO = datosMedNew.getSU_IDUSUARIO();
//                    PERS_USUARIO = datosMedNew.getSU_USUARIO();
//                    PERS_CLAVE = datosMedNew.getSU_CLAVE();
//                    PERS_IDPERFIL = datosMedNew.getSU_IDPERFIL();
//                    DESC_PERFIL = datosMedNew.getSU_DESC_PERFIL();
//                    if (PERS_IDPERFIL == null || PERS_IDPERFIL.isEmpty()) {
//                        PERS_IDPERFIL = datosMedNew.getRP_IDCATEGORIA_PERSONA();
//                        DESC_PERFIL = modeloPerfil.recuperarDescPerfil(PERS_IDPERFIL);
//                    }
                    request.setAttribute("CU_USU_BAND_PERFIL", "1"); // ACTIVO LA BANDERA PARA NO MOSTRARLE EL COMBO CON TODOS LOS PERFILES 
                    request.setAttribute("CU_USU_BAND_CLINICA", "1"); // ACTIVO LA BANDERA PARA NO MOSTRARLE EL COMBO CON TODAS LAS CLINICAS 
//                    PERS_IDCLINICA = datosMedNew.getRP_IDCLINICA();
                }
                
                var_redireccionar = 1;
                acceso = "add_usuario.htm";
                sesionDatosUsuario.setAttribute("ID_USU_PERSONA", CBX_IDPERSONA);
                if (ID_USUARIO == null || ID_USUARIO.isEmpty()) {
//                    request.setAttribute("CU_CLI_NOMBRE", PERSONA_NOM);
//                    request.setAttribute("CU_CLI_APELLIDO", PERSONA_APE);
//                    request.setAttribute("CU_CLI_CINRO", PERSONA_CINRO);
//                    request.setAttribute("CU_CLI_RAZON_SOCIAL", PERSONA_RAZONSOCIAL);
//                    request.setAttribute("CU_CLI_RUC", PERSONA_RUC);
//                    request.setAttribute("CU_CLI_TELEFONO", PERS_TELEF);
//                    request.setAttribute("CU_CLI_DIRECCION", PERS_DIRECCION);
//                    request.setAttribute("CU_CLI_EMAIL", PERS_EMAIL);
//                    request.setAttribute("CU_CLI_USUARIO", PERS_USUARIO);
//                    request.setAttribute("CU_CLI_CLAVE", PERS_CLAVE);
//                    request.setAttribute("CU_CLI_PERFIL", DESC_PERFIL);
//                    request.setAttribute("CU_CLI_ESTADO", ESTADO);
//                    request.setAttribute("CU_CLI_IDCLINICA", PERS_IDCLINICA);
                    request.setAttribute("CU_PAC_LISTA_DATOS", LISTA_DATOS);
                    request.setAttribute("CU_PAC_LISTA_DATOS_AUX", LISTA_DATOS_AUX);
                } else {
                    sesionDatosUsuario.setAttribute("ID_USUARIO", ID_USUARIO);
                }
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-----------------ERROR-USUARIO--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            var_redireccionar = 0;
            Logger.getLogger(ControllerUsuario.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------");
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
    
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelUsuario metodosUsuario) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanPersona> listaFiltro = new ArrayList<>();
        listaFiltro = metodosUsuario.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);

//        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "usuario.htm";
//        var_redireccionar = 1;
        request.setAttribute("CU_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CU_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CU_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("CU_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
} // END CLASS 