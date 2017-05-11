package com.leap.mini.util.listener;

/**
 * 确认接口
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface OnConfirmListener<V, T> {
  void onConfirm(V view, T data);
}
