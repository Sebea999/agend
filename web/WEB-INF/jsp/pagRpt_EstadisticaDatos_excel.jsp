<%-- 
    Document   : pagRpt_EstadisticaDatos_excel
    Created on : 17-abr-2023, 13:52:29
    Author     : USER
--%>

<%@page import="com.agend.modelo.ModelFichaAtencionPacNutri"%>
<%@page import="com.agend.modelo.ModelInicioSesion"%>
<%@page import="com.agend.modelo.ModelRef_Clinica"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="java.util.List"%>
<%@page contentType="application/vnd.ms-excel" pageEncoding="UTF-8"%>

<%
    HttpSession sesion = request.getSession();
    String nombreArchivo = String.valueOf(sesion.getAttribute("CR_RFAN_NAME_EXCEL"))+".xls";
//    String nombreArchivo = "Reporte.xls";
    response.setHeader("Content-Disposition", "attachment;filename="+nombreArchivo);
    //
//    response.setHeader("Content-type","application/vnd.ms-excel");
//    response.setHeader("Content-disposition","inline; filename=nombre.xls");
%>

<!DOCTYPE html>
<html>
    <head>
        <!--<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">-->
        <title>Reporte Excel</title>
    </head>
    <body>
        <h1>Historico de Fichas de <%= String.valueOf(sesion.getAttribute("PARAM_PACIENTE_NAME")) %></h1>
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
                    if (((List<BeanFichaAtePaciente>)sesion.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                        listaDatos = (List<BeanFichaAtePaciente>) sesion.getAttribute("CR_RFAN_LISTA_HISTORICO_PAC");
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
                    ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
                    ModelInicioSesion metodosIniSes = new ModelInicioSesion();

                    while(iterDatos.hasNext()) {
                        datosBean = iterDatos.next();

                        // TRAER DATOS DEL MEDICO 
//                                        BeanPersona datosBPersona = new BeanPersona();
//                                        datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBean.getOA_IDMEDICO());
                        // TRAER DATOS CLINICA 
//                                        BeanClinica datosBClinica = new BeanClinica();
//                                        datosBClinica = metodosRefClinica.datosBasicosClinica(datosBClinica, datosBean.getOA_IDCLINICA());
//                        String DESC_CLINICA = metodosRefClinica.traerDescClinica(datosBean.getOFPN_IDCLINICA());
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
                }
                %>
            </tbody>
        </table>
    </body>
</html>
