// --------------------------------------------------------------------------------------------------------------------------------------------------%>
// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR %>
// --------------------------------------------------------------------------------------------------------------------------------------------------%>
    document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
        if(e.keyCode == 13) {
          e.preventDefault();
//          document.getElementById("Filtrar").click();
        }
      }))
    });
// --------------------------------------------------------------------------------------------------------------------------------------------------%>
    
    
    function limpiar() {
        document.getElementById("tC").value = "";
        document.getElementById("tDC").value = "";
        document.getElementById("tMA").value = "";
        document.getElementById("tDA").value = "";
        document.getElementById("cCli").value = "1";
        document.getElementById("cE").value = "A";
        document.getElementById("tIMAO").value = "0";
    }
    