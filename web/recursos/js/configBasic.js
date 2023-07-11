/* 
 * CODIGO PARA ACTIVAR EL BOTON DE ENTER AL DARLE CLIC A LA TECLA DE ENTER SOBRE CUALQUIER CAJA DE TEXTO 
 */
document.addEventListener('DOMContentLoaded', () => {
document.querySelectorAll('input[type=text]').forEach( node => node.addEventListener('keypress', e => {
    if(e.keyCode == 13) {
      e.preventDefault();
      document.getElementById("Filtrar").click();
    }
  }))
});
