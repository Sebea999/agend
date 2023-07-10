<%-- 
    Document   : pagCuentaCliente_VerCta
    Created on : 02-ago-2021, 14:48:02
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanFacturaCab"%>
<%@page import="com.agend.javaBean.BeanClinica"%>
<%@page import="com.agend.javaBean.BeanCuentaCliente"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cuenta Cliente</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">       --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                System.out.println("[+]_________JSP___________CUENTA_CLIENTE______________");
//                System.out.println(".");
////                BeanPersona beanUserLogin = (BeanPersona) sesionDatosUsuario.getAttribute("BEANPERSONA_USERLOGIN");
////                System.out.println(".___USER-LOGIN____ID_PERSONA:  :"+beanUserLogin.getRP_IDPERSONA());
////                System.out.println(".___USER-LOGIN____APELLIDO_P:  :"+beanUserLogin.getRP_APELLIDO());
//                System.out.println("[.]___SESION____ID_USER_LOGIN:  :"+String.valueOf(sesionDatosUsuario.getAttribute("IDUSUARIO_USERLOGIN")));
//                System.out.println("[.]___COOKIE____ID_USER_LOGIN:  :"+(metodosIniSes.getCookie(request, "JSESSIONIDU").getValue()));
//                System.out.println(".");
                
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
                
                String ID_CLIENTE = (String) sesionDatosUsuario.getAttribute("ID_CLIENTE");
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
                String varTitulo = "";
                if (ID_CLIENTE.isEmpty() || ID_CLIENTE.equals("")) {
                    varTitulo = "CUENTA CLIENTE ";
                } else {
                    varTitulo = "CUENTA CLIENTE - REGISTRO DE CUENTAS";
                }
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
                    
                    <form method="post" action="CCCSrv" autocomplete="off">
                    <%
                        String TXT_FILTRAR_CUENTA = (String) request.getAttribute("CC_FILTRAR_CUENTA");
                        System.out.println("_   _TXT_FILTRAR_CUENTA:    "+TXT_FILTRAR_CUENTA);
                        if(TXT_FILTRAR_CUENTA == null) {
                            TXT_FILTRAR_CUENTA = "";
                        }
                    %>
                        <div class="CCV_PFC">
                            <div>
                                <p class="pt-2 mr-3">Filtrar por Desc.:</p>
                                <input type="text" value="<%= TXT_FILTRAR_CUENTA %>" name="tFC" id="tFC" class="form-control w200 mr-2" />
                                <input type="submit" value="Filtrar Cuenta" name="accion" class="btn btn-info mr-3 h2_5Rem" />
                                <input type="submit" value="Limpiar Filtro" name="accion" class="btn btn-info h2_5Rem" />
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
                                    <td role="columnheader" class="CCVC_E">Clinica Agen.</td>
                                    <td role="columnheader" class="CCVC_D">Descripción</td>
                                    <td role="columnheader" class="CCVC_FV">Fecha Venc.</td>
                                    <td role="columnheader" class="CCVC_M">Monto</td>
                                    <td role="columnheader" class="CCVC_S">Saldo</td>
                                    <td role="columnheader" class="CCVC_E">Estado Cta.</td>
                                    <td role="columnheader" class="CCVC_NF">Nro. Factura</td>
                                    <td role="columnheader" class="CCVC_FF">Fecha Fact.</td>
                                    <td role="columnheader" class="CCVC_FF">Ver Fact.</td>
                                    <td role="columnheader" class="CCVC_BVA">Ver Agend.</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
//                                List<BeanCuentaCliente> listaCtas = metodosCtaCliente.traer_datos(ID_CLIENTE); 
                                List<BeanCuentaCliente> listaCtas = null;
                                if (((List<BeanCuentaCliente>)request.getAttribute("CCC_LISTA_FILTRO_CTA")) != null) { // CONTROLO SI LA LISTA ESTA NULL 
                                    listaCtas = (List<BeanCuentaCliente>) request.getAttribute("CCC_LISTA_FILTRO_CTA");// SI NO ESTA NULL ENTONCES ES PORQUE EL USUARIO FILTRO POR UNA CUENTA DEL CLIENTE Y LE CARGARE EL RESULTADO 
                                } else { // SI SE ENCUENTRA NULO ES PORQUE NO FILTRO POR NINGUNA CUENTA 
                                    listaCtas = metodosCtaCliente.traer_datos(ID_CLIENTE, sesionDatosUsuario); /* Y EN ESE CASO ENTONCES CARGARIA TODAS LAS CUENTAS QUE TENGA EL CLIENTE  */
//                                    listaCtas = metodosCtaCliente.traer_datos_cta_clie(ID_CLIENTE, sesionDatosUsuario); /* Y EN ESE CASO ENTONCES CARGARIA TODAS LAS CUENTAS QUE TENGA EL CLIENTE  */
                                }
                                Iterator<BeanCuentaCliente> iterCtas = listaCtas.iterator();
                                BeanCuentaCliente datos = new BeanCuentaCliente();
                                
                                while(iterCtas.hasNext()) {
                                    datos = iterCtas.next();
                                    
                                    String DESC_CLINICA = metodosRefClinica.traerDescClinica(datos.getOA_IDCLINICA());
                                    // FACTURA.-
                                    String IDFACTURA = datos.getOAD_IDFACTURA();
                                    System.out.println("_   _JSP__ID_FACTURA:   :"+IDFACTURA);
                                    String NROFACTURA="-", FECHAFACTURA="-";
                                    if (IDFACTURA.equals("0") || IDFACTURA.isEmpty() || IDFACTURA==null) {
                                        //
                                    } else {
                                        BeanFacturaCab datosFact = new BeanFacturaCab();
                                        datosFact = metodosFactura.traerDatosBeanFact(IDFACTURA);
                                        NROFACTURA = datosFact.getOF_NROFACTURA();
                                        FECHAFACTURA = datosFact.getOF_FECHAFACTURA();
                                    }
                                    // AGENDAMIENTO.-
                                    String IDAGENDAMIENTO = datos.getCC_IDAGENDAMIENTO();
                                    if (IDAGENDAMIENTO == null) {
                                        IDAGENDAMIENTO = "";
                                    }
                                    System.out.println("_   _JSP_CTA___ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                                    System.out.println("_   _JSP_CTA___ITEM_AGENDAMIEN:   :"+datos.getCC_ITEM_AGEN());
                                    
                                    // BLOQUE DE CODIGO QUE UTILIZO PARA DARLE COLOR A LA PRIMERA COLUMNA DE LA LINEA DEACUERDO AL ESTADO DE LA CUENTA, SI FUERA ACTIVA ENTONCES LE MARCARA UN TONO ROJO YA QUE SE TIENE QUE SALDAR, SI FUERA COBRADO ENTONCES TENDRIA UN COLOR VERDE YA QUE SE HA PAGADO LA CUENTA, Y SI ESTUVIERA ANULADO LE MOSTRARE UN COLOR MEDIO GRIS 
                                    String cssCtaEstado = "";
                                    if (datos.getCC_ESTADO().equalsIgnoreCase("Activo") || datos.getCC_ESTADO().equalsIgnoreCase("A")) {
                                        cssCtaEstado = "backRe";
                                    } else if (datos.getCC_ESTADO().equalsIgnoreCase("Anulado") || datos.getCC_ESTADO().equalsIgnoreCase("X")) {
                                        cssCtaEstado = "backBla";
                                    } else if (datos.getCC_ESTADO().equalsIgnoreCase("Cobrado") || datos.getCC_ESTADO().equalsIgnoreCase("C")) {
                                        cssCtaEstado = "backGre";
                                    }
                                %>
                                <tr role="row">
                                    <td role="cell" class="CCVC_NC <%= cssCtaEstado %>"><%= datos.getCC_NROCUOTA() %></td>
                                    <td role="cell" class="CCVC_E"><%= DESC_CLINICA %></td>
                                    <td role="cell" class="CCVC_D"><%= datos.getCC_COMENTARIO() %></td>
                                    <td role="cell" class="CCVC_FV"><%= datos.getCC_FEC_VENCIMIENTO() %></td>
                                    <td role="cell" class="CCVC_M"><%= datos.getCC_MONTO() %></td>
                                    <td role="cell" class="CCVC_S"><%= datos.getCC_SALDO() %></td>
                                    <td role="cell" class="CCVC_E"><%= datos.getCC_ESTADO() %></td>
                                    <td role="cell" class="CCVC_NF"><%= NROFACTURA %></td>
                                    <td role="cell" class="CCVC_FF"><%= FECHAFACTURA %></td>
                                    <td role="cell" class="CCVC_FF">
                                    <%
                                        if (!IDFACTURA.isEmpty() && IDFACTURA != null && !IDFACTURA.equals("0")) {
                                            System.out.println(".");
                                            System.out.println(".");System.out.println(".");
                                            System.out.println("_       _____________JSP_______________BTN_ATRAS_FACTURA_____________");
                                            System.out.println(".");System.out.println(".");
                                            System.out.println(".");
                                            // LO COMENTE PORQUE A TRAVES DE LA VARIABLE AUXILIAR DE bvaf DESDE EL CONTROLADOR LE CARGO A LA VARIABLE AUXILIAR DE FACTURA PARA EL BTN DE VOLVER ATRAS 
//                                            sesionDatosUsuario.setAttribute("FACTURA_BOTON_VOLVER_ATRAS", "3"); // VARIABLE DE LA SESION QUE UTILIZO PARA SABER A QUE PAGINA VOLVER ATRAS 
                                            // OBSERVACION: SI EL PERFIL DEL USUARIO ES ADMIN, FUNCIONARIO O SECRETARIO ENTONCES LE MUESTRO EL BOTON PARA VER LA FACTURA DESDE LA PAGINA DE "VER FACTURA" 
                                            if (metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil) || metodosPerfil.isPerfilFuncio(idPerfil)) {
                                    %>
                                        <form action="CFSrv" method="post">
                                            <input type="hidden" value="3" name="bvaf" class="form-control disNone"/>
                                            <input type="hidden" value="<%= datos.getOAD_IDFACTURA() %>" name="tIF" class="form-control disNone"/>
                                            <button type="submit" name="accion" value="Ver" class="btn btn-light fs-12">Ver Factura</button>
                                        </form>
                                    <%
                                            } else if (metodosPerfil.isPerfilPaciente(idPerfil)) { // Y SI EL PERFIL FUERA PACIENTE ENTONCES LE IMPRIMIRIA LA FACTURA PARA QUE PUEDA VER LOS DATOS PORQUE EN EL MODELO DE PERFIL NO LE HABILITE AL PERFIL PACIENTE EL INGRESO A LA PAGINA DE FACTURA 
                                    %>
                                        <form action="factura_cliente_pdf" target="_blank" method="post">
                                            <input type="hidden" value="<%= datos.getOF_IDFACTURA() %>" name="tRIF" class="form-control disNone" />
                                            <input type="submit" value="Imprimir" class="btn btn-light" />
                                        </form>
                                    <%
                                            }
                                        } else {
                                    %>
                                        <spam><%= "-" %></spam>
                                    <%
                                        }
                                    %>
                                    </td>
                                    <td role="cell" class="CCVC_BVA">
                                    <%
                                        if (!IDAGENDAMIENTO.isEmpty() && IDAGENDAMIENTO != null && !IDAGENDAMIENTO.equals("0")) {
                                            System.out.println(".");
                                            System.out.println(".");System.out.println(".");
                                            System.out.println("_       _____________JSP_______________BTN_ATRAS_AGENDAMIENTO_____________");
                                            System.out.println(".");System.out.println(".");
                                            System.out.println(".");
                                            // OBSERVACION: SI EL PERFIL DEL USUARIO ES ADMIN, FUNCIONARIO O SECRETARIO ENTONCES LE MUESTRO EL BOTON PARA VER LA FACTURA DESDE LA PAGINA DE "VER FACTURA" 
                                            if (metodosPerfil.isPerfilPaciente(idPerfil) || 
                                                    metodosPerfil.isPerfilAdmin(idPerfil) || metodosPerfil.isPerfilSecre(idPerfil) || metodosPerfil.isPerfilFuncio(idPerfil)) {
                                    %>
                                        <form action="CCCSrv" method="post">
                                            <input type="hidden" value="<%= datos.getCC_IDAGENDAMIENTO()%>" name="tI" class="form-control disNone" />
                                            <input type="hidden" value="<%= datos.getCC_ITEM_AGEN()%>" name="tIAD" class="form-control disNone" />
                                            <button type="submit" value="VER_AGENDAMIENTO_PAC" name="accion" class="btn btn-light fs-12">Ver Agendamiento</button>
                                        </form>
                                    <% // OBSERVACION: COMENTE ESTO Y LA CONSULTA DEL IF DEL PERFIL PACIENTE AGREGUE ARRIBA PARA MOSTRAR LOS DATOS DEL AGENDAMIENTO CON EL MISMO BOTON, Y YA NO LE MUESTRO AL BOTON DE IMPRIMIR EN PDF LOS DATOS DEL AGENDAMIENTO YA QUE EL PACIENTE YA PUEDE VER LOS DATOS DEL AGENDAMIENTO EN PDF A TRAVES DE LA PAGINA DE REPORTE AGENDAMIENTO Y NO TENDRIA SENTIDO LA PAGINA DEL REPORTE SI AL FINAL PUEDE IMPRIMIR EL MISMO REPORTE DESDE ACA NOMAS 
//                                            } else if (metodosPerfil.isPerfilPaciente(idPerfil)) { // Y SI EL PERFIL FUERA PACIENTE ENTONCES LE IMPRIMIRIA LA FACTURA PARA QUE PUEDA VER LOS DATOS PORQUE EN EL MODELO DE PERFIL NO LE HABILITE AL PERFIL PACIENTE EL INGRESO A LA PAGINA DE FACTURA 
//                                    >
//                                        <form action="mi_agend_pdf" target="_blank" method="post">
//                                            <input type="hidden" value="<%= datos.getCC_IDAGENDAMIENTO()>" name="tI" class="form-control disNone" />
//                                            <input type="hidden" value="<%= datos.getCC_ITEM_AGEN()>" name="tIt" class="form-control disNone" />
//                                            <button type="submit" value="Imprimir" class="btn btn-light fs-12 ml-3">Imprimir</button>
//                                        </form>
//                                    <
                                            }
                                        } else {
                                    %>
                                        <spam><%= "-" %></spam>
                                    <%
                                        }
                                    %>
                                    </td>
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
                        LE PERMITO A LOS OTROS PERFILES VER EL BOTON PARA VOLVER A LA PAGINA PRINCIPAL Y AL PACIENTE LE CAMBIO EL URL DE VOLVER ATRAS 
                    */
                    String urlBtnVolverAtras = "";
                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true
                            || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilMedico(idPerfil)==true) {
                        urlBtnVolverAtras = urlCtaCliente;
                    } else if (metodosPerfil.isPerfilPaciente(idPerfil)) {
                        urlBtnVolverAtras = urlPrincipal;
                    }
                    %>
                    <div>
                        <a href="<%= urlBtnVolverAtras %>" class="btn btn-danger mt-4">Volver Atras</a>
                    </div>
                    <%
//                    }
                    %>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>