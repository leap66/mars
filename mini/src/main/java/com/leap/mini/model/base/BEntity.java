package com.leap.mini.model.base;

import java.io.Serializable;


/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :  
 */
public class BEntity implements Serializable {

  private String id; // 唯一标示ID

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
