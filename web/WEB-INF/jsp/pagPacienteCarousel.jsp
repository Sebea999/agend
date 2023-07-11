<%-- 
    Document   : pagPacienteCarousel
    Created on : 10-mar-2023, 13:24:00
    Author     : USER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/themify-icons/0.1.2/css/themify-icons.css">
        <link rel="stylesheet" href="recursos/css/styleCarousel.css" />
    </head>
    <body>
        
        <div class="slider-container" item-display-d="4" item-display-t="3" item-display-m="1">
            <div class="slider-width">
                <div class="item">1</div>
                <div class="item">2</div>
                <div class="item">3</div>
                <div class="item">4</div>
                <div class="item">5</div>
                <div class="item">6</div>
                <div class="item">7</div>
                <div class="item">8</div>
                <div class="item">9</div>
                <div class="item">10</div>
                <div class="item">11</div>
                <div class="item">12</div>
                <div class="item">13</div>
                <div class="item">14</div>
                <div class="item">15</div>
            </div>
        </div>
        <button type="button" class="btn" onclick="prev()">Prev</button>
        <button type="button" class="btn" onclick="next()">Next</button>
        
        
        <script>
            var count = 0;
            var inc = 0;
            margin = 0;
            var slider = document.getElementsByClassName("slider-width")[0];
            var itemDisplay = 0;
            if (screen.width > 990) {
                itemDisplay = document.getElementsByClassName("slider-container")[0].getAttribute("item-display-d");
                margin = itemDisplay * 5;
            }
            if (screen.width > 700 && screen.width < 990) {
                itemDisplay = document.getElementsByClassName("slider-container")[0].getAttribute("item-display-t");
                margin = itemDisplay * 6.8;
            }
            if (screen.width > 280 && screen.width < 700) {
                itemDisplay = document.getElementsByClassName("slider-container")[0].getAttribute("item-display-m");
                margin = itemDisplay * 20;
            }
            
            var items = document.getElementsByClassName("item");
            var itemleft = items.length % itemDisplay;
            var itemSlide = Math.floor(items.length / itemDisplay) - 1;
            for (let i=0; i<items.length; i++) {
                items[i].style.width = (screen.width / itemDisplay) - margin + "px";
            }
            
            function next() {
                if (inc !== itemSlide + itemleft) {
                    if (inc === itemSlide) {
                        inc = inc + itemleft;
                        count = count - (screen.width / itemDisplay) * itemleft;
                    }
                    else {
                        inc ++;
                        count = count - screen.width;
                    }
                }
                slider.style.left = count + "px";
            }
            
            function prev() {
                if (inc !== 0) {
                    if (inc === itemleft) {
                        inc = inc - itemleft;
                        count = count + (screen.width / itemDisplay) * itemleft;
                    }
                    else {
                        inc --;
                        count = count + screen.width;
                    }
                }
                slider.style.left = count + "px";
            }
            
            
            
        </script>
        
    </body>
</html>