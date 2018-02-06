package com.leap.mars.model.chat;

import com.leap.mars.model.Voice;
import com.leap.mini.model.base.BEntity;

import java.util.Date;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public class Chat extends BEntity {

  private String info;// 询问信息
  private String loc;// 地址
  private String userid;// 用户ID
  private Date time;// 时间
  private Voice voice;

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getLoc() {
    return loc;
  }

  public void setLoc(String loc) {
    this.loc = loc;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public Voice getVoice() {
    return voice;
  }

  public void setVoice(Voice voice) {
    this.voice = voice;
  }
}
