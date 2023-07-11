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
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanUsuario;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelPersona implements CRUD {
    
    
    
    public String VAR_ID_CLINICA = "";
    
    // variable que utilizo para colocar en los select el idcategoria persona del paciente 
    private String IDCATEG_PER_CLIENTE = "4"; // ID 4 = PACIENTE
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_PERSONA.-------");
        try {
//            String host = "jdbc:mysql://sql10.freesqldatabase.com:3306/";
//            String bd = "sql10410496";
//            String user = "sql10410496";
//            String pass = "c2AijebkXg";
            // datos local.-
//            String host = "jdbc:mysql://localhost/";
//            String bd = "odonto";
//            String user = "root";
//            String pass = "admin";
            // [OBS]: no uso el archivo properties porque me da error; porque no encuentra el archivo properties, igual si se encuentra en la misma carpeta que el modelo por eso en su lugar uso una clase en la carpeta de "config".-
//            Properties properties= new Properties();
//            properties.load(new FileInputStream(new File("configuration.properties")));
//            String host = properties.getProperty("DB_HOST");
//            String bd = properties.getProperty("DB_NAME");
//            String user = properties.getProperty("DB_USER");
//            String pass = properties.getProperty("DB_PASS");
            // datos-server.-
//            String host = "jdbc:mysql://198.199.88.44:3306/";
//            String bd = "odonto";
//            String user = "user5";
//            String pass = "agend123";
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
            MP_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MP_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
//            System.out.println("[-] Connection is failed --- to properties exception --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- file-not-found-exception.-");
//            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- io-exception.-");
//            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MP_CONEXION;
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
    private static Connection MP_CONEXION = null;
    private static Statement MP_SENTENCIA = null;
    private static ResultSet MP_RESULTADO = null;
    
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
            MP_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MP_SENTENCIA = MP_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MP_RESULTADO = MP_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MP_RESULTADO;
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
            if (MP_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MP_RESULTADO.close();
            }
            if (MP_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MP_SENTENCIA.close();
            }
            if (MP_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MP_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    
    
    
    /*
    OBSERVACION: FILTRO Y TRAIGO UNA CANTIDAD DE REGISTROS QUE ES EL NRO POR DEFECTO QUE VA A MOSTRAR EN EL COMBO PARA TRAER LOS REGISTROS 
    */
    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        List<BeanPersona> lista = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        String TIPODOC, IDCATEGORIA_PERSONA, DESC_CATEG_PERSONA, RAZON_SOCIAL, RUC, DIRECCION, EMAIL, TELFPARTICULAR, TELFMOVIL, TELEFPARTICULAR; // VARIABLES QUE UTILIZO PARA PODER CONTROLAR SI EL RESULTADO DE LA COLUMNA ESTA NULL ASI PARA CARGAR OTRO VALOR A LA CLASE PARA QUE NO SALTE ERROR, Y ESE NUEVO VALOR QUE LE CARGO CUANDO ES NULL VA DENTRO DE ESTA VARIABLE QUE LUEGO PASO A LA CLASE 
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA = 0 Y 1 SON (NO/DEFINIDO) Y ROOT */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n" + 
                "LIMIT "+metodosIniSes.minNroCbxCantFil()+" \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- ---cargar_grilla()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                BeanPersona datos = new BeanPersona();
                    datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                    datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                    datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                    datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                    datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                    if (MP_RESULTADO.getString("TIPODOC") != null) {
                        TIPODOC = MP_RESULTADO.getString("TIPODOC");
                    } else {
                        TIPODOC = "";
                    }
                    datos.setRP_TIPODOC(TIPODOC);
                    if (MP_RESULTADO.getString("IDCATEGORIA_PERSONA") != null) {
                        IDCATEGORIA_PERSONA = MP_RESULTADO.getString("IDCATEGORIA_PERSONA");
                    } else {
                        IDCATEGORIA_PERSONA = "";
                    }
                    datos.setRP_IDCATEGORIA_PERSONA(IDCATEGORIA_PERSONA);
                    if (MP_RESULTADO.getString("DESC_CATEG_PERSONA") != null) {
                        DESC_CATEG_PERSONA = MP_RESULTADO.getString("DESC_CATEG_PERSONA");
                    } else {
                        DESC_CATEG_PERSONA = "";
                    }
                    datos.setRP_DESC_CATEG_PERSONA(DESC_CATEG_PERSONA);
                    if (MP_RESULTADO.getString("RAZON_SOCIAL") != null) {
                        RAZON_SOCIAL = MP_RESULTADO.getString("RAZON_SOCIAL");
                    } else {
                        RAZON_SOCIAL = "";
                    }
                    datos.setRP_RAZON_SOCIAL(RAZON_SOCIAL);
                    if (MP_RESULTADO.getString("RUC") != null) {
                        RUC = MP_RESULTADO.getString("RUC");
                    } else {
                        RUC = "";
                    }
                    datos.setRP_RUC(RUC);
                    if (MP_RESULTADO.getString("DIRECCION") != null) {
                        DIRECCION = MP_RESULTADO.getString("DIRECCION");
                    } else {
                        DIRECCION = "";
                    }
                    datos.setRP_DIRECCION(DIRECCION);
                    if (MP_RESULTADO.getString("EMAIL") != null) {
                        EMAIL = MP_RESULTADO.getString("EMAIL");
                    } else {
                        EMAIL = "";
                    }
                    datos.setRP_EMAIL(EMAIL);
                    if (MP_RESULTADO.getString("TELFPARTICULAR") != null) {
                        TELFPARTICULAR = MP_RESULTADO.getString("TELFPARTICULAR");
                    } else {
                        TELFPARTICULAR = "";
                    }
                    datos.setRP_TELFPARTICULAR(TELFPARTICULAR);
                    if (MP_RESULTADO.getString("TELFMOVIL") != null) {
                        TELFMOVIL = MP_RESULTADO.getString("TELFMOVIL");
                    } else {
                        TELFMOVIL = "";
                    }
                    datos.setRP_TELFMOVIL(TELFMOVIL);
                    if (MP_RESULTADO.getString("TELEFPARTICULAR") != null) {
                        TELEFPARTICULAR = MP_RESULTADO.getString("TELEFPARTICULAR");
                    } else {
                        TELEFPARTICULAR = "";
                    }
                    datos.setRP_TELEFPARTICULAR(TELEFPARTICULAR);
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
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
        List<BeanPersona> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC")));
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
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "COALESCE(rp.FOTO,'') AS FOTO, COALESCE(rp.FOTO_PATH_ABS,'') AS FOTO_PATH_ABS, rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
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
            while (MP_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanPersona datos = new BeanPersona();
                        datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                        datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_TIPODOC(MP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MP_RESULTADO.getString("TELEFPARTICULAR"));
                        datos.setRP_FOTO(MP_RESULTADO.getString("FOTO"));
                        datos.setRP_FOTO_PATH_ABS(MP_RESULTADO.getString("FOTO_PATH_ABS"));
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
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_PAC_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_PAC_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_PAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
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
     METODO QUE UTILIZO PARA PAGINACION DE LA PAGINA DE REPORTE DE PACIENTE 
            - ES EL MISMO METODO Y SELECT DE LA PAGINA DE CLIENTE YA QUE EL REPORTE TRAE A TODOS LOS CLIENTES 
    */
    public List cargar_grilla_paginacion_rpt_pac(HttpSession sesionDatos, 
            String PARAM_NRO_PAG_MOSTRAR, // PARAMETRO PARA SABER EL NRO DEL BTN DE LA LISTA A MOSTRAR EN LA PAGINA 
            String PARAM_NRO_REG_MOSTRAR // PARAMETRO PARA SABER LA CANTIDAD DE REGISTROS QUE SE VAN A SEPARA PARA SABER LA CANTIDAD DE BOTONES DE PAGINAS QUE VOY A TENER 
            ) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
//        PARAM_NRO_REG_MOSTRAR = "1";
        System.out.println("___     ___________cargar_grilla_paginacion_rpt_pac()___________     ___");
        System.out.println("_   _   __PARAM_NRO_PAG_MOSTRAR:   :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println("_   _   __PARAM_NRO_REG_MOSTRAR:   :"+PARAM_NRO_REG_MOSTRAR);
        List<BeanPersona> lista_mostrar = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC")));
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
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" +// DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("-----------------------------MODEL_PERSONA-------------------------------");
            System.out.println("-- --cargar_grilla_paginacion_rpt_pac()--------     "+sql);
            System.out.println("-------------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
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
            while (MP_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanPersona datos = new BeanPersona();
                        datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                        datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_TIPODOC(MP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MP_RESULTADO.getString("TELEFPARTICULAR"));
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
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_PAC_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        // PDF 
        sesionDatos.setAttribute("PDF_RPT_PAC_VAR_AUX_NRO_PAG", "1");
        sesionDatos.setAttribute("PDF_RPT_PAC_VAR_AUX_NRO_REG", ""+PARAM_NRO_REG_MOSTRAR+"");
        
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
    OBSERVACION: METODO QUE UTILIZARIA MAS PARA TRAER TODOS LOS DATOS DE LA TABLA PERSONA Y NO ALGUNOS DATOS NOMAS, POR ESO DIVIDI EN OTRO METODO DONDE TRAERIA LOS DATOS MAS BASICOS QUE UTILIZARIA MAS Y ASI NO LLAMAR CONSTANTEMENTE PARA EXTRAER UNO O DOS DATOS NOMAS DE UN SELECT TAN GRANDE 
    */
//    @Override
//    public List traer_datos(String idTraer) {
//        List<BeanPersona> lista = new ArrayList<>();
//        
//        String TIPODOC, RAZON_SOCIAL, RUC, DIRECCION, EMAIL, TELFPARTICULAR, TELFMOVIL, SEXO, RELIGION, ESTADOCIVIL, GRUPOSANGUINEO, OBSERVACION, FECULTIMOMOV, FOTO, USU_MOD, TELEFPARTICULAR; // VARIABLES QUE UTILIZO PARA PODER CONTROLAR SI EL RESULTADO DE LA COLUMNA ESTA NULL ASI PARA CARGAR OTRO VALOR A LA CLASE PARA QUE NO SALTE ERROR, Y ESE NUEVO VALOR QUE LE CARGO CUANDO ES NULL VA DENTRO DE ESTA VARIABLE QUE LUEGO PASO A LA CLASE 
//        
//        try {
//            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
//                "rp.RAZON_SOCIAL, rp.RUC, rp.IDBARRIO, rp.DESC_BARRIO, rp.IDCIUDAD, rp.DESC_CIUDAD, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, \n" +
//                "rp.IDCIUDADNAC, rp.DESC_CIUDADNAC, rp.FECHANAC, rp.SEXO, rp.RELIGION, rp.ESTADOCIVIL, rp.GRUPOSANGUINEO, rp.OBSERVACION, \n" +
//                "rp.FECHAALTA, rp.FECULTIMOMOV, rp.FOTO, rp.USU_ALTA, rp.USU_MOD, rp.IDPAIS, rp.DESC_PAIS, rp.TELEFPARTICULAR \n" +
//                "FROM rh_persona rp \n" +
//                "WHERE rp.IDPERSONA = "+idTraer+" \n" +
//                "AND rp.IDPERSONA NOT IN (0,1) \n" +
//                "AND IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n"; /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
//            System.out.println("------------------------MODEL_PERSONA--------------------------");
//            System.out.println("-- ---traer_datos()--------     "+sql);
//            System.out.println("---------------------------------------------------------------");
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MP_RESULTADO = consultaBD(sql);
//            
//            while (MP_RESULTADO.next()) {
//                BeanPersona datos = new BeanPersona();
//                    datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
//                    datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
//                    datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
//                    datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
//                    if (MP_RESULTADO.getString("TIPODOC") != null) {
//                        TIPODOC = MP_RESULTADO.getString("TIPODOC");
//                    } else {
//                        TIPODOC = "";
//                    }
//                    datos.setRP_TIPODOC(TIPODOC);
//                    datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
//                    datos.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
//                    if (MP_RESULTADO.getString("RAZON_SOCIAL") != null) {
//                        RAZON_SOCIAL = MP_RESULTADO.getString("RAZON_SOCIAL");
//                    } else {
//                        RAZON_SOCIAL = "";
//                    }
//                    datos.setRP_RAZON_SOCIAL(RAZON_SOCIAL);
//                    if (MP_RESULTADO.getString("RUC") != null) {
//                        RUC = MP_RESULTADO.getString("RUC");
//                    } else {
//                        RUC = "";
//                    }
//                    datos.setRP_RUC(RUC);
//                    datos.setRP_IDBARRIO(MP_RESULTADO.getString("IDBARRIO"));
//                    datos.setRP_DESC_BARRIO(MP_RESULTADO.getString("DESC_BARRIO"));
//                    datos.setRP_IDCIUDAD(MP_RESULTADO.getString("IDCIUDAD"));
//                    datos.setRP_DESC_CIUDAD(MP_RESULTADO.getString("DESC_CIUDAD"));
//                    if (MP_RESULTADO.getString("DIRECCION") != null) {
//                        DIRECCION = MP_RESULTADO.getString("DIRECCION");
//                    } else {
//                        DIRECCION = "";
//                    }
//                    datos.setRP_DIRECCION(DIRECCION);
//                    if (MP_RESULTADO.getString("EMAIL") != null) {
//                        EMAIL = MP_RESULTADO.getString("EMAIL");
//                    } else {
//                        EMAIL = "";
//                    }
//                    datos.setRP_EMAIL(EMAIL);
//                    if (MP_RESULTADO.getString("TELFPARTICULAR") != null) {
//                        TELFPARTICULAR = MP_RESULTADO.getString("TELFPARTICULAR");
//                    } else {
//                        TELFPARTICULAR = "";
//                    }
//                    datos.setRP_TELFPARTICULAR(TELFPARTICULAR);
//                    if (MP_RESULTADO.getString("TELFMOVIL") != null) {
//                        TELFMOVIL = MP_RESULTADO.getString("TELFMOVIL");
//                    } else {
//                        TELFMOVIL = "";
//                    }
//                    datos.setRP_TELFMOVIL(TELFMOVIL);
//                    datos.setRP_IDCIUDADNAC(MP_RESULTADO.getString("IDCIUDADNAC"));
//                    datos.setRP_DESC_CIUDADNAC(MP_RESULTADO.getString("DESC_CIUDADNAC"));
//                    datos.setRP_FECHANAC(MP_RESULTADO.getString("FECHANAC"));
//                    if (MP_RESULTADO.getString("SEXO") != null) {
//                        SEXO = MP_RESULTADO.getString("SEXO");
//                    } else {
//                        SEXO = "";
//                    }
//                    datos.setRP_SEXO(SEXO);
//                    if (MP_RESULTADO.getString("RELIGION") != null) {
//                        RELIGION = MP_RESULTADO.getString("RELIGION");
//                    } else {
//                        RELIGION = "";
//                    }
//                    datos.setRP_RELIGION(RELIGION);
//                    if (MP_RESULTADO.getString("ESTADOCIVIL") != null) {
//                        ESTADOCIVIL = MP_RESULTADO.getString("ESTADOCIVIL");
//                    } else {
//                        ESTADOCIVIL = "";
//                    }
//                    datos.setRP_ESTADOCIVIL(ESTADOCIVIL);
//                    if (MP_RESULTADO.getString("GRUPOSANGUINEO") != null) {
//                        GRUPOSANGUINEO = MP_RESULTADO.getString("GRUPOSANGUINEO");
//                    } else {
//                        GRUPOSANGUINEO = "";
//                    }
//                    datos.setRP_GRUPOSANGUINEO(GRUPOSANGUINEO);
//                    if (MP_RESULTADO.getString("OBSERVACION") != null) {
//                        OBSERVACION = MP_RESULTADO.getString("OBSERVACION");
//                    } else {
//                        OBSERVACION = "";
//                    }
//                    datos.setRP_OBSERVACION(OBSERVACION);
//                    datos.setRP_FECHAALTA("FECHAALTA");
//                    if (MP_RESULTADO.getString("FECULTIMOMOV") != null) {
//                        FECULTIMOMOV = MP_RESULTADO.getString("FECULTIMOMOV");
//                    } else {
//                        FECULTIMOMOV = "";
//                    }
//                    datos.setRP_FECULTIMOMOV(FECULTIMOMOV);
//                    if (MP_RESULTADO.getString("FOTO") != null) {
//                        FOTO = MP_RESULTADO.getString("FOTO");
//                    } else {
//                        FOTO = "";
//                    }
//                    datos.setRP_FOTO(FOTO);
//                    datos.setRP_USU_ALTA(MP_RESULTADO.getString("USU_ALTA"));
//                    if (MP_RESULTADO.getString("USU_MOD") != null) {
//                        USU_MOD = MP_RESULTADO.getString("USU_MOD");
//                    } else {
//                        USU_MOD = "";
//                    }
//                    datos.setRP_USU_MOD(USU_MOD);
//                    datos.setRP_IDPAIS(MP_RESULTADO.getString("IDPAIS"));
//                    datos.setRP_DESC_PAIS(MP_RESULTADO.getString("DESC_PAIS"));
//                    if (MP_RESULTADO.getString("TELEFPARTICULAR") != null) {
//                        TELEFPARTICULAR = MP_RESULTADO.getString("TELEFPARTICULAR");
//                    } else {
//                        TELEFPARTICULAR = "";
//                    }
//                    datos.setRP_TELEFPARTICULAR(TELEFPARTICULAR);
//                lista.add(datos);
//            } // END WHILE 
//            cerrarConexiones();
//        } catch (SQLException e) {
//            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
//        }
//        return lista;
//    } // END METHOD 
    
    
    
    @Override
    public String guardar(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelUsuario metodoUsuario = new ModelUsuario();
        BeanPersona paciente = (BeanPersona) obj;
        
        // FORMATEAMOS EL VALOR DE LA VARIABLE ESTADO PARA PODER GUARDAR LA INICIAL NOMAS Y NO LA PALABRA COMPLETA 
        String ESTADO = paciente.getSU_ESTADO();
        if (ESTADO.equalsIgnoreCase("INACTIVO") || ESTADO.equalsIgnoreCase("I")) {
            ESTADO = "I";
        } else {
            ESTADO = "A";
        }
        
        try {
            int varOperacion;
            String sql;
            String IDPERSONA = paciente.getRP_IDPERSONA();
            String NOMBRE = paciente.getRP_NOMBRE();
            String APELLIDO = paciente.getRP_APELLIDO();
            String CINRO = paciente.getRP_CINRO();
            String TIPODOC = paciente.getRP_TIPODOC();
            String IDCATEGORIA_PERSONA = paciente.getRP_IDCATEGORIA_PERSONA();
            String DESC_CATEG_PERSONA = paciente.getRP_DESC_CATEG_PERSONA();
            String RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
            String RUC = paciente.getRP_RUC();
            String DIRECCION = paciente.getRP_DIRECCION();
            String EMAIL = paciente.getRP_EMAIL();
            String IDBARRIO = paciente.getRP_IDBARRIO();
//            String DESC_BARRIO = paciente.getRP_DESC_BARRIO();
            String IDCIUDAD = paciente.getRP_IDCIUDAD();
//            String DESC_CIUDAD = paciente.getRP_DESC_CIUDAD();
            String TELFPARTICULAR = paciente.getRP_TELFPARTICULAR();
            String TELFMOVIL = paciente.getRP_TELFMOVIL();
            String IDCIUDADNAC = paciente.getRP_IDCIUDADNAC();
//            String DESC_CIUDADNAC = paciente.getRP_DESC_CIUDADNAC();
            String FECHANAC = paciente.getRP_FECHANAC();
            if (FECHANAC == null || FECHANAC.isEmpty()) {
                FECHANAC = "null";
            } else {
                FECHANAC = "'"+FECHANAC+"'";
            }
            String SEXO = paciente.getRP_SEXO();
            String RELIGION = paciente.getRP_RELIGION();
            String ESTADOCIVIL = paciente.getRP_ESTADOCIVIL();
            String GRUPOSANGUINEO = paciente.getRP_GRUPOSANGUINEO();
            String OBSERVACION = paciente.getRP_OBSERVACION().replaceAll("\r\n","<br/>").replace("'","\\'");
            String FECHAALTA = paciente.getRP_FECHAALTA();
            String FECULTMOMOV = paciente.getRP_FECULTIMOMOV();
            String FOTO = paciente.getRP_FOTO();
            String USU_ALTA = paciente.getRP_USU_ALTA();
            String USU_MOD = paciente.getRP_USU_MOD();
            String IDPAIS = paciente.getRP_IDPAIS();
//            String DESC_PAIS = paciente.getRP_DESC_PAIS();
            String TELEFPARTICULAR = paciente.getRP_TELEFPARTICULAR();
            String OCUPACION = paciente.getRP_OCUPACION();
            String ANTECEDENTES = paciente.getRP_ANTECEDENTES().replaceAll("\r\n","<br/>").replace("'","\\'");
            System.out.println("___ANTECEDENTES:        :"+ANTECEDENTES);
            String EXPEDIENTE_CLINICO = paciente.getRP_EXPEDIENTE_CLINICO();
            String IDCLINICA = paciente.getRP_IDCLINICA();
            String SEGURO_MEDICO = paciente.getRP_SEGURO_MEDICO();
            String TIENE_HIJOS = paciente.getRP_TIENE_HIJOS();
            String CANT_HIJOS = paciente.getRP_CANT_HIJOS();
            String FOTO_PATH_ABS = paciente.getRP_FOTO_PATH_ABS().replace("\\","*");
            // USUARIO 
            String SU_IDUSUARIO = paciente.getSU_IDUSUARIO();
            String SU_IDPERSONA = IDPERSONA;
            String SU_USUARIO = paciente.getSU_USUARIO();
            String SU_CLAVE = paciente.getSU_CLAVE();
            String SU_ESTADO = paciente.getSU_ESTADO();
            String SU_IDPERFIL = paciente.getSU_IDPERFIL();
            String SU_DESC_PERFIL = paciente.getSU_DESC_PERFIL();
            String SU_ESTADO_MIGRAR = paciente.getSU_ESTADO_MIGRAR();
            String SU_WEB = paciente.getSU_WEB();
            String SU_EMAIL = paciente.getSU_EMAIL();
            
//            String IMAGEN = paciente.getIMAGEN();
            
            // CONTROLO POR MEDIO DEL IDPERSONA DEL PACIENTE QUE OPERACION DEBERIA DE REALIZAR, SI LA DE UPDATE O LA DE INSERT 
            if (paciente.getRP_IDPERSONA() == null || paciente.getRP_IDPERSONA().equalsIgnoreCase("null") || paciente.getRP_IDPERSONA().isEmpty() || paciente.getRP_IDPERSONA().equals("")) {
                varOperacion = 1; // INSERT 
//                IDPERSONA = cargarNewIdPersona();
                IDPERSONA = paciente.getRP_IDPERSONA_NEW();
                System.out.println("_1__MODELO____NEW_ID_PERSONA:    "+IDPERSONA);
                if (IDPERSONA == null || IDPERSONA.isEmpty() || IDPERSONA.equals("")) {
                    IDPERSONA = cargarNewIdPersona();
                    System.out.println("_1__MODELO____NEW_ID_PERSONA:    "+IDPERSONA);
                }
                SU_IDPERSONA = IDPERSONA; // COMO LE CARGO EL IDPERSONA, ENTONCES VUELVO A CARGAR LA VARIABLE QUE LE PASO AL CONSTRUCTOR DE USUARIO 
                sql = "INSERT INTO rh_persona(IDPERSONA, \n" +
                    "NOMBRE, APELLIDO, CINRO, TIPODOC, IDCATEGORIA_PERSONA, DESC_CATEG_PERSONA, \n" +
                    "RAZON_SOCIAL, RUC, DIRECCION, EMAIL, IDBARRIO, IDCIUDAD, TELFPARTICULAR, \n" +
                    "TELFMOVIL, IDCIUDADNAC, FECHANAC, SEXO, RELIGION, \n" +
                    "ESTADOCIVIL, GRUPOSANGUINEO, OBSERVACION, FECHAALTA, FECULTMOMOV, FOTO, \n" +
                    "USU_ALTA, USU_MOD, IDPAIS, TELEFPARTICULAR, OCUPACION, ANTECEDENTES, EXPEDIENTE_CLINICO, "
                        + "IDCLINICA, SEGURO_MEDICO, TIENE_HIJOS, CANT_HIJOS, FOTO_PATH_ABS)\n" +
                    "VALUES ("+IDPERSONA+", \n" +
                    "'"+NOMBRE+"', '"+APELLIDO+"', '"+CINRO+"', '"+TIPODOC+"', '"+IDCATEGORIA_PERSONA+"', '"+DESC_CATEG_PERSONA+"', \n" +
                    "'"+RAZON_SOCIAL+"', '"+RUC+"', '"+DIRECCION+"', '"+EMAIL+"', '"+IDBARRIO+"', '"+IDCIUDAD+"', '"+TELFPARTICULAR+"', \n" +
                    "'"+TELFMOVIL+"', '"+IDCIUDADNAC+"', "+FECHANAC+", '"+SEXO+"', '"+RELIGION+"', \n" +
                    "'"+ESTADOCIVIL+"', '"+GRUPOSANGUINEO+"', '"+OBSERVACION+"', '"+FECHAALTA+"', "+FECULTMOMOV+", '"+FOTO+"', \n" +
                    "'"+USU_ALTA+"', '"+USU_MOD+"', '"+IDPAIS+"', '"+TELEFPARTICULAR+"', '"+OCUPACION+"', '"+ANTECEDENTES+"', '"+EXPEDIENTE_CLINICO+"', "
                        + "'"+IDCLINICA+"', '"+SEGURO_MEDICO+"', '"+TIENE_HIJOS+"', '"+CANT_HIJOS+"', '"+FOTO_PATH_ABS+"')";
                
            } else {
                varOperacion = 2; // UPDATE 
                sql = "UPDATE rh_persona \n" +
                    "SET NOMBRE = '"+NOMBRE+"',\n" +
                        "APELLIDO = '"+APELLIDO+"', \n" +
                        "CINRO = '"+CINRO+"', \n" +
                        "RAZON_SOCIAL = '"+RAZON_SOCIAL+"', \n" +
                        "RUC = '"+RUC+"', \n" +
                        "TELFPARTICULAR = '"+TELFPARTICULAR+"', \n" +
                        "TELFMOVIL = '"+TELFMOVIL+"', \n" +
                        "DIRECCION = '"+DIRECCION+"', \n" +
                        "FECHANAC = "+FECHANAC+", \n" +
                        "SEXO = '"+SEXO+"', \n" +
                        "EMAIL = '"+EMAIL+"', \n" +
                        "OCUPACION = '"+OCUPACION+"', \n" +
                        "SEGURO_MEDICO = '"+SEGURO_MEDICO+"', \n" +
                        "ESTADOCIVIL = '"+ESTADOCIVIL+"', \n" +
                        "TIENE_HIJOS = '"+TIENE_HIJOS+"', \n" +
                        "CANT_HIJOS = '"+CANT_HIJOS+"', \n" +
                        "OBSERVACION = '"+OBSERVACION+"', \n" +
                        "IDCIUDAD = '"+IDCIUDAD+"', \n" +
                        "FOTO = '"+FOTO+"', \n" +
                        "FOTO_PATH_ABS = '"+FOTO_PATH_ABS+"', \n" +
//                        "IDCATEGORIA_PERSONA = '"+SU_IDPERFIL+"', \n" + 
//                        "DESC_CATEG_PERSONA = '"+SU_DESC_PERFIL+"', \n" +
                        "IDCLINICA = '"+IDCLINICA+"' \n" +
                    "WHERE IDPERSONA = "+IDPERSONA+" \n";
            }
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- ---insert/update("+varOperacion+")--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MP_CONEXION = devolverConex();
            MP_SENTENCIA = MP_CONEXION.createStatement();
            int cantResul = MP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                if (varOperacion == 2) {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Modificado con Exito.";
                } else {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Registrado con Exito.";
                }
                /*
                    OBSERVACION: LE COLOCO ESTE IF PORQUE SI EL USUARIO DEJA LOS CAMPOS VACIOS DE LOGIN Y CLAVE 
                    ENTONCES SOLO QUIERE MODIFICAR O CREAR UN PACIENTE/MEDICO/SECRETARIO Y EN CASO DE QUE SE CARGUEN ESTOS CAMPOS ENTONCES 
                    SI QUIERE CREARLE UN USUARIO.
                */
                if ((SU_USUARIO.isEmpty() || SU_USUARIO.trim().equals("")) == false) {
                    // LUEGO DE GUARDAR / ACTUALIZAR LA PERSONA, HAREMOS LOS MISMO PERO CON LA TABLA DE USUARIO 
                    String SU_CONFIR_EMAIL = "NO"; // VARIABLE QUE UTILIZO PARA SABER QUE EL USUARIO AUN NO CONFIRMO SU EMAIL A TRAVES DEL INGRESO AL SISTEMA (PORQUE EN SU EMIAL SE LE PASA LA CONTRASEÑA) 
                    metodoUsuario.guardar(new BeanUsuario(SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
                }
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    } // END METHOD 
    

    @Override
    public String eliminar(Object obj) {
        return null;
    }
    
    public String guardar_antecedentes(Object obj) {
        String tipoRespuesta="2"; // 1 = Exito / 2 = Error 
        ModelUsuario metodoUsuario = new ModelUsuario();
        BeanPersona paciente = (BeanPersona) obj;
        
        // FORMATEAMOS EL VALOR DE LA VARIABLE ESTADO PARA PODER GUARDAR LA INICIAL NOMAS Y NO LA PALABRA COMPLETA 
        String ESTADO = paciente.getSU_ESTADO();
        if (ESTADO == null || ESTADO.equalsIgnoreCase("ACTIVO") || ESTADO.equalsIgnoreCase("A")) {
            ESTADO = "A";
        } else {
            ESTADO = "I";
        }
        
        try {
            int varOperacion;
            String sql;
            String IDPERSONA = paciente.getRP_IDPERSONA();
            String NOMBRE = paciente.getRP_NOMBRE();
            String APELLIDO = paciente.getRP_APELLIDO();
            String CINRO = paciente.getRP_CINRO();
            String TIPODOC = paciente.getRP_TIPODOC();
            String IDCATEGORIA_PERSONA = paciente.getRP_IDCATEGORIA_PERSONA();
            String DESC_CATEG_PERSONA = paciente.getRP_DESC_CATEG_PERSONA();
            String RAZON_SOCIAL = paciente.getRP_RAZON_SOCIAL();
            String RUC = paciente.getRP_RUC();
            String DIRECCION = paciente.getRP_DIRECCION();
            String EMAIL = paciente.getRP_EMAIL();
            String IDBARRIO = paciente.getRP_IDBARRIO();
//            String DESC_BARRIO = paciente.getRP_DESC_BARRIO();
            String IDCIUDAD = paciente.getRP_IDCIUDAD();
//            String DESC_CIUDAD = paciente.getRP_DESC_CIUDAD();
            String TELFPARTICULAR = paciente.getRP_TELFPARTICULAR();
            String TELFMOVIL = paciente.getRP_TELFMOVIL();
            String IDCIUDADNAC = paciente.getRP_IDCIUDADNAC();
//            String DESC_CIUDADNAC = paciente.getRP_DESC_CIUDADNAC();
            String FECHANAC = paciente.getRP_FECHANAC();
            String SEXO = paciente.getRP_SEXO();
            String RELIGION = paciente.getRP_RELIGION();
            String ESTADOCIVIL = paciente.getRP_ESTADOCIVIL();
            String GRUPOSANGUINEO = paciente.getRP_GRUPOSANGUINEO();
            String OBSERVACION = paciente.getRP_OBSERVACION();
            String FECHAALTA = paciente.getRP_FECHAALTA();
            String FECULTMOMOV = paciente.getRP_FECULTIMOMOV();
            String FOTO = paciente.getRP_FOTO();
            String USU_ALTA = paciente.getRP_USU_ALTA();
            String USU_MOD = paciente.getRP_USU_MOD();
            String IDPAIS = paciente.getRP_IDPAIS();
//            String DESC_PAIS = paciente.getRP_DESC_PAIS();
            String TELEFPARTICULAR = paciente.getRP_TELEFPARTICULAR();
            String OCUPACION = paciente.getRP_OCUPACION();
            String ANTECEDENTES = paciente.getRP_ANTECEDENTES().replaceAll("\r\n","<br/>").replace("'","\\'");
//            String ANTECEDENTES = paciente.getRP_ANTECEDENTES();
            System.out.println("___ANTECEDENTES:        :"+ANTECEDENTES);
            String EXPEDIENTE_CLINICO = paciente.getRP_EXPEDIENTE_CLINICO();
            String IDCLINICA = paciente.getRP_IDCLINICA();
            String SEGURO_MEDICO = paciente.getRP_SEGURO_MEDICO();
//            // USUARIO 
//            String SU_IDUSUARIO = paciente.getSU_IDUSUARIO();
//            String SU_IDPERSONA = IDPERSONA;
//            String SU_USUARIO = paciente.getSU_USUARIO();
//            String SU_CLAVE = paciente.getSU_CLAVE();
//            String SU_ESTADO = paciente.getSU_ESTADO();
//            String SU_IDPERFIL = paciente.getSU_IDPERFIL();
//            String SU_DESC_PERFIL = paciente.getSU_DESC_PERFIL();
//            String SU_ESTADO_MIGRAR = paciente.getSU_ESTADO_MIGRAR();
//            String SU_WEB = paciente.getSU_WEB();
//            String SU_EMAIL = paciente.getSU_EMAIL();
            
                varOperacion = 2; // UPDATE 
                sql = "UPDATE rh_persona \n" +
                    "SET ANTECEDENTES = '"+ANTECEDENTES+"' \n" +
                    "WHERE IDPERSONA = "+IDPERSONA+" \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- ---/update("+varOperacion+")--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            MP_CONEXION = devolverConex();
            MP_SENTENCIA = MP_CONEXION.createStatement();
            int cantResul = MP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                if (varOperacion == 2) {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Modificado con Exito.";
                } else {
                    tipoRespuesta = "1";
//                    respuesta = "Se ha Registrado con Exito.";
                }
//                /*
//                    OBSERVACION: LE COLOCO ESTE IF PORQUE SI EL USUARIO DEJA LOS CAMPOS VACIOS DE LOGIN Y CLAVE 
//                    ENTONCES SOLO QUIERE MODIFICAR O CREAR UN PACIENTE/MEDICO/SECRETARIO Y EN CASO DE QUE SE CARGUEN ESTOS CAMPOS ENTONCES 
//                    SI QUIERE CREARLE UN USUARIO.
//                */
//                if ((SU_USUARIO.isEmpty() || SU_USUARIO.trim().equals("")) == false) {
//                    // LUEGO DE GUARDAR / ACTUALIZAR LA PERSONA, HAREMOS LOS MISMO PERO CON LA TABLA DE USUARIO 
//                    String SU_CONFIR_EMAIL = "NO"; // VARIABLE QUE UTILIZO PARA SABER QUE EL USUARIO AUN NO CONFIRMO SU EMAIL A TRAVES DEL INGRESO AL SISTEMA (PORQUE EN SU EMIAL SE LE PASA LA CONTRASEÑA) 
//                    metodoUsuario.guardar(new BeanUsuario(SU_IDUSUARIO, SU_IDPERSONA, SU_USUARIO, SU_CLAVE, SU_ESTADO, SU_IDPERFIL, SU_DESC_PERFIL, SU_ESTADO_MIGRAR, SU_WEB, SU_EMAIL, SU_CONFIR_EMAIL));
//                }
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".-----------------------------------------");
            System.out.println(".   ___TIPO_DE_RESPUESTA:  :"+tipoRespuesta);
            System.out.println(".-----------------------------------------");
            System.out.println(".");
            System.out.println(".");
            System.out.println(".");
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    } // END METHOD 
    
    
    
    
    
    // METODO PARA LA PAGINACION Y FILTRO DE LA TABLA DE PERSONA POR EL IDCATEGORIA_PERSONA DE PACIENTE 
    public List filtrar(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FILTRO) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
        List<BeanPersona> listaFiltro = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_PAC_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_PAC_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_PAC_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_PAC_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_PAC_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "COALESCE(rp.FOTO,'') AS FOTO, COALESCE(rp.FOTO_PATH_ABS,'') AS FOTO_PATH_ABS, rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --filtrar_paginacion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
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
            while (MP_RESULTADO.next()) {
                
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanPersona datos = new BeanPersona();
                        datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                        datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_TIPODOC(MP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MP_RESULTADO.getString("TELEFPARTICULAR"));
                        datos.setRP_FOTO(MP_RESULTADO.getString("FOTO"));
                        datos.setRP_FOTO_PATH_ABS(MP_RESULTADO.getString("FOTO_PATH_ABS"));
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
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_PAC_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS 
        sesionDatos.setAttribute("PAG_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_PAC_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_PAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    
    // METODO PARA LA PAGINACION Y FILTRO DE LA TABLA DE PERSONA POR EL IDCATEGORIA_PERSONA DE PACIENTE 
    public List filtrar_rpt_pac(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FILTRO) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
//        PARAM_CBX_MOSTRAR = "1";
        System.out.println("___     ___________filtrar_rpt_pac_paginacion()___________     ___");
        List<BeanPersona> listaFiltro = new ArrayList<>();
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
        if (sesionDatos.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_RPT_PAC_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_RPT_PAC_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(rp.IDPERSONA) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_persona rp \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE"*/
                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 0 Y 1 SON (NO/DEFINIDO) Y ROOT  */
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY rp.NOMBRE ASC, rp.APELLIDO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --filtrar_rpt_pac_paginacion()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
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
            while (MP_RESULTADO.next()) {
                
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanPersona datos = new BeanPersona();
                        datos.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                        datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                        datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                        datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                        datos.setRP_TIPODOC(MP_RESULTADO.getString("TIPODOC"));
                        datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                        datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                        datos.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                        datos.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                        datos.setRP_RUC(MP_RESULTADO.getString("RUC"));
                        datos.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                        datos.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                        datos.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                        datos.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                        datos.setRP_TELEFPARTICULAR(MP_RESULTADO.getString("TELEFPARTICULAR"));
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
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_RPT_PAC_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS 
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_RPT_PAC_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        // PDF 
        sesionDatos.setAttribute("PDF_RPT_PAC_VAR_AUX_NRO_PAG", NRO_PAG_ACTUAL_MOSTRAR);
        sesionDatos.setAttribute("PDF_RPT_PAC_VAR_AUX_NRO_REG", ""+PARAM_CBX_MOSTRAR+"");
        
        return listaFiltro;
    }
    
    
    
    
    /*
        METODO QUE UTILIZO PARA TRAER ALGUNOS DATOS QUE UTILIZARE MAS FRECUENTEMENTE Y PARA NO REALIZAR UN SELECT 
        GRANDE A CADA MOMENTO LLAMARE A ESTE METODO QUE SOLO EXTRAERA ALGUNOS DATOS DE LA TABLA PERSONA Y SI 
        QUISIESE DATOS MAS EXTENSOS DE LA PERSONA ENTONCES LLAMARIA AL METODO DE "traer_datos()" 
    */
    public BeanPersona datosBasicosPersona(BeanPersona paramBeanPer, String paramId) {
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "COALESCE(rp.RAZON_SOCIAL, '') AS RAZON_SOCIAL, COALESCE(rp.RUC,'') AS RUC, COALESCE(rp.DIRECCION, '') AS DIRECCION, \n" +
                "COALESCE(rp.EMAIL) AS EMAIL, COALESCE(rp.TELFPARTICULAR) AS TELFPARTICULAR, COALESCE(rp.TELFMOVIL,'') AS TELFMOVIL, \n" +
                "COALESCE(rp.TELEFPARTICULAR,'') AS TELEFPARTICULAR, COALESCE(rp.IDCIUDAD,'') AS IDCIUDAD, COALESCE(rci.DESC_CIUDAD,'') AS DESC_CIUDAD, \n" +
                "rp.IDCLINICA, COALESCE(DATE_FORMAT(rp.FECHANAC, '%d/%m/%Y'),'') AS FECHANAC, COALESCE(rp.OCUPACION) AS OCUPACION, COALESCE(rp.SEXO,'N') AS SEXO \n" +
                "FROM rh_persona rp \n" +
                "LEFT JOIN rh_ciudad rci ON rp.IDCIUDAD = rci.IDCIUDAD \n" +
                "WHERE rp.IDPERSONA = "+paramId+" ";
//            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
//                "COALESCE(rp.RAZON_SOCIAL, '') AS RAZON_SOCIAL, COALESCE(rp.RUC,'') AS RUC, COALESCE(rp.DIRECCION, '') AS DIRECCION, "
//                    + "COALESCE(rp.EMAIL) AS EMAIL, COALESCE(rp.TELFPARTICULAR) AS TELFPARTICULAR, COALESCE(rp.TELFMOVIL,'') AS TELFMOVIL, "
//                    + "COALESCE(rp.TELEFPARTICULAR,'') AS TELEFPARTICULAR, rp.IDCIUDAD, rp.IDCLINICA, \n" +
//                "COALESCE(DATE_FORMAT(rp.FECHANAC, '%Y-%m-%d'),'') AS FECHANAC, COALESCE(rp.OCUPACION) AS OCUPACION \n" +
////                "COALESCE(DATE_FORMAT(FECHANAC, '%d-%m-%Y'),'') AS FECHANAC \n" +
//                "FROM rh_persona rp \n" +
//                "WHERE rp.IDPERSONA = "+paramId+" ";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --datosBasicosPersona()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
//                BeanPersona datos = new BeanPersona();
                paramBeanPer.setRP_IDPERSONA(MP_RESULTADO.getString("IDPERSONA"));
                paramBeanPer.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                paramBeanPer.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                paramBeanPer.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
//                paramBeanPer.setRP_TIPODOC(MP_RESULTADO.getString("TIPODOC"));
//                paramBeanPer.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
//                paramBeanPer.setRP_DESC_CATEG_PERSONA(MP_RESULTADO.getString("DESC_CATEG_PERSONA"));
                paramBeanPer.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                paramBeanPer.setRP_RUC(MP_RESULTADO.getString("RUC"));
                paramBeanPer.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                paramBeanPer.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                paramBeanPer.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                paramBeanPer.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                paramBeanPer.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                paramBeanPer.setRP_IDCIUDAD(MP_RESULTADO.getString("IDCIUDAD"));
                paramBeanPer.setRP_DESC_CIUDAD(MP_RESULTADO.getString("DESC_CIUDAD"));
                paramBeanPer.setRP_FECHANAC(MP_RESULTADO.getString("FECHANAC"));
                paramBeanPer.setRP_OCUPACION(MP_RESULTADO.getString("OCUPACION")); // OCUPACION = PROFESION 
//                paramBeanPer.setRP_DESC_CIUDAD(MP_RESULTADO.getString("DESC_CIUDAD"));
                paramBeanPer.setRP_SEXO(MP_RESULTADO.getString("SEXO"));
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramBeanPer;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA TRAER LOS DATOS DE LA PERSONA PARA EDITAR O AÑADIR UN PACIENTE 
    */
    public List traerDatosPersona(String paramIdCliente) {
        List<BeanPersona> listaDatos = new ArrayList<>();
        try {
            String sql = "SELECT rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, COALESCE(rp.TELFPARTICULAR,'') AS TELFPARTICULAR, COALESCE(rp.TELFMOVIL,'') AS TELFMOVIL, \n" +
                "COALESCE(rp.DIRECCION,'') AS DIRECCION, rp.EMAIL, COALESCE(rp.ESTADOCIVIL,'ND') AS ESTADOCIVIL, \n" +
                "COALESCE(DATE_FORMAT(rp.FECHANAC, '%Y-%m-%d'),'') AS FECHANAC, COALESCE(rp.SEXO,'N') AS SEXO, \n" +
                "COALESCE(su.IDUSUARIO,'')AS IDUSUARIO, COALESCE(su.USUARIO,'') AS USUARIO, COALESCE(su.CLAVE,'') AS CLAVE, \n" +
                "COALESCE((CASE WHEN(su.IDPERFIL) IS NULL THEN rp.IDCATEGORIA_PERSONA ELSE su.IDPERFIL END),'') AS IDPERFIL, COALESCE(perf.PERFIL,'') AS DESC_PERFIL, \n" +
                "COALESCE(su.ESTADO,'') AS ESTADO, rp.IDCLINICA, COALESCE(rp.OCUPACION,'')AS OCUPACION, \n" +
                "COALESCE(rp.SEGURO_MEDICO,'') AS SEGURO_MEDICO, COALESCE(rp.ANTECEDENTES,'') AS ANTECEDENTES \n" +
                " , rp.TIENE_HIJOS, rp.CANT_HIJOS, COALESCE(rp.OBSERVACION,'') AS OBSERVACION, \n" +
                "COALESCE(rp.FOTO,'') AS FOTO, COALESCE(rp.FOTO_PATH_ABS,'') AS FOTO_PATH_ABS, \n" +
                " COALESCE(rp.IDCIUDAD,'0') AS IDCIUDAD, COALESCE(rci.DESC_CIUDAD,'') AS DESC_CIUDAD \n" +
                "FROM rh_persona rp \n" +
                "LEFT JOIN sys_usuario su ON su.IDPERSONA = rp.IDPERSONA \n" +
                "LEFT JOIN sys_perfil perf ON (CASE WHEN(su.IDPERFIL) IS NULL THEN rp.IDCATEGORIA_PERSONA ELSE su.IDPERFIL END) = perf.IDPERFIL \n" +
                "LEFT JOIN rh_ciudad rci ON rp.IDCIUDAD = rci.IDCIUDAD \n" +
                "WHERE rp.IDPERSONA = '"+paramIdCliente+"' \n" +
                "AND rp.IDPERSONA NOT IN (1) \n"; // IDPERSONA 1 = ROOT 
            
            /*
            SELECT COMO TENIA ANTES DE EDITAR LOS CAMPOS PARA ADAPTAR A PACIENTES_NUTRI (23/02/2023) 
            */
//            String sql = "SELECT rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, rp.RAZON_SOCIAL, rp.RUC, rp.TELFMOVIL, rp.DIRECCION, rp.EMAIL, COALESCE(DATE_FORMAT(rp.FECHANAC, '%Y-%m-%d'),'') AS FECHANAC, rp.SEXO, su.IDUSUARIO, su.USUARIO, su.CLAVE, su.IDPERFIL, perf.PERFIL AS DESC_PERFIL, su.ESTADO, rp.IDCLINICA, rp.OCUPACION, rp.SEGURO_MEDICO, rp.ANTECEDENTES \n" +
//                "FROM rh_persona rp \n" +
//                "LEFT JOIN sys_usuario su ON su.IDPERSONA = rp.IDPERSONA \n" +
//                "LEFT JOIN sys_perfil perf ON su.IDPERFIL = perf.IDPERFIL \n" +
//                "WHERE rp.IDPERSONA = '"+paramIdCliente+"' \n" +
//                "AND rp.IDPERSONA NOT IN (1) \n"; // IDPERSONA 1 = ROOT 
            
//            String sql = "SELECT rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.RAZON_SOCIAL, rp.RUC, rp.TELFMOVIL, rp.DIRECCION, rp.EMAIL, DATE_FORMAT(rp.FECHANAC, '%Y-%m-%d') AS FECHANAC, rp.SEXO, su.IDUSUARIO, su.USUARIO, su.CLAVE, su.IDPERFIL, su.ESTADO, rp.IDCLINICA, rp.OCUPACION, rp.SEGURO_MEDICO, rp.ANTECEDENTES \n" +
//                "FROM rh_persona rp \n" +
//                "LEFT JOIN sys_usuario su ON su.IDPERSONA = rp.IDPERSONA \n" +
//                "WHERE rp.IDPERSONA = '"+paramIdCliente+"' \n" +
//                "AND rp.IDPERSONA NOT IN (1) \n"; // IDPERSONA 1 = ROOT 
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --traerDatosPersona()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                BeanPersona datos = new BeanPersona();
                    datos.setRP_IDPERSONA(paramIdCliente);
                    datos.setRP_NOMBRE(MP_RESULTADO.getString("NOMBRE"));
                    datos.setRP_APELLIDO(MP_RESULTADO.getString("APELLIDO"));
                    datos.setRP_CINRO(MP_RESULTADO.getString("CINRO"));
                    datos.setRP_IDCLINICA(MP_RESULTADO.getString("IDCLINICA"));
                    datos.setRP_IDCATEGORIA_PERSONA(MP_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                    datos.setRP_TELFPARTICULAR(MP_RESULTADO.getString("TELFPARTICULAR"));
                    datos.setRP_TELFMOVIL(MP_RESULTADO.getString("TELFMOVIL"));
                    datos.setRP_RAZON_SOCIAL(MP_RESULTADO.getString("RAZON_SOCIAL"));
                    datos.setRP_RUC(MP_RESULTADO.getString("RUC"));
                    datos.setRP_DIRECCION(MP_RESULTADO.getString("DIRECCION"));
                    datos.setRP_EMAIL(MP_RESULTADO.getString("EMAIL"));
                    datos.setSU_IDUSUARIO(MP_RESULTADO.getString("IDUSUARIO"));
                    datos.setSU_USUARIO(MP_RESULTADO.getString("USUARIO"));
                    datos.setSU_CLAVE(MP_RESULTADO.getString("CLAVE"));
                    datos.setSU_ESTADO(MP_RESULTADO.getString("ESTADO"));
                    datos.setSU_IDPERFIL(MP_RESULTADO.getString("IDPERFIL"));
                    datos.setSU_DESC_PERFIL(MP_RESULTADO.getString("DESC_PERFIL"));
                    datos.setRP_FECHANAC(MP_RESULTADO.getString("FECHANAC"));
                    datos.setRP_SEXO(MP_RESULTADO.getString("SEXO"));
                    datos.setRP_OCUPACION(MP_RESULTADO.getString("OCUPACION")); // ESTE CAMPO LO UTILIZO COMO PROFESION PARA LA PAG DE PACIENTE NUTRI 
                    datos.setRP_SEGURO_MEDICO(MP_RESULTADO.getString("SEGURO_MEDICO"));
                    datos.setRP_ANTECEDENTES(MP_RESULTADO.getString("ANTECEDENTES")); // ANTECEDENTES FAMILIARES 
                    datos.setRP_TIENE_HIJOS(MP_RESULTADO.getString("TIENE_HIJOS"));
                    datos.setRP_CANT_HIJOS(MP_RESULTADO.getString("CANT_HIJOS"));
                    datos.setRP_OBSERVACION(MP_RESULTADO.getString("OBSERVACION").replaceAll("<br/>","\r\n"));
                    datos.setRP_ESTADOCIVIL(MP_RESULTADO.getString("ESTADOCIVIL"));
                    datos.setRP_IDCIUDAD(MP_RESULTADO.getString("IDCIUDAD"));
                    datos.setRP_DESC_CIUDAD(MP_RESULTADO.getString("DESC_CIUDAD"));
                    datos.setRP_FOTO(MP_RESULTADO.getString("FOTO").replace("*","\\"));
                    datos.setRP_FOTO_PATH_ABS(MP_RESULTADO.getString("FOTO_PATH_ABS").replace("*","\\"));
                listaDatos.add(datos);
            } // end while 
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return listaDatos;
    } // END METHOD 
    
    
    
    /*
        METODO PARA CONTROLAR QUE EL PACIENTE QUE SE QUIERE INSERTAR NO SE ENCUENTRE CREADO YA 
    */
    public boolean ctrlExistePersona(String PARAM_CINRO, String PARAM_IDCLINICA, String PARAM_IDPERSONA) {
        boolean valor = true; // TRUE = SE CUMPLE LA VALIDACION / FALSE = NO SE CUMPLE CON LA VALIDACION 
        
        String WHERE_NOT_IDPERSONA;
        if (PARAM_IDPERSONA == null || PARAM_IDPERSONA.equalsIgnoreCase("null") || PARAM_IDPERSONA.isEmpty()) {
            WHERE_NOT_IDPERSONA = "";
        } else {
            WHERE_NOT_IDPERSONA = "AND rp.IDPERSONA NOT IN ("+PARAM_IDPERSONA+") \n";
        }
        
        try {
            String sql = "SELECT rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TELFMOVIL, rp.DIRECCION, rp.EMAIL, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.CINRO = "+PARAM_CINRO+" \n" + 
                "AND rp.IDCLINICA = "+PARAM_IDCLINICA+" \n" + 
                ""+WHERE_NOT_IDPERSONA+"";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --ctrlExistePersona()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            if(MP_RESULTADO.next() == true) {
                valor = true; 
            } else {
                valor = false; // COMO EL SELECT NO TIENE RESULTADO, ENTONCES EL PACIENTE NO EXISTE 
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    /*
        METODO PARA CONTROLAR QUE EL PACIENTE QUE SE QUIERE INSERTAR O EDITAR NO TENGA EL MISMO 
        NOMBRE DE USUARIO QUE OTRO YA EXISTENTE, PARA EVITAR QUE DOS CLIENTES TENGAN EL MISMO NOMBRE DE USUARIO 
    */
    public boolean ctrlExisteUsuCli(String PARAM_USUARIO, String PARAM_IDPERSONACLIENTE) {
        boolean valor = true; // TRUE = SE CUMPLE LA VALIDACION / FALSE = NO SE CUMPLE CON LA VALIDACION 
        
        /*
        OBSERVACION: 
            EN CASO DE QUE EL PARAMETRO DE USUARIO VENGA VACIO, ENTONCES EL USUARIO NO ESTA TRATANDO DE 
            CREARLE UN USUARIO, SINO DE AÑADIR/MODIFICAR UN PACIENTE Y ENTONCES NO HAY NECESIDAD DE ACTIVAR LA VALIDACION Y DE CONTROLAR 
        */
        if(PARAM_USUARIO.isEmpty() || PARAM_USUARIO.trim().equals("")) {
            valor = false; // le devuelvo como false ya que se encuentra vacio y no controlo asi 
        } else {
            String whereIdPersona;
            if (PARAM_IDPERSONACLIENTE.equals("null") || PARAM_IDPERSONACLIENTE==null || PARAM_IDPERSONACLIENTE.isEmpty()) {
                whereIdPersona = "";
            } else {
                whereIdPersona = "AND IDPERSONA != '"+PARAM_IDPERSONACLIENTE+"'";
            }

            try {
                String sql = "SELECT IDUSUARIO, IDPERSONA, USUARIO, CLAVE, ESTADO, IDPERFIL \n" +
                    "FROM sys_usuario \n" +
                    "WHERE USUARIO = '"+PARAM_USUARIO+"' \n" + 
                    ""+whereIdPersona+" \n";
                System.out.println("------------------------MODEL_PERSONA--------------------------");
                System.out.println("-- --ctrlExisteUsuCli()--------     "+sql);
                System.out.println("---------------------------------------------------------------");

                // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
                MP_RESULTADO = consultaBD(sql);

                if(MP_RESULTADO.next() == true) {
                    valor = true; 
                } else {
                    valor = false; // COMO EL SELECT NO TIENE RESULTADO
                }
                cerrarConexiones();
            } catch (SQLException e) {
                Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return valor;
    }
    
    
    /*
        METODO QUE UTILIZO PARA PODER RECUPERAR UN NUEVO IDPERSONA PARA EL PACIENTE OBTENIENDO EL UTLIMO IDPERSONA Y SUMARLE UNO PARA UTILIZARLO COMO CLAVE 
    */
    public String cargarNewIdPersona() {
        String paramId = "";
        try {
            String sql = "SELECT (MAX(COALESCE(IDPERSONA))+1) AS IDPERSONA FROM rh_persona ";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --cargarNewIdPersona()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                paramId = MP_RESULTADO.getString("IDPERSONA");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramId;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LOS CLIENTES, UTILIZO MAS PARA LOS BUSCADORES 
    */
    public Map<String, String> cargarComboClientes(String PARAM_IDCLIENTE, HttpSession PARAM_SESION) { // PARAM_IDCLIENTE : PARAMETRO QUE UTILIZO PARA NO FILTRAR POR EL PACIENTE OCASIONAL PASANDOLE SU ID, EN ALGUNAS PAGINAS COMO EMPEÑO Y PASANDOLE VACIO PARA MOSTRAR COMO EN FACTURA 
        Map<String, String> mapaList = new LinkedHashMap<>();
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT DISTINCT(rp.IDPERSONA), rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.IDCLINICA, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "ccc.IDCUENTACLIENTE, ccc.IDAGENDAMIENTO, ccc.ITEM_AGEN, oa.IDCLINICA, oa.IDMEDICO, oa.IDESPECIALIDAD  \n" +
                //"-- , (CASE WHEN (ccc.IDCUENTACLIENTE IS NULL && rp.IDCATEGORIA_PERSONA != 4) THEN '' ELSE rp.IDCATEGORIA_PERSONA END) AS IDCATEG_PERSONA_PAC \n" + // SI SE QUIERE UTILIZAR ESTE CASE SE DEBERIA DE CAMBIAR EL 4 POR LA VARIABLE GLOBAL O POR EL NUMERO DE LA CATEGORIA PERSONA DEL PACIENTE / CLIENTE 
                "FROM rh_persona rp \n" +
                "LEFT JOIN cc_cuenta_cliente ccc ON rp.IDPERSONA = ccc.IDPERSONA AND ccc.ESTADO = 'A' \n" +
                "LEFT JOIN ope_agendamiento oa ON ccc.IDAGENDAMIENTO = oa.IDAGENDAMIENTO \n" +
                "WHERE (rp.IDCLINICA = "+VAR_ID_CLINICA+" || oa.IDCLINICA = "+VAR_ID_CLINICA+") \n" +
                "AND rp.IDPERSONA NOT IN ("+PARAM_IDCLIENTE+"1) \n" + // IDPERSONA: 1 ES ROOT 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
                //"-- AND (CASE WHEN (ccc.IDCUENTACLIENTE IS NULL && rp.IDCATEGORIA_PERSONA != 4) THEN '' ELSE rp.IDCATEGORIA_PERSONA END) != ''  \n" + // SI SE QUIERE UTILIZAR ESTE CASE SE DEBERIA DE CAMBIAR EL 4 POR LA VARIABLE GLOBAL O POR EL NUMERO DE LA CATEGORIA PERSONA DEL PACIENTE / CLIENTE 
                "ORDER BY rp.CINRO ASC, rp.APELLIDO ASC, rp.NOMBRE ASC \n";
            
//            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
//                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR \n" +
//                "FROM rh_persona rp \n" +
//                "WHERE rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" + /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
//                "AND rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND rp.IDPERSONA NOT IN ("+PARAM_IDCLIENTE+"1) \n" + /* IDPERSONA 1 ES ROOT */
////                "AND rp.IDPERSONA NOT IN (0,1) \n" + /* IDPERSONA 1 ES ROOT */
//                "ORDER BY rp.CINRO ASC, rp.APELLIDO ASC, rp.NOMBRE ASC \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --cargarComboClientes()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                String idPersona = MP_RESULTADO.getString("IDPERSONA");
                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("NOMBRE")+" "+MP_RESULTADO.getString("APELLIDO");
//                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("APELLIDO")+" "+MP_RESULTADO.getString("NOMBRE");
                mapaList.put(idPersona, cbxCLIENTE);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LOS CLIENTES, UTILIZO MAS PARA LOS BUSCADORES 
    */
    public Map<String, String> traerComboClientes(String PARAM_IDCLIENTE) {
        Map<String, String> mapaList = new LinkedHashMap<>();
        try {
            String whereParamCliente = ""; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL PACIENTE QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE PACIENTE PARA QUE NO SE REPITA EN EL COMBO 
            // CONTROLO SI ES QUE EL IDCLIENTE QUE LE PASO POR EL PARAMETRO ESTA CARGADO, SI ESTA CARGADO ENTONCES ESE IDCLIENTE ES EL PRIMERO QUE DEBE ESTAR EN LA LISTA DEL COMBO 
            if (!(PARAM_IDCLIENTE.equals("")) || !(PARAM_IDCLIENTE.isEmpty())) {
                List<BeanPersona> listCliente = new ArrayList<>();
                listCliente = traerDatosPersona(PARAM_IDCLIENTE);
                Iterator<BeanPersona> iterParamClie = listCliente.iterator();
                BeanPersona datosCliente = new BeanPersona();
                String CLIENTE_NAME = "";
                while(iterParamClie.hasNext()) {
                    datosCliente = iterParamClie.next();
                    CLIENTE_NAME = datosCliente.getRP_CINRO()+" - "+datosCliente.getRP_APELLIDO()+" "+datosCliente.getRP_NOMBRE();
                }
                mapaList.put(PARAM_IDCLIENTE, CLIENTE_NAME);
                whereParamCliente = "AND rp.IDPERSONA NOT IN ('"+PARAM_IDCLIENTE+"')";
            }
            
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +  /* IDCATEGORIA_PERSONA = 4 SON LAS PERSONAS "PACIENTE" */
                "AND rp.IDPERSONA NOT IN (0,1) \n" +  /* IDPERSONA 1 = ROOT  */
                ""+whereParamCliente+" \n" +
                "ORDER BY rp.CINRO ASC, rp.APELLIDO ASC, rp.NOMBRE ASC \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --traerComboClientes()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                String idPersona = MP_RESULTADO.getString("IDPERSONA");
                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("NOMBRE")+" "+MP_RESULTADO.getString("APELLIDO");
//                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("APELLIDO")+" "+MP_RESULTADO.getString("NOMBRE");
                mapaList.put(idPersona, cbxCLIENTE);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LOS CLIENTES, UTILIZO MAS PARA LOS BUSCADORES 
        TRAIGO A LOS CLIENTES QUE PERTENEZCAN A LA MISMA CLINICA Y QUE TENGAN FACTURA A LA CLINICA AUN SI NO SON DE LA CLINICA 
    */
    public Map<String, String> traerComboClientesConFact(HttpSession PARAM_SESION, String PARAM_IDCLIENTE) {
        Map<String, String> mapaList = new LinkedHashMap<>();
        try {
            String whereParamCliente = ""; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL PACIENTE QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE PACIENTE PARA QUE NO SE REPITA EN EL COMBO 
            // CONTROLO SI ES QUE EL IDCLIENTE QUE LE PASO POR EL PARAMETRO ESTA CARGADO, SI ESTA CARGADO ENTONCES ESE IDCLIENTE ES EL PRIMERO QUE DEBE ESTAR EN LA LISTA DEL COMBO 
            if (!(PARAM_IDCLIENTE.equals("")) || !(PARAM_IDCLIENTE.isEmpty())) {
                List<BeanPersona> listCliente = new ArrayList<>();
                listCliente = traerDatosPersona(PARAM_IDCLIENTE);
                Iterator<BeanPersona> iterParamClie = listCliente.iterator();
                BeanPersona datosCliente = new BeanPersona();
                String CLIENTE_NAME = "";
                while(iterParamClie.hasNext()) {
                    datosCliente = iterParamClie.next();
                    CLIENTE_NAME = datosCliente.getRP_CINRO()+" - "+datosCliente.getRP_NOMBRE()+" "+datosCliente.getRP_APELLIDO();
//                    CLIENTE_NAME = datosCliente.getRP_CINRO()+" - "+datosCliente.getRP_APELLIDO()+" "+datosCliente.getRP_NOMBRE();
                }
                mapaList.put(PARAM_IDCLIENTE, CLIENTE_NAME);
                whereParamCliente = "AND rp.IDPERSONA NOT IN ('"+PARAM_IDCLIENTE+"')";
            }
            
            VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
            
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR \n" +
                "FROM rh_persona rp \n" +
                "LEFT JOIN ope_factura fact ON fact.IDCLIENTE = rp.IDPERSONA AND fact.ESTADO = 'C' \n" +
                "WHERE (rp.IDCLINICA = "+VAR_ID_CLINICA+" || fact.IDCLINICA = "+VAR_ID_CLINICA+") \n" +
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
                "AND rp.IDPERSONA NOT IN (0,1) \n" +  /* IDPERSONA 1 = ROOT */
                ""+whereParamCliente+" \n" +
                "ORDER BY rp.CINRO ASC, rp.APELLIDO ASC, rp.NOMBRE ASC \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --traerComboClientesConFact()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                String idPersona = MP_RESULTADO.getString("IDPERSONA");
                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("NOMBRE")+" "+MP_RESULTADO.getString("APELLIDO");
//                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("APELLIDO")+" "+MP_RESULTADO.getString("NOMBRE");
                mapaList.put(idPersona, cbxCLIENTE);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 
    
    
    
    // METODO QUE UTILIZO PARA GENERAR UN NUMERO RANDOM (EJEMPLO PARA EL CI CUANDO SE AÑADE UN PACIENTE)  
    public String generar_nro_random() {
        String valor;
        
        Random nr = new Random();
        int max_nro = 1000000;
        valor = String.valueOf(nr.nextInt(max_nro));
        System.out.println("__VALOR_RANDOM:   :"+valor);
        
        // VALIDACION PARA CONTROLAR QUE EL NUMERO RANDOM QUE NO ESTE YA GUARDADO EN LA BASE  
        if (existeCIRandom(valor) == true) {
            generar_nro_random();
        }
        return valor;
    } // END METHOD 
    
    
    // METODO QUE UTILIZO PARA CONTROLAR QUE EL NUMERO RANDOM GENERADO NO SEA EXISTENTE COMO NRO DE CI DE ALGUN OTRO PACIENTE Y ASI EVITAR TENER DOS PACIENTES CON EL MISMO NRO DE CI 
    public boolean existeCIRandom(String PARAM_NROCI) {
        boolean valor = false; // true : existe en base  /  false : no existe otro 
        try {
            String sql = "SELECT '*' FROM rh_persona WHERE CINRO = '"+PARAM_NROCI+"' ";
            System.out.println("-----------------MODEL_PERSONA--------------------");
            System.out.println("-- --existeCIRandom()--------     :"+sql);
            System.out.println("--------------------------------------------------");
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            if(MP_RESULTADO.getRow() > 0 || MP_RESULTADO.next() == true) {
                valor = true;
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // END METHOD 

    
    
//    @Override
//    public List cargar_grilla() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

    
    @Override
    public List traer_datos(String idTraer, HttpSession PARAM_SESION) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LOS MEDICOS, UTILIZO MAS PARA LOS BUSCADORES 
    */
    public Map<String, String> traerComboPacientes(String PARAM_IDPACIENTE, HttpSession sesionDatos, String PARAM_IDCLINICA) {
        Map<String, String> mapaList = new LinkedHashMap<>();
        try {
            String whereParamPac = "AND rp.IDPERSONA NOT IN (0, 1"; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE MEDICO PARA QUE NO SE REPITA EN EL COMBO 
//            String whereParamMedico = ""; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE MEDICO PARA QUE NO SE REPITA EN EL COMBO 
            // CONTROLO SI ES QUE EL IDMEDICO QUE LE PASO POR EL PARAMETRO ESTA CARGADO, SI ESTA CARGADO ENTONCES ESE IDCLIENTE ES EL PRIMERO QUE DEBE ESTAR EN LA LISTA DEL COMBO 
            if (!(PARAM_IDPACIENTE.equals("")) || !(PARAM_IDPACIENTE.isEmpty())) {
                List<BeanPersona> listPacientes = new ArrayList<>();
                listPacientes = traerDatosPersona(PARAM_IDPACIENTE);
                Iterator<BeanPersona> iterParamPac = listPacientes.iterator();
                BeanPersona datosPaciente = new BeanPersona();
                String PACIENTE_NAME = "";
                while(iterParamPac.hasNext()) {
                    datosPaciente = iterParamPac.next();
                    PACIENTE_NAME = datosPaciente.getRP_CINRO()+" - "+datosPaciente.getRP_NOMBRE()+" "+datosPaciente.getRP_APELLIDO();
//                    PACIENTE_NAME = datosPaciente.getRP_CINRO()+" - "+datosPaciente.getRP_APELLIDO()+" "+datosPaciente.getRP_NOMBRE();
                }
                mapaList.put(PARAM_IDPACIENTE, PACIENTE_NAME);
                whereParamPac = whereParamPac+", "+PARAM_IDPACIENTE+")";
//                whereParamMedico = "AND rp.IDPERSONA NOT IN ('"+PARAM_IDMEDICO+"')";
            } else {
                whereParamPac = whereParamPac+")";
            }
            
            VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
            String VAR_WHERE_IDCLINICA;
            // CONDICIONAL QUE UTILIZO PARA SABER SI EL PARAMETRO DEL IDCLINICA VIENE VACIO O CON DATO, SI ESTUVIERA VACIO ENTONCES FILTRARIA POR LA CLINICA DE LOGEO NOMAS 
            if (PARAM_IDCLINICA.isEmpty() || PARAM_IDCLINICA.equalsIgnoreCase("")) {
                VAR_WHERE_IDCLINICA = "rp.IDCLINICA = '"+VAR_ID_CLINICA+"'";
            } else { // PERO SI NO ESTUVIERA VACIO, ENTONCES PASARIA A FILTRAR POR EL IDCLINICA QUE LE PASE POR PARAMETRO 
                VAR_WHERE_IDCLINICA = "rp.IDCLINICA = '"+PARAM_IDCLINICA+"'";
            }
            
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                "FROM rh_persona rp \n" +
                "WHERE "+VAR_WHERE_IDCLINICA+" \n" + 
                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
//                "AND rp.IDPERSONA NOT IN (0,1) \n" +  /* IDPERSONA 1 = ROOT  */
                ""+whereParamPac+" \n" +
                "ORDER BY rp.APELLIDO ASC, rp.CINRO ASC, rp.NOMBRE ASC \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --traerComboPacientes()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                String idPersona = MP_RESULTADO.getString("IDPERSONA");
                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("NOMBRE")+" "+MP_RESULTADO.getString("APELLIDO");
//                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("APELLIDO")+" "+MP_RESULTADO.getString("NOMBRE");
                mapaList.put(idPersona, cbxCLIENTE);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA PODER OBTENER EL IDPACIENTE A TRAVES DEL NRO DE CI DEL PACIENTE 
    */
    public String traerIdPacCINRO(HttpSession PARAM_SESION, String PARAM_CINRO) {
        String paramId = "";
        try {
            VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
            
            String sql = "SELECT IDPERSONA FROM RH_PERSONA WHERE CINRO = '"+PARAM_CINRO+"' AND IDCLINICA = "+VAR_ID_CLINICA+" ";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --traerIdPacCINRO()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                paramId = MP_RESULTADO.getString("IDPERSONA");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramId;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LAS PERSONAS, SEAN MEDICO, PACIENTE, SECRETARIO O ADMINISTRADOR, / LO UTILIZO PARA EL MODAL EN AÑADIR USUARIO NUEVO 
    */
    public Map<String, String> cargarComboPersonas(String PARAM_IDPERSONA, HttpSession sesionDatos) {
        Map<String, String> mapaList = new LinkedHashMap<>();
        try {
            String whereParamPac = "AND rp.IDPERSONA NOT IN (0, 1"; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE MEDICO PARA QUE NO SE REPITA EN EL COMBO 
//            String whereParamMedico = ""; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE MEDICO PARA QUE NO SE REPITA EN EL COMBO 
            // CONTROLO SI ES QUE EL IDMEDICO QUE LE PASO POR EL PARAMETRO ESTA CARGADO, SI ESTA CARGADO ENTONCES ESE IDCLIENTE ES EL PRIMERO QUE DEBE ESTAR EN LA LISTA DEL COMBO 
            if (!(PARAM_IDPERSONA.equals("")) || !(PARAM_IDPERSONA.isEmpty())) {
                List<BeanPersona> listPacientes = new ArrayList<>();
                listPacientes = traerDatosPersona(PARAM_IDPERSONA);
                Iterator<BeanPersona> iterParamPac = listPacientes.iterator();
                BeanPersona datosPaciente = new BeanPersona();
                String PACIENTE_NAME = "";
                while(iterParamPac.hasNext()) {
                    datosPaciente = iterParamPac.next();
                    PACIENTE_NAME = datosPaciente.getRP_CINRO()+" - "+datosPaciente.getRP_APELLIDO()+" "+datosPaciente.getRP_NOMBRE();
                }
                mapaList.put(PARAM_IDPERSONA, PACIENTE_NAME);
                whereParamPac = whereParamPac+", "+PARAM_IDPERSONA+")";
            } else {
                whereParamPac = whereParamPac+")";
            }
            
            VAR_ID_CLINICA = (String) sesionDatos.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
            
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA, \n" +
                "rp.RAZON_SOCIAL, rp.RUC, rp.DIRECCION, rp.EMAIL, rp.TELFPARTICULAR, rp.TELFMOVIL, rp.TELEFPARTICULAR, rp.IDCLINICA \n" +
                ", COALESCE((SELECT IDUSUARIO FROM sys_usuario WHERE IDPERSONA = rp.IDPERSONA),'') AS IDUSUARIO, \n" +
//                ", COALESCE((SELECT IDUSUARIO FROM sys_usuario WHERE IDPERSONA = rp.IDPERSONA AND ESTADO = 'A'),'') AS IDUSUARIO \n" +
                "COALESCE((SELECT ESTADO FROM sys_usuario WHERE IDPERSONA = rp.IDPERSONA),'') AS ESTADO_USER  \n" +
                "FROM rh_persona rp \n" +
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
//                "AND rp.IDCATEGORIA_PERSONA = "+IDCATEG_PER_CLIENTE+" \n" +
                ""+whereParamPac+" \n" +
                "ORDER BY rp.APELLIDO ASC, rp.CINRO ASC, rp.NOMBRE ASC \n";
            System.out.println("------------------------MODEL_PERSONA--------------------------");
            System.out.println("-- --cargarComboPersonas()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                String var_tiene_user; // VARIABLE QUE UTILIZO PARA PODER MOSTRAR EN EL COMBO A LAS PERSONAS QUE NO TENGAN USUARIO PARA PODER DIFERENCIARLOS 
                String CATEG_PERS = "";
                String CATEG_PERSONA = MP_RESULTADO.getString("DESC_CATEG_PERSONA");
                if (CATEG_PERSONA.equalsIgnoreCase("ADMINISTRADOR")) {
                    CATEG_PERS = "Admin";
                } else if (CATEG_PERSONA.equalsIgnoreCase("FUNCIONARIO")) {
                    CATEG_PERS = "Func.";
                } else if (CATEG_PERSONA.equalsIgnoreCase("SECRETARIA/O") || CATEG_PERSONA.equalsIgnoreCase("SECRETARIO")) {
                    CATEG_PERS = "Secre.";
                } else if (CATEG_PERSONA.equalsIgnoreCase("PACIENTE")) {
                    CATEG_PERS = "Pac.";
                } else if (CATEG_PERSONA.equalsIgnoreCase("MEDICO")) {
                    CATEG_PERS = "Med.";
                }
                String idPersona = MP_RESULTADO.getString("IDPERSONA");
                String idUsuario = MP_RESULTADO.getString("IDUSUARIO");
                if (idUsuario.isEmpty() || idUsuario.equals("")) { // NO TIENE USER 
                    var_tiene_user = "(*)";
                } else { // TIENE USER 
                    String ESTADO = MP_RESULTADO.getString("ESTADO_USER");
                    var_tiene_user = "("+ESTADO+")"; // CARGARIAMOS LA INICIAL DEL ESTADO 
                }
                String cbxCLIENTE = MP_RESULTADO.getString("CINRO")+" - "+MP_RESULTADO.getString("NOMBRE")+" "+MP_RESULTADO.getString("APELLIDO")+"("+CATEG_PERS+")"+var_tiene_user+"";
                mapaList.put(idPersona, cbxCLIENTE);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA PODER FILTRAR EN LA TABLA DE PACIENTE POR EL NRO DE CI EN LA PAGINA DE AGENDAMIENTO PARA PODER ENCONTRAR MAS RAPIDO AL PACIENTE QUE SE QUIERE AGENDAR 
    */
    public String filtrarPorNroCi(HttpSession PARAM_SESION, String PARAM_CINRO) {
        String paramId = "";
        
        VAR_ID_CLINICA = (String) PARAM_SESION.getAttribute("ID_CLINICA_USER"); // idclinica del usuario logeado 
        
        try {
            String sql = "SELECT rp.IDPERSONA, rp.NOMBRE, rp.APELLIDO, rp.CINRO, rp.TIPODOC, rp.IDCATEGORIA_PERSONA, rp.DESC_CATEG_PERSONA \n" +
                "FROM rh_persona rp \n" + 
                "WHERE rp.IDCLINICA = '"+VAR_ID_CLINICA+"' \n" + 
                "AND rp.CINRO = '"+PARAM_CINRO+"' \n";
            System.out.println("-------------------MODEL_PERSONA---------------------");
            System.out.println("-- --filtrarPorNroCi()-------     "+sql);
            System.out.println("-----------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                paramId = MP_RESULTADO.getString("IDPERSONA");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramId;
    }
    
    
    // METODO PARA ACTUALIZAR LA CONTRASEÑA DEL USUARIO DEL PACIENTE QUE TENGA UN USUARIO Y QUIERA ACTUALIZAR SU CONTRASEÑA 
    public String updatePasswordActual(String PARAM_CLAVE_NUEVA, String PARAM_IDUSUARIO, String PARAM_IDPERSONA) {
        String tipoRespuesta="";
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        String PASS = metodosIniSes.pass_clave();
        try {
            String sql = "UPDATE sys_usuario \n" +
                "SET CLAVE = AES_ENCRYPT('"+PARAM_CLAVE_NUEVA+"','"+PASS+"') \n" +
                "WHERE IDUSUARIO = '"+PARAM_IDUSUARIO+"' \n" +
                    "AND IDPERSONA = '"+PARAM_IDPERSONA+"' \n" +
                    "AND ESTADO = 'A' \n";
//            System.out.println("-------------------MODEL_PERSONA---------------------");
//            System.out.println("-- --updatePasswordActual()-------     "+sql);
//            System.out.println("-----------------------------------------------------");
            MP_CONEXION = devolverConex();
            MP_SENTENCIA = MP_CONEXION.createStatement();
            int cantResul = MP_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
//                if (varOperacion == 2) {
                    tipoRespuesta = "1";
////                    respuesta = "Se ha Modificado con Exito.";
//                } else {
//                    tipoRespuesta = "1";
////                    respuesta = "Se ha Registrado con Exito.";
//                }
                
            } else { // SALTO ALGUN ERROR AL EJECUTAR LA OPERACION SQL 
                tipoRespuesta = "2";
//                respuesta = "No se ha podido guardar, vuelva a intentarlo.";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelPersona.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    
} // END CLASS 