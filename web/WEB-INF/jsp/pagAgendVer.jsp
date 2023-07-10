<%-- 
    Document   : pagAgendVer
    Created on : 03-feb-2022, 12:52:55
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
                String mensaje = (String) request.getAttribute("CA_MENSAJE"); // CONTROLLER AGENDAMIENTO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CA_TIPO_MENSAJE"); // CONTROLLER AGENDAMIENTO TIPO MENSAJE 
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
                <form action="CASrv" method="post" autocomplete="off">
                    <div>
                        <h4 class="mainTitle">AGENDAMIENTO - VER DATOS DE LOS AGENDAMIENTOS DEL MEDICO</h4>
                    </div>
                <%
                    System.out.println("."); System.out.println("."); System.out.println("."); System.out.println(".");
                    
                    String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                    System.out.println("_jsp___ID_AGENDAMIENTO:  "+ID_AGENDAMIENTO);
                    
                    List<BeanAgendamiento> listaDatos = new ArrayList<BeanAgendamiento>();
                    String AGEN_MOTIVO_CONSULTA="", AGEN_IDCLINICA="", CLI_DESC="", AGEN_IDMEDICO="", MED_NOM="", MED_APE="", MED_CINRO="", AGEN_IDESPECIALIDAD="", ESPE_DESC="", AGEN_ESTADO="A";
//                    String PH_IDCLINICA_ORI="", PH_IDMEDICO_ORI="", PH_DESDE_ORI="", PH_HASTA_ORI="",  PH_NOM_MEDICO_ORI="", PH_APE_MEDICO_ORI="", PH_CINRO_MEDICO_ORI="";
                    
                    // CONTROLO EL IDAGENDAMIENTO PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                    if (ID_AGENDAMIENTO.equals("0") || ID_AGENDAMIENTO.isEmpty() || ID_AGENDAMIENTO.equals("")) {
                        // ... 
                    } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                        listaDatos = metodosAgendamiento.traer_datos(ID_AGENDAMIENTO, sesionDatosUsuario);
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
                            AGEN_IDESPECIALIDAD = datos.getOA_IDESPECIALIDAD();
                                ESPE_DESC = metodosRefEspecialidad.traerDescEspecialidad(AGEN_IDESPECIALIDAD); // DESCRIPCION DE LA ESPECIALIDAD 
                            AGEN_ESTADO = datos.getOA_ESTADO();
//                            AGEN_MOTIVO_CONSULTA = datos.getOAD_MOTIVO_CONSULTA();
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
                        } // end while 
                    } // END ELSE 
                    
                    System.out.println("---------------_____JSP_____-------------------");
                    System.out.println("_      ___ID_CLINICA:    :"+AGEN_IDCLINICA);
                    System.out.println("_      _DESC_CLINICA:    :"+CLI_DESC);
                    System.out.println("_      ____ID_MEDICO:    :"+AGEN_IDMEDICO);
                    System.out.println("_      _NOMBR_MEDICO:    :"+MED_NOM);
                    System.out.println("_      _APELL_MEDICO:    :"+MED_APE);
                    System.out.println("_      _CINRO_MEDICO:    :"+MED_CINRO);
                    System.out.println("_      _ID_ESPECIALIDAD: :"+AGEN_IDESPECIALIDAD);
                    System.out.println("_      _MOTIVO_CONSULTA: :"+AGEN_MOTIVO_CONSULTA);
                    System.out.println("_      ____DESC_ESPE:    :"+ESPE_DESC);
                    System.out.println("_      _______ESTADO:    :"+AGEN_ESTADO);
                    System.out.println("-----------------------------------------------");
                %>
                    <div class="divTableData mt-1 content-over">
                        <div class="pac_panel">
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">CÓDIGO:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= ID_AGENDAMIENTO %>" name="tI" readonly="readonly" class="form-control" />
<%--                                </div>
                                <div class="pac_panel_3">--%>
                                    <%//<!--<input type="submit" value="Cerrar Agendamiento" name="accion" class="btn btn-outline-danger ml-2"/>-->%>
                                    <%
                                    String labelName="", labelMensajeModal="", labelTextAdvertencia="";
                                    // OBSERVACION: IF PARA EVITAR MOSTRAR EL BOTON PARA CERRAR EL AGENDAMIENTO CUANDO YA SE CERRO Y MOSTRARLE EL BOTON CUANDO LA CABECERA ESTA ACTIVA NOMAS 
                                    if (AGEN_ESTADO.equalsIgnoreCase("A") || AGEN_ESTADO.equalsIgnoreCase("Activo")) {
                                        // VARIABLES PARA CARGAR EL MENSAJE DE JAVASCRIPT 
                                        labelName = "Cerrar Agendamiento";
                                        labelMensajeModal = "Cerrar Agendamiento"; // TEXTO QUE LE VA A MOSTRAR EL MENSAJE 
                                        labelTextAdvertencia = "Esta acción ya no se podrá deshacer, Así que piénsalo bien.";  // TEXTO SECUNDARIO QUE APARECE ABAJO QUE LO USO PARA DAR UNA ADVERTENCIA AL USUARIO // OBS.: LO DEJO CARGADO Y EN CASO DE QUE NO QUIERA QUE ME MUESTRE EL MENSAJE DE ADVERTENCIA, EN EL IF DEL BOTON QUE QUIERA LO DEJO VACIO O LO CAMBIO 
                                        String varAccionBtn = "accion";
                                        
                                        System.out.println("___VAR_ACCION_BTN:  "+varAccionBtn);
                                        System.out.println("___LABEL_NAME:  "+labelName);
                                        System.out.println("___LABEL_MENS:  "+labelMensajeModal);
                                        System.out.println("___LABEL_ADVE:  "+labelTextAdvertencia);
                                    %>
                                    <input type="submit" value="<%= labelName %>" id="btnPreAux" class="btn btn-outline-danger ml-2" />
                                    <input type="submit" value="<%= labelName %>" name="<%= varAccionBtn %>" id="btnAuxiliar" class="btn btn-warning disNone" />
                                    <%
                                    } // end if 
                                    %>
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
                                    <input type="text" value="<%= MED_NOM+" "+MED_APE %>" id="tMN" name="tMN" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Nro. CI:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= MED_CINRO %>" id="tMC" name="tMC" readonly="readonly" class="form-control" />
                                </div>
                            </div>
                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Estado:</div>
                                <div class="pac_panel_3">
                                    <input type="text" value="<%= metodosIniSes.devolverTextEstadoAgend(AGEN_ESTADO) %>" id="cE" name="cE" readonly="readonly" class="form-control" />
                                    <input type="submit" name="accion" value="Ver Plan de Horario del Médico" class="ml-3 btn btn-outline-info" />
                                </div>
                            </div>
<!--                            <div class="pac_panel_1">
                                <div class="pac_panel_2">Motivo de la Consulta:</div>
                                <div class="pac_panel_3">
                                    <textarea id="TAMCA" name="TAMCA" rows="4" cols="70" readonly="readonly" class="form-control"><%= AGEN_MOTIVO_CONSULTA %></textarea>
                                </div>
                            </div>-->
                        </div>
                        
                        <div class="divTable ml-4">
                            <table role="table" class="tableAgenVer">
                                <thead role="rowgroup">
                                    <tr role="row">
                                        <td role="columnheader" class="AD_CI">#</td>
                                        <td role="columnheader" class="AD_CFA">FECHA AGEN.</td>
                                        <td role="columnheader" class="AD_CH">HORA</td>
                                        <td role="columnheader" class="AD_CP">PACIENTE</td>
                                        <td role="columnheader" class="AD_CNC">NRO. CI</td>
                                        <td role="columnheader" class="AD_CMC">MOTIVO DE LA CONSUTLA</td>
                                        <td role="columnheader" class="AD_CNC">ESTADO</td>
                                        <%
                                        // OBSERVACION: SI EL PERFIL DEL USUARIO ES ADMIN, FUNCIONARIO O SECRETARIO ENTONCES LE MUESTRO EL BOTON PARA QUE PUEDA VER LA ATENCION DEL PACIENTE EN CASO DE QUE TENGA PARA NO IR A BUSCAR DESDE LA PAGINA DE ATENCION LUEGO 
                                            if (metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil) || metodosPerfil.isPerfilFuncio(idPerfil)) {
                                        %>
                                        <td role="columnheader" class="AD_CH">Ver Atención</td>
                                        <%
                                            }
                                        %>
                                    </tr>
                                </thead>
                                <tbody role="rowgroup">
                                    <%
//                                            List<BeanAgendamiento> listaDatosDet = null;
//                                            listaDatosDet = ;
                                        Iterator<BeanAgendamiento> iterDatosDet = listaDatos.iterator();
                                        BeanAgendamiento datosBeanDet = null;
                                        
                                        int nro_item = 0;
                                        String FEC_AGEN_ANT = ""; // GUARDO LA PRIMERA FECHA DE AGENDAMIENTO Y CONTROLO EL DE LA SIGUIENTE LINEA Y SI ES IGUAL ENTONCES LE AUMENTO EL CONTEO AL ITEM PERO SI ES DIFERENTE ENTONCES REINICIO EL NRO DEL ITEM Y LE CAMBIO EL VALOR DE ESTA VARIABLE POR EL DE LA FECHA Y ASI SUCESIVAMENTE PARA PODER MOSTRAR UN NRO DE ITEM PARA CADA FECHA 
                                        while(iterDatosDet.hasNext()) {
                                            datosBeanDet = iterDatosDet.next();
                                            
                                            String ITEM_AGEN = datosBeanDet.getOAD_ITEM();
                                            System.out.println("_   _JSP____ITEM_AGENDAMIENTO:   :"+ITEM_AGEN);
                                            // CONDICIONAL QUE UTILIZO PARA PODER CONTROLAR SI ES QUE TIENE DETALLE O NO 
                                            if((ITEM_AGEN==null || ITEM_AGEN.isEmpty()) == false) {
                                                // explicacion de este if en la observacion que le deje a la variable 
                                                if (nro_item == 0) {
                                                    FEC_AGEN_ANT = datosBeanDet.getOAD_FECHA_AGEN();
                                                    nro_item = nro_item + 1;
                                                } else if((FEC_AGEN_ANT.equals(datosBeanDet.getOAD_FECHA_AGEN())) == true) {
                                                    nro_item = nro_item + 1;
                                                } else if((FEC_AGEN_ANT.equals(datosBeanDet.getOAD_FECHA_AGEN())) == false) {
                                                    nro_item = 1;
                                                    FEC_AGEN_ANT = datosBeanDet.getOAD_FECHA_AGEN();
                                                }
                                                
                                                // TRAER DATOS DEL MEDICO 
                                                BeanPersona datosBPersona = new BeanPersona();
                                                datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBeanDet.getOAD_IDPACIENTE());
                                    %>
                                    <tr role="row">
                                        <td role="cell" class="AD_CI">
                                            <%= nro_item %>
                                        </td>
                                        <td role="cell" class="AD_CFA">
                                            <%= datosBeanDet.getOAD_FECHA_AGEN()%>
                                        </td>
                                        <td role="cell" class="AD_CH">
                                            <%= datosBeanDet.getOAD_HORA()%>
                                        </td>
                                        <td role="cell" class="AD_CP">
                                            <%= datosBPersona.getRP_NOMBRE()+" "+datosBPersona.getRP_APELLIDO() %>
                                        </td>
                                        <td role="cell" class="AD_CNC">
                                            <%= datosBPersona.getRP_CINRO() %>
                                        </td>
                                        <td role="cell" class="AD_CMC">
                                            <%= datosBeanDet.getOAD_MOTIVO_CONSULTA()%>
                                        </td>
                                        <td role="cell" class="AD_CNC">
                                            <%= metodosIniSes.devolverTextEstadoAgend(datosBeanDet.getOAD_ESTADO())%>
                                        </td>
                                        <%
                                        // OBSERVACION: SI EL PERFIL DEL USUARIO ES ADMIN, FUNCIONARIO O SECRETARIO ENTONCES LE MUESTRO EL BOTON PARA QUE PUEDA VER LA ATENCION DEL PACIENTE EN CASO DE QUE TENGA PARA NO IR A BUSCAR DESDE LA PAGINA DE ATENCION LUEGO 
                                            if (metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil) || metodosPerfil.isPerfilFuncio(idPerfil)) {
                                                sesionDatosUsuario.setAttribute("CFAP_BTN_VOLVER_ATRAS", "VER_CAB_AGENDAMIENTO");
                                        %>
                                        <td role="cell" class="AD_CH">
                                            <%
                                            // SI EL IDATENCION NO ESTA CARGADO Y SE ENCUENTRA VACIO, ENTONCES NO LE MUESTRO EL BOTON PARA VER AGENDAMIENTO 
                                                if (datosBeanDet.getOAD_IDATENCION_PAC()==null || datosBeanDet.getOAD_IDATENCION_PAC().isEmpty() || datosBeanDet.getOAD_IDATENCION_PAC().equals("")) {
                                                    System.out.println("________NULL______IF____BTN___________");
                                            %>
                                            <%= "-" %>
                                            <%
                                                } else {
                                                    System.out.println("________no-NULL______ELSE____BTN___________");
                                            %>
                                            <form action="CASrv" method="post" autocomplete="off">
                                                <input type="text" name="tIAP" value="<%= datosBeanDet.getOAD_IDATENCION_PAC() %>" class="form-control"/>
                                                <input type="text" name="tIA" value="<%= datosBeanDet.getOA_IDAGENDAMIENTO() %>" class="form-control"/>
                                                <input type="text" name="tAID" value="<%= datosBeanDet.getOAD_ITEM() %>" class="form-control"/>
                                                <input type="text" name="tIP" value="<%= datosBeanDet.getOAD_IDPACIENTE() %>" class="form-control"/>
                                                <input type="submit" name="accion" value="Ver Atención" class="btn btn-light m-0" />
                                            </form>
                                            <%
                                                }
                                            %>
                                        </td>
                                        <%
                                            }
                                        %>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
                                </tbody>
                            </table>
                        </div>
                        <div class="pac_panel">
                            <div class="pac_panel_btns">
                                <%
                                /*
                                 * OBSERVACION: RECUPERO ESTA VARIABLE DE LA SESION Y DE ACUERDO AL DATO QUE ALMACENE REDIRECCIONARE A UNA PAGINA O A OTRA AL DARLE CLIC AL BOTON "VOLVER ATRAS" 
                                */
                                String urlVolverAtras = urlAgendamiento;
                                String VALUE_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("AGEND_BOTON_VOLVER_ATRAS");
                                System.out.println("_   _JSP___VALUE_BTN_VOLVER_ATRAS:  :"+VALUE_VOLVER_ATRAS);
                                if (VALUE_VOLVER_ATRAS == null || VALUE_VOLVER_ATRAS.equals("1")) { // AGENDAMIENTO 
                                    urlVolverAtras = urlAgendamiento;
                                } else if (VALUE_VOLVER_ATRAS.equals("1")) { // PANEL DE CONTROL - ANULAR AGENDAMIENTO 
                                    urlVolverAtras = urlAnularAgendamientos;
                                }
                                %>
                                <a href="<%= urlVolverAtras %>" class="btn btn-outline-danger">Volver Atras</a>
                            </div>
                        </div>
                    </div>
                </form>
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