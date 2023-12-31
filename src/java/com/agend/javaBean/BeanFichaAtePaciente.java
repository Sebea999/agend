/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.javaBean;

/**
 *
 * @author RYUU
 */
public class BeanFichaAtePaciente extends BeanFichaAtePacienteDet {
    
    // tabla :  ope_ficha_pac_nutri  
    String OFPN_IDFICHAPAC;
    String OFPN_IDAGENDAMIENTO;
    String OFPN_ITEM_AGEND_DET;
    String OFPN_IDPACIENTE;
        // OBS: CAMPOS AUXILIARES QUE UTILIZO PARA GUARDAR LOS DATOS MINIMOS DEL PACIENTE, PARA EVITAR VOLVER A HACER UN SELECT PARA RECUPERAR ESTOS DATOS.-
        String OFPN_PAC_NOMBRE;
        String OFPN_PAC_APELLIDO;
        String OFPN_PAC_NROCI;
    
    // DATOS REFERENTES A LA CONSULTA 
    String OFPN_FECHA_FICHA_ATE;
    String OFPN_MOTIVO_DE_LA_CONSULTA;
    String OFPN_ALIMENTOS_DE_PREFERENCIA;
    String OFPN_ALIMENTOS_QUE_NO_TOLERA;
    String OFPN_ALI_QUE_SUELE_COMER_GRL; // ALIMENTOS QUE SUELE COMER GENERALMENTE 
    String OFPN_CONSUMO_ALCOHOL;
    String OFPN_CONSUMO_CIGARRILLO;
    String OFPN_ALERGIAS_A_ALGO;
    String OFPN_CIRUGIAS;
    String OFPN_PADECE_ALGUNA_ENFERMEDAD;
    String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO; // MEDICAMENTOS_QUE_ESTE_CONSUMIENDO * 
    String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA;
    String OFPN_REALIZA_ACTIVIDAD_FISICA;
    String OFPN_TIPO_DE_ACTIVIDAD_FISICA;
    String OFPN_FRECUENCIA_ACT_FISICA_SEM;
    // CLASIFICACION DEL METABOLISMO 
    String OFPN_DBLCR;
    String OFPN_LGSLCM;
    String OFPN_TBDALN;
    String OFPN_DPALN;
    String OFPN_DDCCF;
    String OFPN_ESTRENHIMIENTO;
    String OFPN_TDEDBU;
    String OFPN_CANSANCIO_FATIGA;
    String OFPN_HICHAZON_ABDOMINAL; // HINCHAZON_ABDOMINAL * 
    String OFPN_INSOMNIO;
    String OFPN_MUCOSIDAD_Y_CATARRO;
    String OFPN_CALAMBRES_Y_HORMIGUEOS;
    String OFPN_ZUMBIDOS_EN_EL_OIDO;
    String OFPN_CAIDA_DE_CABELLO;
    String OFPN_UNHAS_QUEBRADIZAS;
    String OFPN_PIEL_SECA;
    String OFPN_TIPO_DE_METABOLISMO;
    // MEDICIONES POR BIOIMPEDANCIA 
    String OFPN_ESTATURA;
    String OFPN_PESO;
    String OFPN_IMC;
    String OFPN_PORC_GRASA;
    String OFPN_PORC_MUSCULO;
    String OFPN_VISCERAL;
    String OFPN_EDAD_METABOLICA;
    String OFPN_RM;
    String OFPN_TIPO_DE_BALANZA;
    // RESULTADOS Y TRATAMIENTO 
    String OFPN_RESULTADOS_TEST_BIORESONANCIA;
    String OFPN_SUPLEMENTACION_RECETADA;
    String OFPN_LABORATORIO;
    String OFPN_COMENTARIOS_GENERALES;
    
    String OFPN_USU_ALTA;
    String OFPN_FEC_ALTA;
    String OFPN_ESTADO;
    String OFPN_IDCLINICA;
    String OFPN_DESC_CLINICA; // VARIABLE AUXILIAR QUE UTILIZO PARA GUARDAR LA DESCRIPCION DE LA CLINICA PARA NO IR RECUPERANDO A TRAVES DE LA BASE CADA VEZ QUE LE QUIERA MOSTRAR EL IDCLINICA.-
    String OFPN_USU_MODI;
    String OFPN_FEC_MODI;
    String OFPN_FEC_AGEN_AUX; // CAMPO AUXILIAR QUE NO EXISTE EN LA TABLA PERO LA AGREGO PARA MOSTRAR LA FECHA DE AGENDAMIENTO SIN TENER QUE HACER UN SELECT APARTE Y CARGAR A UN BEAN PARA TRAER LOS DATOS DEL AGENDAMIENTO YA QUE QUIERO MOSTRAR LA FECHA DE AGEN EN EL PERFIL DEL PACIENTE 

    
    public BeanFichaAtePaciente() {
    }

    public BeanFichaAtePaciente(String OFPN_IDFICHAPAC, String OFPN_IDAGENDAMIENTO, String OFPN_ITEM_AGEND_DET, String OFPN_IDPACIENTE, String OFPN_FECHA_FICHA_ATE, String OFPN_MOTIVO_DE_LA_CONSULTA, String OFPN_ALIMENTOS_DE_PREFERENCIA, String OFPN_ALIMENTOS_QUE_NO_TOLERA, String OFPN_ALI_QUE_SUELE_COMER_GRL, String OFPN_CONSUMO_ALCOHOL, String OFPN_CONSUMO_CIGARRILLO, String OFPN_ALERGIAS_A_ALGO, String OFPN_CIRUGIAS, String OFPN_PADECE_ALGUNA_ENFERMEDAD, String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO, String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA, String OFPN_REALIZA_ACTIVIDAD_FISICA, String OFPN_TIPO_DE_ACTIVIDAD_FISICA, String OFPN_FRECUENCIA_ACT_FISICA_SEM, String OFPN_DBLCR, String OFPN_LGSLCM, String OFPN_TBDALN, String OFPN_DPALN, String OFPN_DDCCF, String OFPN_ESTRENHIMIENTO, String OFPN_TDEDBU, String OFPN_CANSANCIO_FATIGA, String OFPN_HICHAZON_ABDOMINAL, String OFPN_INSOMNIO, String OFPN_MUCOSIDAD_Y_CATARRO, String OFPN_CALAMBRES_Y_HORMIGUEOS, String OFPN_ZUMBIDOS_EN_EL_OIDO, String OFPN_CAIDA_DE_CABELLO, String OFPN_UNHAS_QUEBRADIZAS, String OFPN_PIEL_SECA, String OFPN_TIPO_DE_METABOLISMO, String OFPN_ESTATURA, String OFPN_PESO, String OFPN_IMC, String OFPN_PORC_GRASA, String OFPN_PORC_MUSCULO, String OFPN_VISCERAL, String OFPN_EDAD_METABOLICA, String OFPN_RM, String OFPN_TIPO_DE_BALANZA, String OFPN_RESULTADOS_TEST_BIORESONANCIA, String OFPN_SUPLEMENTACION_RECETADA, String OFPN_LABORATORIO, String OFPN_COMENTARIOS_GENERALES, String OFPN_USU_ALTA, String OFPN_FEC_ALTA, String OFPN_ESTADO, String OFPN_IDCLINICA, String OFPN_USU_MODI, String OFPN_FEC_MODI, String OFPN_FEC_AGEN_AUX) {
        this.OFPN_IDFICHAPAC = OFPN_IDFICHAPAC;
        this.OFPN_IDAGENDAMIENTO = OFPN_IDAGENDAMIENTO;
        this.OFPN_ITEM_AGEND_DET = OFPN_ITEM_AGEND_DET;
        this.OFPN_IDPACIENTE = OFPN_IDPACIENTE;
        this.OFPN_FECHA_FICHA_ATE = OFPN_FECHA_FICHA_ATE;
        this.OFPN_MOTIVO_DE_LA_CONSULTA = OFPN_MOTIVO_DE_LA_CONSULTA;
        this.OFPN_ALIMENTOS_DE_PREFERENCIA = OFPN_ALIMENTOS_DE_PREFERENCIA;
        this.OFPN_ALIMENTOS_QUE_NO_TOLERA = OFPN_ALIMENTOS_QUE_NO_TOLERA;
        this.OFPN_ALI_QUE_SUELE_COMER_GRL = OFPN_ALI_QUE_SUELE_COMER_GRL;
        this.OFPN_CONSUMO_ALCOHOL = OFPN_CONSUMO_ALCOHOL;
        this.OFPN_CONSUMO_CIGARRILLO = OFPN_CONSUMO_CIGARRILLO;
        this.OFPN_ALERGIAS_A_ALGO = OFPN_ALERGIAS_A_ALGO;
        this.OFPN_CIRUGIAS = OFPN_CIRUGIAS;
        this.OFPN_PADECE_ALGUNA_ENFERMEDAD = OFPN_PADECE_ALGUNA_ENFERMEDAD;
        this.OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = OFPN_MEDICAMENTE_Q_E_CONSUMIENDO;
        this.OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = OFPN_OTROS_DATOS_A_TENER_EN_CUENTA;
        this.OFPN_REALIZA_ACTIVIDAD_FISICA = OFPN_REALIZA_ACTIVIDAD_FISICA;
        this.OFPN_TIPO_DE_ACTIVIDAD_FISICA = OFPN_TIPO_DE_ACTIVIDAD_FISICA;
        this.OFPN_FRECUENCIA_ACT_FISICA_SEM = OFPN_FRECUENCIA_ACT_FISICA_SEM;
        this.OFPN_DBLCR = OFPN_DBLCR;
        this.OFPN_LGSLCM = OFPN_LGSLCM;
        this.OFPN_TBDALN = OFPN_TBDALN;
        this.OFPN_DPALN = OFPN_DPALN;
        this.OFPN_DDCCF = OFPN_DDCCF;
        this.OFPN_ESTRENHIMIENTO = OFPN_ESTRENHIMIENTO;
        this.OFPN_TDEDBU = OFPN_TDEDBU;
        this.OFPN_CANSANCIO_FATIGA = OFPN_CANSANCIO_FATIGA;
        this.OFPN_HICHAZON_ABDOMINAL = OFPN_HICHAZON_ABDOMINAL;
        this.OFPN_INSOMNIO = OFPN_INSOMNIO;
        this.OFPN_MUCOSIDAD_Y_CATARRO = OFPN_MUCOSIDAD_Y_CATARRO;
        this.OFPN_CALAMBRES_Y_HORMIGUEOS = OFPN_CALAMBRES_Y_HORMIGUEOS;
        this.OFPN_ZUMBIDOS_EN_EL_OIDO = OFPN_ZUMBIDOS_EN_EL_OIDO;
        this.OFPN_CAIDA_DE_CABELLO = OFPN_CAIDA_DE_CABELLO;
        this.OFPN_UNHAS_QUEBRADIZAS = OFPN_UNHAS_QUEBRADIZAS;
        this.OFPN_PIEL_SECA = OFPN_PIEL_SECA;
        this.OFPN_TIPO_DE_METABOLISMO = OFPN_TIPO_DE_METABOLISMO;
        this.OFPN_ESTATURA = OFPN_ESTATURA;
        this.OFPN_PESO = OFPN_PESO;
        this.OFPN_IMC = OFPN_IMC;
        this.OFPN_PORC_GRASA = OFPN_PORC_GRASA;
        this.OFPN_PORC_MUSCULO = OFPN_PORC_MUSCULO;
        this.OFPN_VISCERAL = OFPN_VISCERAL;
        this.OFPN_EDAD_METABOLICA = OFPN_EDAD_METABOLICA;
        this.OFPN_RM = OFPN_RM;
        this.OFPN_TIPO_DE_BALANZA = OFPN_TIPO_DE_BALANZA;
        this.OFPN_RESULTADOS_TEST_BIORESONANCIA = OFPN_RESULTADOS_TEST_BIORESONANCIA;
        this.OFPN_SUPLEMENTACION_RECETADA = OFPN_SUPLEMENTACION_RECETADA;
        this.OFPN_LABORATORIO = OFPN_LABORATORIO;
        this.OFPN_COMENTARIOS_GENERALES = OFPN_COMENTARIOS_GENERALES;
        this.OFPN_USU_ALTA = OFPN_USU_ALTA;
        this.OFPN_FEC_ALTA = OFPN_FEC_ALTA;
        this.OFPN_ESTADO = OFPN_ESTADO;
        this.OFPN_IDCLINICA = OFPN_IDCLINICA;
        this.OFPN_USU_MODI = OFPN_USU_MODI;
        this.OFPN_FEC_MODI = OFPN_FEC_MODI;
        this.OFPN_FEC_AGEN_AUX = OFPN_FEC_AGEN_AUX;
    }

    public String getOFPN_IDFICHAPAC() {
        return OFPN_IDFICHAPAC;
    }

    public void setOFPN_IDFICHAPAC(String OFPN_IDFICHAPAC) {
        this.OFPN_IDFICHAPAC = OFPN_IDFICHAPAC;
    }

    public String getOFPN_IDAGENDAMIENTO() {
        return OFPN_IDAGENDAMIENTO;
    }

    public void setOFPN_IDAGENDAMIENTO(String OFPN_IDAGENDAMIENTO) {
        this.OFPN_IDAGENDAMIENTO = OFPN_IDAGENDAMIENTO;
    }

    public String getOFPN_ITEM_AGEND_DET() {
        return OFPN_ITEM_AGEND_DET;
    }

    public void setOFPN_ITEM_AGEND_DET(String OFPN_ITEM_AGEND_DET) {
        this.OFPN_ITEM_AGEND_DET = OFPN_ITEM_AGEND_DET;
    }

    public String getOFPN_IDPACIENTE() {
        return OFPN_IDPACIENTE;
    }

    public void setOFPN_IDPACIENTE(String OFPN_IDPACIENTE) {
        this.OFPN_IDPACIENTE = OFPN_IDPACIENTE;
    }

    public String getOFPN_FECHA_FICHA_ATE() {
        return OFPN_FECHA_FICHA_ATE;
    }

    public void setOFPN_FECHA_FICHA_ATE(String OFPN_FECHA_FICHA_ATE) {
        this.OFPN_FECHA_FICHA_ATE = OFPN_FECHA_FICHA_ATE;
    }

    public String getOFPN_MOTIVO_DE_LA_CONSULTA() {
        return OFPN_MOTIVO_DE_LA_CONSULTA;
    }

    public void setOFPN_MOTIVO_DE_LA_CONSULTA(String OFPN_MOTIVO_DE_LA_CONSULTA) {
        this.OFPN_MOTIVO_DE_LA_CONSULTA = OFPN_MOTIVO_DE_LA_CONSULTA;
    }

    public String getOFPN_ALIMENTOS_DE_PREFERENCIA() {
        return OFPN_ALIMENTOS_DE_PREFERENCIA;
    }

    public void setOFPN_ALIMENTOS_DE_PREFERENCIA(String OFPN_ALIMENTOS_DE_PREFERENCIA) {
        this.OFPN_ALIMENTOS_DE_PREFERENCIA = OFPN_ALIMENTOS_DE_PREFERENCIA;
    }

    public String getOFPN_ALIMENTOS_QUE_NO_TOLERA() {
        return OFPN_ALIMENTOS_QUE_NO_TOLERA;
    }

    public void setOFPN_ALIMENTOS_QUE_NO_TOLERA(String OFPN_ALIMENTOS_QUE_NO_TOLERA) {
        this.OFPN_ALIMENTOS_QUE_NO_TOLERA = OFPN_ALIMENTOS_QUE_NO_TOLERA;
    }

    public String getOFPN_ALI_QUE_SUELE_COMER_GRL() {
        return OFPN_ALI_QUE_SUELE_COMER_GRL;
    }

    public void setOFPN_ALI_QUE_SUELE_COMER_GRL(String OFPN_ALI_QUE_SUELE_COMER_GRL) {
        this.OFPN_ALI_QUE_SUELE_COMER_GRL = OFPN_ALI_QUE_SUELE_COMER_GRL;
    }

    public String getOFPN_CONSUMO_ALCOHOL() {
        return OFPN_CONSUMO_ALCOHOL;
    }

    public void setOFPN_CONSUMO_ALCOHOL(String OFPN_CONSUMO_ALCOHOL) {
        this.OFPN_CONSUMO_ALCOHOL = OFPN_CONSUMO_ALCOHOL;
    }

    public String getOFPN_CONSUMO_CIGARRILLO() {
        return OFPN_CONSUMO_CIGARRILLO;
    }

    public void setOFPN_CONSUMO_CIGARRILLO(String OFPN_CONSUMO_CIGARRILLO) {
        this.OFPN_CONSUMO_CIGARRILLO = OFPN_CONSUMO_CIGARRILLO;
    }

    public String getOFPN_ALERGIAS_A_ALGO() {
        return OFPN_ALERGIAS_A_ALGO;
    }

    public void setOFPN_ALERGIAS_A_ALGO(String OFPN_ALERGIAS_A_ALGO) {
        this.OFPN_ALERGIAS_A_ALGO = OFPN_ALERGIAS_A_ALGO;
    }

    public String getOFPN_CIRUGIAS() {
        return OFPN_CIRUGIAS;
    }

    public void setOFPN_CIRUGIAS(String OFPN_CIRUGIAS) {
        this.OFPN_CIRUGIAS = OFPN_CIRUGIAS;
    }

    public String getOFPN_PADECE_ALGUNA_ENFERMEDAD() {
        return OFPN_PADECE_ALGUNA_ENFERMEDAD;
    }

    public void setOFPN_PADECE_ALGUNA_ENFERMEDAD(String OFPN_PADECE_ALGUNA_ENFERMEDAD) {
        this.OFPN_PADECE_ALGUNA_ENFERMEDAD = OFPN_PADECE_ALGUNA_ENFERMEDAD;
    }

    public String getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO() {
        return OFPN_MEDICAMENTE_Q_E_CONSUMIENDO;
    }

    public void setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO) {
        this.OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = OFPN_MEDICAMENTE_Q_E_CONSUMIENDO;
    }

    public String getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA() {
        return OFPN_OTROS_DATOS_A_TENER_EN_CUENTA;
    }

    public void setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA) {
        this.OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = OFPN_OTROS_DATOS_A_TENER_EN_CUENTA;
    }

    public String getOFPN_REALIZA_ACTIVIDAD_FISICA() {
        return OFPN_REALIZA_ACTIVIDAD_FISICA;
    }

    public void setOFPN_REALIZA_ACTIVIDAD_FISICA(String OFPN_REALIZA_ACTIVIDAD_FISICA) {
        this.OFPN_REALIZA_ACTIVIDAD_FISICA = OFPN_REALIZA_ACTIVIDAD_FISICA;
    }

    public String getOFPN_TIPO_DE_ACTIVIDAD_FISICA() {
        return OFPN_TIPO_DE_ACTIVIDAD_FISICA;
    }

    public void setOFPN_TIPO_DE_ACTIVIDAD_FISICA(String OFPN_TIPO_DE_ACTIVIDAD_FISICA) {
        this.OFPN_TIPO_DE_ACTIVIDAD_FISICA = OFPN_TIPO_DE_ACTIVIDAD_FISICA;
    }

    public String getOFPN_FRECUENCIA_ACT_FISICA_SEM() {
        return OFPN_FRECUENCIA_ACT_FISICA_SEM;
    }

    public void setOFPN_FRECUENCIA_ACT_FISICA_SEM(String OFPN_FRECUENCIA_ACT_FISICA_SEM) {
        this.OFPN_FRECUENCIA_ACT_FISICA_SEM = OFPN_FRECUENCIA_ACT_FISICA_SEM;
    }

    public String getOFPN_DBLCR() {
        return OFPN_DBLCR;
    }

    public void setOFPN_DBLCR(String OFPN_DBLCR) {
        this.OFPN_DBLCR = OFPN_DBLCR;
    }

    public String getOFPN_LGSLCM() {
        return OFPN_LGSLCM;
    }

    public void setOFPN_LGSLCM(String OFPN_LGSLCM) {
        this.OFPN_LGSLCM = OFPN_LGSLCM;
    }

    public String getOFPN_TBDALN() {
        return OFPN_TBDALN;
    }

    public void setOFPN_TBDALN(String OFPN_TBDALN) {
        this.OFPN_TBDALN = OFPN_TBDALN;
    }

    public String getOFPN_DPALN() {
        return OFPN_DPALN;
    }

    public void setOFPN_DPALN(String OFPN_DPALN) {
        this.OFPN_DPALN = OFPN_DPALN;
    }

    public String getOFPN_DDCCF() {
        return OFPN_DDCCF;
    }

    public void setOFPN_DDCCF(String OFPN_DDCCF) {
        this.OFPN_DDCCF = OFPN_DDCCF;
    }

    public String getOFPN_ESTRENHIMIENTO() {
        return OFPN_ESTRENHIMIENTO;
    }

    public void setOFPN_ESTRENHIMIENTO(String OFPN_ESTRENHIMIENTO) {
        this.OFPN_ESTRENHIMIENTO = OFPN_ESTRENHIMIENTO;
    }

    public String getOFPN_TDEDBU() {
        return OFPN_TDEDBU;
    }

    public void setOFPN_TDEDBU(String OFPN_TDEDBU) {
        this.OFPN_TDEDBU = OFPN_TDEDBU;
    }

    public String getOFPN_CANSANCIO_FATIGA() {
        return OFPN_CANSANCIO_FATIGA;
    }

    public void setOFPN_CANSANCIO_FATIGA(String OFPN_CANSANCIO_FATIGA) {
        this.OFPN_CANSANCIO_FATIGA = OFPN_CANSANCIO_FATIGA;
    }

    public String getOFPN_HICHAZON_ABDOMINAL() {
        return OFPN_HICHAZON_ABDOMINAL;
    }

    public void setOFPN_HICHAZON_ABDOMINAL(String OFPN_HICHAZON_ABDOMINAL) {
        this.OFPN_HICHAZON_ABDOMINAL = OFPN_HICHAZON_ABDOMINAL;
    }

    public String getOFPN_INSOMNIO() {
        return OFPN_INSOMNIO;
    }

    public void setOFPN_INSOMNIO(String OFPN_INSOMNIO) {
        this.OFPN_INSOMNIO = OFPN_INSOMNIO;
    }

    public String getOFPN_MUCOSIDAD_Y_CATARRO() {
        return OFPN_MUCOSIDAD_Y_CATARRO;
    }

    public void setOFPN_MUCOSIDAD_Y_CATARRO(String OFPN_MUCOSIDAD_Y_CATARRO) {
        this.OFPN_MUCOSIDAD_Y_CATARRO = OFPN_MUCOSIDAD_Y_CATARRO;
    }

    public String getOFPN_CALAMBRES_Y_HORMIGUEOS() {
        return OFPN_CALAMBRES_Y_HORMIGUEOS;
    }

    public void setOFPN_CALAMBRES_Y_HORMIGUEOS(String OFPN_CALAMBRES_Y_HORMIGUEOS) {
        this.OFPN_CALAMBRES_Y_HORMIGUEOS = OFPN_CALAMBRES_Y_HORMIGUEOS;
    }

    public String getOFPN_ZUMBIDOS_EN_EL_OIDO() {
        return OFPN_ZUMBIDOS_EN_EL_OIDO;
    }

    public void setOFPN_ZUMBIDOS_EN_EL_OIDO(String OFPN_ZUMBIDOS_EN_EL_OIDO) {
        this.OFPN_ZUMBIDOS_EN_EL_OIDO = OFPN_ZUMBIDOS_EN_EL_OIDO;
    }

    public String getOFPN_CAIDA_DE_CABELLO() {
        return OFPN_CAIDA_DE_CABELLO;
    }

    public void setOFPN_CAIDA_DE_CABELLO(String OFPN_CAIDA_DE_CABELLO) {
        this.OFPN_CAIDA_DE_CABELLO = OFPN_CAIDA_DE_CABELLO;
    }

    public String getOFPN_UNHAS_QUEBRADIZAS() {
        return OFPN_UNHAS_QUEBRADIZAS;
    }

    public void setOFPN_UNHAS_QUEBRADIZAS(String OFPN_UNHAS_QUEBRADIZAS) {
        this.OFPN_UNHAS_QUEBRADIZAS = OFPN_UNHAS_QUEBRADIZAS;
    }

    public String getOFPN_PIEL_SECA() {
        return OFPN_PIEL_SECA;
    }

    public void setOFPN_PIEL_SECA(String OFPN_PIEL_SECA) {
        this.OFPN_PIEL_SECA = OFPN_PIEL_SECA;
    }

    public String getOFPN_TIPO_DE_METABOLISMO() {
        return OFPN_TIPO_DE_METABOLISMO;
    }

    public void setOFPN_TIPO_DE_METABOLISMO(String OFPN_TIPO_DE_METABOLISMO) {
        this.OFPN_TIPO_DE_METABOLISMO = OFPN_TIPO_DE_METABOLISMO;
    }

    public String getOFPN_ESTATURA() {
        return OFPN_ESTATURA;
    }

    public void setOFPN_ESTATURA(String OFPN_ESTATURA) {
        this.OFPN_ESTATURA = OFPN_ESTATURA;
    }

    public String getOFPN_PESO() {
        return OFPN_PESO;
    }

    public void setOFPN_PESO(String OFPN_PESO) {
        this.OFPN_PESO = OFPN_PESO;
    }

    public String getOFPN_IMC() {
        return OFPN_IMC;
    }

    public void setOFPN_IMC(String OFPN_IMC) {
        this.OFPN_IMC = OFPN_IMC;
    }

    public String getOFPN_PORC_GRASA() {
        return OFPN_PORC_GRASA;
    }

    public void setOFPN_PORC_GRASA(String OFPN_PORC_GRASA) {
        this.OFPN_PORC_GRASA = OFPN_PORC_GRASA;
    }

    public String getOFPN_PORC_MUSCULO() {
        return OFPN_PORC_MUSCULO;
    }

    public void setOFPN_PORC_MUSCULO(String OFPN_PORC_MUSCULO) {
        this.OFPN_PORC_MUSCULO = OFPN_PORC_MUSCULO;
    }

    public String getOFPN_VISCERAL() {
        return OFPN_VISCERAL;
    }

    public void setOFPN_VISCERAL(String OFPN_VISCERAL) {
        this.OFPN_VISCERAL = OFPN_VISCERAL;
    }

    public String getOFPN_EDAD_METABOLICA() {
        return OFPN_EDAD_METABOLICA;
    }

    public void setOFPN_EDAD_METABOLICA(String OFPN_EDAD_METABOLICA) {
        this.OFPN_EDAD_METABOLICA = OFPN_EDAD_METABOLICA;
    }

    public String getOFPN_RM() {
        return OFPN_RM;
    }

    public void setOFPN_RM(String OFPN_RM) {
        this.OFPN_RM = OFPN_RM;
    }

    public String getOFPN_TIPO_DE_BALANZA() {
        return OFPN_TIPO_DE_BALANZA;
    }

    public void setOFPN_TIPO_DE_BALANZA(String OFPN_TIPO_DE_BALANZA) {
        this.OFPN_TIPO_DE_BALANZA = OFPN_TIPO_DE_BALANZA;
    }

    public String getOFPN_RESULTADOS_TEST_BIORESONANCIA() {
        return OFPN_RESULTADOS_TEST_BIORESONANCIA;
    }

    public void setOFPN_RESULTADOS_TEST_BIORESONANCIA(String OFPN_RESULTADOS_TEST_BIORESONANCIA) {
        this.OFPN_RESULTADOS_TEST_BIORESONANCIA = OFPN_RESULTADOS_TEST_BIORESONANCIA;
    }

    public String getOFPN_SUPLEMENTACION_RECETADA() {
        return OFPN_SUPLEMENTACION_RECETADA;
    }

    public void setOFPN_SUPLEMENTACION_RECETADA(String OFPN_SUPLEMENTACION_RECETADA) {
        this.OFPN_SUPLEMENTACION_RECETADA = OFPN_SUPLEMENTACION_RECETADA;
    }

    public String getOFPN_LABORATORIO() {
        return OFPN_LABORATORIO;
    }

    public void setOFPN_LABORATORIO(String OFPN_LABORATORIO) {
        this.OFPN_LABORATORIO = OFPN_LABORATORIO;
    }

    public String getOFPN_COMENTARIOS_GENERALES() {
        return OFPN_COMENTARIOS_GENERALES;
    }

    public void setOFPN_COMENTARIOS_GENERALES(String OFPN_COMENTARIOS_GENERALES) {
        this.OFPN_COMENTARIOS_GENERALES = OFPN_COMENTARIOS_GENERALES;
    }

    public String getOFPN_USU_ALTA() {
        return OFPN_USU_ALTA;
    }

    public void setOFPN_USU_ALTA(String OFPN_USU_ALTA) {
        this.OFPN_USU_ALTA = OFPN_USU_ALTA;
    }

    public String getOFPN_FEC_ALTA() {
        return OFPN_FEC_ALTA;
    }

    public void setOFPN_FEC_ALTA(String OFPN_FEC_ALTA) {
        this.OFPN_FEC_ALTA = OFPN_FEC_ALTA;
    }

    public String getOFPN_ESTADO() {
        return OFPN_ESTADO;
    }

    public void setOFPN_ESTADO(String OFPN_ESTADO) {
        this.OFPN_ESTADO = OFPN_ESTADO;
    }

    public String getOFPN_IDCLINICA() {
        return OFPN_IDCLINICA;
    }

    public void setOFPN_IDCLINICA(String OFPN_IDCLINICA) {
        this.OFPN_IDCLINICA = OFPN_IDCLINICA;
    }

    public String getOFPN_DESC_CLINICA() {
        return OFPN_DESC_CLINICA;
    }

    public void setOFPN_DESC_CLINICA(String OFPN_DESC_CLINICA) {
        this.OFPN_DESC_CLINICA = OFPN_DESC_CLINICA;
    }

    public String getOFPN_USU_MODI() {
        return OFPN_USU_MODI;
    }

    public void setOFPN_USU_MODI(String OFPN_USU_MODI) {
        this.OFPN_USU_MODI = OFPN_USU_MODI;
    }

    public String getOFPN_FEC_MODI() {
        return OFPN_FEC_MODI;
    }

    public void setOFPN_FEC_MODI(String OFPN_FEC_MODI) {
        this.OFPN_FEC_MODI = OFPN_FEC_MODI;
    }

    public String getOFPN_FEC_AGEN_AUX() {
        return OFPN_FEC_AGEN_AUX;
    }

    public void setOFPN_FEC_AGEN_AUX(String OFPN_FEC_AGEN_AUX) {
        this.OFPN_FEC_AGEN_AUX = OFPN_FEC_AGEN_AUX;
    }

    public String getOFPN_PAC_NOMBRE() {
        return OFPN_PAC_NOMBRE;
    }

    public void setOFPN_PAC_NOMBRE(String OFPN_PAC_NOMBRE) {
        this.OFPN_PAC_NOMBRE = OFPN_PAC_NOMBRE;
    }

    public String getOFPN_PAC_APELLIDO() {
        return OFPN_PAC_APELLIDO;
    }

    public void setOFPN_PAC_APELLIDO(String OFPN_PAC_APELLIDO) {
        this.OFPN_PAC_APELLIDO = OFPN_PAC_APELLIDO;
    }

    public String getOFPN_PAC_NROCI() {
        return OFPN_PAC_NROCI;
    }

    public void setOFPN_PAC_NROCI(String OFPN_PAC_NROCI) {
        this.OFPN_PAC_NROCI = OFPN_PAC_NROCI;
    }
    
}