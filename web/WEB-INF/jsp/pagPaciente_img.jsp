<%-- 
    Document   : pagPaciente
    Created on : 25-nov-2021, 11:09:37
    Author     : RYUU
--%>

<%@page import="java.io.File"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleSlider.css"/>
        <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleCarousel.css"/>-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
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
                    <%
                    // OBSERVACION: AL PERFIL DEL MEDICO NO LE VOY A MOSTRAR LA OPCION DE AÑADIR UN NUEVO PACIENTE 
                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true) {
                    %>
                    <div>
                        <input type="submit" name="accion" value="Add Paciente" class="btn btn-outline-danger mb-2 btnAddCli" />
                    </div>
                    <%
                    }
                    %>
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
                                <select name="cM" class="form-control">
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
                                <input type="submit" name="accion" value="Limpiar" id="Limpiar" class="mb-2 btn btn-primary" />
<%//                                <!--<a href="<%=urlPaciente>" class="btn btn-primary mb-2">Limpiar</a>-->%>
                            </div>
                        </div>
                    </div>
                    
                    
                    <!--<div class="/*divTable*/" style="border: 1px solid red;">-->
                        <section>
                            <div class="container slider-container" item-display-d="4" item-display-t="3" item-display-m="1">
                                <div class="content slider-width">
                                    <%
                                    // contenido_ejemplo_datos_paciente#1.-
                                    %>
                                    <%
                                    String s = File.separator;
                                    // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
                                    String NRO_PAG = "";
                                    if (sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL") == null) {
                                        NRO_PAG = "1";
                                    } else {
                                        NRO_PAG = (String) sesionDatosUsuario.getAttribute("PAG_PAC_LISTA_ACTUAL");
                                    }
                                    System.out.println("_   _JSP____NRO_PAG_ACTUAL:     :"+NRO_PAG);
                                    
                                    List<BeanPersona> listaDatos = null;
                                    if (((List<BeanPersona>)request.getAttribute("CP_LISTA_FILTRO")) != null) { // si se hace el filtro entonces en la lista se debe almacenar del resultado que le pasamos 
                                        listaDatos = (List<BeanPersona>) request.getAttribute("CP_LISTA_FILTRO");
                                    } else {
                                        String cant_min_fija = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
                                        listaDatos = metodosPersona.cargar_grilla_paginacion(sesionDatosUsuario, NRO_PAG, cant_min_fija);
                                        sesionDatosUsuario.setAttribute("CP_BAND_FILTRO", "0"); // CON ESTO RESETEO LA BANDERA QUE UTILIZO PARA LA PAGINACION 
                                    }
                                    Iterator<BeanPersona> iterDatos = listaDatos.iterator();
                                    BeanPersona datosBean = null;
                                    
                                    while(iterDatos.hasNext()) {
                                        datosBean = iterDatos.next();
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".");
                                        System.out.println(".   _________WHILE_PAC_________");
                                        System.out.println("----");
                                        System.out.println("__  __JSP_ID_PAC:     :"+datosBean.getRP_IDPERSONA());
                                        System.out.println("__  __JSP_PACIENTE:   :"+datosBean.getRP_NOMBRE()+" "+datosBean.getRP_APELLIDO());
                                        String fotoName = datosBean.getRP_FOTO();
                                        System.out.println("__  __JSP_FOTO_NAME:  :"+fotoName);
                                        String fotoPath = datosBean.getRP_FOTO_PATH_ABS();
                                        System.out.println("__  __JSP_FOTO_PATH_COMPLETO:  :"+fotoPath);
                                        String fotoDB, sinFoto, fotoFinal;
                                        
                                        System.out.println("___CONTROL_IMG____");
//                                        if (fotoPath == null || fotoPath.isEmpty() || fotoPath.equals("")) {
//                                            System.out.println("_______IF_______");
//                                            String sinFotoRuta = "recursos"+s+"static"+s+"sin-foto.png";
////                                            String sinFotoRuta = "C:"+s+"OdontoAppWeb"+s+"web"+s+"recursos"+s+"static"+s+"sin-foto.png";
//                                            System.out.println("___SIN_FOTO_RUTA:   :"+sinFotoRuta);
//                                            File fileFoto = new File(sinFotoRuta);
////                                            sinFoto = "../recursos/static/"+fileFoto.getName();
//                                            // ruta desde el servidor web ya que por motivos de seguridad no se puede acceder desde la direccion local del directorio.-
//                                            sinFoto = "recursos"+s+"static"+s+fileFoto.getName();
////                                            sinFoto = "http:"+s+s+"localhost:9090"+s+"agend"+s+"recursos"+s+"static"+s+fileFoto.getName();
////                                            sinFoto = "http://localhost:9090/agend/recursos/static/"+fileFoto.getName();
//                                            fotoFinal = sinFoto;
//                                            
//                                        } else {
//                                            System.out.println("_______ELSE_______");
//                                            String ruta_img = fotoPath.replace("*", s);
//                                            System.out.println("____RUTA_IMG:  :"+ruta_img);
//                                            
//                                            File fileFoto = new File(ruta_img);
//                                            if (fileFoto.isFile()==true) {
//                                                System.out.println("-   -   - IF -      [ SI ES UN ARCHIVO ] -    -   -");
//                                                // si es un archivo entonces continuo controlando si se encuentra la imagen en una subcarpeta que seria el idpaciente o si se encuentra en la carpeta principal por no poder crear la subcarpeta con el idpaciente 
//                                                System.out.println("----------------------------sub-------------------------------------");
//                                                if (ruta_img.contains(datosBean.getRP_IDPERSONA())) {
//                                                    System.out.println("___IF----SUBCARPETA___________");
//                                                    fotoDB = "recursos"+s+"static"+s+datosBean.getRP_IDPERSONA()+s+fileFoto.getName();
//    //                                                fotoDB = "http:"+s+s+"localhost:9090"+s+"agend"+s+"recursos"+s+"static"+s+datosBean.getRP_IDPERSONA()+s+fileFoto.getName();
//                                                    System.out.println("____IF___SUB_CARPETA_NO_EXISTE___RUTA:  :"+fotoDB);
//                                                } else {
//                                                    System.out.println("___ELSE---SUBCARPETA_____________");
//                                                    fotoDB = "recursos"+s+"static"+s+fileFoto.getName();
//    //                                                fotoDB = "http:"+s+s+"localhost:9090"+s+"agend"+s+"recursos"+s+"static"+s+fileFoto.getName();
//                                                    System.out.println("____ELSE___SUB_CARPETA_EXISTE___RUTA:  :"+fotoDB);
//                                                }
//                                                System.out.println("--------------------------------------------------------------------");
//                                            
//                                                // ruta desde el servidor web ya que por motivos de seguridad no se puede acceder desde la direccion local del directorio.-
//    //                                            fotoDB = puertoHost+s+"recursos"+s+"static"+s+fileFoto.getName();
//    //                                            fotoDB = "http://localhost:9090/agend/recursos/static/"+fileFoto.getName();
//                                            
//                                            } else {
//                                                System.out.println("-   -   - ELSE -      [ NO ES UN ARCHIVO ] -    -   -");
//                                                // si no se guardo la imagen entonces me ingresara a este else porque devolvera un false a la pregunta de si es un archivo y en ese caso le cargare la imagen de que no es una imagen 
//                                                sinFoto = "recursos"+s+"static"+s+"sin-foto.png";
//                                                fotoDB = sinFoto;
//                                            }
//                                            fotoFinal = fotoDB;
//                                        }
                                        
                                        
                                        System.out.println("----------------------------------_____NUEVO_CONTROL_DE_IMG_____------------------------------------------------------");
                                        if (fotoName == null || fotoName.isEmpty() || fotoName.equals("")) {
                                            System.out.println("-   __IF___   IMG_PATH_IS_EMPTY-----");
                                            sinFoto = "/recursos/static/sin-foto.png";
//                                            sinFoto = "recursos"+s+"static"+s+"sin-foto.png";
                                            fotoFinal = sinFoto;
                                        } else {
                                            System.out.println("-   __ELSE__   IMG_PATH_VERIFY----");
                                            fotoDB = "/recursos/static/"+fotoName;
//                                            fotoDB = "recursos"+s+"static"+s+fotoName; // [OBS] :ruta correcta pero agregando las barras laterales segun el sistema-operativo, pero le agrego en duro y comento esta para poder tener la ruta igual a la que especifique en la etiqueta <mvc:resources> en el dispatcher-serlvet.-
//                                            fotoDB = "recursos"+s+"static"+s+datosBean.getRP_IDPERSONA()+s+fotoName;
                                            System.out.println("_   _   _IMG_PATH:  :"+fotoDB);
//                                            File ctrlImg = new File(fotoDB);
//                                            if (ctrlImg.isFile() == false) {
//                                                System.out.println("_   _   _   __THE_IMG_IS_NOT_FILE______[NEW_PATH_FOR_VERIFY]___");
//                                                fotoDB = "recursos"+s+"static"+s+fotoName;
//                                                System.out.println("_   _   _   ______NEW_IMG_PATH:  :"+fotoDB);
//                                            } else {
//                                                System.out.println("_   _   _   __[OK]_________THE_IMG_IS_FILE_________");
//                                            }
                                            fotoFinal = fotoDB;
                                        }
                                        System.out.println("----------------------------------------------------------------------------------------------------------------------");
                                        
//                                        fotoFinal = "http://localhost:9090/agend/"+fotoFinal;
                                        System.out.println("[*]___RUTA_DE_LA_FOTO_FINAL_A_CARGAR:  :"+fotoFinal);
                                        System.out.println("[*]--------");
                                    %>
                                    <div class="card mr-4">
                                        <div class="card-content">
                                            <div class="image">
                                                <img src="<c:url value="<%=fotoFinal%>" />" alt="" />
                                                <!--<img src="${pageContext.request.contextPath}/<%=fotoFinal%>" alt="" />-->
                                                <!--<img src="<%--= fotoFinal--%>" crossorigin='anonymous' alt="" />-->
                                            </div>
<!--                                            <div class="media-icons">
                                                <i class="fab fa-facebook"></i>
                                                <i class="fab fa-twitter"></i>
                                                <i class="fab fa-github"></i>
                                            </div>-->
                                            <div class="name-profession">
                                                <span class="name">
                                                    <%= datosBean.getRP_NOMBRE()+" "+datosBean.getRP_APELLIDO() %>
                                                </span>
                                                <span class="profession mt-1">
                                                    <i class="fa-solid fa-phone mr-2"></i><%= datosBean.getRP_TELFMOVIL() %>
                                                </span>
                                            </div>
<!--                                            <div class="rating">
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                                <i class="fas fa-star"></i>
                                                <i class="far fa-star"></i>
                                                <i class="far fa-star"></i>
                                            </div>-->
                                            <div class="button">
<!--                                                <button class="aboutMe">About Me</button>
                                                <button class="hireMe">Hire Me</button>-->
                                                <form action="CPSrv" method="post">
                                                    <input type="hidden" value="<%= datosBean.getRP_IDPERSONA() %>" name="tI" class="form-control" />
                                                    <input type="submit" value="Ver Expediente" name="accion" class="btn btn-secondary mr-2" />
                                                    <%
                                                    if(metodosPerfil.isPerfilAdmin(idPerfil)==true || metodosPerfil.isPerfilSecre(idPerfil)==true || metodosPerfil.isPerfilFuncio(idPerfil)==true) {
                                                    %>
                                                    <input type="submit" value="Editar" name="accion" class="btn btn-secondary" />
                                                    <%
                                                    }
                                                    %>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                    <%
                                    }
                                    %>
                                    <%
                                    // end.-
                                    %>
                                </div>
                            </div>
                            
                            <%-- == PAGINACION == --%>
                            <div class="container-fluid text-right mt-4 mr-2 mb-3">
                            <%
    //                        System.out.println(".");
    //                        System.out.println(".");
    //                        System.out.println(".");
    //                        System.out.println(".");
    //                        System.out.println("-       -        JSP_PAGINACION        -        -");
    //                        System.out.println(".");
    //                        System.out.println(".");
    //                        System.out.println(".");
                            // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES QUE TENGO SEPARANDO POR UNA CANTIDAD DINAMICA DE REGISTROS QUE EL USUARIO QUIERE VER EN LA GRILLA 
                            String cant_total_btn_lista = (String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_TOTAL_BTNS"); // PAGINA PACIENTE CANTIDAD TOTAL DE BOTONES 
    //                        System.out.println("_   __JSP___TOTAL_BTN_LISTA:   :"+cant_total_btn_lista);

                            // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE CLICS QUE EL USUARIO DIO PARA LA DERECHA PARA VER LOS DEMAS BOTONES DE PAGINAS, EMPIEZA CON 1 Y POR CADA CLIC AUMENTO UNO Y SI SE DA CLIC A LA IZQUIERDA LE RESTO UNO HASTA QUE VUELVA A SU NUMERO BASE (1) 
                            String CANT_CLIC_DERE = ""; // PAG_PAC_CANT_BTN_DERE_CLIC : PAGINA PACIENTE CANTIDAD BTN DERECHA (VER MAS >>) CLIC 
                            if ((String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC") == null) { // SI ESTA NULO POR ALGUNA RAZON DE INSTANCIA, ENTONCES LE COLOCO 1 COMO MINIMO VALOR ACEPTABLE Y SI NO ESTA NULO ENTONCES LE DEJO CON EL VALOR QUE TIENE 
                                CANT_CLIC_DERE = "1";
                            } else {
                                CANT_CLIC_DERE = (String) sesionDatosUsuario.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC");
                            }
    //                        System.out.println("_   __JSP___CANT_CLIC_DERECHA:    :"+CANT_CLIC_DERE);

                            // VARIABLE QUE UTILIZARE PARA MOSTRAR AL BOTON ACTIVO DEACUERDO AL NUMERO DE LISTA ACTUAL QUE SE ESTA MOSTRANDO 
                            String btn_css_active = "";
                            // VARIABLE QUE UTILIZO PARA COLOCAR AL BOTON ACTIVO EN UNA ETIQUETA QUE NO ENVIE UNA SOLICITUD AL SERVLET Y ESTE A SU VEZ NO VUELVA A CONSULTAR A LA BASE PARA EVITAR QUE SE SOBRECARGUE DE SOLICITUDES, YA QUE NO TIENE SENTIDO VOLVER A APRETAR EL MISMO INDICE Y SOLO A LOS DEMAS BOTONES LE CARGO UN BTN SUBMIT PARA QUE PUEDA SOLICITAR LA VISTA DE SU LITA 
                            String btn_submit = "";

                            // POR MEDIO DE LA CANTIDAD DE CLICS MULTIPLICANDO POR 3 (LA CANTIDAD DE BOTONES A MOSTRAR) ENCUENTRO EL NUMERO DEL TERCER BOTON (EJEMPLO: 2 CLIC (*3) Y EL TERCER BOTON SERIA 6) 
                            int btn_max_mostrar = Integer.parseInt(CANT_CLIC_DERE) * 3;
    //                        System.out.println("_   _JSP__btn_max_mostrar:  :"+btn_max_mostrar);
                            // AL TENER EL NUMERO DEL TERCER BOTON A MOSTRAR, RESTANDO DOS SÉ CUAL ES EL NUMERO DEL PRIMER BOTON A MOSTRAR 
                            int btn_min_mostrar = btn_max_mostrar - 2;
    //                        System.out.println("_   _JSP__btn_min_mostrar:  :"+btn_min_mostrar);

                            /*
                             * OBSERVACION: DE ESTE BLOQUE 
                                SI SE FILTRA Y SE CAMBIA LA CANTIDAD DE BOTONES Y EL NRO DE PAGINA ACTUAL, SE REALIZA CUALQUIER ACCION COMO LA DE AÑADIR UN NUEVO REGISTRO O VER UNO 
                                EL NRO DE PAG ACTUAL DE LA PAGINA PRINCIPAL SE QUEDA CON EL NRO ANTERIOR (EJ.: 4) Y SE VUELVE ATRAS CON EL BOTON DE LA PAGINA O LA FLECHA DEL NAVEGADOR 
                                EN EL CARGA GRILLA SE ESTABLECE LA PRIMERA PAGINA (1) PERO EN EL JSP SE RECUPERA (4) Y EN LAS IMPRESIONES DE ESCRITORIO PUEDO VER EN EL METODO DE CARGA GRILLA ESTA DEFINIDA (1) 
                                ENTONCES CON ESTE BLOQUE DE CODIGO CORRIGO LA PAGINA ACTUAL PARA QUE EL IF DE ABAJO DETECTE A LA PAGINA ACTUAL Y LA MARQUE AL BOTON 
                            * SOLUCIONES A ESTE PROBLEMA: 
                                AGREGUE UN BLOQUE DE CODIGO MAS EN EL CARGA GRILLA PARA FORMATEAR EL CLIC DERECHO CONTROLANDO EL NRO DE PAG ACTUAL 
                                Y AGREGUE ESTE BLOQUE DE CODIGO ACA PARA FORMATEAR LA VARIABLE DEL NRO DE PAG ACTUAL A MOSTRAR, YA QUE DESDE EL MODELO SE ACTUALIZA PERO EN EL JSP SE VE EL VALOR ANTERIOR Y POR ESA RAZON NO SE MARCA EL BOTON DE LA PAGINA ACTUAL MOSTRANDO, Y CON ESTA CONDICIONAL CONSIGO QUE ME MARQUE LA PAGINA SELECCIONADA 
                            */
                            System.out.println(".----------------------------------");
                            System.out.println("_  _NRO_PAG:  :"+NRO_PAG);
                            System.out.println("_  _BTN_MAX:  :"+btn_max_mostrar);
                            System.out.println("_  _BTN_MIN:  :"+btn_min_mostrar);
                            System.out.println("_  _CANT_CLICS_DERE:  :"+CANT_CLIC_DERE);
                            if ((Integer.parseInt(NRO_PAG) > btn_max_mostrar || Integer.parseInt(NRO_PAG) < btn_min_mostrar)) {
                                System.out.println("------IF------");
                                NRO_PAG = "1";
                            } else {
                                System.out.println("-----else------");
                            }
                            System.out.println(".----------------------------------");

                            // SI EL BOTON MINIMO A MOSTRAR ES MAYOR A UNO ENTONCES VOY A MOSTRARLE EL BOTON PARA RETROCEDER ATRAS, PERO SI ES IGUAL A UNO ES PORQUE ESTA EN EL BLOQUE DE LOS PRIMERO TRES BOTONES Y AHI NO TIENE SENTIDO QUE LE MUESTRE EL BOTON PARA IRSE MAS ATRAS  
                            if (btn_min_mostrar > 1) {
                            %>
                                <input type="submit" value="<<" name="accion" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                            <%
                            }

                            // FOR QUE HAGO RECORRIENDO LA CANTIDAD TOTAL DE BOTONES QUE TENGO PARA ENCONTRAR AL BOTON DE LA PAGINA O DE LA LISTA ACTUAL QUE EL USUARIO ESTA VIENDO 
                            for(int i=1; i <= Integer.parseInt(cant_total_btn_lista); i++) {
    //                            System.out.println(".");
    //                            System.out.println(".");
    //                            System.out.println("_  _____FOR____ITEM_TOTAL_______ i : "+i);

    //                            System.out.println("____i("+i+")  >  btn_max_mostrar("+i+")_____");
                                // SI LA CANTIDAD DE BOTONES ACTUAL QUE SE ESTA RECORRIENDO ES MAYOR A LA CANTIDAD DE BOTONES MAXIMA A MOSTRAR ENTONCES CORTO EL FOR PARA NO GASTAR RECURSOS RECORRIENDO EL FOR 
                                if (i > btn_max_mostrar) {
                                    break;
                                }

    //                            System.out.println("__  _JPS_____if( i("+i+") <= resul_multi_primo("+btn_max_mostrar+") && i("+i+") >= pag_min_mostrar("+btn_min_mostrar+")   )____");

                                // VALIDACION PARA MOSTRAR LOS TRES BOTONES 
                                // SI LA FILA ACTUAL ES MENOR AL NRO DEL BOTON ULTIMO A MOSTRAR Y ES MAYOR O IGUAL AL NUMERO MINIMO DEL BOTON A MOSTRAR 
                                if (i <= btn_max_mostrar && i >= btn_min_mostrar) {
                                    // CONTROLO PARA PODER DARLE AL BOTON UNA IMAGEN DISTINTA PARA QUE EL USUARIO SEPA QUE LISTA DE LOS BOTONES SE ENCUENTRA MOSTRANDO LOS DATOS EN LA GRILLA  
                                    if (NRO_PAG.equals(""+i+"")) {
                                        btn_css_active = "btn btn-secondary disabled";
                                        btn_submit = "0";
                                    } else { // 
                                        btn_css_active = "btn btn-outline-secondary";
                                        btn_submit = "1";
                                    }

                                // CONTROLO EL VALOR FINAL DE LA VARIABLE PARA SABER SI LE MUESTRO UN BOTON SUBMIT O UN BOTON VACIO 
                                if (btn_submit.equals("1")) {
                                %>
                                <input type="submit" value="<%= i %>" name="accion" class="py-1 px-3 mr-2 <%=btn_css_active%>" />
                                <%
                                } else if (btn_submit.equals("0")) {
                                %>
                                <a href="#" class="py-1 px-3 mr-2 <%= btn_css_active %>"><%= i %></a>
                                <%
                                } // end if btn 

                                } // end if ctrl_3_btn_mostrar 
                            } // end for cant_btns

                                // SI EL BOTON MAXIMO A MOSTRAR ES MENOR AL ITEM TOTAL ENTONCES 
                                if ((btn_max_mostrar < Integer.parseInt(cant_total_btn_lista))) {
                                %>
                                <input type="submit" value=">>" name="accion" class="py-1 px-3 mr-2 btn btn-outline-secondary" />
                                <%
                                } // end if 
                                %>
                            </div>
                        </section>
                    <!--</div>-->
                </form>           
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasic.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            function getBase64FromImageUrl(url) {
                var img = new Image();

                img.setAttribute('crossOrigin', 'anonymous');

                img.onload = function () {
                    var canvas = document.createElement("canvas");
                    canvas.width =this.width;
                    canvas.height =this.height;

                    var ctx = canvas.getContext("2d");
                    ctx.drawImage(this, 0, 0);

                    var dataURL = canvas.toDataURL("image/png");

                    alert(dataURL.replace(/^data:image\/(png|jpg);base64,/, ""));
                };

                img.src = url;
            }
        </script>
    </body>
</html>