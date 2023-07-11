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
<html lang="es">
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
                if (ID_ATENCION.isEmpty() || ID_ATENCION.equals("")) {
                    varTitulo = "ATENCIÓN - NUEVO REGISTRO";
                    btnName = "Guardar";
                    btnCss = "btn btn-outline-success";
                } else {
                    varTitulo = "ATENCIÓN - MODIFICAR REGISTRO";
                    btnName = "Modificar";
                    btnCss = "btn btn-outline-warning";
                }
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
                            <form action="CFAPSrv" method="post" autocomplete="off">
                            <div class="contenedor-cajas">
                                <div class="c1">
                                    <div class="c1Ti">Parámetros Opcionales</div>
                                    <div class="c1Co">
                                        <div>
                                            <p>Peso</p>
                                            <input type="text" value="<%= TXT_PESO %>" name="tPe" placeholder="en Kgr." class="form-control" />
                                        </div>
                                        <div>
                                            <p>Talla</p>
                                            <input type="text" value="<%= TXT_TALLA %>" name="tTa" placeholder="en Cm." class="form-control" />
                                        </div>
                                        <div>
                                            <p>IMC</p>
                                            <input type="text" value="<%= TXT_IMC %>" name="tIMC" class="form-control" />
                                        </div>
                                        <div>
                                            <p>P. Cefálico</p>
                                            <input type="text" value="<%= TXT_PCEFALICO %>" name="tPC" class="form-control" />
                                        </div>
                                        <div>
                                            <p>FC</p>
                                            <input type="text" value="<%= TXT_FC %>" name="tFC" class="form-control" />
                                        </div>
                                        <div>
                                            <p>Temp.</p>
                                            <input type="text" value="<%= TXT_TEMP %>" name="tTe" class="form-control" />
                                        </div>
                                        <div>
                                            <p>PA</p>
                                            <input type="text" value="<%= TXT_PA %>" name="tPA" class="form-control" />
                                        </div>
                                        <div>
                                            <p>F. Resp.</p>
                                            <input type="text" value="<%= TXT_FRESP %>" name="tFR" class="form-control" />
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
                                            <textarea id="tAMC" name="tAMC" rows="4" cols="70"><%= TXT_MOTIVO_CONSULTA %></textarea>
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
                                            <textarea id="tAEF" name="tAEF" rows="4" cols="70"><%= TXT_EXPLORACION_FISICA %></textarea>
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
                                            <textarea id="tAD" name="tAD" rows="4" cols="70"><%= TXT_DIAGNOSTICO %></textarea>
                                        </div>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Tratamiento</div>
                                    <div class="c1Co">
                                        <div>
                                            Tratamiento:
                                        </div>
                                        <div>
                                            <textarea id="tAT" name="tAT" rows="4" cols="70"><%= TXT_TRATAMIENTO %></textarea>
                                        </div>
                                        <%
//                                        if(!(ID_ATENCION.isEmpty())) {
                                        %>
                                        <%//<div>%>
                                            <%//<form action="tratamiento_pdf" target="_blank" method="post">%> <% // SERVLET DEL CONTROLADOR QUE SE ENCARGA DE CREAR EL REPORTE PDF CON LOS DATOS DEL TRATAMIENTO   %>
                                                <%//<button type="submit" value="Imprimir Tratamiento" name="accion" class="btn btn-outline-danger">Imprimir</button>%>
                                            <%//</form>%>
                                        <%//</div>%>
                                        <%
//                                        }
                                        %>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Receta</div>
                                    <div class="c1Co">
                                        <div>
                                            Receta:
                                        </div>
                                        <div>
                                            <textarea id="tAR" name="tAR" rows="4" cols="70"><%= TXT_RECETA %></textarea>
                                        </div>
                                        <%
//                                        if(!(ID_ATENCION.isEmpty())) {
                                        %>
                                        <%//<div>%>
                                            <%//<button type="submit" value="Imprimir Receta" name="accion" class="btn btn-outline-danger">Imprimir</button>%>
                                        <%//</div>%>
                                        <%
//                                        }
                                        %>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Estudios Solicitados/Realizados</div>
                                    <div class="c1Co">
                                        <div>
                                            Estudios Solicitados:
                                        </div>
                                        <div>
                                            <textarea id="tAES" name="tAES" rows="4" cols="70"><%= TXT_ESTUDIOS_SOLICITADOS %></textarea>
                                        </div>
                                        <%
//                                        if(!(ID_ATENCION.isEmpty())) {
                                        %>
                                        <%//<div>%>
                                            <%//<button type="submit" value="Imprimir Estudios Solicitados" name="accion" class="btn btn-outline-danger">Imprimir</button>%>
                                        <%//</div>%>
                                        <%
//                                        }
                                        %>
                                    </div>
                                </div>
                                <div class="c1">
                                    <div class="c1Ti">Servicio/Proc.</div>
                                    <div class="c1Co">
                                        <div class="c1CoCBS">
                                            <select name="cbServ" class="form-control">
                                                <%
                                                Map<String, String> listaServicios = new LinkedHashMap<String, String>();
                                                listaServicios = metodosRefServicio.cargarComboServicio("", sesionDatosUsuario); // LE PASO VACIO EL IDSERVICIO YA QUE NO TENGO LA NECESIDAD DE COLOCAR O MOSTRARLE UN ELEMENTO EN ESPECIAL DENTRO DEL COMBO Y NO IMPORTA QUE SE RESETE LA LISTA DEL COMBO 
                                                Set setListServ = listaServicios.entrySet();
                                                Iterator iListServ = setListServ.iterator();
                                                
                                                while(iListServ.hasNext()) {
                                                    Map.Entry mapServ = (Map.Entry) iListServ.next();
                                                %>
                                                <option value="<%= mapServ.getKey() %>"><%= mapServ.getValue() %></option>
                                                <%
                                                }
                                                %>
                                            </select>
                                            <input type="submit" value="Agregar Servicio" name="accion" class="btn btn-success btnAS"/>
                                        </div>
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
<%--                                                        <td role="columnheader" class="TSCBE">Acción</td>--%>
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
                                                    %>
                                                    <tr role="row">
                                                        <td <%//data-label="Eliminar: "%> role="cell" class="TSCBE">
                                                            <span>
                                                            <form action="CFAPSrv" method="post">
                                                                <input type="hidden" value="<%=datosTab.getOAPD_ITEM()%>" name="tAIAD" id="tAIAD" class="form-control" />
                                                                <input type="hidden" value="<%=datosTab.getOAPD_IDSERVICIO()%>" name="tAISAD" id="tAISAD" class="form-control" />
                                                                <% // CAMPOS DE TEXTO   %>
                                                                <input type="hidden" value="<%= TXT_PESO %>" name="tPeA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_TALLA %>" name="tTaA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_IMC %>" name="tIMCA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_PCEFALICO %>" name="tPCA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_FC %>" name="tFCA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_TEMP %>" name="tTeA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_PA %>" name="tPAA" class="form-control disNone"/>
                                                                <input type="hidden" value="<%= TXT_FRESP %>" name="tFRA" class="form-control disNone"/>
                                                                <%//<!--<input type="hidden" value="<%= TXT_MOTIVO_CONSULTA >" name="tAMCA" class="form-control disNone"/>-->%>
                                                                <textarea id="tAMCA" name="tAMCA" rows="4" cols="70" class="disNone"><%= TXT_MOTIVO_CONSULTA %></textarea>
                                                                <%//<!--<input type="hidden" value="<%= TXT_EXPLORACION_FISICA >" name="tAEFA" class="form-control disNone"/>-->%>
                                                                <textarea id="tAEFA" name="tAEFA" rows="4" cols="70" class="disNone"><%= TXT_EXPLORACION_FISICA %></textarea>
                                                                <%//<!--<input type="hidden" value="<%= TXT_DIAGNOSTICO >" name="tADA" class="form-control disNone"/>-->%>
                                                                <textarea id="tADA" name="tADA" rows="4" cols="70" class="disNone"><%= TXT_DIAGNOSTICO %></textarea>
                                                                <%//<!--<input type="hidden" value="<%= TXT_TRATAMIENTO >" name="tATA" class="form-control disNone"/>-->%>
                                                                <textarea id="tATA" name="tATA" rows="4" cols="70" class="disNone"><%= TXT_TRATAMIENTO %></textarea>
                                                                <%//<!--<input type="hidden" value="<%= TXT_RECETA >" name="tARA" class="form-control disNone"/>-->%>
                                                                <textarea id="tARA" name="tARA" rows="4" cols="70" class="disNone"><%= TXT_RECETA %></textarea>
                                                                <%//<!--<input type="text" value="<%= TXT_ESTUDIOS_SOLICITADOS >" name="tAESA" class="form-control "/>-->%>
                                                                <textarea id="tAESA" name="tAESA" rows="4" cols="70" class="disNone"><%= TXT_ESTUDIOS_SOLICITADOS %></textarea>
                                                                <!--<span>-->
                                                                <input type="submit" value="X" name="accion" class="btn btn-danger text-center <%/*my-1*/%>" />
                                                                <!--</span>-->
                                                            </form>
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
                                <input type="submit" value="<%=btnName%>" name="accion" class="<%=btnCss%> btngua" />
                                <%
                                if(!(ID_ATENCION.isEmpty())) {
                                %>
                                <input type="submit" value="Eliminar Atención" id="btnPreAux" class="btn btn-outline-danger mr-2" />
                                <input type="submit" value="Eliminar Consulta" name="accion" id="btnAuxiliar" class="btn btn-warning disNone" />
                                <%
                                }
                                %>
                                <%
                                String var_volver_atras;
                                String BTN_VOLVER_ATRAS = (String) sesionDatosUsuario.getAttribute("CFAP_BTN_VOLVER_ATRAS");
                                System.out.println("_   _JSP_FA_VOLVER_ATRAS:   :"+BTN_VOLVER_ATRAS);
                                
                                if (BTN_VOLVER_ATRAS == null || BTN_VOLVER_ATRAS.isEmpty()) {
                                    var_volver_atras = urlFichaAtencionPaciente;
                                } else if (BTN_VOLVER_ATRAS.equalsIgnoreCase("VER_PACIENTE")) { // pagina de paciente 
                                    var_volver_atras = "ver_paciente.htm";
                                } else {
                                    var_volver_atras = urlFichaAtencionPaciente;
                                }
                                %>
                                <a href="<%= var_volver_atras %>" class="btn btn-danger">Volver Atras</a>
                            </div>
                            </form>
                        </div>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <%
        /*
         * OBSERVACION: 
            1- EL JAVASCRIPT (FAPConfigAux) LO UTILIZO PARA QUE LOS CAMPOS CON TEXTAREA HAGAN UN ONCHANGE 
                PASANDO LOS DATOS QUE SE LE CARGUE CON CADA MODIFICACION A LOS CAMPOS AUXILIARES (QUE SON TEXTAREA) 
                DE LA GRILLA PARA QUE AL MOMENTO DE ELIMINAR UN SERVICIO, LOS CAMPOS SE CARGUEN CON SUS PROPIOS DATOS 
                QUE ESTAN EN LOS CAMPOS AUXILIARES, YA EL ELIMINAR TIENE UN FOR Y LOS CAMPOS SE ENCUENTRAN EN EL EXTERIOR 
            2- LOS CAMPOS AUXILIARES DEBEN DE SER TEXTAREA Y NO UN INPUT TEXT PORQUE AL CARGAR DATOS DE EJEMPLO COMO: 
                (Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum"
                by Cicero are also reproduced in their exact original form)
                DONDE TIENE COMILLAS DOBLE Y UN ENTER, AL PASAR LOS DATOS A LOS CAMPOS AUXILIARES, NO SE CARGAN 
                LOS DATOS QUE EMPIEZAN DE LA COMILLA DOBLE EN ADELANTE Y SOLO LA PRIMERA PARTE QUE ESTA DETRAS DE LA COMILLA DOBLE LO QUE PASA 
        */
        // LA FUNCION DEL MENSAJE SE ENCUENTRA EN EL ARCHIVO JS configBasicSecondary 
        %>
        <script>
            /* 
 * Funciones que utilizo en las paginas que son de Ficha de Atencion Paciente
 */
    function cancelar() { // PAG: FICHA ATENCION PACIENTE VER DATOS / FICHA ATENCION PACIENTE DATOS 
//                document.getElementById("tDC").value = "<= SER_DESCRIPCION_ORI %>";
//                document.getElementById("tM").value = "<= SER_MONTO_ORI %>";
        document.getElementById("cE").value = "A"; // SELECCIONO EL VALOR ACTIVO SUPONINEDO QUE LOS VALORES SON POR LA INICIAL Y NO POR EL TEXTO COMPLETO %>
    }

    function btnMensaje(){
            event.preventDefault();
            Swal.fire({
                title: "¿Esta seguro que desea eliminar la consulta?",
                text: "Esta acción ya no se podrá deshacer, Así que piénsalo bien.",
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
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/FAPConfigAux.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasicSecondary.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="${pageContext.request.contextPath}/recursos/sweetAlert2/sweetalert2.all.min.js" type="text/javascript"></script> <% // SweetAlert2 es un plugin para mejorar mensajes  %>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>