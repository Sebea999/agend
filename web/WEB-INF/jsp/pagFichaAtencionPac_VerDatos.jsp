<%-- 
    Document   : pagFichaAtencionPac_Datos
    Created on : 23-feb-2022, 14:15:29
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanServicio"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.agend.javaBean.BeanAtencionPaciente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ficha de Atención</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleAtencion.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CFAP_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CFAP_TIPO_MENSAJE"); // CONTROLLER FICHA ATENCION PACIENTE TIPO MENSAJE 
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
                
                if(mensaje != null) {
                %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
                <%
                }
                %>
                <%
                String ID_ATENCION = (String) sesionDatosUsuario.getAttribute("ID_ATENCION_PAC");
                System.out.println("_jsp___ID_ATENCION_PAC:   :"+ID_ATENCION);
                String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("CFAP_IDAGENDAMIENTO");
                System.out.println("_jsp___ID_AGENDAMIENTO:   :"+ID_AGENDAMIENTO);
                String ITEM_AGEND = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_AGEND");
                System.out.println("_jsp___ITEM_AGENDAMIENTO_DETALLE:   :"+ITEM_AGEND);
                String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("CFAP_IDPACIENTE");
                System.out.println("_jsp___ID_PACIENTE:       :"+ID_PACIENTE);
                
//                List<BeanAtencionPaciente> listaDatos;
//                listaDatos = metodosFichaAtencionPac.traer_datos(ID_ATENCION, sesionDatosUsuario);
//                Iterator<BeanAtencionPaciente> iterAtencion = listaDatos.iterator();
//                BeanAtencionPaciente bean_datos = null;
                
                // VARIABLES QUE REPRESENTAN LAS COLUMNAS DE LA TABLA  
                String TXT_PESO="", TXT_TALLA="", TXT_IMC="", TXT_PCEFALICO="", TXT_FC="", TXT_TEMP="", TXT_PA="", TXT_FRESP="", TXT_MOTIVO_CONSULTA="", TXT_EXPLORACION_FISICA="", TXT_DIAGNOSTICO="", TXT_TRATAMIENTO="", TXT_RECETA="", TXT_ESTUDIOS_SOLICITADOS="";
//                String SER_DESCRIPCION_ORI="", SER_MONTO_ORI="";
//                
//                while(iterAtencion.hasNext()) {
////                    bean_datos = iterAtencion.next();
////                    SER_DESCRIPCION = bean_datos.get;
////                    SER_MONTO = bean_datos.getRHS_MONTO();
////                    SER_ESTADO = bean_datos.getRHS_ESTADO();
////                    // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
////                    SER_DESCRIPCION_ORI = bean_datos.getRHS_DESC_SERVICIO();
////                    SER_MONTO_ORI = bean_datos.getRHS_MONTO();
//                } // end while 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CFAP_TXT_PESO") != null) {
                    TXT_PESO = (String) request.getAttribute("CFAP_TXT_PESO");
                }
                if ((String)request.getAttribute("CFAP_TXT_TALLA") != null) {
                    TXT_TALLA = (String) request.getAttribute("CFAP_TXT_TALLA");
                }
                if ((String)request.getAttribute("CFAP_TXT_IMC") != null) {
                    TXT_IMC = (String) request.getAttribute("CFAP_TXT_IMC");
                }
                if ((String)request.getAttribute("CFAP_TXT_PCEFALICO") != null) {
                    TXT_PCEFALICO = (String) request.getAttribute("CFAP_TXT_PCEFALICO");
                }
                if ((String)request.getAttribute("CFAP_TXT_FC") != null) {
                    TXT_FC = (String) request.getAttribute("CFAP_TXT_FC");
                }
                if ((String)request.getAttribute("CFAP_TXT_TEMP") != null) {
                    TXT_TEMP = (String) request.getAttribute("CFAP_TXT_TEMP");
                }
                if ((String)request.getAttribute("CFAP_TXT_PA") != null) {
                    TXT_PA = (String) request.getAttribute("CFAP_TXT_PA");
                }
                if ((String)request.getAttribute("CFAP_TXT_FRESP") != null) {
                    TXT_FRESP = (String) request.getAttribute("CFAP_TXT_FRESP");
                }
                // ------------------------ ---------------------- ----------------------- -------------------
                if ((String)request.getAttribute("CFAP_TXT_MOTIVO_CONSULTA") != null) {
                    TXT_MOTIVO_CONSULTA = (String) request.getAttribute("CFAP_TXT_MOTIVO_CONSULTA");
                }
                if ((String)request.getAttribute("CFAP_TXT_EXPLORACION_FISICA") != null) {
                    TXT_EXPLORACION_FISICA = (String) request.getAttribute("CFAP_TXT_EXPLORACION_FISICA");
                }
                if ((String)request.getAttribute("CFAP_TXT_DIAGNOSTICO") != null) {
                    TXT_DIAGNOSTICO = (String) request.getAttribute("CFAP_TXT_DIAGNOSTICO");
                }
                if ((String)request.getAttribute("CFAP_TXT_TRATAMIENTO") != null) {
                    TXT_TRATAMIENTO = (String) request.getAttribute("CFAP_TXT_TRATAMIENTO");
                }
                if ((String)request.getAttribute("CFAP_TXT_RECETA") != null) {
                    TXT_RECETA = (String) request.getAttribute("CFAP_TXT_RECETA");
                }
                if ((String)request.getAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS") != null) {
                    TXT_ESTUDIOS_SOLICITADOS = (String) request.getAttribute("CFAP_TXT_ESTUDIOS_SOLICITADOS");
                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR LOS PRODUCTOS, ENTONCES DE ACUERDO AL IDPRODUCTO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO PRODUCTO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                String btnName="", btnCss=""; // VARIABLES QUE UTILIZO PARA DEFINIR EL BOTON, SI VA A SER PARA GUARDAR O MODIFICAR DEPENDIENDO DEL IDPRODUCTO 
//                if (ID_ATENCION.isEmpty() || ID_ATENCION.equals("")) {
//                    varTitulo = "ATENCIÓN - NUEVO REGISTRO";
//                    btnName = "Guardar";
//                    btnCss = "btn btn-outline-success";
//                } else {
                    varTitulo = "ATENCIÓN - VER REGISTRO";
//                    varTitulo = "ATENCIÓN - MODIFICAR REGISTRO";
//                    btnName = "Modificar";
//                    btnCss = "btn btn-outline-warning";
//                }
                %>
                <div class="content-atencion">
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData">
                        <%
                        // TRAER DATOS DEL PACIENTE QUE SE LE VA A CARGAR LA FICHA DE ATENCION 
                        BeanPersona datosBPaciente = new BeanPersona();
                        datosBPaciente = metodosPersona.datosBasicosPersona(datosBPaciente, ID_PACIENTE);
                        String PACIENTE =  datosBPaciente.getRP_NOMBRE()+" "+datosBPaciente.getRP_APELLIDO();
                        System.out.println("_   _JSP____PACIENTE:      :"+PACIENTE);
                        %>
                        <div>
                            <div class="caNaPac">
                                <p>Paciente: </p><p><%= PACIENTE %></p>
                            </div>
                            <%--<form action="CFAPSrv" method="post" autocomplete="off">--%>
                            <div class="contenedor-cajas">
                                <div class="c1">
                                    <div class="c1Ti">Parámetros Opcionales</div>
                                    <div class="c1Co">
                                        <div>
                                            <p>Peso</p>
                                            <input type="text" value="<%= TXT_PESO %>" name="tPe" placeholder="en Kgr." readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>Talla</p>
                                            <input type="text" value="<%= TXT_TALLA %>" name="tTa" placeholder="en Cm." readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>IMC</p>
                                            <input type="text" value="<%= TXT_IMC %>" name="tIMC" readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>P. Cefálico</p>
                                            <input type="text" value="<%= TXT_PCEFALICO %>" name="tPC" readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>FC</p>
                                            <input type="text" value="<%= TXT_FC %>" name="tFC" readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>Temp.</p>
                                            <input type="text" value="<%= TXT_TEMP %>" name="tTe" readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>PA</p>
                                            <input type="text" value="<%= TXT_PA %>" name="tPA" readonly="readonly" class="form-control" />
                                        </div>
                                        <div>
                                            <p>F. Resp.</p>
                                            <input type="text" value="<%= TXT_FRESP %>" name="tFR" readonly="readonly" class="form-control" />
                                        </div>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Motivo de Consulta/Interrogatorio</div>
                                    <div class="c1Co">
                                        <div>
                                            Motivo de la Consulta:
                                        </div>
                                        <div>
                                            <textarea id="tAMC" name="tAMC" rows="4" readonly="readonly" cols="70"><%= TXT_MOTIVO_CONSULTA %></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Exploración Física</div>
                                    <div class="c1Co">
                                        <div>
                                            Exploración Física:
                                        </div>
                                        <div>
                                            <textarea id="tAEF" name="tAEF" rows="4" readonly="readonly" cols="70"><%= TXT_EXPLORACION_FISICA %></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Diagnóstico</div>
                                    <div class="c1Co">
                                        <div>
                                            Diagnóstico:
                                        </div>
                                        <div>
                                            <textarea id="tAD" name="tAD" rows="4" readonly="readonly" cols="70"><%= TXT_DIAGNOSTICO %></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Tratamiento</div>
                                    <div class="c1Co">
                                        <div>
                                            Tratamiento:
                                        </div>
                                        <form action="tratamiento_pdf" target="_blank" method="post"> <% // SERVLET DEL CONTROLADOR QUE SE ENCARGA DE CREAR EL REPORTE PDF CON LOS DATOS DEL TRATAMIENTO %>
                                        <div>
                                            <textarea id="tAT" name="tAT" rows="4" readonly="readonly" cols="70"><%= TXT_TRATAMIENTO %></textarea>
                                        </div>
                                        <div>
                                            <%--<button type="submit" value="Imprimir Tratamiento" name="accion" class="btn btn-outline-danger">Imprimir</button>--%>
                                            <button type="submit" value="Imprimir Tratamiento" name="accion" class="btn btn-outline-danger">Imprimir</button>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Receta</div>
                                    <div class="c1Co">
                                        <div>
                                            Receta:
                                        </div>
                                        <form action="receta_pdf" target="_blank" method="post">
                                        <div>
                                            <textarea id="tAR" name="tAR" rows="4" readonly="readonly" cols="70"><%= TXT_RECETA %></textarea>
                                        </div>
                                        <div>
                                            <button type="submit" value="Imprimir Receta" name="accion" class="btn btn-outline-danger">Imprimir</button>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Estudios Solicitados/Realizados</div>
                                    <div class="c1Co">
                                        <div>
                                            Estudios Solicitados:
                                        </div>
                                        <form action="estudios_solicitados_pdf" target="_blank" method="post">
                                        <div>
                                            <textarea id="tAES" name="tAES" rows="4" readonly="readonly" cols="70"><%= TXT_ESTUDIOS_SOLICITADOS %></textarea>
                                        </div>
                                        <div>
                                            <button type="submit" value="Imprimir Estudios Solicitados" name="accion" class="btn btn-outline-danger">Imprimir</button>
                                        </div>
                                        </form>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Servicio/Proc.</div>
                                    <div class="c1Co">
                                        <%--<div class="c1CoCBS">--%>
                                            <%--<select name="cbServ" class="form-control">--%>
                                                <%
//                                                Map<String, String> listaServicios = new LinkedHashMap<String, String>();
//                                                listaServicios = metodosRefServicio.cargarComboServicio("", sesionDatosUsuario); // LE PASO VACIO EL IDSERVICIO YA QUE NO TENGO LA NECESIDAD DE COLOCAR O MOSTRARLE UN ELEMENTO EN ESPECIAL DENTRO DEL COMBO Y NO IMPORTA QUE SE RESETE LA LISTA DEL COMBO 
//                                                Set setListServ = listaServicios.entrySet();
//                                                Iterator iListServ = setListServ.iterator();
//                                                
//                                                while(iListServ.hasNext()) {
//                                                    Map.Entry mapServ = (Map.Entry) iListServ.next();
                                                %>
                                                <%--<option value="<%= mapServ.getKey() %>"><%= mapServ.getValue() %></option>--%>
                                                <%
//                                                }
                                                %>
                                            <%--</select>--%>
                                            <%--<input type="submit" value="Agregar Servicio" name="accion" class="btn btn-success btnAS"/>--%>
                                        <%--</div>--%>
                                        <div class="c1CoGS mt-2">
                                        <%
                                        if(((List<BeanAtencionPaciente>)sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS")).size() > 0) {
                                        %>
                                            <table role="table" class="tableExample" style="margin:0px; margin-left: 10px;">
                                                <thead role="rowgroup">
                                                    <tr role="row">
                                                        <td role="columnheader" class="TSCBE">  </td>
                                                        <%--<td role="columnheader" class="TSCI">#</td>--%>
                                                        <td role="columnheader" class="TSCDS">Servicio</td>
                                                        <td role="columnheader" class="TSCM">Monto</td>
                                                        <%--<td role="columnheader" class="TSCBE">Acción</td>--%>
                                                    </tr>
                                                </thead>
                                                <tbody role="rowgroup">
                                                    <%
                                                        DecimalFormat formatear = new DecimalFormat("###,###,###");
                                                        // ITEM 
                                                        String CFAP_LISTA_ITEM = (String) sesionDatosUsuario.getAttribute("CFAP_ITEM_LISTA_SERVICIOS");
                                                        System.out.println("__JSP__CFAP_ITEM_LISTA_SERVICIOS:   :"+CFAP_LISTA_ITEM);
                                                        sesionDatosUsuario.setAttribute("CFAP_ITEM_LISTA_SERVICIOS", CFAP_LISTA_ITEM);
                                                        // LISTA 
                                                        List<BeanAtencionPaciente> CFAP_LISTA_DETALLE;
                                                        CFAP_LISTA_DETALLE = (List<BeanAtencionPaciente>) sesionDatosUsuario.getAttribute("CFAP_LISTA_SERVICIOS");
                                                        Iterator<BeanAtencionPaciente> iterLista = CFAP_LISTA_DETALLE.iterator();
                                                        BeanAtencionPaciente datosTab = new BeanAtencionPaciente();
                                                        int item = 0;
                                                        
                                                        while(iterLista.hasNext()) {
                                                            item = item + 1;
                                                            
                                                            datosTab = iterLista.next();
                                                            
                                                            String DESC_SERV = datosTab.getRHS_DESC_SERVICIO();
                                                            if (DESC_SERV == null || DESC_SERV.isEmpty()) {
                                                                BeanServicio beanServ = new BeanServicio();
                                                                beanServ = metodosRefServicio.devolverDatosServicios(datosTab.getOAPD_IDSERVICIO());
                                                                DESC_SERV = beanServ.getRHS_DESC_SERVICIO();
                                                            }
                                                            String MONTO_SERV = datosTab.getOAPD_MONTO();
                                                            if (MONTO_SERV == null || MONTO_SERV.isEmpty()) {
                                                                MONTO_SERV = "0";
                                                            }
                                                            String ITEM = datosTab.getOAPD_ITEM();
                                                            System.out.println("_   __ITEM:  :"+ITEM);
                                                    %>
                                                    <tr role="row">
                                                        <td <%//data-label="Eliminar: "%> role="cell" class="TSCBE">
                                                            <span>
                                                            <%= item %>
                                                            </span>
                                                        </td>
                                                        <td data-label="Servicio: " role="cell" class="TSCDS"><span><%=DESC_SERV%></span></td>
                                                        <td data-label="Monto: " role="cell" class="TSCM"><span><%=formatear.format(Double.parseDouble(MONTO_SERV)) %></span></td>
                                                    </tr>
                                                    <%
                                                        }
                                                    %>
                                                </tbody>
                                            </table>
                                        <%
                                            }
                                        %>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="btns_atencion">
                            <%
                                String volver_atras = urlFichaAtencionPaciente;
                                String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS");
                                System.out.println("_   __JSP___BTN_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                                if (BTN_VOLVER_ATRAS == null) {
                                    System.out.println("_   _______IS__NULL_______");
                                    if (metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true || metodosPerfil.isPerfilMedico(idPerfil)==true) {
                                        System.out.println("_   _   __IF____");
                                        volver_atras = urlFichaAtencionPaciente;
                                    } else {
                                        System.out.println("_   _   __ELSE____");
                                        volver_atras = urlPrincipal;
                                    }
                                }
                                System.out.println("_   __BTN_VOLVER_ATRAS:     :"+BTN_VOLVER_ATRAS);
                                
                                if (BTN_VOLVER_ATRAS.equals("VER_PACIENTE")) { // VIENE DE LA VENTANA DE "VER EXPEDIENTE" DE PACIENTE 
                                    volver_atras = "ver_paciente.htm";
                                } else if (BTN_VOLVER_ATRAS.equals("PERFIL_PACIENTE")) { // VIENE DE LA PAGINA DE PERFIL SIENDO EL USUARIO UN PERFIL PACIENTE 
                                    volver_atras = "perfil_pac.htm";
                                } else if (BTN_VOLVER_ATRAS.equals("VER_CAB_AGENDAMIENTO")) { // VIENE DE LA PAGINA DE VER AGENDAMIENTO 
                                    volver_atras = "ver_agend.htm";
                                }
                            %>
                                <a href="<%= volver_atras %>" class="btn btn-danger">Volver Atras</a>
                            </div>
                            <%--</form>--%>
                        </div>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configFichaAtePac.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <!--<script src="${pageContext.request.contextPath}/recursos/js/configFichaAtePac.js" type="text/javascript"></script>-->
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>