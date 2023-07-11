/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.modelo;

import com.agend.javaBean.BeanPersona;
import com.agend.javaBean.BeanInicioSesion;
import com.agend.javaBean.BeanUsuario;
import com.agend.config.ConfigCookie;
import com.agend.config.ConfigurationProperties;
import com.agend.controlador.ControllerInicioSesion;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.*;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RYUU
 */
@Repository
public class ModelInicioSesion {
    
    
//    Conexion conn = new Conexion();
    
    
    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS (local)
    */
//    private Connection devolverConex() {
//        try {
////            String host = "jdbc:mysql://sql10.freesqldatabase.com:3306/";
////            String bd = "sql10410496";
////            String user = "sql10410496";
////            String pass = "c2AijebkXg";
//            String host = "jdbc:mysql://localhost/";
//            String bd = "odonto";
//            String user = "root";
//            String pass = "admin";
//            System.out.println(".       __host:   "+host);
//            System.out.println(".       __db:   "+bd);
//            System.out.println(".       __user:   "+user);
//            System.out.println(".       __pass:   "+pass);
////            Class.forName("com.mysql.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
//            Class.forName("com.mysql.cj.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
//            MIS_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
//            
//        } catch (ClassNotFoundException | SQLException ex) {
//            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return MIS_CONEXION;
//    } // END METHOD  
    
    // [OBS] :cargue un archivo de configuration.properties con las variables que uso para la base y por medio de @Value y Environment trate de recuperar los valores del properties pero me daba null de valor cuando en el archivo de properties yo tengo las variables cargadas, y el proyecto de spring lee el archivo, y lo termine comentando porque no me funcionaba para recuperar los datos del properties.-
//    @Value("${database.user}")
//    String DB_USER;
//    @Autowired
//    private Environment env;

    // server-connect.-
    private Connection devolverConex() {
        System.out.println("[+] MODEL_INICIO_SESION.-------");
        try {
//            FileReader reader=new FileReader("config.properties");
//            Properties properties=new Properties();
//            properties.load(reader);
            // [OBS]: no uso el archivo properties porque me da error; porque no encuentra el archivo properties, igual si se encuentra en la misma carpeta que el modelo por eso en su lugar uso una clase en la carpeta de "config".-
//            String fileName="/modelo/config.properties";
//            Properties properties;
//            properties = getPropertiesMethod2(fileName);
            // [OBS] no me andaba esta forma de leer el archivo properties, me saltaba error por no encontrar el archivo entonces probe la forma de arriba ↑
//            Properties properties= new Properties();
//            properties.load(new FileInputStream(new File("ConfigurationProperties.properties")));
            // recuperacion de los datos.-
//            String host = properties.getProperty("DB_HOST");
//            String bd = properties.getProperty("DB_NAME");
//            String user = properties.getProperty("DB_USER");
//            String pass = properties.getProperty("DB_PASS");
            ConfigurationProperties properties = new ConfigurationProperties();
//            String host = env.getProperty("database.host");
            String host = properties.getDB_HOST();
//            String bd = env.getProperty("database.db_name");
            String bd = properties.getDB_NAME();
//            String configdb = env.getProperty("database.db_config");
            String configdb = properties.getDB_CONFIG();
//            String user = env.getProperty("database.user");
            String user = properties.getDB_USER();
//            String pass = env.getProperty("database.pass");
            String pass = properties.getDB_PASS();
            // datos-server.-
//            String host = "jdbc:mysql://198.199.88.44:3306/";
//            String bd = "odonto";
//            String user = "user5";
//            String pass = "agend123";
            System.out.println("[*]____host:    :"+host);
            System.out.println("[*]______db:    :"+bd);
            System.out.println("[*]__config-db: :"+configdb);
            System.out.println("[*]____user:    :"+user);
            System.out.println("[*]____pass:    :"+pass);
//            Class.forName("com.mysql.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");//Se registra la direccion dentro del .jar el Driver jdbc de MySQL
            MIS_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MIS_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
//            System.out.println("[-] Connection is failed --- to properties exception --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- file-not-found-exception.-");
//            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            System.out.println("[-] Connection Failed --- to properties exception --- io-exception.-");
//            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MIS_CONEXION;
    } // END METHOD  
    
    // metodo para leer el .properties (probando otro forma de recuperar el archivo .properties pero igual me seguia saltando el error de no encontrar el archivo).-
//    public Properties getPropertiesMethod2(String propertiesName){
//        Properties p=new Properties();
//        try {
//            // flujo de búfer de entrada
//           InputStream in=new BufferedInputStream(new FileInputStream(propertiesName));
//           p.load(in);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return p;
//    }
    
    
    
    // prueba de conexion a mariadb.-
//    String userName,password,url,driver;
//    private Connection devolverConex() {
//        System.out.println("[+] MODEL_INICIO_DE_SESION.-------");
//        userName="root";
//        password="admin";
//        url="jdbc:mariadb://198.199.88.44:3307/odonto"+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false";
//        driver="org.mariadb.jdbc.Driver";
//        try {
//            Class.forName(driver);
//            MIS_CONEXION=DriverManager.getConnection(url, userName, password);
//            MIS_SENTENCIA=MIS_CONEXION.createStatement();
//            System.out.println("[+] Connection is successful.-");
//        } catch (Exception e) {
//            System.out.println("[-] Connection is failed.-");
//            e.printStackTrace();
//        }
//        return MIS_CONEXION;
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
    private static Connection MIS_CONEXION = null;
    private static Statement MIS_SENTENCIA = null;
    private static ResultSet MIS_RESULTADO = null;
    
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
            MIS_CONEXION = devolverConex();
//            if (DIS_CONEXION != null) {
//                System.out.println("_1___CONEXION__DIFERENTE__NULL_____");
//            } else if(DIS_CONEXION == null) {
//                System.out.println("_1___CONEXION______NULL______");
//            }
            MIS_SENTENCIA = MIS_CONEXION.createStatement();
////            java.sql.Statement sentencia = conexion.createStatement();
//            if (DIS_SENTENCIA != null) {
//                System.out.println("_2___SENTENCIA__DIFERENTE__NULL_____");
//            } else if(DIS_SENTENCIA == null) {
//                System.out.println("_2___SENTENCIA______NULL______");
//            }
            MIS_RESULTADO = MIS_SENTENCIA.executeQuery(sql);
////            java.sql.ResultSet resultado = sentencia.executeQuery(sql);
//            if (DIS_RESULTADO != null) {
//                System.out.println("_3___RESULTADO__DIFERENTE__NULL_____");
//            } else if(DIS_RESULTADO == null) {
//                System.out.println("_3___RESULTADO______NULL______");
//            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return MIS_RESULTADO;
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
            if (MIS_RESULTADO != null) {
                System.out.println("__IF____RESULTADO_CERRAR_____");
                MIS_RESULTADO.close();
            }
            if (MIS_SENTENCIA != null) {
                System.out.println("__IF____SENTENCIA_CERRAR_____");
                MIS_SENTENCIA.close();
            }
            if (MIS_CONEXION != null) {
                System.out.println("__IF____CONEXION_CERRAR_____");
                MIS_CONEXION.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * COMENTARIO SOBRE EL METODO. 
     * METODO QUE UTILIZO PARA CONTROLAR VALIDACIONES PARA NO IR AGREGANDO LINEAS DE CODIGOS EN CADA CONTROLADOR SINO EN ESTE METODO NOMAS 
        * OBSERVACION :  ENCAPSULE EL CODIGO QUE COLOCABA EN CADA CONTROLADOR 
        * Y LO COLOCO EN ESTE METODO PARA NO TENER MUCHA LINEAS DE CODIGO EN LOS CONTROLADORES 
        * Y ASI SI NECESITO AGREGAR O SACAR LINEAS DE CODIGO NO TENDRIA QUE EDITAR CADA CONTROLADOR 
        * SINO ESTE METODO NOMAS 
    */
    /** 
     * @param PARAM_ID
     * @param PARAM_PAGINA
     * @param request
     * @return 
     */
    public String controlValidaciones(String PARAM_ID, String PARAM_PAGINA, HttpServletRequest request) {
        String valor; // VARIABLE QUE ALMACENA LA PAGINA A LA CUAL SE LE VA A REDIRECCIONAR 
        int bandError = 0; // BANDERA DE ERROR QUE SE ACTIVA CON LOS IF VALIDANDO 
        
        /*
        OBSERVACION: YO EN ESTE PROYECTO ACTIVO LA CONEXION SOLAMENTE CUANDO INTERACTUO CON LA BASE DE DATOS, LA ACTIVO Y LUEGO LA CIERRO, 
        Y LAS CONEXION SON POR CLASE NO ES COMO EN LOS PROYECTOS ANTEIORES DONDE USABA LA CONEXION DE UNA CLASE Y ESTA LA ACTIVABA CUANDO ABRIA LA PAGINA, 
        POR ESO COMENTE ESTA VALIDACION QUE CONTROLA EL ESTADO DE LA CONEXION A LA BASE.- 
        */
//        Config.Conexion conn = new Config.Conexion();
//        if (conn.conexion == null) {
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println("______CONEXION_NULL______");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            System.out.println(".");
//            bandError = 1;
//        }
        
        System.out.println("[*]____INIT_____---------------_______controlValidaciones()_______---------------_________");
        System.out.println("[*] PARAM_ID:   "+PARAM_ID);
        System.out.println("[*] PARAM_PAGINA:   "+PARAM_PAGINA);
        
        ModelInicioSesion inicioSesMetodos = new ModelInicioSesion();
        ControllerInicioSesion inicioSesController = new ControllerInicioSesion();
        
        /**
            OBSERVACION SOBRE EL METODO. 
                PRIMERAMENTE CONTROLAMOS SI EL USUARIO SIGUE LOGEADO O SI YA HA CERRADO SESION PARA CONTROLAR 
                LAS DEMAS VALIDACIONES, Y EN VEZ DE IR HACIENDO UN IF POR EL bandError SI ES DIFERENTE A CERO (VALOR INICIAL) 
                PARA SABER SI ES QUE LA VALIDACION ANTERIOR SE ACTIVO Y ASI EVITAR IR CONTROLANDO CADA IF VALIDANDO, 
                ES MEJOR HACER EL PRIMER IF CONTROLANDO SI SIGUE LOGEADO O NO, Y LUEGO HACER UN ELSE CONTINUAMENTE POR LAS OTRAS VALIDACIONES, 
                ASI NO TENDRIA QUE HACER UN IF ADICIONAL CONTROLANDO LA BANDERA DE ERROR 
        */
        /**
            OBSERVACION SOBRE EL METODO_2. 
            EN EL CONTROLADOR UsuRecuContraController SOLO CONTROLO SI SE ESTA MIGRANDO O NO Y NO LLAMO AL METODO LUEGO QUE CONTROLA EN SI VARIAS VALIDACIONES, 
            PORQUE SI QUIERO AGREGAR EL METODO PARA QUE HAGA POR MAS CONTROLES DE VALIDACIONES 
            ENTONCES AL METODO DEBO DE AGREGAR UN PARAMATRO MÁS PORQUE O SINO SIEMPRE VA A ENTRAR 
            EN EL CONTROL DE SI ESTA LOGEADO O NO, Y NUNCA VA A REDIRECCIONAR A DONDE DEBE IR Y LO DEJARA EN LA PAGINA DE INICIO DE SESION 
        */
        // ---------CONTROL_SI_TIENE_LOGEO_ACTIVO/ABIERTO------------------------------------------------------------------------------------- 
        // IF LLAMANDO AL METODO QUE CONTROLA SI LA SESION DEL USUARIO SE ENCUENTRA ABIERTA O CERRADA 
//        if (inicioSesMetodos.controlLogeoUsuarioCookie(request) == false) { // EN ESTE IF CONTROLO SI ES QUE LA SESION SE ENCUENTRA CERRADA PARA REDIRECCIONAR A QUE SE LOGEE 
        if (inicioSesMetodos.controlLogeoUsuarioActivo(request) == false) { // EN ESTE IF CONTROLO SI ES QUE LA SESION SE ENCUENTRA CERRADA PARA REDIRECCIONAR A QUE SE LOGEE 
//            System.out.println(".");
//            System.out.println("..");
//            System.out.println("...");
            System.out.println("[-]...........................................................");
            System.out.println("[-]   -       -   USUARIO INACTIVO / CERRADO    -       -   -");
            System.out.println("[-]...........................................................");
//            System.out.println("...");
//            System.out.println("..");
//            System.out.println(".");
//        if (inicioSesMetodos.controlLogeoUsuarioActivo() == false) { // EN ESTE IF CONTROLO SI ES QUE LA SESION SE ENCUENTRA CERRADA PARA REDIRECCIONAR A QUE SE LOGEE - // OBSERVACION: AQUI PREGUNTO SI ES FALSE Y EN EL METODO "inicioSesion()" PREGUNTO SI ES TRUE, POR QUE AQUI QUIERO REDIRECCIONAR AL LOGEO Y EN EL METODO INIT ("inicioSesion()") QUIERO REDIRECCIONAR AL MENU 
            System.out.println("[-]___if___controlLogeoUsuario()___");
            inicioSesController.varValidacionesEstado = 2; // OBSERVACION: POR EL MOMENTO NO HAGO NADA CON ESTE VALOR, QUE ES DIFERENTE AL DE LA VALIDACION DE MIGRAR(1), NO LE CARGO EL MISMO VALOR "1", PARA QUE NO SE ACTIVE EL IF QUE CONTROLA ESTA VARIABLE CON EL VALOR UNO Y EL METODO 
//                inicioSesController.varValidacionMensaje = inicioSesController.varLogeoActivoMensajeError;
//            varLogeoActivoMensajeError = "Sesion cerrada o expirada, vuelva a ingresar.";
//            inicioSesController.varValidacionMensaje =  varLogeoActivoMensajeError; // NO ES NECESARIO MOSTRAR UN MENSAJE LUEGO DE SABER QUE LA SESION ESTA CERRADA 
            inicioSesController.varValidacionMensaje =  inicioSesController.varLogeoInactivoMensajeError; // NO ES NECESARIO MOSTRAR UN MENSAJE LUEGO DE SABER QUE LA SESION ESTA CERRADA 
            bandError = 1;
        } //else 
        // -------------------------------------------------------------------------------------------------------------------------- 
        
//        // ---------CONTROL_SI_SE_ESTA_MIGRANDO------------------------------------------------------------------------------------- 
//        if (inicioSesMetodos.ctrlEstadoMigrarTabUsu() == true) { // CAMBIE EL METODO PARA QUE CONTROLE TODA LA TABLA Y NO SOLO UNA LINEA DE LA TABLA  // if PARA CONTROLAR SI ES QUE SE ESTA MIGRANDO 
////        if (inicioSesion.ctrlEstadoMigrarIdAlumno(PARAM_ID) == true) { // if PARA CONTROLAR SI ES QUE SE ESTA MIGRANDO 
////            InicioSesionController inicioSesController = new InicioSesionController();
//            System.out.println("_*__if___ctrlEstadoMigrarTabUsu()___");
//            inicioSesController.varValidacionesEstado = 1; // LE DOY EL VALOR 1 PARA QUE EN EL CONTROLADOR DE INICIO SESION CARGUE EL MENSAJE DE MIGRADO 
//            inicioSesController.varValidacionMensaje = inicioSesController.varMigrarMensajeError; // AL ENTRAR AL IF ENTONCES QUIERE DECIR QUE SE ESTA MIGRANDO Y ENTONCES CARGO ESTA VARIABLE GLOBAL PARA MOSTRAR EL MENSAJE EN LA PAGINA DE INICIO SESION 
//            bandError = 1;
////            valor = ("inicioSesion");
////        } else { // EN CASO DE QUE NO SE ESTE MIGRANDO ENTONCES LE DEVOLVERIAMOS LA PAGINA QUE SE LE PASA AL PARAMETRO PARA EL MAV 
////            valor = PARAM_PAGINA;
//        }
//        // -------------------------------------------------------------------------------------------------------------------------- 
        
        
        System.out.println("[*]___controlValidaciones('__"+PARAM_PAGINA+"__')____________bandError:      "+bandError);

    // CONTROL DE LA BANDERA PARA SABER SI LE DEVUELVO AL INICIO O A LA PAGINA QUE FUE PASADA AL PARAMETRO  
        if (bandError > 0) {
            valor = ("inicio_sesion");
            // PASAR EL MENSAJE QUE QUIERO MOSTRAR 
            request.setAttribute("respuesta", inicioSesController.varValidacionMensaje);
            
        } else { // EN CASO DE QUE NO SE ESTE MIGRANDO ENTONCES LE DEVOLVERIAMOS LA PAGINA QUE SE LE PASA AL PARAMETRO PARA EL MAV 
            valor = PARAM_PAGINA;
        }
        System.out.println("[*]____END_____---------------_______controlValidaciones()_______---------------_________");
        
        return valor;
    } // END METHOD 
    
    
    
//    [OBS] : COMENTE LOS METODOS QUE USAN COOKIES PORQUE LOCAL NO ME FUNCIONA Y SOLO ME ANDA EN LA VPS DE LINUX CENTOS 9 - EN LOCAL ME FUNCIONA BIEN LAS SESIONES Y POR ESO COMENTO LOS METODOS Y CONTROLES CON COOKIES, DE SEGURO TENGO QUE CONFIGURAR ALGO PARA UTILIZAR COOKIES O PARA IMPLEMENTAR MEJOR LAS SESIONES DE AHI QUE TENGA PROBLEMAS EN LA VPS O EN LOCAL CON AMBOS.-
///* 
//    * [OBS] : CONTROLO LA COOKIE QUE CREO EN EL CONTROLADOR LUEGO DE INICIAR SESION PARA VERIFICAR SI SIGUE ACTIVO EL USUARIO 
//*/
//    public boolean controlLogeoUsuarioCookie(HttpServletRequest request) {
//        boolean valor = false; // LA SESION SE ENCUENTRA CERRADA 
//
//        System.out.println("[+] --------__INIT__---------__CTRL_CONTROL_LOGEO_DEL_USUARIO_ACTIVO_POR_COOKIE__--------------------------");
////        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
//        
//        System.out.println("---------------------------------");
//        String idUsuario = (String) getCookie(request,"JIDUSER").getValue();
////        String idUsuario = (String) getCookie(request,"IDUSUARIO").getValue();
////        String idUsuario = (String) sesionDatosUsuario.getAttribute("IDUSUARIO");
//        System.out.println("[*]_1__COOKIE/_J-SESSION-IDU: :"+idUsuario);
//        System.out.println("[*]_2__COOKIE/_ID_USUARIO:    :"+String.valueOf(getCookie(request,"IDUSUARIO").getValue()));
//        
//        String idPersona = (String) getCookie(request,"IDPERSONA").getValue();
////        String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
//        System.out.println("---------------------------------");
//        System.out.println("[*]_2__COOKIE/_ID_PERSONA:    :"+idPersona);
//        System.out.println("---------------------------------");
//        
//        // VALIDACION PARA CONTROLAR SI ES QUE SE HA CERRADO SESION O SIGUE ACTIVA 
//        if (idUsuario == null || idUsuario.isEmpty()) { // LA SESION ESTA CERRADA, ENTONCES CORTARIA LA CONTINUACION DEL CODIGO Y LE MANDO A LOGEARSE AL USUARIO 
////        if (metodosIniSes.recuperarValorMacAddress() == false) { // LA SESION ESTA CERRADA, ENTONCES CORTARIA LA CONTINUACION DEL CODIGO Y LE MANDO A LOGEARSE AL USUARIO 
//            System.out.println("[-]__1__---SE_HA_CERRADO_SESION----");
//            
//            try {
//                // SI ES QUE NO TIENE EL IDUSUARIO ENTONCES CONTROLARIA SI ES QUE POSEE EL IDPERSONA O IDDOCENTE QUE SE PASA POR MEDIO DEL PARAMETRO Y ENTONCES RECUPERARIA NOMAS EL IDUSUARIO YA QUE ESTE SE CARGA UNA VEZ NOMAS Y PUEDE SER QUE SE DESECHE RAPIDAMENTE MIENTRAS QUE EL IDPERSONA O IDDOCENTE ES PASADO CONSTANTEMENTE DE PAGINA EN PAGINA QUE POR ESO SE MANTIENE MAS TIEMPO EN MEMORIA QUE EL IDUSUARIO 
//                if (idPersona.equals("null") || idPersona.equals("") || idPersona == null) {
////                if (sesionDatosUsuario.getAttribute("IDPERSONA").equals("null") || sesionDatosUsuario.getAttribute("IDPERSONA").equals("") || sesionDatosUsuario.getAttribute("IDPERSONA") == null) {
//                    System.out.println("[-] id-persona esta null en la cookie.-");
//                    valor = false;
//                } else { // EN TODO CASO SI ES QUE EL IDPERSONA ESTE VACIO ESO SIGNIFICARIA QUE ESTUVO MUCHO TIEMPO INACTIVO Y LA SESION SE CERRO Y EN ESE CASO YA DEVOLVERIAMOS FALSE NOMAS YA 
////                    idUsuario = traerIdUsuarioLogin(request); // SI EL IDPERSONA ESTA CARGADO ENTONCES RECUPERARIA EL IDUSUARIO 
//                    System.out.println("[-] deberia de volver a agregar la cookie de id-usu");
////                    sesionDatosUsuario.setAttribute("IDUSUARIO", idUsuario); // LUEGO DE RECUPERAR EL VALOR ENTONCES CARGARIA Y SOBREESCRIBO EL VALOR NULO QUE TIENE 
//                    valor = true; // Y UNA VEZ RECUPERADO EL IDUSUARIO Y CARGADO A LA SESION ENTONCES DEVOLVERIA VALOR TRUE YA QUE LA SIGUE ACTIVO EL LOGEO 
//                }
//            } catch(NullPointerException e) {
//                System.out.println("[-]_CATCH()___controlLogeoUsuarioCookie()_________NULL_POINTER_EXCEPTION___");
//                valor = false; // SI DA ERROR POR NULLPOINTER ENTONCES QUIERE DECIR QUE 
//            }
//            
//        } else { // SI EL VALOR ES TRUE ENTONCES LA SESION SE ENCUENTRA ABIERTA 
//            System.out.println("[+]__2__---LA_SESION_SIGUE_ABIERTA----");
//            valor = true;
//        } // END ELSE IF SESION ACTIVA 
//        System.out.println("[+] --------__END__---------__CTRL_CONTROL_LOGEO_DEL_USUARIO_ACTIVO__--------------------------");
//        // -------------------------------------------------------------------------------------------------------------------------- 
//        
//        return valor;
//    } // END METHOD 
    
/* 
    METODO QUE UTILIZO PARA CONTROLAR SI ES QUE LA SESION DEL USUARIO SE ENCUENTRA ABIERTA EN EL EQUIPO O SI YA SE CERRO, 
    SI SIGUE ABIERTO ENTONCES CONTROLO NOMAS SI EL IDPERSONA SE ENCUENTRA CARGADA Y LE REDIRECCIONO A DONDE DIO CLICK, 
    PERO SI SE ENCUENTRA CERRADA ENTONCES LE REDIRIGO AL LOGIN PARA QUE INICIE SESION 
*/
    public boolean controlLogeoUsuarioActivo(HttpServletRequest request) {
        boolean valor = false; // LA SESION SE ENCUENTRA CERRADA 

        // ---------CONTROL_SI_TIENE_SESION_ACTIVA------------------------------------------------------------------------------------- 
        /** 
         * OBSERVACIÓN.
            » NO HACE FALTA LLAMAR A ESTE METODO QUE CONTROLARIA CUANDO LA DIRECCION DE LA WEB NO LLAMARA AL SERVLET DIRECTAMENTE 
            * SINO AL FORMULARIO QUE ES DECLARADO EN EL DISPATCHER-SERVLET.XML, 
            * PERO DA UN ERROR AL CARGAR ESTE METODO QUE CONTROLARIA EN CASO DE QUE LLAMARA A LA PAGINA DIRECTAMENTE, 
            * PERO EN LA PAGINA WEB (JSP) YA TENGO UNA VALIDACION QUE VERIFICA EL VALOR DE LA COLUMNA "WEB" DE USUARIO, 
            * LO QUE NO SERIA NECESARIO EL LLAMADO DE ESTAS LINEAS DE CODIGOS DE CONTROL 
        **/
        System.out.println("[*]--------__INIT__---------__CTRL_CONTROL_LOGEO_DEL_USUARIO_ACTIVO__--------------------------");
        HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
        
        System.out.println("[*]---------------------------------");
        String idUsuario = (String) sesionDatosUsuario.getAttribute("IDUSUARIO");
        System.out.println("[+]_1__SESION_ID_USUARIO:    "+idUsuario);
        
        String idPersona = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
        System.out.println("[*]---------------------------------");
        System.out.println("[+]_2__SESION_ID_PERSONA:    "+idPersona);
        System.out.println("[*]---------------------------------");
        
        // VALIDACION PARA CONTROLAR SI ES QUE SE HA CERRADO SESION O SIGUE ACTIVA 
        if (idUsuario == null || idUsuario.isEmpty()) { // LA SESION ESTA CERRADA, ENTONCES CORTARIA LA CONTINUACION DEL CODIGO Y LE MANDO A LOGEARSE AL USUARIO 
//        if (metodosIniSes.recuperarValorMacAddress() == false) { // LA SESION ESTA CERRADA, ENTONCES CORTARIA LA CONTINUACION DEL CODIGO Y LE MANDO A LOGEARSE AL USUARIO 
            System.out.println("[-]__1__---SE_HA_CERRADO_SESION----");
            
            try {
                // SI ES QUE NO TIENE EL IDUSUARIO ENTONCES CONTROLARIA SI ES QUE POSEE EL IDPERSONA O IDDOCENTE QUE SE PASA POR MEDIO DEL PARAMETRO Y ENTONCES RECUPERARIA NOMAS EL IDUSUARIO YA QUE ESTE SE CARGA UNA VEZ NOMAS Y PUEDE SER QUE SE DESECHE RAPIDAMENTE MIENTRAS QUE EL IDPERSONA O IDDOCENTE ES PASADO CONSTANTEMENTE DE PAGINA EN PAGINA QUE POR ESO SE MANTIENE MAS TIEMPO EN MEMORIA QUE EL IDUSUARIO 
                if (sesionDatosUsuario.getAttribute("IDPERSONA").equals("null") || sesionDatosUsuario.getAttribute("IDPERSONA").equals("") || sesionDatosUsuario.getAttribute("IDPERSONA") == null) {
                    valor = false;
                } else { // EN TODO CASO SI ES QUE EL IDPERSONA ESTE VACIO ESO SIGNIFICARIA QUE ESTUVO MUCHO TIEMPO INACTIVO Y LA SESION SE CERRO Y EN ESE CASO YA DEVOLVERIAMOS FALSE NOMAS YA 
                    idUsuario = traerIdUsuarioLogin(request); // SI EL IDPERSONA ESTA CARGADO ENTONCES RECUPERARIA EL IDUSUARIO 
                    sesionDatosUsuario.setAttribute("IDUSUARIO", idUsuario); // LUEGO DE RECUPERAR EL VALOR ENTONCES CARGARIA Y SOBREESCRIBO EL VALOR NULO QUE TIENE 
                    valor = true; // Y UNA VEZ RECUPERADO EL IDUSUARIO Y CARGADO A LA SESION ENTONCES DEVOLVERIA VALOR TRUE YA QUE LA SIGUE ACTIVO EL LOGEO 
                }
            } catch(NullPointerException e) {
                System.out.println("[-]__CATCH()___controlLogeoUsuarioActivo()_________NULL_POINTER_EXCEPTION___");
                valor = false; // SI DA ERROR POR NULLPOINTER ENTONCES QUIERE DECIR QUE 
            }
            
        } else { // SI EL VALOR ES TRUE ENTONCES LA SESION SE ENCUENTRA ABIERTA 
            System.out.println("[+]__2__---LA_SESION_SIGUE_ABIERTA----");
            valor = true;
        } // END ELSE IF SESION ACTIVA 
        System.out.println("[*]--------__END__---------__CTRL_CONTROL_LOGEO_DEL_USUARIO_ACTIVO__--------------------------");
        // -------------------------------------------------------------------------------------------------------------------------- 
        
        return valor;
    } // END METHOD 
    
    
//    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value) {
////    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain, String path) {
//        System.out.println("[*] setCookie() --------------- ["+name+"] = ["+value+"] --------------");
//        // recuperamos configuraciones 
//        ConfigCookie confCookie = new ConfigCookie();
//        int maxAge = confCookie.getCC_MAX_AGE();
//        String domain = confCookie.getCC_DOMAIN();
//        String path = confCookie.getCC_PATH();
//        // cargamos la cookie 
//        Cookie cookie = new Cookie(name, value);
//        cookie.setMaxAge(maxAge);
//        cookie.setHttpOnly(false);
//        cookie.setDomain(domain);
//        cookie.setPath(path);
//        response.addCookie(cookie);
//        return response;
//    }
//    
//    public static Cookie getCookie(HttpServletRequest request, String name) {
//        System.out.println("[*] getCookie() -----------------------------");
//        Cookie[] cookies = request.getCookies();
//        System.out.println("Number of cookies: " + cookies.length);
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals(name)) {
//                    return cookie;
//                }
//            }
//        }
//        return null;
//    }
    
    
    
    
    public List controlUsuario(Object obj) {
        List<BeanUsuario> listaUsuario = new ArrayList<>();
        BeanInicioSesion iniSesion = (BeanInicioSesion) obj;
        
        String USUARIO = iniSesion.getLOGIN_USER();
        System.out.println("__controlUsuario()______USUARIO:     "+USUARIO);
        String PASSWORD = iniSesion.getLOGIN_PASSWORD();
        System.out.println("__controlUsuario()______  CLAVE:     "+PASSWORD);
        String PASS = pass_clave();
        try {
            String sql = "SELECT * FROM sys_usuario \n"
                + "WHERE USUARIO = ? \n"
//                + "AND CLAVE = ? \n"
                + "AND CLAVE = AES_ENCRYPT(?,?) \n"
                + "AND ESTADO = 'A' \n";
            
//            String sql = "SELECT * FROM sys_usuario \n"
//                + "WHERE USUARIO = '"+USUARIO+"' \n"
//                + "AND CLAVE = AES_ENCRYPT('"+PASSWORD+"','"+PASS+"') \n"
//                + "AND ESTADO = 'A' \n";
            
//            String sql = "SELECT * FROM sys_usuario \n"
//                + "WHERE USUARIO = '"+USUARIO+"' \n"
//                + "AND CLAVE = '"+PASSWORD+"' \n"
//                + "AND ESTADO = 'A' \n";

//            System.out.println("-----controlUsuario()------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);
            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MIS_RESULTADO = consultaBD(sql);
            
            Connection con = devolverConex();
            PreparedStatement sen = con.prepareStatement(sql);
            sen.setString(1, USUARIO);
            sen.setString(2, PASSWORD);
            sen.setString(3, PASS);
            MIS_RESULTADO = sen.executeQuery();
            
            if(MIS_RESULTADO.next() == true){
                BeanUsuario usu = new BeanUsuario();
                    usu.setSU_USUARIO(MIS_RESULTADO.getString("USUARIO"));
                    usu.setSU_IDPERSONA(MIS_RESULTADO.getString("IDPERSONA"));
                    usu.setSU_IDUSUARIO(MIS_RESULTADO.getString("IDUSUARIO"));
                listaUsuario.add(usu);
            } else {
                listaUsuario = null; 
            }
            cerrarConexiones();
        } catch (SQLException e) {
            System.out.println("---__INIT__-----------__controlUsuario()__--------------ModelInicioSesion-----");
            System.out.println(e.getMessage());
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("---__END__------------__controlUsuario()__--------------ModelInicioSesion-----");
        }
        return listaUsuario;
    } // end method 
    
    
    public BeanPersona controlUsuarioPer(Object obj) {
        System.out.println("[*]_init:------------------------controlUsuarioPer()----------------------------------------");
        BeanPersona persona = new BeanPersona();
        BeanInicioSesion iniSesion = (BeanInicioSesion) obj;
        
        String USUARIO = iniSesion.getLOGIN_USER();
        System.out.println("__controlUsuario()______USUARIO:     "+USUARIO);
        String PASSWORD = iniSesion.getLOGIN_PASSWORD();
        System.out.println("__controlUsuario()______  CLAVE:     "+PASSWORD);
        String PASS = pass_clave();
        try {
            String sql = "SELECT su.IDUSUARIO, su.IDPERSONA, su.USUARIO, su.IDPERFIL, su.ESTADO, \n" +
                "rh.NOMBRE, rh.APELLIDO, rh.CINRO, rh.IDCATEGORIA_PERSONA, rh.DESC_CATEG_PERSONA, rh.TELFMOVIL, rh.TELFPARTICULAR, rh.EMAIL, rh.SEXO, rh.IDCLINICA, \n" +
                "sp.PERFIL AS DESC_PERFIL, sp.IDMENU, sp.ESTADO AS ESTADO_PERFIL, oc.DESC_CLINICA \n" +
                "FROM sys_usuario su \n" +
                "JOIN rh_persona rh ON rh.IDPERSONA = su.IDPERSONA \n" + 
                "LEFT JOIN sys_perfil sp ON su.IDPERFIL = sp.IDPERFIL \n" + 
                "LEFT JOIN ope_clinica oc ON rh.IDCLINICA = oc.IDCLINICA  \n"
                + "WHERE su.USUARIO = ? \n"
//                + "AND CLAVE = ? \n"
                + "AND su.CLAVE = AES_ENCRYPT(?,?) \n"
                + "AND su.ESTADO = 'A' \n";
            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MIS_RESULTADO = consultaBD(sql);
            
            Connection con = devolverConex();
            PreparedStatement sen = con.prepareStatement(sql);
            sen.setString(1, USUARIO);
            sen.setString(2, PASSWORD);
            sen.setString(3, PASS);
            MIS_RESULTADO = sen.executeQuery();
            
            if(MIS_RESULTADO.next() == true){
                // USUARIO 
                persona.setSU_USUARIO(MIS_RESULTADO.getString("USUARIO"));
                persona.setSU_IDPERSONA(MIS_RESULTADO.getString("IDPERSONA"));
                persona.setSU_IDUSUARIO(MIS_RESULTADO.getString("IDUSUARIO"));
                persona.setSU_IDPERFIL(MIS_RESULTADO.getString("IDPERFIL"));
                persona.setSU_ESTADO(MIS_RESULTADO.getString("ESTADO"));
                // PERSONA 
                persona.setRP_NOMBRE(MIS_RESULTADO.getString("NOMBRE"));
                persona.setRP_APELLIDO(MIS_RESULTADO.getString("APELLIDO"));
                persona.setRP_CINRO(MIS_RESULTADO.getString("CINRO"));
                persona.setRP_IDCATEGORIA_PERSONA(MIS_RESULTADO.getString("IDCATEGORIA_PERSONA"));
                persona.setRP_DESC_CATEG_PERSONA(MIS_RESULTADO.getString("DESC_CATEG_PERSONA"));
                persona.setRP_TELFMOVIL(MIS_RESULTADO.getString("TELFMOVIL"));
                persona.setRP_TELFPARTICULAR(MIS_RESULTADO.getString("TELFPARTICULAR"));
                persona.setRP_EMAIL(MIS_RESULTADO.getString("EMAIL"));
                persona.setRP_SEXO(MIS_RESULTADO.getString("SEXO"));
                persona.setRP_IDCLINICA(MIS_RESULTADO.getString("IDCLINICA"));
                // PERFIL 
                persona.setSP_IDPERFIL(MIS_RESULTADO.getString("IDPERFIL"));
                persona.setSP_NOMBRE(MIS_RESULTADO.getString("DESC_PERFIL"));
                persona.setSP_IDMENU(MIS_RESULTADO.getString("IDMENU"));
                persona.setSP_ESTADO(MIS_RESULTADO.getString("ESTADO_PERFIL"));
                // CLINICA 
                persona.setRP_AUX_DESC_CLINICA(MIS_RESULTADO.getString("DESC_CLINICA"));
            }
            cerrarConexiones();
        } catch (SQLException e) {
            System.out.println("[-]__INIT__-----------__controlUsuarioPERSONA()__--------------ModelInicioSesion-----");
            System.out.println(e.getMessage());
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("[-]__END__------------__controlUsuarioPERSONA()__--------------ModelInicioSesion-----");
        }
        System.out.println("[*]_end:------------------------controlUsuarioPer()----------------------------------------");
        return persona;
    } // end method 
    
    
    
    
    
    
    
    
    
// METODO QUE UTILIZO PARA TRAER EL IDUSUARIO DEL USUARIO QUE SE LOGEO 
    public String traerIdUsuarioLogin(HttpServletRequest request) {
        String valor = "";
        
        try {
            HttpSession sesionDatosUsuario = request.getSession(); // SESION ACTIVA PARA RECUPERAR LOS DATOS 
            String IDPERSONA = (String) sesionDatosUsuario.getAttribute("IDPERSONA");
            
            String sql = "SELECT IDUSUARIO, IDPERSONA, USUARIO, CLAVE, ESTADO, \n" +
                "IDPERFIL, ESTADO_MIGRAR, WEB, EMAIL \n" +
                "FROM sys_usuario \n" +
                "WHERE IDPERSONA = '"+IDPERSONA+"' \n" +
                "AND ESTADO = 'A' ";
//            System.out.println("---------traerIdUsuarioLogin()------------------SQL-----------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while (MIS_RESULTADO.next()) {
                valor = MIS_RESULTADO.getString("IDUSUARIO");
            }
            cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("__traerIdUsuarioLogin()______valor:       "+valor);
        return valor;
    } // end method 
    
    
    
// CONTROLAMOS EL ESTADO_MIGRAR EN LA BASE DE DATOS PARA PODER PERMITIR O NEGAR EL ACCESO AL SISTEMA 
    public boolean ctrlEstadoMigrar(String usuario, String password) {
        try {
            String sql = "SELECT ESTADO_MIGRAR "
                + "FROM sys_usuario "
                + "WHERE USUARIO = '"+usuario+"' "
                + "AND CLAVE = '"+password+"' ";
//            System.out.println("-------ctrlEstadoMigrar()-------------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while (MIS_RESULTADO.next()) {
                String estado = MIS_RESULTADO.getString("ESTADO_MIGRAR");
                if (estado.equals("SI") || estado.equals("si")) {
                    return true;
                }
            }
            cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    } // end method 
    
    
    
// CAMBIAMOS EL VALOR DE LA COLUMNA "WEB" DE USUARIO PARA PODER LUEGO PREGUNTAR POR ELLA, SI EL USUARIO SE LOGEA COLOCAMOS 1 (UNO) Y SI CIERRA SESION, COLOCAMOS 0(CERO), ENTONCES SI LLEGA A CERRAR SESION, Y LUEGO APRIETA EL BOTON DE VOLVER ATRAS DE LOS NAVEGADORES CONTROLAREMOS EL VALOR DE ESTA COLUMNA PARA DEJARLE ENTRAR O NO 
    public boolean updateWebLogeo(int accion, String idalumno, String idusuario) {
        try {
            String sql;
        // deacuerdo al valor que ingrese actualizamos el valor de la columna 
            if (accion == 1) { // inicio sesion 
                sql = "UPDATE sys_usuario SET WEB = 1 "
                        + "WHERE IDPERSONA = '"+idalumno+"' AND IDUSUARIO = '"+idusuario+"' ";
            } else { // cerro su sesion 
                sql = "UPDATE sys_usuario SET WEB = 0 "
                        + "WHERE IDPERSONA = '"+idalumno+"' AND IDUSUARIO = '"+idusuario+"' ";
            }
            System.out.println("---------webLogeo()----------SQL----------------    "+sql);
            MIS_CONEXION = devolverConex();
            MIS_SENTENCIA = MIS_CONEXION.createStatement();
            MIS_SENTENCIA.executeUpdate(sql);
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
        return true;
    } // end method 
    
    
//    
//// METODO PARA TRAER EL IDPERFIL QUE TIENE EL USUARIO PARA PODER MOSTRARLE UN MENU 
//    public String traerIdPerfilUsu(String IDUSUARIO){
//        String valor = "";
//        
//        try {
//            String sql = "SELECT su.IDPERFIL, sp.PERFIL AS DESC_PERFIL \n" +
//                "FROM sys_usuario su \n" +
//                "LEFT JOIN sys_perfil sp ON su.IDPERFIL = sp.IDPERFIL \n" +
//                "WHERE su.IDUSUARIO = '"+IDUSUARIO+"' ";
//            System.out.println("----traerIdPerfilUsu()--------------"+sql);
////            conn.sentencia = conn.conexion.createStatement();
////            conn.resultado = conn.sentencia.executeQuery(sql);
//            
//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MIS_RESULTADO = consultaBD(sql);
//            
//            if (MIS_RESULTADO.next() == true) {
//                valor = MIS_RESULTADO.getString("IDPERFIL");
//                System.out.println("IDPERFIL:       "+valor);
//                System.out.println("DESC_PERFIL:    "+MIS_RESULTADO.getString("DESC_PERFIL"));
//            }
//            cerrarConexiones();
//        } catch (SQLException | NullPointerException e) {
//            System.out.println("-----__ERROR__----------------_ModelInicioSesion_-------------------");
//            System.out.println("---traerIdPerfilUsu()-------catch("+e.getMessage()+")----");
//            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
//            System.out.println("--------------------------------------------------------------------");
//        }
//        return valor;
//    } // END METHOD 
//    
    
    
    
// METODO QUE UTILIZO PARA TRAER EL USUARIO DE LA TABLA DE USUARIO 
    public String traerUsuario(String PARAM_IDPERSONA) {
        String valor = "";
        
        try {
            String sql = "SELECT IDUSUARIO, IDPERSONA, USUARIO, CLAVE, ESTADO, \n" +
                "IDPERFIL, ESTADO_MIGRAR, WEB, EMAIL \n" +
                "FROM sys_usuario \n" +
                "WHERE IDPERSONA = '"+PARAM_IDPERSONA+"' \n" +
                "AND ESTADO = 'A' ";
//            System.out.println("-----traerIdUsuario()------------SQL--------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while (MIS_RESULTADO.next()) {
                valor = MIS_RESULTADO.getString("USUARIO");
            }
            cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("__traerIdUsuarioMacAddress()______valor:       "+valor);
        return valor;
    } // end method 
    
    
    
// METODO QUE UTILIZO PARA TRAER EL PASSOWRD O CLAVE DE LA TABLA DE USUARIO 
    public String traerPassword(String PARAM_IDPERSONA) {
        String valor = "";
        String PASS = pass_clave();
        try {
            String sql = "SELECT IDUSUARIO, IDPERSONA, USUARIO, COALESCE(CONVERT(AES_DECRYPT(CLAVE,'"+PASS+"')USING UTF8),'') AS CLAVE, ESTADO, \n" +
                "IDPERFIL, ESTADO_MIGRAR, WEB, EMAIL \n" +
                "FROM sys_usuario \n" +
                "WHERE IDPERSONA = '"+PARAM_IDPERSONA+"' \n" +
                "AND ESTADO = 'A' ";
//            String sql = "SELECT IDUSUARIO, IDPERSONA, USUARIO, CLAVE, ESTADO, \n" +
//                "IDPERFIL, ESTADO_MIGRAR, WEB, EMAIL \n" +
//                "FROM sys_usuario \n" +
//                "WHERE IDPERSONA = '"+PARAM_IDPERSONA+"' \n" +
//                "AND ESTADO = 'A' ";

//            System.out.println("----traerPassword()-------------SQL------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while (MIS_RESULTADO.next()) {
                valor = MIS_RESULTADO.getString("CLAVE");
            }
            cerrarConexiones();
        } catch (SQLException ex) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("__traerPasswordMacAddress()______valor:       "+valor);
        return valor;
    } // end method 
    
    
    
    public String traerFechaHoy() {
        String valor;
        
        Calendar calFecha = new GregorianCalendar();
        String mes1, dia1;
        int anho = calFecha.get(Calendar.YEAR);
        int mes = calFecha.get(Calendar.MONTH)+1;
        mes1 = String.valueOf(mes);
        if (mes < 10) {
            mes = Integer.parseInt(String.valueOf("0"+mes));
            mes1 = "0"+mes;
        }
        int dia = calFecha.get(Calendar.DAY_OF_MONTH);
        dia1 = String.valueOf(dia);
        if (dia < 10) {
            dia = Integer.parseInt(String.valueOf("0"+dia));
            dia1 = "0"+dia;
        }
        valor = ""+anho+"-"+mes1+"-"+dia1+"";
//        valor = ""+anho+"-"+mes+"-"+dia+"";
//        System.out.println("__FECHA:    "+valor);
        
        return valor;
    } // end method 
    
    
    
    public String traerFechaHoraHoy(){
        String valor;
        
        Calendar calFecHora = new GregorianCalendar();
        int anho = calFecHora.get(Calendar.YEAR);
        int mes = calFecHora.get(Calendar.MONTH)+1;
        if (mes < 10) {
            mes = Integer.parseInt(String.valueOf("0"+mes));
        }
        int dia = calFecHora.get(Calendar.DAY_OF_MONTH);
        if (dia < 10) {
            dia = Integer.parseInt(String.valueOf("0"+dia));
        }
        int hora = calFecHora.get(Calendar.HOUR_OF_DAY);
        int minutos = calFecHora.get(Calendar.MINUTE);
        int segundos = calFecHora.get(Calendar.SECOND);
        valor = ""+anho+"-"+mes+"-"+dia+" "+hora+":"+minutos+":"+segundos+"";
//        System.out.println("__FECHA_HORA:   "+valor);
        
        return valor;
    } // end method 
    
    
    public String getHoraHoy(){
        String valor;
        
        Calendar calFecHora = new GregorianCalendar();
//        int anho = calFecHora.get(Calendar.YEAR);
//        int mes = calFecHora.get(Calendar.MONTH)+1;
//        if (mes < 10) {
//            mes = Integer.parseInt(String.valueOf("0"+mes));
//        }
//        int dia = calFecHora.get(Calendar.DAY_OF_MONTH);
//        if (dia < 10) {
//            dia = Integer.parseInt(String.valueOf("0"+dia));
//        }
        int hora = calFecHora.get(Calendar.HOUR_OF_DAY);
        int minutos = calFecHora.get(Calendar.MINUTE);
        int segundos = calFecHora.get(Calendar.SECOND);
        valor = ""+hora+":"+minutos+":"+segundos+"";
//        System.out.println("__FECHA_HORA:   "+valor);
        
        return valor;
    } // end method 
    
    
    public String getHoraMinHoy(){
        String valor;
        
        Calendar calFecHora = new GregorianCalendar();
//        int anho = calFecHora.get(Calendar.YEAR);
//        int mes = calFecHora.get(Calendar.MONTH)+1;
//        if (mes < 10) {
//            mes = Integer.parseInt(String.valueOf("0"+mes));
//        }
//        int dia = calFecHora.get(Calendar.DAY_OF_MONTH);
//        if (dia < 10) {
//            dia = Integer.parseInt(String.valueOf("0"+dia));
//        }
        int hora = calFecHora.get(Calendar.HOUR_OF_DAY);
        int minutos = calFecHora.get(Calendar.MINUTE);
        if (minutos < 10) {
            minutos = Integer.parseInt(minutos+"0");
        }
//        int segundos = calFecHora.get(Calendar.SECOND);
        valor = hora+":"+minutos;
//        System.out.println("__FECHA_HORA:   "+valor);
        
        return valor;
    } // end method 
    
    
    
    /*
    OBSERVACION: 
        UTILIZO ESTE METODO PARA CADA MODELO, PARA PODER FILTRAR EL CARGA GRILLA Y ASI ESTANDARIZAR 
        EL LIMITE DE REGISTRO QUE DEBE DE TRAER DE LA BASE, USO ESTE METODO PARA CARGAR EL VALOR MINIMO 
        Y ASI CUANDO QUIERA VOLVER A CAMBIAR NO TENDRIA QUE IR CAMBIANDO CLASE POR CLASE, SINO DESDE ESTE METODO NOMAS YA LO HARIA 
    */
    public String minNroCbxCantFil() {
        String valor = "10";
        return valor;
    } // end method 
    
    
    
    /*
    METODO PARA CARGAR EL COMBO BOX QUE USO COMO FILTRO PARA LIMITAR LA CANTIDAD DE REGISTROS PARA TRAER (EN LOS JSP)
    */
    public Map<String, String> cargarFilLimitReg(Map<String, String> cbxFilterLimit, String paramNroFiltro) {
        String id, value; // EN CASO DE QUE OBTENIERA DE LA BASE, EL ID LO UTILIZARIA PARA CARGAR DICHO ID Y ASI EL COMBO TENDRA EL VALOR DEL ID OCULTO, LO COLOCO AUNQUE POR EL MOMENTO NO LO USO PORQUE EL VALOR OCULTO ES EL VALOR QUE SE VE TAMBIEN YA QUE POR EL MOMENTO NO TRAIGO DE LA BASE 
        
        // SI LA VARIABLE "paramNroFiltro" SE ENCUENTRA VACIO ENTONCES EL USUARIO NO FILTRO POR NINGUN DATO DEL COMBO Y CARGARIA EL COMBO POR SU ORDEN NORMAL 
        if (paramNroFiltro == null || paramNroFiltro.isEmpty() || paramNroFiltro.equals(" ")) {
            id = "10";
            value = "10";
            cbxFilterLimit.put(id, value);
            id = "20";
            value = "20";
            cbxFilterLimit.put(id, value);
            id = "50";
            value = "50";
            cbxFilterLimit.put(id, value);
            id = "TODOS";
            value = "TODOS";
            cbxFilterLimit.put(id, value);
            
        } else { // SI NO SE ENCONTRACE VACIO, ENTONCES TENDRIA QUE PREGUNTAR POR LOS VALORES ESTABLECIDOS PARA PODER SABER CUAL DE ELLOS FUE EL QUE SE FILTRO Y ASI COLOCARLO EN PRIMER LUEGAR PARA QUE SE MUESTRE Y LOS DEMAS EN EL ORDEN SIGUIENTE 
            if (paramNroFiltro.equalsIgnoreCase("10")) {
                id = "10";
                value = "10";
                cbxFilterLimit.put(id, value);
                id = "20";
                value = "20";
                cbxFilterLimit.put(id, value);
                id = "50";
                value = "50";
                cbxFilterLimit.put(id, value);
                id = "TODOS";
                value = "TODOS";
                cbxFilterLimit.put(id, value);
            } else if(paramNroFiltro.equalsIgnoreCase("20")) {
                id = "20";
                value = "20";
                cbxFilterLimit.put(id, value);
                id = "10";
                value = "10";
                cbxFilterLimit.put(id, value);
                id = "50";
                value = "50";
                cbxFilterLimit.put(id, value);
                id = "TODOS";
                value = "TODOS";
                cbxFilterLimit.put(id, value);
            } else if(paramNroFiltro.equalsIgnoreCase("50")) {
                id = "50";
                value = "50";
                cbxFilterLimit.put(id, value);
                id = "10";
                value = "10";
                cbxFilterLimit.put(id, value);
                id = "20";
                value = "20";
                cbxFilterLimit.put(id, value);
                id = "TODOS";
                value = "TODOS";
                cbxFilterLimit.put(id, value);
            } else if(paramNroFiltro.equalsIgnoreCase("TODOS")) {
                id = "TODOS";
                value = "TODOS";
                cbxFilterLimit.put(id, value);
                id = "10";
                value = "10";
                cbxFilterLimit.put(id, value);
                id = "20";
                value = "20";
                cbxFilterLimit.put(id, value);
                id = "50";
                value = "50";
                cbxFilterLimit.put(id, value);
            }
        }
        return cbxFilterLimit;
    } // end method 
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE ESTADO (ACTIVO / INACTIVO) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboEstado(String paramEstado) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramEstado == null || paramEstado.isEmpty() || paramEstado.equalsIgnoreCase("0") || paramEstado.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("A", "ACTIVO");
            lista.put("I", "INACTIVO");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramEstado.equalsIgnoreCase("ACTIVO") || paramEstado.equalsIgnoreCase("A")) {
                lista.put("A", "ACTIVO");
                lista.put("I", "INACTIVO");
            } else {
                lista.put("I", "INACTIVO");
                lista.put("A", "ACTIVO");
            }
        }
        return lista;
    } // end method 
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE TURNOS (MAÑANA / TARDE / NOCHE) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboTurnos(String paramTurno) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramTurno == null || paramTurno.isEmpty() || paramTurno.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("M", "MAÑANA");
            lista.put("T", "TARDE");
            lista.put("N", "NOCHE");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramTurno.equalsIgnoreCase("M") || paramTurno.equalsIgnoreCase("MAÑANA")) {
                lista.put("M", "MAÑANA");
                lista.put("T", "TARDE");
                lista.put("N", "NOCHE");
            } else if (paramTurno.equalsIgnoreCase("T") || paramTurno.equalsIgnoreCase("TARDE")) {
                lista.put("T", "TARDE");
                lista.put("M", "MAÑANA");
                lista.put("N", "NOCHE");
            } else {
                lista.put("N", "NOCHE");
                lista.put("M", "MAÑANA");
                lista.put("T", "TARDE");
            }
        }
        return lista;
    } // end method 
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE LOS DIAS (LUNES / MARTES / MIERCOLES / JUEVES / VIERNES / SABADO / DOMINGO) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboDias(String paramDias) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramDias == null || paramDias.isEmpty() || paramDias.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("LUNES", "LUNES");
            lista.put("MARTES", "MARTES");
            lista.put("MIERCOLES", "MIERCOLES");
            lista.put("JUEVES", "JUEVES");
            lista.put("VIERNES", "VIERNES");
            lista.put("SABADO", "SABADO");
            lista.put("DOMINGO", "DOMINGO");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramDias.equalsIgnoreCase("L") || paramDias.equalsIgnoreCase("LUNES")) {
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
            } else if (paramDias.equalsIgnoreCase("M") || paramDias.equalsIgnoreCase("MARTES")) {
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
            } else if (paramDias.equalsIgnoreCase("Mier") || paramDias.equalsIgnoreCase("MIERCOLES")) {
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
            } else if (paramDias.equalsIgnoreCase("J") || paramDias.equalsIgnoreCase("JUEVES")) {
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
            } else if (paramDias.equalsIgnoreCase("V") || paramDias.equalsIgnoreCase("VIERNES")) {
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
            } else if (paramDias.equalsIgnoreCase("S") || paramDias.equalsIgnoreCase("SABADO")) {
                lista.put("SABADO", "SABADO");
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
            } else {
                lista.put("DOMINGO", "DOMINGO");
                lista.put("LUNES", "LUNES");
                lista.put("MARTES", "MARTES");
                lista.put("MIERCOLES", "MIERCOLES");
                lista.put("JUEVES", "JUEVES");
                lista.put("VIERNES", "VIERNES");
                lista.put("SABADO", "SABADO");
            }
        }
        return lista;
    } // end method 
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE SEXO (NO_DEFINIDO / MASCULINO / FEMENINO) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboSexoPer(String paramSexo) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramSexo == null || paramSexo.isEmpty() || paramSexo.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("N", "(NO/DEFINIDO)");
            lista.put("M", "Masculino");
            lista.put("F", "Femenino");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramSexo.equalsIgnoreCase("M") || paramSexo.equalsIgnoreCase("Masculino")) {
                lista.put("M", "Masculino");
                lista.put("F", "Femenino");
                lista.put("N", "(NO/DEFINIDO)");
            } else if (paramSexo.equalsIgnoreCase("F") || paramSexo.equalsIgnoreCase("Femenino")) {
                lista.put("F", "Femenino");
                lista.put("M", "Masculino");
                lista.put("N", "(NO/DEFINIDO)");
            } else {
                lista.put("N", "(NO/DEFINIDO)");
                lista.put("M", "Masculino");
                lista.put("F", "Femenino");
            }
        }
        return lista;
    } // end method 
    
    
    public String getNameSexoPer(String PARAM_SEXO) {
        if (PARAM_SEXO == null || PARAM_SEXO.equalsIgnoreCase("ND") || PARAM_SEXO.equalsIgnoreCase("N") || PARAM_SEXO.isEmpty()) {
            return "(NO/DEFINIDO)";
        } else if(PARAM_SEXO.equalsIgnoreCase("F")) {
            return "Femenino";
        } else if(PARAM_SEXO.equalsIgnoreCase("M")) {
            return "Masculino";
        } else {
            return PARAM_SEXO;
        }
    }
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE ESTADO CIVIL ( NO_DEFINIDO / SOLTERO / CASADO / SEPARADO / VIUDO / MINORIA DE EDAD / MAYORIA DE EDAD / CONCUBINATO / UNIÓN DE HECHO ) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboEstadoCivil(String paramSexo) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramSexo == null || paramSexo.isEmpty() || paramSexo.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("ND", "(NO/DEFINIDO)");
            lista.put("SO", "Soltero/a");
            lista.put("CA", "Casado/a");
            lista.put("SE", "Separado/a");
            lista.put("CO", "Concubinado/a");
//            lista.put("AC", "Acompañado/a");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramSexo.equalsIgnoreCase("SO") || paramSexo.equalsIgnoreCase("SOLTERO") || paramSexo.equalsIgnoreCase("SOLTERO/A")) {
                lista.put("SO", "Soltero/a");
                lista.put("CA", "Casado/a");
                lista.put("SE", "Separado/a");
                lista.put("CO", "Concubinado/a");
//                lista.put("AC", "Acompañado/a");
                lista.put("ND", "(NO/DEFINIDO)");
            } else if (paramSexo.equalsIgnoreCase("CA") || paramSexo.equalsIgnoreCase("CASADO") || paramSexo.equalsIgnoreCase("CASADO/A")) {
                lista.put("CA", "Casado/a");
                lista.put("SE", "Separado/a");
                lista.put("CO", "Concubinado/a");
//                lista.put("AC", "Acompañado/a");
                lista.put("ND", "(NO/DEFINIDO)");
                lista.put("SO", "Soltero/a");
            } else if (paramSexo.equalsIgnoreCase("SE") || paramSexo.equalsIgnoreCase("SEPARADO") || paramSexo.equalsIgnoreCase("SEPARADO/A")) {
                lista.put("SE", "Separado/a");
                lista.put("CO", "Concubinado/a");
//                lista.put("AC", "Acompañado/a");
                lista.put("ND", "(NO/DEFINIDO)");
                lista.put("SO", "Soltero/a");
                lista.put("CA", "Casado/a");
            } else if (paramSexo.equalsIgnoreCase("CO") || paramSexo.equalsIgnoreCase("CONCUBINADO") || paramSexo.equalsIgnoreCase("CONCUBINADO/A")) {
                lista.put("CO", "Concubinado/a");
//                lista.put("AC", "Acompañado/a");
                lista.put("ND", "(NO/DEFINIDO)");
                lista.put("SO", "Soltero/a");
                lista.put("CA", "Casado/a");
                lista.put("SE", "Separado/a");
//            } else if (paramSexo.equalsIgnoreCase("AC") || paramSexo.equalsIgnoreCase("ACOMPAÑADO") || paramSexo.equalsIgnoreCase("ACOMPAÑADO/A")) {
//                lista.put("AC", "Acompañado/a");
//                lista.put("ND", "(NO/DEFINIDO)");
//                lista.put("SO", "Soltero/a");
//                lista.put("CA", "Casado/a");
//                lista.put("SE", "Separado/a");
//                lista.put("CO", "Concubinado/a");
            } else {
                lista.put("ND", "(NO/DEFINIDO)");
                lista.put("SO", "Soltero/a");
                lista.put("CA", "Casado/a");
                lista.put("SE", "Separado/a");
                lista.put("CO", "Concubinado/a");
//                lista.put("AC", "Acompañado/a");
            }
        }
        return lista;
    } // end method 
    
    
    
    public String getPacEstadoCivil(String PARAM_ESTADOCIVIL) {
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("____PARAM_ESTADO_CIVIL:  "+PARAM_ESTADOCIVIL);
        
        if (PARAM_ESTADOCIVIL == null || PARAM_ESTADOCIVIL.equalsIgnoreCase("N") || PARAM_ESTADOCIVIL.equalsIgnoreCase("ND")) {
            return "(NO/DEFINIDO)";
        } else if (PARAM_ESTADOCIVIL.equalsIgnoreCase("SO")) {
            return "Soltero/a";
        } else if (PARAM_ESTADOCIVIL.equalsIgnoreCase("CA")) {
            return "Casado/a";
        } else if (PARAM_ESTADOCIVIL.equalsIgnoreCase("SE")) {
            return "Separado/a";
        } else if (PARAM_ESTADOCIVIL.equalsIgnoreCase("CO")) {
            return "Concubinado/a";
        } else {
            return "";
        }
    } // end method 
    
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE IVA (exenta / 5 / 10) QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboIVA(String paramIva) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramIva == null || paramIva.isEmpty() || paramIva.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRE QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("0", "exento");
            lista.put("5", "5%");
            lista.put("10", "10%");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramIva.equalsIgnoreCase("10") || paramIva.equalsIgnoreCase("10%")) {
                lista.put("10", "10%");
                lista.put("5", "5%");
                lista.put("0", "exento");
            } else if (paramIva.equalsIgnoreCase("5") || paramIva.equalsIgnoreCase("5%")) {
                lista.put("5", "5%");
                lista.put("10", "10%");
                lista.put("0", "exento");
            } else {
                lista.put("0", "exento");
                lista.put("10", "10%");
                lista.put("5", "5%");
            }
        }
        return lista;
    } // end method 
    
    
    
    /* OBSERVACION DEL METODO: 
        METODO QUE UTILIZO PARA PODER CARGAR EL TEXTO DEL ESTADO, YA QUE YO GUARDO LA INICIAL NOMAS 
        CUANDO QUIERA MOSTRAR DEBO DE MOSTRAR EL TEXTO Y NO LA INICIAL, ENTONCES PARA ESO UTILIZARE ESTE METODO, 
        CUALQUIER VENTANA QUE QUIERA MOSTRAR UN ESTADO PUEDE UTILIZAR ESTE METODO Y SI HAY ESTADO NUEVOS ENTONCES 
        SE LE AÑADE NOMAS AL METODO Y SERA DEACUERDO A LOS MAS UTILIZADOS, LOS PRIMEROS SON LOS MAS USADOS Y ASI IRA EL ORDEN 
        PARA QUE EL METODO TAMPOCO SE LA PASE PREGUNTANDO POR TODOS, YA QUE NORMALMENTE SOLO SON DOS ESTADOS NOMAS Y RARAS VECES SON MAS DE DOS (COMO EN EMPEÑO) 
    // OBS.: PUEDO HACER CASE EN LOS SELECT DONDE TRAIGO LOS ESTADOS, PERO PREFIERO ESTA OPCION PARA NO IR ALARGANDO LOS SELECT 
    */
    public String devolverTextEstado(String PARAM_ESTADO) {
        String estado = "";
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_____PARAM_ESTADO:  "+PARAM_ESTADO);
        
        if (PARAM_ESTADO.equalsIgnoreCase("A")) {
            estado = "Activo";
        } else if (PARAM_ESTADO.equalsIgnoreCase("X")) {
            estado = "Anulado";
        } else if (PARAM_ESTADO.equalsIgnoreCase("I")) {
            estado = "Inactivo";
//        } else if (PARAM_ESTADO.equalsIgnoreCase("V")) {
//            estado = "Vendido";
//        } else if (PARAM_ESTADO.equalsIgnoreCase("E")) {
//            estado = "Expirado";
//        } else if (PARAM_ESTADO.equalsIgnoreCase("L")) {
//            estado = "Liquidado";
        } else if (PARAM_ESTADO.equalsIgnoreCase("C")) {
            estado = "Cancelado";
        }
        return estado;
    } // end method 
    
    
    
    
    public String devolverTextEstadoAgend(String PARAM_ESTADO) {
        String estado = "";
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_____PARAM_ESTADO:  "+PARAM_ESTADO);
        
        if (PARAM_ESTADO == null || PARAM_ESTADO.isEmpty()) {
            estado = "";
        } else if (PARAM_ESTADO.equalsIgnoreCase("A")) {
            estado = "Activo";
        } else if (PARAM_ESTADO.equalsIgnoreCase("X")) {
            estado = "Anulado";
        } else if (PARAM_ESTADO.equalsIgnoreCase("I")) {
            estado = "Inactivo";
        } else if (PARAM_ESTADO.equalsIgnoreCase("P")) {
            estado = "Pendiente";
        } else if (PARAM_ESTADO.equalsIgnoreCase("C")) {
            estado = "Cerrado"; // DETALLE DEL AGENDAMIENTO 
//            estado = "Cobrado";
        }
        return estado;
    } // end method 
    
    
    /* OBSERVACION DEL METODO: 
        METODO QUE UTILIZO PARA CARGAR LAS INICIALES DE LOS ESTADO
        ESTE METODO HARIA LO CONTRARIO AL METODO: devolverTextEstado() 
    */
    public String devolverInicialEstado(String PARAM_ESTADO) {
        String estado = "";
        // COMO DEVUELVE VACIO SI ES QUE NO ENCUENTRA LA PALABRA DEL ESTADO, ENTONCES COLOQUE PARA QUE PREGUTNE TAMBIEN POR LA INICIAL PORQUE A VECES NO ME DOY CUENTA Y LE PASO LA INICIAL 
        if (PARAM_ESTADO.equalsIgnoreCase("Activo") || PARAM_ESTADO.equalsIgnoreCase("A")) {
            estado = "A";
        } else if (PARAM_ESTADO.equalsIgnoreCase("Inactivo") || PARAM_ESTADO.equalsIgnoreCase("I")) {
            estado = "I";
        } else if (PARAM_ESTADO.equalsIgnoreCase("Vendido") || PARAM_ESTADO.equalsIgnoreCase("V")) {
            estado = "V";
        } else if (PARAM_ESTADO.equalsIgnoreCase("Expirado") || PARAM_ESTADO.equalsIgnoreCase("E")) {
            estado = "E";
        } else if (PARAM_ESTADO.equalsIgnoreCase("Liquidado") || PARAM_ESTADO.equalsIgnoreCase("L")) {
            estado = "L";
        } else if (PARAM_ESTADO.equalsIgnoreCase("Cancelado") || PARAM_ESTADO.equalsIgnoreCase("C")) {
            estado = "C";
        }
        return estado;
    } // end method 
    
    
    
    public String devolverTextTurno(String PARAM_TURNO) {
        String turno = "";
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_____PARAM_TURNO:  "+PARAM_TURNO);
        
        if (PARAM_TURNO == null || PARAM_TURNO.equalsIgnoreCase("M")) {
            turno = "MAÑANA";
        } else if (PARAM_TURNO.equalsIgnoreCase("T")) {
            turno = "TARDE";
        } else if (PARAM_TURNO.equalsIgnoreCase("N")) {
            turno = "NOCHE";
        }
        return turno;
    } // end method 
    
    
    
    public String devolverTextSexo(String PARAM_SEXO) {
        String sexo = "";
        
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println(".");
//        System.out.println("_____PARAM_SEXO:  "+PARAM_SEXO);
        
        if (PARAM_SEXO == null || PARAM_SEXO.equalsIgnoreCase("N")) {
            sexo = "(No/Definido)";
        } else if (PARAM_SEXO.equalsIgnoreCase("M")) {
            sexo = "MASCULINO";
        } else if (PARAM_SEXO.equalsIgnoreCase("F")) {
            sexo = "FEMENINO";
        }
        return sexo;
    } // end method 
    
    
    
    // METODO QUE UTILIZARE MAS EN LOS REPORTES, PARA PODER CARGAR LA EMPRESA 
    public String devolverEmpresa() {
        String valor = "";
        
        try {
            String sql = "SELECT IDEMPRESA, RUC, RAZONSOCIAL, DIRECCION, TELEFONO, DENOMINACION, ESTADO \n" +
                "FROM sys_empresa \n" +
                "WHERE ESTADO = 'A' ";
            System.out.println("------------------------MODEL_INICIO_SESION--------------------------");
            System.out.println("-- --devolverEmpresa()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while(MIS_RESULTADO.next()) {
                valor = MIS_RESULTADO.getString("RAZONSOCIAL");
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA VALIDAR SI UN STRING ES UN NUMERO O SI TIENE ALGUN CARACTER O SIMBOLO DE POR MEDIO, 
    YA QUE ESTO EVITARIA PODER TRANSFORMAR UN STRING A INT 
    */
    public boolean isInteger(String PARAM_NUMERO) {
        boolean valor; // TRUE : ES NUMERO / FALSE : NO ES NUMERO 
        
        try { // SI ES NUMERO LO QUE LE PASAMOS POR PARAMETRO ENTONCES NO VA A ENTRAR EN EL CATCH 
            Integer.parseInt(PARAM_NUMERO);
            valor = true;
        } catch (NumberFormatException e) { // SI ENTRA EN EL CATCH ENTONCES NO ES NUMERO Y TIENE ALGUN SIMBOLO O CARACTER EL STRING 
            valor = false;
        }
        return valor;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER DEVOLVER ALGUN DATO DE LA FECHA QUE SE LE PASE, 
    YA SEA EL AÑO, EL MES O EL DIA DE LA FECHA, POR MEDIO DEL OTRO PARAMETRO NUMERICO 
    QUE YO MISMO LE DEFINO LOS VALORES PARA RECUPERAR UN DATO SIN NECESIDAD DE DUPLICAR EL METODO PARA OBTENER UN DATO A LA VEZ 
    */
    public String getDatoFecha(int nroDato, String PARAM_FECHA) {
        String valor = "";
        
        System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("__________________FECHA___________________________");
        System.out.println("_   PARAM_FECHA:    "+PARAM_FECHA);
        System.out.println("_   SIZE:   "+PARAM_FECHA.length());
        String anho="", mes="", dia="";
        try {
            if (PARAM_FECHA.length() == 10) { // FORMATO : xxxx-xx-xx 
                anho = PARAM_FECHA.substring(0, 4);
                mes = PARAM_FECHA.substring(5, 7);
                dia = PARAM_FECHA.substring(8, 10);

            } else if (PARAM_FECHA.length() == 11) { // FORMATO : xxxxx-xx-xx 
                anho = PARAM_FECHA.substring(0, 5);
                mes = PARAM_FECHA.substring(6, 8);
                dia = PARAM_FECHA.substring(9, 11);

            } else if (PARAM_FECHA.length() == 12) { // FORMATO : xxxxxx-xx-xx (HASTA ESTE FORMATO SE PUEDE CARGAR)
                anho = PARAM_FECHA.substring(0, 6);
                mes = PARAM_FECHA.substring(7, 9);
                dia = PARAM_FECHA.substring(10, 12);
            }
            
            // IF QUE USO PARA DEVOLVER UN DATO DE LA FECHA 
            if (nroDato == 1) { // devuelvo el año de la fecha
                valor = anho;
                System.out.println("__1_DEVOLVER_AÑO:    "+anho);
                
            } else if(nroDato == 2) { // devuelvo el mes de la fecha
                valor = mes;
                System.out.println("__2_DEVOLVER_MES:    "+mes);
                
            } else if(nroDato == 3) { // devuelvo el dia de la fecha
                valor = dia;
                System.out.println("__3_DEVOLVER_DIA:    "+dia);
                
            } else { // devuelvo la fecha completa pero en formato para mostrar al usuario en pantalla 
                valor = dia+"/"+mes+"/"+anho;
                System.out.println("__4_DEVOLVER_FECHA:  "+valor);
            }
        } catch (StringIndexOutOfBoundsException e) {
            valor = "";
        }
        System.out.println("_______________END_FECHA__________________________");
        System.out.println(".");System.out.println(".");System.out.println(".");System.out.println(".");
        return valor;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER CONVERTIR LA FECHA QUE SE LE PASE POR PARAMETRO EN FORMATO YMD : YEAR - MONTH - DAY 
    PARA PODER GUARDAR A LA BASE DE DATOS, Y NO ESTAR CONVIRTIENDO CADA FECHA EN CADA CONTROLADOR 
    Y ASI DESDE ESTE METODO NOMAS IRE CONVIRTIENDO Y DEVOLVIENDO EL STRING PARA GUARDAR A LA BASE Y QUE NO ME DE ERROR POR FORMATO INCORRECTO 
    */
    public String convertirFechaYMD(String PARAM_FECHA) {
        String valor = "";
        
        System.out.println(".");System.out.println(".");System.out.println(".");
        System.out.println("__________________FECHA________________convertirFechaYMD()___________");
        System.out.println("_   PARAM_FECHA:    "+PARAM_FECHA);
        System.out.println("_   SIZE:   "+PARAM_FECHA.length());
        String anho="", mes="", dia="";
        try {
            if (PARAM_FECHA.length() == 10) { // FORMATO : XX-XX-XXXX 
                dia = PARAM_FECHA.substring(0, 2);
                mes = PARAM_FECHA.substring(3, 5);
                anho = PARAM_FECHA.substring(6, 10);
//                anho = PARAM_FECHA.substring(0, 4);
//                mes = PARAM_FECHA.substring(5, 7);
//                dia = PARAM_FECHA.substring(8, 10);

            } else if (PARAM_FECHA.length() == 11) { // FORMATO : XX-XX-XXXX 
                dia = PARAM_FECHA.substring(0, 2);
                mes = PARAM_FECHA.substring(3, 5);
                anho = PARAM_FECHA.substring(6, 11);
//                anho = PARAM_FECHA.substring(0, 5);
//                mes = PARAM_FECHA.substring(6, 8);
//                dia = PARAM_FECHA.substring(9, 11);

            } else if (PARAM_FECHA.length() == 12) { // FORMATO : XX-XX-XXXX  (HASTA ESTE FORMATO SE PUEDE CARGAR)
                dia = PARAM_FECHA.substring(0, 2);
                mes = PARAM_FECHA.substring(3, 5);
                anho = PARAM_FECHA.substring(6, 12);
//                anho = PARAM_FECHA.substring(0, 6);
//                mes = PARAM_FECHA.substring(7, 9);
//                dia = PARAM_FECHA.substring(10, 12);
            }
            valor = anho+"-"+mes+"-"+dia;
        } catch (StringIndexOutOfBoundsException e) {
            valor = "";
        }
        System.out.println("_______________END_FECHA__________________________");
        System.out.println(".");System.out.println(".");System.out.println(".");
        return valor;
    } // end method 
    
    
    
    /**
    * Devuelve el número de dias del mes (número) pasado por parámetro
    * Si es Febrero tiene en cuenta si este año es bisiesto o no
    * Empieza por 1
    * @param mes Mes que queremos saber el número de días
     * @param paramAnho
    * @return Número de días de ese mes
    */
   public static int numeroMaxDiasMes(int mes, int paramAnho) {
        int numeroDias = -1;
        
        System.out.println("------------numero_maximo_dia_mes----------------");
        System.out.println("__MES:  "+mes);
        System.out.println("__AÑO:  "+paramAnho);
        
        switch (mes) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                numeroDias = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                numeroDias = 30;
                break;
            case 2:
//                java.util.Date anioActual = new java.util.Date();
//                Date anioActual = new Date();
                if (esBisiesto(paramAnho)) {
//                if (esBisiesto(1900 + anioActual.getYear())) {
                    numeroDias = 29;
                } else {
                    numeroDias = 28;
                }
                break;
        }
        return numeroDias;
    } // end method 
    
    
    
   /**
    * Indica si un año es bisiesto o no
    * Es bisiesto si es divisible entre 4. Pero no es bisiesto si es divisible entre 100. Pero sí es bisiesto si es divisible entre 400.
    * @param anio Año
    * @return True = es bisiesto
    */
    public static boolean esBisiesto(int anio) {
        boolean esBisiesto = false;
        GregorianCalendar calendar = new GregorianCalendar();
        if (anio % 4 == 0 && ((anio % 100 != 0) || (anio % 400 == 0))) {
//        if (calendar.isLeapYear(anio)) {
            esBisiesto = true;
        }
        return esBisiesto;
    } // end method 
    
    
    
    /* OBSERVACION: 
     *  METODO QUE UTILIZO PARA RECUPERAR LA CANTIDAD TOTAL DE FILAS DEL SELECT QUE SE VA A CONSULTAR EN LOS METODOS DONDE SE QUIERA UTILIZAR LA PAGINACION 
    */
    public String cantidad_resultado(String PARAM_SELECT) {
        String valor = "";
        
        try {
            String sql = PARAM_SELECT;
            System.out.println("------------------------MODEL_INICIO_SESION--------------------------");
            System.out.println("-- --CANTIDAD_RESULTADO()--------     "+sql);
            System.out.println("---------------------------------------------------------------");
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while(MIS_RESULTADO.next()) {
                valor = MIS_RESULTADO.getString("CANTIDAD_FILA"); // EN LOS SELECT QUE SE RECUPERA POR PARAMETRO, EL COUNT DEBE DE TENER EL MISMO NOMBRE 
                System.out.println("_   __CTRL___CANTIDAD_ROW:     :"+valor);
            }
            cerrarConexiones();
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER CONTROLAR QUE AL REGISTRARSE UN USUARIO, NO EXISTA YA EL NRO DE CI 
    */
    public boolean ctrlExisteCinro(String PARAM_CINRO, String PARAM_IDCLINICA) {
        boolean valor = false;
        try {
            String sql = "SELECT * \n" +
                "FROM rh_persona \n" +
                "WHERE CINRO = '"+PARAM_CINRO+"' \n" +
                "AND IDCLINICA = '"+PARAM_IDCLINICA+"' \n";
            System.out.println("--------------------MODEL_INICIO_SESION-----------------------");
            System.out.println("-- --ctrlExisteCinro()--------     "+sql);
            System.out.println("--------------------------------------------------------------");

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while(MIS_RESULTADO.next()==Boolean.TRUE) {
                valor = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA PODER CONTROLAR QUE AL REGISTRARSE UN USUARIO, NO EXISTA EL MISMO NOMBRE DE USUARIO 
    */
    public boolean ctrlExisteUsuario(String PARAM_USUARIO) {
        boolean valor = false;
        try {
            String sql = "SELECT * \n" +
                "FROM sys_usuario \n" +
                "WHERE USUARIO = '"+PARAM_USUARIO+"' \n";
            System.out.println("--------------------MODEL_INICIO_SESION-----------------------");
            System.out.println("-- --ctrlExisteUsuario()--------     "+sql);
            System.out.println("--------------------------------------------------------------");

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MIS_RESULTADO = consultaBD(sql);
            
            while(MIS_RESULTADO.next()==Boolean.TRUE) {
                valor = true;
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    } // end method 
    
    
    
    
    
    
    
    
    
    
    public String pass_clave() {
        String valor="", txt="";
        
        valor = "Lorem Ipsum is simply dummy text";
        /*
        [OBS] :no me recupera el contenido del archivo de texto con el sistema operativo linux, en windows me recupera todo bien.-
        */
//        try {
//            String dire = "\\opt\\sys\\aes_pass.txt"; // linux 
////            String dire = "C:\\sys\\aes_pass.txt"; // windows 
////            String dire = "C:\\OdontoAppWeb\\aes_pass.txt";
//            FileReader fr = new FileReader(dire);
//            BufferedReader bf = new BufferedReader(fr);
//            while ((txt = bf.readLine()) != null){
//                valor = txt;
//            }
//        } catch (IOException ex) {
//            valor = "error: "+System.err;
//            System.out.println("Se generó una excepción."); 
//            ex.printStackTrace(System.err);
//            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return valor;
    }
    
    
    // METODO PARA RECUPERAR DEL BLOC DE NOTAS EL PATH DONDE SE VA A GUARDAR LAS IMAGENES 
    // QUE VENDRIA A SER LA DIRECCION DEL SERVIDOR Y NO LA DIRECCION LOCAL DE ALGUNA CARPETA 
    public String getPathImgLocation() {
        String valor="", txt="";
        try {
//            String dire = "/opt/sys/path_img_server.txt"; // linux 
            String dire = "C:\\sys\\path_img_server.txt"; // windows 
//            String dire = "C:\\OdontoAppWeb\\path_img_server.txt";
            FileReader fr = new FileReader(dire);
            BufferedReader bf = new BufferedReader(fr);
            while ((txt = bf.readLine()) != null){
                valor = txt;
            }
        } catch (IOException ex) {
            valor = "error";
            System.out.println("Se generó una excepción."); 
            ex.printStackTrace(System.err);
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    
    // METODO PARA RECUPERAR DEL BLOC DE NOTAS EL PATH DONDE SE VA A GUARDAR LOS ARCHIVOS RELACIONADOS A UNA FICHA DE ATENCION DEL PACIENTE  
    // QUE VENDRIA A SER LA DIRECCION DEL SERVIDOR Y NO LA DIRECCION LOCAL DE ALGUNA CARPETA 
    public String getPathFileLocation() {
        String valor="", txt="";
        try {
//            String dire = "/opt/sys/path_file_server.txt"; // linux 
            String dire = "C:\\sys\\path_file_server.txt"; // windows 
//            String dire = "C:\\OdontoAppWeb\\path_file_server.txt";
            FileReader fr = new FileReader(dire);
            BufferedReader bf = new BufferedReader(fr);
            while ((txt = bf.readLine()) != null){
                valor = txt;
            }
        } catch (IOException ex) {
            valor = "error";
            System.out.println("Se generó una excepción."); 
            ex.printStackTrace(System.err);
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valor;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    METODO QUE UTILIZO PARA RECUPERAR EL USUARIO A TRAVES DEL NRO DE CI DE LA PERSONA PARA LA PAGINA DE RECUPERAR CONTRASEÑA 
    */
    public BeanPersona recuperarUsuarioNroCI(BeanPersona PARAM_BEAN_USU, String PARAM_NRO_CI) {
        try {
            System.out.println("_   _PARAM_NRO_CI:  :"+PARAM_NRO_CI);
            String PASS = pass_clave();
            String sql = "SELECT su.IDUSUARIO, su.IDPERSONA, rp.NOMBRE, rp.CINRO, rp.APELLIDO, su.USUARIO, rp.EMAIL AS EMAIL_PER, su.EMAIL AS EMAIL_SU, COALESCE(CONVERT(AES_DECRYPT(su.CLAVE,'"+PASS+"')USING UTF8),'') AS CLAVE_DES \n" +
                "FROM rh_persona rp \n" +
                "JOIN sys_usuario su ON rp.IDPERSONA = su.IDPERSONA \n" +
                "WHERE rp.CINRO = ? \n";
//            System.out.println("--------------------MODEL_INICIO_SESION-----------------------");
//            System.out.println("-- --recuperarUsuarioNroCI()--------     "+sql);
//            System.out.println("--------------------------------------------------------------");

//            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
//            MIS_RESULTADO = consultaBD(sql);
            
            Connection con = devolverConex();
            PreparedStatement sen = con.prepareStatement(sql);
            sen.setString(1, PARAM_NRO_CI);
            MIS_RESULTADO = sen.executeQuery();
            
            while(MIS_RESULTADO.next()==Boolean.TRUE) {
                PARAM_BEAN_USU = new BeanPersona();
                System.out.println("__      WHILE  _ __ _ __ _");
                PARAM_BEAN_USU.setSU_IDUSUARIO(MIS_RESULTADO.getString("IDUSUARIO"));
                PARAM_BEAN_USU.setSU_IDPERSONA(MIS_RESULTADO.getString("IDPERSONA"));
                PARAM_BEAN_USU.setRP_NOMBRE(MIS_RESULTADO.getString("NOMBRE"));
                PARAM_BEAN_USU.setRP_APELLIDO(MIS_RESULTADO.getString("APELLIDO"));
                PARAM_BEAN_USU.setRP_CINRO(MIS_RESULTADO.getString("CINRO"));
                PARAM_BEAN_USU.setSU_USUARIO(MIS_RESULTADO.getString("USUARIO"));
                PARAM_BEAN_USU.setSU_CLAVE(MIS_RESULTADO.getString("CLAVE_DES"));
                PARAM_BEAN_USU.setSU_EMAIL(MIS_RESULTADO.getString("EMAIL_SU"));
                PARAM_BEAN_USU.setRP_EMAIL(MIS_RESULTADO.getString("EMAIL_PER"));
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return PARAM_BEAN_USU;
    } // end method 
    
    
    
    // METODO PARA ENVIAR UN EMAIL AL USUARIO RECIEN REGISTRADO CON SU USUARIO Y CLAVE A MODO DE RECORDATORIO (LO USO EN EL CONTROLADOR DE INICIO DE SESION) 
    public void sendMailRegister(HttpServletRequest request) {
        String remitente = "filocobos99@gmail.com";
        String clave = "ttxasshqklvywppd"; // contraseña de aplicacion que genera google al verificar con dos pasos 
        String destino = (String) request.getParameter("txtE");
        String asunto = "Bienvenido a Odonto.";
        String cuerpo = "¡Bienvenido!; Se ha creado correctamente su usuario en Odonto. \n"
                + "Su usuario es: "+String.valueOf(request.getParameter("txtUsu"))+" y su clave es: "+String.valueOf(request.getParameter("txtPU"))+".";
//        String remitente = request.getParameter("Corigen");
//        String clave = request.getParameter("Pass");
//        String destino = request.getParameter("Cdestino");
//        String asunto = request.getParameter("Casunto");
//        String cuerpo = request.getParameter("ta_cuerpo");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);

        javax.mail.Session s = javax.mail.Session.getDefaultInstance(props);
        MimeMessage mensaje = new MimeMessage(s);

        try {
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mensaje.setSubject(asunto);

            mensaje.setText(cuerpo);
            /*Envio Mensaje de texto*/
            BodyPart parteTexto = new MimeBodyPart();                    
            parteTexto.setContent("<b>"+cuerpo+"</b>", "text/html");

//            BodyPart parteArchivo = new MimeBodyPart();
//            parteArchivo.setDataHandler(new DataHandler(new FileDataSource( application.getRealPath("")+ "grafica1.jpg" )));
//            parteArchivo.setFileName("grafica1.jpg");

            MimeMultipart todaslasPartes = new MimeMultipart();
            todaslasPartes.addBodyPart(parteTexto);
//            todaslasPartes.addBodyPart(parteArchivo);

            mensaje.setContent(todaslasPartes);

            Transport transport = s.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
            System.out.println("Correo Enviado");
//            System.out.print("<script> alert('Correo Enviado Exitosamente')</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    // METODO PARA ENVIAR UN EMAIL AL USUARIO CON SU CLAVE PARA QUE PUEDA VOLVER A LOGEARSE (LO USO EN EL CONTROLADOR DE INICIO DE SESION) 
    public void sendMailRecoverPass(String PARAM_EMAIL_DESTINO, String PARAM_USUARIO, String PARAM_CLAVE) {
        String remitente = "filocobos99@gmail.com";
        String clave = "ttxasshqklvywppd"; // contraseña de aplicacion que genera google al verificar con dos pasos 
        String destino = PARAM_EMAIL_DESTINO;
        String asunto = "Recuperación de su clave en Odonto.";
        
        String cuerpo = "En vista de que desea recuperar su password nos comunicamos con usted para brindarle sus datos. <br>\n"
                + "Su usuario es: "+PARAM_USUARIO+" y su clave es: "+PARAM_CLAVE+".";
//        String remitente = request.getParameter("Corigen");
//        String clave = request.getParameter("Pass");
//        String destino = request.getParameter("Cdestino");
//        String asunto = request.getParameter("Casunto");
//        String cuerpo = request.getParameter("ta_cuerpo");

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.user", remitente);
        props.put("mail.smtp.clave", clave);

        javax.mail.Session s = javax.mail.Session.getDefaultInstance(props);
        MimeMessage mensaje = new MimeMessage(s);

        try {
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
            mensaje.setSubject(asunto);

            mensaje.setText(cuerpo);
            /*Envio Mensaje de texto*/
            BodyPart parteTexto = new MimeBodyPart();                    
            parteTexto.setContent("<b>"+cuerpo+"</b>", "text/html");

//            BodyPart parteArchivo = new MimeBodyPart();
//            parteArchivo.setDataHandler(new DataHandler(new FileDataSource( application.getRealPath("")+ "grafica1.jpg" )));
//            parteArchivo.setFileName("grafica1.jpg");

            MimeMultipart todaslasPartes = new MimeMultipart();
            todaslasPartes.addBodyPart(parteTexto);
//            todaslasPartes.addBodyPart(parteArchivo);

            mensaje.setContent(todaslasPartes);

            Transport transport = s.getTransport("smtp");
            transport.connect("smtp.gmail.com", remitente, clave);
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
            transport.close();
            System.out.println("Correo Enviado");
//            System.out.print("<script> alert('Correo Enviado Exitosamente')</script>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    // METODO QUE UTILIZO EN LOS MODELOS DE LAS PAGINAS - PARA LOS METODO DE CARGA_GRILLA() 
    // METODO QUE HICE SOLO PARA NO TENER VARIOS BLOQUE DE CODIGO CON EL MISMO CODIGO SOLO QUE CON DISTINTO CONTENIDO 
    public boolean control_pagActualBotonera(int PARAM_NRO_PAG_MOSTRAR, int CANT_CLICS_DERECHO) {
        boolean valor = false; // true si se cumple y false si no 
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   ___CONTROL_PAG_ACTUAL_DE_LA_BOTONERA()____");
        System.out.println(".       __PARAM_NRO_PAG_MOSTRAR:  :"+PARAM_NRO_PAG_MOSTRAR);
        System.out.println(".       _____CANT_CLICS_DERECHO:  :"+CANT_CLICS_DERECHO);
        System.out.println(".");
        System.out.println(".");
        try {
            int btn_3 = (CANT_CLICS_DERECHO*3);
            int btn_2 = btn_3-1;
            int btn_1 = btn_3-2;
            System.out.println("----__2__----------------");
            System.out.println("_   _nro_pag: :"+PARAM_NRO_PAG_MOSTRAR);
            System.out.println("-------------------------");
            System.out.println("_   _btn_3:  :"+btn_3);
            System.out.println("_   _btn_2:  :"+btn_2);
            System.out.println("_   _btn_1:  :"+btn_1);
            System.out.println("-------------------------");
            if ((PARAM_NRO_PAG_MOSTRAR) == btn_3 || (PARAM_NRO_PAG_MOSTRAR) == btn_2 || (PARAM_NRO_PAG_MOSTRAR) == btn_1
            ) { // SI EL NRO DE PAG ACTUAL A MOSTRAR ES IGUAL A UNO DE LOS TRES BOTONES, SIGNIFICA QUE ESTA TODO BIEN Y LA CANTIDAD DE CLIC DERECHO ES CORRECTA PUES EL NRO DE PAG ACTUAL A MOSTRAR SE ENCUENTRA DENTRO DEL RANGO 
                System.out.println("____IF______EL_NRO_DE_PAG_ACTUAL_ES_IGUAL_A_UNO_DE_LOS_TRES_BOTONES_______________");
                valor = true;
            } else {
                System.out.println("____ELSE______EL_NRO_DE_PAG_ACTUAL_NO_COINCIDE_CON_NINGUNO_DE_LOS_BOTONES_______________");
                valor = false;
            }
        } catch(Exception e) {
            System.out.println("___-----error-------________SALTO_UNA_EXCEPTION_________");
            Logger.getLogger(ModelRef_Clinica.class.getName()).log(Level.SEVERE, null, e);
        }
        return valor;
    }
    
    
    /*
    METODO QUE UTILIZO PARA RECUPERAR LA EDAD DEL PACIENTE / LO ESTOY UTILIZANDO PARA LA FECHA CLINICA DEL PACIENTE 
    */
    public static int getPacEdad(String PARAM_FECNAC) {
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".       ____getPacEdad()_______");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        int varError = 0, years = 0;
//        GregorianCalendar fechaNac = new GregorianCalendar();
        System.out.println("_______PARAM_FEC_NAC:  :"+PARAM_FECNAC);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date;
        date = null;
        try {
            date = df.parse(PARAM_FECNAC);
        } catch (ParseException ex) {
            varError = 1;
            System.out.println("--------------__ERROR___------------------[ModelInicioSesion]--");
            System.out.println("-           error en el metodo de getPacEdad()               --");
            System.out.println("---------------------------------------------------------------");
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        // VALIDACION PARA EVITAR UN ERROR 500 Y QUE NO MUESTRE LA PAGINA - ENTONCES LE DEVUELVO CERO - 
        if (varError == 1) {
            //
        } else {
            Calendar fechaNac = Calendar.getInstance();
            fechaNac.setTime(date);

    //        public static int calcular(Calendar fechaNac) {
            Calendar fechaActual = Calendar.getInstance();

            // Cálculo de las diferencias.
            years = fechaActual.get(Calendar.YEAR) - fechaNac.get(Calendar.YEAR);
            int months = fechaActual.get(Calendar.MONTH) - fechaNac.get(Calendar.MONTH);
            int days = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNac.get(Calendar.DAY_OF_MONTH);

            // Hay que comprobar si el día de su cumpleaños es posterior
            // a la fecha actual, para restar 1 a la diferencia de años,
            // pues aún no ha sido su cumpleaños.
            if(months < 0 // Aún no es el mes de su cumpleaños
               || (months==0 && days < 0)) { // o es el mes pero no ha llegado el día.
                years--;
            }
        }
        System.out.println(".");
        System.out.println(".");
        System.out.println(".   ___return__edad:   :"+years);
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        System.out.println(".");
        return years;
//    }
    }
    
    
    /*
    METODO QUE UTILIZO PARA LOS COMBOS DONDE UTILICE LAS OPCIONE DE SI/NO COMO ES EL CASO DE FICHA DE ATENCIN NUTRI 
    */
    public Map<String, String> cargarComboSiNoAv(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("2", "A veces");
//            lista.put("null", "(Sin/Selección)");
            lista.put("0", "No");
            lista.put("1", "Si");
//            lista.put("2", "A veces");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("0") || paramValue.equalsIgnoreCase("No")) {
                lista.put("0", "No");
                lista.put("1", "Si");
                lista.put("2", "A veces");
            } else if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("Si")) {
                lista.put("1", "Si");
                lista.put("0", "No");
                lista.put("2", "A veces");
            } else {
                lista.put("2", "A veces");
                lista.put("0", "No");
                lista.put("1", "Si");
            }
        }
        return lista;
    } // end method 
    
    
    public String getDatoSiNoAv(String PARAM_SI_NO_AV) {
        if (PARAM_SI_NO_AV == null || PARAM_SI_NO_AV.isEmpty()) {
            return "";
        } else if (PARAM_SI_NO_AV.equals("0")) {
            return "No";
        } else if (PARAM_SI_NO_AV.equals("1")) {
            return "Si";
        } else if (PARAM_SI_NO_AV.equals("2")) {
            return "A veces";
        } else {
            return "";
        }
    }
    
    
    
    /*
    METODO QUE UTILIZO PARA LOS COMBOS DONDE UTILICE LAS OPCIONE DE SI/NO COMO LO UTILIZO EN PACIENTE
    */
    public Map<String, String> cargarComboSiNo(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("0", "No");
            lista.put("1", "Si");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("0") || paramValue.equalsIgnoreCase("No")) {
                lista.put("0", "No");
                lista.put("1", "Si");
            } else if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("Si")) {
                lista.put("1", "Si");
                lista.put("0", "No");
            } else {
                lista.put("0", "No");
                lista.put("1", "Si");
            }
        }
        return lista;
    } // end method 
    
    
    public String getDatoSiNo(String PARAM_SI_NO) {
        if (PARAM_SI_NO == null || PARAM_SI_NO.isEmpty()) {
            return "";
        } else if (PARAM_SI_NO.equals("0")) {
            return "No";
        } else if (PARAM_SI_NO.equals("1")) {
            return "Si";
        } else {
            return "";
        }
    }
    
    
    
    /*
    METODO QUE UTILIZO PARA EL COMBO DE METABOLISMO QUE UTILIZO EN LA PAGINA DE: FICHA DE ATENCIN NUTRI 
    */
    public Map<String, String> cargarComboMetabolismo(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("A", "Acelerado");
            lista.put("T", "Tranquilo");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("0") || paramValue.equalsIgnoreCase("A") || paramValue.equalsIgnoreCase("ACELERADO")) {
                lista.put("A", "Acelerado");
                lista.put("T", "Tranquilo");
            } else if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("T") || paramValue.equalsIgnoreCase("TRANQUILO")) {
                lista.put("T", "Tranquilo");
                lista.put("A", "Acelerado");
            } else {
                lista.put("A", "Acelerado");
                lista.put("T", "Tranquilo");
            }
        }
        return lista;
    } // end method 
    
    
    
    /*
    METODO QUE UTILIZO PARA EL COMBO DE METABOLISMO QUE UTILIZO EN LA PAGINA DE: FICHA DE ATENCIN NUTRI 
    */
    public Map<String, String> cargarComboEscalaBristol(String paramValue) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        if (paramValue == null || paramValue.isEmpty() || paramValue.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO ENTONCES SUPONDRIA QUE QUIERE CREAR UN REGISTRO Y NO QUE ESTA RECUPERANDO UNO 
            lista.put("1", "Tipo 1");
            lista.put("2", "Tipo 2");
            lista.put("3", "Tipo 3");
            lista.put("4", "Tipo 4");
            lista.put("5", "Tipo 5");
            lista.put("6", "Tipo 6");
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCES PREGUNTARIA POR SU VALOR PARA SABER CUAL CARGO PRIMERO 
            if (paramValue.equalsIgnoreCase("1") || paramValue.equalsIgnoreCase("Tipo 1")) {
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
            } else if (paramValue.equalsIgnoreCase("2") || paramValue.equalsIgnoreCase("Tipo 2")) {
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
                lista.put("1", "Tipo 1");
            } else if (paramValue.equalsIgnoreCase("3") || paramValue.equalsIgnoreCase("Tipo 3")) {
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
            } else if (paramValue.equalsIgnoreCase("4") || paramValue.equalsIgnoreCase("Tipo 4")) {
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
            } else if (paramValue.equalsIgnoreCase("5") || paramValue.equalsIgnoreCase("Tipo 5")) {
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
            } else if (paramValue.equalsIgnoreCase("6") || paramValue.equalsIgnoreCase("Tipo 6")) {
                lista.put("6", "Tipo 6");
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
            } else {
                lista.put("1", "Tipo 1");
                lista.put("2", "Tipo 2");
                lista.put("3", "Tipo 3");
                lista.put("4", "Tipo 4");
                lista.put("5", "Tipo 5");
                lista.put("6", "Tipo 6");
            }
        }
        return lista;
    } // end method 
    
    
    
    
    
} // END CLASS 