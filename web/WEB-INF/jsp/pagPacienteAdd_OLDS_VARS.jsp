<%-- 
    Document   : pagPacienteAdd
    Created on : 25-nov-2021, 11:11:28
    Author     : RYUU
--%>

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
        <meta charset="UTF-8">
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        
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
            %>
            <%
                /*
                    * OBSERVACION: PARA AÑADIR UN PACIENTE Y EDITAR UN PACIENTE 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_CLIENTE, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                System.out.println("_jsp___ID_PACIENTE:  "+ID_PACIENTE);
                
                List<BeanPersona> listaDatos;
                String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_OCUPACION="", PAC_SEGURO_MEDICO="";
                String PAC_NOMBRE_ORI="", PAC_APELLIDO_ORI="", PAC_CINRO_ORI="", PAC_RAZON_SOCIAL_ORI="", PAC_RUC_ORI="", PAC_TELEFONO_ORI="", PAC_DIRECCION_ORI="", PAC_EMAIL_ORI="", PAC_IDUSUARIO_ORI="", PAC_USUARIO_ORI="", PAC_CLAVE_ORI="", PAC_ESTADO_ORI="", PAC_IDCLINICA_ORI="", PAC_OCUPACION_ORI="", PAC_SEGURO_MEDICO_ORI="";
                
                // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                if (ID_PACIENTE.equals("0") || ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
                    // ... 
                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                    listaDatos = metodosPersona.traerDatosPersona(ID_PACIENTE);
                    Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                    BeanPersona paciente = null;
                    
                    while(iterPaciente.hasNext()) {
                        paciente = iterPaciente.next();
                        PAC_NOMBRE = paciente.getRP_NOMBRE();
                        PAC_APELLIDO = paciente.getRP_APELLIDO();
                        PAC_CINRO = paciente.getRP_CINRO();
                        PAC_RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
                        PAC_RUC = paciente.getRP_RUC();
                        PAC_TELEFONO = paciente.getRP_TELFMOVIL();
                        PAC_DIRECCION = paciente.getRP_DIRECCION();
                        PAC_EMAIL = paciente.getRP_EMAIL();
                        PAC_IDUSUARIO = paciente.getSU_IDUSUARIO();
                        PAC_USUARIO = paciente.getSU_USUARIO();
                        PAC_CLAVE = paciente.getSU_CLAVE();
                        PAC_ESTADO = paciente.getSU_ESTADO();
                        PAC_FECHA_NAC = paciente.getRP_FECHANAC();
                        PAC_SEXO = paciente.getRP_SEXO();
                        PAC_IDCLINICA = paciente.getRP_IDCLINICA();
                        PAC_OCUPACION = paciente.getRP_OCUPACION();
                        PAC_SEGURO_MEDICO = paciente.getRP_SEGURO_MEDICO();
                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                        PAC_NOMBRE_ORI = paciente.getRP_NOMBRE();
                        PAC_APELLIDO_ORI = paciente.getRP_APELLIDO();
                        PAC_CINRO_ORI = paciente.getRP_CINRO();
                        PAC_RAZON_SOCIAL_ORI = paciente.getRP_RAZON_SOCIAL();
                        PAC_RUC_ORI = paciente.getRP_RUC();
                        PAC_TELEFONO_ORI = paciente.getRP_TELFMOVIL();
                        PAC_DIRECCION_ORI = paciente.getRP_DIRECCION();
                        PAC_EMAIL_ORI = paciente.getRP_EMAIL();
                        PAC_IDUSUARIO_ORI = paciente.getSU_IDUSUARIO();
                        PAC_USUARIO_ORI = paciente.getSU_USUARIO();
                        PAC_CLAVE_ORI = paciente.getSU_CLAVE();
                        PAC_ESTADO_ORI = paciente.getSU_ESTADO();
                        PAC_IDCLINICA_ORI = paciente.getRP_IDCLINICA();
                        PAC_OCUPACION_ORI = paciente.getRP_OCUPACION();
                        PAC_SEGURO_MEDICO_ORI = paciente.getRP_SEGURO_MEDICO();
                    } // end while 
                    
                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
                    sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CP_PAC_NOMBRE") != null) {
                    PAC_NOMBRE = (String) request.getAttribute("CP_PAC_NOMBRE");
                }
                if ((String)request.getAttribute("CP_PAC_APELLIDO") != null) {
                    PAC_APELLIDO = (String) request.getAttribute("CP_PAC_APELLIDO");
                }
                if ((String)request.getAttribute("CP_PAC_CINRO") != null) {
                    PAC_CINRO = (String) request.getAttribute("CP_PAC_CINRO");
                }
                if ((String)request.getAttribute("CP_PAC_RAZON_SOCIAL") != null) {
                    PAC_RAZON_SOCIAL = (String) request.getAttribute("CP_PAC_RAZON_SOCIAL");
                }
                if ((String)request.getAttribute("CP_PAC_RUC") != null) {
                    PAC_RUC = (String) request.getAttribute("CP_PAC_RUC");
                }
                if ((String)request.getAttribute("CP_PAC_TELEFONO") != null) {
                    PAC_TELEFONO = (String) request.getAttribute("CP_PAC_TELEFONO");
                }
                if ((String)request.getAttribute("CP_PAC_DIRECCION") != null) {
                    PAC_DIRECCION = (String) request.getAttribute("CP_PAC_DIRECCION");
                }
                if ((String)request.getAttribute("CP_PAC_EMAIL") != null) {
                    PAC_EMAIL = (String) request.getAttribute("CP_PAC_EMAIL");
                }
                if ((String)request.getAttribute("CP_PAC_USUARIO") != null) {
                    PAC_USUARIO = (String) request.getAttribute("CP_PAC_USUARIO");
                }
                if ((String)request.getAttribute("CP_PAC_CLAVE") != null) {
                    PAC_CLAVE = (String) request.getAttribute("CP_PAC_CLAVE");
                }
                if ((String)request.getAttribute("CP_PAC_ESTADO") != null) {
                    PAC_ESTADO = (String) request.getAttribute("CP_PAC_ESTADO");
                }
                if ((String)request.getAttribute("CP_PAC_FECHA_NAC") != null) {
                    PAC_FECHA_NAC = (String) request.getAttribute("CP_PAC_FECHA_NAC");
                }
                if ((String)request.getAttribute("CP_PAC_SEXO") != null) {
                    PAC_SEXO = (String) request.getAttribute("CP_PAC_SEXO");
                }
                if ((String)request.getAttribute("CP_PAC_IDCLINICA") != null) {
                    PAC_IDCLINICA = (String) request.getAttribute("CP_PAC_IDCLINICA");
                }
                if ((String)request.getAttribute("CP_PAC_OCUPACION") != null) {
                    PAC_OCUPACION = (String) request.getAttribute("CP_PAC_OCUPACION");
                }
                if ((String)request.getAttribute("CP_PAC_SEGURO_MEDICO") != null) {
                    PAC_SEGURO_MEDICO = (String) request.getAttribute("CP_PAC_SEGURO_MEDICO");
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
                        <form action="CPSrv" method="post" autocomplete="off">
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
                                <div class="pac_panel_1">
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
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Teléfono:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_TELEFONO %>" id="tT" name="tT" class="form-control" />
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
                                    <div class="pac_panel_2">Ocupación:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_OCUPACION %>" id="tPO" name="tPO" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Seguro Médico:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PAC_SEGURO_MEDICO %>" id="tSM" name="tSM" class="form-control" />
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
                                
                                <div class="pac_panel_btns">
                                    <input type="submit" name="accion" value="GRABAR" class="btn btn-primary" />
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
        <script>
            function cancelar() {
                document.getElementById("tN").value = "<%= PAC_NOMBRE_ORI %>";
                document.getElementById("tA").value = "<%= PAC_APELLIDO_ORI %>";
                document.getElementById("tNC").value = "<%= PAC_CINRO_ORI %>";
                document.getElementById("tRS").value = "<%= PAC_RAZON_SOCIAL_ORI %>";
                document.getElementById("tR").value = "<%= PAC_RUC_ORI %>";
                document.getElementById("tT").value = "<%= PAC_TELEFONO_ORI %>";
                document.getElementById("tD").value = "<%= PAC_DIRECCION_ORI %>";
                document.getElementById("tE").value = "<%= PAC_EMAIL_ORI %>";
                document.getElementById("tPO").value = "<%= PAC_OCUPACION_ORI %>";
                document.getElementById("tSM").value = "<%= PAC_SEGURO_MEDICO_ORI %>";
                document.getElementById("tFN").value = "";
                document.getElementById("cSe").value = "N"; <% // SELECCIONO EL VALOR ACTIVO SUPONINEDO QUE LOS VALORES SON POR LA INICIAL Y NO POR EL TEXTO COMPLETO %>
                document.getElementById("cCli").value = "0";
            }
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>