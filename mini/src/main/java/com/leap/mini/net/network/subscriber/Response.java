package com.leap.mini.net.network.subscriber;

import java.util.List;
import java.util.Map;

/**
 * 网络返回数据类
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class Response<T> {
  private boolean success;
  private Map<String, String> fields;
  private List<String> message;
  private T data;
  private int total;
  private boolean more;

  public boolean isMore() {
    return more;
  }

  public void setMore(boolean more) {
    this.more = more;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public Map<String, String> getFields() {
    return fields;
  }

  public void setFields(Map<String, String> fields) {
    this.fields = fields;
  }

  public List<String> getMessage() {
    return message;
  }

  public void setMessage(List<String> message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
