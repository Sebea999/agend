<%-- 
    Document   : pagRpt_HistFichaAtencionViewDet
    Created on : 04-may-2023, 10:57:09
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte - Historico de Fichas de Atención</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CR_RFAN_MENSAJE"); // CONTROLLER REPORTES- REPORTE FICHA ATENCION NUTRI- MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CR_RFAN_TIPO_MENSAJE"); // CONTROLLER REPORTES- REPORTE FICHA ATENCION NUTRI- TIPO MENSAJE 
                String claseMensaje = "";
                
                if (mensaje != null) {
                    displayMsn = "block";
                    if (tipoMensaje.equals("1")) {
                        claseMensaje = "alert alert-success";
                    } else {
                        claseMensaje = "alert alert-danger";
                    }
                } else {
                    mensaje = "";
                }
                
                if (mensaje != null) {
                %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
                <%
                }
                
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".   -----------------   JSP - HIST - FICHA - ATENCION - PAC   -----------------------------");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                String ID_FICHA = (String) request.getAttribute("CR_RFAN_TXT_IDFICHA");
                System.out.println(".   ___JSP_________ID_FICHA:   :"+ID_FICHA);
                String PAC_IDPACIENTE = (String) request.getAttribute("CR_RFAN_TXT_IDPACIENTE");
                System.out.println(".   ___JSP_________ID_PACIENTE:   :"+PAC_IDPACIENTE);
                String PACIENTE_NAME = (String) request.getAttribute("CR_RFAN_TXT_NAME_PACIENTE");
                System.out.println(".   ___JSP_________PACIENTE_NAME: :"+PACIENTE_NAME);
                // recupero el nombre y apellido del paciente del cual estoy recuperando las fichas de atencion 
//                String NOMBRE_PAC = (String) request.getAttribute("CR_RFAN_TXT_PAC_NOM");
//                if (NOMBRE_PAC == null || NOMBRE_PAC.isEmpty()) {
//                    NOMBRE_PAC = "";
//                }
//                String APELLIDO_PAC = (String) request.getAttribute("txtPacApellido");
//                if (APELLIDO_PAC == null || APELLIDO_PAC.isEmpty()) {
//                    APELLIDO_PAC = "";
//                }
                String PAG_TITULO = "HISTORICO DE FICHAS DE ATENCIÓN / Archivos Adjuntos de la Ficha #"+ID_FICHA;
//                if (NOMBRE_PAC.isEmpty() && APELLIDO_PAC.isEmpty()) {
//                    System.out.println("_   ___JSP_______NOMBRE_Y_APELLIDO_DEL_PACIENTE________IS_NULL_FOR_TITLE_________");
//                } else {
//                    PAG_TITULO = "VER HISTORICO DE FICHAS DE ATENCIÓN DE / "+NOMBRE_PAC+" "+APELLIDO_PAC;
//                }
                %>
                <div>
                    <h4 class="mainTitle"><%=PAG_TITULO%></h4>
                    <h5 class="mt-2 ml-2"><strong>De:</strong> <%= PACIENTE_NAME %></h5>
                </div>
                <form action="CRSrv" method="post" autocomplete="off">
                    <div>
                        <div class="divTable">
                            <table role="table" class="tableFilterListFic table-striped">
                                <thead role="rowgroup">
                                    <tr role="row">
                                        <td role="columnheader" class="HFA_CI">#</td>
                                        <td role="columnheader" class="TAF_CDA">Nombre del Archivo</td>
                                        <!--<td role="columnheader" class="TAF_CDA">Dirección del Archivo</td>-->
                                    </tr>
                                </thead>
                                <tbody role="rowgroup">
                                    <%
                                    List<BeanFichaAtePaciente> listaDatos = null;
                                    if (((List<BeanFichaAtePaciente>)request.getAttribute("CR_RFAN_LISTA_HISTORICO_DET_FICHA_AA")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        listaDatos = (List<BeanFichaAtePaciente>) request.getAttribute("CR_RFAN_LISTA_HISTORICO_DET_FICHA_AA");
                                        Iterator<BeanFichaAtePaciente> iterDatos = listaDatos.iterator();
                                        BeanFichaAtePaciente datosBean = null;
                                        
                                        int item = 0;
                                        while(iterDatos.hasNext()) {
                                            datosBean = iterDatos.next();
                                            item++;
                                    %>
                                    <tr role="row">
                                        <td role="cell" class="HFA_CI">
                                            <%= item%>
                                        </td>
                                        <td role="cell" class="TAF_CDA">
                                            <%= datosBean.getOFPND_NAME_ARCHIVO()%>
                                        </td>
<!--                                        <td role="cell" class="TAF_CDA">
                                            <%= datosBean.getOFPND_DIR_ARCHIVO()%>
                                        </td>-->
                                    </tr>
                                    <%
                                        } // end-while-lista-fichas.-
                                    } else {
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println("-   -----------LISTA-VACIA-------------");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                    }
                                    %>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    
                    <div>
                        <input type="hidden" value="<%= PAC_IDPACIENTE %>" name="tIP" class="form-control disNone"/>
                        <input type="hidden" value="<%= PACIENTE_NAME %>" name="tCRPNC" class="form-control disNone"/>
                        <input type="submit" value="Volver Atras" name="accionRptHFAN" class="mt-3 btn btn-danger">
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/rptMAConfigAux.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>