<%-- 
    Document   : pagFichaAtencionPac_Datos
    Created on : 23-feb-2022, 14:15:29
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanFichaAtePacienteDet"%>
<%@page import="com.agend.javaBean.BeanServicio"%>
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha de Atención Nutri</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">       --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencionNutri.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
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
                
                String ID_ATENCION = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                System.out.println("_jsp___ID_ATENCION_PAC:   :"+ID_ATENCION);
                String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                System.out.println("_jsp___ID_AGENDAMIENTO:   :"+ID_AGENDAMIENTO);
                String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                System.out.println("_jsp___ITEM_AGENDAMIENTO_DETALLE:   :"+ITEM_AGEND);
                String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                System.out.println("_jsp___ID_PACIENTE:       :"+ID_PACIENTE);
                

                
                // -------------------------------------------------------------------
                //                          NUEVAS VARIABLES
                String TXT_PAC_NOM_APE="", TXT_PAC_FECHA_NAC="", TXT_PAC_EDAD="", TXT_PAC_SEXO="", TXT_PAC_PROFESION="", TXT_PAC_TELEFONO="", TXT_PAC_DESC_CIUDAD="", TXT_PAC_CORREO="";
                // RECUPERO DEL CONTROLADOR 
                List<String> datosPac = (List<String>)request.getAttribute("CFAP_PAC_DATOS");
                if (datosPac != null) {
                    TXT_PAC_NOM_APE = datosPac.get(0);
                    TXT_PAC_FECHA_NAC = datosPac.get(1);
                    TXT_PAC_EDAD = datosPac.get(2);
                    TXT_PAC_SEXO = datosPac.get(3);
                    TXT_PAC_PROFESION = datosPac.get(4);
                    TXT_PAC_TELEFONO = datosPac.get(5);
                    TXT_PAC_DESC_CIUDAD = datosPac.get(6);
                    TXT_PAC_CORREO = datosPac.get(7);
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
                    
                // CONTENIDO DE LA FICHA 
                String TXT_FECHA_FICHA = "", TXT_HORA_FICHA="";
                String TXT_MOTIVO_CONSULTA="", TXT_ALIMENTOS_PREFERENCIA="", TXT_ALIMENTOS_COMES_GRL="", TXT_ALIMENTOS_NO_TOLERA="", TXT_CONSUMO_ALCOHOL="", TXT_CONSUMO_CIGARRILLO="", TXT_ALERGIAS="", TXT_ENFERMEDAD="", TXT_CIRUGIAS="", TXT_MEDICAMENTO="", TXT_OTROS_DATOS="";
                List<String> datosFichaCab = (List) request.getAttribute("CFAP_FICHA_CAB_01");
                if (datosFichaCab != null) {
                    TXT_FECHA_FICHA = datosFichaCab.get(0);
                    TXT_HORA_FICHA = datosFichaCab.get(1);
                    TXT_MOTIVO_CONSULTA = datosFichaCab.get(2);
                    TXT_ALIMENTOS_PREFERENCIA = datosFichaCab.get(3);
                    TXT_ALIMENTOS_COMES_GRL = datosFichaCab.get(4);
                    TXT_ALIMENTOS_NO_TOLERA = datosFichaCab.get(5);
                    TXT_ALERGIAS = datosFichaCab.get(6);
                    TXT_ENFERMEDAD = datosFichaCab.get(7);
                    TXT_CIRUGIAS = datosFichaCab.get(8);
                    TXT_MEDICAMENTO = datosFichaCab.get(9);
                    TXT_OTROS_DATOS = datosFichaCab.get(10);
                    TXT_CONSUMO_ALCOHOL = datosFichaCab.get(11);
                    TXT_CONSUMO_CIGARRILLO = datosFichaCab.get(12);
                }
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("-----------__DATOS_PAC_BLOQUE_01__---------------");
                    System.out.println(".  _FECHA_FICHA_ATE:  :"+TXT_FECHA_FICHA);
                    System.out.println(".  _HORA_FICHA_ATE:   :"+TXT_HORA_FICHA);
                    System.out.println(".  _MOTIVO_CONSULTA:  :"+TXT_MOTIVO_CONSULTA);
                    System.out.println(".  _ALIMENTOS_PREFERENCIA::"+TXT_ALIMENTOS_PREFERENCIA);
                    System.out.println(".  _ALIMENTOS_COMES_GRL:  :"+TXT_ALIMENTOS_COMES_GRL);
                    System.out.println(".  _ALIMENTOS_NO_TOLERA:  :"+TXT_ALIMENTOS_NO_TOLERA);
                    System.out.println(".  _CONSUMO_ALCOHOL:      :"+TXT_ALERGIAS);
                    System.out.println(".  _CONSUMO_CIGARRILLO:   :"+TXT_ALERGIAS);
                    System.out.println(".  _ALERGIAS:    :"+TXT_ALERGIAS);
                    System.out.println(".  _ENFERMEDAD:  :"+TXT_ENFERMEDAD);
                    System.out.println(".  _CIRUGIAS:    :"+TXT_CIRUGIAS);
                    System.out.println(".  _MEDICAMENTO: :"+TXT_MEDICAMENTO);
                    System.out.println(".  _OTROS_DATOS: :"+TXT_OTROS_DATOS);
                    System.out.println("---------------------------------------");
                // BLOQUE#2
                String CBX_REALIZA_ACTIVIDAD_FISICA="", TXT_TIPO_EJERCICIO="", TXT_EJERCICIO_FRECUENCIA="", CBX_DIGIERE_CARNE_ROJA="", CBX_ESTRENHIMIENTO="", CBX_CALAMBRES_Y_O_HORMIGUEOS="", CBX_GRASAS_SATURADAS="", CBX_CANSANCIO_FATIGA="", CBX_ZUMBIDOS_OIDO="", CBX_BUENA_DIGESTION="", CBX_HINCHAZON_ABDOMINAL="", CBX_CAIDA_DEL_CABELLO="", CBX_DUERME_PROFUNDAMENTE="", CBX_INSOMNIO="", CBX_UNHAS_QUEBRADIZAS="", CBX_DOLORES_DE_CABEZA="", CBX_MUCOSIDAD_CATARRO="", CBX_PIEL_SECA="", TXT_METABOLISMO="", CBX_ESCALA_BRISTOL="";
                List<String> datosFichaCab02 = (List) request.getAttribute("CFAP_FICHA_CAB_02");
                if (datosFichaCab02 != null) {
                    CBX_REALIZA_ACTIVIDAD_FISICA = datosFichaCab02.get(0);
                    TXT_TIPO_EJERCICIO = datosFichaCab02.get(1);
                    TXT_EJERCICIO_FRECUENCIA = datosFichaCab02.get(2);
                    CBX_DIGIERE_CARNE_ROJA = datosFichaCab02.get(3);
                    CBX_ESTRENHIMIENTO = datosFichaCab02.get(4);
                    CBX_CALAMBRES_Y_O_HORMIGUEOS = datosFichaCab02.get(5);
                    CBX_GRASAS_SATURADAS = datosFichaCab02.get(6);
                    CBX_CANSANCIO_FATIGA = datosFichaCab02.get(7);
                    CBX_ZUMBIDOS_OIDO = datosFichaCab02.get(8);
                    CBX_BUENA_DIGESTION = datosFichaCab02.get(9);
                    CBX_HINCHAZON_ABDOMINAL = datosFichaCab02.get(10);
                    CBX_CAIDA_DEL_CABELLO = datosFichaCab02.get(11);
                    CBX_DUERME_PROFUNDAMENTE = datosFichaCab02.get(12);
                    CBX_INSOMNIO = datosFichaCab02.get(13);
                    CBX_UNHAS_QUEBRADIZAS = datosFichaCab02.get(14);
                    CBX_DOLORES_DE_CABEZA = datosFichaCab02.get(15);
                    CBX_MUCOSIDAD_CATARRO = datosFichaCab02.get(16);
                    CBX_PIEL_SECA = datosFichaCab02.get(17);
                    TXT_METABOLISMO = datosFichaCab02.get(18);
                    CBX_ESCALA_BRISTOL = datosFichaCab02.get(19);
                }
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("-----------__DATOS_PAC_BLOQUE_02__---------------");
                    System.out.println(".  _REALIZA_ACTIVIDAD_FISICA:  :"+CBX_REALIZA_ACTIVIDAD_FISICA);
                    System.out.println(".  _TIPO_EJERCICIO:         :"+TXT_TIPO_EJERCICIO);
                    System.out.println(".  _EJERCICIO_FRECUENCIA:   :"+TXT_EJERCICIO_FRECUENCIA);
                    System.out.println(".  _DIGIERE_CARNE_ROJA:     :"+CBX_DIGIERE_CARNE_ROJA);
                    System.out.println(".  _ESTRENHIMIENTO:         :"+CBX_ESTRENHIMIENTO);
                    System.out.println(".  _CALAMBRES_Y/O_HORMIGUEOS:  :"+CBX_CALAMBRES_Y_O_HORMIGUEOS);
                    System.out.println(".  _GRASAS_SATURADAS:   :"+CBX_GRASAS_SATURADAS);
                    System.out.println(".  _CANSANCIO_FATIGA:   :"+CBX_CANSANCIO_FATIGA);
                    System.out.println(".  _ZUMBIDOS_OIDO:      :"+CBX_ZUMBIDOS_OIDO);
                    System.out.println(".  _BUENA_DIGESTION:    :"+CBX_BUENA_DIGESTION);
                    System.out.println(".  _HINCHAZON_ABDOMINAL:   :"+CBX_HINCHAZON_ABDOMINAL);
                    System.out.println(".  _CAIDA_DEL_CABELLO:     :"+CBX_CAIDA_DEL_CABELLO);
                    System.out.println(".  _DUERME_PROFUNDAMENTE:  :"+CBX_DUERME_PROFUNDAMENTE);
                    System.out.println(".  _INSOMNIO:           :"+CBX_INSOMNIO);
                    System.out.println(".  _UNHAS_QUEBRADIZAS:  :"+CBX_UNHAS_QUEBRADIZAS);
                    System.out.println(".  _DOLORES_DE_CABEZA:  :"+CBX_DOLORES_DE_CABEZA);
                    System.out.println(".  _MUCOSIDAD_CATARRO:  :"+CBX_MUCOSIDAD_CATARRO);
                    System.out.println(".  _PIEL_SECA:          :"+CBX_PIEL_SECA);
                    System.out.println(".  _METABOLISMO:        :"+TXT_METABOLISMO);
                    System.out.println(".  _TIPO_ESCALA_BRISTOL :"+CBX_ESCALA_BRISTOL);
                    System.out.println("---------------------------------------------------");
                // BLOQUE#3
                String TXT_ESTATURA="", TXT_PORC_GRASA="",TXT_EDAD_M="",TXT_PESO="",TXT_PORC_MUSCULO="",TXT_RM="",TXT_IMC="",TXT_VISCERAL="",TXT_BALANZA="";
                List<String> datosFichaCab03 = (List) request.getAttribute("CFAP_FICHA_CAB_03");
                if (datosFichaCab03 != null) {
                    TXT_ESTATURA = datosFichaCab03.get(0);
                    TXT_PORC_GRASA = datosFichaCab03.get(1);
                    TXT_EDAD_M = datosFichaCab03.get(2);
                    TXT_PESO = datosFichaCab03.get(3);
                    TXT_PORC_MUSCULO = datosFichaCab03.get(4);
                    TXT_RM = datosFichaCab03.get(5);
                    TXT_IMC = datosFichaCab03.get(6);
                    TXT_VISCERAL = datosFichaCab03.get(7);
                    TXT_BALANZA = datosFichaCab03.get(8);
                }
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("-----------__DATOS_PAC_BLOQUE_03__---------------");
                    System.out.println(".  _ESTATURA:   :"+TXT_ESTATURA);
                    System.out.println(".  _PORC_GRASA: :"+TXT_PORC_GRASA);
                    System.out.println(".  _EDAD_M:     :"+TXT_EDAD_M);
                    System.out.println(".  _PESO:       :"+TXT_PESO);
                    System.out.println(".  _PORC_MUSCULO:  :"+TXT_PORC_MUSCULO);
                    System.out.println(".  _RM:   :"+TXT_RM);
                    System.out.println(".  _IMC:  :"+TXT_IMC);
                    System.out.println(".  _VISCERAL:  :"+TXT_VISCERAL);
                    System.out.println(".  _BALANZA:   :"+TXT_BALANZA);
                    System.out.println("---------------------------------------------------");
                // BLOQUE#4
                String TXT_RESULTADOS_TEST="", TXT_SUPLEMENTACION_RECETADA="", TXT_LABORATORIO="", TXT_COMENTARIOS_GENERALES="";
                List<String> datosFichaCab04 = (List) request.getAttribute("CFAP_FICHA_CAB_04");
                if (datosFichaCab04 != null) {
                    TXT_RESULTADOS_TEST = datosFichaCab04.get(0);
                    TXT_SUPLEMENTACION_RECETADA = datosFichaCab04.get(1);
                    TXT_LABORATORIO = datosFichaCab04.get(2);
                    TXT_COMENTARIOS_GENERALES = datosFichaCab04.get(3);
                }
                    
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("-----------__DATOS_PAC_BLOQUE_04__---------------");
                    System.out.println(".  _RESULTADOS_TEST:  :"+TXT_RESULTADOS_TEST);
                    System.out.println(".  _SUPLEMENTACION_RECETADA:  :"+TXT_SUPLEMENTACION_RECETADA);
                    System.out.println(".  _LABORATORIO:      :"+TXT_LABORATORIO);
                    System.out.println(".  _COMENTARIOS_GENERALES:    :"+TXT_COMENTARIOS_GENERALES);
                    System.out.println("---------------------------------------------------");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR LOS PRODUCTOS, ENTONCES DE ACUERDO AL IDPRODUCTO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO PRODUCTO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                String btnName="", btnCss=""; // VARIABLES QUE UTILIZO PARA DEFINIR EL BOTON, SI VA A SER PARA GUARDAR O MODIFICAR DEPENDIENDO DEL IDPRODUCTO 
//                if (ID_ATENCION.isEmpty() || ID_ATENCION.equals("")) {
//                    varTitulo = "ATENCIÓN - NUEVO REGISTRO";
//                    btnName = "Guardar";
//                    btnCss = "btn btn-outline-success";
//                } else {
                    varTitulo = "ATENCIÓN - VER REGISTRO";
//                    varTitulo = "ATENCIÓN - MODIFICAR REGISTRO";
//                    btnName = "Modificar";
//                    btnCss = "btn btn-outline-warning";
//                }
                %>
                <div class="content-atencion">
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData">
                        <%
                        // TRAER DATOS DEL PACIENTE QUE SE LE VA A CARGAR LA FICHA DE ATENCION 
                        BeanPersona datosBPaciente = new BeanPersona();
                        datosBPaciente = metodosPersona.datosBasicosPersona(datosBPaciente, ID_PACIENTE);
                        String PACIENTE =  datosBPaciente.getRP_NOMBRE()+" "+datosBPaciente.getRP_APELLIDO();
                        System.out.println("_   _JSP____PACIENTE:      :"+PACIENTE);
                        %>
                        <div class="mt-3">
                            <!--<form action="CFAPSrvN" method="post" autocomplete="off">-->
                            <div class="form-group d-inline-flex justify-content-end mb-4">
                                <label for="B1TDF" class="form-label mt-2 mr-2">Fecha:</label>
                                <input type="date" onchange="addClass('B1TDF')" value="<%= TXT_FECHA_FICHA %>" readonly="true" id="B1TDF" name="B1TDF" class="form-control inactive">
                                <input type="time" onchange="addClass('B1TDFH')" value="<%= TXT_HORA_FICHA %>" readonly="readonly" id="B1TDFH" name="B1TDFH" class="form-control inactive">
                            </div>
                            <div class="contenedor-cajas mb-2">
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
                                    <input type="text" id="tPS" name="tPS" value="<%= metodosIniSes.getNameSexoPer(TXT_PAC_SEXO) %>" disabled class="form-control w-auto col-8">
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
                            
                            
                            <div class="contenedor-cajas mt-4 mb-4">
                                <div class="form-group">
                                    <b>MOTIVO DE LA CONSULTA:</b>
                                    <input type="text" onchange="addClass('B1TMDLC')" id="B1TMDLC" name="B1TMDLC" value="<%= TXT_MOTIVO_CONSULTA %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alimentos de Preferencia:
                                    <input type="text" onchange="addClass('B1TAP')" id="B1TAP" name="B1TAP" value="<%= TXT_ALIMENTOS_PREFERENCIA %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Que suele comer generalmente:
                                    <input type="text" onchange="addClass('B1TACG')" id="B1TACG" name="B1TACG" value="<%= TXT_ALIMENTOS_COMES_GRL %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alimentos que no tolera o no le guste:
                                    <input type="text" onchange="addClass('B1TANT')" id="B1TANT" name="B1TANT" value="<%= TXT_ALIMENTOS_NO_TOLERA %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Consumo Alcohol:
                                    <input type="text" onchange="addClass('B1TCA')" id="B1TCA" name="B1TCA" value="<%= TXT_CONSUMO_ALCOHOL %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Consumo Cigarrillo:
                                    <input type="text" onchange="addClass('B1TCC')" id="B1TCC" name="B1TCC" value="<%= TXT_CONSUMO_CIGARRILLO %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Alergias a algo:
                                    <input type="text" onchange="addClass('B1TA')" id="B1TA" name="B1TA" value="<%= TXT_ALERGIAS %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    ¿Padece alguna enfermedad? ¿Cuál?:
                                    <input type="text" onchange="addClass('B1TE')" id="B1TE" name="B1TE" value="<%= TXT_ENFERMEDAD %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Cirugías:
                                    <input type="text" onchange="addClass('B1TC')" id="B1TC" name="B1TC" value="<%= TXT_CIRUGIAS %>" disabled class="form-control inactive">
                                </div>
                                
                                <div class="form-group">
                                    Medicamento y/o suplementación que este consumiendo:
                                    <input type="text" onchange="addClass('B1TM')" id="B1TM" name="B1TM" value="<%= TXT_MEDICAMENTO %>" disabled class="form-control inactive">
                                </div>
                            </div>
                                
                            
                            
                            <div class="contenedor-cajas-onebox mb-4">
                                <div class="form-group">
                                    Otros datos a tener en cuenta:
                                    <!--<input type="text" id="" name="" value="<%--= TXT_OTROS_DATOS --%>" class="form-control">-->
                                    <textarea onchange="addClass('B1TOD')" id="B1TOD" name="B1TOD" rows="4" cols="70" disabled class="form-control inactive"><%= TXT_OTROS_DATOS %></textarea>
                                </div>
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec mt-2">
                                <!--<div class="bloque form-group row">-->
                                    <div class="subbloque col-auto ">
                                        <div class="d-flex flex-row">
                                            <label for="B2CRAF" class="form-label  pt-1">Realiza actividad Física:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CRAF" disabled name="B2CRAF">
                                                    <%
                                                    Map<String, String> cbxSINORAF = new LinkedHashMap();
                                                    cbxSINORAF = metodosIniSes.cargarComboSiNoAv(CBX_REALIZA_ACTIVIDAD_FISICA); 
//                                                    cbxSINORAF = metodosIniSes.cargarComboSiNo(CBX_REALIZA_ACTIVIDAD_FISICA); 
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
                                <!--</div>-->
                                                
                                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1 w-auto" for="B2TTE">Tipo de ejercicio o deporte:</label>
                                            <div>
                                                <input type="text" onchange="addClass('B2TTE')" id="B2TTE" disabled name="B2TTE" value="<%= TXT_TIPO_EJERCICIO %>" class="form-control inactive">
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2TEF">Frecuencia:</label>
                                            <div>
                                                <input type="text" onchange="addClass('B2TEF')" id="B2TEF" disabled name="B2TEF" value="<%= TXT_EJERCICIO_FRECUENCIA %>" class="form-control inactive">
                                            </div>
                                        </div>
                                    </div>
                                <!--</div>-->
                                
                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row w-auto">
                                            <label class="form-label  pt-1" for="B2CDCR">Digiere bien las carnes rojas:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDCR" disabled name="B2CDCR">
                                                    <%
                                                    Map<String, String> cbxDBLCR = new LinkedHashMap();
                                                    cbxDBLCR = metodosIniSes.cargarComboSiNoAv(CBX_DIGIERE_CARNE_ROJA); 
//                                                    cbxDBLCR = metodosIniSes.cargarComboSiNo(CBX_DIGIERE_CARNE_ROJA); 
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
                                    
                                    <div class="subbloque col-auto  mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CE">Estreñimiento:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CE" disabled name="B2CE">
                                                    <%
                                                    Map<String, String> cbxE = new LinkedHashMap();
                                                    cbxE = metodosIniSes.cargarComboSiNoAv(CBX_ESTRENHIMIENTO); 
//                                                    cbxE = metodosIniSes.cargarComboSiNo(CBX_ESTRENHIMIENTO); 
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
                                            <label class="form-label  pt-1" for="B2CCOH">Calambres y/o hormigueos:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCOH" disabled name="B2CCOH">
                                                    <%
                                                    Map<String, String> cbxCYOH = new LinkedHashMap();
                                                    cbxCYOH = metodosIniSes.cargarComboSiNoAv(CBX_CALAMBRES_Y_O_HORMIGUEOS); 
//                                                    cbxCYOH = metodosIniSes.cargarComboSiNo(CBX_CALAMBRES_Y_O_HORMIGUEOS); 
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
                                <!--</div>-->
                                
                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CGS">Las Grasas saturadas le cae mal:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CGS" disabled name="B2CGS">
                                                    <%
                                                    Map<String, String> cbxLGSLCM = new LinkedHashMap();
                                                    cbxLGSLCM = metodosIniSes.cargarComboSiNoAv(CBX_GRASAS_SATURADAS); 
//                                                    cbxLGSLCM = metodosIniSes.cargarComboSiNo(CBX_GRASAS_SATURADAS); 
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
                                    
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CCF">Cansancio y Fatiga:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCF" disabled name="B2CCF">
                                                    <%
                                                    Map<String, String> cbxCYF = new LinkedHashMap();
                                                    cbxCYF = metodosIniSes.cargarComboSiNoAv(CBX_CANSANCIO_FATIGA); 
//                                                    cbxCYF = metodosIniSes.cargarComboSiNo(CBX_CANSANCIO_FATIGA); 
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
                                            <label class="form-label  pt-1" for="B2CZO">Zumbidos en el oído:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CZO" disabled name="B2CZO">
                                                    <%
                                                    Map<String, String> cbxZEEO = new LinkedHashMap();
                                                    cbxZEEO = metodosIniSes.cargarComboSiNoAv(CBX_ZUMBIDOS_OIDO); 
//                                                    cbxZEEO = metodosIniSes.cargarComboSiNo(CBX_ZUMBIDOS_OIDO); 
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
<!--                                </div>-->
                                
                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CBD">Tiene buena digestión a la noche:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CBD" disabled name="B2CBD">
                                                    <%
                                                    Map<String, String> cbxTBDAAN = new LinkedHashMap();
                                                    cbxTBDAAN = metodosIniSes.cargarComboSiNoAv(CBX_BUENA_DIGESTION); 
//                                                    cbxTBDAAN = metodosIniSes.cargarComboSiNo(CBX_BUENA_DIGESTION); 
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
                                    
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CHA">Hinchazón abdominal:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CHA" disabled name="B2CHA">
                                                    <%
                                                    Map<String, String> cbxHA = new LinkedHashMap();
                                                    cbxHA = metodosIniSes.cargarComboSiNoAv(CBX_HINCHAZON_ABDOMINAL); 
//                                                    cbxHA = metodosIniSes.cargarComboSiNo(CBX_HINCHAZON_ABDOMINAL); 
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
                                            <label class="form-label  pt-1" for="B2CCDC">Caída del cabello:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CCDC" disabled name="B2CCDC">
                                                    <%
                                                    Map<String, String> cbxCDC = new LinkedHashMap();
                                                    cbxCDC = metodosIniSes.cargarComboSiNoAv(CBX_CAIDA_DEL_CABELLO); 
//                                                    cbxCDC = metodosIniSes.cargarComboSiNo(CBX_CAIDA_DEL_CABELLO); 
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
                                <!--</div>-->
                                
                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CDP">Duerme profundamente:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDP" disabled name="B2CDP">
                                                    <%
                                                    Map<String, String> cbxDP = new LinkedHashMap();
                                                    cbxDP = metodosIniSes.cargarComboSiNoAv(CBX_DUERME_PROFUNDAMENTE); 
//                                                    cbxDP = metodosIniSes.cargarComboSiNo(CBX_DUERME_PROFUNDAMENTE); 
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
                                    
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CI">Insomnio:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CI" disabled name="B2CI">
                                                    <%
                                                    Map<String, String> cbxI = new LinkedHashMap();
                                                    cbxI = metodosIniSes.cargarComboSiNoAv(CBX_INSOMNIO); 
//                                                    cbxI = metodosIniSes.cargarComboSiNo(CBX_INSOMNIO); 
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
                                            <label class="form-label  pt-1" for="B2CUQ">Uñas quebradizas:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CUQ" disabled name="B2CUQ">
                                                    <%
                                                    Map<String, String> cbxUQ = new LinkedHashMap();
                                                    cbxUQ = metodosIniSes.cargarComboSiNoAv(CBX_UNHAS_QUEBRADIZAS); 
//                                                    cbxUQ = metodosIniSes.cargarComboSiNo(CBX_UNHAS_QUEBRADIZAS); 
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
                                <!--</div>-->
                                
                                
                                <!--<div class="bloque  row">-->
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CDDC">Dolores de cabeza con frecuencia:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CDDC" disabled name="B2CDDC">
                                                    <%
                                                    Map<String, String> cbxDDCCF = new LinkedHashMap();
                                                    cbxDDCCF = metodosIniSes.cargarComboSiNoAv(CBX_DOLORES_DE_CABEZA); 
//                                                    cbxDDCCF = metodosIniSes.cargarComboSiNo(CBX_DOLORES_DE_CABEZA); 
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
                                    
                                    <div class="subbloque col-auto mb-2">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CMC">Mucosidad y catarro:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CMC" disabled name="B2CMC">
                                                    <%
                                                    Map<String, String> cbxMYC = new LinkedHashMap();
                                                    cbxMYC = metodosIniSes.cargarComboSiNoAv(CBX_MUCOSIDAD_CATARRO); 
//                                                    cbxMYC = metodosIniSes.cargarComboSiNo(CBX_MUCOSIDAD_CATARRO); 
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
                                            <label class="form-label  pt-1" for="B2CPS">Piel seca:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CPS" disabled name="B2CPS">
                                                    <%
                                                    Map<String, String> cbxPS = new LinkedHashMap();
                                                    cbxPS = metodosIniSes.cargarComboSiNoAv(CBX_PIEL_SECA); 
//                                                    cbxPS = metodosIniSes.cargarComboSiNo(CBX_PIEL_SECA); 
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
                                <!--</div>-->
                                
                                
                                <!--<div class="bloque ">-->
                                    <div class="subbloque col-auto mb-4">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2TM">Metabolismo:</label>
                                            <!--<div>-->
                                                <!--<input type="text" onchange="addClass('B2TM')" id="B2TM" name="B2TM" value="<%= TXT_METABOLISMO %>" disabled class="form-control inactive">-->
                                            <!--</div>-->
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2TM" disabled name="B2TM">
                                                    <%
                                                    Map<String, String> cbxMeta = new LinkedHashMap();
                                                    cbxMeta = metodosIniSes.cargarComboMetabolismo(TXT_METABOLISMO); 
                                                    Set setListCM = cbxMeta.entrySet();
                                                    Iterator iListCM = setListCM.iterator();
                                                    while(iListCM.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCM.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    
                                    <div class="subbloque col-auto mb-4">
                                        <div class="d-flex flex-row">
                                            <label class="form-label  pt-1" for="B2CTDEDBU">Tipo de "Escala de Bristol" Usual:</label>
                                            <div class="pnlSelect">
                                                <select class="cbxselect" id="B2CTDEDBU" disabled name="B2CTDEDBU">
                                                    <%
                                                    Map<String, String> cbxTEDBU = new LinkedHashMap();
                                                    cbxTEDBU = metodosIniSes.cargarComboEscalaBristol(CBX_ESCALA_BRISTOL); 
                                                    Set setListCTEDBU = cbxTEDBU.entrySet();
                                                    Iterator iListCTEDBU = setListCTEDBU.iterator();
                                                    while(iListCTEDBU.hasNext()) {
                                                        Map.Entry mapSelec = (Map.Entry) iListCTEDBU.next();
                                                    %>
                                                    <option value="<%= mapSelec.getKey() %>"><%= mapSelec.getValue() %></option>
                                                    <%
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                <!--</div>-->
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec-3 mt-3 mb-4">
                                <div class="form-group row">
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TE" class="form-label mr-2 pt-1">Estatura:</label>
                                            <input type="text" onchange="addClass('B3TE')" value="<%= TXT_ESTATURA %>" disabled name="B3TE" id="B3TE" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TP" class="form-label mr-2 pt-1">Peso:</label>
                                            <input type="text" onchange="addClass('B3TP')" value="<%= TXT_PESO %>" disabled name="B3TP" id="B3TP" class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label for="B3TI" class="form-label mr-2 pt-1">IMC:</label>
                                            <input type="text" onchange="addClass('B3TI')" value="<%= TXT_IMC  %>" name="B3TI" id="B3TI" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TPG">% Grasa:</label>
                                            <input type="text" onchange="addClass('B3TPG')" id="B3TPG" name="B3TPG" value="<%= TXT_PORC_GRASA %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TPM">% Músculo:</label>
                                            <input type="text" onchange="addClass('B3TPM')" id="B3TPM" name="B3TPM" value="<%= TXT_PORC_MUSCULO %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto mb-3">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TV">Visceral:</label>
                                            <input type="text" onchange="addClass('B3TV')" id="B3TV" name="B3TV" value="<%= TXT_VISCERAL %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TEM">Edad M.:</label>
                                            <input type="text" onchange="addClass('B3TEM')" id="B3TEM" name="B3TEM" value="<%= TXT_EDAD_M %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TRM">RM:</label>
                                            <input type="text" onchange="addClass('B3TRM')" id="B3TRM" name="B3TRM" value="<%= TXT_RM %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                    
                                    <div class="col-auto">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B3TB">Balanza:</label>
                                            <input type="text" onchange="addClass('B3TB')" id="B3TB" name="B3TB" value="<%= TXT_BALANZA %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            
                            <div class="contenedor-cajas-selec-footer mt-3">
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label for="B4TRT" class="form-label mr-2 pt-1">Resultados del test:</label>
                                            <input type="text" onchange="addClass('B4TRT')" name="B4TRT" id="B4TRT" value="<%= TXT_RESULTADOS_TEST  %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B4TSR">Suplementación recetada:</label>
                                            <input type="text" onchange="addClass('B4TSR')" id="B4TSR" name="B4TSR" value="<%= TXT_SUPLEMENTACION_RECETADA %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label class="form-label mr-2 pt-1" for="B4TL">Laboratorio:</label>
                                            <input type="text" onchange="addClass('B4TL')" id="B4TL" name="B4TL" value="<%= TXT_LABORATORIO %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <div class="col-auto /*mb-3*/">
                                        <div class="d-flex flex-row">
                                            <label for="B4TCG" class="form-label mr-2 pt-1">Comentarios generales:</label>
                                            <input type="text" onchange="addClass('B4TCG')" name="B4TCG" id="B4TCG" value="<%= TXT_COMENTARIOS_GENERALES  %>" disabled class="form-control inactive">
                                        </div>
                                    </div>
                                </div>
                                
                                
                                <div class="form-group row">
                                    <%--<label for="B4TCG" class="form-label mr-2 pt-1"></label>--%>
                                    <% 
                                    if(((List<BeanAtencionPaciente>)sesionDatosUsuario.getAttribute("CFAP_LISTA_FILES")).size() > 0) {
                                    %>
                                        <table role="table" class="tableListFilesFicha table-striped">
                                            <caption style="width: 400px;">Archivos Adjuntos:</caption>
                                            <thead role="rowgroup">
                                                <tr role="row">
                                                    <%--<td role="columnheader" class="TAF_CA">  </td>--%>
                                                    <%--<td role="columnheader" class="TSCI">#</td>--%>
                                                    <td role="columnheader" class="TAF_CDA">Descripción del Archivo</td>
                                                    <td role="columnheader" class="TAF_CAV"></td>
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
                                                        String DIR_FILE = datosTab.getOFPND_DIR_ARCHIVO();
                                                        String NAME_FILE = datosTab.getOFPND_NAME_ARCHIVO();
                                                %>
                                                <tr role="row">
                                                    <td data-label="Descripción: " role="cell" class="TAF_CDA text-left">
                                                        <span style="font-size: 12pt;"><%=NAME_FILE%></span>
                                                    </td>
                                                    <td <%//data-label="Eliminar: "%> role="cell" class="TAF_CAV">
                                                        <span>
                                                        <%
                                                        if ((DIR_FILE.equals(""))==false) {
                                                            DIR_FILE = "/recursos/download/"+NAME_FILE;
                                                            System.out.println("_   _JSP________DIR_FILE:   :"+DIR_FILE);
                                                            System.out.println("_   _JSP________NAME_FILE:  :"+NAME_FILE);
                                                        %>
                                                        <form action="CFAPSrvN" method="post">
                                                            <input type="hidden" value="<%=item%>" name="tAFIDV" id="tAFIDV" class="form-control" />
                                                            <input type="hidden" value="<%=datosTab.getOFPND_ITEM()%>" name="tAFIDBD" id="tAFIDBD" class="form-control" />
                                                            <input type="hidden" value="<%=datosTab.getOFPND_IDFICHAPAC()%>" name="tAFIFA" id="tAFIFA" class="form-control" />
                                                            <!--<input type="submit" value="X" name="accionAF" class="btn btn-danger text-center <%/*my-1*/%>" />-->
                                                            <a href="<c:url value="<%=DIR_FILE%>" />" download="<%=NAME_FILE%>"><i class="fa-solid fa-download mr-2"></i>Download File</a>
                                                        </form>
                                                        <%
                                                        }
                                                        %>
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
                            </div>
                        </div>
                                        
                        <div class="btns_atencion">
                            <%
                                String volver_atras = urlFichaAtencionPaciente;
//                                String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS");
//                                System.out.println("_   __JSP___BTN_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
//                                if (BTN_VOLVER_ATRAS == null) {
//                                    System.out.println("_   _______IS__NULL_______");
//                                    if (metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true || metodosPerfil.isPerfilMedico(idPerfil)==true) {
//                                        System.out.println("_   _   __IF____");
//                                        volver_atras = urlFichaAtencionPaciente;
//                                    } else {
//                                        System.out.println("_   _   __ELSE____");
//                                        volver_atras = urlPrincipal;
//                                    }
//                                }
//                                System.out.println("_   __BTN_VOLVER_ATRAS:     :"+BTN_VOLVER_ATRAS);
//                                
//                                if (BTN_VOLVER_ATRAS.equals("VER_PACIENTE")) { // VIENE DE LA VENTANA DE "VER EXPEDIENTE" DE PACIENTE 
//                                    volver_atras = "ver_paciente.htm";
//                                } else if (BTN_VOLVER_ATRAS.equals("PERFIL_PACIENTE")) { // VIENE DE LA PAGINA DE PERFIL SIENDO EL USUARIO UN PERFIL PACIENTE 
//                                    volver_atras = "perfil_pac.htm";
//                                } else if (BTN_VOLVER_ATRAS.equals("VER_CAB_AGENDAMIENTO")) { // VIENE DE LA PAGINA DE VER AGENDAMIENTO 
//                                    volver_atras = "ver_agend.htm";
//                                }
                            %>
                            <!--<a href="<%= volver_atras %>" class="btn btn-danger">Volver Atras</a>-->
                            <form action="CFAPSrvN" method="post" autocomplete="off">
                                <input type="hidden" value="<%= ID_PACIENTE %>" name="tIP" class="form-control"/>
                                <input type="submit" value="Volver Atras" name="accion" class="btn btn-danger" />
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="${pageContext.request.contextPath}/recursos/js/configFichaAtePac.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script>
//            function btnMensaje(){
//                event.preventDefault();
//                Swal.fire({
//                    title: "¿Esta seguro que desea eliminar la consulta?",
//                    text: "Esta acción ya no se podrá deshacer, Así que piénsalo bien.",
//                    type: "question",
//                    showCancelButton: true,
//                    confirmButtonColor: '#3085d6',
//                    cancelButtonColor: '#d33',
//                    confirmButtonText: 'Si, estoy seguro',
//                    cancelButtonText: "Cancelar"
//                }).then((result) => {
//                    if (result.value) {
//                        console.log('prueba exitosa - pre anular');
//                        document.getElementById('btnAuxiliar').click();
//                    }
//                    return false;
//                });
//            }
//            document.getElementById('btnPreAux').addEventListener('click', btnMensaje);
        </script>
    </body>
</html>