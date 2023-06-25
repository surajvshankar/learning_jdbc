package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConnectionManager {
  private final String url;
  private final Properties properties;

  public DatabaseConnectionManager(String hostname, String databaseName, String username, String password) {
    /* Use postgres 13.2 to avoid "The authentication type 10 is not supported" (in postgres 15)
    docker pull postgres:13.2
    docker run --rm --name lil-postgres -e POSTGRES_PASSWORD=password -d -v $HOME/srv/postgres:/var/lib/postgresql/data -p 5432:5432 postgres:13.2
    OR:
    Change scram-sha-256 to md5 in pg_hba.conf and
    Change Password ( this restore password in md5 format): ALTER ROLE postgres WITH PASSWORD 'root'
     */
    this.url = "jdbc:postgresql://" + hostname + "/" + databaseName;
    this.properties = new Properties();
    this.properties.setProperty("user", username);
    this.properties.setProperty("password", password);
  }

  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.url, this.properties);
  }
}
