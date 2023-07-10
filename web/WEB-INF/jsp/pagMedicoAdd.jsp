<%-- 
    Document   : pagMedicoAdd
    Created on : 10-dic-2021, 11:16:04
    Author     : RYUU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Médico</title>
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
                String mensaje = (String) request.getAttribute("CM_MENSAJE"); // CONTROLLER MEDICO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CM_TIPO_MENSAJE"); // CONTROLLER MEDICO TIPO MENSAJE 
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
                    * OBSERVACION: PARA AÑADIR UN MEDICO Y EDITAR UN MEDICO 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_MEDICO, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_MEDICO = (String) sesionDatosUsuario.getAttribute("ID_MEDICO");
                System.out.println("_jsp___ID_MEDICO:  "+ID_MEDICO);
                
                List<BeanPersona> listaDatos;
                String MED_NOMBRE="", MED_APELLIDO="", MED_CINRO="", MED_RAZON_SOCIAL="", MED_RUC="", MED_TELEFONO="", MED_DIRECCION="", MED_EMAIL="", MED_IDUSUARIO="", MED_USUARIO="", MED_CLAVE="", MED_ESTADO="", MED_FECHA_NAC="", MED_SEXO="", MED_IDCLINICA="";
                String MED_NOMBRE_ORI="", MED_APELLIDO_ORI="",MED_CINRO_ORI="", MED_RAZON_SOCIAL_ORI="", MED_RUC_ORI="", MED_TELEFONO_ORI="", MED_DIRECCION_ORI="", MED_EMAIL_ORI="", MED_IDUSUARIO_ORI="", MED_USUARIO_ORI="", MED_CLAVE_ORI="", MED_ESTADO_ORI="", MED_IDCLINICA_ORI="";
                
                // CONTROLO EL ID_MEDICO PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                if (ID_MEDICO.equals("0") || ID_MEDICO.isEmpty() || ID_MEDICO.equals("")) {
                    // ... 
                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                    listaDatos = metodosPersona.traerDatosPersona(ID_MEDICO);
                    Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                    BeanPersona paciente = null;
                    
                    while(iterPaciente.hasNext()) {
                        paciente = iterPaciente.next();
                        MED_NOMBRE = paciente.getRP_NOMBRE();
                        MED_APELLIDO = paciente.getRP_APELLIDO();
                        MED_CINRO = paciente.getRP_CINRO();
                        MED_RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
                        MED_RUC = paciente.getRP_RUC();
                        MED_TELEFONO = paciente.getRP_TELFMOVIL();
                        MED_DIRECCION = paciente.getRP_DIRECCION();
                        MED_EMAIL = paciente.getRP_EMAIL();
                        MED_IDUSUARIO = paciente.getSU_IDUSUARIO();
                        MED_USUARIO = paciente.getSU_USUARIO();
                        MED_CLAVE = paciente.getSU_CLAVE();
                        MED_ESTADO = paciente.getSU_ESTADO();
                        MED_FECHA_NAC = paciente.getRP_FECHANAC();
                        MED_SEXO = paciente.getRP_SEXO();
                        MED_IDCLINICA = paciente.getRP_IDCLINICA();
                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                        MED_NOMBRE_ORI = paciente.getRP_NOMBRE();
                        MED_APELLIDO_ORI = paciente.getRP_APELLIDO();
                        MED_CINRO_ORI = paciente.getRP_CINRO();
                        MED_RAZON_SOCIAL_ORI = paciente.getRP_RAZON_SOCIAL();
                        MED_RUC_ORI = paciente.getRP_RUC();
                        MED_TELEFONO_ORI = paciente.getRP_TELFMOVIL();
                        MED_DIRECCION_ORI = paciente.getRP_DIRECCION();
                        MED_EMAIL_ORI = paciente.getRP_EMAIL();
                        MED_IDUSUARIO_ORI = paciente.getSU_IDUSUARIO();
                        MED_USUARIO_ORI = paciente.getSU_USUARIO();
                        MED_CLAVE_ORI = paciente.getSU_CLAVE();
                        MED_ESTADO_ORI = paciente.getSU_ESTADO();
                        MED_IDCLINICA_ORI = paciente.getRP_IDCLINICA();
                    } // end while 
                    
                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
                    sesionDatosUsuario.setAttribute("ID_USUARIO_MEDICO", MED_IDUSUARIO);
                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CM_MED_NOMBRE") != null) {
                    MED_NOMBRE = (String) request.getAttribute("CM_MED_NOMBRE");
                }
                if ((String)request.getAttribute("CM_MED_APELLIDO") != null) {
                    MED_APELLIDO = (String) request.getAttribute("CM_MED_APELLIDO");
                }
                if ((String)request.getAttribute("CM_MED_CINRO") != null) {
                    MED_CINRO = (String) request.getAttribute("CM_MED_CINRO");
                }
                if ((String)request.getAttribute("CM_MED_RAZON_SOCIAL") != null) {
                    MED_RAZON_SOCIAL = (String) request.getAttribute("CM_MED_RAZON_SOCIAL");
                }
                if ((String)request.getAttribute("CM_MED_RUC") != null) {
                    MED_RUC = (String) request.getAttribute("CM_MED_RUC");
                }
                if ((String)request.getAttribute("CM_MED_TELEFONO") != null) {
                    MED_TELEFONO = (String) request.getAttribute("CM_MED_TELEFONO");
                }
                if ((String)request.getAttribute("CM_MED_DIRECCION") != null) {
                    MED_DIRECCION = (String) request.getAttribute("CM_MED_DIRECCION");
                }
                if ((String)request.getAttribute("CM_MED_EMAIL") != null) {
                    MED_EMAIL = (String) request.getAttribute("CM_MED_EMAIL");
                }
                if ((String)request.getAttribute("CM_MED_USUARIO") != null) {
                    MED_USUARIO = (String) request.getAttribute("CM_MED_USUARIO");
                }
                if ((String)request.getAttribute("CM_MED_CLAVE") != null) {
                    MED_CLAVE = (String) request.getAttribute("CM_MED_CLAVE");
                }
                if ((String)request.getAttribute("CM_MED_ESTADO") != null) {
                    MED_ESTADO = (String) request.getAttribute("CM_MED_ESTADO");
                }
                if ((String)request.getAttribute("CM_MED_FECHA_NAC") != null) {
                    MED_FECHA_NAC = (String) request.getAttribute("CM_MED_FECHA_NAC");
                }
                if ((String)request.getAttribute("CM_MED_SEXO") != null) {
                    MED_SEXO = (String) request.getAttribute("CM_MED_SEXO");
                }
                if ((String)request.getAttribute("CM_MED_IDCLINICA") != null) {
                    MED_IDCLINICA = (String) request.getAttribute("CM_MED_IDCLINICA");
                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR MEDICO, ENTONCES DE ACUERDO AL IDMEDICO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO MEDICO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                if (ID_MEDICO.isEmpty() || ID_MEDICO.equals("")) {
                    varTitulo = "MEDICO - NUEVO REGISTRO";
                } else {
                    varTitulo = "MEDICO - MODIFICAR REGISTRO";
                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CMSrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_MEDICO %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nro. Cédula:</div>
                                    <div class="pac_panel_3_ci">
                                        <input type="text" value="<%= MED_CINRO %>" id="tNC" name="tNC" class="form-control" />
<%--                                    </div>
                                    <div class="pac_panel_3">--%>
                                    <%
                                    if (ID_MEDICO.isEmpty() || ID_MEDICO.equals("")) {
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
                                        <input type="text" value="<%= MED_NOMBRE %>" id="tN" name="tN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Apellido/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_APELLIDO %>" id="tA" name="tA" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Razon Social:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_RAZON_SOCIAL %>" id="tRS" name="tRS" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">RUC:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_RUC %>" id="tR" name="tR" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Teléfono:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_TELEFONO %>" id="tT" name="tT" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Dirección:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_DIRECCION %>" id="tD" name="tD" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Email:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= MED_EMAIL %>" id="tE" name="tE" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Fecha de Nac.:</div>
                                    <div class="pac_panel_3">
                                        <input type="date" value="<%= MED_FECHA_NAC %>" id="tFN" name="tFN" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Sexo:</div>
                                    <div class="pac_panel_3">
                                        <select id="cSe" name="cSe" class="form-control">
                                            <%
                                            Map<String, String> listaSexoPac;
                                            listaSexoPac = metodosIniSes.cargarComboSexoPer(MED_SEXO);
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
                                            listaClinicas = metodosRefClinica.cargarComboClinica(MED_IDCLINICA);
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
                                    <a href="<%= urlMedico %>" class="btn btn-danger">VOLVER</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script>
            function cancelar() {
                document.getElementById("tN").value = "<%= MED_NOMBRE_ORI %>";
                document.getElementById("tA").value = "<%= MED_APELLIDO_ORI %>";
                document.getElementById("tNC").value = "<%= MED_CINRO_ORI %>";
                document.getElementById("tRS").value = "<%= MED_RAZON_SOCIAL_ORI %>";
                document.getElementById("tR").value = "<%= MED_RUC_ORI %>";
                document.getElementById("tT").value = "<%= MED_TELEFONO_ORI %>";
                document.getElementById("tD").value = "<%= MED_DIRECCION_ORI %>";
                document.getElementById("tE").value = "<%= MED_EMAIL_ORI %>";
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