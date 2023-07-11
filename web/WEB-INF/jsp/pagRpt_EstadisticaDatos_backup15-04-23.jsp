<%-- 
    Document   : pagRpt_EstadisticaDatos
    Created on : 06-mar-2023, 11:36:39
    Author     : RYUU
--%>

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
                <div class="divTable mb-5">
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
                                    String DESC_CLINICA = metodosRefClinica.traerDescClinica(datosBean.getOFPN_IDCLINICA());
                            %>
                            <tr role="row">
                                <td role="cell" class="HFA_CI">
                                    <%= datosBean.getOFPN_IDFICHAPAC()%>
                                </td>
                                <td role="cell" class="HFA_CC">
                                    <%= DESC_CLINICA %>
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
//                OBSERVACION PARA PODER HACER LA DE MOSTRAR UN GRAFICO Y OTRO NO CON PORC GRASA Y PORC MUSC:
//                    * CREAR DOS CHECK O UN COMBO Y AGREGARLE UN EVENTO ONCHANGE PARA DETECTAR EL CAMBIO 
//                        - PRIMERAMENTE VENDRAN MARCADOS LOS DOS 
//                                Y AL DESMARCAR UNO EL ONCHANGE LLAMA A UNA FUNCION QUE VA A DETECTAR CUAL SE DESMARCO Y A OCULTAR ESE DATO DEL GRAFICO A TRAVES DEL DOM 
//                                O ACTUALIZARIA LA PAGINA PERO CON EL ONCHANGE Y EDITANDO EL HTML POR MEDIO DEL DISPLAY O EL APPENDCHILD 
                %>
                <div class="container mb-3 bGray3-0-2">
                    <canvas id="estEstatura"></canvas>
                </div>
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estPeso"></canvas>
                </div>
                <div class="container mt-2 mb-3 bGray3-0-2">
                    <canvas id="estIMC"></canvas>
                </div>
                <div class="" style="border:1px solid red;">
                    <p class="chan">A</p>
                    <input type="checkbox" checked="checked" id="porcgrasa-checkbox" name="porcgrasa-checkbox" class="porcgrasa-checkbox ml-5">
                    <label for="porcgrasa-checkbox" class="mr-3"> Porc. Grasa</label>
                    <input type="checkbox" checked="checked" id="porcmusc-checkbox" name="porcmusc-checkbox" class="porcmusc-checkbox">
                    <label for="porcmusc-checkbox"> Porc. Músculo</label>
                </div>
                <div class="container mb-3 bGray3-0-2">
                    <canvas id="estPGM"></canvas>
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
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <script>
            
            var togglePG = document.querySelector(".porcgrasa-checkbox");
            togglePG.addEventListener('change', function() {
                if(this.checked) {
                    // Checkbox is checked..
                    
                } else {
                    // Checkbox is not checked..
                    
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
//            new Chart(estPorcGrasa, {
//              type: 'bar',
//              data: {
//                labels: [<%= datosCabPorcGrasa %>], // fechas 
//                datasets: [{
//                  label: 'Porc. Grasa en la Ficha: ',
//                  data: [<%= datosDetPorcGrasa %>], // datos 
//                  borderWidth: 1
//                }]
//              },
//              options: {
//                scales: {
//                  y: {
//                    beginAtZero: true
//                  }
//                }
//              }
//            });
            // PORC_MUSCULO.-
//            new Chart(estPorcMusc, {
//              type: 'bar',
//              data: {
//                labels: [<%= datosCabPorcMusc %>], // fechas 
//                datasets: [{
//                  label: 'Porc. Musc. en la Ficha: ',
//                  data: [<%= datosDetPorcMusc %>], // datos 
//                  borderWidth: 1
//                }]
//              },
//              options: {
//                scales: {
//                  y: {
//                    beginAtZero: true
//                  }
//                }
//              }
//            });
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
//            new Chart(estPorcGrasa, {
//              type: 'line',
//              data: {
//                labels: [<%= datosCabPorcGrasa %>], // fechas 
//                datasets: [{
//                  label: 'Porc. Grasa en la Ficha: ',
//                  data: [<%= datosDetPorcGrasa %>], // datos 
//                  borderWidth: 2, 
//                  fill: false, 
//                  borderColor: 'rgb(75, 192, 192)',
//                  pointBorderWidth: 7, 
//                  tension: 0.5
//                }]
//              },
//              options: {
//                scales: {
//                  y: {
//                    beginAtZero: true
//                  }
//                }
//              }
//            });
            // PORC_MUSCULO.-
//            new Chart(estPorcMusc, {
//              type: 'line',
//              data: {
//                labels: [<%= datosCabPorcMusc %>], // fechas 
//                datasets: [{
//                  label: 'Porc. Musc. en la Ficha: ',
//                  data: [<%= datosDetPorcMusc %>], // datos 
//                  borderWidth: 2, 
//                  fill: false, 
//                  borderColor: 'rgb(75, 192, 192)',
//                  pointBorderWidth: 7, 
//                  tension: 0.5
//                }]
//              },
//              options: {
//                scales: {
//                  y: {
//                    beginAtZero: true
//                  }
//                }
//              }
//            });
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
    </body>
</html>