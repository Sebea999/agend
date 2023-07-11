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
    
    
    function __main() {
        return(
        document.getElementById('btnPreAux').addEventListener('click', btnMensaje)
        )
    }
    
    export default __main