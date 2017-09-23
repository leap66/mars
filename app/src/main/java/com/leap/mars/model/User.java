package com.leap.mars.model;

import com.leap.mini.model.base.StandardEntity;

import java.util.Date;

/**
 * @author : ylwei
 * @time : 2017/9/8
 * @description :
 */
public class User extends StandardEntity {
  private String name;// 用户名
  private String shortName;// 昵称
  private String mobile;// 手机号
  private String email;// 邮箱
  private String idCard;// 身份证号
  private Date birth;// 出生日期
  private String remark;// 备注
  private Integer education;// 学历
  private String photoName;// 头像名称
  private String photoUrl;// 头像URL

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

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getIdCard() {
    return idCard;
  }

  public void setIdCard(String idCard) {
    this.idCard = idCard;
  }

  public Date getBirth() {
    return birth;
  }

  public void setBirth(Date birth) {
    this.birth = birth;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getEducation() {
    return education;
  }

  public void setEducation(Integer education) {
    this.education = education;
  }

  public String getPhotoName() {
    return photoName;
  }

  public void setPhotoName(String photoName) {
    this.photoName = photoName;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }
}
