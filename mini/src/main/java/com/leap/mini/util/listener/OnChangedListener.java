package com.leap.mini.util.listener;

/**
 * 改变接口
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public interface OnChangedListener<T> {
  void onChange(T oldValue, T newValue);
}
