/*
SQLyog Community v12.2.1 (64 bit)
MySQL - 8.0.22 : Database - odonto
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`odonto` /*!40100 DEFAULT CHARACTER SET latin1 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `odonto`;

/*Table structure for table `cc_cuenta_cliente` */

DROP TABLE IF EXISTS `cc_cuenta_cliente`;

CREATE TABLE `cc_cuenta_cliente` (
  `IDCUENTACLIENTE` int NOT NULL,
  `IDPERSONA` int NOT NULL,
  `IDAGENDAMIENTO` int DEFAULT NULL,
  `ITEM_AGEN` int DEFAULT NULL,
  `NROCUOTA` int DEFAULT NULL,
  `FEC_VENCIMIENTO` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `MONTO` decimal(50,3) DEFAULT NULL,
  `SALDO` decimal(50,3) DEFAULT NULL,
  `COMENTARIO` varchar(500) DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'C: cobrado, A: activo;',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IDCUENTACLIENTE`,`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `cc_cuenta_cliente` */

insert  into `cc_cuenta_cliente`(`IDCUENTACLIENTE`,`IDPERSONA`,`IDAGENDAMIENTO`,`ITEM_AGEN`,`NROCUOTA`,`FEC_VENCIMIENTO`,`MONTO`,`SALDO`,`COMENTARIO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,3,1,1,1,'2023-02-14 15:00:00','0.000','0.000','agendamiento en la clinica central la fecha 14/02/2023 a las 15:00.','C',1,'2023-02-13 12:28:50'),
(2,4,1,2,1,'2023-03-14 15:15:00','0.000','0.000','agendamiento en la clinica central la fecha 14/03/2023 a las 15:15.','C',1,'2023-03-02 11:46:40'),
(3,4,1,3,1,'2023-03-21 16:16:00','0.000','0.000','agendamiento en la clinica central la fecha 21/03/2023 a las 16:16.','X',4,'2023-03-02 12:20:30');

/*Table structure for table `ope_agendamiento` */

DROP TABLE IF EXISTS `ope_agendamiento`;

CREATE TABLE `ope_agendamiento` (
  `IDAGENDAMIENTO` int NOT NULL,
  `IDCLINICA` int DEFAULT NULL COMMENT 'IDCLINICA DONDE EL MEDICO TIENE UN PLAN HORARIO Y EN DONDE EL PACIENTE ELIGIRIA PARA IR A CONSULTAR',
  `IDMEDICO` int DEFAULT NULL,
  `IDESPECIALIDAD` int DEFAULT NULL,
  `IDPERSONA` int DEFAULT NULL,
  `FECHA` date DEFAULT NULL,
  `TURNO` varchar(15) DEFAULT NULL,
  `DESDE` timestamp NULL DEFAULT NULL,
  `HASTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`IDAGENDAMIENTO`),
  KEY `fkc_id_clinica` (`IDCLINICA`),
  KEY `fkc_id_especialidad` (`IDESPECIALIDAD`),
  KEY `fkc_id_medico` (`IDMEDICO`),
  CONSTRAINT `fkc_id_clinica` FOREIGN KEY (`IDCLINICA`) REFERENCES `ope_clinica` (`IDCLINICA`),
  CONSTRAINT `fkc_id_especialidad` FOREIGN KEY (`IDESPECIALIDAD`) REFERENCES `rh_especialidad` (`IDESPECIALIDAD`),
  CONSTRAINT `fkc_id_medico` FOREIGN KEY (`IDMEDICO`) REFERENCES `rh_persona` (`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_agendamiento` */

insert  into `ope_agendamiento`(`IDAGENDAMIENTO`,`IDCLINICA`,`IDMEDICO`,`IDESPECIALIDAD`,`IDPERSONA`,`FECHA`,`TURNO`,`DESDE`,`HASTA`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,1,2,1,NULL,NULL,NULL,NULL,NULL,'A',1,'2023-02-13 12:28:50');

/*Table structure for table `ope_agendamiento_det` */

DROP TABLE IF EXISTS `ope_agendamiento_det`;

CREATE TABLE `ope_agendamiento_det` (
  `IDAGENDAMIENTO` int NOT NULL,
  `ITEM` int NOT NULL,
  `FECHA_AGEN` date DEFAULT NULL,
  `HORA` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `IDPACIENTE` int DEFAULT NULL,
  `NROPACIENTEFICHA` varchar(100) DEFAULT NULL,
  `MOTIVO` varchar(50) DEFAULT NULL,
  `SEGUROMED` varchar(50) DEFAULT NULL,
  `IDSEGUROMED` int DEFAULT NULL,
  `VISITATIPO` varchar(50) DEFAULT NULL,
  `COMENTARIO` varchar(500) DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'P: PENDIENTE; A: ACTIVO; C: CERRADO;',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `FEC_ATENCION` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `IDFACTURA` int DEFAULT NULL,
  `AGENDADO` char(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'COLUMNA QUE ME DIRA SI YA ME HE AGENDADO O NO CON EL MEDICO, PARA EVITAR QUE EL DISPATCHER DEL SERVLET VUELVA A REPETIR LA ACCION DE GUARDAR UTILIZANDO ESTE CAMPO COMO BANDERA',
  `IDCONFIGAGEND` int DEFAULT '0' COMMENT 'LO GUARDO PARA TENER COMO REFERENCIA CUAL ID UTILICE APARTE DE GUARDAR LA DESCRIPCION EN LA CUENTA CLIENTE LO GUARDO EN EL CAMPO DE COMENTARIO YA QUE NO LO UTILIZO PARA OTRA COSA (09/09/22)',
  `MOTIVO_CONSULTA` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`IDAGENDAMIENTO`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_agendamiento_det` */

insert  into `ope_agendamiento_det`(`IDAGENDAMIENTO`,`ITEM`,`FECHA_AGEN`,`HORA`,`IDPACIENTE`,`NROPACIENTEFICHA`,`MOTIVO`,`SEGUROMED`,`IDSEGUROMED`,`VISITATIPO`,`COMENTARIO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`FEC_ATENCION`,`IDFACTURA`,`AGENDADO`,`IDCONFIGAGEND`,`MOTIVO_CONSULTA`) values 
(1,1,'2023-02-14','2023-02-14 15:00:00',3,'','','',0,'','agendamiento en la clinica central la fecha 14/02/2023 a las 15:00.','A',1,'2023-02-13 12:28:50',NULL,0,'SI',19,NULL),
(1,2,'2023-03-14','2023-03-14 15:15:00',4,'','','',0,'','agendamiento en la clinica central la fecha 14/03/2023 a las 15:15.','A',1,'2023-03-02 11:46:40',NULL,0,'SI',19,'EXAMPLE<br/>EXAMPLE<br/>EXAMPLE'),
(1,3,'2023-03-21','2023-03-21 16:16:00',4,'','','',0,'','agendamiento en la clinica central la fecha 21/03/2023 a las 16:16.','X',4,'2023-03-02 12:20:30',NULL,0,'SI',19,'EXAMPLE NEW<br/>EXAMPLE NEW<br/>EXAMPLE NEW ');

/*Table structure for table `ope_atencion_paciente` */

DROP TABLE IF EXISTS `ope_atencion_paciente`;

CREATE TABLE `ope_atencion_paciente` (
  `IDATENCIONPAC` int NOT NULL,
  `IDAGENDAMIENTO` int DEFAULT NULL COMMENT 'ID DEL AGENDAMIENTO',
  `ITEM_AGEND_DET` int DEFAULT NULL COMMENT 'ITEM DEL DETALLE DEL AGENDAMIENTO',
  `IDPACIENTE` int DEFAULT NULL,
  `PESO` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `TALLA` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `IMC` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `P_CEFALICO` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `FC` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `TEMP` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `PA` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `F_RESP` varchar(20) DEFAULT NULL COMMENT 'PARÁMETROS OPCIONALES',
  `MOTIVO_CONSULTA` varchar(500) DEFAULT NULL COMMENT 'MOTIVO DE CONSULTA / INTERROGATORIO',
  `EXPLORACION_FISICA` varchar(500) DEFAULT NULL COMMENT 'EXPLORACION FISICA',
  `DIAGNOSTICO` varchar(800) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DIAGNÓSTICO',
  `TRATAMIENTO` varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'TRATAMIENTO',
  `RECETA` varchar(800) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'RECETA',
  `ESTUDIOS_SOLICITADOS` varchar(800) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'ESTUDIOS SOLICITADOS / REALIZADOS',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `USU_MODI` int DEFAULT NULL COMMENT 'AL ANULAR O MODIFICAR EL REGISTRO VOY A CARGAR EL USUARIO Y LA FECHA EN ESTOS CAMPOS',
  `FEC_MODI` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`IDATENCIONPAC`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_atencion_paciente` */

/*Table structure for table `ope_atencion_paciente_det` */

DROP TABLE IF EXISTS `ope_atencion_paciente_det`;

CREATE TABLE `ope_atencion_paciente_det` (
  `IDATENCIONPAC` int NOT NULL,
  `ITEM` int NOT NULL,
  `IDSERVICIO` int DEFAULT NULL,
  `MONTO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `USU_MODI` int DEFAULT NULL COMMENT 'AL ANULAR O MODIFICAR EL REGISTRO VOY A CARGAR EL USUARIO Y LA FECHA EN ESTOS CAMPOS',
  `FEC_MODI` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`IDATENCIONPAC`,`ITEM`),
  KEY `cn_id_servicio` (`IDSERVICIO`),
  CONSTRAINT `cn_id_servicio` FOREIGN KEY (`IDSERVICIO`) REFERENCES `rh_servicio` (`IDSERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_atencion_paciente_det` */

/*Table structure for table `ope_clinica` */

DROP TABLE IF EXISTS `ope_clinica`;

CREATE TABLE `ope_clinica` (
  `IDCLINICA` int NOT NULL AUTO_INCREMENT,
  `DESC_CLINICA` varchar(100) DEFAULT NULL,
  `DIRECCION` varchar(80) DEFAULT NULL,
  `TELEFONO` varchar(20) DEFAULT NULL,
  `EMAIL` varchar(50) DEFAULT NULL,
  `ESTADO` char(1) DEFAULT 'A',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` datetime DEFAULT NULL,
  PRIMARY KEY (`IDCLINICA`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `ope_clinica` */

insert  into `ope_clinica`(`IDCLINICA`,`DESC_CLINICA`,`DIRECCION`,`TELEFONO`,`EMAIL`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,'CENTRAL','','0','A','A',NULL,NULL);

/*Table structure for table `ope_factura` */

DROP TABLE IF EXISTS `ope_factura`;

CREATE TABLE `ope_factura` (
  `IDFACTURA` int NOT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `NROFACTURA` varchar(150) DEFAULT NULL,
  `TIPOFACTURA` varchar(2) DEFAULT NULL,
  `IDCLIENTE` int DEFAULT NULL,
  `FECHAFACTURA` date DEFAULT NULL,
  `IDARQUEO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `TOTAL_FACTURA` decimal(50,3) DEFAULT NULL,
  `TOTAL_DCTO` decimal(50,3) DEFAULT NULL,
  `TOTAL_IVA10` decimal(50,3) DEFAULT NULL,
  `TOTAL_IVA5` decimal(50,3) DEFAULT NULL,
  `TOTAL_GRAV10` decimal(50,3) DEFAULT NULL,
  `TOTAL_GRAV5` decimal(50,3) DEFAULT NULL,
  `TOTAL_EXENTA` decimal(50,3) DEFAULT NULL,
  `TOTAL_IVA` decimal(50,3) DEFAULT NULL,
  `IDFORMACOBRO` int DEFAULT NULL,
  `LETRAS` varchar(500) DEFAULT NULL,
  `OBSERVACION` varchar(500) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IDFACTURA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_factura` */

/*Table structure for table `ope_factura_det` */

DROP TABLE IF EXISTS `ope_factura_det`;

CREATE TABLE `ope_factura_det` (
  `IDFACTURA` int DEFAULT NULL,
  `ITEM` int DEFAULT NULL,
  `IDCONCEPTO` int DEFAULT NULL,
  `IDTIPOCONCEPTO` int DEFAULT NULL,
  `CANTIDAD` int DEFAULT NULL,
  `PRECIO` decimal(50,3) DEFAULT NULL,
  `IDIMPUESTO` int DEFAULT NULL,
  `EXENTO` decimal(50,3) DEFAULT NULL,
  `IVA5` decimal(50,3) DEFAULT NULL,
  `IVA10` decimal(50,3) DEFAULT NULL,
  `GRAV5` decimal(50,3) DEFAULT NULL,
  `GRAV10` decimal(50,3) DEFAULT NULL,
  `SUBTOTAL` decimal(50,3) DEFAULT NULL,
  `DESCRIPCION_AUX` varchar(500) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'CARGO LA DESCRIPCION DEL CONCEPTO, AUNQUE NO LO UTILICE PARA LOS CONCEPTO DE CUENTA CLIENTE Y PRODUCTO, LO NECESITO MAS PARA EL CONCEPTO DE AGREGAR INTERES GUARDAR SU DESCRIPCION Y LA DESCRIPCION DE DESCUENTO'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_factura_det` */

/*Table structure for table `ope_ficha_pac_nutri` */

DROP TABLE IF EXISTS `ope_ficha_pac_nutri`;

CREATE TABLE `ope_ficha_pac_nutri` (
  `IDFICHAPAC` int NOT NULL,
  `IDAGENDAMIENTO` int DEFAULT NULL COMMENT 'ID DEL AGENDAMIENTO',
  `ITEM_AGEND_DET` int DEFAULT NULL COMMENT 'ITEM DEL DETALLE DEL AGENDAMIENTO',
  `IDPACIENTE` int DEFAULT NULL,
  `FECHA_FICHA_ATE` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `MOTIVO_DE_LA_CONSULTA` varchar(200) DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `ALIMENTOS_DE_PREFERENCIA` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `ALIMENTOS_QUE_NO_TOLERA` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `ALI_QUE_SUELE_COMER_GRL` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `CONSUMO_ALCOHOL` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `CONSUMO_CIGARRILLO` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `ALERGIAS_A_ALGO` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `CIRUGIAS` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `PADECE_ALGUNA_ENFERMEDAD` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `MEDICAMENTE_Q_E_CONSUMIENDO` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `OTROS_DATOS_A_TENER_EN_CUENTA` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `REALIZA_ACTIVIDAD_FISICA` int DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `TIPO_DE_ACTIVIDAD_FISICA` varchar(50) DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `FRECUENCIA_ACT_FISICA_SEM` varchar(30) DEFAULT NULL COMMENT 'DATOS REFERENTES A LA CONSULTA',
  `DBLCR` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (DIGIERE BIEN LAS CARNES ROJAS) (SI/NO)',
  `LGSLCM` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (LAS GRASAS SATURADAS LE CAEN MAL)',
  `TBDALN` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (TIENE BUENA DIGESTION A LA NOCHE)',
  `DPALN` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (DUERME PROFUNDAMENTE A LA NOCHE)',
  `DDCCF` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (DOLORES DE CABEZA CON FRECUENCIA)',
  `ESTRENHIMIENTO` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (ESTRENHIMIENTO)',
  `TDEDBU` varchar(10) DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (TIPO DE ESCALA DE BRISTOL USUAL)',
  `CANSANCIO_FATIGA` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (CANSANCIO, FATIGA)',
  `HICHAZON_ABDOMINAL` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (HICHAZON ABDOMINAL)',
  `INSOMNIO` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (INSOMNIO)',
  `MUCOSIDAD_Y_CATARRO` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (MUCOSIDAD Y CATARRO)',
  `CALAMBRES_Y_HORMIGUEOS` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (CALAMBRES Y/O HORMIGUEOS)',
  `ZUMBIDOS_EN_EL_OIDO` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (ZUMBIDOS EN EL OIDO)',
  `CAIDA_DE_CABELLO` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (CAIDA DEL CABELLO)',
  `UNHAS_QUEBRADIZAS` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (UNHAS QUEBRADIZAS)',
  `PIEL_SECA` int DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (PIEL SECA)',
  `TIPO_DE_METABOLISMO` char(1) DEFAULT NULL COMMENT 'CLASIFICACION DEL METABOLISMO (TIPO DE METABOLISMO :(ACELERADO / TRANQUILO))',
  `ESTATURA` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `PESO` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `IMC` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `PORC_GRASA` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `PORC_MUSCULO` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `VISCERAL` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `EDAD_METABOLICA` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `RM` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `TIPO_DE_BALANZA` varchar(10) DEFAULT NULL COMMENT 'MEDICIONES POR BIOIMPEDANCIA',
  `RESULTADOS_TEST_BIORESONANCIA` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'RESULTADOS Y TRATAMIENTO',
  `SUPLEMENTACION_RECETADA` varchar(100) DEFAULT NULL COMMENT 'RESULTADOS Y TRATAMIENTO',
  `LABORATORIO` varchar(50) DEFAULT NULL COMMENT 'RESULTADOS Y TRATAMIENTO',
  `COMENTARIOS_GENERALES` varchar(150) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'RESULTADOS Y TRATAMIENTO',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `USU_MODI` varchar(10) DEFAULT NULL COMMENT 'AL ANULAR O MODIFICAR EL REGISTRO VOY A CARGAR EL USUARIO Y LA FECHA EN ESTOS CAMPOS',
  `FEC_MODI` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`IDFICHAPAC`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_ficha_pac_nutri` */

insert  into `ope_ficha_pac_nutri`(`IDFICHAPAC`,`IDAGENDAMIENTO`,`ITEM_AGEND_DET`,`IDPACIENTE`,`FECHA_FICHA_ATE`,`MOTIVO_DE_LA_CONSULTA`,`ALIMENTOS_DE_PREFERENCIA`,`ALIMENTOS_QUE_NO_TOLERA`,`ALI_QUE_SUELE_COMER_GRL`,`CONSUMO_ALCOHOL`,`CONSUMO_CIGARRILLO`,`ALERGIAS_A_ALGO`,`CIRUGIAS`,`PADECE_ALGUNA_ENFERMEDAD`,`MEDICAMENTE_Q_E_CONSUMIENDO`,`OTROS_DATOS_A_TENER_EN_CUENTA`,`REALIZA_ACTIVIDAD_FISICA`,`TIPO_DE_ACTIVIDAD_FISICA`,`FRECUENCIA_ACT_FISICA_SEM`,`DBLCR`,`LGSLCM`,`TBDALN`,`DPALN`,`DDCCF`,`ESTRENHIMIENTO`,`TDEDBU`,`CANSANCIO_FATIGA`,`HICHAZON_ABDOMINAL`,`INSOMNIO`,`MUCOSIDAD_Y_CATARRO`,`CALAMBRES_Y_HORMIGUEOS`,`ZUMBIDOS_EN_EL_OIDO`,`CAIDA_DE_CABELLO`,`UNHAS_QUEBRADIZAS`,`PIEL_SECA`,`TIPO_DE_METABOLISMO`,`ESTATURA`,`PESO`,`IMC`,`PORC_GRASA`,`PORC_MUSCULO`,`VISCERAL`,`EDAD_METABOLICA`,`RM`,`TIPO_DE_BALANZA`,`RESULTADOS_TEST_BIORESONANCIA`,`SUPLEMENTACION_RECETADA`,`LABORATORIO`,`COMENTARIOS_GENERALES`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`,`IDCLINICA`,`USU_MODI`,`FEC_MODI`) values 
(1,1,1,3,'2023-02-20 12:05:00','example','example','example','example','','','example','example','example','example','example<br/>example<br/>exampleexample',1,'777','8888',1,1,1,1,1,0,'',0,0,0,0,2,2,2,2,2,'1','170','70','22','10','1','22','5','1','22','example','example','examplef','examplefinal',1,'2023-02-22 12:04:49','I',1,'','2023-02-22 12:04:49'),
(2,1,1,3,'2023-02-19 19:00:00','motivo-de-la-consulta-example','alimentos-de-preferencia','alimentos-que-no-tolera','que-suele-comer-generalmente','consumo-alcohol','consumo-cigarrillo','alergias-a-algo','cirugias','padece-alguna-enfermedad-cual','medicamentos-que-este-consumiendo','otros-datos-a-tener-en-cuenta<br/>exmpl<br/>example<br/>example',1,'fuvol','semanal',2,2,1,1,1,2,'',2,0,0,0,2,2,1,1,0,'T','175','75','1','15','40','55','22','33','44','resultados-del-test','suplementacion-recetada','laboratorio','comentarios-generales',1,'2023-02-22 13:44:57','I',1,'','2023-02-22 13:44:57'),
(3,1,1,3,'2023-02-18 19:19:00','1-EXAMPLE-11-1','2-EXAMPLE-2','4-EXAMPLE','3-EXAMPLE','5-EXAMPLE','6-EXAMPLE','7-EXAMPLE','9-EXAMPLE','8-EXAMPLE','10-EXAMPLE','EXAMPLE-11-<br/>EXAMPLE-11-<br/>11',2,'BASKET','TODOS LOS MESES',0,1,1,1,1,0,'5',0,0,0,0,2,2,2,2,1,'T','180','90','18','13','16','19','14','17','20','21-21','22','23','24-24',1,'2023-02-22 14:20:55','X',1,'null','2023-03-08 11:08:50'),
(4,0,0,9,'2023-03-17 13:39:49','example','example','example','example','example','example','example','example','example','example','example',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','194','80','30','15','30','30','20','30','30','example','example','example','example',1,'2023-03-17 13:39:49','X',1,'null','2023-03-17 13:44:05');

/*Table structure for table `ope_ficha_pac_nutri_det` */

DROP TABLE IF EXISTS `ope_ficha_pac_nutri_det`;

CREATE TABLE `ope_ficha_pac_nutri_det` (
  `IDATENCIONPAC` int NOT NULL,
  `ITEM` int NOT NULL,
  `IDSERVICIO` int DEFAULT NULL,
  `MONTO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `USU_MODI` int DEFAULT NULL COMMENT 'AL ANULAR O MODIFICAR EL REGISTRO VOY A CARGAR EL USUARIO Y LA FECHA EN ESTOS CAMPOS',
  `FEC_MODI` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`IDATENCIONPAC`,`ITEM`),
  KEY `cn_id_servicio` (`IDSERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_ficha_pac_nutri_det` */

/*Table structure for table `ope_marca` */

DROP TABLE IF EXISTS `ope_marca`;

CREATE TABLE `ope_marca` (
  `IDMARCA` int NOT NULL AUTO_INCREMENT,
  `MARCA` varchar(50) DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`IDMARCA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_marca` */

/*Table structure for table `ope_plan_horario` */

DROP TABLE IF EXISTS `ope_plan_horario`;

CREATE TABLE `ope_plan_horario` (
  `IDPLANHORARIO` int NOT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `IDMEDICO` int DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  PRIMARY KEY (`IDPLANHORARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_plan_horario` */

insert  into `ope_plan_horario`(`IDPLANHORARIO`,`IDCLINICA`,`IDMEDICO`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`) values 
(1,1,2,1,'2023-02-13 12:23:25','A');

/*Table structure for table `ope_plan_horario_det` */

DROP TABLE IF EXISTS `ope_plan_horario_det`;

CREATE TABLE `ope_plan_horario_det` (
  `IDPLANHORARIO` int NOT NULL,
  `ITEM` int NOT NULL,
  `TURNO` varchar(15) DEFAULT NULL,
  `DESDE` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `HASTA` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `DIA` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'EL ESTADO EN EL DETALLE LO UTILIZARE PARA CUANDO EL USUARIO QUIERA ELIMINAR UNA LINEA YO LE INACTIVE NOMAS Y ASI NO SE ELIMINE COMPLETAMENTE EL DATO Y EN LA VENTANA SOLO LE MOSTRARE LOS ACTIVO HACIENDOLE CREER AL USUARIO QUE SE ELIMINO COMPLETAMENTE',
  PRIMARY KEY (`IDPLANHORARIO`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_plan_horario_det` */

insert  into `ope_plan_horario_det`(`IDPLANHORARIO`,`ITEM`,`TURNO`,`DESDE`,`HASTA`,`DIA`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`) values 
(1,1,'M','2023-02-13 07:00:00','2023-02-13 21:00:00','MARTES',1,'2023-02-13 12:23:25','A');

/*Table structure for table `ope_producto` */

DROP TABLE IF EXISTS `ope_producto`;

CREATE TABLE `ope_producto` (
  `IDPRODUCTO` int NOT NULL AUTO_INCREMENT,
  `DESCRIPCION` varchar(250) DEFAULT NULL,
  `IDMARCA` int DEFAULT NULL,
  `IVA` int DEFAULT NULL,
  `PRECIO` decimal(50,3) DEFAULT '0.000',
  `COSTO` decimal(50,3) DEFAULT '0.000',
  `ESTADO` char(1) DEFAULT 'A',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`IDPRODUCTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_producto` */

/*Table structure for table `ope_stock` */

DROP TABLE IF EXISTS `ope_stock`;

CREATE TABLE `ope_stock` (
  `IDSTOCK` int NOT NULL AUTO_INCREMENT,
  `IDPRODUCTO` int DEFAULT NULL,
  `LOTE` varchar(80) DEFAULT NULL,
  `STOCK` int DEFAULT '0',
  `IDCLINICA` int DEFAULT NULL,
  `IDDEPOSITO` int DEFAULT NULL,
  `IDCLIENTE` int DEFAULT NULL,
  PRIMARY KEY (`IDSTOCK`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_stock` */

/*Table structure for table `ope_tipo_concepto` */

DROP TABLE IF EXISTS `ope_tipo_concepto`;

CREATE TABLE `ope_tipo_concepto` (
  `IDTIPOCONCEPTO` int NOT NULL AUTO_INCREMENT COMMENT 'TABLA QUE UTILIZARE PARA DIFERENCIAR LOS CONCEPTO QUE SE PUEDAN FACTURAR, YA QUE PUEDE SER CUOTAS, PRODUCTOS, CUENTAS, ETC.',
  `DESCRIPCION` varchar(50) DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'A',
  PRIMARY KEY (`IDTIPOCONCEPTO`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `ope_tipo_concepto` */

insert  into `ope_tipo_concepto`(`IDTIPOCONCEPTO`,`DESCRIPCION`,`ESTADO`) values 
(1,'CUENTA','A'),
(2,'PRODUCTO (STOCK)','A'),
(3,'INTERES AGREGADO','A'),
(4,'DESCUENTO AGREGADO','A'),
(5,'SERVICIO','A');

/*Table structure for table `ope_transaccion` */

DROP TABLE IF EXISTS `ope_transaccion`;

CREATE TABLE `ope_transaccion` (
  `IDTRANSACCION` int NOT NULL,
  `FECHA` date DEFAULT NULL,
  `TIPO_TRANSACCION` varchar(10) DEFAULT NULL,
  `OBSERVACION` varchar(500) DEFAULT NULL,
  `RESPONSABLE` int DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `IDDEPOSITO` int DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`IDTRANSACCION`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_transaccion` */

/*Table structure for table `ope_transaccion_det` */

DROP TABLE IF EXISTS `ope_transaccion_det`;

CREATE TABLE `ope_transaccion_det` (
  `IDTRANSACCION` int NOT NULL,
  `ITEM` int NOT NULL,
  `IDPRODUCTO` int DEFAULT NULL,
  `IDCLIENTE` int DEFAULT NULL,
  `CANTIDAD` int DEFAULT NULL,
  `LOTE` varchar(80) DEFAULT NULL,
  `PRECIO` int DEFAULT NULL,
  `COSTO` int DEFAULT NULL,
  `SUBTOTAL_PRECIO` int DEFAULT NULL,
  `SUBTOTAL_COSTO` int DEFAULT NULL,
  PRIMARY KEY (`IDTRANSACCION`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_transaccion_det` */

/*Table structure for table `rh_ciudad` */

DROP TABLE IF EXISTS `rh_ciudad`;

CREATE TABLE `rh_ciudad` (
  `IDCIUDAD` int NOT NULL AUTO_INCREMENT,
  `DESC_CIUDAD` varchar(50) DEFAULT NULL,
  `IDPAIS` int DEFAULT '0',
  `ESTADO` char(1) DEFAULT 'A',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`IDCIUDAD`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `rh_ciudad` */

insert  into `rh_ciudad`(`IDCIUDAD`,`DESC_CIUDAD`,`IDPAIS`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(0,'(NO/DEFINIDO)',0,'A',0,'2023-02-24 12:34:45'),
(2,'Lima',0,'A',0,'2023-03-01 10:20:23');

/*Table structure for table `rh_especialidad` */

DROP TABLE IF EXISTS `rh_especialidad`;

CREATE TABLE `rh_especialidad` (
  `IDESPECIALIDAD` int NOT NULL,
  `DESC_ESPECIALIDAD` varchar(50) DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'A',
  `MONTO` int DEFAULT '0',
  `IVA` int DEFAULT '0',
  PRIMARY KEY (`IDESPECIALIDAD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_especialidad` */

insert  into `rh_especialidad`(`IDESPECIALIDAD`,`DESC_ESPECIALIDAD`,`ESTADO`,`MONTO`,`IVA`) values 
(1,'example espe','A',0,0);

/*Table structure for table `rh_persona` */

DROP TABLE IF EXISTS `rh_persona`;

CREATE TABLE `rh_persona` (
  `IDPERSONA` int NOT NULL,
  `NOMBRE` varchar(50) DEFAULT NULL,
  `APELLIDO` varchar(50) DEFAULT NULL,
  `CINRO` varchar(20) DEFAULT NULL,
  `TIPODOC` varchar(10) DEFAULT NULL,
  `IDCATEGORIA_PERSONA` int DEFAULT NULL COMMENT 'LO MANEJO COMO SI FUERA PERFIL',
  `DESC_CATEG_PERSONA` varchar(20) DEFAULT NULL,
  `RAZON_SOCIAL` varchar(100) DEFAULT NULL,
  `RUC` varchar(20) DEFAULT NULL,
  `DIRECCION` varchar(80) DEFAULT NULL,
  `EMAIL` varchar(80) DEFAULT NULL,
  `IDBARRIO` int DEFAULT NULL,
  `IDCIUDAD` int DEFAULT NULL,
  `TELFPARTICULAR` varchar(20) DEFAULT NULL,
  `TELFMOVIL` varchar(20) DEFAULT NULL,
  `IDCIUDADNAC` int DEFAULT NULL,
  `FECHANAC` date DEFAULT NULL,
  `SEXO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'N',
  `RELIGION` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'N',
  `ESTADOCIVIL` char(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'ND',
  `GRUPOSANGUINEO` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'N',
  `OBSERVACION` varchar(150) DEFAULT NULL,
  `FECHAALTA` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `FECULTMOMOV` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `FOTO` varchar(100) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `USU_MOD` int DEFAULT NULL,
  `IDPAIS` int DEFAULT NULL,
  `TELEFPARTICULAR` varchar(15) DEFAULT NULL,
  `OCUPACION` varchar(50) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'ESTE CAMPO PARA NUTRI LO UTILIZO COMO PROFESION YA QUE OCUPACION SERIA UN SINONIMO Y PARA NO DUPLICAR EL CAMPO ENTONCES UTILIZO ESTE COMO CAMPO PARA PROFESION',
  `ANTECEDENTES` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'ANTECEDENTES FAMILIARES',
  `EXPEDIENTE_CLINICO` varchar(200) DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `SEGURO_MEDICO` varchar(50) DEFAULT NULL,
  `TIENE_HIJOS` int DEFAULT '0' COMMENT '0=NO / 1=SI',
  `CANT_HIJOS` int DEFAULT '0',
  `FOTO_PATH_ABS` varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'path absoluta de la foto',
  `IMG_PATH` longblob,
  PRIMARY KEY (`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_persona` */

insert  into `rh_persona`(`IDPERSONA`,`NOMBRE`,`APELLIDO`,`CINRO`,`TIPODOC`,`IDCATEGORIA_PERSONA`,`DESC_CATEG_PERSONA`,`RAZON_SOCIAL`,`RUC`,`DIRECCION`,`EMAIL`,`IDBARRIO`,`IDCIUDAD`,`TELFPARTICULAR`,`TELFMOVIL`,`IDCIUDADNAC`,`FECHANAC`,`SEXO`,`RELIGION`,`ESTADOCIVIL`,`GRUPOSANGUINEO`,`OBSERVACION`,`FECHAALTA`,`FECULTMOMOV`,`FOTO`,`USU_ALTA`,`USU_MOD`,`IDPAIS`,`TELEFPARTICULAR`,`OCUPACION`,`ANTECEDENTES`,`EXPEDIENTE_CLINICO`,`IDCLINICA`,`SEGURO_MEDICO`,`TIENE_HIJOS`,`CANT_HIJOS`,`FOTO_PATH_ABS`,`IMG_PATH`) values 
(0,'','Cliente Ocasional','000000','CI',4,'PACIENTE','(NO/DEFINIDO)','000000',NULL,NULL,0,0,NULL,NULL,0,'2021-11-24','N',NULL,NULL,NULL,NULL,'2021-11-26 10:10:47','0000-00-00 00:00:00',NULL,0,NULL,0,NULL,NULL,NULL,NULL,1,NULL,0,0,NULL,NULL),
(1,'','ROOT','000000','CI',1,'ADMINISTRADOR','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N','N','N','N',NULL,'2021-11-26 12:29:05','0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,0,0,NULL,NULL),
(2,'Pedro ','Paredes Acosta','642867','CI',5,'MEDICO','Pedro Acosta','64286-7','Lorem Ipsum is simply dummy text','pedro@gmail.com',0,0,'0901 031 149','0901 031204',0,'1990-01-02','M','','N','','','2023-02-13 12:20:39',NULL,'',1,0,0,'','MEDICO ','','',1,'',0,0,NULL,NULL),
(3,'Maria Cristal','Gimenez Gonzalez','502213','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','cristal@yopmail.com',0,0,'0991 032 203','0997 771 666',0,'1990-01-01','F','','ND','','* EXAMPLE<br/>* EXAMPLE','2023-02-13 12:21:41',NULL,'',1,0,0,'','estudiante','','',1,'',1,4,NULL,NULL),
(4,'PEDRO','PERALTA','581646','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','PEDRO@yopmail.com',0,0,'111111','0971 222 334',0,'2023-02-25','N','N','ND','','1111','2023-02-24 13:26:42',NULL,'',1,0,0,'','11111','','',1,'',0,0,'',NULL),
(5,'Estela Araceli','Gonzalez Pineda','987321','CI',4,'PACIENTE','','','a','a',0,2,'1','1',0,'1990-01-01','F','N','SO','','* dato<br/>* de <br/>* ejemplo','2023-03-08 13:29:12',NULL,'',1,0,0,'','a','','',1,'',1,3,NULL,NULL),
(6,'Julio','Galeano','345194','CI',4,'PACIENTE','','','a','a',0,0,'1','1',0,'1990-01-01','M','N','SO','','* 1<br/>*2','2023-03-08 14:03:14',NULL,'',1,0,0,'','a','','',1,'',0,0,NULL,NULL),
(7,'jose jose','gonzalez','319391','CI',4,'PACIENTE','','','a','a',0,0,'1','1',0,'2023-03-09','N','N','ND','','','2023-03-09 11:18:40',NULL,'',1,0,0,'','a','','',1,'',0,0,'',NULL),
(8,'Zahra','Casas','258797','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','zahra@gmail.com',0,2,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','CA','','example<br/>example','2023-03-13 11:37:48',NULL,'',1,0,0,'','ninguna','','',1,'',1,2,'',NULL),
(9,'Adela','Rivas','13032301','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','adela@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2000-01-01','F','N','SE','','example','2023-03-13 13:31:30',NULL,'photo_stock10.png',1,0,0,'','ninguno','','',1,'',1,1,'C:*OdontoAppWeb*web*recursos*static*9*photo_stock10.png',NULL),
(10,'Melania','Alvarez','984294','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','melania@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','CO','','example','2023-03-13 13:40:04',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,'',NULL),
(11,'Roberto','Maza','833501','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','maza@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','* example<br/>* example','2023-03-13 13:53:33',NULL,'photo_stock.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*11*photo_stock.png',NULL),
(12,'Ivet','Ribera','143349','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','ivet@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2000-01-01','N','N','ND','','* example<br/>* example','2023-03-13 13:56:57',NULL,'photo_stock3.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*12*photo_stock3.png',NULL),
(13,'Laia','Royo','55527','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','laia@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','* example<br/>*example','2023-03-13 14:12:01',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,'',NULL),
(14,'Damaris','Perez','840795','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','damaris@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','N','N','ND','','* example<br/>* example','2023-03-13 14:23:39',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,'',NULL),
(15,'Arsenio','Vaquero','854783','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text ','arsenio@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','M','N','SE','','* example<br/>* example','2023-03-14 11:09:17',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,'',NULL),
(16,'Luna','Ortuño','68254','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','luna@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','ND','','* example<br/>* example','2023-03-14 11:37:43',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,'',NULL),
(17,'Raúl','Mosquera','305672','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','RAUL@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2020-01-01','N','N','ND','','* example<br/>/ example','2023-03-14 13:59:16',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,'',NULL),
(18,'Ana Maria','Correa Baez','258195','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','ana@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','N','N','ND','','new <br/>example','2023-03-14 14:09:11',NULL,'photo_stock2.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*18*photo_stock2.png',NULL),
(19,'Josefina','Amarilla','781593','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','jose@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','','2023-03-14 14:50:34',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,'',NULL),
(20,'Ernesto','Tejada','524429','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','tejada@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','','2023-03-14 15:12:25',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,'',NULL),
(21,'Patricio','Paniagua','875909','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','patricio@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','example','2023-03-15 10:23:36',NULL,'photo_stock4.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*21*photo_stock4.png',NULL),
(22,'Hector ','Saavedra','982279','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','saavedra@yopmail.com',0,2,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','/ example<br/>/ example','2023-03-15 11:56:32',NULL,'photo_stock4.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*22*photo_stock4.png',NULL),
(23,'Nerea','De-La-Rosa','611678','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','nerea@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','N','N','ND','','','2023-03-15 13:07:25',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,'',NULL),
(24,'Azahara','Agudo','470022','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','azahara@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','/ 1-example<br/>/ 2-example','2023-03-15 14:26:01',NULL,'photo_stock5.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*24*photo_stock5.png',NULL),
(25,'Alain ','Cuellar','717119','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','cuellar',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','example<br/>example<br/>','2023-03-15 14:45:49',NULL,'photo_stock4.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*25*photo_stock4.png',NULL),
(26,'Bianca','Cuenca','200718','CI',4,'PACIENTE','','','lorem ipsum','bianca@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2023-03-16','N','N','ND','','','2023-03-16 11:14:50',NULL,'',1,0,0,'','','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*26',NULL),
(27,'Didac','Ochoa','521487','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','didac@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','','2023-03-16 14:01:51',NULL,'photo_stock4.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*imagenes*27*photo_stock4.png',NULL),
(28,'Monica','Tortosa','146665','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','tortosa@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','/ example<br/>/ example','2023-03-17 10:28:37',NULL,'photo_stock11.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*28*photo_stock11.png',NULL);

/*Table structure for table `rh_servicio` */

DROP TABLE IF EXISTS `rh_servicio`;

CREATE TABLE `rh_servicio` (
  `IDSERVICIO` int NOT NULL,
  `DESC_SERVICIO` varchar(50) DEFAULT NULL,
  `MONTO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT 'A',
  `IVA` int DEFAULT '0',
  PRIMARY KEY (`IDSERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_servicio` */

insert  into `rh_servicio`(`IDSERVICIO`,`DESC_SERVICIO`,`MONTO`,`ESTADO`,`IVA`) values 
(1,'example serv',0,'A',0);

/*Table structure for table `sys_config_agend` */

DROP TABLE IF EXISTS `sys_config_agend`;

CREATE TABLE `sys_config_agend` (
  `IDCONFIGAGEND` int NOT NULL AUTO_INCREMENT,
  `DESC_CONFIG` varchar(100) DEFAULT NULL,
  `MAX_AGEND` int DEFAULT NULL COMMENT 'MAXIMO AGENDAMIENTO DE PACIENTES POR MEDICO EN UNA FECHA (POR DIA MAS O MENOS SERIA)',
  `DESC_AGEND` varchar(200) DEFAULT NULL COMMENT 'DESCRIPCION QUE LE VA A APARECER EN LA CUENTA DEL CLIENTE CUENDO TENGA UN AGENDAMIENTO',
  `ESTADO` char(1) DEFAULT NULL COMMENT 'ACTIVO Y INACTIVO; Y SOLO VA A HABER UN ACTIVO POR CLINICA',
  `USU_ALTA` int DEFAULT NULL COMMENT 'USUARIO Y FECHA ALTA DEL USUARIO QUE CREA O EL ULTIMO QUE MODIFICA',
  `FEC_ALTA` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `IDCLINICA` int DEFAULT NULL COMMENT 'CONFIGURACION DE AGENDAMIENTO POR CLINICA',
  `INTERV_MIN_ENTRE_AGEND` int DEFAULT '0' COMMENT 'CAMPO PARA DEFINIR LOS MINUTOS QUE HAY QUE TENER ENTRE UN AGENDAMIENTO Y OTRO (PARA LA FECHA DE AGEND);',
  PRIMARY KEY (`IDCONFIGAGEND`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;

/*Data for the table `sys_config_agend` */

insert  into `sys_config_agend`(`IDCONFIGAGEND`,`DESC_CONFIG`,`MAX_AGEND`,`DESC_AGEND`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`IDCLINICA`,`INTERV_MIN_ENTRE_AGEND`) values 
(19,'config exxample',10,'agendamiento en la clinica central la fecha #DIA#/#MES#/#AÑO# a las #HORA#.','A',1,'2023-02-13 12:28:35',1,30);

/*Table structure for table `sys_empresa` */

DROP TABLE IF EXISTS `sys_empresa`;

CREATE TABLE `sys_empresa` (
  `IDEMPRESA` int NOT NULL AUTO_INCREMENT,
  `RUC` varchar(20) DEFAULT NULL,
  `RAZONSOCIAL` varchar(100) DEFAULT NULL,
  `DIRECCION` varchar(100) DEFAULT NULL,
  `TELEFONO` varchar(20) DEFAULT NULL,
  `IMAGEN` varchar(150) DEFAULT NULL,
  `DENOMINACION` varchar(50) DEFAULT NULL,
  `EMPLEADORNRO` varchar(20) DEFAULT NULL,
  `NROPATROMAJT` varchar(20) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `ESTADO` char(1) DEFAULT 'A',
  PRIMARY KEY (`IDEMPRESA`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `sys_empresa` */

insert  into `sys_empresa`(`IDEMPRESA`,`RUC`,`RAZONSOCIAL`,`DIRECCION`,`TELEFONO`,`IMAGEN`,`DENOMINACION`,`EMPLEADORNRO`,`NROPATROMAJT`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`) values 
(0,'000000','(NO/DEFINIDO)',NULL,NULL,NULL,NULL,NULL,NULL,0,'2021-11-25 12:37:26','A'),
(1,'012345','Agend',NULL,NULL,NULL,'Odontologia',NULL,NULL,0,'2021-11-25 12:38:29','A');

/*Table structure for table `sys_perfil` */

DROP TABLE IF EXISTS `sys_perfil`;

CREATE TABLE `sys_perfil` (
  `IDPERFIL` int NOT NULL AUTO_INCREMENT,
  `PERFIL` varchar(50) DEFAULT NULL,
  `IDMENU` int DEFAULT NULL,
  `ESTADO` varchar(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'A',
  PRIMARY KEY (`IDPERFIL`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `sys_perfil` */

insert  into `sys_perfil`(`IDPERFIL`,`PERFIL`,`IDMENU`,`ESTADO`) values 
(1,'ADMINISTRADOR',1,'A'),
(2,'FUNCIONARIO',1,'I'),
(3,'SECRETARIA/O',1,'A'),
(4,'PACIENTE',1,'A'),
(5,'MEDICO',1,'A');

/*Table structure for table `sys_usuario` */

DROP TABLE IF EXISTS `sys_usuario`;

CREATE TABLE `sys_usuario` (
  `IDUSUARIO` int NOT NULL,
  `IDPERSONA` int DEFAULT NULL,
  `USUARIO` varchar(100) DEFAULT NULL,
  `CLAVE` varchar(100) DEFAULT NULL,
  `ESTADO` varchar(1) DEFAULT 'A',
  `IDPERFIL` int DEFAULT NULL,
  `ESTADO_MIGRAR` varchar(2) DEFAULT 'NO' COMMENT '(NO - SE ESTA MIGRANDO) (SI - SE ESTA MIGRANDO)',
  `WEB` int DEFAULT '0' COMMENT 'Si es 1 (uno) es porque esta conectado o en-linea y si esta en 0 (cero) es porque no esta conectado o ha cerrado sesion',
  `EMAIL` varchar(100) DEFAULT NULL COMMENT 'CAMPO DONDE SE VA A GUARDAR EL EMAIL DONDE SE ENVIARA DATOS DEL USUARIO PARA REGISTRAR O RECUPERAR CUENTA',
  `CONFIR_EMAIL` varchar(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT '(VALUES: SI / NO ) CAMPO PARA SABER SI EL USUARIO CONFIRMO SU CUENTA A TAVES DEL EMAIL QUE INGRESO POR MEDIO DE UN CODIGO QUE LE PASAMOS',
  PRIMARY KEY (`IDUSUARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sys_usuario` */

insert  into `sys_usuario`(`IDUSUARIO`,`IDPERSONA`,`USUARIO`,`CLAVE`,`ESTADO`,`IDPERFIL`,`ESTADO_MIGRAR`,`WEB`,`EMAIL`,`CONFIR_EMAIL`) values 
(0,1,'2aROOT','ž¥Ð6´lZ3®ó|¨','A',1,'NO',1,NULL,'S'),
(1,4,'PAC','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'PEDRO@yopmail.com','NO'),
(2,2,'MED','Úèb‡ÙÓ·b×7‹k®§ü','A',5,'NO',0,'pedro@gmail.com','NO');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
