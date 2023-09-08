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
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
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
import com.agend.javaBean.BeanFichaAtePaciente;
import com.agend.javaBean.BeanFichaAtePacienteDet;
import com.agend.javaBean.BeanPersona;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 *
 * @author RYUU
 */
/**
 * A business plan demo
 * Usage:
 *  BusinessPlan -xls|xlsx
 */
@SuppressWarnings({"java:S106","java:S4823","java:S1192"})
public class ModelFichaAtencionPacNutri implements CRUD {
    
    
    public String VAR_ID_CLINICA = "";
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_FICHA_ATENCION_PAC_NUTRI.-------");
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    
    
    

    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        return null;
    }

    
    /*
     METODO QUE UTILIZO PARA PAGINACION 
    */
    public List cargar_grilla_paginacion(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            ) {
        System.out.println("[.]");
        System.out.println("[.]");
        System.out.println("[.]");
        System.out.println("[.]");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("[+]___     ___________cargar_grilla_paginacion()___________     ___");
        System.out.println("[*]_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("[*]_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
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
                "COALESCE(( SELECT ss_oap.IDFICHAPAC FROM ope_ficha_pac_nutri ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) AS IDATENCION_PAC, \n" +
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
//                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDFICHAPAC \n" +
//                "FROM ope_agendamiento_det ss_agen_det \n" +
//                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
//                "JOIN ope_ficha_pac_nutri ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
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
            
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("----------------------------------------------------------------------------------");
            
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
                        datos.setOAD_IDATENCION_PAC(MFAP_RESULTADO.getString("IDATENCION_PAC")); // SUBSELECT QUE DEVUELVE EL IDFICHAPACIENTE EN CASO DE QUE TENGA UNA FICHA DE ATENCION EL PACIENTE (EL MISMO AGENDAMIENTO Y NRO_ITEM) 
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
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
        List<BeanFichaAtePaciente> lista = new ArrayList<>();
        
//        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String ID_MEDICO = (String) PARAM_SESION.getAttribute("IDPERSONA");
        try {
            String sql = "SELECT IDFICHAPAC, IDAGENDAMIENTO, ITEM_AGEND_DET, IDPACIENTE, \n" +
                "DATE_FORMAT(FECHA_FICHA_ATE, '%Y-%m-%d') AS FECHA_FICHA, DATE_FORMAT(FECHA_FICHA_ATE,'%H:%i') AS HORA_FICHA, \n" +
                "MOTIVO_DE_LA_CONSULTA, ALIMENTOS_DE_PREFERENCIA, ALIMENTOS_QUE_NO_TOLERA, ALI_QUE_SUELE_COMER_GRL, \n" +
                "CONSUMO_ALCOHOL, CONSUMO_CIGARRILLO, ALERGIAS_A_ALGO, CIRUGIAS, PADECE_ALGUNA_ENFERMEDAD, \n" +
                "MEDICAMENTE_Q_E_CONSUMIENDO, OTROS_DATOS_A_TENER_EN_CUENTA, REALIZA_ACTIVIDAD_FISICA, TIPO_DE_ACTIVIDAD_FISICA, \n" +
                "FRECUENCIA_ACT_FISICA_SEM, DBLCR, LGSLCM, TBDALN, DPALN, DDCCF, ESTRENHIMIENTO, TDEDBU, CANSANCIO_FATIGA, \n" +
                "HICHAZON_ABDOMINAL, INSOMNIO, MUCOSIDAD_Y_CATARRO, CALAMBRES_Y_HORMIGUEOS, ZUMBIDOS_EN_EL_OIDO, CAIDA_DE_CABELLO, \n" +
                "UNHAS_QUEBRADIZAS, PIEL_SECA, TIPO_DE_METABOLISMO, ESTATURA, PESO, IMC, PORC_GRASA, PORC_MUSCULO, VISCERAL, \n" +
                "EDAD_METABOLICA, RM, TIPO_DE_BALANZA, RESULTADOS_TEST_BIORESONANCIA, SUPLEMENTACION_RECETADA, LABORATORIO, COMENTARIOS_GENERALES, \n" +
                "USU_ALTA, FEC_ALTA, ESTADO, IDCLINICA, USU_MODI, FEC_MODI \n" +
                "FROM ope_ficha_pac_nutri \n" +
                "WHERE IDFICHAPAC = '"+idTraer+"' \n" +
                "AND ESTADO = 'A' \n" ;
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI-----------------------");
            System.out.println("-- ---traer_datos()--------     "+sql);
            System.out.println("------------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    // CABECERA --
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOFPN_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                    datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA")+" "+MFAP_RESULTADO.getString("HORA_FICHA"));
                    // DATOS REFERENTES A LA CONSULTA 
                    datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
                    datos.setOFPN_ALIMENTOS_DE_PREFERENCIA(MFAP_RESULTADO.getString("ALIMENTOS_DE_PREFERENCIA"));
                    datos.setOFPN_ALIMENTOS_QUE_NO_TOLERA(MFAP_RESULTADO.getString("ALIMENTOS_QUE_NO_TOLERA"));
                    datos.setOFPN_ALI_QUE_SUELE_COMER_GRL(MFAP_RESULTADO.getString("ALI_QUE_SUELE_COMER_GRL"));
                    datos.setOFPN_CONSUMO_ALCOHOL(MFAP_RESULTADO.getString("CONSUMO_ALCOHOL"));
                    datos.setOFPN_CONSUMO_CIGARRILLO(MFAP_RESULTADO.getString("CONSUMO_CIGARRILLO"));
                    datos.setOFPN_ALERGIAS_A_ALGO(MFAP_RESULTADO.getString("ALERGIAS_A_ALGO"));
                    datos.setOFPN_CIRUGIAS(MFAP_RESULTADO.getString("CIRUGIAS"));
                    datos.setOFPN_PADECE_ALGUNA_ENFERMEDAD(MFAP_RESULTADO.getString("PADECE_ALGUNA_ENFERMEDAD"));
                    datos.setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(MFAP_RESULTADO.getString("MEDICAMENTE_Q_E_CONSUMIENDO"));
                    datos.setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(MFAP_RESULTADO.getString("OTROS_DATOS_A_TENER_EN_CUENTA").replaceAll("<br/>","\r\n"));
                    datos.setOFPN_REALIZA_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("REALIZA_ACTIVIDAD_FISICA"));
                    datos.setOFPN_TIPO_DE_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("TIPO_DE_ACTIVIDAD_FISICA"));
                    datos.setOFPN_FRECUENCIA_ACT_FISICA_SEM(MFAP_RESULTADO.getString("FRECUENCIA_ACT_FISICA_SEM"));
                    datos.setOFPN_DBLCR(MFAP_RESULTADO.getString("DBLCR"));
                    datos.setOFPN_LGSLCM(MFAP_RESULTADO.getString("LGSLCM"));
                    datos.setOFPN_TBDALN(MFAP_RESULTADO.getString("TBDALN"));
                    datos.setOFPN_DPALN(MFAP_RESULTADO.getString("DPALN"));
                    datos.setOFPN_DDCCF(MFAP_RESULTADO.getString("DDCCF"));
                    datos.setOFPN_ESTRENHIMIENTO(MFAP_RESULTADO.getString("ESTRENHIMIENTO"));
                    datos.setOFPN_TDEDBU(MFAP_RESULTADO.getString("TDEDBU"));
                    datos.setOFPN_CANSANCIO_FATIGA(MFAP_RESULTADO.getString("CANSANCIO_FATIGA"));
                    datos.setOFPN_HICHAZON_ABDOMINAL(MFAP_RESULTADO.getString("HICHAZON_ABDOMINAL"));
                    datos.setOFPN_INSOMNIO(MFAP_RESULTADO.getString("INSOMNIO"));
                    datos.setOFPN_MUCOSIDAD_Y_CATARRO(MFAP_RESULTADO.getString("MUCOSIDAD_Y_CATARRO"));
                    datos.setOFPN_CALAMBRES_Y_HORMIGUEOS(MFAP_RESULTADO.getString("CALAMBRES_Y_HORMIGUEOS"));
                    datos.setOFPN_ZUMBIDOS_EN_EL_OIDO(MFAP_RESULTADO.getString("ZUMBIDOS_EN_EL_OIDO"));
                    datos.setOFPN_CAIDA_DE_CABELLO(MFAP_RESULTADO.getString("CAIDA_DE_CABELLO"));
                    datos.setOFPN_UNHAS_QUEBRADIZAS(MFAP_RESULTADO.getString("UNHAS_QUEBRADIZAS"));
                    datos.setOFPN_PIEL_SECA(MFAP_RESULTADO.getString("PIEL_SECA"));
                    datos.setOFPN_TIPO_DE_METABOLISMO(MFAP_RESULTADO.getString("TIPO_DE_METABOLISMO"));
                    datos.setOFPN_ESTATURA(MFAP_RESULTADO.getString("ESTATURA"));
                    datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                    datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                    datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                    datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                    datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                    datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                    datos.setOFPN_RESULTADOS_TEST_BIORESONANCIA(MFAP_RESULTADO.getString("RESULTADOS_TEST_BIORESONANCIA"));
                    datos.setOFPN_SUPLEMENTACION_RECETADA(MFAP_RESULTADO.getString("SUPLEMENTACION_RECETADA"));
                    datos.setOFPN_LABORATORIO(MFAP_RESULTADO.getString("LABORATORIO"));
                    datos.setOFPN_COMENTARIOS_GENERALES(MFAP_RESULTADO.getString("COMENTARIOS_GENERALES"));
                    datos.setOFPN_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
                    datos.setOFPN_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPN_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
                    datos.setOFPN_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                    datos.setOFPN_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
                    datos.setOFPN_FEC_MODI(MFAP_RESULTADO.getString("PESO"));
                    // DETALLE --
//                    datos.setOAPD_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                    datos.setOAPD_ITEM(MFAP_RESULTADO.getString("ITEM"));
//                    datos.setOAPD_IDSERVICIO(MFAP_RESULTADO.getString("IDSERVICIO"));
//                    datos.setOAPD_MONTO(MFAP_RESULTADO.getString("MONTO"));
//                    datos.setOAPD_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    

    @Override
    public String guardar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public String guardar_cab(Object obj, List<BeanFichaAtePacienteDet> PARAM_LIST_DET) {
        String tipoRespuesta="1"; // 1 = Exito / 2 = Error 
        BeanFichaAtePaciente datos = (BeanFichaAtePaciente) obj;
        
        // FORMATEAMOS EL VALOR DE LA VARIABLE ESTADO PARA PODER GUARDAR LA INICIAL NOMAS Y NO LA PALABRA COMPLETA 
        String ESTADO = datos.getOFPN_ESTADO();
        if (ESTADO.equalsIgnoreCase("INACTIVO") || ESTADO.equalsIgnoreCase("I")) {
            ESTADO = "I";
        } else {
            ESTADO = "A";
        }
        
        try {
            int varOperacion;
            String sql;
            String OFPN_IDFICHAPAC = datos.getOFPN_IDFICHAPAC();
            String OFPN_IDAGENDAMIENTO = datos.getOFPN_IDAGENDAMIENTO();
            String OFPN_ITEM_AGEND_DET = datos.getOFPN_ITEM_AGEND_DET();
            String OFPN_IDPACIENTE = datos.getOFPN_IDPACIENTE();
            String OFPN_FECHA_FICHA_ATE = datos.getOFPN_FECHA_FICHA_ATE();
            String OFPN_MOTIVO_DE_LA_CONSULTA = datos.getOFPN_MOTIVO_DE_LA_CONSULTA();
            String OFPN_ALIMENTOS_DE_PREFERENCIA = datos.getOFPN_ALIMENTOS_DE_PREFERENCIA();
            String OFPN_ALIMENTOS_QUE_NO_TOLERA = datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA();
            String OFPN_ALI_QUE_SUELE_COMER_GRL = datos.getOFPN_ALI_QUE_SUELE_COMER_GRL();
            String OFPN_CONSUMO_ALCOHOL = datos.getOFPN_CONSUMO_ALCOHOL();
            String OFPN_CONSUMO_CIGARRILLO = datos.getOFPN_CONSUMO_CIGARRILLO();
            String OFPN_ALERGIAS_A_ALGO = datos.getOFPN_ALERGIAS_A_ALGO();
            String OFPN_CIRUGIAS = datos.getOFPN_CIRUGIAS();
            String OFPN_PADECE_ALGUNA_ENFERMEDAD = datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD();
            String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO();
            String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OFPN_REALIZA_ACTIVIDAD_FISICA = datos.getOFPN_REALIZA_ACTIVIDAD_FISICA();
            String OFPN_TIPO_DE_ACTIVIDAD_FISICA = datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA();
            String OFPN_FRECUENCIA_ACT_FISICA_SEM = datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM();
            String OFPN_DBLCR = datos.getOFPN_DBLCR();
            String OFPN_LGSLCM = datos.getOFPN_LGSLCM();
            String OFPN_TBDALN = datos.getOFPN_TBDALN();
            String OFPN_DPALN = datos.getOFPN_DPALN();
            String OFPN_DDCCF = datos.getOFPN_DDCCF();
            String OFPN_ESTRENHIMIENTO = datos.getOFPN_ESTRENHIMIENTO();
            String OFPN_TDEDBU = datos.getOFPN_TDEDBU(); // TIPO DE ESCALA DE BRISTOL USUAL 
            String OFPN_CANSANCIO_FATIGA = datos.getOFPN_CANSANCIO_FATIGA();
            String OFPN_HICHAZON_ABDOMINAL = datos.getOFPN_HICHAZON_ABDOMINAL();
            String OFPN_INSOMNIO = datos.getOFPN_INSOMNIO();
            String OFPN_MUCOSIDAD_Y_CATARRO = datos.getOFPN_MUCOSIDAD_Y_CATARRO();
            String OFPN_CALAMBRES_Y_HORMIGUEOS = datos.getOFPN_CALAMBRES_Y_HORMIGUEOS();
            String OFPN_ZUMBIDOS_EN_EL_OIDO = datos.getOFPN_ZUMBIDOS_EN_EL_OIDO();
            String OFPN_CAIDA_DE_CABELLO = datos.getOFPN_CAIDA_DE_CABELLO();
            String OFPN_UNHAS_QUEBRADIZAS = datos.getOFPN_UNHAS_QUEBRADIZAS();
            String OFPN_PIEL_SECA = datos.getOFPN_PIEL_SECA();
            String OFPN_TIPO_DE_METABOLISMO = datos.getOFPN_TIPO_DE_METABOLISMO();
            String OFPN_ESTATURA = datos.getOFPN_ESTATURA();
            String OFPN_PESO = datos.getOFPN_PESO();
            String OFPN_IMC = datos.getOFPN_IMC();
            String OFPN_PORC_GRASA = datos.getOFPN_PORC_GRASA();
            String OFPN_PORC_MUSCULO = datos.getOFPN_PORC_MUSCULO();
            String OFPN_VISCERAL = datos.getOFPN_VISCERAL();
            String OFPN_EDAD_METABOLICA = datos.getOFPN_EDAD_METABOLICA();
            String OFPN_RM = datos.getOFPN_RM();
            String OFPN_TIPO_DE_BALANZA = datos.getOFPN_TIPO_DE_BALANZA();
            String OFPN_RESULTADOS_TEST_BIORESONANCIA = datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA();
            String OFPN_SUPLEMENTACION_RECETADA = datos.getOFPN_SUPLEMENTACION_RECETADA();
            String OFPN_LABORATORIO = datos.getOFPN_LABORATORIO();
            String OFPN_COMENTARIOS_GENERALES = datos.getOFPN_COMENTARIOS_GENERALES();
            String OFPN_USU_ALTA = datos.getOFPN_USU_ALTA();
            String OFPN_FEC_ALTA = datos.getOFPN_FEC_ALTA();
            String OFPN_ESTADO = datos.getOFPN_ESTADO();
            String OFPN_IDCLINICA = datos.getOFPN_IDCLINICA();
            String OFPN_USU_MODI = datos.getOFPN_USU_MODI();
            String OFPN_FEC_MODI = datos.getOFPN_FEC_MODI();
            
            varOperacion = 1; // INSERT 
            sql = "INSERT INTO ope_ficha_pac_nutri(IDFICHAPAC, IDAGENDAMIENTO, ITEM_AGEND_DET, IDPACIENTE, FECHA_FICHA_ATE, MOTIVO_DE_LA_CONSULTA, ALIMENTOS_DE_PREFERENCIA, ALIMENTOS_QUE_NO_TOLERA, ALI_QUE_SUELE_COMER_GRL, \n" +
                    "CONSUMO_ALCOHOL, CONSUMO_CIGARRILLO, ALERGIAS_A_ALGO, CIRUGIAS, PADECE_ALGUNA_ENFERMEDAD, \n" +
                    "MEDICAMENTE_Q_E_CONSUMIENDO, OTROS_DATOS_A_TENER_EN_CUENTA, REALIZA_ACTIVIDAD_FISICA, TIPO_DE_ACTIVIDAD_FISICA, \n" +
                    "FRECUENCIA_ACT_FISICA_SEM, DBLCR, LGSLCM, TBDALN, DPALN, DDCCF, ESTRENHIMIENTO, TDEDBU, CANSANCIO_FATIGA, \n" +
                    "HICHAZON_ABDOMINAL, INSOMNIO, MUCOSIDAD_Y_CATARRO, CALAMBRES_Y_HORMIGUEOS, ZUMBIDOS_EN_EL_OIDO, CAIDA_DE_CABELLO, \n" +
                    "UNHAS_QUEBRADIZAS, PIEL_SECA, TIPO_DE_METABOLISMO, ESTATURA, PESO, IMC, PORC_GRASA, PORC_MUSCULO, VISCERAL, \n" +
                    "EDAD_METABOLICA, RM, TIPO_DE_BALANZA, RESULTADOS_TEST_BIORESONANCIA, SUPLEMENTACION_RECETADA, LABORATORIO, COMENTARIOS_GENERALES, \n" +
                    "USU_ALTA, FEC_ALTA, ESTADO, IDCLINICA, USU_MODI, FEC_MODI) \n" +
                "VALUES ('"+OFPN_IDFICHAPAC+"', '"+OFPN_IDAGENDAMIENTO+"', '"+OFPN_ITEM_AGEND_DET+"', '"+OFPN_IDPACIENTE+"', '"+OFPN_FECHA_FICHA_ATE+"', '"+OFPN_MOTIVO_DE_LA_CONSULTA+"', '"+OFPN_ALIMENTOS_DE_PREFERENCIA+"', '"+OFPN_ALIMENTOS_QUE_NO_TOLERA+"', '"+OFPN_ALI_QUE_SUELE_COMER_GRL+"', \n" +
                    "'"+OFPN_CONSUMO_ALCOHOL+"', '"+OFPN_CONSUMO_CIGARRILLO+"', '"+OFPN_ALERGIAS_A_ALGO+"', '"+OFPN_CIRUGIAS+"', '"+OFPN_PADECE_ALGUNA_ENFERMEDAD+"', \n" +
                    "'"+OFPN_MEDICAMENTE_Q_E_CONSUMIENDO+"', '"+OFPN_OTROS_DATOS_A_TENER_EN_CUENTA+"', '"+OFPN_REALIZA_ACTIVIDAD_FISICA+"', '"+OFPN_TIPO_DE_ACTIVIDAD_FISICA+"', \n" +
                    "'"+OFPN_FRECUENCIA_ACT_FISICA_SEM+"', '"+OFPN_DBLCR+"', '"+OFPN_LGSLCM+"', '"+OFPN_TBDALN+"', '"+OFPN_DPALN+"', '"+OFPN_DDCCF+"', '"+OFPN_ESTRENHIMIENTO+"', '"+OFPN_TDEDBU+"', '"+OFPN_CANSANCIO_FATIGA+"', \n" +
                    "'"+OFPN_HICHAZON_ABDOMINAL+"', '"+OFPN_INSOMNIO+"', '"+OFPN_MUCOSIDAD_Y_CATARRO+"', '"+OFPN_CALAMBRES_Y_HORMIGUEOS+"', '"+OFPN_ZUMBIDOS_EN_EL_OIDO+"', '"+OFPN_CAIDA_DE_CABELLO+"', \n" +
                    "'"+OFPN_UNHAS_QUEBRADIZAS+"', '"+OFPN_PIEL_SECA+"', '"+OFPN_TIPO_DE_METABOLISMO+"', '"+OFPN_ESTATURA+"', '"+OFPN_PESO+"', '"+OFPN_IMC+"', '"+OFPN_PORC_GRASA+"', '"+OFPN_PORC_MUSCULO+"', '"+OFPN_VISCERAL+"', \n" +
                    "'"+OFPN_EDAD_METABOLICA+"', '"+OFPN_RM+"', '"+OFPN_TIPO_DE_BALANZA+"', '"+OFPN_RESULTADOS_TEST_BIORESONANCIA+"', '"+OFPN_SUPLEMENTACION_RECETADA+"', '"+OFPN_LABORATORIO+"', '"+OFPN_COMENTARIOS_GENERALES+"', \n" +
                    "'"+OFPN_USU_ALTA+"', '"+OFPN_FEC_ALTA+"', '"+OFPN_ESTADO+"', '"+OFPN_IDCLINICA+"', '"+OFPN_USU_MODI+"', "+OFPN_FEC_MODI+") \n";
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI-------------------------");
            System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
            System.out.println("--------------------------------------------------------------------------------");
            
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
                guardar_det(PARAM_LIST_DET, OFPN_IDFICHAPAC, OFPN_USU_ALTA, OFPN_IDAGENDAMIENTO, OFPN_ITEM_AGEND_DET);
                controlarCierreAgend(OFPN_IDAGENDAMIENTO, OFPN_ITEM_AGEND_DET, OFPN_FEC_ALTA);
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    public String guardar_det(List<BeanFichaAtePacienteDet> PARAM_LIST_DET, String PARAM_IDFICHAPAC, String PARAM_USU_ALTA, String OAP_IDAGENDAMIENTO, String OAP_ITEM_AGEND_DET) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("_   _   __   _   __   _   __* GUARDAR__FICHA-ATENCION__DET *__   _   __   _   __   _   _");
        // recorro la lista que le paso por parametro para guardar o actualizar el detalle 
        for (BeanFichaAtePacienteDet APDET_ROW : PARAM_LIST_DET) {
            try {
                int varOperacion;
                String sql;
                String OFPND_IDFICHAPAC = PARAM_IDFICHAPAC;
                String OFPND_ITEM = APDET_ROW.getOFPND_ITEM();
                OFPND_ITEM = maxNroItemNew(PARAM_IDFICHAPAC);
//                String OFPND_IDSERVICIO = APDET_ROW.getOAPD_IDSERVICIO();
//                String OFPND_MONTO = APDET_ROW.getOAPD_MONTO().replace(",",".").replace(".","");
                String OFPND_USU_ALTA = PARAM_USU_ALTA;
//                String OFPND_USU_ALTA = APDET_ROW.getOAPD_USU_ALTA();
                String OFPND_FEC_ALTA = FECHA_HORA_HOY;
//                String OFPND_FEC_ALTA = APDET_ROW.getOAPD_FEC_ALTA();
                String OFPND_ESTADO;
//                String OFPND_ESTADO = APDET_ROW.getOAPD_ESTADO();
//                if (OFPND_ESTADO.equalsIgnoreCase("INACTIVO") || OPHD_ESTADO.equalsIgnoreCase("I")) {
//                    OFPND_ESTADO = "I";
//                } else {
                    OFPND_ESTADO = "A"; // VOY A GUARDAR TODAS LAS FILAS CON ESTADO ACTIVO YA QUE ESTE ESTADO LO UTILIZARE PARA MOSTRARLE POR DEFECTO TODAS LAS LINEAS PERO CUANDO EL USUARIO ELIMINE ENTONCES LE INACTIVO NOMAS LA LINEA DEL DETALLE Y SOLAMENTE MOSTRARE LAS LINEAS DEL DETALLE ACTIVAS  
//                }
                String OFPND_DIR_ARCHIVO = APDET_ROW.getOFPND_DIR_ARCHIVO().replace("\\","*");
                String OFPND_NAME_ARCHIVO = APDET_ROW.getOFPND_NAME_ARCHIVO();
                
                varOperacion = 1; // INSERT 
                sql = "INSERT INTO ope_ficha_pac_nutri_det(IDFICHAPAC, ITEM, IDSERVICIO, MONTO, ESTADO, USU_ALTA, FEC_ALTA, DIR_ARCHIVO, NAME_ARCHIVO) \n" +
                    "VALUES('"+OFPND_IDFICHAPAC+"', '"+OFPND_ITEM+"', '0', '0', '"+OFPND_ESTADO+"', '"+OFPND_USU_ALTA+"', '"+OFPND_FEC_ALTA+"', '"+OFPND_DIR_ARCHIVO+"', '"+OFPND_NAME_ARCHIVO+"') \n";
                System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI------------------------");
                System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
                System.out.println("--------------------------------------------------------------------------------");
                
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
//                    guardarFile(OFPND_DIR_ARCHIVO, OFPND_NAME_ARCHIVO);
//                    controlarCierreAgend(OAP_IDAGENDAMIENTO, OAP_ITEM_AGEND_DET, OAPD_FEC_ALTA);
                } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                    tipoRespuesta = "2";
    //                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
                }
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
            }
        } // END FOR 
        return tipoRespuesta;
    }
    
    
    
    // METODO PARA PODER GUARDAR EL ARCHIVO ADJUNTO DE LA FICHA (ES EL MISMO METODO QUE UTILIZO EN EL CONTROLADOR DE PACIENTE, SOLAMENTE QUE CON DIFERENCIA QUE EN PACIENTE GUARDO UNA IMAGEN Y EN FICHA TENGO QUE RECORRER UNA LISTA PARA PODER GUARDAR DE MANERA INDIVIDUAL CADA ARCHIVO) 
    public void guardarFile(String PARAM_PATH_FILE, String PARAM_NAME_FILE) {
        System.out.println(".   -------------------------------------------------------------------------------");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   -----------     guardarFile     -----------------");
        System.out.println(".");
        System.out.println(".");
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        String PATH_FILE="", NAME_FILE="";
        //-----------------------------------------------------------------------------------------------
        // TRASLADO LA IMAGEN A LA CARPETA PARA RECUPERAR EL PATH Y DE AHI GUARDAR EN LA VARIABLE 
        //-----------------------------------------------------------------------------------------------
        String file_separador =  File.separator;
        String path_destino = metodosIniSes.getPathFileLocation();
        File PATH_DESTINO = new File(path_destino.replace("*", file_separador));
        System.out.println("__PATH_DESTINO:   :"+PATH_DESTINO);
        // recupero el file 
        String part = PARAM_PATH_FILE.replace("*","\\");
//        Part part = request.getPart("PacFile");
        String file_name = PARAM_NAME_FILE;
//        String file_name = part.getSubmittedFileName();
        if (part == null) {
            System.out.println("No ha seleccionado ninguna imagen.");
            return;
        } else {
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__  ___FILE_NAME:    :"+file_name);
            System.out.println(".");
            System.out.println(".");
//            String IMAGEN_JSP = String.valueOf(request.getParameter("TAPI"));
//            String IMAGEN_PATH_ABS_JSP = String.valueOf(request.getParameter("TAPIPA"));
//            System.out.println("__  ___IMAGEN_JSP:   :"+IMAGEN_JSP);
//            System.out.println("__  ___IMAGEN_PATH_ABS_JSP:   :"+IMAGEN_PATH_ABS_JSP);
//            if (file_name.equals(IMAGEN_JSP) || (file_name == null || file_name.isEmpty())) {
//                System.out.println("_________IF_________IMG_DB_==_IMG_JSP________");
//                NAME_FILE = IMAGEN_JSP;
//                PATH_FILE = IMAGEN_PATH_ABS_JSP;
//            } else {
//                System.out.println("_________ELSE_________IMG_DB_!=_IMG_JSP________");
//                File pathNewCarp = new File((path_destino.replace("*", file_separador)));
                PATH_FILE = saveFile(part, PATH_DESTINO, PARAM_NAME_FILE);
                System.out.println("__________SAVE____________");
                System.out.println("_FILE_PATH:   :"+PATH_FILE);
                System.out.println("_FILE_PATH:   :"+PATH_FILE.replace("\\\\","*"));
                System.out.println("__________________________");
                System.out.println("_FILE_NAME:   :"+file_name);
                System.out.println("__________________________");
                NAME_FILE = file_name;
//            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
        }
        //-----------------------------------------------------------------------------------------------
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".-       END_METHOD  ---------  -   guardarFile   -  ------------");
        System.out.println(".");
        System.out.println(".   ----------------------------------------------------------------------");
    }
    
    
    // METODO ENCARGADO DE COPIAR EL ARCHIVO EN LA CARPETA DESTINO, QUE EN ESTE CASO ES "DOWNLOAD" (SOLO COPIA Y PEGA NO REEMPLAZA) 
    private String saveFile(String part, File pathUploads, String pathFileName) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   ____________saveFile_______________");
        System.out.println(".");
        System.out.println(".");
        String pathAbsolute="";
        
        try {
//            Path path = Paths.get(part.getSubmittedFileName());
//            String fileName = path.getFileName().toString();
            String fileName = pathFileName;
            System.out.println("___FILE_NAME:  :"+fileName);
            File archivo = new File(part);
            InputStream input = new FileInputStream(archivo);
//            InputStream input = part.getInputStream();
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println("__  ______METODO:   :"+pathUploads.getAbsolutePath());
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            // leer los ficheros 
//            final File carpeta = new File("/home/usuario/Escritorio");
//            listarFicherosPorCarpeta(pathUploads);
            
            if (part != null) {
                File file = new File(pathUploads, fileName);
                System.out.println("____file.to.path:  :"+file.toPath());
                pathAbsolute = file.getAbsolutePath();
                
                System.out.println("____file.getAbsolutePath:  :"+pathAbsolute);
                System.out.println("____file.getAbsoluteFile:  :"+file.getAbsoluteFile());
                Files.copy(input, file.toPath()); // guardamos el archivo en la carpeta designada 
            }
        } catch (IOException e) {
            System.out.println(".");System.out.println(".");System.out.println(".");
            System.out.println("_________MODEL_______________FICHA_ATENCION_PAC___________________");
            System.out.println("_______saveFiles()___ERROR________________");
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE,null,e);
        }
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        return pathAbsolute;
    }
    
    
    
    
//    // [OBS]: METODO QUE UTILIZO PARA PODER VALIDAR LOS ARCHIVOS ADJUNTOS PARA AL MOMENTO DE COPIAR Y PEGAR EN LA CARPETA FINAL DE "DOWNLOAD" NO SALTE EL ERROR POR TENER YA A OTRO ARCHIVO CON EL MISMO NOMBRE 
//    public boolean ctrlArchivosAdjuntos(HttpServletRequest request, List<BeanFichaAtePacienteDet> PARAM_LIST_DET, File carpetaDownload) {
//        boolean valor = false;
//        System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
//        System.out.println("_   _   __   _   __   _   __* ctrlArchivosAdjuntos *__   _   __   _   __   _   _");
//        // recorro la lista que le paso por parametro para guardar o actualizar el detalle 
//        for (BeanFichaAtePacienteDet APDET_ROW : PARAM_LIST_DET) {
//            try {
//                String ID_FICHA = APDET_ROW.getOFPND_IDFICHAPAC();
//                String DIR_ARCHIVO = APDET_ROW.getOFPND_DIR_ARCHIVO().replace("\\","*");
//                String NAME_ARCHIVO = APDET_ROW.getOFPND_NAME_ARCHIVO();
//                // CONTROLO LA CARPETA 
//                if (ID_FICHA.isEmpty() || ID_FICHA.equals("")) {
//                    if (listarFicherosPorCarpeta(carpetaDownload, NAME_ARCHIVO) == true) { // SI SALTA LA VALIDACION 
//                        valor = true;
//                        request.setAttribute("VALI_CTRL_ADD_FILE_VARNAME", NAME_ARCHIVO); // LE PASO AL CONTROLADOR EL ARCHIVO QUE TIENE PROBLEMA DE NOMBRE 
//                        break; // CORTO EL FOR 
//                    }
//                }
//            } catch (Exception e) {
//                Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
//            }
//        } // END FOR 
//        System.out.println(".");System.out.println(".");System.out.println(".");
//        return valor;
//    }
//    
//    // [OBS]: METODO PARA RECORRER LA CARPETA DE "DOWNLOAD" (PORQUE HAY PASARE LOS ARCHIVOS PARA PODER DESCARGAR AL VER LA FICHA) Y CONTROLAR EL NAME DE LOS ARCHIVOS PARA EVITAR EL ERROR LUEGO DE GUARDAR EN LA BASE LOS NOMBRES Y PATH 
//    public boolean listarFicherosPorCarpeta(final File carpeta, String fileName) {
//        boolean valor = false;
//        System.out.println(".--------------------------------------------------------------------");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".   ___________listarFicherosPorCarpeta()___________");
//        for (final File ficheroEntrada : carpeta.listFiles()) {
//            System.out.println(".-");
//            System.out.println(".-");
//            System.out.println(".   for:1:__"+ficheroEntrada.getName());
//            System.out.println(".   for:2:__"+ficheroEntrada.getAbsolutePath());
//            System.out.println("_");
//            if (ficheroEntrada.isDirectory()) {
//                System.out.println("___IS_DIRECTORY_____");
//                listarFicherosPorCarpeta(ficheroEntrada, fileName);
//            } else {
//                System.out.println("____FILE_NAME______");
//                System.out.println(ficheroEntrada.getName());
//                if (fileName.equals(ficheroEntrada.getName())) {
//                    System.out.println("_   ______IF_______[EL_ARCHIVO_YA_EXISTE]_____");
//                    valor = true;
//                    break;
//                }
//            }
//            System.out.println("_");
//            System.out.println("_");
//            System.out.println("_");
//        }
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".-----------------------__END____listarFicherosPorCarpeta()_____-----------------------------");
//        return valor;
//    }
    
    
    
    
    public String modificar_cab(Object obj, List<BeanFichaAtePacienteDet> PARAM_LIST_DET) {
        String tipoRespuesta = "2"; // 1 : exito / 2 : error 
        BeanFichaAtePaciente datos = (BeanFichaAtePaciente) obj;
        
        try {
            String OFPN_IDFICHAPAC = datos.getOFPN_IDFICHAPAC();
            String OFPN_IDAGENDAMIENTO = datos.getOFPN_IDAGENDAMIENTO();
            String OFPN_ITEM_AGEND_DET = datos.getOFPN_ITEM_AGEND_DET();
            String OFPN_IDPACIENTE = datos.getOFPN_IDPACIENTE();
            String OFPN_FECHA_FICHA_ATE = datos.getOFPN_FECHA_FICHA_ATE();
            String OFPN_MOTIVO_DE_LA_CONSULTA = datos.getOFPN_MOTIVO_DE_LA_CONSULTA();
            String OFPN_ALIMENTOS_DE_PREFERENCIA = datos.getOFPN_ALIMENTOS_DE_PREFERENCIA();
            String OFPN_ALIMENTOS_QUE_NO_TOLERA = datos.getOFPN_ALIMENTOS_QUE_NO_TOLERA();
            String OFPN_ALI_QUE_SUELE_COMER_GRL = datos.getOFPN_ALI_QUE_SUELE_COMER_GRL();
            String OFPN_CONSUMO_ALCOHOL = datos.getOFPN_CONSUMO_ALCOHOL();
            String OFPN_CONSUMO_CIGARRILLO = datos.getOFPN_CONSUMO_CIGARRILLO();
            String OFPN_ALERGIAS_A_ALGO = datos.getOFPN_ALERGIAS_A_ALGO();
            String OFPN_CIRUGIAS = datos.getOFPN_CIRUGIAS();
            String OFPN_PADECE_ALGUNA_ENFERMEDAD = datos.getOFPN_PADECE_ALGUNA_ENFERMEDAD();
            String OFPN_MEDICAMENTE_Q_E_CONSUMIENDO = datos.getOFPN_MEDICAMENTE_Q_E_CONSUMIENDO();
            String OFPN_OTROS_DATOS_A_TENER_EN_CUENTA = datos.getOFPN_OTROS_DATOS_A_TENER_EN_CUENTA().replaceAll("\r\n","<br/>").replace("'","\\'");
            String OFPN_REALIZA_ACTIVIDAD_FISICA = datos.getOFPN_REALIZA_ACTIVIDAD_FISICA();
            String OFPN_TIPO_DE_ACTIVIDAD_FISICA = datos.getOFPN_TIPO_DE_ACTIVIDAD_FISICA();
            String OFPN_FRECUENCIA_ACT_FISICA_SEM = datos.getOFPN_FRECUENCIA_ACT_FISICA_SEM();
            String OFPN_DBLCR = datos.getOFPN_DBLCR();
            String OFPN_LGSLCM = datos.getOFPN_LGSLCM();
            String OFPN_TBDALN = datos.getOFPN_TBDALN();
            String OFPN_DPALN = datos.getOFPN_DPALN();
            String OFPN_DDCCF = datos.getOFPN_DDCCF();
            String OFPN_ESTRENHIMIENTO = datos.getOFPN_ESTRENHIMIENTO();
            String OFPN_TDEDBU = datos.getOFPN_TDEDBU();
            String OFPN_CANSANCIO_FATIGA = datos.getOFPN_CANSANCIO_FATIGA();
            String OFPN_HICHAZON_ABDOMINAL = datos.getOFPN_HICHAZON_ABDOMINAL();
            String OFPN_INSOMNIO = datos.getOFPN_INSOMNIO();
            String OFPN_MUCOSIDAD_Y_CATARRO = datos.getOFPN_MUCOSIDAD_Y_CATARRO();
            String OFPN_CALAMBRES_Y_HORMIGUEOS = datos.getOFPN_CALAMBRES_Y_HORMIGUEOS();
            String OFPN_ZUMBIDOS_EN_EL_OIDO = datos.getOFPN_ZUMBIDOS_EN_EL_OIDO();
            String OFPN_CAIDA_DE_CABELLO = datos.getOFPN_CAIDA_DE_CABELLO();
            String OFPN_UNHAS_QUEBRADIZAS = datos.getOFPN_UNHAS_QUEBRADIZAS();
            String OFPN_PIEL_SECA = datos.getOFPN_PIEL_SECA();
            String OFPN_TIPO_DE_METABOLISMO = datos.getOFPN_TIPO_DE_METABOLISMO();
            String OFPN_ESTATURA = datos.getOFPN_ESTATURA();
            String OFPN_PESO = datos.getOFPN_PESO();
            String OFPN_IMC = datos.getOFPN_IMC();
            String OFPN_PORC_GRASA = datos.getOFPN_PORC_GRASA();
            String OFPN_PORC_MUSCULO = datos.getOFPN_PORC_MUSCULO();
            String OFPN_VISCERAL = datos.getOFPN_VISCERAL();
            String OFPN_EDAD_METABOLICA = datos.getOFPN_EDAD_METABOLICA();
            String OFPN_RM = datos.getOFPN_RM();
            String OFPN_TIPO_DE_BALANZA = datos.getOFPN_TIPO_DE_BALANZA();
            String OFPN_RESULTADOS_TEST_BIORESONANCIA = datos.getOFPN_RESULTADOS_TEST_BIORESONANCIA();
            String OFPN_SUPLEMENTACION_RECETADA = datos.getOFPN_SUPLEMENTACION_RECETADA();
            String OFPN_LABORATORIO = datos.getOFPN_LABORATORIO();
            String OFPN_COMENTARIOS_GENERALES = datos.getOFPN_COMENTARIOS_GENERALES();
            String OFPN_USU_ALTA = datos.getOFPN_USU_ALTA();
            String OFPN_FEC_ALTA = datos.getOFPN_FEC_ALTA();
            String OFPN_ESTADO = datos.getOFPN_ESTADO();
            String OFPN_IDCLINICA = datos.getOFPN_IDCLINICA();
            String OFPN_USU_MODI = datos.getOFPN_USU_MODI();
            String OFPN_FEC_MODI = datos.getOFPN_FEC_MODI();
            
            String sql = "UPDATE ope_ficha_pac_nutri \n" +
                "SET FECHA_FICHA_ATE='"+OFPN_FECHA_FICHA_ATE+"', MOTIVO_DE_LA_CONSULTA='"+OFPN_MOTIVO_DE_LA_CONSULTA+"', ALIMENTOS_DE_PREFERENCIA='"+OFPN_ALIMENTOS_DE_PREFERENCIA+"', ALIMENTOS_QUE_NO_TOLERA='"+OFPN_ALIMENTOS_QUE_NO_TOLERA+"', ALI_QUE_SUELE_COMER_GRL='"+OFPN_ALI_QUE_SUELE_COMER_GRL+"', \n" +
                    "CONSUMO_ALCOHOL='"+OFPN_CONSUMO_ALCOHOL+"', CONSUMO_CIGARRILLO='"+OFPN_CONSUMO_CIGARRILLO+"', ALERGIAS_A_ALGO='"+OFPN_ALERGIAS_A_ALGO+"', CIRUGIAS='"+OFPN_CIRUGIAS+"', PADECE_ALGUNA_ENFERMEDAD='"+OFPN_PADECE_ALGUNA_ENFERMEDAD+"', \n" +
                    "MEDICAMENTE_Q_E_CONSUMIENDO='"+OFPN_MEDICAMENTE_Q_E_CONSUMIENDO+"', OTROS_DATOS_A_TENER_EN_CUENTA='"+OFPN_OTROS_DATOS_A_TENER_EN_CUENTA+"', REALIZA_ACTIVIDAD_FISICA='"+OFPN_REALIZA_ACTIVIDAD_FISICA+"', TIPO_DE_ACTIVIDAD_FISICA='"+OFPN_TIPO_DE_ACTIVIDAD_FISICA+"', \n" +
                    "FRECUENCIA_ACT_FISICA_SEM='"+OFPN_FRECUENCIA_ACT_FISICA_SEM+"', DBLCR='"+OFPN_DBLCR+"', LGSLCM='"+OFPN_LGSLCM+"', TBDALN='"+OFPN_TBDALN+"', DPALN='"+OFPN_DPALN+"', DDCCF='"+OFPN_DDCCF+"', ESTRENHIMIENTO='"+OFPN_ESTRENHIMIENTO+"', TDEDBU='"+OFPN_TDEDBU+"', CANSANCIO_FATIGA='"+OFPN_CANSANCIO_FATIGA+"', \n" +
                    "HICHAZON_ABDOMINAL='"+OFPN_HICHAZON_ABDOMINAL+"', INSOMNIO='"+OFPN_INSOMNIO+"', MUCOSIDAD_Y_CATARRO='"+OFPN_MUCOSIDAD_Y_CATARRO+"', CALAMBRES_Y_HORMIGUEOS='"+OFPN_CALAMBRES_Y_HORMIGUEOS+"', ZUMBIDOS_EN_EL_OIDO='"+OFPN_ZUMBIDOS_EN_EL_OIDO+"', CAIDA_DE_CABELLO='"+OFPN_CAIDA_DE_CABELLO+"', \n" +
                    "UNHAS_QUEBRADIZAS='"+OFPN_UNHAS_QUEBRADIZAS+"', PIEL_SECA='"+OFPN_PIEL_SECA+"', TIPO_DE_METABOLISMO='"+OFPN_TIPO_DE_METABOLISMO+"', ESTATURA='"+OFPN_ESTATURA+"', PESO='"+OFPN_PESO+"', IMC='"+OFPN_IMC+"', PORC_GRASA='"+OFPN_PORC_GRASA+"', PORC_MUSCULO='"+OFPN_PORC_MUSCULO+"', VISCERAL='"+OFPN_VISCERAL+"', \n" +
                    "EDAD_METABOLICA='"+OFPN_EDAD_METABOLICA+"', RM='"+OFPN_RM+"', TIPO_DE_BALANZA='"+OFPN_TIPO_DE_BALANZA+"', RESULTADOS_TEST_BIORESONANCIA='"+OFPN_RESULTADOS_TEST_BIORESONANCIA+"', SUPLEMENTACION_RECETADA='"+OFPN_SUPLEMENTACION_RECETADA+"', LABORATORIO='"+OFPN_LABORATORIO+"', COMENTARIOS_GENERALES='"+OFPN_COMENTARIOS_GENERALES+"', \n" +
//                    "USU_ALTA='"+OFPN_USU_ALTA+"', FEC_ALTA='"+OFPN_FEC_ALTA+"', " + 
                    "ESTADO='"+OFPN_ESTADO+"', "
//                    + "IDCLINICA='"+OFPN_IDCLINICA+"', "
                    + "USU_MODI='"+OFPN_USU_MODI+"', FEC_MODI="+OFPN_FEC_MODI+" " + 
                "WHERE IDFICHAPAC = '"+OFPN_IDFICHAPAC+"' \n";
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI----------------------");
            System.out.println("-- ---modificar()--------          "+sql);
            System.out.println("------------------------------------------------------------------------------");
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
                modificar_det(PARAM_LIST_DET, OFPN_IDFICHAPAC, OFPN_USU_ALTA);
            } else {
                tipoRespuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    public String modificar_det(List<BeanFichaAtePacienteDet> PARAM_LIST_DET, String PARAM_IDFICHAPAC, String PARAM_USU_ALTA) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("_   _   __   _   __   _   __* MODIFICAR_AP_DET *__   _   __   _   __   _   _");
        // recorro la lista que le paso por parametro para guardar o actualizar el detalle 
        for (BeanFichaAtePacienteDet APDET_ROW : PARAM_LIST_DET) {
            try {
                int varOperacion;
                String sql;
                String OFPND_IDFICHAPAC = APDET_ROW.getOFPND_IDFICHAPAC();
                String OFPND_ITEM = APDET_ROW.getOFPND_ITEM();
                String OFPND_IDSERVICIO = "0";
//                String OFPND_IDSERVICIO = APDET_ROW.getOFPND_IDSERVICIO();
                String OFPND_MONTO = "0";
//                String OFPND_MONTO = APDET_ROW.getOFPND_MONTO().replace(",",".").replace(".","");
                String OFPND_USU_ALTA = PARAM_USU_ALTA;
//                String OFPND_USU_ALTA = APDET_ROW.getOAPD_USU_ALTA();
                String OFPND_FEC_ALTA = FECHA_HORA_HOY;
//                String OFPND_FEC_ALTA = APDET_ROW.getOAPD_FEC_ALTA();
                String OFPND_ESTADO;
//                String OFPND_ESTADO = APDET_ROW.getOAPD_ESTADO();
//                if (OFPND_ESTADO.equalsIgnoreCase("INACTIVO") || OPHD_ESTADO.equalsIgnoreCase("I")) {
//                    OFPND_ESTADO = "I";
//                } else {
                    OFPND_ESTADO = "A"; // VOY A GUARDAR TODAS LAS FILAS CON ESTADO ACTIVO YA QUE ESTE ESTADO LO UTILIZARE PARA MOSTRARLE POR DEFECTO TODAS LAS LINEAS PERO CUANDO EL USUARIO ELIMINE ENTONCES LE INACTIVO NOMAS LA LINEA DEL DETALLE Y SOLAMENTE MOSTRARE LAS LINEAS DEL DETALLE ACTIVAS  
//                }
                String OFPND_DIR_ARCHIVO = APDET_ROW.getOFPND_DIR_ARCHIVO().replace("\\","*");
                String OFPND_NAME_ARCHIVO = APDET_ROW.getOFPND_NAME_ARCHIVO();
                
                System.out.println("_  __________________DATOS_____________________");
                System.out.println("_   __PARAM_IDATEPAC: :"+PARAM_IDFICHAPAC);
                System.out.println("_   __ID-FIC-ATEN:    :"+OFPND_IDFICHAPAC);
                System.out.println("_   __ITEM:           :"+OFPND_ITEM);
                System.out.println("_   __ID_SERVICIO:    :"+OFPND_IDSERVICIO);
                System.out.println("_   __MONTO:          :"+OFPND_MONTO);
                System.out.println("_   __ESTADO:         :"+OFPND_ESTADO);
                System.out.println("_   __DIR_ARCHIVO:    :"+OFPND_DIR_ARCHIVO);
                System.out.println("_   __NAME_ARCHIVO:   :"+OFPND_NAME_ARCHIVO);
                System.out.println("_______________________________________________");
                
                if (OFPND_IDFICHAPAC == null || OFPND_IDFICHAPAC.isEmpty()) {
                    varOperacion = 1; // INSERT 
                    OFPND_IDFICHAPAC = PARAM_IDFICHAPAC; // YA QUE ESTA NULO EL IDFICHAPAC POR INGRESAR UN NUEVO SERVICIO, ENTONCES PARA INSERTAR A LA TABLA LE CARGO EL IDFICHAPAC DE LA CABECERA PARA QUE LA LINEA SEPA CUAL ES SU "PADRE" 
                    OFPND_ITEM = maxNroItemNew(PARAM_IDFICHAPAC);
                    sql = "INSERT INTO ope_ficha_pac_nutri_det(IDFICHAPAC, ITEM, IDSERVICIO, MONTO, ESTADO, USU_ALTA, FEC_ALTA, DIR_ARCHIVO, NAME_ARCHIVO) \n" +
                    "VALUES('"+OFPND_IDFICHAPAC+"', '"+OFPND_ITEM+"', '"+OFPND_IDSERVICIO+"', '"+OFPND_MONTO+"', '"+OFPND_ESTADO+"', '"+OFPND_USU_ALTA+"', '"+OFPND_FEC_ALTA+"', '"+OFPND_DIR_ARCHIVO+"', '"+OFPND_NAME_ARCHIVO+"')  \n";
                } else {
                    varOperacion = 2; // UPDATE 
                    sql = "UPDATE ope_ficha_pac_nutri_det \n" +
                        "SET DIR_ARCHIVO='"+OFPND_DIR_ARCHIVO+"', NAME_ARCHIVO='"+OFPND_NAME_ARCHIVO+"', ESTADO='"+OFPND_ESTADO+"', USU_MODI='"+OFPND_USU_ALTA+"', FEC_MODI='"+OFPND_FEC_ALTA+"' \n" +
                        "WHERE IDFICHAPAC='"+PARAM_IDFICHAPAC+"' AND ITEM='"+OFPND_ITEM+"' \n";
                }
                System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI------------------------");
                System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
                System.out.println("--------------------------------------------------------------------------------");
                
                MFAP_CONEXION = devolverConex();
                MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
                int cantResul = MFAP_SENTENCIA.executeUpdate(sql);
                if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                    if (varOperacion == 2) {
                        tipoRespuesta = "1";
    //                    respuesta = "Se ha Modificado con Exito.";
                    } else {
//                        guardarFile(OFPND_DIR_ARCHIVO, OFPND_NAME_ARCHIVO); // SI LA OPERACION SE EJECUTA CORRECTAMENTE [PERO LA OPERACION DE INSERT Y NO LA DE UPDATE POR ESO COLOCO EN EL ELSE DEL IF DE varOperacion], ENTONCES GUARDARE EL FILE 
                        tipoRespuesta = "1";
    //                    respuesta = "Se ha Registrado con Exito.";
                    }
                } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                    tipoRespuesta = "2";
    //                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
                }
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
            }
        } // END FOR 
        return tipoRespuesta;
    }
    
    
    
    
    // METODO PARA ELIMINAR ARCHIVOS ADJUNTOS DE LA FICHA ATENCION DEL PACIENTE.-
    @Override
    public String eliminar(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        try {
            BeanFichaAtePacienteDet datos = (BeanFichaAtePacienteDet) obj;
            String OFPND_IDFICHAPAC = datos.getOFPND_IDFICHAPAC();
            String OFPND_ITEM = datos.getOFPND_ITEM();
            String OFPND_USU_ALTA = datos.getOFPND_USU_ALTA();
            String OFPND_FEC_ALTA = FECHA_HORA_HOY;
            
            String sql = "UPDATE ope_ficha_pac_nutri_det "
                    + "SET ESTADO = 'X', "
                    + "USU_MODI = '"+OFPND_USU_ALTA+"', "
                    + "FEC_MODI = '"+OFPND_FEC_ALTA+"' "
                    + "WHERE IDFICHAPAC = '"+OFPND_IDFICHAPAC+"' "
                    + "AND ITEM = '"+OFPND_ITEM+"' \n";
            System.out.println("---------------------MODEL_FICHA_ATENCION_PACIENTE-NUTRI------------------------");
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    
    public String eliminar_cab(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelInicioSesion modelIniSes = new ModelInicioSesion();
        String FECHA_HORA_HOY = modelIniSes.traerFechaHoraHoy();
        
        try {
            BeanFichaAtePaciente datos = (BeanFichaAtePaciente) obj;
            String OFPN_IDFICHAPAC = datos.getOFPN_IDFICHAPAC();
            String OFPN_IDAGENDAMIENTO = datos.getOFPN_IDAGENDAMIENTO();
            String OFPN_ITEM_AGEN = datos.getOFPN_ITEM_AGEND_DET();
            String OFPN_USU_MODI = datos.getOFPN_USU_MODI();
            String OFPN_FEC_MODI = FECHA_HORA_HOY;
            
            String sql = "UPDATE ope_ficha_pac_nutri "
                + "SET ESTADO = 'X', "
                + "USU_MODI = '"+OFPN_USU_MODI+"', "
                + "FEC_MODI = '"+OFPN_FEC_MODI+"' "
                + "WHERE IDFICHAPAC = '"+OFPN_IDFICHAPAC+"' "
                + "AND IDAGENDAMIENTO = '"+OFPN_IDAGENDAMIENTO+"' "
                + "AND ITEM_AGEND_DET = '"+OFPN_ITEM_AGEN+"' \n";
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    
    // METODO PARA TRAER UN NUEVO IDATENCION PARA PODER HACER UN INSERT Y GUARDAR LOS DATOS 
    public String traerNewIdAtencion() {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(IDFICHAPAC),0)+1) AS ID FROM ope_ficha_pac_nutri ";
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    // METODO PARA REALIZAR LA PAGINACION Y CONSULTA A LA TABLA DE FICHA DE ATENCION PACIENTE PARA FILTRAR 
    public List filtrar_paginacion(HttpSession PARAM_SESION, String PARAM_CBX_MOSTRAR, 
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
                "	OR COALESCE(( SELECT ss_oap.IDFICHAPAC FROM ope_ficha_pac_nutri ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM ),0) LIKE ('%"+PARAM_TXT_FILTRO+"%') " +
                ") \n";
        }
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            /*
            OBSERVACION:   USO EL MISMO SELECT QUE EN "pagAgendVer" (VER AGENDAMIENTO DEL MEDICO) 
                           PARA QUE EL MEDICO PUEDA VER TODOS LOS PACIENTES QUE TENGA AGENDADO EN EL DIA 
            */
            String sql = "SELECT cab.IDAGENDAMIENTO, det.ITEM AS ITEM_AGEN, \n" +
                "COALESCE(( SELECT ss_oap.IDFICHAPAC FROM ope_ficha_pac_nutri ss_oap WHERE ss_oap.ESTADO = 'A' AND ss_oap.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = det.ITEM LIMIT 1),0) AS IDATENCION_PAC, \n" +
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
//                "AND det.ITEM NOT IN (( SELECT ss_agen_det.ITEM-- , ss_agen_det.IDAGENDAMIENTO, ss_oap.IDFICHAPAC \n" +
//                "FROM ope_agendamiento_det ss_agen_det \n" +
//                "JOIN ope_agendamiento ss_agen_cab ON ss_agen_cab.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_agen_cab.ESTADO = 'A' \n" +
//                "JOIN ope_ficha_pac_nutri ss_oap ON ss_oap.IDAGENDAMIENTO = ss_agen_det.IDAGENDAMIENTO AND ss_oap.ITEM_AGEND_DET = ss_agen_det.ITEM AND ss_oap.ESTADO = 'A' \n" +
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
                        datos.setOAD_IDATENCION_PAC(MFAP_RESULTADO.getString("IDATENCION_PAC")); // SUBSELECT QUE DEVUELVE EL IDFICHAPACIENTE EN CASO DE QUE TENGA UNA FICHA DE ATENCION EL PACIENTE (EL MISMO AGENDAMIENTO Y NRO_ITEM) 
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
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
            String sql = "SELECT * FROM ope_ficha_pac_nutri_det "
                    + "WHERE IDFICHAPAC = '"+PARAM_IDATENCION+"' "
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    // METODO PARA TRAER UN NUEVO ITEM DE UN IDATENCION POR QUE EL ITEM YA EXISTE Y LE DOY UN NUEVO ITEM PARA QUE SE PUEDA INSERTAR LA LINEA Y NO SALTE ERROR POR DUPLICACION DE LLAVE DE IDATENCION - ITEM 
    public String maxNroItemNew(String PARAM_IDATENCION) {
        String valor = "";
        try {
            String sql = "SELECT (COALESCE(MAX(ITEM),0)+1) AS ITEM_NEW FROM ope_ficha_pac_nutri_det WHERE IDFICHAPAC = '"+PARAM_IDATENCION+"' ";
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    /*
        UTILIZO ESTE METODO EN LA PAGINA DE PACIENTE PARA VER TODAS LAS FICHAS DE ATENCION DEL PACIENTE QUE SE HAYA SELECCIONADO 
    */
    public List cargar_fichas_pacientes(HttpSession PARAM_SESION, String PARAM_IDPACIENTE) {
        List<BeanFichaAtePaciente> lista = new ArrayList<>();
//        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String ID_MEDICO = (String) PARAM_SESION.getAttribute("IDPERSONA");
//        String ID_PACIENTE = (String) PARAM_SESION.getAttribute("ID_PACIENTE");
        
        try {
            String sql = "SELECT cab.IDFICHAPAC, cab.IDAGENDAMIENTO, cab.ITEM_AGEND_DET, \n" +
//                "-- (CASE WHEN(cab.IDAGENDAMIENTO='0' AND cab.ITEM_AGEND_DET='0') THEN '-' ELSE (SELECT DATE_FORMAT(HORA,'%d/%m/%Y %H:%i') FROM ope_agendamiento_det WHERE IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND ITEM = cab.ITEM_AGEND_DET) END) AS FEC_AGEN, \n" +
                "COALESCE(DATE_FORMAT(agen_det.HORA,'%d/%m/%Y %H:%i'),'-') AS FEC_AGEN, \n" +
                "COALESCE(DATE_FORMAT(cab.FECHA_FICHA_ATE,'%d/%m/%Y %H:%i'),'-') AS FECHA_FICHA_ATE, \n" +
                "cab.IDPACIENTE, cab.MOTIVO_DE_LA_CONSULTA, \n" +
                "cab.PESO, cab.IMC, cab.PORC_GRASA, cab.PORC_MUSCULO, cab.VISCERAL, cab.EDAD_METABOLICA, cab.RM, cab.TIPO_DE_BALANZA, \n" +
                "cab.USU_ALTA, DATE_FORMAT(cab.FEC_ALTA, '%d/%m/%Y  %H:%i') AS FEC_ALTA, cab.ESTADO, cab.IDCLINICA \n" +
                "FROM ope_ficha_pac_nutri cab \n" +
                "LEFT JOIN ope_agendamiento_det agen_det ON agen_det.IDAGENDAMIENTO = cab.IDAGENDAMIENTO AND agen_det.ITEM = cab.ITEM_AGEND_DET AND agen_det.ESTADO NOT IN ('X') \n" +
                "WHERE cab.ESTADO = 'A' \n" +
                //"AND cab.IDCLINICA = '1' \n" +
                "AND cab.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n"
                + "ORDER BY cab.IDFICHAPAC DESC \n";
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-----------------------");
            System.out.println("-- ---cargar_fichas_pacientes()--------     "+sql);
            System.out.println("------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOFPN_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                    datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA_ATE"));
                    datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
                    datos.setOFPN_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
                    datos.setOFPN_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPN_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
                    datos.setOFPN_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                    // CARGO EL CAMPO AUXILIAR PARA MOSTRAR LA FECHA DE AGEN EN CASO DE QUE LA FICHA ESTE VINCULADA A UN AGENDAMIENTO 
                    datos.setOFPN_FEC_AGEN_AUX(MFAP_RESULTADO.getString("FEC_AGEN"));
                    // NEW AGREGADOS
                    datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                    datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                    datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                    datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                    datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                    datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
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
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC_NUTRI--------------------");
            System.out.println("-- ---controlarCierreAgend()-------    "+sql);
            System.out.println("-------------------------------------------------------------------");
            MFAP_CONEXION = devolverConex();
            MFAP_SENTENCIA = MFAP_CONEXION.createStatement();
            MFAP_SENTENCIA.executeUpdate(sql);
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
    /*
     * METODO QUE UTILIZO PARA CONTROLAR LAS LINEAS QUE TENGA EN LA LISTA (QUE VENDRIA SIENDO LOS INPUTS DE LOS BLOQUES) 
        Y ASI PODER RESETEAR O DARLE UN NUEVO VALOR EN CASO DE QUE EL USUARIO DEJE EN NULL O EN OTRO VALOR QUE ACTIVE UNA EXCEPCION 
        Y EVITE LA CONTINUACION DEL PRODECIMIENTO DEL EVENTO DONDE LO NECESITE / ES MAS QUE NADA PARA EVITAR LOS PAROS DE CODIGO POR UNA EXCEPCION QUE SALTE POR UN VALOR NO CORRESPONDIDO AL TIPO DE DATO COMO SUELEN SER LOS NullPointerException 
    */
    public List<String> ctrlAndResetListValueNull(List PARAMETROS, String VALUE_NULL) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   __________ctrlAndResetListValueNull()__________");
        System.out.println(".");
        System.out.println(".");
        for (int i = 0; i < PARAMETROS.size(); i++) {
            String PARAM = String.valueOf(PARAMETROS.get(i));
            System.out.println("_   _PARAM_VALUE_GET("+i+"):  :"+PARAM);
            if (PARAM == null || PARAM.isEmpty()) {
                PARAMETROS.set(i, VALUE_NULL);
            }
        }
        
        return PARAMETROS;
    }
    
    
    /*
     * METODO QUE UTILIZO PARA VALIDAR SI ES QUE ALGUN PARAMETRO O VALOR DE LA LISTA SEA NULL O VACIO 
        Y ASI MOSTRAR AL USUARIO EN LA PAGISNA PARA QUE PUEDA CARGAR ESE CAMPO 
        // ESTE METODO SE DEBE DE UTILIZAR PARA VALIDAR LISTA POR LISTA 
    */
    public boolean validateNull(List PARAMETROS) {
        boolean valor = false; // false : todo bien / true : todo mal
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   __________validateNull()__________");
        System.out.println(".");
        System.out.println(".");
        for (int i = 0; i <= PARAMETROS.size(); i++) {
            String PARAM = String.valueOf(PARAMETROS.get(i));
            System.out.println("_   _PARAM_VALUE_GET("+i+"):  :"+PARAM);
            if (PARAM == null || PARAM.isEmpty()) {
                valor = true;
                break;
            }
        }
        return valor;
    }
    
    
    // METODO QUE UTILIZO PARA PODER CARGAR LOS DATOS BASICOS DEL PACIENTE PARA LA FICHA 
    public List getDatosPaciente(String IDPACIENTE) {
        ModelPersona metodosPer = new ModelPersona();
        BeanPersona datosBPaciente = new BeanPersona();
        // DATOS DEL PACIENTE 
        String TXT_PAC_NOMAPE, TXT_PAC_FECHA_NAC, TXT_PAC_EDAD, TXT_PAC_SEXO, TXT_PAC_PROFESION, TXT_PAC_TELEFONO, TXT_PAC_CIUDAD, TXT_PAC_CORREO;
        // TRAER DATOS DEL PACIENTE QUE SE LE VA A CARGAR LA FICHA DE ATENCION 
        datosBPaciente = metodosPer.datosBasicosPersona(datosBPaciente, IDPACIENTE);
            TXT_PAC_NOMAPE = datosBPaciente.getRP_NOMBRE()+" "+datosBPaciente.getRP_APELLIDO();
            TXT_PAC_FECHA_NAC = datosBPaciente.getRP_FECHANAC();
            TXT_PAC_EDAD = String.valueOf(ModelInicioSesion.getPacEdad(TXT_PAC_FECHA_NAC));
            TXT_PAC_SEXO = datosBPaciente.getRP_SEXO();
            TXT_PAC_PROFESION = datosBPaciente.getRP_OCUPACION();
            TXT_PAC_TELEFONO = datosBPaciente.getRP_TELFPARTICULAR();
            TXT_PAC_CIUDAD = datosBPaciente.getRP_DESC_CIUDAD();
            TXT_PAC_CORREO = datosBPaciente.getRP_EMAIL();
        // INGRESO A UN ARRAY Y EL ARRAY NOMAS LE PASO POR PARAMETRO AL JSP PARA IR DEJANDO DE PASAR VARIABLE POR VARIABLE 
        List<String> datosPac = new ArrayList<>();
            datosPac.add(TXT_PAC_NOMAPE);
            datosPac.add(TXT_PAC_FECHA_NAC);
            datosPac.add(TXT_PAC_EDAD);
            datosPac.add(TXT_PAC_SEXO);
            datosPac.add(TXT_PAC_PROFESION);
            datosPac.add(TXT_PAC_TELEFONO);
            datosPac.add(TXT_PAC_CIUDAD);
            datosPac.add(TXT_PAC_CORREO);
        
        return datosPac;
    }
    
    
    
    /*
     METODO QUE UTILIZO PARA PAGINACION DE LA PAGINA DE REPORTE DE ESTADISTICA 
    */
    public List cargar_grilla_paginacion_rpt_est(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion_rpt_est()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanFichaAtePaciente> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
//        if (PARAM_NRO_REG_MOSTRAR.equals("10")) { // BORRAR 
//            System.out.println("_IF_CAMBIO_DE_VALOR_____");
//            PARAM_NRO_REG_MOSTRAR = "1";
//        } else if(PARAM_NRO_REG_MOSTRAR.equals("20")) {
//            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_NRO_REG_MOSTRAR = "2";
//        }
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")));
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
        String IDCATEG_PER_CLIENTE = "4"; // ID 4 = PACIENTE
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA, \n" +
                "(SELECT ofpn.IDFICHAPAC FROM ope_ficha_pac_nutri ofpn WHERE rp.IDPERSONA = ofpn.IDPACIENTE AND ofpn.ESTADO NOT IN ('X', 'I') LIMIT 1) AS IDFICHAPAC \n" +
//                ", (CASE WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssrh.IDPERSONA = rp.IDPERSONA LIMIT 1)) IS NULL THEN '0' \n" +
//                "	WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssrh.IDPERSONA = rp.IDPERSONA LIMIT 1)) = '*' THEN '1' \n" +
//                "	END) AS EXISTS_DET \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
                "AND rp.IDPERSONA NOT IN (0,1) \n" +
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("-----------------------------MODEL_FICHA_ATENCION_PAC_NUTRI-------------------------------");
            System.out.println("-- --cargar_grilla_paginacion_rpt_est()--------     "+sql);
            System.out.println("------------------------------------------------------------------------------------------");
            
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
                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                        // BEAN-FICHA-ATENCION
                        datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                        datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPERSONA"));
                        // BEAN-PERSONA
                        datos.setRP_IDPERSONA(MFAP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MFAP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MFAP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MFAP_RESULTADO.getString("CINRO"));
                        datos.setRP_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_TIPODOC(MFAP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCATEGORIA_PERSONA(MFAP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MFAP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MFAP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MFAP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MFAP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MFAP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MFAP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MFAP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MFAP_RESULTADO.getString("TELEFPARTICULAR"));
//                        // USO LA VARIABLE DE "EXISTS_DET" PARA SABER SI LA FICHA CUENTA CON UN DETALLE QUE VENDRIAN SIENDO LOS ARCHIVOS ADJUNTOS 
//                        if (MFAP_RESULTADO.getString("EXISTS_DET").equals("1")) {
//                            datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                        } else {
//                            datos.setOFPND_IDFICHAPAC("");
//                        }
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_EST_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
//        // PDF 
//        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_PAG", "1");
//        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_REG", ""+PARAM_NRO_REG_MOSTRAR+"");
        
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
    
    
    
    // METODO PARA LA PAGINACION Y FILTRO DE LA TABLA DE PERSONA POR EL IDCATEGORIA_PERSONA DE PACIENTE 
    public List filtrar_rpt_est(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FILTRO) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
//        PARAM_CBX_MOSTRAR = "1";
        System.out.println("___     ___________filtrar_rpt_est_paginacion()___________     ___");
        List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
            sqlFiltroTxt = "AND (UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                "	OR UPPER(rp.IDPERSONA) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.RAZON_SOCIAL) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.RUC) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%')" + 
                ")";
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        String IDCATEG_PER_CLIENTE = "4"; // ID 4 = PACIENTE
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA, \n" +
                "(SELECT ofpn.IDFICHAPAC FROM ope_ficha_pac_nutri ofpn WHERE rp.IDPERSONA = ofpn.IDPACIENTE LIMIT 1) AS IDFICHAPAC \n" +
//                ", (CASE WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssrh.IDPERSONA = rp.IDPERSONA LIMIT 1)) IS NULL THEN '0' \n" +
//                "	WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssrh.IDPERSONA = rp.IDPERSONA LIMIT 1)) = '*' THEN '1' \n" +
//                "	END) AS EXISTS_DET \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
                "AND rp.IDPERSONA NOT IN (0,1) \n" +
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n";
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_FICHA_ATENCION_PAC_NUTRI--------------------------");
            System.out.println("-- --filtrar_rpt_est_paginacion()--------     "+sql);
            System.out.println("--------------------------------------------------------------------------------");
            
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
                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                        // BEAN-FICHA-ATENCION
                        datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                        datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPERSONA"));
                        // BEAN-PERSONA
                        datos.setRP_IDPERSONA(MFAP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MFAP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MFAP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MFAP_RESULTADO.getString("CINRO"));
                        datos.setRP_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_TIPODOC(MFAP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCATEGORIA_PERSONA(MFAP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MFAP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MFAP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MFAP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MFAP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MFAP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MFAP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MFAP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MFAP_RESULTADO.getString("TELEFPARTICULAR"));
//                        // USO LA VARIABLE DE "EXISTS_DET" PARA SABER SI LA FICHA CUENTA CON UN DETALLE QUE VENDRIAN SIENDO LOS ARCHIVOS ADJUNTOS 
//                        if (MFAP_RESULTADO.getString("EXISTS_DET").equals("1")) {
//                            datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                        } else {
//                            datos.setOFPND_IDFICHAPAC("");
//                        }
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_EST_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS 
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
//        // PDF 
//        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_PAG", NRO_PAG_ACTUAL_MOSTRAR);
//        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_REG", ""+PARAM_CBX_MOSTRAR+"");
        
        return listaFiltro;
    }
    
    
    
    // METODO PARA LA PAGINA DE REPORTE DE ESTADISTICA 
    public List<BeanFichaAtePaciente> getListadoFichasPac(String IDPACIENTE) {
        List<BeanFichaAtePaciente> listado_de_fichas = new ArrayList<>();
        try {
            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDAGENDAMIENTO, ofpn.ITEM_AGEND_DET, ofpn.IDPACIENTE, DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%d/%m/%Y') AS FECHA_FICHA_ATE, \n" +
                "ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ESTATURA, ofpn.PESO, ofpn.IMC, ofpn.PORC_GRASA, ofpn.PORC_MUSCULO, ofpn.VISCERAL, ofpn.EDAD_METABOLICA, ofpn.RM, ofpn.TIPO_DE_BALANZA \n" +
                "FROM ope_ficha_pac_nutri ofpn \n" +
                "WHERE ofpn.ESTADO = 'A' \n" +
                "AND ofpn.IDPACIENTE = '"+IDPACIENTE+"' \n";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---getListadoFichasPac()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA_ATE"));
                    datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOFPN_ESTATURA(MFAP_RESULTADO.getString("ESTATURA"));
                    datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                    datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                    datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                    datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                    datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                    datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                listado_de_fichas.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado_de_fichas;
    }
    
    
    
    /*
    METODO QUE UTILIZO PARA LOS COMBOS DE TIPO DE ESTADISTICA DONDE UTILICE LAS OPCIONE DE "EN BARRA / EN LINEA" EN LA PAGINA DEL REPORTE DE ESTADISTICA 
    */
    public Map<String, String> cargarComboTipoEst(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("0", "En Lineas");
            lista.put("1", "En Barras");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("0") || paramValue.equalsIgnoreCase("No")) {
                lista.put("0", "En Lineas");
                lista.put("1", "En Barras");
            } else if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("Si")) {
                lista.put("1", "En Barras");
                lista.put("0", "En Lineas");
            } else {
                lista.put("0", "En Lineas");
                lista.put("1", "En Barras");
            }
        }
        return lista;
    } // end method 
    
    
    // METODO PARA EL COMBO DEL REPORTE DE ESTADISTICAS 
    public String getDatosTipoEst(String PARAM_SI_NO) {
        if (PARAM_SI_NO == null || PARAM_SI_NO.isEmpty()) {
            return "";
        } else if (PARAM_SI_NO.equals("0")) {
            return "En Lineas";
        } else if (PARAM_SI_NO.equals("1")) {
            return "En Barras";
        } else {
            return "";
        }
    }
    
    
    
    // METODO PARA TRAER LOS DATOS DE LA ULTIMA FICHA DE CONSULTA DEL PACIENTE QUE LE PASE POR PARAMETRO QUE UTILIZO PARA CARGAR EN EL EXPEDIENTE DEL PACIENTE // USO ESTE METODO EN EL CONTROLADOR PARA CARGAR LA LISTA CON LOS ULTIMOS VALORES DE LA ULTIMA LISTA PARA MOSTRAR AL MOMENTO DE CARGAR UNA FICHA 
    public List getUltimaFicha(String PARAM_IDPACIENTE) {
        List<BeanFichaAtePaciente> lista = new ArrayList<>();
        try {
            String sql = "SELECT IDFICHAPAC, IDAGENDAMIENTO, ITEM_AGEND_DET, IDPACIENTE, DATE_FORMAT(FECHA_FICHA_ATE, '%Y-%m-%d') AS FECHA_FICHA, DATE_FORMAT(FECHA_FICHA_ATE,'%H:%i') AS HORA_FICHA, \n" +
//                "-- BLOQUE 2-\n" +
//                "MOTIVO_DE_LA_CONSULTA, ALIMENTOS_DE_PREFERENCIA, ALIMENTOS_QUE_NO_TOLERA, ALI_QUE_SUELE_COMER_GRL, \n" +
//                "CONSUMO_ALCOHOL, CONSUMO_CIGARRILLO, ALERGIAS_A_ALGO, CIRUGIAS, PADECE_ALGUNA_ENFERMEDAD, \n" +
//                "MEDICAMENTE_Q_E_CONSUMIENDO, OTROS_DATOS_A_TENER_EN_CUENTA, REALIZA_ACTIVIDAD_FISICA, TIPO_DE_ACTIVIDAD_FISICA, \n" +
//                "FRECUENCIA_ACT_FISICA_SEM, DBLCR, LGSLCM, TBDALN, DPALN, DDCCF, ESTRENHIMIENTO, TDEDBU, CANSANCIO_FATIGA, \n" +
//                "HICHAZON_ABDOMINAL, INSOMNIO, MUCOSIDAD_Y_CATARRO, CALAMBRES_Y_HORMIGUEOS, ZUMBIDOS_EN_EL_OIDO, CAIDA_DE_CABELLO, \n" +
//                "UNHAS_QUEBRADIZAS, PIEL_SECA, TIPO_DE_METABOLISMO, \n" +
//                "-- BLOQUE 3-\n" +
                "ESTATURA, PESO, IMC, PORC_GRASA, PORC_MUSCULO, VISCERAL, EDAD_METABOLICA, RM, TIPO_DE_BALANZA, \n" +
//                "-- BLOQUE 4-\n" +
//                "RESULTADOS_TEST_BIORESONANCIA, SUPLEMENTACION_RECETADA, LABORATORIO, COMENTARIOS_GENERALES, \n" +
                "USU_ALTA, FEC_ALTA, ESTADO, IDCLINICA, USU_MODI, FEC_MODI \n" +
                "FROM ope_ficha_pac_nutri \n" +
                "WHERE IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n" +
                "AND ESTADO = 'A' \n" +
                "ORDER BY IDFICHAPAC DESC \n" +
                "LIMIT 1 \n";
            
//            String sql = "SELECT IDFICHAPAC, IDAGENDAMIENTO, ITEM_AGEND_DET, IDPACIENTE, \n" +
//                "DATE_FORMAT(FECHA_FICHA_ATE, '%Y-%m-%d') AS FECHA_FICHA, DATE_FORMAT(FECHA_FICHA_ATE,'%H:%i') AS HORA_FICHA, \n" +
//                "MOTIVO_DE_LA_CONSULTA, ALIMENTOS_DE_PREFERENCIA, ALIMENTOS_QUE_NO_TOLERA, ALI_QUE_SUELE_COMER_GRL, \n" +
//                "CONSUMO_ALCOHOL, CONSUMO_CIGARRILLO, ALERGIAS_A_ALGO, CIRUGIAS, PADECE_ALGUNA_ENFERMEDAD, \n" +
//                "MEDICAMENTE_Q_E_CONSUMIENDO, OTROS_DATOS_A_TENER_EN_CUENTA, REALIZA_ACTIVIDAD_FISICA, TIPO_DE_ACTIVIDAD_FISICA, \n" +
//                "FRECUENCIA_ACT_FISICA_SEM, DBLCR, LGSLCM, TBDALN, DPALN, DDCCF, ESTRENHIMIENTO, TDEDBU, CANSANCIO_FATIGA, \n" +
//                "HICHAZON_ABDOMINAL, INSOMNIO, MUCOSIDAD_Y_CATARRO, CALAMBRES_Y_HORMIGUEOS, ZUMBIDOS_EN_EL_OIDO, CAIDA_DE_CABELLO, \n" +
//                "UNHAS_QUEBRADIZAS, PIEL_SECA, TIPO_DE_METABOLISMO, ESTATURA, PESO, IMC, PORC_GRASA, PORC_MUSCULO, VISCERAL, \n" +
//                "EDAD_METABOLICA, RM, TIPO_DE_BALANZA, RESULTADOS_TEST_BIORESONANCIA, SUPLEMENTACION_RECETADA, LABORATORIO, COMENTARIOS_GENERALES, \n" +
//                "USU_ALTA, FEC_ALTA, ESTADO, IDCLINICA, USU_MODI, FEC_MODI \n" +
//                "FROM ope_ficha_pac_nutri \n" +
//                "WHERE IDFICHAPAC = '"+PARAM_IDPACIENTE+"' \n" +
//                "AND ESTADO = 'A' \n" ;
            System.out.println("--------------------MODEL_FICHA_ATENCION_PACIENTE-----------------------");
            System.out.println("-- ---getUltimaFicha()--------     "+sql);
            System.out.println("------------------------------------------------------------------------");
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    // CABECERA --
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOFPN_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                    datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA")+" "+MFAP_RESULTADO.getString("HORA_FICHA"));
                    // DATOS REFERENTES A LA CONSULTA 
//                    datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
//                    datos.setOFPN_ALIMENTOS_DE_PREFERENCIA(MFAP_RESULTADO.getString("ALIMENTOS_DE_PREFERENCIA"));
//                    datos.setOFPN_ALIMENTOS_QUE_NO_TOLERA(MFAP_RESULTADO.getString("ALIMENTOS_QUE_NO_TOLERA"));
//                    datos.setOFPN_ALI_QUE_SUELE_COMER_GRL(MFAP_RESULTADO.getString("ALI_QUE_SUELE_COMER_GRL"));
//                    datos.setOFPN_CONSUMO_ALCOHOL(MFAP_RESULTADO.getString("CONSUMO_ALCOHOL"));
//                    datos.setOFPN_CONSUMO_CIGARRILLO(MFAP_RESULTADO.getString("CONSUMO_CIGARRILLO"));
//                    datos.setOFPN_ALERGIAS_A_ALGO(MFAP_RESULTADO.getString("ALERGIAS_A_ALGO"));
//                    datos.setOFPN_CIRUGIAS(MFAP_RESULTADO.getString("CIRUGIAS"));
//                    datos.setOFPN_PADECE_ALGUNA_ENFERMEDAD(MFAP_RESULTADO.getString("PADECE_ALGUNA_ENFERMEDAD"));
//                    datos.setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(MFAP_RESULTADO.getString("MEDICAMENTE_Q_E_CONSUMIENDO"));
//                    datos.setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(MFAP_RESULTADO.getString("OTROS_DATOS_A_TENER_EN_CUENTA").replaceAll("<br/>","\r\n"));
                    // bloque de combos.-
//                    datos.setOFPN_REALIZA_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("REALIZA_ACTIVIDAD_FISICA"));
//                    datos.setOFPN_TIPO_DE_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("TIPO_DE_ACTIVIDAD_FISICA"));
//                    datos.setOFPN_FRECUENCIA_ACT_FISICA_SEM(MFAP_RESULTADO.getString("FRECUENCIA_ACT_FISICA_SEM"));
//                    datos.setOFPN_DBLCR(MFAP_RESULTADO.getString("DBLCR"));
//                    datos.setOFPN_LGSLCM(MFAP_RESULTADO.getString("LGSLCM"));
//                    datos.setOFPN_TBDALN(MFAP_RESULTADO.getString("TBDALN"));
//                    datos.setOFPN_DPALN(MFAP_RESULTADO.getString("DPALN"));
//                    datos.setOFPN_DDCCF(MFAP_RESULTADO.getString("DDCCF"));
//                    datos.setOFPN_ESTRENHIMIENTO(MFAP_RESULTADO.getString("ESTRENHIMIENTO"));
//                    datos.setOFPN_TDEDBU(MFAP_RESULTADO.getString("TDEDBU"));
//                    datos.setOFPN_CANSANCIO_FATIGA(MFAP_RESULTADO.getString("CANSANCIO_FATIGA"));
//                    datos.setOFPN_HICHAZON_ABDOMINAL(MFAP_RESULTADO.getString("HICHAZON_ABDOMINAL"));
//                    datos.setOFPN_INSOMNIO(MFAP_RESULTADO.getString("INSOMNIO"));
//                    datos.setOFPN_MUCOSIDAD_Y_CATARRO(MFAP_RESULTADO.getString("MUCOSIDAD_Y_CATARRO"));
//                    datos.setOFPN_CALAMBRES_Y_HORMIGUEOS(MFAP_RESULTADO.getString("CALAMBRES_Y_HORMIGUEOS"));
//                    datos.setOFPN_ZUMBIDOS_EN_EL_OIDO(MFAP_RESULTADO.getString("ZUMBIDOS_EN_EL_OIDO"));
//                    datos.setOFPN_CAIDA_DE_CABELLO(MFAP_RESULTADO.getString("CAIDA_DE_CABELLO"));
//                    datos.setOFPN_UNHAS_QUEBRADIZAS(MFAP_RESULTADO.getString("UNHAS_QUEBRADIZAS"));
//                    datos.setOFPN_PIEL_SECA(MFAP_RESULTADO.getString("PIEL_SECA"));
//                    datos.setOFPN_TIPO_DE_METABOLISMO(MFAP_RESULTADO.getString("TIPO_DE_METABOLISMO"));
                    // bloque de mediciones.-
                    datos.setOFPN_ESTATURA(MFAP_RESULTADO.getString("ESTATURA"));
                    datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                    datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                    datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                    datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                    datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                    datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                    // bloque final.-
//                    datos.setOFPN_RESULTADOS_TEST_BIORESONANCIA(MFAP_RESULTADO.getString("RESULTADOS_TEST_BIORESONANCIA"));
//                    datos.setOFPN_SUPLEMENTACION_RECETADA(MFAP_RESULTADO.getString("SUPLEMENTACION_RECETADA"));
//                    datos.setOFPN_LABORATORIO(MFAP_RESULTADO.getString("LABORATORIO"));
//                    datos.setOFPN_COMENTARIOS_GENERALES(MFAP_RESULTADO.getString("COMENTARIOS_GENERALES"));
//                    datos.setOFPN_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
//                    datos.setOFPN_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPN_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
                    datos.setOFPN_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
//                    datos.setOFPN_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
//                    datos.setOFPN_FEC_MODI(MFAP_RESULTADO.getString("PESO"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    
    /*
     METODO QUE UTILIZO PARA PAGINACION DE LA PAGINA DE REPORTE DE ESTADISTICA 
    */
//    public List cargar_grilla_paginacion_rptHistFAN(HttpSession sesionDatos, 
//            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
//            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
//            ) {
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
////        PARAM_NRO_REG_MOSTRAR = "1";
//        System.out.println("___     ___________cargar_grilla_paginacion_rpt_est()___________     ___");
//        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
//        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
//        List<BeanFichaAtePaciente> lista_mostrar = new ArrayList<>();
//        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
//        
////        if (PARAM_NRO_REG_MOSTRAR.equals("10")) { // BORRAR 
////            System.out.println("_IF_CAMBIO_DE_VALOR_____");
////            PARAM_NRO_REG_MOSTRAR = "1";
////        } else if(PARAM_NRO_REG_MOSTRAR.equals("20")) {
////            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
////            PARAM_NRO_REG_MOSTRAR = "2";
////        }
//        
//        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
//        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
////        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
//        
//        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
//        String cant_min = cant_min_fija;
////        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
//        
//        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
//        int CANT_CLICS_DERECHO = 1;
//        if (sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")).isEmpty()) {
//            CANT_CLICS_DERECHO = 1;
//        } else {
//            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")));
//        }
//        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
//        
////        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
////        String cant_resul = "131";
////        System.out.println("_   __CANTIDAD_DE_RESULTADOS:   :"+cant_resul);
//        
//        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
//        String cant_resultado="1";
//        
//        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
//        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
////        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
//        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
//        int cant_btn_lista_final = 1;
////        System.out.println(".");
////        System.out.println(".");
////        System.out.println(".");
//        
////        String sqlFiltroCbx;
////        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
////        if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
////            sqlFiltroCbx = "";
////        } else {
////            sqlFiltroCbx = "LIMIT "+PARAM_NRO_REG_MOSTRAR+"";
////        }
//        
//        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String IDCATEG_PER_CLIENTE = "4"; // ID 4 = PACIENTE
//        
//        try {
//            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
//                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA, \n" +
//                "(SELECT ofpn.IDFICHAPAC FROM ope_ficha_pac_nutri ofpn WHERE rp.IDPERSONA = ofpn.IDPACIENTE AND ofpn.ESTADO NOT IN ('X', 'I') LIMIT 1) AS IDFICHAPAC\n" +
//                "FROM rh_persona rp \n" +
//                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
//                "AND rp.IDPERSONA NOT IN (0,1) \n" +
//                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"
////                ""+sqlFiltroCbx+" \n"
//                    ;
//            
//            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
//                "FROM rh_persona rp \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
//                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
//                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
//                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
//            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
//            
//            System.out.println("-----------------------------MODEL_FICHA_ATENCION_PAC_NUTRI-------------------------------");
//            System.out.println("-- --cargar_grilla_paginacion_rpt_est()--------     "+sql);
//            System.out.println("------------------------------------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MFAP_RESULTADO = consultaBD(sql);
//            
//            // --------------------------------------------------------------------------------------------------------
//            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
//            if (PARAM_NRO_REG_MOSTRAR.equalsIgnoreCase("TODOS")) {
//                cant_btn_lista_final = 1;
//                PARAM_NRO_PAG_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
//            } else {
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".   __________ELSE___________");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
//                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
//                System.out.println(".   __  __CANT_RESULTADO:  :"+cant_resultado);
//                System.out.println(".   __  __NRO_REG_MOSTRAR: :"+PARAM_NRO_REG_MOSTRAR);
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
//                System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
//                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
//                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_NRO_REG_MOSTRAR);
//                System.out.println("_   _NUEVA_DIVISION:    :"+divi);
//                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
//                System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
//                if (resul_redondeo_btn == true) {
//                    System.out.println("_____________IF_____________");
//                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
//                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
//    //                for (String rb : resul_btn) {
//    //                    System.out.println("_   _partes_for:   :"+rb);
//    //                }
//                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
//                    if (Integer.parseInt(resul_btn[1]) == 0) {
//                        //
//                    } else {
//                        cant_btn_lista_final = cant_btn_lista_final + 1;
//                    }
//                    System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
//                } else {
//                    System.out.println("_____________ELSE_____________");
//                }
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                System.out.println(".");
//                // ---------------------------------------------------------------------------------------------------------------------------------------------------
//                System.out.println(".");
//                System.out.println(".   IF ( > ) ");
//                System.out.println("_   _PARAM_NRO_PAG_MOSTRAR:  :"+PARAM_NRO_PAG_MOSTRAR);
//                System.out.println("_   _cant_btns_lista_final:  :"+cant_btn_lista_final);
//                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
//                if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > cant_btn_lista_final) {
//                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
//                    PARAM_NRO_PAG_MOSTRAR = "1";
//                    CANT_CLICS_DERECHO = 1; 
//                } else {
//                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
//                    System.out.println("---------------------_______CONTROL_DE_LA_CANTIDAD_DE_CLIC_DERECHO__Y__LA_PAGINA_ACTUAL_A_MOSTRAR______--------------------------------");
//                    // BLOQUE DE CODIGO PARA CORREGIR EL CLIC DERECHO EN CASO DE QUE LA CANTIDAD DE BOTONES EXISTA PERO EL NRO DE PAG ACTUAL A MOSTRAR NO SE ENCUENTRE DENTRO DEL BLOQUE DE BOTONES DE 3 QUE DEVUELVE EL CLIC DERECHO ------------------------------
//                    // SI LA CANTIDAD DE CLICS DERECHO FUERA 2, LO MULTIPLICO POR 3 (PORQUE ES LA CANTIDAD DE BOTONES A MOSTRAR) Y AHI TENDRIA EL TERCER BOTON DE PAGINACION Y RESTANDO UNO Y DOS VALORES TENDRIAMOS LOS OTROS DOS BOTONES 
//                    // LO IMPORTANTE DE ESTO ES QUE LA PAGINA ACTUAL TENDRIA QUE ESTAR DENTRO DE ESTE RANGO, SINO SE ENCUENTRA ENTRE NINGUNA DE LAS TRES POSIBLIDADES DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHO NO COINCIDE Y LO REINICIARIA 
//                    int btn_3 = (CANT_CLICS_DERECHO*3);
//                    int btn_2 = btn_3-1;
//                    int btn_1 = btn_3-2;
//                    System.out.println("----__1__----------------");
//                    System.out.println("_   _nro_pag: :"+PARAM_NRO_PAG_MOSTRAR);
//                    System.out.println("-------------------------");
//                    System.out.println("_   _btn_3:  :"+btn_3);
//                    System.out.println("_   _btn_2:  :"+btn_2);
//                    System.out.println("_   _btn_1:  :"+btn_1);
//                    System.out.println("-------------------------");
//                    if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_3
//                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_2
//                            || Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) == btn_1
//                    ) { // SI EL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES, SIGNIFICA QUE ESTA TODO BIEN Y LA CANTIDAD DE CLIC DERECHO ES CORRECTA PUES EL NRO DE PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DEL RANGO 
//                        System.out.println("____IF______EL_NRO_DE_PAG_ACTUAL_ES_IGUAL_A_UNO_DE_LOS_TRES_BOTONES_______________");
//                    } else { // SI EL NRO DE PAG NO COINCIDE ES PORQUE LA CANTIDAD DE CLIC DERECHO ES INCORRECTO PUES LAS OPCIONES DE BOTONES QUE MUESTRA NO ES IGUAL AL BOTON QUE SE QUIERE MOSTRAR 
//                        System.out.println("____ELSE______EL_NRO_DE_PAG_NO_ES_IGUAL_A_NINGUNO_DE_LOS_TRES_BOTONES_______CANT_CLIC_DERECHO_ERRONEO________");
//                        // 1- PRIMERA CONDICIONAL PARA VERIFICAR SI EL NRO DE PAG ACTUAL A MOSTRAR ES MAYOR AL TERCER BOTON -
//                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) > btn_3) { // SI FUERA MAYOR ENTONCES HAY QUE SUMARLE UNO A LA CANTIDAD DE CLICS DERECHO PARA VER SI DENTRO DE LOS NUEVOS 3 BOTONES SE ENCUENTRA LA PAG A MOSTRAR 
//                            System.out.println("_   _1_____ES_MAYOR_AL_BTN_3_____");
//                            // BANDERA QUE ME DIRA SI ES QUE SE ENCONTRO LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
//                            int BAND_CORRECTO = 0;
//                            // HAGO UN FOR CON UN MINIMO DE 5 VUELTAS, QUE VENDRIA A SER LOS INTENTOS QUE HARE PARA ENCONTRAR LA CANTIDAD DE CLICS DERECHOS CORRECTA DONDE LA PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DE LOS TRES BOTONES DE ESE CLIC DERECHO 
//                            for (int i = 0; i < 5; i++) {
//                                CANT_CLICS_DERECHO = CANT_CLICS_DERECHO + 1;
//                                if (metodosIniSes.control_pagActualBotonera(Integer.parseInt(PARAM_NRO_PAG_MOSTRAR), CANT_CLICS_DERECHO) == true) {
//                                    System.out.println("__FOR_("+i+")______IF_________");
//                                    BAND_CORRECTO = 1; // LE CAMBIO SU VALOR PARA DECIRLE A LA PROXIMA CONDICIONAL QUE SI SE ENCONTRO A LA CANTIDAD DE CLICS DERECHO CORRECTA CON EL NRO DE PAG ACTUAL A MOSTRAR 
//                                    break; // cortaria el for para no continuar buscando y devolver la cantidad de clics derecho erronea por continuar con el for en caso de a la primera o segunda encontrar a la pagina actual 
//                                } else {
//                                    System.out.println("__FOR_("+i+")_____ else ________");
//                                }
//                            } // end for 
//                            System.out.println("_   __BANDERA_CORRECTO_CLIC_DERECHO_FOR:  :"+BAND_CORRECTO);
//                            // si al finalizar el for no se encontro a la cantidad de clics derecho que devuelva uno de los botones que pertenece a la pagina actual a mostrar, entonces reestableceria ambos valores para no tenerlos erroneo 
//                            if (BAND_CORRECTO == 0) { // NO HAY PROBLEMA EN REINICIAR ESTOS VALORES, PORQUE SI ENTRA EN ESTE IF ES PORQUE LA PAGINA ACTUAL ES MAYOR AL BTN 3 Y ENTONCES ESO QUIERE DECIR QUE NO ENTRARIA EN LA SIGUIENTE CONDICIONAL 
//                                System.out.println(".");
//                                System.out.println(".   _____BANDERA_NO FUE ACTIVADA____ REINICIO LOS VALORES __________");
//                                System.out.println(".");
//                                PARAM_NRO_PAG_MOSTRAR = "1";
//                                CANT_CLICS_DERECHO = 1;
//                            }
//                        }
//                        // 2- SEGUNDA CONDICIONAL PARA VERIFICAR SI LA PAGINA ACTUAL SE ENCUENTRA POR DEBAJO DEL PRIMER BOTON / PODRIA RESTARLE UNO A LA CANTIDAD DE CLICS DERECHOS Y PREGUNTAR POR LOS 3 BOTONES PERO LO VEO INNECESARIO Y LO REESTABLECERIA PARA MOSTRAR LOS PRIMERO 3 BOTONES DE LA PAGINA 
//                        if (Integer.parseInt(PARAM_NRO_PAG_MOSTRAR) < btn_1) {
//                            System.out.println("_   _2_____ES_MENOR_AL_BTN_1_____");
//                            CANT_CLICS_DERECHO = 1;
//                        }
//                    }
//                    System.out.println("--------------------------------------______END_CONTROL_DE_LAS_DOS_VARIABLES_____-----------------------------------------------------------------------");
//                }
//            }
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".   _nro_pag_mostrar:     :"+PARAM_NRO_PAG_MOSTRAR);
//            System.out.println(".   _cant_clics_derecho:  :"+CANT_CLICS_DERECHO);
//            System.out.println(".");
//            System.out.println(".   ___end___inicio del while-----");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            // --------------------------------------------------------------------------------------------------------
//            
//            int i = 1;
//            while (MFAP_RESULTADO.next()) {
////        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
////            System.out.println("_   _   ___FOR_i:  :"+i);
////            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);
//
//                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
//                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
////                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
//                    // INGRESO LOS DATOS A LA LISTA 
//                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//                        // BEAN-FICHA-ATENCION
//                        datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                        datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPERSONA"));
//                        // BEAN-PERSONA
//                        datos.setRP_IDPERSONA(MFAP_RESULTADO.getString("IDPERSONA"));
//                        datos.setRP_NOMBRE(MFAP_RESULTADO.getString("NOMBRE"));
//                        datos.setRP_APELLIDO(MFAP_RESULTADO.getString("APELLIDO"));
//                        datos.setRP_CINRO(MFAP_RESULTADO.getString("CINRO"));
//                        datos.setRP_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
//                        datos.setRP_TIPODOC(MFAP_RESULTADO.getString("TIPODOC"));
//                        datos.setRP_IDCATEGORIA_PERSONA(MFAP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
//                        datos.setRP_DESC_CATEG_PERSONA(MFAP_RESULTADO.getString("DESC_CATEG_PERSONA"));
//                        datos.setRP_RAZON_SOCIAL(MFAP_RESULTADO.getString("RAZON_SOCIAL"));
//                        datos.setRP_RUC(MFAP_RESULTADO.getString("RUC"));
//                        datos.setRP_DIRECCION(MFAP_RESULTADO.getString("DIRECCION"));
//                        datos.setRP_EMAIL(MFAP_RESULTADO.getString("EMAIL"));
//                        datos.setRP_TELFPARTICULAR(MFAP_RESULTADO.getString("TELFPARTICULAR"));
//                        datos.setRP_TELFMOVIL(MFAP_RESULTADO.getString("TELFMOVIL"));
//                        datos.setRP_TELEFPARTICULAR(MFAP_RESULTADO.getString("TELEFPARTICULAR"));
//                    lista_mostrar.add(datos);
//                }
//
//                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
//                if (cant_btn_lista > Integer.parseInt(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+PARAM_NRO_PAG_MOSTRAR+")______");
//                    break;
//                }
//
//                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
////                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
//                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
//                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
//                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
//                    if (cant_min.equals(String.valueOf(i))) {
//    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
//                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
//                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
//                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
//                        cant_btn_lista = cant_btn_lista + 1;
//    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
//                    }
//                }
////                System.out.println(".");
////                System.out.println(".");
//                i = i +1; // le incremento para no mantener el mismo numero 
//            } // end for or while 
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
//        }
//        
//        // CARGO LA PAGINA ACTUAL A MOSTRAR 
//        sesionDatos.setAttribute("PAG_RPT_EST_LISTA_ACTUAL", "1");
//        // CARGO EL TOTAL DE LISTAS
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
////        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
////        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
////        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
//        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
//        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
////        // PDF 
////        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_PAG", "1");
////        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_REG", ""+PARAM_NRO_REG_MOSTRAR+"");
//        
////        System.out.println(".");
////        System.out.println(".");
////        System.out.println("_________DATOS_FINALES____________");
////        System.out.println("_  __CANTIDAD_MIN_MOSTRAR:  :"+cant_min);
//////        System.out.println("_  __CANTIDAD_DE_RESUL:     :"+cant_resul);
////        System.out.println("_  __CANTIDAD_INI_DE_LISTA: :"+cant_btn_lista);
////        System.out.println("__________________________________");
////        System.out.println(".");
////        System.out.println(".");
////        System.out.println(".");
//        
//        return lista_mostrar;
//    } // end method 
//    
//    
//    
//    // METODO PARA LA PAGINACION Y FILTRO DE LA TABLA DE PERSONA POR EL IDCATEGORIA_PERSONA DE PACIENTE 
//    public List filtrar_rptHistFAN(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FILTRO) {
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
////        PARAM_CBX_MOSTRAR = "1";
//        System.out.println("___     ___________filtrar_rpt_est_paginacion()___________     ___");
//        List<BeanFichaAtePaciente> listaFiltro = new ArrayList<>();
//        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
//        
////        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
////            System.out.println("_IF_CAMBIO_DE_VALOR_____");
////            PARAM_CBX_MOSTRAR = "1";
////        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
////            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
////            PARAM_CBX_MOSTRAR = "2";
////        }
//        
//        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
//        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
//        if (sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL")).isEmpty()) {
//            NRO_PAG_ACTUAL_MOSTRAR = "1";
//        } else {
//            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_RPT_EST_LISTA_ACTUAL");
//        }
//        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
//        
//        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
//        int CANT_CLICS_DERECHO = 1;
//        if (sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")).isEmpty()) {
//            CANT_CLICS_DERECHO = 1;
//        } else {
//            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC")));
//        }
//        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
//        
//        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
//        String cant_min_fija = PARAM_CBX_MOSTRAR;
//        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
//        System.out.println("_   _CANTIDAD_INICIAL_ANTERIOR_DE_REGISTROS:    :"+cant_inicial_anterior);
//        // CONTROLO SI LA CANTIDAD MINIMA INCIAL DE LINEAS DE REGISTRO ES IGUAL A LA CANTIDAD DE REGISTROS A MOSTRAR, SI SON IGUALES, ENTONCES NO SE CAMBIO Y SOLO QUIERE VER LOS DATOS DE OTRA PAGINA, PERO SI ES DIFERENTE, ENTONCES ES PORQUE SE CAMBIO LA CANTIDAD DE REGISTROS A MOSTRAR Y VOLVERIA A MOSTRAR DESDE LA PAGINA 1 Y NO DESDE LA QUE ESTA PORQUE LA CANTIDAD DE BOTONES VAN A CAMBIAR PORQUE SE VAN A VOLVER A CALCULAR 
//        if (!(cant_inicial_anterior.equals(cant_min_fija))) { // SI NO SON IGUALES ENTONCES LE REINICIO LA PAGINA ACTUAL A MOSTRAR 
////            System.out.println("_   (*)__CANTIDAD_DE_REGISTROS_DISTINTA__");
//            NRO_PAG_ACTUAL_MOSTRAR = "1";
//            CANT_CLICS_DERECHO = 1;
//        } else { // NO REINICIARIA LA PAGINA ACTUAL A MOSTRAR SI SON IGUALES LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES LE CARGO ESA PAGINA YA QUE ESTARIA MUDANDO DE PAGINA Y NO REORDENANDO LOS REGISTROS POR PAGINA 
////            System.out.println("_   (*)___ELSE____CANTIDAD_DE_REGISTROS_IGUALES________");
//        }
//        
//        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
//        String cant_min = cant_min_fija;
////        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
//        
//        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
//        String cant_resultado="1";
//        
//        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
//        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
////        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
//        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
//        int cant_btn_lista_final = 1;
//        
////        String sqlFiltroCbx;
////        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
////        if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
////            sqlFiltroCbx = "";
////        } else {
////            sqlFiltroCbx = "LIMIT "+PARAM_CBX_MOSTRAR+"";
////        }
//        
//        String sqlFiltroTxt="";
//        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA AL WHERE 
//        if (PARAM_TXT_FILTRO == null || PARAM_TXT_FILTRO.isEmpty() || PARAM_TXT_FILTRO.equals(" ")) {
////            sqlFiltroTxt = "";
//        } else {
//            sqlFiltroTxt = "AND (UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                "	OR UPPER(rp.IDPERSONA) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
////                "	OR UPPER(rp.RAZON_SOCIAL) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
////                "	OR UPPER(rp.RUC) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%')" + 
//                ")";
//        }
//        
//        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
//        String IDCATEG_PER_CLIENTE = "4"; // ID 4 = PACIENTE
//        
//        try {
//            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
//                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA, \n" +
//                "(SELECT ofpn.IDFICHAPAC FROM ope_ficha_pac_nutri ofpn WHERE rp.IDPERSONA = ofpn.IDPACIENTE LIMIT 1) AS IDFICHAPAC\n" +
//                "FROM rh_persona rp \n" +
//                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
//                "AND rp.IDPERSONA NOT IN (0,1) \n" +
//                ""+sqlFiltroTxt+" \n" + 
//                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n";
//            
//            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
//                "FROM rh_persona rp \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
//                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
//                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
//                ""+sqlFiltroTxt+" \n" + 
//                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
//            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
//            
//            System.out.println("------------------------MODEL_FICHA_ATENCION_PAC_NUTRI--------------------------");
//            System.out.println("-- --filtrar_rpt_est_paginacion()--------     "+sql);
//            System.out.println("--------------------------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MFAP_RESULTADO = consultaBD(sql);
//            
//            // --------------------------------------------------------------------------------------------------------
//            // CONTROLO PRIMERAMENTE SI SE QUIERE MOSTRAR TODOS LOS REGISTROS, SI FUERA ASI NO TENDRIA QUE CALCULAR LA CANTIDAD DE BOTONES YA QUE SERIA UNO SOLO PORQUE TODOS LOS REGISTROS SE MOSTRARIAN EN UNA SOLA PAGINA 
//            if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
//                cant_btn_lista_final = 1;
//                NRO_PAG_ACTUAL_MOSTRAR = "1"; // SI SE MUESTRAN TODAS LAS FILAS ENTONCES LA PAGINA VA A SER UNA NOMAS 
//            } else {
//                // OBSERVACION: (LEER COMPLETO PARA ENTENDER EL BLOQUE DE CODIGO)---------------------------------------------------------------------------------------------------------------------------
//                // CALCULO LA CANTIDAD DE BOTONES DE LISTA QUE VOY A TENER DIVIDIENDO LA CANTIDAD DE RESULTADOS DEL SELECT POR LA CANTIDAD DE NROS DE REGISTROS A MOSTRAR QUE LE PASO POR PARAMETRO Y SI EL RESULTADO ES EXACTO, ENTONCES SALDRA UN NUMERO ENTERO (Ej.: 30/10=3[botones]) AHORA SI LA CANTIDAD DE FILAS RESULTADO DEL SELECT ES DISPAREJA A LA CANTIDAD DE REGISTROS A MOSTRAR, ENTONCES SALDRA UN DECIMAL COMO RESULTADO (Ej.: 24/10=2,4[botones]) COSA QUE EL DECIMAL VENDRIA SIENDO UN BOTON MAS CON UNOS REGISTROS A MOSTRAR PERO QUE SIMPLEMENTE NO ALCANZA A REDONDEAR LA CANTIDAD DE REGISTROS ESTABLECIDAS A MOSTRAR, DE AHI QUE REALIZO LA DIVISION EN EL FLOAT Y CONTROLO SI CUENTA CON EL PUNTO Y ME DIRIA SI ES DECIMAL O NO, Y SI LO FUERA ENTONCES LE SUMARIA UNO AL RESULTADO ENTERO QUE VENDRIA A SIENDO POR LA CANTIDAD DE REGISTROS DEL DECIMAL, (OBS.: NO VALE REDONDEAR POR QUE SE REDONDEA A PARTIR DE 5 PARA ARRIBA, PERO PUEDE PRESENTARSE CASOS COMO EL EJEMPLO DONDE EL DECIMAL SERIA MENOR A 5 Y NO LO REDONDEARIA PARA ARRIBA EVITANDO MOSTRAR ESTOS REGISTROS)  
//                cant_btn_lista_final = Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
//    //            System.out.println("_   _final__CANT_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
//                // AL DIVIDIR, Y AL SER NUMEROS ENTEROS, CUANDO LA CANTIDAD DE RESULTADOS ES MENOR A LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR, EL RESULTADO DA UN DECIMAL COMO RESPUESTA, QUE YA EQUIVALDRIA A UN BOTON DE PAGINA MAS DONDE MOSTRAR ESTOS DATOS QUE NO REDONDEAN LA CANTIDAD DE NRO DE REGISTROS A MOSTRAR 
//                float divi = (float) Integer.parseInt(cant_resultado) / Integer.parseInt(PARAM_CBX_MOSTRAR);
//    //            System.out.println("_   _NUEVA_DIVISION:    :"+divi);
//                boolean resul_redondeo_btn = String.valueOf(divi).contains("."); // si da un resultado decimal, va a mostrar un punto 
//    //            System.out.println("_   _BOOLEAN__RESULT_DECIMAL_BTN_LISTA_CANT_1:  :"+resul_redondeo_btn);
//                if (resul_redondeo_btn == true) {
//                    String divi1 = String.valueOf(divi).replace(".", ","); // sustitulo el punto por la coma para que la sentencia split reconozca y la divida 
//                    String[] resul_btn = divi1.split(","); // INGRESO EL RESULTADO DENTRO DE UN ARRAY Y DIVIDO SUS PARTES POR EL PUNTO PARA PODER CONTROLAR EL NUMERO DE LA PARTE DERECHA DEL PUNTO 
//    //                for (String rb : resul_btn) {
//    //                    System.out.println("_   _partes_for:   :"+rb);
//    //                }
//                    // CONTROLO SI EL NUMERO QUE LE SIGUE AL PUNTO, ES IGUAL A CERO, SI FUERA ASI, ES PORQUE EL RESULTADO ES REDONDO, Y SI NO, ES PORQUE COMO ACLARE EN EL COMENTARIO, HAY UN BLOQUE DE RESULTADO QUE NO ALCANZO LA CANTIDAD PARA CONSIDERARLO OTRO BOTON 
//                    if (Integer.parseInt(resul_btn[1]) == 0) {
//                        //
//                    } else {
//                        cant_btn_lista_final = cant_btn_lista_final + 1;
//                    }
//    //                System.out.println("_   _final__CANT_BTN_LISTA_FINAL_2:  :"+cant_btn_lista_final);
//                }
//                // ---------------------------------------------------------------------------------------------------------------------------------------------------
//                System.out.println("_   _NRO_PAG_ACTUAL_MOSTRAR: ("+NRO_PAG_ACTUAL_MOSTRAR+")  >  cant_btn_lista_final: ("+cant_btn_lista_final+") ____");
//                // CONTROLO SI ES QUE EL NRO ACTUAL DE PAGINA A MOSTRAR ES IGUAL O MENOR A LA CANTIDAD DE BOTONES QUE VA A TENER LA PAGINA, Y SI FUERA ASI ENTONCES LE DEJARIA QUE LE MUESTRE ESE RESULTADO PERO SI FUERA MAYOR ENTONCES QUIERE DECIR QUE LA PAGINA ANTERIOR YA NO EXISTE DENTRO DE LA CANTIDAD DE BOTONES A DEVOLVER, POR MOTIVO DE REESTRUCTURACION DE CANTIDAD DE REGISTROS A MOSTRAR O POR QUE LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT SEA MENOR POR LA ACTIVACION DE ALGUN FILTRO 
//                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) > cant_btn_lista_final) {
//                    System.out.println(".");
//                    System.out.println(".");
//                    System.out.println("_   ___IF___NRO_PAG_NO_EXISTE_EN_EL_NUEVO_TOTAL_CANT_BTNS______");
//                    NRO_PAG_ACTUAL_MOSTRAR = "1";
//                    CANT_CLICS_DERECHO = 1;
//                } else {
//                    System.out.println("_   ___ELSE____NRO_PAG_EXISTE_DENTRO_DEL_TOTAL_DE_CANT_DE_BTNS_____");
//                }
//            }
//            // --------------------------------------------------------------------------------------------------------
//            
//            // --------------------------------------------------------------------------------------------------------
//            // OBSERVACION: YA QUE LA NUMERACION DE LA PAGINA DONDE SE ENCONTRABA EL USUARIO NO SE ENCUENTRA EN EXISTENCIA EN LA NUEVA CANTIDAD DE BOTONES, ENTONCES LA CANTIDAD DE CLICS DERECHOS DEBO DE RESETEAR 
//            // VALIDACION PARA REESTABLECER LA CANTIDAD DE CLICS DERECHOS, PARA QUE SI POR EJEMPLO ESTABA EN EL BOTON NRO 4 Y REORDENA LA CANTIDAD DE REGISTROS A VISUALIZAR O AÑADE UN NUEVO FILTRO Y EL BOTON TOTAL SEA 2, COMO NO EXISTE MAS EL 4 EN ESA CLASIFICACION, ENTONCES LE DEVUELVA A LA PRIMERA PAGINA Y EL CLIC DERECHO VOLVERIA A SER 1 
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println("_  ____CONTROL_DE_CLICS_DERECHO_____  _");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            // OBSERVACION_01: EMPEZARIA CON LA VALIDACION DEL CLIC DERECHO EN CASO NO SE CUMPLA UNA DE ESTAS DOS DECLARACIONES, SI EL NRO DE PAGINA ACTUAL ES MENOR O IGUAL A TRES Y LA CANTIDAD DE CLICS DERECHO ES IGUAL A UNO ENTONCES SE DEVOLVERA TRUE, PERO SI UNO DE LOS DOS NO SE CUMPLIERA ENTONCES DEVOLVERA FALSE Y AHI ENTRARIA A VALIDAR PORQUE OSINO ENTRARIA A CALCULAR ALGO QUE POR LOGIA YA SÉ  
//            // OBSERVACION_02: EN LAS ANTERIORES VALIDACIONES YA SE RESETEA ESTOS VALORES O SE PUEDEN LLEGAR A SER MODIFICADAS, POR ESO EMPIEZO CON ESTA CONDICION, YA QUE DESDE ANTES YA PODRIA SER ARREGLADA 
//            if((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)<=3 && CANT_CLICS_DERECHO==1) == false) {
//                System.out.println("_     ____IF_____UNA_DE_LAS_DOS_CONDICIONES_NO_SE_CUMPLE____");
//                if (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= 3 ) { // SI ES MAYOR O IGUAL A 3, ENTONCES LA CANTIDAD DE CLICS ES DE 1, YA QUE SERIA LAS PRIMERA PAGINAS 
//                    System.out.println("_       __IF__VALOR_DENTRO_DE_LO_CALCULADO_______");
//                    CANT_CLICS_DERECHO = 1;
//                } else { // SI EL NUMERO DE PAGINA A DEVOLVER ES DISTINTO A LAS PRIMERA TRES PAGINAS, ENTONCES AHI SI TENDRIA QUE HAYAR LA UBICACION DEL NRO DE PAGINA ACTUAL MIENTRAS HAGO UN CONTEO DE CLICS A TRAVES DE UN FOR POR LA CANTIDAD FINAL (TOTAL) DE BOTONES DE LA PAGINA 
//                    System.out.println("_       __ELSE________IF_ELSE________");
//                    int ctrl_cant_adecuada = CANT_CLICS_DERECHO * 3;
//                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_03:  :"+(ctrl_cant_adecuada));
//                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_02:  :"+(ctrl_cant_adecuada-1));
//                    System.out.println("_         _CTRL_CANT_ADECUADA_/ _BTN_01:  :"+(ctrl_cant_adecuada-2));
//                    if ((Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= ctrl_cant_adecuada) 
//                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-1)) 
//                        || (Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR) <= (ctrl_cant_adecuada-2)) 
//                    ) { // SI EL BOTON DEL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES QUE SE ESTARIAN MOSTRANDO EN EL BLOQUE DE 3 BOTONES DE LA PAGINA, ENTONCES LA CANTIDAD DEL CLIC DERECHO ESTA BIEN, SI FUERA MENOR O MAYOR, ENTONCES LA CANTIDAD DE CLIC DERECHO NO ESTA CORRECTO Y TENDRIA QUE CALCULARLO 
//                        System.out.println("_          _IF___CANTIDAD_CLICS_DERECHO_CORRECTA___BTNS_DENTRO_DEL_CALCULO___");
//                    } else {
//                        System.out.println("_          _ELSE__CALCULAR_CLIC_DERECHO_______");
//                        System.out.println("_           _CANTIDAD_BTN_LISTA_FINAL:  :"+cant_btn_lista_final);
//                        int band_cant_clic_derechos = 1;
//                        int cant_botones_mostrar = 3; // EL VALOR ES 3 PORQUE 3 BOTONES ES LA CANTIDAD MAXIMA A MOSTRAR POR PAGINA 
//                        for (int i = 1; i <= cant_btn_lista_final; i++) {
//                            System.out.println(".");
//                            System.out.println(".");
//                            System.out.println("_       _____FOR_____   _"+i+"__");
//                            System.out.println("_       _PRIMER_BOTON_DEL_SIGUIEN_BLOQUE_DE_BOTONES:   :"+(cant_botones_mostrar+1));
//                            if (i == (cant_botones_mostrar+1)) {
//                                System.out.println("_       ___IF___SUM_BANDERA_CLIC_DERECHO_AND_CANT_BLOQUE_BTNS___");
//                                band_cant_clic_derechos = band_cant_clic_derechos + 1; // LE SUMO UNO A LA CANTIDAD DE CLICS EN CASO DE QUE SEA IGUAL AL PRIMER BOTON DE LA SIGUIENTE FORMACION DE LOS 3 BOTONES PORQUE SE ENTENDERIA COMO UN CLIC MAS HACIA LA DERECHA 
//                                cant_botones_mostrar = cant_botones_mostrar + 3; // LE SUMO 3 A LA CANTIDAD DE BOTONES QUE SE MUESTRAN PARA REPRESENTAR AL LIMITE DEL SIGUIEN CLIC DERECHO, EJEMPLO: PRIMERO SERIAN 3 Y SI FUERA 4, ENTONCES LE SUMO UN CLIC DERECHO Y 3 A ESTA VARIABLE Y EL PROXIMO LIMITE DE 3 BOTONES VISUALES SERIAN 6 Y AHI SI SERIA 7 ENTONCES VOLVERIA A SUMAR OTRO A LOS CLIC DERECHO Y 3 A LA CANTIDAD DE BOTONES, Y ASI HASTA LLEGAR A LA CANTIDAD FINAL DE BOTONES QUE HAY 
//                            }
//                            if (i == Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) { // CORTARIA EL FOR CUANDO LLEGUE A RECORRER AL BOTON ACTUAL A MOSTRAR, YA QUE NO QUIERO CALCULAR TODAS LAS CANTIDADES DE LOS BOTONES DERECHOS SINO LA CANTIDAD DE BOTONES DERECHOS QUE SE DIO PARA MOSTRAR AL BOTON ACTUAL 
//                                System.out.println("_       ____BREAK_FOR____");
//                                CANT_CLICS_DERECHO = band_cant_clic_derechos;
//                                System.out.println("_       _-CANTIDAD_DE_CLICS_DERECHO_ENCONTRADA:    :"+CANT_CLICS_DERECHO);
//                                break;
//                            }
//                        } // END FOR 
//                    }
//                } // END ELSE 
//            } else {
//                System.out.println("_     ____ELSE_____LAS_DOS_CONDICIONES__SE_CUMPLEN____");
//            }
//            // --------------------------------------------------------------------------------------------------------
//            
//            int i = 1;
//            while (MFAP_RESULTADO.next()) {
//                
//                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
//                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
//                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//                        // BEAN-FICHA-ATENCION
//                        datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                        datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPERSONA"));
//                        // BEAN-PERSONA
//                        datos.setRP_IDPERSONA(MFAP_RESULTADO.getString("IDPERSONA"));
//                        datos.setRP_NOMBRE(MFAP_RESULTADO.getString("NOMBRE"));
//                        datos.setRP_APELLIDO(MFAP_RESULTADO.getString("APELLIDO"));
//                        datos.setRP_CINRO(MFAP_RESULTADO.getString("CINRO"));
//                        datos.setRP_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
//                        datos.setRP_TIPODOC(MFAP_RESULTADO.getString("TIPODOC"));
//                        datos.setRP_IDCATEGORIA_PERSONA(MFAP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
//                        datos.setRP_DESC_CATEG_PERSONA(MFAP_RESULTADO.getString("DESC_CATEG_PERSONA"));
//                        datos.setRP_RAZON_SOCIAL(MFAP_RESULTADO.getString("RAZON_SOCIAL"));
//                        datos.setRP_RUC(MFAP_RESULTADO.getString("RUC"));
//                        datos.setRP_DIRECCION(MFAP_RESULTADO.getString("DIRECCION"));
//                        datos.setRP_EMAIL(MFAP_RESULTADO.getString("EMAIL"));
//                        datos.setRP_TELFPARTICULAR(MFAP_RESULTADO.getString("TELFPARTICULAR"));
//                        datos.setRP_TELFMOVIL(MFAP_RESULTADO.getString("TELFMOVIL"));
//                        datos.setRP_TELEFPARTICULAR(MFAP_RESULTADO.getString("TELEFPARTICULAR"));
//                    listaFiltro.add(datos);
//                }
//                
//                // SI LA CANTIDAD DE BOTON DE LA LISTA ES MAYOR YA AL BOTON DE LA PAGINA A MOSTRAR, CORTO EL WHILE PORQUE EL USUARIO YA VA A VER LOS REGISTROS DEL BOTON QUE PRESIONO 
//                if (cant_btn_lista > Integer.parseInt(NRO_PAG_ACTUAL_MOSTRAR)) {
//                    System.out.println("___IF____CORTAR_WHILE_____cant_btn_actual("+cant_btn_lista+") > nro_pag_mostrar("+NRO_PAG_ACTUAL_MOSTRAR+")______");
//                    break;
//                }
//                
//                // OBSERVACION: ESTE BLOQUE DE CODIGO DE IF, ME SIRVE MAS PARA IR ESCALANDO EL BOTON DE LA LISTA (cant_btn_lista) Y ASI IR COMPARANDO CON LA VARIABLE QUE ALMACENA EL NRO DE PAGINA A MOSTRAR (PARAM_NRO_PAG_MOSTRAR) 
////                System.out.println("___cant_min_("+cant_min+")_____for_I_("+i+")_____");
//                // CONTROLO PRIMERAMENTE QUE LA CANTIDAD_MINIMA NO SEA TODOS LOS REGISTROS, SI FUESE ASI NO HACE FALTA QUE ENTRE AL IF Y QUE CARGUE TODO EN UNA PAGINA, PERO SI NO LO ES ENTONCES SI LE DEJO ENTRAR PARA QUE CONTROLE LA CANTIDAD DE REGISTROS Y ASI PUEDA DIVIDIR LOS BOTONES 
//                if ((cant_min.equalsIgnoreCase("Todos")) == false) {
//                    // CONTROLO SI SE ALCANZO EL LIMITE DE RESULTADOS PEDIDOS 
//                    if (cant_min.equals(String.valueOf(i))) {
//    //                    System.out.println("____IF_____CANTIDAD_LIMITE_DE_RESULTADOS_ALCANZADA_______");
//                        // LE SUMO LA MISMA CANTIDAD PARA QUE NO SE MANTENGA EL MISMO NUMERO COMO META PORQUE EL ITEM AL SER ASCENDENTE NO VOLVERA A REPETIR / AUNQUE PUEDO CREAR OTRA VARIABLE QUE ME SIRVA DE CONTADOR Y BANDERA Y LO USÉ PARA COMPARARSE CON LA CANTIDAD DE RESULTADOS QUE SE QUIERE MOSTRAR Y CUANDO ENTRE AL IF LO VUELVA A RESETEAR A 1 Y ASI VOLVERIA A SUMARSE HASTA ALCANZAR EL LIMITE NUEVAMENTE Y RESETEARSE / PERO SUMARLE LA MISMA CANTIDAD ME PARECE MAS OPTIMO PORQUE UTILIZARIA MENOS LINEAS DE CODIGO QUE SI HICIERA LA OTRA OPCION 
//                        cant_min = String.valueOf(Integer.parseInt(cant_min) + Integer.parseInt(cant_min_fija));
//                        // LE SUMO AL CONTADOR UNO PARA QUE VAYA ASCENDENTE LA NUMERACION YA QUE ESTO EQUIVALE A LA CANTIDAD DE BOTONES 
//                        cant_btn_lista = cant_btn_lista + 1;
//    //                    System.out.println("__NUEVO_CANT_LISTA: :"+cant_btn_lista);
//                    }
//                }
////                System.out.println(".");
////                System.out.println(".");
//                i = i +1; // le incremento para no mantener el mismo numero 
//            } // end while 
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
//        }
//        
//        // CARGO LA PAGINA ACTUAL A MOSTRAR 
//        sesionDatos.setAttribute("PAG_RPT_EST_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
//        // CARGO EL TOTAL DE LISTAS 
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
////        sesionDatos.setAttribute("PAG_RPT_EST_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
//        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
//        sesionDatos.setAttribute("PAG_RPT_EST_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
////        // PDF 
////        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_PAG", NRO_PAG_ACTUAL_MOSTRAR);
////        sesionDatos.setAttribute("PDF_RPT_EST_VAR_AUX_NRO_REG", ""+PARAM_CBX_MOSTRAR+"");
//        
//        return listaFiltro;
//    }
    
    
    
    /*
     * METODO QUE UTILIZO PARA LA PAGINA DE HISTORICO DE FICHAS DE ATENCION PARA EL PACIENTE QUE SE ENCUENTRA EN EL REPORTE DE PACIENTE PARA MOSTRAR TODAS LAS FICHAS DEL PACIENTE 
    */
    public List<BeanFichaAtePaciente> getAllListasFichasPac(String IDPACIENTE) { // METODO PARA LA PAGINA DE "HISTORICO DE FICHA DE ATENCION" (NO CARGA EL METODO DEL HISTORICO DE FICHAS PARA EL REPORTE DE ESTADISTICA) 
        List<BeanFichaAtePaciente> listado_de_fichas = new ArrayList<>();
        try {
            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDCLINICA, oc.DESC_CLINICA, ofpn.IDAGENDAMIENTO, ofpn.ITEM_AGEND_DET, ofpn.IDPACIENTE, \n" +
                "DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%d/%m/%Y') AS FECHA_FICHA_ATE, DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%H:%i') AS HORA_FICHA, ofpn.ESTADO, \n" +
                "ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ESTATURA, ofpn.PESO, ofpn.IMC, ofpn.PORC_GRASA, ofpn.PORC_MUSCULO, ofpn.VISCERAL, ofpn.EDAD_METABOLICA, ofpn.RM, ofpn.TIPO_DE_BALANZA \n" +
                ", (CASE WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) IS NULL THEN '0' \n" +
                "	WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) = '*' THEN '1' \n" +
                "	END) AS EXISTS_DET \n" +
                "FROM ope_ficha_pac_nutri ofpn \n" +
                "JOIN ope_clinica oc ON oc.IDCLINICA = ofpn.IDCLINICA \n" +
                "WHERE ofpn.ESTADO = 'A' \n" +
                "AND ofpn.IDPACIENTE = '"+IDPACIENTE+"' \n" +
                "ORDER BY ofpn.IDFICHAPAC DESC \n";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC__NUTRI__--------------------");
            System.out.println("-- ---getAllListasFichasPac()-------    "+sql);
            System.out.println("----------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    // CABECERA --
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                    datos.setOFPN_DESC_CLINICA(MFAP_RESULTADO.getString("DESC_CLINICA"));
                    datos.setOFPN_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOFPN_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                    datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                    datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA_ATE")+" "+MFAP_RESULTADO.getString("HORA_FICHA"));
                    // DATOS REFERENTES A LA CONSULTA 
                    datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
//                    datos.setOFPN_ALIMENTOS_DE_PREFERENCIA(MFAP_RESULTADO.getString("ALIMENTOS_DE_PREFERENCIA"));
//                    datos.setOFPN_ALIMENTOS_QUE_NO_TOLERA(MFAP_RESULTADO.getString("ALIMENTOS_QUE_NO_TOLERA"));
//                    datos.setOFPN_ALI_QUE_SUELE_COMER_GRL(MFAP_RESULTADO.getString("ALI_QUE_SUELE_COMER_GRL"));
//                    datos.setOFPN_CONSUMO_ALCOHOL(MFAP_RESULTADO.getString("CONSUMO_ALCOHOL"));
//                    datos.setOFPN_CONSUMO_CIGARRILLO(MFAP_RESULTADO.getString("CONSUMO_CIGARRILLO"));
//                    datos.setOFPN_ALERGIAS_A_ALGO(MFAP_RESULTADO.getString("ALERGIAS_A_ALGO"));
//                    datos.setOFPN_CIRUGIAS(MFAP_RESULTADO.getString("CIRUGIAS"));
//                    datos.setOFPN_PADECE_ALGUNA_ENFERMEDAD(MFAP_RESULTADO.getString("PADECE_ALGUNA_ENFERMEDAD"));
//                    datos.setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(MFAP_RESULTADO.getString("MEDICAMENTE_Q_E_CONSUMIENDO"));
//                    datos.setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(MFAP_RESULTADO.getString("OTROS_DATOS_A_TENER_EN_CUENTA").replaceAll("<br/>","\r\n"));
                    // bloque de combos.-
//                    datos.setOFPN_REALIZA_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("REALIZA_ACTIVIDAD_FISICA"));
//                    datos.setOFPN_TIPO_DE_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("TIPO_DE_ACTIVIDAD_FISICA"));
//                    datos.setOFPN_FRECUENCIA_ACT_FISICA_SEM(MFAP_RESULTADO.getString("FRECUENCIA_ACT_FISICA_SEM"));
//                    datos.setOFPN_DBLCR(MFAP_RESULTADO.getString("DBLCR"));
//                    datos.setOFPN_LGSLCM(MFAP_RESULTADO.getString("LGSLCM"));
//                    datos.setOFPN_TBDALN(MFAP_RESULTADO.getString("TBDALN"));
//                    datos.setOFPN_DPALN(MFAP_RESULTADO.getString("DPALN"));
//                    datos.setOFPN_DDCCF(MFAP_RESULTADO.getString("DDCCF"));
//                    datos.setOFPN_ESTRENHIMIENTO(MFAP_RESULTADO.getString("ESTRENHIMIENTO"));
//                    datos.setOFPN_TDEDBU(MFAP_RESULTADO.getString("TDEDBU"));
//                    datos.setOFPN_CANSANCIO_FATIGA(MFAP_RESULTADO.getString("CANSANCIO_FATIGA"));
//                    datos.setOFPN_HICHAZON_ABDOMINAL(MFAP_RESULTADO.getString("HICHAZON_ABDOMINAL"));
//                    datos.setOFPN_INSOMNIO(MFAP_RESULTADO.getString("INSOMNIO"));
//                    datos.setOFPN_MUCOSIDAD_Y_CATARRO(MFAP_RESULTADO.getString("MUCOSIDAD_Y_CATARRO"));
//                    datos.setOFPN_CALAMBRES_Y_HORMIGUEOS(MFAP_RESULTADO.getString("CALAMBRES_Y_HORMIGUEOS"));
//                    datos.setOFPN_ZUMBIDOS_EN_EL_OIDO(MFAP_RESULTADO.getString("ZUMBIDOS_EN_EL_OIDO"));
//                    datos.setOFPN_CAIDA_DE_CABELLO(MFAP_RESULTADO.getString("CAIDA_DE_CABELLO"));
//                    datos.setOFPN_UNHAS_QUEBRADIZAS(MFAP_RESULTADO.getString("UNHAS_QUEBRADIZAS"));
//                    datos.setOFPN_PIEL_SECA(MFAP_RESULTADO.getString("PIEL_SECA"));
//                    datos.setOFPN_TIPO_DE_METABOLISMO(MFAP_RESULTADO.getString("TIPO_DE_METABOLISMO"));
                    // bloque de mediciones.-
                    datos.setOFPN_ESTATURA(MFAP_RESULTADO.getString("ESTATURA"));
                    datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                    datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                    datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                    datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                    datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                    datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                    datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                    datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                    // bloque final.-
//                    datos.setOFPN_RESULTADOS_TEST_BIORESONANCIA(MFAP_RESULTADO.getString("RESULTADOS_TEST_BIORESONANCIA"));
//                    datos.setOFPN_SUPLEMENTACION_RECETADA(MFAP_RESULTADO.getString("SUPLEMENTACION_RECETADA"));
//                    datos.setOFPN_LABORATORIO(MFAP_RESULTADO.getString("LABORATORIO"));
//                    datos.setOFPN_COMENTARIOS_GENERALES(MFAP_RESULTADO.getString("COMENTARIOS_GENERALES"));
//                    datos.setOFPN_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
//                    datos.setOFPN_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPN_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
//                    datos.setOFPN_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
//                    datos.setOFPN_FEC_MODI(MFAP_RESULTADO.getString("PESO"));
                    // USO LA VARIABLE DE "EXISTS_DET" PARA SABER SI LA FICHA CUENTA CON UN DETALLE QUE VENDRIAN SIENDO LOS ARCHIVOS ADJUNTOS 
                    if (MFAP_RESULTADO.getString("EXISTS_DET").equals("1")) {
                        datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    } else {
                        datos.setOFPND_IDFICHAPAC("");
                    }
                listado_de_fichas.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado_de_fichas;
    }
    
    
    
    
    /*
    METODO PARA CARGAR EL COMBO ESTADO PARA FILTRAR LAS FICHAS DEL PACIENTE (LO UTILIZO EN LA PAGINA DE: HISTORICO DE FICHAS DEL PACIENTE) 
    */
    public Map<String, String> cargarComboEstadoFA(String paramEstado) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramEstado == null || paramEstado.isEmpty() || paramEstado.equalsIgnoreCase("0") || paramEstado.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("A", "ACTIVO");
            lista.put("I", "INACTIVO");
            lista.put("X", "ANULADO");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramEstado.equalsIgnoreCase("ACTIVO") || paramEstado.equalsIgnoreCase("A")) {
                lista.put("A", "ACTIVO");
                lista.put("I", "INACTIVO");
                lista.put("X", "ANULADO");
            } else if (paramEstado.equalsIgnoreCase("INACTIVO") || paramEstado.equalsIgnoreCase("I")) {
                lista.put("I", "INACTIVO");
                lista.put("X", "ANULADO");
                lista.put("A", "ACTIVO");
            } else if (paramEstado.equalsIgnoreCase("ANULADO") || paramEstado.equalsIgnoreCase("X")) {
                lista.put("X", "ANULADO");
                lista.put("A", "ACTIVO");
                lista.put("I", "INACTIVO");
            } else {
                lista.put("A", "ACTIVO");
                lista.put("I", "INACTIVO");
                lista.put("X", "ANULADO");
            }
        }
        return lista;
    } // end method 
    
    
    
    // METODO PARA FILTRAR LA PAGINA DE HISTORICO DE FICHAS DE ATENCION DEL PACIENTE 
    public List<BeanFichaAtePaciente> filtrarFichasAtePac(
            HttpSession PARAM_SESION, 
            String PARAM_IDPACIENTE, 
            String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_BUSCAR, 
            String PARAM_TXT_FEC_INI, 
            String PARAM_TXT_FEC_FIN, 
            String PARAM_CBX_ESTADO 
    ) { // METODO PARA LA PAGINA DE "HISTORICO DE FICHA DE ATENCION" (NO CARGA EL METODO DEL HISTORICO DE FICHAS PARA EL REPORTE DE ESTADISTICA) 
        System.out.println("[.]");
        System.out.println("[.]");
        System.out.println("[.]");
        System.out.println("[.]");
        System.out.println("[+]___     ___________filtrar_paginacion_agend()___________     ___");
        List<BeanFichaAtePaciente> listado_de_fichas = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
//        System.out.println("[.]");System.out.println("[.]");
//        System.out.println("[.]");
//        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
//            System.out.println("[*]_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "1";
//        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
//            System.out.println("[*]_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "2";
//        }
//        System.out.println("[.]");
//        System.out.println("[.]");System.out.println("[.]");
        
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL");
        }
        System.out.println("[+]__NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("[+]__CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        System.out.println("[*] new control.---------------");
        if (PARAM_CBX_MOSTRAR == null || PARAM_CBX_MOSTRAR.isEmpty()) {
            System.out.println("[-] PARAM_CBX_MOSTRAR is null.-");
            PARAM_CBX_MOSTRAR = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
        } else {
            System.out.println("[+] PARAM_CBX_MOSTRAR not is null, its value is :"+PARAM_CBX_MOSTRAR);
        }
        
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        System.out.println("[*] CANT_MIN_FIJA:  :"+cant_min_fija);
        
        String cant_inicial_anterior = (String) PARAM_SESION.getAttribute("PAG_RPT_FICHA_APN_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
        // [OBS] :IF PARA ESTABLECER EL VALOR INICIAL DE LA CANTIDAD DE FILAS ANTERIOR YA QUE EL RPT DE FICHA NO CUENTA CON UN CARGA_GRILLA, DONDE SE ESTABLECE EL VALOR INICIAL EN LA SESION Y POR ESO UTILIZO ESTE IF PARA EVITAR UN STOP-ERROR.-
        if (cant_inicial_anterior == null || cant_inicial_anterior.isEmpty()) {
            System.out.println("[-] cant_inicial_anterior is null.");
            cant_inicial_anterior = metodosIniSes.minNroCbxCantFil(); // NRO DE REGISTROS A MOSTRAR EN LA GRILLA QUE MUESTRA EL COMBO POR DEFECTO 
            System.out.println("[+] the new value of the cant_inicial_anterior is ["+cant_inicial_anterior+"] because the old one was is null.");
        } else {
            System.out.println("[+] cant_inicial_anterior is not null, is value is: "+cant_inicial_anterior);
        }
//        System.out.println("_   _CANTIDAD_INICIAL_ANTERIOR_DE_REGISTROS:    :"+cant_inicial_anterior);
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
        
        String sqlWhere = "WHERE ofpn.ESTADO = '"+PARAM_CBX_ESTADO+"' \n";
        
        // SI SE ENCUENTRA VACIO EL CAMPO DE TEXTO ENTONCES NO AÑADIRIA NADA AL WHERE 
        if (PARAM_TXT_BUSCAR == null || PARAM_TXT_BUSCAR.isEmpty() || PARAM_TXT_BUSCAR.equals(" ")) {
//            sqlFiltroTxt = "";
        } else {
            sqlWhere = sqlWhere + "AND (UPPER(rp.NOMBRE) LIKE UPPER('%"+PARAM_TXT_BUSCAR+"%') \n" +
                "	OR UPPER(rp.APELLIDO) LIKE UPPER('%"+PARAM_TXT_BUSCAR+"%') \n" +
                "	OR UPPER(rp.CINRO) LIKE UPPER('%"+PARAM_TXT_BUSCAR+"%') \n" +
                "	OR UPPER(rp.IDPERSONA) LIKE UPPER('%"+PARAM_TXT_BUSCAR+"%') \n" +
                ")";
        }
        
        // control del combo de paciente.
        if (PARAM_IDPACIENTE == null || PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals(" ")) {
//            sqlWhere = sqlWhere + "";
        } else {
            sqlWhere = sqlWhere + "AND ofpn.IDPACIENTE = '"+PARAM_IDPACIENTE+"' \n";
        }
        
//        // control del combo de estado de la ficha.
//        if (PARAM_CBX_ESTADO == null || PARAM_CBX_ESTADO.isEmpty() || PARAM_CBX_ESTADO.equals(" ")) {
////            sqlWhere = sqlWhere + "";
//        } else {
//            sqlWhere = sqlWhere + "AND ofpn.ESTADO = '"+PARAM_CBX_ESTADO+"' \n";
//        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADAS LAS FECHAS PARA ASI AGREGUEGAR AL WHERE EL FILTRO POR LAS FECHAS / SI UNA SOLA FECHA ESTA CARGADA, ENTONCES VOY A HACER EL FILTRO POR ESA FECHA 
        if (PARAM_TXT_FEC_INI.equals("") && PARAM_TXT_FEC_FIN.equals("")) {
            //
        } else {
            // CONTROLO PRIMERAMENTE SI AMBAS FECHAS ESTAN CARGADAS 
            if (!(PARAM_TXT_FEC_INI.equals("")) && !(PARAM_TXT_FEC_FIN.equals(""))) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (VAR_WHERE.equals("")) {
//                    VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                } else {
                    sqlWhere = sqlWhere + "AND (DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%Y-%m-%d') >= '"+PARAM_TXT_FEC_INI+"' AND DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%Y-%m-%d') <= '"+PARAM_TXT_FEC_FIN+"') \n";
//                }
            } else { // SI NO ESTAN CARGADAS ENTONCES CONTROLO PARA VER SI UNO DE ELLAS NO ESTA CARGADA 
                if (!(PARAM_TXT_FEC_INI.equals(""))) {
//                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (VAR_WHERE.equals("")) {
//                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
//                    } else {
                        sqlWhere = sqlWhere + "AND DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%Y-%m-%d') = '"+PARAM_TXT_FEC_INI+"' \n";
//                    }
                }
                if (!(PARAM_TXT_FEC_FIN.equals(""))) {
//                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (VAR_WHERE.equals("")) {
//                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                    } else {
                        sqlWhere = sqlWhere + "AND DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%Y-%m-%d') = '"+PARAM_TXT_FEC_FIN+"' \n";
//                    }
                }
            }
        }
        
        try {
            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDPACIENTE, ofpn.IDCLINICA, oc.DESC_CLINICA, ofpn.IDAGENDAMIENTO, ofpn.ITEM_AGEND_DET, ofpn.IDPACIENTE, \n" +
                "DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%d/%m/%Y') AS FECHA_FICHA_ATE, DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%H:%i') AS HORA_FICHA, ofpn.ESTADO, \n" +
                "ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ESTATURA, ofpn.PESO, ofpn.IMC, ofpn.PORC_GRASA, ofpn.PORC_MUSCULO, ofpn.VISCERAL, ofpn.EDAD_METABOLICA, ofpn.RM, ofpn.TIPO_DE_BALANZA \n" +
                ", (CASE WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) IS NULL THEN '0' \n" +
                "	WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) = '*' THEN '1' \n" +
                "	END) AS EXISTS_DET \n" +
                ", rp.NOMBRE, rp.APELLIDO, rp.CINRO \n" +
                "FROM ope_ficha_pac_nutri ofpn \n" +
                "JOIN ope_clinica oc ON oc.IDCLINICA = ofpn.IDCLINICA \n" +
                "JOIN rh_persona rp ON ofpn.IDPACIENTE = rp.IDPERSONA \n" +
                ""+sqlWhere+" \n" +
                "ORDER BY ofpn.IDFICHAPAC DESC \n";
//            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDCLINICA, oc.DESC_CLINICA, ofpn.IDAGENDAMIENTO, ofpn.ITEM_AGEND_DET, ofpn.IDPACIENTE, \n" +
//                "DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%d/%m/%Y') AS FECHA_FICHA_ATE, DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%H:%i') AS HORA_FICHA, ofpn.ESTADO, \n" +
//                "ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ESTATURA, ofpn.PESO, ofpn.IMC, ofpn.PORC_GRASA, ofpn.PORC_MUSCULO, ofpn.VISCERAL, ofpn.EDAD_METABOLICA, ofpn.RM, ofpn.TIPO_DE_BALANZA \n" +
//                ", (CASE WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) IS NULL THEN '0' \n" +
//                "	WHEN((SELECT ('*') FROM rh_persona ssrh JOIN ope_ficha_pac_nutri ssofpn ON ssofpn.IDPACIENTE = ssrh.IDPERSONA JOIN ope_ficha_pac_nutri_det ssofpnd ON ssofpnd.IDFICHAPAC = ssofpn.IDFICHAPAC WHERE ssofpn.IDFICHAPAC = ofpn.IDFICHAPAC AND ssofpn.ESTADO='A' AND ssofpnd.ESTADO='A' LIMIT 1)) = '*' THEN '1' \n" +
//                "	END) AS EXISTS_DET \n" +
//                "FROM ope_ficha_pac_nutri ofpn \n" +
//                "JOIN ope_clinica oc ON oc.IDCLINICA = ofpn.IDCLINICA \n" +
//                ""+sqlWhere+" \n" +
//                "ORDER BY ofpn.IDFICHAPAC DESC \n";
            
            
//            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDCLINICA, oc.DESC_CLINICA, ofpn.IDAGENDAMIENTO, ofpn.ITEM_AGEND_DET, ofpn.IDPACIENTE, \n" +
//                "DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%d/%m/%Y') AS FECHA_FICHA_ATE, DATE_FORMAT(ofpn.FECHA_FICHA_ATE, '%H:%i') AS HORA_FICHA, ofpn.ESTADO, \n" +
//                "ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ESTATURA, ofpn.PESO, ofpn.IMC, ofpn.PORC_GRASA, ofpn.PORC_MUSCULO, ofpn.VISCERAL, ofpn.EDAD_METABOLICA, ofpn.RM, ofpn.TIPO_DE_BALANZA \n" +
//                "FROM ope_ficha_pac_nutri ofpn \n" +
//                "JOIN ope_clinica oc ON oc.IDCLINICA = ofpn.IDCLINICA \n" +
//                ""+sqlWhere+" \n" +
//                "ORDER BY ofpn.IDFICHAPAC DESC \n";
            
            String SELECT_COUNT = "SELECT COUNT(ofpn.IDFICHAPAC) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_ficha_pac_nutri ofpn \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN ope_clinica oc ON oc.IDCLINICA = ofpn.IDCLINICA \n" +
                "JOIN rh_persona rp ON ofpn.IDPACIENTE = rp.IDPERSONA \n" +
                ""+sqlWhere+" \n" +
                "ORDER BY ofpn.IDFICHAPAC DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC--------------------");
            System.out.println("-- ---filtrarFichasAtePac()-------    "+sql);
            System.out.println("-------------------------------------------------------------");
            
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
            while(MFAP_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                        // CABECERA --
                        datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                        datos.setOFPN_IDCLINICA(MFAP_RESULTADO.getString("IDCLINICA"));
                        datos.setOFPN_DESC_CLINICA(MFAP_RESULTADO.getString("DESC_CLINICA"));
                        datos.setOFPN_IDAGENDAMIENTO(MFAP_RESULTADO.getString("IDAGENDAMIENTO"));
                        datos.setOFPN_ITEM_AGEND_DET(MFAP_RESULTADO.getString("ITEM_AGEND_DET"));
                        datos.setOFPN_IDPACIENTE(MFAP_RESULTADO.getString("IDPACIENTE"));
                            datos.setOFPN_PAC_NOMBRE(MFAP_RESULTADO.getString("NOMBRE"));
                            datos.setOFPN_PAC_APELLIDO(MFAP_RESULTADO.getString("APELLIDO"));
                            datos.setOFPN_PAC_NROCI(MFAP_RESULTADO.getString("CINRO"));
                        datos.setOFPN_FECHA_FICHA_ATE(MFAP_RESULTADO.getString("FECHA_FICHA_ATE")+" "+MFAP_RESULTADO.getString("HORA_FICHA"));
                        // DATOS REFERENTES A LA CONSULTA 
                        datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
    //                    datos.setOFPN_ALIMENTOS_DE_PREFERENCIA(MFAP_RESULTADO.getString("ALIMENTOS_DE_PREFERENCIA"));
    //                    datos.setOFPN_ALIMENTOS_QUE_NO_TOLERA(MFAP_RESULTADO.getString("ALIMENTOS_QUE_NO_TOLERA"));
    //                    datos.setOFPN_ALI_QUE_SUELE_COMER_GRL(MFAP_RESULTADO.getString("ALI_QUE_SUELE_COMER_GRL"));
    //                    datos.setOFPN_CONSUMO_ALCOHOL(MFAP_RESULTADO.getString("CONSUMO_ALCOHOL"));
    //                    datos.setOFPN_CONSUMO_CIGARRILLO(MFAP_RESULTADO.getString("CONSUMO_CIGARRILLO"));
    //                    datos.setOFPN_ALERGIAS_A_ALGO(MFAP_RESULTADO.getString("ALERGIAS_A_ALGO"));
    //                    datos.setOFPN_CIRUGIAS(MFAP_RESULTADO.getString("CIRUGIAS"));
    //                    datos.setOFPN_PADECE_ALGUNA_ENFERMEDAD(MFAP_RESULTADO.getString("PADECE_ALGUNA_ENFERMEDAD"));
    //                    datos.setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(MFAP_RESULTADO.getString("MEDICAMENTE_Q_E_CONSUMIENDO"));
    //                    datos.setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(MFAP_RESULTADO.getString("OTROS_DATOS_A_TENER_EN_CUENTA").replaceAll("<br/>","\r\n"));
                        // bloque de combos.-
    //                    datos.setOFPN_REALIZA_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("REALIZA_ACTIVIDAD_FISICA"));
    //                    datos.setOFPN_TIPO_DE_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("TIPO_DE_ACTIVIDAD_FISICA"));
    //                    datos.setOFPN_FRECUENCIA_ACT_FISICA_SEM(MFAP_RESULTADO.getString("FRECUENCIA_ACT_FISICA_SEM"));
    //                    datos.setOFPN_DBLCR(MFAP_RESULTADO.getString("DBLCR"));
    //                    datos.setOFPN_LGSLCM(MFAP_RESULTADO.getString("LGSLCM"));
    //                    datos.setOFPN_TBDALN(MFAP_RESULTADO.getString("TBDALN"));
    //                    datos.setOFPN_DPALN(MFAP_RESULTADO.getString("DPALN"));
    //                    datos.setOFPN_DDCCF(MFAP_RESULTADO.getString("DDCCF"));
    //                    datos.setOFPN_ESTRENHIMIENTO(MFAP_RESULTADO.getString("ESTRENHIMIENTO"));
    //                    datos.setOFPN_TDEDBU(MFAP_RESULTADO.getString("TDEDBU"));
    //                    datos.setOFPN_CANSANCIO_FATIGA(MFAP_RESULTADO.getString("CANSANCIO_FATIGA"));
    //                    datos.setOFPN_HICHAZON_ABDOMINAL(MFAP_RESULTADO.getString("HICHAZON_ABDOMINAL"));
    //                    datos.setOFPN_INSOMNIO(MFAP_RESULTADO.getString("INSOMNIO"));
    //                    datos.setOFPN_MUCOSIDAD_Y_CATARRO(MFAP_RESULTADO.getString("MUCOSIDAD_Y_CATARRO"));
    //                    datos.setOFPN_CALAMBRES_Y_HORMIGUEOS(MFAP_RESULTADO.getString("CALAMBRES_Y_HORMIGUEOS"));
    //                    datos.setOFPN_ZUMBIDOS_EN_EL_OIDO(MFAP_RESULTADO.getString("ZUMBIDOS_EN_EL_OIDO"));
    //                    datos.setOFPN_CAIDA_DE_CABELLO(MFAP_RESULTADO.getString("CAIDA_DE_CABELLO"));
    //                    datos.setOFPN_UNHAS_QUEBRADIZAS(MFAP_RESULTADO.getString("UNHAS_QUEBRADIZAS"));
    //                    datos.setOFPN_PIEL_SECA(MFAP_RESULTADO.getString("PIEL_SECA"));
    //                    datos.setOFPN_TIPO_DE_METABOLISMO(MFAP_RESULTADO.getString("TIPO_DE_METABOLISMO"));
                        // bloque de mediciones.-
                        datos.setOFPN_ESTATURA(MFAP_RESULTADO.getString("ESTATURA"));
                        datos.setOFPN_PESO(MFAP_RESULTADO.getString("PESO"));
                        datos.setOFPN_IMC(MFAP_RESULTADO.getString("IMC"));
                        datos.setOFPN_PORC_GRASA(MFAP_RESULTADO.getString("PORC_GRASA"));
                        datos.setOFPN_PORC_MUSCULO(MFAP_RESULTADO.getString("PORC_MUSCULO"));
                        datos.setOFPN_VISCERAL(MFAP_RESULTADO.getString("VISCERAL"));
                        datos.setOFPN_EDAD_METABOLICA(MFAP_RESULTADO.getString("EDAD_METABOLICA"));
                        datos.setOFPN_RM(MFAP_RESULTADO.getString("RM"));
                        datos.setOFPN_TIPO_DE_BALANZA(MFAP_RESULTADO.getString("TIPO_DE_BALANZA"));
                        // bloque final.-
    //                    datos.setOFPN_RESULTADOS_TEST_BIORESONANCIA(MFAP_RESULTADO.getString("RESULTADOS_TEST_BIORESONANCIA"));
    //                    datos.setOFPN_SUPLEMENTACION_RECETADA(MFAP_RESULTADO.getString("SUPLEMENTACION_RECETADA"));
    //                    datos.setOFPN_LABORATORIO(MFAP_RESULTADO.getString("LABORATORIO"));
    //                    datos.setOFPN_COMENTARIOS_GENERALES(MFAP_RESULTADO.getString("COMENTARIOS_GENERALES"));
    //                    datos.setOFPN_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
    //                    datos.setOFPN_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                        datos.setOFPN_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
    //                    datos.setOFPN_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
    //                    datos.setOFPN_FEC_MODI(MFAP_RESULTADO.getString("PESO"));
                        // USO LA VARIABLE DE "EXISTS_DET" PARA SABER SI LA FICHA CUENTA CON UN DETALLE QUE VENDRIAN SIENDO LOS ARCHIVOS ADJUNTOS 
                        if (MFAP_RESULTADO.getString("EXISTS_DET").equals("1")) {
                            datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                        } else {
                            datos.setOFPND_IDFICHAPAC("");
                        }
                    listado_de_fichas.add(datos);
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
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        PARAM_SESION.setAttribute("PAG_RPT_FICHA_APN_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        PARAM_SESION.setAttribute("PAG_RPT_FICHA_APN_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        PARAM_SESION.setAttribute("PAG_RPT_FICHA_APN_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        PARAM_SESION.setAttribute("PAG_RPT_FICHA_APN_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        PARAM_SESION.setAttribute("PAG_RPT_FICHA_APN_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listado_de_fichas;
    }
    
    
    
    /*
     * METODO QUE UTILIZO PARA PODER RECUPERAR LA LISTA DE ARCHIVOS ADJUNTOS DEL DETALLE DE LA FICHA DE ATENCION.-
    */
    public List<BeanFichaAtePacienteDet> getDatosArchivosAdjuntos(String PARAM_IDFICHAPAC) {
        List<BeanFichaAtePacienteDet> listado_de_fichas = new ArrayList<>();
        try {
            String sql = "SELECT IDFICHAPAC, ITEM, -- IDSERVICIO, MONTO, \n" +
                "USU_ALTA, FEC_ALTA, ESTADO, USU_MODI, FEC_MODI, COALESCE(DIR_ARCHIVO,'') AS DIR_ARCHIVO, COALESCE(NAME_ARCHIVO,'') AS NAME_ARCHIVO \n" +
                "FROM ope_ficha_pac_nutri_det \n" +
                "WHERE IDFICHAPAC = '"+PARAM_IDFICHAPAC+"' \n" +
                "AND ESTADO = 'A' \n" +
                "ORDER BY ITEM ASC \n";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC-NUTRI--------------------");
            System.out.println("-- ---getDatosArchivosAdjuntos()-------    "+sql);
            System.out.println("-------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePacienteDet datos = new BeanFichaAtePacienteDet();
                    // CABECERA --
                    datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPND_ITEM(MFAP_RESULTADO.getString("ITEM"));
//                    datos.setOFPND_IDSERVICIO(MFAP_RESULTADO.getString("IDSERVICIO"));
//                    datos.setOFPND_MONTO(MFAP_RESULTADO.getString("MONTO"));
                    datos.setOFPND_ESTADO(MFAP_RESULTADO.getString("ESTADO"));
                    datos.setOFPND_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
                    datos.setOFPND_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPND_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
                    datos.setOFPND_FEC_MODI(MFAP_RESULTADO.getString("FEC_MODI"));
                    datos.setOFPND_DIR_ARCHIVO(MFAP_RESULTADO.getString("DIR_ARCHIVO").replace("*","\\"));
                    datos.setOFPND_NAME_ARCHIVO(MFAP_RESULTADO.getString("NAME_ARCHIVO"));
                listado_de_fichas.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado_de_fichas;
    }
    
    
//     public void exportarExcel(List obj) throws IOException {
//        JFileChooser chooser = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de exce", "xls");
//        chooser.setFileFilter(filter);
//        chooser.setDialogTitle("Guardar archivo");
//        chooser.setAcceptAllFileFilterUsed(false);
//        if(chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
//            String ruta = chooser.getSelectedFile().toString().concat(".xls");
//            try{
//                File archivoXLS = new File(ruta);
//                if (archivoXLS.exists()){
//                    archivoXLS.delete();
//                }
//                archivoXLS.createNewFile();
//                Workbook libro = new HSSFWorkbook();
//                FileOutputStream archivo =new FileOutputStream(archivoXLS);
//                Sheet hoja = libro.createSheet("Datos");
//                hoja.setDisplayGridlines(false);
//                // 
//                for (int i = 0; i < obj.size(); i++) {
//                    Row fila = hoja.createRow(i);
//                    
//                }
//                for(int f = 0; f < t.getColumnCount(); f++) {
//                    Row fila = hoja.createRow(f);
//                    for(int c = 0; c < t.getColumnCount(); c++){
//                        Cell celda = fila.createCell(c);
//                        if (f == 0) {
//                            celda.setCellValue(t.getColumnName(c));
//                        }
//                    }
//                }
//                
//                // 
//                int filaInicio = 1;
//                for(int f=0; f < t.getRowCount(); f++) {
//                    Row fila = hoja.createRow(filaInicio);
//                    filaInicio++;
//                    for(int c=0; c<t.getColumnCount(); c++){
//                        Cell celda = fila.createCell(c);
//                        if (t.getValueAt(f,c) instanceof Double){
//                            celda.setCellValue(Double.parseDouble(t.getValueAt(f,c).toString()));
//                        } else if (t.getValueAt(f,c) instanceof Float){
//                            celda.setCellValue(Float.parseFloat((String) t.getValueAt (f, c)));
//                        } else{
//                            celda.setCellValue(String.valueOf(t.getValueAt(f, c)));
//                        }
//                    }
//                }
//                
//                libro.write(archivo);
//                archivo.close();
//                Desktop.getDesktop().open(archivoXLS);
//            } catch(IOException | NumberFormatException e) {
//                throw e;
//            }
//        }
//    }
     
     
//	public static ByteArrayInputStream usersToExcel(List<BeanFichaAtePaciente> listaFichas) throws IOException {
//            String[] COLUMNs = { "Id", "Name", "Email", "First Name", "Last Name", "Address" };
//            try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//                Sheet sheet = workbook.createSheet("Users");
//                //
//                Font headerFont = workbook.createFont();
//                headerFont.setBold(true);
//                headerFont.setColor(IndexedColors.BLUE.getIndex());
//                //
//                CellStyle headerCellStyle = workbook.createCellStyle();
//                headerCellStyle.setFont(headerFont);
//                // Header Row
//                Row headerRow = sheet.createRow(0);
//                // Table Header
//                for (int col = 0; col < COLUMNs.length; col++) {
//                    Cell cell = headerRow.createCell(col);
//                    cell.setCellValue(COLUMNs[col]);
//                    cell.setCellStyle(headerCellStyle);
//                }
//                //
//                int rowIdx = 1;
//                for (BeanFichaAtePaciente ficha : listaFichas) {
//                    Row row = sheet.createRow(rowIdx++);
//                    //
//                    row.createCell(0).setCellValue(ficha.getId());
//                    row.createCell(1).setCellValue(ficha.getUsername());
//                    row.createCell(2).setCellValue(ficha.getUseremail());
//                    row.createCell(3).setCellValue(ficha.getUserfirstname());
//                    row.createCell(4).setCellValue(ficha.getUserlastname());
//                    row.createCell(5).setCellValue(ficha.getUseraddress());
//                }
//                //Auto-size all the above columns
//                sheet.autoSizeColumn(0);
//                sheet.autoSizeColumn(1);
//                sheet.autoSizeColumn(2);
//                sheet.autoSizeColumn(3);
//                sheet.autoSizeColumn(4);
//                sheet.autoSizeColumn(5);
//                //
//                workbook.write(out);
//                return new ByteArrayInputStream(out.toByteArray());
//            }
//	}
    
    
    
    /*
     * METODO QUE UTILIZO PARA CARGAR EL COMBO DEL REPORTE DE ESTADISTICA (VER DATOS), EL COMBO PARA SELECCIONAR EL GRAFICO QUE QUIERA DESCARGAR EN FORMATO PDF.-
    */
    public Map<String, String> cargarComboTipoGraf(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("estEstatura", "Estatura");
            lista.put("estPeso", "Peso");
            lista.put("estIMC", "IMC");
            lista.put("estPGM", "Porc. de Grasa y Músculo");
            lista.put("estPorcGrasa", "Porc. de Grasa");
            lista.put("estPorcMusc", "Porc. de Músculo");
            lista.put("estVisceral", "Visceral");
            lista.put("estEdadM", "Edad M.");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("0") || paramValue.equalsIgnoreCase("Estatura") || paramValue.equalsIgnoreCase("estEstatura")) {
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
            } else if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("Peso") || paramValue.equalsIgnoreCase("estPeso")) {
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
            } else if (paramValue.equalsIgnoreCase("2") || paramValue.equalsIgnoreCase("IMC") || paramValue.equalsIgnoreCase("estIMC")) {
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
            } else if (paramValue.equalsIgnoreCase("3") || paramValue.equalsIgnoreCase("Porc. de Grasa y Músculo") || paramValue.equalsIgnoreCase("estPGM")) {
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
            } else if (paramValue.equalsIgnoreCase("4") || paramValue.equalsIgnoreCase("Porc. de Grasa") || paramValue.equalsIgnoreCase("estPorcGrasa")) {
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
            } else if (paramValue.equalsIgnoreCase("5") || paramValue.equalsIgnoreCase("Porc. de Músculo") || paramValue.equalsIgnoreCase("estPorcMusc")) {
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
            } else if (paramValue.equalsIgnoreCase("6") || paramValue.equalsIgnoreCase("Visceral") || paramValue.equalsIgnoreCase("estVisceral")) {
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
            } else if (paramValue.equalsIgnoreCase("7") || paramValue.equalsIgnoreCase("Edad M.") || paramValue.equalsIgnoreCase("estEdadM")) {
                lista.put("estEdadM", "Edad M.");
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
            } else {
                lista.put("estEstatura", "Estatura");
                lista.put("estPeso", "Peso");
                lista.put("estIMC", "IMC");
                lista.put("estPGM", "Porc. de Grasa y Músculo");
                lista.put("estPorcGrasa", "Porc. de Grasa");
                lista.put("estPorcMusc", "Porc. de Músculo");
                lista.put("estVisceral", "Visceral");
                lista.put("estEdadM", "Edad M.");
            }
        }
        return lista;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA VER LOS ARCHIVOS ADJUNTOS DE UNA FICHA EN LA PAGINA DE HISTORICO DE FICHAS DE ATENCION DEL PACIENTE.-
    public List<BeanFichaAtePaciente> getDatosFichasDet(String PARAM_IDFICHAPAC) {
        List<BeanFichaAtePaciente> listado_det = new ArrayList<>();
        try {
            String sql = "SELECT ofpnd.IDFICHAPAC, ofpnd.ITEM, ofpnd.ESTADO AS ESTADO_DET, COALESCE(ofpnd.DIR_ARCHIVO,'') AS DIR_ARCHIVO, COALESCE(ofpnd.NAME_ARCHIVO,'') AS NAME_ARCHIVO, \n" +
                "ofpnd.USU_ALTA, ofpnd.USU_MODI, DATE_FORMAT(ofpnd.FEC_ALTA, '%d/%m/%Y') AS FEC_ALTA, DATE_FORMAT(ofpnd.FEC_MODI, '%d/%m/%Y') AS FEC_MODI \n" +
                "FROM ope_ficha_pac_nutri_det ofpnd \n" +
                "WHERE ofpnd.ESTADO = 'A' \n" +
                "AND ofpnd.IDFICHAPAC = '"+PARAM_IDFICHAPAC+"' \n";
            System.out.println("-----------------MODEL_FICHA_ATENCION_PAC-NUTRI--------------------");
            System.out.println("-- ---getDatosFichasDet()-------    "+sql);
            System.out.println("-------------------------------------------------------------------");
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            while(MFAP_RESULTADO.next()) {
                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPND_ITEM(MFAP_RESULTADO.getString("ITEM"));
//                    datos.setOFPND_IDSERVICIO(MFAP_RESULTADO.getString("IDSERVICIO"));
//                    datos.setOFPND_MONTO(MFAP_RESULTADO.getString("MONTO"));
                    datos.setOFPND_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
                    datos.setOFPND_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
                    datos.setOFPND_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
                    datos.setOFPND_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
                    datos.setOFPND_FEC_MODI(MFAP_RESULTADO.getString("FEC_MODI"));
                    datos.setOFPND_DIR_ARCHIVO(MFAP_RESULTADO.getString("DIR_ARCHIVO").replace("*","\\"));
                    datos.setOFPND_NAME_ARCHIVO(MFAP_RESULTADO.getString("NAME_ARCHIVO"));
                listado_det.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return listado_det;
    }
    
    
    
    // METODO QUE UTILIZO PARA RECUPERAR LOS DATOS DE LA PRIMERA FICHA DEL PACIENTE AL QUE SE LE ESTE CARGANDO SU FICHA.-
    public BeanFichaAtePaciente getDatosPrimeraFicha(String PARAM_IDFICHAPAC) {
        BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
//        List<BeanFichaAtePaciente> firstList = new ArrayList<>();
        try {
            String sql = "SELECT ofpn.IDFICHAPAC, ofpn.IDPACIENTE, ofpn.MOTIVO_DE_LA_CONSULTA, ofpn.ALIMENTOS_DE_PREFERENCIA, ofpn.ALI_QUE_SUELE_COMER_GRL, ofpn.ALIMENTOS_QUE_NO_TOLERA, ofpn.ALERGIAS_A_ALGO, ofpn.PADECE_ALGUNA_ENFERMEDAD, ofpn.CIRUGIAS, ofpn.MEDICAMENTE_Q_E_CONSUMIENDO, ofpn.OTROS_DATOS_A_TENER_EN_CUENTA, ofpn.CONSUMO_ALCOHOL, ofpn.CONSUMO_CIGARRILLO, \n" +
                "ofpn.REALIZA_ACTIVIDAD_FISICA, ofpn.TIPO_DE_ACTIVIDAD_FISICA, ofpn.FRECUENCIA_ACT_FISICA_SEM, ofpn.DBLCR, ofpn.LGSLCM, ofpn.TBDALN, ofpn.DPALN, ofpn.DDCCF, ofpn.ESTRENHIMIENTO, ofpn.TDEDBU, ofpn.CANSANCIO_FATIGA, ofpn.HICHAZON_ABDOMINAL, ofpn.INSOMNIO, ofpn.MUCOSIDAD_Y_CATARRO, ofpn.CALAMBRES_Y_HORMIGUEOS, ofpn.ZUMBIDOS_EN_EL_OIDO, ofpn.CAIDA_DE_CABELLO, \n" +
                "ofpn.UNHAS_QUEBRADIZAS, ofpn.PIEL_SECA, ofpn.TIPO_DE_METABOLISMO  \n" +
                "FROM ope_ficha_pac_nutri ofpn \n" +
                "WHERE ofpn.IDPACIENTE = '"+PARAM_IDFICHAPAC+"' \n" +
                "AND ofpn.ESTADO = 'A' \n" +
                "ORDER BY ofpn.IDFICHAPAC ASC \n" +
                "LIMIT 1 \n";
            System.out.println("[*]-----------------MODEL_FICHA_ATENCION_PAC-NUTRI--------------------");
            System.out.println("[*]-- ---getDatosPrimeraFicha()-------    "+sql);
            System.out.println("[*]-------------------------------------------------------------------");
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MFAP_RESULTADO = consultaBD(sql);
            while(MFAP_RESULTADO.next()) {
//                BeanFichaAtePaciente datos = new BeanFichaAtePaciente();
                    datos.setOFPN_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
                    datos.setOFPN_MOTIVO_DE_LA_CONSULTA(MFAP_RESULTADO.getString("MOTIVO_DE_LA_CONSULTA"));
                    datos.setOFPN_ALIMENTOS_DE_PREFERENCIA(MFAP_RESULTADO.getString("ALIMENTOS_DE_PREFERENCIA"));
                    datos.setOFPN_ALIMENTOS_QUE_NO_TOLERA(MFAP_RESULTADO.getString("ALIMENTOS_QUE_NO_TOLERA"));
                    datos.setOFPN_ALI_QUE_SUELE_COMER_GRL(MFAP_RESULTADO.getString("ALI_QUE_SUELE_COMER_GRL"));
                    datos.setOFPN_CONSUMO_ALCOHOL(MFAP_RESULTADO.getString("CONSUMO_ALCOHOL"));
                    datos.setOFPN_CONSUMO_CIGARRILLO(MFAP_RESULTADO.getString("CONSUMO_CIGARRILLO"));
                    datos.setOFPN_ALERGIAS_A_ALGO(MFAP_RESULTADO.getString("ALERGIAS_A_ALGO"));
                    datos.setOFPN_CIRUGIAS(MFAP_RESULTADO.getString("CIRUGIAS"));
                    datos.setOFPN_PADECE_ALGUNA_ENFERMEDAD(MFAP_RESULTADO.getString("PADECE_ALGUNA_ENFERMEDAD"));
                    datos.setOFPN_MEDICAMENTE_Q_E_CONSUMIENDO(MFAP_RESULTADO.getString("MEDICAMENTE_Q_E_CONSUMIENDO"));
                    datos.setOFPN_OTROS_DATOS_A_TENER_EN_CUENTA(MFAP_RESULTADO.getString("OTROS_DATOS_A_TENER_EN_CUENTA").replaceAll("<br/>","\r\n"));
                    datos.setOFPN_REALIZA_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("REALIZA_ACTIVIDAD_FISICA"));
                    datos.setOFPN_TIPO_DE_ACTIVIDAD_FISICA(MFAP_RESULTADO.getString("TIPO_DE_ACTIVIDAD_FISICA"));
                    datos.setOFPN_FRECUENCIA_ACT_FISICA_SEM(MFAP_RESULTADO.getString("FRECUENCIA_ACT_FISICA_SEM"));
                    datos.setOFPN_DBLCR(MFAP_RESULTADO.getString("DBLCR"));
                    datos.setOFPN_LGSLCM(MFAP_RESULTADO.getString("LGSLCM"));
                    datos.setOFPN_TBDALN(MFAP_RESULTADO.getString("TBDALN"));
                    datos.setOFPN_DPALN(MFAP_RESULTADO.getString("DPALN"));
                    datos.setOFPN_DDCCF(MFAP_RESULTADO.getString("DDCCF"));
                    datos.setOFPN_ESTRENHIMIENTO(MFAP_RESULTADO.getString("ESTRENHIMIENTO"));
                    datos.setOFPN_TDEDBU(MFAP_RESULTADO.getString("TDEDBU"));
                    datos.setOFPN_CANSANCIO_FATIGA(MFAP_RESULTADO.getString("CANSANCIO_FATIGA"));
                    datos.setOFPN_HICHAZON_ABDOMINAL(MFAP_RESULTADO.getString("HICHAZON_ABDOMINAL"));
                    datos.setOFPN_INSOMNIO(MFAP_RESULTADO.getString("INSOMNIO"));
                    datos.setOFPN_MUCOSIDAD_Y_CATARRO(MFAP_RESULTADO.getString("MUCOSIDAD_Y_CATARRO"));
                    datos.setOFPN_CALAMBRES_Y_HORMIGUEOS(MFAP_RESULTADO.getString("CALAMBRES_Y_HORMIGUEOS"));
                    datos.setOFPN_ZUMBIDOS_EN_EL_OIDO(MFAP_RESULTADO.getString("ZUMBIDOS_EN_EL_OIDO"));
                    datos.setOFPN_CAIDA_DE_CABELLO(MFAP_RESULTADO.getString("CAIDA_DE_CABELLO"));
                    datos.setOFPN_UNHAS_QUEBRADIZAS(MFAP_RESULTADO.getString("UNHAS_QUEBRADIZAS"));
                    datos.setOFPN_PIEL_SECA(MFAP_RESULTADO.getString("PIEL_SECA"));
                    datos.setOFPN_TIPO_DE_METABOLISMO(MFAP_RESULTADO.getString("TIPO_DE_METABOLISMO"));
                    //
                    datos.setOFPND_IDFICHAPAC(MFAP_RESULTADO.getString("IDFICHAPAC"));
//                    datos.setOFPND_ITEM(MFAP_RESULTADO.getString("ITEM"));
//                    datos.setOFPND_ESTADO(MFAP_RESULTADO.getString("ESTADO_DET"));
//                    datos.setOFPND_USU_ALTA(MFAP_RESULTADO.getString("USU_ALTA"));
//                    datos.setOFPND_FEC_ALTA(MFAP_RESULTADO.getString("FEC_ALTA"));
//                    datos.setOFPND_USU_MODI(MFAP_RESULTADO.getString("USU_MODI"));
//                    datos.setOFPND_FEC_MODI(MFAP_RESULTADO.getString("FEC_MODI"));
//                    datos.setOFPND_DIR_ARCHIVO(MFAP_RESULTADO.getString("DIR_ARCHIVO").replace("*","\\"));
//                    datos.setOFPND_NAME_ARCHIVO(MFAP_RESULTADO.getString("NAME_ARCHIVO"));
//                firstList.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFichaAtencionPacNutri.class.getName()).log(Level.SEVERE, null, e);
        }
        return datos;
    }
    
    
    
    
    
} // END CLASS 