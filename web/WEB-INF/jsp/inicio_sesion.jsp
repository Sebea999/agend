<%-- 
    Document   : inicio_sesion
    Created on : 10-may-2021, 13:00:25
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanPerfil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <%@include file="menuJspIconStatic.jsp" %> <%-- // INCLUYO LOS LINKS DE LOS ICONOS --%>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <!--<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/styleIniSes.css">
<!--        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/styleIniSesv2.css">-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
        <style>
/*            body {
                background: #75FF59;
                background: linear-gradient(to right, #57CF3F, #51FF6E);
               /* background: #ffe259;
                background: linear-gradient(to right, #ffa751, #ffe259);*/
/*            }
            .bg {
                background-image: url(${pageContext.request.contextPath}/recursos/img/random.jpg);
                background-position: center center;
                background-size: cover;
            }*/
        </style>
    </head>
    <body class="/*backIS*/">
                <%
                    System.out.println("[.]");System.out.println("[.]");System.out.println("[.]");
                    System.out.println("[+]-------------------------------___JSP_______INICIO_SESION_________------------------------");
                    HttpSession sesionDatosUsuario = request.getSession();
                    String claseCss="none", rptaError="";
                    String usuarioDato="", passwordDato="";
                    rptaError = (String)request.getAttribute("respuesta");
                    String tipo_mensaje = (String)request.getAttribute("TIPO_MENSAJE");
                    String css_panel_msn="alert alert-danger";
                    
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
                        if (tipo_mensaje == null || tipo_mensaje.equals("2") || tipo_mensaje.isEmpty()) {
                            css_panel_msn = "alert alert-danger";
                        } else if (tipo_mensaje.equals("1")) {
                            css_panel_msn = "alert alert-success";
                        } else if (tipo_mensaje.equals("3")) {
                            css_panel_msn = "alert alert-warning";
                        }
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
        
<!--        <section>
            <div class="form-container">-->
            <%
            if ((rptaError == null) == false) {
            %>
<!--                <div class="alert alert-danger" style="display:<%=claseCss%>; font-weight: bolder;">
                    <p><%= rptaError %></p>
                </div>-->
            <%
            }
            %>
<!--                <h1>login</h1>
                <form action="ISSrv" method="post" autocomplete="off">
                    <div class="control">
                        <label for="name">Usuario:</label>
                        <input type="text" value="<%= usuarioDato %>" name="txtU" placeholder="...">
                    </div>
                    <div class="control ctrlMiddle">
                        <label for="name">Clave:</label>
                        <input type="password" value="<%= passwordDato %>" name="txtP" placeholder="...">
                    </div>
                    <div class="control">
                        <input type="submit" name="accion" value="Ingresar">
                    </div>
                </form>
            </div>
        </section>-->
        
        
        
        
        
        
        
        
<div class="container w-75 bg-primary mt-5 rounded shadow" style="height: 80vh">
    <div class="row align-items-lg-stretch">
        <div class="col bg d-none d-lg-block col-md-5 col-lg-5 col-xl-6 rounded" style="height: 80vh">
            
        </div>
        <div class="col bg-white p-5 rounded-end" style="height: 80vh">
<!--            <div clas="text-end">
                <%// <img src="../../recursos/img/avatar1.jpg" width="48" alt=""> %>
                <img src="${pageContext.request.contextPath}/recursos/img/avatar1.jpg" width="48" alt="">
            </div>-->
            
            <%
            if ((rptaError == null) == false) {
            %>
                <div class="<%= css_panel_msn %>" style="display:<%=claseCss%>; font-weight: bolder;">
                    <p><%= rptaError %></p>
                </div>
            <%
            }
            %>
            
            <h2 class="fw-bold text-center py-2">Bienvenido</h2>
            
            <!-- LOGIN -->
            <form action="ISSrv" method="post" autocomplete="off">
                <div class="mb-4">
                    <label for="email" class="form-label">Correo electrónico</label>
                    <input type="text" value="<%= usuarioDato %>" class="form-control" name="txtU">
                </div>
                <div class="mb-4">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" value="<%= passwordDato %>" class="form-control" name="txtP">
                </div>
<!--                <div class="mb-4 form-check">
                    <input type="checkbox" name="connected" class="form-check-input">
                    <label for="connected" class="form-check-label">Mantenerme conectado</label>
                </div>-->
                
                <div class="d-grid">
                    <button type="submit" value="Ingresar" name="accion" class="btn btn-primary">Iniciar Sesión</button>
                </div>
                
                <div class="my-3">
                    <!--<span>No tienes cuenta? <a href="#">Regístrate</a></span><br>-->
                    <span>No tienes cuenta? <a href="/odonto/registrar.htm">Regístrate</a></span><br>
                    <span><a href="/odonto/recover_pass.htm">Recuperar Password</a></span>
                </div>
            </form>
            
            <!-- LOGIN CON REDES SOCIALES  -->
<!--            <div class="container w-100 my-5">
                <div class="row text-center">
                    <div class="col-12">Iniciar Sesión</div>
                </div>
                <div class="row">
                    <div class="col">
                        <button class="btn btn-outline-primary w-100 my-1">
                            <div class="row align-items-center">
                                <div class="col-2 d-none d-md-block">
                                    <img src="${pageContext.request.contextPath}/recursos/img/face.png" width="32" alt="">
                                </div>

                                <div class="col-12 col-md-10 text-center">
                                    Facebook
                                </div>
                            </div>
                        </button>
                    </div>      
                    <div class="col">
                        <button clas="btn btn-outline-danger w-100 my-1">
                            <div class="row align-items-center">
                                <div class="col-2 d-none d-md-block">
                                    <img src="${pageContext.request.contextPath}/recursos/img/face.png" width="32" alt="">
                                </div>

                                <div class="col-12 col-md-10 text-center">
                                    Google
                                </div>
                            </div>
                        </button>
                    </div>
                </div>
            </div>-->

        </div>
    </div>
</div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>