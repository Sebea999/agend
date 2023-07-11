<%-- 
    Document   : pagPC_AgendConfigAdd
    Created on : 19-jul-2022, 12:00:10
    Author     : RYUU
--%>

<%@page import="java.util.List"%>
<%@page import="com.agend.javaBean.BeanConfigAgend"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Config. Agendamiento</title>
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
                String mensaje = (String) request.getAttribute("CPC_AC_MENSAJE"); // CONTROLLER PANEL CONTROL - AGENDAMIENTO CONFIGURACION - MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CPC_AC_TIPO_MENSAJE"); // CONTROLLER PANEL CONTROL - AGENDAMIENTO CONFIG - TIPO MENSAJE 
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
                    <h4 class="mainTitle">CONFIGURACIÓN DE AGENDAMIENTOS</h4>
                </div>
                <%
                    // DECLARO VARIABLES 
                    String name_btn_pag = "GUARDAR"; // NOMBRE DEL BOTON DE LA PAGINA PARA SABER LA ACCION QUE VA A HACER EL USUARIO, SI GUARDAR O ACTUALIZAR DEPENDIENDO DEL ID 
                    String btn_limpiar = "Limpiar"; // VARIABLE QUE UTILIZO PARA EL BOTON DE LIMPIAR, SI ESTA CARGADA ENTONCES ESTA CARGANDO UN NUEVO REGISTRO Y AL LIMPIAR LE DEJARE VACIO TODOS LOS CAMPOS, PERO AL EDITAR COMO LIMPIA TODOS LOS DATOS TAMBIEN EL DEL ID, POR ESO AL EDITAR NO LE VOY A MOSTRAR EL BTN DE LIMPIAR 
                    String class_css_btn_pag = "btn btn-outline-primary"; // CLASE CSS DEL BOTON DE LA PAGINA QUE CAMBIARIA SEGUN EL BOTON DE LA PAGINA ( GUARDAR / ACTUALIZAR ) 
                    String ID_CONFIG_AGEND="", DESC_CONFIG="", MAX_AGENDAMIENTO="", DESC_AGENDAMIENTO="", IDCLINICA="", ESTADO="", INTERVALO_MIN_ENTRE_AGEND="";
                    List<BeanConfigAgend> listaDatos;
                    
                    // CONTROLO SI EL ID ESTA CARGADO 
                    if (String.valueOf(sesionDatosUsuario.getAttribute("ID_CONFIG_AGEND")) == null || String.valueOf(sesionDatosUsuario.getAttribute("ID_CONFIG_AGEND")).isEmpty()) {
                        //
                    } else {
                        name_btn_pag = "ACTUALIZAR";
                        btn_limpiar = "";
                        class_css_btn_pag = "btn btn-outline-warning";
                        ID_CONFIG_AGEND = (String) sesionDatosUsuario.getAttribute("ID_CONFIG_AGEND");
                        System.out.println("_jsp___ID_CONFIG_AGEND:  "+ID_CONFIG_AGEND);
                        
                        listaDatos = metodosConfigAgend.traer_datos_reg(ID_CONFIG_AGEND);
                        Iterator<BeanConfigAgend> iterLista = listaDatos.iterator();
                        BeanConfigAgend datosBean = null;
                        
                        while(iterLista.hasNext()) {
                            datosBean = iterLista.next();
                            DESC_CONFIG = datosBean.getSCA_DESC_CONFIG();
                            MAX_AGENDAMIENTO = datosBean.getSCA_MAX_AGEND();
                            DESC_AGENDAMIENTO = datosBean.getSCA_DESC_AGEND();
                            IDCLINICA = datosBean.getSCA_IDCLINICA();
                            ESTADO = datosBean.getSCA_ESTADO();
                            INTERVALO_MIN_ENTRE_AGEND = datosBean.getSCA_INTERV_MIN_ENTRE_AGEND();
                        } // end while 
                    }
                    
                    // SOBREESCRIBO LOS DATOS CARGADOS CON LOS DATOS RECIBIDOS 
                    if ((String)request.getAttribute("CPC_AC_DESC_CONFIG") != null) {
                        DESC_CONFIG = (String) request.getAttribute("CPC_AC_DESC_CONFIG");
                    }
                    if ((String)request.getAttribute("CPC_AC_MAX_AGENDAMIENTO") != null) {
                        MAX_AGENDAMIENTO = (String) request.getAttribute("CPC_AC_MAX_AGENDAMIENTO");
                    }
                    if ((String)request.getAttribute("CPC_AC_DESC_AGENDAMIENTO") != null) {
                        DESC_AGENDAMIENTO = (String) request.getAttribute("CPC_AC_DESC_AGENDAMIENTO");
                    }
                    if ((String)request.getAttribute("CPC_AC_IDCLINICA") != null) {
                        IDCLINICA = (String) request.getAttribute("CPC_AC_IDCLINICA");
                    }
                    if ((String)request.getAttribute("CPC_AC_ESTADO") != null) {
                        ESTADO = (String) request.getAttribute("CPC_AC_ESTADO");
                    }
                    if ((String)request.getAttribute("CPC_AC_INTERV_MIN_ENTRE_AGEND") != null) {
                        INTERVALO_MIN_ENTRE_AGEND = (String) request.getAttribute("CPC_AC_INTERV_MIN_ENTRE_AGEND");
                    }
                %>
                <div class="divTableData mt-1">
                    <form action="CPCSrv" method="post" autocomplete="off">
                        <div class="col-11 pac_panel">
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">CÓDIGO:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= ID_CONFIG_AGEND %>" name="tC" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Nombre de la Configuración:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= DESC_CONFIG %>" id="tDC" name="tDC" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Máximo de Agendamientos por fecha para cada Médico:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= MAX_AGENDAMIENTO %>" id="tMA" name="tMA" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Descripción para los Agendamientos:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= DESC_AGENDAMIENTO %>" id="tDA" name="tDA" class="form-control" />
                                </div>
                                <div class="pac_panel_3">
                                    <div class="lblObsDescAgend mt-1">Agregando las etiquetas: #DIA# #MES# #AÑO# #HORA# podra cargar el dato de la fecha de agendamiento del paciente a la descripción</div>
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Intervalo de minutos entre un agendamiento y otro:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= INTERVALO_MIN_ENTRE_AGEND %>" id="tIMAO" name="tIMAO" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Clinica:</div>
                                <div class="pac_panel_3">
                                    <select id="cCli" name="cCli" class="form-control">
                                        <%
                                        Map<String, String> listaClinicas;
                                        listaClinicas = metodosRefClinica.cargarComboClinica(IDCLINICA);
                                        Set setCbxCli = listaClinicas.entrySet();
                                        Iterator iCbxCli = setCbxCli.iterator();
                                        
                                        while(iCbxCli.hasNext()) {
                                            Map.Entry mapCbxCli = (Map.Entry) iCbxCli.next();
                                        %>
                                        <option value="<%= mapCbxCli.getKey() %>"><%= mapCbxCli.getValue() %></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Estado:</div>
                                <div class="pac_panel_3">
                                    <select id="cE" name="cE" class="form-control">
                                        <%
                                        Map<String, String> listaEstado;
                                        listaEstado = metodosIniSes.cargarComboEstado(ESTADO);
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
                            
                            <div class="pac_panel_btns">
                                <input type="submit" name="accionAC" value="<%= name_btn_pag%>" class="<%= class_css_btn_pag%>" />
                                <%
                                // CONTROLO LA VARIABLE PARA MOSTRARLE O NO EL BTN, PARA MAS DETALLE LEER LA OBS. DE LA VARIABLE 
                                if (!(btn_limpiar.isEmpty())) {
                                %>
                                <button type="submit" value="<%= btn_limpiar %>" name="accionAC" class="btn btn-info">LIMPIAR</button>
                                <%--<a href="javascript:limpiar()" class="btn btn-info">LIMPIAR</a>--%>
                                <%
                                }
                                %>
                                <%
                                String var_volver_atras = urlConfigAgendamiento;
//                                String PARAM_BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("AGEND_CONFIG_BTN_VOLVER_ATRAS");
//                                if (PARAM_BTN_VOLVER_ATRAS == null || PARAM_BTN_VOLVER_ATRAS.isEmpty()) { PARAM_BTN_VOLVER_ATRAS = "AGEND_CONFIG"; }
//                                System.out.println(".    _JSP___AGEND_CONFIG_BTN_VOLVER_ATRAS:     :"+PARAM_BTN_VOLVER_ATRAS);
//                                
//                                if (PARAM_BTN_VOLVER_ATRAS.equals("AGEND_CONFIG")) { // CONTROLO QUE VARIABLE TIENE CARGADO EL PARAMETRO QUE RECIBO POR LA SESION PARA PODER SABER QUE PAGINA FUE LA QUE REDIRECCIONO A LA PAGINA DE PACIENTE 
//                                    var_volver_atras = urlConfigAgendamiento;
//                                } else if (PARAM_BTN_VOLVER_ATRAS.equals("AGEN")) {
//                                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
//                                    var_volver_atras = "add_agend.htm";
//                                }
                                %>
                                <a href="<%= var_volver_atras %>" class="btn btn-danger">VOLVER ATRAS</a>
                            </div>
                        </div>
                    </form>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configAgendAux.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>