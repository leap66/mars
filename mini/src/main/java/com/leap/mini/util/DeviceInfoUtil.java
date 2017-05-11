package com.leap.mini.util;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 功能描述 设备信息获取工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class DeviceInfoUtil {

  public static final String TAG = DeviceInfoUtil.class.getSimpleName();

  /**
   * 
   * 功能描述：获取移动设备国际码（设备唯一性），如果获取失败，则返回"000000000000000"
   */
  public static String getDeviceId(Context context) {
    String imei = null;
    // imei = ((TelephonyManager)
    // context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    return TextUtils.isEmpty(imei) ? "000000000000000" : imei;
  }

  /**
   * 
   * 功能描述：GPS是否可用
   */
  public static boolean isGpsEnabled(Context mContext) {
    LocationManager mLocationManager = (LocationManager) mContext
        .getSystemService(Context.LOCATION_SERVICE);
    return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
  }

  /**
   * 
   * 功能描述：获取屏幕宽度
   */
  public static int getDisplayWidthPixels(Activity mActivity) {
    DisplayMetrics dm = new DisplayMetrics();
    mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
    return dm.widthPixels;
  }

  /**
   * 
   * 功能描述：获取屏幕高度
   */
  public static int getDisplayHeightPixels(Context context) {
    DisplayMetrics dm = new DisplayMetrics();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getMetrics(dm);
    return dm.heightPixels;
  }

  /**
   * 
   * 功能描述：获取WIFI地址
   * 
   * @return 返回wifi地址，如果失败返回0.0.0.0
   */
  public static String getWIFIIPAddress(Context mContext) {
    // 获取wifi服务
    WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    if (wifiManager == null) {
      return "0.0.0.0";
    }
    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
    if (wifiInfo == null) {
      return "0.0.0.0";
    }
    int ipAddress = wifiInfo.getIpAddress();
    return (ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF)
        + "." + (ipAddress >> 24 & 0xFF);
  }

  /**
   * 
   * 功能描述：开启或关闭WIFI
   */
  public static void setWifiStatus(Context mContext, boolean isOpen) {
    WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    if (wifiManager != null) {
      wifiManager.setWifiEnabled(isOpen);
    }
  }
}
