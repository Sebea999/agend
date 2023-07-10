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
(1,15,3,5,1,'2022-03-29 14:30:00','1.000','1.000','Agendamiento para el 29/03/2022 a las 14:30.','A',1,'2022-03-23 14:38:36'),
(2,14,3,6,1,'2022-03-29 15:15:00','1.000','1.000','Agendamiento para el 29/03/2022 a las 15:15.','X',1,'2022-03-24 13:55:18'),
(3,11,4,1,1,'2022-04-08 09:30:00','110000.000','110000.000','Agendamiento para el 08/04/2022 a las 09:30.','X',1,'2022-03-31 12:32:12'),
(4,16,4,2,1,'2022-04-14 10:30:00','1.000','0.000','Agendamiento para el 14/04/2022 a las 10:30.','C',16,'2022-04-04 11:58:41'),
(5,16,3,7,1,'2022-04-11 15:10:00','1.000','0.000','Agendamiento para el 11/04/2022 a las 15:10.','C',16,'2022-04-04 12:12:45'),
(6,16,3,8,1,'2022-04-26 15:55:00','1.000','0.000','Agendamiento para el 26/04/2022 a las 15:55.','C',16,'2022-04-21 13:33:13'),
(7,13,3,9,1,'2022-05-30 15:50:00','1.000','1.000','Agendamiento para el 30/05/2022 a las 15:50.','A',1,'2022-05-19 14:39:25'),
(8,10,5,1,1,'2022-05-24 12:00:00','1.000','0.000','Agendamiento para el 24/05/2022 a las 12:00.','C',1,'2022-05-23 10:46:32'),
(9,20,6,1,1,'2022-07-12 15:00:00','0.000','0.000','Agendamiento para el 12/07/2022 a las 15:00.','C',1,'2022-07-12 14:05:22'),
(10,20,7,1,1,'2022-07-12 16:00:00','100000.000','0.000','Agendamiento para el 12/07/2022 a las 16:00.','C',1,'2022-07-12 14:06:25'),
(11,21,6,2,1,'2022-07-15 11:00:00','0.000','0.000','Agendamiento para el 15/07/2022 a las 11:00.','C',1,'2022-07-14 11:18:14'),
(12,21,7,2,1,'2022-07-18 13:15:00','100000.000','100000.000','Agendamiento para el 18/07/2022 a las 13:15.','A',1,'2022-07-14 11:19:34'),
(13,20,8,1,1,'2022-07-18 12:00:00','125000.000','125000.000','Agendamiento para el 18/07/2022 a las 12:00.','A',1,'2022-07-15 13:17:58'),
(14,19,8,2,1,'2022-07-25 11:00:00','125000.000','125000.000','Agendamiento para el 25/07/2022 a las 11:00.','X',1,'2022-07-16 11:43:37'),
(15,22,8,3,1,'2022-07-20 12:30:00','125000.000','125000.000','Agendamiento para el 20/07/2022 a las 12:30.','A',22,'2022-07-18 12:32:40'),
(16,22,6,3,1,'2022-07-20 14:00:00','0.000','0.000','Agendamiento para el 20/07/2022 a las 14:00.','C',1,'2022-07-18 12:53:11'),
(17,20,8,4,1,'2022-07-29 17:30:00','125000.000','125000.000','EXAMPLE DE AGENDAMIENTO 29/07/2022 a las 17:30.','A',1,'2022-07-26 11:56:21'),
(18,20,7,3,1,'2022-07-29 11:00:00','100000.000','100000.000','Agendamiento de Example el día 29 de 07/2022 a las 11:00.','A',1,'2022-07-29 12:11:11'),
(19,19,7,4,1,'2022-07-29 10:30:00','100000.000','100000.000','Agendamiento de Example el día 29 de 07/2022 a las 10:30.','A',1,'2022-07-29 13:00:26'),
(20,20,7,5,1,'2022-07-29 09:30:00','100000.000','100000.000','Agendamiento de Example el día 29 de 07/2022 a las 09:30.','A',1,'2022-07-29 14:43:27'),
(21,20,7,6,1,'2022-08-05 10:00:00','100000.000','100000.000','Agendamiento de Example el día 05 de 08/2022 a las 10:00.','A',1,'2022-07-30 11:22:19'),
(22,20,7,7,1,'2022-08-05 10:30:00','100000.000','100000.000','Agendamiento de Example el día 05 de 08/2022 a las 10:30.','A',1,'2022-07-30 11:42:26'),
(23,20,9,1,1,'2022-08-02 14:30:00','100000.000','100000.000','Agendamiento de Example el día 02 de 08/2022 a las 14:30.','A',1,'2022-08-08 14:31:05'),
(24,27,10,1,1,'2022-08-05 15:50:00','125000.000','0.000','Agendamiento en San Lorenzo el 05/08/#AÑO a las 15:50.','C',27,'2022-08-08 12:34:34'),
(25,27,11,1,1,'2022-08-05 17:15:00','125000.000','0.000','Agendamiento en San Lorenzo el 05/08/2022 a las 17:15.','C',1,'2022-08-04 13:34:25'),
(26,27,12,1,1,'2022-08-05 17:50:00','125000.000','125000.000','Agendamiento en San Lorenzo el 05/08/2022 a las 17:50.','A',29,'2022-08-04 13:43:49'),
(27,31,13,1,1,'2022-08-17 16:00:00','125000.000','125000.000','Agendamiento en San Lorenzo el 17/08/2022 a las 16:00.','A',32,'2022-08-16 14:14:26'),
(28,27,13,2,1,'2022-08-17 17:00:00','125000.000','125000.000','Agendamiento en San Lorenzo el 17/08/2022 a las 17:00.','A',32,'2022-08-16 14:23:43'),
(29,31,13,3,1,'2022-08-18 17:15:00','125000.000','125000.000','Agendamiento en San Lorenzo el 18/08/2022 a las 17:15.','A',31,'2022-08-16 14:32:27'),
(30,31,13,4,1,'2022-08-17 18:00:00','125000.000','125000.000','Agendamiento en San Lorenzo el 17/08/2022 a las 18:00.','A',31,'2022-08-16 14:54:44'),
(32,33,15,1,1,'2022-08-18 17:30:00','80000.000','80000.000','Agendamiento en San Lorenzo el 18/08/2022 a las 17:30.','A',32,'2022-08-17 12:42:16'),
(33,33,14,1,1,'2022-08-18 17:30:00','40000.000','0.000','Agendamiento en San Lorenzo el 18/08/2022 a las 17:30.','C',32,'2022-08-17 14:26:36'),
(34,31,15,1,1,'2022-08-18 18:00:00','80000.000','80000.000','Agendamiento en San Lorenzo el 18/08/2022 a las 18:00.','A',32,'2022-08-17 15:11:00'),
(39,35,16,1,1,'2022-08-29 15:00:00','100000.000','100000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 15:00.','A',35,'2022-08-24 12:12:36'),
(40,35,17,1,1,'2022-08-29 15:20:00','40000.000','40000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 15:20.','X',35,'2022-08-24 12:43:06'),
(41,35,15,4,1,'2022-08-29 16:00:00','80000.000','80000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 16:00.','A',35,'2022-08-24 13:18:28'),
(42,35,13,5,1,'2022-08-29 15:30:00','125000.000','0.000','Agendamiento en San Lorenzo el 29/08/2022 a las 15:30.','C',35,'2022-08-24 13:26:40'),
(43,35,18,1,1,'2022-08-29 16:30:00','0.000','0.000','Agendamiento en San Lorenzo el 29/08/2022 a las 16:30.','C',35,'2022-08-24 13:44:23'),
(44,34,14,2,1,'2022-08-30 15:30:00','40000.000','40000.000','Agendamiento en San Lorenzo el 30/08/2022 a las 15:30.','A',34,'2022-08-29 11:46:13'),
(45,36,15,2,1,'2022-08-30 17:30:00','80000.000','80000.000','Agendamiento en San Lorenzo el 30/08/2022 a las 17:30.','A',32,'2022-08-30 14:11:39'),
(46,36,19,1,1,'2022-09-12 16:15:00','0.000','0.000','agendamiento example #MES y #dia.','C',29,'2022-09-12 11:17:07'),
(47,40,14,3,1,'2022-09-19 15:30:00','40000.000','40000.000','Agendamiento de ejemplo en la clínica de San Lorenzo el día 19/09.','A',29,'2022-09-15 14:01:13');

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
(3,1,3,4,NULL,NULL,NULL,NULL,NULL,'A',1,'2022-02-02 09:36:12'),
(4,2,9,4,NULL,NULL,NULL,NULL,NULL,'A',1,'2022-03-31 12:32:12'),
(5,1,12,3,NULL,NULL,NULL,NULL,NULL,'A',1,'2022-05-23 10:46:32'),
(6,5,18,3,NULL,NULL,NULL,NULL,NULL,'A',1,'2022-07-12 14:05:22'),
(7,5,18,1,NULL,NULL,NULL,NULL,NULL,'C',1,'2022-07-12 14:06:25'),
(8,5,25,6,NULL,NULL,NULL,NULL,NULL,'C',1,'2022-07-15 13:17:58'),
(9,5,18,1,NULL,NULL,NULL,NULL,NULL,'C',1,'2022-08-08 14:31:04'),
(10,5,28,6,NULL,NULL,NULL,NULL,NULL,'C',27,'2022-08-08 12:34:34'),
(11,5,28,6,NULL,NULL,NULL,NULL,NULL,'C',1,'2022-08-04 13:34:25'),
(12,5,28,6,NULL,NULL,NULL,NULL,NULL,'A',29,'2022-08-04 13:43:49'),
(13,5,30,6,NULL,NULL,NULL,NULL,NULL,'A',32,'2022-08-16 14:14:26'),
(14,5,30,7,NULL,NULL,NULL,NULL,NULL,'A',32,'2022-08-17 14:26:36'),
(15,5,30,8,NULL,NULL,NULL,NULL,NULL,'A',32,'2022-08-17 15:11:00'),
(16,5,30,1,NULL,NULL,NULL,NULL,NULL,'A',35,'2022-08-24 12:12:36'),
(17,5,28,7,NULL,NULL,NULL,NULL,NULL,'A',35,'2022-08-24 12:43:06'),
(18,5,30,5,NULL,NULL,NULL,NULL,NULL,'A',35,'2022-08-24 13:44:23'),
(19,2,25,3,NULL,NULL,NULL,NULL,NULL,'A',29,'2022-09-12 11:17:07');

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
  PRIMARY KEY (`IDAGENDAMIENTO`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_agendamiento_det` */

insert  into `ope_agendamiento_det`(`IDAGENDAMIENTO`,`ITEM`,`FECHA_AGEN`,`HORA`,`IDPACIENTE`,`NROPACIENTEFICHA`,`MOTIVO`,`SEGUROMED`,`IDSEGUROMED`,`VISITATIPO`,`COMENTARIO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`FEC_ATENCION`,`IDFACTURA`,`AGENDADO`,`IDCONFIGAGEND`) values 
(3,1,'2022-02-22','2022-02-22 15:50:00',10,'','','',0,'','','A',1,'2022-02-14 14:52:34',NULL,0,'NO',NULL),
(3,2,'2022-02-22','2022-02-22 15:15:00',10,'','','',0,'','','A',1,'2022-02-14 15:00:42',NULL,0,'NO',NULL),
(3,3,'2022-02-22','2022-02-22 15:25:00',13,'','','',0,'','','A',1,'2022-02-14 15:05:11',NULL,0,'NO',NULL),
(3,4,'2022-02-28','2022-02-28 14:00:00',2,'','','',0,'','','A',1,'2022-02-15 13:51:51',NULL,0,'NO',NULL),
(3,5,'2022-03-29','2022-03-29 14:30:00',15,'','','',0,'','','P',1,'2022-03-23 14:38:36',NULL,0,'NO',NULL),
(3,6,'2022-03-29','2022-03-29 15:15:00',14,'','','',0,'','','X',1,'2022-03-24 13:55:18',NULL,0,'NO',NULL),
(3,7,'2022-04-11','2022-04-11 15:10:00',16,'','','',0,'','','A',16,'2022-04-04 12:12:45',NULL,2,'SI',NULL),
(3,8,'2022-04-26','2022-04-26 15:55:00',16,'','','',0,'','','A',16,'2022-04-21 13:33:13',NULL,4,'SI',NULL),
(3,9,'2022-05-30','2022-05-30 15:50:00',13,'','','',0,'','','P',1,'2022-05-19 14:39:25',NULL,0,'NO',NULL),
(4,1,'2022-04-08','2022-04-08 09:30:00',11,'','','',0,'','','X',1,'2022-03-31 12:32:12',NULL,0,'NO',NULL),
(4,2,'2022-04-14','2022-04-14 10:30:00',16,'','','',0,'','','A',16,'2022-04-04 11:58:41',NULL,3,'SI',NULL),
(5,1,'2022-05-24','2022-05-24 12:00:00',10,'','','',0,'','','A',1,'2022-05-23 10:46:32',NULL,6,'SI',NULL),
(6,1,'2022-07-12','2022-07-12 15:00:00',20,'','','',0,'','','C',1,'2022-07-12 14:05:22','2022-07-12 14:11:48',0,'NO',NULL),
(6,2,'2022-07-15','2022-07-15 11:00:00',21,'','','',0,'','','A',1,'2022-07-14 11:18:14',NULL,0,'NO',NULL),
(6,3,'2022-07-20','2022-07-20 14:00:00',22,'','','',0,'','','C',1,'2022-07-18 12:53:11','2022-07-18 12:55:31',0,'NO',NULL),
(7,1,'2022-07-12','2022-07-12 16:00:00',20,'','','',0,'','','C',1,'2022-07-12 14:06:25','2022-07-13 11:11:48',8,'SI',NULL),
(7,2,'2022-07-18','2022-07-18 13:15:00',21,'','','',0,'','','P',1,'2022-07-14 11:19:34',NULL,0,'NO',NULL),
(7,3,'2022-07-29','2022-07-29 11:00:00',20,'','','',0,'','','P',1,'2022-07-29 12:11:11',NULL,0,'NO',NULL),
(7,4,'2022-07-29','2022-07-29 10:30:00',19,'','','',0,'','','P',1,'2022-07-29 13:00:26',NULL,0,'NO',NULL),
(7,5,'2022-07-29','2022-07-29 09:30:00',20,'','','',0,'','','P',1,'2022-07-29 14:43:27',NULL,0,'NO',NULL),
(7,6,'2022-08-05','2022-08-05 10:00:00',20,'','','',0,'','','P',1,'2022-07-30 11:22:19',NULL,0,'NO',NULL),
(7,7,'2022-08-05','2022-08-05 10:30:00',20,'','','',0,'','','P',1,'2022-07-30 11:42:26',NULL,0,'NO',NULL),
(8,1,'2022-07-18','2022-07-18 12:00:00',20,'','','',0,'','','P',1,'2022-07-15 13:17:58',NULL,0,'NO',NULL),
(8,2,'2022-07-25','2022-07-25 11:00:00',19,'','','',0,'','','X',1,'2022-07-16 11:43:37',NULL,0,'NO',NULL),
(8,3,'2022-07-20','2022-07-20 12:30:00',22,'','','',0,'','','P',22,'2022-07-18 12:32:40',NULL,0,'NO',NULL),
(8,4,'2022-07-29','2022-07-29 17:30:00',20,'','','',0,'','','P',1,'2022-07-26 11:56:21',NULL,0,'NO',NULL),
(9,1,'2022-08-02','2022-08-02 14:30:00',20,'','','',0,'','','P',1,'2022-08-08 14:31:04',NULL,0,'NO',NULL),
(10,1,'2022-08-05','2022-08-05 15:50:00',27,'','','',0,'','','C',27,'2022-08-08 12:34:34','2022-08-04 14:03:36',9,'SI',NULL),
(11,1,'2022-08-05','2022-08-05 17:15:00',27,'','','',0,'','','A',1,'2022-08-04 13:34:25',NULL,9,'SI',NULL),
(12,1,'2022-08-10','2022-08-10 17:50:00',27,'','','',0,'','','P',29,'2022-08-04 13:43:49',NULL,0,'NO',NULL),
(12,2,'2022-08-10','2022-08-10 18:50:01',27,'','','',0,'','','P',0,'2022-08-12 11:22:40','0000-00-00 00:00:00',0,'NO',NULL),
(13,1,'2022-08-17','2022-08-17 16:00:00',31,'','','',0,'','','P',32,'2022-08-16 14:14:26',NULL,0,'NO',NULL),
(13,2,'2022-08-17','2022-08-17 17:00:00',27,'','','',0,'','','P',32,'2022-08-16 14:23:43',NULL,0,'NO',NULL),
(13,3,'2022-08-18','2022-08-18 17:15:00',31,'','','',0,'','','P',31,'2022-08-16 14:32:27',NULL,0,'NO',NULL),
(13,4,'2022-08-17','2022-08-17 18:00:00',31,'','','',0,'','','P',31,'2022-08-16 14:54:44',NULL,0,'NO',NULL),
(13,5,'2022-08-29','2022-08-29 15:30:00',35,'','','',0,'','','A',35,'2022-08-24 13:26:40',NULL,13,'SI',NULL),
(14,1,'2022-08-18','2022-08-18 17:30:00',33,'','','',0,'','','C',32,'2022-08-17 14:26:36','2022-08-30 13:23:23',0,'NO',NULL),
(14,2,'2022-08-30','2022-08-30 15:30:00',34,'','','',0,'','','P',34,'2022-08-29 11:46:13','2022-08-31 13:55:40',0,'NO',NULL),
(14,3,'2022-09-19','2022-09-19 15:30:00',40,'','','',0,'','Agendamiento de ejemplo en la clínica de San Lorenzo el día 19/09.','P',29,'2022-09-15 14:01:13',NULL,0,'NO',14),
(15,1,'2022-08-18','2022-08-18 18:00:00',31,'','','',0,'','','P',32,'2022-08-17 15:11:00',NULL,0,'NO',NULL),
(15,2,'2022-08-30','2022-08-30 17:30:00',36,'','','',0,'','','P',32,'2022-08-30 14:11:39','2022-09-07 11:14:20',0,'NO',NULL),
(16,1,'2022-08-29','2022-08-29 15:00:00',35,'','','',0,'','','P',35,'2022-08-24 12:12:36',NULL,0,'NO',NULL),
(17,1,'2022-08-29','2022-08-29 15:20:00',35,'','','',0,'','','X',35,'2022-08-24 12:43:06',NULL,0,'NO',NULL),
(18,1,'2022-08-29','2022-08-29 16:30:00',35,'','','',0,'','','A',35,'2022-08-24 13:44:23',NULL,0,'NO',NULL),
(19,1,'2022-09-12','2022-09-12 16:15:00',36,'','','',0,'','agendamiento example #MES y #dia.','A',29,'2022-09-12 11:17:07',NULL,0,'SI',9);

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

insert  into `ope_atencion_paciente`(`IDATENCIONPAC`,`IDAGENDAMIENTO`,`ITEM_AGEND_DET`,`IDPACIENTE`,`PESO`,`TALLA`,`IMC`,`P_CEFALICO`,`FC`,`TEMP`,`PA`,`F_RESP`,`MOTIVO_CONSULTA`,`EXPLORACION_FISICA`,`DIAGNOSTICO`,`TRATAMIENTO`,`RECETA`,`ESTUDIOS_SOLICITADOS`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`,`IDCLINICA`,`USU_MODI`,`FEC_MODI`) values 
(1,3,6,14,'1','2','3','4','5','9','7','8','abc<br/>CD','cd','f<br/>g','g','iZA','zAB<br/>a<br/>bcd',1,'2022-04-04 11:18:56','X',1,1,'2022-06-06 14:14:58'),
(2,3,3,13,'60','165','','','','','','','PRUEBA DE ECOGRAFIA','A','PRUEBA DE VISUALIZACION','','','',1,'2022-03-16 14:39:26','A',1,NULL,'0000-00-00 00:00:00'),
(3,0,0,10,'73','175','0','0','0','1','1','','Dolor de espalda','abCDEF','lesión leve por golpe de un objeto punzante en un disco de la espalda','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.','Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',1,'2022-04-04 13:04:26','A',1,NULL,'0000-00-00 00:00:00'),
(4,3,1,10,'45','','','','','','','','PRUEBA PARA ELIMINAR','','ABC','','','',1,'2022-04-04 14:19:43','X',1,1,'2022-04-04 14:20:17'),
(5,0,0,10,'85','1.74','','','','','','','PROBLEMA EN LA RODILLA DERECHA','ASDF','LESION MENOR 1','PARACETAMOL','DESCANSO','',1,'2022-04-04 14:30:12','A',1,NULL,'0000-00-00 00:00:00'),
(6,0,0,16,'65kg','1.72','0','0','0','0','0','0','Revisión de lesión en la rodilla.','Totalmente recuperado.','Recuperación completa de la lesión.','1','2','',1,'2022-04-13 14:06:39','A',2,NULL,'0000-00-00 00:00:00'),
(7,3,6,14,'1','1','1','1','1','1','1','1','ADSF','ASDF','ADSF','ADSF','ADSF','',1,'2022-06-06 14:27:10','X',1,1,'2022-06-10 10:04:43'),
(8,6,1,20,'95','1,80','1','1','1','0','0','0','CONSULTA TO EXAMPLE ','NINGUNA','NINGUNA','NINGUNA','NINGUNA','NINGUNA',1,'2022-07-12 14:13:01','A',5,NULL,'0000-00-00 00:00:00'),
(9,7,1,20,'85','1.85','1','1','2','3','4','5','NINGUNA','EXAMPLE','EXAMPLE','','','',1,'2022-07-13 11:11:48','A',5,NULL,'0000-00-00 00:00:00'),
(10,6,3,22,'82','1.71','1','1','1','2','2','3','EXAMPLE TO LIST','EXAMPLE TO NULL','NULL','NADA','','',1,'2022-07-18 12:55:31','A',5,NULL,'0000-00-00 00:00:00'),
(11,10,1,27,'80','1,79','2','1','2','1','1','1','consulta de prueba','example de carga de medico','example de muestra','1','2','3',28,'2022-08-04 14:03:36','A',5,NULL,'0000-00-00 00:00:00'),
(12,14,1,33,'59','1,57','18','6','3','28','1','1','It is a long established fact that a reader will be distracted','There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form','There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form','It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using \'Content here, content here\', making it look like readable English.','* Lorem Ipsum is simply dummy text of the printing <br/>* when an unknown printer took a galley of type and scrambled <br/>* Contrary to popular belief ','The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested.',32,'2022-08-30 13:23:23','A',5,NULL,'0000-00-00 00:00:00'),
(13,14,2,34,'81','1,81','20','9','12','27','3','7','Is simply dummy text of the printing and typesetting industry.','Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.','It has survived not only five centuries','It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.','* Many desktop publishing packages and web page editors<br/>* Various versions have evolved over the years<br/>','* It was popularised in the 1960s with the release of Letraset sheets<br/>* containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.',32,'2022-08-30 16:35:40','X',5,29,'2022-09-14 13:35:08'),
(14,15,2,36,'76','1,75','1','1','1','1','1','1','Lorem Ipsum is simply dummy text','It is a long established fact that a reader will be distracted','Contrary to popular belief, Lorem Ipsum is not simply random text','There are many variations of passages of Lorem Ipsum available','* The standard chunk of Lorem Ipsum <br/>* used since the 1500s is reproduced below for those interested.','Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form',29,'2022-09-08 10:13:52','X',5,29,'2022-09-08 10:44:37');

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

insert  into `ope_atencion_paciente_det`(`IDATENCIONPAC`,`ITEM`,`IDSERVICIO`,`MONTO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`USU_MODI`,`FEC_MODI`) values 
(1,1,1,150000,'A',1,'2022-03-03 13:42:45',NULL,'0000-00-00 00:00:00'),
(1,2,2,135000,'X',1,'2022-03-16 14:09:25',NULL,'0000-00-00 00:00:00'),
(1,3,3,210000,'A',1,'2022-03-16 14:38:01',NULL,'0000-00-00 00:00:00'),
(2,1,1,150000,'A',1,'2022-03-16 14:39:26',NULL,'0000-00-00 00:00:00'),
(2,2,3,210000,'X',1,'2022-04-04 14:18:00',NULL,'0000-00-00 00:00:00'),
(3,0,2,135000,'A',1,'2022-04-04 12:47:19',1,'2022-04-20 12:16:49'),
(3,1,2,135000,'X',1,'2022-04-04 13:17:52',NULL,'0000-00-00 00:00:00'),
(3,2,2,135000,'X',1,'2022-04-04 13:21:43',NULL,'0000-00-00 00:00:00'),
(3,3,2,135000,'X',1,'2022-04-04 13:21:43',NULL,'0000-00-00 00:00:00'),
(4,1,2,135000,'A',1,'2022-04-04 14:19:43',NULL,'0000-00-00 00:00:00'),
(5,1,3,210000,'A',1,'2022-04-04 14:30:12',1,'2022-04-04 14:33:52'),
(5,2,2,135000,'A',1,'2022-04-04 14:32:03',1,'2022-04-04 14:33:52'),
(5,3,1,150000,'A',1,'2022-04-04 14:33:52',NULL,'0000-00-00 00:00:00'),
(6,1,3,210000,'A',1,'2022-04-13 14:06:39',NULL,'0000-00-00 00:00:00'),
(7,1,2,135000,'A',1,'2022-06-06 14:27:10',NULL,'0000-00-00 00:00:00'),
(8,1,2,135000,'A',1,'2022-07-12 14:13:01',NULL,'0000-00-00 00:00:00'),
(9,1,2,135000,'A',1,'2022-07-13 11:11:48',NULL,'0000-00-00 00:00:00'),
(10,1,2,135000,'A',1,'2022-07-18 12:55:31',NULL,'0000-00-00 00:00:00'),
(11,1,2,135000,'A',28,'2022-08-04 14:03:36',NULL,'0000-00-00 00:00:00'),
(12,1,4,98000,'A',32,'2022-08-30 13:23:23',NULL,'0000-00-00 00:00:00'),
(13,1,2,135000,'A',32,'2022-08-31 13:55:40',NULL,'0000-00-00 00:00:00'),
(14,1,2,135000,'A',29,'2022-09-07 11:14:20',NULL,'0000-00-00 00:00:00');

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
(1,'CENTRAL','','0','A','A',NULL,NULL),
(2,'capiata','aabbcc','012345','ab@hotmail.com','A',1,'2022-01-01 09:55:35'),
(5,'San Lorenzo','Lorem Ipsum is simply dummy text of the printing and typesetting industry','0521 321','clinica_lorenzo@hotmail.com','A',1,'2022-06-29 12:30:03'),
(7,'Concepción','Lorem Ipsum is simply dummy text of the printing.','999 999','concepcion_odonto@gmail.com','A',29,'2022-09-01 13:36:41');

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
(1,1,'001-001-280322','Co',14,'2022-03-28',0,'X','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-03-28 13:48:59'),
(2,1,'001-001-28042022','Co',16,'2022-04-28',0,'C','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-04-28 14:02:46'),
(3,2,'001-002-280422','Co',16,'2022-04-27',0,'C','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-04-28 14:04:22'),
(4,1,'001-001-143828','Co',16,'2022-04-28',0,'C','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-04-28 14:38:58'),
(5,1,'001-002-09062022','Co',14,'2022-06-09',0,'X','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-06-06 14:21:33'),
(6,1,'001-006-16062022','Co',10,'2022-06-16',0,'C','1.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Uno','',1,'2022-06-16 14:11:30'),
(7,5,'005-005-11072022','Co',21,'2022-07-11',0,'C','135000.000','0.000','12273.000','0.000','122727.000','0.000','0.000','12273.000',1,' Ciento  Treinta y Cinco Mil ','',1,'2022-07-11 12:37:11'),
(8,5,'005-005-12071410','Co',20,'2022-10-14',0,'C','100000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Cien  Mil ','',1,'2022-07-12 14:11:05'),
(9,5,'001-004-040822','Co',27,'2022-08-04',0,'C','250000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,'Doscientos Cincuenta  Mil ','',29,'2022-08-04 13:54:36'),
(12,5,'001-004-24082022','Co',35,'2022-08-24',0,'X','180000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Ciento  Ochenta  Mil ','',32,'2022-08-24 14:20:56'),
(13,5,'002-004-24082201','Co',35,'2022-08-25',0,'C','125000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Ciento  VeintiCinco Mil ','',32,'2022-08-24 14:26:24'),
(14,5,'001-001-30082022','Co',34,'2022-08-30',0,'X','40000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Cuarenta  Mil ','',32,'2022-08-30 14:21:57'),
(15,5,'001-005-08092022','Co',36,'2022-09-08',0,'X','80000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Ochenta  Mil ','',29,'2022-09-08 12:36:00'),
(16,5,'001-005-0008922','Co',36,'2022-09-08',0,'C','135000.000','0.000','0.000','6429.000','0.000','128571.000','0.000','6429.000',1,' Ciento  Treinta y Cinco Mil ','',29,'2022-09-08 13:18:01'),
(17,2,'001-002-00089222','Co',11,'2022-09-08',0,'C','150000.000','0.000','0.000','0.000','0.000','0.000','0.000','0.000',1,' Ciento  Cincuenta  Mil ','',1,'2022-09-08 14:01:39');

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
(1,1,2,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 29/03/2022 a las 15:15.'),
(2,1,5,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 11/04/2022 a las 15:10.'),
(3,1,4,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 14/04/2022 a las 10:30.'),
(4,1,6,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 26/04/2022 a las 15:55.'),
(5,1,2,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 29/03/2022 a las 15:15.'),
(6,1,8,1,1,'1.000',0,'0.000','0.000','0.000','0.000','0.000','1.000','Agendamiento para el 24/05/2022 a las 12:00.'),
(7,1,2,5,1,'135000.000',10,'0.000','0.000','12273.000','0.000','122727.000','135000.000','vacunación example'),
(8,1,10,1,1,'100000.000',0,'0.000','0.000','0.000','0.000','0.000','100000.000','Agendamiento para el 12/07/2022 a las 16:00.'),
(9,1,24,1,1,'125000.000',0,'0.000','0.000','0.000','0.000','0.000','125000.000','Agendamiento en San Lorenzo el 05/08/#AÑO a las 15:50.'),
(9,2,25,1,1,'125000.000',0,'0.000','0.000','0.000','0.000','0.000','125000.000','Agendamiento en San Lorenzo el 05/08/2022 a las 17:15.'),
(10,1,35,1,1,'40000.000',0,'0.000','0.000','0.000','0.000','0.000','40000.000','Agendamiento en San Lorenzo el 22/08/2022 a las 17:00.'),
(11,1,38,1,1,'40000.000',0,'0.000','0.000','0.000','0.000','0.000','40000.000','Agendamiento en San Lorenzo el 22/08/2022 a las 17:30.'),
(12,1,39,1,1,'100000.000',0,'0.000','0.000','0.000','0.000','0.000','100000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 15:00.'),
(12,2,41,1,1,'80000.000',0,'0.000','0.000','0.000','0.000','0.000','80000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 16:00.'),
(13,1,42,1,1,'125000.000',0,'0.000','0.000','0.000','0.000','0.000','125000.000','Agendamiento en San Lorenzo el 29/08/2022 a las 15:30.'),
(14,1,44,1,1,'40000.000',0,'0.000','0.000','0.000','0.000','0.000','40000.000','Agendamiento en San Lorenzo el 30/08/2022 a las 15:30.'),
(15,1,45,1,1,'80000.000',0,'0.000','0.000','0.000','0.000','0.000','80000.000','Agendamiento en San Lorenzo el 30/08/2022 a las 17:30.'),
(16,1,2,5,1,'135000.000',5,'0.000','6429.000','0.000','128571.000','0.000','135000.000','Estética Dental'),
(17,1,1,5,1,'150000.000',0,'0.000','0.000','0.000','0.000','0.000','150000.000','Odontología Preventiva');

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
(1,1,3,1,'2022-01-10 11:26:47','A'),
(2,1,12,1,'2022-01-21 14:47:41','A'),
(3,2,3,1,'2022-03-29 14:39:50','A'),
(4,2,9,1,'2022-03-31 12:12:44','A'),
(5,5,18,17,'2022-06-29 13:54:18','A'),
(6,5,25,25,'2022-07-15 11:44:15','A'),
(7,2,25,25,'2022-07-15 12:07:16','A'),
(8,5,28,28,'2022-08-08 10:37:40','A'),
(9,5,30,30,'2022-08-16 11:32:44','A'),
(10,5,39,29,'2022-09-05 13:02:46','A'),
(11,7,39,29,'2022-09-05 13:42:24','A');

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
(1,1,'T','2022-01-14 14:00:00','2022-01-14 18:00:00','LUNES',0,'2022-01-14 14:00:31','A'),
(1,2,'T','2022-01-14 14:00:00','2022-01-14 16:00:00','MARTES',0,'2022-01-14 14:01:00','A'),
(2,1,'M','2022-04-12 08:00:00','2022-04-12 13:00:00','MIERCOLES',1,'2022-04-12 11:33:10','A'),
(2,2,'T','2022-04-12 13:00:00','2022-04-12 17:30:00','VIERNES',1,'2022-04-12 11:33:10','A'),
(2,3,'N','2022-04-12 19:00:00','2022-04-12 23:30:00','SABADO',1,'2022-04-12 11:33:10','A'),
(2,4,'M','2022-04-12 07:30:00','2022-04-12 13:30:00','LUNES',1,'2022-04-12 11:33:10','A'),
(2,5,'M','2022-04-12 09:00:00','2022-04-12 13:00:00','MARTES',1,'2022-04-12 11:33:10','A'),
(3,1,'T','2022-03-29 13:00:00','2022-03-29 17:00:00','LUNES',1,'2022-03-29 14:51:24','A'),
(3,2,'N','2022-03-29 18:00:00','2022-03-29 21:30:00','MIERCOLES',1,'2022-03-29 14:51:24','A'),
(4,1,'M','2022-03-31 07:00:00','2022-03-31 13:00:00','JUEVES',1,'2022-03-31 12:12:44','A'),
(4,2,'M','2022-03-31 07:00:00','2022-03-31 13:00:00','VIERNES',1,'2022-03-31 12:12:44','A'),
(4,3,'T','2022-03-31 13:00:00','2022-03-31 16:00:00','SABADO',1,'2022-03-31 12:12:44','A'),
(5,1,'M','2022-07-12 07:30:00','2022-07-12 14:30:00','LUNES',1,'2022-07-12 12:53:00','A'),
(5,2,'M','2022-07-12 07:30:00','2022-07-12 14:30:00','MIERCOLES',1,'2022-07-12 12:53:00','A'),
(5,3,'M','2022-07-12 07:00:00','2022-07-12 12:00:00','VIERNES',1,'2022-07-12 12:53:00','A'),
(5,4,'T','2022-07-12 13:00:00','2022-07-12 16:00:00','SABADO',1,'2022-07-12 12:53:00','A'),
(5,5,'T','2022-07-12 13:00:00','2022-07-12 18:00:00','MARTES',1,'2022-07-12 12:53:00','A'),
(6,1,'M','2022-07-15 08:00:00','2022-07-15 14:00:00','LUNES',25,'2022-07-15 11:44:15','A'),
(6,2,'M','2022-07-15 08:00:00','2022-07-15 14:00:00','MIERCOLES',25,'2022-07-15 11:44:15','A'),
(6,3,'T','2022-07-15 12:00:00','2022-07-15 18:30:00','VIERNES',25,'2022-07-15 11:44:15','A'),
(6,4,'N','2022-07-15 16:00:00','2022-07-15 21:00:00','SABADO',25,'2022-07-15 11:44:15','A'),
(7,1,'M','2022-08-30 09:00:00','2022-08-30 15:00:00','MARTES',1,'2022-08-30 13:59:45','A'),
(7,2,'T','2022-08-30 13:00:00','2022-08-30 18:00:00','JUEVES',1,'2022-08-30 13:59:45','A'),
(7,3,'T','2022-08-30 12:30:00','2022-08-30 18:30:00','LUNES',1,'2022-08-30 13:59:45','A'),
(8,1,'T','2022-08-12 12:00:00','2022-08-12 19:00:00','LUNES',1,'2022-08-12 11:26:09','A'),
(8,2,'T','2022-08-12 12:00:00','2022-08-12 18:30:00','MARTES',1,'2022-08-12 11:26:09','A'),
(8,3,'T','2022-08-12 12:00:00','2022-08-12 18:30:00','JUEVES',1,'2022-08-12 11:26:09','A'),
(8,4,'T','2022-08-12 12:00:00','2022-08-12 18:30:00','VIERNES',1,'2022-08-12 11:26:09','A'),
(8,5,'T','2022-08-12 12:00:00','2022-08-12 19:00:00','MIERCOLES',1,'2022-08-12 11:26:09','A'),
(8,6,'T','2022-08-12 12:00:00','2022-08-12 19:00:00','MIERCOLES',1,'2022-08-12 11:25:58','X'),
(9,1,'T','2022-08-16 12:00:00','2022-08-16 20:00:00','LUNES',30,'2022-08-16 11:32:44','A'),
(9,2,'T','2022-08-16 12:00:00','2022-08-16 20:00:00','MARTES',30,'2022-08-16 11:32:44','A'),
(9,3,'T','2022-08-16 12:00:00','2022-08-16 20:00:00','MIERCOLES',30,'2022-08-16 11:32:44','A'),
(9,4,'T','2022-08-16 13:00:00','2022-08-16 19:00:00','JUEVES',30,'2022-08-16 11:32:44','A'),
(9,5,'T','2022-08-16 13:00:00','2022-08-16 19:00:00','VIERNES',30,'2022-08-16 11:32:44','A'),
(10,1,'T','2022-09-05 13:00:00','2022-09-05 18:00:00','LUNES',29,'2022-09-05 13:02:46','A'),
(10,2,'T','2022-09-05 13:00:00','2022-09-05 18:00:00','MARTES',29,'2022-09-05 13:02:46','A'),
(10,3,'T','2022-09-05 13:00:00','2022-09-05 18:00:00','MIERCOLES',29,'2022-09-05 13:02:46','A'),
(11,1,'T','2022-09-05 12:00:00','2022-09-05 15:00:00','SABADO',29,'2022-09-05 13:42:24','A');

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
(1,'Patólogo oral','A',100000,10),
(2,'Prueba 02','I',210000,5),
(3,'Ortodoncista.','A',0,0),
(4,'Especialista en encías','A',0,0),
(5,'Especialidad básica','A',0,0),
(6,'Dentista general.','A',125000,10),
(7,'Odontopediatra','A',40000,10),
(8,'Prostodoncista.','A',80000,10),
(9,'Especialista en tto. de conducto','A',350000,0);

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
  `ESTADOCIVIL` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'N',
  `GRUPOSANGUINEO` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'N',
  `OBSERVACION` varchar(150) DEFAULT NULL,
  `FECHAALTA` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `FECULTMOMOV` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `FOTO` varchar(100) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `USU_MOD` int DEFAULT NULL,
  `IDPAIS` int DEFAULT NULL,
  `TELEFPARTICULAR` varchar(15) DEFAULT NULL,
  `OCUPACION` varchar(50) DEFAULT NULL,
  `ANTECEDENTES` varchar(200) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'ANTECEDENTES FAMILIARES',
  `EXPEDIENTE_CLINICO` varchar(200) DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  `SEGURO_MEDICO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_persona` */

insert  into `rh_persona`(`IDPERSONA`,`NOMBRE`,`APELLIDO`,`CINRO`,`TIPODOC`,`IDCATEGORIA_PERSONA`,`DESC_CATEG_PERSONA`,`RAZON_SOCIAL`,`RUC`,`DIRECCION`,`EMAIL`,`IDBARRIO`,`IDCIUDAD`,`TELFPARTICULAR`,`TELFMOVIL`,`IDCIUDADNAC`,`FECHANAC`,`SEXO`,`RELIGION`,`ESTADOCIVIL`,`GRUPOSANGUINEO`,`OBSERVACION`,`FECHAALTA`,`FECULTMOMOV`,`FOTO`,`USU_ALTA`,`USU_MOD`,`IDPAIS`,`TELEFPARTICULAR`,`OCUPACION`,`ANTECEDENTES`,`EXPEDIENTE_CLINICO`,`IDCLINICA`,`SEGURO_MEDICO`) values 
(0,'','Cliente Ocasional','000000','CI',4,'PACIENTE','(NO/DEFINIDO)','000000',NULL,NULL,0,0,NULL,NULL,0,'2021-11-24','N',NULL,NULL,NULL,NULL,'2021-11-26 10:10:47','0000-00-00 00:00:00',NULL,0,NULL,0,NULL,NULL,NULL,NULL,1,NULL),
(1,'','ROOT','000000','CI',1,'ADMINISTRADOR','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N','N','N','N',NULL,'2021-11-26 12:29:05','0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,2,NULL),
(2,'LEO','PAREDES','93202','CI',4,'PACIENTE','LEO TORRES','101221-1','Abc','A',0,0,'','123456',0,'1995-01-01','M','','N','','','2021-12-10 09:39:09',NULL,'',1,0,0,'','','2021-12-10 9:39:9','N',1,NULL),
(3,'Juan','Argaña','511890','CI',5,'MEDICO','Juan Argaña','5118890-1','a','a',0,0,'','012',0,'1990-01-01','M','','N','','','2021-12-11 11:38:37',NULL,'',1,0,0,'','','2021-12-11 11:38:37','N',1,NULL),
(4,'Victor','Secret','75433','CI',3,'SECRETARIO','Victor Secret','75433-1','a','a',0,0,'','0',0,'1990-01-01','M','','N','','','2021-12-14 09:56:55',NULL,'',1,0,0,'','','2021-12-14 9:56:55','N',1,NULL),
(5,'koke','gonzalez','14122021','CI',2,'FUNCIONARIO','koke gonzalez','14122021-1','abc','a',0,0,'','012',0,'2021-12-15','N','','N','','','2021-12-14 12:59:01',NULL,'',1,0,0,'','','2021-12-14 12:59:1','N',1,NULL),
(6,'Luis Enrique','Cortez','12012022','CI',2,'FUNCIONARIO','J. Cortez','1201202-2','a','a@hotmail.com',0,0,'','0',0,'2022-01-12','N','','N','','','2022-01-12 10:08:33',NULL,'',1,0,0,'','','2022-1-12 10:8:33','N',1,NULL),
(7,'Jorge Luis','Corrales Patiño','278153','CI',3,'SECRETARIA/O','Jorge Patiño','278153-1','a','abc@hotmail.com',0,0,'','0991 222 345',0,'2022-04-02','N','','N','','','2022-01-12 11:20:11',NULL,'',1,0,0,'','','2022-1-12 11:20:11','N',2,''),
(8,'Ignacio','Galván','572155','CI',3,'SECRETARIO','Ignacio Galván ','572155-1','abcd','ab@hotmail.com',0,0,'','012345',0,'2022-01-12','M','','N','','','2022-01-12 11:24:14',NULL,'',1,0,0,'','','2022-1-12 11:24:14','N',1,NULL),
(9,'Gaspar','Bellido','572155','CI',5,'MEDICO','Gaspar Bellido','5721555-2','asb','ab@gmail.com',0,0,'','987',0,'2022-01-12','M','','N','','','2022-01-12 11:57:20',NULL,'',1,0,0,'','','','N',2,NULL),
(10,'Martina','Abreu','830205','CI',4,'PACIENTE','Martina Abreu','830205-2','aer','air@hotmail.com',0,0,'','654',0,'2022-01-12','F','','N','','','2022-01-12 12:03:27',NULL,'',1,0,0,'','Estudiante','aaaa','N',1,'PARTICULAR'),
(11,'Tania','Moron','905199','CI',4,'PACIENTE','Tania Moron','905199-1','ab','ab@outlook.com',0,0,'','90',0,'2022-01-12','F','','N','','','2022-01-12 12:06:28',NULL,'',1,0,0,'','','','N',2,NULL),
(12,'Constantino ','Revuelta','393426','CI',5,'MEDICO','Constantino Revuelta','393426-1','uu','uu@hotmail.es',0,0,'','39',0,'2022-01-12','M','','N','','','2022-01-12 12:09:04',NULL,'',1,0,0,'','','','N',1,NULL),
(13,'Soledad','Sosa Peralta','505486','CI',4,'PACIENTE','Sole Sosa','505486','a5','soledad@hotmail.com',0,0,'','5',0,'2000-06-29','F','','N','','','2022-02-11 14:13:11',NULL,'',1,0,0,'','','','N',1,NULL),
(14,'Hernan','Cuevas Moreno','927447','CI',4,'PACIENTE','Hernan Moreno','927447','a9','hernan@gmail.com',0,0,'','9',0,'2000-01-01','M','','N','','','2022-02-11 14:33:54',NULL,'',1,0,0,'','','','N',1,NULL),
(15,'Juan Pablo','Gomez Benitez','399248','CI',4,'PACIENTE','Juan Benitez','399248-1','abc','juan@hotmail.com',0,0,'','1',0,'2000-01-01','M','','N','','','2022-03-22 11:28:16',NULL,'',1,0,0,'','Estudiante','ninguno1','',1,'PARCITULAR'),
(16,'Carlos Miguel','Figueroa Gomez','606939','CI',4,'PACIENTE','Miguel Gomez','606939','aBC','miguel_figue@gmail.com',0,0,'','1',0,'2022-04-02','N','','N','','','2022-04-04 11:08:26',NULL,'',1,0,0,'','','','',2,''),
(17,'Jose Miguel','Cuevas Paredes','29062022','CI',3,'SECRETARIA/O','Miguel Cuevas','29062-2','Lorem Ipsum is simply dummy text of the printing and typesetting industry','cuevas_miguel@gmail.com',0,0,'','0981 312 456',0,'2022-06-29','N','','N','','','2022-06-29 13:33:27',NULL,'',1,0,0,'','','2022-6-29 13:33:27','N',5,''),
(18,'Carlos Alejandro','Pineda Aguayo','848755','CI',5,'MEDICO','Carlos Pineda','848755-0','Lorem Ipsum is simply dummy text of the printing and typesetting industry','pineda_car@gmail.com',0,0,'','512 212',0,'1990-01-01','M','','N','','','2022-06-29 13:49:37',NULL,'',1,0,0,'','','2022-6-29 13:49:37','N',5,''),
(19,'Cristina Elizabeth','Beatriz Mendoza','289348','CI',4,'PACIENTE','Elizabeth Beatriz','29834-1','Lorem Ipsum is simply dummy text of the printing and typesetting industry','cris_eli@hotmail.com',0,0,'','512 333',0,'1995-01-01','F','','N','','','2022-06-29 13:51:08',NULL,'',1,0,0,'','Estudiante','','',5,'NINGUNO'),
(20,'Filomina','Cobos Benitez','04072022','CI',4,'PACIENTE','Filomina Cobos Benitez','04072022','Lorem Ipsum is simply dummy text of the printing','filocobos99@yopmail.com',0,0,'','523 211',0,'2022-07-07','N','','N','','','2022-07-07 11:45:38',NULL,'',1,0,0,'','','Lorem Ipsum is simply dummy text of the printing\r\nLorem Ipsum is simply dummy text of the printing\r\nLorem Ipsum is simply dummy text of the printing','',5,''),
(21,'Adan','Aviles Cortes','05072022','CI',4,'PACIENTE','Adan Aviles Cortes','05072022','Lorem Ipsum is simply dummy text of the printing','adan@gmail.com',0,0,'','531 255',0,'2022-07-05','N','','N','','','2022-07-07 11:35:14',NULL,'',1,0,0,'','','','',5,''),
(22,'Lidia Maria','Sanchez Gimenez','11052477','CI',4,'PACIENTE','Lidia Maria Sanchez Gimenez','11052477','asdf asdfasdf asdf','osbenveniste@gmail.com',0,0,'','522 144',0,'2022-07-05','N','','N','','','2022-07-07 11:54:22',NULL,'',1,0,0,'','','','',5,''),
(23,'Maria Antonia','Gamero','06072022','CI',4,'PACIENTE','Maria Antonia Gamero','06072022','asdf asdffdasf','antonia@yopmail.com',0,0,'','521 324',0,'2022-07-06','N','','N','','','2022-07-07 14:16:02',NULL,'',1,0,0,'','','','',2,''),
(24,'Carolina','Acebedo','0607202214','CI',4,'PACIENTE','Carolina Acebedo','0607202214','asdfadfasdf','carol_ace@yopmail.com',0,0,'','521 222',0,'2022-07-06','N','','N','','','2022-07-07 14:25:21',NULL,'',1,0,0,'','','','',2,''),
(25,'Rut','Del Olmo','14072022','CI',5,'MEDICO','Rut del Olmo','140720-2','generador de nombres','rut_olmo@yopmail.com',0,0,'','521 215',0,'2022-07-14','N','','N','','','2022-07-14 11:35:39',NULL,'',1,0,0,'','','','',5,''),
(26,'Ian Matias','Osorio Gomez','543325','CI',3,'SECRETARIA/O','Ian Osorio','15072-2','It is a long established fact that a reader will be distracted','ian_oso@yopmail.com',0,0,'','524 564',0,'2022-01-15','M','','N','','','2022-07-15 14:03:23',NULL,'',1,0,0,'','','','',5,''),
(27,'Markel Rafael','Ponce Godoy','432816','CI',4,'PACIENTE','Rafael Ponce','020822-1','Lorem Ipsum is simply dummy text of the printing ','rafa_ponce@yopmail.com',0,0,'','512 242',0,'2022-08-02','N','','N','','','2022-08-08 12:58:38',NULL,'',1,0,0,'','','','',5,''),
(28,'Elias Israel','Da Silva Santos','70932899','CI',5,'MEDICO','Elias Santos','02082-2','Lorem Ipsum is simply dummy text of the printing ','isra@yopmail.com',0,0,'','845 221',0,'2022-08-02','N','','N','','','2022-08-08 13:12:28',NULL,'',1,0,0,'','','2022-8-8 13:12:28','N',5,''),
(29,'Isidro Roman','Alcano Gomez','783958','CI',3,'SECRETARIA/O','Isidro Gomez','030822-2','Lorem Ipsum is simply dummy text of the printing ','isidro_med@yopmail.com',0,0,'','512 331',0,'2022-08-03','N','','N','','','2022-08-08 10:55:03',NULL,'',1,0,0,'','','','',5,''),
(30,'Lisandro ','Gimenez Soler','292253','CI',5,'MEDICO','Lisandro Soler','292253-1','adfsdf','lisandro_dr@yopmail.com',0,0,'','462 551',0,'2022-08-16','N','','N','','','2022-08-16 11:23:39',NULL,'',29,0,0,'','','2022-8-16 11:23:39','N',5,''),
(31,'Maria Antonia','Latorre Carballo','320740','CI',4,'PACIENTE','Antonia Latorre','33207-4','asdf fdsafdsa asdf','anto@yopmail.com',0,0,'','512 361',0,'2022-08-16','N','','N','','','2022-08-16 11:55:01',NULL,'',29,0,0,'','','','',5,''),
(32,'Enrique Mateo','Carballo Rivaldo','16082022','CI',1,'ADMINISTRADOR','Enrique Mateo Rivaldo','160820-2','asdf asdffdsa','enrique@yopmail.com',0,0,'','612 351',0,'2022-08-16','N','','N','','','2022-08-16 14:00:22',NULL,'',1,0,0,'','','','',5,''),
(33,'Yasmin Joaquina','Mendez Palma','446350','CI',4,'PACIENTE','Joaquina Palma','446308-17','asdff','joaquina_mendez@yopmail.com',0,0,'','541 621',0,'1999-02-01','F','','N','','','2022-08-17 11:57:06',NULL,'',32,0,0,'','estudiante','','',5,'ninguno'),
(34,'Felix Carlos','Silvestre Martinez','487989','CI',4,'PACIENTE','Felix Silvestre','487898-2','asdf asdf','silvestre@yopmail.com',0,0,'','523 154',0,'2022-08-23','M','','N','','','2022-08-19 10:54:18',NULL,'',32,0,0,'','estudiante','','',5,'ninguno'),
(35,'Alfredo Guillermo','Aranda Rios','24082022','CI',4,'PACIENTE','Alfredo Aranda Rios','28771-4','asdf fdaf','alfredo@yopmail.com',0,0,'','541 678',0,'2022-08-24','N','','N','','','2022-08-24 11:53:23',NULL,'',32,0,0,'','','','',5,''),
(36,'Izaro Miguel','Ruiz Baez','29082022','CI',4,'PACIENTE','Izaro Miguel Ruiz Baez','29082022','asdf asdfasdf','izaro_ruiz@yopmail.com',0,0,'','542 315',0,'2022-08-29','N','','N','','','2022-08-29 12:59:04',NULL,'',1,0,0,'','','','',5,''),
(37,'Mariano Cristaldo','Aquino de la Cruz','1596324','CI',4,'PACIENTE','Mariano de la Cruz','159632-1','Lorem Ipsum is simply dummy text','mariano_aquino@gmail.com',0,0,'','012 345',0,'2022-09-02','N','','N','','','2022-09-02 14:14:40',NULL,'',32,0,0,'','','','',1,''),
(38,'Alan Israel','Caparro Castillo','607866','CI',5,'MEDICO','Alan Castillo','60786-6','Lorem Ipsum is simply dummy text','alan@yopmail.com',0,0,'','512 364',0,'2000-04-01','M','','N','','','2022-09-03 11:27:39',NULL,'',29,0,0,'','','2022-9-3 11:27:39','N',5,''),
(39,'Berta Elizabeth','Quiñonez Torales','829552','CI',5,'MEDICO','Berta Quiñonez','8295-2','Lorem Ipsum is simply dummy text','berta@gmail.com',0,0,'','566 412',0,'1955-02-02','F','','N','','','2022-09-03 11:53:01',NULL,'',29,0,0,'','','2022-9-3 11:53:1','N',5,''),
(40,'Cristina Natalia','Sandoval Lopez','934342','CI',4,'PACIENTE','Natalia Sandoval','93434-2','Lorem Ipsum is simply dummy text','nata91@yopmail.com',0,0,'','534 896',0,'1991-01-15','F','','N','','','2022-09-15 13:35:55',NULL,'',29,0,0,'','trabajando','','',5,'ninguno');

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
(1,'Odontología Preventiva',150000,'A',0),
(2,'Estética Dental',135000,'A',5),
(3,'Resonancia Magnetica',210000,'A',0),
(4,'radiología ',98000,'A',0),
(5,'Periodoncia',65000,'A',10),
(6,'Endodoncista',450000,'A',0);

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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `sys_config_agend` */

insert  into `sys_config_agend`(`IDCONFIGAGEND`,`DESC_CONFIG`,`MAX_AGEND`,`DESC_AGEND`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`IDCLINICA`,`INTERV_MIN_ENTRE_AGEND`) values 
(1,'example CENTRAL',9,'agendamiento example.','I',1,'2022-07-26 14:18:11',1,0),
(8,'EXAMPLE CONFIG',1,'Agendamiento de Example el día #DIA# de #MES#/#AÑO# a las #HORA#.','I',1,'2022-08-08 10:51:22',5,0),
(9,'CONFIG CAPIATA',1,'agendamiento example #MES# y #dia#.','A',29,'2022-09-15 11:58:49',2,0),
(10,'PRUEBA CONFIG 1',1,'AGENDAMIENTO DE PRUEBA MES #mes Y DIA #DIA.','I',1,'2022-07-22 14:23:33',2,0),
(12,'AGENDAMIENTO DE LA CLINICA CENTRAL',1,'Agendamiento el día #DIA# del mes #MES#/#AÑO# a las #HORA#.','A',1,'2022-07-26 14:30:21',1,0),
(13,'config san lorenzo',3,'Agendamiento en San Lorenzo el #DIA#/#MES#/#AÑO# a las #HORA#.','I',29,'2022-09-15 12:42:03',5,30),
(14,'Config Example SanLo',20,'Agendamiento de ejemplo en la clínica de San Lorenzo el día #DIA#/#MES#.','A',29,'2022-09-15 12:56:40',5,0);

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
(1,'012345','Odontologia',NULL,NULL,NULL,'Odontologia',NULL,NULL,0,'2021-11-25 12:38:29','A');

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
(0,1,'ROOT','ž¥Ð6´lZ3®ó|¨','A',1,'NO',0,NULL,'S'),
(1,5,'kgon','÷¨¤hóó¢?ÁC!e¯7Õ','A',5,'NO',0,'a456','NO'),
(2,6,'ecortez','Úèb‡ÙÓ·b×7‹k®§ü','A',3,'NO',1,'a@hotmail.com','NO'),
(3,7,'patiño','123','A',3,'NO',0,'abc@hotmail.com','NO'),
(4,16,'migue','123','A',4,'NO',1,'miguel_figue@gmail.com','NO'),
(55,2406,'registrar','123','A',NULL,'NO',0,'registrar@hotmail.com',NULL),
(89,133,'hash_2','$argon2id$v=19$m=1024,t=3,p=1$8yaSqvzUZzCHnjpaWAjfzA$ENrL63vu3x8/Hf7Gr+CGChXl99urwBveYeGnI12412w','A',NULL,'NO',0,'nueva_prueba@hotmail.com',NULL),
(98,99,'prueba02','$argon2id$v=19$m=1024,t=3,p=1$KtkjuYBR/rugR71OPNA+rg$2fjyZuApFz0BCfmH9oWhDGSL7Gf45KC5SFH/HIAojas','A',NULL,'NO',0,'prueba_project@hotmail.com',NULL),
(99,17,'cuevas','123','A',3,'NO',0,'cuevas_miguel@gmail.com','NO'),
(100,20,'filomena_cobos','Úèb‡ÙÓ·b×7‹k®§ü','A',1,'NO',0,'filocobos99@gmail.com','NO'),
(109,21,'adan','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'adan@gmail.com','NO'),
(110,22,'lidia','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'osbenveniste@gmail.com','NO'),
(111,23,'anto','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',1,'antonia@yopmail.com','NO'),
(112,24,'carol','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'carol_ace@yopmail.com','NO'),
(113,25,'rut','Úèb‡ÙÓ·b×7‹k®§ü','A',5,'NO',0,'rut_olmo@yopmail.com','NO'),
(114,28,'isra','Tý‚&ÂÐdˆÑÎ£¨6#','A',5,'NO',0,'isra@yopmail.com','NO'),
(115,27,'markel','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'rafa_ponce@yopmail.com','NO'),
(116,29,'isidro','Úèb‡ÙÓ·b×7‹k®§ü','A',3,'NO',0,'isidro_med@yopmail.com','NO'),
(117,30,'lisa','Úèb‡ÙÓ·b×7‹k®§ü','A',5,'NO',0,'lisandro_dr@yopmail.com','NO'),
(118,31,'latorre','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'anto@yopmail.com','NO'),
(119,32,'enrique','Úèb‡ÙÓ·b×7‹k®§ü','A',1,'NO',0,'enrique@yopmail.com','NO'),
(120,34,'FELIX','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'silvestre@yopmail.com','NO'),
(121,35,'alfredo','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'alfredo@yopmail.com','NO'),
(122,36,'izaro','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'izaro_ruiz@yopmail.com','NO'),
(123,37,'mariano','Úèb‡ÙÓ·b×7‹k®§ü','A',4,'NO',0,'mariano_aquino@gmail.com','NO');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
