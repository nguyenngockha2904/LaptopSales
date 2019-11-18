package com.superducks.laptopsales.Class;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectDatabase {
    public static Connection Connect() {
        Connection conn = null;
        //database local
        String URL = "jdbc:mysql://localhost:3306/laptop_sales_NSH?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String USERNAME = "root";
        String PASSWORD = "12081989";

        //database online server heroku
        String URL_ONL = "jdbc:mysql://erxv1bzckceve5lh.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/r2yqjhhpfcw4qy0p?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        String USERNAME_ONL = "h0a4st4rqfhquwco";
        String PASSWORD_ONL = "rdf5e6yh3s4gft3v";

        try {
            //database local
            conn = (Connection) DriverManager.getConnection(URL, USERNAME, PASSWORD);

            //database online server heroku
          //  conn = (Connection) DriverManager.getConnection(URL_ONL, USERNAME_ONL, PASSWORD_ONL);

            return conn;
        } catch (Exception e) {
            AlertMessage.showAlert("No data from the server. Check your internet!", "error");
            return null;
        }
    }
}
