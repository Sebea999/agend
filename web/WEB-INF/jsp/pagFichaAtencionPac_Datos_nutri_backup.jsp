<%-- 
    Document   : pagFichaAtencionPac_Datos
    Created on : 23-feb-2022, 14:15:29
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanServicio"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.agend.javaBean.BeanAtencionPaciente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha de Atención Nutri</title>
        <meta charset="UTF-8">
        <%@include file="menuJspCssStatic.jsp" %> <% // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA %>
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencionNutri.css">
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencion.css">-->
        <!--<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">-->
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CFAP_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CFAP_TIPO_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE TIPO MENSAJE 
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
                
                
//                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
//                if ((String)request.getAttribute("CFAP_TXT_PESO") != null) {
//                    TXT_PESO = (String) request.getAttribute("CFAP_TXT_PESO");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_TALLA") != null) {
//                    TXT_TALLA = (String) request.getAttribute("CFAP_TXT_TALLA");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_IMC") != null) {
//                    TXT_IMC = (String) request.getAttribute("CFAP_TXT_IMC");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_PCEFALICO") != null) {
//                    TXT_PCEFALICO = (String) request.getAttribute("CFAP_TXT_PCEFALICO");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_FC") != null) {
//                    TXT_FC = (String) request.getAttribute("CFAP_TXT_FC");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_TEMP") != null) {
//                    TXT_TEMP = (String) request.getAttribute("CFAP_TXT_TEMP");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_PA") != null) {
//                    TXT_PA = (String) request.getAttribute("CFAP_TXT_PA");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_FRESP") != null) {
//                    TXT_FRESP = (String) request.getAttribute("CFAP_TXT_FRESP");
//                }
//                // ------------------------ ---------------------- ----------------------- -------------------
//                if ((String)request.getAttribute("CFAP_TXT_MOTIVO_CONSULTA") != null) {
//                    TXT_MOTIVO_CONSULTA = (String) request.getAttribute("CFAP_TXT_MOTIVO_CONSULTA");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_EXPLORACION_FISICA") != null) {
//                    TXT_EXPLORACION_FISICA = (String) request.getAttribute("CFAP_TXT_EXPLORACION_FISICA");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_DIAGNOSTICO") != null) {
//                    TXT_DIAGNOSTICO = (String) request.getAttribute("CFAP_TXT_DIAGNOSTICO");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_TRATAMIENTO") != null) {
//                    TXT_TRATAMIENTO = (String) request.getAttribute("CFAP_TXT_TRATAMIENTO");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_RECETA") != null) {
//                    TXT_RECETA = (String) request.getAttribute("CFAP_TXT_RECETA");
//                }
//                if ((String)request.getAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS") != null) {
//                    TXT_ESTUDIOS_SOLICITADOS = (String) request.getAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS");
//                }
                
                // -------------------------------------------------------------------
                //                          NUEVAS VARIABLES
                String TXT_PAC_NOM_APE="", TXT_PAC_FECHA_NAC="", TXT_PAC_EDAD="", TXT_PAC_SEXO="", TXT_PAC_PROFESION="", TXT_PAC_TELEFONO="", TXT_PAC_DESC_CIUDAD="", TXT_PAC_CORREO="";
                // RECUPERO DEL CONTROLADOR 
                String[] datosPac = (String[])request.getAttribute("CFAP_PAC_DATOS");
                    TXT_PAC_NOM_APE = datosPac[0];
                    TXT_PAC_FECHA_NAC = datosPac[1];
                    TXT_PAC_EDAD = datosPac[2];
                    TXT_PAC_SEXO = datosPac[3];
                    TXT_PAC_PROFESION = datosPac[4];
                    TXT_PAC_TELEFONO = datosPac[5];
                    TXT_PAC_DESC_CIUDAD = datosPac[6];
                    TXT_PAC_CORREO = datosPac[7];
                // CONTENIDO DE LA FICHA 
                String TXT_MOTIVO_CONSULTA="", TXT_ALIMENTOS_PREFERENCIA="", TXT_ALIMENTOS_COMES_GRL="", TXT_ALIMENTOS_NO_TOLERA="", TXT_ALERGIAS="", TXT_ENFERMEDAD="", TXT_CIRUGIAS="", TXT_MEDICAMENTO="", TXT_OTROS_DATOS="";
                String[] datosFichaCab = (String[]) request.getAttribute("CFAP_FICHA_CAB_01");
                    TXT_MOTIVO_CONSULTA = datosFichaCab[0];
                    TXT_ALIMENTOS_PREFERENCIA = datosFichaCab[1];
                    TXT_ALIMENTOS_COMES_GRL = datosFichaCab[2];
                    TXT_ALIMENTOS_NO_TOLERA = datosFichaCab[3];
                    TXT_ALERGIAS = datosFichaCab[4];
                    TXT_ENFERMEDAD = datosFichaCab[5];
                    TXT_CIRUGIAS = datosFichaCab[6];
                    TXT_MEDICAMENTO = datosFichaCab[7];
                    TXT_OTROS_DATOS = datosFichaCab[8];
                String CBX_REALIZA_ACTIVIDAD_FISICA="", TXT_TIPO_EJERCICIO="", TXT_EJERCICIO_FRECUENCIA="", CBX_DIGIERE_CARNE_ROJA="", CBX_ESTRENHIMIENTO="", CBX_CALAMBRES_Y_O_HORMIGUEOS="", CBX_GRASAS_SATURADAS="", CBX_CANSANCION_FATIGA="", CBX_ZUMBIDOS_OIDO="", CBX_BUENA_DIGESTION="", CBX_HINCHAZON_ABDOMINAL="", CBX_CAIDA_DEL_CABELLO="", CBX_DUERME_PROFUNDAMENTE="", CBX_INSOMNIO="", CBX_UNHAS_QUEBRADIZAS="", CBX_DOLORES_DE_CABEZA="", CBX_MUCOSIDAD_CATARRO="", CBX_PIEL_SECA="", TXT_METABOLISMO="";
                String[] datosFichaCab02 = (String[]) request.getAttribute("CFAP_FICHA_CAB_02");
                    CBX_REALIZA_ACTIVIDAD_FISICA = datosFichaCab02[0];
                    TXT_TIPO_EJERCICIO = datosFichaCab02[1];
                    TXT_EJERCICIO_FRECUENCIA = datosFichaCab02[2];
                    CBX_DIGIERE_CARNE_ROJA = datosFichaCab02[3];
                    CBX_ESTRENHIMIENTO = datosFichaCab02[4];
                    CBX_CALAMBRES_Y_O_HORMIGUEOS = datosFichaCab02[5];
                    CBX_GRASAS_SATURADAS = datosFichaCab02[6];
                    CBX_CANSANCION_FATIGA = datosFichaCab02[7];
                    CBX_ZUMBIDOS_OIDO = datosFichaCab02[8];
                    CBX_BUENA_DIGESTION = datosFichaCab02[9];
                    CBX_HINCHAZON_ABDOMINAL = datosFichaCab02[10];
                    CBX_CAIDA_DEL_CABELLO = datosFichaCab02[11];
                    CBX_DUERME_PROFUNDAMENTE = datosFichaCab02[12];
                    CBX_INSOMNIO = datosFichaCab02[13];
                    CBX_UNHAS_QUEBRADIZAS = datosFichaCab02[14];
                    CBX_DOLORES_DE_CABEZA = datosFichaCab02[15];
                    CBX_MUCOSIDAD_CATARRO = datosFichaCab02[16];
                    CBX_PIEL_SECA = datosFichaCab02[17];
                    TXT_METABOLISMO = datosFichaCab02[18];
                String TXT_ESTATURA="", TXT_PORC_GRASA="",TXT_EDAD_M="",TXT_PESO="",TXT_PORC_MUSCULO="",TXT_RM="",TXT_IMC="",TXT_VISCERAL="",TXT_BALANZA="";
                String[] datosFichaCab03 = (String[]) request.getAttribute("CFAP_FICHA_CAB_03");
                    TXT_ESTATURA = datosFichaCab03[0];
                    TXT_PORC_GRASA = datosFichaCab03[1];
                    TXT_EDAD_M = datosFichaCab03[2];
                    TXT_PESO = datosFichaCab03[3];
                    TXT_PORC_MUSCULO = datosFichaCab03[4];
                    TXT_RM = datosFichaCab03[5];
                    TXT_IMC = datosFichaCab03[6];
                    TXT_VISCERAL = datosFichaCab03[7];
                    TXT_BALANZA = datosFichaCab03[8];
                String TXT_RESULTADOS_TEST="", TXT_SUPLEMENTACION_RECETADA="", TXT_LABORATORIO="", TXT_COMENTARIOS_GENERALES="";
                String[] datosFichaCab04 = (String[]) request.getAttribute("CFAP_FICHA_CAB_04");
                    TXT_RESULTADOS_TEST = datosFichaCab04[0];
                    TXT_SUPLEMENTACION_RECETADA = datosFichaCab04[1];
                    TXT_LABORATORIO = datosFichaCab04[2];
                    TXT_COMENTARIOS_GENERALES = datosFichaCab04[3];
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR LOS PRODUCTOS, ENTONCES DE ACUERDO AL IDPRODUCTO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO PRODUCTO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                String btnName="", btnCss=""; // VARIABLES QUE UTILIZO PARA DEFINIR EL BOTON, SI VA A SER PARA GUARDAR O MODIFICAR DEPENDIENDO DEL IDPRODUCTO 
                if (ID_ATENCION.isEmpty() || ID_ATENCION.equals("")) {
                    varTitulo = "ATENCIÓN - NUEVO REGISTRO";
                    btnName = "Guardar";
                    btnCss = "btn btn-outline-success";
                } else {
                    varTitulo = "ATENCIÓN - MODIFICAR REGISTRO";
                    btnName = "Modificar";
                    btnCss = "btn btn-outline-warning";
                }
                %>
                <div class="content-atencion">
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData">
                        <div>
                            <form action="CFAPSrvN" method="post" autocomplete="off">
                            <div class="contenedor-cajas mt-4">
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPNA">Nombre y Apellido:</label>
                                    <input type="text" id="tPNA" name="tPNA" value="<%= TXT_PAC_NOM_APE %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPFN">Fecha Nac.:</label>
                                    <input type="text" id="tPFN" name="tPFN" value="<%= TXT_PAC_FECHA_NAC %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPE">Edad:</label>
                                    <input type="text" id="tPE" name="tPE" value="<%= TXT_PAC_EDAD %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPS">Sexo:</label>
                                    <input type="text" id="tPS" name="tPS" value="<%= TXT_PAC_SEXO %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPP">Profesión:</label>
                                    <input type="text" id="tPP" name="tPP" value="<%= TXT_PAC_PROFESION %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPT">Teléfono:</label>
                                    <input type="text" id="tPT" name="tPT" value="<%= TXT_PAC_TELEFONO %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPC">Ciudad:</label>
                                    <input type="text" id="tPC" name="tPC" value="<%= TXT_PAC_DESC_CIUDAD %>" disabled class="form-control w-auto col-8">
                                </div>
                                
                                <div class="form-group d-inline-flex flex-wrap">
                                    <label class="form-label pt-1 mr-2" for="tPCo">Correo:</label>
                                    <input type="text" id="tPCo" name="tPCo" value="<%= TXT_PAC_CORREO %>" disabled class="form-control w-auto col-8">
                                </div>
                            </div>
                            
                            
                            <div class="contenedor-cajas mt-4">
                                <div class="form-group">
                                    <b>MOTIVO DE LA CONSULTA:</b>
                                    <input type="text" onchange="addClass('B1TMC')" id="B1TMC" name="B1TMC" value="<%= TXT_MOTIVO_CONSULTA %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alimentos de Preferencia:
                                    <input type="text" onchange="addClass('B1TAP')" id="B1TAP" name="B1TAP" value="<%= TXT_ALIMENTOS_PREFERENCIA %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alimentos que come generalmente:
                                    <input type="text" onchange="addClass('B1TACG')" id="B1TACG" name="B1TACG" value="<%= TXT_ALIMENTOS_COMES_GRL %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alimentos que no tolera:
                                    <input type="text" onchange="addClass('B1TANT')" id="B1TANT" name="B1TANT" value="<%= TXT_ALIMENTOS_NO_TOLERA %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alergias:
                                    <input type="text" onchange="addClass('B1TA')" id="B1TA" name="B1TA" value="<%= TXT_ALERGIAS %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    ¿Padece alguna enfermedad? ¿Cuál?:
                                    <input type="text" onchange="addClass('B1TE')" id="B1TE" name="B1TE" value="<%= TXT_ENFERMEDAD %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Cirugías:
                                    <input type="text" onchange="addClass('B1TC')" id="B1TC" name="B1TC" value="<%= TXT_CIRUGIAS %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Medicamento y/o suplementación:
                                    <input type="text" onchange="addClass('B1TM')" id="B1TM" name="B1TM" value="<%= TXT_MEDICAMENTO %>" class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Otros datos a tener en cuenta:
                                    <!--<input type="text" id="" name="" value="<%--= TXT_OTROS_DATOS --%>" class="form-control">-->
                                    <textarea onchange="addClass('B1TOD')" id="B1TOD" name="B1TOD" rows="4" cols="70" class="form-control inactive"><%= TXT_OTROS_DATOS %></textarea>
                                </div>
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec mt-3">
                                <div class="bloque form-group row">
                                    <div class="subbloque col-auto ">
                                        <div class="d-flex flex-row">
                                            <label for="B2CRAF" class="form-label mr-2 pt-1">Realiza actividad Física:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CRAF" name="B2CRAF">
                                                    <%
                                                    Map<String, String> cbxSINORAF = new LinkedHashMap();
                                                    cbxSINORAF = metodosIniSes.cargarComboSiNo(CBX_REALIZA_ACTIVIDAD_FISICA); 
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
                                        </div>
                                    </div>
                                </div>
                                                
                                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1 w-auto" for="B2TTE">Tipo de ejercicio o deporte:</label>
                                            <div>
                                                <input type="text" onchange="addClass('B2TTE')" id="B2TTE" name="B2TTE" value="<%= TXT_TIPO_EJERCICIO %>" class="form-control inactive">
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2TEF">Frecuencia:</label>
                                            <div>
                                                <input type="text" onchange="addClass('B2TEF')" id="B2TEF" name="B2TEF" value="<%= TXT_EJERCICIO_FRECUENCIA %>" class="form-control inactive">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row w-auto">
                                            <label class="form-label mr-2 pt-1" for="B2CDCR">Digiere bien las carnes rojas:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDCR" name="B2CDCR">
                                                    <%
                                                    Map<String, String> cbxDBLCR = new LinkedHashMap();
                                                    cbxDBLCR = metodosIniSes.cargarComboSiNo(CBX_DIGIERE_CARNE_ROJA); 
                                                    Set setListCDBLCR = cbxDBLCR.entrySet();
                                                    Iterator iListCDBLCR = setListCDBLCR.iterator();
                                                    while(iListCDBLCR.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCDBLCR.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto  mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CE">Estreñimiento:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CE" name="B2CE">
                                                    <%
                                                    Map<String, String> cbxE = new LinkedHashMap();
                                                    cbxE = metodosIniSes.cargarComboSiNo(CBX_ESTRENHIMIENTO); 
                                                    Set setListCE = cbxE.entrySet();
                                                    Iterator iListCE = setListCE.iterator();
                                                    while(iListCE.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCE.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CCOH">Calambres y/o hormigueos:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCOH" name="B2CCOH">
                                                    <%
                                                    Map<String, String> cbxCYOH = new LinkedHashMap();
                                                    cbxCYOH = metodosIniSes.cargarComboSiNo(CBX_CALAMBRES_Y_O_HORMIGUEOS); 
                                                    Set setListCYOH = cbxCYOH.entrySet();
                                                    Iterator iListCYOH = setListCYOH.iterator();
                                                    while(iListCYOH.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCYOH.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CGS">Las Grasas saturadas le cae mal:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CGS" name="B2CGS">
                                                    <%
                                                    Map<String, String> cbxLGSLCM = new LinkedHashMap();
                                                    cbxLGSLCM = metodosIniSes.cargarComboSiNo(CBX_GRASAS_SATURADAS); 
                                                    Set setListCLGSLCM = cbxLGSLCM.entrySet();
                                                    Iterator iListCLGSLCM = setListCLGSLCM.iterator();
                                                    while(iListCLGSLCM.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCLGSLCM.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CCF">Cansancio y Fatiga:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCF" name="B2CCF">
                                                    <%
                                                    Map<String, String> cbxCYF = new LinkedHashMap();
                                                    cbxCYF = metodosIniSes.cargarComboSiNo(CBX_CANSANCION_FATIGA); 
                                                    Set setListCCYF = cbxCYF.entrySet();
                                                    Iterator iListCCYF = setListCCYF.iterator();
                                                    while(iListCCYF.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCCYF.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CZO">Zumbidos en el oído:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CZO" name="B2CZO">
                                                    <%
                                                    Map<String, String> cbxZEEO = new LinkedHashMap();
                                                    cbxZEEO = metodosIniSes.cargarComboSiNo(CBX_ZUMBIDOS_OIDO); 
                                                    Set setListCZEEO = cbxZEEO.entrySet();
                                                    Iterator iListCZEEO = setListCZEEO.iterator();
                                                    while(iListCZEEO.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCZEEO.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CBD">Tiene buena digestión a la noche:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CBD" name="B2CBD">
                                                    <%
                                                    Map<String, String> cbxTBDAAN = new LinkedHashMap();
                                                    cbxTBDAAN = metodosIniSes.cargarComboSiNo(CBX_BUENA_DIGESTION); 
                                                    Set setListCTBDAAN = cbxTBDAAN.entrySet();
                                                    Iterator iListCTBDAAN = setListCTBDAAN.iterator();
                                                    while(iListCTBDAAN.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCTBDAAN.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CHA">Hinchazón abdominal:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CHA" name="B2CHA">
                                                    <%
                                                    Map<String, String> cbxHA = new LinkedHashMap();
                                                    cbxHA = metodosIniSes.cargarComboSiNo(CBX_HINCHAZON_ABDOMINAL); 
                                                    Set setListCHA = cbxHA.entrySet();
                                                    Iterator iListCHA = setListCHA.iterator();
                                                    while(iListCHA.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCHA.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CCDC">Caída del cabello:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCDC" name="B2CCDC">
                                                    <%
                                                    Map<String, String> cbxCDC = new LinkedHashMap();
                                                    cbxCDC = metodosIniSes.cargarComboSiNo(CBX_CAIDA_DEL_CABELLO); 
                                                    Set setListCCDC = cbxCDC.entrySet();
                                                    Iterator iListCCDC = setListCCDC.iterator();
                                                    while(iListCCDC.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCCDC.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CDP">Duerme profundamente:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDP" name="B2CDP">
                                                    <%
                                                    Map<String, String> cbxDP = new LinkedHashMap();
                                                    cbxDP = metodosIniSes.cargarComboSiNo(CBX_DUERME_PROFUNDAMENTE); 
                                                    Set setListCDP = cbxDP.entrySet();
                                                    Iterator iListCDP = setListCDP.iterator();
                                                    while(iListCDP.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCDP.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CI">Insomnio:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CI" name="B2CI">
                                                    <%
                                                    Map<String, String> cbxI = new LinkedHashMap();
                                                    cbxI = metodosIniSes.cargarComboSiNo(CBX_INSOMNIO); 
                                                    Set setListCI = cbxI.entrySet();
                                                    Iterator iListCI = setListCI.iterator();
                                                    while(iListCI.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCI.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CUQ">Uñas quebradizas:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CUQ" name="B2CUQ">
                                                    <%
                                                    Map<String, String> cbxUQ = new LinkedHashMap();
                                                    cbxUQ = metodosIniSes.cargarComboSiNo(CBX_UNHAS_QUEBRADIZAS); 
                                                    Set setListCUQ = cbxUQ.entrySet();
                                                    Iterator iListCUQ = setListCUQ.iterator();
                                                    while(iListCUQ.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCUQ.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque  row">
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CDDC">Dolores de cabeza con frecuencia:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDDC" name="B2CDDC">
                                                    <%
                                                    Map<String, String> cbxDDCCF = new LinkedHashMap();
                                                    cbxDDCCF = metodosIniSes.cargarComboSiNo(CBX_DOLORES_DE_CABEZA); 
                                                    Set setListCDDCCF = cbxDDCCF.entrySet();
                                                    Iterator iListCDDCCF = setListCDDCCF.iterator();
                                                    while(iListCDDCCF.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCDDCCF.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CMC">Mucosidad y catarro:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CMC" name="B2CMC">
                                                    <%
                                                    Map<String, String> cbxMYC = new LinkedHashMap();
                                                    cbxMYC = metodosIniSes.cargarComboSiNo(CBX_MUCOSIDAD_CATARRO); 
                                                    Set setListCMYC = cbxMYC.entrySet();
                                                    Iterator iListCMYC = setListCMYC.iterator();
                                                    while(iListCMYC.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCMYC.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B2CPS">Piel seca:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CPS" name="B2CPS">
                                                    <%
                                                    Map<String, String> cbxPS = new LinkedHashMap();
                                                    cbxPS = metodosIniSes.cargarComboSiNo(CBX_PIEL_SECA); 
                                                    Set setListCPS = cbxPS.entrySet();
                                                    Iterator iListCPS = setListCPS.iterator();
                                                    while(iListCPS.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCPS.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="bloque ">
                                    <div class="subbloque d-flex flex-row">
                                        <div class="d-flex">
                                            <label class="form-label mr-2 pt-1" for="B2TM">Metabolismo:</label>
                                            <div>
                                                <input type="text" onchange="addClass('B2TM')" id="B2TM" name="B2TM" value="<%= TXT_METABOLISMO %>" class="form-control inactive">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec-3 mt-3">
                                <div class="form-group row">
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TE" class="form-label mr-2 pt-1">Estatura:</label>
                                            <input type="text" onchange="addClass('B3TE')" value="<%= TXT_ESTATURA %>" name="B3TE" id="B3TE" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TPG">% Grasa:</label>
                                            <input type="text" onchange="addClass('B3TPG')" id="B3TPG" name="B3TPG" value="<%= TXT_PORC_GRASA %>" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TEM">Edad M.:</label>
                                            <input type="text" onchange="addClass('B3TEM')" id="B3TEM" name="B3TEM" value="<%= TXT_EDAD_M %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TP" class="form-label mr-2 pt-1">Peso:</label>
                                            <input type="text" onchange="addClass('B3TP')" value="<%= TXT_PESO %>" name="B3TP" id="B3TP" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TPM">% Músculo:</label>
                                            <input type="text" onchange="addClass('B3TPM')" id="B3TPM" name="B3TPM" value="<%= TXT_PORC_MUSCULO %>" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TRM">RM:</label>
                                            <input type="text" onchange="addClass('B3TRM')" id="B3TRM" name="B3TRM" value="<%= TXT_RM %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TI" class="form-label mr-2 pt-1">IMC:</label>
                                            <input type="text" onchange="addClass('B3TI')" value="<%= TXT_IMC  %>" name="B3TI" id="B3TI" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TV">Visceral:</label>
                                            <input type="text" onchange="addClass('B3TV')" id="B3TV" name="B3TV" value="<%= TXT_VISCERAL %>" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TB">Balanza:</label>
                                            <input type="text" onchange="addClass('B3TB')" id="B3TB" name="B3TB" value="<%= TXT_BALANZA %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec-footer mt-3">
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label for="B4TRT" class="form-label mr-2 pt-1">Resultados del test:</label>
                                            <input type="text" onchange="addClass('B4TRT')" name="B4TRT" id="B4TRT" value="<%= TXT_RESULTADOS_TEST  %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B4TRT">Suplementación recetada:</label>
                                            <input type="text" onchange="addClass('B4TRT')" id="B4TRT" name="B4TRT" value="<%= TXT_SUPLEMENTACION_RECETADA %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B4TL">Laboratorio:</label>
                                            <input type="text" onchange="addClass('B4TL')" id="B4TL" name="B4TL" value="<%= TXT_LABORATORIO %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label for="B4TCG" class="form-label mr-2 pt-1">Comentarios generales:</label>
                                            <input type="text" onchange="addClass('B4TCG')" name="B4TCG" id="B4TCG" value="<%= TXT_COMENTARIOS_GENERALES  %>" class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            </div>
                            
                            
                            <div class="btns_atencion">
                                <input type="submit" value="<%=btnName%>" name="accion" class="<%=btnCss%> btngua" />
                                <%
                                if(!(ID_ATENCION.isEmpty())) {
                                %>
                                <input type="submit" value="Eliminar Atención" id="btnPreAux" class="btn btn-outline-danger mr-2" />
                                <input type="submit" value="Eliminar Consulta" name="accion" id="btnAuxiliar" class="btn btn-warning disNone" />
                                <%
                                }
                                %>
                                <%
                                String var_volver_atras;
                                String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS");
                                System.out.println("_   _JSP_FA_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                                
                                if (BTN_VOLVER_ATRAS == null || BTN_VOLVER_ATRAS.isEmpty()) {
                                    var_volver_atras = urlFichaAtencionPaciente;
                                } else if (BTN_VOLVER_ATRAS.equalsIgnoreCase("VER_PACIENTE")) { // pagina de paciente 
                                    var_volver_atras = "ver_paciente.htm";
                                } else {
                                    var_volver_atras = urlFichaAtencionPaciente;
                                }
                                %>
                                <a href="<%= var_volver_atras %>" class="btn btn-danger">Volver Atras</a>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        
        <script type="text/javascript">
//            function btnMensaje(){
//                    event.preventDefault();
//                    Swal.fire({
//                        title: "¿Esta seguro que desea eliminar la consulta?",
//                        text: "Esta acción ya no se podrá deshacer, Así que piénsalo bien.",
//                        type: "question",
//                        showCancelButton: true,
//                        confirmButtonColor: '#3085d6',
//                        cancelButtonColor: '#d33',
//                        confirmButtonText: 'Si, estoy seguro',
//                        cancelButtonText: "Cancelar"
//                    }).then((result) => {
//                        if (result.value) {
//                            console.log('prueba exitosa - pre anular');
//                            document.getElementById('btnAuxiliar').click();
//                        }
//                        return false;
//                    });
//            }
//            document.getElementById('btnPreAux').addEventListener('click', btnMensaje);
            
            
            function addClass(name) {
                document.getElementById(name).className = 'form-control active';
            }
            
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <!--<script src="${pageContext.request.contextPath}/recursos/js/configFichaAtePac.js" type="text/javascript"></script>-->
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
    </body>
</html>