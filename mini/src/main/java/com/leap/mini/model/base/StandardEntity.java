package com.leap.mini.model.base;

import java.util.Date;

/**
 * @author : ylwei
 * @time : 2017/9/5
 * @description :
 */
public class StandardEntity extends VersionedEntity {

  private Date created;
  private BUcn creator;
  private Date lastModified;
  private BUcn lastModifier;

  public BUcn getCreator() {
    return creator;
  }

  public void setCreator(BUcn creator) {
    this.creator = creator;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getLastModified() {
    return lastModified;
  }

  public void setLastModified(Date lastModified) {
    this.lastModified = lastModified;
  }

  public BUcn getLastModifier() {
    return lastModifier;
  }

  public void setLastModifier(BUcn lastModifier) {
    this.lastModifier = lastModifier;
  }
}
