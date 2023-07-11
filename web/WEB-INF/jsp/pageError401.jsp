<%-- 
    Document   : pageError401
    Created on : 10-nov-2021, 9:50:38
    Author     : RYUU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Error 401</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                
//                    System.out.println(".");System.out.println(".");System.out.println(".");
//                    System.out.println("-------------------------------___JSP_______INICIO_SESION_________------------------------");
//                    HttpSession sesionDatosUsuario = request.getSession();
//                    String claseCss="none", rptaError="";
//                    String usuarioDato="", passwordDato="";
//                    rptaError = (String)request.getAttribute("respuesta");
//                    
//                    if (rptaError != null) {
//                        usuarioDato = (String)request.getAttribute("usuarioDato");
//                        if (usuarioDato == null) {
//                            usuarioDato = "";
//                        }
//                        passwordDato = (String)request.getAttribute("passwordDato");
//                        if (passwordDato == null) {
//                            passwordDato = "";
//                        }
//                        claseCss="block";
//                    }
//                    
//                    
//                    // INSTANCIO EL CONTROLADOR DE INICIO SESION PARA PODER CONTROLAR LA VARIABLE GLOBAL EN CASO DE QUE SE HAYA ACTIVADO LA VARIABLE GLOBAL DE LA VALIDACION DE ESTADO_MIGRAR 
//                        controlador.ControllerInicioSesion iniSeController = new controlador.ControllerInicioSesion();
//                        if (iniSeController.varValidacionesEstado == 1) { // CONTROLO LA BANDERA SI ES QUE SE CAMBIO SE SU VALOR PARA SABER SI LE MUESTRO EL MENSAJE EN CASO DE QUE SE ESTE MIGRANDO 
//                            System.out.println("....__IF__  VARIABLE GLOBAL varValidacionesEstado = 1 ___........");
//                            rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
//                            claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
//                            iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  
//                            
//                        } else {
//                            System.out.println("....__ELSE__  VARIABLE GLOBAL varValidacionesEstado DIFERENTE A 1 ___........");
//                            // CONTROLAR SI ES QUE HAY ALGUN USUARIO ACTIVO PARA REDIRECCIONAR AL MENU 
//                            modelo.ModelInicioSesion metodosIniSes = new modelo.ModelInicioSesion();
//                            controlador.ControllerInicioSesion inicioSesController = new controlador.ControllerInicioSesion();
//                            String paginaJsp = "inicioSesion.jsp";
//                            
//                            if (iniSeController.varValidacionesEstado == 2) { // SI ES IGUAL A 2 ES PORQUE YA INGRESO YA Y NO CUENTA CON IDALUMNO PERO COMO YO AL REDIRECCIONAR MAS ABAJO AL INICIO DE SESION ESTE ENTRA EN UN BUCLE PORQUE VUELVA A CONTROLAR Y LE VUELVO A REDIRIGIR A LA MISMA PAGINA Y VUELVE A HACER LOS MISMOS CONTROLES Y ASI SE QUEDA ENTRANDO EN UN BUCLE QUE CAUSA ERROR, ENTONCES PARA EVITAR ESO, CONTROLO PRIMERAMENTE SI ES QUE LA VARIABLE NO ESTA YA EN 2 QUE SERIA CUANDO SE HAYA VERIFICADO PARA NO VOLVER A CONTROLAR TODOS LOS DEMAS PASOS 
//                                System.out.println(".       __IF__      varValidacionesEstado = 2_____");
////                                rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
////                                claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
//                                iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  
//                                
//                            } else {
//                                System.out.println(".       __ELSE__    varValidacionesEstado DIFERENTE A 2____");
////                                inicioSesController.VAR_GET_IDDOCENTE = (String) sesionDatosUsuario.getAttribute("idDocente");
////                                System.out.println(".          _IDALUMNO_VAR_GLOBAL:        "+inicioSesController.VAR_GET_IDDOCENTE);
//                                String IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
////                                String IDPERSONA = inicioSesController.VAR_GET_IDDOCENTE;
//                                System.out.println(".       __JSP___ ID_PERSONA_____    "+IDPERSONA+"    _______");
//                                String idUsuario = (String) sesionDatosUsuario.getAttribute("IDUSUARIO");
//                                System.out.println(".       __JSP___ ID_USUARIO:        "+idUsuario);
//                                sesionDatosUsuario.setAttribute("IDPERSONA", IDPERSONA);
//                                
//                                if (IDPERSONA == null || IDPERSONA.isEmpty() || IDPERSONA.equals("")) {
//                                    System.out.println("_IS:JSP___IF___ID_PERSONA_IS_NULL____");
////                                    System.out.println("_*__if___controlLogeoUsuarioActivo()___");
////                                    inicioSesController.varValidacionesEstado = 2; // OBSERVACION: POR EL MOMENTO NO HAGO NADA CON ESTE VALOR, QUE ES DIFERENTE AL DE LA VALIDACION DE MIGRAR(1), NO LE CARGO EL MISMO VALOR "1", PARA QUE NO SE ACTIVE EL IF QUE CONTROLA ESTA VARIABLE CON EL VALOR UNO Y EL METODO 
////
////                                    paginaJsp = "inicioSesion.jsp";
////                                    rptaError = iniSeController.varValidacionMensaje; // CARGO LA VARIABLE DEL MENSAJE CON EL VALOR DE LA VARIABLE GLOBAL QUE GUARDA EL MENSAJE QUE CARGO DESDE EL CONTROLADOR 
////                                    claseCss = "block"; // CAMBIO EL VALOR PARA MOSTRAR EL PANEL DONDE SE MUESTRA EL MENSAJE 
//////                                    iniSeController.varValidacionesEstado = 0; // LE RESTABLESCO SU VALOR PARA EVITAR QUE SE MANTENGA EL ULTIMO VALOR  
//
//                                } else { // SI EL IDALUMNO ESTA CARGADO ES PORQUE LA SESION NO SE HA CERRADO Y EL ALUMNO MANTIENE TODAVIA SU SESION, ENTONCES LE REDIRECCIONO A UNO DE LOS DOS MENU (ADMINISTRATIVO - ACADEMICO) 
//                                    System.out.println("_IS:JSP___ELSE___ID_PERSONA_IS_NULL____");
//                                    BeanPerfil beanPerfil = new BeanPerfil();
//                                    beanPerfil = metodosIniSes.traerDatosPerfil(idUsuario, beanPerfil);
//                                    String idPerfilUsuario = beanPerfil.getSP_IDPERFIL();
//                                    System.out.println(".       _JSP__idPerfil:       :"+idPerfilUsuario);
//                                    String descPerfilUsuario = beanPerfil.getSP_NOMBRE();
//                                    System.out.println(".       _JSP__descPerfil:     :"+descPerfilUsuario);
//                                    String idMenuPerfil = beanPerfil.getSP_IDMENU();
//                                    System.out.println(".       _JSP__idMenuPerfil:   :"+idMenuPerfil);
//                                    
//                                    if(idMenuPerfil.equals("0") || idMenuPerfil.equals("1")){ // CONTROLO EL CAMPO IDMENU QUE RECUPERO DE LA TABLA DE PERFIL QUE ME DIRA QUE MENU CORRESPONDE AL PERFIL DEL USUARIO, SI FUESE DIFERENTE A CERO (ROOT) Y 1 (ADMINISTRADOR) ENTONCES REDIRECCIONO AL USUARIO AL MENU DE CLIENTES PERO SI FUESE 0 Y 1 ENTONCES LO REDIRIJO AL MENU DE ADMINISTRATIVO 
//                                        paginaJsp = "menu_principal.jsp";
////                                        paginaJsp = "menu_adm.jsp"; // MENU ADMINISTRATIVO PARA LOS FUNCIONARIOS / ADMINISTRADORES 
//                                    } else {
//                                        paginaJsp = "menu_principal.jsp"; // MENU PARA LOS USUARIO / CLIENTES 
//                                    }
//                                    System.out.println(".    _REDIRECCIONAR:      "+paginaJsp);
//                                    System.out.println(".    _---------_REDIRECCIONAR_MENUS_LOGEO_ACTIVO_--------_");
//                                    RequestDispatcher vista = request.getRequestDispatcher(paginaJsp);
//                                    vista.forward(request, response);
//                                }
//                            }
//                            System.out.println(".    __2__REDIRECCIONAR:      "+paginaJsp);
//                        } // END ELSE IF CONTROL MIGRADO VALIDATION 
//                        System.out.println(".");System.out.println(".");System.out.println(".");
//                        System.out.println(".");System.out.println(".");System.out.println(".");
                %>
                <h1 class="FEP">401</h1>
                <h3>La solicitud requiere autenticación de usuario. La respuesta DEBE incluir un WWW: campo de encabezado de autenticación que contiene un desafío aplicable al recurso solicitado.</h3>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>