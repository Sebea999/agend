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
import com.agend.javaBean.BeanPersona;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.agend.modelo.ModelInicioSesion;
import com.agend.modelo.ModelMedico;
import com.agend.modelo.ModelPersona;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author RYUU
 */
@Controller
@WebServlet(name="MedicoController", urlPatterns="/CMSrv") // CONTROLLER MEDICO SERVLET 
public class ControllerMedico extends HttpServlet {
    
    
    com.agend.modelo.ModelInicioSesion modeloIniSes = new com.agend.modelo.ModelInicioSesion();
    com.agend.modelo.ModelPerfil modeloPerfil = new com.agend.modelo.ModelPerfil();
    
    
    
    @RequestMapping("/medico")
    public ModelAndView medico(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        System.out.println("-----__1__---------CONTROLLER__MEDICO--------------MODEL_AND_VIEW-------");
        String SESION_IDUSER = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println(".   _CM__ID_USUARIO_PERSONA:     "+SESION_IDUSER);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CM__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuMedico(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagMedico", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDUSER, "pagMedico", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        mav.setViewName(paginaMav);
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDUSER);
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        return mav;
    }
    
    
    
    @RequestMapping("/add_medico")
    public ModelAndView add_medico(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_MEDICO = (String) sesionDatosUsuario.getAttribute("ID_MEDICO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__2__MAV___ID_MEDICO:     "+ID_PERSONA_MEDICO);
        
        System.out.println("-----__2__--------CONTROLLER__AÑADIR_MEDICO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CM__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CM__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuMedico(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagMedicoAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagMedicoAdd", request);
        System.out.println("pagina_mav:     "+paginaMav);
        
        sesionDatosUsuario.setAttribute("IDPERSONA", SESION_IDPERSONA); // SIGO RENOVANDO EL VALOR DE LA SESION DE "IDPERSONA" CON EL MISMO VALOR PARA EVITAR QUE LA SESION DE POR INACTIVA ESTA VARIABLE PORQUE ENTRE UNA ACCION Y OTRA PUEDE PASAR X CANTIDAD DE TIEMPO 
//        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "0"); // CARGO CERO PARA MOSTRARLE ABIERTA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        sesionDatosUsuario.setAttribute("JSP_MOSTRAR_BARRA_MENU", "1"); // CARGO UNO PARA NO MOSTRARLE O PARA MANTENER CERRADA LA BARRA LATERAL DEL MENU EN EL JSP AL USUARIO 
        
        mav.setViewName(paginaMav);
        return mav;
    }
    
    
    
    @RequestMapping("/edit_medico")
    public ModelAndView edit_medico(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();
        
        // UTILIZO LA SESION PARA PASAR DATOS COMO EL ID 
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS DEL USER 
        
        String ID_PERSONA_MEDICO = (String) sesionDatosUsuario.getAttribute("ID_MEDICO"); // RECUPERO A TRAVES DE LA SESION EL DATO DEL IDPERSONA DEL CLIENTE QUE FUE CARGADO EN EL METODO doPost 
        System.out.println("__3__MAV___ID_MEDICO:     "+ID_PERSONA_MEDICO);
        
        System.out.println("-----__3__--------CONTROLLER__AÑADIR_MEDICO------------MODEL_AND_VIEW-------");
        String SESION_IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA"); // IDPERSONA DEL USUARIO LOGEADO 
        System.out.println(".   _CM__ID_USUARIO_PERSONA:     "+SESION_IDPERSONA);
        String IDPERFIL_USER = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
        System.out.println(".   _CM__ID_PERFIL_USER:    :"+IDPERFIL_USER);
        
        String paginaMav = "menu_principal";
        if (modeloPerfil.isMenuMedico(IDPERFIL_USER)) {
            paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagMedicoAdd", request);
        }
//        String paginaMav = modeloIniSes.controlValidaciones(SESION_IDPERSONA, "pagMedicoAdd", request);
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
        
        ModelMedico metodos = new ModelMedico();
        ModelPersona metodosPersona = new ModelPersona();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        int var_redireccionar = 0;
        String acceso = "medico.htm";
        String accion = request.getParameter("accion");
        String idPersona, idPersonaMedico, idUsuarioMedico;
        
        try {
            System.out.println("__ACCION:       "+accion);
            idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            System.out.println("__ID_PERSONA:   "+idPersona);
            
            if (accion.equalsIgnoreCase("Add Medico")) { // BOTON PARA AÑADIR UN NUEVO MEDICO 
                System.out.println("---------------------__AÑADIR_MEDICO__--------------------------");
                var_redireccionar = 1;
                acceso = "add_medico.htm";
                sesionDatosUsuario.setAttribute("ID_MEDICO", ""); // LE PASO EL VALOR VACIO, YA QUE SE QUIERE CREAR UN PACIENTE Y SI NO LE CARGO CON EL VALOR VACIO ENTONCES SI ANTES SE MODIFICO A UN PACIENTE, LA SESION VA A MANTENER ESE IDPERSONA Y NO SE VA A DAR CUENTA DE QUE QUIERO AÑADIR UN PACIENTE  
                
            } else if (accion.equalsIgnoreCase("Editar")) { // BOTON PARA EDITAR O MODIFICAR LOS DATOS DEL CLIENTE 
                System.out.println("---------------------__EDITAR__--------------------------");
                idPersonaMedico = (String) request.getParameter("tI");
                System.out.println("_doPost__ID_PERSONA_MEDICO:     "+idPersonaMedico);
                
                var_redireccionar = 1;
                acceso = "edit_medico.htm";
                sesionDatosUsuario.setAttribute("ID_MEDICO", idPersonaMedico);
                
            } else if(accion.equalsIgnoreCase("GRABAR") || accion.equalsIgnoreCase("Guardar")) {
                System.out.println("---------------------__GUARDAR__--------------------------");
                int varValiVacio = 0; // VARIABLE QUE UTILIZO PARA CONTROLAR DE QUE LOS CAMPOS NO SE ENCUENTREN VACIOS O NULOS 
                idPersonaMedico = (String) sesionDatosUsuario.getAttribute("ID_MEDICO");
                System.out.println("__ID_MEDICO:   "+idPersonaMedico);
                idUsuarioMedico = (String) sesionDatosUsuario.getAttribute("ID_USUARIO_MEDICO");
                System.out.println("__ID_USUARIO_MEDICO:   "+idUsuarioMedico);
                
                // RECUPERAR LOS DATOS 
                String MED_NOMBRE = (String) request.getParameter("tN");
                if (MED_NOMBRE == null || MED_NOMBRE.isEmpty()) { varValiVacio++; MED_NOMBRE=""; }
                String MED_APELLIDO = (String) request.getParameter("tA");
                if (MED_APELLIDO == null || MED_APELLIDO.isEmpty()) { varValiVacio++; MED_APELLIDO=""; }
                String MED_CINRO = (String) request.getParameter("tNC");
                if (MED_CINRO == null || MED_CINRO.isEmpty()) { varValiVacio++; MED_CINRO=""; }
                String MED_RAZON_SOCIAL = (String) request.getParameter("tRS");
                if (MED_RAZON_SOCIAL == null || MED_RAZON_SOCIAL.isEmpty()) { varValiVacio++; MED_RAZON_SOCIAL=""; }
                String MED_RUC = (String) request.getParameter("tR");
                if (MED_RUC == null || MED_RUC.isEmpty()) { varValiVacio++; MED_RUC=""; }
                String MED_TELEFONO = (String) request.getParameter("tT");
                if (MED_TELEFONO == null || MED_TELEFONO.isEmpty()) { varValiVacio++; MED_TELEFONO=""; }
                String MED_DIRECCION = (String) request.getParameter("tD");
                if (MED_DIRECCION == null || MED_DIRECCION.isEmpty()) { varValiVacio++; MED_DIRECCION=""; }
                String MED_EMAIL = (String) request.getParameter("tE");
                if (MED_EMAIL == null || MED_EMAIL.isEmpty()) { varValiVacio++; MED_EMAIL=""; }
                String MED_FECHANAC = (String) request.getParameter("tFN");
                if (MED_FECHANAC == null || MED_FECHANAC.isEmpty()) { varValiVacio++; MED_FECHANAC=""; }
                String MED_SEXO = (String) request.getParameter("cSe");
                if (MED_SEXO == null || MED_SEXO.isEmpty()) { varValiVacio++; MED_SEXO=""; }
                String MED_IDCLINICA = (String) request.getParameter("cCli");
                if (MED_IDCLINICA == null || MED_IDCLINICA.isEmpty()) { varValiVacio++; MED_IDCLINICA=""; }
                String MED_LOGIN;
//                try {
//                    PAC_LOGIN = (String) request.getParameter("tL");
//                } catch(NullPointerException e) {
                    MED_LOGIN = "";
//                }
//                if (PAC_LOGIN.isEmpty() || PAC_LOGIN.equals("")) {
////                    varValiVacio = varValiVacio + 1;
//                    PAC_LOGIN="";
//                }
                String MED_CLAVE;
//                try {
//                    PAC_CLAVE = (String) request.getParameter("tC");
//                } catch(NullPointerException e) {
                    MED_CLAVE = "";
//                }
//                if (PAC_CLAVE.isEmpty() || PAC_CLAVE.equals("")) {
////                    varValiVacio = varValiVacio + 1;
//                    PAC_CLAVE="";
//                }
                String MED_USU_ESTADO = "A"; // COMO LO TENGO OCULTO ENTONCES LO DEJO POR DEFECTO CON ESTADO "A" 
//                String PAC_USU_ESTADO = (String) request.getParameter("cE");
                if (MED_USU_ESTADO == null || MED_USU_ESTADO.isEmpty()) { varValiVacio++; MED_USU_ESTADO=""; }
                
                // IMPRESION DE DATOS 
                System.out.println("-   ------------__DATOS__--------------");
                System.out.println("-       MED_NOMBRE:     "+MED_NOMBRE);
                System.out.println("-       MED_APELLIDO:   "+MED_APELLIDO);
                System.out.println("-       MED_NRO_CI:     "+MED_CINRO);
                System.out.println("-       MED_RAZON_SOCIAL:  "+MED_RAZON_SOCIAL);
                System.out.println("-       MED_RUC:        "+MED_RUC);
                System.out.println("-       MED_TELEFONO:   "+MED_TELEFONO);
                System.out.println("-       MED_DIRECCION:  "+MED_DIRECCION);
                System.out.println("-       MED_EMAIL:      "+MED_EMAIL);
                System.out.println("-       MED_FEC_NAC:    "+MED_FECHANAC);
                System.out.println("-       MED_SEXO:       "+MED_SEXO);
                System.out.println("-       MED_ID_CLI:     "+MED_IDCLINICA);
                System.out.println("-       -----------------------");
                System.out.println("-       MED_LOGIN:      "+MED_LOGIN);
                System.out.println("-       MED_CLAVE:      "+MED_CLAVE);
                System.out.println("-       MED_ESTADO:     "+MED_USU_ESTADO);
                System.out.println("-   -----------------------------------");
                System.out.println("___     ___var_vali_vacio:      "+varValiVacio);
                
                // VALIDACIONES 
                String MENSAJE, TIPO_MENSAJE; // 1 : exito / 2 : error 
                if (varValiVacio > 0) { // VALIDACION PARA CONTROLAR SI ES QUE NINGUNO DE LOS CAMPOS IMPORTANTES SE ENCUENTRA VACIO 
                    MENSAJE = "No puede dejar campos vacios.";
                    TIPO_MENSAJE = "2";
                    
                } else if(metodosPersona.ctrlExistePersona(MED_CINRO, MED_IDCLINICA, "") == true && idPersonaMedico.isEmpty()) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT A UN MEDICO QUE YA EXISTE - PARA SABER QUE SE QUIERE HACER UN INSERT EL IDPERSONA DEL MEDICO DEBE ESTAR VACIO Y NO CARGADO 
                    MENSAJE = "El medico con Nro. de CI "+MED_CINRO+" ya existe.";
                    TIPO_MENSAJE = "2";
//                    
//                } else if(metodos.ctrlExisteUsuCli(CLI_LOGIN, idPersonaPaciente) == true) { // VALIDACION PARA CONTROLAR QUE NO SE HAGA UN INSERT O UPDATE CON EL NOMBRE DE UN USUARIO QUE YA EXISTE PARA EVITAR QUE DOS CLIENTES TENGAN EL MISMO USUARIO 
//                    MENSAJE = "Ya existe otro usuario con el mismo nombre de usuario, seleccione otro.";
//                    TIPO_MENSAJE = "2";
//                    
                } else { // SI HA PASADO CON TODAS LAS VALIDACIONES ENTONCES LLAMARIA AL METODO PARA QUE GUARDE/ACTUALIZE EL MEDICO 
                    String RP_IDPERSONA = idPersonaMedico;
                    String RP_NOMBRE = MED_NOMBRE;
                    String RP_APELLIDO = MED_APELLIDO;
                    String RP_CINRO = MED_CINRO;
                    String RP_TIPODOC = "CI";
                    String RP_IDCATEGORIA_PERSONA = modeloPerfil.getIdPerfilMedico(); // 1 : ADMINISTRADOR / 2 : FUNCIONARIO / 3 : SECRETARIO / 4 : PACIENTE / 5 : MEDICO  
//                    String RP_IDCATEGORIA_PERSONA = "5"; // 1 : ADMINISTRADOR / 2 : FUNCIONARIO / 3 : SECRETARIO / 4 : PACIENTE / 5 : MEDICO  
                    String RP_DESC_CATEG_PERSONA = modeloPerfil.getDescPerfilMedico();
//                    String RP_DESC_CATEG_PERSONA = metodosIniSes.recuperarDescPerfil(RP_IDCATEGORIA_PERSONA);
                    String RP_RAZON_SOCIAL = MED_RAZON_SOCIAL;
                    String RP_RUC = MED_RUC;
                    String RP_IDBARRIO = "0";
                    String RP_DESC_BARRIO = "(NO/DEFINIDO)";
                    String RP_IDCIUDAD = "0";
                    String RP_DESC_CIUDAD = "(NO/DEFINIDO)";
                    String RP_DIRECCION = MED_DIRECCION;
                    String RP_EMAIL = MED_EMAIL;
                    String RP_TELFPARTICULAR = "";
                    String RP_TELFMOVIL = MED_TELEFONO;
                    String RP_IDCIUDADNAC = "0";
                    String RP_DESC_CIUDADNAC = "(NO/DEFINIDO)";
                    String RP_FECHANAC = MED_FECHANAC;
                    String RP_SEXO = MED_SEXO;
                    String RP_RELIGION = "";
                    String RP_ESTADOCIVIL = "ND";
                    String RP_GRUPOSANGUINEO = "";
                    String RP_OBSERVACION = "";
                    String RP_FECHAALTA = metodosIniSes.traerFechaHoraHoy();
                    String RP_FECULTIMOMOV = null;
                    String RP_FOTO = "";
                    String RP_USU_ALTA = idPersona;
                    String RP_USU_MOD = "0";
                    String RP_IDPAIS = "0";
                    String RP_DESC_PAIS = "(NO/DEFINIDO)";
                    String RP_TELEFPARTICULAR = "";
                    String RP_IDCLINICA = MED_IDCLINICA;
                    String RP_OCUPACION = "";
                    String RP_ANTECEDENTES = "";
                    String RP_EXPEDIENTE_CLINICO = "";
                    String RP_SEGURO_MEDICO = "";
                    String RP_TIENE_HIJOS = "0";
                    String RP_CANT_HIJOS = "0";
                    String RP_FOTO_PATH_ABS = "";
                    String RP_IDPERSONA_NEW = "";
                    // USUARIO 
                    String SU_IDUSUARIO = idUsuarioMedico;
                    String SU_IDPERSONA = RP_IDPERSONA;
                    String SU_USUARIO = MED_LOGIN;
                    String SU_CLAVE = MED_CLAVE;
                    String SU_ESTADO = MED_USU_ESTADO;
                    String SU_IDPERFIL = RP_IDCATEGORIA_PERSONA; // idperfil = 5 (MEDICO)
                    String SU_DESC_PERFIL = RP_DESC_CATEG_PERSONA;
                    String SU_EMAIL = MED_EMAIL;
                    String SU_ESTADO_MIGRAR = "NO";
                    String SU_WEB = "0";
                    String SU_CONFIR_EMAIL = "NO";
                    // LLAMO AL METODO PARA REGISTRAR O MODIFICAR AL MEDICO 
                    TIPO_MENSAJE = metodos.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, RP_IDCLINICA, RP_SEGURO_MEDICO, RP_TIENE_HIJOS, RP_CANT_HIJOS, RP_FOTO_PATH_ABS, RP_IDPERSONA_NEW, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
//                    TIPO_MENSAJE = metodos.guardar(new BeanPersona(RP_IDPERSONA, RP_NOMBRE, RP_APELLIDO, RP_CINRO, RP_TIPODOC, RP_IDCATEGORIA_PERSONA, RP_DESC_CATEG_PERSONA, RP_RAZON_SOCIAL, RP_RUC, RP_DIRECCION, RP_EMAIL, RP_IDBARRIO, RP_DESC_BARRIO, RP_IDCIUDAD, RP_DESC_CIUDAD, RP_TELFPARTICULAR, RP_TELFMOVIL, RP_IDCIUDADNAC, RP_DESC_CIUDADNAC, RP_FECHANAC, RP_SEXO, RP_RELIGION, RP_ESTADOCIVIL, RP_GRUPOSANGUINEO, RP_OBSERVACION, RP_FECHAALTA, RP_FECULTIMOMOV, RP_FOTO, RP_USU_ALTA, RP_USU_MOD, RP_IDPAIS, RP_DESC_PAIS, RP_TELEFPARTICULAR, RP_OCUPACION, RP_ANTECEDENTES, RP_EXPEDIENTE_CLINICO, SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL, RP_IDCLINICA, RP_SEGURO_MEDICO));
                    if (TIPO_MENSAJE.equals("2")) { // SALTO UN ERROR AL QUERER GUARDAR/ACTUALIZAR EL CLIENTE 
                        MENSAJE = "Salto un error, vuelva a intentarlo mas tarde.";
                        // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO MEDICO, ENTONCES CONTROLANDO EL IDPERSONA SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                        if (idPersonaMedico == null || idPersonaMedico.isEmpty() || idPersonaMedico.equals("")) {
                            acceso = "add_medico.htm";
                        } else { // SI EL ID MEDICO NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
                            acceso = "edit_medico.htm";
                        }
                    } else { // SI NO FUESE 2 ENTONCES SERIA 1, QUE SE REALIZO CORRECTAMENTE LA OPERACION DE GUARDAR / ACTUALIZAR 
                        MENSAJE = "Se ha realizado correctamente la operación.";
                        acceso = "medico.htm";
                    }
                }
                // CONTROLO SI EL TIPO DE MENSAJE ES IGUAL A 2 PARA SABER SI DIO ALGUN ERROR O SALTO ALGUNA VALIDACION
                if (TIPO_MENSAJE.equals("2")) {// SI FUESE ASI ENTONCES CARGARIA LA VARIABLE DE ACCESO YA QUE EN LAS VALIDACIONES NO LES CARGO, Y SI SALTA ALGUNA, NO VA A ENTRAR EN EL ELSE Y NO SE VA A CARGAR LA PAGINA A DONDE SE DEBE DE REDIRECCIONAR Y ENTONCES VA A SALTAR UN ERROR POR DEJAR VACIO ESTA VARIABLE 
                    // COMO EL BOTON DE GUARDAR SE ENCUENTRA EN LA MISMA PAGINA, QUE ESTA PAGINA ME SIRVE PARA EDITAR Y PARA INSERTAR UN NUEVO CLIENTE, ENTONCES CONTROLANDO EL IDCLIENTE SABRE QUE OPERACION REALIZAR Y A QUE PAGINA LE TENGO QUE REDIRECCIONAR 
                    if (idPersonaMedico == null || idPersonaMedico.isEmpty() || idPersonaMedico.equals("")) {
                        acceso = "add_medico.htm";
                    } else { // SI EL ID CLIENTE NO SE ENCONTRASE VACIO, ENTONCES EL USUARIO ESTARIA EDITAN AL CLIENTE 
                        acceso = "edit_medico.htm";
                    }
                    // SI SE GENERO ALGUN TIPO DE ERROR, YA SEA AL MOMENTO DE INSERT / ACTUALIZAR O SI SE ACTIVO ALGUNA VALIDACION, ENTONCES DEBERIA DE PASAR LAS VARIABLES RECUPERADAS PARA VOLVER A MOSTRARLAS YA QUE EL USUARIO TENDRA QUE EDITARLAS Y NO VOLVER A CARGARLAS // OBS.: NO LES PASO LOS DATOS A TRAVES DE LA SESION PORQUE NO QUIERO MANTENER ESTOS DATOS EN MEMORIA, PREFIORO QUE SE UTILICEN Y LUEGO SE BORREN 
                    request.setAttribute("CM_MED_NOMBRE", MED_NOMBRE);
                    request.setAttribute("CM_MED_APELLIDO", MED_APELLIDO);
                    request.setAttribute("CM_MED_CINRO", MED_CINRO);
                    request.setAttribute("CM_MED_RAZON_SOCIAL", MED_RAZON_SOCIAL);
                    request.setAttribute("CM_MED_RUC", MED_RUC);
                    request.setAttribute("CM_MED_TELEFONO", MED_TELEFONO);
                    request.setAttribute("CM_MED_DIRECCION", MED_DIRECCION);
                    request.setAttribute("CM_MED_EMAIL", MED_EMAIL);
                    request.setAttribute("CM_MED_FECHA_NAC", MED_FECHANAC);
                    request.setAttribute("CM_MED_SEXO", MED_SEXO);
                    request.setAttribute("CM_MED_IDCLINICA", MED_IDCLINICA);
//                    request.setAttribute("CM_MED_USUARIO", MED_LOGIN);
//                    request.setAttribute("CM_MED_CLAVE", MED_CLAVE);
//                    request.setAttribute("CM_MED_ESTADO", MED_USU_ESTADO);
                }
                var_redireccionar = 1;
                // PASAR DATOS POR MEDIO DEL REQUEST O LA SESION 
                /*
                _OBSERVACION_IMPORTANTE: LE VUELVO A PASAR LOS DATOS DEL CLIENTE EDITADO O AÑADIDO PARA QUE SI LE DA AL BOTON DE ATRAS O DE RECARGAR LA PAGINA 
                    ESTE HARA QUE SE VUELVA A REALIZAR LA UTLIMA ACCION, Y SI TENGO ESTOS DATOS, ENTONCES LOS METODOS COMO SON DINAMICOS, NO VOLVERAN A AÑADIR Y EDITARAN NOMAS, COSA QUE NO IMPORTA PORQUE EDITARAN CON LOS DATOS YA CARGADOS, 
                    PERO SI ESTOS DATOS NO ESTUVIERAN, ENTONCES REEMPLAZARIA LOS DATOS CON DATOS VACIOS DE ESAS COLUMNAS, Y AL ELEGIR OTRO CLIENTE, ESTE REEMPLAZARIA ESTOS DATOS DEL CLIENTE ANTERIOR 
                */
                sesionDatosUsuario.setAttribute("ID_MEDICO", idPersonaMedico);
                sesionDatosUsuario.setAttribute("ID_USUARIO_MEDICO", idUsuarioMedico);
                request.setAttribute("CM_MENSAJE", MENSAJE);
                request.setAttribute("CM_TIPO_MENSAJE", TIPO_MENSAJE);
                
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
                acceso = "medico.htm";
                var_redireccionar = 1;
                request.setAttribute("CM_CBX_MOSTRAR", filtro_cbxMostrar);
                request.setAttribute("CM_TXT_BUSCAR", filtro_txtBuscar);
                request.setAttribute("CM_LISTA_FILTRO", listaFiltro);
                sesionDatosUsuario.setAttribute("CM_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
                
            } else if (esNumero(accion) == true) {
                System.out.println("---------------------__PAGINACION_NUMBER_: "+accion+" :__--------------------------");
                // COMO EL NOMBRE DEL BOTON REPRESENTA AL NUMERO DE LA PAGINA QUE QUIERE MOSTRAR ENTONCES LE PASO A LA VARIABLE QUE UTILIZO PARA SABER LA PAGINA ACTUAL 
                sesionDatosUsuario.setAttribute("PAG_MED_LISTA_ACTUAL", accion);
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")).equals("1")) {
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
                String PAG_MED_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_MED_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_MED_CANT_NRO_CLIC == null) {
                    PAG_MED_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_MED_CANT_NRO_CLIC);
                String PAG_MED_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_MED_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:   "+PAG_MED_LISTA_ACTUAL);
                
                PAG_MED_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_MED_CANT_NRO_CLIC)+1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_MED_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_MED_LISTA_ACTUAL", PAG_MED_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_MED_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_MED_CANT_NRO_CLIC)+1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")).equals("1")) {
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
                String PAG_MED_CANT_NRO_CLIC = (String) sesionDatosUsuario.getAttribute("PAG_MED_CANT_BTN_DERE_CLIC"); // BTN >> CLIC 
                if (PAG_MED_CANT_NRO_CLIC == null) {
                    PAG_MED_CANT_NRO_CLIC = "1";
                }
                System.out.println("_   _CTRL__CANT_CLIC:   "+PAG_MED_CANT_NRO_CLIC);
                String PAG_MED_LISTA_ACTUAL = (String) sesionDatosUsuario.getAttribute("PAG_MED_LISTA_ACTUAL");
                System.out.println("_   _CTRL__PAG_ACTUAL:  "+PAG_MED_LISTA_ACTUAL);
                
                PAG_MED_LISTA_ACTUAL = String.valueOf(((Integer.parseInt(PAG_MED_CANT_NRO_CLIC)-1)*3)-2);
                System.out.println("_   __PAG_ACTUAL_SIGTE:     :"+PAG_MED_LISTA_ACTUAL);
                
                sesionDatosUsuario.setAttribute("PAG_MED_LISTA_ACTUAL", PAG_MED_LISTA_ACTUAL);
                sesionDatosUsuario.setAttribute("PAG_MED_CANT_BTN_DERE_CLIC", String.valueOf(Integer.parseInt(PAG_MED_LISTA_ACTUAL)-1));
                
                // CONTROL DE LA BANDERA PARA SABER SI DEBO DE BUSCAR LA PAGINA DESDE EL FILTRO O SE VA A BUSCAR DESDE EL CARGA GRILLA (QUE ESTA EN EL JSP - PERO SI DEBO DE BUSCAR LA PAGINA DESDE EL METODO FILTRAR ENTONCES DEBO DE HACERLO DESDE EL CONTROLADOR) 
                System.out.println(".");System.out.println(".");
                System.out.println(".");System.out.println(".");
                System.out.println("_   ___CONTROL_LISTA_FILTRO____     -");
                String band_filtro = (String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO");
                System.out.println("_   _BAND_FILTRO:   :"+band_filtro);
                // CONTROLO POR MEDIO DE LA BANDERA SI SE ACTIVO LOS FILTROS O NO 
                if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")) == null) {
                    System.out.println("_   _ELSE_________null________");
                } else if (((String)sesionDatosUsuario.getAttribute("CM_BAND_FILTRO")).equals("1")) {
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
                String TXT_CINRO = (String) request.getParameter("tNC");
                String TXT_NOMBRE = (String) request.getParameter("tN");
                String TXT_APELLIDO = (String) request.getParameter("tA");
                String TXT_RAZON_SOCIAL = (String) request.getParameter("tRS");
                String TXT_RUC = (String) request.getParameter("tR");
                String TXT_TELEFONO = (String) request.getParameter("tT");
                String TXT_DIRECCION = (String) request.getParameter("tD");
                String TXT_EMAIL = (String) request.getParameter("tE");
                String TXT_FECHA_NAC = (String) request.getParameter("tFN");
                String TXT_SEXO = (String) request.getParameter("cSe");
                String MED_IDCLINICA = (String) request.getParameter("cCli");
//                System.out.println("-     ------_VARIABLES_----------------------");
//                System.out.println("-     _      NRO_CI:   :"+TXT_CINRO);
//                System.out.println("-     _      NOMBRE:   :"+TXT_NOMBRE);
//                System.out.println("-     _    APELLIDO:   :"+TXT_APELLIDO);
//                System.out.println("-     _RAZON_SOCIAL:   :"+TXT_RAZON_SOCIAL);
//                System.out.println("-     _         RUC:   :"+TXT_RUC);
//                System.out.println("-     _    TELEFONO:   :"+TXT_TELEFONO);
//                System.out.println("-     _   DIRECCION:   :"+TXT_DIRECCION);
//                System.out.println("-     _       EMAIL:   :"+TXT_EMAIL);
//                System.out.println("-     _   FECHA_NAC:   :"+TXT_FECHA_NAC);
//                System.out.println("-     _        SEXO:   :"+TXT_SEXO);
//                System.out.println("-     ---------------------------------------");
                
                // GENERO EL NUMERO ALEATORIO CON LA CLASE RANDOM 
                TXT_CINRO = metodosPersona.generar_nro_random();
                System.out.println("_   _NRO_CI_RANDOM:   :"+TXT_CINRO);
                
                // PASO LAS VARIABLES AL JSP
                acceso = "add_medico.htm"; // OBS.: SOLO AL AÑADIR UN NUEVO PACIENTE SE PODRA UTILIZAR EL BOTON DE GENERAR NRO TEMPORAL PORQUE NO TIENE SENTIDO QUE EL USUARIO SIGA VIENDO EL BOTON CUANDO EDITE LOS DATOS DE UN PACIENTE 
                var_redireccionar = 1;
                request.setAttribute("CM_MED_NOMBRE", TXT_NOMBRE);
                request.setAttribute("CM_MED_APELLIDO", TXT_APELLIDO);
                request.setAttribute("CM_MED_CINRO", TXT_CINRO);
                request.setAttribute("CM_MED_RAZON_SOCIAL", TXT_RAZON_SOCIAL);
                request.setAttribute("CM_MED_RUC", TXT_RUC);
                request.setAttribute("CM_MED_TELEFONO", TXT_TELEFONO);
                request.setAttribute("CM_MED_DIRECCION", TXT_DIRECCION);
                request.setAttribute("CM_MED_EMAIL", TXT_EMAIL);
                request.setAttribute("CM_MED_FECHA_NAC", TXT_FECHA_NAC);
                request.setAttribute("CM_MED_SEXO", TXT_SEXO);
//                request.setAttribute("CM_MED_USUARIO", TXT_LOGIN);
//                request.setAttribute("CM_MED_CLAVE", TXT_CLAVE);
//                request.setAttribute("CM_MED_ESTADO", TXT_USU_ESTADO);
                request.setAttribute("CM_MED_IDCLINICA", MED_IDCLINICA);
                
                
                /*
                 * OBSERVACION: COMO NECESITO LIMPIAR LAS VARIABLES DE LA PAGINACION AL REFRESCAR LA PAGINA 
                    Y NO PUEDO LIMPIAR LAS VARIABLES DESDE EL CONTROLADOR, ENTONCES POR ESO LE CREO 
                    UN METODO AL EVENTO DE LIMPIAR QUE SE ENCUENTRA EN LA PAGINA PRINCIPAL 
                */
            } else if (accion.equalsIgnoreCase("Limpiar")) {
                System.out.println("------------------------__LIMPIAR_PAGINA_PRINCIPAL__------------------------------");
                var_redireccionar = 1;
                acceso = "medico.htm";
                sesionDatosUsuario.setAttribute("PAG_MED_CANT_BTN_DERE_CLIC", "1");
                sesionDatosUsuario.setAttribute("PAG_MED_LISTA_ACTUAL", "1");
                sesionDatosUsuario.setAttribute("CM_BAND_FILTRO", "0"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
            }
            sesionDatosUsuario.setAttribute("IDPERSONA", idPersona);
        } catch (Exception e) {
            System.out.println("-----------------ERROR-MEDICO--------------------");
            System.out.println("_ERROR EN doPost():      "+e.getMessage());
            acceso = "medico.htm";
            var_redireccionar = 0;
            Logger.getLogger(ControllerMedico.class.getName()).log(Level.SEVERE, null, e);
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
    
    
    private void filtrar_pag(HttpSession sesionDatosUsuario, HttpServletRequest request, ModelMedico metodos) {
        System.out.println("---------------------__FILTRAR__--------------------------");
        String filtro_cbxMostrar = (String) request.getParameter("cM"); // cM: combo Mostrar 
        System.out.println("_   FILTRO_CBX_MOSTRAR:     "+filtro_cbxMostrar);
        String filtro_txtBuscar = (String) request.getParameter("txB"); // txB: txt Buscar 
        System.out.println("_   FILTRO_TXT_BUSCAR:      "+filtro_txtBuscar);

        // CARGAR UNA LISTA LLAMANDO AL METODO QUE VA A FILTRAR 
        List<BeanPersona> listaFiltro = new ArrayList<>();
        listaFiltro = metodos.filtrar(sesionDatosUsuario, filtro_cbxMostrar, filtro_txtBuscar);

        // PASAR LA LISTA Y LOS DATOS AL JSP 
//        acceso = "medico.htm";
//        var_redireccionar = 1;
        request.setAttribute("CM_CBX_MOSTRAR", filtro_cbxMostrar);
        request.setAttribute("CM_TXT_BUSCAR", filtro_txtBuscar);
        request.setAttribute("CM_LISTA_FILTRO", listaFiltro);
        sesionDatosUsuario.setAttribute("CM_BAND_FILTRO", "1"); // VARIABLE QUE USO COMO BANDERA PARA PODER IDENTIFICAR CUANDO REDIRECCIONAR AL METODO DE FILTRAR DESDE LA PAGINACION 
    }
    
    
} // END CLASS 