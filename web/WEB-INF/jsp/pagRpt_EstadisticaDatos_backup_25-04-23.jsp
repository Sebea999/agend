<%-- 
    Document   : pagRpt_EstadisticaDatos
    Created on : 06-mar-2023, 11:36:39
    Author     : RYUU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Estadisticas</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CR_RE_MENSAJE"); // CONTROLLER REPORTES REPORTE ESTADISTICA MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CR_RE_TIPO_MENSAJE"); // CONTROLLER REPORTES REPORTE ESTADISTICA TIPO MENSAJE 
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
                    <h4 class="mainTitle">REPORTE DE ESTADISTICA</h4>
                </div>
                <%
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".       _____________JSP________________");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                String TIPO_DE_ESTADISTICA = (String)request.getAttribute("CR_RE_TIPO_ESTADISTICA");
                System.out.println("_   __JSP______TIPO_DE_ESTADISTICA:  :"+TIPO_DE_ESTADISTICA);
                String IDPACIENTE = "", PAC_NOMBRE="", PAC_APELLIDO="";
                if (request.getAttribute("CR_RE_TXT_IDPACIENTE") != null) {
                    IDPACIENTE = (String) request.getAttribute("CR_RE_TXT_IDPACIENTE");
                    PAC_NOMBRE = (String) request.getAttribute("CR_RE_TXT_PAC_NOM");
                    PAC_APELLIDO = (String) request.getAttribute("CR_RE_TXT_PAC_APE");
                }
                System.out.println("_   ___IDPACIENTE_JSP:   :"+IDPACIENTE);
                %>
                <div class="ml-5 mt-3 mb-3">
                    <h5>Del Paciente: <%= (PAC_NOMBRE+" "+PAC_APELLIDO) %></h5>
                </div>
                <div class="divTable mb-5 d-flex flex-column">
                    <table role="table" class="tableFilterListFic table-striped">
                        <thead role="rowgroup">
                            <tr role="row">
                                <td role="columnheader" class="HFA_CI">Cód.</td>
                                <td role="columnheader" class="HFA_CC">Clinica</td>
                                <td role="columnheader" class="HFA_CFH">Fecha y Hora</td>
                                <td role="columnheader" class="HFA_CMC">Motivo de la Consulta</td>
                                <td role="columnheader" class="HFA_CE">Estatura</td>
                                <td role="columnheader" class="HFA_CP">Peso</td>
                                <td role="columnheader" class="HFA_CPG">Porc. Grasa</td>
                                <td role="columnheader" class="HFA_CEM">Edad M.</td>
                                <td role="columnheader" class="HFA_CPM">Porc. Musc.</td>
                                <td role="columnheader" class="HFA_CRM">RM</td>
                                <td role="columnheader" class="HFA_CIMC">IMC</td>
                                <td role="columnheader" class="HFA_CV">Visceral</td>
                                <td role="columnheader" class="HFA_CB">Balanza</td>
                                <td role="columnheader" class="HFA_CS">Estado</td>
                                <!--<td role="columnheader" class="HFA_CA">Acciones</td>-->
                            </tr>
                        </thead>
                        <tbody role="rowgroup">
                            <%
                                // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
    //                                        String NRO_PAG = "";
    //                                        if (sesionDatosUsuario.getAttribute("PAG_RPT_HIST_FAN_LISTA_ACTUAL") == null) {
    //                                            NRO_PAG = "1";
    //                                        } else {
    //                                            NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_RPT_HIST_FAN_LISTA_ACTUAL");
    //                                        }
    //                                        System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);

                                List<BeanFichaAtePaciente> listaDatos = null;
                                if (((List<BeanFichaAtePaciente>)request.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                    listaDatos = (List<BeanFichaAtePaciente>) request.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC");
    //                                        } else { // y si no entonces le decimos que carge nomas a traves del metodo que le pasamos 
    ////                                            String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
    ////                                            String IDPACIENTE = "";
    ////                                            if (metodosPerfil.isPerfilPaciente(idPerfil)) {
    ////                                                IDPACIENTE = idPersona;
    ////                                            } else if (metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil)) {
    ////                                                IDPACIENTE = "";
    ////                                            }
    ////                                            listaDatos = metodosFichaAtencionPacNutri.cargar_grilla_paginacion_rptHistFAN(sesionDatosUsuario, NRO_PAG, cant_min_fija, IDPACIENTE, idPerfil);
    ////                                            sesionDatosUsuario.setAttribute("VAR_PAGI_BAND_FILTRO", "0"); // CON ESTO RESETEO LA BANDERA QUE UTILIZO PARA LA PAGINACION 
    //                                        }
                                Iterator<BeanFichaAtePaciente> iterDatos = listaDatos.iterator();
                                BeanFichaAtePaciente datosBean = null;
                                
                                while(iterDatos.hasNext()) {
                                    datosBean = iterDatos.next();
                                    
                                    // TRAER DATOS DEL MEDICO 
    //                                        BeanPersona datosBPersona = new BeanPersona();
    //                                        datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBean.getOA_IDMEDICO());
                                    // TRAER DATOS CLINICA 
    //                                        BeanClinica datosBClinica = new BeanClinica();
    //                                        datosBClinica = metodosRefClinica.datosBasicosClinica(datosBClinica, datosBean.getOA_IDCLINICA());
//                                    String DESC_CLINICA = metodosRefClinica.traerDescClinica(datosBean.getOFPN_IDCLINICA());
                            %>
                            <tr role="row">
                                <td role="cell" class="HFA_CI">
                                    <%= datosBean.getOFPN_IDFICHAPAC()%>
                                </td>
                                <td role="cell" class="HFA_CC">
                                    <%= datosBean.getOFPN_DESC_CLINICA() %>
                                </td>
                                <td role="cell" class="HFA_CFH">
                                    <%= datosBean.getOFPN_FECHA_FICHA_ATE()%>
                                </td>
                                <td role="cell" class="HFA_CMC">
                                    <%= datosBean.getOFPN_MOTIVO_DE_LA_CONSULTA()%>
                                </td>
                                <td role="cell" class="HFA_CE">
                                    <%= datosBean.getOFPN_ESTATURA()%>
                                </td>
                                <td role="cell" class="HFA_CP">
                                    <%= datosBean.getOFPN_PESO()%>
                                </td>
                                <td role="cell" class="HFA_CPG">
                                    <%= datosBean.getOFPN_PORC_GRASA()%>
                                </td>
                                <td role="cell" class="HFA_CEM">
                                    <%= datosBean.getOFPN_EDAD_METABOLICA()%>
                                </td>
                                <td role="cell" class="HFA_CPM">
                                    <%= datosBean.getOFPN_PORC_MUSCULO()%>
                                </td>
                                <td role="cell" class="HFA_CRM">
                                    <%= datosBean.getOFPN_RM()%>
                                </td>
                                <td role="cell" class="HFA_CIMC">
                                    <%= datosBean.getOFPN_IMC()%>
                                </td>
                                <td role="cell" class="HFA_CV">
                                    <%= datosBean.getOFPN_VISCERAL()%>
                                </td>
                                <td role="cell" class="HFA_CB">
                                    <%= datosBean.getOFPN_TIPO_DE_BALANZA()%>
                                </td>
                                <td role="cell" class="HFA_CS">
                                    <%= metodosIniSes.devolverTextEstadoAgend(datosBean.getOFPN_ESTADO()) %>
                                </td>
    <!--                                        <td role="cell" class="HFA_CA">
                                    <form action="CFAPSrvN" method="post" autocomplete="off">
                                        <input type="hidden" name="tIAP" value="<%= datosBean.getOFPN_IDFICHAPAC() %>" class="form-control"/>
                                        <input type="hidden" name="tIA" value="<%= datosBean.getOFPN_IDAGENDAMIENTO() %>" class="form-control"/>
                                        <input type="hidden" name="tAID" value="<%= datosBean.getOFPN_ITEM_AGEND_DET() %>" class="form-control"/>
                                        <input type="hidden" name="tIP" value="<%--= PAC_IDPACIENTE --%>" class="form-control"/>
                                        <button type="submit" value="Ver Atención" name="accion" class="btn btn-link">Ver</button>
                                    </form>
                                </td>-->
                            </tr>
                            <%
                                }
                            %>
                            <%
                            } else {
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println("-   -----------LISTA-VACIA-------------");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                            }
                            %>
                        </tbody>
                    </table>
                    <div class="mt-3">
                        <form method="post" action="CRSrv" class="d-flex justify-content-around">
                        <div class="d-flex flex-row align-items-center">
                            <label for="RECTE" class="form-label pt-1 mr-2">Tipo de Gráficos:</label>
                            <div>
                                <select class="form-control" style="width: 150px;" id="RECTE" name="RECTE">
                                    <%
                                    Map<String, String> cbxSINORAF = new LinkedHashMap();
                                    cbxSINORAF = metodosFichaAtencionPacNutri.cargarComboTipoEst(TIPO_DE_ESTADISTICA); 
                                    Set setListSINORAF = cbxSINORAF.entrySet();
                                    Iterator iListSINORAF = setListSINORAF.iterator();
                                    while(iListSINORAF.hasNext()) {
                                        Map.Entry mapSelec = (Map.Entry) iListSINORAF.next();
                                    %>
                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                    <%
                                        }
                                    %>
                                </select>
                            </div>
                            <input type="submit" value="Recargar los Gráficos" name="accionRptEst" class="btn btn-primary ml-2" />
                        </div>
                        <div>
                            <input type="hidden" value="<%= IDPACIENTE %>" name="tIC" class="form-control disNone" />
                            <input type="hidden" value="<%= PAC_NOMBRE %>" name="tICN" class="form-control disNone" />
                            <input type="hidden" value="<%= PAC_APELLIDO %>" name="tICA" class="form-control disNone" />
                            <input type="hidden" value="<%= TIPO_DE_ESTADISTICA %>" name="RECTE" class="form-control disNone" />
                            <input type="submit" value="Exportar a Excel" name="accionRptEst" class="btn btn-success" />
                            <%--<a href="rptexcel.htm" class="btn btn-success mt-2 mb-2">Exportar a Excel (Jsp)</a>--%>
                        </div>
                        </form>
                    </div>
                </div>
                <%
                List<BeanFichaAtePaciente> listado_fichas = metodosFichaAtencionPacNutri.getListadoFichasPac(IDPACIENTE);
                BeanFichaAtePaciente datos_fichas = null;
                String datosCabEstatura = "";
                String datosDetEstatura = "";
                String datosCabPeso = "";
                String datosDetPeso = "";
                String datosCabIMC = "";
                String datosDetIMC = "";
                String datosCabPorcGrasa = "";
                String datosDetPorcGrasa = "";
                String datosCabPorcMusc = "";
                String datosDetPorcMusc = "";
                String datosCabVisceral = "";
                String datosDetVisceral = "";
                String datosCabEdadM = "";
                String datosDetEdadM = "";
                for (int indice = 0; indice < listado_fichas.size(); indice++) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".___________FOR_LISTADO____________");
                    System.out.println("_   _indice:  :"+indice);
                    System.out.println(".");
                    if (indice == (listado_fichas.size()-1)) {
                        System.out.println("__IF________________");
                        datos_fichas = listado_fichas.get(indice);
                        // estatura 
                        datosCabEstatura = datosCabEstatura + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetEstatura = datosDetEstatura + "'"+datos_fichas.getOFPN_ESTATURA()+"'";
                        // peso 
                        datosCabPeso = datosCabPeso + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetPeso = datosDetPeso + "'"+datos_fichas.getOFPN_PESO()+"'";
                        // imc 
                        datosCabIMC = datosCabIMC + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetIMC = datosDetIMC + "'"+datos_fichas.getOFPN_IMC()+"'";
                        // porc_grasa 
                        datosCabPorcGrasa = datosCabPorcGrasa + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetPorcGrasa = datosDetPorcGrasa + "'"+datos_fichas.getOFPN_PORC_GRASA()+"'";
                        // porc_musculo 
                        datosCabPorcMusc = datosCabPorcMusc + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetPorcMusc = datosDetPorcMusc + "'"+datos_fichas.getOFPN_PORC_MUSCULO()+"'";
                        // visceral 
                        datosCabVisceral = datosCabVisceral + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetVisceral = datosDetVisceral + "'"+datos_fichas.getOFPN_VISCERAL()+"'";
                        // edad_m 
                        datosCabEdadM = datosCabEdadM + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'";
                        datosDetEdadM = datosDetEdadM + "'"+datos_fichas.getOFPN_EDAD_METABOLICA()+"'";
                    } else {
                        System.out.println("__else______________");
                        datos_fichas = listado_fichas.get(indice);
                        // estatura 
                        datosCabEstatura = datosCabEstatura + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetEstatura = datosDetEstatura + datos_fichas.getOFPN_ESTATURA()+ ",";
                        // peso 
                        datosCabPeso = datosCabPeso + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetPeso = datosDetPeso + datos_fichas.getOFPN_PESO() + ",";
                        // imc 
                        datosCabIMC = datosCabIMC + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetIMC = datosDetIMC + datos_fichas.getOFPN_IMC() + ",";
                        // porc_grasa 
                        datosCabPorcGrasa = datosCabPorcGrasa + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetPorcGrasa = datosDetPorcGrasa + datos_fichas.getOFPN_PORC_GRASA()+ ",";
                        // porc_musculo 
                        datosCabPorcMusc = datosCabPorcMusc + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetPorcMusc = datosDetPorcMusc + datos_fichas.getOFPN_PORC_MUSCULO()+ ",";
                        // visceral 
                        datosCabVisceral = datosCabVisceral + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetVisceral = datosDetVisceral + datos_fichas.getOFPN_VISCERAL()+ ",";
                        // edad_m 
                        datosCabEdadM = datosCabEdadM + "'"+datos_fichas.getOFPN_FECHA_FICHA_ATE()+"'" + ",";
                        datosDetEdadM = datosDetEdadM + datos_fichas.getOFPN_EDAD_METABOLICA()+ ",";
                    }
                    System.out.println(".");
                    System.out.println(".");
                }
                sesionDatosUsuario.setAttribute("GRAFICOS_ESTATURA_CAB", datosCabEstatura);
                sesionDatosUsuario.setAttribute("GRAFICOS_ESTATURA_DET", datosDetEstatura);
                System.out.println("____________END_TO__FOR_____________________");
                System.out.println("_____ESTATURA_____________________");
                System.out.println("_       __datos_cab: :"+datosCabEstatura);
                System.out.println("_       __datos_det: :"+datosDetEstatura);
                System.out.println("_____PESO_________________________");
                System.out.println("_       __datos_cab: :"+datosCabPeso);
                System.out.println("_       __datos_det: :"+datosDetPeso);
                System.out.println("_____IMC__________________________");
                System.out.println("_       __datos_cab: :"+datosCabIMC);
                System.out.println("_       __datos_det: :"+datosDetIMC);
                System.out.println("_____PORC_GRASA___________________");
                System.out.println("_       __datos_cab: :"+datosCabPorcGrasa);
                System.out.println("_       __datos_det: :"+datosDetPorcGrasa);
                System.out.println("_____PORC_MUSC____________________");
                System.out.println("_       __datos_cab: :"+datosCabPorcMusc);
                System.out.println("_       __datos_det: :"+datosDetPorcMusc);
                System.out.println("_____VISCERAL_____________________");
                System.out.println("_       __datos_cab: :"+datosCabVisceral);
                System.out.println("_       __datos_det: :"+datosDetVisceral);
                System.out.println("_____EDAD_M_______________________");
                System.out.println("_       __datos_cab: :"+datosCabEdadM);
                System.out.println("_       __datos_det: :"+datosDetEdadM);
                System.out.println("__________________________________");
                %>
                
                <a href="#" onclick="downloadPDF()" id="downloadPdf">Download Report Page as PDF</a>
                <br/><br/>
                <div id="reportPage" class="mb-5 mt-5" style="border:1px solid red; width: 800px; height: 500px;">
                  <div id="chartContainer" style="width: 30%;float: left;">
                    <div class="container mt-2 mb-3 bGray3-0-2">
                        <canvas id="estPeso"></canvas>
                    </div>
                  </div>
                </div>
                
                <div class="container mb-3 bGray3-0-2">
                    <canvas id="estEstatura"></canvas>
                </div>
<!--                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estPeso"></canvas>
                </div>-->
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estIMC"></canvas>
                </div>
                <div class="text-center">
                    <input type="checkbox" checked="checked" id="porcgrasa-checkbox" name="porcgrasa-checkbox" class="porcgrasa-checkbox ml-5">
                    <label for="porcgrasa-checkbox" class="mr-3"> Porc. Grasa</label>
                    <input type="checkbox" checked="checked" id="porcmusc-checkbox" name="porcmusc-checkbox" class="porcmusc-checkbox">
                    <label for="porcmusc-checkbox"> Porc. Músculo</label>
                </div>
                <div class="container mb-3 bGray3-0-2">
                    <canvas id="estPGM"></canvas>
                    <canvas id="estPorcGrasa" style="display: none;"></canvas>
                    <canvas id="estPorcMusc" style="display: none;"></canvas>
                </div>
<%--                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estPorcGrasa"></canvas>
                </div>
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estPorcMusc"></canvas>
                </div>--%>
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estVisceral"></canvas>
                </div>
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estEdadM"></canvas>
                </div>
                <div class="mt-5">
                    <form action="CRSrv" method="post">
                        <button type="submit" value="Limpiar" name="accionRptEst" class="btn btn-danger">Volver Atras</button>
                    </form>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <!--<script src="${pageContext.request.contextPath}/recursos/js/rptEstDownloadGraf.js" type="text/javascript"></script>-->
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.4/jspdf.debug.js"></script>-->
        
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdf.js/2.11.338/pdf.min.js" integrity="sha512-t2JWqzirxOmR9MZKu+BMz0TNHe55G5BZ/tfTmXMlxpUY8tsTo3QMD27QGoYKZKFAraIPDhFv56HLdN11ctmiTQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js" integrity="sha384-NaWTHo/8YCBYJ59830LTz/P4aQZK1sS0SneOgAvhsIl3zBu8r9RevNg5lHCHAuQ/" crossorigin="anonymous"></script>-->
        <!--<script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.3.2/html2canvas.js" integrity="sha512-sk0cNQsixYVuaLJRG0a/KRJo9KBkwTDqr+/V94YrifZ6qi8+003iJEoHi0LvcTVv1HaBbbIvpx+MCjOuLVnwkg==" crossorigin="anonymous"></script>-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.2/jspdf.debug.js"></script>
        <script>
            var togglePG = document.querySelector(".porcgrasa-checkbox");
            var togglePM = document.querySelector(".porcmusc-checkbox");
            // porcentaje grasa 
            togglePG.addEventListener('change', function() {
                if(this.checked) {
                    // Checkbox is checked..
                    // ...
                    if (togglePM.checked) {
                        document.getElementById('estPGM').style.display = 'block';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPorcMusc').style.display = 'none';
                    } else {
                        document.getElementById('estPorcMusc').style.display = 'none';
                        document.getElementById('estPorcGrasa').style.display = 'block';
                        document.getElementById('estPGM').style.display = 'none';
                    }
                } else {
                    // Checkbox is not checked..
                    // ...
                    if (togglePM.checked) {
                        document.getElementById('estPGM').style.display = 'none';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPorcMusc').style.display = 'block';
                    } else {
                        document.getElementById('estPorcMusc').style.display = 'none';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPGM').style.display = 'none';
                    }
                }
            });
            // porcentaje musculo 
            togglePM.addEventListener('change', function() {
                if(this.checked) {
                    // Checkbox is checked..
                    //...
                    if (togglePG.checked) {
                        document.getElementById('estPGM').style.display = 'block';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPorcMusc').style.display = 'none';
                    } else {
                        document.getElementById('estPorcMusc').style.display = 'block';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPGM').style.display = 'none';
                    }
                } else {
                    // Checkbox is not checked..
                    // ...
                    if (togglePG.checked) {
                        document.getElementById('estPGM').style.display = 'none';
                        document.getElementById('estPorcGrasa').style.display = 'block';
                        document.getElementById('estPorcMusc').style.display = 'none';
                    } else {
                        document.getElementById('estPorcMusc').style.display = 'none';
                        document.getElementById('estPorcGrasa').style.display = 'none';
                        document.getElementById('estPGM').style.display = 'none';
                    }
                }
            });
            
            const estEstatura = document.getElementById('estEstatura');
            const estPeso = document.getElementById('estPeso');
            const estIMC = document.getElementById('estIMC');
            const estPorcGrasa = document.getElementById('estPorcGrasa');
            const estPorcMusc = document.getElementById('estPorcMusc');
            const estVisceral = document.getElementById('estVisceral');
            const estEdadM = document.getElementById('estEdadM');
            // Obtener una referencia al elemento canvas del DOM
            const estPGM = document.querySelector("#estPGM");
        <%
        if (TIPO_DE_ESTADISTICA.equals("1")) { // grafica en barras 
        %>
            // ESTATURA.-
            new Chart(estEstatura, {
              type: 'bar',
              data: {
                labels: [<%= datosCabEstatura %>], // fechas 
//                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'], // fechas 
                datasets: [{
                  label: 'Estatura en la Ficha: ',
//                  label: '# of Votes',
                  data: [<%= datosDetEstatura %>], // datos 
//                  data: [12, 19, 3, 5, 2, 3], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PESO.-
            new Chart(estPeso, {
              type: 'bar',
              data: {
                labels: [<%= datosCabPeso %>], // fechas 
                datasets: [{
                  label: 'Peso en la Ficha: ',
                  data: [<%= datosDetPeso %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // IMC.-
            new Chart(estIMC, {
              type: 'bar',
              data: {
                labels: [<%= datosCabIMC %>], // fechas 
                datasets: [{
                  label: 'IMC en la Ficha: ',
                  data: [<%= datosDetIMC %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PORCENTAJE DE GRASA Y PORCENTAJE DE MUSCULO.-
            // Las etiquetas son las que van en el eje X. 
            const etiquetas = [<%= datosCabPorcGrasa %>]
            // Podemos tener varios conjuntos de datos
            const datosPorcGrasa = {
                label: "Porcentaje de Grasa",
                data: [<%= datosDetPorcGrasa %>], // La data es un arreglo que debe tener la misma cantidad de valores que la cantidad de etiquetas
                backgroundColor: 'rgba(255, 159, 64, 0.6)',// Color de fondo
                borderColor: 'rgba(255, 159, 64, 1)',// Color del borde
                borderWidth: 1,// Ancho del borde
            };
            const datosPorcMusc = {
                label: "Porcentaje de Músculo",
                data: [<%= datosDetPorcMusc %>], // La data es un arreglo que debe tener la misma cantidad de valores que la cantidad de etiquetas
                backgroundColor: 'rgba(54, 162, 235, 0.6)', // Color de fondo
                borderColor: 'rgba(54, 162, 235, 1)', // Color del borde
                borderWidth: 1,// Ancho del borde
            };
            new Chart(estPGM, {
                type: 'bar',// Tipo de gráfica
                data: {
                    labels: etiquetas,
                    datasets: [
                        datosPorcGrasa,
                        datosPorcMusc,
                    ]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }],
                    },
                }
            });
            // PORC_GRASA.-
            new Chart(estPorcGrasa, {
              type: 'bar',
              data: {
                labels: [<%= datosCabPorcGrasa %>], // fechas 
                datasets: [{
                  label: 'Porc. Grasa en la Ficha: ',
                  data: [<%= datosDetPorcGrasa %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PORC_MUSCULO.-
            new Chart(estPorcMusc, {
              type: 'bar',
              data: {
                labels: [<%= datosCabPorcMusc %>], // fechas 
                datasets: [{
                  label: 'Porc. Musc. en la Ficha: ',
                  data: [<%= datosDetPorcMusc %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // VISCERAL.-
            new Chart(estVisceral, {
              type: 'bar',
              data: {
                labels: [<%= datosCabVisceral %>], // fechas 
                datasets: [{
                  label: 'Visceral en la Ficha: ',
                  data: [<%= datosDetVisceral %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // EDAD_M.-
            new Chart(estEdadM, {
              type: 'bar',
              data: {
                labels: [<%= datosCabEdadM %>], // fechas 
                datasets: [{
                  label: 'Edad M. en la Ficha: ',
                  data: [<%= datosDetEdadM %>], // datos 
                  borderWidth: 1
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
        <%
        } else if (TIPO_DE_ESTADISTICA.equals("0")) { // grafica en lineas 
        %>
            // ESTATURA.-
            new Chart(estEstatura, {
              type: 'line',
              data: {
                labels: [<%= datosCabEstatura %>], // fechas 
//                labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'], // fechas 
                datasets: [{
                  label: 'Estatura en la Ficha: ',
//                  label: '# of Votes',
                  data: [<%= datosDetEstatura %>], // datos 
//                  data: [12, 19, 3, 5, 2, 3], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PESO.-
            new Chart(estPeso, {
              type: 'line',
              data: {
                labels: [<%= datosCabPeso %>], // fechas 
                datasets: [{
                  label: 'Peso en la Ficha: ',
                  data: [<%= datosDetPeso %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // IMC.-
            new Chart(estIMC, {
              type: 'line',
              data: {
                labels: [<%= datosCabIMC %>], // fechas 
                datasets: [{
                  label: 'IMC en la Ficha: ',
                  data: [<%= datosDetIMC %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PORCENTAJE DE GRASA Y PORCENTAJE DE MUSCULO.-
            // Las etiquetas son las que van en el eje X. 
            const etiquetas = [<%= datosCabPorcGrasa %>]
            // Podemos tener varios conjuntos de datos
            const datosPorcGrasa = {
                label: "Porcentaje de Grasa",
                data: [<%= datosDetPorcGrasa %>], // La data es un arreglo que debe tener la misma cantidad de valores que la cantidad de etiquetas
                backgroundColor: 'rgba(255, 159, 64, 0.2)',// Color de fondo
                borderColor: 'rgba(255, 159, 64, 1)',// Color del borde
                borderWidth: 2,// Ancho del borde
                fill: false,
                pointBorderWidth: 7,
                tension: 0.5,
            };
            const datosPorcMusc = {
                label: "Porcentaje de Músculo",
                data: [<%= datosDetPorcMusc %>], // La data es un arreglo que debe tener la misma cantidad de valores que la cantidad de etiquetas
                backgroundColor: 'rgba(54, 162, 235, 0.2)', // Color de fondo
                borderColor: 'rgba(54, 162, 235, 1)', // Color del borde
                borderWidth: 2,// Ancho del borde
                fill: false,
                pointBorderWidth: 7,
                tension: 0.5,
            };
            new Chart(estPGM, {
                type: 'line',// Tipo de gráfica
                data: {
                    labels: etiquetas,
                    datasets: [
                        datosPorcGrasa,
                        datosPorcMusc
                    ]
                },
                options: {
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: true
                            }
                        }],
                    },
                }
            });
            // PORC_GRASA.-
            new Chart(estPorcGrasa, {
              type: 'line',
              data: {
                labels: [<%= datosCabPorcGrasa %>], // fechas 
                datasets: [{
                  label: 'Porc. Grasa en la Ficha: ',
                  data: [<%= datosDetPorcGrasa %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // PORC_MUSCULO.-
            new Chart(estPorcMusc, {
              type: 'line',
              data: {
                labels: [<%= datosCabPorcMusc %>], // fechas 
                datasets: [{
                  label: 'Porc. Musc. en la Ficha: ',
                  data: [<%= datosDetPorcMusc %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // VISCERAL.-
            new Chart(estVisceral, {
              type: 'line',
              data: {
                labels: [<%= datosCabVisceral %>], // fechas 
                datasets: [{
                  label: 'Visceral en la Ficha: ',
                  data: [<%= datosDetVisceral %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
            // Edad_M.-
            new Chart(estEdadM, {
              type: 'line',
              data: {
                labels: [<%= datosCabEdadM %>], // fechas 
                datasets: [{
                  label: 'Edad M. en la Ficha: ',
                  data: [<%= datosDetEdadM %>], // datos 
                  borderWidth: 2, 
                  fill: false, 
                  borderColor: 'rgb(75, 192, 192)',
                  pointBorderWidth: 7, 
                  tension: 0.5
                }]
              },
              options: {
                scales: {
                  y: {
                    beginAtZero: true
                  }
                }
              }
            });
        <%
        }
        %>
        </script>
        <script>
        
//        const doc = new jsPDF();
        
        
        const bgColor = {
            id: 'bgColor',
            beforeDraw: (chart, steps, options) => {
                const { ctx, width, height } = chart;
                ctx.fillStyle = options.backgroundColor; // 'white';
                ctx.fillRect(0,0,width,height)
                ctx.restore();
            }
        }
        
        const config = {
            type: 'bar',
            data,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                },
                plugins: {
                    bgColor: {
                        backgroundColor: 'blue';
                    }
                }
            }
        }
        
        // render init block
        const myChart = new Chart(document.getElementById('estPeso'), config);
        
        function downloadPDF() {
            const canvas = document.getElementById('estPeso');
            
            // create image
            const canvasImage = canvas.toDataURL('image/jpeg', 1.0);
            console.log(canvasImage);
            // image must go to PDF
            let pdf = new jsPDF('landscape');
            pdf.setFontSize(20);
            pdf.addImage(canvasImage, 'JPEG', 15, 15, 280, 150);
            pdf.text(15, 15, "we have discovered that our sales on monday is the highest");
            pdf.save();
        }
        
//        $('#downloadPdf').click(function(event) {
//            console.log("-----------clic-download-pdf------------");
//        //    doc.text("Hello world!", 10, 10);
//        //    doc.save("example.pdf");
//            
//          // get size of report page
//          var reportPageHeight = $('#reportPage').innerHeight();
//          var reportPageWidth = $('#reportPage').innerWidth();
//
//          // create a new canvas object that we will populate with all other canvas objects
//          var pdfCanvas = $('<canvas />').attr({
//            id: "canvaspdf",
//            width: reportPageWidth,
//            height: reportPageHeight
//          });
//
//          // keep track canvas position
//          var pdfctx = $(pdfCanvas)[0].getContext('2d');
//          var pdfctxX = 0;
//          var pdfctxY = 0;
//          var buffer = 100;
//
//          // for each chart.js chart
//          $("canvas").each(function(index) {
//            // get the chart height/width
//            var canvasHeight = $(this).innerHeight();
//            var canvasWidth = $(this).innerWidth();
//
//            // draw the chart into the new canvas
//            pdfctx.drawImage($(this)[0], pdfctxX, pdfctxY, canvasWidth, canvasHeight);
//            pdfctxX += canvasWidth + buffer;
//
//            // our report page is in a grid pattern so replicate that in the new canvas
//            if (index % 2 === 1) {
//              pdfctxX = 0;
//              pdfctxY += canvasHeight + buffer;
//            }
//          });
//
//          // create new pdf and add our new canvas as an image
//          var pdf = new jsPDF('l', 'pt', [reportPageWidth, reportPageHeight]);
//          pdf.addImage($(pdfCanvas)[0], 'PNG', 0, 0);
//
//          // download the pdf
//          pdf.save('filename.pdf');
//        });
        </script>
    </body>
</html>