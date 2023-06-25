package DAO_challenge.dao;

import DAO_challenge.model.PurchaseOrder;
import DAO_challenge.util.DataAccessObject;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PurchaseOrderDAO extends DataAccessObject<PurchaseOrder> {
  // DAO need not be a 1:1 mapping with a table. DAO is an interface to the DB. A DTO (example: PurchaseOrder) can have
  // data from multiple tables which is insert by the DAO (see: findById here)

  public static final String GET_DETAILS = "SELECT c.name, o.id, o.date, o.total, oi.products, s.name, oi.name, oi.price "
      + "FROM order o JOIN customer c on o.customer_id = c.id JOIN salesperson s on o.salesperson_id = s.id WHERE order_id = ?";
  public static final String GET_ONE = "SELECT id, date, orderItemIds, customerId, salespersonId, total FROM "
    + "purchase_order WHERE id = ?";
  public static final String INSERT = "INSERT INTO purchase_order (date, orderItemIds, customerId, salespersonId, "
      + "total) VALUES (?, ?, ?, ?, ?)";

  public PurchaseOrderDAO(Connection connection){
    super(connection);
  }

  @Override
  public PurchaseOrder findById(long id){
    PurchaseOrder order = new PurchaseOrder();
    try(PreparedStatement stmt = this.connection.prepareStatement(GET_ONE);) {
      stmt.setLong(1, id);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        order.setId(rs.getLong("id"));
        order.setDate(rs.getDate("date").toLocalDate());
        Array array = rs.getArray("orderItemIds");
        if (array != null) {
          ResultSet rs_orderItemIds = array.getResultSet();
          while(rs_orderItemIds.next()) {
            order.setOrderItemId(rs_orderItemIds.getLong("value"));
          }
        }
        order.setCustomerId(rs.getLong("customerId"));
        order.setSalespersonId(rs.getLong("salespersonId"));
        order.setTotal(rs.getLong("total"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
    return order;
  }

  @Override
  public PurchaseOrder create(PurchaseOrder dto, String sequence){
    try(PreparedStatement stmt = connection.prepareStatement(INSERT);){
      stmt.setDate(1, Date.valueOf(dto.getDate()));
      stmt.setArray(2, connection.createArrayOf("integer", dto.getOrderItemIds().toArray()));
      stmt.setLong(3, dto.getCustomerId());
      stmt.setLong(4, dto.getSalespersonId());
      stmt.setDouble(5, dto.getTotal());
      stmt.execute();
      int id = this.getLastVal(sequence);
      return this.findById(id);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }
}
