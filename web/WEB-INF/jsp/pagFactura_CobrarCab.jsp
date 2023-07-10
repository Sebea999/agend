<%-- 
    Document   : pagFactura_Cobrar
    Created on : 22-jun-2021, 10:09:45
    Author     : RYUU
--%>

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
                    <h4 class="mainTitle">Crear Factura</h4>
                </div>
                <%
                    HttpSession FacCobCab_sesionDatos = request.getSession();
                    ModelPersona FCC_MET_PERSONA = new ModelPersona(); // FACTURA COBRO CABECERA METODOS PERSONA 
                %>
                <form action="CFSrv" method="post" autocomplete="off">
                    <%
                //        HttpSession sesionDatosUsuario = request.getSession();
                        String TXT_NRO_FACTURA = "";
                        if ((String) request.getAttribute("CF_TXT_NRO_FACTURA") != null) {
                            TXT_NRO_FACTURA = (String) request.getAttribute("CF_TXT_NRO_FACTURA");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA") != null) {
                            TXT_NRO_FACTURA = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA");
                        }
                        String CBX_TIPO_FACTURA = "";
                        if ((String) request.getAttribute("CF_CBX_TIPO_FACTURA") != null) {
                            CBX_TIPO_FACTURA = (String) request.getAttribute("CF_CBX_TIPO_FACTURA");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA") != null) {
                            CBX_TIPO_FACTURA = (String) FacCobCab_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA");
                        }
                        String TXT_FECHA_FACTURA = "";
                        if ((String) request.getAttribute("CF_TXT_FECHA_FACTURA") != null) {
                            TXT_FECHA_FACTURA = (String) request.getAttribute("CF_TXT_FECHA_FACTURA");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA") != null) {
                            TXT_FECHA_FACTURA = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA");
                        }
                        String TXT_ID_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_ID") != null) {
                            TXT_ID_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_ID");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID") != null) {
                            TXT_ID_CLIENTE = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID");
                        }
                        String TXT_NAME_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_NAME") != null) {
                            TXT_NAME_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_NAME");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME") != null) {
                            TXT_NAME_CLIENTE = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME");
                        }
                        String TXT_CINRO_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_CINRO") != null) {
                            TXT_CINRO_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_CINRO");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO") != null) {
                            TXT_CINRO_CLIENTE = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO");
                        }
                        String TXT_RAZONSOCIAL_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL") != null) {
                            TXT_RAZONSOCIAL_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL") != null) {
                            TXT_RAZONSOCIAL_CLIENTE = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                        }
                        String TXT_RUC_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_RUC") != null) {
                            TXT_RUC_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_RUC");
                        } else if(FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC") != null) {
                            TXT_RUC_CLIENTE = (String) FacCobCab_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC");
                        }

                        System.out.println("-------JSP-----------FACTURA_COBRAR_CAB---------------");
                        System.out.println("__JSP____NRO_FACTURA:     :"+TXT_NRO_FACTURA);
                        System.out.println("__JSP____TIPO_FACTURA:    :"+CBX_TIPO_FACTURA);
                        System.out.println("__JSP____FECHA-FACTURA:   :"+TXT_FECHA_FACTURA);
                        System.out.println("__JSP____ID-CLIENTE:      :"+TXT_ID_CLIENTE);
                        System.out.println("__JSP____NAME-CLIENTE:    :"+TXT_NAME_CLIENTE);
                        System.out.println("__JSP____CINRO-CLIENTE:   :"+TXT_CINRO_CLIENTE);
                        System.out.println("__JSP____RAZON-SOCIAL-CLIENTE:   :"+TXT_RAZONSOCIAL_CLIENTE);
                        System.out.println("__JSP____RUC-CLIENTE:     :"+TXT_RUC_CLIENTE);
                    %>
                    <div class="box">
                        <div class="box1">
                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Nro. Factura: </p>
                                    </div>
                                    <div class="box3t">
                                        <input type="text" value="<%= TXT_NRO_FACTURA %>" name="tNF" id="tNF" class="form-control" />
                                    </div>
                                </div>

                                <div>
                                    <div class="box3l">
                                        <p>Fecha Factura: </p>
                                    </div>
                                    <div class="box3t">
                                        <input type="date" value="<%= TXT_FECHA_FACTURA %>" name="tFF" id="tFF" class="form-control" />
                                    </div>
                                </div>
                            </div>
                        </div>
                                
                        <div class="box1">
                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Tipo Factura: </p>
                                    </div>
                                    <div class="box3t">
                                        <select name="cTF" id="cTF" class="form-control cbxTipoFact">
                                        <%
                                        Map<String, String> listaTipoFact;
                                        listaTipoFact = metodosFactura.cargarComboTipoFact(CBX_TIPO_FACTURA);
                                        Set setCbxTipFac = listaTipoFact.entrySet();
                                        Iterator iCbxTipFac = setCbxTipFac.iterator();

                                        while(iCbxTipFac.hasNext()) {
                                            Map.Entry mapaTipoFact = (Map.Entry) iCbxTipFac.next();
                                        %>
                                        <option value="<%= mapaTipoFact.getKey() %>"><%= mapaTipoFact.getValue() %></option>
                                        <%
                                        }
                                        %>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="box1">
                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Razon Social: </p>
                                    </div>
                                    <div class="box3t1">
                                        <input type="text" value="<%= TXT_RAZONSOCIAL_CLIENTE %>" name="tCRS" id="tCRS" readonly="readonly" class="form-control" />
                                    </div>
                                </div>

                                <div>
                                    <div class="box3l">
                                        <p>RUC: </p>
                                    </div>
                                    <div class="box3t11">
                                        <input type="text" value="<%= TXT_RUC_CLIENTE %>" name="tCR" id="tCR" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Cliente: </p>
                                    </div>
                                    <div class="box3t1">
                                        <input type="hidden" value="<%= TXT_ID_CLIENTE %>" name="tIC" id="tIC" class="form-control" />
                                        <input type="text" value="<%= TXT_NAME_CLIENTE %>" name="tCN" id="tCN" readonly="readonly" class="form-control" />
                                    </div>
                                </div>

                                <div>
                                    <div class="box3l">
                                        <p>Nro. CI: </p>
                                    </div>
                                    <div class="box3t11">
                                        <input type="text" value="<%= TXT_CINRO_CLIENTE %>" name="tCNC" id="tCNC" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                
                                <div class="box3b">
                                    <%
                                    // VARIABLE QUE USO PARA MOSTRAR O NO EL BOTON PARA LLEVAR AL DETALLE DIRECTAMENTE 
                                    /*
                                    __OBSERVACION: ESTE BOTON SOLO LO MOSTRARE CUANDO HAYA ERRORES EN LA CABECERA AL MOMENTO DE ESTAR CONFIRMANDO EL COBRO, 
                                        Y AL REDIRECCIONAR A ESTE JSP PARA VOLVER A CONFIRMAR EL COBRO TENDRIA QUE VOLVER AL DETALLE PERO PARA ESO TENDRIA QUE VOLVER 
                                        A CARGAR TODOS LOS DATOS DEL DETALLE QUE CARGO, Y PARA EVITAR ESE PROCESO LARGO ENTONCES LE COLOCO ESTE BOTON QUE MANTENDRIA TODOS 
                                        LOS DATOS DEL DETALLE Y ASI LE PERMITIRIA COBRAR SIN VOLVER A PASAR POR CUALQUIERA DE LOS DOS BOTONES ("Cargar Cuenta Cliente" Y "Cargar Producto") PARA LLEGAR AL DETALLE.
                                    */
                                    String permitir_detalle = (String) sesionDatosUsuario.getAttribute("CF_VALI_MOSTRAR_BTN_DETALLE");
                                    System.out.println("____JSP___PREMITIR_VOLVER_AL_DETALLE:    :"+permitir_detalle);
                                    // SI EL BOTON PARA MOSTRAR EL DETALLE SE MUESTRA ENTONCES EL BOTON PARA CAMBIAR AL CLIENTE NO DEBERIA DE MOSTRARLE PARA EVITAR QUE EL USUARIO CAMBIE AL CLIENTE Y LUEGO VUELVA AL DETALLE 
                                    if (permitir_detalle == null || permitir_detalle.equals("0")) {
                                    %>
                                    <input type="checkbox" id="btn-cli" />
                                    <label for="btn-cli" class="btn btn-info btnCaCli ml-3">Cargar Cliente</label>
                                    <%
                                    /*
                                        VENTANA EMERGENTE PARA CARGAR EL CLIENTE ---------------
                                    */
                                    %>
                                    <form method="post" action="CFSrv">
                                        <div class="modalCl">
                                            <div class="modalPanelCl">
                                                <div class="panelHeader">
                                                    <div class="header01">Cargar Cliente</div>
                                                    <div class="header02"><label for="btn-cli" class="btnTi">Cerrar</label></div>
                                                </div>
                                                
                                                <div class="panelBodyCl">
                                                    <div class="boxBody">
                                                        <p class="boxLabel">Clientes:</p>
                                                    </div>
                                                    <div class="boxBody boxBodyMiddle">
                                                        <select name="cbxAddNewCli" class="form-control">
                                                        <%
                                                        Map<String, String> listaClientes1;
                                                        listaClientes1 = FCC_MET_PERSONA.cargarComboClientes("", sesionDatosUsuario); // LE PASO VACIO COMO PARAMETRO PARA QUE ME MUESTRE EL CLIENTE OCASIONAL 
                                                        Set setLisCli1 = listaClientes1.entrySet();
                                                        Iterator iterLisCli1 = setLisCli1.iterator();

                                                        while(iterLisCli1.hasNext()) {
                                                            Map.Entry mapaListCli1 = (Map.Entry) iterLisCli1.next();
                                                        %>
                                                            <option value="<%= mapaListCli1.getKey() %>"><%= mapaListCli1.getValue() %></option>
                                                        <%
                                                        }
                                                        %>
                                                        </select>
                                                    </div>
                                                    <div class="boxBody">
                                                        <input type="submit" value="Cargar Cliente" name="accion" class="btn btn-info" />
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <%
                                    // -----------------     END VENTANA EMERGENTE    -------------------------
                                    %>
                                    <%
                                    } // end if ctrl_btn_volver_detalle
                                    %>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="boxBtnFinally">
                        <%
                        String pagPrincFact = "factura.htm";
                        %>
                        <a href="<%= pagPrincFact %>" class="btn btn-danger ml-1 mt-4">Cancelar</a>
                        <form method="post" action="CFSrv"> <% // COLOCAR ESTE FORM ACA, AYUDA A QUE SE OBTENGAN CORRECTAMENTE LOS DATOS DE ESTOS CAMPOS DE TEXTOS EN LOS EVENTOS DE LOS DOS BOTONES (SI NO ESTA, PUEDE OBTENER MAL EL DATO TRAYENDO EL DATO DE OTRO CAMPO DE TEXTO) %>
                            <input type="hidden" name="tANF" id="tANF" value="<%= TXT_NRO_FACTURA %>" class="form-control" /> <% // tANF : TXT AUXILIAR NRO FACTURA  %>
                            <input type="hidden" name="cATF" id="cATF" value="<%= CBX_TIPO_FACTURA %>" class="form-control" /> <% // cATF : COMBOBOX AUXILIAR TIPO FACTURA  %>
                            <input type="hidden" name="tAFF" id="tAFF" value="<%= TXT_FECHA_FACTURA %>" class="form-control" /> <% // tAFF : TXT AUXILIAR FECHA FACTURA  %>
                            <input type="hidden" name="tACRS" id="tACRS" value="<%= TXT_RAZONSOCIAL_CLIENTE %>" class="form-control" /> <% // tACRS : TXT AUXILIAR CLIENTE RAZON SOCIAL  %>
                            <input type="hidden" name="tACR" id="tACR" value="<%= TXT_RUC_CLIENTE %>" class="form-control" /> <% // tACR : TXT AUXILIAR CLIENTE RUC  %>
                            <input type="hidden" name="tAIC" id="tAIC" value="<%= TXT_ID_CLIENTE %>" class="form-control" /> <% // tAIC : TXT AUXLIAR ID CLIENTE  %>
                            <input type="hidden" name="tACN" id="tACN" value="<%= TXT_NAME_CLIENTE %>" class="form-control" /> <% // tACN : TXT AUXILIAR CLIENTE NAME  %>
                            <input type="hidden" name="tACNC" id="tACNC" value="<%= TXT_CINRO_CLIENTE %>" class="form-control" /> <% // tACNC : TXT AUXILIAR CLIENTE NRO CI %>
                            
                            <input type="submit" value="Cargar Cuenta del Cliente" name="accion" class="btn btn-primary ml-2 mt-4" />
                            <input type="submit" value="Cargar Servicio" name="accion" class="btn btn-primary ml-2 mt-4" />
                            <%// -----------------  __BOTON_CARGAR_PRODUCTO__  -------------------- %>
                            <%//<input type="submit" value="Cargar Productos" name="accion" class="btn btn-primary ml-2 mt-4" />%>
                            <%// ------------------------------------------------------------------ %>
                            <%
//                            // VARIABLE QUE USO PARA MOSTRAR O NO EL BOTON PARA LLEVAR AL DETALLE DIRECTAMENTE 
//                            /*
//                            __OBSERVACION: ESTE BOTON SOLO LO MOSTRARE CUANDO HAYA ERRORES EN LA CABECERA AL MOMENTO DE ESTAR CONFIRMANDO EL COBRO, 
//                                Y AL REDIRECCIONAR A ESTE JSP PARA VOLVER A CONFIRMAR EL COBRO TENDRIA QUE VOLVER AL DETALLE PERO PARA ESO TENDRIA QUE VOLVER 
//                                A CARGAR TODOS LOS DATOS DEL DETALLE QUE CARGO, Y PARA EVITAR ESE PROCESO LARGO ENTONCES LE COLOCO ESTE BOTON QUE MANTENDRIA TODOS 
//                                LOS DATOS DEL DETALLE Y ASI LE PERMITIRIA COBRAR SIN VOLVER A PASAR POR CUALQUIERA DE LOS DOS BOTONES ("Cargar Cuenta Cliente" Y "Cargar Producto") PARA LLEGAR AL DETALLE.
//                            */
//                            String permitir_detalle = (String) sesionDatosUsuario.getAttribute("CF_VALI_MOSTRAR_BTN_DETALLE");
//                            System.out.println("____JSP___PREMITIR_VOLVER_AL_DETALLE:    :"+permitir_detalle);
//                            
                            /*
                            ESTA VARIABLE TENDRA LOS VALORES NULO O CERO, YA QUE NO ME IMPORTA SU VALOR O NO LO TENGO EN CUENTA 
                            PARA LAS DEMAS PAGINAS, Y SOLAMENTE ME IMPORTA PARA EL EVENTO DE COBRAR, DONDE CAMBIARIA SU VALOR PARA 
                            PODER MOSTRAR AL USUARIO LA POSIBILIDAD DE LLEGAR AL DETALLE PARA VOLVER A COBRAR LUEGO DE CORREGIR EL ERROR QUE SALTO EN LA CABECERA (YA QUE LE REDIRECCIONO A LA CABECERA PORQUE SALTO ALGUNA VALIDACION AL MOMENTO DE COBRAR) 
                            */
                            if (permitir_detalle == null || permitir_detalle.equals("0")) {
                                // NO HAGO NADA, NO LE MOSTRARIA 
                            } else 
                            if (permitir_detalle.equals("1")) {
                            %>
                            <input type="submit" value="Volver al Detalle" name="accion" name="accion" class="btn btn-warning ml-5 mt-4" />
                            <%
                            }
                            %>
                        </form>
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script type="text/javascript">
            document.getElementById("tNF").onchange = function(event) { <% // NRO FACTURA %>
                document.getElementById("tANF").value = event.target.value;
            };
            
            document.getElementById("cTF").onchange = function(event) { <% // TIPO FACTURA %>
                document.getElementById("cATF").value = event.target.value;
            };
            
            document.getElementById("tFF").onchange = function(event) { <% // FECHA FACTURA %>
                document.getElementById("tAFF").value = event.target.value;
            };
            
            document.getElementById("tCRS").onchange = function(event) { <% // CLIENTE RAZON SOCIAL %>
                document.getElementById("tACRS").value = event.target.value;
            };
            
            document.getElementById("tCR").onchange = function(event) { <% // CLIENTE RUC %>
                document.getElementById("tACR").value = event.target.value;
            };
            
            document.getElementById("tIC").onchange = function(event) { <% // ID CLIENTE %>
                document.getElementById("tAIC").value = event.target.value;
            };
            
            document.getElementById("tCN").onchange = function(event) { <% // CLIENTE NAME %>
                document.getElementById("tACN").value = event.target.value;
            };
            
            document.getElementById("tCNC").onchange = function(event) { <% // CLIENTE NRO CI %>
                document.getElementById("tACNC").value = event.target.value;
            };
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>