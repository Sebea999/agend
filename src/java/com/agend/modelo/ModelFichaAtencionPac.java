/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo;

import com.agend.config.ConfigurationProperties;
import com.agend.interfaz.CRUD;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanAgendamiento;
import com.agend.javaBean.BeanAtencionPaciente;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelFichaAtencionPac implements CRUD {
    
    
    public String VAR_ID_CLINICA = "";
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_FICHA_ATENCION_PAC.-------");
        try {
//            String host = "jdbc:mysql://sql10.freesqldatabase.com:3306/";
//            String bd = "sql10410496";
//            String user = "sql10410496";
//            String pass = "c2AijebkXg";
            // datos-local.-
//            String host = "jdbc:mysql://localhost/";
//            String bd = "odonto";
//            String user = "root";
//            String pass = "admin";
            ConfigurationProperties properties = new ConfigurationProperties();
            String host = properties.getDB_HOST();
            String bd = properties.getDB_NAME();
            String configdb = properties.getDB_CONFIG();
            String user = properties.getDB_USER();
            String pass = properties.getDB_PASS();
            System.out.println(".       __host:   "+host);
            System.out.println(".       __db:     "+bd);
            System.out.println(".   __config-db:  "+configdb);
            System.out.println(".       __user:   "+user);
            System.out.println(".       __pass:   "+pass);
//            Class.forName("com.mysql.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            MFAP_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MFAP_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MFAP_CONEXION;
    } // END METHOD  
    
    
    
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
    private static Connection MFAP_CONEXION = null;
    private static Statement MFAP_SENTENCIA = null;
    private static ResultSet MFAP_RESULTADO = null;
    
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
            MFAP_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MFAP_RESULTADO = MFAP_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MFAP_RESULTADO;
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
            if (MFAP_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MFAP_RESULTADO.close();
            }
            if (MFAP_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MFAP_SENTENCIA.close();
            }
            if (MFAP_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MFAP_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    
    
    
    /*
    OBSERVACION: FILTRO Y TRAIGO UNA CANTIDAD DE REGISTROS QUE ES EL NRO POR DEFECTO QUE VA A MOSTRAR EN EL COMBO PARA TRAER LOS REGISTROS 
    */
    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        List<BeanAgendamiento> lista = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        String ID_MEDICO = (String) PARAM_SESION.getAttribute("IDPERSONA");
        try {
            /*
            OBSERVACION:   USO EL MISMO SELECT QUE EN "pagAgendVer" (VER AGENDAMIENTO DEL MEDICO) 
                           PARA QUE EL MEDICO PUEDA VER TODOS LOS PACIENTES QUE TENGA AGENDADO EN EL DIA 
            */
            String sql = "SELECT cab.IDAGENDAMIENTO, det.ITEM AS ITEM_AGEN, \n" +
                "COALESCE(( SELECT ss_oap.IDATENCIONPAC FROM ope_atencion_paciente ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) AS IDATENCION_PAC, \n" +
                "cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
                "WHERE cab.ESTADO = 'A' \n" +
                "AND cab.IDMEDICO = '3' \n" +
//                "AND cab.IDMEDICO = '"+ID_MEDICO+"' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
    /* OBSERVACION: ESTA LINEA DEL WHERE ES PARA EVITAR TRAER UNA LINEA DEL AGENDAMIENTO DETALLE SI ES QUE EL PACIENTE YA CUENTA CON UNA FICHA DE ATENCION CREADA, PERO LA COMENTE PORQUE LE TRAIGO LA MISMA LINEA PERO EN VEZ DE MOSTRARLE EL BOTON PARA CREAR UNA FICHA DE ATENCION LE MUESTRO UN BOTON PARA QUE PUEDA EDITAR LA FICHA DE ATENCION EXISTENTE SOBRE ESE AGENDAMIENTO  */
//                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDATENCIONPAC \n" +
//                "FROM ope_agendamiento_det ss_agen_det \n" +
//                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
//                "JOIN ope_atencion_paciente ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
//                "WHERE ss_agen_det.ESTADO = 'A' AND ss_agen_cab.IDMEDICO = cab.IDMEDICO AND ss_agen_cab.IDCLINICA = cab.IDCLINICA )) \n" +
                "ORDER BY FECHA_AGEN ASC, HORA ASC \n" +
                "LIMIT "+metodosIniSes.minNroCbxCantFil()+" \n";
            
            
//            String sql = "SELECT cab.IDAGENDAMIENTO, det.ITEM AS ITEM_AGEN, \n" +
//                "cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
//                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
//                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
//                "FROM ope_agendamiento cab \n" +
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
//                "WHERE cab.ESTADO = 'A' \n" +
//                "AND cab.IDMEDICO = '3' \n" +
////                "AND cab.IDMEDICO = '"+ID_MEDICO+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
////                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
//                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//    /* OBSERVACION: ESTA LINEA DEL WHERE ES PARA EVITAR TRAER UNA LINEA DEL AGENDAMIENTO DETALLE SI ES QUE EL PACIENTE YA CUENTA CON UNA FICHA DE ATENCION CREADA, PERO LA COMENTE PORQUE LE TRAIGO LA MISMA LINEA PERO EN VEZ DE MOSTRARLE EL BOTON PARA CREAR UNA FICHA DE ATENCION LE MUESTRO UN BOTON PARA QUE PUEDA EDITAR LA FICHA DE ATENCION EXISTENTE SOBRE ESE AGENDAMIENTO  */
////                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDATENCIONPAC \n" +
////                "FROM ope_agendamiento_det ss_agen_det \n" +
////                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
////                "JOIN ope_atencion_paciente ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
////                "WHERE ss_agen_det.ESTADO = 'A' AND ss_agen_cab.IDMEDICO = cab.IDMEDICO AND ss_agen_cab.IDCLINICA = cab.IDCLINICA )) \n" +
//                "ORDER BY FECHA_AGEN ASC, HORA ASC \n" +
//                "LIMIT "+metodosIniSes.minNroCbxCantFil()+" \n";


//            String sql = "SELECT cab.IDAGENDAMIENTO, cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
//                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
//                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
//                "FROM ope_agendamiento cab \n" +
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
//                "WHERE cab.ESTADO = 'A' \n" +
//                "AND cab.IDMEDICO = '3' \n" +
////                "AND cab.IDMEDICO = '"+ID_MEDICO+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
////                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
//                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "ORDER BY FECHA_AGEN ASC, HORA ASC \n" ;
////                "LIMIT "+metodosIniSes.minNroCbxCantFil()+" \n";
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-----------------------");
            System.out.println("-- ---cargar_grilla()--------     "+sql);
            System.out.println("------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanAgendamiento datos = new BeanAgendamiento();
                    datos.setOA_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOAD_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOAD_IDATENCION_PAC(MFAP_RESULTADO.getString("IDATENCION_PAC")); // SUBSELECT QUE DEVUELVE EL IDATENCIONPACIENTE EN CASO DE QUE TENGA UNA FICHA DE ATENCION EL PACIENTE (EL MISMO AGENDAMIENTO Y NRO_ITEM) 
                    // CABECERA --
                    datos.setOA_IDESPECIALIDAD(MFAP_RESULTADO.getString("IDESPECIALIDAD"));
                    datos.setOA_IDMEDICO(MFAP_RESULTADO.getString("IDMEDICO"));
                    datos.setOA_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                    datos.setOA_ESTADO(MFAP_RESULTADO.getString("ESTADO_CAB"));
                    // DETALLE --
                    datos.setOAD_ITEM(MFAP_RESULTADO.getString("ITEM"));
                    datos.setOAD_FECHA_AGEN(MFAP_RESULTADO.getString("FECHA_AGEN"));
                    datos.setOAD_HORA(MFAP_RESULTADO.getString("HORA"));
                    datos.setOAD_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAD_NROPACIENTEFICHA(MFAP_RESULTADO.getString("NROPACIENTEFICHA"));
                    datos.setOAD_MOTIVO(MFAP_RESULTADO.getString("MOTIVO"));
                    datos.setOAD_VISITATIPO(MFAP_RESULTADO.getString("VISITATIPO"));
                    datos.setOAD_COMENTARIO(MFAP_RESULTADO.getString("COMENTARIO"));
                    datos.setOAD_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                    datos.setOAD_FEC_ATENCION(MFAP_RESULTADO.getString("FEC_ATENCION"));
                    datos.setOAD_IDFACTURA(MFAP_RESULTADO.getString("IDFACTURA"));
                    datos.setOAD_AGENDADO(MFAP_RESULTADO.getString("AGENDADO"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }

    
    
    /*
     METODO QUE UTILIZO PARA PAGINACION 
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
        if (sesionDatos.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC")));
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
        String ID_MEDICO = (String) sesionDatos.getAttribute("IDPERSONA");
        System.out.println("_       _ID_MEDICO:  :"+ID_MEDICO);
        
        try {
            /*
            OBSERVACION:   USO EL MISMO SELECT QUE EN "pagAgendVer" (VER AGENDAMIENTO DEL MEDICO) 
                           PARA QUE EL MEDICO PUEDA VER TODOS LOS PACIENTES QUE TENGA AGENDADO EN EL DIA 
            */
            String sql = "SELECT cab.IDAGENDAMIENTO, det.ITEM AS ITEM_AGEN, \n" +
                "COALESCE(( SELECT ss_oap.IDATENCIONPAC FROM ope_atencion_paciente ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) AS IDATENCION_PAC, \n" +
                "cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO \n" +
//                // OBS.: LE COMENTO EL ESTADO DEL DETALLE DE AGENDAMIENTO POR QUE AL CREARLE UNA FICHA DE ATENCION A UN AGENDAMIENTO DETALLE, A ESTE LE CAMBIO SU ESTADO Y LO CIERRO PARA NO DEJARLE ABIERTO 
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
                "WHERE cab.ESTADO IN ('A', 'C') \n" +
//                "AND cab.IDMEDICO = '3' \n" +
                "AND cab.IDMEDICO = '"+ID_MEDICO+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
    /* OBSERVACION: ESTA LINEA DEL WHERE ES PARA EVITAR TRAER UNA LINEA DEL AGENDAMIENTO DETALLE SI ES QUE EL PACIENTE YA CUENTA CON UNA FICHA DE ATENCION CREADA, PERO LA COMENTE PORQUE LE TRAIGO LA MISMA LINEA PERO EN VEZ DE MOSTRARLE EL BOTON PARA CREAR UNA FICHA DE ATENCION LE MUESTRO UN BOTON PARA QUE PUEDA EDITAR LA FICHA DE ATENCION EXISTENTE SOBRE ESE AGENDAMIENTO  */
//                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDATENCIONPAC \n" +
//                "FROM ope_agendamiento_det ss_agen_det \n" +
//                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
//                "JOIN ope_atencion_paciente ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
//                "WHERE ss_agen_det.ESTADO = 'A' AND ss_agen_cab.IDMEDICO = cab.IDMEDICO AND ss_agen_cab.IDCLINICA = cab.IDCLINICA )) \n" +
                "ORDER BY FECHA_AGEN ASC, HORA ASC \n"// +
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(cab.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento cab \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO \n" +
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
                "WHERE cab.ESTADO IN ('A', 'C') \n" +
                "AND cab.IDMEDICO = '"+ID_MEDICO+"' \n" +
//                "AND cab.IDMEDICO = '3' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "ORDER BY FECHA_AGEN ASC, HORA ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("----------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
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
            while (MFAP_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);
                
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDATENCION_PAC(MFAP_RESULTADO.getString("IDATENCION_PAC")); // SUBSELECT QUE DEVUELVE EL IDATENCIONPACIENTE EN CASO DE QUE TENGA UNA FICHA DE ATENCION EL PACIENTE (EL MISMO AGENDAMIENTO Y NRO_ITEM) 
                        // CABECERA --
                        datos.setOA_IDESPECIALIDAD(MFAP_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_IDMEDICO(MFAP_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_ESTADO(MFAP_RESULTADO.getString("ESTADO_CAB"));
                        // DETALLE --
                        datos.setOAD_ITEM(MFAP_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MFAP_RESULTADO.getString("FECHA_AGEN"));
                        datos.setOAD_HORA(MFAP_RESULTADO.getString("HORA"));
                        datos.setOAD_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                        datos.setOAD_NROPACIENTEFICHA(MFAP_RESULTADO.getString("NROPACIENTEFICHA"));
                        datos.setOAD_MOTIVO(MFAP_RESULTADO.getString("MOTIVO"));
                        datos.setOAD_VISITATIPO(MFAP_RESULTADO.getString("VISITATIPO"));
                        datos.setOAD_COMENTARIO(MFAP_RESULTADO.getString("COMENTARIO"));
                        datos.setOAD_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                        datos.setOAD_FEC_ATENCION(MFAP_RESULTADO.getString("FEC_ATENCION"));
                        datos.setOAD_IDFACTURA(MFAP_RESULTADO.getString("IDFACTURA"));
                        datos.setOAD_AGENDADO(MFAP_RESULTADO.getString("AGENDADO"));
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
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_FICATEPAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_FICATEPAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_FICATEPAC_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
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
    
    
    
    
    @Override
    public List traer_datos(String idTraer, HttpSession PARAM_SESION) {
        List<BeanAtencionPaciente> lista = new ArrayList<>();
        
//        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String ID_MEDICO = (String) PARAM_SESION.getAttribute("IDPERSONA");
        try {
            String sql = "SELECT cab.IDATENCIONPAC, cab.IDAGENDAMIENTO, cab.IDPACIENTE, \n" +
                "cab.PESO, cab.TALLA, cab.IMC, cab.P_CEFALICO, cab.FC, cab.TEMP, cab.PA, cab.F_RESP, \n" +
                "cab.MOTIVO_CONSULTA, cab.EXPLORACION_FISICA, cab.DIAGNOSTICO, cab.TRATAMIENTO, cab.RECETA, cab.ESTUDIOS_SOLICITADOS, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, det.IDSERVICIO, det.MONTO, det.ESTADO AS ESTADO_DET \n" +
                "FROM ope_atencion_paciente cab \n" +
                "LEFT JOIN ope_atencion_paciente_det det ON cab.IDATENCIONPAC = det.IDATENCIONPAC AND det.ESTADO = 'A' \n" +
                "WHERE cab.IDATENCIONPAC = '"+idTraer+"' \n" +
                "AND cab.ESTADO = 'A' \n" ;
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-----------------------");
            System.out.println("-- ---traer_datos()--------     "+sql);
            System.out.println("------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanAtencionPaciente datos = new BeanAtencionPaciente();
                    // CABECERA --
                    datos.setOAP_IDATENCIONPAC(MFAP_RESULTADO.getString("IDATENCIONPAC"));
                    datos.setOAP_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOAP_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAP_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOAP_TALLA(MFAP_RESULTADO.getString("TALLA"));
                    datos.setOAP_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOAP_P_CEFALICO(MFAP_RESULTADO.getString("P_CEFALICO"));
                    datos.setOAP_FC(MFAP_RESULTADO.getString("FC"));
                    datos.setOAP_TEMP(MFAP_RESULTADO.getString("TEMP"));
                    datos.setOAP_PA(MFAP_RESULTADO.getString("PA"));
                    datos.setOAP_F_RESP(MFAP_RESULTADO.getString("F_RESP"));
                    datos.setOAP_MOTIVO_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_CONSULTA").replaceAll("<br/>","\r\n"));
                    datos.setOAP_EXPLORACION_FISICA(MFAP_RESULTADO.getString("EXPLORACION_FISICA").replaceAll("<br/>","\r\n"));
                    datos.setOAP_DIAGNOSTICO(MFAP_RESULTADO.getString("DIAGNOSTICO").replaceAll("<br/>","\r\n"));
                    datos.setOAP_TRATAMIENTO(MFAP_RESULTADO.getString("TRATAMIENTO").replaceAll("<br/>","\r\n"));
                    datos.setOAP_RECETA(MFAP_RESULTADO.getString("RECETA").replaceAll("<br/>","\r\n"));
                    datos.setOAP_ESTUDIOS_SOLICITADOS(MFAP_RESULTADO.getString("ESTUDIOS_SOLICITADOS").replaceAll("<br/>","\r\n"));
                    datos.setOAP_ESTADO(MFAP_RESULTADO.getString("ESTADO_CAB"));
                    // DETALLE --
                    datos.setOAPD_IDATENCIONPAC(MFAP_RESULTADO.getString("IDATENCIONPAC"));
                    datos.setOAPD_ITEM(MFAP_RESULTADO.getString("ITEM"));
                    datos.setOAPD_IDSERVICIO(MFAP_RESULTADO.getString("IDSERVICIO"));
                    datos.setOAPD_MONTO(MFAP_RESULTADO.getString("MONTO"));
                    datos.setOAPD_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    

    @Override
    public String guardar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String guardar_cab(List<BeanAtencionPaciente> PARAM_LISTA_DET, Object obj) {
        String tipoRespuesta="1"; // 1 = Exito / 2 = Error 
        BeanAtencionPaciente datos = (BeanAtencionPaciente) obj;
        
        // FORMATEAMOS EL VALOR DE LA VARIABLE ESTADO PARA PODER GUARDAR LA INICIAL NOMAS Y NO LA PALABRA COMPLETA 
        String ESTADO = datos.getOAP_ESTADO();
        if (ESTADO.equalsIgnoreCase("INACTIVO") || ESTADO.equalsIgnoreCase("I")) {
            ESTADO = "I";
        } else {
            ESTADO = "A";
        }
        
        try {
            int varOperacion;
            String sql;
            String OAP_IDATENCIONPAC = datos.getOAP_IDATENCIONPAC();
            String OAP_IDAGENDAMIENTO = datos.getOAP_IDAGENDAMIENTO();
            String OAP_ITEM_AGEND_DET = datos.getOAP_ITEM_AGEND_DET();
            String OAP_IDPACIENTE = datos.getOAP_IDPACIENTE();
            String OAP_PESO = datos.getOAP_PESO();
            String OAP_TALLA = datos.getOAP_TALLA();
            String OAP_IMC = datos.getOAP_IMC();
            String OAP_P_CEFALICO = datos.getOAP_P_CEFALICO();
            String OAP_FC = datos.getOAP_FC();
            String OAP_TEMP = datos.getOAP_TEMP();
            String OAP_PA = datos.getOAP_PA();
            String OAP_F_RESP = datos.getOAP_F_RESP();
            String OAP_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_TRATAMIENTO = datos.getOAP_TRATAMIENTO().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_RECETA = datos.getOAP_RECETA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_USU_ALTA = datos.getOAP_USU_ALTA();
            String OAP_FEC_ALTA = datos.getOAP_FEC_ALTA();
            String OAP_ESTADO = datos.getOAP_ESTADO();
            String OAP_IDCLINICA = datos.getOAP_IDCLINICA();
            
            varOperacion = 1; // INSERT 
            sql = "INSERT INTO ope_atencion_paciente(IDATENCIONPAC, IDAGENDAMIENTO, ITEM_AGEND_DET, IDPACIENTE, \n" +
                "PESO, TALLA, IMC, P_CEFALICO, FC, TEMP, PA, F_RESP, \n" +
                "MOTIVO_CONSULTA, EXPLORACION_FISICA, DIAGNOSTICO, TRATAMIENTO, \n" +
                "RECETA, ESTUDIOS_SOLICITADOS, USU_ALTA, FEC_ALTA, ESTADO, IDCLINICA) \n" +
                "VALUES ("+OAP_IDATENCIONPAC+", "+OAP_IDAGENDAMIENTO+", '"+OAP_ITEM_AGEND_DET+"', "+OAP_IDPACIENTE+", \n" +
                "'"+OAP_PESO+"', '"+OAP_TALLA+"', '"+OAP_IMC+"', '"+OAP_P_CEFALICO+"', '"+OAP_FC+"', '"+OAP_TEMP+"', '"+OAP_PA+"', '"+OAP_F_RESP+"', \n" +
                "'"+OAP_MOTIVO_CONSULTA+"', '"+OAP_EXPLORACION_FISICA+"', '"+OAP_DIAGNOSTICO+"', '"+OAP_TRATAMIENTO+"', \n" +
                "'"+OAP_RECETA+"', '"+OAP_ESTUDIOS_SOLICITADOS+"', '"+OAP_USU_ALTA+"', '"+OAP_FEC_ALTA+"', '"+OAP_ESTADO+"', '"+OAP_IDCLINICA+"') \n";
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-------------------------");
            System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
            System.out.println("--------------------------------------------------------------------------");
            
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                if (varOperacion == 2) {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Modificado con Exito.";
                } else {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Registrado con Exito.";
                }
                guardar_det(PARAM_LISTA_DET, OAP_IDATENCIONPAC, OAP_USU_ALTA, OAP_IDAGENDAMIENTO, OAP_ITEM_AGEND_DET);
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    public String guardar_det(List<BeanAtencionPaciente> PARAM_LIST_DET, String PARAM_IDATEPAC, String PARAM_USU_ALTA, String OAP_IDAGENDAMIENTO, String OAP_ITEM_AGEND_DET) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("_   _   __   _   __   _   __* GUARDAR_AP_DET *__   _   __   _   __   _   _");
        // recorro la lista que le paso por parametro para guardar o actualizar el detalle 
        for (BeanAtencionPaciente APDET_ROW : PARAM_LIST_DET) {
            try {
                int varOperacion;
                String sql;
                String OAPD_IDATENCIONPAC = PARAM_IDATEPAC;
                String OAPD_ITEM = APDET_ROW.getOAPD_ITEM();
                String OAPD_IDSERVICIO = APDET_ROW.getOAPD_IDSERVICIO();
                String OAPD_MONTO = APDET_ROW.getOAPD_MONTO().replace(",",".").replace(".","");
                String OAPD_USU_ALTA = PARAM_USU_ALTA;
//                String OAPD_USU_ALTA = APDET_ROW.getOAPD_USU_ALTA();
                String OAPD_FEC_ALTA = FECHA_HORA_HOY;
//                String OAPD_FEC_ALTA = APDET_ROW.getOAPD_FEC_ALTA();
                String OAPD_ESTADO;
//                String OAPD_ESTADO = APDET_ROW.getOAPD_ESTADO();
//                if (OAPD_ESTADO.equalsIgnoreCase("INACTIVO") || OPHD_ESTADO.equalsIgnoreCase("I")) {
//                    OAPD_ESTADO = "I";
//                } else {
                    OAPD_ESTADO = "A"; // VOY A GUARDAR TODAS LAS FILAS CON ESTADO ACTIVO YA QUE ESTE ESTADO LO UTILIZARE PARA MOSTRARLE POR DEFECTO TODAS LAS LINEAS PERO CUANDO EL USUARIO ELIMINE ENTONCES LE INACTIVO NOMAS LA LINEA DEL DETALLE Y SOLAMENTE MOSTRARE LAS LINEAS DEL DETALLE ACTIVAS  
//                }
                
                varOperacion = 1; // INSERT 
                sql = "INSERT INTO ope_atencion_paciente_det(IDATENCIONPAC, ITEM, IDSERVICIO, MONTO, ESTADO, USU_ALTA, FEC_ALTA) \n" +
                    "VALUES('"+OAPD_IDATENCIONPAC+"', '"+OAPD_ITEM+"', '"+OAPD_IDSERVICIO+"', '"+OAPD_MONTO+"', '"+OAPD_ESTADO+"', '"+OAPD_USU_ALTA+"', '"+OAPD_FEC_ALTA+"')  \n";
                System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE------------------------");
                System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
                System.out.println("--------------------------------------------------------------------------");
                
                MFAP_CONEXION = devolverConex();
                MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
                int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
                if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                    if (varOperacion == 2) {
                        tipoRespuesta = "1";
    //                    respuesta = "Se ha Modificado con Exito.";
                    } else {
                        tipoRespuesta = "1";
    //                    respuesta = "Se ha Registrado con Exito.";
                    }
                    controlarCierreAgend(OAP_IDAGENDAMIENTO, OAP_ITEM_AGEND_DET, OAPD_FEC_ALTA);
                } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                    tipoRespuesta = "2";
    //                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
                }
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
            }
        } // END FOR 
        return tipoRespuesta;
    }
    
    
    
    
    public String modificar_cab(List<BeanAtencionPaciente> PARAM_LISTA_DET, Object obj) {
        String tipoRespuesta = "2"; // 1 : exito / 2 : error 
        BeanAtencionPaciente datos = (BeanAtencionPaciente) obj;
        
        try {
            String OAP_IDATENCIONPAC = datos.getOAP_IDATENCIONPAC();
//            String OAP_IDAGENDAMIENTO = datos.getOAP_IDAGENDAMIENTO();
//            String OAP_ITEM_AGEND_DET = datos.getOAP_ITEM_AGEND_DET();
//            String OAP_IDPACIENTE = datos.getOAP_IDPACIENTE();
            String OAP_PESO = datos.getOAP_PESO();
            String OAP_TALLA = datos.getOAP_TALLA();
            String OAP_IMC = datos.getOAP_IMC();
            String OAP_P_CEFALICO = datos.getOAP_P_CEFALICO();
            String OAP_FC = datos.getOAP_FC();
            String OAP_TEMP = datos.getOAP_TEMP();
            String OAP_PA = datos.getOAP_PA();
            String OAP_F_RESP = datos.getOAP_F_RESP();
            String OAP_MOTIVO_CONSULTA = datos.getOAP_MOTIVO_CONSULTA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_EXPLORACION_FISICA = datos.getOAP_EXPLORACION_FISICA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_DIAGNOSTICO = datos.getOAP_DIAGNOSTICO().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_TRATAMIENTO = datos.getOAP_TRATAMIENTO().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_RECETA = datos.getOAP_RECETA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_ESTUDIOS_SOLICITADOS = datos.getOAP_ESTUDIOS_SOLICITADOS().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OAP_USU_ALTA = datos.getOAP_USU_ALTA();
//            String OAP_FEC_ALTA = datos.getOAP_FEC_ALTA();
            String OAP_ESTADO = datos.getOAP_ESTADO();
            String OAP_IDCLINICA = datos.getOAP_IDCLINICA();
            
            String sql = "UPDATE ope_atencion_paciente \n" +
                "SET PESO='"+OAP_PESO+"', TALLA='"+OAP_TALLA+"', IMC='"+OAP_IMC+"', P_CEFALICO='"+OAP_P_CEFALICO+"', \n" + 
                    "FC='"+OAP_FC+"', TEMP='"+OAP_TEMP+"', PA='"+OAP_PA+"', F_RESP='"+OAP_F_RESP+"', \n" +
                    "MOTIVO_CONSULTA='"+OAP_MOTIVO_CONSULTA+"', EXPLORACION_FISICA='"+OAP_EXPLORACION_FISICA+"', DIAGNOSTICO='"+OAP_DIAGNOSTICO+"', " + 
                    "TRATAMIENTO='"+OAP_TRATAMIENTO+"', RECETA='"+OAP_RECETA+"', ESTUDIOS_SOLICITADOS='"+OAP_ESTUDIOS_SOLICITADOS+"', " + 
    //                "USU_ALTA='"+OAP_USU_ALTA+"', FEC_ALTA='"+OAP_FEC_ALTA+"', " + 
                    "ESTADO='"+OAP_ESTADO+"' \n" +
                "WHERE IDATENCIONPAC = '"+OAP_IDATENCIONPAC+"' \n";
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE----------------------");
            System.out.println("-- ---modificar()--------          "+sql);
            System.out.println("------------------------------------------------------------------------");
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
                modificar_det(PARAM_LISTA_DET, OAP_IDATENCIONPAC, OAP_USU_ALTA);
            } else {
                tipoRespuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    public String modificar_det(List<BeanAtencionPaciente> PARAM_LIST_DET, String PARAM_IDATEPAC, String PARAM_USU_ALTA) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("_   _   __   _   __   _   __* MODIFICAR_AP_DET *__   _   __   _   __   _   _");
        // recorro la lista que le paso por parametro para guardar o actualizar el detalle 
        for (BeanAtencionPaciente APDET_ROW : PARAM_LIST_DET) {
            try {
                int varOperacion;
                String sql;
                String OAPD_IDATENCIONPAC = APDET_ROW.getOAPD_IDATENCIONPAC();
                String OAPD_ITEM = APDET_ROW.getOAPD_ITEM();
                String OAPD_IDSERVICIO = APDET_ROW.getOAPD_IDSERVICIO();
                String OAPD_MONTO = APDET_ROW.getOAPD_MONTO().replace(",",".").replace(".","");
                String OAPD_USU_ALTA = PARAM_USU_ALTA;
//                String OAPD_USU_ALTA = APDET_ROW.getOAPD_USU_ALTA();
                String OAPD_FEC_ALTA = FECHA_HORA_HOY;
//                String OAPD_FEC_ALTA = APDET_ROW.getOAPD_FEC_ALTA();
                String OAPD_ESTADO;
//                String OAPD_ESTADO = APDET_ROW.getOAPD_ESTADO();
//                if (OAPD_ESTADO.equalsIgnoreCase("INACTIVO") || OPHD_ESTADO.equalsIgnoreCase("I")) {
//                    OAPD_ESTADO = "I";
//                } else {
                    OAPD_ESTADO = "A"; // VOY A GUARDAR TODAS LAS FILAS CON ESTADO ACTIVO YA QUE ESTE ESTADO LO UTILIZARE PARA MOSTRARLE POR DEFECTO TODAS LAS LINEAS PERO CUANDO EL USUARIO ELIMINE ENTONCES LE INACTIVO NOMAS LA LINEA DEL DETALLE Y SOLAMENTE MOSTRARE LAS LINEAS DEL DETALLE ACTIVAS  
//                }
                
                System.out.println("_  __________________DATOS_____________________");
                System.out.println("_   __PARAM_IDATEPAC: :"+PARAM_IDATEPAC);
                System.out.println("_   __ID_ATENCION:    :"+OAPD_IDATENCIONPAC);
                System.out.println("_   __ITEM:           :"+OAPD_ITEM);
                System.out.println("_   __ID_SERVICIO:    :"+OAPD_IDSERVICIO);
                System.out.println("_   __MONTO:          :"+OAPD_MONTO);
                System.out.println("_   __ESTADO:         :"+OAPD_ESTADO);
                System.out.println("_______________________________________________");
                
                if (OAPD_IDATENCIONPAC == null || OAPD_IDATENCIONPAC.isEmpty()) {
                    varOperacion = 1; // INSERT 
                    OAPD_IDATENCIONPAC = PARAM_IDATEPAC; // YA QUE ESTA NULO EL IDATENCIONPAC POR INGRESAR UN NUEVO SERVICIO, ENTONCES PARA INSERTAR A LA TABLA LE CARGO EL IDATENCIONPAC DE LA CABECERA PARA QUE LA LINEA SEPA CUAL ES SU "PADRE" 
                    // VALIDACION 
                    if (controlIdItemDet(OAPD_IDATENCIONPAC, OAPD_ITEM) == true) { // VALIDACION PARA EVITAR QUE DE ERROR EN CASO DE QUE LA LLAVE Y EL ITEM YA EXISTA EN LA TABLA PERO LA LINEA ESTE CON UN ESTADO ANULADO, ENTONCES PARA EVITAR ESTE ERROR CAMBIARIA EL ITEM 
                        OAPD_ITEM = maxNroItemNew(OAPD_IDATENCIONPAC);
                        sql = "INSERT INTO ope_atencion_paciente_det(IDATENCIONPAC, ITEM, IDSERVICIO, MONTO, ESTADO, USU_ALTA, FEC_ALTA) \n" +
                        "VALUES('"+OAPD_IDATENCIONPAC+"', '"+OAPD_ITEM+"', '"+OAPD_IDSERVICIO+"', '"+OAPD_MONTO+"', '"+OAPD_ESTADO+"', '"+OAPD_USU_ALTA+"', '"+OAPD_FEC_ALTA+"')  \n";
                    } else {
                        sql = "INSERT INTO ope_atencion_paciente_det(IDATENCIONPAC, ITEM, IDSERVICIO, MONTO, ESTADO, USU_ALTA, FEC_ALTA) \n" +
                        "VALUES('"+OAPD_IDATENCIONPAC+"', '"+OAPD_ITEM+"', '"+OAPD_IDSERVICIO+"', '"+OAPD_MONTO+"', '"+OAPD_ESTADO+"', '"+OAPD_USU_ALTA+"', '"+OAPD_FEC_ALTA+"')  \n";
                    }
                    
                } else {
                    varOperacion = 2; // UPDATE 
                    sql = "UPDATE ope_atencion_paciente_det \n" +
                        "SET IDSERVICIO = '"+OAPD_IDSERVICIO+"', "
                            + "MONTO = '"+OAPD_MONTO+"', "
                            + "ESTADO = '"+OAPD_ESTADO+"' " +
                            ", USU_MODI = '"+OAPD_USU_ALTA+"', FEC_MODI = '"+OAPD_FEC_ALTA+"' \n" +
                        "WHERE IDATENCIONPAC = '"+PARAM_IDATEPAC+"' AND ITEM = '"+OAPD_ITEM+"' \n";
                }
                System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE------------------------");
                System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
                System.out.println("--------------------------------------------------------------------------");
                
                MFAP_CONEXION = devolverConex();
                MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
                int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
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
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
            }
        } // END FOR 
        return tipoRespuesta;
    }
    
    
    
    
    
    @Override
    public String eliminar(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        try {
            BeanAtencionPaciente datos = (BeanAtencionPaciente) obj;
            String OAPD_IDATENCIONPAC = datos.getOAPD_IDATENCIONPAC();
            String OAPD_ITEM = datos.getOAPD_ITEM();
            String OAPD_IDSERVICIO = datos.getOAPD_IDSERVICIO();
            String OAPD_USU_ALTA = datos.getOAPD_USU_ALTA();
            String OAPD_FEC_ALTA = FECHA_HORA_HOY;
            
            String sql = "UPDATE ope_atencion_paciente_det "
                    + "SET ESTADO = 'X' "
//                    + "USU_ALTA = '"+OAPD_USU_ALTA+"', "
//                    + "FEC_ALTA = '"+OAPD_FEC_ALTA+"' "
                    + "WHERE IDATENCIONPAC = '"+OAPD_IDATENCIONPAC+"' "
                    + "AND ITEM = '"+OAPD_ITEM+"' "
                    + "AND IDSERVICIO = '"+OAPD_IDSERVICIO+"' \n";
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE------------------------");
            System.out.println("-- ---delete/update()--------     "+sql);
            System.out.println("--------------------------------------------------------------------------");
            
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
//                    respuesta = "Se ha realizado correctamente la operacion.";
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    
    public String eliminar_cab(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        try {
            BeanAtencionPaciente datos = (BeanAtencionPaciente) obj;
            String OAP_IDATENCIONPAC = datos.getOAP_IDATENCIONPAC();
            String OAP_IDAGENDAMIENTO = datos.getOAP_IDAGENDAMIENTO();
            String OAP_ITEM_AGEN = datos.getOAP_ITEM_AGEND_DET();
            String OAP_USU_ALTA = datos.getOAP_USU_ALTA();
            String OAP_FEC_ALTA = FECHA_HORA_HOY;
            
            String sql = "UPDATE ope_atencion_paciente "
                + "SET ESTADO = 'X', "
                + "USU_MODI = '"+OAP_USU_ALTA+"', "
                + "FEC_MODI = '"+OAP_FEC_ALTA+"' "
                + "WHERE IDATENCIONPAC = '"+OAP_IDATENCIONPAC+"' "
                + "AND IDAGENDAMIENTO = '"+OAP_IDAGENDAMIENTO+"' "
                + "AND ITEM_AGEND_DET = '"+OAP_ITEM_AGEN+"' \n";
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE------------------------");
            System.out.println("-- ---delete/update()--------     "+sql);
            System.out.println("--------------------------------------------------------------------------");
            
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
//                    respuesta = "Se ha realizado correctamente la operacion.";
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE PESO (KG / G / MG / UG / NG) QUE UTILICEN LAS PAGINAS (JSP) 
    fuente de las unidades de medida en peso: https://www.greenfacts.org/es/glosario/tuv/unidades-masa.htm 
    */
    public Map<String, String> cargarComboPeso(String param_peso) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (param_peso == null || param_peso.isEmpty() || param_peso.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("kg", "en kg"); // kilogramo 
            lista.put("g", "en g"); // gramo 
            lista.put("mg", "en mg"); // milligramo
//            lista.put("µg", "en µg"); // microgramo 
//            lista.put("ng", "en ng"); // nanogramo 
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (param_peso.equalsIgnoreCase("kg") || param_peso.equalsIgnoreCase("en kg")) { // KILOGRAMO 
                lista.put("kg", "en kg"); // kilogramo 
                lista.put("g", "en g"); // gramo 
                lista.put("mg", "en mg"); // milligramo
//                lista.put("µg", "en µg"); // microgramo 
//                lista.put("ng", "en ng"); // nanogramo 
            } else if (param_peso.equalsIgnoreCase("g") || param_peso.equalsIgnoreCase("en g")) { // GRAMO 
                lista.put("g", "en g"); // gramo 
                lista.put("mg", "en mg"); // milligramo
//                lista.put("µg", "en µg"); // microgramo 
//                lista.put("ng", "en ng"); // nanogramo 
                lista.put("kg", "en kg"); // kilogramo 
            } else if (param_peso.equalsIgnoreCase("mg") || param_peso.equalsIgnoreCase("en mg")) { // MILLIGRAMO 
                lista.put("mg", "en mg"); // milligramo
//                lista.put("µg", "en µg"); // microgramo 
//                lista.put("ng", "en ng"); // nanogramo 
                lista.put("kg", "en kg"); // kilogramo 
                lista.put("g", "en g"); // gramo 
//            } else if (param_peso.equalsIgnoreCase("µg") || param_peso.equalsIgnoreCase("en µg")) { // MICROGRAMO 
//                lista.put("µg", "en µg"); // microgramo 
//                lista.put("ng", "en ng"); // nanogramo 
//                lista.put("kg", "en kg"); // kilogramo 
//                lista.put("g", "en g"); // gramo 
//                lista.put("mg", "en mg"); // milligramo
//            } else if (param_peso.equalsIgnoreCase("ng") || param_peso.equalsIgnoreCase("en ng")) { // NANOGRAMO 
//                lista.put("ng", "en ng"); // nanogramo 
//                lista.put("kg", "en kg"); // kilogramo 
//                lista.put("g", "en g"); // gramo 
//                lista.put("mg", "en mg"); // milligramo
//                lista.put("µg", "en µg"); // microgramo 
            } else {
                lista.put("kg", "en kg"); // kilogramo 
                lista.put("g", "en g"); // gramo 
                lista.put("mg", "en mg"); // milligramo
//                lista.put("µg", "en µg"); // microgramo 
//                lista.put("ng", "en ng"); // nanogramo 
            }
        }
        return lista;
    } // end method 
    
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE TALLA (MM / CM / DM / M / INCH) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboTalla(String param_talla) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (param_talla == null || param_talla.isEmpty() || param_talla.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
//            lista.put("mm", "en mm"); // milimetros 
            lista.put("cm", "en cm"); // centimetros 
            lista.put("dm", "en dm"); // decimetros 
            lista.put("m", "en m"); // metros 
            lista.put("inch", "en inch"); // inche = pulgada 
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
//            if (param_talla.equalsIgnoreCase("mm") || param_talla.equalsIgnoreCase("en mm")) { // MILIMETROS 
//                lista.put("mm", "en mm"); // milimetros 
//                lista.put("cm", "en cm"); // centimetros 
//                lista.put("dm", "en dm"); // decimetros 
//                lista.put("m", "en m"); // metros 
//                lista.put("inch", "en inch"); // inche = pulgada 
//            } else 
                if (param_talla.equalsIgnoreCase("cm") || param_talla.equalsIgnoreCase("en cm")) { // CENTIMETROS 
                lista.put("cm", "en cm"); // centimetros 
                lista.put("dm", "en dm"); // decimetros 
                lista.put("m", "en m"); // metros 
                lista.put("inch", "en inch"); // inche = pulgada 
//                lista.put("mm", "en mm"); // milimetros 
            } else if (param_talla.equalsIgnoreCase("dm") || param_talla.equalsIgnoreCase("en dm")) { // DECIMETROS 
                lista.put("dm", "en dm"); // decimetros 
                lista.put("m", "en m"); // metros 
                lista.put("inch", "en inch"); // inche = pulgada 
//                lista.put("mm", "en mm"); // milimetros 
                lista.put("cm", "en cm"); // centimetros 
            } else if (param_talla.equalsIgnoreCase("m") || param_talla.equalsIgnoreCase("en m")) { // METROS  
                lista.put("m", "en m"); // metros 
                lista.put("inch", "en inch"); // inche = pulgada 
//                lista.put("mm", "en mm"); // milimetros 
                lista.put("cm", "en cm"); // centimetros 
                lista.put("dm", "en dm"); // decimetros 
            } else if (param_talla.equalsIgnoreCase("ACTIVO") || param_talla.equalsIgnoreCase("A")) { // INCHE - PULGADA  
                lista.put("inch", "en inch"); // inche = pulgada 
//                lista.put("mm", "en mm"); // milimetros 
                lista.put("cm", "en cm"); // centimetros 
                lista.put("dm", "en dm"); // decimetros 
                lista.put("m", "en m"); // metros 
            } else {
//                lista.put("mm", "en mm"); // milimetros 
                lista.put("cm", "en cm"); // centimetros 
                lista.put("dm", "en dm"); // decimetros 
                lista.put("m", "en m"); // metros 
                lista.put("inch", "en inch"); // inche = pulgada 
            }
        }
        return lista;
    } // end method 
    
    
    
    // METODO PARA TRAER UN NUEVO IDATENCION PARA PODER HACER UN INSERT Y GUARDAR LOS DATOS 
    public String traerNewIdAtencion() {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(IDATENCIONPAC),0)+1) AS ID FROM ope_atencion_paciente ";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---traerNewIdAtencion()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                valor = MFAP_RESULTADO.getString("ID");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    // METODO PARA REALIZAR LA PAGINACION Y CONSULTA A LA TABLA DE FICHA DE ATENCION PACIENTE PARA FILTRAR 
    public List filtrar(HttpSession PARAM_SESION, String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FIL_FECHA, 
            String PARAM_TXT_FILTRO, 
            String PARAM_ID_MEDICO,
            String PARAM_ID_PACIENTE
    ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
//        PARAM_CBX_MOSTRAR = "1";
        System.out.println("___     ___________filtrar_paginacion()___________     ___");
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
        if (PARAM_SESION.getAttribute("PAG_FICATEPAC_LISTA_ACTUAL") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_FICATEPAC_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) PARAM_SESION.getAttribute("PAG_FICATEPAC_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (PARAM_SESION.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(PARAM_SESION.getAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) PARAM_SESION.getAttribute("PAG_FICATEPAC_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        String sqlWhereIdMedico;
        // SI SE ENCUENTRA VACIO EL PARAMETRO DE IDMEDICO QUERRA DECIR QUE NO SE FILTRO POR EL MEDICO Y ASI TOMARIA EL IDPERSONA QUE VENDRIA SIENDO EL USUARIO PARA FILTRAR POR EL MISMO, YA QUE SI ES USUARIO CON PERFIL MEDICO TENDRIA QUE VER SOLO SUS AGENDAMIENTOS PARA CARGAR ATENCION PERO SI ES USUARIO SECRETARIO O ADMINISTRADOR ENTONCES DE IGUAL MANERA FILTRARIA EL SELECT POR SU ID AUNQUE NO VA A TRAER NADA YA QUE NO SON MEDICOS Y NO TENDRIAN AGENDAMIENTOS ENCIMA PERO SI PODRAN VER EL COMBO PARA FILTRAR POR EL MÉDICO Y EL SECRETARIO O ADMIN NO TENDRIA NECESIDAD PARA FILTRAR SIN SELECCIONAR UN MEDICO YA QUE PUEDE FILTRAR POR ÉL 
        if (PARAM_ID_MEDICO == null || PARAM_ID_MEDICO.isEmpty() || PARAM_ID_MEDICO.equals(" ")) {
            String idMedActual = (String) PARAM_SESION.getAttribute("IDPERSONA");
//            sqlWhereIdMedico = "AND cab.IDMEDICO = '3' \n";
            sqlWhereIdMedico = "AND cab.IDMEDICO = '"+idMedActual+"' \n";
        } else {
            sqlWhereIdMedico = "AND cab.IDMEDICO = '"+PARAM_ID_MEDICO+"' \n";
        }
        
        String sqlWhereIdPaciente;
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_ID_PACIENTE == null || PARAM_ID_PACIENTE.isEmpty() || PARAM_ID_PACIENTE.equals(" ")) {
            sqlWhereIdPaciente = "";
        } else {
            sqlWhereIdPaciente = "AND det.IDPACIENTE = '"+PARAM_ID_PACIENTE+"' \n";
        }
        
        String sqlFiltroTxt="";
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
        if (PARAM_TXT_FILTRO == null || PARAM_TXT_FILTRO.isEmpty() || PARAM_TXT_FILTRO.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlFiltroTxt = "AND (UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR COALESCE(( SELECT ss_oap.IDATENCIONPAC FROM ope_atencion_paciente ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) LIKE ('%"+PARAM_TXT_FILTRO+"%') " +
                ") \n";
        }
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            /*
            OBSERVACION:   USO EL MISMO SELECT QUE EN "pagAgendVer" (VER AGENDAMIENTO DEL MEDICO) 
                           PARA QUE EL MEDICO PUEDA VER TODOS LOS PACIENTES QUE TENGA AGENDADO EN EL DIA 
            */
            String sql = "SELECT cab.IDAGENDAMIENTO, det.ITEM AS ITEM_AGEN, \n" +
                "COALESCE(( SELECT ss_oap.IDATENCIONPAC FROM ope_atencion_paciente ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) AS IDATENCION_PAC, \n" +
                "cab.IDESPECIALIDAD, cab.IDMEDICO, cab.IDCLINICA, cab.ESTADO AS ESTADO_CAB, \n" +
                "det.ITEM, DATE_FORMAT(det.FECHA_AGEN, '%d-%m-%Y') AS FECHA_AGEN, DATE_FORMAT(det.HORA, '%H:%i') AS HORA, det.IDPACIENTE, rp.NOMBRE, rp.APELLIDO, rp.CINRO, det.NROPACIENTEFICHA, \n" +
                "det.MOTIVO, det.VISITATIPO, det.COMENTARIO, det.ESTADO AS ESTADO_DET, DATE_FORMAT(det.FEC_ATENCION, '%d-%m-%Y %H:%i') AS FEC_ATENCION, det.IDFACTURA, det.AGENDADO \n" +
                "FROM ope_agendamiento cab \n" +
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A','C') \n" +
//                // OBS.: LE COMENTO EL ESTADO DEL DETALLE DE AGENDAMIENTO POR QUE AL CREARLE UNA FICHA DE ATENCION A UN AGENDAMIENTO DETALLE, A ESTE LE CAMBIO SU ESTADO Y LO CIERRO PARA NO DEJARLE ABIERTO 
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
                "JOIN rh_persona rp ON det.IDPACIENTE = rp.IDPERSONA \n" +
                "WHERE cab.ESTADO IN ('A', 'C') \n" +
                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FECHA+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
                ""+sqlWhereIdMedico+"" +
                ""+sqlWhereIdPaciente+"" +
                ""+sqlFiltroTxt+" \n" +
    /* OBSERVACION: ESTA LINEA DEL WHERE ES PARA EVITAR TRAER UNA LINEA DEL AGENDAMIENTO DETALLE SI ES QUE EL PACIENTE YA CUENTA CON UNA FICHA DE ATENCION CREADA, PERO LA COMENTE PORQUE LE TRAIGO LA MISMA LINEA PERO EN VEZ DE MOSTRARLE EL BOTON PARA CREAR UNA FICHA DE ATENCION LE MUESTRO UN BOTON PARA QUE PUEDA EDITAR LA FICHA DE ATENCION EXISTENTE SOBRE ESE AGENDAMIENTO  */
//                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDATENCIONPAC \n" +
//                "FROM ope_agendamiento_det ss_agen_det \n" +
//                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
//                "JOIN ope_atencion_paciente ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
//                "WHERE ss_agen_det.ESTADO = 'A' AND ss_agen_cab.IDMEDICO = cab.IDMEDICO AND ss_agen_cab.IDCLINICA = cab.IDCLINICA )) \n" +
                "ORDER BY FECHA_AGEN ASC, HORA ASC \n"// +
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(det.IDAGENDAMIENTO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_agendamiento cab \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO IN ('A','C') \n" +
//                "JOIN ope_agendamiento_det det ON cab.IDAGENDAMIENTO = det.IDAGENDAMIENTO AND det.ESTADO = 'A' \n" +
                "JOIN rh_persona rp ON det.IDPACIENTE = rp.IDPERSONA \n" +
                "WHERE cab.ESTADO IN ('A', 'C') \n" +
                "AND cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '2022-02-22' \n" +
                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FECHA+"' \n" +
//                "AND DATE_FORMAT(det.FECHA_AGEN, '%Y-%m-%d') = '"+metodosIniSes.traerFechaHoy()+"' \n" +
                ""+sqlWhereIdMedico+"" +
                ""+sqlWhereIdPaciente+"" +
                ""+sqlFiltroTxt+" \n" +
                "ORDER BY FECHA_AGEN ASC, HORA ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("-----------------------MODEL_FICHA_ATENCION_PACIENTE------------------------");
            System.out.println("-- --filtrar_paginacion()--------     "+sql);
            System.out.println("----------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
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
            while (MFAP_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanAgendamiento datos = new BeanAgendamiento();
                        datos.setOA_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOAD_IDATENCION_PAC(MFAP_RESULTADO.getString("IDATENCION_PAC")); // SUBSELECT QUE DEVUELVE EL IDATENCIONPACIENTE EN CASO DE QUE TENGA UNA FICHA DE ATENCION EL PACIENTE (EL MISMO AGENDAMIENTO Y NRO_ITEM) 
                        // CABECERA --
                        datos.setOA_IDESPECIALIDAD(MFAP_RESULTADO.getString("IDESPECIALIDAD"));
                        datos.setOA_IDMEDICO(MFAP_RESULTADO.getString("IDMEDICO"));
                        datos.setOA_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                        datos.setOA_ESTADO(MFAP_RESULTADO.getString("ESTADO_CAB"));
                        // DETALLE --
                        datos.setOAD_ITEM(MFAP_RESULTADO.getString("ITEM"));
                        datos.setOAD_FECHA_AGEN(MFAP_RESULTADO.getString("FECHA_AGEN"));
                        datos.setOAD_HORA(MFAP_RESULTADO.getString("HORA"));
                        datos.setOAD_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                        datos.setOAD_NROPACIENTEFICHA(MFAP_RESULTADO.getString("NROPACIENTEFICHA"));
                        datos.setOAD_MOTIVO(MFAP_RESULTADO.getString("MOTIVO"));
                        datos.setOAD_VISITATIPO(MFAP_RESULTADO.getString("VISITATIPO"));
                        datos.setOAD_COMENTARIO(MFAP_RESULTADO.getString("COMENTARIO"));
                        datos.setOAD_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                        datos.setOAD_FEC_ATENCION(MFAP_RESULTADO.getString("FEC_ATENCION"));
                        datos.setOAD_IDFACTURA(MFAP_RESULTADO.getString("IDFACTURA"));
                        datos.setOAD_AGENDADO(MFAP_RESULTADO.getString("AGENDADO"));
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
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        PARAM_SESION.setAttribute("PAG_FICATEPAC_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        PARAM_SESION.setAttribute("PAG_FICATEPAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        PARAM_SESION.setAttribute("PAG_FICATEPAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        PARAM_SESION.setAttribute("PAG_FICATEPAC_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        PARAM_SESION.setAttribute("PAG_FICATEPAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    /*
    METODO QUE UTILIZO PARA CONTROLAR SI ES QUE LA COMBINACION DE IDATENCION Y ITEM YA EXISTE, 
    SI YA EXISTE RETORNO TRUE Y SINO RETORNO FALSE, PARA EVITAR QUE SALTE ERROR POR DUPLICACION DE LLAVE A LA HORA DE INSERTAR UNA NUEVA LINEA EN EL DETALLE 
    */
    public boolean controlIdItemDet(String PARAM_IDATENCION, String PARAM_ITEM) {
        boolean valor = false;
        try {
            String sql = "SELECT * FROM ope_atencion_paciente_det "
                    + "WHERE IDATENCIONPAC = '"+PARAM_IDATENCION+"' "
                    + "AND ITEM = '"+PARAM_ITEM+"' ";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---controlIdItemDet()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            if(MFAP_RESULTADO.next() == true) {
                valor = true;
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    // METODO PARA TRAER UN NUEVO ITEM DE UN IDATENCION POR QUE EL ITEM YA EXISTE Y LE DOY UN NUEVO ITEM PARA QUE SE PUEDA INSERTAR LA LINEA Y NO SALTE ERROR POR DUPLICACION DE LLAVE DE IDATENCION - ITEM 
    public String maxNroItemNew(String PARAM_IDATENCION) {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(ITEM),0)+1) AS ITEM_NEW FROM ope_atencion_paciente_det WHERE IDATENCIONPAC = '"+PARAM_IDATENCION+"' ";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---maxNroItemNew()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                valor = MFAP_RESULTADO.getString("ITEM_NEW");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    /*
        UTILIZO ESTE METODO EN LA PAGINA DE PACIENTE PARA VER TODAS LAS FICHAS DE ATENCION DEL PACIENTE QUE SE HAYA SELECCIONADO 
    */
    public List cargar_fichas_pacientes(HttpSession PARAM_SESION, String PARAM_IDPACIENTE) {
        List<BeanAtencionPaciente> lista = new ArrayList<>();
//        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String ID_MEDICO = (String) PARAM_SESION.getAttribute("IDPERSONA");
//        String ID_PACIENTE = (String) PARAM_SESION.getAttribute("ID_PACIENTE");
        
        try {
            String sql = "SELECT cab.IDATENCIONPAC, cab.IDAGENDAMIENTO, cab.ITEM_AGEND_DET, \n" +
//                "-- (CASE WHEN(cab.IDAGENDAMIENTO='0' AND cab.ITEM_AGEND_DET='0') THEN '-' ELSE (SELECT DATE_FORMAT(HORA,'%d/%m/%Y %H:%i') FROM ope_agendamiento_det WHERE IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ITEM = cab.ITEM_AGEND_DET) END) AS FEC_AGEN, \n" +
                "COALESCE(DATE_FORMAT(agen_det.HORA,'%d/%m/%Y %H:%i'),'-') AS FEC_AGEN, \n" +
                "cab.IDPACIENTE, cab.PESO, cab.TALLA, cab.IMC, cab.P_CEFALICO, \n" +
                "cab.FC, cab.TEMP, cab.PA, cab.F_RESP, cab.MOTIVO_CONSULTA, cab.EXPLORACION_FISICA, cab.DIAGNOSTICO, \n" +
                "cab.TRATAMIENTO, cab.RECETA, cab.ESTUDIOS_SOLICITADOS, cab.USU_ALTA, DATE_FORMAT(cab.FEC_ALTA, '%d/%m/%Y  %H:%i') AS FEC_ALTA, cab.ESTADO, cab.IDCLINICA \n" +
                "FROM ope_atencion_paciente cab \n" +
                "LEFT JOIN ope_agendamiento_det agen_det ON agen_det.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND agen_det.ITEM = cab.ITEM_AGEND_DET AND agen_det.ESTADO NOT IN ('X') \n" +
                "WHERE cab.ESTADO = 'A' \n" +
                //"AND cab.IDCLINICA = '1' \n" +
                "AND cab.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n"
                + "ORDER BY cab.IDATENCIONPAC DESC \n";
            /*
             * OBSERVACION: SELECT SIN EL SUBSELECT PARA TRAER LA FECHA DE AGENDAMIENTO 
            */
//            String sql = "SELECT cab.IDATENCIONPAC, cab.IDAGENDAMIENTO, cab.ITEM_AGEND_DET, cab.IDPACIENTE, cab.PESO, cab.TALLA, cab.IMC, cab.P_CEFALICO, \n" +
//                "cab.FC, cab.TEMP, cab.PA, cab.F_RESP, cab.MOTIVO_CONSULTA, cab.EXPLORACION_FISICA, cab.DIAGNOSTICO, \n" +
//                "cab.TRATAMIENTO, cab.RECETA, cab.ESTUDIOS_SOLICITADOS, cab.USU_ALTA, DATE_FORMAT(cab.FEC_ALTA, '%d-%m-%Y  %H:%i') AS FEC_ALTA, cab.ESTADO, cab.IDCLINICA \n" +
//                "FROM ope_atencion_paciente cab \n" +
//                "WHERE cab.ESTADO = 'A' \n" +
//                //"AND cab.IDCLINICA = '1' \n" +
//                "AND cab.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n";
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-----------------------");
            System.out.println("-- ---cargar_fichas_pacientes()--------     "+sql);
            System.out.println("------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanAtencionPaciente datos = new BeanAtencionPaciente();
                    datos.setOAP_IDATENCIONPAC(MFAP_RESULTADO.getString("IDATENCIONPAC"));
                    datos.setOAP_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOAP_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                    datos.setOAP_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOAP_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOAP_TALLA(MFAP_RESULTADO.getString("TALLA"));
                    datos.setOAP_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOAP_P_CEFALICO(MFAP_RESULTADO.getString("P_CEFALICO"));
                    datos.setOAP_FC(MFAP_RESULTADO.getString("FC"));
                    datos.setOAP_TEMP(MFAP_RESULTADO.getString("TEMP"));
                    datos.setOAP_PA(MFAP_RESULTADO.getString("PA"));
                    datos.setOAP_F_RESP(MFAP_RESULTADO.getString("F_RESP"));
                    datos.setOAP_MOTIVO_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_CONSULTA"));
                    datos.setOAP_EXPLORACION_FISICA(MFAP_RESULTADO.getString("EXPLORACION_FISICA"));
                    datos.setOAP_DIAGNOSTICO(MFAP_RESULTADO.getString("DIAGNOSTICO"));
                    datos.setOAP_TRATAMIENTO(MFAP_RESULTADO.getString("TRATAMIENTO"));
                    datos.setOAP_RECETA(MFAP_RESULTADO.getString("RECETA"));
                    datos.setOAP_ESTUDIOS_SOLICITADOS(MFAP_RESULTADO.getString("ESTUDIOS_SOLICITADOS"));
                    datos.setOAP_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
                    datos.setOAP_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOAP_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
                    datos.setOAP_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                    // CARGO EL CAMPO AUXILIAR PARA MOSTRAR LA FECHA DE AGEN EN CASO DE QUE LA FICHA ESTE VINCULADA A UN AGENDAMIENTO 
                    datos.setOAP_FEC_AGEN_AUX(MFAP_RESULTADO.getString("FEC_AGEN"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    
    // METODO QUE UTILIZO PARA PODER CONTROLAR EL CIERRE DEL AGENDAMIENTO DETALLE 
    public void controlarCierreAgend(String PARAM_IDAGENDAMIENTO, String PARAM_ITEM, String PARAM_FEC_ATENCION) {
        try {
            String sql = "UPDATE ope_agendamiento_det \n" +
                "SET ESTADO = 'C', " + 
                    "FEC_ATENCION = '"+PARAM_FEC_ATENCION+"' \n" + 
                "WHERE ITEM = '"+PARAM_ITEM+"' \n" + 
                "AND IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' ";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---controlarCierreAgend()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            MFAP_SENTENCIA.executeUpdate(sql);
            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MFAP_RESULTADO = consultaBD(sql);
//            
//            if(MFAP_RESULTADO.next() == true) {
//                
//            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPac.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
} // END CLASS 