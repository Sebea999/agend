<%-- 
    Document   : pagFactura_AddInteresDet
    Created on : 08-sep-2021, 13:42:45
    Author     : RYUU
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanFacturaCab"%>
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
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CF_CI_MENSAJE"); // CONTROLLER FACTURA CARGAR INTERES MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CF_CI_TIPO_MENSAJE"); // CONTROLLER FACTURA CARGAR INTERES TIPO MENSAJE 
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
                
                if(mensaje != null) { // SI ES DIFERENTE A NULL ENTONCES MOSTRARE LAS ETIQUETAS PARA QUE EL USUARIO PUEDA VER EL MENSAJE 
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
            <%
                }
            %>
                <form action="CFSrv" method="post" autocomplete="off">
                <div>
                    <h4 class="mainTitle">Crear Factura / Cargar Interés</h4>
                </div>
                <%
                    DecimalFormat formatear = new DecimalFormat("###,###,###");
                    HttpSession facCarPro_sesionDatos = request.getSession(); // facCarPro_sesionDatos : factura cargar productos sesion datos 
                    
                    String TXT_NRO_FACTURA = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA");
                    String CBX_TIPO_FACTURA = (String) facCarPro_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA");
                    String TXT_FECHA_FACTURA = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA");
                    String TXT_RAZONSOCIAL_CLIENTE = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                    String TXT_RUC_CLIENTE = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC");
                    String TXT_ID_CLIENTE = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID");
                    String TXT_NAME_CLIENTE = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME");
                    String TXT_CINRO_CLIENTE = (String) facCarPro_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO");
                    
                    String TXT_DESCRIPCION_INTERES = (String) request.getAttribute("CF_FCI_TXT_DESCRIPCION");
                    if (TXT_DESCRIPCION_INTERES == null) {
                        TXT_DESCRIPCION_INTERES = "";
                    }
                    String CBX_IVA = (String) request.getAttribute("CF_FCI_CBX_IVA");
                    String TXT_PRECIO = (String) request.getAttribute("CF_FCI_TXT_PRECIO");
                    if (TXT_PRECIO == null) {
                        TXT_PRECIO = "";
                    }
                %>
                <div class="boxFCP">
                    <div class="boxFCP1">
                        <div class="div1fci">
                            <p>Descripción del Interés:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_DESCRIPCION_INTERES %>" name="tD" id="tD" class="form-control txtProDesc" />
                        </div>
                    </div>
                    
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>IVA:</p>
                        </div>
                        <div class="div2">
                            <select name="cI" id="cI" class="form-control cbxIva">
                                <%
                                Map<String, String> listaIva;
                                listaIva = metodosIniSes.cargarComboIVA(CBX_IVA);
                                Set setCbxIva = listaIva.entrySet();
                                Iterator iCbxIva = setCbxIva.iterator();

                                while(iCbxIva.hasNext()) {
                                    Map.Entry mapCbxIva = (Map.Entry) iCbxIva.next();
                                %>
                                <option value="<%= mapCbxIva.getKey() %>"><%= mapCbxIva.getValue() %></option>
                                <%
                                }
                                %>
                            </select>
                        </div>
                    </div>
                    
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>Monto:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_PRECIO %>" name="tP" id="tP" class="form-control" />
                        </div>
                    </div>
                    
                    <div class="boxFCP1 panelBtn">
                        <input type="submit" value="Cargar Interés a la Lista" name="accionFCI" class="btn btn-info mr-2" />
                        <a href="javascript:limpiarFAID()" class="btn btn-info">Limpiar</a>
                    </div>
                    
                    <div class="boxFCP1">
                        <%
                        if(((List<BeanFacturaCab>)facCarPro_sesionDatos.getAttribute("CF_LISTA_DETALLE")).size() > 0) {
                        %>
                        <table role="table" class="tableFCP">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="FCPCI">#</td>
                                    <td role="columnheader" class="FCPCP">Descripción</td>
                                    <td role="columnheader" class="FCPCIV">IVA</td>
                                    <td role="columnheader" class="FCPCC">Cant.</td>
                                    <td role="columnheader" class="FCPCMO">Monto</td>
                                    <td role="columnheader" class="FCPCST">SubTotal</td>
                                    <td role="columnheader" class="FCPCE">Eliminar</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
                                    // ITEM 
                                    String CF_LISTA_ITEM = (String) facCarPro_sesionDatos.getAttribute("CF_LISTA_ITEM");
                                    System.out.println("__JSP__CF_LISTA_ITEM:   "+CF_LISTA_ITEM);
                                    facCarPro_sesionDatos.setAttribute("CF_LISTA_ITEM", CF_LISTA_ITEM);
                                    
                                    // LISTA 
                                    List<BeanFacturaCab> CF_LISTA_DETALLE;
                                    CF_LISTA_DETALLE = (List<BeanFacturaCab>) facCarPro_sesionDatos.getAttribute("CF_LISTA_DETALLE");
                                    Iterator<BeanFacturaCab> iterLista = CF_LISTA_DETALLE.iterator();
                                    BeanFacturaCab datosTab = new BeanFacturaCab();
                                    int item = 0;
                                    
                                    while(iterLista.hasNext()) {
                                        item = item + 1;
                                        
                                        datosTab = iterLista.next();
                                %>
                                <tr role="row">
                                    <td role="cell" class="FCPCI"><%= item %></td>
                                    <td role="cell" class="FCPCP"><%= datosTab.getOFD_DESCRIPCION_AUX() %></td>
                                    <td role="cell" class="FCPCIV"><%= datosTab.getOFD_IDIMPUESTO() %></td>
                                    <td role="cell" class="FCPCC"><%= datosTab.getOFD_CANTIDAD() %></td>
                                    <td role="cell" class="FCPCMO"><%= formatear.format(Double.parseDouble(datosTab.getOFD_PRECIO())) %></td>
                                    <td role="cell" class="FCPCST"><%= formatear.format(Double.parseDouble(datosTab.getOFD_SUBTOTAL())) %></td>
                                    <td role="cell" class="FCPCE">
                                        <form action="CFSrv" method="post">
                                            <input type="hidden" value="<%= datosTab.getOFD_ITEM() %>" name="tAI" id="tAI" class="form-control" />
                                            <input type="hidden" value="<%= datosTab.getOFD_IDCONCEPTO() %>" name="tAIC" id="tAIC" class="form-control" />
                                            <input type="submit" value="Eliminar" name="accionFCI" class="btn btn-danger" />
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <%
                            }
                        %>
                    </div>
                </div>
                <div class="boxBtnCtaCli">
                    <form method="post" action="CFSrv"> <% // COLOCAR ESTE FORM ACA, AYUDA A QUE SE OBTENGAN CORRECTAMENTE LOS DATOS DE ESTOS CAMPOS DE TEXTOS EN LOS EVENTOS DE LOS DOS BOTONES (SI NO ESTA, PUEDE OBTENER MAL EL DATO TRAYENDO EL DATO DE OTRO CAMPO DE TEXTO) %>
                        <input type="hidden" name="tANF" id="tANF" value="<%= TXT_NRO_FACTURA %>" class="form-control" /> <% // tANF : TXT AUXILIAR NRO FACTURA  %>
                        <input type="hidden" name="cATF" id="cATF" value="<%= CBX_TIPO_FACTURA %>" class="form-control" /> <% // cATF : COMBOBOX AUXILIAR TIPO FACTURA  %>
                        <input type="hidden" name="tAFF" id="tAFF" value="<%= TXT_FECHA_FACTURA %>" class="form-control" /> <% // tAFF : TXT AUXILIAR FECHA FACTURA  %>
                        <input type="hidden" name="tACRS" id="tACRS" value="<%= TXT_RAZONSOCIAL_CLIENTE %>" class="form-control" /> <% // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL  %>
                        <input type="hidden" name="tACR" id="tACR" value="<%= TXT_RUC_CLIENTE %>" class="form-control" /> <% // tACR : TXT AUXILIAR CLIENTE RUC  %>
                        <input type="hidden" name="tAIC" id="tAIC" value="<%= TXT_ID_CLIENTE %>" class="form-control" /> <% // tAIC : TXT AUXLIAR ID CLIENTE  %>
                        <input type="hidden" name="tACN" id="tACN" value="<%= TXT_NAME_CLIENTE %>" class="form-control" /> <% // tACN : TXT AUXILIAR CLIENTE NAME  %>
                        <input type="hidden" name="tACNC" id="tACNC" value="<%= TXT_CINRO_CLIENTE %>" class="form-control" /> <% // tACNC : TXT AUXILIAR CLIENTE NRO CI %>
                        <input type="submit" value="Volver a la Cabecera" name="accion" class="btn btn-warning mt-2" />
                        <input type="submit" value="Cargar Detalle" name="accionFCI" class="btn btn-primary mt-2" />
                    </form>
                </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configCleanPagsFacts.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>