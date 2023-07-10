<%-- 
    Document   : pagFactura_CobDet
    Created on : 23-jun-2021, 10:37:18
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanFacturaCab"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalClie.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalProd.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CF_MENSAJE"); // CONTROLLER FACTURA MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CF_TIPO_MENSAJE"); // CONTROLLER FACTURA TIPO MENSAJE 
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
                <div>
                    <h4 class="mainTitle">Crear Factura / DETALLE </h4>
                </div>
                <form action="CFSrv" method="post" autocomplete="off">
                    <%
                    HttpSession FacCobDet_sesionDatos = request.getSession();
                    ModelFactura FacCobDet_metodosFact = new ModelFactura();
                    ModelAgendamiento FacCobDet_metodosAgen = new ModelAgendamiento();
                    %>
                    <%
                    /*
                        TOTALES DE LA FACTURA 
                    */
                    String TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="";
                    
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA5") != null) {
                        TXT_TOTAL_IVA5 = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA5");
                    } else {
                        TXT_TOTAL_IVA5 = "0";
                    }
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV5") != null) {
                        TXT_TOTAL_GRAV5 = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV5");
                    } else {
                        TXT_TOTAL_GRAV5 = "0";
                    }
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA10") != null) {
                        TXT_TOTAL_IVA10 = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA10");
                    } else {
                        TXT_TOTAL_IVA10 = "0";
                    }
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV10") != null) {
                        TXT_TOTAL_GRAV10 = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV10");
                    } else {
                        TXT_TOTAL_GRAV10 = "0";
                    }
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA") != null) {
                        TXT_TOTAL_IVA = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA");
                    } else {
                        TXT_TOTAL_IVA = "0";
                    }
                    if ((String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL") != null) {
                        TXT_TOTAL = (String) FacCobDet_sesionDatos.getAttribute("CF_TXT_TOTAL");
                    } else {
                        TXT_TOTAL = "0";
                    }
                    /*
                    RECUPERABA LOS TOTALES POR MEDIO DE LOS REQUEST PORQUE HABIA UN SOLO FORM QUE CUBRIA TODO EL FORMULARIO 
                    PERO COMO AÑADI OTRO FORM EN EL BOTON DE ELIMINAR ENTONCES NO PUEDO RECUPERAR ESTOS DATOS POR MEDIO DEL REQUEST 
                    Y CAMBIE PARA RECUPERARLOS POR MEDIO DE LA SESION 
                    */
        //            if ((String) request.getAttribute("CF_TXT_TOTAL_IVA5") != null) {
        //                TXT_TOTAL_IVA5 = (String) request.getAttribute("CF_TXT_TOTAL_IVA5");
        //            } else {
        //                TXT_TOTAL_IVA5 = "0";
        //            }
        //            if ((String) request.getAttribute("CF_TXT_TOTAL_GRAV5") != null) {
        //                TXT_TOTAL_GRAV5 = (String) request.getAttribute("CF_TXT_TOTAL_GRAV5");
        //            } else {
        //                TXT_TOTAL_GRAV5 = "0";
        //            }
        //            if ((String) request.getAttribute("CF_TXT_TOTAL_IVA10") != null) {
        //                TXT_TOTAL_IVA10 = (String) request.getAttribute("CF_TXT_TOTAL_IVA10");
        //            } else {
        //                TXT_TOTAL_IVA10 = "0";
        //            }
        //            if ((String) request.getAttribute("CF_TXT_TOTAL_GRAV10") != null) {
        //                TXT_TOTAL_GRAV10 = (String) request.getAttribute("CF_TXT_TOTAL_GRAV10");
        //            } else {
        //                TXT_TOTAL_GRAV10 = "0";
        //            }
        //            if ((String) request.getAttribute("CF_TXT_TOTAL_IVA") != null) {
        //                TXT_TOTAL_IVA = (String) request.getAttribute("CF_TXT_TOTAL_IVA");
        //            } else {
        //                TXT_TOTAL_IVA = "0";
        //            }
        //            if ((String) request.getAttribute("CF_TXT_TOTAL") != null) {
        //                TXT_TOTAL = (String) request.getAttribute("CF_TXT_TOTAL");
        //            } else {
        //                TXT_TOTAL = "0";
        //            }
                    
                    System.out.println("----JSP_DETALLE____TOTALES_____---------");
                    System.out.println("__TOTAL_IVA_5:      :"+TXT_TOTAL_IVA5);
                    System.out.println("__TOTAL_IVA_10:     :"+TXT_TOTAL_IVA10);
                    System.out.println("__TOTAL_GRAV_5:     :"+TXT_TOTAL_GRAV5);
                    System.out.println("__TOTAL_GRAV_10:    :"+TXT_TOTAL_GRAV10);
                    System.out.println("__TOTAL_IVA :      :"+TXT_TOTAL_IVA);
                    System.out.println("__TOTAL_FACT:      :"+TXT_TOTAL);
                    System.out.println("----------------------------------------");
                    %>
                    <div class="detBox">
                        <div class="detBox1">
                            <table role="table" class="tableCobDet">
                                <thead role="rowgroup">
                                    <tr role="row">
                                        <td role="columnheader" class="GRICAB_CI">#</td>
                                        <td role="columnheader" class="GRICAB_CD">Descripción</td>
                                        <td role="columnheader" class="GRICAB_CP">Precio</td>
                                        <td role="columnheader" class="GRICAB_CA">Cant.</td>
                                        <td role="columnheader" class="GRICAB_CIV">IVA</td>
                                        <%//<td role="columnheader" class="GRICAB_CMI">Monto IVA</td>%>
                                        <td role="columnheader" class="GRICAB_CST">SubTotal</td>
                                        <td role="columnheader" class="GRICAB_CE">Eliminar</td>
                                    </tr>
                                </thead>
                                <tbody role="rowgroup">
                                    <%
                                    // NRO DE ITEM DE LA LISTA 
                                    System.out.println("...");
                                    System.out.println("..");
                                    System.out.println(".");
                                    System.out.println(".");
                                    System.out.println(".");
                                    System.out.println("________________JSP_____DETALLE___________________");
                                    System.out.println("___ITEM_NUMERO__________");
                                    String CF_ITEM_LISTA = "";
                                    try {
                                        CF_ITEM_LISTA = String.valueOf(FacCobDet_sesionDatos.getAttribute("CF_LISTA_ITEM"));
                                    } catch(NumberFormatException e) {
                                        //
                                    }
                                    System.out.println("__JSP__CF_ITEM_LISTA:   "+CF_ITEM_LISTA);
                                    FacCobDet_sesionDatos.setAttribute("CF_LISTA_ITEM", CF_ITEM_LISTA);
                                    System.out.println("_______ITEM_RECUPERADO_________LISTA___↓________");
                                    
//                                    String CF_ITEM_LISTA_ELIMINAR_ANTES="9999";
//                                    try {
//                                        CF_ITEM_LISTA_ELIMINAR_ANTES = (String) FacCobDet_sesionDatos.getAttribute("CF_ITEM_LISTA_ELIMINAR_ANTES");
//                                    } catch (NullPointerException e) {
//                                        CF_ITEM_LISTA_ELIMINAR_ANTES = "9999";
//                                    }
//                                    System.out.println("__JSP__CF_ITEM_LISTA_ELIMINAR_ANTES:   "+CF_ITEM_LISTA_ELIMINAR_ANTES);
//                                    FacCobDet_sesionDatos.setAttribute("CF_ITEM_LISTA_ELIMINAR_ANTES", CF_ITEM_LISTA_ELIMINAR_ANTES);
                                    
                                    // LA LISTA 
                                    List<BeanFacturaCab> listaDatos;
                                    listaDatos = (List<BeanFacturaCab>) FacCobDet_sesionDatos.getAttribute("CF_LISTA_DETALLE");
                                    System.out.println("----------------------------------------------------");
                                    System.out.println("____JSP______LISTA_ITEM_NRO:        "+listaDatos.size());
                                    if (listaDatos.isEmpty()) {
                                        System.out.println(".");System.out.println(".");
                                        System.out.println("______JSP______");
                                        System.out.println("__LISTA_VACIA__");
                                        System.out.println(".");System.out.println(".");
                                        /*
                                        SI LA LISTA ESTA VACIA PUEDE SER PORQUE ELIMINO TODOS LOS DATOS O PORQUE SE PRODUCIO UN ERROR AL RECARGAR LA PAGINA REPITIENDO ACCIONES 
                                        POR EJEMPLO AL ELIMINAR LA ULTIMA FILA Y LUEGO DE ESO RECARGAS LA PAGINA REPITIENDO LA ULTIMA ACCION ENTONCES SE PRODUCE UN ERROR VACIANDO LA LISTA 
                                        */
                                        TXT_TOTAL_IVA5 = "0";
                                        TXT_TOTAL_IVA10 = "0";
                                        TXT_TOTAL_IVA = "0";
                                        TXT_TOTAL = "0";
                                    }
                                    Iterator<BeanFacturaCab> iterFactura = listaDatos.iterator();
                                    BeanFacturaCab datosDet = null;
                                    
                                    while(iterFactura.hasNext()) {
                                        datosDet = iterFactura.next();
                                        
                                        String montoIva = "";
                                        if (datosDet.getOFD_IDIMPUESTO().equals("5")) { // 5% 
                                            montoIva = datosDet.getOFD_IVA5();
                                        } else if(datosDet.getOFD_IDIMPUESTO().equals("10")) { // 10% 
                                            montoIva = datosDet.getOFD_IVA10();
                                        } else { // exenta 
                                            montoIva = "0";
                                        }
                                        
                                        System.out.println("____JSP_DETALLE_CONCEPTOS______");
                                        System.out.println("_   __ID_CONCEPTO:     _"+datosDet.getOFD_IDCONCEPTO());
                                        System.out.println("_   __CONCEPTO:        _"+datosDet.getOFD_DESCRIPCION_AUX());
                                        System.out.println("_   __TIPO_CONCEPTO:   _"+datosDet.getOFD_IDTIPOCONCEPTO());
                                        System.out.println("_______________________________");
                                        
                                        String idTipoConcepto = String.valueOf(datosDet.getOFD_IDTIPOCONCEPTO());
                                        String claseDescuento = ""; // variable que utilizo para poder identificar la linea del descuento, en caso de que haya alguna linea de descuento y asi poder cambiarle el color del font para que el usuario pueda idenficiar a la vista 
                                        if (idTipoConcepto.equals("4")) { // descuento 
                                            claseDescuento = "FD_IDTC";
                                        }
                                    %>
                                    <tr role="row">
                                        <td role="cell" class="GRICAB_CI"><%= datosDet.getOFD_ITEM() %></td>
                                        <td role="cell" class="GRICAB_CD <%=claseDescuento%>"><%= datosDet.getOFD_DESCRIPCION_AUX() %></td>
                                        <%//<td class="GRICAB_CD"><%= // FacCobDet_metodosFact.traerDescConcepto(String.valueOf(datosDet.getOFD_IDTIPOCONCEPTO()), String.valueOf(datosDet.getOFD_IDCONCEPTO())) ></td>%> <% // COMENTO ESTE METODO QUE TRAE LA DESCRIPCION DEPENDIENDO DEL IDTIPOCONCEPTO, PORQUE DESDE EL CONTROLADOR YA LE CARGO LA DESCRIPCION AL BEAN PORQUE DEFINO EN DOS DISTINTAS PAGINAS POR MEDIO DE DOS BOTONES EL CARGAR LA CUENTA CLIENTE O EL PRODUCTO LO QUE ME PERMITE BUSCAR LA DESCRIPCION CON MAS FACILIDAD %>
                                        <td role="cell" class="GRICAB_CP"><%= datosDet.getOFD_PRECIO()%></td>
                                        <td role="cell" class="GRICAB_CA"><%= datosDet.getOFD_CANTIDAD()%></td>
                                        <td role="cell" class="GRICAB_CIV"><%= datosDet.getOFD_IDIMPUESTO() %></td>
                                        <% // OBSERVACION: OCULTO VISUALMENTE EL MONTO IVA PORQUE NO ES NECESARIO POR EL MOMENTO QUE SE VISUALICE (29/07/2021) (SI SE DESCOMENTA SE DEBE DE CAMBIAR LOS VALORES DEL CSS / ESTA COMENTADO EN LA CLASE CUAL HAY QUE DESCOMENTAR) %>
                                        <%//<td role="cell" class="GRICAB_CMI"><%= montoIva ></td> %>
                                        <td role="cell" class="GRICAB_CST"><%= datosDet.getOFD_SUBTOTAL() %></td>
                                        <td role="cell" class="GRICAB_CE">
                                            <form action="CFSrv" method="post">
                                                <input type="text" name="tDFI" value="<%= datosDet.getOFD_ITEM() %>" class="form-control" /> <% // TXT DETALLE FACTURA ITEM  %>
                                                <input type="text" value="<%= datosDet.getOFD_IDIMPUESTO() %>" name="tLI" class="ocultarTextTable form-control" /> <% // tLI :  TXT LIQUIDACION IVA %>
                                                <input type="text" value="<%= montoIva %>" name="tTLI" class="ocultarTextTable form-control" /> <% // tTLI :  TXT TOTAL LIQUIDACION IVA %>
                                                <input type="text" value="<%= datosDet.getOFD_SUBTOTAL() %>" name="tST" class="ocultarTextTable form-control" /> <% // tST :  TXT SUBTOTAL  %>
                                                <input type="submit" name="accion" value="Eliminar Fila" class="btn btn-danger" />
                                            </form>
                                        </td>
                                    </tr>
                                    <%
                                    }
                                    %>
                                </tbody>
                            </table>
                        </div>

                        <div class="detBox1Lbl">
                            <div class="detBox12">
                                <div class="detBox12Box">
                                    <div class="detBox12l">
                                        <p>Liquidación del IVA: </p>
                                    </div>
                                </div>

                                <div class="detBox12Box">
                                    <div class="detBox12l">
                                        <p>(5%): </p>
                                    </div>
                                    <div class="detBox12t">
                                        <input type="text" value="<%= TXT_TOTAL_IVA5 %>" name="tTI5" readonly="readonly" class="form-control" />
                                    </div>
                                </div>

                                <div class="detBox12Box">
                                    <div class="detBox12l">
                                        <p>(10%): </p>
                                    </div>
                                    <div class="detBox12t">
                                        <input type="text" value="<%= TXT_TOTAL_IVA10 %>" name="tTI10" readonly="readonly" class="form-control" />
                                    </div>
                                </div>

                                <div class="detBox12Box">
                                    <div class="detBox12l">
                                        <p>TOTAL I.V.A.: </p>
                                    </div>
                                    <div class="detBox12t">
                                        <input type="text" value="<%= TXT_TOTAL_IVA %>" name="tTLI" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="detBox12">
                                <div class="detBox12Box">
                                    <div class="detBox12l">
                                        <p>TOTAL: </p>
                                    </div>
                                    <div class="detBox12t">
                                        <input type="text" value="<%= TXT_TOTAL %>" name="tTF" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="detBox1">
                            <div class="detBox123">
                                <div class="detBox12b2">
                                    <input type="submit" value="Volver a la Cabecera" name="accion" class="btn btn-warning ml-2 w-edit" />
                                </div>
                            <% // ----------------------------- BOTON DE INTERES ----------------------------- 
                            /*
                            * OBSERVACION_DEL_PORQUE_AGREGUE_ESTA_VARIABLE:
                                AGREGUE ESTA VARIABLE PARA PODER HABILITAR EL BOTON PARA PODER AGREGAR IMPUESTO EN CASO DE QUE EL USUARIO DESEE CANCELAR LA CUENTA 
                                ENTONCES LE MUESTRO LA OPCION PARA QUE PUEDA AGREGAR IMPUESTO Y NO TENGA QUE VOLVER A LA CABECERA Y VOLVER A CARGAR TODAS LAS CUENTAS PARA PODER AGREGAR IMPUESTO 
                            */
                                //String RES_EMP_HBLT_BTN_ADD_IMPU = (String) sesionDatosUsuario.getAttribute("RES_EMP_HBLT_BTN_ADD_IMPU");
                                //System.out.println("__JSP___RES_EMP_HBLT_BTN_ADD_IMPU:     :"+RES_EMP_HBLT_BTN_ADD_IMPU);
                                //
                                //if (RES_EMP_HBLT_BTN_ADD_IMPU == null || RES_EMP_HBLT_BTN_ADD_IMPU.isEmpty() == true || RES_EMP_HBLT_BTN_ADD_IMPU.equals("0")) {
                                //    //
                                //} else {
                            %>
                                <%//<div class="detBox12b">
                                  //  <input type="submit" value="Cargar Interés" name="accion" class="btn btn-info" />
                                //</div>%>
                            <%
                                //}
                            %>
                            <% // ----------------------------- BOTON DE DESCUENTO --------------------------- 
//                                <div class="detBox12b2">
//                                    <input type="submit" value="Cargar Descuento" name="accion" class="btn btn-primary ml-2" />
//                                </div>
                            %>
                                <div class="detBox12b">
                                    <% //  PENSAR EN AGREGAR CAMPOS DE TEXTO DE LA CABECERA PARA COBRAR  %>
                                    <input type="submit" value="Cobrar" name="accion" class="btn btn-success" />
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>