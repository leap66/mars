package com.leap.mini.model.network;

import java.io.Serializable;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :
 */
public class SortParam implements Serializable {
  private String property;
  private String direction;

  public SortParam() {
  }

  public SortParam(String property, String direction) {
    this.property = property;
    this.direction = direction;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public String getDirection() {
    return direction;
  }

  public void setDirection(String direction) {
    this.direction = direction;
  }
}
