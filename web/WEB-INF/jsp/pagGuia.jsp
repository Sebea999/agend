<%-- 
    Document   : pagReglamento
    Created on : 12-nov-2021, 9:56:55
    Author     : RYUU
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manual de Usuario</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <div>
                    <h4 class="mainTitle">Manual para el Usuario</h4>
                </div>
                <div>
                    <div>
                        <p style="width: 80%; text-align: justify;" class="mt-3 ">Manual que contendra las indicaciones para poder utilizar de manera correcta las páginas. Aclarando varios puntos de algunas páginas.</p>
                        <%
                        // DEPENDIENDO DEL PERFIL DEL USUARIO, SE CARGARA LA DIRECCION DEL PDF 
                        String url_pdf = "";
                        if (metodosPerfil.isPerfilPaciente(idPerfil)) {
                            url_pdf = "/recursos/manual_usuario_4.pdf";
                        } else if (metodosPerfil.isPerfilSecre(idPerfil) || metodosPerfil.isPerfilFuncio(idPerfil)) {
                            url_pdf = "/recursos/manual_usuario_3.pdf";
                        } else if (metodosPerfil.isPerfilMedico(idPerfil)) {
                            url_pdf = "/recursos/manual_usuario_5.pdf";
                        } else if (metodosPerfil.isPerfilAdmin(idPerfil)) {
                            url_pdf = "/recursos/manual_usuario_1.pdf";
                        }
                        // EN CASO DE QUE ESTE VACIO, COMO ULTIMA INSTANCIA NO LE MOSTRARIA NADA 
                        if (url_pdf.isEmpty() || url_pdf.equals("")) {
                        %>
                        <%="(No se cuenta con el PDF).-"%>
                        <%
                        } else {
                        %>
                        <a href="${pageContext.request.contextPath}<%=url_pdf%>" download="Manual de Usuario" class="btn btn-info">Descargar el Manual de Usuario</a>
                        <%
                        }
                        %>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script type="text/javascript">
            <%// ---------------------------------------------------------------------------------------------------------------%>
            <%// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR %>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
                if(e.keyCode == 13) {
                  e.preventDefault();
                  document.getElementById("Filtrar").click();
                }
              }))
            });
            <%// ---------------------------------------------------------------------------------------------------------------%>
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>