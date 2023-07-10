<%-- 
    Document   : pagFichaAtencionPac_Datos
    Created on : 23-feb-2022, 14:15:29
    Author     : RYUU
--%>

<%@page import="java.io.File"%>
<%@page import="com.agend.javaBean.BeanFichaAtePacienteDet"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="com.agend.javaBean.BeanServicio"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.agend.javaBean.BeanAtencionPaciente"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha de Atención Nutri</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencionNutri.css">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencion.css">-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CFAP_AF_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CFAP_AF_TIPO_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE TIPO MENSAJE 
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
                
                if(mensaje != null) {
                %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
                <%
                }
                %>
                <%
                String ID_ATENCION = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                System.out.println("_jsp___ID_ATENCION_PAC:   :"+ID_ATENCION);
                String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                System.out.println("_jsp___ID_AGENDAMIENTO:   :"+ID_AGENDAMIENTO);
                String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                System.out.println("_jsp___ITEM_AGENDAMIENTO_DETALLE:   :"+ITEM_AGEND);
                String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                System.out.println("_jsp___ID_PACIENTE:       :"+ID_PACIENTE);
                
//                List<BeanAtencionPaciente> listaDatos;
//                listaDatos = metodosFichaAtencionPac.traer_datos(ID_ATENCION, sesionDatosUsuario);
//                Iterator<BeanAtencionPaciente> iterAtencion = listaDatos.iterator();
//                BeanAtencionPaciente bean_datos = null;
                
                // VARIABLES QUE REPRESENTAN LAS COLUMNAS DE LA TABLA  
//                String TXT_PESO="", TXT_TALLA="", TXT_IMC="", TXT_PCEFALICO="", TXT_FC="", TXT_TEMP="", TXT_PA="", TXT_FRESP="", TXT_MOTIVO_CONSULTA="", TXT_EXPLORACION_FISICA="", TXT_DIAGNOSTICO="", TXT_TRATAMIENTO="", TXT_RECETA="", TXT_ESTUDIOS_SOLICITADOS="";
//                String SER_DESCRIPCION_ORI="", SER_MONTO_ORI="";
//                
//                while(iterAtencion.hasNext()) {
////                    bean_datos = iterAtencion.next();
////                    SER_DESCRIPCION = bean_datos.get;
////                    SER_MONTO = bean_datos.getRHS_MONTO();
////                    SER_ESTADO = bean_datos.getRHS_ESTADO();
////                    // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
////                    SER_DESCRIPCION_ORI = bean_datos.getRHS_DESC_SERVICIO();
////                    SER_MONTO_ORI = bean_datos.getRHS_MONTO();
//                } // end while 
                
                
                // -------------------------------------------------------------------
                //                          NUEVAS VARIABLES
                String TXT_PAC_NOM_APE="", TXT_PAC_FECHA_NAC="", TXT_PAC_EDAD="", TXT_PAC_SEXO="", TXT_PAC_PROFESION="", TXT_PAC_TELEFONO="", TXT_PAC_DESC_CIUDAD="", TXT_PAC_CORREO="";
                String FIC_FILE="", FIC_FILE_ABS="";
                // RECUPERO DEL CONTROLADOR 
                List<String> datosPac = (List<String>)request.getAttribute("CFAP_PAC_DATOS");
                if (datosPac != null) {
                    TXT_PAC_NOM_APE = datosPac.get(0);
                    TXT_PAC_FECHA_NAC = datosPac.get(1);
                    TXT_PAC_EDAD = datosPac.get(2);
                    TXT_PAC_SEXO = metodosIniSes.getNameSexoPer(datosPac.get(3));
                    TXT_PAC_PROFESION = datosPac.get(4);
                    TXT_PAC_TELEFONO = datosPac.get(5);
                    TXT_PAC_DESC_CIUDAD = datosPac.get(6);
                    TXT_PAC_CORREO = datosPac.get(7);
                } else {
                    System.out.println("------------BLOQUE-PAC---is-null---------------");
                    // OBSERVACION: SI LA LISTA DEL REQUEST DA NULL - ENTONCES CONTROLARIA LA MISMA LISTA PERO EN LA SESION, YA QUE AL VER EL HISTORIAL CARGO UNA COPIA DE LOS CAMPOS EN LA SESION Y NO EN EL REQUEST, ENTONCES CUANDO VUELVA ATRAS DESDE EL HISTORICO CARGARA LOS CAMPOS CON LOS ULTIMOS DATOS QUE CARGO A LA SESION 
                    List<String> datosPac1 = (List<String>)sesionDatosUsuario.getAttribute("CFAP_PAC_DATOS");
                    if (datosPac1 != null) {
                        System.out.println("---------BLOQUE-PAC---sesion-es-diferente-a-null------------");
                        TXT_PAC_NOM_APE = datosPac1.get(0);
                        TXT_PAC_FECHA_NAC = datosPac1.get(1);
                        TXT_PAC_EDAD = datosPac1.get(2);
                        TXT_PAC_SEXO = metodosIniSes.getNameSexoPer(datosPac1.get(3));
                        TXT_PAC_PROFESION = datosPac1.get(4);
                        TXT_PAC_TELEFONO = datosPac1.get(5);
                        TXT_PAC_DESC_CIUDAD = datosPac1.get(6);
                        TXT_PAC_CORREO = datosPac1.get(7);
                    } else{
                        System.out.println("---------BLOQUE-PAC----sesion-null----------------");
                    }
                }
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".       _________________JSP_________________");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("-----------__DATOS_PAC__---------------");
                    System.out.println(".  _PAC_NOM_APE:  :"+TXT_PAC_NOM_APE);
                    System.out.println(".  _FECHA_NAC:  :"+TXT_PAC_FECHA_NAC);
                    System.out.println(".  _EDAD:  :"+TXT_PAC_EDAD);
                    System.out.println(".  _SEXO:  :"+TXT_PAC_SEXO);
                    System.out.println(".  _PROFESION:  :"+TXT_PAC_PROFESION);
                    System.out.println(".  _TELEFONO:  :"+TXT_PAC_TELEFONO);
                    System.out.println(".  _DESC_CIUDAD:  :"+TXT_PAC_DESC_CIUDAD);
                    System.out.println(".  _CORREO:  :"+TXT_PAC_CORREO);
                    System.out.println("---------------------------------------");
                
                    
                    
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR LOS PRODUCTOS, ENTONCES DE ACUERDO AL IDPRODUCTO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO PRODUCTO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "ATENCIÓN - ADJUNTAR ARCHIVO";
//                String btnName="", btnCss=""; // VARIABLES QUE UTILIZO PARA DEFINIR EL BOTON, SI VA A SER PARA GUARDAR O MODIFICAR DEPENDIENDO DEL IDPRODUCTO 
//                if (ID_ATENCION.isEmpty() || ID_ATENCION.equals("")) {
//                    varTitulo = "ATENCIÓN - NUEVO REGISTRO";
//                    btnName = "Guardar";
//                    btnCss = "btn btn-outline-success";
//                } else {
//                    varTitulo = "ATENCIÓN - MODIFICAR REGISTRO";
//                    btnName = "Modificar";
//                    btnCss = "btn btn-outline-warning";
//                }
                %>
                <div class="content-atencion">
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="<%--divTableData--%> ml-2">
                        <div class="mt-3">
                            <form action="CFAPSrvN" enctype="multipart/form-data" method="post" autocomplete="off">
                                <div class="contenedor-cajas mt-2">
                                    <div class="form-group">
                                        <b>Nombre del Archivo:</b>
                                    </div>
                                </div>
<%--                                <div class="contenedor-cajas mb-2">
                                    <div class="form-group">
                                        <input type="text" id="tADA" name="tADA" value="" class="form-control">
                                    </div>
                                </div>
                                
                                <div class="contenedor-cajas mt-2">
                                    <div class="form-group">
                                        <b>Path del Archivo:</b>
                                    </div>
                                </div>
                                <div class="contenedor-cajas mb-2">
                                    <div class="form-group">
                                        <input type="text" id="tADAP" name="tADAP" value="" class="form-control">
                                    </div>
                                </div>--%>
                                
                                <div class="contenedor-cajas mb-2">
                                    <div class="form-group">
                                        <input type="file" name="PacFile" id="PacFile" class="form-control form-control-file">
                                    </div>
                                </div>
                                
                                <div class="form-group row mt-1">
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <input type="hidden" name="tIP" value="<%=ID_PACIENTE%>" class="form-control disNone"/>
                                            <input type="hidden" name="tCRPNC" value="<%=TXT_PAC_NOM_APE%>" class="form-control disNone"/>
                                            <input type="submit" value="Add Archivo a la Ficha" name="accionAF" class="btn btn-primary mb-2" />
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <!--<div class="contenedor-cajas mt-2 mb-3">-->
                                    <div class="form-group">
                                        <%
                                        if(((List<BeanAtencionPaciente>)sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES")).size() > 0) {
                                        %>
                                            <table role="table" class="tableListFilesFicha table-striped">
                                                <thead role="rowgroup">
                                                    <tr role="row">
                                                        <%--<td role="columnheader" class="TAF_CA">  </td>--%>
                                                        <%--<td role="columnheader" class="TSCI">#</td>--%>
                                                        <td role="columnheader" class="TAF_CDA">Descripción del Archivo</td>
                                                        <td role="columnheader" class="TAF_CA"></td>
                                                    </tr>
                                                </thead>
                                                <tbody role="rowgroup">
                                                    <%
                                                        // ITEM 
                                                        String CFAP_LISTA_ITEM = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_ADD_FILES");
                                                        System.out.println("___JSP____CFAP_ITEM_LISTA:   :"+CFAP_LISTA_ITEM);
//                                                        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_ADD_FILES", CFAP_LISTA_ITEM);
                                                        // LISTA 
                                                        List<BeanFichaAtePacienteDet> CFAP_LISTA_DETALLE;
                                                        CFAP_LISTA_DETALLE = (List<BeanFichaAtePacienteDet>) sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES");
                                                        Iterator<BeanFichaAtePacienteDet> iterLista = CFAP_LISTA_DETALLE.iterator();
                                                        BeanFichaAtePacienteDet datosTab = new BeanFichaAtePacienteDet();
                                                        int item = 0;
                                                        while(iterLista.hasNext()) {
                                                            item = item + 1;
                                                            datosTab = iterLista.next();
                                                            String FILE_NAME = datosTab.getOFPND_NAME_ARCHIVO();
//                                                            String DIR_FILE = datosTab.getOFPND_DIR_ARCHIVO();
                                                    %>
                                                    <tr role="row">
                                                        <td data-label="Descripción: " role="cell" class="TAF_CDA">
                                                            <span><%=FILE_NAME%></span>
                                                        </td>
                                                        <td <%//data-label="Eliminar: "%> role="cell" class="TAF_CA">
                                                            <span>
                                                            <form action="CFAPSrvN" method="post">
                                                                <input type="hidden" value="<%=item%>" name="tAFIDV" id="tAFIDV" class="form-control" />
                                                                <input type="hidden" value="<%=datosTab.getOFPND_ITEM()%>" name="tAFIDBD" id="tAFIDBD" class="form-control" />
                                                                <input type="hidden" value="<%=datosTab.getOFPND_IDFICHAPAC()%>" name="tAFIFA" id="tAFIFA" class="form-control" />
                                                                <input type="hidden" value="<%=datosTab.getOFPND_DIR_ARCHIVO()%>" name="tAFDA" id="tAFDA" class="form-control" />
                                                                <input type="submit" value="X" name="accionAF" class="btn btn-danger text-center <%/*my-1*/%>" />
                                                            </form>
                                                            </span>
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
                                <!--</div>-->
                                
                                <div class="btns_atencion mt-3">
                                    <input type="submit" value="Volver a la Ficha" name="accionAF" class="btn btn-danger" />
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <!--<script src="${pageContext.request.contextPath}/recursos/js/configFichaAtePac.js" type="text/javascript"></script>-->
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
    </body>
</html>