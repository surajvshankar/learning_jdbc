package DAO_challenge.dao;

import DAO_challenge.model.OrderItem;
import DAO_challenge.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class OrderItemDAO extends DataAccessObject<OrderItem> {
  private static final String GET_ONE = "SELECT id, name, price, quantity FROM order_item name WHERE id = ?";
  private static final String INSERT = "INSERT INTO order_item (name, price, quantity) VALUES (?, ?, ?)";

  public OrderItemDAO(Connection connection) {
    super(connection);
  }

  @Override
  public OrderItem findById(long id) {
    OrderItem orderItem = new OrderItem();
    try(PreparedStatement stmt = this.connection.prepareStatement(GET_ONE);) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      while(rs.next()) {
        orderItem.setId(rs.getLong("id"));
        orderItem.setName(rs.getString("name"));
        orderItem.setPrice(rs.getDouble("price"));
        orderItem.setQuantity(rs.getLong("quantity"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return orderItem;
  }

  @Override
  public OrderItem create(OrderItem dto, String sequence) {
    try(PreparedStatement stmt = this.connection.prepareStatement(INSERT);){
      stmt.setString(1, dto.getName());
      stmt.setDouble(2, dto.getPrice());
      stmt.setLong(3, dto.getQuantity());
      stmt.execute();
      int id = this.getLastVal(sequence);
      return this.findById(id);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
