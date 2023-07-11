<%-- 
    Document   : pagPaciente
    Created on : 25-nov-2021, 11:09:37
    Author     : RYUU
--%>

<%@page import="java.io.*"%>
<%@page import="com.agend.javaBean.BeanPersona"%>
<%@page import="java.util.*"%>
<%--<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.LinkedHashMap"%>--%>
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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/recursos/css/styleSlider.css"/>
        <%-- Link Swiper's CSS --%>
        <link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css"/>
    </head>
    <body>
        <%@include file="menu_jsp.jsp" %> <% // INCLUYO EL MENU %>
            <main>
                
                <section>
                    <div class="swiper mySwiper container">
                        <div class="swiper-wrapper content">
                            <%
                            // contenido_ejemplo_datos_paciente#1.-
                            %>
                            <div class="swiper-slide card">
                                <div class="card-content">
                                    <div class="image">
                                        <img src="recursos/static/sin-foto.png" alt="" />
                                    </div>
                                    
                                    <div class="media-icons">
                                        <i class="fab fa-facebook"></i>
                                        <i class="fab fa-twitter"></i>
                                        <i class="fab fa-github"></i>
                                    </div>
                                    
                                    <div class="name-profession">
                                        <span class="name">Someone Name</span>
                                        <span class="profession">Web Developer</span>
                                    </div>
                                    
                                    <div class="rating">
                                        <i class="fas fa-star"></i>
                                        <i class="fas fa-star"></i>
                                        <i class="fas fa-star"></i>
                                        <i class="far fa-star"></i>
                                        <i class="far fa-star"></i>
                                    </div>
                                    
                                    <div class="button">
                                        <button class="aboutMe">About Me</button>
                                        <button class="hireMe">Hire Me</button>
                                    </div>
                                </div>
                            </div>
                            <%
                            // end.-
                            %>
                        </div>
                    </div>
                    
                    <div class="swiper-button-next"></div>
                    <div class="swiper-button-prev"></div>
                    <div class="swiper-pagination"></div>
                </section>
                
            </main>
        </div> <% // ESTE DIV QUE CIERRO ES DE LA CLASE "main-content" QUE SE ENCUENTRA DENTRO DE "menu_jsp" QUE UTILIZO PARA CARGAR EL CONTENIDO DENTRO DE LA ZONA VISIBLE %>
        <script src="${pageContext.request.contextPath}/recursos/js/configBasic.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/jquery.js" type="text/javascript"></script>
        <script src="${pageContext.request.contextPath}/recursos/js/bootstrap.min.js" type="text/javascript"></script>
        <%-- -- Swiper JS -- --%>
        <script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
        <script>
            var swiper = new Swiper(".mySwiper", {
                slidesPerView: 3,
                spaceBetween: 30,
                slidesPerGroup: 3,
                loop: true,
                loopFillGroupWithBlank: true,
                pagination: {
                    el: ".swiper-pagination",
                    clickable: true,
                },
                navigation: {
                    nextEl: ".swiper-button-next",
                    prevEl: ".swiper-button-prev",
                },
            });
        </script>
    </body>
</html>