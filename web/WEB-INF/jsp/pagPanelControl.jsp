<%-- 
    Document   : pagPanelControl
    Created on : 11-ago-2021, 10:20:05
    Author     : RYUU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Panel de Control</title>
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
                <div class="cards_pc">
                    <div class="pc_caja">
                    <%
                    if(metodosPerfil.isPCAnularAgendamiento(idPerfil)==true || metodosPerfil.isPCAnularFactura(idPerfil) == true) {
                    %>
                        <p>Anular</p>
                    <%
                    }
                    %>
                    <%
                    if(metodosPerfil.isPCAnularAgendamiento(idPerfil) == true) {
                    %>
                        <div class="card-single">
                            <a href="<%= urlAnularAgendamientos %>">
                                <div>
                                    <h1>Anular Agendamientos</h1>
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
                    if(metodosPerfil.isPCAnularFactura(idPerfil) == true) {
                    %>
                        <div class="card-single">
                            <a href="<%= urlAnularFacturas %>">
                                <div>
                                    <h1>Anular Facturas</h1>
                                    <%//<span>......</span>%>
                                </div>
                                <div>
                                    <span class="las la-comment"></span>
                                </div>
                            </a>
                        </div>
                    <%
                    }
                    %>
                    </div>
                    
                    
                    <div class="pc_caja">
                    <%
                    if(metodosPerfil.isPCConfigAgendamiento(idPerfil) == true) {
                    %>
                        <p>Config.</p>
                    <%
                    }
                    %>
                    <%
                    if(metodosPerfil.isPCConfigAgendamiento(idPerfil) == true) {
                    %>
                        <div class="card-single">
                            <a href="<%= urlConfigAgendamiento %>">
                                <div>
                                    <h1>Configuración de Agendamiento</h1>
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
                    </div>
                    
                    
                    <%
//                    <div class="pc_empenhos">
//                        <p>Empeño</p>
//                        <div>
//                            <div class="card-single">
//                                <a href="<%= urlResumenEmpenho >">
//                                    <div>
//                                        <h1>Resumen Empeño</h1>
//                                        <%//<!--<span>......</span>-->>
//                                    </div>
//                                    <div>
//                                        <span class="las la-comment"></span>
//                                    </div>
//                                </a>
//                            </div>
//                        </div>
//                    </div>
                    %>
                    
                    <%
//                    <div class="pc_inventario">
//                        <p>Inventario</p>
//                        <div>
//                            <div class="card-single">
//                                <a href="<%= urlPrendasEmpeñadas >">
//                                    <div>
//                                        <h1>Prendas Empeñadas</h1>
//                                        <%//<!--<span>......</span>-->>
//                                    </div>
//                                    <div>
//                                        <span class="las la-comment"></span>
//                                    </div>
//                                </a>
//                            </div>
//
//                            <div class="card-single">
//                                <a href="<%= urlPrendasEnVenta >">
//                                    <div>
//                                        <h1>Prendas en Venta</h1>
//                                        <%//<!--<span>......</span>-->>
//                                    </div>
//                                    <div>
//                                        <span class="las la-comment"></span>
//                                    </div>
//                                </a>
//                            </div>
//                        </div>
//                    </div>
                    %>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>