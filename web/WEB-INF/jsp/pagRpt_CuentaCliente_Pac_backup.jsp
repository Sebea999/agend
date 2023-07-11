<%-- 
    Document   : pagRpt_CuentaCliente_Pac
    Created on : 25-abr-2022, 11:18:03
    Author     : RYUU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanCuentaCliente"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Cuenta Paciente</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab123.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CC_MENSAJE"); // CONTROLLER CLIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CC_TIPO_MENSAJE"); // CONTROLLER CLIENTE TIPO MENSAJE 
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
                 * OBSERVACION: COMO ESTA PAGINA LE VOY A MOSTRAR AL PACIENTE YA QUE VA A SER SU PAGINA 
                    DONDE VA A PODER ENCONTRAR SU CUENTAS, ENTONCES EL IDCLIENTE VA A SER DIRECTAMENTE EL IDPERSONA DEL USUARIO LOGEADO 
                    YA QUE EL USUARIO LOGEADO ES EL MISMO PACIENTE QUE QUIERE VER SUS CUENTAS 
                */
                String ID_CLIENTE = idPersona;
//                String ID_CLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
                System.out.println("_jsp___ID_CLIENTE:  "+ID_CLIENTE);
                
                List<BeanPersona> listaDatos;
                listaDatos = metodosPersona.traerDatosPersona(ID_CLIENTE);
                Iterator<BeanPersona> iterCliente = listaDatos.iterator();
                BeanPersona cliente = null;
                
                String CLI_NOMBRE="", CLI_APELLIDO="", CLI_CINRO="", CLI_RAZON_SOCIAL="", CLI_RUC="";
                while(iterCliente.hasNext()) {
                    cliente = iterCliente.next();
                    CLI_NOMBRE = cliente.getRP_NOMBRE();
                    CLI_APELLIDO = cliente.getRP_APELLIDO();
                    CLI_CINRO = cliente.getRP_CINRO();
                    CLI_RAZON_SOCIAL = cliente.getRP_RAZON_SOCIAL();
                    CLI_RUC = cliente.getRP_RUC();
//                    CLI_TELEFONO = cliente.getRP_TELFMOVIL();
//                    CLI_DIRECCION = cliente.getRP_DIRECCION();
//                    CLI_EMAIL = cliente.getRP_EMAIL();
                } // end while 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CC_CLI_NOMBRE") != null) {
                    CLI_NOMBRE = (String) request.getAttribute("CC_CLI_NOMBRE");
//                    System.out.println("_REQUEST_CLI_NOMBRE:    "+CLI_NOMBRE);
                }
                if ((String)request.getAttribute("CC_CLI_APELLIDO") != null) {
                    CLI_APELLIDO = (String) request.getAttribute("CC_CLI_APELLIDO");
//                    System.out.println("_REQUEST_CLI_APELLIDO:    "+CLI_APELLIDO);
                }
                if ((String)request.getAttribute("CC_CLI_CINRO") != null) {
                    CLI_CINRO = (String) request.getAttribute("CC_CLI_CINRO");
//                    System.out.println("_REQUEST_CLI_CINRO:    "+CLI_CINRO);
                }
                if ((String)request.getAttribute("CC_CLI_RAZON_SOCIAL") != null) {
                    CLI_RAZON_SOCIAL = (String) request.getAttribute("CC_CLI_RAZON_SOCIAL");
//                    System.out.println("_REQUEST_CLI_RAZON_SOCIAL:    "+CLI_RAZON_SOCIAL);
                }
                if ((String)request.getAttribute("CC_CLI_RUC") != null) {
                    CLI_RUC = (String) request.getAttribute("CC_CLI_RUC");
//                    System.out.println("_REQUEST_CLI_RUC:    "+CLI_RUC);
                }
//                if ((String)request.getAttribute("CC_CLI_TELEFONO") != null) {
//                    CLI_TELEFONO = (String) request.getAttribute("CC_CLI_TELEFONO");
////                    System.out.println("_REQUEST_CLI_TELEFONO:    "+CLI_TELEFONO);
//                }
//                if ((String)request.getAttribute("CC_CLI_DIRECCION") != null) {
//                    CLI_DIRECCION = (String) request.getAttribute("CC_CLI_DIRECCION");
////                    System.out.println("_REQUEST_CLI_DIRECCION:    "+CLI_DIRECCION);
//                }
//                if ((String)request.getAttribute("CC_CLI_EMAIL") != null) {
//                    CLI_EMAIL = (String) request.getAttribute("CC_CLI_EMAIL");
////                    System.out.println("_REQUEST_CLI_EMAIL:    "+CLI_EMAIL);
//                }
                
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR CLIENTES, ENTONCES DE ACUERDO AL IDCLIENTE VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO CLIENTE O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "REPORTE CUENTA CLIENTE - REGISTRO DE CUENTAS";
                %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    
                    <div>
                        <table class="dataCtaCliente">
                            <tbody>
                            <tr>
                                <td>CÓDIGO:</td>
                                <td>
                                    <input type="text" value="<%= ID_CLIENTE %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <%
                            /*
                             * OBSERVACION: EN CASO DE QUE SEA EL PERFIL DE PACIENTE, NO TIENE SENTIDO MOSTRARLE SUS PROPIOS DATOS 
                                EN CASO DE SER OTROS PERFILES SI, PARA QUE VEA LOS DATOS DEL PACIENTE QUE ESTAN MIRANDO SUS CUENTA. 
                            */
                            if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true
                                    || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilMedico(idPerfil)==true) {
                            %>
                            <tr>
                                <td>Nombres:</td>
                                <td>
                                    <input type="text" value="<%= CLI_NOMBRE %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Apellidos:</td>
                                <td>
                                    <input type="text" value="<%= CLI_APELLIDO %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Nro. Cédula:</td>
                                <td>
                                    <input type="text" value="<%= CLI_CINRO %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Razon Social:</td>
                                <td>
                                    <input type="text" value="<%= CLI_RAZON_SOCIAL %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>RUC:</td>
                                <td>
                                    <input type="text" value="<%= CLI_RUC %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <%
                            }
                            %>
                            </tbody>
                        </table>
                    </div>
                    
                    <form method="post" action="CRSrv" autocomplete="off">
                    <%
                        String TXT_FILTRAR_CUENTA = (String) request.getAttribute("CR_RCC_FILTRAR_CUENTA");
                        System.out.println("_   _TXT_FILTRAR_CUENTA:    "+TXT_FILTRAR_CUENTA);
                        if(TXT_FILTRAR_CUENTA == null) {
                            TXT_FILTRAR_CUENTA = "";
                        }
                        String txtFiltroCliId = (String) request.getAttribute("CR_RCC_TXT_FIL_ID_CLI");
                        System.out.println("_   _TXT_FILTRAR_CLINICA:    "+txtFiltroCliId);
                        if (txtFiltroCliId == null || txtFiltroCliId.isEmpty()) {
                            txtFiltroCliId = "";
                        }
                    %>
                        <table class="divRptCtaPacCli">
                            <tbody>
                                <tr>
                                    <td>
                        <%
                            // VALIDACION PARA HABILITARLE EL FILTRO DE CLINICA A LOS PERFILES DE SECRETARIO Y ADMINISTRADOR 
//                            if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true) {
                        %>
                            <!--<div class="">-->
                                <!--<div class="panel_clinica" style="margin-left: -1px;">-->
                                    <p class="mainLabelSearch mt-2">Clinica:</p>
                                    </td>
                                    <td>
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
                                        String CHECK_FILTER_CLINICA = (String) request.getAttribute("CR_RCC_CHECK_FILTRAR_CLI"); // VARIABLE QUE SE ENCUENTRA EN EL MAV / REQUEST_MAPPING DE LA PAGINA 
                                        String CHECK_FILTER_CLINICA01 = (String) request.getAttribute("CR_RCC_CHECK_FILTRAR_CLI_01"); // VARIABLE QUE SE ENCUENTRA DENTRO DEL DO_POST 
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
                                    <input type="checkbox" <%= CHECK_CLINICA %>id="check_clinica" style="margin-right: -4rem;" name="check_clinica" />
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
                                    <label for="check_clinica" class="btn btn-light ml-3">TODOS</label>
                                <!--</div>-->
                            <!--</div>-->
                        <%
//                            }
                        %>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <div class="CCV_PFC">
                            <div>
                                <p class="pt-2 mr-3">Filtrar por Desc.:</p>
                                <input type="text" value="<%= TXT_FILTRAR_CUENTA %>" name="tFC" id="tFC" class="form-control w200 mr-2" />
                                <input type="submit" value="Filtrar Cuenta" name="accionRptCtaPac" class="btn btn-info mr-3 h2_5Rem" />
                                <input type="submit" value="Limpiar Filtro" name="accionRptCtaPac" class="btn btn-info h2_5Rem" />
                            </div>
                        </div>
                    </form>
                    
                    <div>
                        <%
                        // EN UNA GRILLA MOSTRAR LAS CUENTAS DEL CLIENTE 
                        // UNA COLUMNA PARA MONTO, SALDO, ESTADO, COMENTARIO, FECHA VENCIMIENTO 
                        // CREO QUE SE PUEDE AGREGAR UN FILTRO POR NRO DE EMPEÑO Y LUEGO UN BOTON DE IMPRIMIR
                        %>
                        <table role="table" class="tableCtas mt-2">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="CCVC_NC">Nro. Cuota</td>
                                    <td role="columnheader" class="CCVC_E">Clinica</td>
                                    <td role="columnheader" class="CCVC_D">Descripción</td>
                                    <td role="columnheader" class="CCVC_FV">Fecha Venc.</td>
                                    <td role="columnheader" class="CCVC_M">Monto</td>
                                    <td role="columnheader" class="CCVC_S">Saldo</td>
                                    <td role="columnheader" class="CCVC_E">Estado</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
//                                List<BeanCuentaCliente> listaCtas = metodosCtaCliente.traer_datos(ID_CLIENTE); 
                                List<BeanCuentaCliente> listaCtas = null;
                                if (((List<BeanCuentaCliente>)request.getAttribute("CR_RCC_LISTA_FILTRO_CTA")) != null) { // CONTROLO SI LA LISTA ESTA NULL 
                                    listaCtas = (List<BeanCuentaCliente>) request.getAttribute("CR_RCC_LISTA_FILTRO_CTA");// SI NO ESTA NULL ENTONCES ES PORQUE EL USUARIO FILTRO POR UNA CUENTA DEL CLIENTE Y LE CARGARE EL RESULTADO 
                                } else { // SI SE ENCUENTRA NULO ES PORQUE NO FILTRO POR NINGUNA CUENTA 
                                    listaCtas = metodosCtaCliente.traer_datos(ID_CLIENTE, sesionDatosUsuario); /* Y EN ESE CASO ENTONCES CARGARIA TODAS LAS CUENTAS QUE TENGA EL CLIENTE  */
                                }
                                Iterator<BeanCuentaCliente> iterCtas = listaCtas.iterator();
                                BeanCuentaCliente datos = new BeanCuentaCliente();
                                
                                while(iterCtas.hasNext()) {
                                    datos = iterCtas.next();
                                    
                                    String DESC_CLINICA = metodosRefClinica.traerDescClinica(datos.getOA_IDCLINICA());
                                %>
                                <tr role="row">
                                    <td role="cell" class="CCVC_NC"><%= datos.getCC_NROCUOTA() %></td>
                                    <td role="cell" class="CCVC_E"><%= DESC_CLINICA %></td>
                                    <td role="cell" class="CCVC_D"><%= datos.getCC_COMENTARIO() %></td>
                                    <td role="cell" class="CCVC_FV"><%= datos.getCC_FEC_VENCIMIENTO() %></td>
                                    <td role="cell" class="CCVC_M"><%= datos.getCC_MONTO() %></td>
                                    <td role="cell" class="CCVC_S"><%= datos.getCC_SALDO() %></td>
                                    <td role="cell" class="CCVC_E"><%= datos.getCC_ESTADO() %></td>
                                </tr>
                                <%
                                }
                                %>
                            </tbody>
                        </table>
                    </div>

                    <%
                    /*
                     * OBSERVACION: HAGO ESTO PORQUE EL PACIENTE ES EL PERFIL QUE ENTRARA DIRECTAMENTE A ESTA PAGINA Y NO VERA LA PRINCIPAL 
                        YA QUE NO TIENE SENTIDO MOSTRARLE LA PAGINA PRINCIPAL Y QUE PUEDA VER LAS CUENTAS DE OTROS PACIENTES, ENTONCES 
                        LE PERMITO A LOS OTROS PERFILES VER EL BOTON PARA VOLVER A LA PAGINA PRINCIPAL 
                    */
                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true
                            || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilMedico(idPerfil)==true) {
                    %>
                    <div>
                        <a href="<%= urlCtaCliente %>" class="btn btn-danger mt-4">Volver Atras</a>
                    </div>
                    <%
                    }
                    %>
                </div>
            </main>
        
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>