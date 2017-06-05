package com.leap.mars.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Base64;

/**
 * Base64编码解码工具类
 * <p>
 * </>Created by weiyaling on 2017/6/2.
 */

public class Base64Util {

  /**
   * 对字符串进行Base64编码
   */
  public static String S2B(String text) {
    return Base64.encodeToString(text.getBytes(), Base64.DEFAULT);
  }

  /**
   * 对字符串进行Base64解码
   */
  public static String B2S(String encodedString) {
    return new String(Base64.decode(encodedString, Base64.DEFAULT));
  }

  /**
   * 对文件进行Base64编码
   */
  public static String F2B(@NonNull String filePath) {
    File file = new File(filePath);
    return F2B(file);
  }

  /**
   * 对文件进行Base64编码
   */
  public static String F2B(@NonNull File file) {
    String encodedString = null;
    FileInputStream inputFile = null;
    try {
      inputFile = new FileInputStream(file);
      byte[] buffer = new byte[(int) file.length()];
      inputFile.read(buffer);
      inputFile.close();
      encodedString = Base64.encodeToString(buffer, Base64.DEFAULT);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return encodedString;
  }

  /**
   * 对文件进行Base64解码
   */
  public static File B2F(String encodedString, String filePath) {
    File desFile = new File(filePath);
    FileOutputStream fos = null;
    try {
      byte[] decodeBytes = Base64.decode(encodedString.getBytes(), Base64.DEFAULT);
      fos = new FileOutputStream(desFile);
      fos.write(decodeBytes);
      fos.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return desFile;
  }

  /**
   * 对图片进行Base64编码
   */
  public static String I2B(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    if (bitmap != null) {
      bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
      byte[] byteArray = stream.toByteArray();
      return Base64.encodeToString(byteArray, Base64.DEFAULT);
    } else {
      return null;
    }
  }

  /**
   * 对图片进行Base64解码
   */
  public static Bitmap B2I(String base64) {
    Bitmap bitmap = null;
    try {
      byte[] bitmapArray;
      bitmapArray = Base64.decode(base64, Base64.DEFAULT);
      bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return bitmap;
  }
}
