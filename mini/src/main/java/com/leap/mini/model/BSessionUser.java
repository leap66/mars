/**
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2016，所有权利保留。
 * 
 * 项目名：	dpos-auth-api
 * 文件名：	SessionUser.java
 * 模块说明：	
 * 修改历史：
 * 2016年6月12日 - linzhu - 创建。
 */
package com.leap.mini.model;

import java.io.Serializable;

/**
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class BSessionUser implements Serializable {

  private static final long serialVersionUID = -1229184913897748367L;

  private String id;
  private String name;
  private String mobile;
  private String photo;
  private String idCard;

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

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }
}
