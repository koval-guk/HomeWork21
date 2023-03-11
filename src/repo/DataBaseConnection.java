package repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public static Connection getConnection() {
        try {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://localhost:3306/homework21";
            String userName = System.getenv("MYSQL_LOGIN");
            String password = System.getenv("MYSQL_PASSWORD");
            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver not found : " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Create connection exception : " + e.getMessage());
        }
        return null;
    }

    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection close exception : " + e.getMessage());
            }
        }
    }
}
