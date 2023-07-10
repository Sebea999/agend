/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agend.config;

import java.sql.*;

/**
 *
 * @author RYUU
 */
public class Conex {
    
    
    public static Connection getConexion() {
        String conexionUrl = "jdbc:sqlserver://192.168.1.11:1433;"
            + "database=db_prueba;"
            + "user=sa;"
            + "password=root;"
            + "loginTimeout=30;";
        
        try {
            Connection con = DriverManager.getConnection(conexionUrl);
            return con;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    
    
    
    
    static Connection con = null;
    static PreparedStatement prepSQL = null;
    static ResultSet rs = null;
//    UserInfo userDetails = null;
    
    public static String consulta() {
        String valor = "";
        try {
            //Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String DBuserName = "sa";
            String DBpassword = "root";
            String url = "jdbc:sqlserver://192.168.1.11:1433"+";databaseName=db_prueba";
            con = DriverManager.getConnection(url, DBuserName, DBpassword);

            String query = "SELECT TOP 1 * FROM sys_usuario WHERE IDUSUARIO= ? ";
            prepSQL  = con.prepareStatement(query);
            prepSQL.setString(1, "0");
            rs = prepSQL.executeQuery();

            String[] result = new String[8];
            if(rs!=null){
                while (rs.next()){
//                    if (EncryptDecrypt.decrypt(rs.getString("Password")).equals(password)) {
//                        userDetails = new UserInfo();
//                        userDetails.setID(Integer.parseInt(rs.getString("ID")));
//                        userDetails.setUsername(rs.getString("Username"));
//                        userDetails.setFirstName(rs.getString("FirstName"));
//                        userDetails.setSurname(rs.getString("Surname"));
//                        userDetails.setRole(rs.getString("Role"));
                        valor = (rs.getString("USUARIO"));
//                    } else
//                        userDetails = null;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
//            userDetails = null;
            
//        } finally {
//            if (rs!=null)
//                rs.close();
//            if (prepSQL!=null)
//                prepSQL.close();
//            if (con!=null)
//                con.close();
        }
        return valor;
    }
    
    
    
}