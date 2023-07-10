
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.modelo.ModelRef_Clinica"%>
<%@page import="com.agend.modelo.ModelPerfil"%>
<%@page import="com.agend.modelo.ModelInicioSesion"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" session="false"%>--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>MENU</title>
        <!--<meta charset="UTF-8">-->
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
        <!--<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">-->
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <!--<link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>-->
        <!--<link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">-->
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">-->
    </head>
    <body>
        <%
            ModelInicioSesion metodosIniSes = new ModelInicioSesion();
            ModelPerfil metodosPerfil = new ModelPerfil();
            ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
            
            String urlPrincipal = "menu.htm";
            String urlReferenciales = "referenciales.htm";
            String urlUsuario = "usuario.htm";
            String urlMedico = "medico.htm";
            String urlSecretario = "secret.htm";
            String urlPaciente = "paciente.htm";
            String urlAgendamiento = "agend.htm";
            String urlFichaAtencionPaciente = "atencion.htm";
            String urlPlanHorario = "plan_hor.htm";
            String urlCtaCliente = "cuenta_cliente.htm";
            String urlFactura = "factura.htm";
            String urlReportes = "reportes.htm";
            String urlPanelControl = "panel_control.htm";
            String urlManualUsuario = "manualUsuario.htm";
            String urlPerfil = "perfil_pac.htm";
            
            HttpSession sesionDatosUsuario = request.getSession(false);
            
            String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            String userName = (String)sesionDatosUsuario.getAttribute("RP_APELLIDO");
//            String userName = (String)sesionDatosUsuario.getAttribute("RP_APELLIDO")+" "+(String)sesionDatosUsuario.getAttribute("RP_NOMBRE");
            String idPerfil = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
            String descPerfil = (String) sesionDatosUsuario.getAttribute("DESC_PERFIL");
            String idClinica = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");
            String DESC_CLINICA = (String) sesionDatosUsuario.getAttribute("DESC_CLINICA_USER");
            if (DESC_CLINICA == null || DESC_CLINICA.isEmpty()) {
                System.out.println("_   ____JSP__MENU_______DESC_CLINICA_NULL______");
                DESC_CLINICA = metodosRefClinica.traerDescClinica(idClinica);
            }
            
            System.out.println("---   ------ MENU PRINCIPAL --------   ---");
            System.out.println("__IDPERSONA_SESION:     "+idPersona);
            System.out.println("__USER_NAME:            "+userName);
            System.out.println("__ID_PERFIL:            "+idPerfil);
            System.out.println("__DESC_PERFIL:          "+descPerfil);
            System.out.println("__ID_CLINICA:          "+idClinica);
            System.out.println("__DESC_CLINICA:        "+DESC_CLINICA);
            System.out.println("------------------------------------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_   _JSP_MP____GET_SERVLET_CONTEXT_SESION:   :"+sesionDatosUsuario.getServletContext());
            System.out.println("_   _JSP_MP____GET_REQUEST_SESSION_ID:   :"+request.getRequestedSessionId());
            System.out.println("_   _JSP_MP____GET__SESSION_ID:   :"+sesionDatosUsuario.getId());
            System.out.println("_   _JSP_MP____GET_REMOTE_USER:   :"+request.getRemoteUser());
            System.out.println(".");
            System.out.println(".");
            System.out.println("[+]__JSP_________MENU_PRINCIPAL________");
//            BeanPersona beanUserLogin = (BeanPersona) sesionDatosUsuario.getAttribute("BEANPERSONA_USERLOGIN");
//            System.out.println(".___USER-LOGIN____ID_PERSONA:  :"+beanUserLogin.getRP_IDPERSONA());
//            System.out.println(".___USER-LOGIN____APELLIDO_P:  :"+beanUserLogin.getRP_APELLIDO());
            System.out.println("[*]___USER-LOGIN____ID_USER_LOGIN:  :"+String.valueOf(sesionDatosUsuario.getAttribute("IDUSUARIO_USERLOGIN")));
        %>
        <input type="checkbox" checked="checked" id="nav-toggle">
        <div class="sidebar">
            <div class="sidebar-brand">
                <%
                String empresa = metodosIniSes.devolverEmpresa();
                System.out.println("___EMPRESA:     :"+empresa);
                %>
                <h2><span class="lab la-accusoft"></span> <span><%= empresa %></span></h2>
            </div>
            
            <div class="sidebar-menu">
                <ul>
                <%
                /*
                * OBSERVACION :
                    SI EL IDPERFIL ES IGUAL A 1 "ADMINISTRADOR" ENTONCES SI LE MOSTRARIA LA ETIQUETA 
                */
//                if(idPerfil.equals("1") == true) {
                if(metodosPerfil.isMenuReferenciales(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlReferenciales %>"><span class="las la-comment"></span>
                        <span>Referenciales</span></a>
                    </li>
                <%
                }
                %>
                <%
                /*
                * OBSERVACION :
                    SI EL IDPERFIL ES IGUAL A 1 "ADMINISTRADOR" ENTONCES SI LE MOSTRARIA LA ETIQUETA PARA QUE PUEDA INGRESAR A LA PAGINA DE USUARIOS 
                */
//                if(idPerfil.equals("1") == true) {
                if(metodosPerfil.isMenuUsuario(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlUsuario %>"><span class="las la-users"></span>
                        <span>Usuario</span></a>
                    </li>
                <%
                }
                %>
                <%
                /*
                * OBSERVACION :
                    SI EL IDPERFIL ES IGUAL A 1 "ADMINISTRADOR" ENTONCES SI LE MOSTRARIA LA ETIQUETA 
                */
//                if(idPerfil.equals("1") == true || idPerfil.equals("2")) { // IDPERFIL_MEDICO = 2 
                if(metodosPerfil.isMenuMedico(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlMedico %>"><span class="las la-users"></span>
                        <span>Médico</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuSecretario(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlSecretario %>"><span class="las la-users"></span>
                        <span>Secretario/a</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuPaciente(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlPaciente %>"><span class="las la-users"></span>
                        <span>Paciente</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuPerfil(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlPerfil %>"><span class="las la-users"></span>
                        <span>Perfil</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuCuentaCliente(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlCtaCliente %>"><span class="las la-clipboard-list"></span>
                        <span>Cuenta Cliente</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuPlanHorario(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlPlanHorario %>"><span class="las la-clipboard-list"></span>
                        <span>Plan de Horario</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuAgendamiento(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlAgendamiento %>"><span class="las la-clipboard-list"></span>
                        <span>Agendamiento</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuAtencionFichaPaciente(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlFichaAtencionPaciente %>"><span class="las la-clipboard-list"></span>
                        <span>Atención</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuFactura(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlFactura %>"><span class="las la-receipt"></span>
                        <span>Factura</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuReportes(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlReportes %>"><span class="las la-clipboard-list"></span>
                        <span>Reportes</span></a>
                    </li>
                <%
                }
                %>
                <%
                if(metodosPerfil.isMenuPanelDeControl(idPerfil) == true) {
                %>
                    <li>
                        <a href="<%= urlPanelControl %>"><span class="las la-user-circle"></span>
                        <span>Panel de Control</span></a>
                    </li>
                <%
                }
                %>
                </ul>
            </div>
        </div>
        
        
        <div class="main-content">
            <header>
                <h2>
                    <label for="nav-toggle">
                        <span class="las la-bars"></span>
                    </label>
                    Menú Principal
                </h2>
                
<%//<!--                <div class="search-wrapper">
//                    <span class="las la-search"></span>
//                    <input type="search" placeholder="Search here" />
//                </div>-->%>
                
                <div class="user-wrapper">
                    <%//<img src="${pageContext.request.contextPath}/recursos/img/avatar.png" width="40px" height="40px" alt="">%>
                    <div class="img-user"></div>
                    <div>
                        <h4><%= userName %></h4>
                        <small><%= descPerfil %></small>
                        <small>
                            <form action="ISSrv" method="post">
                                <input type="submit" value="Cerrar Sesion" name="accion" class="btnCerSes dropdown-item" />
                            </form>
                        </small>
                    </div>
                </div>
            </header>
            
            
            <main>
                <div class="cards">
                <%
                if(metodosPerfil.isMenuReferenciales(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReferenciales %>">
                            <div>
                                <h1>Referenciales</h1>
                                <%//<!--<span>Referenciales</span>-->%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                
                <%
                if(metodosPerfil.isMenuUsuario(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlUsuario %>">
                            <div>
                                <h1>Usuario</h1>
                                <%//<!--<span>Usuario</span>-->%>
                            </div>
                            <div>
                                <span class="las la-users"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuMedico(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlMedico %>">
                            <div>
                                <h1>Médico</h1>
                                <%//<!--<span>Usuario</span>-->%>
                            </div>
                            <div>
                                <span class="las la-users"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuSecretario(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlSecretario %>">
                            <div>
                                <h1>Secretario/a</h1>
                                <%//<!--<span>Usuario</span>-->%>
                            </div>
                            <div>
                                <span class="las la-users"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuPaciente(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlPaciente %>">
                            <div>
                                <h1>Paciente</h1>
                                <%//<!--<span>Usuario</span>-->%>
                            </div>
                            <div>
                                <span class="las la-users"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuPerfil(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlPerfil %>">
                            <div>
                                <h1>Perfil</h1>
                                <%//<!--<span>Cuenta Cliente</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuCuentaCliente(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlCtaCliente %>">
                            <div>
                                <h1>Cuenta Cliente</h1>
                                <%//<!--<span>Cuenta Cliente</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuPlanHorario(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlPlanHorario %>">
                            <div>
                                <h1>Plan de Horario</h1>
                                <%//<!--<span>Plan de Horario</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuAgendamiento(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlAgendamiento %>">
                            <div>
                                <h1>Agendamiento</h1>
                                <%//<!--<span>Agendamiento</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuAtencionFichaPaciente(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlFichaAtencionPaciente %>">
                            <div>
                                <h1>Atención</h1>
                                <%//<!--<span>Atención</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuFactura(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlFactura %>">
                            <div>
                                <h1>Factura</h1>
                                <%//<!--<span>Factura</span>-->%>
                            </div>
                            <div>
                                <span class="las la-receipt"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuReportes(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReportes %>">
                            <div>
                                <h1>Reportes</h1>
                                <%//<!--<span>Reportes</span>-->%>
                            </div>
                            <div>
                                <span class="las la-clipboard-list"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuManualDeUsuario(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlManualUsuario %>">
                            <div>
                                <h1>Manual de Usuario</h1>
                                <%//<!--<span>Configuración</span>-->%>
                            </div>
                            <div>
                                <span class="las la-user-circle"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                    
                <%
                if(metodosPerfil.isMenuPanelDeControl(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlPanelControl %>">
                            <div>
                                <h1>Panel de Control</h1>
                                <%//<!--<span>Configuración</span>-->%>
                            </div>
                            <div>
                                <span class="las la-user-circle"></span>
                            </div>
                        </a>
                    </div>
                <%
                }
                %>
                </div>
                <div class="cards mt-5 lblMPDC">
                    <p class="mr-1 ml-4 fw500">Clínica:</p><%= DESC_CLINICA %>
                </div>
            </main>
        </div>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>