<%-- 
    Document   : menu_jsp
    Created on : 16-jun-2021, 12:11:26
    Author     : RYUU
--%>

<%@page import="com.agend.modelo.*"%><%--
<%--<%@page import="modelo.ModelPlanHorario"%>
<%@page import="modelo.ModelRef_Clinica"%>
<%@page import="modelo.ModelSecretario"%>
<%@page import="modelo.ModelMedico"%>
<%@page import="modelo.ModelUsuario"%>--%>
<%@page import="com.agend.controlador.ControllerInicioSesion"%>
<%--<%@page import="modelo.ModelCuentaCliente"%>
<%@page import="modelo.ModelFactura"%>
<%@page import="modelo.ModelInicioSesion"%>
<%@page import="modelo.ModelPersona"%>--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%--<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>--%>
<%//<!DOCTYPE html>%>
<%
    /*
    OBSERVACION:
        COMENTO ESTO PORQUE AL INCLUIR ESTE ARCHIVO SE VA DUPLICAR LOS CODIGOS PORQUE EL OTRO JSP VA A TENER ESTO COMO ESTRUCTURA BASICA 
        APARTE DE QUE ENTRE UN JSP Y OTRO PUEDEN HABER VARIAS COSAS QUE SE INCLUYAN Y TAL VEZ PARA UNO SEA NECESARIO Y PARA OTRO NO, 
        ENTONCES POR ESO MANTENGO LA ESTRUCTURA BASICA EN LOS OTROS JSP QUE IMPLEMENTAN ESTE MENU (QUE TODOS DEBERIAN DE TENER) 
    
        DEJO ESTE CODIGO COMENTADO COMO RECORDATORIO DE LO MINIMO QUE DEBERIA DE TENER CADA JSP QUE INCLUYA ESTE MENU 
        ES NECESARIO IMPLEMENTAR LOS DOS CSS, PARA DARLE FORMATO Y MOSTRAR LOS ICONOS 
    */
//<!--<html>
//    <head>
//        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
//        <title>....</title>
//        <meta charset="UTF-8">
//        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
//        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
//        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
//        
//        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
//        
//        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">
//    </head>
//    <body>-->
%>

<%
            ModelInicioSesion metodosIniSes = new ModelInicioSesion();
            ModelPersona metodosPersona = new ModelPersona();
            ModelMedico metodosMedico = new ModelMedico();
            ModelSecretario metodosSecretario = new ModelSecretario();
            ModelUsuario metodosUsuario = new ModelUsuario();
            ModelFactura metodosFactura = new ModelFactura();
            ModelCuentaCliente metodosCtaCliente = new ModelCuentaCliente();
            ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
            ModelRef_Especialidad metodosRefEspecialidad = new ModelRef_Especialidad();
            ModelPlanHorario metodosPlanHorario = new ModelPlanHorario();
            ModelAgendamiento metodosAgendamiento = new ModelAgendamiento();
            ModelFichaAtencionPac metodosFichaAtencionPac = new ModelFichaAtencionPac();
            ModelFichaAtencionPacNutri metodosFichaAtencionPacNutri = new ModelFichaAtencionPacNutri();
            ModelRef_Servicio metodosRefServicio = new ModelRef_Servicio();
            ModelRef_Ciudad metodosRefCiudad = new ModelRef_Ciudad();
            ModelConfigAgend metodosConfigAgend = new ModelConfigAgend();
            ModelPerfil metodosPerfil = new ModelPerfil();
            ControllerInicioSesion controllerIniSes = new ControllerInicioSesion();
            
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
            String urlPerfil = "perfil_pac.htm";
            
            // REFERENCIALES: 
            String urlRefClinica = "ref_clinica.htm";
            String urlRefServicios = "ref_servicios.htm";
            String urlRefEspecialidad = "ref_esp.htm";
            String urlRefBarrio = "ref_barrio.htm";
            String urlRefCiudad = "ref_ciudad.htm";
            String urlRefPais = "ref_pais.htm";
            String urlRefReligion = "ref_religion.htm";
            String urlRefEstadoCivil = "ref_estado_civil.htm";
            
            // REPORTES: 
            String urlReportePaciente = "rpt_paciente.htm";
            String urlReporteAjusteStock = "rpt_ajuste_stock.htm";
            String urlReporteStock = "rpt_stock.htm";
            String urlReporteEmpenho = "rpt_empenho.htm";
            String urlReporteCuentaCliente = "rpt_cta_paciente.htm";
            String urlReporteFactura = "rpt_factura.htm";
            String urlReporteMisAgend = "rpt_mis_agen.htm";
            String urlReporteEstadistica = "rpt_est.htm";
            
            // PANEL DE CONTROL: 
                // CAJA     --
            String urlConfigAgendamiento = "config_agend.htm";
            String urlAnularAgendamientos = "anular_agend.htm";
            String urlAnularFacturas = "anular_fact.htm";
                // EMPEÑO   --
            String urlResumenEmpenho = "emp_resumen.htm";
            String urlAnularEmpenho = "emp_anular.htm";
            String urlEmpVigentes = "emp_vigentes.htm";
            String urlEmpPorVencer = "emp_por_vencer.htm";
            String urlEmpVencidos = "emp_vencidos.htm";
            String urlEmpExpirados = "emp_expirados.htm";
            String urlEmpLiquidados = "emp_liquidados.htm";
                // INVENTARIO -- 
            String urlPrendasEmpeñadas = "prendas_emp.htm";
            String urlPrendasEnVenta = "prendas_venta.htm";
            
            HttpSession sesionDatosUsuario = request.getSession(false);
//            HttpSession sesionDatosUsuario = request.getSession();
            String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            if (idPersona == null) {
                System.out.println("[-] id-persona is null");
//                idPersona = metodosIniSes.getCookie(request, "IDPERSONA").getValue();
//                System.out.println("[*] id-persona the cookie is value = "+idPersona);
            } else {
                System.out.println("[+] id-persona is not null - it value is :"+idPersona);
            }
            String userName = (String)sesionDatosUsuario.getAttribute("RP_APELLIDO");
            if (userName == null) {
                System.out.println("[-] user-name is null");
//                userName = metodosIniSes.getCookie(request, "RP_APELLIDO").getValue();
//                System.out.println("[*] user-name the cookie is value = "+userName);
            } else {
                System.out.println("[+] user-name is not null - it value is :"+userName);
            }
//            String userName = (String)sesionDatosUsuario.getAttribute("RP_APELLIDO")+" "+(String)sesionDatosUsuario.getAttribute("RP_NOMBRE");
            String idPerfil = (String) sesionDatosUsuario.getAttribute("IDPERFIL");
            if (idPerfil == null) {
                System.out.println("[-] id-perfil is null");
//                idPerfil = metodosIniSes.getCookie(request, "IDPERFIL").getValue();
//                System.out.println("[*] id-perfil the cookie is value = "+idPerfil);
            } else {
                System.out.println("[+] id-perfil is not null - it value is :"+idPerfil);
            }
            String descPerfil = (String) sesionDatosUsuario.getAttribute("DESC_PERFIL");
            if (descPerfil == null) {
                System.out.println("[-] desc-perfil is null");
//                descPerfil = metodosIniSes.getCookie(request, "DESC_PERFIL").getValue();
//                System.out.println("[*] desc-perfil the cookie is value = "+descPerfil);
            } else {
                System.out.println("[+] desc-perfil is not null - it value is :"+descPerfil);
            }
            String idClinica = (String) sesionDatosUsuario.getAttribute("ID_CLINICA_USER");
            if (idClinica == null) {
                System.out.println("[-] id-clinica is null");
//                idClinica = metodosIniSes.getCookie(request, "ID_CLINICA_USER").getValue();
//                System.out.println("[*] id-clinica the cookie is value = "+idClinica);
            } else {
                System.out.println("[+] id-clinica is not null - it value is :"+idClinica);
            }
            
            System.out.println("--    ----------___MENU_JSP___-----------     --");
            System.out.println("[*]__IDPERSONA_SESION:  :"+idPersona);
            System.out.println("[*]__USER_NAME:         :"+userName);
            System.out.println("[*]__ID_PERFIL:         :"+idPerfil);
            System.out.println("[*]__DESC_PERFIL:       :"+descPerfil);
            System.out.println("[*]__ID_CLINICA:        :"+idClinica);
            System.out.println("------------------------------------------------");
            
//            // CODIGO PARA CONTROLAR SI EL USUARIO SIGUE ACTIVO EN VEZ DE IR CONSULTANDO LA BASE DE DATOS 
//            if (metodosIniSes.controlLogeoUsuarioActivo(request) == false) { // EN ESTE IF CONTROLO SI ES QUE LA SESION SE ENCUENTRA CERRADA PARA REDIRECCIONAR A QUE SE LOGEE 
//                System.out.println(".");
//                System.out.println("...............___JSP___...............");
//                System.out.println("- IF -- USUARIO INACTIVO / CERRADO -- -");
//                System.out.println(".......................................");
//                System.out.println(".");
//                System.out.println("_*__if___controlLogeoUsuarioActivo()___");
//                controllerIniSes.varValidacionesEstado = 2; // OBSERVACION: POR EL MOMENTO NO HAGO NADA CON ESTE VALOR, QUE ES DIFERENTE AL DE LA VALIDACION DE MIGRAR(1), NO LE CARGO EL MISMO VALOR "1", PARA QUE NO SE ACTIVE EL IF QUE CONTROLA ESTA VARIABLE CON EL VALOR UNO Y EL METODO 
//                controllerIniSes.varValidacionMensaje =  controllerIniSes.varLogeoInactivoMensajeError; // NO ES NECESARIO MOSTRAR UN MENSAJE LUEGO DE SABER QUE LA SESION ESTA CERRADA 
//                // REDIRECCIONO A LA PAGINA DE INICIAR SESION PARA QUE VUELVA A LOGEARSE 
//                RequestDispatcher vista = request.getRequestDispatcher("inicio_sesion.jsp");
//                vista.forward(request, response);
//            }
            
            
            /*
            _OBSERVACION: UTILIZO ESTA VARIABLE PARA PODER SABER SI LE MUESTRO CERRADO O LE MUESTRO ABIERTO LA BARRA LATERAL IZQUIERDA DEL MENU AL USUARIO, PORQUE EN ALGUNAS PAGINAS COMO LAS PRINCIPALES CREO QUE ES BUENO MOSTRAR (ABRIR) Y EN LAS SUBPAGINAS DE LAS PRINCIPALES CREO QUE NO ES NECESARIO MOSTRARLAS (CERRAR) PORQUE EL USUARIO TIENE OTRO ENFOQUE 
            _CONTROLO LA VARIABLE CON VALORES BINARIOS:  0 = MOSTRAR O ABRIR LA BARRA LATERAL DEL MENU.  
                                                     /   1 = NO MOSTRAR O MOSTRAR CERRADA LA BARRA LATERAL DEL MENU...
            */
            String mostrarBarraMenu = (String) sesionDatosUsuario.getAttribute("JSP_MOSTRAR_BARRA_MENU");
            if (mostrarBarraMenu == null) {
                System.out.println("[-] mostrar-barra-menu is null");
//                mostrarBarraMenu = metodosIniSes.getCookie(request, "JSP_MOSTRAR_BARRA_MENU").getValue();
//                System.out.println("[*] mostrar-barra-menu the cookie is value = "+mostrarBarraMenu);
            } else {
                System.out.println("[+] mostrar-barra-menu is not null - it value is :"+mostrarBarraMenu);
            }
            String mostrar; // VARIABLE PARA CARGAR A LA ETIQUETA DEL CHECK 
            if (mostrarBarraMenu.equalsIgnoreCase("1")) { // NO LE MUESTRO ABIERTA O LA MANTENGO CERRADA LA BARRA DEL MENU 
                System.out.println("__    _IF_  checked  __");
                mostrar = "checked=\"checked\"";
            } else { // SI NO ES 1 ENTONCES DEBERIA DE SER 0, O EN TODO CASO VACIO SI NO CARGO LA VARIABLE Y LE MOSTRARIA ABIERTA LA BARRA LATERAL DEL MENU 
                System.out.println("__    else  not_check  __");
                mostrar = "";
            }
        %>
        <input type="checkbox" <%=mostrar%> id="nav-toggle">
        <%//<input type="checkbox" checked="checked" id="nav-toggle">%>
        <div class="sidebar">
            <div class="sidebar-brand">
                <%
                String empresa = metodosIniSes.devolverEmpresa();
                System.out.println("[*]__JSP_MENU_______EMPRESA:     :"+empresa);
                %>
                <h2><span class="lab la-accusoft"></span> <span><%= empresa %></span></h2>
            </div>
            
            <div class="sidebar-menu">
                <ul>
                <%
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
//                if(idPerfil.equals("1") == true || idPerfil.equals("3")) { // IDPERFIL_SECRETARIO = 3 
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
//                if(idPerfil.equals("1") == true || idPerfil.equals("3") || idPerfil.equals("4")) { // IDPERFIL_PACIENTE = 4 / IDPERFIL_SECRETARIO = 3 
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
//                if(idPerfil.equals("1") == true || idPerfil.equals("3") || idPerfil.equals("4")) { // IDPERFIL_PACIENTE = 4 / IDPERFIL_SECRETARIO = 3 
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
//                if(idPerfil.equals("1") == true || idPerfil.equals("3") || idPerfil.equals("4")) { // IDPERFIL_PACIENTE = 4 / IDPERFIL_SECRETARIO = 3 
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
        
        <div class="main-content"> <% // DIV QUE CIERRO DENTRO DEL JSP QUE IMPLEMENTE EL MENU, ESTE DIV ME SIRVE PARA COLOCAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE Y QUE NO SE SOBREPONGA SOBRE OTRO ELEMENTE DEL MENU O OTRO DIV %>
            <header>
                <h2>
                    <label for="nav-toggle">
                        <span class="las la-bars"></span>
                    </label>
                    
                    <a href="<%= urlPrincipal %>">Menú Principal</a>
                </h2>
                
<%//<!--                <div class="search-wrapper">
//                    <span class="las la-search"></span>
//                    <input type="search" placeholder="Search here" />
//                </div>-->%>
                
                <div class="user-wrapper">
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
<!--        </div>--> <% // SI CIERRO ACA EL DIV DE "main-content"  %>

<%
//    </body>
//</html>
%>