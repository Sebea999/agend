<%-- 
    Document   : pagSecretarioAdd
    Created on : 11-dic-2021, 11:16:04
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
        <title>Secretario</title>
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
                String mensaje = (String) request.getAttribute("CS_MENSAJE"); // CONTROLLER SECRETARIO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CS_TIPO_MENSAJE"); // CONTROLLER SECRETARIO TIPO MENSAJE 
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
                    * OBSERVACION: PARA AÑADIR UN SECRETARIO Y EDITAR UN SECRETARIO 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_SECRETARIO, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_SECRETARIO = (String) sesionDatosUsuario.getAttribute("ID_SECRETARIO");
                System.out.println("_jsp___ID_SECRETARIO:  "+ID_SECRETARIO);
                
                List<BeanPersona> listaDatos;
                String SEC_NOMBRE="", SEC_APELLIDO="", SEC_CINRO="", SEC_RAZON_SOCIAL="", SEC_RUC="", SEC_TELEFONO="", SEC_DIRECCION="", SEC_EMAIL="", SEC_IDUSUARIO="", SEC_USUARIO="", SEC_CLAVE="", SEC_ESTADO="", SEC_FECHA_NAC="", SEC_SEXO="", SEC_IDCLINICA="";
                String SEC_NOMBRE_ORI="", SEC_APELLIDO_ORI="", SEC_CINRO_ORI="", SEC_RAZON_SOCIAL_ORI="", SEC_RUC_ORI="", SEC_TELEFONO_ORI="", SEC_DIRECCION_ORI="", SEC_EMAIL_ORI="", SEC_IDUSUARIO_ORI="", SEC_USUARIO_ORI="", SEC_CLAVE_ORI="", SEC_ESTADO_ORI="";
                
                // CONTROLO EL ID_MEDICO PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                if (ID_SECRETARIO.equals("0") || ID_SECRETARIO.isEmpty() || ID_SECRETARIO.equals("")) {
                    // ... 
                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                    listaDatos = metodosPersona.traerDatosPersona(ID_SECRETARIO);
                    Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                    BeanPersona paciente = null;
                    
                    while(iterPaciente.hasNext()) {
                        paciente = iterPaciente.next();
                        SEC_NOMBRE = paciente.getRP_NOMBRE();
                        SEC_APELLIDO = paciente.getRP_APELLIDO();
                        SEC_CINRO = paciente.getRP_CINRO();
                        SEC_RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
                        SEC_RUC = paciente.getRP_RUC();
                        SEC_TELEFONO = paciente.getRP_TELFMOVIL();
                        SEC_DIRECCION = paciente.getRP_DIRECCION();
                        SEC_EMAIL = paciente.getRP_EMAIL();
                        SEC_IDUSUARIO = paciente.getSU_IDUSUARIO();
                        SEC_USUARIO = paciente.getSU_USUARIO();
                        SEC_CLAVE = paciente.getSU_CLAVE();
                        SEC_ESTADO = paciente.getSU_ESTADO();
                        SEC_FECHA_NAC = paciente.getRP_FECHANAC();
                        SEC_SEXO = paciente.getRP_SEXO();
                        SEC_IDCLINICA = paciente.getRP_IDCLINICA();
                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                        SEC_NOMBRE_ORI = paciente.getRP_NOMBRE();
                        SEC_APELLIDO_ORI = paciente.getRP_APELLIDO();
                        SEC_CINRO_ORI = paciente.getRP_CINRO();
                        SEC_RAZON_SOCIAL_ORI = paciente.getRP_RAZON_SOCIAL();
                        SEC_RUC_ORI = paciente.getRP_RUC();
                        SEC_TELEFONO_ORI = paciente.getRP_TELFMOVIL();
                        SEC_DIRECCION_ORI = paciente.getRP_DIRECCION();
                        SEC_EMAIL_ORI = paciente.getRP_EMAIL();
                        SEC_IDUSUARIO_ORI = paciente.getSU_IDUSUARIO();
                        SEC_USUARIO_ORI = paciente.getSU_USUARIO();
                        SEC_CLAVE_ORI = paciente.getSU_CLAVE();
                        SEC_ESTADO_ORI = paciente.getSU_ESTADO();
                    } // end while 
                    
                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
                    sesionDatosUsuario.setAttribute("ID_USUARIO_SECRETARIO", SEC_IDUSUARIO);
                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CS_SEC_NOMBRE") != null) {
                    SEC_NOMBRE = (String) request.getAttribute("CS_SEC_NOMBRE");
                }
                if ((String)request.getAttribute("CS_SEC_APELLIDO") != null) {
                    SEC_APELLIDO = (String) request.getAttribute("CS_SEC_APELLIDO");
                }
                if ((String)request.getAttribute("CS_SEC_CINRO") != null) {
                    SEC_CINRO = (String) request.getAttribute("CS_SEC_CINRO");
                }
                if ((String)request.getAttribute("CS_SEC_RAZON_SOCIAL") != null) {
                    SEC_RAZON_SOCIAL = (String) request.getAttribute("CS_SEC_RAZON_SOCIAL");
                }
                if ((String)request.getAttribute("CS_SEC_RUC") != null) {
                    SEC_RUC = (String) request.getAttribute("CS_SEC_RUC");
                }
                if ((String)request.getAttribute("CS_SEC_TELEFONO") != null) {
                    SEC_TELEFONO = (String) request.getAttribute("CS_SEC_TELEFONO");
                }
                if ((String)request.getAttribute("CS_SEC_DIRECCION") != null) {
                    SEC_DIRECCION = (String) request.getAttribute("CS_SEC_DIRECCION");
                }
                if ((String)request.getAttribute("CS_SEC_EMAIL") != null) {
                    SEC_EMAIL = (String) request.getAttribute("CS_SEC_EMAIL");
                }
                if ((String)request.getAttribute("CS_SEC_USUARIO") != null) {
                    SEC_USUARIO = (String) request.getAttribute("CS_SEC_USUARIO");
                }
                if ((String)request.getAttribute("CS_SEC_CLAVE") != null) {
                    SEC_CLAVE = (String) request.getAttribute("CS_SEC_CLAVE");
                }
                if ((String)request.getAttribute("CS_SEC_ESTADO") != null) {
                    SEC_ESTADO = (String) request.getAttribute("CS_SEC_ESTADO");
                }
                if ((String)request.getAttribute("CS_SEC_FECHA_NAC") != null) {
                    SEC_FECHA_NAC = (String) request.getAttribute("CS_SEC_FECHA_NAC");
                }
                if ((String)request.getAttribute("CS_SEC_SEXO") != null) {
                    SEC_SEXO = (String) request.getAttribute("CS_SEC_SEXO");
                }
                if ((String)request.getAttribute("CS_SEC_IDCLINICA") != null) {
                    SEC_IDCLINICA = (String) request.getAttribute("CS_SEC_IDCLINICA");
                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR MEDICO, ENTONCES DE ACUERDO AL IDMEDICO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO MEDICO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                if (ID_SECRETARIO.isEmpty() || ID_SECRETARIO.equals("")) {
                    varTitulo = "SECRETARIO - NUEVO REGISTRO";
                } else {
                    varTitulo = "SECRETARIO - MODIFICAR REGISTRO";
                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CSSrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_SECRETARIO %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nro. Cédula:</div>
                                    <div class="pac_panel_3_ci">
                                        <input type="text" value="<%= SEC_CINRO %>" id="tNC" name="tNC" class="form-control" />
<%--                                    </div>
                                    <div class="pac_panel_3">--%>
                                    <%
                                    if (ID_SECRETARIO.isEmpty() || ID_SECRETARIO.equals("")) {
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
                                        <input type="text" value="<%= SEC_NOMBRE %>" id="tN" name="tN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Apellido/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_APELLIDO %>" id="tA" name="tA" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Razon Social:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_RAZON_SOCIAL %>" id="tRS" name="tRS" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">RUC:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_RUC %>" id="tR" name="tR" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Teléfono:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_TELEFONO %>" id="tT" name="tT" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Dirección:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_DIRECCION %>" id="tD" name="tD" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Email:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= SEC_EMAIL %>" id="tE" name="tE" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Fecha de Nac.:</div>
                                    <div class="pac_panel_3">
                                        <input type="date" value="<%= SEC_FECHA_NAC %>" id="tFN" name="tFN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Sexo:</div>
                                    <div class="pac_panel_3">
                                        <select id="cSe" name="cSe" class="form-control">
                                            <%
                                            Map<String, String> listaSexoPac;
                                            listaSexoPac = metodosIniSes.cargarComboSexoPer(SEC_SEXO);
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
                                        <select class="form-control" id="cCli" name="cCli">
                                            <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosRefClinica.cargarComboClinica(SEC_IDCLINICA);
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
                                    <a href="<%= urlSecretario %>" class="btn btn-danger">VOLVER</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script>
            function cancelar() {
                document.getElementById("tN").value = "<%= SEC_NOMBRE_ORI %>";
                document.getElementById("tA").value = "<%= SEC_APELLIDO_ORI %>";
                document.getElementById("tNC").value = "<%= SEC_CINRO_ORI %>";
                document.getElementById("tRS").value = "<%= SEC_RAZON_SOCIAL_ORI %>";
                document.getElementById("tR").value = "<%= SEC_RUC_ORI %>";
                document.getElementById("tT").value = "<%= SEC_TELEFONO_ORI %>";
                document.getElementById("tD").value = "<%= SEC_DIRECCION_ORI %>";
                document.getElementById("tE").value = "<%= SEC_EMAIL_ORI %>";
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