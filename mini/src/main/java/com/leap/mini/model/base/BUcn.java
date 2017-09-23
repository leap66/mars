package com.leap.mini.model.base;

/**
 * @author : ylwei
 * @time : 2017/9/5
 * @description :
 */
public class BUcn extends BEntity {

  private String code;
  private String name;
  private boolean newUcn;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isNewUcn() {
    return newUcn;
  }

  public void setNewUcn(boolean newUcn) {
    this.newUcn = newUcn;
  }
}
