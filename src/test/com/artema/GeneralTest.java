package com.artema;

import com.artema.util.MySQLConnection;
import com.artema.util.QueryExecuter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GeneralTest {
    private static final Logger logger = LogManager.getLogger();

    @Test
    public void testConnection() {
        try (Connection connection = MySQLConnection.getConection()) {
            QueryExecuter queryExecuter = new QueryExecuter(connection);
            List<Map<String, String>> rows = queryExecuter.getRows("SHOW DATABASES");

            logger.info("Available databases:");

            for (Map<String, String> row : rows) {
                logger.info(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
