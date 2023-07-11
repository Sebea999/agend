<%-- 
    Document   : pagPlanHorarioAddDet
    Created on : 17-ene-2022, 13:46:20
    Author     : RYUU
--%>

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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalClie.css">
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
                
//                // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
//                if (ID_PLAN_HORARIO.equals("0") || ID_PLAN_HORARIO.isEmpty() || ID_PLAN_HORARIO.equals("")) {
//                    // ... 
//                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
//                    listaDatos = metodosPlanHorario.traer_datos(ID_PLAN_HORARIO, sesionDatosUsuario);
//                    Iterator<BeanPlanHorario> iterPlanHor = listaDatos.iterator();
//                    BeanPlanHorario plan_horario = null;
//                    
//                    while(iterPlanHor.hasNext()) {
//                        plan_horario = iterPlanHor.next();
//                        PH_IDCLINICA = plan_horario.getOPH_IDCLINICA();
//                        PH_IDMEDICO = plan_horario.getOPH_IDMEDICO();
//                            BeanPersona datosMed = new BeanPersona();
//                            datosMed = metodosPersona.datosBasicosPersona(datosMed, PH_IDMEDICO);
//                        PH_NOM_MEDICO = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
//                        PH_APE_MEDICO = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
//                        PH_CINRO_MEDICO = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
//                        PH_DESDE = plan_horario.getOPHD_DESDE();
//                        PH_HASTA = plan_horario.getOPHD_HASTA();
//                        PH_TURNO = plan_horario.getOPHD_TURNO();
//                        PH_DIA = plan_horario.getOPHD_DIA();
//                        PH_ESTADO = plan_horario.getOPH_ESTADO();
//                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
//                        PH_IDCLINICA_ORI = plan_horario.getOPH_IDCLINICA();
//                        PH_IDMEDICO_ORI = plan_horario.getOPH_IDMEDICO();
//                        PH_NOM_MEDICO_ORI = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
//                        PH_APE_MEDICO_ORI = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
//                        PH_CINRO_MEDICO_ORI = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
//                        PH_DESDE_ORI = plan_horario.getOPHD_DESDE();
//                        PH_HASTA_ORI = plan_horario.getOPHD_HASTA();
//                    } // end while 
//                    
////                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
////                    sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
//                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                // CABECERA ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- 
                if ((String)sesionDatosUsuario.getAttribute("CPH_IDCLINICA") != null) {
                    PH_IDCLINICA = (String) sesionDatosUsuario.getAttribute("CPH_IDCLINICA");
                } else if ((String)request.getAttribute("CPH_IDCLINICA") != null) {
                    PH_IDCLINICA = (String) request.getAttribute("CPH_IDCLINICA");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_IDMEDICO") != null) {
                    PH_IDMEDICO = (String) sesionDatosUsuario.getAttribute("CPH_IDMEDICO");
                } else if ((String)request.getAttribute("CPH_IDMEDICO") != null) {
                    PH_IDMEDICO = (String) request.getAttribute("CPH_IDMEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO") != null) {
                    PH_NOM_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO");
                } else if((String)request.getAttribute("CPH_NOM_MEDICO") != null) {
                    PH_NOM_MEDICO = (String) request.getAttribute("CPH_NOM_MEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_APE_MEDICO") != null) {
                    PH_APE_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_APE_MEDICO");
                } else if ((String)request.getAttribute("CPH_APE_MEDICO") != null) {
                    PH_APE_MEDICO = (String) request.getAttribute("CPH_APE_MEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO") != null) {
                    PH_CINRO_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO");
                } else if ((String)request.getAttribute("CPH_CINRO_MEDICO") != null) {
                    PH_CINRO_MEDICO = (String) request.getAttribute("CPH_CINRO_MEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_ESTADO") != null) {
                    PH_ESTADO = (String) sesionDatosUsuario.getAttribute("CPH_ESTADO");
                } else if ((String)request.getAttribute("CPH_ESTADO") != null) {
                    PH_ESTADO = (String) request.getAttribute("CPH_ESTADO");
                }
                
                // DETALLE ---- ---- ---- ---- ---- ---- ---- ---- ---- ---- 
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
                    varTitulo = "PLAN HORARIO - NUEVO REGISTRO";
//                } else {
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
                                    <div class="pac_panel_2">Dia/s:</div>
                                    <div class="pac_panel_3">
                                        <select id="tPHD" name="tPHD" class="form-control">
                                            <%
                                            Map<String, String> listaDias;
                                            listaDias = metodosIniSes.cargarComboDias(PH_DIA);
                                            Set setCbxDias = listaDias.entrySet();
                                            Iterator iCbxDias = setCbxDias.iterator();

                                            while(iCbxDias.hasNext()) {
                                                Map.Entry mapCbxDias = (Map.Entry) iCbxDias.next();
                                            %>
                                            <option value="<%= mapCbxDias.getKey() %>"><%= mapCbxDias.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Turno:</div>
                                    <div class="pac_panel_3">
                                        <select id="cPHT" name="cPHT" class="form-control">
                                            <%
                                            Map<String, String> listaTurno;
                                            listaTurno = metodosIniSes.cargarComboTurnos(PH_TURNO);
                                            Set setCbxTurno = listaTurno.entrySet();
                                            Iterator iCbxTurno = setCbxTurno.iterator();

                                            while(iCbxTurno.hasNext()) {
                                                Map.Entry mapCbxTurno = (Map.Entry) iCbxTurno.next();
                                            %>
                                            <option value="<%= mapCbxTurno.getKey() %>"><%= mapCbxTurno.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Desde:</div>
                                    <div class="pac_panel_3">
                                        <input type="time" value="<%= PH_DESDE %>" id="tPHFD" name="tPHFD" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Hasta:</div>
                                    <div class="pac_panel_3">
                                        <input type="time" value="<%= PH_HASTA %>" id="tPHFH" name="tPHFH" class="form-control" />
                                    </div>
                                </div>
                                
                                <div class="pac_panel_1 mt-2">
                                    <input type="submit" name="accion" value="Cargar a la lista" class="btn btn-primary"/>
                                </div>
                                
                                <div class="pac_panel_1 mt-2">
                                <%
                                if(((List<BeanPlanHorario>)sesionDatosUsuario.getAttribute("CPH_LISTA_DET")).size() > 0) {
                                %>
                                    <table role="table" class="tablePlanHoDet">
                                        <thead role="rowgroup">
                                            <tr role="row">
                                                <td role="columnheader" class="PHD_CI">#</td>
                                                <td role="columnheader" class="PHD_CT">Turno</td>
                                                <td role="columnheader" class="PHD_CDi">Dia</td>
                                                <td role="columnheader" class="PHD_CDe">Desde</td>
                                                <td role="columnheader" class="PHD_CH">Hasta</td>
                                                <td role="columnheader" class="PHD_CBE">Eliminar</td>
                                            </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                            <%
                                                // ITEM 
                                                String CPH_LISTA_ITEM = (String) sesionDatosUsuario.getAttribute("CPH_ITEM_LISTA_DET");
                                                System.out.println("__JSP__CPH_ITEM_LISTA_DET:   "+CPH_LISTA_ITEM);
                                                sesionDatosUsuario.setAttribute("CPH_ITEM_LISTA_DET", CPH_LISTA_ITEM);
                                                
                                                // LISTA 
                                                List<BeanPlanHorario> CPH_LISTA_DETALLE;
                                                CPH_LISTA_DETALLE = (List<BeanPlanHorario>) sesionDatosUsuario.getAttribute("CPH_LISTA_DET");
                                                Iterator<BeanPlanHorario> iterLista = CPH_LISTA_DETALLE.iterator();
                                                BeanPlanHorario datosTab = new BeanPlanHorario();
                                                int item = 0;
                                                
                                                while(iterLista.hasNext()) {
                                                    item = item + 1;
                                                    
                                                    datosTab = iterLista.next();
                                                    
                                            %>
                                            <tr role="row">
                                                <td role="cell" class="PHD_CI"><%= item %></td>
                                                <td role="cell" class="PHD_CT"><%= metodosIniSes.devolverTextTurno(datosTab.getOPHD_TURNO()) %></td>
                                                <td role="cell" class="PHD_CDi"><%= datosTab.getOPHD_DIA() %></td>
                                                <td role="cell" class="PHD_CDe"><%= datosTab.getOPHD_DESDE() %></td>
                                                <td role="cell" class="PHD_CH"><%= datosTab.getOPHD_HASTA() %></td>
                                                <td role="cell" class="PHD_CBE">
                                                    <form action="CPHSrv" method="post">
                                                        <!--<p><%--= // datosTab.getOPHD_ITEM() --%></p>-->
                                                        <!--<p><%--= // datosTab.getOPH_IDPLANHORARIO() --%></p>-->
                                                        <input type="hidden" value="<%= item %>" name="tAIPHD" id="tAIPHD" class="form-control" />
                                                        <input type="hidden" value="<%= datosTab.getOPHD_ITEM() %>" name="tAIPHDdb" id="tAIPHDdb" class="form-control" />
                                                        <input type="hidden" value="<%= datosTab.getOPH_IDPLANHORARIO() %>" name="tAIPH" id="tAIPH" class="form-control" />
                                                        <input type="submit" value="Eliminar Fila" name="accion" class="btn btn-danger" />
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
                                
                                <div class="boxBtnCtaCli mt-3">
                                    <input type="hidden" name="tAIC" value="<%= PH_IDCLINICA %>" class="form-control"/>
                                    <input type="hidden" name="tAIM" value="<%= PH_IDMEDICO %>" class="form-control"/>
                                    <input type="hidden" name="tAMN" value="<%= PH_NOM_MEDICO %>" class="form-control"/>
                                    <input type="hidden" name="tAMA" value="<%= PH_APE_MEDICO %>" class="form-control"/>
                                    <input type="hidden" name="tAMC" value="<%= PH_CINRO_MEDICO %>" class="form-control"/>
                                    <input type="hidden" name="tAPHE" value="<%= PH_ESTADO %>" class="form-control"/>
                                    
                                    <input type="submit" name="accion" value="Guardar" class="btn btn-outline-primary" />
                                    <input type="submit" name="accion" value="Volver a la cabecera" class="btn btn-warning" />
                                    <a href="<%= urlPlanHorario %>" class="btn btn-danger">CANCELAR</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script>
            function cancelar() {
                document.getElementById("cCli").value = "1";
                document.getElementById("cPHT").value = "M";
                document.getElementById("tPHFD").value = "<%= PH_DESDE_ORI %>";
                document.getElementById("tPHFH").value = "<%= PH_HASTA_ORI %>";
                document.getElementById("tPHD").value = "L";
            }
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>