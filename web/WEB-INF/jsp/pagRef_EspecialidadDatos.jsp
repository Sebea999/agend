<%-- 
    Document   : pagRef_EspecialidadDatos
    Created on : 24-ene-2022, 12:50:46
    Author     : RYUU
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="com.agend.javaBean.BeanEspecialidad"%>
<%@page import="com.agend.modelo.ModelRef_Especialidad"%>
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
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Especialidad</title>
        <%@include file="menuJspCssStatic.jsp" %> <%-- // INCLUYO LOS LINKS CSS QUE SON COMUNES EN CADA PAGINA --%>
<%--        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">   --%>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.min.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                <%
                ModelRef_Especialidad metodosEspecialidad = new ModelRef_Especialidad();
                
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CRE_MENSAJE"); // CONTROLLER REFERENCIA ESPECIALIDAD MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CRE_TIPO_MENSAJE"); // CONTROLLER REFERENCIA ESPECIALIDAD TIPO MENSAJE 
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
                /*
                    * OBSERVACION: PARA AÑADIR CLINICA Y EDITAR CLINICA 
                    UTILIZO LA MISMA PAGINA QUE ES ESTA, ENTONCES LA MANERA DE DIFERENCIAR QUE ACCION 
                    QUIERO REALIZAR SERA POR MEDIO DEL ID_CLINICA, YA QUE AL AÑADIR ESTE NO TENDRA DATO 
                    Y AL EDITAR SI TENDRA UN VALOR QUE RECUPERO DE LA GRILLA 
                */
                String ID_ESPECIALIDAD = (String) sesionDatosUsuario.getAttribute("ID_ESPECIALIDAD");
                System.out.println("_jsp___ID_ESPECIALIDAD:  "+ID_ESPECIALIDAD);
                
                List<BeanEspecialidad> listaDatos;
                listaDatos = metodosEspecialidad.traer_datos(ID_ESPECIALIDAD, sesionDatosUsuario);
                Iterator<BeanEspecialidad> iterEspecialidad = listaDatos.iterator();
                BeanEspecialidad espe_bean = null;
                // VARIABLES QUE REPRESENTAN LAS COLUMNAS DE LA TABLA  
                String ESP_DESCRIPCION="", ESP_ESTADO="A", ESP_MONTO="0", ESP_IVA="0";
                String ESP_DESCRIPCION_ORI="";
                
                while(iterEspecialidad.hasNext()) {
                    espe_bean = iterEspecialidad.next();
                    ESP_DESCRIPCION = espe_bean.getRHE_DESC_ESPECIALIDAD();
                    ESP_ESTADO = espe_bean.getRHE_ESTADO();
                    ESP_MONTO = espe_bean.getRHE_MONTO();
                    ESP_IVA = espe_bean.getRHE_IVA();
                    // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
                    ESP_DESCRIPCION_ORI = espe_bean.getRHE_DESC_ESPECIALIDAD();
                } // end while 
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CRE_ESPECIALIDAD_DESC") != null) {
                    ESP_DESCRIPCION = (String) request.getAttribute("CRE_ESPECIALIDAD_DESC");
                }
                if ((String)request.getAttribute("CRE_ESTADO") != null) {
                    ESP_ESTADO = (String) request.getAttribute("CRE_ESTADO");
                }
                if ((String)request.getAttribute("CRE_MONTO") != null) {
                    ESP_MONTO = (String) request.getAttribute("CRE_MONTO");
                }
                if ((String)request.getAttribute("CRE_IVA") != null) {
                    ESP_IVA = (String) request.getAttribute("CRE_IVA");
                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR LOS PRODUCTOS, ENTONCES DE ACUERDO AL IDPRODUCTO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO PRODUCTO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "";
                String btnName="", btnCss=""; // VARIABLES QUE UTILIZO PARA DEFINIR EL BOTON, SI VA A SER PARA GUARDAR O MODIFICAR DEPENDIENDO DEL IDPRODUCTO 
                if (ID_ESPECIALIDAD.isEmpty() || ID_ESPECIALIDAD.equals("")) {
                    varTitulo = "ESPECIALIDAD - NUEVO REGISTRO";
                    btnName = "GRABAR";
                    btnCss = "btn btn-primary";
                } else {
                    varTitulo = "ESPECIALIDAD - MODIFICAR REGISTRO";
                    btnName = "MODIFICAR";
                    btnCss = "btn btn-warning";
                }
                %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData">
                        <form action="CRESrv" method="post" autocomplete="off">
                        <table>
                            <tbody>
                            <tr>
                                <td>CÓDIGO:</td>
                                <td>
                                    <input type="text" value="<%= ID_ESPECIALIDAD %>" readonly="readonly" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Descripción:</td>
                                <td>
                                    <input type="text" value="<%= ESP_DESCRIPCION %>" id="tDC" name="tDC" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>Monto:</td>
                                <td>
                                    <input type="text" value="<%= ESP_MONTO %>" id="tM" name="tM" class="form-control" />
                                </td>
                            </tr>
                            <tr>
                                <td>IVA</td>
                                <td>
                                    <select name="cI" id="cI" class="form-control cbxIva">
                                        <%
                                        Map<String, String> listaIva;
                                        listaIva = metodosIniSes.cargarComboIVA(ESP_IVA);
                                        Set setCbxIva = listaIva.entrySet();
                                        Iterator iCbxIva = setCbxIva.iterator();

                                        while(iCbxIva.hasNext()) {
                                            Map.Entry mapCbxIva = (Map.Entry) iCbxIva.next();
                                        %>
                                        <option value="<%= mapCbxIva.getKey() %>"><%= mapCbxIva.getValue() %></option>
                                        <%
                                        }
                                        %>
                                    </select>
                                </td>
                            </tr>
                            <tr>
                                <td>Estado:</td>
                                <td>
                                    <select id="cE" name="cE" class="form-control">
                                        <%
                                        Map<String, String> listaEstado;
                                        listaEstado = metodosIniSes.cargarComboEstado(ESP_ESTADO);
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
                                    <%
                                    /*
                                    * OBSERVACION: 
                                        VALIDACIONES PARA PODER CARGAR EL BOTON DE: "ELIMINAR"; 
                                        COLOQUE ESTOS BOTONES ACA PARA PODER ACTIVAR EL MENSAJE DE CONSULTA YA QUE EN SUS PAGINAS NO PODIA MOSTRAR EL MENSAJE EN TODOS LOS CASOS SOLO LO MOSTRABA CUANDO FILTRABA POR UN EMPEÑO Y NO SE ACTIVABA EL MENSAJE CON LA LISTA COMPLETA 
                                    */
                                    System.out.println(".");System.out.println(".");System.out.println(".");
                                    // VARIABLES BANDERA 
                                    int varMostrarBtns = 0;
                                    int varEliminar = 0;
                                    
                                    // VARIABLES PARA COLOCAR LOS NOMBRES DE LOS EVENTOS DEL CONTROLADOR QUE CORRESPONDE 
                                    String varAccionBtn = "";
                                    
                                    /*
                                    OBSERVACION: LA IDEA ES QUE CADA BOTON TENGA UNA VARIABLE EN LA SESION Y ACTIVARLOS DESDE LOS CONTROLADORES Y PARA SABER DE QUE PAGINA VIENE O LA ACTIVO, ME FIJARE EN SU VALOR COMO EL BOTON DE "VOLVER ATRAS" 
                                    */
                                    // VARIABLES DE LA SESION 
                                    String btnReqEliminar = (String) request.getAttribute("REFESP_BTN_ELIMINAR");
                                    System.out.println("___JSP___BTN_ELIMINAR:   :"+btnReqEliminar);
                                    
                                    // VARIABLES PARA CARGAR EL MENSAJE DE JAVASCRIPT 
                                    String labelName = "";
                                    String labelMensajeModal = "";
                                    String labelTextAdvertencia = "Esta acción ya no se podrá deshacer, Así que piénsalo bien.";  // TEXTO SECUNDARIO QUE APARECE ABAJO QUE LO USO PARA DAR UNA ADVERTENCIA AL USUARIO // OBS.: LO DEJO CARGADO Y EN CASO DE QUE NO QUIERA QUE ME MUESTRE EL MENSAJE DE ADVERTENCIA, EN EL IF DEL BOTON QUE QUIERA LO DEJO VACIO O LO CAMBIO 
                                    
                                    // VALIDACIONES PARA ACTIVAR LOS BOTONES 
                                    // BOTON DE ANULAR 
                                    if(btnReqEliminar == null || btnReqEliminar.isEmpty()) {
                                        //
                                    } else if(btnReqEliminar.equalsIgnoreCase("1")) {
                                        varMostrarBtns = 1;
                                        varEliminar = 1;
                                        varAccionBtn = "accion";
                                        labelName = "EliminarSeg";
                                        labelMensajeModal = "¿Esta seguro que desea eliminar el registro?";
                                    }
                                    
                                    System.out.println(".");
                                    System.out.println(".");
                                    System.out.println("___VAR_BANDERA_BOTONES:  "+varMostrarBtns);
                                    System.out.println("___VAR_ELIMINAR  :  "+varEliminar);
                                    System.out.println("___VAR_ACCION_BTN:  "+varAccionBtn);
                                    System.out.println("___LABEL_NAME:  "+labelName);
                                    System.out.println("___LABEL_MENS:  "+labelMensajeModal);
                                    System.out.println("___LABEL_ADVE:  "+labelTextAdvertencia);
                                    System.out.println(".");
                                    System.out.println(".");
                                    
                                    if (varMostrarBtns == 0) { // CONTROLO LA VARIABLE QUE UTILIZO COMO BANDERA Y SI NO SE ACTIVO EL BOTON DE ELIMINAR ENTONES LE MUESTRO LOS OTROS BOTONES 
                                    %>
                                    <input type="submit" name="accion" value="<%= btnName %>" class="<%= btnCss %>" />
                                    <a href="javascript:cancelar()" class="btn btn-primary">LIMPIAR</a>
                                    <%
                                    } else if(varMostrarBtns > 0) { // MOSTRAR SOLAMENTE EL BOTON DE ELIMINAR 
                                        if(varEliminar > 0) {
                                    %>
                                        <input type="hidden" value="<%= ID_ESPECIALIDAD %>" name="tI" class="form-control disNone" />
                                        <input type="submit" value="Eliminar" id="btnPreAux" class="btn btn-warning" />
                                        <input type="submit" value="<%= labelName %>" name="<%= varAccionBtn %>" id="btnAuxiliar" class="btn btn-warning disNone" />
                                    <%
                                        } // end if var_eliminar 
                                    } // end else-if bandera 
                                    System.out.println(".");System.out.println(".");System.out.println(".");
                                    %>
                                    <a href="<%= urlRefEspecialidad %>" class="btn btn-danger">VOLVER</a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script type="text/javascript">
            function cancelar() {
                document.getElementById("tDC").value = "<%= ESP_DESCRIPCION_ORI %>";
                document.getElementById("cE").value = "A"; <% // SELECCIONO EL VALOR ACTIVO SUPONINEDO QUE LOS VALORES SON POR LA INICIAL Y NO POR EL TEXTO COMPLETO %>
            }
            <%
            //  CODIGO QUE UTILIZO PARA PODER INVOCAR AL MENSAJE PARA CONSULTARLE SI ESTA SEGURO DE QUE DESEA HACER LA ACCION 
            %>
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
                            console.log('prueba exitosa - pre eliminar');
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
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>