<%-- 
    Document   : pagRpt_Factura
    Created on : 05-ago-2021, 12:41:34
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanFacturaCab"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Ficha</title>
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
                <%
                System.out.println("[+] JSP-REPORTE_FICHA_PACIENTE__N__");
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CR_RFAPN_MENSAJE"); // CONTROLLER REPORTES REPORTE FICHA ATENCION PACIENTE (NUTRI) MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CR_RFAPN_TIPO_MENSAJE"); // CONTROLLER REPORTES REPORTE FICHA ATENCION PACIENTE (NUTRI) TIPO MENSAJE 
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
                %>
                <div>
                    <h4 class="mainTitle">REPORTE FICHAS DE ATENCION DEL PACIENTE</h4>
                </div>
                <form action="CRSrv" method="post" autocomplete="off">
                    <div class="mainFilter mt-2">
                        <%
                        // RECUPERAMOS LOS DATOS DEL FILTRO EN CASO DE QUE SE HAGA PARA VOLVER A MOSTRAR Y SI NO, ENTONCES PASARIA DATOS VACIOS 
                        String cbxMostrar = (String) request.getAttribute("CR_RFAPN_CBX_MOSTRAR");
                        if (cbxMostrar == null || cbxMostrar.isEmpty()) {
                            cbxMostrar = "";
                        }
                        System.out.println("[*] cbx_mostrar:  :"+cbxMostrar);
                        String txtFiltro = (String) request.getAttribute("CR_RFAPN_TXT_BUSCAR");
                        if (txtFiltro == null || txtFiltro.isEmpty()) {
                            txtFiltro = "";
                        }
                        System.out.println("[*] txt_filtro:   :"+txtFiltro);
                        %>
                        <div class="mainFilter">
                            <div class="mainFilterBox">
                                <div>
                                    <p class="mainLabelSearch">Mostrar:</p>
                                    <select id="cM" name="cM" onchange="functCm()" class="form-control">
                                    <%
                                    // Combo Filter Limit Registro
                                    Map<String, String> cbxFilterLimit = new LinkedHashMap();
                                    cbxFilterLimit = metodosIniSes.cargarFilLimitReg(cbxFilterLimit, cbxMostrar); 
                                    Set setListFilLim = cbxFilterLimit.entrySet();
                                    Iterator iListFilLim = setListFilLim.iterator();

                                    while(iListFilLim.hasNext()) {
                                        Map.Entry limitMap = (Map.Entry) iListFilLim.next();
                                    %>
                                        <option value="<%= limitMap.getKey() %>"><%= limitMap.getValue() %></option>
                                    <%
                                    }
                                    %>
                                    </select>
                                    <p>registros</p>
                                </div>
                            </div>
                        </div>
                        <%
                        String FILTRO_FECHA_INI = (String) request.getAttribute("CR_RFAPN_TXT_FILTRAR_FEC_INI");
                        if (FILTRO_FECHA_INI == null || FILTRO_FECHA_INI.isEmpty()) {
                            FILTRO_FECHA_INI = "";
                        }
                        String FILTRO_FECHA_FIN = (String) request.getAttribute("CR_RFAPN_TXT_FILTRAR_FEC_FIN");
                        if (FILTRO_FECHA_FIN == null || FILTRO_FECHA_FIN.isEmpty()) {
                            FILTRO_FECHA_FIN = "";
                        }
                        String FILTRO_CLIENTE_ID = (String) request.getAttribute("CR_RFAPN_TXT_FILTRAR_CLIE_ID");
                        if (FILTRO_CLIENTE_ID == null || FILTRO_CLIENTE_ID.isEmpty()) {
                            FILTRO_CLIENTE_ID = "";
                        }
                        String FILTRO_ESTADO_FIC = (String) request.getAttribute("CR_RFAPN_TXT_FILTRAR_ESTADO");
                        if (FILTRO_ESTADO_FIC == null || FILTRO_ESTADO_FIC.isEmpty()) {
                            FILTRO_ESTADO_FIC = "";
                        }
                        System.out.println("[*] FILTRO_FECHA_INI:   "+FILTRO_FECHA_INI);
                        System.out.println("[*] FILTRO_FECHA_FIN:   "+FILTRO_FECHA_FIN);
                        System.out.println("[*] FILTRO_CLIENTE_ID:  "+FILTRO_CLIENTE_ID);
                        System.out.println("[*] FILTRO_ESTADO_FIC:  "+FILTRO_ESTADO_FIC);
                        %>
                        <div class="mainFilterBox mt-2">
                            <div class="filterDate">
                                <div>
                                    <p class="mainLabelSearch">Fecha Inicio:</p>
                                    <input type="date" value="<%= FILTRO_FECHA_INI %>" name="tFFI" id="tFFI" onchange="functFecIni()" class="form-control" />
                                </div>
                                <div>
                                    <p class="mainLabelSearch">Fecha Fin:</p>
                                    <input type="date" value="<%= FILTRO_FECHA_FIN %>" name="tFFF" id="tFFF" onchange="functFecFin()" class="form-control" />
                                </div>
                            </div>
                        </div>
                        <div class="mainFilterBox mt-2">
                            <div class="">
                                <div>
                                    <p class="mainLabelSearch">Paciente:</p>
                                    <%
                                    String CHECK_CLIENTE = "checked=\"checked\" ";
                                /*
                                * OBSERVACION_DEL_PORQUE_HAY_DOS_VARIABLES_Y_NO_UNA_SOLA_CON_EL_MISMO_NOMBRE: 
                                    TENGO DOS VARIABLES PORQUE UNA LA TENGO EN EL MAV Y OTRA EN EL METODO DO_POST DEL CONTROLADOR, NECESITO LA PRIMERA LA QUE SE ENCUENTRA EN EL METODO MAV DEL REQUEST_MAPPING 
                                    PARA QUE AL ABRIR EL FORMULARIO ME MUESTRE EL CHECK MARCADO Y NO DESMARCADO, Y LUEGO NECESITO EL OTRO PARA IR GUIANDO AL CHECK EN CADA ACCION O EVENTO QUE SE REALICE, Y NO PUEDO TENER 
                                    UNO SOLO CON EL MISMO NOMBRE PORQUE NO ME HACE CASO, EL JSP SOLO HACE CASO AL "ON" QUE LE CARGO EN EL MAV Y LUEGO CUANDO MODIFICO CON "OFF" EN ALGUN EVENTO DEL DO_POST, EL JSP YA NO ME HACE CASO
                                    Y ME DEJA EL VALOR CON "ON", ENTONCES POR ESO CREE LA SEGUNDA VARIABLE QUE AL INICIAR ESTARA NULO Y AHI HARE CASO A LA PRIMERA VARIABLE, PERO LUEGO CUANDO SE REALICE ALGUN EVENTO EN LA PAGINA SE CARGARA 
                                    Y AHI TOMARE SU VALOR YA QUE ESTE PUEDE IR CAMBIANDO ENTRE "ON" Y "OFF" O NULO, PERO EL PRIMERO SIEMPRE SE MANTIENE EN "ON" 
                                */
                                    String CHECK_FILTER_CLIENTE = (String) request.getAttribute("CR_RFAPN_CHECK_FILTRAR_CLIE"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                    String CHECK_FILTER_CLIENTE01 = (String) request.getAttribute("CR_RFAPN_CHECK_FILTRAR_CLIE_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
                                    if (CHECK_FILTER_CLIENTE01 == null) {
                                        if (CHECK_FILTER_CLIENTE == null || CHECK_FILTER_CLIENTE.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_CLIENTE = "";
                                        } else if (CHECK_FILTER_CLIENTE.equalsIgnoreCase("ON")) {
                                            CHECK_CLIENTE = "checked=\"checked\" ";
                                        }
                                    } else {
                                        if (CHECK_FILTER_CLIENTE01 == null || CHECK_FILTER_CLIENTE01.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_CLIENTE = "";
                                        } else if (CHECK_FILTER_CLIENTE01.equalsIgnoreCase("ON")) {
                                            CHECK_CLIENTE = "checked=\"checked\" ";
                                        }
                                    }
                                    %>
                                    <input type="checkbox" <%=CHECK_CLIENTE%>id="check_cli" name="check_cli" />
                                    <div class="combocliente">
                                    <select name="cbxAddNewCli" id="cbxAddNewCli" class="cbxRFCli form-control" onchange="functClie()">
                                        <%
                                        Map<String, String> listaClientes;
                                        listaClientes = metodosPersona.traerComboClientesConFact(sesionDatosUsuario, FILTRO_CLIENTE_ID);
                                        Set setLisCli = listaClientes.entrySet();
                                        Iterator iterLisCli = setLisCli.iterator();
                                        
                                        while(iterLisCli.hasNext()) {
                                            Map.Entry mapaListCli = (Map.Entry) iterLisCli.next();
                                        %>
                                        <option value="<%= mapaListCli.getKey() %>"><%= mapaListCli.getValue() %></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                    </div>
                                    <label for="check_cli" class="btn btn-light">TODOS</label>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mainFilterBox mt-2 boxFiltroBtn">
                            <div class="/*mainFilterBox*/">
                                <p class="mainLabelSearch">Estado:</p>
                                <div class="combomedico">
                                    <select class="cbxEstado form-control" id="cE" name="cE" onchange="functCbxEst()">
                                    <%
                                    Map<String, String> listaEstado;
                                    listaEstado = metodosFichaAtencionPacNutri.cargarComboEstadoFA(FILTRO_ESTADO_FIC);
                                    Set setCbxEstado = listaEstado.entrySet();
                                    Iterator iCbxEstado = setCbxEstado.iterator();

                                    while(iCbxEstado.hasNext()) {
                                        Map.Entry mapCbxEst = (Map.Entry) iCbxEstado.next();
                                    %>
                                    <option value="<%= mapCbxEst.getKey() %>"><%= mapCbxEst.getValue() %></option>
                                    <%
                                    }
                                    %>
                                    </select>
                                </div>
                            </div>
                        </div>
                        
                        <div class="mainFilterBox mt-2 boxFiltroBtn">
                            <div>
                                <p class="mainLabelSearch">Buscar:</p>
                                <input type="text" name="txB" value="<%= txtFiltro %>" onchange="functTxB()" class="form-control" />
                            </div>
                            <div>
                                
                                <input type="submit" name="accionRptFichaAPN" value="Filtrar" id="Filtrar" class="btn btn-primary mb-2 mr-2" />
                                <input type="submit" name="accionRptFichaAPN" value="Limpiar" id="Limpiar" class="btn btn-primary mb-2" />
<%//                                <!--<a href="<%=urlReporteFactura>" class="btn btn-primary mb-2">Limpiar</a>-->%>
                            </div>
                        </div>
                    </div>
                </form>
                
                
                <%--<form action="CRSrv" method="post" autocomplete="off">--%>
                    <div class="divTable" id="divTable">
                        <table role="table" class="tableFilterListFic table-striped">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="RFAPN_CP">Cód. Pac.</td>
                                    <td role="columnheader" class="RFAPN_CPNA">Nombre/Apellido</td>
                                    <td role="columnheader" class="RFAPN_CPCI">Nro. CI</td>
                                    <td role="columnheader" class="RFAPN_CF">Cód. Ficha</td>
                                    <!--<td role="columnheader" class="RFAPN_CC">Clinica</td>-->
                                    <td role="columnheader" class="RFAPN_CFH">Fecha y Hora</td>
                                    <td role="columnheader" class="RFAPN_CMCF">Motivo de la Consulta</td>
                                    <!--<td role="columnheader" class="RFAPN_C">Peso</td>-->
                                    <td role="columnheader" class="RFAPN_CPP">Peso</td>
                                    <td role="columnheader" class="RFAPN_CPG">Porc. Grasa</td>
                                    <!--<td role="columnheader" class="RFAPN_C">Edad M.</td>-->
                                    <td role="columnheader" class="RFAPN_CPM">Porc. Musc.</td>
                                    <!--<td role="columnheader" class="RFAPN_C">IMC</td>-->
                                    <!--<td role="columnheader" class="RFAPN_C">Visceral</td>-->
                                    <td role="columnheader" class="RFAPN_CFE">Estado</td>
                                    <td role="columnheader" class="RFAPN_CIP">Imprimir PDF</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
                                    // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
                                    String NRO_PAG = "";
                                    if (sesionDatosUsuario.getAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL") == null) {
                                        NRO_PAG = "1";
                                    } else {
                                        NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL");
                                    }
                                    System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                                    
                                    List<BeanFichaAtePaciente> listaDatos = null;
                                    Iterator<BeanFichaAtePaciente> iterDatos=null;
                                    if (((List<BeanFichaAtePaciente>)request.getAttribute("CR_RFAPN_LISTA_DE_FICHAS")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        listaDatos = (List<BeanFichaAtePaciente>) request.getAttribute("CR_RFAPN_LISTA_DE_FICHAS");
//                                    } else { // y si no entonces le decimos que carge nomas a traves del metodo que le pasamos 
//                                        String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
//                                        listaDatos = metodosFactura.cargar_grilla_paginacion_rtp_fact(sesionDatosUsuario, NRO_PAG, cant_min_fija, "");
//                                        sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // CON ESTO RESETEO LA BANDERA QUE UTILIZO PARA LA PAGINACION 
//                                    }
                                        iterDatos = listaDatos.iterator(); // agrego el iterador dentro del if para evitar que salte la excepcion NullPointer por no recuperar datos de la lista.-
                                    }
//                                    Iterator<BeanFichaAtePaciente> iterDatos = listaDatos.iterator();
                                    BeanFichaAtePaciente datosFicha = null;
//                                    BeanPersona datosPersona = new BeanPersona();
                                    
                                    if(listaDatos != null) {
                                        while(iterDatos.hasNext()) {
                                            datosFicha = iterDatos.next();
    //                                        datosPersona = metodosPersona.datosBasicosPersona(datosPersona, String.valueOf(datosBean.getOF_IDCLIENTE()));
    //                                        String IDFACTURA = String.valueOf(datosBean.getOF_IDFACTURA());
                                %>
                                <tr role="row">
                                    <td role="cell" class="RFAPN_CP">
                                        <%= datosFicha.getOFPN_IDPACIENTE() %>
                                    </td>
                                    <td role="cell" class="RFAPN_CPNA">
                                        <%= datosFicha.getOFPN_PAC_APELLIDO()+" "+datosFicha.getOFPN_PAC_NOMBRE() %>
                                    </td>
                                    <td role="cell" class="RFAPN_CPCI">
                                        <%= datosFicha.getOFPN_PAC_NROCI() %>
                                    </td>
                                    <td role="cell" class="RFAPN_CF">
                                        <%= datosFicha.getOFPN_IDFICHAPAC() %>
                                    </td>
<!--                                    <td role="cell" class="RFAPN_CC">
                                        <%--= datosFicha.getOFPN_DESC_CLINICA() --%>
                                    </td>-->
                                    <td role="cell" class="RFAPN_CFH">
                                        <%= datosFicha.getOFPN_FECHA_FICHA_ATE() %>
                                    </td>
                                    <td role="cell" class="RFAPN_CMCF">
                                        <%= datosFicha.getOFPN_MOTIVO_DE_LA_CONSULTA() %>
                                    </td>
                                    <!--<td role="columnheader" class="RFAPN_C">Peso</td>-->
                                    <td role="cell" class="RFAPN_CPP">
                                        <%= datosFicha.getOFPN_PESO() %>
                                    </td>
                                    <td role="cell" class="RFAPN_CPG">
                                        <%= datosFicha.getOFPN_PORC_GRASA() %>
                                    </td>
                                    <!--<td role="columnheader" class="RFAPN_C">Edad M.</td>-->
                                    <td role="cell" class="RFAPN_CPM">
                                        <%= datosFicha.getOFPN_PORC_MUSCULO() %>
                                    </td>
                                    <!--<td role="columnheader" class="RFAPN_C">IMC</td>-->
                                    <!--<td role="columnheader" class="RFAPN_C">Visceral</td>-->
                                    <td role="cell" class="RFAPN_CFE">
                                        <%= metodosIniSes.devolverTextEstadoAgend(datosFicha.getOFPN_ESTADO()) %>
                                    </td>
                                    <td role="cell" class="RFAPN_CIP">
                                        <form action="pdf_rpt_ficha" target="_blank" method="post">
                                            <input type="text" value="<%= datosFicha.getOFPN_IDFICHAPAC()%>" name="tRFPNIF" class="form-control" />
                                            <input type="text" value="<%= datosFicha.getOFPN_IDPACIENTE()%>" name="tRFPNIP" class="form-control" />
                                            <input type="submit" value="Imprimir" class="btn btn-light" />
                                        </form>
                                    </td>
                                </tr>
                                <%
                                        }//end-while.
                                    }//end-if-verify-list_is-not_null.
                                %>
                            </tbody>
                        </table>
                        <div class="container-fluid text-right mt-4 mr-2 mb-3">
                        <form action="CRSrv" method="post" autocomplete="off">
                        <%
                            /*
                             * OBSERVACION: 
                                UTILIZO ESTAS CAJAS DE TEXTOS AUXILIARES PARA PODER VOLVER A REALIZAR EL FILTRO EN CASO DE UNA PAGINACION 
                                YA QUE LOS CAMPOS DE TEXTOS VISIBLES NO SE ENCUENTRAN DENTRO DEL MISMO FOR Y POR ESO NECESITO ESTOS CAMPOS DE TEXTOS AUXILIARES, 
                                QUE SON CARGADOS A TRAVES DE UNOS METODOS CHANGE DE JAVASCRIPT PARA PODER CAPTAR EL CAMBIO EN ALGUN FILTRO Y PODER DEVOLVERLO OTRA VEZ (EN CASO DE QUE FILTRE EN MEDIO DE UNA PAGINACION) 
                            */
                        %>
                            <input type="hidden" value="<%= cbxMostrar %>" id="tAcM" name="tAcM" class="form-control disNone"/>
                            <input type="hidden" value="<%= txtFiltro %>" id="tAtxB" name="tAtxB" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_FECHA_INI %>" id="tAtFFI" name="tAtFFI" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_FECHA_FIN %>" id="tAtFFF" name="tAtFFF" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_CLIENTE_ID %>" id="tAcbxAddNewCli" name="tAcbxAddNewCli" class="form-control disNone"/>
                            <input type="hidden" value="<%= CHECK_CLIENTE %>" id="tAcheck_cli" name="tAcheck_cli" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_ESTADO_FIC %>" id="tAcE" name="tAcE" class="form-control disNone"/>
                        <%
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("-       -        JSP_PAGINACION        -        -");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
                        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES QUE TENGO SEPARANDO POR UNA CANTIDAD DINAMICA DE REGISTROS QUE EL USUARIO QUIERE VER EN LA GRILLA 
                        String cant_total_btn_lista = (String) sesionDatosUsuario.getAttribute("PAG_RPT_FICHA_APN_CANT_TOTAL_BTNS"); // PAGINA PACIENTE CANTIDAD TOTAL DE BOTONES 
                        if (cant_total_btn_lista == null || cant_total_btn_lista.isEmpty()) {
                            cant_total_btn_lista = "1";
                        }
//                        System.out.println("_   __JSP___TOTAL_BTN_LISTA:   :"+cant_total_btn_lista);
                        
                        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE CLICS QUE EL USUARIO DIO PARA LA DERECHA PARA VER LOS DEMAS BOTONES DE PAGINAS, EMPIEZA CON 1 Y POR CADA CLIC AUMENTO UNO Y SI SE DA CLIC A LA IZQUIERDA LE RESTO UNO HASTA QUE VUELVA A SU NUMERO BASE (1) 
                        String CANT_CLIC_DERE = ""; // PAG_PAC_CANT_BTN_DERE_CLIC : PAGINA PACIENTE CANTIDAD BTN DERECHA (VER MAS >>) CLIC 
                        if ((String) sesionDatosUsuario.getAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC") == null) { // SI ESTA NULO POR ALGUNA RAZON DE INSTANCIA, ENTONCES LE COLOCO 1 COMO MINIMO VALOR ACEPTABLE Y SI NO ESTA NULO ENTONCES LE DEJO CON EL VALOR QUE TIENE 
                            CANT_CLIC_DERE = "1";
                        } else {
                            CANT_CLIC_DERE = (String) sesionDatosUsuario.getAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC");
                        }
//                        System.out.println("_   __JSP___CANT_CLIC_DERECHA:    :"+CANT_CLIC_DERE);
                        
                        // VARIABLE QUE UTILIZARE PARA MOSTRAR AL BOTON ACTIVO DEACUERDO AL NUMERO DE LISTA ACTUAL QUE SE ESTA MOSTRANDO 
                        String btn_css_active = "";
                        // VARIABLE QUE UTILIZO PARA COLOCAR AL BOTON ACTIVO EN UNA ETIQUETA QUE NO ENVIE UNA SOLICITUD AL SERVLET Y ESTE A SU VEZ NO VUELVA A CONSULTAR A LA BASE PARA EVITAR QUE SE SOBRECARGUE DE SOLICITUDES, YA QUE NO TIENE SENTIDO VOLVER A APRETAR EL MISMO INDICE Y SOLO A LOS DEMAS BOTONES LE CARGO UN BTN SUBMIT PARA QUE PUEDA SOLICITAR LA VISTA DE SU LITA 
                        String btn_submit = "";
                        
                        // POR MEDIO DE LA CANTIDAD DE CLICS MULTIPLICANDO POR 3 (LA CANTIDAD DE BOTONES A MOSTRAR) ENCUENTRO EL NUMERO DEL TERCER BOTON (EJEMPLO: 2 CLIC (*3) Y EL TERCER BOTON SERIA 6) 
                        int btn_max_mostrar = Integer.parseInt(CANT_CLIC_DERE) * 3;
//                        System.out.println("_   _JSP__btn_max_mostrar:  :"+btn_max_mostrar);
                        // AL TENER EL NUMERO DEL TERCER BOTON A MOSTRAR, RESTANDO DOS SÉ CUAL ES EL NUMERO DEL PRIMER BOTON A MOSTRAR 
                        int btn_min_mostrar = btn_max_mostrar - 2;
//                        System.out.println("_   _JSP__btn_min_mostrar:  :"+btn_min_mostrar);
                        
                        /*
                         * OBSERVACION: DE ESTE BLOQUE 
                            SI SE FILTRA Y SE CAMBIA LA CANTIDAD DE BOTONES Y EL NRO DE PAGINA ACTUAL, SE REALIZA CUALQUIER ACCION COMO LA DE AÑADIR UN NUEVO REGISTRO O VER UNO 
                            EL NRO DE PAG ACTUAL DE LA PAGINA PRINCIPAL SE QUEDA CON EL NRO ANTERIOR (EJ.: 4) Y SE VUELVE ATRAS CON EL BOTON DE LA PAGINA O LA FLECHA DEL NAVEGADOR 
                            EN EL CARGA GRILLA SE ESTABLECE LA PRIMERA PAGINA (1) PERO EN EL JSP SE RECUPERA (4) Y EN LAS IMPRESIONES DE ESCRITORIO PUEDO VER EN EL METODO DE CARGA GRILLA ESTA DEFINIDA (1) 
                            ENTONCES CON ESTE BLOQUE DE CODIGO CORRIGO LA PAGINA ACTUAL PARA QUE EL IF DE ABAJO DETECTE A LA PAGINA ACTUAL Y LA MARQUE AL BOTON 
                        * SOLUCIONES A ESTE PROBLEMA: 
                            AGREGUE UN BLOQUE DE CODIGO MAS EN EL CARGA GRILLA PARA FORMATEAR EL CLIC DERECHO CONTROLANDO EL NRO DE PAG ACTUAL 
                            Y AGREGUE ESTE BLOQUE DE CODIGO ACA PARA FORMATEAR LA VARIABLE DEL NRO DE PAG ACTUAL A MOSTRAR, YA QUE DESDE EL MODELO SE ACTUALIZA PERO EN EL JSP SE VE EL VALOR ANTERIOR Y POR ESA RAZON NO SE MARCA EL BOTON DE LA PAGINA ACTUAL MOSTRANDO, Y CON ESTA CONDICIONAL CONSIGO QUE ME MARQUE LA PAGINA SELECCIONADA 
                        */
                        System.out.println(".----------------------------------");
                        System.out.println("_  _NRO_PAG:  :"+NRO_PAG);
                        System.out.println("_  _BTN_MAX:  :"+btn_max_mostrar);
                        System.out.println("_  _BTN_MIN:  :"+btn_min_mostrar);
                        System.out.println("_  _CANT_CLICS_DERE:  :"+CANT_CLIC_DERE);
                        if ((Integer.parseInt(NRO_PAG) > btn_max_mostrar || Integer.parseInt(NRO_PAG) < btn_min_mostrar)) {
                            System.out.println("------IF------");
                            NRO_PAG = "1";
                        } else {
                            System.out.println("-----else------");
                        }
                        System.out.println(".----------------------------------");
                        
                        // SI EL BOTON MINIMO A MOSTRAR ES MAYOR A UNO ENTONCES VOY A MOSTRARLE EL BOTON PARA RETROCEDER ATRAS, PERO SI ES IGUAL A UNO ES PORQUE ESTA EN EL BLOQUE DE LOS PRIMERO TRES BOTONES Y AHI NO TIENE SENTIDO QUE LE MUESTRE EL BOTON PARA IRSE MAS ATRAS  
                        if (btn_min_mostrar > 1) {
                        %>
                            <input type="submit" value="<<" name="accionRptFichaAPN" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                        <%
                        }
                        
                        // FOR QUE HAGO RECORRIENDO LA CANTIDAD TOTAL DE BOTONES QUE TENGO PARA ENCONTRAR AL BOTON DE LA PAGINA O DE LA LISTA ACTUAL QUE EL USUARIO ESTA VIENDO 
                        for(int i=1; i <= Integer.parseInt(cant_total_btn_lista); i++) {
//                            System.out.println(".");
//                            System.out.println(".");
//                            System.out.println("_  _____FOR____ITEM_TOTAL_______ i : "+i);
                            
//                            System.out.println("____i("+i+")  >  btn_max_mostrar("+i+")_____");
                            // SI LA CANTIDAD DE BOTONES ACTUAL QUE SE ESTA RECORRIENDO ES MAYOR A LA CANTIDAD DE BOTONES MAXIMA A MOSTRAR ENTONCES CORTO EL FOR PARA NO GASTAR RECURSOS RECORRIENDO EL FOR 
                            if (i > btn_max_mostrar) {
                                break;
                            }
                            
//                            System.out.println("__  _JPS_____if( i("+i+") <= resul_multi_primo("+btn_max_mostrar+") && i("+i+") >= pag_min_mostrar("+btn_min_mostrar+")   )____");
                            
                            // VALIDACION PARA MOSTRAR LOS TRES BOTONES 
                            // SI LA FILA ACTUAL ES MENOR AL NRO DEL BOTON ULTIMO A MOSTRAR Y ES MAYOR O IGUAL AL NUMERO MINIMO DEL BOTON A MOSTRAR 
                            if (i <= btn_max_mostrar && i >= btn_min_mostrar) {
                                // CONTROLO PARA PODER DARLE AL BOTON UNA IMAGEN DISTINTA PARA QUE EL USUARIO SEPA QUE LISTA DE LOS BOTONES SE ENCUENTRA MOSTRANDO LOS DATOS EN LA GRILLA  
                                if (NRO_PAG.equals(""+i+"")) {
                                    btn_css_active = "btn btn-secondary disabled";
                                    btn_submit = "0";
                                } else { // 
                                    btn_css_active = "btn btn-outline-secondary";
                                    btn_submit = "1";
                                }
                                
                            // CONTROLO EL VALOR FINAL DE LA VARIABLE PARA SABER SI LE MUESTRO UN BOTON SUBMIT O UN BOTON VACIO 
                            if (btn_submit.equals("1")) {
                            %>
                            <input type="submit" value="<%= i %>" name="accionRptFichaAPN" class="py-1 px-3 mr-2 <%=btn_css_active%>" />
                            <%
                            } else if (btn_submit.equals("0")) {
                            %>
                            <a href="#" class="py-1 px-3 mr-2 <%= btn_css_active %>"><%= i %></a>
                            <%
                            } // end if btn 
                            
                            } // end if ctrl_3_btn_mostrar 
                        } // end for cant_btns
                            
                            // SI EL BOTON MAXIMO A MOSTRAR ES MENOR AL ITEM TOTAL ENTONCES 
                            if ((btn_max_mostrar < Integer.parseInt(cant_total_btn_lista))) {
                            %>
                            <input type="submit" value=">>" name="accionRptFichaAPN" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                            <%
                            } // end if 
                            %>
                        </form>
                        </div>
                    </div>
                    <!--<button onclick="javascript:demoFromHTML();">PDF</button>-->
                <%--</form>--%>
                <div>
                    <a href="<%= urlReportes %>" class="mt-3 btn btn-danger">Volver Atras</a>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
<%
//<!--        <script type="text/javascript">
//            <%// --------------------------------------------------------------------------------------------------------------->
//            <%// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR >
//            <%// --------------------------------------------------------------------------------------------------------------->
//            document.addEventListener('DOMContentLoaded', () => {
//            document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
//                if(e.keyCode == 13) {
//                  e.preventDefault();
//                  document.getElementById("Filtrar").click();
//                }
//              }))
//            });
//            <%// --------------------------------------------------------------------------------------------------------------->
//            
//            function functCm() {
//                var x = document.getElementById("cM").value;
//                document.getElementById("tAcM").value = x;
//            }
//            
//            function functTxB() {
//                var x = document.getElementById("txB").value;
//                document.getElementById("tAtxB").value = x;
//            }
//            
//            function functFecIni() {
//                var x = document.getElementById("tFFI").value;
//                document.getElementById("tAtFFI").value = x;
//            }
//            
//            function functFecFin() {
//                var x = document.getElementById("tFFF").value;
//                document.getElementById("tAtFFF").value = x;
//            }
//            
//            function functClie() {
//                var x = document.getElementById("cbxAddNewCli").value;
//                document.getElementById("tAcbxAddNewCli").value = x;
//            }
//            
//        </script>-->
%>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.min.js"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/configAux.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        
        
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.4.1/jspdf.debug.js"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
        <!--<script src="https://code.jquery.com/jquery-3.3.1.js" integrity="sha256-2Kok7MbOyxpgUVvAk/HJ2jigOSYS2auK4Pfzbm7uH60=" crossorigin="anonymous"></script>-->
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.3/Chart.bundle.js"></script>-->
        <script>
//            function startPrintProcess() {
//                html2canvas(document.getElementById('divTable'), {
//                    onrendered: function (canvasObj) {
//                        var pdf = new jsPDF('P', 'pt', 'a4'),
//                            pdfConf = {
//                                pagesplit: false,
//                                backgroundColor: '#FFF'
//                            };
//                        document.body.appendChild(canvasObj); //appendChild is required for html to add page in pdf
//                        pdf.addHTML(canvasObj, 0, 0, pdfConf, function () {
//                            document.body.removeChild(canvasObj);
//                            //pdf.addPage();
//                            pdf.save('Test1.pdf');
//                        });
//                    }
//                });
//            }
        </script>
        <script>
            // METODO PARA IMPRIMIR UN PDF FOR EXAMPLE 
//            function demoFromHTML() {
//                var pdf = new jsPDF('p', 'pt', 'letter');
//                // source can be HTML-formatted string, or a reference
//                // to an actual DOM element from which the text will be scraped.
//                source = $('#divTable11')[0];
//
//                // we support special element handlers. Register them with jQuery-style 
//                // ID selector for either ID or node name. ("#iAmID", "div", "span" etc.)
//                // There is no support for any other type of selectors 
//                // (class, of compound) at this time.
//                specialElementHandlers = {
//                    // element with id of "bypass" - jQuery style selector
//                    '#bypassme': function (element, renderer) {
//                        // true = "handled elsewhere, bypass text extraction"
//                        return true
//                    }
//                };
//                margins = {
//                    top: 80,
//                    bottom: 60,
//                    left: 40,
//                    width: 522
//                };
//                // all coords and widths are in jsPDF instance's declared units
//                // 'inches' in this case
//                pdf.fromHTML(
//                source, // HTML string or DOM elem ref.
//                margins.left, // x coord
//                margins.top, { // y coord
//                    'width': margins.width, // max width of content on PDF
//                    'elementHandlers': specialElementHandlers
//                },
//
//                function (dispose) {
//                    // dispose: object with X, Y of the last line add to the PDF 
//                    //          this allow the insertion of new lines after html
//                    pdf.save('Test.pdf');
//                }, margins);
//            }
            
        </script>
    </body>
</html>