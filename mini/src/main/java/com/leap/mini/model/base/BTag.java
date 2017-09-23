package com.leap.mini.model.base;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :  
 */
public class BTag extends VersionedEntity {

  private String name;

  public BTag() {
  }

  public BTag(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
