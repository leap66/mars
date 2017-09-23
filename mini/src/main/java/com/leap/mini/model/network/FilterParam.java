package com.leap.mini.model.network;

import java.io.Serializable;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :
 */
public class FilterParam implements Serializable {
  private String property;
  private Object value;

  public FilterParam() {
  }

  public FilterParam(String property, Object value) {
    this.property = property;
    this.value = value;
  }

  public String getProperty() {
    return property;
  }

  public void setProperty(String property) {
    this.property = property;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public String toString() {
    return property + value.toString();
  }
}
