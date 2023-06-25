package DAO_challenge.model;

import DAO_challenge.util.DataTransferObject;
import java.time.LocalDate;
import java.util.ArrayList;


public class PurchaseOrder implements DataTransferObject {
  private long id;
  private LocalDate date;
  private ArrayList<Long> orderItemIds = new ArrayList<>();
  private ArrayList<OrderItem> orderItems = new ArrayList<>();
  private long customerId;
  private long salespersonId;
  private double total;

  @Override
  public long getId() { return id; }

  public void setId(long id) { this.id = id; }

  public LocalDate getDate() { return date; }

  public void setDate(LocalDate date) { this.date = date; }

  public ArrayList<Long> getOrderItemIds() { return orderItemIds; }

  public void setOrderItemId(Long orderItemId) { this.orderItemIds.add(orderItemId); }

  public long getCustomerId() { return customerId; }

  public void setCustomerId(long customerId) { this.customerId = customerId; }

  public long getSalespersonId() { return salespersonId; }

  public void setSalespersonId(long salespersonId) { this.salespersonId = salespersonId; }

  public double getTotal() { return total; }

  public void setTotal(double total) { this.total = total; }
}
