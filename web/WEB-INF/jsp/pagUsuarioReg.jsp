
<%@page import="com.agend.modelo.ModelInicioSesion"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="com.agend.modelo.ModelPersona"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/cliente/styleCliente_Reg.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalClie.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
            <%
            // DECLARO LAS VARIABLES AQUI PORQUE SI SE ENCUENTRAN DENTRO DEL IF ME VA A DAR ERROR POR UTILIZARLAS EN EL SCRIPT DE JAVASCRIPT 
            String USU_NOMBRE_ORI="", USU_APELLIDO_ORI="", USU_CINRO_ORI="", USU_RAZON_SOCIAL_ORI="", USU_RUC_ORI="", USU_TELEFONO_ORI="", USU_DIRECCION_ORI="", USU_EMAIL_ORI="", USU_IDUSUARIO_ORI="", USU_USUARIO_ORI="", USU_CLAVE_ORI="", USU_ESTADO_ORI="", USU_PERFIL_ORI="";

            if(idPerfil.equals("1")) { // SI FUESE ADMINISTRADOR NOMAS LE VOY A MOSTRAR EL CONTENIDO DE LA PAGINA, YA QUE SI EL USUARIO ESCRIBE EN LA BARRA DEL NAVEGADOR "usuario" INGRESARIA A LA PAGINA 
            %>
            <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CU_MENSAJE"); // CONTROLLER USUARIO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CU_TIPO_MENSAJE"); // CONTROLLER USUARIO TIPO MENSAJE 
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
                    * OBSERVACION: PARA AÑADIR USUARIO Y EDITAR USUARIO 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_USUARIO, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_USUARIO = (String) sesionDatosUsuario.getAttribute("ID_USUARIO");
                System.out.println("_jsp___ID_USUARIO:  "+ID_USUARIO);
//                String IDUSUARIO = (String) request.getParameter("tI");
//                System.out.println("_jsp__222___ID_USUARIO__:   "+IDUSUARIO);
                
                String USU_IDPERSONA="", USU_NOMBRE="", USU_APELLIDO="", USU_CINRO="", USU_RAZON_SOCIAL="", USU_RUC="", USU_TELEFONO="", USU_DIRECCION="", USU_EMAIL="", USU_IDUSUARIO="", USU_USUARIO="", USU_CLAVE="", USU_ESTADO="", USU_PERFIL="", USU_IDCLINICA="";
                String USU_CELULAR="", USU_IDCIUDAD="", USU_PROFESION="", USU_FECHA_NAC="", USU_SEXO="", USU_ESTADO_CIVIL="", USU_TIENE_HIJOS="", USU_CANTIDAD_HIJOS="", USU_OTROS_DATOS="";
//                String USU_NOMBRE_ORI="", USU_APELLIDO_ORI="", USU_CINRO_ORI="", USU_RAZON_SOCIAL_ORI="", USU_RUC_ORI="", USU_TELEFONO_ORI="", USU_DIRECCION_ORI="", USU_EMAIL_ORI="", USU_IDUSUARIO_ORI="", USU_USUARIO_ORI="", USU_CLAVE_ORI="", USU_ESTADO_ORI="";
//                if (ID_USUARIO == null || ID_USUARIO.isEmpty()) {
//                    System.out.println("__  _IF_  ___JSP_____ID_USUARIO_VACIO______");
//                    USU_IDPERSONA = (String) sesionDatosUsuario.getAttribute("ID_USU_PERSONA");
//                } else {
//                    System.out.println("__  else  ___JSP_____ID_USUARIO_CARGADO____");
//                    // TRAERIA LOS DATOS DEL USUARIO EN CASO DE QUE NO SE ENCUENTRE VACIO EL IDUSUARIO, PARA NO HACER UNA CONSULTA A LA BASE CUANDO NO TIENE IDUSUARIO YA QUE SERIA INNECESARIO 
//                    List<BeanPersona> listaDatos;
//                    listaDatos = metodosUsuario.traer_datos(ID_USUARIO, sesionDatosUsuario);
//                    Iterator<BeanPersona> iterCliente = listaDatos.iterator();
//                    BeanPersona usuario = null;
//                    
//                    while(iterCliente.hasNext()) {
//                        usuario = iterCliente.next();
//                        USU_IDPERSONA = usuario.getRP_IDPERSONA();
//                        USU_NOMBRE = usuario.getRP_NOMBRE();
//                        USU_APELLIDO = usuario.getRP_APELLIDO();
//                        USU_CINRO = usuario.getRP_CINRO();
//                        USU_RAZON_SOCIAL = usuario.getRP_RAZON_SOCIAL();
//                        USU_RUC = usuario.getRP_RUC();
//                        USU_TELEFONO = usuario.getRP_TELFMOVIL();
//                        USU_DIRECCION = usuario.getRP_DIRECCION();
//                        USU_EMAIL = usuario.getRP_EMAIL();
//                        USU_IDUSUARIO = usuario.getSU_IDUSUARIO();
//                        USU_USUARIO = usuario.getSU_USUARIO();
//                        USU_CLAVE = usuario.getSU_CLAVE();
//                        USU_ESTADO = usuario.getSU_ESTADO();
//                        USU_PERFIL = usuario.getSU_DESC_PERFIL();
//                        USU_IDCLINICA = usuario.getRP_IDCLINICA();
//                        // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
//                        USU_NOMBRE_ORI = usuario.getRP_NOMBRE();
//                        USU_APELLIDO_ORI = usuario.getRP_APELLIDO();
//                        USU_CINRO_ORI = usuario.getRP_CINRO();
//                        USU_RAZON_SOCIAL_ORI = usuario.getRP_RAZON_SOCIAL();
//                        USU_RUC_ORI = usuario.getRP_RUC();
//                        USU_TELEFONO_ORI = usuario.getRP_TELFMOVIL();
//                        USU_DIRECCION_ORI = usuario.getRP_DIRECCION();
//                        USU_EMAIL_ORI = usuario.getRP_EMAIL();
//                        USU_IDUSUARIO_ORI = usuario.getSU_IDUSUARIO();
//                        USU_USUARIO_ORI = usuario.getSU_USUARIO();
//                        USU_CLAVE_ORI = usuario.getSU_CLAVE();
//                        USU_ESTADO_ORI = usuario.getSU_ESTADO();
//                        USU_PERFIL_ORI = usuario.getSU_DESC_PERFIL();
//                    } // end while 
//                }
                
                
                List<BeanPersona> datosPaciente = (List<BeanPersona>) request.getAttribute("CU_PAC_LISTA_DATOS");
                // recupero de la lista.-
                if(datosPaciente != null) {
                    BeanPersona usuario = datosPaciente.get(0);
                    USU_IDPERSONA = usuario.getRP_IDPERSONA();
                    USU_NOMBRE = usuario.getRP_NOMBRE();
                    USU_APELLIDO = usuario.getRP_APELLIDO();
                    USU_CINRO = usuario.getRP_CINRO();
                    USU_RAZON_SOCIAL = usuario.getRP_RAZON_SOCIAL();
                    USU_RUC = usuario.getRP_RUC();
                    USU_TELEFONO = usuario.getRP_TELFPARTICULAR();
                    USU_CELULAR = usuario.getRP_TELFMOVIL();
                    USU_DIRECCION = usuario.getRP_DIRECCION();
                    USU_EMAIL = usuario.getRP_EMAIL();
                    USU_IDUSUARIO = usuario.getSU_IDUSUARIO();
                    USU_USUARIO = usuario.getSU_USUARIO();
                    USU_CLAVE = usuario.getSU_CLAVE();
                    USU_ESTADO = usuario.getSU_ESTADO();
                    USU_PERFIL = usuario.getSU_DESC_PERFIL();
                    USU_IDCLINICA = usuario.getRP_IDCLINICA();
                    USU_IDCIUDAD = usuario.getRP_IDCIUDAD();
//                    USU_DESC_CIUDAD = usuario.getRP_DESC_CIUDAD();
                    USU_PROFESION = usuario.getRP_OCUPACION();
                    USU_FECHA_NAC = usuario.getRP_FECHANAC();
                    USU_SEXO = usuario.getRP_SEXO();
                    USU_ESTADO_CIVIL = usuario.getRP_ESTADOCIVIL();
                    USU_TIENE_HIJOS = usuario.getRP_TIENE_HIJOS();
                    USU_CANTIDAD_HIJOS = usuario.getRP_CANT_HIJOS();
                    USU_OTROS_DATOS = usuario.getRP_OBSERVACION();
//                    // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
//                    USU_NOMBRE_ORI = usuario.getRP_NOMBRE();
//                    USU_APELLIDO_ORI = usuario.getRP_APELLIDO();
//                    USU_CINRO_ORI = usuario.getRP_CINRO();
//                    USU_RAZON_SOCIAL_ORI = usuario.getRP_RAZON_SOCIAL();
//                    USU_RUC_ORI = usuario.getRP_RUC();
//                    USU_TELEFONO_ORI = usuario.getRP_TELFMOVIL();
//                    USU_DIRECCION_ORI = usuario.getRP_DIRECCION();
//                    USU_EMAIL_ORI = usuario.getRP_EMAIL();
//                    USU_IDUSUARIO_ORI = usuario.getSU_IDUSUARIO();
//                    USU_USUARIO_ORI = usuario.getSU_USUARIO();
//                    USU_CLAVE_ORI = usuario.getSU_CLAVE();
//                    USU_ESTADO_ORI = usuario.getSU_ESTADO();
//                    USU_PERFIL_ORI = usuario.getSU_DESC_PERFIL();
                }
                
                
//                System.out.println(".");System.out.println(".");System.out.println(".");
//                System.out.println("_   _JPS__ID_USU_PERSONA:   "+USU_IDPERSONA);
//                // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
//                sesionDatosUsuario.setAttribute("ID_USU_PERSONA", USU_IDPERSONA);
                
//                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
//                if ((String)request.getAttribute("CU_CLI_NOMBRE") != null) {
//                    USU_NOMBRE = (String) request.getAttribute("CU_CLI_NOMBRE");
//                }
//                if ((String)request.getAttribute("CU_CLI_APELLIDO") != null) {
//                    USU_APELLIDO = (String) request.getAttribute("CU_CLI_APELLIDO");
//                }
//                if ((String)request.getAttribute("CU_CLI_CINRO") != null) {
//                    USU_CINRO = (String) request.getAttribute("CU_CLI_CINRO");
//                }
//                if ((String)request.getAttribute("CU_CLI_RAZON_SOCIAL") != null) {
//                    USU_RAZON_SOCIAL = (String) request.getAttribute("CU_CLI_RAZON_SOCIAL");
//                }
//                if ((String)request.getAttribute("CU_CLI_RUC") != null) {
//                    USU_RUC = (String) request.getAttribute("CU_CLI_RUC");
//                }
//                if ((String)request.getAttribute("CU_CLI_TELEFONO") != null) {
//                    USU_TELEFONO = (String) request.getAttribute("CU_CLI_TELEFONO");
//                }
//                if ((String)request.getAttribute("CU_CLI_DIRECCION") != null) {
//                    USU_DIRECCION = (String) request.getAttribute("CU_CLI_DIRECCION");
//                }
//                if ((String)request.getAttribute("CU_CLI_EMAIL") != null) {
//                    USU_EMAIL = (String) request.getAttribute("CU_CLI_EMAIL");
//                }
//                if ((String)request.getAttribute("CU_CLI_USUARIO") != null) {
//                    USU_USUARIO = (String) request.getAttribute("CU_CLI_USUARIO");
//                }
//                if ((String)request.getAttribute("CU_CLI_CLAVE") != null) {
//                    USU_CLAVE = (String) request.getAttribute("CU_CLI_CLAVE");
//                }
//                if ((String)request.getAttribute("CU_CLI_PERFIL") != null) {
//                    USU_PERFIL = (String) request.getAttribute("CU_CLI_PERFIL");
//                }
//                if ((String)request.getAttribute("CU_CLI_ESTADO") != null) {
//                    USU_ESTADO = (String) request.getAttribute("CU_CLI_ESTADO");
//                }
//                if ((String)request.getAttribute("CU_CLI_IDCLINICA") != null) {
//                    USU_IDCLINICA = (String) request.getAttribute("CU_CLI_IDCLINICA");
//                }
                
                String btn_cancelar = "Add Usuario"; // OBS.: LE DEJO CON ESTA OPCION, Y NO QUE CARGUE EN EL IF, POR QUE SI SÉ DA LA SITUACION DE AÑADIR UN NUEVO USUARIO Y NO DE EDITAR USUARIO, AL CARGAR UNA PERSONA QUE TENGA USUARIO, AL CARGAR LOS DATOS SE CARGA TAMBIEN EL IDUSUARIO, Y LUEGO YA NO ME RECONOCE EL AÑADIR USUARIO Y AL "CANCELAR" NO ME LIMPIA Y ME SIGUE CARGANDO EL QUE ELEGI Y NO ME DEJA CARGAR OTRO 
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR CLIENTES, ENTONCES DE ACUERDO AL IDCLIENTE VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO CLIENTE O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                if (ID_USUARIO.isEmpty() || ID_USUARIO.equals("")) {
//                    btn_cancelar = "Add Usuario";
                    varTitulo = "USUARIO - NUEVO REGISTRO";
                } else {
//                    btn_cancelar = "Editar";
                    varTitulo = "USUARIO - MODIFICAR REGISTRO";
                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData">
                        <form action="CUSrv" method="post" autocomplete="off">
                        <table>
                            <tbody>
                            <tr>
                                <td>CÓDIGO:</td>
                                <td>
                                    <input type="hidden" value="<%= USU_IDPERSONA %>" id="tIUP" name="tIUP" readonly="readonly" class="form-control" />
                                    <input type="text" value="<%= ID_USUARIO %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Nombres:</td>
                                <td>
                                    <input type="text" value="<%= USU_NOMBRE %>" id="tN" name="tN" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Apellidos:</td>
                                <td>
                                    <input type="text" value="<%= USU_APELLIDO %>" id="tA" name="tA" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Nro. Cédula:</td>
                                <td>
                                    <input type="text" value="<%= USU_CINRO %>" id="tNC" name="tNC" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Teléfono:</td>
                                <td>
                                    <input type="text" value="<%= USU_TELEFONO %>" id="tT" name="tT" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Celular</td>
                                <td>
                                    <input type="text" value="<%= USU_CELULAR %>" id="tCP" name="tCP" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Dirección:</td>
                                <td>
                                    <input type="text" value="<%= USU_DIRECCION %>" id="tD" name="tD" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Email:</td>
                                <td>
                                    <input type="text" value="<%= USU_EMAIL %>" id="tE" name="tE" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Profesión:</td>
                                <td>
                                    <input type="text" value="<%= USU_PROFESION %>" id="tPP" name="tPP" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Fecha Nac.:</td>
                                <td>
                                    <input type="date" value="<%= USU_FECHA_NAC %>" id="tFN" name="tFN" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Sexo:</td>
                                <td>
                                    <select id="cSe" name="cSe" class="form-control">
                                        <%
                                        Map<String, String> listaSexoPac;
                                        listaSexoPac = metodosIniSes.cargarComboSexoPer(USU_SEXO);
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
                                </td>
                            </tr>
                            <tr>
                                <td>Ciudad:</td>
                                <td>
                                    <select id="cCI" name="cCI" class="form-control">
                                        <%
                                        Map<String, String> listaCiudad;
                                        listaCiudad = metodosRefCiudad.cargarComboCiudad(USU_IDCIUDAD);
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
                                </td>
                            </tr>
                            <tr>
                                <td></td>
                                <td>
                                    <%--<div class="pac_panel_3">--%>
                                            <%
                                            if ((ID_USUARIO == null) || (ID_USUARIO.equals(""))) {
                                            %>
                                            <input type="checkbox" id="btn-cli" />
                                            <label for="btn-cli" class="btn btn-info btnCaCli ml-3" style="width: 300px;">Cargar Persona</label>
                                            <%
                                            /*
                                                VENTANA EMERGENTE PARA CARGAR LA PERSONA SEA MEDICO/PACIENTE/SECRETARIO ---------------
                                            */
                                            %>
                                            <form method="post" action="CUSrv">
                                                <div class="modalCl">
                                                    <div class="modalPanelCl">
                                                        <div class="panelHeader">
                                                            <div class="header01">Cargar Persona</div>
                                                            <div class="header02"><label for="btn-cli" class="btnTi">Cerrar</label></div>
                                                        </div>

                                                        <div class="panelBodyCl">
                                                            <div class="boxBody">
                                                                <p class="boxLabel">Personas:</p>
                                                            </div>
                                                            <div class="boxBody boxBodyMiddle">
                                                                <select name="cbxAddNewCli" class="form-control">
                                                                <%
                                                                Map<String, String> listaPersonas;
                                                                String idpersona="";
                                                                listaPersonas = metodosPersona.cargarComboPersonas(idpersona, sesionDatosUsuario); // LE PASO VACIO COMO PARAMETRO PARA QUE ME MUESTRE EL CLIENTE OCASIONAL 
                                                                Set setLisPer = listaPersonas.entrySet();
                                                                Iterator iterLisPer = setLisPer.iterator();
                                                                
                                                                while(iterLisPer.hasNext()) {
                                                                    Map.Entry mapaListPer = (Map.Entry) iterLisPer.next();
                                                                %>
                                                                    <option value="<%= mapaListPer.getKey() %>"><%= mapaListPer.getValue() %></option>
                                                                <%
                                                                }
                                                                %>
                                                                </select>
                                                            </div>
                                                            <div class="boxBody">
                                                                <input type="submit" value="Cargar Persona" name="accion" class="btn btn-info" />
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
                                            %>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td>Login:</td>
                                <td>
                                    <input type="text" value="<%= USU_USUARIO %>" id="tL" name="tL" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Contraseña:</td>
                                <td>
                                    <input type="text" value="<%= USU_CLAVE %>" id="tC" name="tC" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Perfil:</td>
                                <td>
                                <%
                                /*
                                 * OBSERVACION: 
                                    BANDERA QUE UTILIZO PARA SABER SI SE UTILIZO EL BOTON PARA CARGAR LOS DATOS CON LA PERSONA A LA CUAL LE QUIERE CARGAR UN USUARIO. 
                                    ESTA BANDERA ME SERVIRA PARA EVITAR QUE SE LE CAMBIE EL PERFIL, YA QUE EL USUARIO ESTA CARGANDO UNA PERSONA EXISTENTE Y POR LO TANTO YA TIENE UNA CATEGORIA SU PERSONA, QUE VENDRIA SIENDO EL PERFIL, 
                                    Y SI LE PERMITO EDITAR, ENTONCES SE PUEDE DAR EL CASO DE QUE EN LA TABLA USUARIO SEA SECRETARIO Y EN LA TABLA PERSONA TENGA LA CATEGORIA DE MEDICO, Y PARA EVITAR ESTO, ENTONCES NO LE MUESTRO EL COMBO DE PERFILES CUANDO CARGUE LOS DATOS POR MEDIO DEL MODAL DE PERSONAS 
                                */
                                String BAND_PERFIL = (String) request.getAttribute("CU_USU_BAND_PERFIL");
                                if (BAND_PERFIL == null || BAND_PERFIL.isEmpty()) {
                                    BAND_PERFIL = "0";
                                }
                                System.out.println("_   _JSP__BAND_PERFIL:  :"+BAND_PERFIL);
                                
                                // LA BANDERA SOLO MANEJARA LOS VALORES DE CERO O NULO Y UNO 
                                // SI LA BANDERA ES IGUAL A UNO ENTONCES LOS DATOS FUERON CARGADOS POR MEDIO DE UNA PERSONA EXISTENTE Y LE QUIERE EDITAR EL USER O CREAR UN USER 
                                if (BAND_PERFIL.equals("1")) {
                                %>
                                    <input type="text" value="<%= USU_PERFIL %>" name="tUP" readonly="readonly" class="form-control" />
                                <%
                                } else {
                                %>
                                    <select class="form-control" id="cP" name="cP">
                                        <%
                                        Map<String, String> listaPerfil;
                                        listaPerfil = metodosPerfil.cargarComboPerfil(USU_PERFIL);
                                        Set setCbxPerfil = listaPerfil.entrySet();
                                        Iterator iCbxPerfil = setCbxPerfil.iterator();
                                        
                                        while(iCbxPerfil.hasNext()) {
                                            Map.Entry mapCbxPer = (Map.Entry) iCbxPerfil.next();
                                        %>
                                        <option value="<%= mapCbxPer.getKey() %>"><%= mapCbxPer.getValue() %></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                <%
                                }
                                %>
                                </td>
                            </tr>
                            <tr>
                                <td>Clinica:</td>
                                <td>
                                <%
                                /*
                                 * OBSERVACION: LO MISMO QUE PERFIL PERO PARA CLINICA 
                                    BANDERA QUE UTILIZO PARA SABER SI SE UTILIZO EL BOTON PARA CARGAR LOS DATOS CON LA PERSONA A LA CUAL LE QUIERE CARGAR UN USUARIO. 
                                    ESTA BANDERA ME SERVIRA PARA EVITAR QUE SE LE CAMBIE EL PERFIL, YA QUE EL USUARIO ESTA CARGANDO UNA PERSONA EXISTENTE Y POR LO TANTO YA TIENE UNA CATEGORIA SU PERSONA, QUE VENDRIA SIENDO EL PERFIL, 
                                    Y SI LE PERMITO EDITAR, ENTONCES SE PUEDE DAR EL CASO DE QUE EN LA TABLA USUARIO SEA SECRETARIO Y EN LA TABLA PERSONA TENGA LA CATEGORIA DE MEDICO, Y PARA EVITAR ESTO, ENTONCES NO LE MUESTRO EL COMBO DE PERFILES CUANDO CARGUE LOS DATOS POR MEDIO DEL MODAL DE PERSONAS 
                                */
                                String BAND_CLINICA = (String) request.getAttribute("CU_USU_BAND_CLINICA");
                                if (BAND_CLINICA == null || BAND_CLINICA.isEmpty()) {
                                    BAND_CLINICA = "0";
                                }
                                System.out.println("_   _JSP__BAND_CLINICA:  :"+BAND_CLINICA);
                                
                                // LA BANDERA SOLO MANEJARA LOS VALORES DE CERO O NULO Y UNO 
                                // SI LA BANDERA ES IGUAL A UNO ENTONCES LOS DATOS FUERON CARGADOS POR MEDIO DE UNA PERSONA EXISTENTE Y LE QUIERE EDITAR EL USER O CREAR UN USER 
                                if (BAND_CLINICA.equals("1")) {
                                    String DESC_CLINICA = metodosRefClinica.traerDescClinica(USU_IDCLINICA);
                                %>
                                    <input type="hidden" value="<%= USU_IDCLINICA %>" name="tUIC" readonly="readonly" class="form-control" />
                                    <input type="text" value="<%= DESC_CLINICA %>" name="tUDC" readonly="readonly" class="form-control" />
                                <%
                                } else {
                                %>
                                    <select class="form-control" id="cCli" name="cCli">
                                        <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosRefClinica.cargarComboClinica(USU_IDCLINICA);
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
                                <%
                                }
                                %>
                                </td>
                            </tr>
                            <tr>
                                <td>Estado:</td>
                                <td>
                                    <select class="form-control" id="cE" name="cE">
                                        <%
                                        Map<String, String> listaEstado;
                                        listaEstado = metodosIniSes.cargarComboEstado(USU_ESTADO);
                                        Set setCbxEstado = listaEstado.entrySet();
                                        Iterator iCbxEstado = setCbxEstado.iterator();
                                        
                                        while(iCbxEstado.hasNext()) {
                                            Map.Entry mapCbxEst = (Map.Entry) iCbxEstado.next();
                                        %>
                                        <option value="<%= mapCbxEst.getKey() %>"><%= mapCbxEst.getValue() %></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" name="accion" value="GRABAR" class="btn btn-outline-primary" />
                                    <button type="submit" name="accion" value="<%= btn_cancelar %>" class="btn btn-primary mt-3 mr-2">CANCELAR</button>
                                    <%//<a href="javascript:cancelar()" class="btn btn-primary">CANCELAR</a>%>
                                    <a href="<%= urlUsuario %>" class="btn btn-danger">VOLVER</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </form>
                    </div>
                </div>
            <%
                }
            %>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script type="text/javascript">
            function cancelar() {
                document.getElementById("tN").value = "<%= USU_NOMBRE_ORI %>";
                document.getElementById("tA").value = "<%= USU_APELLIDO_ORI %>";
                document.getElementById("tNC").value = "<%= USU_CINRO_ORI %>";
                document.getElementById("tRS").value = "<%= USU_RAZON_SOCIAL_ORI %>";
                document.getElementById("tR").value = "<%= USU_RUC_ORI %>";
                document.getElementById("tT").value = "<%= USU_TELEFONO_ORI %>";
                document.getElementById("tD").value = "<%= USU_DIRECCION_ORI %>";
                document.getElementById("tE").value = "<%= USU_EMAIL_ORI %>";
                document.getElementById("tL").value = "<%= USU_USUARIO_ORI %>";
                document.getElementById("tC").value = "<%= USU_CLAVE_ORI %>";
                document.getElementById("cE").value = "A"; <% // SELECCIONO EL VALOR ACTIVO SUPONINEDO QUE LOS VALORES SON POR LA INICIAL Y NO POR EL TEXTO COMPLETO %>
                document.getElementById("cCli").value = "0";
                document.getElementById("cP").value = "FUNCIONARIO";
            }
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>