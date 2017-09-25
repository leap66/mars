package com.leap.mini.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * JSON格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class GsonUtil {
  private static Gson instance;

  /**
   * 将json字符串转为 java对象
   */
  synchronized public static <T> T parse(String json, Class<T> classOfT) {
    return getInstance().fromJson(json, classOfT);
  }

  /**
   * 将json字符串转为 java列表对象
   */
  synchronized public static <T> List<T> parseList(String json, Type classOfT) {
    return getInstance().fromJson(json, classOfT);
  }

  /**
   * 将obj对象转为json格式数据
   */
  synchronized public static String toJson(Object obj) {
    return getInstance().toJson(obj);
  }

  /**
   * 获取gson实例(排除了FINAL、TRANSIENT、STATIC)
   */
  public static Gson getInstance() {
    if (IsEmpty.object(instance))
      instance = new GsonBuilder().serializeNulls()
          .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
          .registerTypeAdapter(String.class, new StringConverter())
          .registerTypeAdapter(Date.class, new DateConverter()).create();
    return instance;
  }

  /**
   * 实现了 序列化 接口 对为null的字段进行转换
   */
  private static class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {
    // 字符串为null 转换成"",否则为字符串类型
    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      if (IsEmpty.object(json.getAsJsonPrimitive()))
        return null;
      return json.getAsJsonPrimitive().getAsString();
    }

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
      return src == null || src.equals("null") ? new JsonPrimitive("") : new JsonPrimitive(src);
    }
  }

  private static class DateConverter implements JsonDeserializer<Date>, JsonSerializer<Date> {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {
      if (IsEmpty.object(json.getAsString()))
        return null;
      try {
        return sdf.parse(json.getAsString());
      } catch (ParseException e) {
        return null;
      }
    }

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
      if (IsEmpty.object(src))
        return null;
      return new JsonPrimitive(sdf.format(src));
    }
  }

}
