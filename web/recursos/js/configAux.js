
// --------------------------------------------------------------------------------------------------------------- 
// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO AL DAR ENTER SE ACTIVARA EL BOTON DE FILTRAR --
// --------------------------------------------------------------------------------------------------------------- 
    document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
        if(e.keyCode == 13) {
          e.preventDefault();
          document.getElementById("Filtrar").click();
        }
      }))
    });
// --------------------------------------------------------------------------------------------------------------- 


    function functCm() {
        var x = document.getElementById("cM").value;
        document.getElementById("tAcM").value = x;
    }

    function functTxB() {
        var x = document.getElementById("txB").value;
        document.getElementById("tAtxB").value = x;
    }

    function functFecIni() {
        var x = document.getElementById("tFFI").value;
        document.getElementById("tAtFFI").value = x;
    }

    function functFecFin() {
        var x = document.getElementById("tFFF").value;
        document.getElementById("tAtFFF").value = x;
    }

    function functClie() {
        var x = document.getElementById("cbxAddNewCli").value;
        document.getElementById("tAcbxAddNewCli").value = x;
    }

    function functCli() {
        var x = document.getElementById("cbxAddNewClinica").value;
        document.getElementById("tAcbxAddNewClinica").value = x;
    }

    function functCbxEst() {
        var x = document.getElementById("cE").value;
        document.getElementById("tAcE").value = x;
    }
    
    
