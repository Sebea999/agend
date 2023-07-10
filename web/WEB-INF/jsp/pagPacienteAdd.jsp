<%-- 
    Document   : pagPacienteAdd
    Created on : 25-nov-2021, 11:11:28
    Author     : RYUU
--%>

<%@page import="java.io.File"%>
<%@page import="java.util.LinkedHashMap"%>
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
        <title>Paciente</title>
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
                String mensaje = (String) request.getAttribute("CP_MENSAJE"); // CONTROLLER PACIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CP_TIPO_MENSAJE"); // CONTROLLER PACIENTE TIPO MENSAJE 
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
            
                /*
                    * OBSERVACION: PARA AÑADIR UN PACIENTE Y EDITAR UN PACIENTE 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_CLIENTE, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                System.out.println("_jsp___ID_PACIENTE:  "+ID_PACIENTE);
                
                // VARS._
                String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_OCUPACION="", PAC_SEGURO_MEDICO="", 
                        PAC_FOTO="", PAC_FOTO_ABS="", PAC_IDCIUDAD="", PAC_PROFESION="", PAC_ESTADO_CIVIL="", PAC_CANTIDAD_HIJOS="", PAC_TIENE_HIJOS="", PAC_CELULAR="", PAC_OTROS_DATOS="";
                String PAC_NOMBRE_ORI="", PAC_APELLIDO_ORI="", PAC_CINRO_ORI="", PAC_RAZON_SOCIAL_ORI="", PAC_RUC_ORI="", PAC_TELEFONO_ORI="", PAC_DIRECCION_ORI="", PAC_EMAIL_ORI="", PAC_IDUSUARIO_ORI="", PAC_USUARIO_ORI="", PAC_CLAVE_ORI="", PAC_ESTADO_ORI="", PAC_IDCLINICA_ORI="", PAC_OCUPACION_ORI="", PAC_SEGURO_MEDICO_ORI="";
                String PAC_CELULAR_ORI="", PAC_IDCIUDAD_ORI="", PAC_PROFESION_ORI="", PAC_OTROS_DATOS_ORI="", PAC_FECHA_NAC_ORI="", PAC_SEXO_ORI="", PAC_ESTADO_CIVIL_ORI="", PAC_TIENE_HIJOS_ORI="", PAC_CANTIDAD_HIJOS_ORI="";
                
//                // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
//                if (ID_PACIENTE.equals("0") || ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
//                    // ... 
//                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
//                    
//                } // END ELSE 
//                
//                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
//                if ((String)request.getAttribute("CP_PAC_NOMBRE") != null) {
//                    PAC_NOMBRE = (String) request.getAttribute("CP_PAC_NOMBRE");
//                }
//                if ((String)request.getAttribute("CP_PAC_APELLIDO") != null) {
//                    PAC_APELLIDO = (String) request.getAttribute("CP_PAC_APELLIDO");
//                }
//                if ((String)request.getAttribute("CP_PAC_CINRO") != null) {
//                    PAC_CINRO = (String) request.getAttribute("CP_PAC_CINRO");
//                }
//                if ((String)request.getAttribute("CP_PAC_RAZON_SOCIAL") != null) {
//                    PAC_RAZON_SOCIAL = (String) request.getAttribute("CP_PAC_RAZON_SOCIAL");
//                }
//                if ((String)request.getAttribute("CP_PAC_RUC") != null) {
//                    PAC_RUC = (String) request.getAttribute("CP_PAC_RUC");
//                }
//                if ((String)request.getAttribute("CP_PAC_TELEFONO") != null) {
//                    PAC_TELEFONO = (String) request.getAttribute("CP_PAC_TELEFONO");
//                }
//                if ((String)request.getAttribute("CP_PAC_DIRECCION") != null) {
//                    PAC_DIRECCION = (String) request.getAttribute("CP_PAC_DIRECCION");
//                }
//                if ((String)request.getAttribute("CP_PAC_EMAIL") != null) {
//                    PAC_EMAIL = (String) request.getAttribute("CP_PAC_EMAIL");
//                }
//                if ((String)request.getAttribute("CP_PAC_USUARIO") != null) {
//                    PAC_USUARIO = (String) request.getAttribute("CP_PAC_USUARIO");
//                }
//                if ((String)request.getAttribute("CP_PAC_CLAVE") != null) {
//                    PAC_CLAVE = (String) request.getAttribute("CP_PAC_CLAVE");
//                }
//                if ((String)request.getAttribute("CP_PAC_ESTADO") != null) {
//                    PAC_ESTADO = (String) request.getAttribute("CP_PAC_ESTADO");
//                }
//                if ((String)request.getAttribute("CP_PAC_FECHA_NAC") != null) {
//                    PAC_FECHA_NAC = (String) request.getAttribute("CP_PAC_FECHA_NAC");
//                }
//                if ((String)request.getAttribute("CP_PAC_SEXO") != null) {
//                    PAC_SEXO = (String) request.getAttribute("CP_PAC_SEXO");
//                }
//                if ((String)request.getAttribute("CP_PAC_IDCLINICA") != null) {
//                    PAC_IDCLINICA = (String) request.getAttribute("CP_PAC_IDCLINICA");
//                }
//                if ((String)request.getAttribute("CP_PAC_OCUPACION") != null) {
//                    PAC_OCUPACION = (String) request.getAttribute("CP_PAC_OCUPACION");
//                }
//                if ((String)request.getAttribute("CP_PAC_SEGURO_MEDICO") != null) {
//                    PAC_SEGURO_MEDICO = (String) request.getAttribute("CP_PAC_SEGURO_MEDICO");
//                }
                
                List<BeanPersona> datosPaciente = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                // recupero de la lista 
                if(datosPaciente != null) {
                    BeanPersona paciente_datos = datosPaciente.get(0);
                    PAC_CINRO = paciente_datos.getRP_CINRO();
                    PAC_NOMBRE = paciente_datos.getRP_NOMBRE();
                    PAC_APELLIDO = paciente_datos.getRP_APELLIDO();
                    PAC_RAZON_SOCIAL = paciente_datos.getRP_RAZON_SOCIAL();
                    PAC_RUC = paciente_datos.getRP_RUC();
                    PAC_TELEFONO = paciente_datos.getRP_TELFPARTICULAR();
                    PAC_CELULAR = paciente_datos.getRP_TELFMOVIL();
                    PAC_DIRECCION = paciente_datos.getRP_DIRECCION();
                    PAC_EMAIL = paciente_datos.getRP_EMAIL();
                    PAC_IDCIUDAD = paciente_datos.getRP_IDCIUDAD();
//                    PAC_DESC_CIUDAD = paciente_datos.getRP_DESC_CIUDAD();
                    PAC_IDUSUARIO = paciente_datos.getSU_IDUSUARIO();
//                    PAC_USUARIO = paciente_datos.getSU_USUARIO();
//                    PAC_CLAVE = paciente_datos.getSU_CLAVE();
//                    PAC_ESTADO_USU = paciente_datos.getSU_ESTADO();
                    PAC_PROFESION = paciente_datos.getRP_OCUPACION();
                    PAC_FECHA_NAC = paciente_datos.getRP_FECHANAC();
                    PAC_SEXO = paciente_datos.getRP_SEXO();
                    PAC_ESTADO_CIVIL = paciente_datos.getRP_ESTADOCIVIL();
                    PAC_TIENE_HIJOS = paciente_datos.getRP_TIENE_HIJOS();
                    PAC_CANTIDAD_HIJOS = paciente_datos.getRP_CANT_HIJOS();
                    PAC_IDCLINICA = paciente_datos.getRP_IDCLINICA();
//                    PAC_OCUPACION = paciente_datos.getRP_OCUPACION();
//                    PAC_SEGURO_MEDICO = paciente_datos.getRP_SEGURO_MEDICO();
                    PAC_OTROS_DATOS = paciente_datos.getRP_OBSERVACION();
                    PAC_FOTO = paciente_datos.getRP_FOTO();
                    PAC_FOTO_ABS = paciente_datos.getRP_FOTO_PATH_ABS();
                }

                List<BeanPersona> LISTA_DATOS_AUX = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS_AUX");
                // recupero de la lista 
                if(LISTA_DATOS_AUX != null) {
                    BeanPersona paciente_datos = datosPaciente.get(0);
                    PAC_NOMBRE_ORI = paciente_datos.getRP_NOMBRE();
                    PAC_APELLIDO_ORI = paciente_datos.getRP_APELLIDO();
                    PAC_CINRO_ORI = paciente_datos.getRP_CINRO();
//                    PAC_RAZON_SOCIAL_ORI = paciente_datos.getRP_RAZON_SOCIAL();
//                    PAC_RUC_ORI = paciente_datos.getRP_RUC();
                    PAC_TELEFONO_ORI = paciente_datos.getRP_TELFPARTICULAR();
                    PAC_CELULAR_ORI = paciente_datos.getRP_TELFMOVIL();
                    PAC_DIRECCION_ORI = paciente_datos.getRP_DIRECCION();
                    PAC_EMAIL_ORI = paciente_datos.getRP_EMAIL();
                    PAC_IDCIUDAD_ORI = paciente_datos.getRP_IDCIUDAD();
                    PAC_IDUSUARIO_ORI = paciente_datos.getSU_IDUSUARIO();
//                    PAC_USUARIO_ORI = paciente_datos.getSU_USUARIO();
//                    PAC_CLAVE_ORI = paciente_datos.getSU_CLAVE();
//                    PAC_ESTADO_USU_ORI = paciente_datos.getSU_ESTADO();
                    PAC_IDCLINICA_ORI = paciente_datos.getRP_IDCLINICA();
                    PAC_PROFESION_ORI = paciente_datos.getRP_OCUPACION();
                    PAC_SEGURO_MEDICO_ORI = paciente_datos.getRP_SEGURO_MEDICO();
                    PAC_OTROS_DATOS_ORI = paciente_datos.getRP_OBSERVACION();
                    PAC_FECHA_NAC_ORI = paciente_datos.getRP_FECHANAC();
                    PAC_SEXO_ORI = paciente_datos.getRP_SEXO();
                    PAC_ESTADO_CIVIL_ORI = paciente_datos.getRP_ESTADOCIVIL();
                    PAC_TIENE_HIJOS_ORI = paciente_datos.getRP_TIENE_HIJOS();
                    PAC_CANTIDAD_HIJOS_ORI = paciente_datos.getRP_CANT_HIJOS();
                }
                
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR CLIENTES, ENTONCES DE ACUERDO AL IDCLIENTE VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO CLIENTE O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                if (ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
                    varTitulo = "PACIENTE - NUEVO REGISTRO";
                } else {
                    varTitulo = "PACIENTE - MODIFICAR REGISTRO";
                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CPSrv" method="post" autocomplete="off" enctype="multipart/form-data">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_PACIENTE %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nro. Cédula:</div>
                                    <div class="pac_panel_3_ci">
                                        <input type="text" value="<%= PAC_CINRO %>" id="tNC" name="tNC" class="form-control" />
<%--                                    </div>
                                    <div class="pac_panel_3">--%>
                                    <%
                                    if (ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
                                    %>
                                        <div class="tooltip_btn_gnt">
                                            <input type="submit" value="Generar Nro. Temporal" name="accion" class="w-auto btn btn-outline-info">
                                            <span class="tooltiptext_btn_gnt">Botón para generar un codigo aleatorio para registrar al paciente en caso de que no cuente con su Nro. de C.I.</span>
                                        </div>
                                    <%
                                    }
                                    %>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nombre/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_NOMBRE %>" id="tN" name="tN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Apellido/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_APELLIDO %>" id="tA" name="tA" class="form-control" />
                                    </div>
                                </div>
                                <%--<div class="pac_panel_1">
                                    <div class="pac_panel_2">Razon Social:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_RAZON_SOCIAL %>" id="tRS" name="tRS" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">RUC:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_RUC %>" id="tR" name="tR" class="form-control" />
                                    </div>
                                </div>--%>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Teléfono:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_TELEFONO %>" id="tTP" name="tTP" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Celular:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_CELULAR %>" id="tCP" name="tCP" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Dirección:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_DIRECCION %>" id="tD" name="tD" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Email:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_EMAIL %>" id="tE" name="tE" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Profesión:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_PROFESION %>" id="tPP" name="tPP" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Fecha de Nac.:</div>
                                    <div class="pac_panel_3">
                                        <input type="date" value="<%= PAC_FECHA_NAC %>" id="tFN" name="tFN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Sexo:</div>
                                    <div class="pac_panel_3">
                                        <select id="cSe" name="cSe" class="form-control">
                                            <%
                                            Map<String, String> listaSexoPac;
                                            listaSexoPac = metodosIniSes.cargarComboSexoPer(PAC_SEXO);
                                            Set setCbxSexo = listaSexoPac.entrySet();
                                            Iterator iCbxSexo = setCbxSexo.iterator();

                                            while(iCbxSexo.hasNext()) {
                                                Map.Entry mapCbxSexo = (Map.Entry) iCbxSexo.next();
                                            %>
                                            <option value="<%= mapCbxSexo.getKey() %>"><%= mapCbxSexo.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Estado Civil:</div>
                                    <div class="pac_panel_3">
                                        <select id="cECP" name="cECP" class="form-control">
                                            <%
                                            Map<String, String> listaEstadoCivil;
                                            listaEstadoCivil = metodosIniSes.cargarComboEstadoCivil(PAC_ESTADO_CIVIL);
                                            Set setCbxEC = listaEstadoCivil.entrySet();
                                            Iterator iCbxEC = setCbxEC.iterator();

                                            while(iCbxEC.hasNext()) {
                                                Map.Entry mapCbxSexo = (Map.Entry) iCbxEC.next();
                                            %>
                                            <option value="<%= mapCbxSexo.getKey() %>"><%= mapCbxSexo.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">¿Tiene Hijos?:</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cbxTHP" name="cbxTHP">
                                            <%
                                            Map<String, String> cbxCYOH = new LinkedHashMap();
                                            cbxCYOH = metodosIniSes.cargarComboSiNo(PAC_TIENE_HIJOS); 
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
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Cantidad de Hijos:</div>
                                    <div class="pac_panel_3">
                                        <input type="number" value="<%= PAC_CANTIDAD_HIJOS %>" id="TCHP" name="TCHP" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Ciudad:</div>
                                    <div class="pac_panel_3">
                                        <select id="cCI" name="cCI" class="form-control">
                                            <%
                                            Map<String, String> listaCiudad;
                                            listaCiudad = metodosRefCiudad.cargarComboCiudad(PAC_IDCIUDAD);
                                            Set setCbxCiu = listaCiudad.entrySet();
                                            Iterator iCbxCiu = setCbxCiu.iterator();

                                            while(iCbxCiu.hasNext()) {
                                                Map.Entry mapCbxCiu = (Map.Entry) iCbxCiu.next();
                                            %>
                                            <option value="<%= mapCbxCiu.getKey() %>"><%= mapCbxCiu.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Clinica:</div>
                                    <div class="pac_panel_3">
                                        <select id="cCli" name="cCli" class="form-control">
                                            <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosRefClinica.cargarComboClinica(PAC_IDCLINICA);
                                            Set setCbxCli = listaClinicas.entrySet();
                                            Iterator iCbxCli = setCbxCli.iterator();

                                            while(iCbxCli.hasNext()) {
                                                Map.Entry mapCbxCli = (Map.Entry) iCbxCli.next();
                                            %>
                                            <option value="<%= mapCbxCli.getKey() %>"><%= mapCbxCli.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Otros Datos:</div>
                                    <div class="pac_panel_3">
                                        <textarea id="TPOD" name="TPOD" rows="4" cols="70" class="form-control"><%= PAC_OTROS_DATOS %></textarea>
                                    </div>
                                </div>
                                
                                
                                <div class="pac_panel_btns">
                                    <input type="hidden" name="ID_USUARIO_PACIENTE" value="<%=PAC_IDUSUARIO%>" class="my-3 border-1 border-color-red" />
                                    <input type="submit" name="accion" value="GRABAR" class="btn btn-primary" />
                                    <!--<input type="submit" name="accion" value="LIMPIAR" class="btn btn-primary" />-->
                                    <a href="javascript:cancelar()" class="btn btn-primary">LIMPIAR</a>
                                    <input type="submit" name="accion" value="VOLVER ATRAS" class="btn btn-danger" />
                                    <%
                                    // OBSERVACION_COMENTARIO:      COMENTE PORQUE AL ENTRAR A LA PAGINA YA SE COLOCA A LA VARIABLE DE LA SESION CON NULL Y AL GUARDAR NO ME SIRVE PORQUE RETORNA A PACIENTE Y SI DESDE AGENDAMIENTO CREO UN PACIENTE AL GUARDAR ME DEVERIA DE DEVOLVER AL AGENDAMIENTO PARA CARGAR EL AGENDAMIENTO, POR ESO LO COMENTE Y LO AGREGUE EN UN EVENTO EN EL CONTROLADOR.
//                                    /*
//                                        * OBSERVACION: 
//                                        - COMO LA PAGINA DE AGENDAMIENTO TIENE UN BOTON PARA REDIRECCIONAR A LA PAGINA DE PACIENTE, CUAQUIER OTRA PAGINA TAMBIEN PUEDE TENER ESTA CLASE DE BOTON, 
//                                          Y POR ESO LE CREE ESTA VARIABLE "PAC_BTN_VOLVER_ATRAS" PARA PODER SABER QUE PAGINA LE REDIRECCIONO A LA PAGINA DE PACIENTE Y ASI PODER VOLVER A ESA PAGINA 
//                                    */
//                                    String var_volver_atras = urlPaciente;
//                                    String PARAM_BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("PAC_BTN_VOLVER_ATRAS");
//                                    if (PARAM_BTN_VOLVER_ATRAS == null || PARAM_BTN_VOLVER_ATRAS.isEmpty()) { PARAM_BTN_VOLVER_ATRAS = "PAC"; }
//                                    System.out.println(".    _JSP___PAC_BTN_VOLVER_ATRAS:     :"+PARAM_BTN_VOLVER_ATRAS);
//                                    
//                                    if (PARAM_BTN_VOLVER_ATRAS.equals("PAC")) { // CONTROLO QUE VARIABLE TIENE CARGADO EL PARAMETRO QUE RECIBO POR LA SESION PARA PODER SABER QUE PAGINA FUE LA QUE REDIRECCIONO A LA PAGINA DE PACIENTE 
//                                        var_volver_atras = urlPaciente;
//                                    } else if (PARAM_BTN_VOLVER_ATRAS.equals("AGEN")) {
//                                        sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
//                                        var_volver_atras = "add_agend.htm";
////                                        var_volver_atras = urlAgendamiento;
//                                    } else if (PARAM_BTN_VOLVER_ATRAS.equals("VER_PAC")) {
//                                        sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", null);
//                                        var_volver_atras = "ver_paciente.htm";
//                                    }
                                    %>
                                    <%--<a href="<= var_volver_atras %>" class="btn btn-danger">VOLVER</a>--%>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            function cancelar() {
                document.getElementById("tN").value = '';
                document.getElementById("tA").value = '';
                document.getElementById("tNC").value = '';
                document.getElementById("tTP").value = '';
                document.getElementById("tCP").value = '';
                document.getElementById("tD").value = '';
                document.getElementById("tE").value = '';
                document.getElementById("tPP").value = '';
                document.getElementById("cSe").value = '';
                document.getElementById("cECP").value = '';
                document.getElementById("cbxTHP").value = '';
                document.getElementById("tFN").value = '';
                document.getElementById("TCHP").value = '';
                document.getElementById("cCI").value = '';
                document.getElementById("cCli").value = '';
                document.getElementById("TPOD").value = '';
                
//                document.getElementById("PacFile").value = '';
//                document.getElementById('preview').style.display = "none";
////                document.getElementById("preview").className = "styleImage disNone";
            }
            
//            // codigo para mostrar la imagen que se selecciona con el boton para cargar un archivo 
//            document.getElementById('PacFile').onchange = function(e) {
//                let reader = new FileReader();
//                reader.readAsDataURL(e.target.files[0]);
//                reader.onload = function() {
////                    imgLoad = document.getElementById('loadImg');
//                    
//                    let preview = document.getElementById('preview');
//                    imagen = document.getElementById('previewImg');
////                    imagen = document.createElement('img');
//                    imagen.src = reader.result;
//                    
////                    imgLoad.className = 'disNone';
////                    preview.className = 'styleImage disBlock';
//                    preview.append(imagen);
//                    
//                    var loadImg = document.getElementById('loadImg');
//                    if (loadImg !== null) {
//                        loadImg.style.display = 'none';
//                    }
//                    document.getElementById('preview').style.display = 'block';
//                }
//            }
        </script>
    </body>
</html>