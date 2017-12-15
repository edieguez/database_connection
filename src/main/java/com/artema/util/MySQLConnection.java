package com.artema.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MySQLConnection {
    private static final Logger logger = LogManager.getLogger(QueryExecuter.class);

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
            Class.forName("com.mysql.cj.jdbc.Driver");

            if (properties == null) {
                properties = ResourceBundle.getBundle("database");

                host = properties.getString("host");
                port = properties.getString("port");
                database = properties.getString("database");
                user = properties.getString("user");
                password = properties.getString("password");
            }

            String connectionURL = String.format("jdbc:mysql://%s:%s/%s?useSSL=false", host, port, database);
            logger.info(String.format("%s connecting...", connectionURL));

            Connection connection = DriverManager.getConnection(connectionURL, user, password);

            logger.info(String.format("%s connected...", connectionURL));
            return connection;
        } catch (ClassNotFoundException | MissingResourceException ex) {
            ex.printStackTrace(System.err);
            return null;
        }
    }
}
