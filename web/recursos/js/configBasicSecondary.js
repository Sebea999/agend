// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
// CODIGO JAVASCRIPT PARA EVITAR QUE AL DAR ENTER REALIZE CUALQUIER ACCION, EN ESTE CASO PARA LAS PAGINAS SECUNDARIAS Y QUE EL ENTER NO ACTIVE NINGUN BOTON  ----------%>
// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
    document.addEventListener('DOMContentLoaded', ()=> {
       document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
           if (e.keyCode == 13) {
               e.preventDefault();
           }
       }))
    });
// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
    // PARA CAMPOS DE FECHA ------------------------------------------------------------------------------------------------------------------------
// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
    document.addEventListener('DOMContentLoaded', () => {
    document.querySelectorAll('input[type=date]').forEach( node => node.addEventListener('keypress', e => {
        if(e.keyCode == 13) {
          e.preventDefault();
        }
      }))
    });
// ---------------------------------------------------------------------------------------------------------------%>
    