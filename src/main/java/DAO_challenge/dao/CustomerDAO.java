package DAO_challenge.dao;

import DAO_challenge.model.Customer;
import DAO_challenge.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerDAO extends DataAccessObject<Customer> {
  public static final String GET_ONE = "SELECT id, name FROM customer WHERE id = ?";
  public static final String INSERT = "INSERT INTO customer (name) VALUES (?)";

  public CustomerDAO(Connection connection){
    super(connection);
  }

  @Override
  public Customer findById(long id){
    Customer customer = new Customer();
    try(PreparedStatement stmt = this.connection.prepareStatement(GET_ONE);) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        customer.setId(rs.getLong("id"));
        customer.setName(rs.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return customer;
  }

  @Override
  public Customer create(Customer dto, String sequence) {
    try (PreparedStatement stmt = this.connection.prepareStatement(INSERT);) {
      stmt.setString(1, dto.getName());
      stmt.execute();
      int id = this.getLastVal(sequence);
      return this.findById(id);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
