package com.artema.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QueryExecuter {
    private static final Logger logger = LogManager.getLogger(QueryExecuter.class);
    private final Connection connection;

    public QueryExecuter(Connection connection) {
        this.connection = connection;
    }

    public List<Map<String, String>> getRows(String query, Object... parameters) {
        List<Map<String, String>> list = new ArrayList<>();

        try (PreparedStatement prepareStatement = this.connection.prepareStatement(query)) {
            this.setParameters(prepareStatement, parameters);

            try (ResultSet resultSet = prepareStatement.executeQuery()) {
                this.setParameters(prepareStatement, parameters);
                List<String> columnNames = this.getColumnNames(resultSet);

                while (resultSet.next()) {
                    Map<String, String> hashMap = new HashMap<String, String>();

                    for (String columnName : columnNames) {
                        hashMap.put(columnName, resultSet.getString(columnName));
                    }

                    list.add(hashMap);
                }
            }
        } catch (SQLException ex) {
            String message = String.format("Error executing query '%s'", query);
            logger.error(message, ex);
        }

        return (list.size() > 0) ? list : null;
    }

    public Map<String, String> getRow(String query, Object... parameters) {
        List<Map<String, String>> result = this.getRows(query, parameters);
        Map<String, String> row = null;

        if (!result.isEmpty()) {
            row = result.get(0);
        }

        return row;
    }

    public boolean execute(String query, Object... parameters) {
        boolean status = false;

        try (PreparedStatement prepareStatement = this.connection.prepareStatement(query)) {
            this.setParameters(prepareStatement, parameters);

            status = prepareStatement.executeUpdate() != 0;
        } catch (SQLException ex) {
            String message = String.format("Error executing query '%s'", query);
            logger.error(message, ex);
        }

        return status;
    }

    private void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        if (parameters.length != 0) {
            int index = 1;

            for (Object parameter : parameters) {
                preparedStatement.setObject(index++, parameter);
            }
        }
    }

    private List<String> getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int n = metaData.getColumnCount();

        List<String> columnNames = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            columnNames.add(metaData.getColumnLabel(i));
        }

        return columnNames;
    }
}
