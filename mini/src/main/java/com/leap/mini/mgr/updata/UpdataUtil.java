package com.leap.mini.mgr.updata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

/**
 * 更新工具
 * <p>
 * </> Created by weiyaling on 2017/4/18.
 */

public class UpdataUtil {

  static String UPDATE_MODE_BLOCK = "BLOCK";
  public static String UPDATE_MODE_SILENT = "SILENT";

  // 安装apk
  public static void installApk(Context context, String apkPath) {
    Intent intent = new Intent();
    intent.setAction(Intent.ACTION_VIEW);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      Uri contentUri = FileProvider.getUriForFile(context,
          context.getPackageName() + ".fileProvider", new File(apkPath));
      intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      context.grantUriPermission(context.getPackageName(), contentUri,
          Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.setDataAndType(contentUri, "application/vnd.android.package-archive");

    } else {
      intent.setDataAndType(Uri.fromFile(new File(apkPath)),
          "application/vnd.android.package-archive");
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    context.startActivity(intent);
    android.os.Process.killProcess(android.os.Process.myPid());
  }

  // 生成md5
  public static String calculateMD5ofFile(String filePath)
      throws IOException, NoSuchAlgorithmException {
    FileInputStream fs = new FileInputStream(filePath);
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] buffer = new byte[2048];
    int bytes = 0;
    do {
      bytes = fs.read(buffer, 0, 2048);
      if (bytes > 0)
        md.update(buffer, 0, bytes);

    } while (bytes > 0);
    byte[] Md5Sum = md.digest();
    fs.close();
    return ByteArraytoHexString(Md5Sum);
  }

  private static String ByteArraytoHexString(byte[] bytes) {
    StringBuilder hexString = new StringBuilder();
    for (int i = 0; i < bytes.length; i++) {
      String hex = Integer.toHexString(bytes[i] & 0xFF);
      if (hex.length() == 1) {
        hexString.append('0');
      }
      hexString.append(hex);
    }
    return hexString.toString();
  }
}
