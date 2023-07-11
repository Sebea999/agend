<%-- 
    Document   : pagPlanHorarioAdd
    Created on : 07-ene-2022, 13:12:13
    Author     : RYUU
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanPlanHorario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plan de Horarios</title>
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
                String mensaje = (String) request.getAttribute("CPH_MENSAJE"); // CONTROLLER PLAN HORARIO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CPH_TIPO_MENSAJE"); // CONTROLLER PLAN HORARIO TIPO MENSAJE 
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
            <%
                /*
                    * OBSERVACION: PARA AÑADIR UN PLAN DE HORARIO Y EDITAR UN PLAN DE HORARIO 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_PLAN_HORARIO, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO");
                System.out.println("_jsp___ID_PLAN_HORARIO:  "+ID_PLAN_HORARIO);
                
                List<BeanPlanHorario> listaDatos;
                String PH_IDCLINICA="", PH_DESC_CLINICA="", PH_IDMEDICO="", PH_DESDE="", PH_HASTA="", PH_TURNO="", PH_DIA="", PH_NOM_MEDICO="", PH_APE_MEDICO="", PH_CINRO_MEDICO="", PH_ESTADO="A";
                String PH_IDCLINICA_ORI="", PH_IDMEDICO_ORI="", PH_DESDE_ORI="", PH_HASTA_ORI="",  PH_NOM_MEDICO_ORI="", PH_APE_MEDICO_ORI="", PH_CINRO_MEDICO_ORI="";
                
                // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                if (ID_PLAN_HORARIO.equals("0") || ID_PLAN_HORARIO.isEmpty() || ID_PLAN_HORARIO.equals("")) {
                    // ... 
                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                    listaDatos = metodosPlanHorario.traer_datos(ID_PLAN_HORARIO, sesionDatosUsuario);
                    Iterator<BeanPlanHorario> iterPlanHor = listaDatos.iterator();
                    BeanPlanHorario plan_horario = null;
                    
                    while(iterPlanHor.hasNext()) {
                        plan_horario = iterPlanHor.next();
                        PH_IDCLINICA = plan_horario.getOPH_IDCLINICA();
                            BeanClinica datosCli = new BeanClinica();
                            datosCli = metodosRefClinica.datosBasicosClinica(datosCli, PH_IDCLINICA);
                            PH_DESC_CLINICA = datosCli.getOC_DESC_CLINICA();
                        PH_IDMEDICO = plan_horario.getOPH_IDMEDICO();
                            BeanPersona datosMed = new BeanPersona();
                            datosMed = metodosPersona.datosBasicosPersona(datosMed, PH_IDMEDICO);
                        PH_NOM_MEDICO = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
                        PH_APE_MEDICO = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
                        PH_CINRO_MEDICO = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
                        PH_DESDE = plan_horario.getOPHD_DESDE();
                        PH_HASTA = plan_horario.getOPHD_HASTA();
                        PH_TURNO = plan_horario.getOPHD_TURNO();
                        PH_DIA = plan_horario.getOPHD_DIA();
                        PH_ESTADO = plan_horario.getOPH_ESTADO();
                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                        PH_IDCLINICA_ORI = plan_horario.getOPH_IDCLINICA();
                        PH_IDMEDICO_ORI = plan_horario.getOPH_IDMEDICO();
                        PH_NOM_MEDICO_ORI = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
                        PH_APE_MEDICO_ORI = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
                        PH_CINRO_MEDICO_ORI = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
                        PH_DESDE_ORI = plan_horario.getOPHD_DESDE();
                        PH_HASTA_ORI = plan_horario.getOPHD_HASTA();
                    } // end while 
                    
//                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
//                    sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CPH_IDCLINICA") != null) {
                    PH_IDCLINICA = (String) request.getAttribute("CPH_IDCLINICA");
                }
                if ((String)request.getAttribute("CPH_IDMEDICO") != null) {
                    PH_IDMEDICO = (String) request.getAttribute("CPH_IDMEDICO");
                }
                if ((String)request.getAttribute("CPH_NOM_MEDICO") != null) {
                    PH_NOM_MEDICO = (String) request.getAttribute("CPH_NOM_MEDICO");
                }
                if ((String)request.getAttribute("CPH_APE_MEDICO") != null) {
                    PH_APE_MEDICO = (String) request.getAttribute("CPH_APE_MEDICO");
                }
                if ((String)request.getAttribute("CPH_CINRO_MEDICO") != null) {
                    PH_CINRO_MEDICO = (String) request.getAttribute("CPH_CINRO_MEDICO");
                }
                if ((String)request.getAttribute("CPH_DESDE") != null) {
                    PH_DESDE = (String) request.getAttribute("CPH_DESDE");
                }
                if ((String)request.getAttribute("CPH_HASTA") != null) {
                    PH_HASTA = (String) request.getAttribute("CPH_HASTA");
                }
                if ((String)request.getAttribute("CPH_TURNO") != null) {
                    PH_TURNO = (String) request.getAttribute("CPH_TURNO");
                }
                if ((String)request.getAttribute("CPH_DIA") != null) {
                    PH_DIA = (String) request.getAttribute("CPH_DIA");
                }
                if ((String)request.getAttribute("CPH_ESTADO") != null) {
                    PH_ESTADO = (String) request.getAttribute("CPH_ESTADO");
                }
                
                System.out.println("---------------_____JSP_____-------------------");
                System.out.println("_       ___ID_CLINICA:    :"+PH_IDCLINICA);
                System.out.println("_       ____ID_MEDICO:    :"+PH_IDMEDICO);
                System.out.println("_       _NOMBR_MEDICO:    :"+PH_NOM_MEDICO);
                System.out.println("_       _APELL_MEDICO:    :"+PH_APE_MEDICO);
                System.out.println("_       _CINRO_MEDICO:    :"+PH_CINRO_MEDICO);
                System.out.println("_       _DESDE:    :"+PH_DESDE);
                System.out.println("_       _HASTA:    :"+PH_HASTA);
                System.out.println("_       _TURNO:    :"+PH_TURNO);
                System.out.println("_       ___DIA:    :"+PH_DIA);
                System.out.println("_       _ESTADO:   :"+PH_ESTADO);
                System.out.println("-----------------------------------------------");
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR CLIENTES, ENTONCES DE ACUERDO AL IDCLIENTE VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO CLIENTE O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
//                if (ID_PLAN_HORARIO.isEmpty() || ID_PLAN_HORARIO.equals("")) {
//                    varTitulo = "PLAN HORARIO - NUEVO REGISTRO";
//                } else {
                    varTitulo = "PLAN HORARIO - VER REGISTRO";
//                    varTitulo = "PLAN HORARIO - MODIFICAR REGISTRO";
//                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CPHSrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_PLAN_HORARIO %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nombre/s:</div>
                                    <div class="pac_panel_3">
                                        <%--<input type="hidden" value="<%= PH_IDMEDICO %>" id="tIM" name="tIM" readonly="readonly" class="form-control disNone" />--%>
                                        <input type="text" value="<%= PH_NOM_MEDICO %>" id="tNM" name="tNM" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Apellido/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PH_APE_MEDICO %>" id="tAM" name="tAM" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nro. CI:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PH_CINRO_MEDICO %>" id="tCM" name="tCM" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Clinica:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PH_DESC_CLINICA %>" id="cCli" name="cCli" readonly="readonly" class="form-control" />
<%--                                        <select class="form-control" id="cCli" name="cCli">
                                            <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosRefClinica.cargarComboClinica(PH_IDCLINICA);
                                            Set setCbxCli = listaClinicas.entrySet();
                                            Iterator iCbxCli = setCbxCli.iterator();

                                            while(iCbxCli.hasNext()) {
                                                Map.Entry mapCbxCli = (Map.Entry) iCbxCli.next();
                                            %>
                                            <option value="<%= mapCbxCli.getKey() %>"><%= mapCbxCli.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>--%>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Estado:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= metodosIniSes.devolverTextEstado(PH_ESTADO) %>" id="cE" name="cE" readonly="readonly" class="form-control" />
<%--                                        <select class="form-control" id="cE" name="cE">
                                            <%
                                            Map<String, String> listaEstado;
                                            listaEstado = metodosIniSes.cargarComboEstado(PH_ESTADO);
                                            Set setCbxEstado = listaEstado.entrySet();
                                            Iterator iCbxEstado = setCbxEstado.iterator();

                                            while(iCbxEstado.hasNext()) {
                                                Map.Entry mapCbxEstado = (Map.Entry) iCbxEstado.next();
                                            %>
                                            <option value="<%= mapCbxEstado.getKey() %>"><%= mapCbxEstado.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>--%>
                                    </div>
                                </div>
                                
                                <div class="pac_panel_1 mt-2">
                                <%
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".   _   ___GRILLA_DIAS_DE_LA_SEMANA____");
                                System.out.println(".");
                                %>
                                    <table role="table" class="tablePlanHoDet mt-3">
                                        <thead role="rowgroup">
                                            <tr role="row">
                                                <td role="columnheader" class="PHD_CDi">Dia</td>
                                                <td role="columnheader" class="PHD_CT">Turno</td>
                                                <td role="columnheader" class="PHD_CDe">Desde</td>
                                                <td role="columnheader" class="PHD_CH">Hasta</td>
                                            </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                <%
                                String DIA, TURNO, DESDE, HASTA;
                                // CARGO LA LISTA DEL DETALLE 
                                List<BeanPlanHorario> listaDetalle;
                                listaDetalle = metodosPlanHorario.traer_datos(ID_PLAN_HORARIO, sesionDatosUsuario);
                                
                                // CREO LA LISTA DE LOS DIAS DE LA SEMANA PARA PODER RECORRER Y ASI ORDENAR LAS LINEAS DEL DETALLE 
                                List<String> listaDias = new ArrayList<String>();
                                    listaDias.add("LUNES");
                                    listaDias.add("MARTES");
                                    listaDias.add("MIERCOLES");
                                    listaDias.add("JUEVES");
                                    listaDias.add("VIERNES");
                                    listaDias.add("SABADO");
                                    listaDias.add("DOMINGO");
                                Iterator<String> iListDias = listaDias.iterator();
                                
                                // RECORRO PRIMERO LOS DIAS DE LA SEMANA 
                                while(iListDias.hasNext()) {
                                    System.out.println(".");
                                    System.out.println(".");
                                    String DIA_SEMANA = String.valueOf(iListDias.next());
                                    System.out.println("___JSP___DIA_SEMANA:      :"+DIA_SEMANA);
                                    System.out.println(".");
                                    System.out.println(".");
                                    
                                    // Y LUEGO RECORRO EL DETALLE PARA PODER IR ORDENANDO SEGUN LOS DIAS DE LA SEMANA 
                                    for(BeanPlanHorario datosTab : listaDetalle) {
                                        System.out.println(".");
                                        System.out.println(".    FOR_DETALLE_PH   .");
                                        System.out.println(".");
                                        DIA = datosTab.getOPHD_DIA();
                                        System.out.println("_______DIA_TABLA:     :"+DIA);
                                        
                                        // CONTROLO QUE LA LINEA DEL DETALLE SEA IGUAL A LA LINEA DE LA SEMANA QUE SE ESTA RECORRIENDO, Y SI SON IGUALES LA IMPRIMO Y SI NO ENTONCES NO LE MUESTRO PORQUE VA A LLEGAR A SU TURNO YA QUE TODOS LOS DIAS DE LA SEMANA SE VAN A RECORRER 
                                        if(DIA_SEMANA.equalsIgnoreCase(DIA)) {
                                            // OBTENGO LOS DATOS DEL DETALLE 
                                            TURNO = metodosIniSes.devolverTextTurno(datosTab.getOPHD_TURNO());
                                            DESDE = datosTab.getOPHD_DESDE();
                                            HASTA = datosTab.getOPHD_HASTA();
                                %>
                                            <tr role="row">
                                                <td role="cell" class="PHD_CDi"><%= DIA %></td>
                                                <td role="cell" class="PHD_CT"><%= TURNO %></td>
                                                <td role="cell" class="PHD_CDe"><%= DESDE %></td>
                                                <td role="cell" class="PHD_CH"><%= HASTA %></td>
                                            </tr>
                                <%
                                        } // IF_DAYS 
                                    } // FOR_DETALLE 
                                } // WHILE_LIST_DAYS 
                                %>
                                        </tbody>
                                    </table>
                                </div>
                                
                                <div class="pac_panel_btns">
                                    <%--<input type="submit" name="accion" value="GRABAR" class="btn btn-primary" />--%>
                                    <%--<a href="javascript:cancelar()" class="btn btn-primary">LIMPIAR</a>--%>
                                    <%
                                    /*
                                        OBSERVACION: 
                                            UTILIZO ESTE BLOQUE DE CODIGO PARA SABER A QUE PAGINA DEBO DE REDIRECCIONAR CUANDO SE DE CLIC AL BOTON DE VOLVER ATRAS 
                                            YA QUE DESDE AGENDAMIENTO LE PUEDO REDIRECCIONAR TAMBIEN A ESTA PAGINA Y ES MAS OPTIMO QUE EL BOTON RECONOZCA DE DONDE ES QUE SE ESTA INGRESANDO A LA PAGINA 
                                            PARA ASI PODER VOLVER ATRAS A ESA PAGINA Y NO A LA PAGINA DE PLAN DE HORARIO 
                                    */
                                    String var_volver_atras = urlPlanHorario;
                                    String PARAM_BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CPH_BTN_VOLVER_ATRAS");
                                    if (PARAM_BTN_VOLVER_ATRAS == null || PARAM_BTN_VOLVER_ATRAS.isEmpty()) { PARAM_BTN_VOLVER_ATRAS = "PLAN_HORARIO"; }
                                    System.out.println(".    _JSP___CPH_BTN_VOLVER_ATRAS:     :"+PARAM_BTN_VOLVER_ATRAS);
                                    
                                    if (PARAM_BTN_VOLVER_ATRAS.equals("PLAN_HORARIO")) { // CONTROLO QUE VARIABLE TIENE CARGADO EL PARAMETRO QUE RECIBO POR LA SESION PARA PODER SABER QUE PAGINA FUE LA QUE REDIRECCIONO A LA PAGINA DE PLAN_DE_HORARIO  
                                        var_volver_atras = urlPlanHorario;
                                    } else if (PARAM_BTN_VOLVER_ATRAS.equals("AGENDAMIENTO")) {
                                        sesionDatosUsuario.setAttribute("CPH_BTN_VOLVER_ATRAS", "");
                                        var_volver_atras = "ver_agend.htm";
//                                        sesionDatosUsuario.setAttribute("ID_AGENDAMIENTO", idAgend); OBSERVACION: SI ME LLEGA A SALIR NULO A LA HORA DE VOLVER ATRAS, ENTONCES DESCOMENTAR ESTE CODIGO Y RECUPERAR EL IDAGENDAMIENTO AL ENTRAR AL JSP Y AL DARLE EL BOTON ATRAS VOLVER A PASARLE EL IDAGENDAMIENTO 
                                    }
                                    %>
                                    <a href="<%= var_volver_atras %>" class="btn btn-outline-danger">Volver Atras</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
<%--        <script>
//            function cancelar() {
//                document.getElementById("tIM").value = "<%= PH_IDMEDICO_ORI %>";
//                document.getElementById("tNM").value = "<%= PH_NOM_MEDICO_ORI %>";
//                document.getElementById("tAM").value = "<%= PH_APE_MEDICO_ORI %>";
//                document.getElementById("tCM").value = "<%= PH_CINRO_MEDICO_ORI %>";
//                document.getElementById("cCli").value = "1";
//                document.getElementById("cPHT").value = "M";
//                document.getElementById("tPHFD").value = "<%= PH_DESDE_ORI %>";
//                document.getElementById("tPHFH").value = "<%= PH_HASTA_ORI %>";
//                document.getElementById("tPHD").value = "";
//                document.getElementById("cE").value = "A";
//            }
        </script>--%>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>