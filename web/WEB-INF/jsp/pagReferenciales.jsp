
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Referenciales</title>
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
                    <div class="card-single">
                        <a href="<%= urlRefClinica %>">
                            <div>
                                <h1>Clinica</h1>
                                <%//<!--<span>......</span>-->%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>
                    
                    <div class="card-single">
                        <a href="<%= urlRefServicios %>">
                            <div>
                                <h1>Servicios</h1>
                                <%//<!--<span>......</span>-->%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>
                    
                    <div class="card-single">
                        <a href="<%= urlRefEspecialidad %>">
                            <div>
                                <h1>Especialidad</h1>
                                <%//<!--<span>......</span>-->%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>
                    
                    <div class="card-single">
                        <a href="<%= urlRefCiudad %>">
                            <div>
                                <h1>Ciudad</h1>
                                <%//<!--<span>......</span>-->%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlRefBarrio %>">
                            <div>
                                <h1>Barrio</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlRefCiudad %>">
                            <div>
                                <h1>Ciudad</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlRefPais %>">
                            <div>
                                <h1>País</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlRefReligion %>">
                            <div>
                                <h1>Religión</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlRefEstadoCivil %>">
                            <div>
                                <h1>Estado Civil</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                    
<%--                    <div class="card-single">
                        <a href="<%= urlReferenciales %>">
                            <div>
                                <h1>......</h1>
                                <%//<span>......</span>%>
                            </div>
                            <div>
                                <span class="las la-comment"></span>
                            </div>
                        </a>
                    </div>--%>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>