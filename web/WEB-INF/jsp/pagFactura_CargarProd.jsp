<%-- 
    Document   : pagFactura_CargarProd
    Created on : 16-jul-2021, 9:47:56
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalProd.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CF_CP_MENSAJE"); // CONTROLLER FACTURA CARGAR PRODUCTO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CF_CP_TIPO_MENSAJE"); // CONTROLLER FACTURA CARGAR PRODUCTO TIPO MENSAJE 
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
                
                <form action="CFSrv" method="post" autocomplete="off" >
                <div>
                    <h4 class="mainTitle">Crear Factura / Cargar Producto</h4>
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
                    
                    String TXT_ID_STOCK = (String) request.getAttribute("CF_FCP_TXT_ID_STOCK");
                    if (TXT_ID_STOCK == null) { TXT_ID_STOCK = ""; }
                    String TXT_LOTE = (String) request.getAttribute("CF_FCP_TXT_LOTE");
                    if (TXT_LOTE == null) { TXT_LOTE = ""; }
                    String TXT_STOCK = (String) request.getAttribute("CF_FCP_TXT_STOCK");
                    if (TXT_STOCK == null) { TXT_STOCK = ""; }
                    String TXT_ID_PRODUCTO = (String) request.getAttribute("CF_FCP_TXT_ID_PRODUCTO");
                    String TXT_DESCRIPCION_PROD = (String) request.getAttribute("CF_FCP_TXT_DESCRIPCION");
                    String TXT_MARCA = (String) request.getAttribute("CF_FCP_TXT_MARCA");
                    String TXT_CANTIDAD = (String) request.getAttribute("CF_FCP_TXT_CANTIDAD");
                    String CBX_IVA = (String) request.getAttribute("CF_FCP_CBX_IVA");
                    String TXT_PRECIO = (String) request.getAttribute("CF_FCP_TXT_PRECIO");
                %>
                <div class="boxFCP">
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>Producto:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_ID_PRODUCTO %>" name="tI" id="tI" readonly="readonly" class="form-control disNone" />
                            <input type="text" value="<%= TXT_DESCRIPCION_PROD %>" name="tD" id="tD" readonly="readonly" class="form-control txtProDesc" />
                            <%//<!--<form method="post" action="CESrv">-->%>
                            <input type="checkbox" id="btn-modal" />
                            <label for="btn-modal" class="btn btn-info btnANP">Add Nuevo Producto</label>
                                <%
                                /*
                                    ------------------------------------------
                                                 MODAL PRODUCTO 
                                    ------------------------------------------
                                */
                                %>
                                <div class="modal">
                                    <div class="contenedor">
                                        <div class="titulo2">
                                            <div class="titu01">Añadir Nuevo Producto</div>
                                            <div class="titu02"><label for="btn-modal" class="btnTi">Cerrar</label></div>
                                        </div>
                                        <%//<!--<header>Añadir Nuevo Producto</header>-->%>
                                        <%//<!--<label for="btn-modal" class="btnSa">X</label>-->%>
                                        <div class="contenido">
                                            <%//<!--<form method="post" action="CESrv">-->%>
                                                <div class="contDataProd">
                                                    <p>Producto:</p>
                                                </div>
                                                <div class="contDataProd">
                                                    <select name="cbxAddNewProd" class="form-control cbxProd">
                                                        <%
//                                                        Map<String, String> listaProductos;
//                                                        listaProductos = metodosEmpenho.cargarProductosStock(); // PRODUCTOS QUE SOLO ESTAN EN LA TABLA DE STOCK 
//                                                        Set setProductos = listaProductos.entrySet();
//                                                        Iterator<BeanProducto> iterProductos = setProductos.iterator();
//                                                        
//                                                        while(iterProductos.hasNext()) {
//                                                            Map.Entry mapaPro = (Map.Entry) iterProductos.next();
                                                        %>
                                                        <!--<option value="<%--= mapaPro.getKey() %>"><%= mapaPro.getValue() --%></option>-->
                                                        <%
//                                                        }
                                                        %>
                                                    </select>
                                                </div>
                                                <div class="contDataProd">
                                                    <input type="submit" name="accionFCP" value="Cargar Producto" class="btn btn-success" />
                                                </div>
                                            <%//<!--</form>-->%>
                                        </div>
                                    </div>
                                </div>
                                <%
                                /*
                                    ------------------------------------------
                                                END MODAL PRODUCTO 
                                    ------------------------------------------
                                */
                                %>
                            <%//<!--</form>-->%>
                        </div>
                    </div>
                    
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>Marca:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_MARCA %>" name="tM" id="tM" readonly="readonly" class="form-control" />
                        </div>
                    </div>
                    
                    
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>Lote:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_LOTE %>" name="tLo" id="tLo" readonly="readonly" class="form-control" />
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
                            <p>Precio:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_PRECIO %>" name="tP" id="tP" class="form-control" />
                        </div>
                    </div>
                    
                    <div class="boxFCP1">
                        <div class="div1">
                            <p>Cantidad:</p>
                        </div>
                        <div class="div2">
                            <input type="text" value="<%= TXT_CANTIDAD %>" name="tC" id="tC" class="form-control" />
                        </div>
                    </div>
                    
                    <div class="boxFCP1 panelBtn">
                        <input type="hidden" value="<%= TXT_ID_STOCK %>" name="tIS" id="tIS" class="form-control"/>
                        <input type="hidden" value="<%= TXT_STOCK %>" name="tSP" id="tSP" class="form-control"/>
                        <input type="submit" value="Cargar Producto a la Lista" name="accionFCP" class="btn btn-info mr-2" />
                        <a href="javascript:limpiarFCP()" class="btn btn-info">Limpiar</a>
                    </div>
                    
                    <div class="boxFCP1">
                        <%
                        if(((List<BeanFacturaCab>)facCarPro_sesionDatos.getAttribute("CF_LISTA_DETALLE")).size() > 0) {
                        %>
                        <table role="table" class="tableFCP">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="FCPCI">#</td>
                                    <td role="columnheader" class="FCPCP">Producto</td>
                                    <td role="columnheader" class="FCPCM">Marca</td>
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
                                    <td role="cell" class="FCPCM"><%= datosTab.getOF_OBSERVACION() %></td> <% // EN OBSERVACION DE LA CABECERA DE FACTURA GUARDO PARA MOSTRAR LA DESCRIPCION DE LA MARCA, YA QUE ESTE CAMPO NO UTILIZO  %>
                                    <td role="cell" class="FCPCIV"><%= datosTab.getOFD_IDIMPUESTO() %></td>
                                    <td role="cell" class="FCPCC"><%= datosTab.getOFD_CANTIDAD() %></td>
                                    <td role="cell" class="FCPCMO"><%= formatear.format(Double.parseDouble(datosTab.getOFD_PRECIO())) %></td>
                                    <td role="cell" class="FCPCST"><%= formatear.format(Double.parseDouble(datosTab.getOFD_SUBTOTAL())) %></td>
                                    <td role="cell" class="FCPCE">
                                        <form action="CFSrv" method="post">
                                            <input type="text" value="<%= datosTab.getOFD_ITEM() %>" name="tAI" id="tAI" class="form-control" />
                                            <input type="text" value="<%= datosTab.getOFD_IDCONCEPTO() %>" name="tAIC" id="tAIC" class="form-control" />
                                            <input type="submit" value="Eliminar" name="accionFCP" class="btn btn-danger" />
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
                        <%//<input type="submit" value="Cargar Interés" name="accionFCP" class="btn btn-info mt-2" />%>
                        <% // ------------------------------------------------  %>
                        <input type="submit" value="Cargar Detalle" name="accionFCP" class="btn btn-primary mt-2" />
                    </form>
                </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        
        <script type="text/javascript">
            <%// ---------------------------------------------------------------------------------------------------------------%>
            <%// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION %>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            document.addEventListener('DOMContentLoaded', () => {
            document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
                if(e.keyCode == 13) {
                  e.preventDefault();
                }
              }))
            });
            <%// ---------------------------------------------------------------------------------------------------------------%>
            
            function limpiarFCP() {
                document.getElementById("tI").value = "";
                document.getElementById("tD").value = "";
                document.getElementById("tM").value = "";
                document.getElementById("cI").value = "0";
                document.getElementById("tP").value = "";
                document.getElementById("tC").value = "1";
            }
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>