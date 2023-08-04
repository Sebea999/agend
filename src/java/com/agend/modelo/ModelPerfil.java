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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.agend.javaBean.BeanPerfil;
import javax.servlet.http.HttpSession;

/**
 *
 * @author RYUU
 */
public class ModelPerfil implements CRUD {
    
    
    
    public String VAR_ID_CLINICA = "";

    
    /*
        METODO QUE DEVUELVE LA CONEXION CREADA A LA BASE DE DATOS 
    */
    private Connection devolverConex() {
        System.out.println("[+] MODEL_PERFIL.-------");
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
            MP_CONEXION = java.sql.DriverManager.getConnection((host+bd+configdb), user, pass);
//            MP_CONEXION = java.sql.DriverManager.getConnection((host+bd+"?autoReconnect=true&useTimeZone=true&serverTimezone=UTC&useSSL=false"), user, pass);
            System.out.println("[+] Connection is successful.-");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("[-] Connection is failed --- class-not-found-exception | sql-exception.-");
            Logger.getLogger(ModelPerfil.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModelPerfil.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ModelPerfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // END METHOD 
    
    
    
    
    
    
    
    
    
    
    @Override
    public List cargar_grilla(HttpSession PARAM_SESION) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List traer_datos(String idTraer, HttpSession PARAM_SESION) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String guardar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String eliminar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
    
    
// METODO PARA CARGAR LOS DATOS DEL PERFIL PARA LUEGO UTILIZARLOS POR MEDIO DEL IDUSUARIO 
    public BeanPerfil traerDatosPerfil(String PARAM_IDUSUARIO, BeanPerfil beanPerfil){
        try {
            String sql = "SELECT su.IDPERFIL, " + 
                "sp.PERFIL AS DESC_PERFIL, sp.IDMENU, sp.ESTADO AS ESTADO_PERFIL \n" + 
                "FROM sys_usuario su \n" +
                "LEFT JOIN sys_perfil sp ON su.IDPERFIL = sp.IDPERFIL \n" +
                "WHERE su.IDUSUARIO = '"+PARAM_IDUSUARIO+"' ";
            
//            String sql = "SELECT IDPERFIL, PERFIL AS DESC_PERFIL, IDMENU, ESTADO \n" +
//                "FROM sys_perfil \n" +
//                "WHERE IDPERFIL = '"+IDPERFIL+"' ";
            System.out.println("----traerDescPerfil()--------------"+sql);
//            conn.sentencia = conn.conexion.createStatement();
//            conn.resultado = conn.sentencia.executeQuery(sql);
            
            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            if (MP_RESULTADO.next() == true) {
                System.out.println("ID:     "+MP_RESULTADO.getString("IDPERFIL"));
                System.out.println("NOMBRE:     "+MP_RESULTADO.getString("DESC_PERFIL"));
                System.out.println("IDMENU:     "+MP_RESULTADO.getString("IDMENU"));
                // UNA VEZ SE RECUPERE LOS DATOS DEL PERFIL ENTONCES PASARIA A CARGAR EL BEAN QUE SE PASA POR EL PARAMETRO PARA LUEGO DEVOLVERLO 
                beanPerfil.setSP_IDPERFIL(MP_RESULTADO.getString("IDPERFIL"));
                beanPerfil.setSP_NOMBRE(MP_RESULTADO.getString("DESC_PERFIL"));
                beanPerfil.setSP_IDMENU(MP_RESULTADO.getString("IDMENU"));
                beanPerfil.setSP_ESTADO(MP_RESULTADO.getString("ESTADO_PERFIL"));
            }
            cerrarConexiones();
        } catch (SQLException | NullPointerException e) {
            System.out.println("-----__ERROR__----------------_ModelInicioSesion_-------------------");
            System.out.println("---traerDescPerfil()-------catch("+e.getMessage()+")----");
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("--------------------------------------------------------------------");
        }
        return beanPerfil;
    } // END METHOD 
    
    
    
    /*
    METODO PARA CARGAR LOS COMBOS DE PERFIL QUE UTILICEN LAS PAGINAS (JSP) 
    */
    public Map<String, String> cargarComboPerfil(String PARAM_PERFIL) {
        Map<String, String> lista = new LinkedHashMap<>();
        
        String sqlFiltroPerfil="";
        if (PARAM_PERFIL == null || PARAM_PERFIL.isEmpty() || PARAM_PERFIL.equalsIgnoreCase("")) { // SI SE ENCUENTRA VACIO EL PARAMETRO DE PERFIL, ENTONCES CARGARE TODOS LOS PERFILES  
            sqlFiltroPerfil = "";
        } else { // EN CASO DE QUE NO SE ENCUENTRE VACIO ENTONCE AGREGARIA AL WHERE PARA QUE NO TRAIGA EL PERFIL DEL PARAMETRO 
            lista.put(PARAM_PERFIL, PARAM_PERFIL);
            sqlFiltroPerfil = "AND PERFIL NOT IN ('"+PARAM_PERFIL+"')";
        }
        
        try {
            String sql = "SELECT IDPERFIL, PERFIL, IDMENU, ESTADO \n" +
                "FROM sys_perfil \n" +
                "WHERE ESTADO = 'A' \n" +
                ""+sqlFiltroPerfil+" \n" +
                "ORDER BY PERFIL ASC \n";
            System.out.println("------------------------MODEL_INICIO_SESION--------------------------");
            System.out.println("-- --cargarComboPerfil()--------     "+sql);
            System.out.println("---------------------------------------------------------------");

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                lista.put(MP_RESULTADO.getString("PERFIL"), MP_RESULTADO.getString("PERFIL"));
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return lista;
    } // end method 
    
    
    /*
    METODO PARA TRAER EL IDPERFIL POR MEDIO DE LA DESCRIPCION DEL PERFIL 
    */
    public String recuperarIdPerfil(String PARAM_PERFIL) {
        String idperfil = "";
        try {
            String sql = "SELECT IDPERFIL, PERFIL, IDMENU, ESTADO \n" +
                "FROM sys_perfil \n" +
                "WHERE PERFIL = '"+PARAM_PERFIL+"' \n";
            System.out.println("--------------------MODEL_INICIO_SESION-----------------------");
            System.out.println("-- --recuperarIdPerfil()--------     "+sql);
            System.out.println("--------------------------------------------------------------");

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                idperfil = MP_RESULTADO.getString("IDPERFIL");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return idperfil;
    } // end method 
    
    
    /*
    METODO PARA TRAER LA DESCRIPCION DEL PERFIL POR MEDIO DEL IDPERFIL 
    */
    public String recuperarDescPerfil(String PARAM_IDPERFIL) {
        String idperfil = "";
        try {
            String sql = "SELECT IDPERFIL, PERFIL, IDMENU, ESTADO \n" +
                "FROM sys_perfil \n" +
                "WHERE IDPERFIL = '"+PARAM_IDPERFIL+"' \n";
            System.out.println("--------------------MODEL_INICIO_SESION-----------------------");
            System.out.println("-- --recuperarDescPerfil()--------     "+sql);
            System.out.println("--------------------------------------------------------------");

            // CARGO LA VARIABLE GLOBAL DE RESULTADO CON EL METODO QUE HARA LA CONSULTA Y DEVOLVERA ESE RESULTADO 
            MP_RESULTADO = consultaBD(sql);
            
            while(MP_RESULTADO.next()) {
                idperfil = MP_RESULTADO.getString("PERFIL");
            }
        } catch (SQLException e) {
            Logger.getLogger(ModelInicioSesion.class.getName()).log(Level.SEVERE, null, e);
        }
        return idperfil;
    } // end method 
    
    
    
    
    
    
    
    
    /*
    EN PACIENTE LE CARGO EL IDPERFIL DEL PACIENTE ENTONCES ESTOS METODOS SERAN PARA 
    DESDE LOS CONTROLADORES O MODELOS DONDE NECESITE EL ID EXACTO DE UNA CATEGORIA DE PERFIL 
    EN VEZ DE IR A CONSULTAR LA BASE VENDRIA A RECUPERAR ESTA CLASE DE METODO 
    */
    public String getIdPerfilPaciente() {
        return "4"; // PERFIL PACIENTE ID=4 / ACTUALIZAR EN CASO DE QUE CAMBIE 
    }
    public String getDescPerfilPaciente() {
        return "PACIENTE";
    }
    
    
    // PARA EL CONTROLADOR DE MEDICO / METODOS PARA EVITAR HACER CONSULTAS A LA BASE Y ASI TENDRIA QUE DEFINIR NOMAS EN ESTOS METODOS CUALES SON LOS DATOS CORRESPONDIENTES PUES ESTOS NO DEBERIAN DE VARIAR 
    public String getIdPerfilMedico() {
        return "5"; // PERFIL PACIENTE ID=5 / ACTUALIZAR EN CASO DE QUE CAMBIE 
    }
    public String getDescPerfilMedico() {
        return "MEDICO";
    }
    
    
    // PARA EL CONTROLADOR DE SECRETARIO / METODOS PARA EVITAR HACER CONSULTAS A LA BASE Y ASI TENDRIA QUE DEFINIR NOMAS EN ESTOS METODOS CUALES SON LOS DATOS CORRESPONDIENTES PUES ESTOS NO DEBERIAN DE VARIAR 
    public String getIdPerfilSecretario() {
        return "3"; // PERFIL PACIENTE ID=3 / ACTUALIZAR EN CASO DE QUE CAMBIE 
    }
    public String getDescPerfilSecretario() {
        return "SECRETARIA/O";
    }
    
    

    public String getIdPerfilAdministrador() {
        return "1"; // PERFIL PACIENTE ID=1 / ACTUALIZAR EN CASO DE QUE CAMBIE 
    }
    public String getDescPerfilAdministrador() {
        return "ADMINISTRADOR";
    }
    
    
    
    public String getIdPerfil(HttpSession PARAM_SESION) {
        String idperfil = (String) PARAM_SESION.getAttribute("IDPERFIL");
        String desc_perfil = (String) PARAM_SESION.getAttribute("DESC_PERFIL");
        System.out.println("_   _MODEL_PERFIL:      _IDPERFIL:   :"+idperfil);
        System.out.println("_   _MODEL_PERFIL:      _DESC_PERFIL:   :"+desc_perfil);
        if (idperfil == null) {
            idperfil = "";
        }
        return idperfil;
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL ES ADMINISTRADOR 
    public boolean isPerfilAdmin(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL ES FUNCIONARIO 
    public boolean isPerfilFuncio(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO  
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL ES SECRETARIO 
    public boolean isPerfilSecre(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO  
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL ES PACIENTE 
    public boolean isPerfilPaciente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL ES MEDICO 
    public boolean isPerfilMedico(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
            return true;
        } else {
            return false;
        }
    }
    
    
    
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REFERENCIALES 
    public boolean isMenuReferenciales(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REFERENCIALES - LA PAGINA DE CLINICA 
    public boolean isMenuRef_Clinica(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REFERENCIALES - LA PAGINA DE CLINICA 
    public boolean isMenuRef_Ciudad(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REFERENCIALES - LA PAGINA DE ESPECIALIDAD 
    public boolean isMenuRef_Especialidad(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REFERENCIALES - LA PAGINA DE SERVICIO 
    public boolean isMenuRef_Servicio(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE USUARIO 
    public boolean isMenuUsuario(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE MEDICO 
    public boolean isMenuMedico(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE SECRETARIO/A 
    public boolean isMenuSecretario(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PACIENTE 
    public boolean isMenuPaciente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PERFIL 
    public boolean isMenuPerfil(String PARAM_IDPERFIL) {
//        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
//            return true;
//        } else 
            if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE CUENTA CLIENTE 
    public boolean isMenuCuentaCliente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PLAN DE HORARIO 
    public boolean isMenuPlanHorario(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE AGENDAMIENTO 
    public boolean isMenuAgendamiento(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE ATENCION (QUE SERIA COMO LA FICHA DEL PACIENTE) 
    public boolean isMenuAtencionFichaPaciente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE FACTURA 
    public boolean isMenuFactura(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE REPORTES 
    public boolean isMenuReportes(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETAREIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE MANUAL DE USUARIO 
    public boolean isMenuManualDeUsuario(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR SI EL PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PANEL DE CONTROL 
    public boolean isMenuPanelDeControl(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR QUE PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PANEL DE CONTROL Y A LA PAGINA DE CONFIGURACION DE AGENDAMIENTO 
    public boolean isPCConfigAgendamiento(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR QUE PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PANEL DE CONTROL Y A LA PAGINA DE ANULAR AGENDAMIENTOS 
    public boolean isPCAnularAgendamiento(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR QUE PERFIL TIENE PERMITIDO ACCEDER AL MENU DE PANEL DE CONTROL Y A LA PAGINA DE ANULAR FACTURAS 
    public boolean isPCAnularFactura(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else {
            return false;
        }
    }
    
    
    
    /*
     * PAGINA:   REPORTE     /      PAGINAS DENTRO DE LA PAGINA DE REPORTES 
    */
    public boolean isReportePaciente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
//        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteMisAgendamientos(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
//        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
//            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteAjusteStock(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return false;
//        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteStock(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return false;
//        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteFactura(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteCuentaCliente(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteEstadistica(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    public boolean isReporteHistoricoFichaAtencion(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
//        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
    // METODO PARA CONTROLAR QUE PERFIL TIENE HABILITADO INGRESAR A LA PAGINA DE REPORTE DE FICHA ATENCION DEL PACIENTE (NUTRI).-
    public boolean isReporteFichaAteNutri(String PARAM_IDPERFIL) {
        if (PARAM_IDPERFIL.equals("1")) { // 1 = IDPERFIL ADMINISTRADOR 
            return true;
        } else if (PARAM_IDPERFIL.equals("2")) { // 2 = IDPERFIL FUNCIONARIO 
            return true;
        } else if (PARAM_IDPERFIL.equals("3")) { // 3 = IDPERFIL SECRETARIO 
            return true;
//        } else if (PARAM_IDPERFIL.equals("4")) { // 4 = IDPERFIL PACIENTE 
//            return true;
//        } else if (PARAM_IDPERFIL.equals("5")) { // 5 = IDPERFIL MEDICO 
//            return true;
        } else {
            return false;
        }
    }
    
} // end class 