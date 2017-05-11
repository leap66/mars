package com.leap.mini.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class DateUtil {
  public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

  public static String format(Date date, String format) {
    if (IsEmpty.object(date))
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    return sdf.format(date);
  }

  public static Date parse(String date, String format) throws ParseException {
    if (IsEmpty.object(date))
      return null;
    SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
    return sdf.parse(date);
  }

}
