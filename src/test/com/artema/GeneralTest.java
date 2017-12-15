package com.artema;

import com.artema.util.MySQLConnection;
import com.artema.util.QueryExecuter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GeneralTest {
    private static final Logger logger = LogManager.getLogger(QueryExecuter.class);

    @Test
    public void testConnection() {
        try (Connection connection = MySQLConnection.getConection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SHOW DATABASES");
            ResultSet resultSet = preparedStatement.executeQuery();

            logger.info("Available databases:");

            while (resultSet.next()) {
                logger.info("  " + resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
