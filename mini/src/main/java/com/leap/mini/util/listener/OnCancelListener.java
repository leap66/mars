package com.leap.mini.util.listener;

/**
 * 取消接口
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface OnCancelListener<V, T> {
  void onCancel(V view, T data);
}
