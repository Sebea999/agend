<%-- 
    Document   : pagAgendAdd_SecreFilter
    Created on : 08-abr-2022, 14:12:16
    Author     : RYUU
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.agend.javaBean.BeanPlanHorario"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamiento</title>
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
                String mensaje = (String) request.getAttribute("CA_MENSAJE"); // CONTROLLER AGENDAMIENTO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CA_TIPO_MENSAJE"); // CONTROLLER AGENDAMIENTO TIPO MENSAJE 
                String claseMensaje = "";
                String segundo_mensaje = "";
                
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
                    String SEGUNDO_MENSAJE = "";
                    System.out.println("_   __JSP___SEGUNDO_MENSAJE:      :"+SEGUNDO_MENSAJE);
                    if ((sesionDatosUsuario.getAttribute("CA_SEGUNDO_MENSAJE") == null) == false) {
                        SEGUNDO_MENSAJE = (String) sesionDatosUsuario.getAttribute("CA_SEGUNDO_MENSAJE");
                        segundo_mensaje = "<p>"+SEGUNDO_MENSAJE+"</p>";
                        // LIMPIO LAS VARIABLES PARA NO MANTENER DATOS EN LA SESION Y ASI SE PUEDAN VOLVER A CARGAR CUANDO LA VALIDACION SE VUELVA A ACTIVAR DESDE EL CONTROLADOR 
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_DIAS");
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_DESDE");
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_HASTA");
                        sesionDatosUsuario.removeAttribute("CA_SEGUNDO_MENSAJE");
                    }
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                    <%= segundo_mensaje %>
                </div>
            <%
                }
            %>
            <%
                String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                System.out.println("_jsp___ID_AGENDAMIENTO:  "+ID_AGENDAMIENTO);
                
//                List<BeanPersona> listaDatos;
                String AGEN_MOTIVO_CONSULTA="", AGEN_IDCLINICA="", AGEN_CANT_IDCLINICA="", AGEN_IDPACIENTE="", AGEN_FECHA="", AGEN_HORA="", AGEN_IDMEDICO="", AGEN_IDESPECIALIDAD="", AGEN_ESTADO="";
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CA_AGEN_IDESPECIALIDAD") != null) {
                    AGEN_IDESPECIALIDAD = (String) request.getAttribute("CA_AGEN_IDESPECIALIDAD");
                }
                if ((String)request.getAttribute("CA_AGEN_IDPACIENTE") != null) {
                    AGEN_IDPACIENTE = (String) request.getAttribute("CA_AGEN_IDPACIENTE");
                }
                if ((String)request.getAttribute("CA_AGEN_IDMEDICO") != null) {
                    AGEN_IDMEDICO = (String) request.getAttribute("CA_AGEN_IDMEDICO");
                }
                if ((String)request.getAttribute("CA_AGEN_MOTIVO_CONSULTA") != null) {
                    AGEN_MOTIVO_CONSULTA = (String) request.getAttribute("CA_AGEN_MOTIVO_CONSULTA");
                }
                if ((String)request.getAttribute("CA_AGEN_FECHA") != null) {
                    AGEN_FECHA = (String) request.getAttribute("CA_AGEN_FECHA");
                }
                if ((String)request.getAttribute("CA_AGEN_HORA") != null) {
                    AGEN_HORA = (String) request.getAttribute("CA_AGEN_HORA");
                }
                if ((String)request.getAttribute("CA_AGEN_IDCLINICA") != null) {
                    AGEN_IDCLINICA = (String) request.getAttribute("CA_AGEN_IDCLINICA");
                }
                if ((String)request.getAttribute("CA_AGEN_CANT_IDCLINICA") != null) {
                    AGEN_CANT_IDCLINICA = (String) request.getAttribute("CA_AGEN_CANT_IDCLINICA");
                }
                
                // VARIABLES DE LOS FILTROS DE LA PAGINA --
                String TXT_FILTER_IDMEDICO="", TXT_FILTER_IDCLINICA="";
                if ((String)request.getAttribute("CA_FILTER_IDMEDICO") != null) {
                    TXT_FILTER_IDMEDICO = (String) request.getAttribute("CA_FILTER_IDMEDICO");
                }
                if ((String)request.getAttribute("CA_FILTER_IDCLINICA") != null) {
                    TXT_FILTER_IDCLINICA = (String) request.getAttribute("CA_FILTER_IDCLINICA");
                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR AGENDAMIENTOS, ENTONCES DE ACUERDO AL IDMEDICO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO MEDICO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "AGREGAR AGENDAMIENTO / Filtro";
//                if (ID_AGENDAMIENTO.isEmpty() || ID_AGENDAMIENTO.equals("")) {
//                    varTitulo = "AGENDAMIENTO - NUEVO REGISTRO";
//                } else {
//                    varTitulo = "AGENDAMIENTO - MODIFICAR REGISTRO";
//                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CASrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2"><strong>Médico:</strong></div>
                                    <div class="pac_panel_3">
                                        <div class="/*wi-550 wi-550max wi-550min*/">
                                            <%--<p class="mr-1">Médico:</p>--%>
                                            <select name="cbxAddNewMed" id="cbxMed" class="cbxMed form-control wi-550">
                                            <%
                                                Map<String, String> listaMedico;
                                                listaMedico = metodosMedico.traerComboMedicosPH(TXT_FILTER_IDMEDICO, sesionDatosUsuario);
//                                                listaMedico = metodosMedico.traerComboMedicos(TXT_FILTER_IDMEDICO, sesionDatosUsuario);
                                                Set setLisMed = listaMedico.entrySet();
                                                Iterator iterLisMed = setLisMed.iterator();

                                                while(iterLisMed.hasNext()) {
                                                    Map.Entry mapaListMed = (Map.Entry) iterLisMed.next();
                                            %>
                                                <option value="<%= mapaListMed.getKey() %>"><%= mapaListMed.getValue() %></option>
                                            <%
                                                }
                                            %>
                                            </select>
                                            <%--<div class="med_prueba" id="med_prueba"></div>--%>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2"><strong>Clinica:</strong></div>
                                    <div class="pac_panel_3">
                                        <div>
                                            <%--<p class="mr-1">Clinica:</p>--%>
                                            <select name="cbxAddNewClinica" id="cbxCli" class="cbxClinica form-control wi-550">
                                            <%
                                                Map<String, String> listaClinica;
                                                listaClinica = metodosRefClinica.cargarComboClinica(TXT_FILTER_IDCLINICA);
                                                Set setLisCli = listaClinica.entrySet();
                                                Iterator iterLisCli = setLisCli.iterator();

                                                while(iterLisCli.hasNext()) {
                                                    Map.Entry mapaListCli = (Map.Entry) iterLisCli.next();
                                            %>
                                                <option value="<%= mapaListCli.getKey() %>"><%= mapaListCli.getValue() %></option>
                                            <%
                                                }
                                            %>
                                            </select>
                                            <%--<div class="cli_prueba" id="cli_prueba"></div>--%>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="pac_panel_1 mt-3">
                                    <% /*table-responsive*/
                                    if(((List<BeanPlanHorario>)request.getAttribute("CA_LISTA_PH_MEDICO")).size() > 0) {
                                    %>
                                    <table role="table" class="tableAASF" style="width: 90%; margin:0;">
                                    <%
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".   _   ___GRILLA_DIAS_DE_LA_SEMANA____");
                                        System.out.println(".");
                                    %>
                                        <thead role="rowgroup">
                                            <tr role="row">
                                                <td role="columnheader" class="TAAF_CDi">Día</td>
                                                <td role="columnheader" class="TAAF_CT">Turno</td>
                                                <td role="columnheader" class="TAAF_CDe">Desde</td>
                                                <td role="columnheader" class="TAAF_CH">Hasta</td>
                                            </tr>
                                        </thead>
                                        <tbody role="rowgroup">
                                    <%
                                        String DIA, TURNO, DESDE, HASTA;
                                        // CARGO LA LISTA DEL DETALLE 
                                        List<BeanPlanHorario> listaDetalle;
                                        listaDetalle = (List<BeanPlanHorario>) request.getAttribute("CA_LISTA_PH_MEDICO");
//                                                                listaDetalle = metodosPlanHorario.traer_datos(ID_PLAN_HORARIO, sesionDatosUsuario);

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
//                                            System.out.println(".");
//                                            System.out.println(".");
                                            String DIA_SEMANA = String.valueOf(iListDias.next());
//                                            System.out.println("___JSP___DIA_SEMANA:      :"+DIA_SEMANA);
//                                            System.out.println(".");
//                                            System.out.println(".");

                                            // Y LUEGO RECORRO EL DETALLE PARA PODER IR ORDENANDO SEGUN LOS DIAS DE LA SEMANA 
                                            for(BeanPlanHorario datosTab : listaDetalle) {
//                                                System.out.println(".");
//                                                System.out.println(".    FOR_DETALLE_PH   .");
//                                                System.out.println(".");
                                                DIA = datosTab.getOPHD_DIA();
//                                                System.out.println("_______DIA_TABLA:     :"+DIA);

                                                // CONTROLO QUE LA LINEA DEL DETALLE SEA IGUAL A LA LINEA DE LA SEMANA QUE SE ESTA RECORRIENDO, Y SI SON IGUALES LA IMPRIMO Y SI NO ENTONCES NO LE MUESTRO PORQUE VA A LLEGAR A SU TURNO YA QUE TODOS LOS DIAS DE LA SEMANA SE VAN A RECORRER 
                                                if(DIA_SEMANA.equalsIgnoreCase(DIA)) {
                                                    // OBTENGO LOS DATOS DEL DETALLE 
                                                    TURNO = metodosIniSes.devolverTextTurno(datosTab.getOPHD_TURNO());
                                                    DESDE = datosTab.getOPHD_DESDE();
                                                    HASTA = datosTab.getOPHD_HASTA();
                                    %>
                                            <tr role="row">
                                                <td role="cell" class="TAAF_CDi"><span><%= DIA %></span></td>
                                                <td role="cell" class="TAAF_CT"><%= TURNO %></td>
                                                <td role="cell" class="TAAF_CDe"><%= DESDE %></td>
                                                <td role="cell" class="TAAF_CH"><%= HASTA %></td>
                                            </tr>
                                            <%
                                                } // IF_DAYS 
                                            } // FOR_DETALLE 
                                        } // WHILE_LIST_DAYS 
                                            %>
                                        </tbody>
                                    </table>
                                    <%
                                        }
                                    %>
                                </div>
                                
                                <div class="pac_panel_btns mt-4">
                                    <input type="hidden" value="<%= AGEN_IDESPECIALIDAD %>" name="tAIE" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_IDPACIENTE %>" name="tAIP" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_IDMEDICO %>" name="tAIM" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_MOTIVO_CONSULTA %>" name="TAMCA" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_FECHA %>" name="tAFA" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_HORA %>" name="tAHA" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_IDCLINICA %>" name="tAAIC" class="form-control" />
                                    <input type="hidden" value="<%= AGEN_CANT_IDCLINICA %>" name="tAACIC" class="form-control" />
                                    <button type="submit" value="Ver Plan Horario" name="accion" class="btn btn-primary">Ver Plan Horario</button>
                                    <button type="submit" value="Volver a la cabecera" name="accion" class="btn btn-danger">Volver Atras</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
<%--        <script src="${pageContext.request.contextPath}/recursos/js/calendarApp.js"></script>--%>
        <%--<script src="${pageContext.request.contextPath}/recursos/js/AgenPHM.js"></script>--%>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>