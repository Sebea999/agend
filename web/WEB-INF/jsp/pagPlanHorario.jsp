<%-- 
    Document   : pagPlanHorario
    Created on : 07-ene-2022, 12:52:33
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.javaBean.BeanPlanHorario"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
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
                
                if (mensaje != null) { // SI ES DIFERENTE A NULL ENTONCES MOSTRARE LAS ETIQUETAS PARA QUE EL USUARIO PUEDA VER EL MENSAJE 
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
            <%
                }
            %>
                <form action="CPHSrv" method="post" autocomplete="off">
                    <div>
                        <h4 class="mainTitle">PLAN HORARIO - GESTION DE HORARIOS</h4>
                    </div>
                    <div>
                        <input type="submit" name="accion" value="Add Nuevo Plan" class="btn btn-outline-danger mb-2 btnAddCli" />
                    </div>
                    <div class="mainFilter">
                        <%
                        // RECUPERAMOS LOS DATOS DEL FILTRO EN CASO DE QUE SE HAGA PARA VOLVER A MOSTRAR Y SI NO, ENTONCES PASARIA DATOS VACIOS 
                        String cbxMostrar = (String) request.getAttribute("CPH_CBX_MOSTRAR");
                        if (cbxMostrar == null || cbxMostrar.isEmpty()) {
                            cbxMostrar = "";
                        }
                        String txtFiltro = (String) request.getAttribute("CPH_TXT_BUSCAR");
                        if (txtFiltro == null || txtFiltro.isEmpty()) {
                            txtFiltro = "";
                        }
                        String txtFiltroCliId = (String) request.getAttribute("CPH_TXT_FIL_ID_CLI");
                        if (txtFiltroCliId == null || txtFiltroCliId.isEmpty()) {
                            txtFiltroCliId = "";
                        }
                        String txtFiltroMedId = (String) request.getAttribute("CPH_TXT_FIL_ID_MED");
                        if (txtFiltroMedId == null || txtFiltroMedId.isEmpty()) {
                            txtFiltroMedId = "";
                        }
                        %>
                        <div class="mainFilterBox">
                            <div>
                                <p class="mainLabelSearch">Mostrar:</p>
                                <select name="cM" class="form-control">
                                <%
                                // Combo Filter Limit Registro
                                Map<String, String> cbxFilterLimit = new LinkedHashMap();
                                cbxFilterLimit = metodosIniSes.cargarFilLimitReg(cbxFilterLimit, cbxMostrar); 
                                Set setListFilLim = cbxFilterLimit.entrySet();
                                Iterator iListFilLim = setListFilLim.iterator();

                                while(iListFilLim.hasNext()) {
                                    Map.Entry limitMap = (Map.Entry) iListFilLim.next();
                                %>
                                    <option value="<%= limitMap.getKey() %>"><%= limitMap.getValue() %></option>
                                <%
                                }
                                %>
                                </select>
                                <p>registros</p>
                            </div>
                        </div>
                    <%
                        // VALIDACION PARA HABILITARLE EL FILTRO DE CLINICA A LOS PERFILES DE SECRETARIO Y ADMINISTRADOR 
                        if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true) {
                    %>
                        <div class="mainFilterBox">
                            <div>
                                <p class="mainLabelSearch">Clinica:</p>
                                <%
                                    String CHECK_CLINICA = "checked=\"checked\" ";
                                /*
                                * OBSERVACION_DEL_PORQUE_HAY_DOS_VARIABLES_Y_NO_UNA_SOLA_CON_EL_MISMO_NOMBRE: 
                                    TENGO DOS VARIABLES PORQUE UNA LA TENGO EN EL MAV Y OTRA EN EL METODO DO_POST DEL CONTROLADOR, NECESITO LA PRIMERA LA QUE SE ENCUENTRA EN EL METODO MAV DEL REQUEST_MAPPING 
                                    PARA QUE AL ABRIR EL FORMULARIO ME MUESTRE EL CHECK MARCADO Y NO DESMARCADO, Y LUEGO NECESITO EL OTRO PARA IR GUIANDO AL CHECK EN CADA ACCION O EVENTO QUE SE REALICE, Y NO PUEDO TENER 
                                    UNO SOLO CON EL MISMO NOMBRE PORQUE NO ME HACE CASO, EL JSP SOLO HACE CASO AL "ON" QUE LE CARGO EN EL MAV Y LUEGO CUANDO MODIFICO CON "OFF" EN ALGUN EVENTO DEL DO_POST, EL JSP YA NO ME HACE CASO
                                    Y ME DEJA EL VALOR CON "ON", ENTONCES POR ESO CREE LA SEGUNDA VARIABLE QUE AL INICIAR ESTARA NULO Y AHI HARE CASO A LA PRIMERA VARIABLE, PERO LUEGO CUANDO SE REALICE ALGUN EVENTO EN LA PAGINA SE CARGARA 
                                    Y AHI TOMARE SU VALOR YA QUE ESTE PUEDE IR CAMBIANDO ENTRE "ON" Y "OFF" O NULO, PERO EL PRIMERO SIEMPRE SE MANTIENE EN "ON" 
                                */
                                    String CHECK_FILTER_CLINICA = (String) request.getAttribute("CPH_CHECK_FILTRAR_CLI"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                    String CHECK_FILTER_CLINICA01 = (String) request.getAttribute("CPH_CHECK_FILTRAR_CLI_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
                                    if (CHECK_FILTER_CLINICA01 == null) {
                                        if (CHECK_FILTER_CLINICA == null || CHECK_FILTER_CLINICA.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_CLINICA = "";
                                        } else if (CHECK_FILTER_CLINICA.equalsIgnoreCase("ON")) {
                                            CHECK_CLINICA = "checked=\"checked\" ";
                                        }
                                    } else {
                                        if (CHECK_FILTER_CLINICA01 == null || CHECK_FILTER_CLINICA01.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_CLINICA = "";
                                        } else if (CHECK_FILTER_CLINICA01.equalsIgnoreCase("ON")) {
                                            CHECK_CLINICA = "checked=\"checked\" ";
                                        }
                                    }
                                %>
                                <input type="checkbox" <%= CHECK_CLINICA %>id="check_clinica" name="check_clinica" />
                                <div class="comboclinica">
                                    <select name="cbxAddNewClinica" class="cbxClinica form-control">
                                    <%
                                        Map<String, String> listaClinica;
                                        listaClinica = metodosRefClinica.cargarComboClinica(txtFiltroCliId);
                                        Set setLisCli = listaClinica.entrySet();
                                        Iterator iterLisCli = setLisCli.iterator();
                                        
                                        while(iterLisCli.hasNext()) {
                                            Map.Entry mapaListCli = (Map.Entry) iterLisCli.next();
                                    %>
                                        <option value="<%= mapaListCli.getKey() %>"><%= mapaListCli.getValue() %></option>
                                    <%
                                        }
                                    %>
                                    </select>
                                </div>
                                <label for="check_clinica" class="btn btn-light">TODOS</label>
                            </div>
                        </div>
                    <%
                        }
                    %>
                    <%
                        // VALIDACION PARA HABILITARLE EL FILTRO DE MEDICO A LOS PERFILES DE SECRETARIO Y ADMINISTRADOR 
                        if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true) {
                    %>
                        <div class="mainFilterBox">
                            <div>
                                <p class="mainLabelSearch">Medico:</p>
                                <%
                                    String CHECK_MEDICO = "checked=\"checked\" ";
                                /*
                                * OBSERVACION_DEL_PORQUE_HAY_DOS_VARIABLES_Y_NO_UNA_SOLA_CON_EL_MISMO_NOMBRE: 
                                    TENGO DOS VARIABLES PORQUE UNA LA TENGO EN EL MAV Y OTRA EN EL METODO DO_POST DEL CONTROLADOR, NECESITO LA PRIMERA LA QUE SE ENCUENTRA EN EL METODO MAV DEL REQUEST_MAPPING 
                                    PARA QUE AL ABRIR EL FORMULARIO ME MUESTRE EL CHECK MARCADO Y NO DESMARCADO, Y LUEGO NECESITO EL OTRO PARA IR GUIANDO AL CHECK EN CADA ACCION O EVENTO QUE SE REALICE, Y NO PUEDO TENER 
                                    UNO SOLO CON EL MISMO NOMBRE PORQUE NO ME HACE CASO, EL JSP SOLO HACE CASO AL "ON" QUE LE CARGO EN EL MAV Y LUEGO CUANDO MODIFICO CON "OFF" EN ALGUN EVENTO DEL DO_POST, EL JSP YA NO ME HACE CASO
                                    Y ME DEJA EL VALOR CON "ON", ENTONCES POR ESO CREE LA SEGUNDA VARIABLE QUE AL INICIAR ESTARA NULO Y AHI HARE CASO A LA PRIMERA VARIABLE, PERO LUEGO CUANDO SE REALICE ALGUN EVENTO EN LA PAGINA SE CARGARA 
                                    Y AHI TOMARE SU VALOR YA QUE ESTE PUEDE IR CAMBIANDO ENTRE "ON" Y "OFF" O NULO, PERO EL PRIMERO SIEMPRE SE MANTIENE EN "ON" 
                                */
                                    String CHECK_FILTER_MEDICO = (String) request.getAttribute("CPH_CHECK_FILTRAR_MED"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                    String CHECK_FILTER_MEDICO01 = (String) request.getAttribute("CPH_CHECK_FILTRAR_MED_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
                                    if (CHECK_FILTER_MEDICO01 == null) {
                                        if (CHECK_FILTER_MEDICO == null || CHECK_FILTER_MEDICO.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_MEDICO = "";
                                        } else if (CHECK_FILTER_MEDICO.equalsIgnoreCase("ON")) {
                                            CHECK_MEDICO = "checked=\"checked\" ";
                                        }
                                    } else {
                                        if (CHECK_FILTER_MEDICO01 == null || CHECK_FILTER_MEDICO01.equalsIgnoreCase("OFF")) { // NO ESTA MARCADO 
                                            CHECK_MEDICO = "";
                                        } else if (CHECK_FILTER_MEDICO01.equalsIgnoreCase("ON")) {
                                            CHECK_MEDICO = "checked=\"checked\" ";
                                        }
                                    }
                                %>
                                <input type="checkbox" <%= CHECK_MEDICO %>id="check_med" name="check_med" />
                                <div class="combomedico">
                                    <select name="cbxAddNewMed" class="cbxMed form-control">
                                    <%
                                        Map<String, String> listaMedico;
                                        listaMedico = metodosMedico.traerComboMedicos(txtFiltroMedId, sesionDatosUsuario);
                                        Set setLisMed = listaMedico.entrySet();
                                        Iterator iterLisMed = setLisMed.iterator();
                                        
                                        while(iterLisMed.hasNext()) {
                                            Map.Entry mapaListMed = (Map.Entry) iterLisMed.next();
                                    %>
                                        <option value="<%= mapaListMed.getKey() %>"><%= mapaListMed.getValue() %></option>
                                    <%
                                        }
                                    %>
                                    </select>
                                </div>
                                <label for="check_med" class="btn btn-light">TODOS</label>
                            </div>
                        </div>
                    <%
                        }
                    %>
                        <div class="mainFilterBox boxFiltroBtn">
                            <div>
                                <p class="mainLabelSearch">Buscar:</p>
                                <input type="text" name="txB" value="<%= txtFiltro %>" class="form-control" />
                            </div>
                            
                            <div>
                                <input type="submit" name="accion" value="Filtrar" id="Filtrar" class="mb-2 mr-2 btn btn-primary" />
                                <input type="submit" name="accion" value="Limpiar" id="Limpiar" class="mb-2 btn btn-primary" />
<%//                                <!--<a href="<%=urlPlanHorario>" class="btn btn-primary mb-2">Limpiar</a>-->%>
                            </div>
                        </div>
                    </div>
                    
                    <div class="divTable">
                        <table role="table" class="tableFilter">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="PH_CI">Cód.</td>
                                    <td role="columnheader" class="PH_CC">Clinica</td>
                                    <td role="columnheader" class="PH_CM">Médico</td>
                                    <td role="columnheader" class="PH_CE">Estado</td>
                                    <td role="columnheader" class="PH_CBA">Acciones</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
                                    // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
                                    String NRO_PAG = "";
                                    if (sesionDatosUsuario.getAttribute("PAG_PLANHO_LISTA_ACTUAL") == null) {
                                        NRO_PAG = "1";
                                    } else {
                                        NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_LISTA_ACTUAL");
                                    }
                                    System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                                    
                                    List<BeanPlanHorario> listaDatos = null;
                                    if (((List<BeanPlanHorario>)request.getAttribute("CPH_LISTA_FILTRO")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        listaDatos = (List<BeanPlanHorario>) request.getAttribute("CPH_LISTA_FILTRO");
                                    } else {
                                        String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                                        listaDatos = metodosPlanHorario.cargar_grilla_paginacion(sesionDatosUsuario, NRO_PAG, cant_min_fija);
                                        sesionDatosUsuario.setAttribute("CPH_BAND_FILTRO", "0"); // CON ESTO RESETEO LA BANDERA QUE UTILIZO PARA LA PAGINACION 
                                    }
                                    Iterator<BeanPlanHorario> iterDatos = listaDatos.iterator();
                                    BeanPlanHorario datosBean = null;
                                    
                                    while(iterDatos.hasNext()) {
                                        datosBean = iterDatos.next();
                                        
                                        // TRAER DATOS DEL MEDICO 
                                        BeanPersona datosBPersona = new BeanPersona();
                                        datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBean.getOPH_IDMEDICO());
                                        BeanClinica datosBClinica = new BeanClinica();
                                        datosBClinica = metodosRefClinica.datosBasicosClinica(datosBClinica, datosBean.getOPH_IDCLINICA());
                                %>
                                <tr role="row">
                                    <td role="cell" class="PH_CI">
                                        <%= datosBean.getOPH_IDPLANHORARIO() %>
                                    </td>
                                    <td role="cell" class="PH_CC">
                                        <%= datosBClinica.getOC_DESC_CLINICA() %>
                                    </td>
                                    <td role="cell" class="PH_CM">
                                        <%= datosBPersona.getRP_NOMBRE()+" "+datosBPersona.getRP_APELLIDO() %>
                                    </td>
                                    <td role="cell" class="PH_CE">
                                        <%= metodosIniSes.devolverTextEstado(datosBean.getOPH_ESTADO()) %>
                                    </td>
                                    <td role="cell" class="PH_CBA">
                                        <form action="CPHSrv" method="post">
                                            <input type="text" value="<%= datosBean.getOPH_IDPLANHORARIO() %>" name="tI" class="form-control" />
                                            <input type="submit" value="Ver" name="accion" class="btn btn-light" />
                                            <%
                                            int var_btn_editar = 0;
                                            if (request.getAttribute("CPH_BTN_EDITAR_VALI") == null) {
                                                var_btn_editar = 0;
                                            } else {
                                                var_btn_editar = Integer.parseInt(String.valueOf(request.getAttribute("CPH_BTN_EDITAR_VALI")));
                                            }
                                            System.out.println("_   _JSP__VAR_VISIBILITY_BTN_EDITAR:   :"+var_btn_editar);
                                            // OBSERVACION: 
                                            // EL BOTON DE EDITAR LES VOY A PERMITIR A LOS PERFILES DE ADMINISTRADOR, FUNCIONARIO Y SECRETARIO 
                                            // PERO TAMBIEN LES VOY A PERMITIR VER PARA EDITAR AL PERFIL MEDICO QUE QUIERA EDITAR SU PROPIO PLAN HORARIO, SIN QUE PUEDA EDITAR EL PLAN HORARIO DE OTRO MEDICO 
                                            // OBS_2: SI EL PERFIL FUERA MEDICO ENTONCES NO LE CONTROLO LA VARIABLE (CPH_BTN_EDITAR_VALI) PORQUE AL MEDICO NO LE MUESTRO EL COMBO DE CLINICA Y LE MUESTRO TODOS SUS DATOS EN TODAS LAS CLINICAS, POR ESO NO TIENE SENTIDO CONTROLAR LA VARIABLE SI FUERA MEDICO PORQUE NO LE PERMITIRA EDITAR NINGUNO DE SUS REGISTROS EN CASO DE QUE FILTRARA 
                                            if (
                                                (((metodosPerfil.isPerfilAdmin(idPerfil))==true || (metodosPerfil.isPerfilFuncio(idPerfil))==true || (metodosPerfil.isPerfilSecre(idPerfil))==true) && (var_btn_editar == 0))
                                                || 
                                                ((metodosPerfil.isPerfilMedico(idPerfil))==true && idPersona.equals(datosBean.getOPH_IDMEDICO()))
                                            ) {
//                                                if(var_btn_editar == 0) {
                                            %>
                                            <input type="submit" value="Editar" name="accion" class="btn btn-light" />
                                            <%
//                                                }
                                            }
                                            %>
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="container-fluid text-right mt-4 mr-2 mb-3">
                        <%
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println("-       -        JSP_PAGINACION        -        -");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES QUE TENGO SEPARANDO POR UNA CANTIDAD DINAMICA DE REGISTROS QUE EL USUARIO QUIERE VER EN LA GRILLA 
                        String cant_total_btn_lista = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_CANT_TOTAL_BTNS"); // PAGINA PLAN_HORARIO CANTIDAD TOTAL DE BOTONES 
//                        System.out.println("_   __JSP___TOTAL_BTN_LISTA:   :"+cant_total_btn_lista);
                        
                        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE CLICS QUE EL USUARIO DIO PARA LA DERECHA PARA VER LOS DEMAS BOTONES DE PAGINAS, EMPIEZA CON 1 Y POR CADA CLIC AUMENTO UNO Y SI SE DA CLIC A LA IZQUIERDA LE RESTO UNO HASTA QUE VUELVA A SU NUMERO BASE (1) 
                        String CANT_CLIC_DERE = ""; // PAG_PAC_CANT_BTN_DERE_CLIC : PAGINA PACIENTE CANTIDAD BTN DERECHA (VER MAS >>) CLIC 
                        if ((String) sesionDatosUsuario.getAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC") == null) { // SI ESTA NULO POR ALGUNA RAZON DE INSTANCIA, ENTONCES LE COLOCO 1 COMO MINIMO VALOR ACEPTABLE Y SI NO ESTA NULO ENTONCES LE DEJO CON EL VALOR QUE TIENE 
                            CANT_CLIC_DERE = "1";
                        } else {
                            CANT_CLIC_DERE = (String) sesionDatosUsuario.getAttribute("PAG_PLANHO_CANT_BTN_DERE_CLIC");
                        }
//                        System.out.println("_   __JSP___CANT_CLIC_DERECHA:    :"+CANT_CLIC_DERE);
                        
                        // VARIABLE QUE UTILIZARE PARA MOSTRAR AL BOTON ACTIVO DEACUERDO AL NUMERO DE LISTA ACTUAL QUE SE ESTA MOSTRANDO 
                        String btn_css_active = "";
                        // VARIABLE QUE UTILIZO PARA COLOCAR AL BOTON ACTIVO EN UNA ETIQUETA QUE NO ENVIE UNA SOLICITUD AL SERVLET Y ESTE A SU VEZ NO VUELVA A CONSULTAR A LA BASE PARA EVITAR QUE SE SOBRECARGUE DE SOLICITUDES, YA QUE NO TIENE SENTIDO VOLVER A APRETAR EL MISMO INDICE Y SOLO A LOS DEMAS BOTONES LE CARGO UN BTN SUBMIT PARA QUE PUEDA SOLICITAR LA VISTA DE SU LITA 
                        String btn_submit = "";
                        
                        // POR MEDIO DE LA CANTIDAD DE CLICS MULTIPLICANDO POR 3 (LA CANTIDAD DE BOTONES A MOSTRAR) ENCUENTRO EL NUMERO DEL TERCER BOTON (EJEMPLO: 2 CLIC (*3) Y EL TERCER BOTON SERIA 6) 
                        int btn_max_mostrar = Integer.parseInt(CANT_CLIC_DERE) * 3;
//                        System.out.println("_   _JSP__btn_max_mostrar:  :"+btn_max_mostrar);
                        // AL TENER EL NUMERO DEL TERCER BOTON A MOSTRAR, RESTANDO DOS SÉ CUAL ES EL NUMERO DEL PRIMER BOTON A MOSTRAR 
                        int btn_min_mostrar = btn_max_mostrar - 2;
//                        System.out.println("_   _JSP__btn_min_mostrar:  :"+btn_min_mostrar);
                        
                        /*
                         * OBSERVACION: DE ESTE BLOQUE 
                            SI SE FILTRA Y SE CAMBIA LA CANTIDAD DE BOTONES Y EL NRO DE PAGINA ACTUAL, SE REALIZA CUALQUIER ACCION COMO LA DE AÑADIR UN NUEVO REGISTRO O VER UNO 
                            EL NRO DE PAG ACTUAL DE LA PAGINA PRINCIPAL SE QUEDA CON EL NRO ANTERIOR (EJ.: 4) Y SE VUELVE ATRAS CON EL BOTON DE LA PAGINA O LA FLECHA DEL NAVEGADOR 
                            EN EL CARGA GRILLA SE ESTABLECE LA PRIMERA PAGINA (1) PERO EN EL JSP SE RECUPERA (4) Y EN LAS IMPRESIONES DE ESCRITORIO PUEDO VER EN EL METODO DE CARGA GRILLA ESTA DEFINIDA (1) 
                            ENTONCES CON ESTE BLOQUE DE CODIGO CORRIGO LA PAGINA ACTUAL PARA QUE EL IF DE ABAJO DETECTE A LA PAGINA ACTUAL Y LA MARQUE AL BOTON 
                        * SOLUCIONES A ESTE PROBLEMA: 
                            AGREGUE UN BLOQUE DE CODIGO MAS EN EL CARGA GRILLA PARA FORMATEAR EL CLIC DERECHO CONTROLANDO EL NRO DE PAG ACTUAL 
                            Y AGREGUE ESTE BLOQUE DE CODIGO ACA PARA FORMATEAR LA VARIABLE DEL NRO DE PAG ACTUAL A MOSTRAR, YA QUE DESDE EL MODELO SE ACTUALIZA PERO EN EL JSP SE VE EL VALOR ANTERIOR Y POR ESA RAZON NO SE MARCA EL BOTON DE LA PAGINA ACTUAL MOSTRANDO, Y CON ESTA CONDICIONAL CONSIGO QUE ME MARQUE LA PAGINA SELECCIONADA 
                        */
                        System.out.println(".----------------------------------");
                        System.out.println("_  _NRO_PAG:  :"+NRO_PAG);
                        System.out.println("_  _BTN_MAX:  :"+btn_max_mostrar);
                        System.out.println("_  _BTN_MIN:  :"+btn_min_mostrar);
                        System.out.println("_  _CANT_CLICS_DERE:  :"+CANT_CLIC_DERE);
                        if ((Integer.parseInt(NRO_PAG) > btn_max_mostrar || Integer.parseInt(NRO_PAG) < btn_min_mostrar)) {
                            System.out.println("------IF------");
                            NRO_PAG = "1";
                        } else {
                            System.out.println("-----else------");
                        }
                        System.out.println(".----------------------------------");
                        
                        // SI EL BOTON MINIMO A MOSTRAR ES MAYOR A UNO ENTONCES VOY A MOSTRARLE EL BOTON PARA RETROCEDER ATRAS, PERO SI ES IGUAL A UNO ES PORQUE ESTA EN EL BLOQUE DE LOS PRIMERO TRES BOTONES Y AHI NO TIENE SENTIDO QUE LE MUESTRE EL BOTON PARA IRSE MAS ATRAS  
                        if (btn_min_mostrar > 1) {
                        %>
                            <input type="submit" value="<<" name="accion" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                        <%
                        }
                        
                        // FOR QUE HAGO RECORRIENDO LA CANTIDAD TOTAL DE BOTONES QUE TENGO PARA ENCONTRAR AL BOTON DE LA PAGINA O DE LA LISTA ACTUAL QUE EL USUARIO ESTA VIENDO 
                        for(int i=1; i <= Integer.parseInt(cant_total_btn_lista); i++) {
//                            System.out.println(".");
//                            System.out.println(".");
//                            System.out.println("_  _____FOR____ITEM_TOTAL_______ i : "+i);
                            
//                            System.out.println("____i("+i+")  >  btn_max_mostrar("+i+")_____");
                            // SI LA CANTIDAD DE BOTONES ACTUAL QUE SE ESTA RECORRIENDO ES MAYOR A LA CANTIDAD DE BOTONES MAXIMA A MOSTRAR ENTONCES CORTO EL FOR PARA NO GASTAR RECURSOS RECORRIENDO EL FOR 
                            if (i > btn_max_mostrar) {
                                break;
                            }
                            
//                            System.out.println("__  _JPS_____if( i("+i+") <= resul_multi_primo("+btn_max_mostrar+") && i("+i+") >= pag_min_mostrar("+btn_min_mostrar+")   )____");
                            
                            // VALIDACION PARA MOSTRAR LOS TRES BOTONES 
                            // SI LA FILA ACTUAL ES MENOR AL NRO DEL BOTON ULTIMO A MOSTRAR Y ES MAYOR O IGUAL AL NUMERO MINIMO DEL BOTON A MOSTRAR 
                            if (i <= btn_max_mostrar && i >= btn_min_mostrar) {
                                // CONTROLO PARA PODER DARLE AL BOTON UNA IMAGEN DISTINTA PARA QUE EL USUARIO SEPA QUE LISTA DE LOS BOTONES SE ENCUENTRA MOSTRANDO LOS DATOS EN LA GRILLA  
                                if (NRO_PAG.equals(""+i+"")) {
                                    btn_css_active = "btn btn-secondary disabled";
                                    btn_submit = "0";
                                } else { // 
                                    btn_css_active = "btn btn-outline-secondary";
                                    btn_submit = "1";
                                }
                                
                            // CONTROLO EL VALOR FINAL DE LA VARIABLE PARA SABER SI LE MUESTRO UN BOTON SUBMIT O UN BOTON VACIO 
                            if (btn_submit.equals("1")) {
                            %>
                            <input type="submit" value="<%= i %>" name="accion" class="py-1 px-3 mr-2 <%=btn_css_active%>" />
                            <%
                            } else if (btn_submit.equals("0")) {
                            %>
                            <a href="#" class="py-1 px-3 mr-2 <%= btn_css_active %>"><%= i %></a>
                            <%
                            } // end if btn 
                            
                            } // end if ctrl_3_btn_mostrar 
                        } // end for cant_btns
                            
                            // SI EL BOTON MAXIMO A MOSTRAR ES MENOR AL ITEM TOTAL ENTONCES 
                            if ((btn_max_mostrar < Integer.parseInt(cant_total_btn_lista))) {
                            %>
                            <input type="submit" value=">>" name="accion" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                            <%
                            } // end if 
                            %>
                        </div>
                    </div>
                </form>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasic.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>