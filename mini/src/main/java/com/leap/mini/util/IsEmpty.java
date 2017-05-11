package com.leap.mini.util;

import java.util.List;
import java.util.Set;

/**
 * 判空格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class IsEmpty {

  public static boolean list(List object) {
    return null == object || object.size() == 0;
  }

  // 判断所有的list是否都为空或者null
  public static boolean list(List... objects) {
    boolean allNull = true;
    for (List obj : objects) {
      if (null != obj && obj.size() > 0) {
        allNull = false;
        break;
      }
    }
    return allNull;
  }

  public static boolean list(Set object) {
    return null == object || object.size() == 0;
  }

  public static boolean string(String object) {
    return null == object || "".equals(object);
  }

  public static boolean string(String... objects) {
    boolean allEmpty = true;
    for (String s : objects) {
      if (null == s || "".equals(s)) {
        allEmpty = false;
        break;
      }
    }
    return allEmpty;
  }

  public static boolean stringWithSomeEmpty(String... objects) {
    boolean someEmpty = false;
    for (String s : objects) {
      if (null == s || "".equals(s)) {
        someEmpty = true;
        break;
      }
    }
    return someEmpty;
  }

  public static int whereIsEmptyStr(String... objects) {
    int emptyIndex = -1;
    for (int i = 0; i < objects.length; i++) {
      if (null == objects[i] || "".equals(objects[i])) {
        emptyIndex = i;
        break;
      }
    }
    return emptyIndex;
  }

  public static boolean object(Object object) {
    return null == object;
  }
}
