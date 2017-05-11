package com.leap.mars;

import android.app.Application;

/**
 * MarsApp
 * <p>
 * </> Created by weiyaling on 2017/5/11.
 */

public class MarsApp extends Application {
  private static MarsApp instance;

  public static MarsApp getInstance() {
    return instance;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    instance = this;
  }
}
