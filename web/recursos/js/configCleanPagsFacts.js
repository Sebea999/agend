/* 
 * METODOS PARA LIMPIAR LOS CAMPOS DE LAS PAGINAS DE FACTURA 
*/
    function limpiarFCS() { /* PAG: FACTURA - CARGAR SERVICIO */
        document.getElementById("tI").value = "";
        document.getElementById("tD").value = "";
//                document.getElementById("tM").value = "";
        document.getElementById("cI").value = "0";
        document.getElementById("tP").value = "";
        document.getElementById("tC").value = "1";
    }

    function limpiarFAID() { /* PAG: FACTURA - ADD INTERES DET */
        document.getElementById("tD").value = "";
        document.getElementById("cI").value = "0";
        document.getElementById("tP").value = "";
    }

    function limpiarFADD() { /* PAG: FACTURA - ADD DESCUENTO DET */
        document.getElementById("tD").value = "";
        document.getElementById("cI").value = "0";
        document.getElementById("tP").value = "";
    }
    