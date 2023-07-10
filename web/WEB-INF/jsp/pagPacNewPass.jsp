<%-- 
    Document   : pagPacNewPass
    Created on : 21-oct-2022, 11:48:39
    Author     : RYUU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paciente</title>
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
                String mensaje = (String) request.getAttribute("CP_UP_MENSAJE"); // CONTROLLER PACIENTE update password MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CP_UP_TIPO_MENSAJE"); // CONTROLLER PACIENTE update password TIPO MENSAJE 
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
                <div class="content-over">
                <%
                    String ID_PACIENTE = (String) sesionDatosUsuario.getAttribute("ID_PACIENTE");
                    System.out.println("_jsp___ID_PACIENTE:  "+ID_PACIENTE);
                    
                    List<BeanPersona> listaDatos;
                    String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_SEGURO_MEDICO="", PAC_OCUPACION="", PAC_ANTECEDENTES_FAMILIARES="";
                    String PAC_USER_ACTUAL="", ACTUAL_PASS="", VAR_NEW_PASS="", VAR_REP_NEW_PASS="";
                    
//                    // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
//                    if (ID_PACIENTE.equals("0") || ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
//                        // ... 
//                    } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
//                        System.out.println("__  else  ___JSP_____ID_USUARIO_CARGADO____");
//                        // TRAERIA LOS DATOS DEL USUARIO EN CASO DE QUE NO SE ENCUENTRE VACIO EL IDUSUARIO, PARA NO HACER UNA CONSULTA A LA BASE CUANDO NO TIENE IDUSUARIO YA QUE SERIA INNECESARIO 
//                        listaDatos = metodosUsuario.traer_datos_idpac(ID_PACIENTE, sesionDatosUsuario);
//                        Iterator<BeanPersona> iterCliente = listaDatos.iterator();
//                        BeanPersona usuario = null;
//                        
//                        while(iterCliente.hasNext()) {
//                            usuario = iterCliente.next();
////                            USU_IDPERSONA = usuario.getRP_IDPERSONA();
//                            PAC_NOMBRE = usuario.getRP_NOMBRE();
//                            PAC_APELLIDO = usuario.getRP_APELLIDO();
//                            PAC_CINRO = usuario.getRP_CINRO();
////                            PAC_RAZON_SOCIAL = usuario.getRP_RAZON_SOCIAL();
////                            PAC_RUC = usuario.getRP_RUC();
////                            PAC_TELEFONO = usuario.getRP_TELFMOVIL();
////                            PAC_DIRECCION = usuario.getRP_DIRECCION();
////                            PAC_EMAIL = usuario.getRP_EMAIL();
////                            USU_IDUSUARIO = usuario.getSU_IDUSUARIO();
//                            PAC_USER_ACTUAL = usuario.getSU_USUARIO();
//                            ACTUAL_PASS = usuario.getSU_CLAVE();
////                            USU_ESTADO = usuario.getSU_ESTADO();
////                            USU_PERFIL = usuario.getSU_DESC_PERFIL();
////                            USU_IDCLINICA = usuario.getRP_IDCLINICA();
////                            // CARGO LAS VARIABLES QUE UTILIZARE CUANDO SE DE CLICK AL BOTON DE CANCELAR 
////                            USU_NOMBRE_ORI = usuario.getRP_NOMBRE();
////                            USU_APELLIDO_ORI = usuario.getRP_APELLIDO();
////                            USU_CINRO_ORI = usuario.getRP_CINRO();
////                            USU_RAZON_SOCIAL_ORI = usuario.getRP_RAZON_SOCIAL();
////                            USU_RUC_ORI = usuario.getRP_RUC();
////                            USU_TELEFONO_ORI = usuario.getRP_TELFMOVIL();
////                            USU_DIRECCION_ORI = usuario.getRP_DIRECCION();
////                            USU_EMAIL_ORI = usuario.getRP_EMAIL();
////                            USU_IDUSUARIO_ORI = usuario.getSU_IDUSUARIO();
////                            USU_USUARIO_ORI = usuario.getSU_USUARIO();
////                            USU_CLAVE_ORI = usuario.getSU_CLAVE();
////                            USU_ESTADO_ORI = usuario.getSU_ESTADO();
////                            USU_PERFIL_ORI = usuario.getSU_DESC_PERFIL();
//                        } // end while 
//                    } // END ELSE 
                    
                    
                    // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                    if ((String)request.getAttribute("CP_PAC_IDUSUARIO") != null) {
                        PAC_IDUSUARIO = (String) request.getAttribute("CP_PAC_IDUSUARIO");
                    }
                    if ((String)request.getAttribute("CP_PAC_NOMBRE") != null) {
                        PAC_NOMBRE = (String) request.getAttribute("CP_PAC_NOMBRE");
                    }
                    if ((String)request.getAttribute("CP_PAC_APELLIDO") != null) {
                        PAC_APELLIDO = (String) request.getAttribute("CP_PAC_APELLIDO");
                    }
                    if ((String)request.getAttribute("CP_PAC_CINRO") != null) {
                        PAC_CINRO = (String) request.getAttribute("CP_PAC_CINRO");
                    }
                    if ((String)request.getAttribute("CP_PAC_USUARIO_ACTUAL") != null) {
                        PAC_USER_ACTUAL = (String) request.getAttribute("CP_PAC_USUARIO_ACTUAL");
                    }
                    if ((String)request.getAttribute("CP_PAC_ACTUAL_PASSWORD") != null) {
                        ACTUAL_PASS = (String) request.getAttribute("CP_PAC_ACTUAL_PASSWORD");
                    }
                    if ((String)request.getAttribute("CP_PAC_NEW_PASSWORD") != null) {
                        VAR_NEW_PASS = (String) request.getAttribute("CP_PAC_NEW_PASSWORD");
                    }
                    if ((String)request.getAttribute("CP_PAC_REPEAT_NEW_PASSWORD") != null) {
                        VAR_REP_NEW_PASS = (String) request.getAttribute("CP_PAC_REPEAT_NEW_PASSWORD");
                    }
                    
                    String varTitulo = "PACIENTE - CAMBIAR CONTRASEÑA DE USUARIO";
                %>
                <form action="CPSrv" method="post" autocomplete="off">
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="datosPac">
                        <div class="datosPacBlo1">
                            <div class="datos-pac">
                                <span class="datosPacTitle">
                                    <p>Datos del Usuario</p>
                                </span>
                                <div class="datosPacBody">
                                    <div class="mt-1">
                                        <p>Nombre y Apellido:</p>
                                        <p><%= PAC_NOMBRE+" "+PAC_APELLIDO %></p>
                                    </div>
                                    <div class="mt-1">
                                        <p>Nro. Doc.:</p>
                                        <p><%= PAC_CINRO %></p>
                                    </div>
                                    <div class="mt-1">
                                        <p>Usuario:</p>
                                        <p><%= PAC_USER_ACTUAL %></p>
                                    </div>
                                    <div class="mt-1">
                                        <p class="mt-2">Contraseña Actual:</p>
                                        <p><input type="text" value="<%= ACTUAL_PASS %>" name="tAP" class="form-control"/></p>
                                    </div>
                                    <div class="mt-1">
                                        <p class="mt-2">Nueva Contraseña:</p>
                                        <p><input type="text" value="<%= VAR_NEW_PASS %>" name="tNP" class="form-control"/></p>
                                    </div>
                                    <div class="mt-1">
                                        <p class="mt-2">Repetir Nueva Contraseña:</p>
                                        <p><input type="text" value="<%= VAR_REP_NEW_PASS %>" name="tRNP" class="form-control"/></p>
                                    </div>
                                </div>
                                <span class="datosPacBtn mb-2">
                                    <input type="hidden" value="<%= PAC_IDUSUARIO %>" name="tIUP" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <input type="hidden" value="<%= ID_PACIENTE %>" name="tI" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <input type="hidden" value="<%= PAC_USER_ACTUAL %>" name="tUA" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <input type="hidden" value="<%= PAC_NOMBRE %>" name="tPN" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <input type="hidden" value="<%= PAC_APELLIDO %>" name="tPA" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <input type="hidden" value="<%= PAC_CINRO %>" name="tPC" class="form-control disNone"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                    <button type="submit" value="Guardar CambioPass" name="accion" class="btn btn-info">Guardar Nueva Contraseña</button>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="ml-4 mt-4">
                        <button type="submit" value="Ver Expediente" name="accion" class="btn btn-danger">Volver Atras</button>
                    </div>
                </form>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>