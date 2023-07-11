<%-- 
    Document   : pagRpt_MisAgendamientos
    Created on : 03-may-2022, 11:58:48
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
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
        <title>Reporte - Historico de Fichas de Atención</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CR_RFAN_MENSAJE"); // CONTROLLER REPORTES- REPORTE FICHA ATENCION NUTRI- MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CR_RFAN_TIPO_MENSAJE"); // CONTROLLER REPORTES- REPORTE FICHA ATENCION NUTRI- TIPO MENSAJE 
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
                
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".   -----------------   JSP - HIST - FICHA - ATENCION - PAC   -----------------------------");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                String PAC_IDPACIENTE = (String) request.getAttribute("CR_RFAN_TXT_IDPACIENTE");
                System.out.println(".   ___JSP_________ID_PACIENTE:   :"+PAC_IDPACIENTE);
                String PACIENTE_NAME = (String) request.getAttribute("CR_RFAN_TXT_NAME_PACIENTE");
                System.out.println(".   ___JSP_________PACIENTE_NAME: :"+PACIENTE_NAME);
                // recupero el nombre y apellido del paciente del cual estoy recuperando las fichas de atencion 
//                String NOMBRE_PAC = (String) request.getAttribute("CR_RFAN_TXT_PAC_NOM");
//                if (NOMBRE_PAC == null || NOMBRE_PAC.isEmpty()) {
//                    NOMBRE_PAC = "";
//                }
//                String APELLIDO_PAC = (String) request.getAttribute("txtPacApellido");
//                if (APELLIDO_PAC == null || APELLIDO_PAC.isEmpty()) {
//                    APELLIDO_PAC = "";
//                }
                String PAG_TITULO = "VER HISTORICO DE FICHAS DE ATENCIÓN";
//                if (NOMBRE_PAC.isEmpty() && APELLIDO_PAC.isEmpty()) {
//                    System.out.println("_   ___JSP_______NOMBRE_Y_APELLIDO_DEL_PACIENTE________IS_NULL_FOR_TITLE_________");
//                } else {
//                    PAG_TITULO = "VER HISTORICO DE FICHAS DE ATENCIÓN DE / "+NOMBRE_PAC+" "+APELLIDO_PAC;
//                }
                %>
                <div>
                    <h4 class="mainTitle"><%=PAG_TITULO%></h4>
                    <h5 class="mt-2 ml-2"><strong>De:</strong> <%= PACIENTE_NAME %></h5>
                </div>
                <form action="CRSrv" method="post" autocomplete="off">
                    <%
                        // RECUPERAMOS LOS DATOS DEL FILTRO EN CASO DE QUE SE HAGA PARA VOLVER A MOSTRAR Y SI NO, ENTONCES PASARIA DATOS VACIOS 
//                        String cbxMostrar = (String) request.getAttribute("CR_RFAN_CBX_MOSTRAR");
//                        if (cbxMostrar == null || cbxMostrar.isEmpty()) {
//                            cbxMostrar = "";
//                        }
//                        String txtFiltro = (String) request.getAttribute("CR_RFAN_TXT_BUSCAR");
//                        if (txtFiltro == null || txtFiltro.isEmpty()) {
//                            txtFiltro = "";
//                        }
                        String FILTRO_FECHA_INI = (String) request.getAttribute("CR_RFAN_TXT_FILTRAR_FEC_INI");
                        if (FILTRO_FECHA_INI == null || FILTRO_FECHA_INI.isEmpty()) {
                            FILTRO_FECHA_INI = "";
                        }
                        String FILTRO_FECHA_FIN = (String) request.getAttribute("CR_RFAN_TXT_FILTRAR_FEC_FIN");
                        if (FILTRO_FECHA_FIN == null || FILTRO_FECHA_FIN.isEmpty()) {
                            FILTRO_FECHA_FIN = "";
                        }
//                        String txtFiltroCliId = (String) request.getAttribute("CR_RFAN_TXT_FILTRAR_CLINICA_ID");
//                        if (txtFiltroCliId == null || txtFiltroCliId.isEmpty()) {
//                            txtFiltroCliId = "";
//                        }
//                        String FILTRO_PACIENTE_ID = (String) request.getAttribute("CR_RFAN_TXT_FILTRAR_PACIENTE_ID");
//                        if (FILTRO_PACIENTE_ID == null || FILTRO_PACIENTE_ID.isEmpty()) {
//                            FILTRO_PACIENTE_ID = "";
//                        }
                        String txtFiltroEstado = (String) request.getAttribute("CR_RFAN_TXT_FIL_ESTADO");
                        if (txtFiltroEstado == null || txtFiltroEstado.isEmpty()) {
                            txtFiltroEstado = "";
                        }
                    %>
                    <div>
                        <div class="mainFilter">
                            <div class="mainFilterBox mt-2">
                                <div class="filterDate">
                                    <div class="mr-3">
                                        <p class="mainLabelSearch">Fecha Inicio:</p>
                                        <input type="date" value="<%= FILTRO_FECHA_INI %>" name="tRHFAFI" id="tRMAFI" class="form-control" />
                                    </div>
                                    <div>
                                        <p class="mainLabelSearch">Fecha Fin:</p>
                                        <input type="date" value="<%= FILTRO_FECHA_FIN %>" name="tRHFAFF" id="tRHFAFF" class="form-control" />
                                    </div>
                                </div>
                            </div>
                            
                            <div class="mainFilterBox mt-2 boxFiltroBtn">
                                <div class="/*mainFilterBox*/">
                                    <p class="mainLabelSearch">Estado:</p>
                                    <div class="combomedico">
                                        <select class="cbxEstado form-control" id="cE" name="cE" onchange="functEstado()">
                                        <%
                                        Map<String, String> listaEstado;
                                        listaEstado = metodosFichaAtencionPacNutri.cargarComboEstadoFA(txtFiltroEstado);
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
                                
                                <div>
                                    <input type="hidden" value="<%=PAC_IDPACIENTE%>" name="tIP" class="form-control disNone" />
                                    <input type="hidden" value="<%=PACIENTE_NAME%>" name="tCRPNC" class="form-control disNone" />
                                    <input type="submit" name="accionRptHFAN" value="Filtrar" id="Filtrar" class="btn btn-primary mr-2" />
                                    <button type="submit" name="accionRptHFAN" value="Limpiar" class="btn btn-primary">Limpiar</button>
                                    <%--<input type="submit" name="accionRptHFAN" value="Limpiar" id="Limpiar" class="btn btn-primary" />--%>
                                </div>
                            </div>
                        </div>
                        
                        
                        
                        <div class="divTable">
                            <table role="table" class="tableFilterListFic table-striped">
                                <thead role="rowgroup">
                                    <tr role="row">
                                        <td role="columnheader" class="HFA_CI">Cód.</td>
                                        <td role="columnheader" class="HFA_CC">Clinica</td>
                                        <td role="columnheader" class="HFA_CFH">Fecha y Hora</td>
                                        <!--<td role="columnheader" class="HFA_CMC">Motivo de la Consulta</td>-->
                                        <!--<td role="columnheader" class="HFA_CE">Estatura</td>-->
                                        <td role="columnheader" class="HFA_CP">Peso</td>
                                        <td role="columnheader" class="HFA_CPG">Porc. Grasa</td>
                                        <td role="columnheader" class="HFA_CEM">Edad M.</td>
                                        <td role="columnheader" class="HFA_CPM">Porc. Musc.</td>
                                        <!--<td role="columnheader" class="HFA_CRM">RM</td>-->
                                        <td role="columnheader" class="HFA_CIMC">IMC</td>
                                        <td role="columnheader" class="HFA_CV">Visceral</td>
                                        <!--<td role="columnheader" class="HFA_CB">Balanza</td>-->
                                        <td role="columnheader" class="HFA_CS">Estado</td>
                                        <!--<td role="columnheader" class="HFA_CA">Acciones</td>-->
                                        <td role="columnheader" class="HFA_CA">Archivos Adjuntos</td>
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
                                            String DET_NAME_ARCHIVO = "DET: "+datosBean.getOFPN_IDFICHAPAC();

                                            // TRAER DATOS DEL MEDICO 
    //                                        BeanPersona datosBPersona = new BeanPersona();
    //                                        datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBean.getOA_IDMEDICO());
                                            // TRAER DATOS CLINICA 
    //                                        BeanClinica datosBClinica = new BeanClinica();
    //                                        datosBClinica = metodosRefClinica.datosBasicosClinica(datosBClinica, datosBean.getOA_IDCLINICA());
//                                            String DESC_CLINICA = metodosRefClinica.traerDescClinica(datosBean.getOFPN_IDCLINICA());
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
<!--                                        <td role="cell" class="HFA_CMC">
                                            <%--= datosBean.getOFPN_MOTIVO_DE_LA_CONSULTA()--%>
                                        </td>-->
<!--                                        <td role="cell" class="HFA_CE">
                                            <%--= datosBean.getOFPN_ESTATURA()--%>
                                        </td>-->
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
<!--                                        <td role="cell" class="HFA_CRM">
                                            <%--= datosBean.getOFPN_RM()--%>
                                        </td>-->
                                        <td role="cell" class="HFA_CIMC">
                                            <%= datosBean.getOFPN_IMC()%>
                                        </td>
                                        <td role="cell" class="HFA_CV">
                                            <%= datosBean.getOFPN_VISCERAL()%>
                                        </td>
<!--                                        <td role="cell" class="HFA_CB">
                                            <%--= datosBean.getOFPN_TIPO_DE_BALANZA()--%>
                                        </td>-->
                                        <td role="cell" class="HFA_CS">
                                            <%= metodosIniSes.devolverTextEstadoAgend(datosBean.getOFPN_ESTADO()) %>
                                        </td>
<!--                                        <td role="cell" class="HFA_CA">
                                            <form action="CFAPSrvN" method="post" autocomplete="off">
                                                <input type="hidden" name="tIAP" value="<%= datosBean.getOFPN_IDFICHAPAC() %>" class="form-control"/>
                                                <input type="hidden" name="tIA" value="<%= datosBean.getOFPN_IDAGENDAMIENTO() %>" class="form-control"/>
                                                <input type="hidden" name="tAID" value="<%= datosBean.getOFPN_ITEM_AGEND_DET() %>" class="form-control"/>
                                                <input type="hidden" name="tIP" value="<%= PAC_IDPACIENTE %>" class="form-control"/>
                                                <button type="submit" value="Ver Atención" name="accion" class="btn btn-link">Ver</button>
                                            </form>
                                        </td>-->
                                        <td role="cell" class="HFA_CA">
                                            <%
                                            if (datosBean.getOFPND_IDFICHAPAC()==null || datosBean.getOFPND_IDFICHAPAC().equals("")) {
                                                System.out.println(".");
                                                System.out.println(".");
                                                System.out.println("____________DET_ID_FIC_IS_NULL___________");
                                                System.out.println(".");
                                                System.out.println(".");
                                            %>
                                            <span>----</span>
                                            <%
                                            } else if (datosBean.getOFPND_IDFICHAPAC().isEmpty()==false) {
                                                System.out.println(".");
                                                System.out.println(".");
                                                System.out.println("___LA_FICHA_CUENTA_CON_ARCHIVOS_ADJUNTOS___("+datosBean.getOFPND_IDFICHAPAC()+")___");
                                                System.out.println(".");
                                                System.out.println(".");
                                            %>
                                            <form action="CRSrv" method="post" autocomplete="off" class="d-flex flex-row justify-content-center align-content-center">
                                                <input type="hidden" value="<%= datosBean.getOFPN_IDFICHAPAC() %>" name="tI" class="form-control disNone"/>
                                                <input type="hidden" value="<%= PAC_IDPACIENTE %>" name="tIP" class="form-control disNone"/>
                                                <input type="hidden" value="<%= PACIENTE_NAME %>" name="tCRPNC" class="form-control disNone"/>
                                                <button type="submit" name="accionRptHFAN" value="VerArchivosAdj" class="btn-file-linked"><i class="pt-1 iconSize12pt fa-solid fa-paperclip mr-2"></i>Ver Archivos Adjuntos</button>
                                            </form>
                                            <%--<span class="d-flex flex-row justify-content-center align-content-center btn-file-linked"><i class="pt-1 iconSize12pt fa-solid fa-paperclip mr-2"></i>Ver Archivos Adjuntos</span>--%>
                                            <!-- Button trigger modal -->
<!--                                            <button type="button" class="btn btn-primary" onclick="exampleModal()" data-toggle="modal" data-target="#exampleModal<%=datosBean.getOFPN_IDFICHAPAC()%>">
                                              Ver Archivos Vinculados <%= datosBean.getOFPN_IDFICHAPAC() %>
                                            </button>-->
                                            <!-- Modal -->
                                            <%
//                                            System.out.println(".");
//                                            System.out.println(".");
//                                            System.out.println(".---------------__ CONTROL_DETALLE_DE_LA_BANDERA __-----------------");
//                                            System.out.println(".");
//                                            if (request.getAttribute("CR_RHFAV_BAND_VER") == null || String.valueOf(request.getAttribute("CR_RHFAV_BAND_VER")).isEmpty()) {
//                                                System.out.println("-   if (null)   -");
//                                            } else if (String.valueOf(request.getAttribute("CR_RHFAV_BAND_VER")).equals("0")) {
//                                                System.out.println("-   else - if   -");
//                                            } else if (String.valueOf(request.getAttribute("CR_RHFAV_BAND_VER")).equals("1")) {
//                                                System.out.println("-   else - if > else - if   -");
                                            
                                            %>
                                            <div class="modal fade" id="exampleModal<%=datosBean.getOFPN_IDFICHAPAC()%>" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                              <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                  <div class="modal-header">
                                                    <h5 class="modal-title" id="exampleModalLabel">Archivos Vinculados de la Ficha</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                      <span aria-hidden="true">&times;</span>
                                                    </button>
                                                  </div>
                                                  <div class="modal-body">
                                                    <table role="table" class="table table-striped">
                                                        <thead role="rowgroup">
                                                            <tr role="row">
                                                                <td role="columnheader"> </td>
                                                                <td role="columnheader">Nombre del Archivo</td>
                                                            </tr>
                                                        </thead>
                                                        <tbody role="rowgroup">
                                                        <%
                                                            //
//                                                            List<BeanFichaAtePaciente> listaDatosDet = null;
//                                                            if (((List<BeanFichaAtePaciente>)request.getAttribute("CR_LISTA_HIST_DET_FICHAS")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
//                                                                listaDatosDet = (List<BeanFichaAtePaciente>) request.getAttribute("CR_LISTA_HIST_DET_FICHAS");
//                                                            }
//                                                            Iterator<BeanFichaAtePaciente> iterDatosDet = listaDatosDet.iterator();
//                                                            BeanFichaAtePaciente datosBeanDet = null;
//                                                            
//                                                            while(iterDatosDet.hasNext()) {
//                                                                datosBeanDet = iterDatosDet.next();
//                                                                String DET_NAME_ARCHIVO = datosBeanDet.getOFPND_NAME_ARCHIVO();
                                                        %>
                                                            <tr role="row">
                                                                <td role="cell"><i class="las la-paperclip"></i></td>
                                                                <td role="cell"><%= DET_NAME_ARCHIVO %></td>
                                                            </tr>
                                                        <%
//                                                            }
                                                        %>
                                                        </tbody>
                                                    </table>
                                                  </div>
                                                  <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                                                    <%// <button type="button" class="btn btn-primary">Save changes</button> <% // [OBS] ESTARIA BUENO QUE ACA EN EL MODAL MUESTRE UN BOTON PARA VER LA FICHA DE EXPEDIENTE.- %>
                                                  </div>
                                                </div>
                                              </div>
                                            </div>
                                            <%
//                                            } else {
//                                                System.out.println("-   else    -");
//                                            }
//                                            System.out.println(".");
//                                            System.out.println(".");
//                                            System.out.println(".   ------------___END-control___----------------");
//                                            System.out.println(".");
//                                            System.out.println(".");
//                                            System.out.println(".");
                                            %>
                                            <%
                                            } // end-if-exits-det.-
                                            %>
                                        </td>
                                    </tr>
                                    <%
                                        } // end-while-lista-fichas.-
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
                    </div>
                    
                    <div>
                        <input type="submit" value="Volver Atras" name="accionRptHFAN" class="mt-3 btn btn-danger">
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/rptMAConfigAux.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            
            
            
        </script>
    </body>
</html>