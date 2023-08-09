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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanCuentaCliente;
import com.agend.javaBean.BeanFacturaCab;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelFactura implements CRUD {

    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_FACTURA.-------");
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
            MF_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MF_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MF_CONEXION;
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
    private static Connection MF_CONEXION = null;
    private static Statement MF_SENTENCIA = null;
    private static ResultSet MF_RESULTADO = null;
    
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
            MF_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MF_SENTENCIA = MF_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MF_RESULTADO = MF_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MF_RESULTADO;
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
            if (MF_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MF_RESULTADO.close();
            }
            if (MF_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MF_SENTENCIA.close();
            }
            if (MF_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MF_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    String VAR_ID_CLINICA="";
    DecimalFormat formatear = new DecimalFormat("###,###,###");
    DecimalFormat sin_formato = new DecimalFormat("#########");
    
    
    /*
    OBSERVACION: FILTRO Y TRAIGO UNA CANTIDAD DE REGISTROS QUE ES EL NRO POR DEFECTO QUE VA A MOSTRAR EN EL COMBO PARA TRAER LOS REGISTROS 
    */
    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        List<BeanFacturaCab> lista = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        try {
            String sql = " \n" +
                "LIMIT "+metodosIniSes.minNroCbxCantFil()+" \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---cargar_grilla()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                BeanFacturaCab datos = new BeanFacturaCab();
                    datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
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
        if (PARAM_NRO_PAG_MOSTRAR.isEmpty()) {
            PARAM_NRO_PAG_MOSTRAR = "1";
        }
        List<BeanFacturaCab> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
//        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
//        String cant_resul = "131";
//        System.out.println("_   __CANTIDAD_DE_RESULTADOS:   :"+cant_resul);
        
        // VARIABLE QUE ME VA A SERVIR PARA SABER LA CANTIDAD DE FILAS QUE DEVUELVE EL SELECT 
        String cant_resultado="1";
        
        // CANTIDAD DE BOTONES INICIAL DE LA LISTA 
        int cant_btn_lista = 1; // OBSERVACION: PARA OBTENER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER PODIA OBTENER LA CANTIDAD DE LINEAS DE REGISTROS QUE ME DEVUELVE O ME VA A DEVOLVER (CON UN COUNT) EL SELECT Y DIVIDIRLO POR LA CANTIDAD DE REGISTROS QUE QUIERO QUE SE MUESTRE EN CADA PAGINA PERO HACERLO DE ESTA FORMA UTILIZANDO EL WHILE CREO QUE TAMPOCO ESTA TAN MAL YA QUE AMBOS TIENEN SUS PROS Y CONTRAS (EN UNO QUE LLAMARIA A LA BASE OTRA VEZ PARA EL COUNT Y DE ESTA FORMA QUE RECORRE TODOS PARA CONTABILIZAR LAS LINEAS Y DIVIDIRLAS)
        // LE CREO ESTA NUEVA VARIABLE PARA NO UTILIZAR LA OTRA (cant_btn_lista), Y QUE LA OTRA ME SIRVA PARA GUIARME EN EL WHILE NOMAS Y ESTA PARA OTRA VALIDACION Y GUARDAR LA CANTIDAD DE BTNS FINAL 
        int cant_btn_lista_final = 1;
//        System.out.println("_   __CANTIDAD_INI_DE_LISTA:    :"+cant_btn_lista);
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
            String sql = "SELECT cab.IDFACTURA, cab.IDCLINICA, cab.NROFACTURA, cab.TIPOFACTURA, cab.IDCLIENTE, DATE_FORMAT(cab.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, cab.ESTADO, cab.TOTAL_FACTURA \n" +
                "FROM ope_factura cab \n" +
                "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND cab.ESTADO = 'C' \n" +
                "ORDER BY cab.IDFACTURA DESC \n" //+
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(cab.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_factura cab \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND cab.ESTADO = 'C' \n" +
                "ORDER BY cab.IDFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_IDCLINICA(MF_RESULTADO.getInt("IDCLINICA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
//                        datos.setOF_IDARQUEO(MF_RESULTADO.getString("IDARQUEO"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
//                        datos.setOF_TOTAL_DCTO(MF_RESULTADO.getString("TOTAL_DCTO"));
//                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
//                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
//                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
//                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
//                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
//                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
//                        datos.setOF_IDFORMACOBRO(MF_RESULTADO.getString("IDFORMACOBRO"));
//                        datos.setOF_LETRAS(MF_RESULTADO.getString("LETRAS"));
//                        datos.setOF_OBSERVACION(MF_RESULTADO.getString("OBSERVACION"));
//                        datos.setOF_USU_ALTA(MF_RESULTADO.getString("USU_ALTA"));
//                        datos.setOF_FEC_ALTA(MF_RESULTADO.getString("FEC_ALTA"));
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
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_FACT_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
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
     METODO QUE UTILIZO PARA PAGINACION DE LA PAGINA DEL REPORTE DE FACTURA 
    */
    public List cargar_grilla_paginacion_rtp_fact(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            , String PARAM_IDPACIENTE // PARAMETRO QUE UTILIZO PARA CARGAR EL IDPACIENTE CUANDO EL PERFIL SEA PACIENTE, YA QUE ESTE METODO UTILIZO EN VARIOS LUGARES, Y LO DIVIDO CON ESTA VARIABLE, SI ESTA CARGADO ENTONCES EL PERFIL ES DE PACIENTE, Y SI ESTA VACIO, ENTONCES EL PERFIL PUEDE SER ADMIN, SECRETARIO O MEDICO 
        ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion_rtp_fact()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanFacturaCab> lista_mostrar = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC")));
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
            String sql = "";
            /*
             * OBSERVACION: SI EL IDPACIENTE QUE VIENE POR PARAMETRO, ESTA VACIO, ENTONCES EL IDPERFIL ES DE ADMIN O SECRE O OTRO PERFIL 
                PERO SI VIENE CARGADO, ENTONCES EL PERFIL ES PACIENTE Y LE FILTRO PARA MOSTRAR LAS FACTURAS DE ESE PACIENTE Y NO DE OTROS 
            */
            if (PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
                sql = "SELECT cab.IDFACTURA, cab.IDCLINICA, cab.NROFACTURA, cab.TIPOFACTURA, cab.IDCLIENTE, DATE_FORMAT(cab.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, cab.ESTADO, cab.TOTAL_FACTURA \n" +
                    ", cab.TOTAL_DCTO, cab.TOTAL_IVA10, cab.TOTAL_IVA5, cab.TOTAL_GRAV10, cab.TOTAL_GRAV5, cab.TOTAL_EXENTA, cab.TOTAL_IVA, cab.IDFORMACOBRO, cab.LETRAS, cab.OBSERVACION, cab.USU_ALTA, cab.FEC_ALTA \n" +
                    "FROM ope_factura cab \n" +
                    "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                    "AND cab.ESTADO = 'C' \n" +
                    "ORDER BY cab.IDFACTURA DESC \n" //+
//                    ""+sqlFiltroCbx+" \n"
                        ;
                // MISMO SELECT PERO CON EL OBJETIVO DE RECUPERAR LA CANTIDAD DE FILAS QUE VA A RECUPERAR EL SELECT PRINCIPAL 
                String SELECT_COUNT = "SELECT COUNT(cab.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                    "FROM ope_factura cab \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                    "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                    "AND cab.ESTADO = 'C' \n" +
                    "ORDER BY cab.IDFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
                cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
                
            } else {
                sql = "SELECT cab.IDFACTURA, cab.IDCLINICA, cab.NROFACTURA, cab.TIPOFACTURA, cab.IDCLIENTE, DATE_FORMAT(cab.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, cab.ESTADO, cab.TOTAL_FACTURA \n" +
                    ", cab.TOTAL_DCTO, cab.TOTAL_IVA10, cab.TOTAL_IVA5, cab.TOTAL_GRAV10, cab.TOTAL_GRAV5, cab.TOTAL_EXENTA, cab.TOTAL_IVA, cab.IDFORMACOBRO, cab.LETRAS, cab.OBSERVACION, cab.USU_ALTA, cab.FEC_ALTA \n" +
                    "FROM ope_factura cab \n" +
//                    "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                    "WHERE cab.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n" +
                    "AND cab.ESTADO = 'C' \n" +
                    "ORDER BY cab.IDFACTURA DESC \n" //+
//                    ""+sqlFiltroCbx+" \n"
                        ;
                // MISMO SELECT PERO CON EL OBJETIVO DE RECUPERAR LA CANTIDAD DE FILAS QUE VA A RECUPERAR EL SELECT PRINCIPAL 
                String SELECT_COUNT = "SELECT COUNT(cab.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                    "FROM ope_factura cab \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
//                    "WHERE cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                    "WHERE cab.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n" +
                    "AND cab.ESTADO = 'C' \n" +
                    "ORDER BY cab.IDFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
                cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            }
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- --cargar_grilla_paginacion_rtp_fact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_IDCLINICA(MF_RESULTADO.getInt("IDCLINICA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
//                        datos.setOF_IDARQUEO(MF_RESULTADO.getString("IDARQUEO"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                        datos.setOF_TOTAL_DCTO(MF_RESULTADO.getString("TOTAL_DCTO"));
                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                        datos.setOF_IDFORMACOBRO(MF_RESULTADO.getString("IDFORMACOBRO"));
                        datos.setOF_LETRAS(MF_RESULTADO.getString("LETRAS"));
                        datos.setOF_OBSERVACION(MF_RESULTADO.getString("OBSERVACION"));
                        datos.setOF_USU_ALTA(MF_RESULTADO.getString("USU_ALTA"));
                        datos.setOF_FEC_ALTA(MF_RESULTADO.getString("FEC_ALTA"));
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
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_FACT_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        // PDF 
        sesionDatos.setAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_PAG", "1");
        sesionDatos.setAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_REG", ""+PARAM_NRO_REG_MOSTRAR+"");
        sesionDatos.setAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_PAG", "1");
        sesionDatos.setAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_REG", ""+PARAM_NRO_REG_MOSTRAR+"");
        
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
        List<BeanFacturaCab> listaDet = new ArrayList<>();
        
        String OBSERVACION; // VARIABLES QUE UTILIZO PARA PODER CONTROLAR SI EL RESULTADO DE LA COLUMNA ESTA NULL ASI PARA CARGAR OTRO VALOR A LA CLASE PARA QUE NO SALTE ERROR, Y ESE NUEVO VALOR QUE LE CARGO CUANDO ES NULL VA DENTRO DE ESTA VARIABLE QUE LUEGO PASO A LA CLASE 
        
        try {
            String sql = "SELECT fact.IDFACTURA, fact.IDCLINICA AS IDCLINICA_FACT, per.IDCLINICA AS IDCLINICA_PER, cli.DESC_CLINICA, fact.NROFACTURA, fact.TIPOFACTURA, \n" +
                "fact.IDCLIENTE, -- per.CINRO, per.NOMBRE, per.APELLIDO, per.DIRECCION, per.TELFMOVIL, fact.IDARQUEO, \n" +
                "DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
                "fact.TOTAL_DCTO, fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, \n" +
                "fact.TOTAL_EXENTA, fact.TOTAL_IVA, fact.IDFORMACOBRO, fact.LETRAS, fact.OBSERVACION \n" +
                ", det.ITEM, det.IDCONCEPTO, det.IDTIPOCONCEPTO, \n" +
                "(CASE WHEN(det.IDTIPOCONCEPTO) = 1 THEN (SELECT COMENTARIO FROM cc_cuenta_cliente WHERE IDCUENTACLIENTE = det.IDCONCEPTO) \n" +
                "      WHEN(det.IDTIPOCONCEPTO) = 2 THEN (SELECT prod.DESCRIPCION FROM ope_stock stock JOIN ope_producto prod ON stock.IDPRODUCTO = prod.IDPRODUCTO WHERE stock.IDSTOCK = det.IDCONCEPTO) \n" +
                "      WHEN(det.IDTIPOCONCEPTO) = 3 THEN (det.DESCRIPCION_AUX) \n" +
                "      ELSE det.DESCRIPCION_AUX END) AS CONCEPTO, \n" +
                "det.CANTIDAD, det.PRECIO, det.IDIMPUESTO, \n" +
                "det.EXENTO, det.IVA5, det.IVA10, det.GRAV5, det.GRAV10, det.SUBTOTAL \n" +
                "FROM ope_factura fact \n" +
                "JOIN rh_persona per ON per.IDPERSONA = fact.IDCLIENTE \n" +
                "JOIN ope_factura_det det ON fact.IDFACTURA = det.IDFACTURA \n" +
                "JOIN ope_clinica cli ON fact.IDCLINICA = cli.IDCLINICA \n" + 
                "WHERE fact.IDFACTURA = '"+idTraer+"' \n";
            
            /*
            * OBSERVACION: (DE EMPEÑO ES ESTE SELECT) 
                CAMBIE EL SELECT PORQUE EL IDPRODUCTO GUARDABA ANTES EN EL IDCONCEPTO 
                PERO AGREGUE "STOCK" Y GUARDO EL IDSTOCK EN IDCONCEPTO Y DESDE STOCK RECUPERO EL PRODUCTO 
            */
//            String sql = "SELECT fact.IDFACTURA, fact.IDSUCURSAL, fact.NROFACTURA, fact.TIPOFACTURA, \n" +
//                "fact.IDCLIENTE, -- per.CINRO, per.NOMBRE, per.APELLIDO, per.DIRECCION, per.TELFMOVIL, fact.IDARQUEO, \n" +
//                "DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
//                "fact.TOTAL_DCTO, fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, \n" +
//                "fact.TOTAL_EXENTA, fact.TOTAL_IVA, fact.IDFORMACOBRO, fact.LETRAS, fact.OBSERVACION \n" +
//                ", det.ITEM, det.IDCONCEPTO, det.IDTIPOCONCEPTO, \n" +
//                "(CASE WHEN(det.IDTIPOCONCEPTO) = 1 THEN (SELECT COMENTARIO FROM cc_cuenta_cliente WHERE IDCUENTACLIENTE = det.IDCONCEPTO) \n" +
//                "      WHEN(det.IDTIPOCONCEPTO) = 2 THEN (SELECT DESCRIPCION FROM ope_producto WHERE IDPRODUCTO = det.IDCONCEPTO) \n" +
//                "      WHEN(det.IDTIPOCONCEPTO) = 3 THEN (det.DESCRIPCION_AUX) \n" +
//                "      ELSE det.DESCRIPCION_AUX END) AS CONCEPTO, \n" +
//                "det.CANTIDAD, det.PRECIO, det.IDIMPUESTO, \n" +
//                "det.EXENTO, det.IVA5, det.IVA10, det.GRAV5, det.GRAV10, det.SUBTOTAL \n" +
//                "FROM ope_factura fact \n" +
//                "-- JOIN rh_persona per ON fact.IDCLIENTE = per.IDPERSONA \n" +
//                "JOIN ope_factura_det det ON fact.IDFACTURA = det.IDFACTURA \n" +
//                "WHERE fact.IDFACTURA = '"+idTraer+"' ";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---traer_datos()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                BeanFacturaCab datos = new BeanFacturaCab();
                    // CABECERA ------------------ ------------------ 
                    datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                    datos.setOF_IDCLINICA(MF_RESULTADO.getInt("IDCLINICA_FACT")); // IDCLINICA DE LA FACTURA 
                    datos.setOF_IDCLINICA_AUX(MF_RESULTADO.getString("IDCLINICA_PER")); // IDCLINICA DE LA PERSONA 
                    datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                    datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                    datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                    datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
//                    datos.setOF_IDARQUEO(MF_RESULTADO.getString("IDARQUEO"));
                    datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
                    datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                    datos.setOF_TOTAL_DCTO(MF_RESULTADO.getString("TOTAL_DCTO"));
                    datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                    datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                    datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                    datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                    datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                    datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                    datos.setOF_IDFORMACOBRO(MF_RESULTADO.getString("IDFORMACOBRO"));
                    datos.setOF_LETRAS(MF_RESULTADO.getString("LETRAS"));
                    if (MF_RESULTADO.getString("OBSERVACION") != null) {
                        OBSERVACION = MF_RESULTADO.getString("OBSERVACION");
                    } else {
                        OBSERVACION = "";
                    }
                    datos.setOF_OBSERVACION(OBSERVACION);
//                    datos.setOF_USU_ALTA(MF_RESULTADO.getString("USU_ALTA"));
//                    datos.setOF_FEC_ALTA(MF_RESULTADO.getString("FEC_ALTA"));
                    // DETALLE ------------------ ------------------ 
                    datos.setOFD_ITEM(MF_RESULTADO.getInt("ITEM"));
                    datos.setOFD_IDCONCEPTO(MF_RESULTADO.getInt("IDCONCEPTO"));
                    datos.setOFD_IDTIPOCONCEPTO(MF_RESULTADO.getInt("IDTIPOCONCEPTO"));
                    datos.setOFD_DESCRIPCION_AUX(MF_RESULTADO.getString("CONCEPTO"));
                    datos.setOFD_CANTIDAD(MF_RESULTADO.getInt("CANTIDAD"));
                    datos.setOFD_PRECIO(MF_RESULTADO.getString("PRECIO"));
                    datos.setOFD_IDIMPUESTO(MF_RESULTADO.getString("IDIMPUESTO"));
                    datos.setOFD_EXENTO(MF_RESULTADO.getString("EXENTO"));
                    datos.setOFD_IVA5(MF_RESULTADO.getString("IVA5"));
                    datos.setOFD_IVA10(MF_RESULTADO.getString("IVA10"));
                    datos.setOFD_GRAV5(MF_RESULTADO.getString("GRAV5"));
                    datos.setOFD_GRAV10(MF_RESULTADO.getString("GRAV10"));
                    datos.setOFD_SUBTOTAL(MF_RESULTADO.getString("SUBTOTAL"));
                listaDet.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaDet;
    }
    
    
    
    // METODO QUE UTILIZO PARA TRAER LOS DATOS DE UNA FACTURA - TIPO LOS DATOS DE LA CABECERA, POR ESO LO GUARDO EN UN BEAN 
    public BeanFacturaCab traerDatosBeanFact(String PARAM_IDFACTURA) {
        BeanFacturaCab datos = new BeanFacturaCab();
        String OBSERVACION; // VARIABLES QUE UTILIZO PARA PODER CONTROLAR SI EL RESULTADO DE LA COLUMNA ESTA NULL ASI PARA CARGAR OTRO VALOR A LA CLASE PARA QUE NO SALTE ERROR, Y ESE NUEVO VALOR QUE LE CARGO CUANDO ES NULL VA DENTRO DE ESTA VARIABLE QUE LUEGO PASO A LA CLASE 
        try {
            String sql = "SELECT fact.IDFACTURA, fact.IDCLINICA, fact.NROFACTURA, fact.TIPOFACTURA, \n" +
                "fact.IDCLIENTE, -- per.CINRO, per.NOMBRE, per.APELLIDO, per.DIRECCION, per.TELFMOVIL, fact.IDARQUEO, \n" +
                "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
                "-- fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, \n" +
                "fact.TOTAL_IVA, fact.LETRAS, fact.OBSERVACION \n" +
                "FROM ope_factura fact \n" +
                "JOIN ope_factura_det det ON fact.IDFACTURA = det.IDFACTURA \n" +
                "WHERE fact.IDFACTURA = '"+PARAM_IDFACTURA+"' \n";
            
//            String sql = "SELECT fact.IDFACTURA, fact.IDCLINICA, fact.NROFACTURA, fact.TIPOFACTURA, \n" +
//                "fact.IDCLIENTE, -- per.CINRO, per.NOMBRE, per.APELLIDO, per.DIRECCION, per.TELFMOVIL, fact.IDARQUEO, \n" +
//                "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
//                "fact.TOTAL_DCTO, fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, \n" +
//                "fact.TOTAL_EXENTA, fact.TOTAL_IVA, fact.IDFORMACOBRO, fact.LETRAS, fact.OBSERVACION \n" +
//                ", det.ITEM, det.IDCONCEPTO, det.IDTIPOCONCEPTO, \n" +
//                "(CASE WHEN(det.IDTIPOCONCEPTO) = 1 THEN (SELECT COMENTARIO FROM cc_cuenta_cliente WHERE IDCUENTACLIENTE = det.IDCONCEPTO) \n" +
//                "      WHEN(det.IDTIPOCONCEPTO) = 2 THEN (SELECT prod.DESCRIPCION FROM ope_stock stock JOIN ope_producto prod ON stock.IDPRODUCTO = prod.IDPRODUCTO WHERE stock.IDSTOCK = det.IDCONCEPTO) \n" +
//                "      WHEN(det.IDTIPOCONCEPTO) = 3 THEN (det.DESCRIPCION_AUX) \n" +
//                "      ELSE det.DESCRIPCION_AUX END) AS CONCEPTO, \n" +
//                "det.CANTIDAD, det.PRECIO, det.IDIMPUESTO, \n" +
//                "det.EXENTO, det.IVA5, det.IVA10, det.GRAV5, det.GRAV10, det.SUBTOTAL \n" +
//                "FROM ope_factura fact \n" +
//                "JOIN ope_factura_det det ON fact.IDFACTURA = det.IDFACTURA \n" +
//                "WHERE fact.IDFACTURA = '"+PARAM_IDFACTURA+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---traerDatosBeanFact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                // CABECERA ------------------ ------------------ 
                datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                datos.setOF_IDCLINICA(MF_RESULTADO.getInt("IDCLINICA"));
                datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
////                 datos.setOF_IDARQUEO(MF_RESULTADO.getString("IDARQUEO"));
                datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
                datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
//                datos.setOF_TOTAL_DCTO(MF_RESULTADO.getString("TOTAL_DCTO"));
//                datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
//                datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
//                datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
//                datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
//                datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
//                datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
//                datos.setOF_IDFORMACOBRO(MF_RESULTADO.getString("IDFORMACOBRO"));
//                datos.setOF_LETRAS(MF_RESULTADO.getString("LETRAS"));
//                if (MF_RESULTADO.getString("OBSERVACION") != null) {
//                    OBSERVACION = MF_RESULTADO.getString("OBSERVACION");
//                } else {
//                    OBSERVACION = "";
//                }
//                datos.setOF_OBSERVACION(OBSERVACION);
////                    datos.setOF_USU_ALTA(MF_RESULTADO.getString("USU_ALTA"));
////                    datos.setOF_FEC_ALTA(MF_RESULTADO.getString("FEC_ALTA"));
                // DETALLE ------------------ ------------------ 
//                datos.setOFD_ITEM(MF_RESULTADO.getInt("ITEM"));
//                datos.setOFD_IDCONCEPTO(MF_RESULTADO.getInt("IDCONCEPTO"));
//                datos.setOFD_IDTIPOCONCEPTO(MF_RESULTADO.getInt("IDTIPOCONCEPTO"));
//                datos.setOFD_DESCRIPCION_AUX(MF_RESULTADO.getString("CONCEPTO"));
//                datos.setOFD_CANTIDAD(MF_RESULTADO.getInt("CANTIDAD"));
//                datos.setOFD_PRECIO(MF_RESULTADO.getString("PRECIO"));
//                datos.setOFD_IDIMPUESTO(MF_RESULTADO.getString("IDIMPUESTO"));
//                datos.setOFD_EXENTO(MF_RESULTADO.getString("EXENTO"));
//                datos.setOFD_IVA5(MF_RESULTADO.getString("IVA5"));
//                datos.setOFD_IVA10(MF_RESULTADO.getString("IVA10"));
//                datos.setOFD_GRAV5(MF_RESULTADO.getString("GRAV5"));
//                datos.setOFD_GRAV10(MF_RESULTADO.getString("GRAV10"));
//                datos.setOFD_SUBTOTAL(MF_RESULTADO.getString("SUBTOTAL"));
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return datos;
    }
    
    
    
    
    @Override
    public String guardar(Object obj) {
        String tipoRespuesta; // 1 : EXITO / 2 : ERROR 
        
        BeanFacturaCab datos = (BeanFacturaCab) obj;
        
        try {
            // OBTENGO LAS VARIABLES DEL BEAN 
            String IDFACTURA = String.valueOf(datos.getOF_IDFACTURA());
            String IDCLINICA = String.valueOf(datos.getOF_IDCLINICA());
            String NROFACTURA = datos.getOF_NROFACTURA();
            String TIPOFACTURA = datos.getOF_TIPOFACTURA();
            String IDCLIENTE = String.valueOf(datos.getOF_IDCLIENTE());
            String FECHAFACTURA = datos.getOF_FECHAFACTURA();
            String IDARQUEO = datos.getOF_IDARQUEO();
            String ESTADO = datos.getOF_ESTADO();
            String TOTAL_FACTURA = datos.getOF_TOTAL_FACTURA();
            String TOTAL_DCTO = datos.getOF_TOTAL_DCTO();
            String TOTAL_IVA10 = datos.getOF_TOTAL_IVA10();
            String TOTAL_IVA5 = datos.getOF_TOTAL_IVA5();
            String TOTAL_GRAV10 = datos.getOF_TOTAL_GRAV10();
            String TOTAL_GRAV5 = datos.getOF_TOTAL_GRAV5();
            String TOTAL_EXENTA = datos.getOF_TOTAL_EXENTA();
            String TOTAL_IVA = datos.getOF_TOTAL_IVA();
            String IDFORMACOBRO = datos.getOF_IDFORMACOBRO();
            String LETRAS = datos.getOF_LETRAS();
            String OBSERVACION = datos.getOF_OBSERVACION();
            String USU_ALTA = datos.getOF_USU_ALTA();
            String FEC_ALTA = datos.getOF_FEC_ALTA();
            
            String sql = "INSERT INTO ope_factura(IDFACTURA, \n" +
                "IDCLINICA, NROFACTURA, TIPOFACTURA, IDCLIENTE, FECHAFACTURA, IDARQUEO, \n" +
                "ESTADO, TOTAL_FACTURA, TOTAL_DCTO, TOTAL_IVA10, TOTAL_IVA5, TOTAL_GRAV10, TOTAL_GRAV5, \n" +
                "TOTAL_EXENTA, TOTAL_IVA, IDFORMACOBRO, LETRAS, OBSERVACION, USU_ALTA, FEC_ALTA) \n" +
                "VALUES("+IDFACTURA+", \n" +
                "'"+IDCLINICA+"', '"+NROFACTURA+"', '"+TIPOFACTURA+"', '"+IDCLIENTE+"', '"+FECHAFACTURA+"', '"+IDARQUEO+"', \n" +
                "'"+ESTADO+"', '"+TOTAL_FACTURA+"', '"+TOTAL_DCTO+"', '"+TOTAL_IVA10+"', '"+TOTAL_IVA5+"', '"+TOTAL_GRAV10+"', '"+TOTAL_GRAV5+"', \n" +
                "'"+TOTAL_EXENTA+"', '"+TOTAL_IVA+"', '"+IDFORMACOBRO+"', '"+LETRAS+"', '"+OBSERVACION+"', '"+USU_ALTA+"', '"+FEC_ALTA+"') ";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---guardar()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            int cantResul = MF_SENTENCIA.executeUpdate(sql); // LA EJECUCION DE LA OPERACION DEVUELVE UN VALOR NUMERICO Y CAPTURO ESE VALOR EN UNA VARIABLE PARA DETERMINAR SI SE REALIZO CORRECTAMENTE O LANZO UN ERROR 
            if (cantResul == 1) {
                tipoRespuesta = "1";
            } else {
                tipoRespuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            tipoRespuesta = "2";
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    public void guardarDetalle(List<BeanFacturaCab> paramListaDet, String PARAM_IDFACTURA, String PARAM_IDCLIENTE) {
        String IDFACTURA = PARAM_IDFACTURA;
        
        for (BeanFacturaCab detalle : paramListaDet) {
            try {
                int ITEM = detalle.getOFD_ITEM();
                int IDCONCEPTO = detalle.getOFD_IDCONCEPTO();
                String IDTIPOCONCEPTO = String.valueOf(detalle.getOFD_IDTIPOCONCEPTO());
                String CANTIDAD = String.valueOf(detalle.getOFD_CANTIDAD());
                String PRECIO = detalle.getOFD_PRECIO().replace(".", "").replace(",",".");
                String IDIMPUESTO = detalle.getOFD_IDIMPUESTO();
                if (IDIMPUESTO.isEmpty()) { IDIMPUESTO = "0"; }
                String EXENTO = detalle.getOFD_EXENTO();
                String IVA5 = detalle.getOFD_IVA5();
                if (IVA5 == null) {
                    IVA5 = "0";
                } else {
                    IVA5 = detalle.getOFD_IVA5().replace(".", "").replace(",",".");
                }
                String IVA10 = detalle.getOFD_IVA10();
                if (IVA10 == null) {
                    IVA10 = "0";
                } else {
                    IVA10 = detalle.getOFD_IVA10().replace(".", "").replace(",",".");
                }
                String GRAV5 = detalle.getOFD_GRAV5();
                if (GRAV5 == null) {
                    GRAV5 = "0";
                } else {
                    GRAV5 = detalle.getOFD_GRAV5().replace(".", "").replace(",",".");
                }
                String GRAV10 = detalle.getOFD_GRAV10();
                if (GRAV10 == null) {
                    GRAV10 = "0";
                } else {
                    GRAV10 = detalle.getOFD_GRAV10().replace(".", "").replace(",",".");
                }
                String SUBTOTAL = detalle.getOFD_SUBTOTAL().replace(".", "").replace(",",".");
                String DESCRIPCION_AUX = detalle.getOFD_DESCRIPCION_AUX();
                
                String sql = "INSERT INTO ope_factura_det (IDFACTURA, ITEM, \n" +
                    "IDCONCEPTO, IDTIPOCONCEPTO, CANTIDAD, PRECIO, IDIMPUESTO, \n" +
                    "EXENTO, IVA5, IVA10, GRAV5, GRAV10, SUBTOTAL, DESCRIPCION_AUX) \n" +
                    "VALUES("+IDFACTURA+", "+ITEM+", \n" +
                    "'"+IDCONCEPTO+"', '"+IDTIPOCONCEPTO+"', '"+CANTIDAD+"', '"+PRECIO+"', '"+IDIMPUESTO+"', \n" +
                    "'"+EXENTO+"', '"+IVA5+"', '"+IVA10+"', '"+GRAV5+"', '"+GRAV10+"', '"+SUBTOTAL+"', '"+DESCRIPCION_AUX+"') ";
                System.out.println("------------------------MODEL_FACTURA--------------------------");
                System.out.println("-- ---guardarDetalle()--------     "+sql);
                System.out.println("---------------------------------------------------------------");
                MF_CONEXION = devolverConex();
                MF_SENTENCIA = MF_CONEXION.createStatement();
                String tipoRespuesta = "";
                int cantResul = MF_SENTENCIA.executeUpdate(sql); // LA EJECUCION DE LA OPERACION DEVUELVE UN VALOR NUMERICO Y CAPTURO ESE VALOR EN UNA VARIABLE PARA DETERMINAR SI SE REALIZO CORRECTAMENTE O LANZO UN ERROR 
                if (cantResul == 1) {
                    tipoRespuesta = "1";
                } else {
                    tipoRespuesta = "2";
                }
                System.out.println("_   _DET_FACT___SENTENCIA__: :"+tipoRespuesta);
                cerrarConexiones();
                if (cantResul == 1) {
                    if (IDTIPOCONCEPTO.equals("1")) { // ID_TIPO_CONCEPTO = 1 (CUENTA CLIENTE)
                        ctrlCtaClieSaldo(IDFACTURA, IDCONCEPTO, PARAM_IDCLIENTE, SUBTOTAL);
                    }
                }
            } catch (SQLException e) {
                Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
            }
        } // END FOR 
    } // END METHOD 
    
    
    public void ctrlCtaClieSaldo(String PARAM_IDFACTURA, int PARAM_IDCUENTACLIENTE, String PARAM_IDCLIENTE, String PARAM_SUBTOTAL) {
        System.out.println("---------  ctrlCtaClieSaldo  -----------");
        System.out.println("_   _ID_FACT:  :"+PARAM_IDFACTURA);
        System.out.println("_   _ID__CTA:  :"+PARAM_IDCUENTACLIENTE);
        System.out.println("_   _ID_CLIE:  :"+PARAM_IDCLIENTE);
        System.out.println("_   _SUBTOTA:  :"+PARAM_SUBTOTAL);
        System.out.println("--------- ------------------ -----------");
        
        try {
            String sql = "SELECT IDCUENTACLIENTE, IDPERSONA, IDAGENDAMIENTO, ITEM_AGEN, NROCUOTA, MONTO, SALDO, ESTADO \n" +
                "FROM cc_cuenta_cliente \n" +
                "WHERE IDCUENTACLIENTE = '"+PARAM_IDCUENTACLIENTE+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---ctrlCtaClieSaldo()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while (MF_RESULTADO.next()) {
                String IDAGENDAMIENTO = MF_RESULTADO.getString("IDAGENDAMIENTO");
                String ITEM_AGEN = MF_RESULTADO.getString("ITEM_AGEN");
                String SALDO = sin_formato.format(MF_RESULTADO.getDouble("SALDO"));
                System.out.println("_   __SALDO_CTA:   :"+SALDO);
                int resul = Integer.parseInt(SALDO) - Integer.parseInt(PARAM_SUBTOTAL);
                System.out.println(".");
                System.out.println(".");
                System.out.println("_   __RESULTADO:   :"+resul);
                System.out.println(".");
                System.out.println(".");
                if (resul == 0) {
                    updateSaldo(PARAM_IDCUENTACLIENTE, IDAGENDAMIENTO, ITEM_AGEN);
                }
                habilitarConsulta(PARAM_IDFACTURA, IDAGENDAMIENTO, ITEM_AGEN);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // END METHOD 

    
    public void updateSaldo(int PARAM_IDCUENTACLIENTE, String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN) {
        try {
            System.out.println("------__UPDATE_CUENTA_CLIENTE()__---------");
            System.out.println("_   _ID_CTA_CLIE:  :"+PARAM_IDCUENTACLIENTE);
            System.out.println("_   _ID_AGENDAMI:  :"+PARAM_IDAGENDAMIENTO);
            System.out.println("_   _ID_ITEM_AGE:  :"+PARAM_ITEM_AGEN);
            System.out.println("------------------------------------------");
            String sql = "UPDATE cc_cuenta_cliente \n" +
                "SET SALDO = '0', ESTADO = 'C' \n" +
                "WHERE IDCUENTACLIENTE = '"+PARAM_IDCUENTACLIENTE+"' ";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---updateSaldo()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            MF_SENTENCIA.executeUpdate(sql);
//            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // END METHOD 

    
    public void habilitarConsulta(String PARAM_IDFACTURA, String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN) {
        try {
            String sql = "UPDATE ope_agendamiento_det \n" +
                "SET ESTADO = 'A', "
                    + "IDFACTURA = '"+PARAM_IDFACTURA+"', "
                    + "AGENDADO = 'SI' \n" +
                "WHERE IDAGENDAMIENTO = '"+PARAM_IDAGENDAMIENTO+"' \n" +
                "AND ITEM = '"+PARAM_ITEM_AGEN+"' ";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---habilitarConsulta()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            MF_SENTENCIA.executeUpdate(sql);
//            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // END METHOD 

    
    
    
    
    @Override
    public String eliminar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    // METODO PARA TRAER UN NUEVO IDFACTURA PARA PODER HACER UN INSERT Y GUARDAR LOS DATOS 
    public String traerNewIdFactura() {
        String valor = "";
        try {
            String sql = "SELECT COALESCE(MAX(IDFACTURA),0)+1 AS ID FROM ope_factura ";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---traerNewIdFactura()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                valor = MF_RESULTADO.getString("ID");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
    // METODO PARA CONTROLAR QUE NO SE DEJEN CAMPOS VACIOS 
    public boolean controlCamposVacios(String PARAM_NRO_FACTURA, String PARAM_FECHA_FACTURA, String PARAM_TIPO_FACTURA, String PARAM_CLIENTE_ID) {
        boolean valor = false;
        if (PARAM_NRO_FACTURA.isEmpty() || PARAM_NRO_FACTURA == null) {
            valor = true;
        } else if (PARAM_FECHA_FACTURA.isEmpty() || PARAM_FECHA_FACTURA == null) {
            valor = true;
        } else if (PARAM_TIPO_FACTURA.isEmpty() || PARAM_TIPO_FACTURA == null) {
            valor = true;
        } else if (PARAM_CLIENTE_ID.isEmpty() || PARAM_CLIENTE_ID == null) {
            valor = true;
        }
        return valor;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA PODER VALIDAR LOS CAMPOS QUE SE PASAN COMO PARAMETRO PARA EVITAR QUE ESTOS TENGAN VALORES NEGATIVOS AL MOMENTO DE COBRAR LA FACTURA 
    public boolean ctrlValoresNegativos(String TOTAL_IVA5, String TOTAL_IVA10, String TOTAL_IVA, String TOTAL_FACT) {
        boolean valor = false;
        
        System.out.println("--------------CONTROL_DE_VALORES_NEGATIVOS------------------");
        System.out.println("___TOTAL_IVA_5:  "+TOTAL_IVA5);
        System.out.println("__TOTAL_IVA_10:  "+TOTAL_IVA10);
        System.out.println("_____TOTAL_IVA:  "+TOTAL_IVA);
        System.out.println("____TOTAL_FACT:  "+TOTAL_FACT);
        System.out.println("-  -  -  -  -  -  -  -  -");
        System.out.println("___TOTAL_IVA_5:  "+Double.parseDouble(TOTAL_IVA5.replace(".","").replace(",", "")));
        System.out.println("__TOTAL_IVA_10:  "+Double.parseDouble(TOTAL_IVA10.replace(".","").replace(",", "")));
        System.out.println("_____TOTAL_IVA:  "+Double.parseDouble(TOTAL_IVA.replace(".","").replace(",", "")));
        System.out.println("____TOTAL_FACT:  "+Double.parseDouble(TOTAL_FACT.replace(".","").replace(",", "")));
        System.out.println("------------------------------------------------------------");
        
        int band_activar = 0; // variable que utilizo como bandera para poder saber si es que alguno de los parametros tiene un valor negativo 
        if (Double.parseDouble(TOTAL_IVA5.replace(".","").replace(",", "")) < 0) {
            band_activar = 1;
        } else if(Double.parseDouble(TOTAL_IVA10.replace(".","").replace(",", "")) < 0) {
            band_activar = 1;
        } else if(Double.parseDouble(TOTAL_IVA.replace(".","").replace(",", "")) < 0) {
            band_activar = 1;
        } else if(Double.parseDouble(TOTAL_FACT.replace(".","").replace(",", "")) < 0) {
            band_activar = 1;
        }
        // verifico la bandera para saber si alguno de los parametros tiene valor negativo 
        if (band_activar > 0) {
            valor = true; // le retorno true porque la bandera esta activa 
        }
        return valor;
    } // end method 
    
    
    
    // METODO PARA CONTROLAR QUE NO SE REPITA EL NRO DE FACTURA 
    public boolean ctrlNroFactRepetir(HttpSession PARAM_SESION, String PARAM_NRO_FACTURA) {
        boolean valor = false;
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT IDFACTURA, IDCLINICA, NROFACTURA \n"
                + "FROM ope_factura \n"
                + "WHERE NROFACTURA = '"+PARAM_NRO_FACTURA+"' \n"
                + "AND IDCLINICA = '"+VAR_ID_CLINICA+"' \n"
                + "AND ESTADO NOT IN ('X') \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---ctrlNroFactRepetir()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                valor = true;
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
//    // METODO PARA FILTRAR LA TABLA DE FACTURA Y MOSTRAR LUEGO EN LA VENTANA PRINCIPAL EL RESULTADO DEL FILTRO 
//    public List filtrar(String PARAM_CBX_MOSTRAR, String PARAM_TXT_FIL_NRO_FACT) {
//        List<BeanFacturaCab> lista = new ArrayList<>();
//        
//        String sqlFiltroCbx;
//        // CONTROLO SI EL COMBO TIENE SELECCIONADO LA OPCION DE "TODOS", SI FUESE ASI ENTONCES NO AGREGARIA EL LIMIT AL SELECT 
//        if (PARAM_CBX_MOSTRAR.equalsIgnoreCase("TODOS")) {
//            sqlFiltroCbx = "";
//        } else {
//            sqlFiltroCbx = "LIMIT "+PARAM_CBX_MOSTRAR+"";
//        }
//        
//        String sqlFiltroNroFact;
//        // CONTROLO SI ES QUE SE CARGO ALGUN NRO DE FACTURA EN EL TXT 
//        if (PARAM_TXT_FIL_NRO_FACT.equals("") || PARAM_TXT_FIL_NRO_FACT.isEmpty()) {
//            sqlFiltroNroFact = "";
//        } else {
//            sqlFiltroNroFact = "WHERE (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) ";
//        }
//        
//        try {
//            String sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
//                "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
//                "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
//                "FROM ope_factura fact \n" +
//                "JOIN rh_persona pers ON pers.IDPERSONA = fact.IDCLIENTE \n" +
//                ""+sqlFiltroNroFact+" \n" + 
//                "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n" + 
//                ""+sqlFiltroCbx+" \n";
//            System.out.println("------------------------MODEL_FACTURA--------------------------");
//            System.out.println("-- ---filtrar()--------     "+sql);
//            System.out.println("---------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MF_RESULTADO = consultaBD(sql);
//            
//            while(MF_RESULTADO.next()) {
//                BeanFacturaCab datos = new BeanFacturaCab();
//                    datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
//                    datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
//                    datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
//                    datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
//                    datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
//                    datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
//                    datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
//                    datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
//                    datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
//                    datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
//                    datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
//                    datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
//                    datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
//                lista.add(datos);
//            }
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return lista;
//    }
    
    
    
    // METODO PARA HACER LA PAGINACION Y FILTRAR POR LA TABLA DE FACTURA 
    public List filtrar(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FIL_NRO_FACT) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("___     ___________filtrar_paginacion_agend()___________     ___");
        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        System.out.println(".-");
        System.out.println(".-");
        System.out.println(".-");
        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
            System.out.println("_IF_CAMBIO_DE_VALOR_____");
            PARAM_CBX_MOSTRAR = "1";
        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
            PARAM_CBX_MOSTRAR = "2";
        }
        System.out.println(".-");
        System.out.println(".-");
        System.out.println(".-");
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = "1"; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (sesionDatos.getAttribute("PAG_FACT_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_FACT_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_FACT_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_FACT_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_FACT_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        String sqlFiltroNroFact;
        // CONTROLO SI ES QUE SE CARGO ALGUN NRO DE FACTURA EN EL TXT 
        if (PARAM_TXT_FIL_NRO_FACT.equals("") || PARAM_TXT_FIL_NRO_FACT.isEmpty()) {
            sqlFiltroNroFact = "";
        } else {
            sqlFiltroNroFact = "AND (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) ";
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
                "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
                "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
                "FROM ope_factura fact \n" +
                "JOIN rh_persona pers ON pers.IDPERSONA = fact.IDCLIENTE \n" +
                "WHERE fact.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                "AND fact.ESTADO = 'C' \n" + 
                ""+sqlFiltroNroFact+" \n" + 
                "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(fact.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_factura fact \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN rh_persona pers ON pers.IDPERSONA = fact.IDCLIENTE \n" +
                "WHERE fact.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                "AND fact.ESTADO = 'C' \n" + 
                ""+sqlFiltroNroFact+" \n" + 
                "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- --filtrar_paginacion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
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
            Logger.getLogger(ModelCuentaCliente.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_FACT_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    /*
        METODO PARA FILTRAR CON PAGINACION PARA LA PAGINA DE REPORTE FACTURA 
    */
    public List filtrar_paginacion_rpt_fact(HttpSession sesionDatos, 
            String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FIL_NRO_FACT, 
            String PARAM_TXT_FIL_FEC_INI,
            String PARAM_TXT_FIL_FEC_FIN, 
            String PARAM_TXT_FIL_ID_CLIENTE,
            String PARAM_VAR_ESTADO_CAB, // VARIABLE QUE UTILIZO PARA FILTRAR EL SELECT POR EL ESTADO DE LA FACTURA DE LA CABECERA, LO AGREGUE MAS POR LA PAGINA DE ANULAR FACTURA DONDE SOLO QUIERO VER LAS FACTURAS ACTIVAS Y NO LAS ANULADAS 
            String PARAM_ID_CLINICA // SI SE FILTRA POR LA CLINICA ENTONCES ES VABARIABLE ESTARA CARGADA Y FILTRARIA POR ELLA, EN CASO CONTRARIO FILTRARIA POR LA CLINICA DE LOGEO DEL USUARIO 
            , String PARAM_IDPACIENTE // PARAMETRO QUE UTILIZO PARA CARGAR EL IDPACIENTE CUANDO EL PERFIL SEA PACIENTE, YA QUE ESTE METODO UTILIZO EN VARIOS LUGARES, Y LO DIVIDO CON ESTA VARIABLE, SI ESTA CARGADO ENTONCES EL PERFIL ES DE PACIENTE, Y SI ESTA VACIO, ENTONCES EL PERFIL PUEDE SER ADMIN, SECRETARIO O MEDICO 
    ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println("___     ___________filtrar_paginacion_rpt_fact()___________     ___");
        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_RPT_FACT_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_FACT_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_RPT_FACT_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_RPT_FACT_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        /*
            DE AQUI EMPIEZA LOS FILTROS PARA EL WHERE DEL SELECT --
        */
        String VAR_WHERE="WHERE fact.ESTADO = 'C' \n";
        
        // OBSERVACION_PACIENTE: CONTROLO PRIMERAMENTE SI EL PARAMETRO DE IDPACIENTE ESTA CARGADO, SI ESO PASA ES PORQUE EL IDPERFIL ES PACIENTE, Y EL PARAMETRO DE IDPACIENTE ES EL IDPERSONA LOGEADA, ENTONCES EL SELECT DEBERIA DE FILTRAR POR LAS FACTURAS DE ESE PACIENTE NOMAS Y NO POR LAS DE OTRO 
        if (PARAM_IDPACIENTE == null || PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//            VAR_WHERE = "";
        } else { // SI ESTUVIERA CARGADO ENTONCES ASUMO QUE EL PERFIL ES DE PACIENTE Y APLICARIA LA OBSERVACION_PACIENTE  
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//            if (VAR_WHERE.equals("")) {
//                VAR_WHERE = "WHERE fact.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n";
//            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n";
//            }
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        // OBSERVACION_SOBRE_CLINICA_1: SI EL PERFIL ES DE PACIENTE, ENTONCES LE MOSTRARIA TODAS SUS FACTURAS DE CUALQUIER CLINICA Y SI FUERA OTRO PERFIL COMO SECRETARIO, ENTONCES AHI SI LE FILTRARIA POR LA CLINICA FILTRADA O LOGEADA 
        if (PARAM_IDPACIENTE == null || PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
            // OBSERVACION_SOBRE_CLINICA_2: SI SE ENCUENTRA VACIO ENTONCES FILTRARIA POR LA CLINICA DE LOGEO DEL USUARIO PERO SI SE ENCUENTRA CARGADO ENTONCES EL USUARIO DESMARCO EL CHECK Y FILTRO POR UNA CLINICA Y CARGARIA ESO EN EL WHERE... ESTE CAMPO SIEMPRE ESTARA CARGADO YA QUE EL SELECT SIEMPRE ESTARA FILTRANDO POR CLINICA YA SEA LA CLINICA DEL FILTRO O DEL USUARIO LOGEADO 
            if (PARAM_ID_CLINICA == null || PARAM_ID_CLINICA.isEmpty() || PARAM_ID_CLINICA.equals(" ")) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (VAR_WHERE.equals("")) {
//                    VAR_WHERE = "WHERE (rp.IDCLINICA = "+VAR_ID_CLINICA+" || fact.IDCLINICA = "+VAR_ID_CLINICA+") \n";
//                } else {
                    VAR_WHERE = VAR_WHERE + "AND fact.IDCLINICA = "+VAR_ID_CLINICA+" \n";
//                    VAR_WHERE = VAR_WHERE + "AND (rp.IDCLINICA = "+VAR_ID_CLINICA+" || fact.IDCLINICA = "+VAR_ID_CLINICA+") \n";
//                }
            } else {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (VAR_WHERE.equals("")) {
//                    VAR_WHERE = "WHERE (rp.IDCLINICA = "+PARAM_ID_CLINICA+" || fact.IDCLINICA = "+PARAM_ID_CLINICA+") \n";
//                } else {
                    VAR_WHERE = VAR_WHERE + "AND fact.IDCLINICA = "+PARAM_ID_CLINICA+" \n";
//                    VAR_WHERE = VAR_WHERE + "AND (rp.IDCLINICA = "+PARAM_ID_CLINICA+" || fact.IDCLINICA = "+PARAM_ID_CLINICA+") \n";
//                }
            }
        }
        
        // CONTROLO SI ES QUE SE CARGO ALGUN NRO DE FACTURA EN EL TXT 
        if (PARAM_TXT_FIL_NRO_FACT.equals("") || PARAM_TXT_FIL_NRO_FACT.isEmpty()) {
            //
        } else {
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//            if (VAR_WHERE.equals("")) {
//                VAR_WHERE = "WHERE (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') "
//                        + "OR UPPER(fact.IDFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) \n";
//            } else {
                VAR_WHERE = VAR_WHERE + "AND (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') "
                        + "OR UPPER(fact.IDFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) \n";
//            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADAS LAS FECHAS PARA ASI AGREGUEGAR AL WHERE EL FILTRO POR LAS FECHAS / SI UNA SOLA FECHA ESTA CARGADA, ENTONCES VOY A HACER EL FILTRO POR ESA FECHA 
        if (PARAM_TXT_FIL_FEC_INI.equals("") && PARAM_TXT_FIL_FEC_FIN.equals("")) {
            //
        } else {
            // CONTROLO PRIMERAMENTE SI AMBAS FECHAS ESTAN CARGADAS 
            if (!(PARAM_TXT_FIL_FEC_INI.equals("")) && !(PARAM_TXT_FIL_FEC_FIN.equals(""))) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                if (VAR_WHERE.equals("")) {
//                    VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                } else {
                    VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                }
            } else { // SI NO ESTAN CARGADAS ENTONCES CONTROLO PARA VER SI UNO DE ELLAS NO ESTA CARGADA 
                if (!(PARAM_TXT_FIL_FEC_INI.equals(""))) {
//                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (VAR_WHERE.equals("")) {
//                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
//                    } else {
                        VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
//                    }
                }
                if (!(PARAM_TXT_FIL_FEC_FIN.equals(""))) {
//                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//                    if (VAR_WHERE.equals("")) {
//                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                    } else {
                        VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
//                    }
                }
            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADO EL IDCLIENTE PARA ASI AGREGAR AL WHERE EL FILTRO POR IDCLIENTE  
        if (PARAM_TXT_FIL_ID_CLIENTE.equals("") || PARAM_TXT_FIL_ID_CLIENTE.isEmpty()) {
            //
        } else {
//            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//            if (VAR_WHERE.equals("")) {
//                VAR_WHERE = "WHERE fact.IDCLIENTE = '"+PARAM_TXT_FIL_ID_CLIENTE+"' \n";
//            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.IDCLIENTE = '"+PARAM_TXT_FIL_ID_CLIENTE+"' \n";
//            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADO LA VARIABLE DE ESTADO PARA ASI AGREGAR AL WHERE EL FILTRO POR ESTADO DE LA FACTURA DE LA CABECERA   
        if (PARAM_VAR_ESTADO_CAB.equals("") || PARAM_VAR_ESTADO_CAB.isEmpty()) {
            //
        } else {
//            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
//            if (VAR_WHERE.equals("")) {
//                VAR_WHERE = "WHERE fact.ESTADO = '"+PARAM_VAR_ESTADO_CAB+"' \n";
//            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.ESTADO = '"+PARAM_VAR_ESTADO_CAB+"' \n";
//            }
        }
        
        try {
            String sql = "";
            /*
             * OBSERVACION: SI EL IDPACIENTE QUE VIENE POR PARAMETRO, ESTA VACIO, ENTONCES EL IDPERFIL ES DE ADMIN O SECRE O OTRO PERFIL 
                PERO SI VIENE CARGADO, ENTONCES EL PERFIL ES PACIENTE Y LE FILTRO PARA MOSTRAR LAS FACTURAS DE ESE PACIENTE Y NO DE OTROS 
            */
//            if (PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//                sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
//                    "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
//                    "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
//                    "FROM ope_factura fact \n" +
//                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
//    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
//                    ""+VAR_WHERE+" \n" + 
//                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n" + 
//                    ""+sqlFiltroCbx+" \n";
//            } else {
                sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
                    "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
                    "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
                    "FROM ope_factura fact \n" +
                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                    ""+VAR_WHERE+" \n" + 
                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"// + 
//                    ""+sqlFiltroCbx+" \n"
                        ;
                
                String SELECT_COUNT = "SELECT COUNT(fact.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                    "FROM ope_factura fact \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                    ""+VAR_WHERE+" \n" + 
                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
                cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
//            }
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- --filtrar_paginacion_rpt_fact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
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
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_FACT_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        // PDF 
        sesionDatos.setAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_PAG", ""+NRO_PAG_ACTUAL_MOSTRAR+"");
        sesionDatos.setAttribute("PDF_FACT_LISTA_VAR_AUX_NRO_REG", ""+PARAM_CBX_MOSTRAR+"");
        sesionDatos.setAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_PAG", ""+NRO_PAG_ACTUAL_MOSTRAR+"");
        sesionDatos.setAttribute("PDF_FACT_LISTARESU_VAR_AUX_NRO_REG", ""+PARAM_CBX_MOSTRAR+"");
        
        return listaFiltro;
    }
    
    
    
    // METODO SIMILAR AL QUE SE ENCUENTA EN EL MODELO DE INICIO DE SESION, SOLO QUE AQUI LO REPLIQUE PARA ESTADOS DE FACTURA NOMAS 
    public String devolverTextEstado(String PARAM_ESTADO) {
        String estado = "";
        
        if (PARAM_ESTADO.equalsIgnoreCase("A")) {
            estado = "Activo";
        } else if (PARAM_ESTADO.equalsIgnoreCase("X")) {
            estado = "Anulado";
        } else if (PARAM_ESTADO.equalsIgnoreCase("C")) {
            estado = "Cobrado";
        }
        return estado;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA PODER CARGAR EL COMBO DE TIPO DE FACTURA
    public Map<String, String> cargarComboTipoFact(String paramTipoFact) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramTipoFact == null || paramTipoFact.isEmpty() || paramTipoFact.equals("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRE QUE QUIERE CREAR UN REGISTRO Y NO QUE QUIERE RECUPERAR UNO 
            lista.put("Co","Contado");
            lista.put("Cr","Crédito");
        } else { // SI NO SE ENCUENTRA VACIO ES PORQUE TIENE EL VALOR DE UN REGISTRO RECUPERADO Y CARGARE PRIMERO YA QUE ES EL DATO GUARDADO 
            if (paramTipoFact.equalsIgnoreCase("CONTADO") || paramTipoFact.equalsIgnoreCase("Co")) {
                lista.put("Co","Contado");
                lista.put("Cr","Crédito");
            } else if (paramTipoFact.equalsIgnoreCase("CREDITO") || paramTipoFact.equalsIgnoreCase("Crédito") || paramTipoFact.equalsIgnoreCase("Cr")) {
                lista.put("Cr","Crédito");
                lista.put("Co","Contado");
            } else { // en caso de que de no entre en ninguna entonces para que no de error le pasare uno 
                lista.put("Co","Contado");
                lista.put("Cr","Crédito");
            }
        } // end else 
        return lista;
    } // end method 
    
    
    
    /*
    METODO PARA TRAER LAS CUENTAS DE UN CLIENTE EN ESPECIFICO 
    ESTE METODO LO UTILIZO EN FACTURA PARA LUEGO DE CARGAR AL CLIENTE PUEDA ELEGIR LAS CUENTAS DE ESE CLIENTE 
    */
    public List cargarCuentaCliente(String PARAM_IDCLIENTE, HttpSession PARAM_SESION) {
        List<BeanCuentaCliente> listaDatos = new ArrayList<>();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT ccc.IDCUENTACLIENTE, ccc.IDPERSONA, ccc.IDAGENDAMIENTO, ccc.ITEM_AGEN, ccc.NROCUOTA, agen_cab.IDCLINICA, agen_cab.IDESPECIALIDAD, \n" +
                "DATE_FORMAT(ccc.FEC_VENCIMIENTO, '%d/%m/%Y') AS FEC_VENCIMIENTO, ccc.MONTO, ccc.SALDO, ccc.COMENTARIO, ccc.ESTADO \n" +
                "FROM cc_cuenta_cliente ccc \n" +
                "JOIN ope_agendamiento agen_cab ON ccc.IDAGENDAMIENTO = agen_cab.IDAGENDAMIENTO \n" +
                "JOIN ope_agendamiento_det agen_det ON ccc.IDAGENDAMIENTO = agen_det.IDAGENDAMIENTO AND ccc.ITEM_AGEN = agen_det.ITEM AND agen_det.ESTADO <> 'X' \n" +
                "WHERE ccc.IDPERSONA = '"+PARAM_IDCLIENTE+"' \n" +
                "AND agen_cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "AND ccc.ESTADO = 'A' \n";
            
//            String sql = "SELECT ccc.IDCUENTACLIENTE, ccc.IDPERSONA, ccc.IDAGENDAMIENTO, ccc.ITEM_AGEN, ccc.NROCUOTA, agen_cab.IDCLINICA, agen_cab.IDESPECIALIDAD, \n" +
//                "DATE_FORMAT(ccc.FEC_VENCIMIENTO, '%d/%m/%Y') AS FEC_VENCIMIENTO, ccc.MONTO, ccc.SALDO, ccc.COMENTARIO, ccc.ESTADO \n" +
//                "FROM cc_cuenta_cliente ccc \n" +
//                "JOIN ope_agendamiento agen_cab ON ccc.IDAGENDAMIENTO = agen_cab.IDAGENDAMIENTO \n" +
//                "WHERE ccc.IDPERSONA = '"+PARAM_IDCLIENTE+"' \n" +
//                "AND agen_cab.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
//                "AND ccc.ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---cargarCuentaCliente()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while(MF_RESULTADO.next()) {
                BeanCuentaCliente datos = new BeanCuentaCliente();
                // bean cuenta cliente -- 
                    datos.setCC_IDCUENTACLIENTE(MF_RESULTADO.getString("IDCUENTACLIENTE"));
                    datos.setCC_IDPERSONA(MF_RESULTADO.getString("IDPERSONA"));
                    datos.setCC_IDAGENDAMIENTO(MF_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setCC_ITEM_AGEN(MF_RESULTADO.getString("ITEM_AGEN"));
                    datos.setCC_NROCUOTA(MF_RESULTADO.getString("NROCUOTA"));
                    datos.setCC_FEC_VENCIMIENTO(MF_RESULTADO.getString("FEC_VENCIMIENTO"));
//                    datos.setCC_INTERES(formatear.format(MF_RESULTADO.getDouble("INTERES")));
                    datos.setCC_MONTO(formatear.format(MF_RESULTADO.getDouble("MONTO")));
                    datos.setCC_SALDO(formatear.format(MF_RESULTADO.getDouble("SALDO")));
                    datos.setCC_COMENTARIO(MF_RESULTADO.getString("COMENTARIO"));
                    datos.setCC_ESTADO(MF_RESULTADO.getString("ESTADO"));
                // bean de agendamiento -- 
                    datos.setOA_IDCLINICA(MF_RESULTADO.getString("IDCLINICA"));
                    datos.setOA_IDAGENDAMIENTO(MF_RESULTADO.getString("IDAGENDAMIENTO"));
                    datos.setOA_IDESPECIALIDAD(MF_RESULTADO.getString("IDESPECIALIDAD"));
                listaDatos.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaDatos;
    }
    
    
    
    // METODO QUE UTILIZO PARA PODER RECUPERAR LA CANTIDAD DE IVA QUE TENGA EL DETALLE DEL EMPEÑO DE LA CUENTA DEL CLIENTE 
    public List traerIvaAgendamiento(String PARAM_IDAGENDAMIENTO, String PARAM_ITEM_AGEN) {
        List<BeanFacturaCab> lista = new ArrayList<>();
        
//        try {
//            String sql = "SELECT DISTINCT(IVA) AS IVA \n"
//                + "FROM ope_agendamiento_det \n"
//                + "WHERE IDAGENDAMIENTO = "+PARAM_IDAGENDAMIENTO+" AND ITEM = '"+PARAM_ITEM_AGEN+"' \n";
//            System.out.println("------------------------MODEL_FACTURA--------------------------");
//            System.out.println("-- ---traerIvaAgendamiento()--------     "+sql);
//            System.out.println("---------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MF_RESULTADO = consultaBD(sql);
//            
//            while(MF_RESULTADO.next()) {
                BeanFacturaCab datos = new BeanFacturaCab();
                    datos.setOFD_IDIMPUESTO("0");
//                    datos.setOFD_IDIMPUESTO(MF_RESULTADO.getString("IVA"));
                lista.add(datos);
//            }
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
//        }
        return lista;
    } // end method 
    
    
    
    // METODO PARA HACER LA PAGINACION CARGANDO LAS FACTURAS ACTIVAS PARA LA PAGINA DE ANULAR FACTURA, YA QUE EL CARGA GRILLA NORMAL NO ME SIRVE PORQUE NO FILTRA POR LOS ESTADOS DE LAS FACTURAS ACTIVAS 
    public List cargar_grilla_anular_fact(HttpSession PARAM_SESION, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
        ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_anular_fact()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanFacturaCab> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (PARAM_SESION.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(PARAM_SESION.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(PARAM_SESION.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD DE RESULTADOS QUE DEVUELVE EL SELECT / A MODO DE EJEMPLO 
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
        
        String OBSERVACION; // VARIABLES QUE UTILIZO PARA PODER CONTROLAR SI EL RESULTADO DE LA COLUMNA ESTA NULL ASI PARA CARGAR OTRO VALOR A LA CLASE PARA QUE NO SALTE ERROR, Y ESE NUEVO VALOR QUE LE CARGO CUANDO ES NULL VA DENTRO DE ESTA VARIABLE QUE LUEGO PASO A LA CLASE 
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT fact.IDFACTURA, fact.IDCLINICA, fact.NROFACTURA, fact.TIPOFACTURA, \n" +
                "fact.IDCLIENTE, per.CINRO, per.NOMBRE, per.APELLIDO, per.DIRECCION, per.TELFMOVIL, \n" +
                "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.IDARQUEO, fact.ESTADO, \n" +
                "fact.TOTAL_FACTURA, fact.TOTAL_DCTO, fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, \n" +
                "fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA, fact.IDFORMACOBRO, fact.LETRAS, \n" +
                "fact.OBSERVACION, fact.USU_ALTA, fact.FEC_ALTA \n" +
                "FROM ope_factura fact \n" +
                "JOIN rh_persona per ON fact.IDCLIENTE = per.IDPERSONA \n" +
                "WHERE fact.ESTADO = 'C' \n" +
                "AND fact.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "ORDER BY fact.IDFACTURA DESC \n" //+
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(fact.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM ope_factura fact \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "JOIN rh_persona per ON fact.IDCLIENTE = per.IDPERSONA \n" +
                "WHERE fact.ESTADO = 'C' \n" +
                "AND fact.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" +
                "ORDER BY fact.IDFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---cargar_grilla_anular_fact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);
            
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_IDCLINICA(MF_RESULTADO.getInt("IDCLINICA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
                        datos.setOF_IDARQUEO(MF_RESULTADO.getString("IDARQUEO"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                        datos.setOF_TOTAL_DCTO(MF_RESULTADO.getString("TOTAL_DCTO"));
                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                        datos.setOF_IDFORMACOBRO(MF_RESULTADO.getString("IDFORMACOBRO"));
                        datos.setOF_LETRAS(MF_RESULTADO.getString("LETRAS"));
                        if (MF_RESULTADO.getString("OBSERVACION") != null) {
                            OBSERVACION = MF_RESULTADO.getString("OBSERVACION");
                        } else {
                            OBSERVACION = "";
                        }
                        datos.setOF_OBSERVACION(OBSERVACION);
                        datos.setOF_USU_ALTA(MF_RESULTADO.getString("USU_ALTA"));
                        datos.setOF_FEC_ALTA(MF_RESULTADO.getString("FEC_ALTA"));
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
                        System.out.println("__NUEVO_CANT_LISTA:  :"+cant_btn_lista);
                    }
                }
                System.out.println(".");
                System.out.println(".");
                i = i +1; // le incremento para no mantener el mismo numero 
            } // end for or while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
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
    
    
    
    /*
     *   METODO PARA FILTRAR CON PAGINACION PARA LA PAGINA DE PANEL DE CONTROL - ANULAR FACTURA 
    */
    public List filtrar_paginacion_anular_fact(HttpSession sesionDatos, 
            String PARAM_CBX_MOSTRAR, 
            String PARAM_TXT_FIL_NRO_FACT, 
            String PARAM_TXT_FIL_FEC_INI,
            String PARAM_TXT_FIL_FEC_FIN, 
            String PARAM_TXT_FIL_ID_CLIENTE,
            String PARAM_VAR_ESTADO_CAB, // VARIABLE QUE UTILIZO PARA FILTRAR EL SELECT POR EL ESTADO DE LA FACTURA DE LA CABECERA, LO AGREGUE MAS POR LA PAGINA DE ANULAR FACTURA DONDE SOLO QUIERO VER LAS FACTURAS ACTIVAS Y NO LAS ANULADAS 
            String PARAM_ID_CLINICA // SI SE FILTRA POR LA CLINICA ENTONCES ES VABARIABLE ESTARA CARGADA Y FILTRARIA POR ELLA, EN CASO CONTRARIO FILTRARIA POR LA CLINICA DE LOGEO DEL USUARIO 
            , String PARAM_IDPACIENTE // PARAMETRO QUE UTILIZO PARA CARGAR EL IDPACIENTE CUANDO EL PERFIL SEA PACIENTE, YA QUE ESTE METODO UTILIZO EN VARIOS LUGARES, Y LO DIVIDO CON ESTA VARIABLE, SI ESTA CARGADO ENTONCES EL PERFIL ES DE PACIENTE, Y SI ESTA VACIO, ENTONCES EL PERFIL PUEDE SER ADMIN, SECRETARIO O MEDICO 
    ) {
        List<BeanFacturaCab> listaFiltro = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_PC_ANU_FACT_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        /*
            DE AQUI EMPIEZA LOS FILTROS PARA EL WHERE DEL SELECT --
        */
        String VAR_WHERE="WHERE fact.ESTADO = 'C' \n";
        
        // OBSERVACION_PACIENTE: CONTROLO PRIMERAMENTE SI EL PARAMETRO DE IDPACIENTE ESTA CARGADO, SI ESO PASA ES PORQUE EL IDPERFIL ES PACIENTE, Y EL PARAMETRO DE IDPACIENTE ES EL IDPERSONA LOGEADA, ENTONCES EL SELECT DEBERIA DE FILTRAR POR LAS FACTURAS DE ESE PACIENTE NOMAS Y NO POR LAS DE OTRO 
        if (PARAM_IDPACIENTE == null || PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//            VAR_WHERE = "";
        } else { // SI ESTUVIERA CARGADO ENTONCES ASUMO QUE EL PERFIL ES DE PACIENTE Y APLICARIA LA OBSERVACION_PACIENTE  
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
            if (VAR_WHERE.equals("")) {
                VAR_WHERE = "WHERE fact.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n";
            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.IDCLIENTE = '"+PARAM_IDPACIENTE+"' \n";
            }
        }
        
        VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        // OBSERVACION_SOBRE_CLINICA_1: SI EL PERFIL ES DE PACIENTE, ENTONCES LE MOSTRARIA TODAS SUS FACTURAS DE CUALQUIER CLINICA Y SI FUERA OTRO PERFIL COMO SECRETARIO, ENTONCES AHI SI LE FILTRARIA POR LA CLINICA FILTRADA O LOGEADA 
        if (PARAM_IDPACIENTE == null || PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
            // OBSERVACION_SOBRE_CLINICA_2: SI SE ENCUENTRA VACIO ENTONCES FILTRARIA POR LA CLINICA DE LOGEO DEL USUARIO PERO SI SE ENCUENTRA CARGADO ENTONCES EL USUARIO DESMARCO EL CHECK Y FILTRO POR UNA CLINICA Y CARGARIA ESO EN EL WHERE... ESTE CAMPO SIEMPRE ESTARA CARGADO YA QUE EL SELECT SIEMPRE ESTARA FILTRANDO POR CLINICA YA SEA LA CLINICA DEL FILTRO O DEL USUARIO LOGEADO 
            if (PARAM_ID_CLINICA == null || PARAM_ID_CLINICA.isEmpty() || PARAM_ID_CLINICA.equals(" ")) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
                if (VAR_WHERE.equals("")) {
                    VAR_WHERE = "WHERE (rp.IDCLINICA = "+VAR_ID_CLINICA+" || fact.IDCLINICA = "+VAR_ID_CLINICA+") \n";
                } else {
                    VAR_WHERE = VAR_WHERE + "AND (rp.IDCLINICA = "+VAR_ID_CLINICA+" || fact.IDCLINICA = "+VAR_ID_CLINICA+") \n";
                }
            } else {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
                if (VAR_WHERE.equals("")) {
                    VAR_WHERE = "WHERE (rp.IDCLINICA = "+PARAM_ID_CLINICA+" || fact.IDCLINICA = "+PARAM_ID_CLINICA+") \n";
                } else {
                    VAR_WHERE = VAR_WHERE + "AND (rp.IDCLINICA = "+PARAM_ID_CLINICA+" || fact.IDCLINICA = "+PARAM_ID_CLINICA+") \n";
                }
            }
        }
        
        // CONTROLO SI ES QUE SE CARGO ALGUN NRO DE FACTURA EN EL TXT 
        if (PARAM_TXT_FIL_NRO_FACT.equals("") || PARAM_TXT_FIL_NRO_FACT.isEmpty()) {
            //
        } else {
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
            if (VAR_WHERE.equals("")) {
                VAR_WHERE = "WHERE (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) \n";
            } else {
                VAR_WHERE = VAR_WHERE + "AND (UPPER(fact.NROFACTURA) LIKE UPPER('%"+PARAM_TXT_FIL_NRO_FACT+"%') ) \n";
            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADAS LAS FECHAS PARA ASI AGREGUEGAR AL WHERE EL FILTRO POR LAS FECHAS / SI UNA SOLA FECHA ESTA CARGADA, ENTONCES VOY A HACER EL FILTRO POR ESA FECHA 
        if (PARAM_TXT_FIL_FEC_INI.equals("") && PARAM_TXT_FIL_FEC_FIN.equals("")) {
            //
        } else {
            // CONTROLO PRIMERAMENTE SI AMBAS FECHAS ESTAN CARGADAS 
            if (!(PARAM_TXT_FIL_FEC_INI.equals("")) && !(PARAM_TXT_FIL_FEC_FIN.equals(""))) {
                // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
                if (VAR_WHERE.equals("")) {
                    VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
                } else {
                    VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') >= '"+PARAM_TXT_FIL_FEC_INI+"' AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') <= '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
                }
            } else { // SI NO ESTAN CARGADAS ENTONCES CONTROLO PARA VER SI UNO DE ELLAS NO ESTA CARGADA 
                if (!(PARAM_TXT_FIL_FEC_INI.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
                    if (VAR_WHERE.equals("")) {
                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
                    } else {
                        VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_INI+"' \n";
                    }
                }
                if (!(PARAM_TXT_FIL_FEC_FIN.equals(""))) {
                    // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
                    if (VAR_WHERE.equals("")) {
                        VAR_WHERE = "WHERE DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
                    } else {
                        VAR_WHERE = VAR_WHERE + "AND DATE_FORMAT(fact.FECHAFACTURA, '%Y-%m-%d') = '"+PARAM_TXT_FIL_FEC_FIN+"' \n";
                    }
                }
            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADO EL IDCLIENTE PARA ASI AGREGAR AL WHERE EL FILTRO POR IDCLIENTE  
        if (PARAM_TXT_FIL_ID_CLIENTE.equals("") || PARAM_TXT_FIL_ID_CLIENTE.isEmpty()) {
            //
        } else {
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
            if (VAR_WHERE.equals("")) {
                VAR_WHERE = "WHERE fact.IDCLIENTE = '"+PARAM_TXT_FIL_ID_CLIENTE+"' \n";
            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.IDCLIENTE = '"+PARAM_TXT_FIL_ID_CLIENTE+"' \n";
            }
        }
        
        // CONTROLO SI ES QUE SE ENCUENTRAN CARGADO LA VARIABLE DE ESTADO PARA ASI AGREGAR AL WHERE EL FILTRO POR ESTADO DE LA FACTURA DE LA CABECERA   
        if (PARAM_VAR_ESTADO_CAB.equals("") || PARAM_VAR_ESTADO_CAB.isEmpty()) {
            //
        } else {
            // CONTROLO SI ES QUE YA SE AGREGO ALGO AL WHERE ASI PARA CONCATENAR CON "AND" Y NO CON "WHERE" Y EVITAR ERRORES DE CONSULTA 
            if (VAR_WHERE.equals("")) {
                VAR_WHERE = "WHERE fact.ESTADO = '"+PARAM_VAR_ESTADO_CAB+"' \n";
            } else {
                VAR_WHERE = VAR_WHERE + "AND fact.ESTADO = '"+PARAM_VAR_ESTADO_CAB+"' \n";
            }
        }
        
        try {
            String sql = "";
            /*
             * OBSERVACION: SI EL IDPACIENTE QUE VIENE POR PARAMETRO, ESTA VACIO, ENTONCES EL IDPERFIL ES DE ADMIN O SECRE O OTRO PERFIL 
                PERO SI VIENE CARGADO, ENTONCES EL PERFIL ES PACIENTE Y LE FILTRO PARA MOSTRAR LAS FACTURAS DE ESE PACIENTE Y NO DE OTROS 
            */
//            if (PARAM_IDPACIENTE.isEmpty() || PARAM_IDPACIENTE.equals("")) {
//                sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
//                    "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
//                    "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
//                    "FROM ope_factura fact \n" +
//                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
//    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
//                    ""+VAR_WHERE+" \n" + 
//                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n" + 
//                    ""+sqlFiltroCbx+" \n";
//            } else {
                sql = "SELECT fact.IDFACTURA, fact.NROFACTURA, fact.TIPOFACTURA, fact.IDCLIENTE, \n" +
                    "DATE_FORMAT(fact.FECHAFACTURA, '%d/%m/%Y') AS FECHAFACTURA, fact.ESTADO, fact.TOTAL_FACTURA, \n" +
                    "fact.TOTAL_IVA10, fact.TOTAL_IVA5, fact.TOTAL_GRAV10, fact.TOTAL_GRAV5, fact.TOTAL_EXENTA, fact.TOTAL_IVA \n" +
                    "FROM ope_factura fact \n" +
                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                    ""+VAR_WHERE+" \n" + 
                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"// + 
//                    ""+sqlFiltroCbx+" \n"
                    ;
                
                String SELECT_COUNT = "SELECT COUNT(fact.IDFACTURA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                    "FROM ope_factura fact \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                    "JOIN rh_persona rp ON rp.IDPERSONA = fact.IDCLIENTE \n" +
    //                "WHERE rp.IDCLINICA = "+VAR_ID_CLINICA+" \n" + 
                    ""+VAR_WHERE+" \n" + 
                    "ORDER BY fact.FECHAFACTURA DESC, fact.NROFACTURA DESC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
                cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
//            }
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- --filtrar_paginacion_anular_fact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
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
            while (MF_RESULTADO.next()) {
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanFacturaCab datos = new BeanFacturaCab();
                        datos.setOF_IDFACTURA(MF_RESULTADO.getInt("IDFACTURA"));
                        datos.setOF_NROFACTURA(MF_RESULTADO.getString("NROFACTURA"));
                        datos.setOF_IDCLIENTE(MF_RESULTADO.getInt("IDCLIENTE"));
                        datos.setOF_TIPOFACTURA(MF_RESULTADO.getString("TIPOFACTURA"));
                        datos.setOF_FECHAFACTURA(MF_RESULTADO.getString("FECHAFACTURA"));
                        datos.setOF_TOTAL_IVA10(MF_RESULTADO.getString("TOTAL_IVA10"));
                        datos.setOF_TOTAL_IVA5(MF_RESULTADO.getString("TOTAL_IVA5"));
                        datos.setOF_TOTAL_GRAV10(MF_RESULTADO.getString("TOTAL_GRAV10"));
                        datos.setOF_TOTAL_GRAV5(MF_RESULTADO.getString("TOTAL_GRAV5"));
                        datos.setOF_TOTAL_EXENTA(MF_RESULTADO.getString("TOTAL_EXENTA"));
                        datos.setOF_TOTAL_IVA(MF_RESULTADO.getString("TOTAL_IVA"));
                        datos.setOF_TOTAL_FACTURA(MF_RESULTADO.getString("TOTAL_FACTURA"));
                        datos.setOF_ESTADO(MF_RESULTADO.getString("ESTADO"));
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
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_PC_ANU_FACT_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_PC_ANU_FACT_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_PC_ANU_FACT_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    
    // ANULO LA CABECERA DE LA FACTURA 
    public String anular_factura(String PARAM_IDFACTURA, String PARAM_IDCLIENTE) {
        String tipo_resultado = ""; // 1 : exito / 2 : error 
        try {
            String sql = "UPDATE ope_factura \n" +
                "SET ESTADO = 'X' \n" +
                "WHERE IDFACTURA = '"+PARAM_IDFACTURA+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---anular_factura()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            int varresultado = MF_SENTENCIA.executeUpdate(sql);
            if (varresultado == 1) {
                tipo_resultado = "1";
                cerrarConexiones();
                anularDetalleFact(PARAM_IDFACTURA, PARAM_IDCLIENTE);
            } else {
                tipo_resultado = "2";
                cerrarConexiones();
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipo_resultado;
    } // end method 
    
    
    // HAGO UN SELECT PARA RECUPERAR EL DETALLE DE LA FACTURA Y ASI CONTROLAR SI SE FACTURA ALGUNA CUENTA PARA PODER VOLVER A REESTABLECER LA CUENTA PARA QUE SIGUA ACTIVA Y NO SE QUEDE COMO CANCELADA 
    public void anularDetalleFact(String PARAM_IDFACTURA, String PARAM_IDCLIENTE) {
        try {
            String sql = "SELECT cab.IDFACTURA, cab.NROFACTURA, cab.TIPOFACTURA, cab.IDCLIENTE, cab.FECHAFACTURA, cab.TOTAL_FACTURA, \n" +
                "det.ITEM, det.IDCONCEPTO, det.IDTIPOCONCEPTO, det.CANTIDAD, det.PRECIO, det.IDIMPUESTO, det.EXENTO, \n" +
                "det.IVA5, det.IVA10, det.GRAV5, det.GRAV10, det.SUBTOTAL \n" +
                "FROM ope_factura cab \n" +
                "JOIN ope_factura_det det ON cab.IDFACTURA = det.IDFACTURA \n" +
                "WHERE cab.IDFACTURA = '"+PARAM_IDFACTURA+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---anularDetalleFact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            List<BeanFacturaCab> listaIdsConc = new ArrayList<BeanFacturaCab>(); // LISTA QUE UTILIZO PARA CARGAR RESULTADO Y LUEGO RECORRER PARA SABER LOS IDCONCEPTO PORQUE EL RESULTADO SE CIERRA Y NO PARA HACER DENTRO DEL WHILE 
            while (MF_RESULTADO.next()) {
                // CABECERA  ------  ------  ------ 
//                String IDFACTURA = String.valueOf(MF_RESULTADO.getString("IDFACTURA"));
//                String NROFACTURA = MF_RESULTADO.getString("NROFACTURA");
//                String TIPOFACTURA = MF_RESULTADO.getString("TIPOFACTURA");
//                String IDCLIENTE = String.valueOf(MF_RESULTADO.getString("IDCLIENTE"));
//                String FECHAFACTURA = MF_RESULTADO.getString("FECHAFACTURA");
//                String TOTAL_FACTURA = MF_RESULTADO.getString("TOTAL_FACTURA");
                // DETALLE ------  ------  ------ 
//                String ITEM = MF_RESULTADO.getString("ITEM");
                String IDCONCEPTO = MF_RESULTADO.getString("IDCONCEPTO"); // IDCUENTACLIENTE O IDSTOCK(IDPRODUCTO)  
                String IDTIPOCONCEPTO = MF_RESULTADO.getString("IDTIPOCONCEPTO");
                int CANTIDAD = MF_RESULTADO.getInt("CANTIDAD");
//                String PRECIO = MF_RESULTADO.getString("PRECIO");
//                String IDIMPUESTO = MF_RESULTADO.getString("IDIMPUESTO");
//                String EXENTO = MF_RESULTADO.getString("EXENTO");
//                String IVA5 = MF_RESULTADO.getString("IVA5");
//                String IVA10 = MF_RESULTADO.getString("IVA10");
//                String GRAV5 = MF_RESULTADO.getString("GRAV5");
//                String GRAV10 = MF_RESULTADO.getString("GRAV10");
//                String SUBTOTAL = MF_RESULTADO.getString("SUBTOTAL");
                BeanFacturaCab datos = new BeanFacturaCab();
                    datos.setOFD_IDCONCEPTO(Integer.parseInt(IDCONCEPTO));
                    datos.setOFD_IDTIPOCONCEPTO(Integer.parseInt(IDTIPOCONCEPTO));
                    datos.setOFD_CANTIDAD(CANTIDAD);
                listaIdsConc.add(datos);
            } // end while 
            cerrarConexiones();
            
            for (BeanFacturaCab listaIdConcepto : listaIdsConc) {
                String IDCONCEPTO = String.valueOf(listaIdConcepto.getOFD_IDCONCEPTO());
                String IDTIPOCONCEPTO = String.valueOf(listaIdConcepto.getOFD_IDTIPOCONCEPTO());
                String CANTIDAD = String.valueOf(listaIdConcepto.getOFD_CANTIDAD());
                
                // CONTROLO QUE TIPO DE CONCEPTO ES (CUENTA O PRODUCTO) 
                if (IDTIPOCONCEPTO.equalsIgnoreCase("1")) { // CUENTA (DEL CLIENTE) 
                    reestablecerCtaCliente(IDCONCEPTO, PARAM_IDCLIENTE);
                } else if (IDTIPOCONCEPTO.equalsIgnoreCase("2")) { // PRODUCTO (DEL STOCK) 
//                    modificarStockProd("+", Integer.parseInt(IDCONCEPTO), CANTIDAD);
//                    if (ctrlStockProdEmpEst(String.valueOf(IDCONCEPTO)) == true) { // CONTROLO SI NO TIENE MAS STOCK EL PRODUCTO, Y COMO ESTOY ANULANDO, EN ESTE CASO SI TIENE STOCK CAMBIARIA EL ESTADO 
//                        modificarEstEmpDet(String.valueOf(IDCONCEPTO), "L"); // CAMBIO EL ESTADO DEL PRODUCTO A LIQUIDADO EN EL DETALLE DE EMPEÑO PORQUE VENDIDO YA NO ESTA AL ANULAR ESTA FACTURA 
//                    }
//                    if (ctrlEstEmpDet(Integer.parseInt(IDCONCEPTO)) == true) { // CONTROLO SI NO HAY NINGUN PRODUCTO ACTIVO, EXPIRADO O LIQUIDADO, Y COMO ESTAMOS ANULANDO EN ESTE CASO SI ES QUE HAY CAMBIARIA LA CABECERA... 
//                        modificarEstEmpCab(Integer.parseInt(IDCONCEPTO), "L"); // CAMBIO EL ESTADO DE LA CABECERA DEL EMPEÑO PORQUE SU DETALLE YA NO TIENE A TODOS LOS PRODUCTOS VENDIDOS 
//                    }
                } else if (IDTIPOCONCEPTO.equalsIgnoreCase("3")) { // INTERES AGREGADO 
                    //
                } else if (IDTIPOCONCEPTO.equalsIgnoreCase("4")) { // DESCUENTO AGREGADO 
                    //
                }
            } // end for 
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // end method 
    
    
    // LE VUELVO A CARGAR EL SALDO Y CAMBIAR EL ESTADO DE LA CUENTA DEL CLIENTE PARA QUE EL SISTEMA INTERPRETE QUE LA FACTURA SE ANULO Y ENTONCES ESA CUENTA SIGUE ACTIVA Y NO SE ENCUENTRA FINALIZADA 
    public void reestablecerCtaCliente(String PARAM_IDCONCEPTO, String PARAM_IDCLIENTE) {
        try {
            String SALDO_CTA = recuperarSaldoCtaCli(PARAM_IDCONCEPTO, PARAM_IDCLIENTE);// RECUPERO EL MONTO DE LA CUOTA DE LA CUENTA PARA CARGARLA COMO SALDO YA QUE SE SUPONE QUE DEBE DE VOLVER A PAGAR PORQUE SE ANULO LA FACTURA Y NO LO PUEDO DEJAR CON SALDO CERO  
            String sql = "UPDATE cc_cuenta_cliente \n" +
                "SET SALDO = '"+SALDO_CTA+"', \n" + 
                "ESTADO = 'A' \n" + // ESTADO A : ACTIVO 
                "WHERE IDCUENTACLIENTE = '"+PARAM_IDCONCEPTO+"' \n" + 
                "AND IDPERSONA = '"+PARAM_IDCLIENTE+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---reestablecerCtaCliente()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            MF_SENTENCIA.executeUpdate(sql);
            cerrarConexiones();
            reestablecerEstAgend(PARAM_IDCONCEPTO, PARAM_IDCLIENTE);
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // end method 
    
    
    // METODO QUE UTILIZO PARA DEVOLVER EL ESTADO DE PENDIENTE AL DETALLE DEL AGENDAMIENTO Y RESETEAR LAS COLUMNAS DE AGENDADO Y IDFACTURA, YA QUE AL ANULAR LA FACTURA, Y CAMBIAR EL ESTADO DE LA CUENTA, SE DEBERIA DE REESTABLECER TAMBIEN EL ESTADO DEL DETALLE DEL AGENDAMIENTO 
    public void reestablecerEstAgend(String PARAM_IDCONCEPTO, String PARAM_IDCLIENTE) {
        try {
            String IDAGENDAMIENTO = getIdAgendamiento(PARAM_IDCONCEPTO, PARAM_IDCLIENTE);
            String ITEM_AGEN = getItemAgendamiento(PARAM_IDCONCEPTO, PARAM_IDCLIENTE);
            String sql = "UPDATE ope_agendamiento_det \n" +
                "SET AGENDADO = 'NO', \n" + 
                "IDFACTURA = '0', \n" + 
                "ESTADO = 'P' \n" + // ESTADO P : PENDIENTE 
                "WHERE IDAGENDAMIENTO = '"+IDAGENDAMIENTO+"' \n" + 
                "AND ITEM = '"+ITEM_AGEN+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---reestablecerEstAgend()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            MF_CONEXION = devolverConex();
            MF_SENTENCIA = MF_CONEXION.createStatement();
            MF_SENTENCIA.executeUpdate(sql);
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
    } // end method 
    
    
    // METODO QUE UTILIZO PARA RECUPERAR EL MONTO DE LA CUENTA DEL CLIENTE PARA AL ANULAR LA FACTURA REESTABLECERLE AL SALDO YA QUE SE ANULO EL COBRO DE LA CUOTA DE ESA CUENTA Y NO PUEDO DEJAR LA CUOTA CON SALDO CERO 
    public String getIdAgendamiento(String PARAM_IDCTACLI, String PARAM_IDCLIENTE) {
        String IDAGENDAMIENTO = "";
        try {
            String sql = "SELECT IDCUENTACLIENTE, IDPERSONA, IDAGENDAMIENTO, ITEM_AGEN \n" +
                "FROM cc_cuenta_cliente \n" +
                "WHERE IDCUENTACLIENTE = '"+PARAM_IDCTACLI+"' \n" +
                "AND IDPERSONA = '"+PARAM_IDCLIENTE+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---getIdAgendamiento()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while (MF_RESULTADO.next()) {
                IDAGENDAMIENTO = MF_RESULTADO.getString("IDAGENDAMIENTO");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return IDAGENDAMIENTO;
    } // end method 
    
    
    // METODO QUE UTILIZO PARA RECUPERAR EL MONTO DE LA CUENTA DEL CLIENTE PARA AL ANULAR LA FACTURA REESTABLECERLE AL SALDO YA QUE SE ANULO EL COBRO DE LA CUOTA DE ESA CUENTA Y NO PUEDO DEJAR LA CUOTA CON SALDO CERO 
    public String getItemAgendamiento(String PARAM_IDCTACLI, String PARAM_IDCLIENTE) {
        String ITEM_AGEN = "";
        try {
            String sql = "SELECT IDCUENTACLIENTE, IDPERSONA, IDAGENDAMIENTO, ITEM_AGEN \n" +
                "FROM cc_cuenta_cliente \n" +
                "WHERE IDCUENTACLIENTE = '"+PARAM_IDCTACLI+"' \n" +
                "AND IDPERSONA = '"+PARAM_IDCLIENTE+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---recuperarSaldoCtaCli()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while (MF_RESULTADO.next()) {
                ITEM_AGEN = MF_RESULTADO.getString("ITEM_AGEN");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return ITEM_AGEN;
    } // end method 
    
    
    // METODO QUE UTILIZO PARA RECUPERAR EL MONTO DE LA CUENTA DEL CLIENTE PARA AL ANULAR LA FACTURA REESTABLECERLE AL SALDO YA QUE SE ANULO EL COBRO DE LA CUOTA DE ESA CUENTA Y NO PUEDO DEJAR LA CUOTA CON SALDO CERO 
    public String recuperarSaldoCtaCli(String PARAM_IDCTACLI, String PARAM_IDCLIENTE) {
        String SALDO_CTA = "";
//        DecimalFormat formatear = new DecimalFormat("###,###,###");
        try {
            String sql = "SELECT (MONTO) AS SALDO \n" +
                "FROM cc_cuenta_cliente \n" +
                "WHERE IDPERSONA = '"+PARAM_IDCLIENTE+"' \n" +
                "AND IDCUENTACLIENTE = '"+PARAM_IDCTACLI+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---recuperarSaldoCtaCli()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while (MF_RESULTADO.next()) {
                SALDO_CTA = formatear.format(MF_RESULTADO.getDouble("SALDO")).replace(".", "");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return SALDO_CTA;
    } // end method 
    
    
    
    // METODO QUE UTILIZO PARA CONTROLAR QUE ANTES DE ANULAR UNA FACTURA ESTA NO CUENTA CON NINGUNA FICHA DE ATENCION 
    public boolean ctrlFichaAtencion(HttpSession PARAM_SESION, String PARAM_IDFACTURA, String PARAM_IDCLIENTE) {
        boolean valor = false;
        try {
            String sql = "SELECT fact.IDFACTURA, ccc.IDCUENTACLIENTE, oap.IDATENCIONPAC \n" +
                "FROM ope_factura fact \n" +
                "JOIN ope_factura_det ofd ON ofd.IDFACTURA = fact.IDFACTURA AND ofd.IDTIPOCONCEPTO = 1 \n" +
                "JOIN cc_cuenta_cliente ccc ON ofd.IDCONCEPTO = ccc.IDCUENTACLIENTE AND ccc.ESTADO <> 'X' \n" +
                "LEFT JOIN ope_atencion_paciente oap ON ccc.IDAGENDAMIENTO = oap.IDAGENDAMIENTO AND ccc.ITEM_AGEN = oap.ITEM_AGEND_DET AND oap.ESTADO <> 'X' \n" +
                "WHERE fact.IDFACTURA = '"+PARAM_IDFACTURA+"' \n";
            System.out.println("------------------------MODEL_FACTURA--------------------------");
            System.out.println("-- ---ctrlFichaAtencion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MF_RESULTADO = consultaBD(sql);
            
            while (MF_RESULTADO.next()) {
                String IDATENCIONPAC = MF_RESULTADO.getString("IDATENCIONPAC");
                System.out.println("_   _ID_ATENCION_PAC:   :"+IDATENCIONPAC);
                String IDCUENTACLIENTE = MF_RESULTADO.getString("IDCUENTACLIENTE");
                System.out.println("_   _ID_CUENTA_CLIENTE: :"+IDCUENTACLIENTE);
                
                if (IDATENCIONPAC == null || IDATENCIONPAC.isEmpty()) { // SI ESTA VACIO EL IDATENCIONPAC ES PORQUE NO CUENTA CON UNA ATENCION ACTIVA 
                    System.out.println("_   ___IF_____VALOR_FALSE_____");
                    valor = false;
                } else {
                    System.out.println("_   ___ELSE_____VALOR_TRUE_____");
                    valor = true;
                    PARAM_SESION.setAttribute("VALI_FA_COD_IDATENCION", IDATENCIONPAC);
                }
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelFactura.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
} // END CLASS 