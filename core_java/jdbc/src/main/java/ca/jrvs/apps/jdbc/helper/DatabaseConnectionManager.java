package ca.jrvs.apps.jdbc.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:postgresql://" + PropertyManager.getServer() +"/" + PropertyManager.getDatabase(),
                PropertyManager.getUsername(),
                PropertyManager.getPassword());
    }

}
