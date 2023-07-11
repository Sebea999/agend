<%-- 
    Document   : pagFactura_CtaCliente
    Created on : 13-jul-2021, 12:22:07
    Author     : RYUU
--%>

<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanCuentaCliente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Factura</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleFacturaCobrar.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
        <form action="CFSrv" method="post">
        <main>
            <div>
                <h4 class="mainTitle">Crear Factura / Cargar Cuenta Cliente</h4>
            </div>
            <%
                HttpSession facCtaCli_sesionDatos = request.getSession(); // facCtaCli_sesionDatos : factura cuenta cliente sesion datos 
                
                String TXT_NRO_FACTURA = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA");
                String CBX_TIPO_FACTURA = (String) facCtaCli_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA");
                String TXT_FECHA_FACTURA = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA");
                String TXT_RAZONSOCIAL_CLIENTE = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                String TXT_RUC_CLIENTE = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC");
                String TXT_ID_CLIENTE = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID");
                String TXT_NAME_CLIENTE = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME");
                String TXT_CINRO_CLIENTE = (String) facCtaCli_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO");
            
            %>
            <div>
                <table role="table" class="tableFacCtaCliente">
                    <thead role="rowgroup">
                        <tr role="row">
                            <td role="columnheader" class="FCCI">#</td>
                            <td role="columnheader" class="FCCFV">CLINICA</td>
<%--                            <td role="columnheader" class="">IDPERSONA</td>
                            <td role="columnheader" class="">IDEMPENHO</td>--%>
                            <td role="columnheader" class="FCCNE">CUENTAS</td>
                            <td role="columnheader" class="FCCNC">NRO CUOTA</td>
                            <td role="columnheader" class="FCCM">MONTO</td>
                            <td role="columnheader" class="FCCFV">FECHA VENC.</td>
                            <td role="columnheader" class="FCCCH">CARGAR</td>
                        </tr>
                    </thead>
                    <tbody role="rowgroup">
                        <%
                        // LISTA 
                        List<BeanCuentaCliente> listaCtaCliente;
                        listaCtaCliente = metodosFactura.cargarCuentaCliente(TXT_ID_CLIENTE, sesionDatosUsuario);
                        Iterator<BeanCuentaCliente> iterCta = listaCtaCliente.iterator();
                        BeanCuentaCliente datosCta = null;
                        
                        int ITEM = 0;
                        while(iterCta.hasNext()) {
                            datosCta = iterCta.next();
                            
                            ITEM = ITEM + 1;
                            String DESC_CLINICA = metodosRefClinica.traerDescClinica(datosCta.getOA_IDCLINICA());
                        %>
                        <tr role="row">
                            <td role="cell" class="FCCI"><%= ITEM %></td>
                            <td role="cell" class="FCCFV"><%= DESC_CLINICA %></td>
                            <td role="cell" class="FCCNE"><%= datosCta.getCC_COMENTARIO() %></td>
                            <td role="cell" class="FCCNC"><%= datosCta.getCC_NROCUOTA() %></td>
                            <td role="cell" class="FCCM">
                                <%= datosCta.getCC_MONTO() %>
                            </td>
                            <td role="cell" class="FCCFV"><%= datosCta.getCC_FEC_VENCIMIENTO() %></td>
                            <td role="cell" class="FCCCH">
                                <input type="checkbox" name="checkCta<%= datosCta.getCC_IDCUENTACLIENTE() %>" />
                            </td>
                        </tr>
                        <%
                        }
                        %>
                    </tbody>
                </table>
            </div>
            
            <div class="boxBtnCtaCli_v2">
                <form method="post" action="CFSrv"> <% // COLOCAR ESTE FORM ACA, AYUDA A QUE SE OBTENGAN CORRECTAMENTE LOS DATOS DE ESTOS CAMPOS DE TEXTOS EN LOS EVENTOS DE LOS DOS BOTONES (SI NO ESTA, PUEDE OBTENER MAL EL DATO TRAYENDO EL DATO DE OTRO CAMPO DE TEXTO) %>
                    <input type="text" name="tANF" id="tANF" value="<%= TXT_NRO_FACTURA %>" class="form-control" /> <% // tANF : TXT AUXILIAR NRO FACTURA  %>
                    <input type="text" name="cATF" id="cATF" value="<%= CBX_TIPO_FACTURA %>" class="form-control" /> <% // cATF : COMBOBOX AUXILIAR TIPO FACTURA  %>
                    <input type="text" name="tAFF" id="tAFF" value="<%= TXT_FECHA_FACTURA %>" class="form-control" /> <% // tAFF : TXT AUXILIAR FECHA FACTURA  %>
                    <input type="text" name="tACRS" id="tACRS" value="<%= TXT_RAZONSOCIAL_CLIENTE %>" class="form-control" /> <% // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL  %>
                    <input type="text" name="tACR" id="tACR" value="<%= TXT_RUC_CLIENTE %>" class="form-control" /> <% // tACR : TXT AUXILIAR CLIENTE RUC  %>
                    <input type="text" name="tAIC" id="tAIC" value="<%= TXT_ID_CLIENTE %>" class="form-control" /> <% // tAIC : TXT AUXLIAR ID CLIENTE  %>
                    <input type="text" name="tACN" id="tACN" value="<%= TXT_NAME_CLIENTE %>" class="form-control" /> <% // tACN : TXT AUXILIAR CLIENTE NAME  %>
                    <input type="text" name="tACNC" id="tACNC" value="<%= TXT_CINRO_CLIENTE %>" class="form-control" /> <% // tACNC : TXT AUXILIAR CLIENTE NRO CI %>

                    <input type="submit" value="Volver a la Cabecera" name="accion" class="btn btn-warning mt-2" />
                    <% // ------------   BOTON DE INTERES   --------------  %>
                    <%//<input type="submit" value="Cargar Interés" name="accionFCC" class="btn btn-info mt-2" />%>
                    <% // ------------------------------------------------  %>
                    <input type="submit" value="Cargar Detalle" name="accionFCC" class="btn btn-primary mt-2" />
                </form>
            </div>
        </main>
        </form>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>