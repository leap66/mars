package com.leap.mini.mgr.logger;

import java.util.Date;

/**
 * 页面描述：
 *
 * Created by ditclear on 2016/11/26.
 */

public class LogModel {
  private LogLevel level;
  private Date timestamp;
  private String message;
  private String thread;
  private String fileName;
  private String function;
  private int line;

  private String osVersion;
  private String hostName;
  private String appVersion;
  private String appId;
  private int appBuild;
  private String deviceModel;
  private String deviceName;

  public LogLevel getLevel() {
    return level;
  }

  public void setLevel(LogLevel level) {
    this.level = level;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getThread() {
    return thread;
  }

  public void setThread(String thread) {
    this.thread = thread;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFunction() {
    return function;
  }

  public void setFunction(String function) {
    this.function = function;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public int getAppBuild() {
    return appBuild;
  }

  public void setAppBuild(int appBuild) {
    this.appBuild = appBuild;
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void setDeviceModel(String deviceModel) {
    this.deviceModel = deviceModel;
  }

  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }
}
