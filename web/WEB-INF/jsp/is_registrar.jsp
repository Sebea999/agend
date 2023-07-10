<%-- 
    Document   : is_registrar
    Created on : 30-jun-2022, 11:45:19
    Author     : RYUU
--%>

<%@page import="com.agend.modelo.ModelInicioSesion"%>
<%@page import="com.agend.modelo.ModelRef_Clinica"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="com.agend.javaBean.BeanPerfil"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registrar</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link href="${pageContext.request.contextPath}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/recursos/css/styleIniSes.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/stylePagCab.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    </head>
    <body>
        <%
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("-------------------------------___JSP_______REGISTRAR_________------------------------");
            ModelRef_Clinica metodosRefClinica = new ModelRef_Clinica();
            HttpSession sesionDatosUsuario = request.getSession();
            System.out.println("_   _SERIAL_UID:  :"+sesionDatosUsuario.getId());
            
            
            String claseCss="none", rptaError="";
            String usuarioDato="", passwordDato="";
            String nombre="", apellido="", cinro="", email="", telefono="", direccion="", idclinica="";
            rptaError = (String)request.getAttribute("respuesta");
            System.out.println("_   _JSP____RESPUESTA:    :"+rptaError);
            
            if (rptaError != null) {
                usuarioDato = (String)request.getAttribute("usuarioDato");
                if (usuarioDato == null) {
                    usuarioDato = "";
                }
                passwordDato = (String)request.getAttribute("passwordDato");
                if (passwordDato == null) {
                    passwordDato = "";
                }
                nombre = (String)request.getAttribute("VAR_NOMBRE");
                if (nombre == null) { nombre = ""; }
                apellido = (String)request.getAttribute("VAR_APELLIDO");
                if (apellido == null) { apellido = ""; }
                cinro = (String)request.getAttribute("VAR_CINRO");
                if (cinro == null) { cinro = ""; }
                email = (String)request.getAttribute("VAR_EMAIL");
                if (email == null) { email = ""; }
                telefono = (String)request.getAttribute("VAR_TELEFONO");
                if (telefono == null) { telefono = ""; }
                direccion = (String)request.getAttribute("VAR_DIRECCION");
                if (direccion == null) { direccion = ""; }
                idclinica = (String)request.getAttribute("VAR_IDCLINICA");
                if (idclinica == null) { idclinica = ""; }
                claseCss="block";
            }
        %>
        <div class="container w-75 mt-5 rounded shadow <%//bg-primary%>" style="/*height: 80vh*/">
            <div class="row align-items-lg-stretch">
<!--                <div class="col bg d-none d-lg-block col-md-5 col-lg-5 col-xl-6 rounded" style="height: 80vh">

                </div>-->
                <div class="col bg-white p-5 rounded-end" style="/*height: 80vh*/ margin: auto; max-width: 80%; ">
                    <%
                    if ((rptaError == null) == false) {
                    %>
                        <div class="alert alert-danger" style="display:<%=claseCss%>; font-weight: bolder;">
                            <p><%= rptaError %></p>
                        </div>
                    <%
                    }
                    %>
                    <h2 class="fw-bold text-center py-2">Cargue sus Datos</h2>
                    <!-- REGISTRESE -->
                    <form action="ISSrv" method="post" autocomplete="off">
                        <div class="mb-4">
                            <label for="nombre" class="form-label">Nombre/s:</label>
                            <input type="text" value="<%= nombre %>" class="form-control" name="txtN">
                        </div>
                        <div class="mb-4">
                            <label for="apellido" class="form-label">Apellido/s:</label>
                            <input type="text" value="<%= apellido %>" class="form-control" name="txtA">
                        </div>
                        <div class="mb-4">
                            <label for="cinro" class="form-label">Nro. CI:</label>
                            <input type="text" value="<%= cinro %>" class="form-control" name="txtCINRO">
                        </div>
                        <div class="mb-4">
                            <label for="telf" class="form-label">Email:</label>
                            <input type="text" value="<%= email %>" class="form-control" name="txtE">
                        </div>
                        <div class="mb-4">
                            <label for="telf" class="form-label">Teléfono:</label>
                            <input type="text" value="<%= telefono %>" class="form-control" name="txtT">
                        </div>
                        <div class="mb-4">
                            <label for="direccion" class="form-label">Dirección:</label>
                            <input type="text" value="<%= direccion %>" class="form-control" name="txtD">
                        </div>
                        <div class="mb-4">
                            <label for="email" class="form-label">Usuario:</label>
                            <input type="text" value="<%= usuarioDato %>" class="form-control" name="txtUsu">
                        </div>
                        <div class="mb-4">
                            <label for="password" class="form-label">Password:</label>
                            <input type="password" value="<%= passwordDato %>" class="form-control" name="txtPU">
                        </div>
                        <div class="mb-4">
                            <label for="clinica" class="form-label">Clinica:</label>
                            
                            <select class="form-control" id="cCli" name="cCli">
                                <%
                                Map<String, String> listaClinicas;
                                listaClinicas = metodosRefClinica.cargarComboClinica(idclinica);
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

                        <div class="panelbtnISR <%/*d-grid*/%>">
                            <button type="submit" value="Registrar" name="accion" class="btn btn-primary wi-250max">Registrar</button>
                            <a href="/odonto/inicio_sesion.htm" class="btn btn-danger ml-2 wi-250max">Volver Atras</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
    </body>
</html>