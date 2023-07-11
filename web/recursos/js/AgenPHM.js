
document.querySelector('#cbxMed').onchange = () => {
    let idMed = document.getElementById('cbxMed').value;
    console.log('ID_MEDICO:  :'+idMed)
    let med_prueba = document.querySelector('.med_prueba')
    med_prueba.innerHTML = ''
    let day = document.createElement('div')
    day.innerHTML = `<input type="hidden" value="`+idMed+`" name="txt_idMed" class="form-control disNone" />`
    day.innerHTML += ``
    day.classList.add('idmed-date')
    med_prueba.appendChild(day)
    
    
    let idCli = document.getElementById('cbxCli').value;
    console.log('ID_CLINICA:  :'+idCli)
    let cli_prueba = document.querySelector('.cli_prueba')
    cli_prueba.innerHTML = ''
    let cli = document.createElement('div')
    cli.innerHTML = `<input type="hidden" value="`+idCli+`" name="txt_idCli" class="form-control disNone" />`
    cli.innerHTML += ``
    cli.classList.add('idcli-date')
    cli_prueba.appendChild(cli)
}


document.querySelector('#cbxCli').onchange = () => {
    let idCli = document.getElementById('cbxCli').value;
    console.log('ID_CLINICA:  :'+idCli)
    let cli_prueba = document.querySelector('.cli_prueba')
    cli_prueba.innerHTML = ''
    let cli = document.createElement('div')
    cli.innerHTML = `<input type="hidden" value="`+idCli+`" name="txt_idCli" class="form-control disNone" />`
    cli.innerHTML += ``
    cli.classList.add('idcli-date')
    cli_prueba.appendChild(cli)
    
    let idMed = document.getElementById('cbxMed').value;
    console.log('ID_MEDICO:  :'+idMed)
    let med_prueba = document.querySelector('.med_prueba')
    med_prueba.innerHTML = ''
    let day = document.createElement('div')
    day.innerHTML = `<input type="hidden" value="`+idMed+`" name="txt_idMed" class="form-control disNone" />`
    day.innerHTML += ``
    day.classList.add('idmed-date')
    med_prueba.appendChild(day)
}


document.querySelector('#btn_cph').onclick = () => {
    let idMed = document.getElementById('cbxMed').value;
    console.log('ID_MEDICO:  :'+idMed)
    let med_prueba = document.querySelector('.med_prueba')
    med_prueba.innerHTML = ''
    let day = document.createElement('div')
    day.innerHTML = `<input type="hidden" value="`+idMed+`" name="txt_idMed" class="form-control disNone" />`
    day.innerHTML += ``
    day.classList.add('idmed-date')
    med_prueba.appendChild(day)
    
    let idCli = document.getElementById('cbxCli').value;
    console.log('ID_CLINICA:  :'+idCli)
    let cli_prueba = document.querySelector('.cli_prueba')
    cli_prueba.innerHTML = ''
    let cli = document.createElement('div')
    cli.innerHTML = `<input type="hidden" value="`+idCli+`" name="txt_idCli" class="form-control disNone" />`
    cli.innerHTML += ``
    cli.classList.add('idcli-date')
    cli_prueba.appendChild(cli)
}


//document.querySelector('#btn_cph').onclick = () => {
//    console.log('----boton para buscar-----');
//}
//
//document.getElementById("btn_cph").addEventListener("click", function(event){
//    console.log('----boton para buscar-----');
//    event.preventDefault();
//    
//});


//const mysql = require('mysql')
//
//const conection = mysql.createConnection({
//    host:'localhost', 
//    user:'root', 
//    password:'admin', 
//    database:'odonto'
//})
//
//conection.connect((err) =>{
//    if(err) throw err 
//    console.log('la conexion funciona')
//})
//
//
//conection.query('SELECT * FROM rh_persona WHERE IDCLINICA = 2', (err, rows) => {
//    if(err) throw err; 
//    console.log('Los datos de la tabla son estos: ');
//    console.log(rows);
//});
//
//
//conection.end();

