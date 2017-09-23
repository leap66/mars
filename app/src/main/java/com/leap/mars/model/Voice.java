package com.leap.mars.model;

import com.leap.mini.model.base.BEntity;

/**
 * @author : ylwei
 * @time : 2017/9/20
 * @description :
 */
public class Voice extends BEntity {

  private String userId;
  private Long len;// 接收文件长度
  private byte[] code;// 接收、返回File字节流
  private String info;// 接收、返回识别信息
  private String name;// 文件名
  private boolean ask;// 是否为文件
  private String format;// 语音文件的格式，pcm 或者 wav 或者 amr。不区分大小写

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Long getLen() {
    return len;
  }

  public void setLen(Long len) {
    this.len = len;
  }

  public byte[] getCode() {
    return code;
  }

  public void setCode(byte[] code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAsk() {
    return ask;
  }

  public void setAsk(boolean ask) {
    this.ask = ask;
  }

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }
}
