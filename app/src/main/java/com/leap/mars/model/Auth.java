package com.leap.mars.model;

/**
 * @author : ylwei
 * @time : 2017/9/6
 * @description : 认证
 */
public class Auth {
  private String id;// 唯一标识符
  private String mobile;// 手机号
  private String password;// 密码
  private String code;// 验证码
  private String remark;// 备注

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
