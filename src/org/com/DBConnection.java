package org.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static Connection cn=null;

    private DBConnection(){

    }
    static String driverClass="com.mysql.cj.jdbc.Driver";
    static String url="jdbc:mysql://localhost/medicine";
    static String username="root";
    static String password="param";


    public static Connection getConnection(){
        if(cn==null){
            // Optional
            try {
                Class.forName(driverClass);
                cn= DriverManager.getConnection(url,username,password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        return cn;


    }

    public static void closeConnection(){
        try {
            cn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

