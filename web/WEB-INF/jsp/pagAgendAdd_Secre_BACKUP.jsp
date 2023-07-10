<%-- 
    Document   : pagAgendAdd
    Created on : 06-ene-2022, 11:42:56
    Author     : RYUU
--%>

<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agendamiento</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleCale.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalCale.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleModalClie.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab123.css">
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            
            <main>
            <%
                String displayMsn = "none";
                String mensaje = (String) request.getAttribute("CA_MENSAJE"); // CONTROLLER AGENDAMIENTO MENSAJE 
                String tipoMensaje = (String) request.getAttribute("CA_TIPO_MENSAJE"); // CONTROLLER AGENDAMIENTO TIPO MENSAJE 
                String claseMensaje = "";
                String segundo_mensaje = "";
                
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
                    String SEGUNDO_MENSAJE = "";
                    System.out.println("_   __JSP___SEGUNDO_MENSAJE:      :"+SEGUNDO_MENSAJE);
                    if ((sesionDatosUsuario.getAttribute("CA_SEGUNDO_MENSAJE") == null) == false) {
                        SEGUNDO_MENSAJE = (String) sesionDatosUsuario.getAttribute("CA_SEGUNDO_MENSAJE");
                        segundo_mensaje = "<p>"+SEGUNDO_MENSAJE+"</p>";
                        // LIMPIO LAS VARIABLES PARA NO MANTENER DATOS EN LA SESION Y ASI SE PUEDAN VOLVER A CARGAR CUANDO LA VALIDACION SE VUELVA A ACTIVAR DESDE EL CONTROLADOR 
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_DIAS");
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_DESDE");
                        sesionDatosUsuario.removeAttribute("VALI_PARAM_HASTA");
                        sesionDatosUsuario.removeAttribute("CA_SEGUNDO_MENSAJE");
                    }
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                    <%= segundo_mensaje %>
                </div>
            <%
                }
            %>
            <%
                String ID_AGENDAMIENTO = (String) sesionDatosUsuario.getAttribute("ID_AGENDAMIENTO");
                System.out.println("_jsp___ID_AGENDAMIENTO:  "+ID_AGENDAMIENTO);
                
//                List<BeanPersona> listaDatos;
                String AGEN_IDPACIENTE="", AGEN_FECHA="", AGEN_HORA="", AGEN_IDMEDICO="", AGEN_IDESPECIALIDAD="", AGEN_ESTADO="";
                
                // RECUPERO LAS VARIABLES DEL CONTROLADOR, Y SI SE ENCUENTRAN CARGADAS, ENTONCES CARGARIA ESAS VARIABLES PARA MOSTRAR AL USUARIO 
                if ((String)request.getAttribute("CA_AGEN_IDESPECIALIDAD") != null) {
                    AGEN_IDESPECIALIDAD = (String) request.getAttribute("CA_AGEN_IDESPECIALIDAD");
                }
                if ((String)request.getAttribute("CA_AGEN_IDPACIENTE") != null) {
                    AGEN_IDPACIENTE = (String) request.getAttribute("CA_AGEN_IDPACIENTE");
                }
                if ((String)request.getAttribute("CA_AGEN_IDMEDICO") != null) {
                    AGEN_IDMEDICO = (String) request.getAttribute("CA_AGEN_IDMEDICO");
                }
                if ((String)request.getAttribute("CA_AGEN_FECHA") != null) {
                    AGEN_FECHA = (String) request.getAttribute("CA_AGEN_FECHA");
                }
                if ((String)request.getAttribute("CA_AGEN_HORA") != null) {
                    AGEN_HORA = (String) request.getAttribute("CA_AGEN_HORA");
                }
//                if ((String)request.getAttribute("CM_MED_CINRO") != null) {
//                    MED_CINRO = (String) request.getAttribute("CM_MED_CINRO");
//                }
//                if ((String)request.getAttribute("CM_MED_RUC") != null) {
//                    MED_RUC = (String) request.getAttribute("CM_MED_RUC");
//                }
//                if ((String)request.getAttribute("CM_MED_ESTADO") != null) {
//                    MED_ESTADO = (String) request.getAttribute("CM_MED_ESTADO");
//                }
                
                // COMO UTILIZO ESTE JSP PARA REGISTRAR Y EDITAR AGENDAMIENTOS, ENTONCES DE ACUERDO AL IDMEDICO VOY A SABER PARA QUE SE UTILIZARA LA PAGINA, SI PARA AÑADIR UN NUEVO MEDICO O PARA EDITAR UNO EXISTENTE 
                String varTitulo = "AGREGAR AGENDAMIENTO";
//                if (ID_AGENDAMIENTO.isEmpty() || ID_AGENDAMIENTO.equals("")) {
//                    varTitulo = "AGENDAMIENTO - NUEVO REGISTRO";
//                } else {
//                    varTitulo = "AGENDAMIENTO - MODIFICAR REGISTRO";
//                }
            %>
                <div>
                    <div>
                        <h4 class="mainTitle"><%= varTitulo %></h4>
                    </div>
                    <div class="divTableData mt-1">
                        <form action="CASrv" method="post" autocomplete="off">
                            <div class="col-11 pac_panel">
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">CÓDIGO:</div>
                                    <div class="pac_panel_3">
                                        <input type="text" value="<%= ID_AGENDAMIENTO %>" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Paciente:</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cPac" name="cPac">
                                            <%
                                            Map<String, String> listaPacientes;
                                            listaPacientes = metodosPersona.traerComboPacientes(AGEN_IDPACIENTE, sesionDatosUsuario, "");
                                            Set setCbxPac = listaPacientes.entrySet();
                                            Iterator iCbxPac = setCbxPac.iterator();
                                            
                                            while(iCbxPac.hasNext()) {
                                                Map.Entry mapCbxPac = (Map.Entry) iCbxPac.next();
                                            %>
                                            <option value="<%= mapCbxPac.getKey() %>"><%= mapCbxPac.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                        <input type="submit" value="Agregar Nuevo Paciente" name="accionSec" class="ml-3 btn btn-outline-info" />
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Especialidad</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cEs" name="cEs">
                                            <%
                                            Map<String, String> listaEspecialidad;
                                            listaEspecialidad = metodosRefEspecialidad.cargarComboEspecialidad(AGEN_IDESPECIALIDAD);
                                            Set setCbxEspe = listaEspecialidad.entrySet();
                                            Iterator iCbxEspe = setCbxEspe.iterator();

                                            while(iCbxEspe.hasNext()) {
                                                Map.Entry mapCbxEspe = (Map.Entry) iCbxEspe.next();
                                            %>
                                            <option value="<%= mapCbxEspe.getKey() %>"><%= mapCbxEspe.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Médico:</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cMe" name="cMe">
                                            <%
                                            Map<String, String> listaMedicos;
                                            listaMedicos = metodosMedico.traerComboMedicosPH(AGEN_IDMEDICO, sesionDatosUsuario);
                                            Set setCbxMed = listaMedicos.entrySet();
                                            Iterator iCbxMed = setCbxMed.iterator();

                                            while(iCbxMed.hasNext()) {
                                                Map.Entry mapCbxMed = (Map.Entry) iCbxMed.next();
                                            %>
                                            <option value="<%= mapCbxMed.getKey() %>"><%= mapCbxMed.getValue() %></option>
                                            <%
                                            }
                                            %>
                                        </select>
                                    </div>
                                </div>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Fecha de Agendamiento:</div>
                                    <div class="pac_panel_3">
                                        <div>
                                        <input type="text" value="<%= AGEN_FECHA %>" id="tFA" name="tFA" readonly="readonly" class="wi-320 wi-320min wi-320max form-control" />
                                        <%
                                        /*
                                            OBSERVACION: 
                                            PUEDO UTILIZAR ESTA VARIABLE PARA MOSTRARLE AL USUARIO SI ES QUE SE ENCUENTRA DISPONIBLE, 
                                            PERO LO UTILIZO PARA MOSTRARLE SOLO SI NO SE ENCUENTRA DISPONIBLE EL DIA QUE SELECCIONO 
                                        */
                                        String var_mensaje_fec = (String) request.getAttribute("CA_MSN_DISP_FEC_MED");
                                        System.out.println("_    _VAR_MENSAJE_DISPONIBLE_FEC_SELEC:    :"+var_mensaje_fec);
                                        
                                        if((var_mensaje_fec == null || var_mensaje_fec.isEmpty()) == false) {
                                        %>
                                        <p class="mt-1 wi-320 alert alert-danger agen_fec_msn"><%= var_mensaje_fec %></p>
                                        <%
                                        }
                                        %>
                                        </div>
                                            <%
                                            /*
                                                01__VENTANA EMERGENTE PARA CARGAR LA FECHA DE AGENDAMIENTO ---------------
                                            */
                                            %>
                                            <input type="checkbox" id="btn-cale" />
                                            <label for="btn-cale" class="btn btn-info btnCaCli ml-3 wi-250 wi-250min wi-250max he-38">Mostrar Calendario</label>
                                            <%
                                            /*
                                                02__VENTANA EMERGENTE PARA CARGAR LA FECHA DE AGENDAMIENTO ---------------
                                            */
                                            %>
                                            <form method="post" action="CASrv">
                                                <div class="modalCale">
                                                    <div class="modalPanelCale">
                                                        <div class="panelHeader">
                                                            <div class="header01">Fecha de Agendamiento</div>
                                                            <div class="header02"><label for="btn-cale" class="btnTi">Cerrar</label></div>
                                                        </div>
                                                        
                                                        <div class="panelBodyCale">
                                                            <%//<div class="boxBody">
                                                            //    <p class="boxLabel">Seleccione su fecha de agendamiento:</p>
                                                            //</div>%>
                                                            <div class="boxBody boxBodyMiddle">
                                                                <%
                                                                //  ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---  
                                                                //                         ---         CALENDARIO          ---             
                                                                //  ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---  
                                                                %>
                                                                <div class="calendar mt-4" style="margin: auto;">
                                                                    <div class="calendar-header">
                                                                        <span class="month-picker" id="month-picker">
                                                                            February
                                                                        </span>
                                                                        <div class="year-picker">
                                                                            <span class="year-change" id="prev-year">
                                                                                <pre><</pre>
                                                                            </span>
                                                                            <span id="year">2022</span>
                                                                            <span class="year-change" id="next-year">
                                                                                <pre>></pre>
                                                                            </span>
                                                                        </div>
                                                                    </div>

                                                                    <div class="calendar-body">
                                                                        <div class="calendar-week-day">
                                                                            <div>Do</div>
                                                                            <div>Lu</div>
                                                                            <div>Ma</div>
                                                                            <div>Mi</div>
                                                                            <div>Ju</div>
                                                                            <div>Vi</div>
                                                                            <div>Sa</div>
                                                                        </div>
                                                                        <div class="calendar-days">
                                                                            <form action="CASrv" method="post">
                                                                            <div>
                                                                                1
                                                <%//                                <span></span>
                                                  //                              <span></span>
                                                  //                              <span></span>
                                                  //                              <span></span>%>
                                                                            </div>
                                                                            <div>2</div>
                                                                            <div>3</div>
                                                                            <div>4</div>
                                                                            <div>5</div>
                                                                            <div>6</div>
                                                                            <div>7</div>
                                                                            <div>1</div>
                                                                            <div>2</div>
                                                                            <div>3</div>
                                                                            <div>4</div>
                                                                            <div>5</div>
                                                                            <div>6</div>
                                                                            <div>7</div>
                                                                            </form>
                                                                        </div>
                                                                    </div>

                                                                    <div class="calendar-footer">
                                                                        <div class="toggle">
                                                                            <%
                                                                            // descomentar en calendarApp.js la primera linea si es que se descomenta el switch para cambiar a modo oscuro 
                                                                            %>
                                                <%//                            <span>Dark Mode</span>
                                                //                            <div class="dark-mode-switch">
                                                //                                <div class="dark-mode-switch-ident"></div>
                                                //                            </div>%>
                                                                        </div>
                                                                    </div>

                                                                    <div class="month-list"></div>
                                                                </div>
                                                                <%
                                                                //                          _______END_MODAL_CALENDARIO_________          
                                                                //  ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---    ---  
                                                                %>
                                                            </div>
                                                            <%//<div class="boxBody">
                                                            //    <input type="submit" value="Cargar Fecha" name="accion" class="btn btn-info" />
                                                            //</div>%>
                                                            <div class="boxBody boxBottom text-right mr-5 mt-2">
                                                                <label for="btn-cale" class="btn btn-outline-danger">Cancelar</label>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </form>
                                            <%
                                            // -----------------     END VENTANA EMERGENTE    -------------------------
                                            %>
                                    </div>
                                </div>
<%//<!--                                <div class="pac_panel_1">
//                                    <div class="pac_panel_2">Fecha de Agendamiento:</div>
//                                    <div class="pac_panel_3">
//                                        <input type="date" value="<%= AGEN_FECHA >" id="tFA" name="tFA" class="form-control" />
//                                    </div>
//                                </div>-->%>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Hora de Agendamiento:</div>
                                    <div class="pac_panel_3">
                                        <input type="time" value="<%= AGEN_HORA %>" id="tHA" name="tHA" class="form-control" />
                                    </div>
                                </div>
                                <%
                                /*
                                 * OBSERVACION: 
                                    EL MEDICO PUEDE TENER VARIOS PLAN HORARIOS EN DIFERENTES CLINICAS Y EL USUARIO 
                                    AL SELECCIONAR UNA FECHA ESTA PUEDE COINCIDIR EN ALGUNAS CLINICAS O EN UNA SOLA 
                                    ENTONCES RECUPERO LA CANTIDAD Y SI ES UNA SOLA LE MUESTRO DONDE SERIA EL AGENDAMIENTO 
                                    Y SI HAY MAS DE UNA ENTONCES VOY A MOSTRARLE UN COMBO CON LAS CLINICAS PARA QUE ELIGA 
                                    DONDE SE QUIERE AGENDAR. 
                                */
                                String AGEN_IDCLINICA = (String) sesionDatosUsuario.getAttribute("CA_AGEN_IDCLINICA");
                                String AGEN_DESC_CLINICA = (String) sesionDatosUsuario.getAttribute("CA_AGEN_DESC_CLINICA");
                                if (AGEN_DESC_CLINICA == null) {
                                    if (AGEN_IDCLINICA == null || AGEN_IDCLINICA.isEmpty()) {
                                        AGEN_DESC_CLINICA = "";
                                    } else {
                                        AGEN_DESC_CLINICA = metodosRefClinica.traerDescClinica(AGEN_IDCLINICA);
                                    }
                                }
                                String CANT_IDCLINICA = (String) request.getAttribute("CA_AGEN_CANT_IDCLINICA");
                                System.out.println("_   _JSP__AGEN_ID_CLINICA:   :"+AGEN_IDCLINICA);
                                
                                if(CANT_IDCLINICA == null || CANT_IDCLINICA.isEmpty()) {
                                    // 
                                } else if(Integer.parseInt(CANT_IDCLINICA) == 1) {
                                %>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Clinica de Agendamiento:</div>
                                    <div class="pac_panel_3">
                                        <input type="hidden" value="<%= AGEN_IDCLINICA %>" id="tAIC" name="tAIC" readonly="readonly" class="form-control" />
                                        <input type="text" value="<%= AGEN_DESC_CLINICA %>" id="tADC" name="tADC" readonly="readonly" class="form-control" />
                                    </div>
                                </div>
                                <%
                                } else if(Integer.parseInt(CANT_IDCLINICA) > 1) {
                                %>
                                <div class="pac_panel_1">
                                    <div class="pac_panel_2">Clinicas para Agendamiento:</div>
                                    <div class="pac_panel_3">
                                        <select class="form-control" id="cCli" name="cCli">
                                            <%
                                            Map<String, String> listaClinicas;
                                            listaClinicas = metodosAgendamiento.cargarCbxClinicaPHMed(metodosIniSes, AGEN_IDCLINICA, AGEN_IDMEDICO, AGEN_FECHA);
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
                                    </div>
                                </div>
                                <%
                                }
                                %>
<%//<!--                                <div class="pac_panel_1">
//                                    <div class="pac_panel_2">RUC:</div>
//                                    <div class="pac_panel_3">
//                                        <input type="text" value="<%= AGEN_ESTADO >" id="tR" name="tR" class="form-control" />
//                                    </div>
//                                </div>-->%>
                                
                                
                                <div class="pac_panel_btns mt-5">
                                    <input type="submit" name="accionSec" value="AGENDAR" class="btn btn-outline-primary" />
                                    <button type="submit" value="Add Agendamiento" name="accion" class="btn btn-primary">LIMPIAR</button>
                                    <a href="<%= urlAgendamiento %>" class="btn btn-danger">CANCELAR</a>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        
        <script src="${pageContext.request.contextPath}/recursos/js/calendarApp.js"></script>
        <script>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            <%// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR %>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            document.addEventListener('DOMContentLoaded', ()=> {
               document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
                   if (e.keyCode == 13) {
                       e.preventDefault();
//                       document.getElementById("Filtrar").click();
                   }
               }))
            });
            <%// ---------------------------------------------------------------------------------------------------------------%>
        </script>
    </body>
</html>