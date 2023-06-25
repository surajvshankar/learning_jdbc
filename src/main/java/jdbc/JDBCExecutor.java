package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class JDBCExecutor {
  public static void main(String... args) {
    System.out.println("Hello, from Learning JDBC!");
//    basicQuery();
//    withDAO();
//    getRecord();
//    updateRecord();
//    deleteRecord();

    findManyLimit();
    findManyPagination();
    updateRecordWithTransactions();
  }

  public static void basicQuery() {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      Statement stmt = connection.createStatement();
      ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM CUSTOMER");
      while (resultSet.next()) {
        System.out.println("Total record count: " + resultSet.getInt(1));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void withDAO() {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = new Customer();
      customer.setFirstName("Suraj");
      customer.setLastName("Shankar");
      customer.setEmail("Suraj.Shankar@email.com");
      customer.setPhone("408-393-7755");
      customer.setAddress("1234 Main St");
      customer.setCity("San Jose");
      customer.setState("CA");
      customer.setZipCode("21212");

      customerDAO.create(customer);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void getRecord() {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(1000);
      System.out.println("Record at 1000: " + customer.getFirstName() + " " + customer.getLastName());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updateRecord() {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(10000);
      System.out.println("Email ID before Updation: " + customer.getEmail());
      customer.setEmail("surshankar@email.com");
      customer = customerDAO.update(customer);
      System.out.println("Email ID after Updation: " + customer.getEmail());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void deleteRecord(){
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(10002);
      System.out.println("Email ID before Deletion: " + customer);
      customerDAO.delete(customer.getId());
      customer = customerDAO.findById(10002);
      System.out.println("Email ID after Deletion: " + customer);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void findManyLimit(){
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      List<Customer> customers = customerDAO.findManyLimit();
      for (Customer c : customers) {
        System.out.println("Record: " + c.getFirstName() + " " + c.getLastName());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void findManyPagination(){
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      for (int i = 0; i < 3; i++) {
        List<Customer> customers = customerDAO.findManyPagination(i);
        System.out.println("Page No.: " + i);
        for (Customer c : customers) {
          System.out.println("Record: " + c.getFirstName() + " " + c.getLastName());
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static void updateRecordWithTransactions() {
    DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
    try {
      Connection connection = dcm.getConnection();
      CustomerDAO customerDAO = new CustomerDAO(connection);
      Customer customer = customerDAO.findById(10000);
      System.out.println("Email ID before Updation: " + customer.getEmail());
      customer.setEmail("surshankar@email.com");
      customer = customerDAO.update(customer);
      System.out.println("Email ID after Updation: " + customer.getEmail());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
