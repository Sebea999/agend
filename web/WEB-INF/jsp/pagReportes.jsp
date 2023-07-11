<%-- 
    Document   : pagReportes
    Created on : 16-jun-2021, 12:20:20
    Author     : RYUU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reportes</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <div class="cards">
                <%
                if(metodosPerfil.isReportePaciente(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReportePaciente %>">
                            <div>
                                <h1>Reporte Paciente</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteMisAgendamientos(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteMisAgend %>">
                            <div>
                                <h1>Reporte Mis Agendamientos</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteAjusteStock(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteAjusteStock %>">
                            <div>
                                <h1>Reporte Ajuste Stock</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteStock(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteStock %>">
                            <div>
                                <h1>Reporte Stock</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteFactura(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteFactura %>">
                            <div>
                                <h1>Reporte Factura</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteCuentaCliente(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteCuentaCliente %>">
                            <div>
                                <h1>Reporte Cuenta Cliente</h1>
                                <%//<!--<span>......</span>-->%>
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
                if(metodosPerfil.isReporteEstadistica(idPerfil) == true) {
                %>
                    <div class="card-single">
                        <a href="<%= urlReporteEstadistica %>">
                            <div>
                                <h1>Reporte Estadística</h1>
                                <%//<!--<span>......</span>-->%>
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
//                    <div class="card-single">
//                        <a href="<%= urlReportes >">
//                            <div>
//                                <h1>......</h1>
//                                <%//<span>......</span>>
//                            </div>
//                            <div>
//                                <span class="las la-comment"></span>
//                            </div>
//                        </a>
//                    </div>
                %>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>