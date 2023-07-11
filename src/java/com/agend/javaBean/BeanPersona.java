/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.javaBean;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 *
 * @author RYUU
 */
public class BeanPersona extends BeanUsuario {
    
    // RP : RH_PERSONA 
    private String RP_IDPERSONA;
    private String RP_NOMBRE;
    private String RP_APELLIDO;
    private String RP_CINRO;
    private String RP_TIPODOC;
    private String RP_IDCATEGORIA_PERSONA;
    private String RP_DESC_CATEG_PERSONA;
    private String RP_RAZON_SOCIAL;
    private String RP_RUC;
    private String RP_DIRECCION;
    private String RP_EMAIL;
    private String RP_IDBARRIO;
        private String RP_DESC_BARRIO;
    private String RP_IDCIUDAD;
        private String RP_DESC_CIUDAD;
    private String RP_TELFPARTICULAR;
    private String RP_TELFMOVIL;
    private String RP_IDCIUDADNAC;
        private String RP_DESC_CIUDADNAC;
    private String RP_FECHANAC;
    private String RP_SEXO;
    private String RP_RELIGION;
    private String RP_ESTADOCIVIL;
    private String RP_GRUPOSANGUINEO;
    private String RP_OBSERVACION;
    private String RP_FECHAALTA;
    private String RP_FECULTIMOMOV;
    private String RP_FOTO; // nombre de la foto 
    private String RP_USU_ALTA;
    private String RP_USU_MOD;
    private String RP_IDPAIS;
        private String RP_DESC_PAIS;
    private String RP_TELEFPARTICULAR;
    private String RP_OCUPACION;
    private String RP_ANTECEDENTES;
    private String RP_EXPEDIENTE_CLINICO;
    private String RP_IDCLINICA;
        private String RP_AUX_DESC_CLINICA;
    private String RP_SEGURO_MEDICO;
    // NUEVA VARIABLES AGREGADAS
    private String RP_TIENE_HIJOS;
    private String RP_CANT_HIJOS;
    private String RP_FOTO_PATH_ABS; // ruta absoluta de la foto 
//    private String RP_PROFESION;
    private String RP_IDPERSONA_NEW; // VARIABLE QUE SOLO LO USO PARA GUARDAR - DESDE EL CONTROLADOR RECUPERO Y LO GUARDO EN ESTA VARIABLE Y DESDE EL MODELO UTILIZO ESTA EN VEZ DE RECUPERAR DESDE EL METODO.-
    
//    @Lob
//    @Column(columnDefinition = "MEDIUMBLOB")
//    private String IMAGEN;
//    public String getIMAGEN() {
//        return IMAGEN;
//    }
//    public void setIMAGEN(String IMAGEN) {
//        this.IMAGEN = IMAGEN;
//    }
    
    
    public BeanPersona() {
    }

    
    // CONSTRUCTOR PARA LA PAGINA DE PACIENTE (NUTRI) / para cargar los campos y interacturar con el controlador 
    public BeanPersona(String RP_IDPERSONA, String RP_NOMBRE, String RP_APELLIDO, String RP_CINRO, String RP_TIPODOC, String RP_IDCATEGORIA_PERSONA, String RP_DESC_CATEG_PERSONA, String RP_RAZON_SOCIAL, String RP_RUC, String RP_DIRECCION, String RP_EMAIL, String RP_IDCIUDAD, String RP_DESC_CIUDAD, String RP_TELFPARTICULAR, String RP_TELFMOVIL, String RP_FECHANAC, String RP_SEXO, String RP_ESTADOCIVIL, String RP_OBSERVACION, String RP_FECHAALTA, String RP_USU_ALTA, String RP_IDCLINICA, String RP_TIENE_HIJOS, String RP_CANT_HIJOS, String RP_PROFESION, String RP_FOTO, String RP_FOTO_PATH_ABS) {
        this.RP_IDPERSONA = RP_IDPERSONA;
        this.RP_NOMBRE = RP_NOMBRE;
        this.RP_APELLIDO = RP_APELLIDO;
        this.RP_CINRO = RP_CINRO;
        this.RP_TIPODOC = RP_TIPODOC;
        this.RP_IDCATEGORIA_PERSONA = RP_IDCATEGORIA_PERSONA;
        this.RP_DESC_CATEG_PERSONA = RP_DESC_CATEG_PERSONA;
        this.RP_RAZON_SOCIAL = RP_RAZON_SOCIAL;
        this.RP_RUC = RP_RUC;
        this.RP_DIRECCION = RP_DIRECCION;
        this.RP_EMAIL = RP_EMAIL;
        this.RP_IDCIUDAD = RP_IDCIUDAD;
        this.RP_DESC_CIUDAD = RP_DESC_CIUDAD;
        this.RP_TELFPARTICULAR = RP_TELFPARTICULAR;
        this.RP_TELFMOVIL = RP_TELFMOVIL;
        this.RP_FECHANAC = RP_FECHANAC;
        this.RP_SEXO = RP_SEXO;
        this.RP_ESTADOCIVIL = RP_ESTADOCIVIL;
        this.RP_OBSERVACION = RP_OBSERVACION;
        this.RP_FECHAALTA = RP_FECHAALTA;
        this.RP_USU_ALTA = RP_USU_ALTA;
        this.RP_IDCLINICA = RP_IDCLINICA;
        this.RP_TIENE_HIJOS = RP_TIENE_HIJOS;
        this.RP_CANT_HIJOS = RP_CANT_HIJOS;
        this.RP_OCUPACION = RP_PROFESION;
        this.RP_FOTO = RP_FOTO;
        this.RP_FOTO_PATH_ABS = RP_FOTO_PATH_ABS;
    }
    
    
    // CONSTRUCTOR PARA LA PAGINA DE USUARIO 
    public BeanPersona(String RP_IDPERSONA, String RP_NOMBRE, String RP_APELLIDO, String RP_CINRO, String RP_TIPODOC, String RP_IDCATEGORIA_PERSONA, String RP_DESC_CATEG_PERSONA, String RP_RAZON_SOCIAL, String RP_RUC, String RP_DIRECCION, String RP_EMAIL, String RP_IDCIUDAD, String RP_DESC_CIUDAD, String RP_TELFPARTICULAR, String RP_TELFMOVIL, String RP_FECHANAC, String RP_SEXO, String RP_ESTADOCIVIL, String RP_OBSERVACION, String RP_FECHAALTA, String RP_USU_ALTA, String RP_IDCLINICA, String RP_TIENE_HIJOS, String RP_CANT_HIJOS, String RP_PROFESION, String SU_IDUSUARIO, String SU_IDPERSONA, String SU_USUARIO, String SU_CLAVE, String SU_ESTADO, String SU_IDPERFIL, String SU_DESC_PERFIL, String SU_ESTADO_MIGRAR, String SU_WEB, String SU_EMAIL, String SU_CONFIR_EMAIL) {
        super(SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL);
        this.RP_IDPERSONA = RP_IDPERSONA;
        this.RP_NOMBRE = RP_NOMBRE;
        this.RP_APELLIDO = RP_APELLIDO;
        this.RP_CINRO = RP_CINRO;
        this.RP_TIPODOC = RP_TIPODOC;
        this.RP_IDCATEGORIA_PERSONA = RP_IDCATEGORIA_PERSONA;
        this.RP_DESC_CATEG_PERSONA = RP_DESC_CATEG_PERSONA;
        this.RP_RAZON_SOCIAL = RP_RAZON_SOCIAL;
        this.RP_RUC = RP_RUC;
        this.RP_DIRECCION = RP_DIRECCION;
        this.RP_EMAIL = RP_EMAIL;
        this.RP_IDCIUDAD = RP_IDCIUDAD;
        this.RP_DESC_CIUDAD = RP_DESC_CIUDAD;
        this.RP_TELFPARTICULAR = RP_TELFPARTICULAR;
        this.RP_TELFMOVIL = RP_TELFMOVIL;
        this.RP_FECHANAC = RP_FECHANAC;
        this.RP_SEXO = RP_SEXO;
        this.RP_ESTADOCIVIL = RP_ESTADOCIVIL;
        this.RP_OBSERVACION = RP_OBSERVACION;
        this.RP_FECHAALTA = RP_FECHAALTA;
        this.RP_USU_ALTA = RP_USU_ALTA;
        this.RP_IDCLINICA = RP_IDCLINICA;
        this.RP_TIENE_HIJOS = RP_TIENE_HIJOS;
        this.RP_CANT_HIJOS = RP_CANT_HIJOS;
        this.RP_OCUPACION = RP_PROFESION;
    }
    

    
    
    // VARIABLES DE BEAN_PERSONA 
    public BeanPersona(String RP_IDPERSONA, String RP_NOMBRE, String RP_APELLIDO, String RP_CINRO, String RP_TIPODOC, String RP_IDCATEGORIA_PERSONA, String RP_DESC_CATEG_PERSONA, String RP_RAZON_SOCIAL, String RP_RUC, String RP_IDBARRIO, String RP_DESC_BARRIO, String RP_IDCIUDAD, String RP_DESC_CIUDAD, String RP_DIRECCION, String RP_EMAIL, String RP_TELFPARTICULAR, String RP_TELFMOVIL, String RP_IDCIUDADNAC, String RP_DESC_CIUDADNAC, String RP_FECHANAC, String RP_SEXO, String RP_RELIGION, String RP_ESTADOCIVIL, String RP_GRUPOSANGUINEO, String RP_OBSERVACION, String RP_FECHAALTA, String RP_FECULTIMOMOV, String RP_FOTO, String RP_USU_ALTA, String RP_USU_MOD, String RP_IDPAIS, String RP_DESC_PAIS, String RP_TELEFPARTICULAR, String RP_OCUPACION, String RP_ANTECEDENTES, String RP_EXPEDIENTE_CLINICO, String RP_IDCLINICA, String RP_SEGURO_MEDICO, String RP_TIENE_HIJOS, String RP_CANT_HIJOS) {
        this.RP_IDPERSONA = RP_IDPERSONA;
        this.RP_NOMBRE = RP_NOMBRE;
        this.RP_APELLIDO = RP_APELLIDO;
        this.RP_CINRO = RP_CINRO;
        this.RP_TIPODOC = RP_TIPODOC;
        this.RP_IDCATEGORIA_PERSONA = RP_IDCATEGORIA_PERSONA;
        this.RP_DESC_CATEG_PERSONA = RP_DESC_CATEG_PERSONA;
        this.RP_RAZON_SOCIAL = RP_RAZON_SOCIAL;
        this.RP_RUC = RP_RUC;
        this.RP_IDBARRIO = RP_IDBARRIO;
        this.RP_DESC_BARRIO = RP_DESC_BARRIO;
        this.RP_IDCIUDAD = RP_IDCIUDAD;
        this.RP_DESC_CIUDAD = RP_DESC_CIUDAD;
        this.RP_DIRECCION = RP_DIRECCION;
        this.RP_EMAIL = RP_EMAIL;
        this.RP_TELFPARTICULAR = RP_TELFPARTICULAR;
        this.RP_TELFMOVIL = RP_TELFMOVIL;
        this.RP_IDCIUDADNAC = RP_IDCIUDADNAC;
        this.RP_DESC_CIUDADNAC = RP_DESC_CIUDADNAC;
        this.RP_FECHANAC = RP_FECHANAC;
        this.RP_SEXO = RP_SEXO;
        this.RP_RELIGION = RP_RELIGION;
        this.RP_ESTADOCIVIL = RP_ESTADOCIVIL;
        this.RP_GRUPOSANGUINEO = RP_GRUPOSANGUINEO;
        this.RP_OBSERVACION = RP_OBSERVACION;
        this.RP_FECHAALTA = RP_FECHAALTA;
        this.RP_FECULTIMOMOV = RP_FECULTIMOMOV;
        this.RP_FOTO = RP_FOTO;
        this.RP_USU_ALTA = RP_USU_ALTA;
        this.RP_USU_MOD = RP_USU_MOD;
        this.RP_IDPAIS = RP_IDPAIS;
        this.RP_DESC_PAIS = RP_DESC_PAIS;
        this.RP_TELEFPARTICULAR = RP_TELEFPARTICULAR;
        this.RP_OCUPACION = RP_OCUPACION;
        this.RP_ANTECEDENTES = RP_ANTECEDENTES;
        this.RP_EXPEDIENTE_CLINICO = RP_EXPEDIENTE_CLINICO;
        this.RP_IDCLINICA = RP_IDCLINICA;
        this.RP_SEGURO_MEDICO = RP_SEGURO_MEDICO;
        this.RP_TIENE_HIJOS = RP_TIENE_HIJOS;
        this.RP_CANT_HIJOS = RP_CANT_HIJOS;
    }
    
//    // VARIABLES DE BEAN PERSONA + BEAN USUARIO 
    public BeanPersona(String RP_IDPERSONA, String RP_NOMBRE, String RP_APELLIDO, String RP_CINRO, String RP_TIPODOC, String RP_IDCATEGORIA_PERSONA, String RP_DESC_CATEG_PERSONA, String RP_RAZON_SOCIAL, String RP_RUC, String RP_DIRECCION, String RP_EMAIL, String RP_IDBARRIO, String RP_DESC_BARRIO, String RP_IDCIUDAD, String RP_DESC_CIUDAD, String RP_TELFPARTICULAR, String RP_TELFMOVIL, String RP_IDCIUDADNAC, String RP_DESC_CIUDADNAC, String RP_FECHANAC, String RP_SEXO, String RP_RELIGION, String RP_ESTADOCIVIL, String RP_GRUPOSANGUINEO, String RP_OBSERVACION, String RP_FECHAALTA, String RP_FECULTIMOMOV, String RP_FOTO, String RP_USU_ALTA, String RP_USU_MOD, String RP_IDPAIS, String RP_DESC_PAIS, String RP_TELEFPARTICULAR, String RP_OCUPACION, String RP_ANTECEDENTES, String RP_EXPEDIENTE_CLINICO, String RP_IDCLINICA, String RP_SEGURO_MEDICO, String RP_TIENE_HIJOS, String RP_CANT_HIJOS, String RP_FOTO_PATH_ABS, String RP_IDPERSONA_NEW, String SU_IDUSUARIO, String SU_IDPERSONA, String SU_USUARIO, String SU_CLAVE, String SU_ESTADO, String SU_IDPERFIL, String SU_DESC_PERFIL, String SU_ESTADO_MIGRAR, String SU_WEB, String SU_EMAIL, String SU_CONFIR_EMAIL) {
        super(SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL);
        this.RP_IDPERSONA = RP_IDPERSONA;
        this.RP_NOMBRE = RP_NOMBRE;
        this.RP_APELLIDO = RP_APELLIDO;
        this.RP_CINRO = RP_CINRO;
        this.RP_TIPODOC = RP_TIPODOC;
        this.RP_IDCATEGORIA_PERSONA = RP_IDCATEGORIA_PERSONA;
        this.RP_DESC_CATEG_PERSONA = RP_DESC_CATEG_PERSONA;
        this.RP_RAZON_SOCIAL = RP_RAZON_SOCIAL;
        this.RP_RUC = RP_RUC;
        this.RP_DIRECCION = RP_DIRECCION;
        this.RP_EMAIL = RP_EMAIL;
        this.RP_IDBARRIO = RP_IDBARRIO;
        this.RP_DESC_BARRIO = RP_DESC_BARRIO;
        this.RP_IDCIUDAD = RP_IDCIUDAD;
        this.RP_DESC_CIUDAD = RP_DESC_CIUDAD;
        this.RP_TELFPARTICULAR = RP_TELFPARTICULAR;
        this.RP_TELFMOVIL = RP_TELFMOVIL;
        this.RP_IDCIUDADNAC = RP_IDCIUDADNAC;
        this.RP_DESC_CIUDADNAC = RP_DESC_CIUDADNAC;
        this.RP_FECHANAC = RP_FECHANAC;
        this.RP_SEXO = RP_SEXO;
        this.RP_RELIGION = RP_RELIGION;
        this.RP_ESTADOCIVIL = RP_ESTADOCIVIL;
        this.RP_GRUPOSANGUINEO = RP_GRUPOSANGUINEO;
        this.RP_OBSERVACION = RP_OBSERVACION;
        this.RP_FECHAALTA = RP_FECHAALTA;
        this.RP_FECULTIMOMOV = RP_FECULTIMOMOV;
        this.RP_FOTO = RP_FOTO;
        this.RP_USU_ALTA = RP_USU_ALTA;
        this.RP_USU_MOD = RP_USU_MOD;
        this.RP_IDPAIS = RP_IDPAIS;
        this.RP_DESC_PAIS = RP_DESC_PAIS;
        this.RP_TELEFPARTICULAR = RP_TELEFPARTICULAR;
        this.RP_OCUPACION = RP_OCUPACION;
        this.RP_ANTECEDENTES = RP_ANTECEDENTES;
        this.RP_EXPEDIENTE_CLINICO = RP_EXPEDIENTE_CLINICO;
        this.RP_IDCLINICA = RP_IDCLINICA;
        this.RP_SEGURO_MEDICO = RP_SEGURO_MEDICO;
        this.RP_TIENE_HIJOS = RP_TIENE_HIJOS;
        this.RP_CANT_HIJOS = RP_CANT_HIJOS;
        this.RP_FOTO_PATH_ABS = RP_FOTO_PATH_ABS;
        this.RP_IDPERSONA_NEW = RP_IDPERSONA_NEW;
    }
    

    public String getRP_IDPERSONA() {
        return RP_IDPERSONA;
    }

    public void setRP_IDPERSONA(String RP_IDPERSONA) {
        this.RP_IDPERSONA = RP_IDPERSONA;
    }

    public String getRP_NOMBRE() {
        return RP_NOMBRE;
    }

    public void setRP_NOMBRE(String RP_NOMBRE) {
        this.RP_NOMBRE = RP_NOMBRE;
    }

    public String getRP_APELLIDO() {
        return RP_APELLIDO;
    }

    public void setRP_APELLIDO(String RP_APELLIDO) {
        this.RP_APELLIDO = RP_APELLIDO;
    }

    public String getRP_CINRO() {
        return RP_CINRO;
    }

    public void setRP_CINRO(String RP_CINRO) {
        this.RP_CINRO = RP_CINRO;
    }

    public String getRP_TIPODOC() {
        return RP_TIPODOC;
    }

    public void setRP_TIPODOC(String RP_TIPODOC) {
        this.RP_TIPODOC = RP_TIPODOC;
    }

    public String getRP_IDCATEGORIA_PERSONA() {
        return RP_IDCATEGORIA_PERSONA;
    }

    public void setRP_IDCATEGORIA_PERSONA(String RP_IDCATEGORIA_PERSONA) {
        this.RP_IDCATEGORIA_PERSONA = RP_IDCATEGORIA_PERSONA;
    }

    public String getRP_DESC_CATEG_PERSONA() {
        return RP_DESC_CATEG_PERSONA;
    }

    public void setRP_DESC_CATEG_PERSONA(String RP_DESC_CATEG_PERSONA) {
        this.RP_DESC_CATEG_PERSONA = RP_DESC_CATEG_PERSONA;
    }

    public String getRP_RAZON_SOCIAL() {
        return RP_RAZON_SOCIAL;
    }

    public void setRP_RAZON_SOCIAL(String RP_RAZON_SOCIAL) {
        this.RP_RAZON_SOCIAL = RP_RAZON_SOCIAL;
    }

    public String getRP_RUC() {
        return RP_RUC;
    }

    public void setRP_RUC(String RP_RUC) {
        this.RP_RUC = RP_RUC;
    }

    public String getRP_IDBARRIO() {
        return RP_IDBARRIO;
    }

    public void setRP_IDBARRIO(String RP_IDBARRIO) {
        this.RP_IDBARRIO = RP_IDBARRIO;
    }

    public String getRP_DESC_BARRIO() {
        return RP_DESC_BARRIO;
    }

    public void setRP_DESC_BARRIO(String RP_DESC_BARRIO) {
        this.RP_DESC_BARRIO = RP_DESC_BARRIO;
    }

    public String getRP_IDCIUDAD() {
        return RP_IDCIUDAD;
    }

    public void setRP_IDCIUDAD(String RP_IDCIUDAD) {
        this.RP_IDCIUDAD = RP_IDCIUDAD;
    }

    public String getRP_DESC_CIUDAD() {
        return RP_DESC_CIUDAD;
    }

    public void setRP_DESC_CIUDAD(String RP_DESC_CIUDAD) {
        this.RP_DESC_CIUDAD = RP_DESC_CIUDAD;
    }

    public String getRP_DIRECCION() {
        return RP_DIRECCION;
    }

    public void setRP_DIRECCION(String RP_DIRECCION) {
        this.RP_DIRECCION = RP_DIRECCION;
    }

    public String getRP_EMAIL() {
        return RP_EMAIL;
    }

    public void setRP_EMAIL(String RP_EMAIL) {
        this.RP_EMAIL = RP_EMAIL;
    }

    public String getRP_TELFPARTICULAR() {
        return RP_TELFPARTICULAR;
    }

    public void setRP_TELFPARTICULAR(String RP_TELFPARTICULAR) {
        this.RP_TELFPARTICULAR = RP_TELFPARTICULAR;
    }

    public String getRP_TELFMOVIL() {
        return RP_TELFMOVIL;
    }

    public void setRP_TELFMOVIL(String RP_TELFMOVIL) {
        this.RP_TELFMOVIL = RP_TELFMOVIL;
    }

    public String getRP_IDCIUDADNAC() {
        return RP_IDCIUDADNAC;
    }

    public void setRP_IDCIUDADNAC(String RP_IDCIUDADNAC) {
        this.RP_IDCIUDADNAC = RP_IDCIUDADNAC;
    }

    public String getRP_DESC_CIUDADNAC() {
        return RP_DESC_CIUDADNAC;
    }

    public void setRP_DESC_CIUDADNAC(String RP_DESC_CIUDADNAC) {
        this.RP_DESC_CIUDADNAC = RP_DESC_CIUDADNAC;
    }

    public String getRP_FECHANAC() {
        return RP_FECHANAC;
    }

    public void setRP_FECHANAC(String RP_FECHANAC) {
        this.RP_FECHANAC = RP_FECHANAC;
    }

    public String getRP_SEXO() {
        return RP_SEXO;
    }

    public void setRP_SEXO(String RP_SEXO) {
        this.RP_SEXO = RP_SEXO;
    }

    public String getRP_RELIGION() {
        return RP_RELIGION;
    }

    public void setRP_RELIGION(String RP_RELIGION) {
        this.RP_RELIGION = RP_RELIGION;
    }

    public String getRP_ESTADOCIVIL() {
        return RP_ESTADOCIVIL;
    }

    public void setRP_ESTADOCIVIL(String RP_ESTADOCIVIL) {
        this.RP_ESTADOCIVIL = RP_ESTADOCIVIL;
    }

    public String getRP_GRUPOSANGUINEO() {
        return RP_GRUPOSANGUINEO;
    }

    public void setRP_GRUPOSANGUINEO(String RP_GRUPOSANGUINEO) {
        this.RP_GRUPOSANGUINEO = RP_GRUPOSANGUINEO;
    }

    public String getRP_OBSERVACION() {
        return RP_OBSERVACION;
    }

    public void setRP_OBSERVACION(String RP_OBSERVACION) {
        this.RP_OBSERVACION = RP_OBSERVACION;
    }

    public String getRP_FECHAALTA() {
        return RP_FECHAALTA;
    }

    public void setRP_FECHAALTA(String RP_FECHAALTA) {
        this.RP_FECHAALTA = RP_FECHAALTA;
    }

    public String getRP_FECULTIMOMOV() {
        return RP_FECULTIMOMOV;
    }

    public void setRP_FECULTIMOMOV(String RP_FECULTIMOMOV) {
        this.RP_FECULTIMOMOV = RP_FECULTIMOMOV;
    }

    public String getRP_FOTO() {
        return RP_FOTO;
    }

    public void setRP_FOTO(String RP_FOTO) {
        this.RP_FOTO = RP_FOTO;
    }

    public String getRP_USU_ALTA() {
        return RP_USU_ALTA;
    }

    public void setRP_USU_ALTA(String RP_USU_ALTA) {
        this.RP_USU_ALTA = RP_USU_ALTA;
    }

    public String getRP_USU_MOD() {
        return RP_USU_MOD;
    }

    public void setRP_USU_MOD(String RP_USU_MOD) {
        this.RP_USU_MOD = RP_USU_MOD;
    }

    public String getRP_IDPAIS() {
        return RP_IDPAIS;
    }

    public void setRP_IDPAIS(String RP_IDPAIS) {
        this.RP_IDPAIS = RP_IDPAIS;
    }

    public String getRP_DESC_PAIS() {
        return RP_DESC_PAIS;
    }

    public void setRP_DESC_PAIS(String RP_DESC_PAIS) {
        this.RP_DESC_PAIS = RP_DESC_PAIS;
    }

    public String getRP_TELEFPARTICULAR() {
        return RP_TELEFPARTICULAR;
    }

    public void setRP_TELEFPARTICULAR(String RP_TELEFPARTICULAR) {
        this.RP_TELEFPARTICULAR = RP_TELEFPARTICULAR;
    }

    public String getRP_OCUPACION() {
        return RP_OCUPACION;
    }

    public void setRP_OCUPACION(String RP_OCUPACION) {
        this.RP_OCUPACION = RP_OCUPACION;
    }

    public String getRP_ANTECEDENTES() {
        return RP_ANTECEDENTES;
    }

    public void setRP_ANTECEDENTES(String RP_ANTECEDENTES) {
        this.RP_ANTECEDENTES = RP_ANTECEDENTES;
    }

    public String getRP_EXPEDIENTE_CLINICO() {
        return RP_EXPEDIENTE_CLINICO;
    }

    public void setRP_EXPEDIENTE_CLINICO(String RP_EXPEDIENTE_CLINICO) {
        this.RP_EXPEDIENTE_CLINICO = RP_EXPEDIENTE_CLINICO;
    }

    public String getRP_IDCLINICA() {
        return RP_IDCLINICA;
    }

    public void setRP_IDCLINICA(String RP_IDCLINICA) {
        this.RP_IDCLINICA = RP_IDCLINICA;
    }

    public String getRP_SEGURO_MEDICO() {
        return RP_SEGURO_MEDICO;
    }

    public void setRP_SEGURO_MEDICO(String RP_SEGURO_MEDICO) {
        this.RP_SEGURO_MEDICO = RP_SEGURO_MEDICO;
    }

    public String getRP_TIENE_HIJOS() {
        return RP_TIENE_HIJOS;
    }

    public void setRP_TIENE_HIJOS(String RP_TIENE_HIJOS) {
        this.RP_TIENE_HIJOS = RP_TIENE_HIJOS;
    }

    public String getRP_CANT_HIJOS() {
        return RP_CANT_HIJOS;
    }

    public void setRP_CANT_HIJOS(String RP_CANT_HIJOS) {
        this.RP_CANT_HIJOS = RP_CANT_HIJOS;
    }

    public String getRP_FOTO_PATH_ABS() {
        return RP_FOTO_PATH_ABS;
    }

    public void setRP_FOTO_PATH_ABS(String RP_FOTO_PATH_ABS) {
        this.RP_FOTO_PATH_ABS = RP_FOTO_PATH_ABS;
    }

    
    public String getRP_IDPERSONA_NEW() {
        return RP_IDPERSONA_NEW;
    }

    public void setRP_IDPERSONA_NEW(String RP_IDPERSONA_NEW) {
        this.RP_IDPERSONA_NEW = RP_IDPERSONA_NEW;
    }

    public String getRP_AUX_DESC_CLINICA() {
        return RP_AUX_DESC_CLINICA;
    }

    public void setRP_AUX_DESC_CLINICA(String RP_AUX_DESC_CLINICA) {
        this.RP_AUX_DESC_CLINICA = RP_AUX_DESC_CLINICA;
    }
    
    
    
} // END CLASS 