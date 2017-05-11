package com.leap.mini.mgr.updata;

public interface IUpdateListener {
  int UPDATE_CODE_NEWEST = 0x1;
  int UPDATE_CODE_FAIL = 0x2;
  int UPDATE_CODE_IGNORE = 0x3;

  void onCancel(int type, String... args);
}
