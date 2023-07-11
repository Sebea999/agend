/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo;

import com.agend.config.ConfigurationProperties;
import com.agend.interfaz.CRUD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanAgendamiento;
import com.agend.javaBean.BeanCuentaCliente;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelAgendamiento implements CRUD {

    
    
    
    public String VAR_ID_CLINICA = "";
    public String VAR_IDCATEGORIA_MEDICO = "5"; // IDCATEGORIA_PERSONA DEL MEDICO 
    
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_AGENDAMIENTO.-------");
        try {
//            String host = "jdbc:mysql://sql10.freesqldatabase.com:3306/";
//            String bd = "sql10410496";
//            String user = "sql10410496";
//            String pass = "c2AijebkXg";
            // [OBS]: no uso el archivo properties porque me da error; porque no encuentra el archivo properties, igual si se encuentra en la misma carpeta que el modelo por eso en su lugar uso una clase en la carpeta de "config".-
//            Properties properties= new Properties();
//            properties.load(new FileInputStream(new File("configuration.properties")));
//            String host = properties.getProperty("DB_HOST");
//            String bd = properties.getProperty("DB_NAME");
//            String user = properties.getProperty("DB_USER");
//            String pass = properties.getProperty("DB_PASS");
            ConfigurationProperties properties = new ConfigurationProperties();
            String host = properties.getDB_HOST();
            String bd = properties.getDB_NAME();
            String configdb = properties.getDB_CONFIG();
            String user = properties.getDB_USER();
            String pass = properties.getDB_PASS();
            // SERVER.-
//            String host = "jdbc:mysql://198.199.88.44:3306/";
//            String bd = "odonto";
//            String user = "user5";
//            String pass = "agend123";
            // LOCAL.-
//            String host = "jdbc:mysql://localhost/";
//            String bd = "odonto";
//            String user = "root";
//            String pass = "admin";
            System.out.println(".       __host:   "+host);
            System.out.println(".       __db:     "+bd);
            System.out.println(".   __config-db:  "+configdb);
            System.out.println(".       __user:   "+user);
            System.out.println(".       __pass:   "+pass);
//            Class.forName("com.mysql.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            MA_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MA_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
//            System.out.println("[-] Connection is failed --- to properties exception --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- file-not-found-exception.-");
//            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- io-exception.-");
//            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MA_CONEXION;
    } // END METHOD  
    
    
    
    // PRUEBA DE CONEXION A LA BASE DE DATOS DE MARIADB.-
//    String userName,password,url,driver;
//    // conexion a la base de mariadb.- 
//    private Connection devolverConex() {
//        System.out.println("[+] MODEL_AGENDAMIENTO.-------");
//        userName="root";
//        password="admin";
//        url="jdbc:mariadb://localhost:3307/odonto"+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false";
//        driver="org.mariadb.jdbc.Driver";
//        try {
//            Class.forName(driver);
//            MA_CONEXION=DriverManager.getConnection(url, userName, password);
//            MA_SENTENCIA=MA_CONEXION.createStatement();
//            System.out.println("[+] Connection is successful.-");
//        } catch (Exception e) {
//            System.out.println("[-] Connection is failed.-");
//            e.printStackTrace();
//        }
//        return MA_CONEXION;
//    }
    
    
    /*
    _OBSERVACION: --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
        EN ESTE METODO HARE LA CONEXION A LA BASE DE DATOS Y ASI EN CADA CLASE, 
        Y CADA METODO UTILIZARA ESTE METODO PARA HACER CONSULTAS SELECT PASANDOLES SUS SQL AL METODO PARA SER DINAMICOS 
        Y AL UTILIZAR TODO O REALIZAR LAS OPERACIONES QUE TENGA QUE HACER CADA METODO, CERRAR EL RESULTADO, SENTENCIA Y CONEXION QUE SE ABRE EN ESE METODO 
        Y PARA EVITAR AÑADIR ESTE CODIGO QUE CONECTA A LA BASE DE DATOS PARA HACER CADA CONSULTA, ENTONCES CREE ESTE METODO ASI, DESDE EL METODO QUE QUIERA CONSULTAR 
        VA A TENER QUE PASARLE SU SQL NOMAS Y LUEGO UTILIZARLO Y CERRAR AL FINAL PARA EVITAR QUE SE QUEDE EN MEMORIA EL RESULTADO, SENTENICA Y CONEXION Y NO SALTE NINGUN ERROR 
        - HAGO LA CONEXION DESDE CADA CLASE DAO PORQUE EN ALGUNAS OCASIONES ME SALTA ERROR DE NULL POINTER AL QUERER USAR LA CONEXION DE LA CLASE Conexion 
        COMO QUE SE PIERDE LA CONEXION Y DEVUELVE NULL Y AL QUERER CONECTARME A LA BASE PARA HACER LA CONSULTA, ME SALTA ERROR TAMBIEN 
        Y SI QUIERO UTILIZAR LA CONEXION DE LA CLASE O LAS VARIABLES DE ESA CLASE, TAMBIEN ME SALTA ERROR, 
        POR ESO ES QUE INSTANCIO LAS VARIABLES Y LO QUE NECESITO EN CADA CLASE Y NO LO UTILIZO NOMAS LO QUE SE INSTANCIARON EN LA CLASE CONEXION 
    ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    */
    private static Connection MA_CONEXION = null;
    private static Statement MA_SENTENCIA = null;
    private static ResultSet MA_RESULTADO = null;
    
    private ResultSet consultaBD(String sql) {
        try {
////            java.sql.Connection conexion = null;
//            String host = "jdbc:mysql://localhost/";
//            String bd = "unichaco_web";
//            String user = "root";
//            String pass = "admin";
//            System.out.println(".       __host:   "+host);
//            System.out.println(".       __db:   "+bd);
//            System.out.println(".       __user:   "+user);
//            System.out.println(".       __pass:   "+pass);
//            Class.forName("com.mysql.cj.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
//            DIS_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            MA_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MA_SENTENCIA = MA_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MA_RESULTADO = MA_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MA_RESULTADO;
    } // END METHOD  
    
    
    /*
    METODO QUE UTILIZO AL FINAL DE CADA METODO QUE UTILIZA LA BASE PARA DESCONECTAR 
    EL RESULTADO, SENTENCIA Y CONEXION EN CADO DE QUE SEAN UN VALOR DIFERENTE AL NULO 
    ESTE METODO SOLO SE DEBEN DE UTILIZAR AL FINAL DE CADA METODO O CUANDO YA NO 
    UTILICEN NINGUNA DE ESTAS VARIABLES PARA RECUPERAR ALGUN DATO, YA QUE UNA VEZ 
    CERRADAS NO SE PODRAN RECUPERAR NADA 
    */
    private void cerrarConexiones() {
        try {
            if (MA_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MA_RESULTADO.close();
            }
            if (MA_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MA_SENTENCIA.close();
            }
            if (MA_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MA_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    

    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public List traer_datos(String idTraer, HttpSession PARAM_SESION) {
        List<BeanAgendamiento> lista = new ArrayList<>();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO, COALESCE(det.MOTIVO_CONSULTA,'') AS MOTIVO_CONSULTA \n" +
                ", (CASE WHEN(SELECT IDFICHAPAC FROM ope_ficha_pac_nutri oap WHERE oap.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND oap.ITEM_AGEND_DET = det.ITEM AND oap.IDPACIENTE = det.IDPACIENTE AND oap.ESTADO = 'A') IS NULL THEN '' \n" +
                "	ELSE (SELECT IDFICHAPAC FROM ope_ficha_pac_nutri oap WHERE oap.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND oap.ITEM_AGEND_DET = det.ITEM AND oap.IDPACIENTE = det.IDPACIENTE AND oap.ESTADO = 'A') END) AS IDATENCION \n" +
                    // SUBSELECTS CON LA TABLA DE FICHA DE ATENCION PERO DE LA ANTERIOR FICHA PORQUE LA QUE ESTA ACTUAL ES NUTRI 
//                ", (CASE WHEN(SELECT IDATENCIONPAC FROM ope_atencion_paciente oap WHERE oap.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND oap.ITEM_AGEND_DET = det.ITEM AND oap.IDPACIENTE = det.IDPACIENTE AND oap.ESTADO = 'A') IS NULL THEN '' \n" +
//                "	ELSE (SELECT IDATENCIONPAC FROM ope_atencion_paciente oap WHERE oap.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND oap.ITEM_AGEND_DET = det.ITEM AND oap.IDPACIENTE = det.IDPACIENTE AND oap.ESTADO = 'A') END) AS IDATENCION \n" +
                "FROM ope_agendamiento cab \n" +
                "LEFT JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A', 'P', 'C') \n" +
                "WHERE cab.ESTADO <> 'X' \n" +
                "AND cab.IDAGENDAMIENTO = '"+idTraer+"' \n" +
                "ORDER BY det.FECHA_AGEN ASC, HORA ASC \n";
            
            /*
             * OBSERVACION: ANTIGUO SELECT QUE NO TRAE EL IDATENCION (POR MEDIO DE UN SUBSELECT) 
            */
//            String sql = "SELECT cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
//                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
//                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
//                "FROM ope_agendamiento cab \n" +
//                "LEFT JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A', 'P', 'C') \n" +
//                "WHERE cab.ESTADO <> 'X' \n" +
////                "WHERE cab.ESTADO = 'A' \n" +
//                "AND cab.IDAGENDAMIENTO = '"+idTraer+"' \n" +
////                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "ORDER BY det.FECHA_AGEN ASC, HORA ASC \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traer_datos()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while (MA_RESULTADO.next()) {
                BeanAgendamiento datos = new BeanAgendamiento();
                    datos.setOA_IDAGENDAMIENTO(idTraer);
                    datos.setOAD_IDAGENDAMIENTO(idTraer);
                    // CABECERA 
                    datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                    datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                    datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                    datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO_CAB"));
                    // DETALLE 
                    datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                    datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA_AGEN"));
                    datos.setOAD_HORA(MA_RESULTADO.getString("HORA"));
                    datos.setOAD_IDPACIENTE(MA_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAD_NROPACIENTEFICHA(MA_RESULTADO.getString("NROPACIENTEFICHA"));
                    datos.setOAD_MOTIVO(MA_RESULTADO.getString("MOTIVO"));
                    datos.setOAD_VISITATIPO(MA_RESULTADO.getString("VISITATIPO"));
                    datos.setOAD_COMENTARIO(MA_RESULTADO.getString("COMENTARIO"));
                    datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    datos.setOAD_FEC_ATENCION(MA_RESULTADO.getString("FEC_ATENCION"));
                    datos.setOAD_IDFACTURA(MA_RESULTADO.getString("IDFACTURA"));
                    datos.setOAD_AGENDADO(MA_RESULTADO.getString("AGENDADO"));
                    datos.setOAD_IDATENCION_PAC(MA_RESULTADO.getString("IDATENCION"));
//                    if (MA_RESULTADO.getString("MOTIVO_CONSULTA") == null || MA_RESULTADO.getString("MOTIVO_CONSULTA").isEmpty()) {
//                        datos.setOAD_MOTIVO_CONSULTA((""));
//                    } else {
//                        datos.setOAD_MOTIVO_CONSULTA(MA_RESULTADO.getString("MOTIVO_CONSULTA").replaceAll("<br/>","\r\n"));
//                    }
                    datos.setOAD_MOTIVO_CONSULTA(MA_RESULTADO.getString("MOTIVO_CONSULTA").replaceAll("<br/>","\r\n"));
                lista.add(datos);
            } // END WHILE 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    public List traer_datos_det(String idAgen, String itemAgen, HttpSession PARAM_SESION) {
        List<BeanAgendamiento> lista = new ArrayList<>();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d/%m/%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO, COALESCE(det.MOTIVO_CONSULTA,'') AS MOTIVO_CONSULTA \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO \n" + // LE SAQUE EL ESTADO PARA MOSTRAR LOS ANULADOS TAMBIEN - YA QUE USO LA PAGINA PARA MOSTRAR LOS DATOS DEL AGENDAMIENTO Y PUEDE SER QUE EL USER QUIERA VER LOS DATOS DE UN AGENDAMIENTO ANULADO 
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A', 'P', 'C') \n" +
                "WHERE cab.IDAGENDAMIENTO = '"+idAgen+"' \n" +
                "AND det.ITEM = '"+itemAgen+"' \n" +
//                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "ORDER BY det.FECHA_AGEN ASC, HORA ASC \n";
            /*
             * OBSERVACION: SELECT QUE UTILIZIBA ANTES SIN TRAER DATOS DE LOS AGENDAMIENTOS ANULAR PARA EVITAR QUERER REPETIR LA ACCION 
                PERO COMO AHORA DESDE CUENTA CLIENTE EL PACIENTE TIENE LA POSIBLIDAD DE REDIRECCIONAR AL AGENDAMIENTO DE LA CUENTA PARA VER LOS DATOS DEL AGENDAMIENTO 
                ENTONCES ALGUNO PUEDE ESTAR ANULADO Y AUN ASI QUERER VER ESOS DATOS, ENTONCES POR ESO COMENTE EL SELECT Y LE OCULTO EL BOTON DE ANULAR ASI VE EL DATO DEL AGENDAMIENTO DE FORMA INDIVIDUAL 
            */
//            String sql = "SELECT cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
//                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d/%m/%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
//                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
//                "FROM ope_agendamiento cab \n" +
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A', 'P', 'C') \n" +
//                "WHERE cab.ESTADO <> 'X' \n" +
////                "WHERE cab.ESTADO = 'A' \n" +
//                "AND cab.IDAGENDAMIENTO = '"+idAgen+"' \n" +
//                "AND det.ITEM = '"+itemAgen+"' \n" +
////                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "ORDER BY det.FECHA_AGEN ASC, HORA ASC \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traer_datos_det(id, item, sesion)--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while (MA_RESULTADO.next()) {
                BeanAgendamiento datos = new BeanAgendamiento();
                    datos.setOA_IDAGENDAMIENTO(idAgen);
                    datos.setOAD_IDAGENDAMIENTO(idAgen);
                    datos.setOAD_ITEM(itemAgen);
                    // CABECERA 
                    datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                    datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                    datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                    datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO_CAB"));
                    // DETALLE 
                    datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                    datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA_AGEN"));
                    datos.setOAD_HORA(MA_RESULTADO.getString("HORA"));
                    datos.setOAD_IDPACIENTE(MA_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAD_NROPACIENTEFICHA(MA_RESULTADO.getString("NROPACIENTEFICHA"));
                    datos.setOAD_MOTIVO(MA_RESULTADO.getString("MOTIVO"));
                    datos.setOAD_VISITATIPO(MA_RESULTADO.getString("VISITATIPO"));
                    datos.setOAD_COMENTARIO(MA_RESULTADO.getString("COMENTARIO"));
                    datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    datos.setOAD_FEC_ATENCION(MA_RESULTADO.getString("FEC_ATENCION"));
                    datos.setOAD_IDFACTURA(MA_RESULTADO.getString("IDFACTURA"));
                    datos.setOAD_AGENDADO(MA_RESULTADO.getString("AGENDADO"));
                    datos.setOAD_MOTIVO_CONSULTA(MA_RESULTADO.getString("MOTIVO_CONSULTA").replaceAll("<br/>","\r\n"));
                lista.add(datos);
            } // END WHILE 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    @Override
    public String guardar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String guardar_cab(HttpSession PARAM_SESION, Object obj) {
        String tipoRespuesta="1"; // 1 = Exito / 2 = Error 
        BeanAgendamiento datos = (BeanAgendamiento) obj;
        
        // FORMATEAMOS EL VALOR DE LA VARIABLE ESTADO PARA PODER GUARDAR LA INICIAL NOMAS Y NO LA PALABRA COMPLETA 
        String ESTADO = datos.getOA_ESTADO();
        if (ESTADO.equalsIgnoreCase("INACTIVO") || ESTADO.equalsIgnoreCase("I")) {
            ESTADO = "I";
        } else {
            ESTADO = "A";
        }
        
        try {
            int varOperacion;
            String sql="";
            String IDAGENDAMIENTO = datos.getOA_IDAGENDAMIENTO();
            System.out.println("_   __MODEL___ID_AGENDAMIENTO:    :"+IDAGENDAMIENTO);
            String IDCLINICA = datos.getOA_IDCLINICA();
            String IDESPECIALIDAD = datos.getOA_IDESPECIALIDAD();
            String IDMEDICO = datos.getOA_IDMEDICO();
            String USU_ALTA = datos.getOA_USU_ALTA();
            String FEC_ALTA = datos.getOA_FEC_ALTA();
            
            if (datos.getOA_IDAGENDAMIENTO() == null || datos.getOA_IDAGENDAMIENTO().isEmpty() || datos.getOA_IDAGENDAMIENTO().equals("")) {
                varOperacion = 1; // INSERT 
                IDAGENDAMIENTO = ctrlIDAgend(PARAM_SESION, IDMEDICO, IDESPECIALIDAD, IDCLINICA);
                System.out.println("_   __GUARDAR__ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
                // SI EL IDAGENDAMIENTO ESTA NULO ENTONCES ES PORQUE NO ENCONTRO UNA CABECERA CREADA DEL MEDICO Y ENTONCES CARGARIA UNA NUEVA Y EN CASO DE QUE ENCUENTRE ENTONCES NO HARIA NINGUN INSERT Y PASARIA EL IDAGENDAMIENTO AL DETALLE 
                if (IDAGENDAMIENTO.isEmpty() || IDAGENDAMIENTO == null) {
                    System.out.println("-   -       ___IF____INSERT_____    -     -");
                    IDAGENDAMIENTO = traerNewIdAgen();
                    sql = "INSERT INTO ope_agendamiento(IDAGENDAMIENTO, "
                        + "IDCLINICA, IDMEDICO, IDESPECIALIDAD, "
                        + "ESTADO, USU_ALTA, FEC_ALTA) \n" +
                    "VALUES('"+IDAGENDAMIENTO+"', "
                        + "'"+IDCLINICA+"', '"+IDMEDICO+"', '"+IDESPECIALIDAD+"', "
                        + "'"+ESTADO+"', '"+USU_ALTA+"', '"+FEC_ALTA+"') \n";
                    System.out.println("--------------------MODEL_AGENDAMIENTO-------------------------");
                    System.out.println("-- ---insert/update()--------     "+sql);
                    System.out.println("---------------------------------------------------------------");
                    
                    MA_CONEXION = devolverConex();
                    MA_SENTENCIA = MA_CONEXION.createStatement();
                    int cantResul = MA_SENTENCIA.executeUpdate(sql);
                    if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                        if (varOperacion == 2) {
                            tipoRespuesta = "1";
        //                    respuesta = "Se ha Modificado con Exito.";
                        } else {
                            tipoRespuesta = "1";
        //                    respuesta = "Se ha Registrado con Exito.";
                        }
                        
                    } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                        tipoRespuesta = "2";
        //                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
                    }
                    
                } else {
                    System.out.println("-   -       ___ELSE____IDAGENDAMIENTO_CARGADO_____    -     -");
                    System.out.println("-   -_ID_AGENDAMIENTO_EXISTENTE:  :"+IDAGENDAMIENTO);
                    // SI EXISTE UN IDAGENDAMIENTO, CONTROLO LA VARIABLE DE LA SESION EN CASO DE QUE YA HAYA ALCANZO EL LIMITE DE AGENDAMIENTO Y EL USUARIO HAYA CONFIRMADO EN CREAR UNA NUEVA CABECERA PARA EL MEDICO Y EL ACTUAL CERRARLA 
                    if (String.valueOf(PARAM_SESION.getAttribute("CA_VAR_VERIFY_CAB_AGEND")).equals("0")) {
                        System.out.println("_       _if/else/if/______CERRAR_AGENDAMIENTO_Y_ABRIR_UNA_NUEVA_CABECERA________     _");
                        cerrarAgendamiento(IDAGENDAMIENTO);
                        IDAGENDAMIENTO = traerNewIdAgen(); // COMO SE TIENE QUE ABRIR UN NUEVO AGENDAMIENTO, ENTONCES RECUPERO UN NUEVO ID PARA PASARLO POR PARAMETRO 
                        System.out.println("_   _   _   _nuevo_ID_AGENDAMIENTO:__:"+IDAGENDAMIENTO);
                        abrirNuevoAgendamiento(IDAGENDAMIENTO, IDCLINICA, IDMEDICO, IDESPECIALIDAD, ESTADO, USU_ALTA, FEC_ALTA);
                    } // EN CASO DE QUE NO ENTRE AL IF DE LA VALIDACION, TAMPOCO PASA NADA PORQUE AL FINAL GUARDARE EL DETALLE EN LA CABECERA QUE ENCONTRE 
                }
            } // end if idagendamiento null 
            else {
                System.out.println("_   _________ID_AGENDAMIENTO___CARGADO_______");
                // EN CASO DE QUE DESDE LA PAGINA VER AGENDAMIENTO SE AGREGUE UN BOTON COMO ACCESO RAPIDO PARA QUE SE PUEDA AGREGAR UN NUEVO AGENDAMIENTO, PARA EVITAR CARGAR ALGUNOS DATOS Y ASI CARGAR LOS DATOS DEL PACIENTE NOMAS, 
                // EN ESE CASO ENTONCES AGREGAR ACA EL BLOQUE DE CODIGO QUE ESTA ARRIBA QUE CIERRA Y ABRE UNA NUEVA CABECERA 
            }
            guardar_det(obj, IDAGENDAMIENTO, USU_ALTA, IDCLINICA);
            cerrarConexiones();
        } catch (SQLException e) {
            tipoRespuesta = "2";
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    public String guardar_det(Object obj, String PARAM_IDAGENDAMIENTO, String PARAM_USU_ALTA, String PARAM_IDCLINICA) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
//        String FECHA_HOY = modelIniSes.traerFechaHoy(); // traigo la fecha de hoy para completar como dato timestamp las columnas de "DESDE" y "HASTA" 
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        BeanAgendamiento datos = (BeanAgendamiento) obj;
        
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_   _   __   _   __   _   __* GUARDAR__DET *__   _   __   _   __   _   _");
        String CAB_IDESPECIALIDAD = datos.getOA_IDESPECIALIDAD();
            String ESPE_MONTO = traerMontoEspecialidad(CAB_IDESPECIALIDAD);
        try {
            int varOperacion;
            String sql;
            String OAD_IDAGENDAMIENTO = PARAM_IDAGENDAMIENTO;
            String OAD_ITEM = traerMaxItemIdAgen(OAD_IDAGENDAMIENTO);
            String OAD_FECHA_AGEN = datos.getOAD_FECHA_AGEN();
            String OAD_HORA = OAD_FECHA_AGEN+" "+datos.getOAD_HORA()+":00";
            String OAD_IDPACIENTE = datos.getOAD_IDPACIENTE();
            String OAD_NROPACIENTEFICHA = datos.getOAD_NROPACIENTEFICHA();
            String OAD_MOTIVO = datos.getOAD_MOTIVO();
            String OAD_SEGUROMED = datos.getOAD_SEGUROMED();
            String OAD_IDSEGUROMED = datos.getOAD_IDSEGUROMED();
            String OAD_VISITATIPO = datos.getOAD_VISITATIPO();
            // CARGO EN LA VARIABLE LA DESCRIPCION QUE CARGARE PARA EL AGENDAMIENTO DE LA TABLA DE SYS_CONFIG_AGEND DONDE ESTA LA CONFIGURACION POR AGENDAMIENTO DEACUERDO AL IDCLINICA QUE SE CARGUE 
            String IDCONFIGAGEND = datos.getOAD_IDCONFIGAGEND();
//            String IDCONFIGAGEND = traer_idconfig_agend(PARAM_IDCLINICA);
//            String DESC_AGEN = traer_desc_agend(modelIniSes, PARAM_IDCLINICA, OAD_FECHA_AGEN, datos.getOAD_HORA());
//                if (DESC_AGEN == null || DESC_AGEN.isEmpty()) { // EN CASO DE QUE ESTE NULO, YA SEA PORQUE NO HAY UNA CONFIGURACION PARA EL IDCLINICA O NO HAYA NINGUNO ACTIVO, ENTONCES LE CARGARE POR DEFECTO ESTO 
//                    System.out.println("_   _if__guardar_det:   :___DESCRIPCION_VACIA_____NO_SE_RECUPERO_DEL_CONFIG_AGEND____");
//                    DESC_AGEN = "Agendamiento para el "+modelIniSes.getDatoFecha(0, OAD_FECHA_AGEN)+" a las "+datos.getOAD_HORA()+".";
//                }
//                System.out.println("_   _guardar_cta_cliente______desc_agend:    :"+DESC_AGEN);
            String OAD_COMENTARIO = datos.getOAD_COMENTARIO();
            String OAD_USU_ALTA = PARAM_USU_ALTA;
            String OAD_FEC_ALTA = FECHA_HORA_HOY;
            String OAD_FEC_ATENCION = datos.getOAD_FEC_ATENCION();
            String OAD_IDFACTURA = datos.getOAD_IDFACTURA();
            String OAD_AGENDADO = datos.getOAD_AGENDADO();
            String OAD_MOTIVO_CONSULTA = datos.getOAD_MOTIVO_CONSULTA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAD_ESTADO;
//            String OAD_ESTADO = datos.getOAD_ESTADO();
//            if (OAD_ESTADO.equalsIgnoreCase("INACTIVO") || OAD_ESTADO.equalsIgnoreCase("I")) {
//                OAD_ESTADO = "I";
//            } else {
//                OAD_ESTADO = "P"; // VOY A GUARDAR TODAS LAS FILAS CON ESTADO ACTIVO YA QUE ESTE ESTADO LO UTILIZARE PARA MOSTRARLE POR DEFECTO TODAS LAS LINEAS PERO CUANDO EL USUARIO ELIMINE ENTONCES LE INACTIVO NOMAS LA LINEA DEL DETALLE Y SOLAMENTE MOSTRARE LAS LINEAS DEL DETALLE ACTIVAS  
//            }
            // OBSERVACION: 
            // SI LA ESPECIALIDAD CUENTA CON MONTO, ENTONCES LE DEJO CON ESTADO PENDIENTE YA QUE TENDRIA QUE PAGAR PRIMERO EL MONTO DEL AGENDAMIENTO 
            // PERO SI LA ESPECIALIDAD NO CUENTA CON MONTO ENTONCES NO HAY NECESIDAD DE CARGARLE EL DETALLE CON ESTADO PENDIENTE YA QUE NO VA A TENER CUENTA CLIENTE PARA PAGAR EL AGENDAMIENTO 
            if (ESPE_MONTO.isEmpty() || ESPE_MONTO.equals("0")) {
                OAD_ESTADO = "A"; // ACTIVO PARA AGREGAR FICHA DE ATENCION 
                OAD_AGENDADO = "SI"; // SI NO TIENE MONTO ENTONCES NO VA A PODER AGREGAR EL "SI" DESDE FACTURA Y ENTONCES COMO LE ACTIVO EL ESTADO ENTONCES LE AGREGO EL AGENDADO TAMBIEN 
            } else {
                OAD_ESTADO = "P"; // PENDIENTE A PAGO 
            }
            
            varOperacion = 1; // INSERT 
            sql = "INSERT INTO ope_agendamiento_det(IDAGENDAMIENTO, ITEM, "
                    + "FECHA_AGEN, HORA, IDPACIENTE, NROPACIENTEFICHA, MOTIVO, "
                    + "SEGUROMED, IDSEGUROMED, VISITATIPO, COMENTARIO, "
                    + "ESTADO, USU_ALTA, FEC_ALTA, FEC_ATENCION, IDFACTURA, AGENDADO, IDCONFIGAGEND, MOTIVO_CONSULTA) \n" +
                "VALUES('"+OAD_IDAGENDAMIENTO+"', '"+OAD_ITEM+"', "
                    + "'"+OAD_FECHA_AGEN+"', '"+OAD_HORA+"', '"+OAD_IDPACIENTE+"', '"+OAD_NROPACIENTEFICHA+"', '"+OAD_MOTIVO+"', "
                    + "'"+OAD_SEGUROMED+"', '"+OAD_IDSEGUROMED+"', '"+OAD_VISITATIPO+"', '"+OAD_COMENTARIO+"', "
                    + "'"+OAD_ESTADO+"', '"+OAD_USU_ALTA+"', '"+OAD_FEC_ALTA+"', "+OAD_FEC_ATENCION+", '"+OAD_IDFACTURA+"', '"+OAD_AGENDADO+"', '"+IDCONFIGAGEND+"', '"+OAD_MOTIVO_CONSULTA+"') \n";
            System.out.println("---------------------MODEL_AGENDAMIENTO------------------------");
            System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MA_CONEXION = devolverConex();
            MA_SENTENCIA = MA_CONEXION.createStatement();
            int cantResul = MA_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                if (varOperacion == 2) {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Modificado con Exito.";
                } else {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Registrado con Exito.";
                }
                // OBSERVACION: CONDICIONAL QUE UTILIZO PARA SABER SI EL MONTO DE LA ESPECIALIDAD ESTA CARGADO, SI ESTA VACIO O EN CERO ENTONCES NO LE AGREGO UNA CUENTA AL CLIENTE, PERO SI TUVIERA MONTO ENTONCES SI LE AGREGARIA UNA CUENTA CLIENTE DONDE EL CLIENTE TENDRIA QUE PAGAR PRIMERO EL AGENDAMIENTO PARA PODER SER ATENDIDO 
                if (ESPE_MONTO.isEmpty() || ESPE_MONTO.equals("0")) {// #OBS_2: ESTE BLOQUE DE CODIGO ME PUEDE SERVIR EN CASO DE QUE EL MONTO SEA IGUAL A CERO O VACIO Y QUIERA HACER OTRA COSA O NO QUIERA HACER NADA 
                    System.out.println("_           --------IF_--------");
                    // LE CARGO EL METODO PARA QUE INGRESE LA CUENTA CLIENTE DEL PACIENTE PARA TENERLO A MODO DE HISTORICO, POR ESO DEJO QUE INGRESE CERO EN LAS COLUMNAS DE "SALDO" Y "MONTO" Y LE DEJO CON EL ESTADO DE "COBRADO" SIN QUE TENGA ALGUNA FACTURA QUE LINKEAR 
                    guardar_cuenta_cliente(OAD_IDPACIENTE, PARAM_IDAGENDAMIENTO, OAD_ITEM, OAD_FECHA_AGEN, datos.getOAD_HORA(), OAD_USU_ALTA, "0", "C", PARAM_IDCLINICA, OAD_COMENTARIO);
                } else {
                    System.out.println("_           --------ELSE_--------");
                    guardar_cuenta_cliente(OAD_IDPACIENTE, PARAM_IDAGENDAMIENTO, OAD_ITEM, OAD_FECHA_AGEN, datos.getOAD_HORA(), OAD_USU_ALTA, ESPE_MONTO, "A", PARAM_IDCLINICA, OAD_COMENTARIO);
                }
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    // METODO PARA GUARDAR UNA CUENTA AL PACIENTE QUE SE AGENDO PARA QUE AL MOMENTO DE VENIR PUEDA PAGAR SU CONSULTA PARA HABILITARLE 
    public void guardar_cuenta_cliente(String PARAM_IDCLIENTE, String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN, String PARAM_FECHA_AGEN, String PARAM_HORA_AGEN, String idPersona, String PARAM_MONTO_ESPE, String PARAM_ESTADO, String PARAM_IDCLINICA, String DESC_AGEN) {
        ModelCuentaCliente metodosCtaCliente = new ModelCuentaCliente();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        try {
            /*
            // SI SE GUARDO CORRECTAMENTE LA CABECERA Y EL DETALLE DEL EMPEÑO, ENTONCES LLAMO AL METODO QUE SE ENCARGARA DE GUARDAR EN CUENTA CLIENTE 
            */
            String CC_FEC_ALTA = metodosIniSes.traerFechaHoraHoy();
            int item = 0;
    //        for (BeanCuentaCliente listCuotera : CE_LISTA_CUOTERA) {
                item++;
                System.out.println("____FOR:    "+item);
                String CC_IDCUENTACLIENTE = metodosCtaCliente.traerNewIdCtaCliente();
                String CC_IDPERSONA = PARAM_IDCLIENTE;
                String CC_IDAGENDAMIENTO = PARAM_IDAGENDAMIENTO;
                String CC_ITEM_AGEN = PARAM_ITEM_AGEN;
                String CC_NROCUOTA = "1";
                String CC_FEC_VENCIMIENTO = (PARAM_FECHA_AGEN+" "+PARAM_HORA_AGEN);
//                String CC_FEC_VENCIMIENTO = metodosIniSes.convertirFechaYMD(PARAM_FECHA_AGEN);
                String CC_MONTO = PARAM_MONTO_ESPE;
                String CC_SALDO = PARAM_MONTO_ESPE;
                
                // CARGO EN LA VARIABLE LA DESCRIPCION QUE CARGARE PARA EL AGENDAMIENTO DE LA TABLA DE SYS_CONFIG_AGEND DONDE ESTA LA CONFIGURACION POR AGENDAMIENTO DEACUERDO AL IDCLINICA QUE SE CARGUE 
//                String DESC_AGEN = traer_desc_agend(metodosIniSes, PARAM_IDCLINICA, PARAM_FECHA_AGEN, PARAM_HORA_AGEN);
//                if (DESC_AGEN == null) { // EN CASO DE QUE ESTE NULO, YA SEA PORQUE NO HAY UNA CONFIGURACION PARA EL IDCLINICA O NO HAYA NINGUNO ACTIVO, ENTONCES LE CARGARE POR DEFECTO ESTO 
//                    DESC_AGEN = "Agendamiento para el "+metodosIniSes.getDatoFecha(0, PARAM_FECHA_AGEN)+" a las "+PARAM_HORA_AGEN+".";
//                }
//                System.out.println("_   _guardar_cta_cliente______desc_agend:    :"+DESC_AGEN);
                String CC_COMENTARIO = DESC_AGEN;
//                String CC_COMENTARIO = "Agendamiento para el "+metodosIniSes.getDatoFecha(0, PARAM_FECHA_AGEN)+" a las "+PARAM_HORA_AGEN+".";
                String CC_ESTADO = PARAM_ESTADO; // "A"; 
                String CC_USU_ALTA = idPersona;
                
                String rpt_ctaCliente = metodosCtaCliente.guardar(new BeanCuentaCliente(CC_IDCUENTACLIENTE, CC_IDPERSONA, CC_IDAGENDAMIENTO, CC_ITEM_AGEN, CC_NROCUOTA, CC_FEC_VENCIMIENTO, CC_MONTO, CC_SALDO, CC_COMENTARIO, CC_ESTADO, CC_USU_ALTA, CC_FEC_ALTA));
                System.out.println("___"+item+"___________GUARDAR_CUENTA_CLIENTE__________________");
                System.out.println("RESPUESTA:      "+rpt_ctaCliente);
                System.out.println("______________________________________________________________");
    //        } // for lista cuotera 
        } catch(Exception e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    

    @Override
    public String eliminar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    /*
    VALIDACION PARA CONTROLAR SI EN LA CABECERA DEL AGENDAMIENTO YA EXISTE UNA LINEA CON EL IDCLINICA Y IDMEDICO CON ESTADO ACTIVO 
    Y SI ASI FUERA ENTONCES UTILIZARIA ESE ID PARA CARGAR EL DATO DEL AGENDAMIENTO EN EL DETALLE Y SI NO EXISTIERA LA LINEA ACTIVA 
    ENTONCES INSERTARIA UNA NUEVA LINEA DE AGENDAMIENTO 
    */
    public String ctrlIDAgend(HttpSession PARAM_SESION, String PARAM_IDMEDICO, String PARAM_IDESPECIALIDAD, String PARAM_IDCLINICA) {
        String valor = "";
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT IDAGENDAMIENTO \n" +
                "FROM ope_agendamiento \n" +
                "WHERE IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
//                "AND IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND IDESPECIALIDAD = '"+PARAM_IDESPECIALIDAD+"' \n" +
                "AND ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---ctrlIDAgend()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("___FILAS_RESULT_:       :"+MA_RESULTADO.getRow());
            if (MA_RESULTADO.next() == true || MA_RESULTADO.getRow() > 0) {
                System.out.println("_   ___IF___    _");
                String IDAGEN = MA_RESULTADO.getString("IDAGENDAMIENTO");
                valor = IDAGEN;
//            } else {
//                System.out.println("_   ___ELSE___    _");
//                valor = traerNewIdAgen();
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    // METODO PARA TRAER EL NRO DEL ITEM MAXIMO PARA ASI PODER AÑADIR UNA NUEVA LINEA AL DETALLE SIN QUE SALTE ERROR POR ESTAR UTILIZANDO UN ITEM INACTIVO O ANULADO 
    private String traerNewIdAgen() {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(IDAGENDAMIENTO),0)+1) AS ID FROM ope_agendamiento ";
            System.out.println("-----------------MODEL_AGENDAMIENTO--------------------");
            System.out.println("-- ---traerNewIdAgen()-------    "+sql);
            System.out.println("-------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next()) {
                valor = MA_RESULTADO.getString("ID");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    // METODO PARA TRAER EL NRO DEL ITEM MAXIMO PARA ASI PODER AÑADIR UNA NUEVA LINEA AL DETALLE SIN QUE SALTE ERROR POR ESTAR UTILIZANDO UN ITEM INACTIVO O ANULADO 
    private String traerMaxItemIdAgen(String PARAM_IDAGEN) {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(ITEM),0)+1) AS ITEM FROM ope_agendamiento_det WHERE IDAGENDAMIENTO = '"+PARAM_IDAGEN+"' ";
            System.out.println("-----------------MODEL_AGENDAMIENTO--------------------");
            System.out.println("-- ---traerMaxItemIdAgen()-------    "+sql);
            System.out.println("-------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next()) {
                valor = MA_RESULTADO.getString("ITEM");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    /*
        VALIDACION PARA CONTROLAR SI EL DIA DEL AGENDAMIENTO ENTRA DENTRO DE LOS DIAS DE ATENCION DEL MEDICO 
    */
    public boolean ctrlDiaPHMedico(HttpSession PARAM_SESION, ModelInicioSesion metodosIniSes, String PARAM_IDMEDICO, String PARAM_FECHA, String PARAM_HORA, String PARAM_IDCLINICA) {
        boolean valor = false; // false : no se activo la validacion  / / true : se activo la validacion 
        
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_    _________ ctrlDiaPHMedico() _________    _");
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        String PARAM_FEC_DIA = devolverDia(metodosIniSes, PARAM_FECHA);
        try {
            String sql = "SELECT oph.IDPLANHORARIO, ophd.ITEM, ophd.TURNO, ophd.DIA, \n" +
                "DATE_FORMAT(ophd.DESDE,'%H:%i') AS DESDE, DATE_FORMAT(ophd.HASTA,'%H:%i') AS HASTA \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO \n" +
                "WHERE oph.IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
//                "WHERE oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.ESTADO = 'A' \n" +
                "AND ophd.DIA = '"+PARAM_FEC_DIA+"' \n" +
                "AND DATE_FORMAT(ophd.DESDE,'%H:%i') <= '"+PARAM_HORA+"' \n" +
                "AND DATE_FORMAT(ophd.HASTA,'%H:%i') >= '"+PARAM_HORA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---ctrlDiaPHMedico()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            if (MA_RESULTADO.getRow() > 0 || MA_RESULTADO.next() == true) {
//                System.out.println(".");System.out.println(".");
//                String DIA = MA_RESULTADO.getString("DIA");
//                System.out.println("_   __DIA:   :"+DIA);
//                String DESDE = MA_RESULTADO.getString("DESDE");
//                System.out.println("_   _DESDE:   :"+DESDE);
//                String HASTA = MA_RESULTADO.getString("HASTA");
//                System.out.println("_   _HASTA:   :"+HASTA);
                
                valor = false;
            } else {
                valor = true;
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    public String devolverDia(ModelInicioSesion metodosIniSes, String PARAM_FECHA) {
        String Valor_dia = null;
        
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_   _______ devolverDia() _______    _");
        System.out.println(".");System.out.println(".");
        // OBSERVACION: CUALQUIER FECHA QUE SE QUIERA CONVERTIR A DATE POR MEDIO DEL PARSEO DE SIMPLE_DATE_FORMAT DEBE DE ESTAR PRIMERAMENTE EN EL FORMATO QUE SE LE CARGA COMO PARAMETRO CUANDO SE INSTANCIA EL SIMPLE_DATE_FORMAT O SINO LA CONVERSION AL TIPO DE DATO DATE NO SALDRA DE FORMA CORRECTA 
        String FECHA_FORMATEADA = metodosIniSes.convertirFechaYMD(PARAM_FECHA);
        // LUEGO DE FORMATEAR LA FECHA QUE RECIBO POR PARAMETRO YA ESTARIA PARA PODER CONVERTIR A TIPO DATE 
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaActual = null;
        try {
            fechaActual = df.parse(FECHA_FORMATEADA);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        System.out.println("_   _FECHA_CONVERTIDA:  :"+df.format(fechaActual));
        
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
        System.out.println("_   _dia_semana:    :"+diaSemana);
        
        if (diaSemana == 1) {
            Valor_dia = "DOMINGO";
        } else if (diaSemana == 2) {
            Valor_dia = "LUNES";
        } else if (diaSemana == 3) {
            Valor_dia = "MARTES";
        } else if (diaSemana == 4) {
            Valor_dia = "MIERCOLES";
        } else if (diaSemana == 5) {
            Valor_dia = "JUEVES";
        } else if (diaSemana == 6) {
            Valor_dia = "VIERNES";
        } else if (diaSemana == 7) {
            Valor_dia = "SABADO";
        }
        System.out.println("_   ___DIA:   :"+Valor_dia);
        return Valor_dia;
    } // end method 
    
    
    
    /*
     METODO QUE UTILIZO PARA LA PAGINACION DE LA PAGINA PRINCIPAL 
    */
    public List cargar_grilla_paginacion(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanAgendamiento> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
//        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
//        String cant_resul = "131";
//        System.out.println("_   __CANTIDAD_DE_RESULTADOS:   :"+cant_resul);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_NRO_REG_MOSTRAR+"";
//        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT oa.IDAGENDAMIENTO, oa.IDCLINICA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO \n" +
                "FROM ope_agendamiento oa \n" +
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = "+VAR_IDCATEGORIA_MEDICO+") \n" +
                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oa.ESTADO = 'A' \n" +
                "ORDER BY rp.APELLIDO ASC, rp.NOMBRE ASC \n" //+
//                ""+sqlFiltroCbx+" \n"
                    ;
            
//            String sql = "SELECT oa.IDAGENDAMIENTO, oa.IDCLINICA, oc.DESC_CLINICA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, \n" +
//                "oa.IDESPECIALIDAD, re.DESC_ESPECIALIDAD, oa.ESTADO \n" +
//                "FROM ope_agendamiento oa \n" +
//                "JOIN ope_clinica oc ON oa.IDCLINICA = oc.IDCLINICA \n" +
//                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 5)\n" +
//                "JOIN rh_especialidad re ON oa.IDESPECIALIDAD = re.IDESPECIALIDAD \n" +
//                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND oa.ESTADO = 'A' \n" +
//                "ORDER BY rp.APELLIDO ASC, rp.NOMBRE ASC \n" +
//                ""+sqlFiltroCbx+" \n";
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = "+VAR_IDCATEGORIA_MEDICO+") \n" +
                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oa.ESTADO = 'A' \n" +
                "ORDER BY rp.APELLIDO ASC, rp.NOMBRE ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                PARAM_NRO_PAG_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".   __________ELSE___________");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                System.out.println(".   __  __CANT_RESULTADO:  :"+cant_resultado);
                System.out.println(".   __  __NRO_REG_MOSTRAR: :"+PARAM_NRO_REG_MOSTRAR);
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
                System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    System.out.println("_____________IF_____________");
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
                    System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                } else {
                    System.out.println("_____________ELSE_____________");
                }
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println(".");
                System.out.println(".   IF ( > ) ");
                System.out.println("_   _PARAM_NRO_PAG_MOSTRAR:  :"+PARAM_NRO_PAG_MOSTRAR);
                System.out.println("_   _cant_btns_lista_final:  :"+cant_btn_lista_final);
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    PARAM_NRO_PAG_MOSTRAR = "1";
                    CANT_CLICS_DERECHO = 1; 
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                    System.out.println("---------------------_______CONTROL_DE_LA_CANTIDAD_DE_CLIC_DERECHO__Y__LA_PAGINA_ACTUAL_A_MOSTRAR______--------------------------------");
                    // BLOQUE DE CODIGO PARA CORREGIR EL CLIC DERECHO EN CASO DE QUE LA CANTIDAD DE BOTONES EXISTA PERO EL NRO DE PAG ACTUAL A MOSTRAR NO SE ENCUENTRE DENTRO DEL BLOQUE DE BOTONES DE 3 QUE DEVUELVE EL CLIC DERECHO ------------------------------
                    // SI LA CANTIDAD DE CLICS DERECHO FUERA 2, LO MULTIPLICO POR 3 (PORQUE ES LA CANTIDAD DE BOTONES A MOSTRAR) Y AHI TENDRIA EL TERCER BOTON DE PAGINACION Y RESTANDO UNO Y DOS VALORES TENDRIAMOS LOS OTROS DOS BOTONES 
                    // LO IMPORTANTE DE ESTO ES QUE LA PAGINA ACTUAL TENDRIA QUE ESTAR DENTRO DE ESTE RANGO, SINO SE ENCUENTRA ENTRE NINGUNA DE LAS TRES POSIBLIDADES DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHO NO COINCIDE Y LO REINICIARIA 
                    int btn_3 = (CANT_CLICS_DERECHO*3);
                    int btn_2 = btn_3-1;
                    int btn_1 = btn_3-2;
                    System.out.println("----__1__----------------");
                    System.out.println("_   _nro_pag: :"+PARAM_NRO_PAG_MOSTRAR);
                    System.out.println("-------------------------");
                    System.out.println("_   _btn_3:  :"+btn_3);
                    System.out.println("_   _btn_2:  :"+btn_2);
                    System.out.println("_   _btn_1:  :"+btn_1);
                    System.out.println("-------------------------");
                    if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_3
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_2
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_1
                    ) { // SI EL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES, SIGNIFICA QUE ESTA TODO BIEN Y LA CANTIDAD DE CLIC DERECHO ES CORRECTA PUES EL NRO DE PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DEL RANGO 
                        System.out.println("____IF______EL_NRO_DE_PAG_ACTUAL_ES_IGUAL_A_UNO_DE_LOS_TRES_BOTONES_______________");
                    } else { // SI EL NRO DE PAG NO COINCIDE ES PORQUE LA CANTIDAD DE CLIC DERECHO ES INCORRECTO PUES LAS OPCIONES DE BOTONES QUE MUESTRA NO ES IGUAL AL BOTON QUE SE QUIERE MOSTRAR 
                        System.out.println("____ELSE______EL_NRO_DE_PAG_NO_ES_IGUAL_A_NINGUNO_DE_LOS_TRES_BOTONES_______CANT_CLIC_DERECHO_ERRONEO________");
                        // 1- PRIMERA CONDICIONAL PARA VERIFICAR SI EL NRO DE PAG ACTUAL A MOSTRAR ES MAYOR AL TERCER BOTON -
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > btn_3) { // SI FUERA MAYOR ENTONCES HAY QUE SUMARLE UNO A LA CANTIDAD DE CLICS DERECHO PARA VER SI DENTRO DE LOS NUEVOS 3 BOTONES SE ENCUENTRA LA PAG A MOSTRAR 
                            System.out.println("_   _1_____ES_MAYOR_AL_BTN_3_____");
                            // BANDERA QUE ME DIRA SI ES QUE SE ENCONTRO LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                            int BAND_CORRECTO = 0;
                            // HAGO UN FOR CON UN MINIMO DE 5 VUELTAS, QUE VENDRIA A SER LOS INTENTOS QUE HARE PARA ENCONTRAR LA CANTIDAD DE CLICS DERECHOS CORRECTA DONDE LA PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DE LOS TRES BOTONES DE ESE CLIC DERECHO 
                            for (int i = 0; i < 5; i++) {
                                CANT_CLICS_DERECHO = CANT_CLICS_DERECHO + 1;
                                if (metodosIniSes.control_pagActualBotonera(Integer.parseInt(PARAM_NRO_PAG_MOSTRAR), CANT_CLICS_DERECHO) == true) {
                                    System.out.println("__FOR_("+i+")______IF_________");
                                    BAND_CORRECTO = 1; // LE CAMBIO SU VALOR PARA DECIRLE A LA PROXIMA CONDICIONAL QUE SI SE ENCONTRO A LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                                    break; // cortaria el for para no continuar buscando y devolver la cantidad de clics derecho erronea por continuar con el for en caso de a la primera o segunda encontrar a la pagina actual 
                                } else {
                                    System.out.println("__FOR_("+i+")_____ else ________");
                                }
                            } // end for 
                            System.out.println("_   __BANDERA_CORRECTO_CLIC_DERECHO_FOR:  :"+BAND_CORRECTO);
                            // si al finalizar el for no se encontro a la cantidad de clics derecho que devuelva uno de los botones que pertenece a la pagina actual a mostrar, entonces reestableceria ambos valores para no tenerlos erroneo 
                            if (BAND_CORRECTO == 0) { // NO HAY PROBLEMA EN REINICIAR ESTOS VALORES, PORQUE SI ENTRA EN ESTE IF ES PORQUE LA PAGINA ACTUAL ES MAYOR AL BTN 3 Y ENTONCES ESO QUIERE DECIR QUE NO ENTRARIA EN LA SIGUIENTE CONDICIONAL 
                                System.out.println(".");
                                System.out.println(".   _____BANDERA_NO FUE ACTIVADA____ REINICIO LOS VALORES __________");
                                System.out.println(".");
                                PARAM_NRO_PAG_MOSTRAR = "1";
                                CANT_CLICS_DERECHO = 1;
                            }
                        }
                        // 2- SEGUNDA CONDICIONAL PARA VERIFICAR SI LA PAGINA ACTUAL SE ENCUENTRA POR DEBAJO DEL PRIMER BOTON / PODRIA RESTARLE UNO A LA CANTIDAD DE CLICS DERECHOS Y PREGUNTAR POR LOS 3 BOTONES PERO LO VEO INNECESARIO Y LO REESTABLECERIA PARA MOSTRAR LOS PRIMERO 3 BOTONES DE LA PAGINA 
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) < btn_1) {
                            System.out.println("_   _2_____ES_MENOR_AL_BTN_1_____");
                            CANT_CLICS_DERECHO = 1;
                        }
                    }
                    System.out.println("--------------------------------------______END_CONTROL_DE_LAS_DOS_VARIABLES_____-----------------------------------------------------------------------");
                }
            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   _nro_pag_mostrar:     :"+PARAM_NRO_PAG_MOSTRAR);
            System.out.println(".   _cant_clics_derecho:  :"+CANT_CLICS_DERECHO);
            System.out.println(".");
            System.out.println(".   ___end___inicio del while-----");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                    lista_mostrar.add(datos);
                }

                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(PARAM_NRO_PAG_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+PARAM_NRO_PAG_MOSTRAR+")______");
                    break;
                }
                
                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end for or while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_AGEN_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_________DATOS_FINALES____________");
//        System.out.println("_  __CANTIDAD_MIN_MOSTRAR:  :"+cant_min);
////        System.out.println("_  __CANTIDAD_DE_RESUL:     :"+cant_resul);
//        System.out.println("_  __CANTIDAD_INI_DE_LISTA: :"+cant_btn_lista);
//        System.out.println("__________________________________");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
        return lista_mostrar;
    } // end method 
    
    
    
    
    /*
     METODO QUE UTILIZO PARA LA PAGINACION DE LA PAGINA DEL REPORTE DE MIS AGENDAMIENTOS QUE ES DEL PERFIL PACIENTE PARA QUE PUEDA VER LOS AGENDAMIENTOS QUE TENGA 
    */
    public List cargar_grilla_paginacion_rptMisAgen(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR, // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            String PARAM_IDPERSONA, // PARAMETRO PARA SABER EL IDPACIENTE DEL CUAL SE QUIERE VER LOS AGENDAMIENTOS QUE TENGA 
            String PARAM_IDPERFIL_USER // PARAMETRO QUE RECIBE EL IDPERFIL DEL USUARIO, QUE UTILIZARE PARA NO PERMITIRLE AL PERFIL DE PACIENTE QUE VEA LOS REGISTROS ANULADOS - / - PUEDO RECUPERAR DE LA SESION PERO COMO YA RECUPERO EL IDPERFIL ANTES DE INVOCAR A ESTE METODO, ENTONCES LA PASARE COMO PARAMETRO NOMAS EN VEZ DE VOLVER A INSTANCIARLA 
            ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion_rptMisAgen()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanAgendamiento> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        ModelPerfil metodosPerfil = new ModelPerfil();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
//        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
//        String cant_resul = "131";
//        System.out.println("_   __CANTIDAD_DE_RESULTADOS:   :"+cant_resul);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_NRO_REG_MOSTRAR+"";
//        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        String VAR_CATEGORIA_PERSONA = "4"; // PODIA COLOCAR EN DURO ESTE DATO EN EL SELECT PERO LO COLOCO EN ESTA VARIABLE PARA SER CONSCIENTE AL TOCAR O VER EL SELECT DE QUE LA PERSONA POR LA QUE ESTOY HACIENDO JOIN ES POR EL PACIENTE DEL DETALLE Y NO POR LA PERSONA DE LA CABECERA QUE SERIA EL MEDICO 
        
        // RECUPERO EL IDPERFIL DEL USUARIO Y VERIFICO QUE CLASE DE USUARIO ES Y DEPENDIENDO DE ESO AGREGO ALGO AL SELECT O NO 
//        String IDPERFIL = (String) sesionDatos.getAttribute("IDPERFIL");
        String VAR_IDPERFIL_ESTADO_VALI = "";
        if (metodosPerfil.isPerfilPaciente(PARAM_IDPERFIL_USER)==true) { // SI EL PERFIL ES PACIENTE ENTONCES NO LE MOSTRARE LAS CUENTAS ANULADAS 
            VAR_IDPERFIL_ESTADO_VALI = " AND oad.ESTADO NOT IN ('X') \n";
        }
        
        try {
            // OBSERVACION: NO HAY NECESIDAD DE FILTRAR POR LA CLINICA, YA QUE SE QUIERE VER TODOS LOS AGENDAMIENTOS QUE TENGA SIN IMPORTAR EN QUE CLINICA SE ENCUENTRE 
            String sql = "SELECT oa.IDAGENDAMIENTO, oad.ITEM, oa.IDCLINICA, DATE_FORMAT(oad.HORA, '%d/%m/%Y %H:%i') AS FECHA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO, oad.ESTADO AS ESTADO_DET \n" +
                "FROM ope_agendamiento oa \n" +
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO \n" +
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = "+VAR_CATEGORIA_PERSONA+") \n" +
                "WHERE oad.IDPACIENTE = '"+PARAM_IDPERSONA+"' \n" +
                "AND oa.ESTADO IN ('A','C') \n" +
                ""+VAR_IDPERFIL_ESTADO_VALI+"" + 
                "ORDER BY oad.HORA DESC, rp.APELLIDO ASC, rp.NOMBRE ASC \n"// +
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO \n" +
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 4) \n" +
                "WHERE oad.IDPACIENTE = '"+PARAM_IDPERSONA+"' \n" +
                "AND oa.ESTADO IN ('A','C') \n" +
                ""+VAR_IDPERFIL_ESTADO_VALI+"" + 
                "ORDER BY oad.HORA DESC, rp.APELLIDO ASC, rp.NOMBRE ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- --cargar_grilla_paginacion_rptMisAgen()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                PARAM_NRO_PAG_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".   __________ELSE___________");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                System.out.println(".   __  __CANT_RESULTADO:  :"+cant_resultado);
                System.out.println(".   __  __NRO_REG_MOSTRAR: :"+PARAM_NRO_REG_MOSTRAR);
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
                System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    System.out.println("_____________IF_____________");
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
                    System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                } else {
                    System.out.println("_____________ELSE_____________");
                }
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println(".");
                System.out.println(".   IF ( > ) ");
                System.out.println("_   _PARAM_NRO_PAG_MOSTRAR:  :"+PARAM_NRO_PAG_MOSTRAR);
                System.out.println("_   _cant_btns_lista_final:  :"+cant_btn_lista_final);
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    PARAM_NRO_PAG_MOSTRAR = "1";
                    CANT_CLICS_DERECHO = 1; 
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                    System.out.println("---------------------_______CONTROL_DE_LA_CANTIDAD_DE_CLIC_DERECHO__Y__LA_PAGINA_ACTUAL_A_MOSTRAR______--------------------------------");
                    // BLOQUE DE CODIGO PARA CORREGIR EL CLIC DERECHO EN CASO DE QUE LA CANTIDAD DE BOTONES EXISTA PERO EL NRO DE PAG ACTUAL A MOSTRAR NO SE ENCUENTRE DENTRO DEL BLOQUE DE BOTONES DE 3 QUE DEVUELVE EL CLIC DERECHO ------------------------------
                    // SI LA CANTIDAD DE CLICS DERECHO FUERA 2, LO MULTIPLICO POR 3 (PORQUE ES LA CANTIDAD DE BOTONES A MOSTRAR) Y AHI TENDRIA EL TERCER BOTON DE PAGINACION Y RESTANDO UNO Y DOS VALORES TENDRIAMOS LOS OTROS DOS BOTONES 
                    // LO IMPORTANTE DE ESTO ES QUE LA PAGINA ACTUAL TENDRIA QUE ESTAR DENTRO DE ESTE RANGO, SINO SE ENCUENTRA ENTRE NINGUNA DE LAS TRES POSIBLIDADES DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHO NO COINCIDE Y LO REINICIARIA 
                    int btn_3 = (CANT_CLICS_DERECHO*3);
                    int btn_2 = btn_3-1;
                    int btn_1 = btn_3-2;
                    System.out.println("----__1__----------------");
                    System.out.println("_   _nro_pag: :"+PARAM_NRO_PAG_MOSTRAR);
                    System.out.println("-------------------------");
                    System.out.println("_   _btn_3:  :"+btn_3);
                    System.out.println("_   _btn_2:  :"+btn_2);
                    System.out.println("_   _btn_1:  :"+btn_1);
                    System.out.println("-------------------------");
                    if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_3
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_2
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_1
                    ) { // SI EL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES, SIGNIFICA QUE ESTA TODO BIEN Y LA CANTIDAD DE CLIC DERECHO ES CORRECTA PUES EL NRO DE PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DEL RANGO 
                        System.out.println("____IF______EL_NRO_DE_PAG_ACTUAL_ES_IGUAL_A_UNO_DE_LOS_TRES_BOTONES_______________");
                    } else { // SI EL NRO DE PAG NO COINCIDE ES PORQUE LA CANTIDAD DE CLIC DERECHO ES INCORRECTO PUES LAS OPCIONES DE BOTONES QUE MUESTRA NO ES IGUAL AL BOTON QUE SE QUIERE MOSTRAR 
                        System.out.println("____ELSE______EL_NRO_DE_PAG_NO_ES_IGUAL_A_NINGUNO_DE_LOS_TRES_BOTONES_______CANT_CLIC_DERECHO_ERRONEO________");
                        // 1- PRIMERA CONDICIONAL PARA VERIFICAR SI EL NRO DE PAG ACTUAL A MOSTRAR ES MAYOR AL TERCER BOTON -
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > btn_3) { // SI FUERA MAYOR ENTONCES HAY QUE SUMARLE UNO A LA CANTIDAD DE CLICS DERECHO PARA VER SI DENTRO DE LOS NUEVOS 3 BOTONES SE ENCUENTRA LA PAG A MOSTRAR 
                            System.out.println("_   _1_____ES_MAYOR_AL_BTN_3_____");
                            // BANDERA QUE ME DIRA SI ES QUE SE ENCONTRO LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                            int BAND_CORRECTO = 0;
                            // HAGO UN FOR CON UN MINIMO DE 5 VUELTAS, QUE VENDRIA A SER LOS INTENTOS QUE HARE PARA ENCONTRAR LA CANTIDAD DE CLICS DERECHOS CORRECTA DONDE LA PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DE LOS TRES BOTONES DE ESE CLIC DERECHO 
                            for (int i = 0; i < 5; i++) {
                                CANT_CLICS_DERECHO = CANT_CLICS_DERECHO + 1;
                                if (metodosIniSes.control_pagActualBotonera(Integer.parseInt(PARAM_NRO_PAG_MOSTRAR), CANT_CLICS_DERECHO) == true) {
                                    System.out.println("__FOR_("+i+")______IF_________");
                                    BAND_CORRECTO = 1; // LE CAMBIO SU VALOR PARA DECIRLE A LA PROXIMA CONDICIONAL QUE SI SE ENCONTRO A LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                                    break; // cortaria el for para no continuar buscando y devolver la cantidad de clics derecho erronea por continuar con el for en caso de a la primera o segunda encontrar a la pagina actual 
                                } else {
                                    System.out.println("__FOR_("+i+")_____ else ________");
                                }
                            } // end for 
                            System.out.println("_   __BANDERA_CORRECTO_CLIC_DERECHO_FOR:  :"+BAND_CORRECTO);
                            // si al finalizar el for no se encontro a la cantidad de clics derecho que devuelva uno de los botones que pertenece a la pagina actual a mostrar, entonces reestableceria ambos valores para no tenerlos erroneo 
                            if (BAND_CORRECTO == 0) { // NO HAY PROBLEMA EN REINICIAR ESTOS VALORES, PORQUE SI ENTRA EN ESTE IF ES PORQUE LA PAGINA ACTUAL ES MAYOR AL BTN 3 Y ENTONCES ESO QUIERE DECIR QUE NO ENTRARIA EN LA SIGUIENTE CONDICIONAL 
                                System.out.println(".");
                                System.out.println(".   _____BANDERA_NO FUE ACTIVADA____ REINICIO LOS VALORES __________");
                                System.out.println(".");
                                PARAM_NRO_PAG_MOSTRAR = "1";
                                CANT_CLICS_DERECHO = 1;
                            }
                        }
                        // 2- SEGUNDA CONDICIONAL PARA VERIFICAR SI LA PAGINA ACTUAL SE ENCUENTRA POR DEBAJO DEL PRIMER BOTON / PODRIA RESTARLE UNO A LA CANTIDAD DE CLICS DERECHOS Y PREGUNTAR POR LOS 3 BOTONES PERO LO VEO INNECESARIO Y LO REESTABLECERIA PARA MOSTRAR LOS PRIMERO 3 BOTONES DE LA PAGINA 
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) < btn_1) {
                            System.out.println("_   _2_____ES_MENOR_AL_BTN_1_____");
                            CANT_CLICS_DERECHO = 1;
                        }
                    }
                    System.out.println("--------------------------------------______END_CONTROL_DE_LAS_DOS_VARIABLES_____-----------------------------------------------------------------------");
                }
            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   _nro_pag_mostrar:     :"+PARAM_NRO_PAG_MOSTRAR);
            System.out.println(".   _cant_clics_derecho:  :"+CANT_CLICS_DERECHO);
            System.out.println(".");
            System.out.println(".   ___end___inicio del while-----");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA"));
                        datos.setOAD_HORA(MA_RESULTADO.getString("FECHA"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                        datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    lista_mostrar.add(datos);
                }

                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(PARAM_NRO_PAG_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+PARAM_NRO_PAG_MOSTRAR+")______");
                    break;
                }

                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end for or while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_________DATOS_FINALES____________");
//        System.out.println("_  __CANTIDAD_MIN_MOSTRAR:  :"+cant_min);
////        System.out.println("_  __CANTIDAD_DE_RESUL:     :"+cant_resul);
//        System.out.println("_  __CANTIDAD_INI_DE_LISTA: :"+cant_btn_lista);
//        System.out.println("__________________________________");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
        return lista_mostrar;
    } // end method 
    
    
    
    // METODO PARA REALIZAR LA PAGINACION DE LA PAGINA POR MEDIO DE LOS FILTROS 
    public List filtrar(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FILTRO, 
            String PARAM_TXT_IDMED, 
            String PARAM_TXT_IDESPE, 
            String PARAM_CBX_ESTADO 
    ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("___     ___________filtrar_paginacion_agend()___________     ___");
//        PARAM_CBX_MOSTRAR = "1";
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
//        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
//            System.out.println("_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "1";
//        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
//            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "2";
//        }
        
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (sesionDatos.getAttribute("PAG_AGEN_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_AGEN_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_AGEN_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_AGEN_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
        System.out.println("_   _CANTIDAD_INICIAL_ANTERIOR_DE_REGISTROS:    :"+cant_inicial_anterior);
        // CONTROLO SI LA CANTIDAD MINIMA INCIAL DE LINEAS DE REGISTRO ES IGUAL A LA CANTIDAD DE REGISTROS A MOSTRAR, SI SON IGUALES, ENTONCES NO SE CAMBIO Y SOLO QUIERE VER LOS DATOS DE OTRA PAGINA, PERO SI ES DIFERENTE, ENTONCES ES PORQUE SE CAMBIO LA CANTIDAD DE REGISTROS A MOSTRAR Y VOLVERIA A MOSTRAR DESDE LA PAGINA 1 Y NO DESDE LA QUE ESTA PORQUE LA CANTIDAD DE BOTONES VAN A CAMBIAR PORQUE SE VAN A VOLVER A CALCULAR 
        if (!(cant_inicial_anterior.equals(cant_min_fija))) { // SI NO SON IGUALES ENTONCES LE REINICIO LA PAGINA ACTUAL A MOSTRAR 
//            System.out.println("_   (*)__CANTIDAD_DE_REGISTROS_DISTINTA__");
            NRO_PAG_ACTUAL_MOSTRAR = "1";
            CANT_CLICS_DERECHO = 1;
        } else { // NO REINICIARIA LA PAGINA ACTUAL A MOSTRAR SI SON IGUALES LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES LE CARGO ESA PAGINA YA QUE ESTARIA MUDANDO DE PAGINA Y NO REORDENANDO LOS REGISTROS POR PAGINA 
//            System.out.println("_   (*)___ELSE____CANTIDAD_DE_REGISTROS_IGUALES________");
        }
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_CBX_MOSTRAR+"";
//        }
        
        String sqlFiltroTxt="";
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_FILTRO == null || PARAM_TXT_FILTRO.isEmpty() || PARAM_TXT_FILTRO.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND (UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(oa.IDESPECIALIDAD) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(oa.IDAGENDAMIENTO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                ")";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty() || PARAM_TXT_IDMED.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDMEDICO) LIKE ('%"+PARAM_TXT_IDMED+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty() || PARAM_TXT_IDESPE.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDESPECIALIDAD) LIKE ('%"+PARAM_TXT_IDESPE+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_CBX_ESTADO == null || PARAM_CBX_ESTADO.isEmpty() || PARAM_CBX_ESTADO.equals("")) {
//            sqlFiltroTxt = "AND oa.ESTADO = 'A' \n"; // EN CASO DE QUE VENGA VACIO, ENTONCES LE FILTRO POR LAS CABECERAS ABIERTAS 
        } else {
            if (PARAM_CBX_ESTADO.equalsIgnoreCase("T") || PARAM_CBX_ESTADO.equalsIgnoreCase("TODOS")) {
                //
            } else {
                sqlFiltroTxt = sqlFiltroTxt + "AND UPPER(oa.ESTADO) = UPPER('"+PARAM_CBX_ESTADO+"') \n";
            }
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT oa.IDAGENDAMIENTO, oa.IDCLINICA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO \n" +
                "FROM ope_agendamiento oa \n" + 
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 5) \n" + 
                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND oa.ESTADO = 'A' \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oa.IDAGENDAMIENTO DESC \n"// +
//                ""+sqlFiltroCbx+" \n"
                    ;
            
//            String sql = "SELECT oph.IDPLANHORARIO, oph.IDCLINICA, oph.IDMEDICO, DATE_FORMAT(ophd.DESDE,'%H:%i') AS DESDE, \n" +
//                "DATE_FORMAT(ophd.HASTA,'%H:%i') AS HASTA, ophd.TURNO, ophd.DIA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oph.ESTADO \n" +
//                "FROM ope_plan_horario oph \n" +
//                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO \n" +
//                "JOIN rh_persona rp ON oph.IDMEDICO = rp.IDPERSONA \n" +
//                "WHERE oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                ""+sqlFiltroTxt+" \n" + 
//                ""+sqlFiltroMed+" \n" + 
//                "ORDER BY oph.IDPLANHORARIO DESC \n" + 
//                ""+sqlFiltroCbx+" \n";
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 5) \n" + 
                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND oa.ESTADO = 'A' \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oa.IDAGENDAMIENTO DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("---------------------MODEL_AGENDAMIENTO------------------------");
            System.out.println("-- --filtrar()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                NRO_PAG_ACTUAL_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
    //            System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
    //                System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                }
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    NRO_PAG_ACTUAL_MOSTRAR = "1";
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                }
            }
            // --------------------------------------------------------------------------------------------------------
            
            // --------------------------------------------------------------------------------------------------------
            // OBSERVACION: YA QUE LA NUMERACION DE LA PAGINA DONDE SE ENCONTRABA EL USUARIO NO SE ENCUENTRA EN EXISTENCIA EN LA NUEVA CANTIDAD DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHOS DEBO DE RESETEAR 
            // VALIDACION PARA REESTABLECER LA CANTIDAD DE CLICS DERECHOS, PARA QUE SI POR EJEMPLO ESTABA EN EL BOTON NRO 4 Y REORDENA LA CANTIDAD DE REGISTROS A VISUALIZAR O AÑADE UN NUEVO FILTRO Y EL BOTON TOTAL SEA 2, COMO NO EXISTE MAS EL 4 EN ESA CLASIFICACION, ENTONCES LE DEVUELVA A LA PRIMERA PAGINA Y EL CLIC DERECHO VOLVERIA A SER 1 
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_  ____CONTROL_DE_CLICS_DERECHO_____  _");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // OBSERVACION_01: EMPEZARIA CON LA VALIDACION DEL CLIC DERECHO EN CASO NO SE CUMPLA UNA DE ESTAS DOS DECLARACIONES, SI EL NRO DE PAGINA ACTUAL ES MENOR O IGUAL A TRES Y LA CANTIDAD DE CLICS DERECHO ES IGUAL A UNO ENTONCES SE DEVOLVERA TRUE, PERO SI UNO DE LOS DOS NO SE CUMPLIERA ENTONCES DEVOLVERA FALSE Y AHI ENTRARIA A VALIDAR PORQUE OSINO ENTRARIA A CALCULAR ALGO QUE POR LOGIA YA SÉ  
            // OBSERVACION_02: EN LAS ANTERIORES VALIDACIONES YA SE RESETEA ESTOS VALORES O SE PUEDEN LLEGAR A SER MODIFICADAS, POR ESO EMPIEZO CON ESTA CONDICION, YA QUE DESDE ANTES YA PODRIA SER ARREGLADA 
            if((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)<=3 && CANT_CLICS_DERECHO==1) == false) {
                System.out.println("_     ____IF_____UNA_DE_LAS_DOS_CONDICIONES_NO_SE_CUMPLE____");
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= 3 ) { // SI ES MAYOR O IGUAL A 3, ENTONCES LA CANTIDAD DE CLICS ES DE 1, YA QUE SERIA LAS PRIMERA PAGINAS 
                    System.out.println("_       __IF__VALOR_DENTRO_DE_LO_CALCULADO_______");
                    CANT_CLICS_DERECHO = 1;
                } else { // SI EL NUMERO DE PAGINA A DEVOLVER ES DISTINTO A LAS PRIMERA TRES PAGINAS, ENTONCES AHI SI TENDRIA QUE HAYAR LA UBICACION DEL NRO DE PAGINA ACTUAL MIENTRAS HAGO UN CONTEO DE CLICS A TRAVES DE UN FOR POR LA CANTIDAD FINAL (TOTAL) DE BOTONES DE LA PAGINA 
                    System.out.println("_       __ELSE________IF_ELSE________");
                    int ctrl_cant_adecuada = CANT_CLICS_DERECHO * 3;
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_03:  :"+(ctrl_cant_adecuada));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_02:  :"+(ctrl_cant_adecuada-1));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_01:  :"+(ctrl_cant_adecuada-2));
                    if ((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= ctrl_cant_adecuada) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-1)) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-2)) 
                    ) { // SI EL BOTON DEL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES QUE SE ESTARIAN MOSTRANDO EN EL BLOQUE DE 3 BOTONES DE LA PAGINA, ENTONCES LA CANTIDAD DEL CLIC DERECHO ESTA BIEN, SI FUERA MENOR O MAYOR, ENTONCES LA CANTIDAD DE CLIC DERECHO NO ESTA CORRECTO Y TENDRIA QUE CALCULARLO 
                        System.out.println("_          _IF___CANTIDAD_CLICS_DERECHO_CORRECTA___BTNS_DENTRO_DEL_CALCULO___");
                    } else {
                        System.out.println("_          _ELSE__CALCULAR_CLIC_DERECHO_______");
                        System.out.println("_           _CANTIDAD_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                        int band_cant_clic_derechos = 1;
                        int cant_botones_mostrar = 3; // EL VALOR ES 3 PORQUE 3 BOTONES ES LA CANTIDAD MAXIMA A MOSTRAR POR PAGINA 
                        for (int i = 1; i <= cant_btn_lista_final; i++) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("_       _____FOR_____   _"+i+"__");
                            System.out.println("_       _PRIMER_BOTON_DEL_SIGUIEN_BLOQUE_DE_BOTONES:   :"+(cant_botones_mostrar+1));
                            if (i == (cant_botones_mostrar+1)) {
                                System.out.println("_       ___IF___SUM_BANDERA_CLIC_DERECHO_AND_CANT_BLOQUE_BTNS___");
                                band_cant_clic_derechos = band_cant_clic_derechos + 1; // LE SUMO UNO A LA CANTIDAD DE CLICS EN CASO DE QUE SEA IGUAL AL PRIMER BOTON DE LA SIGUIENTE FORMACION DE LOS 3 BOTONES PORQUE SE ENTENDERIA COMO UN CLIC MAS HACIA LA DERECHA 
                                cant_botones_mostrar = cant_botones_mostrar + 3; // LE SUMO 3 A LA CANTIDAD DE BOTONES QUE SE MUESTRAN PARA REPRESENTAR AL LIMITE DEL SIGUIEN CLIC DERECHO, EJEMPLO: PRIMERO SERIAN 3 Y SI FUERA 4, ENTONCES LE SUMO UN CLIC DERECHO Y 3 A ESTA VARIABLE Y EL PROXIMO LIMITE DE 3 BOTONES VISUALES SERIAN 6 Y AHI SI SERIA 7 ENTONCES VOLVERIA A SUMAR OTRO A LOS CLIC DERECHO Y 3 A LA CANTIDAD DE BOTONES, Y ASI HASTA LLEGAR A LA CANTIDAD FINAL DE BOTONES QUE HAY 
                            }
                            if (i == Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) { // CORTARIA EL FOR CUANDO LLEGUE A RECORRER AL BOTON ACTUAL A MOSTRAR, YA QUE NO QUIERO CALCULAR TODAS LAS CANTIDADES DE LOS BOTONES DERECHOS SINO LA CANTIDAD DE BOTONES DERECHOS QUE SE DIO PARA MOSTRAR AL BOTON ACTUAL 
                                System.out.println("_       ____BREAK_FOR____");
                                CANT_CLICS_DERECHO = band_cant_clic_derechos;
                                System.out.println("_       _-CANTIDAD_DE_CLICS_DERECHO_ENCONTRADA:    :"+CANT_CLICS_DERECHO);
                                break;
                            }
                        } // END FOR 
                    }
                } // END ELSE 
            } else {
                System.out.println("_     ____ELSE_____LAS_DOS_CONDICIONES__SE_CUMPLEN____");
            }
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                    listaFiltro.add(datos);
                }
                
                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+NRO_PAG_ACTUAL_MOSTRAR+")______");
                    break;
                }
                
                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_AGEN_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    /*
        METODO PARA FILTRAR CON PAGINACION LA PAGINA DE REPORTE DE MIS AGENDAMIENTOS 
    */
    public List filtrar_pagi_rpt_misAgen(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FILTRO, 
            String PARAM_TXT_FECHA_INI, 
            String PARAM_TXT_FECHA_FIN, 
            String PARAM_TXT_IDESPE, 
            String PARAM_TXT_IDMEDICO, 
            String PARAM_TXT_IDCLINICA, 
            String PARAM_IDPACIENTE, 
            String PARAM_IDPERFIL_USER, // VARIABLE QUE VOY A UTILIZAR PARA QUE CUANDO SEA PERFIL PACIENTE NO LE MUESTRE LOS AGEND ANULADOS -/- PUEDO RECUPERAR ESTE DATO DE LA SESION PERO PARA NO IR INSTANCIANDO EN CADA LUGAR ENTONCES APROVECHO QUE DESDE DONDE SE LLAME A ESTE METODO ESA VARIABLE YA VA A ESTAR INSTANCIADA 
            String PARAM_CBX_ESTADO // PARAMETRO PARA RECIBIR UN ESTADO QUE FILTRARE POR EL ESTADO DEL DETALLE 
    ) {
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        ModelPerfil metodosPerfil = new ModelPerfil();
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
//        PARAM_CBX_MOSTRAR = "1";
        System.out.println("___     ___________filtrar_pagi_rpt_misAgen()___________     ___");
        
//        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
//            System.out.println("_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "1";
//        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
//            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "2";
//        }
        
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_RPT_MIS_AGEN_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
        System.out.println("_   _CANTIDAD_INICIAL_ANTERIOR_DE_REGISTROS:    :"+cant_inicial_anterior);
        // CONTROLO SI LA CANTIDAD MINIMA INCIAL DE LINEAS DE REGISTRO ES IGUAL A LA CANTIDAD DE REGISTROS A MOSTRAR, SI SON IGUALES, ENTONCES NO SE CAMBIO Y SOLO QUIERE VER LOS DATOS DE OTRA PAGINA, PERO SI ES DIFERENTE, ENTONCES ES PORQUE SE CAMBIO LA CANTIDAD DE REGISTROS A MOSTRAR Y VOLVERIA A MOSTRAR DESDE LA PAGINA 1 Y NO DESDE LA QUE ESTA PORQUE LA CANTIDAD DE BOTONES VAN A CAMBIAR PORQUE SE VAN A VOLVER A CALCULAR 
        if (!(cant_inicial_anterior.equals(cant_min_fija))) { // SI NO SON IGUALES ENTONCES LE REINICIO LA PAGINA ACTUAL A MOSTRAR 
//            System.out.println("_   (*)__CANTIDAD_DE_REGISTROS_DISTINTA__");
            NRO_PAG_ACTUAL_MOSTRAR = "1";
            CANT_CLICS_DERECHO = 1;
        } else { // NO REINICIARIA LA PAGINA ACTUAL A MOSTRAR SI SON IGUALES LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES LE CARGO ESA PAGINA YA QUE ESTARIA MUDANDO DE PAGINA Y NO REORDENANDO LOS REGISTROS POR PAGINA 
//            System.out.println("_   (*)___ELSE____CANTIDAD_DE_REGISTROS_IGUALES________");
        }
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_CBX_MOSTRAR+"";
//        }
        
        String sqlFiltroTxt="";
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_FILTRO == null || PARAM_TXT_FILTRO.isEmpty() || PARAM_TXT_FILTRO.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ("
//                    + "UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR "
                + "UPPER(oa.IDAGENDAMIENTO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                ")";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_FECHA_INI.equals("") && PARAM_TXT_FECHA_FIN.equals("")) {
//            sqlFiltroTxt = "";
        } else {
            // CONTROLO PRIMERAMENTE SI AMBAS FECHAS ESTAN CARGADAS 
            if (!(PARAM_TXT_FECHA_INI.equals("")) && !(PARAM_TXT_FECHA_FIN.equals(""))) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (sqlFiltroTxt.equals("")) {
//                    sqlFiltroTxt = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                } else {
                    sqlFiltroTxt = sqlFiltroTxt + "AND (oad.FECHA_AGEN) >= '"+PARAM_TXT_FECHA_INI+"' AND (oad.FECHA_AGEN) <= '"+PARAM_TXT_FECHA_FIN+"' \n";
//                }
            } else { // SI NO ESTAN CARGADAS ENTONCES CONTROLO PARA VER SI UNO DE ELLAS NO ESTA CARGADA 
                if (!(PARAM_TXT_FECHA_INI.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (sqlFiltroTxt.equals("")) {
//                        sqlFiltroTxt = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
//                    } else {
                        sqlFiltroTxt = sqlFiltroTxt + "AND (oad.FECHA_AGEN) = '"+PARAM_TXT_FECHA_INI+"' \n";
//                    }
                }
                if (!(PARAM_TXT_FECHA_FIN.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (sqlFiltroTxt.equals("")) {
//                        sqlFiltroTxt = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                    } else {
                        sqlFiltroTxt = sqlFiltroTxt + "AND (oad.FECHA_AGEN) = '"+PARAM_TXT_FECHA_FIN+"' \n";
//                    }
                }
            }
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty() || PARAM_TXT_IDESPE.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDESPECIALIDAD) LIKE ('%"+PARAM_TXT_IDESPE+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDMEDICO == null || PARAM_TXT_IDMEDICO.isEmpty() || PARAM_TXT_IDMEDICO.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDMEDICO) LIKE ('%"+PARAM_TXT_IDMEDICO+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDCLINICA == null || PARAM_TXT_IDCLINICA.isEmpty() || PARAM_TXT_IDCLINICA.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDCLINICA) LIKE ('%"+PARAM_TXT_IDCLINICA+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_CBX_ESTADO == null || PARAM_CBX_ESTADO.isEmpty() || PARAM_CBX_ESTADO.equals("")) {
//            sqlFiltroTxt = "AND oa.ESTADO = 'A' \n"; // EN CASO DE QUE VENGA VACIO, ENTONCES LE FILTRO POR LAS CABECERAS ABIERTAS 
        } else {
            if (PARAM_CBX_ESTADO.equalsIgnoreCase("T") || PARAM_CBX_ESTADO.equalsIgnoreCase("TODOS")) {
                //
            } else {
                sqlFiltroTxt = sqlFiltroTxt + "AND UPPER(oad.ESTADO) = UPPER('"+PARAM_CBX_ESTADO+"') \n";
            }
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        // RECUPERO EL IDPERFIL DEL USUARIO Y VERIFICO QUE CLASE DE USUARIO ES Y DEPENDIENDO DE ESO AGREGO ALGO AL SELECT O NO 
//        String IDPERFIL = (String) sesionDatos.getAttribute("IDPERFIL");
        String VAR_IDPERFIL_ESTADO_VALI = "";
        if (metodosPerfil.isPerfilPaciente(PARAM_IDPERFIL_USER)==true) { // SI EL PERFIL ES PACIENTE ENTONCES NO LE MOSTRARE LAS CUENTAS ANULADAS 
            VAR_IDPERFIL_ESTADO_VALI = " AND oad.ESTADO NOT IN ('X') \n";
        }
        
        try {
            // OBSERVACION_SOBRE_PACIENTE: EL IDPACIENTE SIEMPRE SE VA A FILTRAR, SI ES PERFIL PACIENTE ENTONCES VOY A FILTRAR POR LOS AGENDAMIENTOS QUE TENGA EL PACIENTE DE LOGEO, PERO SI FUERA PERFIL ADMINISTRADOR O SECRETARIO, ENTONCES LE ACTIVO UN FILTRO POR PACIENTE PARA QUE PUEDAN VER LOS AGENDAMIENTOS DE LOS PACIENTES QUE QUIERAN 
            String sql = "SELECT oa.IDAGENDAMIENTO, oad.ITEM, oa.IDCLINICA, DATE_FORMAT(oad.HORA, '%d/%m/%Y %H:%i') AS FECHA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO, oad.ESTADO AS ESTADO_DET \n" +
                "FROM ope_agendamiento oa \n" + 
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO \n" + 
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA) \n" + 
                "WHERE oad.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n" + 
                "AND oa.ESTADO IN ('A', 'C') \n" + 
//                "AND oa.ESTADO = 'A' \n" + 
                ""+VAR_IDPERFIL_ESTADO_VALI+" \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oad.HORA DESC \n" //+
//                "ORDER BY oa.IDAGENDAMIENTO DESC \n" //+
//                ""+sqlFiltroCbx+" \n"
                    ;
            
//            String sql = "SELECT oph.IDPLANHORARIO, oph.IDCLINICA, oph.IDMEDICO, DATE_FORMAT(ophd.DESDE,'%H:%i') AS DESDE, \n" +
//                "DATE_FORMAT(ophd.HASTA,'%H:%i') AS HASTA, ophd.TURNO, ophd.DIA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oph.ESTADO \n" +
//                "FROM ope_plan_horario oph \n" +
//                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO \n" +
//                "JOIN rh_persona rp ON oph.IDMEDICO = rp.IDPERSONA \n" +
//                "WHERE oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                ""+sqlFiltroTxt+" \n" + 
//                ""+sqlFiltroMed+" \n" + 
//                "ORDER BY oph.IDPLANHORARIO DESC \n" + 
//                ""+sqlFiltroCbx+" \n";
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO \n" + 
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA) \n" + 
                "WHERE oad.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n" + 
                "AND oa.ESTADO IN ('A', 'C') \n" + 
//                "AND oa.ESTADO = 'A' \n" + 
                ""+VAR_IDPERFIL_ESTADO_VALI+" \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oad.HORA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
//                "ORDER BY oa.IDAGENDAMIENTO DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("---------------------MODEL_AGENDAMIENTO------------------------");
            System.out.println("-- --filtrar_pagi_rpt_misAgen()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                NRO_PAG_ACTUAL_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
    //            System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
    //                System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                }
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    NRO_PAG_ACTUAL_MOSTRAR = "1";
                    CANT_CLICS_DERECHO = 1;
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                }
            }
            // --------------------------------------------------------------------------------------------------------
            
            // --------------------------------------------------------------------------------------------------------
            // OBSERVACION: YA QUE LA NUMERACION DE LA PAGINA DONDE SE ENCONTRABA EL USUARIO NO SE ENCUENTRA EN EXISTENCIA EN LA NUEVA CANTIDAD DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHOS DEBO DE RESETEAR 
            // VALIDACION PARA REESTABLECER LA CANTIDAD DE CLICS DERECHOS, PARA QUE SI POR EJEMPLO ESTABA EN EL BOTON NRO 4 Y REORDENA LA CANTIDAD DE REGISTROS A VISUALIZAR O AÑADE UN NUEVO FILTRO Y EL BOTON TOTAL SEA 2, COMO NO EXISTE MAS EL 4 EN ESA CLASIFICACION, ENTONCES LE DEVUELVA A LA PRIMERA PAGINA Y EL CLIC DERECHO VOLVERIA A SER 1 
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_  ____CONTROL_DE_CLICS_DERECHO_____  _");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // OBSERVACION_01: EMPEZARIA CON LA VALIDACION DEL CLIC DERECHO EN CASO NO SE CUMPLA UNA DE ESTAS DOS DECLARACIONES, SI EL NRO DE PAGINA ACTUAL ES MENOR O IGUAL A TRES Y LA CANTIDAD DE CLICS DERECHO ES IGUAL A UNO ENTONCES SE DEVOLVERA TRUE, PERO SI UNO DE LOS DOS NO SE CUMPLIERA ENTONCES DEVOLVERA FALSE Y AHI ENTRARIA A VALIDAR PORQUE OSINO ENTRARIA A CALCULAR ALGO QUE POR LOGIA YA SÉ  
            // OBSERVACION_02: EN LAS ANTERIORES VALIDACIONES YA SE RESETEA ESTOS VALORES O SE PUEDEN LLEGAR A SER MODIFICADAS, POR ESO EMPIEZO CON ESTA CONDICION, YA QUE DESDE ANTES YA PODRIA SER ARREGLADA 
            if((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)<=3 && CANT_CLICS_DERECHO==1) == false) {
                System.out.println("_     ____IF_____UNA_DE_LAS_DOS_CONDICIONES_NO_SE_CUMPLE____");
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= 3 ) { // SI ES MAYOR O IGUAL A 3, ENTONCES LA CANTIDAD DE CLICS ES DE 1, YA QUE SERIA LAS PRIMERA PAGINAS 
                    System.out.println("_       __IF__VALOR_DENTRO_DE_LO_CALCULADO_______");
                    CANT_CLICS_DERECHO = 1;
                } else { // SI EL NUMERO DE PAGINA A DEVOLVER ES DISTINTO A LAS PRIMERA TRES PAGINAS, ENTONCES AHI SI TENDRIA QUE HAYAR LA UBICACION DEL NRO DE PAGINA ACTUAL MIENTRAS HAGO UN CONTEO DE CLICS A TRAVES DE UN FOR POR LA CANTIDAD FINAL (TOTAL) DE BOTONES DE LA PAGINA 
                    System.out.println("_       __ELSE________IF_ELSE________");
                    int ctrl_cant_adecuada = CANT_CLICS_DERECHO * 3;
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_03:  :"+(ctrl_cant_adecuada));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_02:  :"+(ctrl_cant_adecuada-1));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_01:  :"+(ctrl_cant_adecuada-2));
                    if ((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= ctrl_cant_adecuada) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-1)) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-2)) 
                    ) { // SI EL BOTON DEL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES QUE SE ESTARIAN MOSTRANDO EN EL BLOQUE DE 3 BOTONES DE LA PAGINA, ENTONCES LA CANTIDAD DEL CLIC DERECHO ESTA BIEN, SI FUERA MENOR O MAYOR, ENTONCES LA CANTIDAD DE CLIC DERECHO NO ESTA CORRECTO Y TENDRIA QUE CALCULARLO 
                        System.out.println("_          _IF___CANTIDAD_CLICS_DERECHO_CORRECTA___BTNS_DENTRO_DEL_CALCULO___");
                    } else {
                        System.out.println("_          _ELSE__CALCULAR_CLIC_DERECHO_______");
                        System.out.println("_           _CANTIDAD_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                        int band_cant_clic_derechos = 1;
                        int cant_botones_mostrar = 3; // EL VALOR ES 3 PORQUE 3 BOTONES ES LA CANTIDAD MAXIMA A MOSTRAR POR PAGINA 
                        for (int i = 1; i <= cant_btn_lista_final; i++) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("_       _____FOR_____   _"+i+"__");
                            System.out.println("_       _PRIMER_BOTON_DEL_SIGUIEN_BLOQUE_DE_BOTONES:   :"+(cant_botones_mostrar+1));
                            if (i == (cant_botones_mostrar+1)) {
                                System.out.println("_       ___IF___SUM_BANDERA_CLIC_DERECHO_AND_CANT_BLOQUE_BTNS___");
                                band_cant_clic_derechos = band_cant_clic_derechos + 1; // LE SUMO UNO A LA CANTIDAD DE CLICS EN CASO DE QUE SEA IGUAL AL PRIMER BOTON DE LA SIGUIENTE FORMACION DE LOS 3 BOTONES PORQUE SE ENTENDERIA COMO UN CLIC MAS HACIA LA DERECHA 
                                cant_botones_mostrar = cant_botones_mostrar + 3; // LE SUMO 3 A LA CANTIDAD DE BOTONES QUE SE MUESTRAN PARA REPRESENTAR AL LIMITE DEL SIGUIEN CLIC DERECHO, EJEMPLO: PRIMERO SERIAN 3 Y SI FUERA 4, ENTONCES LE SUMO UN CLIC DERECHO Y 3 A ESTA VARIABLE Y EL PROXIMO LIMITE DE 3 BOTONES VISUALES SERIAN 6 Y AHI SI SERIA 7 ENTONCES VOLVERIA A SUMAR OTRO A LOS CLIC DERECHO Y 3 A LA CANTIDAD DE BOTONES, Y ASI HASTA LLEGAR A LA CANTIDAD FINAL DE BOTONES QUE HAY 
                            }
                            if (i == Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) { // CORTARIA EL FOR CUANDO LLEGUE A RECORRER AL BOTON ACTUAL A MOSTRAR, YA QUE NO QUIERO CALCULAR TODAS LAS CANTIDADES DE LOS BOTONES DERECHOS SINO LA CANTIDAD DE BOTONES DERECHOS QUE SE DIO PARA MOSTRAR AL BOTON ACTUAL 
                                System.out.println("_       ____BREAK_FOR____");
                                CANT_CLICS_DERECHO = band_cant_clic_derechos;
                                System.out.println("_       _-CANTIDAD_DE_CLICS_DERECHO_ENCONTRADA:    :"+CANT_CLICS_DERECHO);
                                break;
                            }
                        } // END FOR 
                    }
                } // END ELSE 
            } else {
                System.out.println("_     ____ELSE_____LAS_DOS_CONDICIONES__SE_CUMPLEN____");
            }
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA"));
                        datos.setOAD_HORA(MA_RESULTADO.getString("FECHA"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                        datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    listaFiltro.add(datos);
                }
                
                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+NRO_PAG_ACTUAL_MOSTRAR+")______");
                    break;
                }
                
                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_MIS_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA VALIDAR QUE LA FECHA DE AGENDAMIENTO QUE SE SELECCIONO NO SEA MENOR A LA FECHA DE HOY, PORQUE NO SE PUEDE AGENDAR UN DIA PASADO, EN TODO CASO EL MISMO DIA SI PUEDE 
    */
    public boolean ctrlFechaAgenDia(ModelInicioSesion modelIniSes, String PARAM_FECHA_AGEN) {
        boolean valor = false; // true : la fecha_agen es menor a la fecha de hoy 
        
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_______  ---- __ctrlFechaAgenDia()__ ----  _______");
        System.out.println("_          __PARAM_FECHA_AGEN:  :"+PARAM_FECHA_AGEN);
        String FEC_AGEN_NEW = modelIniSes.convertirFechaYMD(PARAM_FECHA_AGEN);
        System.out.println("_           _   _NEW_FORMATO:   :"+FEC_AGEN_NEW);
        
        String FECHA_HOY = modelIniSes.traerFechaHoy();
        System.out.println("_          __FECHA_HOY:   :"+FECHA_HOY);
        System.out.println(".");System.out.println(".");
        
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        try {
            date1 = sdformat.parse(FEC_AGEN_NEW);
        } catch (ParseException ex) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date date2 = null;
        try {
            date2 = sdformat.parse(FECHA_HOY);
        } catch (ParseException ex) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Date-1 - FECHA_AGE:   :" + sdformat.format(date1));
        System.out.println("Date-2 - FECHA_HOY:   :" + sdformat.format(date2));
        System.out.println(".");System.out.println(".");
        
        if(date1.compareTo(date2) > 0) {
            System.out.println("Date-1 es mayor Date-2");
            valor = false;
        } else if(date1.compareTo(date2) < 0) {
            System.out.println("Date-1 es menor Date-2");
            valor = true;
        } else if(date1.compareTo(date2) == 0) {
            System.out.println("Date-1 es igual a Date-2");
            valor = false;
        }
        System.out.println(".");System.out.println(".");
        return valor;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA OBTENER DATOS PARA MOSTRAR EN UN MENSAJE CUANDO SE ACTIVA LA VALIDACION 
    */
    public void obtenerHorarioDiaMed(HttpSession PARAM_SESION, ModelInicioSesion metodosIniSes, String PARAM_IDMEDICO, String PARAM_FECHA, String PARAM_IDCLINICA) {
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_    _________ obtenerHorarioDiaMed() _________    _");
        String VALI_DESDE="", VALI_HASTA="";
        String PARAM_FEC_DIA = devolverDia(metodosIniSes, PARAM_FECHA);
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        try {
            String sql = "SELECT ophd.IDPLANHORARIO, ophd.TURNO, ophd.DIA, DATE_FORMAT(ophd.DESDE, '%H:%i') AS DESDE, DATE_FORMAT(ophd.HASTA, '%H:%i') AS HASTA \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO \n" +
                "WHERE oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND oph.IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
//                "AND oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.ESTADO = 'A' \n" +
                "AND ophd.DIA = '"+PARAM_FEC_DIA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---obtenerHorarioDiaMed()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            if (MA_RESULTADO.getRow() > 0 || MA_RESULTADO.next() == true) {
                System.out.println(".");
                System.out.println(".");
                VALI_DESDE = MA_RESULTADO.getString("DESDE");
                System.out.println("_   __DESDE:   :"+VALI_DESDE);
                VALI_HASTA = MA_RESULTADO.getString("HASTA");
                System.out.println("_   __HASTA:   :"+VALI_HASTA);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        // CARGO EN LA SESION LAS VARIABLES PARA ASI DESDE EL CONTROLADOR RECUPERAR ESTOS DATOS Y PODER CARGAR EN EL MENSAJE 
        PARAM_SESION.setAttribute("VALI_PARAM_DIAS", PARAM_FEC_DIA);
        PARAM_SESION.setAttribute("VALI_PARAM_DESDE", VALI_DESDE);
        PARAM_SESION.setAttribute("VALI_PARAM_HASTA", VALI_HASTA);
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA PODER OBTENER LOS DIAS DE ATENCION DEL MEDICO PARA PODER MOSTRARLE AL USUARIO COMO MENSAJE QUE CARGO EN EL CONTROLADOR AL MOMENTO DE QUE AGENDARSE 
    */
    public void obtenerDiasAteMed(HttpSession PARAM_SESION, String PARAM_IDMEDICO, String PARAM_IDCLINICA) {
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("_    _________ obtenerDiasAteMed() _________    _");
        String VALI_DIAS="";
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        try {
            String sql = "SELECT ophd.IDPLANHORARIO, ophd.TURNO, ophd.DIA, DATE_FORMAT(ophd.DESDE, '%H:%i') AS DESDE, DATE_FORMAT(ophd.HASTA, '%H:%i') AS HASTA \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO \n" +
                "WHERE oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND oph.IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
//                "AND oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---obtenerDiasAteMed()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            System.out.println(".");
            System.out.println(".");
            System.out.println("_   _ROW_RESULT:    :"+MA_RESULTADO.getRow());
            ResultSet resultado2 = MA_RESULTADO;
            int nroVueltas = 1;
            if (MA_RESULTADO.getRow() > 0 || MA_RESULTADO.next() == true) {
                while(resultado2.next() == true) {
                    System.out.println(".");
                    System.out.println(".       WHILE       .");
                    System.out.println(".");
                    if (nroVueltas == 1) {
                        VALI_DIAS =  MA_RESULTADO.getString("DIA");
                        nroVueltas = nroVueltas + 1; // PARA QUE SI VUELVA A ENTRAR EN EL WHILE ENTRE EN EL ELSE Y CONCATENE LOS DIAS 
                    } else {
                        VALI_DIAS = VALI_DIAS + ", " + MA_RESULTADO.getString("DIA");
                    }
                    System.out.println("_   __DIAS:   :"+VALI_DIAS);
                }
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        // CARGO EN LA SESION LAS VARIABLES PARA ASI DESDE EL CONTROLADOR RECUPERAR ESTOS DATOS Y PODER CARGAR EN EL MENSAJE 
        PARAM_SESION.setAttribute("VALI_PARAM_DIAS", VALI_DIAS);
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA CONTROLAR SI EL DIA DE LA FECHA QUE SELECCIONO EL USUARIO PARA AGENDARSE ES UN DIA QUE SE ENCUENTRA DENTRO DEL PLAN HORARIO DEL MEDICO 
    */
    public String ctrlDiaSelecMedDisp(HttpSession PARAM_SESION, String PARAM_IDMEDICO, String PARAM_DIA) {
        String valor = "No se encuentra disponible el médico los días "+PARAM_DIA+"."; // SI NO SE ENCUENTRA RESULTADO ENTONCES LE PASO A LA VARIABLE QUE ESE DIA NO SE ENCUENTRA DISPONIBLE EL MEDICO Y SI SE ENCUENTRA RESULTADO, PUEDO MOSTRARLE UN MENSAJE DICIENDOLE QUE SE ENCUENTRA DISPONIBLE PERO POR EL MOMENTO NO LE MUESTRO NADA SUPONIENDO QUE SE ENCUENTRA DISPONIBLE 
        try {
            VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
            
            String sql = "SELECT ophd.IDPLANHORARIO, ophd.TURNO, ophd.DIA, DATE_FORMAT(ophd.DESDE, '%H:%i') AS DESDE, DATE_FORMAT(ophd.HASTA, '%H:%i') AS HASTA \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO AND ophd.ESTADO = 'A' \n" +
                "WHERE oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
//                "AND oph.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.DIA = '"+PARAM_DIA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---ctrlDiaSelecMedDisp()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            System.out.println(".");
            System.out.println(".");
            System.out.println("_   _ROW_RESULT:    :"+MA_RESULTADO.getRow());
            
            if (MA_RESULTADO.getRow() > 0 || MA_RESULTADO.next() == true) {
                valor = "";
                String dia =  MA_RESULTADO.getString("DIA");
                System.out.println("_   __DIAS:   :"+dia);
                String desde =  MA_RESULTADO.getString("DESDE");
                System.out.println("_   __DESDE:   :"+desde);
                String hasta =  MA_RESULTADO.getString("HASTA");
                System.out.println("_   __HASTA:   :"+hasta);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    // METODO QUE UTILIZO PARA PODER CARGAR COMBOBOX DE CLINICA (EN LA PAGINA DE AGENDAMIENTO POR EJEMPLO) DE ACUERDO AL PLAN HORARIO QUE TENGA EL MEDICO (LAS CLINICAS QUE TENGA EL MEDICO) 
    public Map<String, String> cargarCbxClinicaPHMed(ModelInicioSesion modeloIniSes, String PARAM_IDCLINICA, String PARAM_IDMEDICO, String PARAM_FECHA) {
        Map<String, String> mapaClinica = new LinkedHashMap<>();
        ModelRef_Clinica metodosClinica = new ModelRef_Clinica();
        
        String PARAM_DESC_CLINICA;
        if (PARAM_IDCLINICA == null || PARAM_IDCLINICA.isEmpty() || PARAM_IDCLINICA.equals("")) { // EN CASO DE QUE ESTE VACIO, ENTONCES ES PORQUE SE ESTA CARGANDO UN NUEVO REGISTRO Y ASI LE MUESTRO PRIMERO EL NO/DEFINIDO 
            PARAM_IDCLINICA = "";
//            PARAM_DESC_CLINICA = "(NO/DEFINIDO)";
        } else { // SI NO VIENE VACIO ENTONCES SI RECUPERO LA DESCRIPCION Y LA INGRESO COMO PRIMERA FILA A LA CLINICA EN CASO DE QUE EL PARAMETRO NO VENGA VACIO 
            PARAM_DESC_CLINICA = metodosClinica.traerDescClinica(PARAM_IDCLINICA);
            mapaClinica.put(PARAM_IDCLINICA, PARAM_DESC_CLINICA); // CARGO LA PRIMERA LINEA DEL COMBO CON LO QUE SELECCIONO EL USUARIO EN CASO DE QUE SE TRAIGA UN PRODUCTO Y SI ES UN NUEVO REGISTRO ENTONCES VA A CARGAR EL NO DEFINIDO 
        }
        
        try { // LUEGO DE ESO CARGARIA LAS DEMAS MARCAS EXCLUYENDO EL QUE YA SE CARGO 
            String FEC_AGEN_DIA = devolverDia(modeloIniSes, PARAM_FECHA);
            String sql = "SELECT oph.IDPLANHORARIO, oph.IDCLINICA, cli.DESC_CLINICA, ophd.DIA, ophd.DESDE, ophd.HASTA  \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_clinica cli ON oph.IDCLINICA = cli.IDCLINICA \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO AND ophd.ESTADO = 'A' \n" +
                "WHERE oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.DIA = '"+FEC_AGEN_DIA+"' \n" +
                "ORDER BY cli.DESC_CLINICA ASC \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---cargarCbxClinicaPHMed()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next()) {
                String idclinica = MA_RESULTADO.getString("IDCLINICA");
                String desc_clinica = MA_RESULTADO.getString("DESC_CLINICA");
                mapaClinica.put(idclinica, desc_clinica);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaClinica;
    } // END METHOD 
    
    
    
    // METODO QUE UTILIZO PARA DEVOLVER LA CANTIDAD DE CLINICAS QUE HAY DISPONIBLES EL DIA QUE SELECCIONO EL USUARIO PARA AGENDARSE Y ASI MOSTRARLE LAS CLINICAS PARA QUE PUEDA SELECCIONAR DONDE QUIERA AGENDARSE 
    public String cantidadClinicaPHMed(HttpSession PARAM_SESION, String PARAM_IDMEDICO, String PARAM_DIA) {
        String valor = "0";
        String IDCLINICA="", DESC_CLINICA="";
        try {
            String sql = "SELECT oph.IDPLANHORARIO, oph.IDCLINICA, cli.DESC_CLINICA, COUNT(oph.IDCLINICA) AS CANT_CLINICA \n" +
                "FROM ope_plan_horario oph \n" +
                "JOIN ope_clinica cli ON oph.IDCLINICA = cli.IDCLINICA \n" +
                "JOIN ope_plan_horario_det ophd ON oph.IDPLANHORARIO = ophd.IDPLANHORARIO AND ophd.ESTADO = 'A' \n" +
                "WHERE oph.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                "AND oph.ESTADO = 'A' \n" +
                "AND ophd.DIA = '"+PARAM_DIA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---cantidadClinicaPHMed()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next()) {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                valor = MA_RESULTADO.getString("CANT_CLINICA");
                System.out.println("_   _CANTIDAD_CLINICAS:    :"+valor);
                // SI LA CANTIDAD DE CLINICAS QUE TIENE DISPONIBLE PARA AGENDAR ESE DIA ES IGUAL A UNO, ENTONCES EN LA PAGINA NO LE VOY A MOSTRAR EL COMBO YA QUE NO HAY MAS DE UNA CLINICA PARA ELEGIR, EN ESE CASO LE CARGARIA NOMAS YA LA CLINICA Y SU ID A LA SESION PARA NO CREAR OTRO METODO DONDE RECUPERE ESTOS DATOS PARA MOSTRAR EN EL JSP 
                if (Integer.parseInt(valor) == 1) {
                    System.out.println("_   __IF____UNA_CLINICA____");
                    IDCLINICA = MA_RESULTADO.getString("IDCLINICA");
                    System.out.println("_   _   _ID_CLI:   :"+IDCLINICA);
                    DESC_CLINICA = MA_RESULTADO.getString("DESC_CLINICA");
                    System.out.println("_   _   _DESC_CLI: :"+DESC_CLINICA);
                    PARAM_SESION.setAttribute("CA_AGEN_IDCLINICA", IDCLINICA);
                    PARAM_SESION.setAttribute("CA_AGEN_DESC_CLINICA", DESC_CLINICA);
                }
            }
            // EN CASO DE QUE LA CANTIDAD DE CLINICAS SEA CERO O MAYOR A UNO, ENTONCES LE LIMPIO LOS CAMPOS DE LA SESION YA QUE HABRIA VARIAS CLINICAS PARA ELEGIR Y LE MOSTRARIA EL COMBO PARA QUE SELECCIONE Y EN ESE CASO EN LA ACCION DEL EVENTO EN EL CONTROLADOR LE CARGARIA ESTOS DATOS Y LIMPIO LOS CAMPOS DE LA SESION PARA NO MANTENER DATOS ANTERIORES 
            if (Integer.parseInt(valor) == 0 || Integer.parseInt(valor) > 1) {
                PARAM_SESION.setAttribute("CA_AGEN_IDCLINICA", "");
                PARAM_SESION.setAttribute("CA_AGEN_DESC_CLINICA", "");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // END METHOD 
    
    
    
    // METODO PARA CARGAR LOS AGENDAMIENTOS ACTIVAS PARA LA PAGINA DE ANULAR AGENDAMIENTO
    public List cargar_grilla_anular_agen(HttpSession PARAM_SESION, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            , String PARAM_IDPACIENTE // PARAMETRO QUE UTILIZO PARA CARGAR EL IDPACIENTE CUANDO EL PERFIL SEA PACIENTE, YA QUE ESTE METODO UTILIZO EN VARIOS LUGARES, Y LO DIVIDO CON ESTA VARIABLE, SI ESTA CARGADO ENTONCES EL PERFIL ES DE PACIENTE, Y SI ESTA VACIO, ENTONCES EL PERFIL PUEDE SER ADMIN, SECRETARIO O MEDICO 
        ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion_anular_agen()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanAgendamiento> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (PARAM_SESION.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(PARAM_SESION.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
//        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
//        String cant_resul = "131";
//        System.out.println("_   __CANTIDAD_DE_RESULTADOS:   :"+cant_resul);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_NRO_REG_MOSTRAR+"";
//        }
        
        String vali_paciente = "";
        // VALIDACION PARA CONTROLAR SI ES QUE EL PARAMETRO DE PACIENTE ESTA CARGADO O NO, SI SE ENCUENTRA VACIO ENTONCES EL QUE INGRESO A LA PAGINA ES UNA PERSONA CON PERFIL DE SECRETARIO Y ADMINISTRADOR Y EN ESTE CASO LE MOSTRARE TODOS LOS AGENDAMIENTOS DE TODOS LOS PACIENTES (HASTA QUE EL QUIERA FILTRAR POR EL DE UN PACIENTE EN ESPECIFICO) 
        if (PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//            vali_paciente = "";
        } else { // Y EN CASO DE QUE ESTE CARGO EL PARAMETRO DE IDPACIENTE ENTONCES ESO QUIERE DECIR QUE LA PERSONA LOGEADA TIENE UN PERFIL DE PACIENTE, Y LE TENGO QUE MOSTRAR SOLO SUS AGENDAMIENTOS Y NO LA DE OTROS PACIENTES PARA QUE NO PUEDA ANULARLAS O VER ESOS DATOS 
            vali_paciente = "AND oad.IDPACIENTE = '"+PARAM_IDPACIENTE+"' ";
        }
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT oa.IDAGENDAMIENTO, oad.ITEM, oa.IDCLINICA, DATE_FORMAT(oad.HORA, '%d/%m/%Y %H:%i') AS FECHA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO, oad.IDPACIENTE, oad.ESTADO AS ESTADO_DET \n" +
                "FROM ope_agendamiento oa \n" +
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO AND oad.ESTADO <> 'X' \n" +
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA) \n" + // CREO QUE NO ES NECESARIO ESPECIFICAR QUE EL DATO QUE SE RECUPERE DE LA TABLA PERSONA SEA DE UN IDCATEGORIA_PERSONA 4 (PACIENTE) 
//                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 4) \n" +
                "WHERE oa.ESTADO NOT IN ('X') \n" + 
                "AND oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "WHERE oa.ESTADO = 'P' \n" + // AGENDAMIENTO CON ESTADO PENDIENTE 
                ""+vali_paciente+" \n" + 
                "ORDER BY oa.IDAGENDAMIENTO DESC, oad.ITEM DESC \n" //+
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO AND oad.ESTADO <> 'X' \n" +
                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA) \n" + // CREO QUE NO ES NECESARIO ESPECIFICAR QUE EL DATO QUE SE RECUPERE DE LA TABLA PERSONA SEA DE UN IDCATEGORIA_PERSONA 4 (PACIENTE) 
//                "JOIN rh_persona rp ON (oad.IDPACIENTE = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 4) \n" +
                "WHERE oa.ESTADO NOT IN ('X') \n" + 
                "AND oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "WHERE oa.ESTADO = 'P' \n" + // AGENDAMIENTO CON ESTADO PENDIENTE 
                ""+vali_paciente+" \n" + 
                "ORDER BY oa.IDAGENDAMIENTO DESC, oad.ITEM DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---cargar_grilla_anular_agen()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                PARAM_NRO_PAG_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".   __________ELSE___________");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                System.out.println(".   __  __CANT_RESULTADO:  :"+cant_resultado);
                System.out.println(".   __  __NRO_REG_MOSTRAR: :"+PARAM_NRO_REG_MOSTRAR);
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
                System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
                System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    System.out.println("_____________IF_____________");
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
                    System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                } else {
                    System.out.println("_____________ELSE_____________");
                }
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                System.out.println(".");
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println(".");
                System.out.println(".   IF ( > ) ");
                System.out.println("_   _PARAM_NRO_PAG_MOSTRAR:  :"+PARAM_NRO_PAG_MOSTRAR);
                System.out.println("_   _cant_btns_lista_final:  :"+cant_btn_lista_final);
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    PARAM_NRO_PAG_MOSTRAR = "1";
                    CANT_CLICS_DERECHO = 1; 
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                    System.out.println("---------------------_______CONTROL_DE_LA_CANTIDAD_DE_CLIC_DERECHO__Y__LA_PAGINA_ACTUAL_A_MOSTRAR______--------------------------------");
                    // BLOQUE DE CODIGO PARA CORREGIR EL CLIC DERECHO EN CASO DE QUE LA CANTIDAD DE BOTONES EXISTA PERO EL NRO DE PAG ACTUAL A MOSTRAR NO SE ENCUENTRE DENTRO DEL BLOQUE DE BOTONES DE 3 QUE DEVUELVE EL CLIC DERECHO ------------------------------
                    // SI LA CANTIDAD DE CLICS DERECHO FUERA 2, LO MULTIPLICO POR 3 (PORQUE ES LA CANTIDAD DE BOTONES A MOSTRAR) Y AHI TENDRIA EL TERCER BOTON DE PAGINACION Y RESTANDO UNO Y DOS VALORES TENDRIAMOS LOS OTROS DOS BOTONES 
                    // LO IMPORTANTE DE ESTO ES QUE LA PAGINA ACTUAL TENDRIA QUE ESTAR DENTRO DE ESTE RANGO, SINO SE ENCUENTRA ENTRE NINGUNA DE LAS TRES POSIBLIDADES DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHO NO COINCIDE Y LO REINICIARIA 
                    int btn_3 = (CANT_CLICS_DERECHO*3);
                    int btn_2 = btn_3-1;
                    int btn_1 = btn_3-2;
                    System.out.println("----__1__----------------");
                    System.out.println("_   _nro_pag: :"+PARAM_NRO_PAG_MOSTRAR);
                    System.out.println("-------------------------");
                    System.out.println("_   _btn_3:  :"+btn_3);
                    System.out.println("_   _btn_2:  :"+btn_2);
                    System.out.println("_   _btn_1:  :"+btn_1);
                    System.out.println("-------------------------");
                    if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_3
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_2
                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_1
                    ) { // SI EL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES, SIGNIFICA QUE ESTA TODO BIEN Y LA CANTIDAD DE CLIC DERECHO ES CORRECTA PUES EL NRO DE PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DEL RANGO 
                        System.out.println("____IF______EL_NRO_DE_PAG_ACTUAL_ES_IGUAL_A_UNO_DE_LOS_TRES_BOTONES_______________");
                    } else { // SI EL NRO DE PAG NO COINCIDE ES PORQUE LA CANTIDAD DE CLIC DERECHO ES INCORRECTO PUES LAS OPCIONES DE BOTONES QUE MUESTRA NO ES IGUAL AL BOTON QUE SE QUIERE MOSTRAR 
                        System.out.println("____ELSE______EL_NRO_DE_PAG_NO_ES_IGUAL_A_NINGUNO_DE_LOS_TRES_BOTONES_______CANT_CLIC_DERECHO_ERRONEO________");
                        // 1- PRIMERA CONDICIONAL PARA VERIFICAR SI EL NRO DE PAG ACTUAL A MOSTRAR ES MAYOR AL TERCER BOTON -
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > btn_3) { // SI FUERA MAYOR ENTONCES HAY QUE SUMARLE UNO A LA CANTIDAD DE CLICS DERECHO PARA VER SI DENTRO DE LOS NUEVOS 3 BOTONES SE ENCUENTRA LA PAG A MOSTRAR 
                            System.out.println("_   _1_____ES_MAYOR_AL_BTN_3_____");
                            // BANDERA QUE ME DIRA SI ES QUE SE ENCONTRO LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                            int BAND_CORRECTO = 0;
                            // HAGO UN FOR CON UN MINIMO DE 5 VUELTAS, QUE VENDRIA A SER LOS INTENTOS QUE HARE PARA ENCONTRAR LA CANTIDAD DE CLICS DERECHOS CORRECTA DONDE LA PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DE LOS TRES BOTONES DE ESE CLIC DERECHO 
                            for (int i = 0; i < 5; i++) {
                                CANT_CLICS_DERECHO = CANT_CLICS_DERECHO + 1;
                                if (metodosIniSes.control_pagActualBotonera(Integer.parseInt(PARAM_NRO_PAG_MOSTRAR), CANT_CLICS_DERECHO) == true) {
                                    System.out.println("__FOR_("+i+")______IF_________");
                                    BAND_CORRECTO = 1; // LE CAMBIO SU VALOR PARA DECIRLE A LA PROXIMA CONDICIONAL QUE SI SE ENCONTRO A LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
                                    break; // cortaria el for para no continuar buscando y devolver la cantidad de clics derecho erronea por continuar con el for en caso de a la primera o segunda encontrar a la pagina actual 
                                } else {
                                    System.out.println("__FOR_("+i+")_____ else ________");
                                }
                            } // end for 
                            System.out.println("_   __BANDERA_CORRECTO_CLIC_DERECHO_FOR:  :"+BAND_CORRECTO);
                            // si al finalizar el for no se encontro a la cantidad de clics derecho que devuelva uno de los botones que pertenece a la pagina actual a mostrar, entonces reestableceria ambos valores para no tenerlos erroneo 
                            if (BAND_CORRECTO == 0) { // NO HAY PROBLEMA EN REINICIAR ESTOS VALORES, PORQUE SI ENTRA EN ESTE IF ES PORQUE LA PAGINA ACTUAL ES MAYOR AL BTN 3 Y ENTONCES ESO QUIERE DECIR QUE NO ENTRARIA EN LA SIGUIENTE CONDICIONAL 
                                System.out.println(".");
                                System.out.println(".   _____BANDERA_NO FUE ACTIVADA____ REINICIO LOS VALORES __________");
                                System.out.println(".");
                                PARAM_NRO_PAG_MOSTRAR = "1";
                                CANT_CLICS_DERECHO = 1;
                            }
                        }
                        // 2- SEGUNDA CONDICIONAL PARA VERIFICAR SI LA PAGINA ACTUAL SE ENCUENTRA POR DEBAJO DEL PRIMER BOTON / PODRIA RESTARLE UNO A LA CANTIDAD DE CLICS DERECHOS Y PREGUNTAR POR LOS 3 BOTONES PERO LO VEO INNECESARIO Y LO REESTABLECERIA PARA MOSTRAR LOS PRIMERO 3 BOTONES DE LA PAGINA 
                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) < btn_1) {
                            System.out.println("_   _2_____ES_MENOR_AL_BTN_1_____");
                            CANT_CLICS_DERECHO = 1;
                        }
                    }
                    System.out.println("--------------------------------------______END_CONTROL_DE_LAS_DOS_VARIABLES_____-----------------------------------------------------------------------");
                }
            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".   _nro_pag_mostrar:     :"+PARAM_NRO_PAG_MOSTRAR);
            System.out.println(".   _cant_clics_derecho:  :"+CANT_CLICS_DERECHO);
            System.out.println(".");
            System.out.println(".   ___end___inicio del while-----");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA"));
                        datos.setOAD_HORA(MA_RESULTADO.getString("FECHA"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOAD_IDPACIENTE(MA_RESULTADO.getString("IDPACIENTE"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                        datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    lista_mostrar.add(datos);
                }

                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(PARAM_NRO_PAG_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+PARAM_NRO_PAG_MOSTRAR+")______");
                    break;
                }

                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end for or while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        PARAM_SESION.setAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        PARAM_SESION.setAttribute("PAG_PC_ANU_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        PARAM_SESION.setAttribute("PAG_PC_ANU_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        PARAM_SESION.setAttribute("PAG_PC_ANU_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        PARAM_SESION.setAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_________DATOS_FINALES____________");
//        System.out.println("_  __CANTIDAD_MIN_MOSTRAR:  :"+cant_min);
////        System.out.println("_  __CANTIDAD_DE_RESUL:     :"+cant_resul);
//        System.out.println("_  __CANTIDAD_INI_DE_LISTA: :"+cant_btn_lista);
//        System.out.println("__________________________________");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
        
        return lista_mostrar;
    }
    
    
    
    public List filtrar_anu_agen(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FILTRO, 
            String PARAM_TXT_IDMED, 
            String PARAM_TXT_IDESPE, 
            String PARAM_TXT_FECHA_INI, 
            String PARAM_TXT_FECHA_FIN, 
            String PARAM_IDPACIENTE,
            String PARAM_IDCLINICA
    ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("___     ___________filtrar_anu_agen()___________     ___");
        List<BeanAgendamiento> listaFiltro = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
//        if (PARAM_CBX_MOSTRAR.equals("10")) { // borrar despues
//            System.out.println("_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "1";
//        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
//            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "2";
//        }
        
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (sesionDatos.getAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_PC_ANU_AGEN_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
        System.out.println("_   _CANTIDAD_INICIAL_ANTERIOR_DE_REGISTROS:    :"+cant_inicial_anterior);
        // CONTROLO SI LA CANTIDAD MINIMA INCIAL DE LINEAS DE REGISTRO ES IGUAL A LA CANTIDAD DE REGISTROS A MOSTRAR, SI SON IGUALES, ENTONCES NO SE CAMBIO Y SOLO QUIERE VER LOS DATOS DE OTRA PAGINA, PERO SI ES DIFERENTE, ENTONCES ES PORQUE SE CAMBIO LA CANTIDAD DE REGISTROS A MOSTRAR Y VOLVERIA A MOSTRAR DESDE LA PAGINA 1 Y NO DESDE LA QUE ESTA PORQUE LA CANTIDAD DE BOTONES VAN A CAMBIAR PORQUE SE VAN A VOLVER A CALCULAR 
        if (!(cant_inicial_anterior.equals(cant_min_fija))) { // SI NO SON IGUALES ENTONCES LE REINICIO LA PAGINA ACTUAL A MOSTRAR 
//            System.out.println("_   (*)__CANTIDAD_DE_REGISTROS_DISTINTA__");
            NRO_PAG_ACTUAL_MOSTRAR = "1";
            CANT_CLICS_DERECHO = 1;
        } else { // NO REINICIARIA LA PAGINA ACTUAL A MOSTRAR SI SON IGUALES LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES LE CARGO ESA PAGINA YA QUE ESTARIA MUDANDO DE PAGINA Y NO REORDENANDO LOS REGISTROS POR PAGINA 
//            System.out.println("_   (*)___ELSE____CANTIDAD_DE_REGISTROS_IGUALES________");
        }
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_CBX_MOSTRAR+"";
//        }
        
        String sqlFiltroTxt = "";
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_FILTRO == null || PARAM_TXT_FILTRO.isEmpty() || PARAM_TXT_FILTRO.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND (UPPER(oa.IDAGENDAMIENTO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "       OR UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(oa.IDESPECIALIDAD) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                ")";
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADAS LAS FECHAS PARA ASI AGREGUEGAR AL WHERE EL FILTRO POR LAS FECHAS / SI UNA SOLA FECHA ESTA CARGADA, ENTONCES VOY A HACER EL FILTRO POR ESA FECHA 
        if (PARAM_TXT_FECHA_INI.equals("") && PARAM_TXT_FECHA_FIN.equals("")) {
            //
        } else {
            // CONTROLO PRIMERAMENTE SI AMBAS FECHAS ESTAN CARGADAS 
            if (!(PARAM_TXT_FECHA_INI.equals("")) && !(PARAM_TXT_FECHA_FIN.equals(""))) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (sqlFiltroTxt.equals("")) {
//                    sqlFiltroTxt = "WHERE DATE_FORMAT(oad.HORA, '%Y-%m-%d') >= '"+PARAM_TXT_FECHA_INI+"' AND DATE_FORMAT(oad.HORA, '%Y-%m-%d') <= '"+PARAM_TXT_FECHA_FIN+"' \n";
//                } else {
                    sqlFiltroTxt = sqlFiltroTxt + "AND DATE_FORMAT(oad.HORA, '%Y-%m-%d') >= '"+PARAM_TXT_FECHA_INI+"' AND DATE_FORMAT(oad.HORA, '%Y-%m-%d') <= '"+PARAM_TXT_FECHA_FIN+"' \n";
//                }
            } else { // SI NO ESTAN CARGADAS ENTONCES CONTROLO PARA VER SI UNO DE ELLAS NO ESTA CARGADA 
                if (!(PARAM_TXT_FECHA_INI.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (sqlFiltroTxt.equals("")) {
//                        sqlFiltroTxt = "WHERE DATE_FORMAT(oad.HORA, '%Y-%m-%d') = '"+PARAM_TXT_FECHA_INI+"' \n";
//                    } else {
                        sqlFiltroTxt = sqlFiltroTxt + "AND DATE_FORMAT(oad.HORA, '%Y-%m-%d') = '"+PARAM_TXT_FECHA_INI+"' \n";
//                    }
                }
                if (!(PARAM_TXT_FECHA_FIN.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (sqlFiltroTxt.equals("")) {
//                        sqlFiltroTxt = "WHERE DATE_FORMAT(oad.HORA, '%Y-%m-%d') = '"+PARAM_TXT_FECHA_FIN+"' \n";
//                    } else {
                        sqlFiltroTxt = sqlFiltroTxt + "AND DATE_FORMAT(oad.HORA, '%Y-%m-%d') = '"+PARAM_TXT_FECHA_FIN+"' \n";
//                    }
                }
            }
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDMED == null || PARAM_TXT_IDMED.isEmpty() || PARAM_TXT_IDMED.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDMEDICO) LIKE ('%"+PARAM_TXT_IDMED+"%')) \n";
        }
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_IDESPE == null || PARAM_TXT_IDESPE.isEmpty() || PARAM_TXT_IDESPE.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND ((oa.IDESPECIALIDAD) LIKE ('%"+PARAM_TXT_IDESPE+"%')) \n";
        }
        
        String vali_paciente = "";
        // VALIDACION PARA CONTROLAR SI ES QUE EL PARAMETRO DE PACIENTE ESTA CARGADO O NO, SI SE ENCUENTRA VACIO ENTONCES EL QUE INGRESO A LA PAGINA ES UNA PERSONA CON PERFIL DE SECRETARIO Y ADMINISTRADOR Y EN ESTE CASO LE MOSTRARE TODOS LOS AGENDAMIENTOS DE TODOS LOS PACIENTES (HASTA QUE EL QUIERA FILTRAR POR EL DE UN PACIENTE EN ESPECIFICO) 
        if (PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//            vali_paciente = "";
        } else { // Y EN CASO DE QUE ESTE CARGO EL PARAMETRO DE IDPACIENTE ENTONCES ESO QUIERE DECIR QUE LA PERSONA LOGEADA TIENE UN PERFIL DE PACIENTE, Y LE TENGO QUE MOSTRAR SOLO SUS AGENDAMIENTOS Y NO LA DE OTROS PACIENTES PARA QUE NO PUEDA ANULARLAS O VER ESOS DATOS 
            vali_paciente = "AND oad.IDPACIENTE = '"+PARAM_IDPACIENTE+"' ";
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_IDCLINICA == null || PARAM_IDCLINICA.isEmpty() || PARAM_IDCLINICA.equals(" ")) {
            sqlFiltroTxt = sqlFiltroTxt + "AND oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n";
        } else {
            sqlFiltroTxt = sqlFiltroTxt + "AND oa.IDCLINICA = '"+PARAM_IDCLINICA+"' \n";
        }
        
        try {
            String sql = "SELECT oa.IDAGENDAMIENTO, oad.ITEM, oa.IDCLINICA, DATE_FORMAT(oad.HORA, '%d/%m/%Y %H:%i') AS FECHA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO, oad.IDPACIENTE, oad.ESTADO AS ESTADO_DET \n" +
                "FROM ope_agendamiento oa \n" +
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO AND oad.ESTADO <> 'X' \n" +
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA) \n" +
                "WHERE oa.ESTADO NOT IN ('X') \n" + 
//                "WHERE oa.ESTADO = 'P' \n" + 
                ""+vali_paciente+" \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oad.HORA DESC \n"// +
//                ""+sqlFiltroCbx+" \n"
                    ;
            
//            String sql = "SELECT oa.IDAGENDAMIENTO, oa.IDCLINICA, oa.IDMEDICO, rp.NOMBRE, rp.APELLIDO, rp.CINRO, oa.IDESPECIALIDAD, oa.ESTADO \n" +
//                "FROM ope_agendamiento oa \n" + 
//                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA AND rp.IDCATEGORIA_PERSONA = 5) \n" + 
//                "WHERE oa.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND oa.ESTADO = 'A' \n" + 
//                ""+sqlFiltroTxt+" \n" + 
//                "ORDER BY oa.IDAGENDAMIENTO DESC \n" +
//                ""+sqlFiltroCbx+" \n";
            
            String SELECT_COUNT = "SELECT COUNT(oa.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento oa \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det oad ON oad.IDAGENDAMIENTO = oa.IDAGENDAMIENTO AND oad.ESTADO <> 'X' \n" +
                "JOIN rh_persona rp ON (oa.IDMEDICO = rp.IDPERSONA) \n" +
                "WHERE oa.ESTADO NOT IN ('X') \n" + 
//                "WHERE oa.ESTADO = 'P' \n" + 
                ""+vali_paciente+" \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY oad.HORA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("---------------------MODEL_AGENDAMIENTO------------------------");
            System.out.println("-- --filtrar_anu_agen()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            // --------------------------------------------------------------------------------------------------------
            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
            if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
                cant_btn_lista_final = 1;
                NRO_PAG_ACTUAL_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
            } else {
                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
    //            System.out.println("_   _NUEVA_DIVISION:    :"+divi);
                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
    //            System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
                if (resul_redondeo_btn == true) {
                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
    //                for (String rb : resul_btn) {
    //                    System.out.println("_   _partes_for:   :"+rb);
    //                }
                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
                    if (Integer.parseInt(resul_btn[1]) == 0) {
                        //
                    } else {
                        cant_btn_lista_final = cant_btn_lista_final + 1;
                    }
    //                System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
                }
                // ---------------------------------------------------------------------------------------------------------------------------------------------------
                System.out.println("_   _NRO_PAG_ACTUAL_MOSTRAR: ("+NRO_PAG_ACTUAL_MOSTRAR+")  >  cant_btn_lista_final: ("+cant_btn_lista_final+") ____");
                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) > cant_btn_lista_final) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
                    NRO_PAG_ACTUAL_MOSTRAR = "1";
                    CANT_CLICS_DERECHO = 1;
                } else {
                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
                }
            }
            // --------------------------------------------------------------------------------------------------------
            
            // --------------------------------------------------------------------------------------------------------
            // OBSERVACION: YA QUE LA NUMERACION DE LA PAGINA DONDE SE ENCONTRABA EL USUARIO NO SE ENCUENTRA EN EXISTENCIA EN LA NUEVA CANTIDAD DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHOS DEBO DE RESETEAR 
            // VALIDACION PARA REESTABLECER LA CANTIDAD DE CLICS DERECHOS, PARA QUE SI POR EJEMPLO ESTABA EN EL BOTON NRO 4 Y REORDENA LA CANTIDAD DE REGISTROS A VISUALIZAR O AÑADE UN NUEVO FILTRO Y EL BOTON TOTAL SEA 2, COMO NO EXISTE MAS EL 4 EN ESA CLASIFICACION, ENTONCES LE DEVUELVA A LA PRIMERA PAGINA Y EL CLIC DERECHO VOLVERIA A SER 1 
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("_  ____CONTROL_DE_CLICS_DERECHO_____  _");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // OBSERVACION_01: EMPEZARIA CON LA VALIDACION DEL CLIC DERECHO EN CASO NO SE CUMPLA UNA DE ESTAS DOS DECLARACIONES, SI EL NRO DE PAGINA ACTUAL ES MENOR O IGUAL A TRES Y LA CANTIDAD DE CLICS DERECHO ES IGUAL A UNO ENTONCES SE DEVOLVERA TRUE, PERO SI UNO DE LOS DOS NO SE CUMPLIERA ENTONCES DEVOLVERA FALSE Y AHI ENTRARIA A VALIDAR PORQUE OSINO ENTRARIA A CALCULAR ALGO QUE POR LOGIA YA SÉ  
            // OBSERVACION_02: EN LAS ANTERIORES VALIDACIONES YA SE RESETEA ESTOS VALORES O SE PUEDEN LLEGAR A SER MODIFICADAS, POR ESO EMPIEZO CON ESTA CONDICION, YA QUE DESDE ANTES YA PODRIA SER ARREGLADA 
            if((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)<=3 && CANT_CLICS_DERECHO==1) == false) {
                System.out.println("_     ____IF_____UNA_DE_LAS_DOS_CONDICIONES_NO_SE_CUMPLE____");
                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= 3 ) { // SI ES MAYOR O IGUAL A 3, ENTONCES LA CANTIDAD DE CLICS ES DE 1, YA QUE SERIA LAS PRIMERA PAGINAS 
                    System.out.println("_       __IF__VALOR_DENTRO_DE_LO_CALCULADO_______");
                    CANT_CLICS_DERECHO = 1;
                } else { // SI EL NUMERO DE PAGINA A DEVOLVER ES DISTINTO A LAS PRIMERA TRES PAGINAS, ENTONCES AHI SI TENDRIA QUE HAYAR LA UBICACION DEL NRO DE PAGINA ACTUAL MIENTRAS HAGO UN CONTEO DE CLICS A TRAVES DE UN FOR POR LA CANTIDAD FINAL (TOTAL) DE BOTONES DE LA PAGINA 
                    System.out.println("_       __ELSE________IF_ELSE________");
                    int ctrl_cant_adecuada = CANT_CLICS_DERECHO * 3;
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_03:  :"+(ctrl_cant_adecuada));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_02:  :"+(ctrl_cant_adecuada-1));
                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_01:  :"+(ctrl_cant_adecuada-2));
                    if ((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= ctrl_cant_adecuada) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-1)) 
                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-2)) 
                    ) { // SI EL BOTON DEL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES QUE SE ESTARIAN MOSTRANDO EN EL BLOQUE DE 3 BOTONES DE LA PAGINA, ENTONCES LA CANTIDAD DEL CLIC DERECHO ESTA BIEN, SI FUERA MENOR O MAYOR, ENTONCES LA CANTIDAD DE CLIC DERECHO NO ESTA CORRECTO Y TENDRIA QUE CALCULARLO 
                        System.out.println("_          _IF___CANTIDAD_CLICS_DERECHO_CORRECTA___BTNS_DENTRO_DEL_CALCULO___");
                    } else {
                        System.out.println("_          _ELSE__CALCULAR_CLIC_DERECHO_______");
                        System.out.println("_           _CANTIDAD_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
                        int band_cant_clic_derechos = 1;
                        int cant_botones_mostrar = 3; // EL VALOR ES 3 PORQUE 3 BOTONES ES LA CANTIDAD MAXIMA A MOSTRAR POR PAGINA 
                        for (int i = 1; i <= cant_btn_lista_final; i++) {
                            System.out.println(".");
                            System.out.println(".");
                            System.out.println("_       _____FOR_____   _"+i+"__");
                            System.out.println("_       _PRIMER_BOTON_DEL_SIGUIEN_BLOQUE_DE_BOTONES:   :"+(cant_botones_mostrar+1));
                            if (i == (cant_botones_mostrar+1)) {
                                System.out.println("_       ___IF___SUM_BANDERA_CLIC_DERECHO_AND_CANT_BLOQUE_BTNS___");
                                band_cant_clic_derechos = band_cant_clic_derechos + 1; // LE SUMO UNO A LA CANTIDAD DE CLICS EN CASO DE QUE SEA IGUAL AL PRIMER BOTON DE LA SIGUIENTE FORMACION DE LOS 3 BOTONES PORQUE SE ENTENDERIA COMO UN CLIC MAS HACIA LA DERECHA 
                                cant_botones_mostrar = cant_botones_mostrar + 3; // LE SUMO 3 A LA CANTIDAD DE BOTONES QUE SE MUESTRAN PARA REPRESENTAR AL LIMITE DEL SIGUIEN CLIC DERECHO, EJEMPLO: PRIMERO SERIAN 3 Y SI FUERA 4, ENTONCES LE SUMO UN CLIC DERECHO Y 3 A ESTA VARIABLE Y EL PROXIMO LIMITE DE 3 BOTONES VISUALES SERIAN 6 Y AHI SI SERIA 7 ENTONCES VOLVERIA A SUMAR OTRO A LOS CLIC DERECHO Y 3 A LA CANTIDAD DE BOTONES, Y ASI HASTA LLEGAR A LA CANTIDAD FINAL DE BOTONES QUE HAY 
                            }
                            if (i == Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) { // CORTARIA EL FOR CUANDO LLEGUE A RECORRER AL BOTON ACTUAL A MOSTRAR, YA QUE NO QUIERO CALCULAR TODAS LAS CANTIDADES DE LOS BOTONES DERECHOS SINO LA CANTIDAD DE BOTONES DERECHOS QUE SE DIO PARA MOSTRAR AL BOTON ACTUAL 
                                System.out.println("_       ____BREAK_FOR____");
                                CANT_CLICS_DERECHO = band_cant_clic_derechos;
                                System.out.println("_       _-CANTIDAD_DE_CLICS_DERECHO_ENCONTRADA:    :"+CANT_CLICS_DERECHO);
                                break;
                            }
                        } // END FOR 
                    }
                } // END ELSE 
            } else {
                System.out.println("_     ____ELSE_____LAS_DOS_CONDICIONES__SE_CUMPLEN____");
            }
            // --------------------------------------------------------------------------------------------------------
            
            int i = 1;
            while (MA_RESULTADO.next()) {
                
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA"));
                        datos.setOAD_HORA(MA_RESULTADO.getString("FECHA"));
                        datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                        datos.setOAD_IDPACIENTE(MA_RESULTADO.getString("IDPACIENTE"));
                        datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO"));
                        datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    listaFiltro.add(datos);
                }
                
                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
                if (cant_btn_lista > Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) {
                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+NRO_PAG_ACTUAL_MOSTRAR+")______");
                    break;
                }
                
                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
//                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
                    if (cant_min.equals(String.valueOf(i))) {
    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
                        cant_btn_lista = cant_btn_lista + 1;
    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
                    }
                }
//                System.out.println(".");
//                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_PC_ANU_AGEN_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_PC_ANU_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_PC_ANU_AGEN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_PC_ANU_AGEN_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_PC_ANU_AGEN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    // METODO QUE UTILIZO PARA CONTROLAR SI EL AGENDAMIENTO CUENTA CON UNA FACTURA ACTIVA ANTES DE ANULAR EL AGENDAMIENTO YA QUE SE TIENE QUE ANULAR PRIMERO LA FACTURA 
    public boolean ctrlFacturaActDeAgen(HttpSession PARAM_SESION, String PARAM_IDAGENDAMIENTO, String PARAM_ITEM) {
        boolean valor = false;
        try {
            String sql = "SELECT ccc.IDCUENTACLIENTE, ccc.IDPERSONA, fact_det.IDFACTURA, fact_det.ITEM, fact_det.IDTIPOCONCEPTO, fact_cab.NROFACTURA, fact_cab.ESTADO \n" +
                "FROM cc_cuenta_cliente ccc \n" +
                "JOIN ope_factura_det fact_det ON fact_det.IDCONCEPTO = ccc.IDCUENTACLIENTE AND fact_det.IDTIPOCONCEPTO = 1 \n" +
                "JOIN ope_factura fact_cab ON fact_det.IDFACTURA = fact_cab.IDFACTURA AND fact_cab.ESTADO <> 'X' \n" +
                "WHERE ccc.ITEM_AGEN = '"+PARAM_ITEM+"' \n" +
                "AND ccc.IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n";
            
//            String sql = "SELECT IDFACTURA, AGENDADO \n" +
//                "FROM ope_agendamiento_det \n" +
//                "WHERE ITEM = '"+PARAM_ITEM+"' \n" +
//                "AND IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---ctrlFacturaActDeAgen()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while (MA_RESULTADO.next()) {
                String IDFACTURA = MA_RESULTADO.getString("IDFACTURA");
                System.out.println("_   _ID_FACTURA:  :"+IDFACTURA);
//                String AGENDADO = MA_RESULTADO.getString("AGENDADO");
//                System.out.println("_   _AGENDADO:    :"+AGENDADO);
                
                if (IDFACTURA == null || IDFACTURA.equalsIgnoreCase("0") || IDFACTURA.isEmpty()) { // SI ESTA VACIO EL IDATENCIONPAC ES PORQUE NO CUENTA CON UNA ATENCION ACTIVA 
                    System.out.println("_   ___IF_____VALOR_FALSE_____");
                    valor = false;
                } else {
                    System.out.println("_   ___ELSE_____VALOR_TRUE_____");
                    valor = true;
                    String NRO_FACTURA = MA_RESULTADO.getString("NROFACTURA");
                    PARAM_SESION.setAttribute("VALI_NRO_FACTURA", NRO_FACTURA);
                }
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    // METODO QUE UTILIZO PARA ANULAR EL AGENDAMIENTO (DETALLE) DEL PACIENTE 
    public String anular_agendamiento(String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN, String PARAM_IDPACIENTE) {
        String tipo_resultado = ""; // 1 : exito / 2 : error 
        try {
            String sql = "UPDATE ope_agendamiento_det \n" +
                "SET ESTADO = 'X' \n" +
                "WHERE IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n" +
                "AND ITEM = '"+PARAM_ITEM_AGEN+"' \n" +
                "AND IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---anular_agendamiento()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            MA_CONEXION = devolverConex();
            MA_SENTENCIA = MA_CONEXION.createStatement();
            int varresultado = MA_SENTENCIA.executeUpdate(sql);
            if (varresultado == 1) {
                tipo_resultado = "1";
                anular_cuenta_cliente(PARAM_IDAGENDAMIENTO, PARAM_ITEM_AGEN, PARAM_IDPACIENTE);
                cerrarConexiones();
            } else {
                tipo_resultado = "2";
                cerrarConexiones();
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipo_resultado;
    } // end method 
    
    
    
    // METODO PARA ANULAR LA CUENTA QUE SE CREO AL AGENDARSE EL PACIENTE 
    public void anular_cuenta_cliente(String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN, String PARAM_IDPACIENTE) {
//        String tipo_resultado = ""; // 1 : exito / 2 : error 
        try {
            String sql = "UPDATE cc_cuenta_cliente \n" +
                "SET ESTADO = 'X' \n" +
                "WHERE IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n" +
                "AND ITEM_AGEN = '"+PARAM_ITEM_AGEN+"' \n" +
                "AND IDPERSONA = '"+PARAM_IDPACIENTE+"'  \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---anular_cuenta_cliente()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            MA_CONEXION = devolverConex();
            MA_SENTENCIA = MA_CONEXION.createStatement();
            int varresultado = MA_SENTENCIA.executeUpdate(sql);
            System.out.println("_   ___VAR_RESULTADO_SQL:  :"+varresultado);
//            if (varresultado == 1) {
//                tipo_resultado = "1";
//                cerrarConexiones();
//            } else {
//                tipo_resultado = "2";
                cerrarConexiones();
//            }
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
//        return tipo_resultado;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA DEVOLVER EL MONTO DE LA ESPECIALIDAD Y ASI SABER SI DEBO DE CREARLE UNA CUENTA POR AGENDAMIENTO O NO 
    public String traerMontoEspecialidad(String PARAM_IDESPECIALIDAD) {
        String MONTO = "0";
        try {
            String sql = "SELECT IDESPECIALIDAD, DESC_ESPECIALIDAD, MONTO, IVA \n" +
                "FROM rh_especialidad \n" +
                "WHERE IDESPECIALIDAD = '"+PARAM_IDESPECIALIDAD+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traerMontoEspecialidad()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                MONTO = MA_RESULTADO.getString("MONTO");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        System.out.println("_       __*__________monto_____:   :"+MONTO);
        return MONTO;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA DEVOLVER LA DESCRIPCION PARA EL AGENDAMIENTO, PARA GUARDAR PARA LA TABLA DE CUENTA CLIENTE 
    // OBSERVACION: SI NO HAY UN PUNTO FINAL Y UNA VARIABLE REEMPLAZABLE (#DIA#,#MES#,#AÑO#,#HORA#) SE ENCUENTRA AL FINAL, DENTRO DEL FOR EN EL IF-ELSE NO SE VA A AGREGAR EL VALOR DE LA VARIABLE PORQUE FALTA UNA PREGUNTA MAS CUANDO LA ULTIMA PALABRA SEA LA VARIABLE, POR ESO NECESITO EL PUNTO PORQUE CON EL PUNTO LA VARIABLE REEMPLAZABLE SIEMPRE VA A ESTAR ANTES DEL PUNTO Y EL PUNTO SERA EL FINAL DE LA ORACION Y SI LA PALABRA EXISTE SI O SI SE VA A ENCONTRAR ANTES DEL PUNTO.- 
    public String traer_desc_agend(ModelInicioSesion metodosIniSes, String PARAM_IDCLINICA, String PARAM_FECHA_AGEN, String PARAM_HORA_AGEN) {
        String VALOR = "";
//        System.out.println(".");System.out.println(".");
//        System.out.println(".");System.out.println(".");
//        System.out.println(".       ______________     __traer_desc_agend("+PARAM_IDCLINICA+")__     _____________");
        // DE LA FECHA DE AGENDAMIENTO QUE RECIBO COMO PARAMETRO, SEPARO EN PARTES Y CARGO CADA VARIABLE QUE UTILIZO MAS ADELANTE 
        String DIA_AGEN = metodosIniSes.getDatoFecha(3, PARAM_FECHA_AGEN);
        String MES_AGEN = metodosIniSes.getDatoFecha(2, PARAM_FECHA_AGEN);
        String ANHO_AGEN = metodosIniSes.getDatoFecha(1, PARAM_FECHA_AGEN);
        String HORA_AGEN = PARAM_HORA_AGEN;
        try {
            String sql = "SELECT IDCONFIGAGEND, DESC_CONFIG, MAX_AGEND, DESC_AGEND \n" +
                "FROM sys_config_agend \n" +
                "WHERE ESTADO = 'A' \n" +
                "AND IDCLINICA = '"+PARAM_IDCLINICA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traer_desc_agend()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                String DESC_AGEND = MA_RESULTADO.getString("DESC_AGEND");
//                VALOR = MA_RESULTADO.getString("DESC_AGEND");
                System.out.println("_   _DESC_AGEND:  :"+DESC_AGEND);
                
                // POR CADA VARIABLE REEMPLAZABLE HAGO UN IF PARA CONTROLAR LA EXISTENCIA DE LA PALABRA PARA LUEGO ASI RECORRER CON EL FOR -- 
                // ----------------- #DIA# -------------------- 
                if (DESC_AGEND.contains("#DIA#") || DESC_AGEND.contains("#dia#") || DESC_AGEND.contains("#DÍA#") || DESC_AGEND.contains("#día#")) {
                    System.out.println("_   _   ____IF______#DIA#________");
//                if (DESC_AGEND.contains("#DIA#")) {
                    // COMO HAY CUATRO OPCIONES POSIBLES PARA ESCRIBIR "DIA", ENTONCES PREGUNTO CUAL DE LAS OPCIONES ES LA PRESENTE PARA ASI PODER DIVIDIR POR LA PALABRA CORRECTA 
                    String varDescSplitDia = "#DIA#";
                    if (DESC_AGEND.contains("#DIA#")) {
                        varDescSplitDia = "#DIA#";
                    }
                    if (DESC_AGEND.contains("#dia#")) {
                        varDescSplitDia = "#dia#";
                    }
                    if (DESC_AGEND.contains("#DÍA#")) {
                        varDescSplitDia = "#DÍA#";
                    }
                    if (DESC_AGEND.contains("#día#")) {
                        varDescSplitDia = "#día#";
                    }
                    String[] ubi_dia = DESC_AGEND.split(varDescSplitDia);
//                    String[] ubi_dia = DESC_AGEND.split("#DIA#");
                    for (int i = 0; i < ubi_dia.length; i++) {
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("_       _FOR_DIA_      ");
//                        System.out.println("_   _VAR_["+i+"]_:   :"+ubi_dia[i]);
                        if ( (i+1) == ubi_dia.length ) {
//                            System.out.println("_   ___IF______SIN_AGREGAR_DIA__");
                            VALOR = VALOR + ubi_dia[i];
                        } else if ( (i+1) < ubi_dia.length ) {
//                            System.out.println("_   ___ELSE_IF______AGREGANDO_DIA__");
                            VALOR = VALOR + ubi_dia[i] + DIA_AGEN;
                        }
                    }
//                    System.out.println(".");System.out.println(".");System.out.println(".");
                } else {
                    System.out.println("_   _   ____ELSE______#DIA#________");
                    VALOR = DESC_AGEND;
                }
                // -------------------------------------------- 
                
                // ----------------- #MES# -------------------- 
                if (VALOR.contains("#MES#") || VALOR.contains("#mes#")) {
                    System.out.println("_   _   ____IF______#MES#________");
//                if (DESC_AGEND.contains("#MES#")) {
                    String VALOR01="";// VARIABLE QUE UTILIZO PARA GUARDAR LA DESCRIPCION DEL AGENDAMIENTO SUSTITUYENDO LAS VARIABLES DE LA FECHA DE AGENDAMIENTO 
                    // COMO HAY DOS OPCIONES POSIBLES PARA ESCRIBIR "MES", ENTONCES PREGUNTO CUAL DE LAS OPCIONES ES LA PRESENTE PARA ASI PODER DIVIDIR POR LA PALABRA CORRECTA 
                    String varDescSplitMes = "#MES#";
                    if (VALOR.contains("#MES#")) {
                        varDescSplitMes = "#MES#";
                    }
                    if (VALOR.contains("#mes#")) {
                        varDescSplitMes = "#mes#";
                    }
                    String[] ubi_mes = VALOR.split(varDescSplitMes);
//                    String[] ubi_mes = DESC_AGEND.split("#MES#");
                    for (int i = 0; i < ubi_mes.length; i++) {
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("_       _FOR_MES_      ");
//                        System.out.println("_   _VAR_["+i+"]_:   :"+ubi_mes[i]);
                        if ( (i+1) == ubi_mes.length ) {
//                            System.out.println("_   ___IF______SIN_AGREGAR_MES__");
                            VALOR01 = VALOR01 + ubi_mes[i];
                        } else if ( (i+1) < ubi_mes.length ) {
//                            System.out.println("_   ___ELSE_IF______AGREGANDO_MES__");
                            VALOR01 = VALOR01 + ubi_mes[i] + MES_AGEN;
                        }
                    }
//                    System.out.println(".");System.out.println(".");System.out.println(".");
                    VALOR = VALOR01;
                } else {
                    System.out.println("_   _   ____ELSE______#MES#________");
                }
                // -------------------------------------------- 
                
                // ----------------- #AÑO# -------------------- 
                if (VALOR.contains("#AÑO#") || VALOR.contains("#año#") || VALOR.contains("#ANHO#") || VALOR.contains("#anho#")) {
                    System.out.println("_   _   ____IF______#AÑO#________");
//                if (DESC_AGEND.contains("#AÑO#")) {
                    String VALOR02=""; // VARIABLE QUE UTILIZO PARA GUARDAR LA DESCRIPCION DEL AGENDAMIENTO SUSTITUYENDO LAS VARIABLES DE LA FECHA DE AGENDAMIENTO 
                    // COMO HAY CUATRO OPCIONES POSIBLES PARA ESCRIBIR "AÑO", ENTONCES PREGUNTO CUAL DE LAS OPCIONES ES LA PRESENTE PARA ASI PODER DIVIDIR POR LA PALABRA CORRECTA 
                    String varDescSplitAnho = "#AÑO#";
                    if (VALOR.contains("#AÑO#")) {
                        varDescSplitAnho = "#AÑO#";
                    }
                    if (VALOR.contains("#año#")) {
                        varDescSplitAnho = "#año#";
                    }
                    if (VALOR.contains("#ANHO#")) {
                        varDescSplitAnho = "#ANHO#";
                    }
                    if (VALOR.contains("#anho#")) {
                        varDescSplitAnho = "#anho#";
                    }
                    String[] ubi_anho = VALOR.split(varDescSplitAnho);
//                    String[] ubi_anho = DESC_AGEND.split("#AÑO#");
                    for (int i = 0; i < ubi_anho.length; i++) {
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("_       _FOR_AÑO_      ");
//                        System.out.println("_   _VAR_["+i+"]_:   :"+ubi_anho[i]);
                        if ( (i+1) == ubi_anho.length ) {
//                            System.out.println("_   ___IF______SIN_AGREGAR_AÑO__");
                            VALOR02 = VALOR02 + ubi_anho[i];
                        } else if ( (i+1) < ubi_anho.length ) {
//                            System.out.println("_   ___ELSE_IF______AGREGANDO_AÑO__");
                            VALOR02 = VALOR02 + ubi_anho[i] + ANHO_AGEN;
                        }
                    }
//                    System.out.println(".");System.out.println(".");System.out.println(".");
                    VALOR = VALOR02;
                }
                // -------------------------------------------- 
                
                // ----------------- #HORA# -------------------- 
                if (VALOR.contains("#HORA#") || VALOR.contains("#hora#")) {
                    System.out.println("_   _   ____IF______#HORA#________");
//                if (DESC_AGEND.contains("#HORA#")) {
                    String VALOR03=""; // VARIABLE QUE UTILIZO PARA GUARDAR LA DESCRIPCION DEL AGENDAMIENTO SUSTITUYENDO LAS VARIABLES DE LA FECHA DE AGENDAMIENTO 
                    // COMO HAY DOS OPCIONES POSIBLES PARA ESCRIBIR "HORA", ENTONCES PREGUNTO CUAL DE LAS OPCIONES ES LA PRESENTE PARA ASI PODER DIVIDIR POR LA PALABRA CORRECTA 
                    String varDescSplitHora = "#HORA#";
                    if (VALOR.contains("#HORA#")) {
                        varDescSplitHora = "#HORA#";
                    }
                    if (VALOR.contains("#hora#")) {
                        varDescSplitHora = "#hora#";
                    }
                    String[] ubi_hora = VALOR.split(varDescSplitHora);
//                    String[] ubi_hora = DESC_AGEND.split("#HORA#");
                    for (int i = 0; i < ubi_hora.length; i++) {
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println(".");
//                        System.out.println("_       _FOR_HORA_      ");
//                        System.out.println("_   _VAR_["+i+"]_:   :"+ubi_hora[i]);
//                        System.out.println("---------------------------------------------------------------");
//                        System.out.println("_       _PRUEBA_01:   :"+i);
//                        System.out.println("_       _PRUEBA_02:   :"+ubi_hora.length);
//                        System.out.println("_       _PRUEBA_03:   :"+ubi_hora[i].contains("#HORA#"));
//                        System.out.println("---------------------------------------------------------------");
                        if ( (i+1) < ubi_hora.length ) {
//                            System.out.println("_   01___ELSE_IF______AGREGANDO_HORA__");
                            VALOR03 = VALOR03 + ubi_hora[i] + HORA_AGEN;
                        } else if ( (i+1) == ubi_hora.length ) {
//                            System.out.println("_   ___IF______SIN_AGREGAR_HORA__");
                            VALOR03 = VALOR03 + ubi_hora[i];
                        }
                    }
//                    System.out.println(".");System.out.println(".");System.out.println(".");
                    VALOR = VALOR03;
                }
                // -------------------------------------------- 
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    } // end method 
    
    
    
    // METODO PARA TRAER EL IDCONFIGAGEND QUE UTILIZO PARA EL COMENTARIO Y ASI GUARDAR COMO AUXILIAR EL ID EN EL DETALLE DEL AGENDAMIENTO 
    public String traer_idconfig_agend(String PARAM_IDCLINICA) {
        String VALOR = "";
        try {
            String sql = "SELECT IDCONFIGAGEND, DESC_CONFIG, MAX_AGEND, DESC_AGEND \n" +
                "FROM sys_config_agend \n" +
                "WHERE ESTADO = 'A' \n" +
                "AND IDCLINICA = '"+PARAM_IDCLINICA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traer_idconfig_agend()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                VALOR = MA_RESULTADO.getString("IDCONFIGAGEND");
//                System.out.println("_   _ID_CONFIG_AGEND:  :"+VALOR);
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA CONTROLAR SI EXISTE UN PUNTO AL FINAL DE LA ORACION Y EN CASO DE QUE NO LO HAA ENTONCES AGREGAR UNO PARA EVITAR QUE SI SE AGREGA UNA VARIABLE QUE TENGO QUE REEMPLAZAR (COMO #DIA#, #MES#, #AÑO# Y #HORA#) PUEDA REEMPLAZAR POR EL DATO DE LA FECHA DE AGENDAMIENTO Y SI ALGUNA DE ESAS VARIABLES SE ENCONTRARA AL FINAL Y NO HAY PUNTO ENTONCES NO ME SUSTITUYE PERO CON PUNTO FINAL, SI.- 
    public String verificarPuntoFinal(String PARAM_DESC_AGEND) {
        System.out.println(".");System.out.println(".");
        System.out.println(".");System.out.println(".");
        System.out.println(".       ______________     __verificarPuntoFinal(["+PARAM_DESC_AGEND+"])__     _____________");
        try {
            // OBS: BLOQUE DE CODIGO QUE UTILIZO PARA CONTROLAR SI EXISTE UN PUNTO AL FINAL DE LA ORACION Y EN CASO DE QUE NO LO HAA ENTONCES AGREGAR UNO PARA EVITAR QUE SI SE AGREGA UNA VARIABLE QUE TENGO QUE REEMPLAZAR (COMO #DIA#, #MES#, #AÑO# Y #HORA#) PUEDA REEMPLAZAR POR EL DATO DE LA FECHA DE AGENDAMIENTO Y SI ALGUNA DE ESAS VARIABLES SE ENCONTRARA AL FINAL Y NO HAY PUNTO ENTONCES NO ME SUSTITUYE PERO CON PUNTO FINAL, SI.- 
            if (PARAM_DESC_AGEND.contains(".") == true) { // si se encuentra el punto entonces controlo que exista un punto al final del texto 
                System.out.println("_       ___SE_ENCUENTRA_EL_PUNTO___");
                System.out.println("_   _   _   _CANT:  :"+PARAM_DESC_AGEND.length());
                String ULT_PAL = PARAM_DESC_AGEND.substring((PARAM_DESC_AGEND.length()-1), (PARAM_DESC_AGEND.length()));
                System.out.println("_   _   _   _PALABRA:  :"+ULT_PAL);
                // controlo si el ultimo caracter es un punto o no para asi saber si agrego o no al final 
                if (ULT_PAL.equalsIgnoreCase(".")==true) {
                    System.out.println("_   _   _   _   _IF__________SE_ENCUENTRA_EL_PUNTO:__________");
                } else {
                    System.out.println("_   _   _   _   _ELSE_________NO_SE_ENCUENTRA_EL_PUNTO:__________");
                    PARAM_DESC_AGEND = PARAM_DESC_AGEND + ".";
                }
                System.out.println("_   _   _   _   _   _DESC_AGEND_NEW:   :"+PARAM_DESC_AGEND);
            } else { // si no se encuentra le agrego el punto al final 
                System.out.println("_       ___ELSE__NO_SE_ENCUENTRA_EL_PUNTO____");
                PARAM_DESC_AGEND = PARAM_DESC_AGEND + ".";
                System.out.println("_   _   _   _DESC_AGEND_NEW:   :"+PARAM_DESC_AGEND);
            }
        } catch(Exception e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return PARAM_DESC_AGEND;
    }
    
    
    
    // METODO QUE UTILIZO PARA LA VERIFICACION DE LA EXISTENCIA DE LA CABECERA DEL AGENDAMIENTO DEL MEDICO PARA VER SI ALCANZO EL LIMITE DE AGENDAMIENTO Y ASI MOSTRARLE UN MENSAJE PARA SABER SI AUN ASI QUIERE CONTINUAR Y CREARLE UNA NUEVA CABECERA PORQUE YA ALCANZO EL LIMITE EN LA ANTERIOR 
    public boolean verifyAgendExist(HttpSession PARAM_SESION, String AGEN_IDMEDICO, String AGEN_IDESPECIALIDAD, String AGEN_IDCLINICA, String PARAM_FECHA_AGEN) {
        boolean valor = false;
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".          ________verifyAgendExist()_______ ");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        try {
            String sql = "SELECT cab.IDAGENDAMIENTO, COUNT(det.ITEM) AS CANT_AGEND, det.ESTADO \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO NOT IN ('X','C') \n" +
                "WHERE cab.ESTADO <> 'X' \n" +
                "AND cab.IDMEDICO = '"+AGEN_IDMEDICO+"' \n" +
                "AND cab.IDCLINICA = '"+AGEN_IDCLINICA+"' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN,'%Y-%m-%d') = '"+PARAM_FECHA_AGEN+"' \n";
            
            /*
             * OBSERVACION: 
                EL PROBLEMA CON ESTE SELECT ES QUE AL FILTRAR POR LA ESPECIALIDAD PUEDE SER QUE SE TRAIGA MENOS RESULTADOS O NO SE TRAIGA NINGUN RESULTADO 
                POR ESO NO HAY QUE FILTRAR POR LA ESPECIALIDAD, YA QUE EL MISMO MEDICO PUEDE TENER VARIOS AGENDAMIENTOS CON DISTINTAS ESPECIALIDADES Y DEBERIA 
                DE DEVOLVER LOS AGENDAMIENTOS QUE TIENE YA AGENDADO Y NO SOLO LOS AGENDAMIENTOS DE UNA SOLA ESPECIALIDAD 
            */
//            String sql = "SELECT IDAGENDAMIENTO \n" +
//                "FROM ope_agendamiento \n" +
//                "WHERE IDMEDICO = '"+AGEN_IDMEDICO+"' \n" +
//                "AND IDCLINICA = '"+AGEN_IDCLINICA+"' \n" +
//                "AND IDESPECIALIDAD = '"+AGEN_IDESPECIALIDAD+"' \n" +
//                "AND ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---verifyAgendExist()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            if(MA_RESULTADO.next() == true) {
                String ID_AGEND = MA_RESULTADO.getString("IDAGENDAMIENTO");
                System.out.println("_   _ID_AGEND:  :"+ID_AGEND);
                // CANTIDAD DE FILAS 
                String CANT_AGEND = MA_RESULTADO.getString("CANT_AGEND");
//                String CANT_AGEND = traerCantFilasAgendDet(ID_AGEND, PARAM_FECHA_AGEN);
                System.out.println("_   _CANT_AGEND:  :"+CANT_AGEND);
                // CANTIDAD LIMITE DE AGENDAMIENTO POR MEDICO 
                String CANT_LIMIT_AGEND = traerCantLimitAgendDet(AGEN_IDCLINICA);
                System.out.println("_   _CANT_LIMIT_AGEND:  :"+CANT_LIMIT_AGEND);
                // VALIDACION POR SI SEA NULL 
                if (CANT_AGEND == null) {
                    System.out.println("_01__NULL___");
                    CANT_AGEND = "1";
                }
                if (CANT_LIMIT_AGEND == null) {
                    System.out.println("_02__NULL___");
                    CANT_LIMIT_AGEND = "1";
                }
                // SI EL LIMITE DE CANTIDAD DE AGENDAMIENTO DE LA CONFIGURACION ES IGUAL A CERO, ENTONCES NO CONTRALARIA POR LA CANTIDAD DE AGENDAMIENTOS 
                if (CANT_LIMIT_AGEND.equalsIgnoreCase("0")) {
                    System.out.println("_     1*  _CTRL_VALI______   IF   __CANT_LIMIT_AGEND == CERO__   ___NO_CUENTA_CON_LIMITE_DE_AGENDAMIENTO__   ______");
                    valor = false;
                } else { // SI EL LIMITE FUERA DIFERENTE A CERO, ENTONCES SI PASARIA A CONTROLAR EL LIMITE 
                    System.out.println("_     2*  _CTRL_VALI______   ELSE   __CANT_LIMIT_AGEND__   ___TIENE_LIMITE_DE_AGENDAMIENTO__   _________");
                    // CONTROLO SI LA CANTIDAD DE AGENDAMIENTO EXISTENTE ES MAYOR A LA CANTIDAD ESTABLECIDA COMO LIMITE POR AGENDAMIENTO 
                    if (Integer.parseInt(CANT_AGEND) >= Integer.parseInt(CANT_LIMIT_AGEND)) { // SI FUERA MAYOR ENTONCES LE DEVUELVO TRUE YA QUE YA ALCANZO AL LIMITE 
                        System.out.println("--      --IF____CANT_AGEND_[_MAYOR_O_IGUAL_]_A_CANT_LIMIT_AGEND____--      --");
                        valor = true;
                    } else if (Integer.parseInt(CANT_AGEND) < Integer.parseInt(CANT_LIMIT_AGEND)) { // PERO SI FUERA MENOR ENTONCES LE DEVOLVERIA FALSE 
                        System.out.println("--      --IF____CANT_AGEND_[_MENOR_]_A_CANT_LIMIT_AGEND____--      --");
                        valor = false;
                    }
                }
            } // end while 
//            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    // METODO QUE UTILIZO PARA TRAER LA CANTIDAD DE FILAS DE AGENDAMIENTO DEL DETALLE 
    public String traerCantFilasAgendDet(String PARAM_IDAGENDAMIENTO, String PARAM_FECHA_AGEN) {
        String VALOR = "0";
        try {
            String sql = "SELECT cab.IDAGENDAMIENTO, COUNT(det.ITEM) AS CANT_AGEND, det.ESTADO \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO NOT IN ('X','C') \n" +
                "WHERE cab.ESTADO <> 'X' \n" +
                "AND det.IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n" + 
                "AND DATE_FORMAT(det.FECHA_AGEN,'%Y-%m-%d') = '"+PARAM_FECHA_AGEN+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traerCantFilasAgendDet()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                VALOR = MA_RESULTADO.getString("CANT_AGEND");
                System.out.println("_   _CANT_AGEND:  :"+VALOR);
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    } // end method 
    
    
    // METODO QUE UTILIZO PARA TRAER LA CANTIDAD MAXIMA DE AGENDAMIENTO PERMITIDO PARA CADA MEDICO POR CADA FECHA 
    public String traerCantLimitAgendDet(String PARAM_IDCLINICA) {
        String VALOR = "0";
        try {
            String sql = "SELECT IDCONFIGAGEND, DESC_CONFIG, MAX_AGEND, DESC_AGEND \n" +
                "FROM sys_config_agend \n" +
                "WHERE ESTADO = 'A' \n" +
                "AND IDCLINICA = '"+PARAM_IDCLINICA+"' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traerCantLimitAgendDet()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                VALOR = MA_RESULTADO.getString("MAX_AGEND");
                System.out.println("_   _MAX_CANT_AGEND:  :"+VALOR);
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA CERRAR LA CABECERA DEL AGENDAMIENTO DEL MEDICO 
    public String cerrarAgendamiento(String PARAM_IDAGENDAMIENTO) {
        String tipoRespuesta="0"; // 1 = Exito / 2 = Error 
        
//        System.out.println(".");System.out.println(".");System.out.println(".");
//        System.out.println("_   _   __   _   __   _   __* CERRAR_AGENDAMIENTO *__   _   __   _   __   _   _");
        try {
            String sql = "UPDATE ope_agendamiento SET ESTADO = 'C' WHERE IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n";
            System.out.println("---------------------MODEL_AGENDAMIENTO------------------------");
            System.out.println("-- ---cerrarAgendamiento()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MA_CONEXION = devolverConex();
            MA_SENTENCIA = MA_CONEXION.createStatement();
            int cantResul = MA_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
//                if (varOperacion == 2) {
//                    tipoRespuesta = "1";
////                    respuesta = "Se ha Modificado con Exito.";
//                } else {
                    tipoRespuesta = "1";
////                    respuesta = "Se ha Registrado con Exito.";
//                }
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    } // end method 
    
    
    
    /*
    METODO PARA CARGAR EL COMBO ESTADO QUE UTILIZO EN LA PAGINA PRINCIPAL DE AGENDAMIENTO PARA EL PERFIL DE SECRETARIO 
    */
    public Map<String, String> cargarComboEstado(String paramEstado) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramEstado == null || paramEstado.isEmpty() || paramEstado.equalsIgnoreCase("0") || paramEstado.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("T", "TODOS");
            lista.put("A", "ACTIVO");
            lista.put("C", "CERRADO");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramEstado.equalsIgnoreCase("ACTIVO") || paramEstado.equalsIgnoreCase("A")) {
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
                lista.put("T", "TODOS");
            } else if (paramEstado.equalsIgnoreCase("TODOS") || paramEstado.equalsIgnoreCase("T")) {
                lista.put("T", "TODOS");
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
            } else {
                lista.put("C", "CERRADO");
                lista.put("A", "ACTIVO");
                lista.put("T", "TODOS");
            }
        }
        return lista;
    } // end method 
    
    
    
    /*
    METODO PARA CARGAR EL COMBO ESTADO QUE UTILIZO EN LA PAGINA PRINCIPAL DE AGENDAMIENTO PARA EL PERFIL DE SECRETARIO 
    */
    public Map<String, String> cargarComboEstadoDetAgen(String paramEstado, String PARAM_IDPERFIL_USER, ModelPerfil metodosPerfil) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        // RECUPERO EL IDPERFIL DEL USUARIO Y VERIFICO QUE CLASE DE USUARIO ES Y DEPENDIENDO DE ESO MODIFICO LA BANDERA 
//        String IDPERFIL = (String) PARAM_SESION.getAttribute("IDPERFIL");
        int VAR_IDPERFIL_ESTADO_VALI = 0;
        if (metodosPerfil.isPerfilPaciente(PARAM_IDPERFIL_USER)==true) { // SI EL PERFIL ES PACIENTE ENTONCES NO LE MOSTRARE LAS CUENTAS ANULADAS 
            VAR_IDPERFIL_ESTADO_VALI = 1; // SI FUERA PERFIL PACIENTE ENTONCES LE AGREGO UNO, YA QUE AL PERFIL PACIENTE ES EL UNICO PERFIL AL QUE NO LE QUIERO MOSTRAR LA OPCION DE "ANULADO" PARA EVITAR QUE FILTRE POR ESE ESTADO 
        }
        
        if (paramEstado == null || paramEstado.isEmpty() || paramEstado.equalsIgnoreCase("0") || paramEstado.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("T", "TODOS");
            lista.put("A", "ACTIVO");
            lista.put("C", "CERRADO");
            lista.put("P", "PENDIENTE");
            if (VAR_IDPERFIL_ESTADO_VALI == 0) {
                lista.put("X", "ANULADO");
            }
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramEstado.equalsIgnoreCase("ACTIVO") || paramEstado.equalsIgnoreCase("A")) {
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
                lista.put("P", "PENDIENTE");
                if (VAR_IDPERFIL_ESTADO_VALI == 0) {
                    lista.put("X", "ANULADO");
                }
                lista.put("T", "TODOS");
            } else if (paramEstado.equalsIgnoreCase("TODOS") || paramEstado.equalsIgnoreCase("T")) {
                lista.put("T", "TODOS");
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
                lista.put("P", "PENDIENTE");
                if (VAR_IDPERFIL_ESTADO_VALI == 0) {
                    lista.put("X", "ANULADO");
                }
            } else if (paramEstado.equalsIgnoreCase("PENDIENTE") || paramEstado.equalsIgnoreCase("P")) {
                lista.put("P", "PENDIENTE");
                if (VAR_IDPERFIL_ESTADO_VALI == 0) {
                    lista.put("X", "ANULADO");
                }
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
                lista.put("T", "TODOS");
            } else if (paramEstado.equalsIgnoreCase("ANULADO") || paramEstado.equalsIgnoreCase("X")) {
                lista.put("X", "ANULADO");
                lista.put("A", "ACTIVO");
                lista.put("C", "CERRADO");
                lista.put("P", "PENDIENTE");
                lista.put("T", "TODOS");
            } else {
                lista.put("C", "CERRADO");
                lista.put("P", "PENDIENTE");
                if (VAR_IDPERFIL_ESTADO_VALI == 0) {
                    lista.put("X", "ANULADO");
                }
                lista.put("A", "ACTIVO");
                lista.put("T", "TODOS");
            }
        }
        return lista;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA ABRIR UNA NUEVA CABECERA DE AGENDAMIENTO EN CASO DE QUE LA QUE TENGA EN ESTADO ACTIVO, HAYA ALCANZADO EL LIMITE DE AGENDAMIENTO, LA CIERRE Y NECESITE ABRIR UNA NUEVA PARA PODER GUARDAR EL DETALLE DEL AGENDAMIENTO DEL PACIENTE 
    private void abrirNuevoAgendamiento(String IDAGENDAMIENTO, String IDCLINICA, String IDMEDICO, String IDESPECIALIDAD, String ESTADO, String USU_ALTA, String FEC_ALTA) {
        try {
            String sql = "INSERT INTO ope_agendamiento(IDAGENDAMIENTO, "
                + "IDCLINICA, IDMEDICO, IDESPECIALIDAD, "
                + "ESTADO, USU_ALTA, FEC_ALTA) \n" +
                "VALUES('"+IDAGENDAMIENTO+"', "
                + "'"+IDCLINICA+"', '"+IDMEDICO+"', '"+IDESPECIALIDAD+"', "
                + "'"+ESTADO+"', '"+USU_ALTA+"', '"+FEC_ALTA+"') \n";
            System.out.println("--------------------MODEL_AGENDAMIENTO-------------------------");
            System.out.println("-- ---abrirNuevoAgendamiento()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MA_CONEXION = devolverConex();
            MA_SENTENCIA = MA_CONEXION.createStatement();
            /*int cantResul = */MA_SENTENCIA.executeUpdate(sql);
//            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
//                if (varOperacion == 2) {
//                    tipoRespuesta = "1";
////                    respuesta = "Se ha Modificado con Exito.";
//                } else {
//                    tipoRespuesta = "1";
////                    respuesta = "Se ha Registrado con Exito.";
//                }
//
//            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
//                tipoRespuesta = "2";
////                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
//            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    // METODO QUE UTILIZO PARA CONTROLAR LA CANTIDAD DE TIEMPO QUE PASA ENTRE LA HORA DE AGENDAMIENTO Y DEL ANTERIOR AGENDAMIENTO A ESTE, EN CASO DE QUE EXISTA UN AGENDAMIENTO PREVIO, ENTONCES CONTROLO LA HORA DE ESE CON EL DE AHORA SEGUN ESTE ESTABLECIDO EN LA CONFIG DE AGEND 
    public boolean ctrlIntervMinEntreAgend(HttpSession PARAM_SESION, String PARAM_IDCLINICA, String PARAM_IDMEDICO, String PARAM_IDESPECIALIDAD, String PARAM_FECHA_AGEND, String PARAM_HORA_AGEND) {
        boolean VALOR = false;
        System.out.println(".");
            System.out.println(".");
            System.out.println(".");System.out.println(".");
            System.out.println(".");
            System.out.println(".       ________ctrlIntervMinEntreAgend()__________");
            System.out.println(".");System.out.println(".");
            System.out.println(".");
            System.out.println(".");
//        // RECUPERO EL IDAGENDAMIENTO COMO LO HAGO EN EL METODO DE GUARDAR CAB 
//        String IDAGENDAMIENTO = ctrlIDAgend(PARAM_SESION, PARAM_IDMEDICO, PARAM_IDESPECIALIDAD, PARAM_IDCLINICA);
//        System.out.println("_   __GUARDAR__ID_AGENDAMIENTO:   :"+IDAGENDAMIENTO);
        // RECUPERO EL CANTIDAD DE MINUTOS DE INTERVALO ENTRE AGENDAMIENTOS 
        String MIN_INTERVALO_ENTRE_AGEND = traerIntervMinEntreAgend(PARAM_IDCLINICA);
        System.out.println("_   __GUARDAR__MIN_INTERVALO_ENTRE_AGEND:   :"+MIN_INTERVALO_ENTRE_AGEND);
        
        // VERIFICO 
        // SI EL IDAGENDAMIENTO ES DISTINTO A VACIO Y A NULO ENTONCES PROSEGUIRIA CON EL METODO PARA VALIDAR LA HORA DE LA FECHA DE AGENDAMIENTO 
//        if (!IDAGENDAMIENTO.isEmpty() || IDAGENDAMIENTO != null) {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".               _FECH_AGEND_PAC:  :"+PARAM_FECHA_AGEND);
            System.out.println(".               _HORA_AGEND_PAC:  :"+PARAM_HORA_AGEND);
            String HORA_AGEND = PARAM_HORA_AGEND.replace(":", "");
            System.out.println(".               _HORA_AGEND_PAC:  :"+HORA_AGEND);
            System.out.println(".               _HORA_AGEND_PAC:  :"+PARAM_HORA_AGEND.substring(0,2));
            System.out.println(".               _HORA_AGEND_PAC:  :"+PARAM_HORA_AGEND.substring(3,5));
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            try {
                String sql = "SELECT cab.IDAGENDAMIENTO, det.FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.ITEM \n" +
                    "FROM ope_agendamiento cab \n" +
                    "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO NOT IN ('X','C') \n" +
                    "WHERE cab.IDMEDICO = '"+PARAM_IDMEDICO+"' \n" +
                    "AND cab.IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
                    "-- AND cab.IDESPECIALIDAD = '"+PARAM_IDESPECIALIDAD+"' \n" +
                    "AND cab.ESTADO = 'A' \n" +
                    "AND DATE_FORMAT(det.FECHA_AGEN,'%Y-%m-%d') = '"+PARAM_FECHA_AGEND+"' \n";
                
//                String sql = "SELECT FECHA_AGEN, DATE_FORMAT(HORA, '%H:%i') AS HORA, ITEM \n" +
//                    "FROM ope_agendamiento_det \n" +
//                    "WHERE IDAGENDAMIENTO = '"+IDAGENDAMIENTO+"' \n" +
//                    "AND FECHA_AGEN = '"+PARAM_FECHA_AGEND+"' \n" +
//                    "AND ESTADO IN ('A', 'P') \n" +
//                    "ORDER BY ITEM ASC \n";
                System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
                System.out.println("-- ---ctrlIntervMinEntreAgend()--------     "+sql);
                System.out.println("--------------------------------------------------------------------");
                
                // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
                MA_RESULTADO = consultaBD(sql);
                
                while(MA_RESULTADO.next() == true) {
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".       ________WHILE_________");
                    System.out.println(".           _ID_A:  :"+MA_RESULTADO.getString("IDAGENDAMIENTO"));
                    System.out.println(".           _ITEM:  :"+MA_RESULTADO.getString("ITEM"));
                    System.out.println(".           _HORA:  :"+MA_RESULTADO.getString("HORA"));
                    System.out.println(".           _FECHA: :"+MA_RESULTADO.getString("FECHA_AGEN"));
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    String ITEM = MA_RESULTADO.getString("ITEM");
                    System.out.println("_   _ITEM: :"+ITEM);
                    String FECHA_AGEN = MA_RESULTADO.getString("FECHA_AGEN");
                    System.out.println("_   _FECHA_AGEN: :"+FECHA_AGEN);
                    String HORA = MA_RESULTADO.getString("HORA");
                    System.out.println("_   _HORA: :"+HORA);
                    String HORA_DET = HORA.replace(":", "");
                    System.out.println("_   _HORA_DETALLE: :"+HORA_DET);
                    System.out.println(".");
                    System.out.println(".");
                    System.out.println(".");
                    
                    // SI SE ENCUENTRA LINEA DEL DETALLE ENTONCES SI CONTINUARIA 
                    if (!ITEM.isEmpty() || ITEM != null) {
                        String HORA_MAYOR, HORA_MENOR;
                        // nueva var / VARIABLE QUE LO UTILIZO COMO LA DIFERENCIA DE MINUTOS 
                        int cant_restar_cab = 0;
                        // HAGO ESTO PARA EVITAR QUE LA RESTA LUEGO DE UN VALOR NEGATIVO 
                        if (Integer.parseInt(HORA_AGEND) < Integer.parseInt(HORA_DET)) {
                            System.out.println("__IF_____");
                            HORA_MAYOR = HORA_DET;
                            HORA_MENOR = HORA_AGEND;
                            System.out.println("_   _i = HORA_AGEND :    :"+HORA_AGEND);
                            System.out.println("_   _<= HORA_DET :    :"+HORA_DET);
                            for (int i = Integer.parseInt(HORA_AGEND); i <= Integer.parseInt(HORA_DET); i++) {
                                System.out.println("_   ___FOR___  i:"+i);
//                                cant_restar_cab = cant_restar_cab + 1;
                                System.out.println("_   _   _cant_restar_cab:  :"+cant_restar_cab);
                                System.out.println("_   _SUB_STRING_PRI_1:  :"+String.valueOf(i).substring(0,2));
                                /*
                                * OBSERVACION: 
                                    EL BLOQUE DE CODIGO ES EL MISMO, LO UNICO QUE CAMBIA ENTRE UN BLOQUE Y OTRO ES QUE AL BLOQUE DEL IF LE AGREGO UN CERO AL HACER LOS SUBSTRING AL CONVERTIR EN INTEGER 
                                    Y AGREGO ESTE CERO PORQUE AL TRANSFORMAR UN STRING A INTEGER, LOS NUMEROS MENORES A 10 NO LE AGREGA EL CERO QUE ESTA ANTES QUE EL NUMERO EJ.: 09, 01... SE QUEDAN COMO 9, 1...
                                    Y AL PERDER ESTE CERO, LA CANTIDAD ES DE 3 Y NO DE 4, ENTONCES EL SUBSTRING SUELTA UNA EXCEPCION (java.lang.StringIndexOutOfBoundsException) POR NO TENER LA CANTIDAD QUE CORTE QUE ESTOY PIDIENDO 
                                    Y CON ESTA CONDICIONAL CONTROLO SI EL PRIMERO NUMERO DE LA HORA DE AGENDAMIENTO ES EL CERO, ENTONCES AL HACER LOS SUBSTRING, LE CONVIERTO A STRING EL INTEGER Y LE AGREGO EL CERO AL STRING, Y AL HACER EL SUBSTRING YA CUENTA CON EL CERO QUE AGREGO Y CUMPLE CON LA CANTIDAD DE 4 PARA QUE NO SALTE LA VALIDACION 
                                */
                                if ((HORA_AGEND.substring(0, 1)).equals("0")) { // ------__AGREGANDO_CERO_ANTES_DEL_CORTE_SUBSTRING_-------------------------------------------------------------
                                    System.out.println("_   _SUB_STRING_PRI_2:  :"+("0"+String.valueOf(i)).substring(2,4)+" == 60");
                                    if (Integer.parseInt(("0"+String.valueOf(i)).substring(2,4)) == 60) {
                                        System.out.println("_   _   _FOR____IF______");
                                        i = Integer.parseInt(("0"+String.valueOf(i)).substring(0,2))+1;
                                        System.out.println("_   _   _   _i:  :"+i);
                                        String uni = String.valueOf(i)+"00";
                                        System.out.println("_   _   _   _uni:  :"+uni);
                                        i = Integer.parseInt(uni);
                                        System.out.println("_   _   _   _resul_i:  :"+i);
                                        System.out.println("_   _   ________________");
                                    } // END IF 60 
                                    else {
                                        cant_restar_cab = cant_restar_cab + 1;
                                    }
                                } else { // ------__COMO_LO_HACIA_NORMAL_-------------------------------------------------------------
                                    System.out.println("_   _SUB_STRING_PRI_2:  :"+String.valueOf(i).substring(2,4)+" == 60");
                                    if (Integer.parseInt(String.valueOf(i).substring(2,4)) == 60) {
                                        System.out.println("_   _   _FOR____IF______");
                                        i = Integer.parseInt(String.valueOf(i).substring(0,2))+1;
                                        System.out.println("_   _   _   _i:  :"+i);
                                        String uni = String.valueOf(i)+"00";
                                        System.out.println("_   _   _   _uni:  :"+uni);
                                        i = Integer.parseInt(uni);
                                        System.out.println("_   _   _   _resul_i:  :"+i);
                                        System.out.println("_   _   ________________");
                                    } // END IF 60 
                                    else {
                                        cant_restar_cab = cant_restar_cab + 1;
                                    }
                                } // -------------------------------------------------------------------------
                            } // END FOR 
                        } else if (Integer.parseInt(HORA_AGEND) > Integer.parseInt(HORA_DET)) {
                            System.out.println("__ELSE_IF______");
                            HORA_MAYOR = HORA_AGEND;
                            HORA_MENOR = HORA_DET;
                            System.out.println("_   _i = HORA_DET :    :"+HORA_DET);
                            System.out.println("_   _<= HORA_AGEND :   :"+HORA_AGEND);
                            for (int i = Integer.parseInt(HORA_DET); i < Integer.parseInt(HORA_AGEND); i++) {
                                System.out.println("_   ___FOR___  i:"+i);
//                                cant_restar_cab = cant_restar_cab + 1;
                                System.out.println("_   _   _cant_restar_cab:  :"+cant_restar_cab);
                                System.out.println("_   _SUB_STRING_PRI_1:  :"+String.valueOf(i).substring(0,2));
                                /*
                                * OBSERVACION: 
                                    EL BLOQUE DE CODIGO ES EL MISMO, LO UNICO QUE CAMBIA ENTRE UN BLOQUE Y OTRO ES QUE AL BLOQUE DEL IF LE AGREGO UN CERO AL HACER LOS SUBSTRING AL CONVERTIR EN INTEGER 
                                    Y AGREGO ESTE CERO PORQUE AL TRANSFORMAR UN STRING A INTEGER, LOS NUMEROS MENORES A 10 NO LE AGREGA EL CERO QUE ESTA ANTES QUE EL NUMERO EJ.: 09, 01... SE QUEDAN COMO 9, 1...
                                    Y AL PERDER ESTE CERO, LA CANTIDAD ES DE 3 Y NO DE 4, ENTONCES EL SUBSTRING SUELTA UNA EXCEPCION (java.lang.StringIndexOutOfBoundsException) POR NO TENER LA CANTIDAD QUE CORTE QUE ESTOY PIDIENDO 
                                    Y CON ESTA CONDICIONAL CONTROLO SI EL PRIMERO NUMERO DE LA HORA DE AGENDAMIENTO ES EL CERO, ENTONCES AL HACER LOS SUBSTRING, LE CONVIERTO A STRING EL INTEGER Y LE AGREGO EL CERO AL STRING, Y AL HACER EL SUBSTRING YA CUENTA CON EL CERO QUE AGREGO Y CUMPLE CON LA CANTIDAD DE 4 PARA QUE NO SALTE LA VALIDACION 
                                */
                                if ((HORA_DET.substring(0, 1)).equals("0")) {// OBS_2: PREGUNTO AL REVES PORQUE EN ESTE ELSE-IF LA HORA_DET ES MENOR Y NO LA HORA_AGEND // ------__AGREGANDO_CERO_ANTES_DEL_CORTE_SUBSTRING_-------------------------------------------------------------
                                    System.out.println("_   _SUB_STRING_PRI_2:  :"+("0"+String.valueOf(i)).substring(2,4)+" == 60");
                                    if (Integer.parseInt(("0"+String.valueOf(i)).substring(2,4)) == 60) {
                                        System.out.println("_   _   _FOR____IF______");
                                        i = Integer.parseInt(("0"+String.valueOf(i)).substring(0,2))+1;
                                        System.out.println("_   _   _   _i:  :"+i);
                                        String uni = String.valueOf(i)+"00";
                                        System.out.println("_   _   _   _uni:  :"+uni);
                                        i = Integer.parseInt(uni);
                                        System.out.println("_   _   _   _resul_i:  :"+i);
                                        System.out.println("_   _   ________________");
                                    } // END IF 60 
                                    else {
                                        cant_restar_cab = cant_restar_cab + 1;
                                    }
                                } else { // ------__COMO_LO_HACIA_NORMAL_-------------------------------------------------------------
                                    System.out.println("_   _SUB_STRING_PRI_2:  :"+String.valueOf(i).substring(2,4)+" == 60");
                                    if (Integer.parseInt(String.valueOf(i).substring(2,4)) == 60) {
                                        System.out.println("_   _   _FOR____IF______");
                                        i = Integer.parseInt(String.valueOf(i).substring(0,2))+1;
                                        System.out.println("_   _   _   _i:  :"+i);
                                        String uni = String.valueOf(i)+"00";
                                        System.out.println("_   _   _   _uni:  :"+uni);
                                        i = Integer.parseInt(uni);
                                        System.out.println("_   _   _   _resul_i:  :"+i);
                                        System.out.println("_   _   ________________");
                                    } // END IF 60 
                                    else {
                                        cant_restar_cab = cant_restar_cab + 1;
                                    }
                                } // -------------------------------------------------------------------------
                            } // END FOR 
                        } else {
                            System.out.println("__ELSE_______");
                            HORA_MAYOR = HORA_DET;
                            HORA_MENOR = HORA_AGEND;
                            // OBSERVACION: NO INTANCIO A LA VARIABLE "cant_restar_cab" CON CERO COMO VALOR POR QUE LA VARIABLE YA NACE CON ESE VALOR, Y COMO NO ENTRA EN EL IF NI EN EL ELSE-IF ENTONCES SU VALOR NO VA A CAMBIAR Y ESTARIA REDUNDANTE VOLVER A CARGARLO ACA A MENOS QUE NAZCA SIN INSTANCIA COSA QUE TENDRIA COMO VALOR NULL 
                        }
                        System.out.println(".");
                        System.out.println(".");
                        System.out.println("_   _   __HORA_MAYOR:  :"+HORA_MAYOR);
                        System.out.println("_   _   __HORA_MENOR:  :"+HORA_MENOR);
                        System.out.println("_   _   __cant_restar_cab:  :"+cant_restar_cab);
                        System.out.println("_   _____________________END_FOR________________________");
                        
                        // COMPARAR AHORA LOS MINUTOS DE LA RESTA CON LOS MINUTOS QUE ESTAN DEFINIDOS COMO INTERVALOS ENTRE AGENDAMIENTOS 
                        if (cant_restar_cab < Integer.parseInt(MIN_INTERVALO_ENTRE_AGEND)) {
                            VALOR = true;
//                            PARAM_SESION.setAttribute("VALI_INTERV_DIFERENCIA_MIN", cant_restar_cab);
                            PARAM_SESION.setAttribute("VALI_INTERV_MIN_ENTRE_AGEND", MIN_INTERVALO_ENTRE_AGEND);
                            PARAM_SESION.setAttribute("VALI_INTERV_HORA_AGEND_DET", HORA);
                            break; // CUANDO LAS HORAS COINCIDAN CON LA COLISION 
                        }
                    }
                } // end while 
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
            }
//        } // end if id-agendamiento
            System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println(".end_method_");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
        return VALOR;
    }
    
    
    // METODO QUE UTILIZO PARA TRAER LA CANTIDAD DE TIEMPO EN MINUTOS ENTRE UN AGENDAMIENTO Y OTRO 
    private String traerIntervMinEntreAgend(String PARAM_IDCLINICA) {
        String VALOR = "";
        try {
            String sql = "SELECT IDCONFIGAGEND, INTERV_MIN_ENTRE_AGEND, DESC_AGEND \n" +
                "FROM sys_config_agend \n" +
                "WHERE IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
                "AND ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traerIntervMinEntreAgend()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                VALOR = MA_RESULTADO.getString("INTERV_MIN_ENTRE_AGEND");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    }
    
    
//    // METODO QUE UTILIZO PARA TRAER LA ULTIMA FECHA DE AGENDAMIENTO DEL DETALLE DEL AGENDAMIENTO 
//    private String traerHoraUltFechaAgendDet(String PARAM_IDAGENDAMIENTO, String PARAM_FECHA_AGEND) {
//        String VALOR = "";
//        try {
//            String sql = "SELECT FECHA_AGEN, DATE_FORMAT(HORA, '%H:%i') AS HORA, ITEM \n" +
//                "FROM ope_agendamiento_det \n" +
//                "WHERE IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n" +
//                "AND FECHA_AGEN = '"+PARAM_FECHA_AGEND+"' \n" +
//                "AND ESTADO IN ('A', 'P') \n" +
//                "ORDER BY ITEM DESC \n" +
//                "LIMIT 1 \n";
//            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
//            System.out.println("-- ---traerHoraUltFechaAgendDet()--------     "+sql);
//            System.out.println("--------------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MA_RESULTADO = consultaBD(sql);
//            
//            while(MA_RESULTADO.next() == true) {
//                VALOR = MA_RESULTADO.getString("HORA");
//            }
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return VALOR;
//    }
    
    
    
    // METODO QUE UTILIZO PARA CONTROLAR LA EXISTENCIA DE UNA CONFIGURACION DE AGENDAMIENTO PARA LA CLINICA DONDE SE QUIERA AGENDAR EL PACIENTE 
    public boolean ctrlConfigAgendCli(String PARAM_IDCLINICA) {
        boolean VALOR = true; // true: se cumple y no tiene config / false: no se cumple y cuenta con una config 
        try {
            String sql = "SELECT '*' FROM sys_config_agend \n" +
                "WHERE IDCLINICA = '"+PARAM_IDCLINICA+"' \n" +
                "AND ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---ctrlConfigAgendCli()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while(MA_RESULTADO.next() == true) {
                System.out.println("_   ___while___  *  _____el_select_cuenta_con_rows______");
                VALOR = false;
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return VALOR;
    }
    
    
    
    // METODO QUE UTILIZO PARA PODER RECUPERAR LOS AGENDAMIENTOS QUE SE HAYAN HECHO EN LA FECHA SELECCIONADA DEL CALENDARIO AL MOMENTO DE ESTAR CARGANDO O AÑADIENDO UN NUEVO AGENDAMIENTO, ASI EL SECRETARIO O ADMINISTRADOR PODRA TOMAR COMO REFERENCIA LOS AGENDAMIENTOS QUE TENGA EL DIA PARA CARGAR LA HORA Y NO PISAR A OTRO AGENDAMIENTO YA HECHO (IGUAL SALTARIA UNA VALIDACION EN CASO DE CONFIGURAR LOS AGENDAMIENTOS CON UN TIEMPO EN MINUTOS ENTRE AGENDAMIENTOS) 
    public List traerAgendamientosFecha(HttpSession PARAM_SESION, String PARAM_FECHA) {
        List<BeanAgendamiento> lista = new ArrayList<>();
        // OBSERVACION_DEL_PORQUE_NO_AGREGUE_FILTRO_DE_CLINICA_AL_SELECT: 
        // PUEDO AGREGAR UN BLOQUE DE CODIGO PARA QUE EL SELECT SOLO TRAIGA LOS AGENDAMIENTOS EXISTENTES DE UNA CLINICA Y NO TODOS LOS AGENDAMIENTOS DE TODAS LAS CLINICAS PARA NO SOBRECARGAR EL SELECT Y EL TIEMPO DE ESPERA SEA MUY LARGO 
        // PERO NO LE AGREGO NINGUNA VALIDACION POR EL MOMENTO PORQUE ES BUENO TAMBIEN PODER VER TODOS LOS AGENDAMIENTOS QUE HAYA EN LA FECHA E INCLUSIVO DE OTRAS CLINICAS PARA QUE EN CASO DE QUE SE QUIERA AGENDAR EN OTRA CLINICA POR UN TEMA DE HORARIOS EL USUARIO PUEDA SABER EN QUE CLINICA 
            //LAS CLINICAS DEBERIAN DE SER LAS DEL MEDICO O VER LA FORMA PARA NO FILTRAR POR TODAS LAS CLINICAS (PUEDE SER RECIBIENDO LA CANTIDAD DE CLINICAS QUE DEVUELVO A LA MISMA PAGINA O OTRA FORMA) 
            //VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT cab.IDAGENDAMIENTO, cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
                "FROM ope_agendamiento cab \n" +
                "LEFT JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A', 'P') \n" +
                "WHERE cab.ESTADO <> 'X' \n" +
                "AND det.FECHA_AGEN = '"+PARAM_FECHA+"' \n" +
                "ORDER BY det.FECHA_AGEN ASC, HORA ASC \n";
            System.out.println("------------------------MODEL_AGENDAMIENTO--------------------------");
            System.out.println("-- ---traerAgendamientosFecha()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MA_RESULTADO = consultaBD(sql);
            
            while (MA_RESULTADO.next()) {
                BeanAgendamiento datos = new BeanAgendamiento();
                    datos.setOA_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOAD_IDAGENDAMIENTO(MA_RESULTADO.getString("IDAGENDAMIENTO"));
                    // CABECERA 
                    datos.setOA_IDESPECIALIDAD(MA_RESULTADO.getString("IDESPECIALIDAD"));
                    datos.setOA_IDMEDICO(MA_RESULTADO.getString("IDMEDICO"));
                    datos.setOA_IDCLINICA(MA_RESULTADO.getString("IDCLINICA"));
                    datos.setOA_ESTADO(MA_RESULTADO.getString("ESTADO_CAB"));
                    // DETALLE 
                    datos.setOAD_ITEM(MA_RESULTADO.getString("ITEM"));
                    datos.setOAD_FECHA_AGEN(MA_RESULTADO.getString("FECHA_AGEN"));
                    datos.setOAD_HORA(MA_RESULTADO.getString("HORA"));
                    datos.setOAD_IDPACIENTE(MA_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAD_NROPACIENTEFICHA(MA_RESULTADO.getString("NROPACIENTEFICHA"));
                    datos.setOAD_MOTIVO(MA_RESULTADO.getString("MOTIVO"));
                    datos.setOAD_VISITATIPO(MA_RESULTADO.getString("VISITATIPO"));
                    datos.setOAD_COMENTARIO(MA_RESULTADO.getString("COMENTARIO"));
                    datos.setOAD_ESTADO(MA_RESULTADO.getString("ESTADO_DET"));
                    datos.setOAD_FEC_ATENCION(MA_RESULTADO.getString("FEC_ATENCION"));
                    datos.setOAD_IDFACTURA(MA_RESULTADO.getString("IDFACTURA"));
                    datos.setOAD_AGENDADO(MA_RESULTADO.getString("AGENDADO"));
//                    datos.setOAD_IDATENCION_PAC(MA_RESULTADO.getString("IDATENCION"));
                lista.add(datos);
            } // END WHILE 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelAgendamiento.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    
} // END CLASS 