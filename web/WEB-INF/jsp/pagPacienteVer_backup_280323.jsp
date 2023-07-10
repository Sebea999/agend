<%-- 
    Document   : pagPacienteVer
    Created on : 17-mar-2022, 11:29:52
    Author     : RYUU
--%>

<%@page import="com.agend.javaBean.BeanFichaAtePaciente"%>
<%@page import="com.agend.javaBean.BeanAgendamiento"%>
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
                    
                    List<BeanPersona> listaDatos;
                    String PAC_NOMBRE="", PAC_APELLIDO="", PAC_CINRO="", PAC_RAZON_SOCIAL="", PAC_RUC="", PAC_TELEFONO="", PAC_DIRECCION="", PAC_EMAIL="", PAC_IDUSUARIO="", PAC_USUARIO="", PAC_CLAVE="", PAC_ESTADO="", PAC_FECHA_NAC="", PAC_SEXO="", PAC_IDCLINICA="", PAC_SEGURO_MEDICO="", PAC_OCUPACION="", PAC_ANTECEDENTES_FAMILIARES="";
                    String PAC_ESTADO_CIVIL="", PAC_DESC_CIUDAD="", PAC_CELULAR="", PAC_TIENE_HIJOS="", PAC_CANT_HIJOS="";
                    
                    // CONTROLO EL IDCLIENTE PARA QUE EN CASO DE QUE ESTE VACIO ENTONCES NO HAGO NADA 
                    if (ID_PACIENTE.equals("0") || ID_PACIENTE.isEmpty() || ID_PACIENTE.equals("")) {
                        // ... 
                    } else { // Y SI SE ENCUENTRA CARGADO ENTONCES LLAMO AL METODO PARA RECUPERAR LOS DATOS  
                        listaDatos = metodosPersona.traerDatosPersona(ID_PACIENTE);
                        Iterator<BeanPersona> iterPaciente = listaDatos.iterator();
                        BeanPersona paciente = null;
                        
                        while(iterPaciente.hasNext()) {
                            paciente = iterPaciente.next();
                            PAC_NOMBRE = paciente.getRP_NOMBRE();
                            PAC_APELLIDO = paciente.getRP_APELLIDO();
                            PAC_CINRO = paciente.getRP_CINRO();
                            PAC_RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
                            PAC_RUC = paciente.getRP_RUC();
                            PAC_TELEFONO = paciente.getRP_TELFPARTICULAR();
                            PAC_DIRECCION = paciente.getRP_DIRECCION();
                            PAC_EMAIL = paciente.getRP_EMAIL();
                            PAC_IDUSUARIO = paciente.getSU_IDUSUARIO();
                            PAC_USUARIO = paciente.getSU_USUARIO();
                            PAC_CLAVE = paciente.getSU_CLAVE();
                            PAC_ESTADO = paciente.getSU_ESTADO();
                            PAC_FECHA_NAC = paciente.getRP_FECHANAC();
                            PAC_SEXO = paciente.getRP_SEXO();
                            PAC_IDCLINICA = paciente.getRP_IDCLINICA();
                            PAC_SEGURO_MEDICO = paciente.getRP_SEGURO_MEDICO();
                            PAC_OCUPACION = paciente.getRP_OCUPACION();
                            PAC_ANTECEDENTES_FAMILIARES = paciente.getRP_ANTECEDENTES();
                            PAC_DESC_CIUDAD = paciente.getRP_DESC_CIUDAD();
                            PAC_CELULAR = paciente.getRP_TELFMOVIL();
                            PAC_TIENE_HIJOS = metodosIniSes.getDatoSiNo(paciente.getRP_TIENE_HIJOS());
                            PAC_CANT_HIJOS = paciente.getRP_CANT_HIJOS();
                            PAC_ESTADO_CIVIL = paciente.getRP_ESTADOCIVIL();
                        } // end while 

                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".   _____JSP_____PAC_FECHA_NAC:   :"+PAC_FECHA_NAC);
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println(".");
                        
                        
                        // CARGO A LA SESION PARA LUEGO RECUPERARLO DESDE EL SERVLET Y NO DEJARLO EN UN CAMPO OCULTO 
                        sesionDatosUsuario.setAttribute("ID_USUARIO_PACIENTE", PAC_IDUSUARIO);
                    } // END ELSE 

                    String varTitulo = "PACIENTE - VER REGISTRO";
                %>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="datosPac">
                        <div class="datosPacBlo1">
                            <div class="datos-pac">
                                <span class="datosPacTitle">
                                    <p>Datos Personales</p>
                                </span>
                                <div class="datosPacBody">
                                    <div>
                                        <p>Nombre y Apellido:</p>
                                        <p><%= PAC_NOMBRE+" "+PAC_APELLIDO %></p>
                                    </div>
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
                                        <p><%= PAC_TIENE_HIJOS %></p>
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
                        
                        <div class="grillaFicAte">
                            <table role="table" class="tableFicAte">
                                <thead role="rowgroup">
                                    <tr role="row">
                                        <td role="columnheader" class="PFA_CI">Cod.</td>
                                        <%--<td role="columnheader" class="PFA_CA">CLINICA</td>--%>
                                        <td role="columnheader" class="PFA_CF">FECHA AGEN.</td>
                                        <td role="columnheader" class="PFA_CF">FECHA ATENCIÓN</td>
                                        <td role="columnheader" class="PFA_CMC">MOTIVO CONSULTA</td>
                                        <td role="columnheader" class="PFA_CA">ACCIONES</td>
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
                                            String DESC_CLINICA = metodosRefClinica.traerDescClinica(IDCLINICA);
                                            
//                                            // TRAER DATOS DEL MEDICO 
//                                            BeanPersona datosBPersona = new BeanPersona();
//                                            datosBPersona = metodosPersona.datosBasicosPersona(datosBPersona, datosBeanDet.getOAP_IDPACIENTE());
                                    %>
                                    <tr role="row">
                                        <td role="cell" class="PFA_CI">
                                            <%= IDFICHAPAC /*nro_item*/ %>
                                        </td>
<%--                                        <td role="cell" class="PFA_CA">
                                            <%= DESC_CLINICA %>
                                        </td>--%>
                                        <td role="cell" class="PFA_CF">
                                            <%= (datosBeanDet.getOFPN_FEC_AGEN_AUX())%>
                                        </td>
                                        <td role="cell" class="PFA_CF">
                                            <%= (datosBeanDet.getOFPN_FECHA_FICHA_ATE()) %>
                                        </td>
                                        <td role="cell" class="PFA_CMC">
                                            <%= datosBeanDet.getOFPN_MOTIVO_DE_LA_CONSULTA()%>
                                        </td>
                                        <td role="cell" class="PFA_CA">
                                            <form action="CFAPSrvN" method="post" autocomplete="off">
                                            <input type="text" name="tIAP" value="<%= IDFICHAPAC %>" class="form-control"/>
                                            <input type="text" name="tIA" value="<%= datosBeanDet.getOFPN_IDAGENDAMIENTO() %>" class="form-control"/>
                                            <input type="text" name="tAID" value="<%= datosBeanDet.getOFPN_ITEM_AGEND_DET() %>" class="form-control"/>
                                            <input type="text" name="tIP" value="<%= ID_PACIENTE %>" class="form-control"/>
                                            <button type="submit" name="accion" value="Editar Atención" class="<%=BTN_CSS%>"><%=BTN_NAME%></button>
                                            <input type="submit" name="accion" value="Ver Atención" class="btn btn-secondary mt-1" />
                                            <%
//                                            if(!(IDATENCIONPAC.isEmpty())) {
//                                                request.setAttribute("FICATE_BTN_ANULAR", "PACIENTE"); // LE PASO ESTE DATO PARA QUE DESDE EL CONTROLADOR DE FICHA ATENCION PUEDA SABER A DONDE TENGO QUE REDIRECCIONAR LUEGO DE ANULAR LA FICHA 
//                                            
//                                            <input type="submit" name="accion" value="Eliminar Consulta" class="btn btn-danger mt-1" />
//                                            
//                                            }
                                            %>
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