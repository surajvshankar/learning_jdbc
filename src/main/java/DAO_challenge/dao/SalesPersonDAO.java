package DAO_challenge.dao;

import DAO_challenge.model.SalesPerson;
import DAO_challenge.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SalesPersonDAO extends DataAccessObject<SalesPerson> {
  public static final String GET_ONE = "SELECT id, name FROM salesperson WHERE id = ?";
  public static final String INSERT = "INSERT INTO salesperson (name) VALUES (?)";

  public SalesPersonDAO(Connection connection){
    super(connection);
  }

  @Override
  public SalesPerson findById(long id) {
    SalesPerson salesPerson = new SalesPerson();
    try(PreparedStatement stmt = this.connection.prepareStatement(GET_ONE);){
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        salesPerson.setId(rs.getLong("id"));
        salesPerson.setName(rs.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return salesPerson;
  }

  @Override
  public SalesPerson create(SalesPerson dto, String sequence) {
    try(PreparedStatement stmt = this.connection.prepareStatement(INSERT);){
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
