package com.artema;

import com.artema.util.MySQL_Connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCase {

    public static void main(String[] args) {
        try (Connection connection = MySQL_Connection.getConection()) {
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
