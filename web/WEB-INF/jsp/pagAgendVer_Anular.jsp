<%-- 
    Document   : pagAgendVer_Anular
    Created on : 13-jun-2022, 11:12:03
    Author     : RYUU
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.agend.javaBean.BeanEspecialidad"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanAgendamiento"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamiento</title>
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
                String mensaje = (String) request.getAttribute("CPC_AA_MENSAJE"); // CONTROLLER PANEL CONTROL - ANULAR AGENDAMIENTO - MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CPC_AA_TIPO_MENSAJE"); // CONTROLLER PANEL CONTROL - ANULAR AGENDAMIENTO - TIPO MENSAJE 
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
                
                if (mensaje != null) { // SI ES DIFERENTE A NULL ENTONCES MOSTRARE LAS ETIQUETAS PARA QUE EL USUARIO PUEDA VER EL MENSAJE 
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
            <%
                }
            %>
                <%--<form action="CASrv" method="post" autocomplete="off">--%>
                    <div>
                        <h4 class="mainTitle">AGENDAMIENTO - VER DATOS DEL AGENDAMIENTO</h4>
                    </div>
                <%
                    System.out.println("."); System.out.println("."); System.out.println("."); System.out.println(".");
                    
//                    String ID_AGENDAMIENTO = (String) request.getAttribute("ID_AGENDAMIENTO");
                    String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    System.out.println("_jsp___ID_AGENDAMIENTO:  "+ID_AGENDAMIENTO);
//                    String ITEM_AGENDAMIENTO = (String) request.getAttribute("ITEM_AGENDAMIENTO");
                    String ITEM_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ITEM_AGENDAMIENTO");
                    System.out.println("_jsp___ITEM_AGENDAMIENTO:  "+ITEM_AGENDAMIENTO);
                    
                    List<BeanAgendamiento> listaDatos = new ArrayList<BeanAgendamiento>();
                    String AGEN_MOTIVO_CONSULTA="", AGEN_IDCLINICA="", CLI_DESC="", AGEN_IDMEDICO="", MED_NOM="", MED_APE="", MED_CINRO="", AGEN_IDESPECIALIDAD="", ESPE_DESC="", AGEN_ESTADO="", FECHA_HORA_AGEND="", AGEN_IDPACIENTE="", CINRO_NAME_PACIENTE="";
                    String NAME_MEDICO = "", AGEN_ESTADO_DET="";
                    
                    // CONTROLO EL IDAGENDAMIENTO PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                    if (ID_AGENDAMIENTO.equals("0") || ID_AGENDAMIENTO.isEmpty() || ID_AGENDAMIENTO.equals("")) {
                        // ... 
                    } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                        listaDatos = metodosAgendamiento.traer_datos_det(ID_AGENDAMIENTO, ITEM_AGENDAMIENTO, sesionDatosUsuario);
                        Iterator<BeanAgendamiento> iterDatos = listaDatos.iterator();
                        BeanAgendamiento datos = null;
                        
                        while(iterDatos.hasNext()) {
                            datos = iterDatos.next();
                            AGEN_IDCLINICA = datos.getOA_IDCLINICA();
                                BeanClinica datosCli = new BeanClinica();
                                datosCli = metodosRefClinica.datosBasicosClinica(datosCli, AGEN_IDCLINICA);
                                CLI_DESC = datosCli.getOC_DESC_CLINICA(); // DESCRIPCION DE LA CLINICA 
                            AGEN_IDMEDICO = datos.getOA_IDMEDICO();
                                BeanPersona datosMed = new BeanPersona();
                                datosMed = metodosPersona.datosBasicosPersona(datosMed, AGEN_IDMEDICO);
                                MED_NOM = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
                                MED_APE = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
                                MED_CINRO = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
                                // CONDICIONAL PARA PODER SABER DE QUE MANERA CARGAR LA VARIABLE CON LOS DATOS DEL MEDICO PARA MOSTRAR 
                                if (metodosPerfil.isPerfilPaciente(idPerfil)) { // SI FUERA PACIENTE EL PERFIL DEL USUARIO, ENTONCES LE MOSTRARIA EL NOMBRE Y APELLIDO NOMAS DEL MEDICO 
                                    NAME_MEDICO = MED_NOM+" "+MED_APE;
                                } else { // SI FUERA OTRO PERFIL (ADMIN O SECRE) LE MOSTRARIA EL NRO DE CI, EL NOMBRE Y APELLIDO DEL MEDICO 
                                    NAME_MEDICO = MED_CINRO+" - "+MED_NOM+" "+MED_APE;
                                }
                            AGEN_MOTIVO_CONSULTA = datos.getOAD_MOTIVO_CONSULTA();
                            AGEN_IDESPECIALIDAD = datos.getOA_IDESPECIALIDAD();
                                ESPE_DESC = metodosRefEspecialidad.traerDescEspecialidad(AGEN_IDESPECIALIDAD); // DESCRIPCION DE LA ESPECIALIDAD 
                            AGEN_ESTADO = datos.getOA_ESTADO();
                            AGEN_ESTADO_DET = datos.getOAD_ESTADO();
                            // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
//                            AGEN_IDCLINICA = datos.getOA_IDCLINICA();
//                                BeanClinica datosCli = new BeanClinica();
//                                datosCli = metodosRefClinica.datosBasicosClinica(datosCli, AGEN_IDCLINICA);
//                                CLI_DESC = datosCli.getOC_DESC_CLINICA(); // DESCRIPCION DE LA CLINICA 
//                            AGEN_IDMEDICO = datos.getOA_IDMEDICO();
//                                BeanPersona datosMed = new BeanPersona();
//                                datosMed = metodosPersona.datosBasicosPersona(datosMed, AGEN_IDMEDICO);
//                                MED_NOM = datosMed.getRP_NOMBRE(); // NOMBRE DEL MEDICO 
//                                MED_APE = datosMed.getRP_APELLIDO(); // APELLIDO DEL MEDICO 
//                                MED_CINRO = datosMed.getRP_CINRO();// CINRO DEL MEDICO 
//                            AGEN_IDESPECIALIDAD = datos.getOA_DESDE();
//                                ESPE_DESC = metodosRefEspecialidad.traerDescEspecialidad(AGEN_IDESPECIALIDAD); // DESCRIPCION DE LA ESPECIALIDAD 
//                            AGEN_ESTADO = datos.getOA_ESTADO();
                            FECHA_HORA_AGEND = datos.getOAD_FECHA_AGEN()+" a las "+datos.getOAD_HORA();
                            // DATOS DEL PACIENTE 
                            AGEN_IDPACIENTE = datos.getOAD_IDPACIENTE();
                            BeanPersona datosPac = new BeanPersona();
                            datosPac = metodosPersona.datosBasicosPersona(datosPac, AGEN_IDPACIENTE);
                            CINRO_NAME_PACIENTE = datosPac.getRP_CINRO()+" - "+datosPac.getRP_NOMBRE()+" "+datosPac.getRP_APELLIDO();
                        } // end while 
                    } // END ELSE 
                    
                    System.out.println("---------------_____JSP_____-------------------");
                    System.out.println("_      ___ID_CLINICA:    :"+AGEN_IDCLINICA);
                    System.out.println("_      _DESC_CLINICA:    :"+CLI_DESC);
                    System.out.println("_      ____ID_MEDICO:    :"+AGEN_IDMEDICO);
                    System.out.println("_      _NOMBR_MEDICO:    :"+MED_NOM);
                    System.out.println("_      _APELL_MEDICO:    :"+MED_APE);
                    System.out.println("_      _CINRO_MEDICO:    :"+MED_CINRO);
                    System.out.println("_      _MOTIVO_CONSULTA: :"+AGEN_MOTIVO_CONSULTA);
                    System.out.println("_      _ID_ESPECIALIDAD: :"+AGEN_IDESPECIALIDAD);
                    System.out.println("_      ____DESC_ESPE:    :"+ESPE_DESC);
                    System.out.println("_      _______ESTADO:    :"+AGEN_ESTADO);
                    System.out.println("_      ___ESTADO_DET:    :"+AGEN_ESTADO_DET);
                    System.out.println("_      _FECHA_HORA_AGEN: :"+FECHA_HORA_AGEND);
                    System.out.println("_      _____PACIENTE:    :"+CINRO_NAME_PACIENTE);
                    System.out.println("-----------------------------------------------");
                %>
                    <div class="divTableData mt-1 content-over">
                        <div class="pac_panel">
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">CÓDIGO:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= ID_AGENDAMIENTO %>" readonly="readonly" class="form-control" />
                                </div>
                            </div>

                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Clinica:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= CLI_DESC %>" id="tCD" name="tCD" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Especialidad:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= ESPE_DESC %>" id="tED" name="tED" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Médico:</div>
                                <div class="pac_panel_3">
                                    <input type="hidden" value="<%= AGEN_IDMEDICO %>" id="tIM" name="tIM" readonly="readonly" class="form-control disNone" />
                                    <input type="text" value="<%= NAME_MEDICO %>" id="tMN" name="tMN" readonly="readonly" class="form-control mr-2" />
                                </div>
                            <%--</div>--%>
<%--                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Nro. C.I.:</div>
                                <div class="pac_panel_3">--%>
                                    <%--<input type="text" value="<%= MED_CINRO %>" id="tMC" name="tMC" readonly="readonly" class="form-control" />--%>
                                <%--</div>--%>
                            </div>
<%--                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Estado:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= metodosIniSes.devolverTextEstado(AGEN_ESTADO) %>" id="cE" name="cE" readonly="readonly" class="form-control" />
                                    <input type="submit" name="accion" value="Ver Plan de Horario del Médico" class="ml-3 btn btn-outline-info" />
                                </div>
                            </div>--%>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Fecha y Hora de Agendamiento:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= FECHA_HORA_AGEND %>" id="tFHA" name="tFHA" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Paciente:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= CINRO_NAME_PACIENTE %>" id="tCNP" name="tCNP" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Estado del Agendamiento:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= metodosIniSes.devolverTextEstadoAgend(AGEN_ESTADO_DET) %>" id="tAE" name="tAE" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Motivo de la Consulta:</div>
                                <div class="pac_panel_3">
                                    <textarea id="TAMCA" name="TAMCA" rows="4" cols="70" readonly="readonly" class="form-control"><%= AGEN_MOTIVO_CONSULTA %></textarea>
                                </div>
                            </div>
                        </div>
                        
                        
                        <div class="pac_panel">
                            <div class="pac_panel_btns">
                            <%
                            // VARIABLES PARA CARGAR EL MENSAJE DE JAVASCRIPT 
                            String labelName = "";
                            String labelMensajeModal = ""; // TEXTO QUE LE VA A MOSTRAR EL MENSAJE 
                            String labelTextAdvertencia = "Esta acción ya no se podrá deshacer, Así que piénsalo bien.";  // TEXTO SECUNDARIO QUE APARECE ABAJO QUE LO USO PARA DAR UNA ADVERTENCIA AL USUARIO // OBS.: LO DEJO CARGADO Y EN CASO DE QUE NO QUIERA QUE ME MUESTRE EL MENSAJE DE ADVERTENCIA, EN EL IF DEL BOTON QUE QUIERA LO DEJO VACIO O LO CAMBIO 
                            
                            /*
                             * OBSERVACION: 
                               VARIABLE QUE SOLO UTILIZARE PARA CARGAR DESDE OTRA PAGINA QUE NO SEA LA DE ANULAR AGENDAMIENTO Y ASI NO MOSTRAR EL BOTON PARA ANULAR, YA QUE EN CUENTA CLIENTE UTILIZO ESTA PAGINA LA UTILIZO PARA MOSTRAR LOS DATOS DEL AGENDAMIENTO NOMAS DE FORMA INDIVIDUAL YA QUE EN LA OTRA PAGINA DE AGENDAMIENTO VER MUESTRA TODOS LOS PACIENTES DE UN AGENDAMIENTO 
                            */
                            if (request.getAttribute("AGEN_ANULAR_PERMITIR") == null || String.valueOf(request.getAttribute("AGEN_ANULAR_PERMITIR")).equals("0")) {
                            %>
                            <%
                            /*
                            * OBSERVACION: 
                                VALIDACIONES PARA PODER CARGAR LOS BOTONES DE: "ANULAR", "CANCELAR", "LIQUIDAR", "EXPIRAR"; 
                                COLOQUE ESTOS BOTONES ACA PARA PODER ACTIVAR EL MENSAJE DE CONSULTA YA QUE EN SUS PAGINAS NO PODIA MOSTRAR EL MENSAJE EN TODOS LOS CASOS SOLO LO MOSTRABA CUANDO FILTRABA POR UN EMPEÑO Y NO SE ACTIVABA EL MENSAJE CON LA LISTA COMPLETA 
                            */
                                System.out.println(".");System.out.println(".");
                                System.out.println(".");
                                // VARIABLES BANDERA 
                                int varMostrarBtns = 0; // VARIABLE QUE UTILIZARE PARA SABER SI ALGUNA VARIABLE DE LA SESION ACTIVA ALGUN BOTON Y ASI MOSTRARLE EL FORM 
                                
                                // VARIABLES PARA COLOCAR LOS NOMBRES DE LOS EVENTOS DEL CONTROLADOR QUE CORRESPONDE 
                                String varAccionBtn = "";
                                
                                /*
                                OBSERVACION: LA IDEA ES QUE CADA BOTON TENGA UNA VARIABLE EN LA SESION Y ACTIVARLOS DESDE LOS CONTROLADORES Y PARA SABER DE QUE PAGINA VIENE O LA ACTIVO, ME FIJARE EN SU VALOR COMO EL BOTON DE "VOLVER ATRAS" 
                                */
                                // VARIABLES DE LA SESION 
                                String btnAnular = (String) sesionDatosUsuario.getAttribute("AGEND_BTN_ANULAR");
                //                String btnAnular = (String) request.getAttribute("AGEND_BTN_ANULAR");
                                System.out.println("__JSP__BTN_ANULAR:      "+btnAnular);
                                
//                                // VARIABLES PARA CARGAR EL MENSAJE DE JAVASCRIPT 
//                                String labelName = "";
//                                String labelMensajeModal = ""; // TEXTO QUE LE VA A MOSTRAR EL MENSAJE 
//                                String labelTextAdvertencia = "Esta acción ya no se podrá deshacer, Así que piénsalo bien.";  // TEXTO SECUNDARIO QUE APARECE ABAJO QUE LO USO PARA DAR UNA ADVERTENCIA AL USUARIO // OBS.: LO DEJO CARGADO Y EN CASO DE QUE NO QUIERA QUE ME MUESTRE EL MENSAJE DE ADVERTENCIA, EN EL IF DEL BOTON QUE QUIERA LO DEJO VACIO O LO CAMBIO 
//                                
                                // VALIDACIONES PARA ACTIVAR LOS BOTONES 
                                // BOTON DE ANULAR 
                                if(btnAnular == null || btnAnular.isEmpty()) {
                                    //
                                } else if (btnAnular.equalsIgnoreCase("2")) { // PAG:   PANEL DE CONTROL / ANULAR FACTURA 
                                    varMostrarBtns = 1;
                                    varAccionBtn = "accionAA";
                                    labelName = "Anular Agendamiento";
                                    labelMensajeModal = "¿Esta seguro que desea anular el agendamiento?";
                                }
                                
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println("___ID_AGENDAMIENTO:      :"+ID_AGENDAMIENTO);
                                System.out.println("___ITEM_AGENDAMIENTO:    :"+ITEM_AGENDAMIENTO);
                                System.out.println("___ID_PACIENTE_AGEND:    :"+AGEN_IDPACIENTE);
                                System.out.println("___VAR_BANDERA_BOTONES:  "+varMostrarBtns);
                                System.out.println("___VAR_ACCION_BTN:  "+varAccionBtn);
                                System.out.println("___LABEL_NAME:  "+labelName);
                                System.out.println("___LABEL_MENS:  "+labelMensajeModal);
                                System.out.println("___LABEL_ADVE:  "+labelTextAdvertencia);
                                System.out.println(".");
                                System.out.println(".");
                                
                                if (varMostrarBtns > 0) { // CONTROLO LA VARIABLE QUE UTILIZO COMO BANDERA 
                            %>
                                <form action="CPCSrv" method="post" class="w-auto h-auto">
                                    <input type="hidden" value="<%= ID_AGENDAMIENTO %>" name="tI" class="form-control disNone" />
                                    <input type="hidden" value="<%= ITEM_AGENDAMIENTO %>" name="tIA" class="form-control disNone" />
                                    <input type="hidden" value="<%= AGEN_IDPACIENTE %>" name="tIP" class="form-control disNone" />
                                    <input type="submit" value="<%= labelName %>" id="btnPreAux" class="btn btn-danger" />
                                    <input type="submit" value="<%= labelName %>" name="<%= varAccionBtn %>" id="btnAuxiliar" class="btn btn-warning disNone" />
                                </form>
                            <%
                                }
                                System.out.println(".");
                                System.out.println(".");System.out.println(".");
                            %>
                            <%
                            }
                            %>
                            <%
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".           ----------------------_JSP_---------------------");
                                System.out.println(".");
                                System.out.println(".");
                                String IDPACIENTECTACLIE = "";
                                int varOtroBtn = 0; // VARIABLE QUE UTILIZO PARA EN CASO DE QUE QUIERA VOLVER ATRAS Y NO SEA A UNA PAGINA PRINCIPAL SINO A UNA PAGINA SECUNDARIA Y NO PUEDO VOLVER ATRAS A TRAVES DE UNA URL SINO DE UN EVENTO 
                            /*
                             * OBSERVACION: RECUPERO ESTA VARIABLE DE LA SESION Y DE ACUERDO AL DATO QUE ALMACENE REDIRECCIONARE A UNA PAGINA O A OTRA AL DARLE CLIC AL BOTON "VOLVER ATRAS" 
                            */
                                String urlVolverAtras = urlAgendamiento;
                                String VALUE_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("AGEND_BOTON_VOLVER_ATRAS");
                                System.out.println("_   _JSP___VALUE_BTN_VOLVER_ATRAS:  :"+VALUE_VOLVER_ATRAS);
                                if (VALUE_VOLVER_ATRAS == null || VALUE_VOLVER_ATRAS.equals("0")) { // AGENDAMIENTO 
                                    urlVolverAtras = urlAgendamiento;
                                } else if (VALUE_VOLVER_ATRAS.equals("1")) { // PANEL DE CONTROL - ANULAR AGENDAMIENTO 
                                    urlVolverAtras = urlAnularAgendamientos;
                                } else if (VALUE_VOLVER_ATRAS.equals("2")) {
                                    varOtroBtn = 1;
                                    IDPACIENTECTACLIE = (String) sesionDatosUsuario.getAttribute("AGEND_IDPACIENTE_CTAVER");
                                    System.out.println("_   _JSP__CTAPAC__ID_PACIENTE:   :"+IDPACIENTECTACLIE);
                                }
                                System.out.println("_   _JSP___VAR_OTRO_BTN:    :"+varOtroBtn);
                                // CONTROLO SI LA VARIABLE MANTIENE SU VALOR INICIAL O SI AL CONTROLAR 
                                if (varOtroBtn == 0) {
                            %>
                                <a href="<%= urlVolverAtras %>" class="btn btn-outline-danger">Volver Atras</a>
                            <%
                                } else if (varOtroBtn == 1) {
                            %>
                            <form action="CPCSrv" method="post" class="w-auto h-auto">
                                <input type="hidden" value="<%= IDPACIENTECTACLIE %>" name="tIC" id="tIC" class="form-control" />
                                <button type="submit" name="accionAA" value="VOLVER_ATRAS_CTAPAC" class="btn btn-outline-danger">Volver Atras</button>
                            </form>
                            <%
                                }
                            %>
                            </div>
                        </div>
                    </div>
                <%--</form>--%>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script>
            <%
            //  CODIGO QUE UTILIZO PARA PODER INVOCAR AL MENSAJE PARA CONSULTARLE SI ESTA SEGURO DE QUE DESEA HACER LA ACCION 
            %>
    <%//            function btnMensaje(){
    //                    event.preventDefault();
    //                    Swal.fire({
    //                        title: "¿Esta seguro que desea anular el empeño?",
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
    //            }%>
                function btnMensaje(){
                        event.preventDefault();
                        Swal.fire({
                            title: "<%= labelMensajeModal %>",
                            text: "<%= labelTextAdvertencia %>",
                            type: "question",
                            showCancelButton: true,
                            confirmButtonColor: '#3085d6',
                            cancelButtonColor: '#d33',
                            confirmButtonText: 'Si, estoy seguro',
                            cancelButtonText: "Cancelar"
                        }).then((result) => {
                            if (result.value) {
                                console.log('prueba exitosa - pre anular');
                                document.getElementById('btnAuxiliar').click();
                            }
                            return false;
                        });
                }
                document.getElementById('btnPreAux').addEventListener('click', btnMensaje);
            <%
            // ----------------------------------------------------------------------------------------------
            %>
        </script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
    </body>
</html>