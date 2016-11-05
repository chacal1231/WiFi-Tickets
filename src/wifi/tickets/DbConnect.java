/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wifi.tickets;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author ACER-PC
 */
public class DbConnect {

    static private Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            //JDBC
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/wifi-tickets", "root", "123456789");
        }
        return connection;
    }

}
