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
(3,4,1,3,1,'2023-03-21 16:16:00','0.000','0.000','agendamiento en la clinica central la fecha 21/03/2023 a las 16:16.','X',4,'2023-03-02 12:20:30'),
(4,13,2,1,1,'2023-03-29 19:10:00','0.000','0.000','agendamiento en la clinica central la fecha 29/03/2023 a las 19:10.','C',1,'2023-03-22 09:58:20'),
(5,17,2,2,1,'2023-03-29 20:10:00','75000.000','0.000','agendamiento en la clinica central la fecha 29/03/2023 a las 20:10.','C',1,'2023-03-22 10:28:57'),
(6,9,1,4,1,'2023-03-27 13:30:00','0.000','0.000','agendamiento en la clinica central la fecha 27/03/2023 a las 13:30.','C',1,'2023-03-27 13:14:04'),
(7,8,1,5,1,'2023-05-08 14:00:00','0.000','0.000','agendamiento en la clinica central la fecha 08/05/2023 a las 14:00.','C',1,'2023-05-08 12:02:52'),
(8,8,2,3,1,'2023-05-10 18:30:00','75000.000','75000.000','agendamiento en la clinica central la fecha 10/05/2023 a las 18:30.','A',1,'2023-05-08 12:45:16'),
(9,40,3,1,1,'2023-07-26 21:21:00','0.000','0.000','agendamiento en la clinica central la fecha 26/07/2023 a las 21:21.','C',1,'2023-07-26 10:12:06');

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
(1,1,2,1,NULL,NULL,NULL,NULL,NULL,'A',1,'2023-02-13 12:28:50'),
(2,1,30,2,NULL,NULL,NULL,NULL,NULL,'A',1,'2023-03-22 09:58:20'),
(3,1,30,1,NULL,NULL,NULL,NULL,NULL,'A',1,'2023-07-26 10:12:06');

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
(1,1,'2023-02-14','2023-02-14 15:00:00',3,'','','',0,'','agendamiento en la clinica central la fecha 14/02/2023 a las 15:00.','C',1,'2023-02-13 12:28:50','2023-04-12 12:16:06',0,'SI',19,NULL),
(1,2,'2023-03-14','2023-03-14 15:15:00',4,'','','',0,'','agendamiento en la clinica central la fecha 14/03/2023 a las 15:15.','A',1,'2023-03-02 11:46:40',NULL,0,'SI',19,'EXAMPLE<br/>EXAMPLE<br/>EXAMPLE'),
(1,3,'2023-03-21','2023-03-21 16:16:00',4,'','','',0,'','agendamiento en la clinica central la fecha 21/03/2023 a las 16:16.','X',4,'2023-03-02 12:20:30',NULL,0,'SI',19,'EXAMPLE NEW<br/>EXAMPLE NEW<br/>EXAMPLE NEW '),
(1,4,'2023-03-27','2023-03-27 13:30:00',9,'','','',0,'','agendamiento en la clinica central la fecha 27/03/2023 a las 13:30.','C',1,'2023-03-27 13:14:04','2023-03-27 13:28:08',0,'SI',19,'revision de consulta '),
(1,5,'2023-05-08','2023-05-08 14:00:00',8,'','','',0,'','agendamiento en la clinica central la fecha 08/05/2023 a las 14:00.','C',1,'2023-05-08 12:02:52','2023-05-08 12:05:17',0,'SI',19,'for example'),
(2,1,'2023-03-29','2023-03-29 19:10:00',13,'','','',0,'','agendamiento en la clinica central la fecha 29/03/2023 a las 19:10.','C',1,'2023-03-22 09:58:20','2023-03-22 11:31:05',0,'SI',19,'Lorem Ipsum is simply dummy text'),
(2,2,'2023-03-29','2023-03-29 20:10:00',17,'','','',0,'','agendamiento en la clinica central la fecha 29/03/2023 a las 20:10.','A',1,'2023-03-22 10:28:57',NULL,1,'SI',19,'example the money in especialidad'),
(2,3,'2023-05-10','2023-05-10 18:30:00',8,'','','',0,'','agendamiento en la clinica central la fecha 10/05/2023 a las 18:30.','P',1,'2023-05-08 12:45:15',NULL,0,'NO',19,'example for control '),
(3,1,'2023-07-26','2023-07-26 21:21:00',40,'','','',0,'','agendamiento en la clinica central la fecha 26/07/2023 a las 21:21.','A',1,'2023-07-26 10:12:06',NULL,0,'SI',19,'example');

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `ope_clinica` */

insert  into `ope_clinica`(`IDCLINICA`,`DESC_CLINICA`,`DIRECCION`,`TELEFONO`,`EMAIL`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,'CENTRAL','','0','A','A',NULL,NULL),
(10,'Example','lorem ipsum is simply dummy text','0','a','A',1,'2023-03-21 11:54:46');

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

insert  into `ope_factura`(`IDFACTURA`,`IDCLINICA`,`NROFACTURA`,`TIPOFACTURA`,`IDCLIENTE`,`FECHAFACTURA`,`IDARQUEO`,`ESTADO`,`TOTAL_FACTURA`,`TOTAL_DCTO`,`TOTAL_IVA10`,`TOTAL_IVA5`,`TOTAL_GRAV10`,`TOTAL_GRAV5`,`TOTAL_EXENTA`,`TOTAL_IVA`,`IDFORMACOBRO`,`LETRAS`,`OBSERVACION`,`USU_ALTA`,`FEC_ALTA`) values 
(1,1,'002-002-000220323','Co',17,'2023-03-22',0,'C','75000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Setenta y Cinco Mil ','',1,'2023-03-22 10:29:36');

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

insert  into `ope_factura_det`(`IDFACTURA`,`ITEM`,`IDCONCEPTO`,`IDTIPOCONCEPTO`,`CANTIDAD`,`PRECIO`,`IDIMPUESTO`,`EXENTO`,`IVA5`,`IVA10`,`GRAV5`,`GRAV10`,`SUBTOTAL`,`DESCRIPCION_AUX`) values 
(1,1,5,1,1,'75000.000',0,'0.000','0.000','0.000','0.000','0.000','75000.000','agendamiento en la clinica central la fecha 29/03/2023 a las 20:10.');

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
(4,0,0,9,'2023-03-17 13:39:49','example','example','example','example','example','example','example','example','example','example','example',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','194','80','30','15','30','30','20','30','30','example','example','example','example',1,'2023-03-17 13:39:49','X',1,'null','2023-03-17 13:44:05'),
(5,2,1,13,'2023-03-22 19:50:00','Lorem Ipsum is simply dummy text','manzana','remolacha','pera','no','a veces','ninguna','ninguna','no se','ninguna','Lorem Ipsum is simply dummy text<br/>Lorem Ipsum is simply dummy text',1,'deporte','a veces',1,0,2,0,2,1,'6',0,2,1,2,1,0,2,0,2,'A','175','65','55','30','45','60','30','50','65','example','Lorem Ipsum is simply dummy text','Lorem Ipsum is simply dummy text','Lorem Ipsum is simply dummy text',1,'2023-03-22 10:12:49','X',1,'null','2023-03-22 11:27:02'),
(6,2,1,13,'2023-03-22 20:10:00','Lorem Ipsum is simply dummy text','PIÑA','LOCOTE','MANZANA','NO','NO','NO SE','NINGUNA','NO SE','NINGUNA','Lorem Ipsum <br/>is simply <br/>dummy text',1,'BASKET','LUNES, MIERCOLES Y VIERNES',1,1,1,1,1,0,'4',0,0,0,0,2,2,2,2,2,'T','175','45','60','30','50','65','35','55','70','Lorem Ipsum is simply dummy text','Lorem Ipsum is simply dummy text','Lorem Ipsum is simply dummy text','Lorem Ipsum is simply dummy textLorem Ipsum is simply dummy text',1,'2023-03-22 11:31:05','A',1,'','2023-03-22 11:31:05'),
(7,0,0,37,'2023-03-24 13:54:32','this is simply text','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-03-24 13:54:32','A',1,'','2023-03-24 13:54:32'),
(8,0,0,9,'2023-03-22 15:05:00','Bajar de peso','le gusta mas lo salado','no le gusta el brocoli y es alergica al picante','desayuna cafe con leche, cena huevo','socialmente','dejo hace 2 años','al cambio de clima','dos cesareas','hipertensión','enalapril de 20mg 1 vez al dia','no suele tener hambre por la noche y si tiene busca algo dulce',0,'','',0,0,0,0,0,1,'6',1,1,1,1,2,2,2,2,2,'T','176','95','22','34','26','10','78','1430','OMROM','deficiencia de magnesio zinc y vitamina B1','citrato de magnesio 300mg zinc de 20mg ','arte','proxima consulta cambiar de receta y agregar enzimas digestivas ',1,'2023-03-27 13:07:38','A',1,'','2023-03-27 13:07:38'),
(9,1,4,9,'2023-03-27 13:20:00','control de consulta','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','175','91','21','27','26','7','75','1340','OMROM','','','','mejoro notablemente su calidad de sueño',1,'2023-03-27 13:28:08','A',1,'','2023-03-27 13:28:08'),
(10,0,0,9,'2023-03-27 13:30:00','tercer control','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','175','88','19','25','30','5','60','1340','OMROM','','','','',1,'2023-03-27 13:30:57','A',1,'','2023-03-30 13:06:29'),
(11,0,0,8,'2023-03-29 14:25:00','example-29-03-23','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','178','65','30','30','30','30','30','30','ORMOM','','','','',1,'2023-03-29 14:25:52','A',1,'','2023-05-08 12:01:24'),
(12,0,0,9,'2023-03-30 10:00:00','EXAMPLE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','177','65','85','45','55','55','35','75','ORMOM','','','','',1,'2023-03-30 10:04:08','X',1,'null','2023-03-30 12:06:19'),
(13,0,0,9,'2023-03-30 13:13:00','NEW EXAMPLE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','180','80','55','40','40','55','80','55','0','','','','',1,'2023-03-30 13:07:27','A',1,'','2023-03-31 11:44:23'),
(14,0,0,34,'2023-03-30 13:08:00','EXAMPLE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','88','','','','','','','','','','','',1,'2023-03-30 13:08:54','A',1,'','2023-03-30 13:13:56'),
(15,0,0,9,'2023-04-11 13:50:00','example','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','181','50','50','50','50','50','50','50','OMROM','','','','',1,'2023-04-11 13:51:00','A',1,'','2023-04-11 13:51:00'),
(16,0,0,9,'2023-04-11 14:05:00','example two','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','181','60','60','60','60','60','60','60','OMROM','','','','',1,'2023-04-11 14:04:32','A',1,'','2023-04-13 13:19:34'),
(17,0,0,9,'2023-04-11 15:00:00','EXAMPLE THREE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','181','70','71','72','70','70','70','70','OMROM','','','','',1,'2023-04-11 15:01:08','A',1,'','2023-04-13 12:16:30'),
(18,1,1,3,'2023-04-12 12:16:00','EXAMPLE EXAMPLE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','SAMPLE',1,'2023-04-12 12:16:06','A',1,'','2023-04-12 12:17:04'),
(19,0,0,9,'2023-04-13 11:11:00','EXAMPLE 11','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','191','','','','','','','','','','','','',1,'2023-04-13 11:46:31','A',1,'','2023-04-13 11:46:31'),
(20,0,0,9,'2023-04-13 14:17:00','EXAMPLE TO FILE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','191','82','85','80','83','86','81','84','OMROM','','','','SAVE EXAMPLE TO FILE - NOT UPDATE',1,'2023-04-13 14:20:16','A',1,'','2023-04-13 14:20:16'),
(21,0,0,9,'2023-04-13 14:26:00','example two for add file','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-13 14:27:00','X',1,'null','2023-04-14 09:43:44'),
(22,0,0,9,'2023-04-13 14:31:00','EXAMPLE THREE FOR ADD FILE','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-13 14:32:16','X',1,'null','2023-04-14 09:43:33'),
(23,0,0,9,'2023-04-13 14:37:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-13 14:40:13','X',1,'null','2023-04-14 09:43:26'),
(24,0,0,9,'2023-04-13 14:42:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-13 14:42:51','X',1,'null','2023-04-14 09:43:17'),
(25,0,0,9,'2023-04-13 14:47:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-13 14:48:05','X',1,'null','2023-04-14 09:43:06'),
(26,0,0,9,'2023-04-14 10:06:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-14 10:06:17','A',1,'','2023-04-14 10:06:17'),
(27,0,0,9,'2023-04-14 10:10:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-14 10:11:11','A',1,'','2023-05-02 14:12:39'),
(28,0,0,9,'2023-04-14 10:43:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-14 10:44:32','A',1,'','2023-05-02 14:19:32'),
(29,0,0,9,'2023-04-14 10:46:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-14 10:46:52','A',1,'','2023-04-14 10:46:52'),
(30,0,0,9,'2023-04-14 10:50:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-04-14 10:50:52','A',1,'','2023-04-14 10:50:52'),
(31,0,0,9,'2023-04-14 10:53:00','example','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','191','65','70','55','66','70','64','70','OMROM','','','','example',1,'2023-04-14 10:54:38','A',1,'','2023-04-21 13:55:00'),
(32,0,0,9,'2023-04-27 13:50:00','EJEMPLO 27','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','191','65','65','65','65','65','65','65','OMROM','','','','example',1,'2023-04-27 13:09:11','A',1,'','2023-04-27 13:09:11'),
(33,0,0,9,'2023-05-05 13:58:00','example file','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','191','70','70','70','70','70','70','70','OMROM','','','','',1,'2023-05-05 13:59:05','A',1,'','2023-05-05 13:59:51'),
(34,0,0,26,'2023-05-08 10:56:00','first example','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','165','35','35','35','35','35','35','35','OMROM','','','','',1,'2023-05-08 10:58:07','A',1,'','2023-05-08 10:58:07'),
(35,1,5,8,'2023-05-08 12:30:00','example ficha','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','178','45','45','45','45','45','45','45','OMROM','','','','',1,'2023-05-08 12:05:17','A',1,'','2023-05-08 12:05:17'),
(36,0,0,15,'2023-05-10 13:18:00','','','','','','','','','','','',2,'','',2,2,2,2,2,2,'1',2,2,2,2,2,2,2,2,2,'A','','','','','','','','','','','','','',1,'2023-05-10 13:19:02','A',1,'','2023-05-10 13:19:02'),
(37,0,0,36,'2023-07-06 13:41:00','example for first file','1','3','2','4','5','6','8','7','9','10',0,'97','98',0,0,0,0,0,1,'2',1,1,1,1,0,0,0,0,0,'T','172','50','50','50','50','50','50','50','OMROM','','','','',1,'2023-07-06 13:41:58','A',1,'','2023-07-19 11:43:41'),
(38,0,0,9,'2023-07-18 13:44:00','Bajar de peso','le gusta mas lo salado','no le gusta el brocoli y es alergica al picante','desayuna cafe con leche, cena huevo','socialmente','dejo hace 2 años','al cambio de clima','dos cesareas','hipertensión','enalapril de 20mg 1 vez al dia','no suele tener hambre por la noche y si tiene busca algo dulce',1,'example','todos los dias',0,0,0,0,0,1,'6',1,1,1,1,2,2,2,2,2,'T','191','75','75','75','75','75','75','75','OMROM','','','','',1,'2023-07-18 13:45:53','A',1,'','2023-07-18 13:45:53'),
(39,0,0,36,'2023-07-19 11:44:00','example for second file','1','3','2','7','8','9','4','10','5','6',0,'97','98',0,0,0,0,0,1,'2',1,1,1,1,0,0,0,0,0,'T','172','55','55','55','55','55','55','55','OMROM','','','','',1,'2023-07-19 11:47:09','A',1,'','2023-07-19 11:47:09'),
(40,0,0,40,'2023-07-26 10:14:00','1','2','4','3','5','6','7','9','8','10','11',1,'888','777',0,1,0,1,0,0,'6',1,0,1,0,1,0,1,0,1,'T','192','66','77','55','66','88','55','66','OMROM','','','','',1,'2023-07-26 10:16:45','A',1,'','2023-07-26 10:16:45');

/*Table structure for table `ope_ficha_pac_nutri_det` */

DROP TABLE IF EXISTS `ope_ficha_pac_nutri_det`;

CREATE TABLE `ope_ficha_pac_nutri_det` (
  `IDFICHAPAC` int NOT NULL,
  `ITEM` int NOT NULL,
  `IDSERVICIO` int DEFAULT NULL,
  `MONTO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `USU_MODI` int DEFAULT NULL COMMENT 'AL ANULAR O MODIFICAR EL REGISTRO VOY A CARGAR EL USUARIO Y LA FECHA EN ESTOS CAMPOS',
  `FEC_MODI` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `DIR_ARCHIVO` varchar(500) DEFAULT NULL COMMENT 'DIRECCION DEL ARCHIVO QUE VINCULE A LA FICHA DE ATENCION',
  `NAME_ARCHIVO` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`IDFICHAPAC`,`ITEM`),
  KEY `cn_id_servicio` (`IDSERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_ficha_pac_nutri_det` */

insert  into `ope_ficha_pac_nutri_det`(`IDFICHAPAC`,`ITEM`,`IDSERVICIO`,`MONTO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`USU_MODI`,`FEC_MODI`,`DIR_ARCHIVO`,`NAME_ARCHIVO`) values 
(11,1,0,0,'A',1,'2023-05-02 12:54:01',1,'2023-05-08 12:01:24','C:*Users*USER*Pictures*photo15.jpg','photo15.jpg'),
(16,1,0,0,'X',1,'2023-04-11 14:04:32',1,'2023-04-13 13:19:21',NULL,NULL),
(16,2,0,0,'A',1,'2023-04-13 13:19:34',NULL,'0000-00-00 00:00:00','https://drive.google.com/file/d/1Yac0cWoY281iQraXhk6oIOvcUXhlAvex/view?usp=share_link',NULL),
(17,1,0,0,'X',1,'2023-04-11 15:01:09',1,'2023-04-12 14:18:17','Lorem Ipsum is simply dummy text ',NULL),
(17,2,0,0,'A',1,'2023-04-13 12:16:30',NULL,'0000-00-00 00:00:00','photo_stock.png',NULL),
(19,1,0,0,'A',1,'2023-04-13 11:46:31',NULL,'0000-00-00 00:00:00','C:\\Users\\USER\\Pictures\\photo15.jpg',NULL),
(20,1,0,0,'A',1,'2023-04-13 14:20:16',NULL,'0000-00-00 00:00:00','C:UsersUSERDownloadsdescarga.png','descarga.png'),
(27,1,0,0,'X',1,'2023-04-14 10:11:11',1,'2023-05-02 14:12:36','C:*Users*USER*Downloads*descarga.png','descarga.png'),
(28,1,0,0,'X',1,'2023-04-14 10:44:32',1,'2023-05-02 14:19:28','C:*Users*USER*Documents*Java Printing.pdf','Java Printing.pdf'),
(29,1,0,0,'A',1,'2023-04-14 10:46:52',NULL,'0000-00-00 00:00:00','C:*Users*USER*Downloads*Glassfish - ERROR - SOLUCION - JDK instalar y cargar direccion a archivos conf y bat (1).docx','Glassfish - ERROR - SOLUCION - JDK instalar y cargar direccion a archivos conf y bat (1).docx'),
(30,1,0,0,'A',1,'2023-04-14 10:50:52',NULL,'0000-00-00 00:00:00','C:*Users*USER*Downloads*descarga.png','descarga.png'),
(31,1,0,0,'A',1,'2023-04-14 10:54:38',1,'2023-04-21 13:55:00','C:*Users*USER*Downloads*java error - solucion.txt','java error - solucion.txt'),
(31,2,0,0,'A',1,'2023-04-14 10:54:38',1,'2023-04-21 13:55:00','C:*Users*USER*Downloads*agendamiento_fields.docx','agendamiento_fields.docx'),
(31,3,0,0,'A',1,'2023-04-14 12:09:24',1,'2023-04-21 13:55:00','C:*Users*USER*Downloads*ProductRepositry.class','ProductRepositry.class'),
(32,1,0,0,'A',1,'2023-04-27 13:09:11',NULL,'0000-00-00 00:00:00','C:*Users*USER*Documents*EXAMPLE 27.docx','EXAMPLE 27.docx'),
(33,1,0,0,'A',1,'2023-05-05 13:59:06',1,'2023-05-05 13:59:51','C:*OdontoAppWeb*web*recursos*download*EXAMPLE 27.docx','EXAMPLE 27.docx'),
(34,1,0,0,'A',1,'2023-05-08 10:58:07',NULL,'0000-00-00 00:00:00','C:*OdontoAppWeb*web*recursos*download*EXAMPLE 28.docx','EXAMPLE 28.docx'),
(35,1,0,0,'A',1,'2023-05-08 12:05:17',NULL,'0000-00-00 00:00:00','C:*OdontoAppWeb*web*recursos*download*EXAMPLE 288.docx','EXAMPLE 288.docx'),
(36,1,0,0,'A',1,'2023-05-10 13:19:02',NULL,'0000-00-00 00:00:00','C:*sys*web*recursos*download*EXAMPLE 27.docx','EXAMPLE 27.docx'),
(37,1,0,0,'A',1,'2023-07-06 13:41:58',1,'2023-07-19 11:43:41','C:*AgendApp*build*web*recursos*downloadEXAMPLE 28899.docx','EXAMPLE 28899.docx'),
(39,1,0,0,'A',1,'2023-07-19 11:47:09',NULL,'0000-00-00 00:00:00','C:*OdontoAppWeb*build*web*recursos*downloadphoto_stock23.jpg','photo_stock23.jpg');

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
(1,1,2,1,'2023-02-13 12:23:25','A'),
(2,1,30,23,'2023-03-21 12:34:06','A');

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
(1,1,'M','2023-03-27 07:00:00','2023-03-27 21:00:00','MARTES',1,'2023-03-27 13:12:40','A'),
(1,2,'T','2023-03-27 13:00:00','2023-03-27 19:00:00','LUNES',1,'2023-03-27 13:12:40','A'),
(2,1,'T','2023-03-21 13:00:00','2023-03-21 18:30:00','LUNES',1,'2023-03-21 14:39:27','X'),
(2,2,'N','2023-03-21 18:00:00','2023-03-21 23:00:00','MIERCOLES',1,'2023-03-21 14:39:53','A'),
(2,3,'T','2023-03-21 14:00:00','2023-03-21 18:00:00','SABADO',1,'2023-03-21 14:37:36','X'),
(2,4,'T','2023-03-21 14:00:00','2023-03-21 18:00:00','SABADO',1,'2023-03-21 14:39:24','X'),
(2,5,'T','2023-03-21 13:00:00','2023-03-21 18:30:00','MIERCOLES',1,'2023-03-21 13:04:21','X'),
(2,6,'T','2023-03-21 13:00:00','2023-03-21 18:30:00','JUEVES',1,'2023-03-21 13:05:01','X'),
(2,7,'N','2023-03-21 18:00:00','2023-03-21 23:00:00','MIERCOLES',1,'2023-03-21 13:54:34','X'),
(2,8,'M','2023-03-21 08:00:00','2023-03-21 13:30:00','SABADO',1,'2023-03-21 13:13:34','X'),
(2,9,'T','2023-03-21 14:00:00','2023-03-21 18:00:00','SABADO',1,'2023-03-21 14:18:18','X');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `rh_ciudad` */

insert  into `rh_ciudad`(`IDCIUDAD`,`DESC_CIUDAD`,`IDPAIS`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(0,'(NO/DEFINIDO)',0,'A',0,'2023-02-24 12:34:45'),
(2,'Lima',0,'A',0,'2023-03-01 10:20:23'),
(4,'Asunción',0,'A',1,'2023-03-21 11:53:36');

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
(1,'example espe','A',0,0),
(2,'example fact','A',75000,0);

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
  `TELFPARTICULAR` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'TELEFONO DONDE ES MAS FACIL COMUNICARSE CON LLAMADAS O MENSAJES',
  `TELFMOVIL` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'TELEFONO CON WHATSAPP PARA LA COMUNICACION PARA EL CASO DE QUE TENGA DOS TELEFONOS DONDE UNO USE COMO PERSONAL',
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
  PRIMARY KEY (`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_persona` */

insert  into `rh_persona`(`IDPERSONA`,`NOMBRE`,`APELLIDO`,`CINRO`,`TIPODOC`,`IDCATEGORIA_PERSONA`,`DESC_CATEG_PERSONA`,`RAZON_SOCIAL`,`RUC`,`DIRECCION`,`EMAIL`,`IDBARRIO`,`IDCIUDAD`,`TELFPARTICULAR`,`TELFMOVIL`,`IDCIUDADNAC`,`FECHANAC`,`SEXO`,`RELIGION`,`ESTADOCIVIL`,`GRUPOSANGUINEO`,`OBSERVACION`,`FECHAALTA`,`FECULTMOMOV`,`FOTO`,`USU_ALTA`,`USU_MOD`,`IDPAIS`,`TELEFPARTICULAR`,`OCUPACION`,`ANTECEDENTES`,`EXPEDIENTE_CLINICO`,`IDCLINICA`,`SEGURO_MEDICO`,`TIENE_HIJOS`,`CANT_HIJOS`,`FOTO_PATH_ABS`) values 
(0,'','Cliente Ocasional','000000','CI',4,'PACIENTE','(NO/DEFINIDO)','000000',NULL,NULL,0,0,NULL,NULL,0,'2021-11-24','N',NULL,NULL,NULL,NULL,'2021-11-26 10:10:47','0000-00-00 00:00:00','',0,NULL,0,NULL,NULL,NULL,NULL,1,NULL,0,0,''),
(1,'','ROOT','000000','CI',1,'ADMINISTRADOR','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N','N','N','N',NULL,'2021-11-26 12:29:05','0000-00-00 00:00:00','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1,NULL,0,0,''),
(2,'Pedro ','Paredes Acosta','642867','CI',5,'MEDICO','Pedro Acosta','64286-7','Lorem Ipsum is simply dummy text','pedro@gmail.com',0,0,'0901 031 149','0901 031204',0,'1990-01-02','M','','N','','','2023-02-13 12:20:39',NULL,'',1,0,0,'','MEDICO ','','',1,'',0,0,''),
(3,'Maria Cristal','Gimenez Gonzalez','502213','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','cristal@yopmail.com',0,0,'0991 032 203','0997 771 666',0,'1990-01-01','F','','ND','','* EXAMPLE<br/>* EXAMPLE','2023-02-13 12:21:41',NULL,'',1,0,0,'','estudiante','','',1,'',1,4,''),
(4,'PEDRO','PERALTA','581646','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','PEDRO@yopmail.com',0,0,'111111','0971 222 334',0,'2023-02-25','N','N','ND','','1111','2023-02-24 13:26:42',NULL,'',1,0,0,'','11111','','',1,'',0,0,''),
(5,'Estela Araceli','Gonzalez Pineda','987321','CI',4,'PACIENTE','','','a','a',0,2,'1','1',0,'1990-01-01','F','N','SO','','* dato<br/>* de <br/>* ejemplo','2023-03-08 13:29:12',NULL,'',1,0,0,'','a','','',1,'',1,3,''),
(6,'Julio','Galeano','345194','CI',4,'PACIENTE','','','a','a',0,0,'1','1',0,'1990-01-01','M','N','SO','','* 1<br/>*2','2023-03-08 14:03:14',NULL,'',1,0,0,'','a','','',1,'',0,0,''),
(7,'jose jose','gonzalez','319391','CI',4,'PACIENTE','','','a','a',0,0,'1','1',0,'2023-03-09','N','N','ND','','','2023-03-09 11:18:40',NULL,'',1,0,0,'','a','','',1,'',0,0,''),
(8,'Zahra','Casas','258797','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','zahra@gmail.com',0,2,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','CA','','example<br/>example','2023-03-13 11:37:48',NULL,'photo_stock4.png',1,0,0,'','ninguna','lorem ipsum is simply dummy text','',1,'',1,2,'C:*OdontoAppWeb*web*recursos*static*photo_stock4.png'),
(9,'Adela','Rivas Espinola','13032301','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','adela@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2000-01-01','F','N','SE','','example','2023-03-13 13:31:30',NULL,'photo_stock3.jpg',1,0,0,'','ninguno','lorem ipsum is simply dummy text<br/>lorem ipsum is simply dummy text<br/>ipsum SAM','',1,'',1,2,'C:*OdontoAppWeb*web*recursos*static*photo_stock3.jpg'),
(10,'Melania','Alvarez','984294','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','melania@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','CO','','example','2023-03-13 13:40:04',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(11,'Roberto','Maza','833501','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','maza@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','* example<br/>* example','2023-03-13 13:53:33',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(12,'Ivet','Ribera','143349','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','ivet@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2000-01-01','N','N','ND','','* example<br/>* example','2023-03-13 13:56:57',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(13,'Laia','Royo','55527','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','laia@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','* example<br/>*example','2023-03-13 14:12:01',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(14,'Damaris','Perez','840795','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text ','damaris@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','N','N','ND','','* example<br/>* example','2023-03-13 14:23:39',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(15,'Arsenio','Vaquero','854783','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text ','arsenio@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2005-01-01','M','N','SE','','* example<br/>* example','2023-03-14 11:09:17',NULL,'photo_stock3.jpg',1,0,0,'','ninguno','','',1,'',0,0,'C:*sys*web*recursos*static*photo_stock3.jpg'),
(16,'Luna','Ortuño','68254','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','luna@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','F','N','ND','','* example<br/>* example','2023-03-14 11:37:43',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(17,'Raúl','Mosquera','305672','CI',4,'PACIENTE','','','Lorem Ipsum is simply dummy text','RAUL@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2020-01-01','N','N','ND','','* example<br/>/ example','2023-03-14 13:59:16',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,''),
(18,'Ana Maria','Correa Baez','258195','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','ana@yopmail.com',0,0,'0991 123 ','0991 123 123',0,'2005-01-01','N','N','ND','','new <br/>example','2023-03-14 14:09:11',NULL,'photo_stock20.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock20.png'),
(19,'Josefina','Amarilla','781593','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','jose@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','','2023-03-14 14:50:34',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,''),
(20,'Ernesto','Tejada','524429','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','tejada@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','','2023-03-14 15:12:25',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,''),
(21,'Patricio','Paniagua','875909','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','patricio@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','example','2023-03-15 10:23:36',NULL,'photo_stock28.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock28.png'),
(22,'Hector ','Saavedra','982279','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','saavedra@yopmail.com',0,2,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','/ example<br/>/ example','2023-03-15 11:56:32',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(23,'Nerea','De-La-Rosa','611678','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','nerea@yopmail.com',0,4,'0991 123 123','0991 123 123',0,'2005-01-01','F','N','ND','','','2023-03-15 13:07:25',NULL,'photo_stock11.png',1,0,0,'','ninguna','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock11.png'),
(24,'Azahara','Agudo','470022','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','azahara@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','/ 1-example<br/>/ 2-example','2023-03-15 14:26:01',NULL,'photo_stock.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock.png'),
(25,'Alain ','Cuellar','717119','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','cuellar',0,0,'0991 123 123','0991 123 123',0,'1990-01-01','N','N','ND','','example<br/>example<br/>','2023-03-15 14:45:49',NULL,'photo_stock28 - copia.png',1,0,0,'','ninguno','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock28 - copia.png'),
(26,'Bianca','Cuenca','200718','CI',4,'PACIENTE','','','lorem ipsum','bianca@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'2023-03-16','N','N','ND','','','2023-03-16 11:14:50',NULL,'',1,0,0,'','','','',1,'',0,0,''),
(27,'Didac','Ochoa','521487','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','didac@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','','2023-03-16 14:01:51',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(28,'Monica','Tortosa','146665','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','tortosa@yopmail.com',0,0,'0991 123 123','0991 123 123',0,'1995-01-01','N','N','ND','','/ example<br/>/ example','2023-03-17 10:28:37',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(29,'Miranda','Mendoza Pearl','978238','CI',5,'MEDICO','Miranda Pearl','9782538','lorem ipsum is simply dummy text','pearl@yopmail.com',0,0,'','0971 111 222',0,'1995-01-01','F','','ND','','','2023-03-21 12:03:46',NULL,'',23,0,0,'','','','',10,'',0,0,''),
(30,'Paola','Cordoba','719207','CI',5,'MEDICO','Paola Cordoba','719207','lorem ipsum is simply dummy text','paola@yopmail.com',0,0,'','0972 234 567',0,'1995-01-01','F','','ND','','','2023-03-21 12:26:30',NULL,'',23,0,0,'','','','',1,'',0,0,''),
(31,'Sergio Ezequiel','De la Cruz Ramirez','190079','CI',3,'SECRETARIA/O','Ezequiel de la Cruz','190079','lorem ipsum is simply dummy text','sergio@yopmail.com',0,0,'','0982 234 567',0,'1995-01-01','M','','ND','','','2023-03-21 12:29:27',NULL,'',23,0,0,'','','','',1,'',0,0,''),
(32,'Marcelo','Gonzalez Jimenez','217014','CI',4,'PACIENTE','','','lorem ipsum is simpl dummy text','marcelo@yopmail.com',0,4,'0971 412 325','0972 333 445',0,'1997-01-01','M','N','SO','','* example<br/>*example','2023-03-24 10:53:59',NULL,'',1,0,0,'','ninguna','','',1,'',0,0,''),
(33,'Ameliano','Gimenez Torres','352129','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','ameliano@yopmail.com',0,4,'0972 352 153','0999 999 999',0,'1995-01-01','M','N','SE','','* example','2023-03-24 10:57:56',NULL,'',1,0,0,'','ninguno','','',1,'',0,0,''),
(34,'Alan Cristaldo','Oviedo Paredes','598383','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','alan@gmail.com',0,4,'0981 123 456','0999 999 987',0,'2005-01-01','M','N','SO','','* this is simply example','2023-03-24 12:38:20',NULL,'photo_stock5.png',1,0,0,'','ninguna','lorem ipsum <br/>is simply dummy<br/>text','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock5.png'),
(35,'Araceli Josefina','Miranda Amarilla','648620','CI',4,'PACIENTE','','','999','999',0,0,'999','999',0,'2000-01-01','F','N','SE','','* example','2023-03-24 12:44:06',NULL,'',1,0,0,'','999','','',1,'',1,2,''),
(36,'Bernardo','Romero Fernandez','743636','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','bernardo@gmail.com',0,4,'0971 234 567','0999 987 654',0,'1995-01-01','M','N','SE','','* this is simply example','2023-03-24 12:58:23',NULL,'photo.jpg',1,0,0,'','ninguna','','',1,'',1,1,'C:*OdontoAppWeb*build*web*recursos*static*photo.jpg'),
(37,'Emilio','Martinez','859973','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','emilio@gmail.com',0,4,'0971 123 456','0991 987 963',0,'1995-01-01','M','N','SE','','* this example is simply dummy text','2023-03-24 13:52:57',NULL,'',1,0,0,'','estudiante','','',1,'',1,4,'C:*OdontoAppWeb*web*recursos*static'),
(38,'Alberto Carlos','Amarilla Rios','880651','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','ninguna@gmail.com',0,4,'0971 156 231','0991 987 987',0,'2001-01-01','M','N','CO','','* this example <br/>* is simply dummy text','2023-03-25 10:31:44',NULL,'photo_stock25.png',1,0,0,'','doctor','','',1,'',1,3,'C:*OdontoAppWeb*web*recursos*static*photo_stock25.png'),
(39,'Karina','Lopez','471996','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text','kari@gmail.com',0,4,'0971 123 444','0998 889 998',0,'1996-01-01','F','N','SO','','* example<br/>/ example','2023-03-27 11:42:51',NULL,'photo_stock16.png',1,0,0,'','ninguna','','',1,'',1,2,'C:*OdontoAppWeb*web*recursos*static*photo_stock16.png'),
(40,'Fiodor','Dostoievski','13310','CI',4,'PACIENTE','','','a','a',0,0,'0','0',0,'2023-04-21','N','N','ND','','example','2023-04-21 13:34:59',NULL,'',1,0,0,'','a','','',1,'',0,3,''),
(41,'Reinaldo ','Moreira','962657','CI',4,'PACIENTE','','','a','example@gmail.com',0,0,'0','0',0,'2000-04-26','M','N','ND','','example','2023-04-26 12:22:03',NULL,'photo_stock25.png',1,0,0,'','ninguna','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock25.png'),
(42,'Ghost','Example','39215','CI',4,'PACIENTE','','','lorem ipsum is simply dummy text ','examp@gmail.com',0,0,'0971 156 324','0971 159 324',0,'2001-04-27','N','N','ND','','example<br/>example<br/>example','2023-04-27 12:07:23',NULL,'photo_stock26.png',1,0,0,'','ninguna','','',1,'',0,0,'C:*OdontoAppWeb*web*recursos*static*photo_stock26.png');

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
(1,'example serv',0,'A',0),
(2,'example serv 02',0,'A',0);

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
(2,2,'MED','Úèb‡ÙÓ·b×7‹k®§ü','A',5,'NO',0,'pedro@gmail.com','NO'),
(3,23,'nerea','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'nerea@yopmail.com','NO'),
(4,24,'JOSE','eíÙÉÑ\"2ÂÐ£êî±','A',4,'NO',0,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
