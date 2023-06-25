package DAO_challenge.model;

import DAO_challenge.util.DataTransferObject;


public class Customer implements DataTransferObject {
  private long id;
  protected String name;

  @Override
  public long getId() { return id; }

  public void setId(long id) { this.id = id; }

  public String getName() { return name; }

  public void setName(String name) { this.name = name; }
}
