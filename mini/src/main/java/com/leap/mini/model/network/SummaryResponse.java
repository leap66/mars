package com.leap.mini.model.network;

/**
 * @author  : ylwei
 * @time    : 2017/9/5
 * @description :
 */
public class SummaryResponse<T, S> extends Response<T> {
  private S summary;

  public S getSummary() {
    return summary;
  }

  public void setSummary(S summary) {
    this.summary = summary;
  }

}
