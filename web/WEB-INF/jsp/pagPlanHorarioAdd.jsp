<%-- 
    Document   : pagPlanHorarioAdd
    Created on : 07-ene-2022, 13:12:13
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanPlanHorario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Plan de Horarios</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalClie.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
            <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CPH_MENSAJE"); // CONTROLLER PLAN HORARIO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CPH_TIPO_MENSAJE"); // CONTROLLER PLAN HORARIO TIPO MENSAJE 
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
                * OBSERVACION: PARA AÑADIR UN PLAN DE HORARIO Y EDITAR UN PLAN DE HORARIO 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_PLAN_HORARIO, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_PLAN_HORARIO = (String) sesionDatosUsuario.getAttribute("ID_PLAN_HORARIO");
                System.out.println("_jsp___ID_PLAN_HORARIO:  "+ID_PLAN_HORARIO);
                
                List<BeanPlanHorario> listaDatos;
                String PH_IDCLINICA="", PH_DESC_CLINICA="", PH_IDMEDICO="", PH_DESDE="", PH_HASTA="", PH_TURNO="", PH_DIA="", PH_NOM_MEDICO="", PH_APE_MEDICO="", PH_CINRO_MEDICO="", PH_ESTADO="A";
                String PH_IDCLINICA_ORI="", PH_IDMEDICO_ORI="", PH_DESDE_ORI="", PH_HASTA_ORI="",  PH_NOM_MEDICO_ORI="", PH_APE_MEDICO_ORI="", PH_CINRO_MEDICO_ORI="";
                
                // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                if (ID_PLAN_HORARIO.equals("0") || ID_PLAN_HORARIO.isEmpty() || ID_PLAN_HORARIO.equals("")) {
                    // ... 
                } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                    listaDatos = metodosPlanHorario.traer_datos(ID_PLAN_HORARIO, sesionDatosUsuario);
                    Iterator<BeanPlanHorario> iterPlanHor = listaDatos.iterator();
                    BeanPlanHorario plan_horario = null;
                    
                    while(iterPlanHor.hasNext()) {
                        plan_horario = iterPlanHor.next();
                        PH_IDCLINICA = plan_horario.getOPH_IDCLINICA();
                        PH_IDMEDICO = plan_horario.getOPH_IDMEDICO();
                            BeanPersona datosMed = new BeanPersona();
                            datosMed = metodosPersona.datosBasicosPersona(datosMed, PH_IDMEDICO);
                        PH_NOM_MEDICO = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
                        PH_APE_MEDICO = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
                        PH_CINRO_MEDICO = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
                        PH_DESDE = plan_horario.getOPHD_DESDE();
                        PH_HASTA = plan_horario.getOPHD_HASTA();
                        PH_TURNO = plan_horario.getOPHD_TURNO();
                        PH_DIA = plan_horario.getOPHD_DIA();
                        PH_ESTADO = plan_horario.getOPH_ESTADO();
                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                        PH_IDCLINICA_ORI = plan_horario.getOPH_IDCLINICA();
                        PH_IDMEDICO_ORI = plan_horario.getOPH_IDMEDICO();
                        PH_NOM_MEDICO_ORI = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
                        PH_APE_MEDICO_ORI = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
                        PH_CINRO_MEDICO_ORI = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
                        PH_DESDE_ORI = plan_horario.getOPHD_DESDE();
                        PH_HASTA_ORI = plan_horario.getOPHD_HASTA();
                    } // end while 
                    
//                    // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
//                    sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
                } // END ELSE 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)sesionDatosUsuario.getAttribute("CPH_IDCLINICA") != null) {
                    PH_IDCLINICA = (String) sesionDatosUsuario.getAttribute("CPH_IDCLINICA");
                } else if ((String)request.getAttribute("CPH_IDCLINICA") != null) {
                    PH_IDCLINICA = (String) request.getAttribute("CPH_IDCLINICA");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_IDMEDICO") != null) {
                    PH_IDMEDICO = (String) sesionDatosUsuario.getAttribute("CPH_IDMEDICO");
                } else if ((String)request.getAttribute("CPH_IDMEDICO") != null) {
                    PH_IDMEDICO = (String) request.getAttribute("CPH_IDMEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO") != null) {
                    PH_NOM_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_NOM_MEDICO");
                } else if((String)request.getAttribute("CPH_NOM_MEDICO") != null) {
                    PH_NOM_MEDICO = (String) request.getAttribute("CPH_NOM_MEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_APE_MEDICO") != null) {
                    PH_APE_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_APE_MEDICO");
                } else if ((String)request.getAttribute("CPH_APE_MEDICO") != null) {
                    PH_APE_MEDICO = (String) request.getAttribute("CPH_APE_MEDICO");
                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO") != null) {
                    PH_CINRO_MEDICO = (String) sesionDatosUsuario.getAttribute("CPH_CINRO_MEDICO");
                } else if ((String)request.getAttribute("CPH_CINRO_MEDICO") != null) {
                    PH_CINRO_MEDICO = (String) request.getAttribute("CPH_CINRO_MEDICO");
                }
                
//                if ((String)request.getAttribute("CPH_DESDE") != null) {
//                    PH_DESDE = (String) request.getAttribute("CPH_DESDE");
//                }
//                if ((String)request.getAttribute("CPH_HASTA") != null) {
//                    PH_HASTA = (String) request.getAttribute("CPH_HASTA");
//                }
//                if ((String)request.getAttribute("CPH_TURNO") != null) {
//                    PH_TURNO = (String) request.getAttribute("CPH_TURNO");
//                }
//                if ((String)request.getAttribute("CPH_DIA") != null) {
//                    PH_DIA = (String) request.getAttribute("CPH_DIA");
//                }
                
                if ((String)sesionDatosUsuario.getAttribute("CPH_ESTADO") != null) {
                    PH_ESTADO = (String) sesionDatosUsuario.getAttribute("CPH_ESTADO");
                } else if ((String)request.getAttribute("CPH_ESTADO") != null) {
                    PH_ESTADO = (String) request.getAttribute("CPH_ESTADO");
                }
                
                System.out.println("---------------_____JSP_____-------------------");
                System.out.println("_       ___ID_CLINICA:    :"+PH_IDCLINICA);
                System.out.println("_       ____ID_MEDICO:    :"+PH_IDMEDICO);
                System.out.println("_       _NOMBR_MEDICO:    :"+PH_NOM_MEDICO);
                System.out.println("_       _APELL_MEDICO:    :"+PH_APE_MEDICO);
                System.out.println("_       _CINRO_MEDICO:    :"+PH_CINRO_MEDICO);
                System.out.println("_       _______ESTADO:    :"+PH_ESTADO);
                System.out.println("-----------------------------------------------");
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR CLIENTES, ENTONCES DE ACUERDO AL IDCLIENTE VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO CLIENTE O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
//                if (ID_PLAN_HORARIO.isEmpty() || ID_PLAN_HORARIO.equals("")) {
                    varTitulo = "PLAN HORARIO - NUEVO REGISTRO";
//                } else {
//                    varTitulo = "PLAN HORARIO - MODIFICAR REGISTRO";
//                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CPHSrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_PLAN_HORARIO %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nombre/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="hidden" value="<%= PH_IDMEDICO %>" id="tIM" name="tIM" readonly="readonly" class="form-control disNone" />
                                        <input type="text" value="<%= PH_NOM_MEDICO %>" id="tNM" name="tNM" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Apellido/s:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PH_APE_MEDICO %>" id="tAM" name="tAM" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Nro. CI:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= PH_CINRO_MEDICO %>" id="tCM" name="tCM" readonly="readonly" class="form-control" />
                                            <%
                                            // VARIABLE QUE USO PARA MOSTRAR O NO EL BOTON PARA LLEVAR AL DETALLE DIRECTAMENTE 
                                            /*
                                            __OBSERVACION: ESTE BOTON SOLO LO MOSTRARE CUANDO HAYA ERRORES EN LA CABECERA AL MOMENTO DE ESTAR CONFIRMANDO EL COBRO, 
                                                Y AL REDIRECCIONAR A ESTE JSP PARA VOLVER A CONFIRMAR EL COBRO TENDRIA QUE VOLVER AL DETALLE PERO PARA ESO TENDRIA QUE VOLVER 
                                                A CARGAR TODOS LOS DATOS DEL DETALLE QUE CARGO, Y PARA EVITAR ESE PROCESO LARGO ENTONCES LE COLOCO ESTE BOTON QUE MANTENDRIA TODOS 
                                                LOS DATOS DEL DETALLE Y ASI LE PERMITIRIA COBRAR SIN VOLVER A PASAR POR CUALQUIERA DE LOS DOS BOTONES ("Cargar Cuenta Cliente" Y "Cargar Producto") PARA LLEGAR AL DETALLE.
                                            */
//                                            String permitir_detalle = (String) sesionDatosUsuario.getAttribute("CF_VALI_MOSTRAR_BTN_DETALLE");
//                                            System.out.println("____JSP___PREMITIR_VOLVER_AL_DETALLE:    :"+permitir_detalle);
//                                            // SI EL BOTON PARA MOSTRAR EL DETALLE SE MUESTRA ENTONCES EL BOTON PARA CAMBIAR AL CLIENTE NO DEBERIA DE MOSTRARLE PARA EVITAR QUE EL USUARIO CAMBIE AL CLIENTE Y LUEGO VUELVA AL DETALLE 
//                                            if (permitir_detalle == null || permitir_detalle.equals("0")) {
                                            if ((metodosPerfil.isPerfilAdmin(idPerfil)==true) || (metodosPerfil.isPerfilFuncio(idPerfil)==true) || (metodosPerfil.isPerfilSecre(idPerfil)==true)) { // SOLO A LOS PERFILES DE ADMINISTRADOR, FUNCIONARIO Y SECRETARIO LE PERMITIRE MOSTRAR EL BOTON PARA MOSTRAR EL MODAL PARA ELEGIR AL MEDICO, YA QUE SI ES UN MEDICO EL QUE ENTRA, SOLO DEBERIA DE PODER ELEGIR SUS DATOS Y NO CARGAR LOS DE OTRO MEDICO 
                                                if ((ID_PLAN_HORARIO == null) || (ID_PLAN_HORARIO.equals(""))) {
                                            %>
                                            <input type="checkbox" id="btn-cli" />
                                            <label for="btn-cli" class="btn btn-info btnCaCli ml-3" style="width: 300px;">Cargar Medico</label>
                                            <%
                                            /*
                                                VENTANA EMERGENTE PARA CARGAR EL CLIENTE ---------------
                                            */
                                            %>
                                            <form method="post" action="CPHSrv">
                                                <div class="modalCl">
                                                    <div class="modalPanelCl">
                                                        <div class="panelHeader">
                                                            <div class="header01">Cargar Medico</div>
                                                            <div class="header02"><label for="btn-cli" class="btnTi">Cerrar</label></div>
                                                        </div>

                                                        <div class="panelBodyCl">
                                                            <div class="boxBody">
                                                                <p class="boxLabel">Medicos:</p>
                                                            </div>
                                                            <div class="boxBody boxBodyMiddle">
                                                                <select name="cbxAddNewCli" class="form-control">
                                                                <%
                                                                Map<String, String> listaClientes1;
                                                                String idmedico="";
                                                                listaClientes1 = metodosMedico.cargarComboMedicos(idmedico, sesionDatosUsuario); // LE PASO VACIO COMO PARAMETRO PARA QUE ME MUESTRE EL CLIENTE OCASIONAL 
                                                                Set setLisCli1 = listaClientes1.entrySet();
                                                                Iterator iterLisCli1 = setLisCli1.iterator();
                                                                
                                                                while(iterLisCli1.hasNext()) {
                                                                    Map.Entry mapaListCli1 = (Map.Entry) iterLisCli1.next();
                                                                %>
                                                                    <option value="<%= mapaListCli1.getKey() %>"><%= mapaListCli1.getValue() %></option>
                                                                <%
                                                                }
                                                                %>
                                                                </select>
                                                            </div>
                                                            <div class="boxBody">
                                                                <input type="submit" value="Cargar Medico" name="accion" class="btn btn-info" />
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                            <%
                                            // -----------------     END VENTANA EMERGENTE    -------------------------
                                            %>
                                            <%
                                                } // end if ctrl_btn_volver_detalle 
                                            } // end if perfil admin-funcio-secre 
                                            %>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Clinica:</div>
                                    <div class="pac_panel_3">
                                        <%
                                        // OBS.: VALIDACION PARA EVITAR QUE EDITE Y SE LE TOME EN CUENTA A LA HORA DE CAMBIAR LA CLINICA 
                                        
                                        %>
                                        <select class="form-control" id="cCli" name="cCli">
                                            <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosRefClinica.cargarComboClinica(PH_IDCLINICA);
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
                                    <div class="pac_panel_2">Estado:</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cE" name="cE">
                                            <%
                                            Map<String, String> listaEstado;
                                            listaEstado = metodosIniSes.cargarComboEstado(PH_ESTADO);
                                            Set setCbxEstado = listaEstado.entrySet();
                                            Iterator iCbxEstado = setCbxEstado.iterator();

                                            while(iCbxEstado.hasNext()) {
                                                Map.Entry mapCbxEstado = (Map.Entry) iCbxEstado.next();
                                            %>
                                            <option value="<%= mapCbxEstado.getKey() %>"><%= mapCbxEstado.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_btns mt-5">
                                    <input type="submit" name="accion" value="Cargar Detalle" class="btn btn-warning" />
                                    <a href="javascript:cancelar()" class="btn btn-primary">LIMPIAR</a>
                                    <a href="<%= urlPlanHorario %>" class="btn btn-danger">CANCELAR</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script>
            function cancelar() {
                document.getElementById("tIM").value = "<%= PH_IDMEDICO_ORI %>";
                document.getElementById("tNM").value = "<%= PH_NOM_MEDICO_ORI %>";
                document.getElementById("tAM").value = "<%= PH_APE_MEDICO_ORI %>";
                document.getElementById("tCM").value = "<%= PH_CINRO_MEDICO_ORI %>";
                document.getElementById("cCli").value = "1";
                document.getElementById("cE").value = "A";
            }
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>