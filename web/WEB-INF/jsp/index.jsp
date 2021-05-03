
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to Spring Web MVC project</title>
    </head>

    <body>
        <p>Hello! This is the default welcome page for a Spring Web MVC project.</p>
        <%
            String user="PRUEBA";
//            try {
//                String sql = "SELECT USUARIO FROM sys_usuario WHERE IDUSUARIO = 0";
//                System.out.println("-------------   "+sql);
//                Statement sentencia = conexion.Conex.getConexion().createStatement();
//                ResultSet resultado = sentencia.executeQuery(sql);
//                
//                while(resultado.next()) {
//                    user=resultado.getString("USUARIO");
//                }
//            } catch (SQLException e) {
//                user="NO_ENCONTRADO";
//            }
            
            
            
            
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String connectionURL = "jdbc:sqlserver://DESKTOP-M9F7GB3:1433;databaseName=sys_usuario;user=usuarioSQL;password=admin;";
//                String connectionURL = "jdbc:sqlserver://192.168.1.11:1433;databaseName=sys_usuario;user=usuarioSQL;password=admin;";
                Connection con = DriverManager.getConnection(connectionURL);
                System.out.println("Nos conectamos");
                
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM sys_usuario");

                while(rs.next()) {
                    int ID = rs.getInt(1);
                    String USUARIO = rs.getString(2);
                    String CLAVE = rs.getString(3);
                    int IDALUM = rs.getInt(4);
                    String ESTADO = rs.getString(5);
                    System.out.println("--------------------");
                    System.out.println("ID_USUARIO:   "+ID);
                    System.out.println("USUARIO:      "+USUARIO);
                    System.out.println("CLAVE:        "+CLAVE);
                    System.out.println("ID_ALUMNO:    "+IDALUM);
                    System.out.println("ESTADO:       "+ESTADO);
                    System.out.println("--------------------");
                    user = USUARIO;
                }
            } catch (SQLException e) {
                user = "error base";
                System.out.println("_1__ERROR:      "+e.getMessage());
                System.out.println("_2__ERROR:      "+e.getErrorCode());
                System.out.println("_3__ERROR:      "+e.getNextException());
    //            Logger.getLogger(InicioSesionController.class.getName()).log(Level.SEVERE, null, e);
            }
            
            
            
            
        %>
        <p>
            User 0:    <%= user %>
        </p>
    </body>
</html>