package com.leap.mini.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * APP 版本更新实体类
 * <p>
 * </>Created by weiyaling on 2017/2/22.
 */
public class UpdateModel implements Serializable {

  private static final long serialVersionUID = 2965065498031595999L;
  public boolean upgradable;
  public String updateMode;
  public String description;
  public String versionName;
  public BigDecimal versionCode;
  public String downloadUrl;
  public String md5;

  public boolean already;// 是否准备好
  public long position;// 下载位置
  public String fileName;// 下载文件名
}