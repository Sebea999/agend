/* 
 * JAVASCRIPT PARA LA PAGINA DE: FICHA ATENCION PACIENTE ( pagFichaAtencionPac_Datos.jsp) 
 */
document.getElementById("tAMC").onchange = function(event) { // MOTIVO DE LA CONSULTA 
    document.getElementById("tAMCA").value = event.target.value;
};
document.getElementById("tAEF").onchange = function(event) { // EXPLORACION FISICA 
    document.getElementById("tAEFA").value = event.target.value;
};
document.getElementById("tAD").onchange = function(event) { // DIAGNOSTICO 
    document.getElementById("tADA").value = event.target.value;
};
document.getElementById("tAT").onchange = function(event) { // TRATAMIENTO 
    document.getElementById("tATA").value = event.target.value;
};
document.getElementById("tAR").onchange = function(event) { // RECETA 
    document.getElementById("tARA").value = event.target.value;
};
document.getElementById("tAES").onchange = function(event) { // ESTUDIOS SOLICITADOS 
    document.getElementById("tAESA").value = event.target.value;
};
