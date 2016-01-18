package com.artema.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MySQL_Connection {

    private static ResourceBundle properties;
    private static String host;
    private static String port;
    private static String database;
    private static String user;
    private static String password;

    /**
     * Get a new database connection using the settings
     * in database.properties file
     * 
     * @return java.sql.Connection
     * @throws SQLException
     */
    public static Connection getConection() throws SQLException {
        try {
            System.out.print("Conecting with the database... ");
            Class.forName("com.mysql.jdbc.Driver");

            if (properties == null) {
                properties = ResourceBundle.getBundle("database");

                host = properties.getString("host");
                port = properties.getString("port");
                database = properties.getString("database");
                user = properties.getString("user");
                password = properties.getString("password");
            }

            Connection connection = DriverManager.getConnection(
                    String.format("jdbc:mysql://%s:%s/%s", host, port, database),
                    user, password);

            System.out.println("success.");
            return connection;
        } catch (ClassNotFoundException | MissingResourceException ex) {
            System.out.println("failed.\n");
            ex.printStackTrace(System.err);
            return null;
        }
    }
}
