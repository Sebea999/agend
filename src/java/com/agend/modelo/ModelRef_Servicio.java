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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanServicio;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelRef_Servicio implements CRUD {
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_REF_SERVICIO.-------");
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
            MRS_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MRS_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MRS_CONEXION;
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
    private static Connection MRS_CONEXION = null;
    private static Statement MRS_SENTENCIA = null;
    private static ResultSet MRS_RESULTADO = null;
    
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
            MRS_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MRS_SENTENCIA = MRS_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MRS_RESULTADO = MRS_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MRS_RESULTADO;
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
            if (MRS_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MRS_RESULTADO.close();
            }
            if (MRS_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MRS_SENTENCIA.close();
            }
            if (MRS_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MRS_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    @Override
    public List traer_datos(String idTraer, HttpSession PARAM_SESION) {
        List<BeanServicio> lista = new ArrayList<>();
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA \n" +
                "FROM rh_servicio \n" +
                "WHERE IDSERVICIO = '"+idTraer+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---traer_datos()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                BeanServicio datos = new BeanServicio();
                    datos.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                    datos.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                    datos.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                    datos.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
                    datos.setRHS_IVA(MRS_RESULTADO.getString("IVA"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }

    
    @Override
    public String guardar(Object obj) {
        String tipoRespuesta = "2"; // 1 : exito / 2 : error 
        BeanServicio datos = (BeanServicio) obj;
        
        try {
            String IDSERVICIO = datos.getRHS_IDSERVICIO();
            String DESC_SERVICIO = datos.getRHS_DESC_SERVICIO();
            String MONTO = datos.getRHS_MONTO();
            String ESTADO = datos.getRHS_ESTADO();
            if (ESTADO.equalsIgnoreCase("ACTIVO")) {
                ESTADO = "A";
            } else if (ESTADO.equalsIgnoreCase("INACTIVO")) {
                ESTADO = "I";
            }
            String IVA = datos.getRHS_IVA();
            
            String sql = "INSERT INTO rh_servicio(IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA) \n" +
                "VALUES('"+IDSERVICIO+"', '"+DESC_SERVICIO+"', '"+MONTO+"', '"+ESTADO+"', '"+IVA+"') ";
            System.out.println("------------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- ---guardar()--------          "+sql);
            System.out.println("-------------------------------------------------------------------");
            MRS_CONEXION = devolverConex();
            MRS_SENTENCIA = MRS_CONEXION.createStatement();
            int cantResul = MRS_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
            } else {
                tipoRespuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }

    
    public String modificar(Object obj) {
        String tipoRespuesta = "2"; // 1 : exito / 2 : error 
        BeanServicio datos = (BeanServicio) obj;
        
        try {
            String IDSERVICIO = String.valueOf(datos.getRHS_IDSERVICIO());
            String DESC_SERVICIO = datos.getRHS_DESC_SERVICIO();
            String MONTO = datos.getRHS_MONTO().replace(".","");
            String ESTADO = datos.getRHS_ESTADO();
            if (ESTADO.equalsIgnoreCase("ACTIVO")) {
                ESTADO = "A";
            } else if (ESTADO.equalsIgnoreCase("INACTIVO")) {
                ESTADO = "I";
            }
            String IVA = datos.getRHS_IVA();
            
            String sql = "UPDATE rh_servicio \n" +
                "SET DESC_SERVICIO = '"+DESC_SERVICIO+"', \n" +
                    "MONTO = '"+MONTO+"', \n" +
                    "ESTADO = '"+ESTADO+"', \n" +
                    "IVA = '"+IVA+"' \n" +
                "WHERE IDSERVICIO = '"+IDSERVICIO+"' ";
            System.out.println("------------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- ---modificar()--------          "+sql);
            System.out.println("-------------------------------------------------------------------");
            MRS_CONEXION = devolverConex();
            MRS_SENTENCIA = MRS_CONEXION.createStatement();
            int cantResul = MRS_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                tipoRespuesta = "1";
            } else {
                tipoRespuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return tipoRespuesta;
    }
    
    
    @Override
    public String eliminar(Object obj) {
        String respuesta = "2"; // 1 : exito / 2 : error 
        
        BeanServicio datos = (BeanServicio) obj;
        
        try {
            String IDSERVICIO = String.valueOf(datos.getRHS_IDSERVICIO());
            
            String sql = "DELETE FROM rh_servicio WHERE IDSERVICIO = '"+IDSERVICIO+"' ";
            System.out.println("------------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- ---eliminar()--------          "+sql);
            System.out.println("-------------------------------------------------------------------");
            MRS_CONEXION = devolverConex();
            MRS_SENTENCIA = MRS_CONEXION.createStatement();
            int cantResul = MRS_SENTENCIA.executeUpdate(sql);
            if (cantResul == 1) { // COMO LA OPERACION (INSERT/UPDATE) DEVUELVE UN VALOR (1) CUANDO SE EJECUTA SIN NINGUN ERROR, ENTONCES SI DA ERROR DEVUELVE (0) 
                respuesta = "1";
            } else {
                respuesta = "2";
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return respuesta;
    }
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER CONTROLAR AL GUARDAR SI ES QUE LA SERVICIO YA EXISTE 
    */
    public boolean ctrlExisteServicio(String PARAM_SERVICIO, String PARAM_IDSERVICIO) {
        boolean valor = false; // TRUE = SE CUMPLE LA VALIDACION / FALSE = NO SE CUMPLE CON LA VALIDACION 
        
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO \n" +
                "FROM rh_servicio \n" +
                "WHERE (UPPER(DESC_SERVICIO) = UPPER('"+PARAM_SERVICIO.replace("'", "\\'")+"')) \n" +
                "AND IDSERVICIO NOT IN ('"+PARAM_IDSERVICIO+"') \n" +
                "AND ESTADO = 'A' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- ---ctrlExisteServicio()--------     "+sql);
            System.out.println("-------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MRS_RESULTADO = consultaBD(sql);
            
            if (MRS_RESULTADO.next() == true) {
                valor = true;
            } else {
                valor = false; // COMO EL SELECT NO TIENE RESULTADO, ENTONCES EL PRODUCTO NO EXISTE 
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
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
        List<BeanServicio> lista_mostrar = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_NRO_REG_MOSTRAR;
//        String cant_min_fija = metodosIniSes.minNroCbxCantFil();
        
        // LE ESTABLESCO OTRA VARIABLE QUE UTILIZARE PARA IR ESTABLECIENDO EL LIMITE DE MANERA ASCENDENTE SUMANDO LA CANTIDAD MINIMA DE RESULTADO QUE SE PIDE 
        String cant_min = cant_min_fija;
//        System.out.println("_   __CANTIDAD_MIN_MOSTRAR:     :"+cant_min);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC")));
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
        
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA \n" +
                "FROM rh_servicio \n" + 
                "WHERE ESTADO NOT IN ('X') \n" + 
                "ORDER BY DESC_SERVICIO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(IDSERVICIO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_servicio \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE ESTADO NOT IN ('X') \n" + 
                "ORDER BY DESC_SERVICIO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- --cargar_grilla_paginacion()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
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
            while (MRS_RESULTADO.next()) {
//        for (int i = 1; i <= Integer.parseInt(cant_resul); i++) {
//            System.out.println("_   _   ___FOR_i:  :"+i);
//            System.out.println("_   _   _   __cant_lista:  :"+cant_btn_lista);

                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(PARAM_NRO_PAG_MOSTRAR)) {
//                    System.out.println("__*____LISTA_MOSTRAR___( "+PARAM_NRO_PAG_MOSTRAR+" )___");
                    // INGRESO LOS DATOS A LA LISTA 
                    BeanServicio datos = new BeanServicio();
                        datos.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                        datos.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                        datos.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                        datos.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
                        datos.setRHS_IVA(MRS_RESULTADO.getString("IVA"));
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
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_REFSERV_LISTA_ACTUAL", "1");
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_REFSERV_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_REFSERV_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
//        // CARGO A LA SESION LA CANTIDAD DE FILAS QUE HAY PARA DESDE EL JSP PODER CALCULAR Y SABER SI NECESITA EL BOTON DE >> O NO 
//        PARAM_SESION.setAttribute("PAG_PC_ANU_FACT_CANT_TOTAL_ROWS", ""+cant_resultado+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_REFSERV_CANT_ROWS_MOSTRAR", ""+PARAM_NRO_REG_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LOS NUMEROS DE LOS BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
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
    
    
    
    // METODO PARA LA PAGINACION SOBRE EL FILTRO QUE SE LE HACE A LA TABLA DEL REFERENCIAL DE SERVICIO 
    public List filtrar_paginacion(HttpSession sesionDatos, String PARAM_CBX_MOSTRAR, String PARAM_TXT_FILTRO) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       _MOSTRAR__:  :"+PARAM_CBX_MOSTRAR);
//        PARAM_CBX_MOSTRAR = "1";
        System.out.println("___     ___________filtrar_paginacion()___________     ___");
        List<BeanServicio> listaFiltro = new ArrayList<>();
        ModelInicioSesion metodosIniSes = new ModelInicioSesion();
        
//        if (PARAM_CBX_MOSTRAR.equals("10")) { // BORRAR 
//            System.out.println("_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "1";
//        } else if(PARAM_CBX_MOSTRAR.equals("20")) {
//            System.out.println("_ELSE_IF_CAMBIO_DE_VALOR_____");
//            PARAM_CBX_MOSTRAR = "2";
//        }
        
        // OBTENGO EL NUMERO DE PAGINA ACTUAL QUE LA GRILLA VA A MOSTRAR 
        String NRO_PAG_ACTUAL_MOSTRAR = ""; // OBSERVACION: NO OBTENGO DE LA SESION PORQUE AL FILTRAR SE SUPONE QUE LOS DATOS SE REFRESCAN Y POR ESA RAZON DEBERIA DE MOSTRARLE AL USUARIO DESDE LA PRIMERA PAGINA 
        if (sesionDatos.getAttribute("PAG_REFSERV_LISTA_ACTUAL") == null || String.valueOf(sesionDatos.getAttribute("PAG_REFSERV_LISTA_ACTUAL")).isEmpty()) {
            NRO_PAG_ACTUAL_MOSTRAR = "1";
        } else {
            NRO_PAG_ACTUAL_MOSTRAR = (String) sesionDatos.getAttribute("PAG_REFSERV_LISTA_ACTUAL");
        }
        System.out.println("_   __NRO_PAG_ACTUAL:     :"+NRO_PAG_ACTUAL_MOSTRAR);
        
        // VARIABLE QUE REPRESENTARIA LA CANTIDAD DE CLICS DERECHO QUE SE TIENE, DEACUERDO A ESTO YO SÉ LOS TRES BOTONES QUE POSIBLEMENTE SE VISUALICEN EN LA PAGINA (SI NO SE LLEGA A COMPLETAR CON FILAS UN BOTON PUES NO SE VE) Y DENTRO DE ESTOS TRES BOTONES SE ENCUENTRA EL BOTON DE LA PAGINA ACTUAL QUE SE ESTARA VISUALIZANDO 
        int CANT_CLICS_DERECHO = 1;
        if (sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC") == null || String.valueOf(sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC")).isEmpty()) {
            CANT_CLICS_DERECHO = 1;
        } else {
            CANT_CLICS_DERECHO = Integer.parseInt(String.valueOf(sesionDatos.getAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC")));
        }
        System.out.println("_   __CANT_CLIC_DERECHO:     :"+CANT_CLICS_DERECHO);
        
        // CANTIDAD MINIMA DE RESULTADO QUE SE SOLICITA MOSTRAR EN LA GRILLA DE LA PAGINA 
        String cant_min_fija = PARAM_CBX_MOSTRAR;
        String cant_inicial_anterior = (String) sesionDatos.getAttribute("PAG_REFSERV_CANT_ROWS_MOSTRAR"); // ESTA ES LA CANTIDAD DE LA ANTERIOR SELECCION DE CANTIDAD DE FILAS A MOSTRAR EN LA GRILLA, UTILIZO ESTA VARIABLE PARA COMPARARLA CON LA DE AHORA PARA SABER SI QUIERE CAMBIAR LA CANTIDAD DE FILAS A MOSTRAR O QUIERE CAMBIAR DE PAGINA NOMAS O ACTUALIZAR EL FILTRO 
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
//            sqlFiltroTxt = "AND ("
//                    + "UPPER(DESC_SERVICIO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" 
//                    + "OR UPPER(IDSERVICIO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
//                ") ";
            // OBSERVACION: EN CASO DE QUE SE FILTRE POR EL ESTADO, LE AGREGARE DIRECTAMENTE EL WHERE PARA ESTADO Y SI NO ES UN ESTADO ENTONCES LE HARE QUE CONSULTE POR LA DESCRIPCION Y EL ID DEL SERVICIO 
            if (PARAM_TXT_FILTRO.equalsIgnoreCase("A") || PARAM_TXT_FILTRO.equalsIgnoreCase("ACTIVO")) {
                sqlFiltroTxt = "AND ESTADO = 'A' \n";
            } else if (PARAM_TXT_FILTRO.equalsIgnoreCase("INACTIVO") || PARAM_TXT_FILTRO.equalsIgnoreCase("I")) {
                sqlFiltroTxt = "AND ESTADO = 'I' \n";
            } else {
                sqlFiltroTxt = "AND (UPPER(DESC_SERVICIO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%') \n" +
                    "  OR UPPER(IDSERVICIO) LIKE UPPER('%"+PARAM_TXT_FILTRO+"%')"
                        + ") \n";
            }
        }
        
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA \n" +
                "FROM rh_servicio \n" +
                "WHERE ESTADO NOT IN ('X') \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY DESC_SERVICIO ASC \n"// + 
//                ""+sqlFiltroCbx+" \n"
                    ;
            
            String SELECT_COUNT = "SELECT COUNT(IDSERVICIO) AS CANTIDAD_FILA \n" + // EL NOMBRE "CANTIDAD_FILA" ES NECESARIO PORQUE DESDE EL METODO SE RECUPERA ASI 
                "FROM rh_servicio \n" + // DESDE ACA YA DEBE DE SER IGUAL AL SELECT DE ARRIBA QUE RECUPERA LAS FILAS QUE VAN A LA GRILLA 
                "WHERE ESTADO NOT IN ('X') \n" + 
                ""+sqlFiltroTxt+" \n" + 
                "ORDER BY DESC_SERVICIO ASC \n"; // ES EL MISMO SELECT QUE SE USA PARA RECUPERAR LAS FILAS PERO CON UN COUNT 
            cant_resultado = metodosIniSes.cantidad_resultado(SELECT_COUNT);
            
            System.out.println("------------------------MODEL_SERVICIO--------------------------");
            System.out.println("-- --filtrar_paginacion()--------     "+sql);
            System.out.println("----------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
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
            while (MRS_RESULTADO.next()) {
                
                // SI LA CANTIDAD DE LISTA ACTUAL ES IGUAL A 1 ENTONCES CARGO A UNA LISTA PARA MOSTRARLA Y RETORNARLA PARA QUE MUESTRE EN CASO DE QUE ENTRE POR PRIMERA VEZ EN LA PAGINA DE PACIENTE 
                if (String.valueOf(cant_btn_lista).equals(NRO_PAG_ACTUAL_MOSTRAR)) {
                    BeanServicio datos = new BeanServicio();
                        datos.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                        datos.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                        datos.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                        datos.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
                        datos.setRHS_IVA(MRS_RESULTADO.getString("IVA"));
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
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        
        // CARGO LA PAGINA ACTUAL A MOSTRAR 
        sesionDatos.setAttribute("PAG_REFSERV_LISTA_ACTUAL", NRO_PAG_ACTUAL_MOSTRAR);
        // CARGO EL TOTAL DE LISTAS
        sesionDatos.setAttribute("PAG_REFSERV_CANT_TOTAL_BTNS", ""+cant_btn_lista_final+"");
//        sesionDatos.setAttribute("PAG_REFSERV_CANT_TOTAL_BTNS", ""+cant_btn_lista+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE FILAS QUE SE MUESTRAN PARA LUEGO UTILIZARLO AL COMIENZO DEL METODO PARA HACER UNA VALIDACION 
        sesionDatos.setAttribute("PAG_REFSERV_CANT_ROWS_MOSTRAR", ""+PARAM_CBX_MOSTRAR+"");
        // VARIABLE QUE UTILIZO PARA SABER LA CANTIDAD DE BOTONES A MOSTRAR DEACUERDO A LA CANTIDAD DE CLIS DERECHOS 
        sesionDatos.setAttribute("PAG_REFSERV_CANT_BTN_DERE_CLIC", ""+CANT_CLICS_DERECHO+"");
        
        return listaFiltro;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA PODER RECUPERAR UN NUEVO ID OBTENIENDO EL UTLIMO ID Y SUMARLE UNO PARA UTILIZARLO COMO PK 
    */
    public String traerNewIdServicio() {
        String paramId = "";
        try {
            String sql = "SELECT (COALESCE(MAX(IDSERVICIO),0)+1) AS ID FROM rh_servicio";
            System.out.println("------------------------MODEL_SERVICIO--------------------------");
            System.out.println("-- --cargarNewIdServicio()--------     "+sql);
            System.out.println("----------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                paramId = MRS_RESULTADO.getString("ID");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramId;
    }
    
    
    
    /*
    METODO QUE VERIFICA LA EXISTENCIA DEL IDSERVICIO DENTRO DE LA TABLA DE SERVICIO PARA EVITAR REALIZAR ACCIONES CON UN ID QUE NO EXISTE 
    */
    public boolean ctrlExisteIdServicio(String PARAM_IDSERVICIO) {
        boolean valor = false; // TRUE = SE CUMPLE LA VALIDACION / FALSE = NO SE CUMPLE CON LA VALIDACION 
        
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO \n" +
                "FROM rh_servicio \n" +
                "WHERE IDSERVICIO = '"+PARAM_IDSERVICIO+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---ctrlExisteIdServicio()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MRS_RESULTADO = consultaBD(sql);
            
            if (MRS_RESULTADO.next() == true) {
                valor = true;
            } else {
                valor = false; // COMO EL SELECT NO TIENE RESULTADO, ENTONCES EL ID NO EXISTE 
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER CONTROLAR SI EL IDSERVICIO ESTA SIENDO UTILIZADA DE MANERA ACTIVA EN OTRA TABLA PARA NO ELIMINARLA EN CASO DE QUE SE ESTE UTILIZANDO 
    */
    public boolean ctrlUsoIdServicio(String PARAM_IDSERVICIO) {
        boolean valor = false; // TRUE = SE ESTA USANDO EL ID  /  FALSE = NO SE USA EL IDCLINICA 
        try {
            String sql = "SELECT '*' \n" +
                "FROM ope_atencion_paciente_det aten_det \n" +
                "JOIN ope_atencion_paciente aten_cab ON aten_det.IDATENCIONPAC = aten_cab.IDATENCIONPAC \n" +
                "WHERE aten_det.IDSERVICIO = '"+PARAM_IDSERVICIO+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---ctrlUsoIdServicio()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MRS_RESULTADO = consultaBD(sql);
            
            if (MRS_RESULTADO.next() == true) {
                valor = true;
            } else {
                valor = false; // COMO EL SELECT NO TIENE RESULTADO, ENTONCES EL ID NO EXISTE 
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Clinica.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    
    /*
        METODO QUE UTILIZO PARA TRAER ALGUNOS DATOS QUE UTILIZARE MAS FRECUENTEMENTE Y PARA NO REALIZAR UN SELECT 
        GRANDE A CADA MOMENTO LLAMARE A ESTE METODO QUE SOLO EXTRAERA ALGUNOS DATOS DE LA TABLA SERVICIO Y SI 
        QUISIESE DATOS MAS EXTENSOS DEL SERVICIO ENTONCES LLAMARIA AL METODO DE "traer_datos()" 
    */
    public BeanServicio datosBasicosServicio(BeanServicio paramBeanServ, String paramId) {
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA \n" +
                "FROM rh_servicio \n" +
                "WHERE IDSERVICIO = "+paramId+" \n";
            System.out.println("----------------------MODEL_REF_SERVICIO------------------------");
            System.out.println("-- --datosBasicosServicio()--------     "+sql);
            System.out.println("----------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
//                BeanPersona datos = new BeanPersona();
                paramBeanServ.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                paramBeanServ.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                paramBeanServ.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                paramBeanServ.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
                paramBeanServ.setRHS_IVA(MRS_RESULTADO.getString("IVA"));
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return paramBeanServ;
    } // END METHOD 
    
    
    
    // METODO QUE UTILIZO PARA PODER CARGAR COMBOBOX DE SERVICIO (EN LA PAGINA DE ATENCION (FICHA DEL PACIENTE) POR EJEMPLO) 
    public Map<String, String> cargarComboServicio(String PARAM_IDSERVICIO) {
        Map<String, String> mapaServicio = new LinkedHashMap<>();
        
        String PARAM_DESC_ESPECIALIDAD;
        if (PARAM_IDSERVICIO == null || PARAM_IDSERVICIO.isEmpty() || PARAM_IDSERVICIO.equals("")) { // EN CASO DE QUE ESTE VACIO, ENTONCES ES PORQUE SE ESTA CARGANDO UN NUEVO REGISTRO Y ASI LE MUESTRO PRIMERO EL NO/DEFINIDO 
            PARAM_IDSERVICIO = "1";
//            PARAM_DESC_CLINICA = "(NO/DEFINIDO)";
        }
        
        PARAM_DESC_ESPECIALIDAD = traerDescServicio(PARAM_IDSERVICIO);
        mapaServicio.put(PARAM_IDSERVICIO, PARAM_DESC_ESPECIALIDAD); // CARGO LA PRIMERA LINEA DEL COMBO CON LO QUE SELECCIONO EL USUARIO EN CASO DE QUE SE TRAIGA UN PRODUCTO Y SI ES UN NUEVO REGISTRO ENTONCES VA A CARGAR EL NO DEFINIDO 
        
        try { // LUEGO DE ESO CARGARIA LAS DEMAS MARCAS EXCLUYENDO EL QUE YA SE CARGO 
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO \n" +
                "FROM rh_servicio \n" +
                "WHERE IDSERVICIO NOT IN ("+PARAM_IDSERVICIO+") \n" +
                "AND ESTADO = 'A' \n" +
                "ORDER BY DESC_SERVICIO ASC \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---cargarComboServicio()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                String idservicio = MRS_RESULTADO.getString("IDSERVICIO");
                String desc_servicio = MRS_RESULTADO.getString("DESC_SERVICIO");
                mapaServicio.put(idservicio, desc_servicio);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaServicio;
    } // END METHOD 
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER TRAER LA DESCRIPCION DEL ID SERVICIO 
    */
    public String traerDescServicio(String PARAM_IDSERVICIO) {
        String desc_servicio = "";
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO \n" +
                "FROM rh_servicio \n" +
                "WHERE IDSERVICIO = '"+PARAM_IDSERVICIO+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- ---traerDescServicio()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO EL RESULTADO CON EL METODO QUE REALIZA LA CONEXION Y CONSULTA A LA BASE DE DATOS Y ESTE METODO DEVUELVE UN RESULTADO QUE CARGO AL MIO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                desc_servicio = MRS_RESULTADO.getString("DESC_SERVICIO");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return desc_servicio;
    } // END METHOD 
    
    
    
    /*
        METODO QUE UTILIZO PARA CARGAR COMBOBOX CON LOS SERVICIOS 
    */
    public Map<String, String> cargarComboServicio(String PARAM_IDSERVICIO, HttpSession sesionDatos) {
        Map<String, String> mapaList = new LinkedHashMap<>();
        ModelPersona metodosPer = new ModelPersona();
        try {
            String whereParamServ = "AND IDSERVICIO NOT IN (0"; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE REGISTRO PARA QUE NO SE REPITA EN EL COMBO 
//            String whereParamMedico = ""; // VARIABLE QUE UTILIZARE PARA QUE EN CASO DE QUE EL COMBO DEBA SER CARGADO POR EL MEDICO QUE LE PASO POR PARAMETRO, EN EL SELECT NO VUELVA A TRAER ESE REGISTRO PARA QUE NO SE REPITA EN EL COMBO 
            // CONTROLO SI ES QUE EL ID QUE LE PASO POR EL PARAMETRO ESTA CARGADO, SI ESTA CARGADO ENTONCES ESE ID ES EL PRIMERO QUE DEBE ESTAR EN LA LISTA DEL COMBO 
            if (!(PARAM_IDSERVICIO.equals("")) || !(PARAM_IDSERVICIO.isEmpty())) {
                List<BeanServicio> listServicio = new ArrayList<>();
                listServicio = traer_datos(PARAM_IDSERVICIO, sesionDatos);
                Iterator<BeanServicio> iterParamServicio = listServicio.iterator();
                BeanServicio datos = new BeanServicio();
                String DESC_SERVICIO = "";
                while(iterParamServicio.hasNext()) {
                    datos = iterParamServicio.next();
                    DESC_SERVICIO = datos.getRHS_DESC_SERVICIO();
                }
                mapaList.put(PARAM_IDSERVICIO, DESC_SERVICIO);
                whereParamServ = whereParamServ+", "+PARAM_IDSERVICIO+")";
            } else {
                whereParamServ = whereParamServ+")";
            }
            
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO \n" +
                "FROM rh_servicio \n" +
                "WHERE ESTADO = 'A' \n" +
                ""+whereParamServ+" \n" +
                "ORDER BY DESC_SERVICIO ASC \n";
            System.out.println("-----------------------MODEL_REF_SERVICIO-------------------------");
            System.out.println("-- --cargarComboServicio()--------     "+sql);
            System.out.println("------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                String idEspe = MRS_RESULTADO.getString("IDSERVICIO");
                String cbxEspe = MRS_RESULTADO.getString("DESC_SERVICIO");
                mapaList.put(idEspe, cbxEspe);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return mapaList;
    } // END METHOD 

    
    
    /*
        METODO QUE DEVUELVE UN BEAN PARA PODER RECUPERAR LOS DATOS DEL SERVICIO (LO UTILIZO EN ATENCION) 
    */
    public BeanServicio devolverDatosServicios(String PARAM_IDSERVICIO) {
        BeanServicio datos = new BeanServicio();
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO \n" +
                "FROM RH_SERVICIO \n" +
                "WHERE IDSERVICIO = '"+PARAM_IDSERVICIO+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---devolverDatosServicios()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                datos.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                datos.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                datos.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                datos.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return datos;
    }

    
    
    /*
        METODO QUE DEVUELVE UN BEAN PARA PODER RECUPERAR LOS DATOS DEL SERVICIO (LO UTILIZO EN ATENCION) 
    */
    public List traerDatosServicios(String PARAM_IDSERVICIO) {
        List<BeanServicio> lista = new ArrayList<>();
        try {
            String sql = "SELECT IDSERVICIO, DESC_SERVICIO, MONTO, ESTADO, IVA \n" +
                "FROM RH_SERVICIO \n" +
                "WHERE IDSERVICIO = '"+PARAM_IDSERVICIO+"' \n";
            System.out.println("------------------------MODEL_REF_SERVICIO--------------------------");
            System.out.println("-- ---traerDatosServicios()--------     "+sql);
            System.out.println("--------------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MRS_RESULTADO = consultaBD(sql);
            
            while(MRS_RESULTADO.next()) {
                BeanServicio datos = new BeanServicio();
                    datos.setRHS_IDSERVICIO(MRS_RESULTADO.getString("IDSERVICIO"));
                    datos.setRHS_DESC_SERVICIO(MRS_RESULTADO.getString("DESC_SERVICIO"));
                    datos.setRHS_MONTO(MRS_RESULTADO.getString("MONTO"));
                    datos.setRHS_ESTADO(MRS_RESULTADO.getString("ESTADO"));
                    datos.setRHS_IVA(MRS_RESULTADO.getString("IVA"));
                lista.add(datos);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelRef_Servicio.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    }
    
    
    
}