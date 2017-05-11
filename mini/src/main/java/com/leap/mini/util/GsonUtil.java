package com.leap.mini.util;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * JSON格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class GsonUtil {
  private static Gson gson;

  /**
   * 将json字符串转为 java对象
   */
  synchronized public static <T> T parse(String json, Class<T> classOfT) {
    return getGsonInstance(false).fromJson(json, classOfT);
  }

  /**
   * 将json字符串转为 java列表对象
   */
  synchronized public static <T> List<T> parse(String json, Type type) {
    return getGsonInstance(false).fromJson(json, type);
  }

  /**
   * 将obj对象转为json格式数据
   */
  synchronized public static String toJson(Object obj) {
    return getGsonInstance(false).toJson(obj);
  }

  /**
   * 获取gson实例
   */
  public static final Gson getGsonInstance(boolean isExplain) {
    if (!isExplain) {
      gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    } else {
      gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
    return gson;
  }

  /**
   * 获取gson实例(排除了FINAL、TRANSIENT、STATIC)
   */
  public static final Gson getGsonInstance() {
    gson = new GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
        .serializeNulls().create();
    return gson;
  }
}
