package com.leap.mini.model.base;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :  
 */
public class VersionedEntity extends BEntity {

  private long version;

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }
}
