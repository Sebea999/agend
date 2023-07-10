<%-- 
    Document   : pagFactura_VerFact
    Created on : 28-jul-2021, 11:21:03
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">       --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleFacturaCobrar.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.min.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
            <%
                HttpSession VerFact_sesionDatos = request.getSession();
            %>
            <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CE_MENSAJE"); // CONTROLLER CLIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CE_TIPO_MENSAJE"); // CONTROLLER CLIENTE TIPO MENSAJE 
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
                    <h4 class="mainTitle">Ver Factura</h4>
                </div>
                <%
            /*
            * OBSERVACION: 
                VALIDACIONES PARA PODER CARGAR LOS BOTONES DE: "ANULAR", "CANCELAR", "LIQUIDAR", "EXPIRAR"; 
                COLOQUE ESTOS BOTONES ACA PARA PODER ACTIVAR EL MENSAJE DE CONSULTA YA QUE EN SUS PAGINAS NO PODIA MOSTRAR EL MENSAJE EN TODOS LOS CASOS SOLO LO MOSTRABA CUANDO FILTRABA POR UN EMPEÑO Y NO SE ACTIVABA EL MENSAJE CON LA LISTA COMPLETA 
            */
                System.out.println(".");System.out.println(".");
                System.out.println(".");
                // VARIABLES BANDERA 
                int varMostrarBtns = 0; // VARIABLE QUE UTILIZARE PARA SABER SI ALGUNA VARIABLE DE LA SESION ACTIVA ALGUN BOTON Y ASI MOSTRARLE EL FORM 
                
                // VARIABLES PARA COLOCAR LOS NOMBRES DE LOS EVENTOS DEL CONTROLADOR QUE CORRESPONDE 
                String varAccionBtn = "";
                
                /*
                OBSERVACION: LA IDEA ES QUE CADA BOTON TENGA UNA VARIABLE EN LA SESION Y ACTIVARLOS DESDE LOS CONTROLADORES Y PARA SABER DE QUE PAGINA VIENE O LA ACTIVO, ME FIJARE EN SU VALOR COMO EL BOTON DE "VOLVER ATRAS" 
                */
                // VARIABLES DE LA SESION 
                String btnAnular = (String) request.getAttribute("FACT_BTN_ANULAR");
                System.out.println("__JSP__BTN_ANULAR:      "+btnAnular);
                
                // VARIABLES PARA CARGAR EL MENSAJE DE JAVASCRIPT 
                String labelName = "";
                String labelMensajeModal = ""; // TEXTO QUE LE VA A MOSTRAR EL MENSAJE 
                String labelTextAdvertencia = "Esta acción ya no se podrá deshacer, Así que piénsalo bien.";  // TEXTO SECUNDARIO QUE APARECE ABAJO QUE LO USO PARA DAR UNA ADVERTENCIA AL USUARIO // OBS.: LO DEJO CARGADO Y EN CASO DE QUE NO QUIERA QUE ME MUESTRE EL MENSAJE DE ADVERTENCIA, EN EL IF DEL BOTON QUE QUIERA LO DEJO VACIO O LO CAMBIO 
                
                // VALIDACIONES PARA ACTIVAR LOS BOTONES 
                // BOTON DE ANULAR 
                if(btnAnular == null || btnAnular.isEmpty()) {
                    //
                } else if (btnAnular.equalsIgnoreCase("2")) { // PAG:   PANEL DE CONTROL / ANULAR FACTURA 
                    varMostrarBtns = 1;
                    varAccionBtn = "accionAF";
                    labelName = "Anular";
                    labelMensajeModal = "¿Esta seguro que desea anular la factura?";
                }
                
                String ID_CLIENTE = (String) sesionDatosUsuario.getAttribute("CF_TXT_CLIENTE_ID");
                String ID_FACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                
                System.out.println(".");
                System.out.println(".");
                System.out.println("___ID_CLIENTE:      :"+ID_CLIENTE);
                System.out.println("___ID_FACTURA:      :"+ID_FACTURA);
                System.out.println("___VAR_BANDERA_BOTONES:  "+varMostrarBtns);
                System.out.println("___VAR_ACCION_BTN:  "+varAccionBtn);
                System.out.println("___LABEL_NAME:  "+labelName);
                System.out.println("___LABEL_MENS:  "+labelMensajeModal);
                System.out.println("___LABEL_ADVE:  "+labelTextAdvertencia);
                System.out.println(".");
                System.out.println(".");
                
                if (varMostrarBtns > 0) { // CONTROLO LA VARIABLE QUE UTILIZO COMO BANDERA 
            %>
                <form action="CPCSrv" method="post" class="mb-2">
                    <input type="hidden" value="<%= ID_CLIENTE %>" name="tIC" class="form-control disNone" />
                    <input type="hidden" value="<%= ID_FACTURA %>" name="tIF" class="form-control disNone" />
                    <input type="submit" value="<%= labelName %>" id="btnPreAux" class="btn btn-danger" />
                    <input type="submit" value="<%= labelName %>" name="<%= varAccionBtn %>" id="btnAuxiliar" class="btn btn-warning disNone" />
                </form>
            <%
                }
                System.out.println(".");
                System.out.println(".");System.out.println(".");
            %>
                <form action="CFSrv" method="post" autocomplete="off">
                    <%
                //        HttpSession sesionDatosUsuario = request.getSession();
                        String TXT_NRO_FACTURA = "";
                        if ((String) request.getAttribute("CF_TXT_NRO_FACTURA") != null) {
                            TXT_NRO_FACTURA = (String) request.getAttribute("CF_TXT_NRO_FACTURA");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA") != null) {
                            TXT_NRO_FACTURA = (String) VerFact_sesionDatos.getAttribute("CF_TXT_NRO_FACTURA");
                        }
                        String CBX_TIPO_FACTURA = "";
                        if ((String) request.getAttribute("CF_CBX_TIPO_FACTURA") != null) {
                            CBX_TIPO_FACTURA = (String) request.getAttribute("CF_CBX_TIPO_FACTURA");
                        } else if(VerFact_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA") != null) {
                            CBX_TIPO_FACTURA = (String) VerFact_sesionDatos.getAttribute("CF_CBX_TIPO_FACTURA");
                        }
                        String TXT_ESTADO_FACTURA = "";
                        if ((String) request.getAttribute("CF_TXT_ESTADO_FACTURA") != null) {
                            TXT_ESTADO_FACTURA = (String) request.getAttribute("CF_TXT_ESTADO_FACTURA");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_ESTADO_FACTURA") != null) {
                            TXT_ESTADO_FACTURA = (String) VerFact_sesionDatos.getAttribute("CF_TXT_ESTADO_FACTURA");
                        }
                        String TXT_FECHA_FACTURA = "";
                        if ((String) request.getAttribute("CF_TXT_FECHA_FACTURA") != null) {
                            TXT_FECHA_FACTURA = (String) request.getAttribute("CF_TXT_FECHA_FACTURA");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA") != null) {
                            TXT_FECHA_FACTURA = (String) VerFact_sesionDatos.getAttribute("CF_TXT_FECHA_FACTURA");
                        }
                        String TXT_ID_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_ID") != null) {
                            TXT_ID_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_ID");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID") != null) {
                            TXT_ID_CLIENTE = (String) VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_ID");
                        }
                        String TXT_NAME_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_NAME") != null) {
                            TXT_NAME_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_NAME");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME") != null) {
                            TXT_NAME_CLIENTE = (String) VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_NAME");
                        }
                        String TXT_CINRO_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_CINRO") != null) {
                            TXT_CINRO_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_CINRO");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO") != null) {
                            TXT_CINRO_CLIENTE = (String) VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_CINRO");
                        }
                        String TXT_RAZONSOCIAL_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL") != null) {
                            TXT_RAZONSOCIAL_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL") != null) {
                            TXT_RAZONSOCIAL_CLIENTE = (String) VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_RAZONSOCIAL");
                        }
                        String TXT_RUC_CLIENTE = "";
                        if ((String) request.getAttribute("CF_TXT_CLIENTE_RUC") != null) {
                            TXT_RUC_CLIENTE = (String) request.getAttribute("CF_TXT_CLIENTE_RUC");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC") != null) {
                            TXT_RUC_CLIENTE = (String) VerFact_sesionDatos.getAttribute("CF_TXT_CLIENTE_RUC");
                        }
                        String TXT_ID_CLINICA_PERS = "";
                        if ((String) request.getAttribute("CF_TXT_IDCLINICA_PERS") != null) {
                            TXT_ID_CLINICA_PERS = (String) request.getAttribute("CF_TXT_IDCLINICA_PERS");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_IDCLINICA_PERS") != null) {
                            TXT_ID_CLINICA_PERS = (String) VerFact_sesionDatos.getAttribute("CF_TXT_IDCLINICA_PERS");
                        }
                        String TXT_NAME_CLINICA_PERS = "";
                        if ((String) request.getAttribute("CF_TXT_NAME_CLINICA_PERS") != null) {
                            TXT_NAME_CLINICA_PERS = (String) request.getAttribute("CF_TXT_NAME_CLINICA_PERS");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_NAME_CLINICA_PERS") != null) {
                            TXT_NAME_CLINICA_PERS = (String) VerFact_sesionDatos.getAttribute("CF_TXT_NAME_CLINICA_PERS");
                        }
                        String TXT_ID_CLINICA_FACT = "";
                        if ((String) request.getAttribute("CF_TXT_IDCLINICA_FACT") != null) {
                            TXT_ID_CLINICA_FACT = (String) request.getAttribute("CF_TXT_IDCLINICA_FACT");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_IDCLINICA_FACT") != null) {
                            TXT_ID_CLINICA_FACT = (String) VerFact_sesionDatos.getAttribute("CF_TXT_IDCLINICA_FACT");
                        }
                        String TXT_NAME_CLINICA_FACT = "";
                        if ((String) request.getAttribute("CF_TXT_NAME_CLINICA_FACT") != null) {
                            TXT_NAME_CLINICA_FACT = (String) request.getAttribute("CF_TXT_NAME_CLINICA_FACT");
                        } else if(VerFact_sesionDatos.getAttribute("CF_TXT_NAME_CLINICA_FACT") != null) {
                            TXT_NAME_CLINICA_FACT = (String) VerFact_sesionDatos.getAttribute("CF_TXT_NAME_CLINICA_FACT");
                        }

                        System.out.println("-------JSP-----------_VER__FACTURA_---------------");
                        System.out.println("__JSP____NRO_FACTURA:     :"+TXT_NRO_FACTURA);
                        System.out.println("__JSP____TIPO_FACTURA:    :"+CBX_TIPO_FACTURA);
                        System.out.println("__JSP____ESTADO_FACTU:    :"+TXT_ESTADO_FACTURA);
                        System.out.println("__JSP____FECHA-FACTURA:   :"+TXT_FECHA_FACTURA);
                        System.out.println("__JSP____ID-CLIENTE:      :"+TXT_ID_CLIENTE);
                        System.out.println("__JSP____NAME-CLIENTE:    :"+TXT_NAME_CLIENTE);
                        System.out.println("__JSP____CINRO-CLIENTE:   :"+TXT_CINRO_CLIENTE);
                        System.out.println("__JSP____RAZON-SOCIAL-CLIENTE:   :"+TXT_RAZONSOCIAL_CLIENTE);
                        System.out.println("__JSP____RUC-CLIENTE:     :"+TXT_RUC_CLIENTE);
                        System.out.println("__JSP____ID-CLINICA_PERS:  :"+TXT_ID_CLINICA_PERS);
                        System.out.println("__JSP____NAME-CLINICA_PERS::"+TXT_NAME_CLINICA_PERS);
                        System.out.println("__JSP____ID-CLINICA_FACT:  :"+TXT_ID_CLINICA_FACT);
                        System.out.println("__JSP____NAME-CLINICA_FACT::"+TXT_NAME_CLINICA_FACT);
                        System.out.println("-");
                        System.out.println("-");
                        System.out.println("-");
                    %>
                    <div class="box">
                        <div class="box1">
                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Nro. Factura: </p>
                                    </div>
                                    <div class="box3t">
                                        <input type="text" value="<%= TXT_NRO_FACTURA %>" name="tNF" id="tNF" readonly="readonly" class="form-control" />
                                    </div>
                                </div>

                                <div>
                                    <div class="box3l ml-4">
                                        <p>Fecha Factura: </p>
                                    </div>
                                    <div class="box3t">
                                        <input type="date" value="<%= TXT_FECHA_FACTURA %>" name="tFF" id="tFF" readonly="readonly" class="form-control" />
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
                                        <select name="cTF" id="cTF" readonly="readonly" class="form-control cbxTipoFact">
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
                                
                                <div>
                                    <div class="box3l ml-4">
                                        <p>Estado Factura: </p>
                                    </div>
                                    <div class="box3t11">
                                        <input type="text" value="<%= TXT_ESTADO_FACTURA %>" name="tEF" id="tEF" readonly="readonly" class="form-control" />
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
                                    <div class="box3l ml-4">
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
                                    <div class="box3l ml-4">
                                        <p>Nro. CI: </p>
                                    </div>
                                    <div class="box3t11">
                                        <input type="text" value="<%= TXT_CINRO_CLIENTE %>" name="tCNC" id="tCNC" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                            </div>

                            <div class="box2">
                                <div>
                                    <div class="box3l">
                                        <p>Clinica del Cliente: </p>
                                    </div>
                                    <div class="box3t1">
                                        <input type="hidden" value="<%= TXT_ID_CLINICA_PERS %>" name="tICliC" id="tICliC" class="form-control" />
                                        <input type="text" value="<%= TXT_NAME_CLINICA_PERS %>" name="tCliNC" id="tCliNC" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                
                                <div>
                                    <div class="box3l">
                                        <p>Clinica de la Facturación: </p>
                                    </div>
                                    <div class="box3t1">
                                        <input type="hidden" value="<%= TXT_ID_CLINICA_FACT %>" name="tICliF" id="tICliF" class="form-control" />
                                        <input type="text" value="<%= TXT_NAME_CLINICA_FACT %>" name="tCliNF" id="tCliNF" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    
                <%--<form action="CFSrv" method="post" autocomplete="off">--%>
                    <%
                    /*
                        TOTALES DE LA FACTURA 
                    */
                    String TXT_TOTAL_IVA5="", TXT_TOTAL_IVA10="", TXT_TOTAL_GRAV5="", TXT_TOTAL_GRAV10="", TXT_TOTAL_IVA="", TXT_TOTAL="";
                    
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA5") != null) {
                        TXT_TOTAL_IVA5 = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA5");
                    } else {
                        TXT_TOTAL_IVA5 = "0";
                    }
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV5") != null) {
                        TXT_TOTAL_GRAV5 = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV5");
                    } else {
                        TXT_TOTAL_GRAV5 = "0";
                    }
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA10") != null) {
                        TXT_TOTAL_IVA10 = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA10");
                    } else {
                        TXT_TOTAL_IVA10 = "0";
                    }
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV10") != null) {
                        TXT_TOTAL_GRAV10 = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_GRAV10");
                    } else {
                        TXT_TOTAL_GRAV10 = "0";
                    }
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA") != null) {
                        TXT_TOTAL_IVA = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL_IVA");
                    } else {
                        TXT_TOTAL_IVA = "0";
                    }
                    if ((String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL") != null) {
                        TXT_TOTAL = (String) VerFact_sesionDatos.getAttribute("CF_TXT_TOTAL");
                    } else {
                        TXT_TOTAL = "0";
                    }
                    
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
                                        <td role="columnheader" class="GRICAB_CMI">Monto IVA</td>
                                        <td role="columnheader" class="GRICAB_CST">SubTotal</td>
                                    </tr>
                                </thead>
                                <tbody role="rowgroup">
                                    <%
                                    // NRO DE ITEM DE LA LISTA 
                                    System.out.println("________________JSP_____VER_FACTURA_DETALLE___________________");
//                                    String CF_ITEM_LISTA = (String) VerFact_sesionDatos.getAttribute("CF_LISTA_ITEM");
//                                    System.out.println("__JSP__CF_ITEM_LISTA:   "+CF_ITEM_LISTA);
//                                    VerFact_sesionDatos.setAttribute("CF_LISTA_ITEM", CF_ITEM_LISTA);
                                    
                                    // LA LISTA 
                                    List<BeanFacturaCab> listaDatos;
                                    listaDatos = (List<BeanFacturaCab>) VerFact_sesionDatos.getAttribute("CF_LISTA_DETALLE");
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
                                    %>
                                    <tr role="row">
                                        <td role="cell" class="GRICAB_CI"><%= datosDet.getOFD_ITEM() %></td>
                                        <td role="cell" class="GRICAB_CD"><%= datosDet.getOFD_DESCRIPCION_AUX() %></td>
                                        <%//<td class="GRICAB_CD"><%= // FacCobDet_metodosFact.traerDescConcepto(String.valueOf(datosDet.getOFD_IDTIPOCONCEPTO()), String.valueOf(datosDet.getOFD_IDCONCEPTO())) ></td>%> <% // COMENTO ESTE METODO QUE TRAE LA DESCRIPCION DEPENDIENDO DEL IDTIPOCONCEPTO, PORQUE DESDE EL CONTROLADOR YA LE CARGO LA DESCRIPCION AL BEAN PORQUE DEFINO EN DOS DISTINTAS PAGINAS POR MEDIO DE DOS BOTONES EL CARGAR LA CUENTA CLIENTE O EL PRODUCTO LO QUE ME PERMITE BUSCAR LA DESCRIPCION CON MAS FACILIDAD %>
                                        <td role="cell" class="GRICAB_CP"><%= datosDet.getOFD_PRECIO()%></td>
                                        <td role="cell" class="GRICAB_CA"><%= datosDet.getOFD_CANTIDAD()%></td>
                                        <td role="cell" class="GRICAB_CIV"><%= datosDet.getOFD_IDIMPUESTO() %></td>
                                        <td role="cell" class="GRICAB_CMI"><%= montoIva %></td>
                                        <td role="cell" class="GRICAB_CST"><%= datosDet.getOFD_SUBTOTAL() %></td>
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
                                <%
                                    // VALIDACION PARA SABER A QUE PAGINA TENGO QUE REDIRECCIONAR AL USUARIO DARLE CLIC AL BOTON DE VOLVER ATRAS 
                                    String var_botonAtras = (String) sesionDatosUsuario.getAttribute("FACTURA_BOTON_VOLVER_ATRAS");
                                    System.out.println("_   _JSP__var_boton_atras:  :"+var_botonAtras);
                                    String paginaRedirect = "";
                                    int var_btnVolverAtras = 0; // VARIABLE QUE UTILIZO PARA MOSTRAR OTRO BLOQUE DE CODIGO HTML EN CASO DE QUE POR EJEMPLO VENGA DESDE LA PAGINA DE CUENTA CLIENTE Y PARA VOLVER ATRAS NO ES CARGANDO EL URL DE LA PAGINA PRINCIPAL YA QUE DESDE LA PAGINA DE CUENTAS LO QUE TIENE QUE VOLVER, Y POR ESO DEPENDIENDO DE LA PAGINA QUE VENGA UTILIZARE MAS CODIGO O MENOS 
                                    
                                    if (var_botonAtras == null) { // EN CASO DE QUE ESTE NULL, VOY A REDIRECCIONAR A LA PAGINA PRINCIPAL DE FACTURA NOMAS 
                                        paginaRedirect = urlFactura;
                                    } else if (var_botonAtras.equals("0")) { // FACTURA 
                                        paginaRedirect = urlFactura;
//                                    } else if (var_botonAtras.equals("1")) { // RESUMEN FACTURA 
//                                        paginaRedirect = urlResumenFacturas;
                                    } else if (var_botonAtras.equals("2")) { // ANULAR FACTURA 
                                        paginaRedirect = urlAnularFacturas;
                                    } else if (var_botonAtras.equals("3")) { // CUENTA CLIENTE // OBS.: AL REDIRECCIONAR DESDE LA PAGINA DE CUENTA CLIENTE DEBERIA DE DEVOLVERLE EL ID DEL PACIENTE Y NO REDIRECCIONAR A LA PAGINA PRINCIPAL 
                                        paginaRedirect = urlCtaCliente;
                                        var_btnVolverAtras = 1;
                                    }
                                    
                                    // BOTON DE VOLVER ATRAS 
                                    // OBSERVACION: COMO LA VARIABLE INSTANCIO CON CERO YA QUE EN SITUACIONES ESPECIFICAS ES QUE UTILIZARE OTRO BLOQUE DE CODIGO PARA VOLVER ATRAS; LO NORMAL SERIA QUE ENTRE EN EL IF Y QUE VUELVA ATRAS POR MEDIO DE LA URL 
                                    if(var_btnVolverAtras == 0) {
                                %>
                                    <a href="<%= paginaRedirect %>" class="btn btn-warning ml-2 w-edit" />Volver Atras</a>
                                </div>
                                <%
                                    } else { // Y EN EL CASO DE CUENTA CLIENTE HARIA ALGO ASI SOLO QUE CON OTRA NUMERACION PARA INSTANCIAR, PARA QUE DESDE EL CONTROLADOR DE FACTURA VUELVA ATRAS A LA PAGINA DE "VER CUENTA CLIENTE" 
                                %>
                                    <input type="hidden" value="<%= TXT_ID_CLIENTE %>" name="tIC" class="form-control disNone"/>
                                    <button type="submit" value="Volver Atras Cta" name="accion" class="btn btn-warning ml-2 w-edit">Volver Atras</button>
                                </div>
                                <%
                                    }
                                // BOTON DE COBRAR -- 
                                %>
                                <div class="detBox12b">
                                <%
                                    String IDFACTURA = (String) sesionDatosUsuario.getAttribute("ID_FACTURA");
                                    if (IDFACTURA.equals("") == true) { // SI EL IDFACTURA ESTUVIERA VACIO ENTONCES LE MOSTRARIA EL BOTON COBRAR, COSA QUE NO ESTARA YA QUE A ESTA PAGINA LE REDIRECCIONO PARA VER LAS FACTURAS GUARDADAS 
                                %>
                                    <input type="submit" value="Cobrar" name="accion" class="btn btn-success" />
                                <%
                                    }
                                %>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script type="text/javascript">
        <%
        //  CODIGO QUE UTILIZO PARA PODER INVOCAR AL MENSAJE PARA CONSULTARLE SI ESTA SEGURO DE QUE DESEA HACER LA ACCION 
        %>
<%//            function btnMensaje(){
//                    event.preventDefault();
//                    Swal.fire({
//                        title: "¿Esta seguro que desea anular el empeño?",
//                        text: "Esta acción ya no se podrá deshacer, Así que piénsalo bien.",
//                        type: "question",
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Si, estoy seguro',
//                        cancelButtonText: "Cancelar"
//                    }).then((result) => {
//                        if (result.value) {
//                            console.log('prueba exitosa - pre anular');
//                            document.getElementById('btnAuxiliar').click();
//                        }
//                        return false;
//                    });
//            }%>
            function btnMensaje(){
                    event.preventDefault();
                    Swal.fire({
                        title: "<%= labelMensajeModal %>",
                        text: "<%= labelTextAdvertencia %>",
                        type: "question",
                        showCancelButton: true,
                        confirmButtonColor: '#3085d6',
                        cancelButtonColor: '#d33',
                        confirmButtonText: 'Si, estoy seguro',
                        cancelButtonText: "Cancelar"
                    }).then((result) => {
                        if (result.value) {
                            console.log('prueba exitosa - pre anular');
                            document.getElementById('btnAuxiliar').click();
                        }
                        return false;
                    });
            }
            document.getElementById('btnPreAux').addEventListener('click', btnMensaje);
        <%
        // ----------------------------------------------------------------------------------------------
        %>
        </script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
    </body>
</html>