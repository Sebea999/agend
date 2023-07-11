<%-- 
    Document   : pagPacienteVer
    Created on : 17-mar-2022, 11:29:52
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanAtencionPaciente"%>
<%@page import="java.io.File"%>
<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="com.agend.javaBean.BeanAgendamiento"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
                String mensaje = (String) request.getAttribute("CP_MENSAJE"); // CONTROLLER PACIENTE MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CP_TIPO_MENSAJE"); // CONTROLLER PACIENTE TIPO MENSAJE 
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
                    
//                    List<BeanPersona> listaDatos;
                    String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_SEGURO_MEDICO="", PAC_OCUPACION="", PAC_ANTECEDENTES_FAMILIARES="";
                    String PAC_DESC_CIUDAD="", PAC_CANT_HIJOS="", PAC_FOTO="", PAC_FOTO_ABS="", PAC_IDCIUDAD="", PAC_PROFESION="", PAC_ESTADO_CIVIL="", PAC_TIENE_HIJOS="", PAC_CELULAR="", PAC_OTROS_DATOS="";
                    
                    // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                    if (ID_PACIENTE.equals("0") || ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
                        // ... 
                    } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
//                        listaDatos = metodosPersona.traerDatosPersona(ID_PACIENTE);
//                        Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
//                        BeanPersona paciente = null;
//                        
//                        while(iterPaciente.hasNext()) {
//                            paciente = iterPaciente.next();
//                            PAC_NOMBRE = paciente.getRP_NOMBRE();
//                            PAC_APELLIDO = paciente.getRP_APELLIDO();
//                            PAC_CINRO = paciente.getRP_CINRO();
//                            PAC_RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
//                            PAC_RUC = paciente.getRP_RUC();
//                            PAC_TELEFONO = paciente.getRP_TELFPARTICULAR();
//                            PAC_DIRECCION = paciente.getRP_DIRECCION();
//                            PAC_EMAIL = paciente.getRP_EMAIL();
//                            PAC_IDUSUARIO = paciente.getSU_IDUSUARIO();
//                            PAC_USUARIO = paciente.getSU_USUARIO();
//                            PAC_CLAVE = paciente.getSU_CLAVE();
//                            PAC_ESTADO = paciente.getSU_ESTADO();
//                            PAC_FECHA_NAC = paciente.getRP_FECHANAC();
//                            PAC_SEXO = paciente.getRP_SEXO();
//                            PAC_IDCLINICA = paciente.getRP_IDCLINICA();
//                            PAC_SEGURO_MEDICO = paciente.getRP_SEGURO_MEDICO();
//                            PAC_OCUPACION = paciente.getRP_OCUPACION();
//                            PAC_ANTECEDENTES_FAMILIARES = paciente.getRP_ANTECEDENTES();
//                            PAC_DESC_CIUDAD = paciente.getRP_DESC_CIUDAD();
//                            PAC_CELULAR = paciente.getRP_TELFMOVIL();
//                            PAC_TIENE_HIJOS = metodosIniSes.getDatoSiNo(paciente.getRP_TIENE_HIJOS());
//                            PAC_CANT_HIJOS = paciente.getRP_CANT_HIJOS();
//                            PAC_ESTADO_CIVIL = paciente.getRP_ESTADOCIVIL();
//                        } // end while 
//                        // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
//                        sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
                        
                        
                        List<BeanPersona> datosPaciente = (List<BeanPersona>) request.getAttribute("CP_PAC_LISTA_DATOS");
                        // recupero de la lista 
                        if(datosPaciente != null) {
                            BeanPersona paciente_datos = datosPaciente.get(0);
                            PAC_CINRO = paciente_datos.getRP_CINRO();
                            PAC_NOMBRE = paciente_datos.getRP_NOMBRE();
                            PAC_APELLIDO = paciente_datos.getRP_APELLIDO();
                            PAC_RAZON_SOCIAL = paciente_datos.getRP_RAZON_SOCIAL();
                            PAC_RUC = paciente_datos.getRP_RUC();
                            PAC_TELEFONO = paciente_datos.getRP_TELFPARTICULAR();
                            PAC_CELULAR = paciente_datos.getRP_TELFMOVIL();
                            PAC_DIRECCION = paciente_datos.getRP_DIRECCION();
                            PAC_EMAIL = paciente_datos.getRP_EMAIL();
                            PAC_IDCIUDAD = paciente_datos.getRP_IDCIUDAD();
                            PAC_IDUSUARIO = paciente_datos.getSU_IDUSUARIO();
                            PAC_USUARIO = paciente_datos.getSU_USUARIO();
                            PAC_CLAVE = paciente_datos.getSU_CLAVE();
                            PAC_ESTADO = paciente_datos.getSU_ESTADO();
                            PAC_FECHA_NAC = paciente_datos.getRP_FECHANAC();
                            PAC_SEXO = paciente_datos.getRP_SEXO();
                            PAC_ESTADO_CIVIL = paciente_datos.getRP_ESTADOCIVIL();
                            PAC_TIENE_HIJOS = paciente_datos.getRP_TIENE_HIJOS();
                            PAC_CANT_HIJOS = paciente_datos.getRP_CANT_HIJOS();
                            PAC_IDCLINICA = paciente_datos.getRP_IDCLINICA();
                            PAC_SEGURO_MEDICO = paciente_datos.getRP_SEGURO_MEDICO();
                            PAC_OTROS_DATOS = paciente_datos.getRP_OBSERVACION();
                            PAC_FOTO = paciente_datos.getRP_FOTO();
                            PAC_FOTO_ABS = paciente_datos.getRP_FOTO_PATH_ABS();
                            PAC_OCUPACION = paciente_datos.getRP_OCUPACION();
//                            PAC_PROFESION = paciente_datos.getRP_OCUPACION();
                            PAC_DESC_CIUDAD = paciente_datos.getRP_DESC_CIUDAD();
                            PAC_ANTECEDENTES_FAMILIARES = paciente_datos.getRP_ANTECEDENTES().replace("<br/>", "\n");
                        }
                    } // END ELSE 
                    
                    System.out.println(".-----------------__JSP__------------------------");
                    System.out.println(".   _FOTO:  :"+PAC_FOTO);
                    System.out.println(".   _FOTO_ABS:  :"+PAC_FOTO_ABS);
                    System.out.println(".-----------------__END__------------------------");
                    
                    String varTitulo = "PACIENTE - VER REGISTRO";
                %>
                    <div>
                        <h4 class="mainTitle mb-1"><%= varTitulo %></h4>
                    </div>
                    <div class="datosMainImg mb-3">
                        <div class="pacImg">
                            <!--<div>-->
                                <%
                                String s = File.separator;
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".   _________EXPEDIENTE_PAC_________");
                                System.out.println("----");
//                                System.out.println("__  __JSP_ID_PAC:     :"+datosBean.getRP_IDPERSONA());
//                                System.out.println("__  __JSP_PACIENTE:   :"+datosBean.getRP_NOMBRE()+" "+datosBean.getRP_APELLIDO());
                                String fotoName = PAC_FOTO;
//                                String fotoName = datosBean.getRP_FOTO();
                                System.out.println("__  __JSP_FOTO_NAME:  :"+fotoName);
                                String fotoPath = PAC_FOTO_ABS;
//                                String fotoPath = datosBean.getRP_FOTO_PATH_ABS();
                                System.out.println("__  __JSP_FOTO_PATH_COMPLETO:  :"+fotoPath);
                                String fotoDB, sinFoto, fotoFinal;

                                System.out.println("___CONTROL_IMG____");
                                System.out.println("----------------------------------_____NUEVO_CONTROL_DE_IMG_____------------------------------------------------------");
                                if (fotoName == null || fotoName.isEmpty() || fotoName.equals("")) {
                                    System.out.println("-   __IF___   IMG_PATH_IS_EMPTY-----");
                                    sinFoto = "/recursos/static/sin-foto.png";
//                                    sinFoto = "recursos"+s+"static"+s+"sin-foto.png";
                                    fotoFinal = sinFoto;
                                } else {
                                    System.out.println("-   __ELSE__   IMG_PATH_VERIFY----");
                                    fotoDB = "/recursos/static/"+fotoName;
//                                    fotoDB = "recursos"+s+"static"+s+fotoName; // [OBS] :ruta correcta pero agregando las barras laterales segun el sistema-operativo, pero le agrego en duro y comento esta para poder tener la ruta igual a la que especifique en la etiqueta <mvc:resources> en el dispatcher-serlvet.-
                                    System.out.println("_   _   _IMG_PATH:  :"+fotoDB);
                                    fotoFinal = fotoDB;
                                }
                                System.out.println("----------------------------------------------------------------------------------------------------------------------");
                                System.out.println("____RUTA_DE_LA_FOTO_FINAL_A_CARGAR:  :"+fotoFinal);
                                System.out.println("----");
                                %>
                                <img src="<c:url value="<%=fotoFinal%>" />" alt="" />
                            <!--</div>-->
                        </div>
                        <div class="dataName">
                            <%= PAC_NOMBRE+" "+PAC_APELLIDO %>
                        </div>
                        <%
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".-------------------------------------------");
                            System.out.println(".---------      CONTROL DE FICHA");
                            System.out.println(".--------------------------------------------");
                            System.out.println(".");
                            System.out.println(".");
                        String UF_ID_FICHA_ATENCION="", UF_ID_AGENDAMIENTO="", UF_ITEM_AGEND="", UF_PESO="", UF_ESTATURA="", UF_IMC="", UF_PORC_GRASA="";
                        // POR MEDIO DEL CONTROLADOR LE PASO LA LISTA CON LOS DATOS DE LA ULTIMA FICHA DE CONSULTA DEL PACIENTE Y EN CASO DE QUE NO TENGA NINGUNA FICHA DE CONSULTA ENTONCES NO LE MUESTRE EL BLOQUE DE DATOS NI LA OPCION PARA VER LA FICHA.-
                        List<BeanFichaAtePaciente> listaDatosUltFicha = (List<BeanFichaAtePaciente>) request.getAttribute("CP_PAC_LISTA_DATOS_ULTFICHA");
//                        System.out.println("_   ____LISTA_DATOS_ULT_FICHA:   :"+listaDatosUltFicha.size());
//                        System.out.println("_   ____LISTA_DATOS_ULT_FICHA:   :"+listaDatosUltFicha.isEmpty());
                        if (listaDatosUltFicha == null || listaDatosUltFicha.isEmpty()==true || listaDatosUltFicha.size() <= 0) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".____________IF____- vacio -____LISTA_ULTIMA_FICHA_____________");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                        } else {
//                        if (listaDatosUltFicha != null || listaDatosUltFicha.isEmpty()==false || listaDatosUltFicha.size() > 0) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".____________ELSE________LISTA_ULTIMA_FICHA_____________");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            BeanFichaAtePaciente ficha = listaDatosUltFicha.get(0);
                            // datos visuales
                            UF_PESO=ficha.getOFPN_PESO();
                            if (UF_PESO.isEmpty()) {
                                UF_PESO = "...";
                            } else {
                                UF_PESO = UF_PESO+"kg";
                            }
                            UF_ESTATURA=ficha.getOFPN_ESTATURA();
                            if (UF_ESTATURA.isEmpty()) {
                                UF_ESTATURA = "...";
                            } else {
                                UF_ESTATURA = UF_ESTATURA+"cm";
                            }
                            UF_IMC=ficha.getOFPN_IMC();
                            if (UF_IMC.isEmpty()) {
                                UF_IMC = "...";
                            }
                            UF_PORC_GRASA=ficha.getOFPN_PORC_GRASA();
                            if (UF_PORC_GRASA.isEmpty()) {
                                UF_PORC_GRASA = "...";
                            } else {
                                UF_PORC_GRASA = UF_PORC_GRASA+"%";
                            }
                            // datos para ver la ficha
                            UF_ID_FICHA_ATENCION = ficha.getOFPN_IDFICHAPAC();
                            UF_ID_AGENDAMIENTO = ficha.getOFPN_IDAGENDAMIENTO();
                            UF_ITEM_AGEND = ficha.getOFPN_ITEM_AGEND_DET();
                        %>
                        <div class="dataPac">
                            <div class="dataPacTab">
                                <table class="tableData">
                                    <tbody>
                                        <tr><td><% if (!UF_PESO.equals("...")) { %><span class="peLabel">Peso:</span><% } %><%=UF_PESO%></td><td><% if (!UF_ESTATURA.equals("...")) { %><span class="peLabel">Estatura:</span><% } %><%=UF_ESTATURA%></td></tr>
                                        <tr><td><% if (!UF_IMC.equals("...")) { %><span class="peLabel">IMC:</span><% } %><%=UF_IMC%></td><td><% if (!UF_PORC_GRASA.equals("...")) { %><span class="peLabel">Porc. Grasa:</span><% } %><%=UF_PORC_GRASA%></td></tr>
                                    </tbody>
                                </table>
                            </div>
                            <div class="dataPacHis">
<!--                                <form action="CFAPSrvN" method="post" autocomplete="off">
                                    <input type="hidden" name="tIAP" value="<%= UF_ID_FICHA_ATENCION %>" class="form-control"/>
                                    <input type="hidden" name="tIA" value="<%= UF_ID_AGENDAMIENTO %>" class="form-control"/>
                                    <input type="hidden" name="tAID" value="<%= UF_ITEM_AGEND %>" class="form-control"/>
                                    <input type="hidden" name="tIP" value="<%= ID_PACIENTE %>" class="form-control"/>
                                    <button type="submit" name="accion" value="Ver Atención" class="btn btn-outline-success">Ver Historial</button>
                                </form>-->
                                <form action="CPSrv" method="post" autocomplete="off">
                                    <input type="hidden" name="tIP" value="<%=ID_PACIENTE%>" class="form-control disNone"/>
                                    <input type="hidden" name="tCRPNC" value="<%=PAC_NOMBRE+" "+PAC_APELLIDO%>" class="form-control disNone"/>
                                    <!--<input type="hidden" name="tIPN" value="<%--= datosBean.getRP_NOMBRE()--%>" class="form-control disNone"/>-->
                                    <!--<input type="hidden" name="tIPA" value="<%--= datosBean.getRP_APELLIDO()--%>" class="form-control disNone"/>-->
                                    <input type="submit" value="Ver Historico" name="accionExp" class="btn btn-outline-success" />
                                </form>
                            </div>
                        </div>
                        <%
                            }
                        %>
                    </div>
                    
                    
                    <div class="datosPac">
                        <div class="datosPacBlo1">
                            <div class="datos-pac">
                                <span class="datosPacTitle">
                                    <p>Datos Personales</p>
                                </span>
                                <div class="datosPacBody">
<!--                                    <div>
                                        <p>Nombre y Apellido:</p>
                                        <p><%= PAC_NOMBRE+" "+PAC_APELLIDO %></p>
                                    </div>-->
                                    <div>
                                        <p>Nro. Doc.:</p>
                                        <p><%= PAC_CINRO %></p>
                                    </div>
                                    <div>
                                        <p>Dirección:</p>
                                        <p><%= PAC_DIRECCION %></p>
                                    </div>
                                    <div>
                                        <p>Edad:</p>
                                        <p><%= ModelInicioSesion.getPacEdad(metodosIniSes.getDatoFecha(0, PAC_FECHA_NAC)) %></p>
                                    </div>
                                    <div>
                                        <p>Fecha de Nacimiento:</p>
                                        <p><%= metodosIniSes.getDatoFecha(0, PAC_FECHA_NAC) %></p>
                                    </div>
                                    <div>
                                        <p>Estado Civil:</p>
                                        <p><%= metodosIniSes.getPacEstadoCivil(PAC_ESTADO_CIVIL) %></p>
                                    </div>
                                    <div>
                                        <p>Sexo:</p>
                                        <p><%= metodosIniSes.devolverTextSexo(PAC_SEXO) %></p>
                                    </div>
                                    <div>
                                        <p>Ciudad:</p>
                                        <p><%= PAC_DESC_CIUDAD %></p>
                                    </div>
                                    <div>
                                        <p>Teléfono:</p>
                                        <p><%= PAC_TELEFONO %></p>
                                    </div>
                                    <div>
                                        <p>Celular</p>
                                        <p><%= PAC_CELULAR %></p>
                                    </div>
                                    <div>
                                        <p>Profesión:</p>
                                        <p><%= PAC_OCUPACION %></p>
                                    </div>
                                    <div>
                                        <p>Email:</p>
                                        <p><%= PAC_EMAIL %></p>
                                    </div>
                                    <div>
                                        <p>Tiene Hijos:</p>
                                        <p><%= metodosIniSes.getDatoSiNo(PAC_TIENE_HIJOS) %></p>
                                    </div>
                                    <div>
                                        <p>Cant. Hijos:</p>
                                        <p><%= PAC_CANT_HIJOS %></p>
                                    </div>
                                </div>
                                <span class="datosPacBtn">
                                <%
                                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true) {
                                %>
                                    <form action="CPSrv" method="post" autocomplete="off">
                                        <input type="hidden" value="<%= ID_PACIENTE %>" name="tI" class="form-control"/>
                                        <input type="submit" value="Editar" name="accion" class="btn btn-outline-success"/>
                                    </form>
                                <%
                                    }
                                %>
                                <%
                                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true
                                            || metodosPerfil.isPerfilMedico(idPerfil) // OBS: AGREGUE ESTE PERFIL A LA CONDICIONAL YA QUE UN MEDICO TIENE PERMITIDO CARGAR UNA CONSULTA COMO UNA ATENCIÓN PERO NO PODRÍA EDITAR LOS DATOS DEL PACIENTE, POR ESO DIVIDI LAS CONDICIONALES PARA LOS DOS BOTONES (EDITAR Y AGREGAR CONSULTA) 
                                    ) {
                                %>
                                    <form action="CFAPSrvN" method="post" autocomplete="off">
                                        <input type="hidden" value="<%= ID_PACIENTE %>" name="tIP" class="form-control"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                        <input type="hidden" value="0" name="tIA" class="form-control"/><% // ID_AGENDAMIENTO PARA AGREGAR UNA NUEVA FICHA DE ATENCION SIN VINCULACION CON AGENDAMIENTO %>
                                        <input type="hidden" value="0" name="tAID" class="form-control"/> <% // ITEM_AGENDAMIENTO PARA AGREGAR UNA NUEVA FICHA DE ATENCION SIN VINCULACION CON AGENDAMIENTO %>
                                        <button type="submit" value="Cargar Atencion" name="accion" class="btn btn-outline-info">Agregar Consulta</button>
                                    </form>
                                <%
                                    }
                                %>
                                <%
                                    if((metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilPaciente(idPerfil)==true) 
                                            && !(PAC_IDUSUARIO.isEmpty())) {// SI EL PERFIL ES ADMIN/SECRE/PACIENTE Y CUENTA CON IDUSUARIO ENTONCES AHI LE PERMITO VER EL BOTON PARA CAMBIAR CONTRASEÑA  
                                %>
                                    <form action="CPSrv" method="post" autocomplete="off">
                                        <input type="hidden" value="<%= ID_PACIENTE %>" name="tIP" class="form-control"/> <% // ID_PACIENTE PARA AGREGAR UNA NUEVA FICHA DE ATENCION - EL NOMBRE DEL NAME ES DIFERENTE AL CAMPO DE IDPACIENTE DE ARRIBA POR ESO TENGO DOS CAMPOS DE TEXTOS CON EL MISMO DATO %>
                                        <button type="submit" value="Cambiar Contraseña" name="accion" class="btn btn-outline-danger">Cambiar Contraseña</button>
                                    </form>
                                <%
                                    }
                                %>
                                </span>
                            </div>


                            <div class="antec-fam">
                                <div class="datosTitle">
                                    <p>Antecedentes Familiares y Personales</p>
                                </div>
                                <form action="CPSrv" method="post" autocomplete="off">
                                <div class="datosAnte">
                                    <textarea id="tPAF" name="tPAF"><%= PAC_ANTECEDENTES_FAMILIARES %></textarea>
                                </div>
                                <div class="datosPacBtn">
                                    <%
                                    /*
                                      * OBSERVACION:
                                          LE CARGO A LA SESION LA PAGINA DONDE TIENE QUE RETORNAR SI ES QUE LE DA AL BOTON DE VOLVER ATRAS 
                                    */
                                    sesionDatosUsuario.setAttribute("PAC_BTN_VOLVER_ATRAS", "VER_PAC");
                                    %>
                                    <input type="hidden" value="<%= ID_PACIENTE %>" name="tI" class="form-control"/>
                                    <a onclick="presionarImprimir()" href="#" class="btn btn-outline-danger btnAnteImpr">Imprimir</a>
                                    <input type="submit" value="Guardar Antecedentes" name="accion" class="btn btn-outline-success btnAnteSave"/>
                                </div>
                                </form>
                                <%
                                /*
                                    * OBSERVACION: 
                                        ESTE SERIA EL BOTON QUE SE ENCARGARIA DE LLAMAR O ACTIVAR AL CONTROLADOR QUE CREA EL PDF CON LOS DATOS DE LOS ANTECEDENTES FAMILIARES Y PERSONALES 
                                        LE COLOCO ACA ABAJO Y LE OCULTO PORQUE ESTE TIENE UN FORM PARA LLAMAR AL PDF Y ESTE NO PUEDE ESTAR DENTRO DE OTRO FORM 
                                        ENTONCES POR MEDIO DE UN ONCLICK LE ACTIVO A ESTE BOTON 
                                */
                                %>
                                <form action="pdf_afp" target="_blank" method="post" class="disNone">
                                    <input type="hidden" value="<%= PAC_ANTECEDENTES_FAMILIARES %>" name="tAFP" class="form-control disNone"/>
                                    <input type="submit" value="Imprimir" name="accion" id="btnImprimirAFP" class="btn btn-outline-danger btnAnteImpr disNone"/>
                                </form>
                            </div>
                        </div>
                        
                        
                        <div class="panelFicPac">
                            
                            <div id="accordion" style="width: 890px;">
                                <div class="card">
                                  <div class="card-header" id="headingOne">
                                    <h5 class="mb-0">
                                      <button class="btn-exp-pac collapsed" data-toggle="collapse" data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                        Ver Fichas Anteriores
                                      </button>
                                    </h5>
                                  </div>

                                  <div id="collapseOne" class="collapse <%-- clase css para mostrar el collapse: show--%>" aria-labelledby="headingOne" data-parent="#accordion">
                                    <div class="card-body">
                                        <table class="table table-striped">
                                            <thead class="thead-light" role="rowgroup">
                                                <tr role="row">
                                                    <th role="columnheader" scope="col"></th>
                                                    <th role="columnheader" scope="col" style="width: 240px;">Fecha</th>
                                                    <th role="columnheader" scope="col">Peso</th>
                                                    <th role="columnheader" scope="col" style="width: 160px;">% Grasa</th>
                                                    <th role="columnheader" scope="col" style="width: 160px;">% Músc.</th>
                                                    <th role="columnheader" scope="col">Visceral</th>
                                                    <th role="columnheader" scope="col" style="width: 170px;">Edad M.</th>
                                                    <th role="columnheader" scope="col" style="width: 300px;"></th>
                                                </tr>
                                            </thead>
                                            <tbody role="rowgroup">
                                                <%
                                                    System.out.println(".");
                                                    System.out.println(".");
                                                    System.out.println(".");
                                                    System.out.println(".");
                                                    System.out.println("__       ____ CARGAR_HISTORICO_FICHAS_ATENCION ____      __");
                                                    System.out.println(".");
                                                    System.out.println(".");
                                                    List<BeanFichaAtePaciente> listaDatosDet = null;
                                                    listaDatosDet = metodosFichaAtencionPacNutri.cargar_fichas_pacientes(sesionDatosUsuario, ID_PACIENTE);
                                                    Iterator<BeanFichaAtePaciente> iterDatosDet = listaDatosDet.iterator();
                                                    BeanFichaAtePaciente datosBeanDet = null;

                                                    int nro_item = 0;
                //                                    String FEC_AGEN_ANT = ""; // GUARDO LA PRIMERA FECHA DE AGENDAMIENTO Y CONTROLO EL DE LA SIGUIENTE LINEA Y SI ES IGUAL ENTONCES LE AUMENTO EL CONTEO AL ITEM PERO SI ES DIFERENTE ENTONCES REINICIO EL NRO DEL ITEM Y LE CAMBIO EL VALOR DE ESTA VARIABLE POR EL DE LA FECHA Y ASI SUCESIVAMENTE PARA PODER MOSTRAR UN NRO DE ITEM PARA CADA FECHA 
                                                    while(iterDatosDet.hasNext()) {
                                                        datosBeanDet = iterDatosDet.next();

                //                                        // explicacion de este if en la observacion que le deje a la variable 
                //                                        if (nro_item == 0) {
                //                                            FEC_AGEN_ANT = datosBeanDet.getOAD_FECHA_AGEN();
                //                                            nro_item = nro_item + 1;
                //                                        } else if((FEC_AGEN_ANT.equals(datosBeanDet.getOAD_FECHA_AGEN())) == true) {
                                                            nro_item = nro_item + 1;
                //                                        } else if((FEC_AGEN_ANT.equals(datosBeanDet.getOAD_FECHA_AGEN())) == false) {
                //                                            nro_item = 1;
                //                                            FEC_AGEN_ANT = datosBeanDet.getOAD_FECHA_AGEN();
                //                                        }

                                                        String IDFICHAPAC = datosBeanDet.getOFPN_IDFICHAPAC();
                                                        System.out.println("_   _JSP__ID_FICHA_ATENCION_SUB_SELECT:   :"+IDFICHAPAC);
                                                        String BTN_NAME = "";
                                                        String BTN_CSS = "";
            //                                            // VALIDACION PARA CONTROLAR SI ES QUE EL PACIENTE DE ESTE AGENDAMIENTO YA CUENTA CON UNA FICHA DE ATENCION PARA MOSTRARLE EL BOTON DE MODIFICAR FICHA Y NO DE CARGAR FICHA 
            //                                            if (IDATENCIONPAC.isEmpty() || IDATENCIONPAC.equals("0") || IDATENCIONPAC == null) {
            //                                                BTN_NAME = "Cargar Atención";
            //                                                BTN_CSS = "btn btn-info";
            //                                            } else {
                                                            BTN_NAME = "Editar Atención";
                                                            BTN_CSS = "btn btn-info";
            //                                            }

                                                        // TRAER DATOS DE LA CLINICA 
                                                        String IDCLINICA = datosBeanDet.getOFPN_IDCLINICA();
//                                                        String DESC_CLINICA = metodosRefClinica.traerDescClinica(IDCLINICA);

                                                        String DET_FIC_PESO = datosBeanDet.getOFPN_PESO();
                                                        if (DET_FIC_PESO.isEmpty()) {
                                                            DET_FIC_PESO = "...";
                                                        } else {
                                                            DET_FIC_PESO = DET_FIC_PESO+"kg";
                                                        }
                                                        String DET_FIC_PORC_GRASA = datosBeanDet.getOFPN_PORC_GRASA();
                                                        if (DET_FIC_PORC_GRASA.isEmpty()) {
                                                            DET_FIC_PORC_GRASA = "...";
                                                        } else {
                                                            DET_FIC_PORC_GRASA = DET_FIC_PORC_GRASA+"%";
                                                        }
                                                        String DET_FIC_PORC_MUSC = datosBeanDet.getOFPN_PORC_MUSCULO();
                                                        if (DET_FIC_PORC_MUSC.isEmpty()) {
                                                            DET_FIC_PORC_MUSC = "...";
                                                        } else {
                                                            DET_FIC_PORC_MUSC = DET_FIC_PORC_MUSC+"%";
                                                        }
                                                        String DET_FIC_IMC = datosBeanDet.getOFPN_IMC();
                                                        if (DET_FIC_IMC.isEmpty()) {
                                                            DET_FIC_IMC = "...";
                                                        }
                                                        String DET_FIC_VISCERAL = datosBeanDet.getOFPN_VISCERAL();
                                                        if (DET_FIC_VISCERAL.isEmpty()) {
                                                            DET_FIC_VISCERAL = "...";
                                                        }
                                                        String DET_FIC_EDAD_M = datosBeanDet.getOFPN_EDAD_METABOLICA();
                                                        if (DET_FIC_EDAD_M.isEmpty()) {
                                                            DET_FIC_EDAD_M = "...";
                                                        }

                                                        // OBSERVACION:
                                                        // ESTA VARIABLE GUARDA LA FECHA DE AGENDAMIENTO :      datosBeanDet.getOFPN_FEC_AGEN_AUX();
                                                        // POR SI NECESITE VOLVER A MOSTRAR.
                                                        // VARIABLE QUE ALMACENA EL MOTIVO DE LA CONSULTA:      datosBeanDet.getOFPN_MOTIVO_DE_LA_CONSULTA();
                                                %>
                                                <tr role="row">
                                                    <th role="cell" scope="row">
                                                        <%=IDFICHAPAC /*nro_item*/%>
                                                    </th>
                                                    <td role="cell">
                                                        <%=datosBeanDet.getOFPN_FECHA_FICHA_ATE()%>
                                                    </td>
                                                    <td role="cell" class="text-center">
                                                        <%=DET_FIC_PESO%>
                                                    </td>
                                                    <td role="cell" class="text-center">
                                                        <%=DET_FIC_PORC_GRASA%>
                                                    </td>
                                                    <td role="cell" class="text-center">
                                                        <%=DET_FIC_PORC_MUSC%>
                                                    </td>
                                                    <td role="cell" class="text-center">
                                                        <%=DET_FIC_VISCERAL%>
                                                    </td>
                                                    <td role="cell" class="text-center">
                                                        <%=DET_FIC_EDAD_M%>
                                                    </td>
                                                    <td role="cell">
                                                        <%--<form action="CFAPSrvN" method="post" autocomplete="off">--%>
                                                        <form action="CPSrv" method="post" autocomplete="off">
                                                            <input type="hidden" name="tIAP" value="<%= IDFICHAPAC %>" class="form-control"/>
                                                            <input type="hidden" name="tIA" value="<%= datosBeanDet.getOFPN_IDAGENDAMIENTO() %>" class="form-control"/>
                                                            <input type="hidden" name="tAID" value="<%= datosBeanDet.getOFPN_ITEM_AGEND_DET() %>" class="form-control"/>
                                                            <input type="hidden" name="tIP" value="<%= ID_PACIENTE %>" class="form-control"/>
                                                            <!--<button type="submit" name="accion" value="Editar Atención" class="<%=BTN_CSS%>"><%=BTN_NAME%></button>-->
                                                            <!--<input type="submit" name="accion" value="Ver Atención" class="btn btn-secondary mt-1" />-->
                                                            <button type="submit" value="Ver Atención" name="accionExp" class="btn btn-link">Ver</button>
                                                            <button type="submit" value="Editar Atención" name="accionExp" class="btn btn-link">Editar</button>
                                                        </form>
                                                    </td>
                                                </tr>
                                                <%
                                                    }
                                                %>
                                            </tbody>
                                        </table>
                                    </div>
                                  </div>
                                </div>
                            </div>
                            
                            
                        </div>
                    </div>
                    <div class="ml-4 mt-4">
                        <a href="<%= urlPaciente %>" class="btn btn-danger">Volver Atras</a>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configPagsPaciente.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>