package com.leap.mars.model.chat;

/**
 * @author : ylwei
 * @time : 2017/9/18
 * @description : 会话消息类型
 */
public enum DialogueType {
  activeChat("请求文字信息"), receiveChat("接收文字信息"),
  activeVoice("请求语音信息"), receiveVoice("接收语音信息"),
  receiveNew("新闻"),receiveVoic("接收语音信息"),

  ;

  private String name;

  DialogueType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
