package com.artema;

import com.artema.util.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCase {

    public static void main(String[] args) {
        try (Connection connection = MySQLConnection.getConection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SHOW DATABASES");
            ResultSet resultSet = preparedStatement.executeQuery();

            System.out.println("Available databases:");

            while (resultSet.next()) {
                System.out.println("  " + resultSet.getString(1));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}
