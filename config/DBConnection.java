package config;

import java.sql.DriverManager;
import java.sql.Connection;

public class DBConnection {
  private static final String URL = "jdbc:mysql://localhost:3306/parking_lot";
  private static final String USER = "admin";
  private static final String PASSWORD = "admin123";

  private static Connection connection;

  public static Connection getConnection() {
    try {
      if (connection == null || connection.isClosed()) {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
      }
    } catch (Exception e) {
      System.out.println("Database connection failed: "+e.getMessage());
    }

    return connection;
  }

  public static void main(String[] args) {
    DBConnection conn = new DBConnection();

    System.out.println(conn.getConnection());
  }
}
