<%-- 
    Document   : pagPaciente
    Created on : 25-nov-2021, 11:09:37
    Author     : RYUU
--%>

<%--<%@page import="com.agend.config.Paginacion"%>--%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Paciente</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
        <!--<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">-->
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxst.icons8.com/vue-static/landings/line-awesome/line-awesome/1.3.0/css/line-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleMenuPrincipal.css">        
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
                
                if (mensaje != null) { // SI ES DIFERENTE A NULL ENTONCES MOSTRARE LAS ETIQUETAS PARA QUE EL USUARIO PUEDA VER EL MENSAJE 
            %>
                <div class="<%= claseMensaje %>" style="display: <%= displayMsn %>;">
                    <p><%= mensaje %></p>
                </div>
            <%
                }
            %>
                
                <form action="CPSrv" method="post" autocomplete="off">
                    <div>
                        <h4 class="mainTitle">PACIENTE - GESTION DE PACIENTES</h4>
                    </div>
                    <div>
                        <input type="submit" name="accion" value="Add Paciente" class="btn btn-danger mb-2 btnAddCli" />
                    </div>
                    <div class="mainFilter">
                        <%
                        // RECUPERAMOS LOS DATOS DEL FILTRO EN CASO DE QUE SE HAGA PARA VOLVER A MOSTRAR Y SI NO, ENTONCES PASARIA DATOS VACIOS 
                        String cbxMostrar = (String) request.getAttribute("CP_CBX_MOSTRAR");
                        if (cbxMostrar == null || cbxMostrar.isEmpty()) {
                            cbxMostrar = "";
                        }
                        String txtFiltro = (String) request.getAttribute("CP_TXT_BUSCAR");
                        if (txtFiltro == null || txtFiltro.isEmpty()) {
                            txtFiltro = "";
                        }
                        %>
                        <div class="mainFilterBox">
                            <div>
                                <p class="mainLabelSearch">Mostrar:</p>
                                <select class="form-control" name="cM">
                                <%
                                // Combo Filter Limit Registro
                                Map<String, String> cbxFilterLimit = new LinkedHashMap();
                                cbxFilterLimit = metodosIniSes.cargarFilLimitReg(cbxFilterLimit, cbxMostrar); 
                                Set setListFilLim = cbxFilterLimit.entrySet();
                                Iterator iListFilLim = setListFilLim.iterator();

                                while(iListFilLim.hasNext()) {
                                    Map.Entry limitMap = (Map.Entry) iListFilLim.next();
                                %>
                                    <option value="<%= limitMap.getKey() %>"><%= limitMap.getValue() %></option>
                                <%
                                }
                                %>
                                </select>
                                <p>registros</p>
                            </div>
                        </div>
                        <div class="mainFilterBox boxFiltroBtn">
                            <div>
                                <p class="mainLabelSearch">Buscar:</p>
                                <input type="text" name="txB" value="<%= txtFiltro %>" class="form-control" />
                            </div>
                            
                            <div>
                                <input type="submit" name="accion" value="Filtrar" id="Filtrar" class="mb-2 mr-2 btn btn-primary" />
                                <a href="<%=urlPaciente%>" class="btn btn-primary mb-2">Limpiar</a>
                            </div>
                        </div>
                    </div>
                    
                    <div class="divTable" style="border: 1px solid red;">
                        <table role="table" class="tableFilter">
                            <thead role="rowgroup">
                                <tr role="row">
                                    <td role="columnheader" class="C_CC">Nombre y Apellido</td>
                                    <td role="columnheader" class="C_CT">Nro. CI</td>
                                    <td role="columnheader" class="C_CT">Teléfono</td>
                                    <td role="columnheader" class="C_CE">EDITAR</td>
                                </tr>
                            </thead>
                            <tbody role="rowgroup">
                                <%
                                    // OBTENGO EL NUMERO DE PAGINA 
                                    String NRO_PAG = "1";
                                    if (sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL") == null) {
                                        NRO_PAG = "1";
                                    } else {
                                        NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL");
                                    }
                                    System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
//                                    String prueba = "PAG_PAC_LISTA"+NRO_PAG+"";
//                                    System.out.println("_   _JSP____LISTA_NRO_DE_PAG_ACTUAL:     ___:"+prueba);
                                    
                                    List<BeanPersona> listaDatos = null;
                                    if (((List<BeanPersona>)request.getAttribute("CP_LISTA_FILTRO")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        listaDatos = (List<BeanPersona>) request.getAttribute("CP_LISTA_FILTRO");
                                        
                                    } else if((NRO_PAG.equals("1")) == false) { // SI LA PAGINA ACTUAL ES DIFERENTE A UNO ENTONCES SI LLAMO A LA LISTA QUE CORRESPONDA AL NUMERO DE PAGINA ACTUAL 
                                        System.out.println("____ELSE___IF____");
//                                        try {
//                                            listaDatos = (List<BeanPersona>) sesionDatosUsuario.getAttribute("PAG_PAC_LISTA"+NRO_PAG+"");
//                                        } catch(NullPointerException e) {
//                                            System.out.println("____JSP________LISTA___DIFERENTE__A__UNO____________");
                                            // obs: no existe mas el metodo 
//                                            listaDatos = metodosPersona.cargar_grilla_prueba(sesionDatosUsuario, NRO_PAG);
//                                        }
                                        
                                    } else { // y si no entonces le decimos que carge nomas a traves del metodo que le pasamos 
                                        // obs.: no existe mas el metodo 
//                                        listaDatos = metodosPersona.cargar_grilla_prueba(sesionDatosUsuario, "1"); /* EN EL CARGA GRILLA YA FILTRO POR 10 REGISTROS NOMAS Y LUEGO EL AL CAMBIAR ESO SE VA A LLAMAR A OTRO METODO QUE SI VA A OBTENER EL VALOR DE REGISTROS QUE QUIERE TRAER PERO COMO POR DEFECTO VA A APARECER ESE NRO ENTONCES CON ESE DEFAULT FITLRO */
                                    }
                                    Iterator<BeanPersona> iterDatos = listaDatos.iterator();
                                    BeanPersona datosBean = null;
                                    
                                    while(iterDatos.hasNext()) {
                                        datosBean = iterDatos.next();
                                %>
                                <tr role="row">
                                    <td role="cell" class="C_CC">
                                        <%= datosBean.getRP_NOMBRE()+" "+datosBean.getRP_APELLIDO() %>
                                    </td>
                                    <td role="cell" class="C_CCI">
                                        <%= datosBean.getRP_CINRO()%>
                                    </td>
                                    <td role="cell" class="C_CT">
                                        <%= datosBean.getRP_TELFMOVIL() %>
                                    </td>
                                    <td role="cell" class="C_CE">
                                        <form action="CPSrv" method="post">
                                            <input type="text" value="<%= datosBean.getRP_IDPERSONA() %>" name="tI" class="form-control" />
                                            <input type="submit" value="Editar" name="accion" class="btn btn-light" />
                                        </form>
                                    </td>
                                </tr>
                                <%
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="container-fluid" style="border: 1px solid red;">
                        <%
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("-               JSP                -");
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println(".");
//                        Paginacion paginacion = new Paginacion();
                        String item_total = (String) sesionDatosUsuario.getAttribute("PAG_PAC_ITEM_TOTAL");
                        System.out.println("_   __JSP___ITEM_TOTAL_LISTA:   :"+item_total);
                        
                        String CANT_NRO_PRIMO = "";
                        if ((String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_NRO_PRIMO_CLIC") == null) {
                            CANT_NRO_PRIMO = "2";
                        } else {
                            CANT_NRO_PRIMO = (String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_NRO_PRIMO_CLIC");
                        }
                        System.out.println("_   __JSP___CANT_NRO_PRIMO_CLIC:    :"+CANT_NRO_PRIMO);
                        
                        // VARIABLE QUE UTILIZARE PARA MOSTRAR AL BOTON ACTIVO DEACUERDO AL NUMERO DE LISTA ACTUAL QUE SE ESTA MOSTRANDO 
                        String btn_css_active = "";
                        // VARIABLE QUE UTILIZO PARA COLOCAR AL BOTON ACTIVO EN UNA ETIQUETA QUE NO ENVIE UNA SOLICITUD AL SERVLET Y ESTE A SU VEZ NO VUELVA A CONSULTAR A LA BASE PARA EVITAR QUE SE SOBRECARGUE DE SOLICITUDES, YA QUE NO TIENE SENTIDO VOLVER A APRETAR EL MISMO INDICE Y SOLO A LOS DEMAS BOTONES LE CARGO UN BTN SUBMIT PARA QUE PUEDA SOLICITAR LA VISTA DE SU LITA 
                        String btn_submit = "";
                        
                        // VARIABLE QUE UTILIZO PARA CONTABILIZAR LA CANTIDAD DE NUMEROS PRIMOS 
                        int conteo_pag_primos_tres = 0;
                        
                        for(int i=1; i <= Integer.parseInt(item_total); i++) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("_  _____FOR____ITEM_TOTAL_______ i : "+i);
//                            // CONTROLO PARA PODER DARLE AL BOTON UNA IMAGEN DISTINTA PARA QUE EL USUARIO SEPA EN QUE PAGINA ESTA MOSTRANDO 
//                            if (NRO_PAG.equals(""+i+"")) {
//                                btn_css_active = "btn btn-secondary disabled";
//                                btn_submit = "0";
//                            } else { // SI ES 1 EL NRO DE PAG ENTONCES SE VA A MANTENER EL "ACTIVE" Y POR ESO LA LIMPIO SI ES QUE NO COINCIDE 
//                                btn_css_active = "btn btn-outline-secondary";
//                                btn_submit = "1";
//                            }
                            // btn btn-link
                            
                            String resultado = String.valueOf(i/3);
                            System.out.println("__  __i( "+i+" ) / 3 ");
                            System.out.println("__  _JSP___RESULTADO:   "+resultado);
                            
//                            if (paginacion.encontrar_primo_tres(i) == false) {
//                                System.out.println("___DECIMAL___");
//                                
//                            } else {
//                                System.out.println("___ENTERO___");
//                                conteo_pag_primos_tres = conteo_pag_primos_tres + 1;
//                            }
//                            System.out.println("_   _CONTEO_PAG_PRIMO_TRES:  "+conteo_pag_primos_tres);
                            
                            if (conteo_pag_primos_tres == Integer.parseInt(CANT_NRO_PRIMO)) {
                                System.out.println("_   __IF____NRO_PRIMO_TOTAL_ENCONTRADO");
                                System.out.println(".");
                                System.out.println(".");
                                System.out.println(".");
                        %>
                            <%
                            for (int x = i-3; x < i; x++) {
                                System.out.println("__FOR____x: "+x);
                                System.out.println("_   _nro_pag: "+NRO_PAG);
                                // 
                                if (NRO_PAG.equals(""+x+"")) {
                                    btn_css_active = "btn btn-secondary disabled";
                                    btn_submit = "0";
                                } else { // 
                                    btn_css_active = "btn btn-outline-secondary";
                                    btn_submit = "1";
                                }
                            %>
                            <%
                            // CONTROLO EL VALOR FINAL DE LA VARIABLE PARA SABER SI LE MUESTRO UN BOTON SUBMIT O UN BOTON VACIO 
                            if (btn_submit.equals("1")) {
                            %>
                            <input type="submit" value="<%= x %>" name="accion" class="py-1 px-3 mr-2 <%=btn_css_active%>" />
                            <%
                            } else if (btn_submit.equals("0")) {
                            %>
                            <a href="#" class="py-1 px-3 mr-2 <%= btn_css_active %>"><%= x %></a>
                            <%
                            }
                            %>
                            <%
                            } // end for nro_primo 
                            %>
                        <%
                            } // end if nro_primo
                            
                            // colocar validacion que cuente que si el resultado de i es mayor a tres para colocar el boton de ver los siguientes botones 
                            // agregar evento en el controlador 
                            %>
                            
                            <%
                        
                            
                        } // end for 
                        %>
                        </div>
                    </div>
                </form>
            </main>
        
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        
        <script>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            <%// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR %>
            <%// ---------------------------------------------------------------------------------------------------------------%>
            document.addEventListener('DOMContentLoaded', ()=> {
               document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
                   if (e.keyCode == 13) {
                       e.preventDefault();
                       document.getElementById("Filtrar").click();
                   }
               }))
            });
            <%// ---------------------------------------------------------------------------------------------------------------%>
        </script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>