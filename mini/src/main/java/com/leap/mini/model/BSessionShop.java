package com.leap.mini.model;

import java.io.Serializable;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class BSessionShop implements Serializable {

  private static final long serialVersionUID = -1229184913897748367L;
  private String id;
  private String name;
  private String shortName;
  private String address;
  private String domain;
  private String owner;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
