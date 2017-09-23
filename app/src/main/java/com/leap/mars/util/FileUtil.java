package com.leap.mars.util;

import com.leap.mars.MarsApp;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author : ylwei
 * @time : 2017/9/20
 * @description :
 */
public class FileUtil {

  public static byte[] loadFile(File file) throws IOException {
    byte[] buffer;
    FileInputStream fis = new FileInputStream(file);
    ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
    byte[] b = new byte[1000];
    int n;
    while ((n = fis.read(b)) != -1) {
      bos.write(b, 0, n);
    }
    fis.close();
    bos.close();
    buffer = bos.toByteArray();
    return buffer;
  }

  public static void converFile(byte[] code, String fileName) {
    if (!fileName.contains(".mp3"))
      fileName = fileName + ".mp3";
    BufferedOutputStream bos = null;
    FileOutputStream fos = null;
    try {
      String filePath = MarsApp.getInstance().getCacheDir().getAbsolutePath();
      File file = new File(filePath, fileName);
      fos = new FileOutputStream(file);
      bos = new BufferedOutputStream(fos);
      bos.write(code);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (bos != null) {
        try {
          bos.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      }
    }
  }
}
