package DAO_challenge;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionManager {
  private final String url;
  private final Properties properties;

  public DatabaseConnectionManager(String hostname, String database, String username, String password){
    this.url = "jdbc:postgresql://" + hostname + "/" + database;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.url, this.properties);
  }
}
