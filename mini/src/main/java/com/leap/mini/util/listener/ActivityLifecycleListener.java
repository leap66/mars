package com.leap.mini.util.listener;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Application.ActivityLifecycleCallbacks
 * <p>
 * </> Created by weiyaling on 2017/5/2.
 */

public abstract class ActivityLifecycleListener implements Application.ActivityLifecycleCallbacks {
  @Override
  public void onActivityCreated(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityStarted(Activity activity) {

  }

  @Override
  public abstract void onActivityResumed(Activity activity);

  @Override
  public void onActivityPaused(Activity activity) {

  }

  @Override
  public void onActivityStopped(Activity activity) {

  }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

  }

  @Override
  public void onActivityDestroyed(Activity activity) {

  }
}
