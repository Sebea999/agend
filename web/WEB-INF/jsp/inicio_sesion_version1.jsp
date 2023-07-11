<%-- 
    Document   : inicio_sesion
    Created on : 10-may-2021, 13:00:25
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanPerfil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/styleIniSes.css">
    </head>
    <body class="backIS">
<!--        <div class="container">
            <form action="" method="post">
                <p class="login-text" style="font-size: 2rem; font-weight: 800;">Login</p>
                <div class="input-group">
                    <input type="email" placeholder="Email" required="required">
                </div>
                <div class="input-group">
                    <input type="password" placeholder="Password" required="required">
                </div>
                <div class="input-group">
                    <input type="submit" name="accion" value="Ingresar" class="btn btn-primary" />
                </div>
                <p class="login-register-text">Don't have an account? <a href="register.htm">Register Here</a></p>
            </form>
        </div>-->
        
        <!--<div class="container">-->
            <form class="box" action="ISSrv" method="post" autocomplete="off">
                <div class="obj-group">
                    <h1>Login</h1>
                </div>
                <%
                    System.out.println(".");System.out.println(".");System.out.println(".");
                    System.out.println("-------------------------------___JSP_______INICIO_SESION_________------------------------");
                    HttpSession sesionDatosUsuario = request.getSession();
                    String claseCss="none", rptaError="";
                    String usuarioDato="", passwordDato="";
                    rptaError = (String)request.getAttribute("respuesta");
                    
                    if (rptaError != null) {
                        usuarioDato = (String)request.getAttribute("usuarioDato");
                        if (usuarioDato == null) {
                            usuarioDato = "";
                        }
                        passwordDato = (String)request.getAttribute("passwordDato");
                        if (passwordDato == null) {
                            passwordDato = "";
                        }
                        claseCss="block";
                    }
                    
                    
                    // INSTANCIO EL CONTROLADOR DE INICIO SESION PARA PODER CONTROLAR LA VARIABLE GLOBAL EN CASO DE QUE SE HAYA ACTIVADO LA VARIABLE GLOBAL DE LA VALIDACION DE ESTADO_MIGRAR 
                        com.agend.controlador.ControllerInicioSesion iniSeController = new com.agend.controlador.ControllerInicioSesion();
                        if (iniSeController.varValidacionesEstado == 1) { // CONTROLO LA BANDERA SI ES QUE SE CAMBIO SE SU VALOR PARA SABER SI LE MUESTRO EL MENSAJE EN CASO DE QUE SE ESTE MIGRANDO 
                            System.out.println("....__IF__  VARIABLE GLOBAL varValidacionesEstado = 1 ___........");
                            rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
                            claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
                            iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  
                            
                        } else {
                            System.out.println("....__ELSE__  VARIABLE GLOBAL varValidacionesEstado DIFERENTE A 1 ___........");
                            // CONTROLAR SI ES QUE HAY ALGUN USUARIO ACTIVO PARA REDIRECCIONAR AL MENU 
                            com.agend.modelo.ModelInicioSesion metodosIniSes = new com.agend.modelo.ModelInicioSesion();
                            com.agend.modelo.ModelPerfil metodosPerfil = new com.agend.modelo.ModelPerfil();
                            com.agend.controlador.ControllerInicioSesion inicioSesController = new com.agend.controlador.ControllerInicioSesion();
                            String paginaJsp = "inicioSesion.jsp";
                            
                            if (iniSeController.varValidacionesEstado == 2) { // SI ES IGUAL A 2 ES PORQUE YA INGRESO YA Y NO CUENTA CON IDALUMNO PERO COMO YO AL REDIRECCIONAR MAS ABAJO AL INICIO DE SESION ESTE ENTRA EN UN BUCLE PORQUE VUELVA A CONTROLAR Y LE VUELVO A REDIRIGIR A LA MISMA PAGINA Y VUELVE A HACER LOS MISMOS CONTROLES Y ASI SE QUEDA ENTRANDO EN UN BUCLE QUE CAUSA ERROR, ENTONCES PARA EVITAR ESO, CONTROLO PRIMERAMENTE SI ES QUE LA VARIABLE NO ESTA YA EN 2 QUE SERIA CUANDO SE HAYA VERIFICADO PARA NO VOLVER A CONTROLAR TODOS LOS DEMAS PASOS 
                                System.out.println(".       __IF__      varValidacionesEstado = 2_____");
//                                rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
//                                claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
                                iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  
                                
                            } else {
                                System.out.println(".       __ELSE__    varValidacionesEstado DIFERENTE A 2____");
//                                inicioSesController.VAR_GET_IDDOCENTE = (String) sesionDatosUsuario.getAttribute("idDocente");
//                                System.out.println(".          _IDALUMNO_VAR_GLOBAL:        "+inicioSesController.VAR_GET_IDDOCENTE);
                                String IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//                                String IDPERSONA = inicioSesController.VAR_GET_IDDOCENTE;
                                System.out.println(".       __JSP___ ID_PERSONA_____    "+IDPERSONA+"    _______");
                                String idUsuario = (String) sesionDatosUsuario.getAttribute("IDUSUARIO");
                                System.out.println(".       __JSP___ ID_USUARIO:        "+idUsuario);
                                sesionDatosUsuario.setAttribute("IDPERSONA", IDPERSONA);
                                
                                if (IDPERSONA == null || IDPERSONA.isEmpty() || IDPERSONA.equals("")) {
                                    System.out.println("_IS:JSP___IF___ID_PERSONA_IS_NULL____");
//                                    System.out.println("_*__if___controlLogeoUsuarioActivo()___");
//                                    inicioSesController.varValidacionesEstado = 2; // OBSERVACION: POR EL MOMENTO NO HAGO NADA CON ESTE VALOR, QUE ES DIFERENTE AL DE LA VALIDACION DE MIGRAR(1), NO LE CARGO EL MISMO VALOR "1", PARA QUE NO SE ACTIVE EL IF QUE CONTROLA ESTA VARIABLE CON EL VALOR UNO Y EL METODO 
//
//                                    paginaJsp = "inicioSesion.jsp";
//                                    rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
//                                    claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
////                                    iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  

                                } else { // SI EL IDALUMNO ESTA CARGADO ES PORQUE LA SESION NO SE HA CERRADO Y EL ALUMNO MANTIENE TODAVIA SU SESION, ENTONCES LE REDIRECCIONO A UNO DE LOS DOS MENU (ADMINISTRATIVO - ACADEMICO) 
                                    System.out.println("_IS:JSP___ELSE___ID_PERSONA_IS_NULL____");
                                    BeanPerfil beanPerfil = new BeanPerfil();
                                    beanPerfil = metodosPerfil.traerDatosPerfil(idUsuario, beanPerfil);
                                    String idPerfilUsuario = beanPerfil.getSP_IDPERFIL();
                                    System.out.println(".       _JSP__idPerfil:       :"+idPerfilUsuario);
                                    String descPerfilUsuario = beanPerfil.getSP_NOMBRE();
                                    System.out.println(".       _JSP__descPerfil:     :"+descPerfilUsuario);
                                    String idMenuPerfil = beanPerfil.getSP_IDMENU();
                                    System.out.println(".       _JSP__idMenuPerfil:   :"+idMenuPerfil);
                                    
                                    if(idMenuPerfil.equals("0") || idMenuPerfil.equals("1")){ // CONTROLO EL CAMPO IDMENU QUE RECUPERO DE LA TABLA DE PERFIL QUE ME DIRA QUE MENU CORRESPONDE AL PERFIL DEL USUARIO, SI FUESE DIFERENTE A CERO (ROOT) Y 1 (ADMINISTRADOR) ENTONCES REDIRECCIONO AL USUARIO AL MENU DE CLIENTES PERO SI FUESE 0 Y 1 ENTONCES LO REDIRIJO AL MENU DE ADMINISTRATIVO 
                                        paginaJsp = "menu_principal.jsp";
//                                        paginaJsp = "menu_adm.jsp"; // MENU ADMINISTRATIVO PARA LOS FUNCIONARIOS / ADMINISTRADORES 
                                    } else {
                                        paginaJsp = "menu_principal.jsp"; // MENU PARA LOS USUARIO / CLIENTES 
                                    }
                                    System.out.println(".    _REDIRECCIONAR:      "+paginaJsp);
                                    System.out.println(".    _---------_REDIRECCIONAR_MENUS_LOGEO_ACTIVO_--------_");
                                    RequestDispatcher vista = request.getRequestDispatcher(paginaJsp);
                                    vista.forward(request, response);
                                }
                            }
                            System.out.println(".    __2__REDIRECCIONAR:      "+paginaJsp);
                        } // END ELSE IF CONTROL MIGRADO VALIDATION 
                        System.out.println(".");System.out.println(".");System.out.println(".");
                        System.out.println(".");System.out.println(".");System.out.println(".");
                %>
                <div class="alert alert-danger" style="display:<%=claseCss%>; font-weight: bolder;">
                    <p><%= rptaError %></p>
                </div>
                
                <div class="obj-group">
                    <input type="text" value="<%= usuarioDato %>" name="txtU" placeholder="User">
                </div>
                <div class="obj-group">
                    <input type="password" value="<%= passwordDato %>" name="txtP" placeholder="Password">
                </div>
                <div class="obj-group">
                    <input type="submit" name="accion" value="Ingresar">
                </div>
            </form>
        <!--</div>-->
        
    </body>
</html>