package com.leap.mini.net.network.request;

/**
 * 排序参数实体类
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class SortParam {
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
