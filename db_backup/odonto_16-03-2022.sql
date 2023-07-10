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

/*Table structure for table `ope_agendamiento` */

DROP TABLE IF EXISTS `ope_agendamiento`;

CREATE TABLE `ope_agendamiento` (
  `IDAGENDAMIENTO` int NOT NULL,
  `IDCLINICA` int DEFAULT NULL,
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
  PRIMARY KEY (`IDAGENDAMIENTO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_agendamiento` */

insert  into `ope_agendamiento`(`IDAGENDAMIENTO`,`IDCLINICA`,`IDMEDICO`,`IDESPECIALIDAD`,`IDPERSONA`,`FECHA`,`TURNO`,`DESDE`,`HASTA`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(3,1,3,4,NULL,NULL,NULL,NULL,NULL,'A',1,'2022-02-02 09:36:12');

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
  `ESTADO` char(1) DEFAULT NULL,
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `FEC_ATENCION` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `IDFACTURA` int DEFAULT NULL,
  `AGENDADO` char(2) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL COMMENT 'COLUMNA QUE ME DIRA SI YA ME HE AGENDADO O NO CON EL MEDICO, PARA EVITAR QUE EL DISPATCHER DEL SERVLET VUELVA A REPETIR LA ACCION DE GUARDAR UTILIZANDO ESTE CAMPO COMO BANDERA',
  PRIMARY KEY (`IDAGENDAMIENTO`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_agendamiento_det` */

insert  into `ope_agendamiento_det`(`IDAGENDAMIENTO`,`ITEM`,`FECHA_AGEN`,`HORA`,`IDPACIENTE`,`NROPACIENTEFICHA`,`MOTIVO`,`SEGUROMED`,`IDSEGUROMED`,`VISITATIPO`,`COMENTARIO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`,`FEC_ATENCION`,`IDFACTURA`,`AGENDADO`) values 
(3,1,'2022-02-22','2022-02-22 15:50:00',10,'','','',0,'','','A',1,'2022-02-14 14:52:34',NULL,0,'NO'),
(3,2,'2022-02-22','2022-02-22 15:15:00',10,'','','',0,'','','A',1,'2022-02-14 15:00:42',NULL,0,'NO'),
(3,3,'2022-02-22','2022-02-22 15:25:00',13,'','','',0,'','','A',1,'2022-02-14 15:05:11',NULL,0,'NO'),
(3,4,'2022-02-28','2022-02-28 14:00:00',2,'','','',0,'','','A',1,'2022-02-15 13:51:51',NULL,0,'NO');

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
  `DIAGNOSTICO` varchar(500) DEFAULT NULL COMMENT 'DIAGNÓSTICO',
  `TRATAMIENTO` varchar(500) DEFAULT NULL COMMENT 'TRATAMIENTO',
  `RECETA` varchar(500) DEFAULT NULL COMMENT 'RECETA',
  `ESTUDIOS_SOLICITADOS` varchar(500) DEFAULT NULL COMMENT 'ESTUDIOS SOLICITADOS / REALIZADOS',
  `USU_ALTA` int DEFAULT NULL,
  `FEC_ALTA` timestamp NULL DEFAULT NULL,
  `ESTADO` char(1) DEFAULT NULL,
  PRIMARY KEY (`IDATENCIONPAC`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_atencion_paciente` */

insert  into `ope_atencion_paciente`(`IDATENCIONPAC`,`IDAGENDAMIENTO`,`ITEM_AGEND_DET`,`IDPACIENTE`,`PESO`,`TALLA`,`IMC`,`P_CEFALICO`,`FC`,`TEMP`,`PA`,`F_RESP`,`MOTIVO_CONSULTA`,`EXPLORACION_FISICA`,`DIAGNOSTICO`,`TRATAMIENTO`,`RECETA`,`ESTUDIOS_SOLICITADOS`,`USU_ALTA`,`FEC_ALTA`,`ESTADO`) values 
(1,3,1,10,'1','2','3','4','5','9','7','8','ab<br/>CD','cd','f<br/>g','g','iZA','zAB<br/>a<br/>bcd',1,'2022-03-03 13:42:45','A');

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
  PRIMARY KEY (`IDATENCIONPAC`,`ITEM`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `ope_atencion_paciente_det` */

insert  into `ope_atencion_paciente_det`(`IDATENCIONPAC`,`ITEM`,`IDSERVICIO`,`MONTO`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,1,1,150000,'A',1,'2022-03-03 13:42:45'),
(1,2,2,135000,'A',1,'2022-03-03 13:42:45');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `ope_clinica` */

insert  into `ope_clinica`(`IDCLINICA`,`DESC_CLINICA`,`DIRECCION`,`TELEFONO`,`EMAIL`,`ESTADO`,`USU_ALTA`,`FEC_ALTA`) values 
(1,'CENTRAL','','0','A','A',NULL,NULL),
(2,'capiata','aabbcc','012345','ab@hotmail.com','A',1,'2022-01-01 09:55:35');

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
(2,1,12,1,'2022-01-21 14:47:41','A');

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
(2,1,'M','2022-01-21 08:00:00','2022-01-21 13:00:00','MIERCOLES',1,'2022-01-21 14:47:41','A'),
(2,2,'T','2022-01-20 13:00:00','2022-01-20 17:30:00','VIERNES',1,'2022-01-21 14:20:47','X'),
(2,3,'N','2022-01-21 19:00:00','2022-01-21 23:30:00','SABADO',1,'2022-01-21 14:47:41','A'),
(2,4,'M','2022-01-21 07:30:00','2022-01-21 13:30:00','LUNES',1,'2022-01-21 14:47:41','A');

/*Table structure for table `rh_especialidad` */

DROP TABLE IF EXISTS `rh_especialidad`;

CREATE TABLE `rh_especialidad` (
  `IDESPECIALIDAD` int NOT NULL,
  `DESC_ESPECIALIDAD` varchar(50) DEFAULT NULL,
  `ESTADO` char(1) CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT 'A',
  PRIMARY KEY (`IDESPECIALIDAD`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_especialidad` */

insert  into `rh_especialidad`(`IDESPECIALIDAD`,`DESC_ESPECIALIDAD`,`ESTADO`) values 
(1,'Ejemplo','A'),
(2,'Prueba 02','I'),
(3,'ejemplo 02','A'),
(4,'ejemplo 03','A');

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
  `ANTECEDENTES` varchar(200) DEFAULT NULL,
  `EXPEDIENTE_CLINICO` varchar(200) DEFAULT NULL,
  `IDCLINICA` int DEFAULT NULL,
  PRIMARY KEY (`IDPERSONA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_persona` */

insert  into `rh_persona`(`IDPERSONA`,`NOMBRE`,`APELLIDO`,`CINRO`,`TIPODOC`,`IDCATEGORIA_PERSONA`,`DESC_CATEG_PERSONA`,`RAZON_SOCIAL`,`RUC`,`DIRECCION`,`EMAIL`,`IDBARRIO`,`IDCIUDAD`,`TELFPARTICULAR`,`TELFMOVIL`,`IDCIUDADNAC`,`FECHANAC`,`SEXO`,`RELIGION`,`ESTADOCIVIL`,`GRUPOSANGUINEO`,`OBSERVACION`,`FECHAALTA`,`FECULTMOMOV`,`FOTO`,`USU_ALTA`,`USU_MOD`,`IDPAIS`,`TELEFPARTICULAR`,`OCUPACION`,`ANTECEDENTES`,`EXPEDIENTE_CLINICO`,`IDCLINICA`) values 
(0,'','Cliente Ocasional','000000','CI',4,'PACIENTE','(NO/DEFINIDO)','000000',NULL,NULL,0,0,NULL,NULL,0,'2021-11-24','N',NULL,NULL,NULL,NULL,'2021-11-26 10:10:47','0000-00-00 00:00:00',NULL,0,NULL,0,NULL,NULL,NULL,NULL,1),
(1,'','ROOT','000000','CI',1,'ADMINISTRADOR','',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'N','N','N','N',NULL,'2021-11-26 12:29:05','0000-00-00 00:00:00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),
(2,'LEO','PAREDES','93202','CI',4,'PACIENTE','LEO TORRES','101221-1','Abc','A',0,0,'','123456',0,'1995-01-01','M','','N','','','2021-12-10 09:39:09',NULL,'',1,0,0,'','','2021-12-10 9:39:9','N',1),
(3,'Juan','Argaña','511890','CI',5,'MEDICO','Juan Argaña','5118890-1','a','a',0,0,'','012',0,'1990-01-01','M','','N','','','2021-12-11 11:38:37',NULL,'',1,0,0,'','','2021-12-11 11:38:37','N',1),
(4,'Victor','Secret','75433','CI',3,'SECRETARIO','Victor Secret','75433-1','a','a',0,0,'','0',0,'1990-01-01','M','','N','','','2021-12-14 09:56:55',NULL,'',1,0,0,'','','2021-12-14 9:56:55','N',1),
(5,'koke','gonzalez','14122021','CI',2,'FUNCIONARIO','koke gonzalez','14122021-1','abc','a',0,0,'','012',0,'2021-12-15','N','','N','','','2021-12-14 12:59:01',NULL,'',1,0,0,'','','2021-12-14 12:59:1','N',1),
(6,'Luis Enrique','Cortez','12012022','CI',2,'FUNCIONARIO','J. Cortez','1201202-2','a','a@hotmail.com',0,0,'','0',0,'2022-01-12','N','','N','','','2022-01-12 10:08:33',NULL,'',1,0,0,'','','2022-1-12 10:8:33','N',1),
(7,'Jorge Luis','Corrales Patiño','278153','CI',3,'SECRETARIO','Jorge Patiño','278153-1','a','abc@hotmail.com',0,0,'','0',0,'2022-01-12','M','','N','','','2022-01-12 11:20:11',NULL,'',1,0,0,'','','2022-1-12 11:20:11','N',2),
(8,'Ignacio','Galván','572155','CI',3,'SECRETARIO','Ignacio Galván ','572155-1','abcd','ab@hotmail.com',0,0,'','012345',0,'2022-01-12','M','','N','','','2022-01-12 11:24:14',NULL,'',1,0,0,'','','2022-1-12 11:24:14','N',1),
(9,'Gaspar','Bellido','572155','CI',5,'MEDICO','Gaspar Bellido','5721555-2','asb','ab@gmail.com',0,0,'','987',0,'2022-01-12','M','','N','','','2022-01-12 11:57:20',NULL,'',1,0,0,'','','2022-1-12 11:57:20','N',2),
(10,'Martina','Abreu','830205','CI',4,'PACIENTE','Martina Abreu','830205-2','aer','air@hotmail.com',0,0,'','654',0,'2022-01-12','F','','N','','','2022-01-12 12:03:27',NULL,'',1,0,0,'','','2022-1-12 12:3:27','N',1),
(11,'Tania','Moron','905199','CI',4,'PACIENTE','Tania Moron','905199-1','ab','ab@outlook.com',0,0,'','90',0,'2022-01-12','F','','N','','','2022-01-12 12:06:28',NULL,'',1,0,0,'','','2022-1-12 12:6:28','N',2),
(12,'Constantino ','Revuelta','393426','CI',5,'MEDICO','Constantino Revuelta','393426-1','uu','uu@hotmail.es',0,0,'','39',0,'2022-01-12','M','','N','','','2022-01-12 12:09:04',NULL,'',1,0,0,'','','2022-1-12 12:9:4','N',1),
(13,'Soledad','Sosa Peralta','505486','CI',4,'PACIENTE','Sole Sosa','505486','a5','soledad@hotmail.com',0,0,'','5',0,'2000-06-29','F','','N','','','2022-02-11 14:13:11',NULL,'',1,0,0,'','','2022-2-11 14:13:11','N',1),
(14,'Hernan','Cuevas Moreno','927447','CI',4,'PACIENTE','Hernan Moreno','927447','a9','hernan@gmail.com',0,0,'','9',0,'2000-01-01','M','','N','','','2022-02-11 14:33:54',NULL,'',1,0,0,'','','2022-2-11 14:33:54','N',1);

/*Table structure for table `rh_servicio` */

DROP TABLE IF EXISTS `rh_servicio`;

CREATE TABLE `rh_servicio` (
  `IDSERVICIO` int NOT NULL,
  `DESC_SERVICIO` varchar(50) DEFAULT NULL,
  `MONTO` int DEFAULT NULL,
  `ESTADO` char(1) DEFAULT 'A',
  PRIMARY KEY (`IDSERVICIO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `rh_servicio` */

insert  into `rh_servicio`(`IDSERVICIO`,`DESC_SERVICIO`,`MONTO`,`ESTADO`) values 
(1,'Ecografia',150000,'A'),
(2,'vacunación example',135000,'A'),
(3,'Resonancia Magnetica',210000,'A');

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
(0,1,'ROOT','ADMIN','A',1,'NO',1,NULL,'S'),
(1,5,'kgon','456','A',5,'NO',0,'a','NO'),
(2,6,'ecortez','123','A',3,'NO',0,'a@hotmail.com','NO');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
