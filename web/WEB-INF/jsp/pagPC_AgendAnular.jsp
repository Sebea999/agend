<%-- 
    Document   : pagPCCaja_FacturaAnular
    Created on : 06-sep-2021, 12:58:39
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanEspecialidad"%>
<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanAgendamiento"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="com.agend.javaBean.BeanFacturaCab"%>
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
        <title>Anular Agendamiento</title>
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
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CPC_AA_MENSAJE"); // CONTROLLER PANEL CONTROL - ANULAR AGENDAMIENTO - MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CPC_AA_TIPO_MENSAJE"); // CONTROLLER PANEL CONTROL - ANULAR AGENDAMIENTO - TIPO MENSAJE 
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
<%--                <form method="post" action="CPCSrv" autocomplete="off">--%>
                    <div>
                        <h4 class="mainTitle">ANULAR AGENDAMIENTOS</h4>
                    </div>
                    <%
                        // RECUPERAMOS LOS DATOS DEL FILTRO EN CASO DE QUE SE HAGA PARA VOLVER A MOSTRAR Y SI NO, ENTONCES PASARIA DATOS VACIOS 
                        String cbxMostrar = (String) request.getAttribute("CPC_AA_CBX_MOSTRAR");
                        if (cbxMostrar == null || cbxMostrar.isEmpty()) {
                            cbxMostrar = "";
                        }
                        String txtFiltrarNroFact = (String) request.getAttribute("CPC_AA_TXT_FILTRAR_NRO_FACT");
                        if (txtFiltrarNroFact == null || txtFiltrarNroFact.isEmpty()) {
                            txtFiltrarNroFact = "";
                        }
                        String FILTRO_FECHA_INI = (String) request.getAttribute("CPC_AA_TXT_FILTRAR_FEC_INI");
                        if (FILTRO_FECHA_INI == null || FILTRO_FECHA_INI.isEmpty()) {
                            FILTRO_FECHA_INI = "";
                        }
                        String FILTRO_FECHA_FIN = (String) request.getAttribute("CPC_AA_TXT_FILTRAR_FEC_FIN");
                        if (FILTRO_FECHA_FIN == null || FILTRO_FECHA_FIN.isEmpty()) {
                            FILTRO_FECHA_FIN = "";
                        }
                        String FILTRO_CLIENTE_ID = (String) request.getAttribute("CPC_AA_TXT_FILTRAR_CLIE_ID");
                        if (FILTRO_CLIENTE_ID == null || FILTRO_CLIENTE_ID.isEmpty()) {
                            FILTRO_CLIENTE_ID = "";
                        }
                        String FILTRO_CLINICA_ID = (String) request.getAttribute("CPC_AA_TXT_FILTRAR_CLINICA_ID");
                        if (FILTRO_CLINICA_ID == null || FILTRO_CLINICA_ID.isEmpty()) {
                            FILTRO_CLINICA_ID = "";
                        }
                    %>
                    <div class="mainFilter">
                        <form method="post" action="CPCSrv" autocomplete="off">
                        <div class="mainFilterBox mt-2">
                            <div>
                                <p class="mainLabelSearch">Mostrar:</p>
                                <select id="cM" name="cM" onchange="functCm()" class="form-control w-20">
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
                        <%
                        String CHECK_CLIENTE = "checked=\"checked\" ";
                        // SOLO A LOS PERFILES DE ADMINISTRADOR, FUNCIONARIO Y SECRETARIO LE MOSTRARE EL COMBO DE PACIENTE PARA EVITAR QUE EL PACIENTE PUEDA FILTRAR Y VER DATOS DE OTROS PACIENTES 
                        if (metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true) {
                        %>
                        <div class="mainFilterBox mt-2">
                            <div class="">
                                <div>
                                    <p class="mainLabelSearch">Paciente:</p>
                                    <%
//                                    String CHECK_CLIENTE = "checked=\"checked\" ";
                                /*
                                * OBSERVACION_DEL_PORQUE_HAY_DOS_VARIABLES_Y_NO_UNA_SOLA_CON_EL_MISMO_NOMBRE: 
                                    TENGO DOS VARIABLES PORQUE UNA LA TENGO EN EL MAV Y OTRA EN EL METODO DO_POST DEL CONTROLADOR, NECESITO LA PRIMERA LA QUE SE ENCUENTRA EN EL METODO MAV DEL REQUEST_MAPPING 
                                    PARA QUE AL ABRIR EL FORMULARIO ME MUESTRE EL CHECK MARCADO Y NO DESMARCADO, Y LUEGO NECESITO EL OTRO PARA IR GUIANDO AL CHECK EN CADA ACCION O EVENTO QUE SE REALICE, Y NO PUEDO TENER 
                                    UNO SOLO CON EL MISMO NOMBRE PORQUE NO ME HACE CASO, EL JSP SOLO HACE CASO AL "ON" QUE LE CARGO EN EL MAV Y LUEGO CUANDO MODIFICO CON "OFF" EN ALGUN EVENTO DEL DO_POST, EL JSP YA NO ME HACE CASO
                                    Y ME DEJA EL VALOR CON "ON", ENTONCES POR ESO CREE LA SEGUNDA VARIABLE QUE AL INICIAR ESTARA NULO Y AHI HARE CASO A LA PRIMERA VARIABLE, PERO LUEGO CUANDO SE REALICE ALGUN EVENTO EN LA PAGINA SE CARGARA 
                                    Y AHI TOMARE SU VALOR YA QUE ESTE PUEDE IR CAMBIANDO ENTRE "ON" Y "OFF" O NULO, PERO EL PRIMERO SIEMPRE SE MANTIENE EN "ON" 
                                */
                                    String CHECK_FILTER_CLIENTE = (String) request.getAttribute("CPC_AA_CHECK_FILTRAR_CLIE"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                    String CHECK_FILTER_CLIENTE01 = (String) request.getAttribute("CPC_AA_CHECK_FILTRAR_CLIE_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
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
                                    <select name="cbxAddNewCli" id="cbxAddNewCli" onchange="functCli()" class="cbxRFCli form-control">
                                        <%
                                        Map<String, String> listaClientes;
                                        listaClientes = metodosPersona.traerComboPacientes(FILTRO_CLIENTE_ID, sesionDatosUsuario, FILTRO_CLINICA_ID);
//                                        listaClientes = metodosPersona.traerComboClientes(FILTRO_CLIENTE_ID);
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
                        <%
                        }
                        %>
                        <div class="mainFilterBox mt-2">
                            <div class="">
                                <div>
                                    <p class="mainLabelSearch">Clinica:</p>
                                    <%
                                        String CHECK_CLINICA = "checked=\"checked\" ";
                                    /*
                                    * OBSERVACION_DEL_PORQUE_HAY_DOS_VARIABLES_Y_NO_UNA_SOLA_CON_EL_MISMO_NOMBRE: 
                                        TENGO DOS VARIABLES PORQUE UNA LA TENGO EN EL MAV Y OTRA EN EL METODO DO_POST DEL CONTROLADOR, NECESITO LA PRIMERA LA QUE SE ENCUENTRA EN EL METODO MAV DEL REQUEST_MAPPING 
                                        PARA QUE AL ABRIR EL FORMULARIO ME MUESTRE EL CHECK MARCADO Y NO DESMARCADO, Y LUEGO NECESITO EL OTRO PARA IR GUIANDO AL CHECK EN CADA ACCION O EVENTO QUE SE REALICE, Y NO PUEDO TENER 
                                        UNO SOLO CON EL MISMO NOMBRE PORQUE NO ME HACE CASO, EL JSP SOLO HACE CASO AL "ON" QUE LE CARGO EN EL MAV Y LUEGO CUANDO MODIFICO CON "OFF" EN ALGUN EVENTO DEL DO_POST, EL JSP YA NO ME HACE CASO
                                        Y ME DEJA EL VALOR CON "ON", ENTONCES POR ESO CREE LA SEGUNDA VARIABLE QUE AL INICIAR ESTARA NULO Y AHI HARE CASO A LA PRIMERA VARIABLE, PERO LUEGO CUANDO SE REALICE ALGUN EVENTO EN LA PAGINA SE CARGARA 
                                        Y AHI TOMARE SU VALOR YA QUE ESTE PUEDE IR CAMBIANDO ENTRE "ON" Y "OFF" O NULO, PERO EL PRIMERO SIEMPRE SE MANTIENE EN "ON" 
                                    */
                                        String CHECK_FILTER_CLINICA = (String) request.getAttribute("CPC_AA_CHECK_FILTRAR_CLINICA"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                        String CHECK_FILTER_CLINICA01 = (String) request.getAttribute("CPC_AA_CHECK_FILTRAR_CLINICA_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
                                        if (CHECK_FILTER_CLINICA01 == null) {
                                            if (CHECK_FILTER_CLINICA == null || CHECK_FILTER_CLINICA.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                                CHECK_CLINICA = "";
                                            } else if (CHECK_FILTER_CLINICA.equalsIgnoreCase("ON")) {
                                                CHECK_CLINICA = "checked=\"checked\" ";
                                            }
                                        } else {
                                            if (CHECK_FILTER_CLINICA01 == null || CHECK_FILTER_CLINICA01.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                                CHECK_CLINICA = "";
                                            } else if (CHECK_FILTER_CLINICA01.equalsIgnoreCase("ON")) {
                                                CHECK_CLINICA = "checked=\"checked\" ";
                                            }
                                        }
                                    %>
                                    <input type="checkbox" <%= CHECK_CLINICA %>id="check_clinica" name="check_clinica" />
                                    <div class="comboclinica">
                                        <select name="cbxAddNewClinica" id="cbxAddNewClinica" onchange="functClinica()" class="cbxClinica form-control">
                                        <%
                                            Map<String, String> listaClinica;
                                            listaClinica = metodosRefClinica.cargarComboClinica(FILTRO_CLINICA_ID);
                                            Set setLisClinica = listaClinica.entrySet();
                                            Iterator iterLisClinica = setLisClinica.iterator();
                                            
                                            while(iterLisClinica.hasNext()) {
                                                Map.Entry mapaListClinica = (Map.Entry) iterLisClinica.next();
                                        %>
                                            <option value="<%= mapaListClinica.getKey() %>"><%= mapaListClinica.getValue() %></option>
                                        <%
                                            }
                                        %>
                                        </select>
                                    </div>
                                    <label for="check_clinica" class="btn btn-light">TODOS</label>
                                </div>
                            </div>
                        </div>
                        <div class="mainFilterBox mt-2 boxFiltroBtn">
                            <div>
                                <p class="mainLabelSearch">Buscar Cod. Agen.:</p>
                                <input type="text" value="<%= txtFiltrarNroFact %>" name="tFNF" id="tFNF" onchange="functTNF()" class="form-control" />
                            </div>
                            <div>
                                <input type="submit" name="accionAA" value="Filtrar" id="Filtrar" class="btn btn-primary mb-2 mr-2" />
                                <input type="submit" name="accionAA" value="Limpiar" id="Limpiar" class="btn btn-primary mb-2 " />
<%//                                <!--<a href="<%= // urlAnularAgendamientos >" class="btn btn-primary mb-2">Limpiar</a>-->%>
                            </div>
                        </div>
                        </form>
<%//                        <div class="mainFilterBox mt-1">
//                            <div>
//                                <form action="lista_agend_pdf" target="_blank" method="post">%> <% // SERVLET DEL CONTROLADOR QUE SE ENCARGA DE CREAR EL REPORTE PDF CON LA LISTA DE EMPEÑOS  %>
                                <%
                                /*
                                OBSERVACION DEL PORQUE CARGO A LA SESION LA LISTA DEL FILTRO ACA Y NO EN EL SERVLET: 
                                    * LISTA QUE UTILIZO PARA PODER IMPRIMIR PDF LA LISTA DE EMPEÑOS, 
                                      SI NO SE FILTRA O SI SE ENTRA AL FORMULARIO SE ENCONTRARIA NULO (NO REALIZO NINGUN FILTRO) 
                                      Y ASI SE CARGARIA EL PDF CON EL METODO DE CARGA GRILLA DESDE EL SERVLET Y ESTA LISTA 
                                      QUE MANTENGO EN LA SESION LA CARGARIA AL REALIZAR FILTROS; 
                                    * NO SE PORQUE PERO AL INTENTAR CARGAR LA LISTA A LA SESION DESDE EL SERVLET, PRIMERAMENTE 
                                      EN EL MAV CON EL VALOR NULO Y LUEGO EN doPost AL FILTRAR CARGARLO CON LA LISTA DEL FILTRO, 
                                      PERO EN EL SERVLET QUE SE ENCARGA DE IMPRIMIR LA LISTA LO RECUPERABA NULO Y SOLO RECUPERABA 
                                      LA LISTA CON EL FILTRO CUANDO CARGO A LA SESION DENTRO DEL FORM, POR ESO LO DEJO ACA, Y AL NO 
                                      ENVIAR LA LISTA DEL FILTRO, RECUPERARIA NULO COSA QUE INTERPRETARIA QUE NO SE FILTRO DESDE EL SERVLET QUE IMPRIME LA LISTA; 
                                    * SI INTENTO CARGAR LA LISTA DEL FILTRO A UN REQUEST Y NO A LA SESION, NO VA A FUNCIONAR, 
                                      NO RECIBE EL SERVLET, LO RECONOCE COMO NULO, PERO AL PASARLO A TRAVES DE LA SESION, SI RECUPERA LA LISTA.
                                */
//                                    sesionDatosUsuario.setAttribute("PDF_LISTA_RPT_AGENDAMIENTO", ((List<BeanAgendamiento>)request.getAttribute("CPC_AA_LISTA_FILTRO")));
                                %>
<%//                                    <input type="submit" value="Imprimir Lista" class="btn btn-primary mt-2 mr-2" />
//                                </form>
//                            </div>
//                        </div>%>
                    </div>
                <%--</form>--%>
                
                    <div class="divTable">
                        <table role="table" class="tableFilter">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="PH_CI">Cód.</td>
                                    <td role="columnheader" class="PH_CC">Clinica</td>
                                    <td role="columnheader" class="PH_CC">Fecha y Hora (Agen.)</td>
                                    <td role="columnheader" class="PH_CES">Especialidad</td>
                                    <td role="columnheader" class="PH_CM">Médico</td>
                                    <td role="columnheader" class="PH_CM">Paciente</td>
                                    <td role="columnheader" class="PH_CE">Estado</td>
                                    <td role="columnheader" class="PH_CBA">Acciones</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
                                    // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
                                    String NRO_PAG = "";
                                    if (sesionDatosUsuario.getAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL") == null) {
                                        NRO_PAG = "1";
                                    } else {
                                        NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL");
                                    }
                                    System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                                    
                                    List<BeanAgendamiento> listaDatos = null;
                                    if (((List<BeanAgendamiento>)request.getAttribute("CPC_AA_LISTA_FILTRO")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        System.out.println("_    _IF_    _");
                                        listaDatos = (List<BeanAgendamiento>) request.getAttribute("CPC_AA_LISTA_FILTRO");
                                    } else { // y si no entonces le decimos que carge nomas a traves del metodo que le pasamos 
                                        System.out.println("_    _ELSE_    _");
                                        String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                                        String IDPACIENTE = "";
                                        if (metodosPerfil.isPerfilPaciente(idPerfil)) {
                                            IDPACIENTE = idPersona;
                                        } else if (metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil)) {
                                            IDPACIENTE = "";
                                        }
                                        listaDatos = metodosAgendamiento.cargar_grilla_anular_agen(sesionDatosUsuario, NRO_PAG, cant_min_fija, IDPACIENTE); /* EN EL CARGA GRILLA YA FILTRO POR 10 REGISTROS NOMAS Y LUEGO EL AL CAMBIAR ESO SE VA A LLAMAR A OTRO METODO QUE SI VA A OBTENER EL VALOR DE REGISTROS QUE QUIERE TRAER PERO COMO POR DEFECTO VA A APARECER ESE NRO ENTONCES CON ESE DEFAULT FITLRO */
                                        sesionDatosUsuario.setAttribute("CPC_VAR_BAND_FILTRO", "0"); // CON ESTO RESETEO LA BANDERA QUE UTILIZO PARA LA PAGINACION 
                                    }
                                    Iterator<BeanAgendamiento> iterDatos = listaDatos.iterator();
                                    BeanAgendamiento datosBean = new BeanAgendamiento();
                                    
                                    while(iterDatos.hasNext()) {
                                        datosBean = iterDatos.next();
                                        
                                        // TRAER DATOS DEL MEDICO 
                                        BeanPersona datosBPersMed = new BeanPersona();
                                        datosBPersMed = metodosPersona.datosBasicosPersona(datosBPersMed, datosBean.getOA_IDMEDICO());
                                        BeanPersona datosBPersPac = new BeanPersona();
                                        datosBPersPac = metodosPersona.datosBasicosPersona(datosBPersPac, datosBean.getOAD_IDPACIENTE());
                                        BeanClinica datosBClinica = new BeanClinica();
                                        datosBClinica = metodosRefClinica.datosBasicosClinica(datosBClinica, datosBean.getOA_IDCLINICA());
                                        BeanEspecialidad datosBEspe = new BeanEspecialidad();
                                        datosBEspe = metodosRefEspecialidad.datosBasicosEspecialidad(datosBEspe, datosBean.getOA_IDESPECIALIDAD());
                                        String ITEM_AGEN_DET = datosBean.getOAD_ITEM();
                                        String IDAGENDAMIENTO = datosBean.getOAD_IDAGENDAMIENTO();
                                        String IDPACIENTE = datosBean.getOAD_IDPACIENTE();
                                %>
                                <tr role="row">
                                    <td role="cell" class="PH_CI">
                                        <%= datosBean.getOA_IDAGENDAMIENTO() %>
                                    </td>
                                    <td role="cell" class="PH_CC">
                                        <%= datosBClinica.getOC_DESC_CLINICA() %>
                                    </td>
                                    <td role="cell" class="PH_CC">
                                        <%= datosBean.getOAD_FECHA_AGEN() %>
                                    </td>
                                    <td role="cell" class="PH_CES">
                                        <%= datosBEspe.getRHE_DESC_ESPECIALIDAD() %>
                                    </td>
                                    <td role="cell" class="PH_CM">
                                        <%= datosBPersMed.getRP_NOMBRE()+" "+datosBPersMed.getRP_APELLIDO() %>
                                    </td>
                                    <td role="cell" class="PH_CM">
                                        <%= datosBPersPac.getRP_NOMBRE()+" "+datosBPersPac.getRP_APELLIDO() %>
                                    </td>
                                    <td role="cell" class="PH_CE">
                                        <%= metodosIniSes.devolverTextEstadoAgend(datosBean.getOAD_ESTADO()) %>
                                    </td>
                                    <td role="cell" class="PH_CBA">
                                        <form action="CPCSrv" method="post">
                                            <input type="hidden" value="<%= ITEM_AGEN_DET %>" name="tIAD" class="form-control" />
                                            <input type="hidden" value="<%= IDAGENDAMIENTO %>" name="tI" class="form-control" />
                                            <input type="hidden" value="<%= IDPACIENTE %>" name="tIP" class="form-control" />
                                            <button type="submit" value="PreAnular" name="accionAA" class="btn btn-light">Anular</button>
                                            <%//<input type="submit" value="Anular" name="accionAA" class="btn btn-light"/>%>
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="container-fluid text-right mt-4 mr-2 mb-3">
                        <form action="CPCSrv" method="post">
                        <%
                            /*
                             * OBSERVACION: 
                                UTILIZO ESTAS CAJAS DE TEXTOS AUXILIARES PARA PODER VOLVER A REALIZAR EL FILTRO EN CASO DE UNA PAGINACION 
                                YA QUE LOS CAMPOS DE TEXTOS VISIBLES NO SE ENCUENTRAN DENTRO DEL MISMO FOR Y POR ESO NECESITO ESTOS CAMPOS DE TEXTOS AUXILIARES, 
                                QUE SON CARGADOS A TRAVES DE UNOS METODOS CHANGE DE JAVASCRIPT PARA PODER CAPTAR EL CAMBIO EN ALGUN FILTRO Y PODER DEVOLVERLO OTRA VEZ (EN CASO DE QUE FILTRE EN MEDIO DE UNA PAGINACION) 
                            */
                        %>
                            <input type="hidden" value="<%= cbxMostrar %>" id="tAcM" name="tAcM" class="form-control disNone"/>
                            <input type="hidden" value="<%= txtFiltrarNroFact %>" id="tAtFNF" name="tAtFNF" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_FECHA_INI %>" id="tAtFFI" name="tAtFFI" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_FECHA_FIN %>" id="tAtFFF" name="tAtFFF" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_CLIENTE_ID %>" id="tAcbxAddNewCli" name="tAcbxAddNewCli" class="form-control disNone"/>
                            <input type="hidden" value="<%= CHECK_CLIENTE %>" id="tAcheck_cli" name="tAcheck_cli" class="form-control disNone"/>
                            <input type="hidden" value="<%= FILTRO_CLINICA_ID %>" id="tAcbxAddNewClinica" name="tAcbxAddNewClinica" class="form-control disNone"/>
                            <input type="hidden" value="<%= CHECK_CLINICA %>" id="tAcheck_clinica" name="tAcheck_clinica" class="form-control disNone"/>
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
                        String cant_total_btn_lista = (String) sesionDatosUsuario.getAttribute("PAG_PC_ANU_AGEN_CANT_TOTAL_BTNS"); // PAGINA PACIENTE CANTIDAD TOTAL DE BOTONES 
//                        System.out.println("_   __JSP___TOTAL_BTN_LISTA:   :"+cant_total_btn_lista);
                        
                        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE CLICS QUE EL USUARIO DIO PARA LA DERECHA PARA VER LOS DEMAS BOTONES DE PAGINAS, EMPIEZA CON 1 Y POR CADA CLIC AUMENTO UNO Y SI SE DA CLIC A LA IZQUIERDA LE RESTO UNO HASTA QUE VUELVA A SU NUMERO BASE (1) 
                        String CANT_CLIC_DERE = ""; // PAG_PAC_CANT_BTN_DERE_CLIC : PAGINA PACIENTE CANTIDAD BTN DERECHA (VER MAS >>) CLIC 
                        if ((String) sesionDatosUsuario.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC") == null) { // SI ESTA NULO POR ALGUNA RAZON DE INSTANCIA, ENTONCES LE COLOCO 1 COMO MINIMO VALOR ACEPTABLE Y SI NO ESTA NULO ENTONCES LE DEJO CON EL VALOR QUE TIENE 
                            CANT_CLIC_DERE = "1";
                        } else {
                            CANT_CLIC_DERE = (String) sesionDatosUsuario.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC");
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
                            <input type="submit" value="<<" name="accionAA" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
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
                            <input type="submit" value="<%= i %>" name="accionAA" class="py-1 px-3 mr-2 <%=btn_css_active%>" />
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
                            <input type="submit" value=">>" name="accionAA" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                            <%
                            } // end if 
                            %>
                        </form>
                        </div>
                    </div>
<%--                </form>--%>
                <div>
                    <a href="<%= urlPanelControl %>" class="mt-2 btn btn-danger">Volver Atras</a>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configAuxPCFA.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>