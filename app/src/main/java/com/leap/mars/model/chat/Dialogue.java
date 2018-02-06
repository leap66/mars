package com.leap.mars.model.chat;

import com.leap.mini.model.base.BEntity;

import java.util.Date;
import java.util.List;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description :
 */
public class Dialogue extends BEntity {

  private boolean ask;// 判断询问
  private Integer code;// 标示码
  private String info;// 询问信息
  private String loc;// 地址
  private String userId;// 用户ID
  private Date time;// 时间
  private String text;// 结果
  private String url;// 链接地址
  private List<News> list;// 新闻链接
  private String voiceName;// 语音文件名
  private long voiceLen;// 语音文件长度

  public boolean isAsk() {
    return ask;
  }

  public void setAsk(boolean ask) {
    this.ask = ask;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

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

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public Date getTime() {
    return time;
  }

  public void setTime(Date time) {
    this.time = time;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public List<News> getList() {
    return list;
  }

  public void setList(List<News> list) {
    this.list = list;
  }

  public String getVoiceName() {
    return voiceName;
  }

  public void setVoiceName(String voiceName) {
    this.voiceName = voiceName;
  }

  public long getVoiceLen() {
    return voiceLen;
  }

  public void setVoiceLen(long voiceLen) {
    this.voiceLen = voiceLen;
  }
}
