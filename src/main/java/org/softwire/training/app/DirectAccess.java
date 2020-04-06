package org.softwire.training.app;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DirectAccess {

    List<String> getAllOwnerNames() throws SQLException {

        try
        {
            String url = "jdbc:mysql://localhost:3306/cat_shelter?serverTimezone=UTC";
            Connection connection = DriverManager.getConnection(url,"root","jellyfish_123");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT name FROM owners");
            List<String> names = new ArrayList<>();
            while (resultSet.next()) {
                names.add(resultSet.getString("name"));
            }
            return names;
        }
        catch (Exception e)
        {
            System.err.println("D'oh! Got an exception!");
            System.err.println(e.getMessage());
            throw e;
        }
    }
}
