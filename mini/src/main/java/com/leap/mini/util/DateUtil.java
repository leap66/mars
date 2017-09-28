package com.leap.mini.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 日期格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */
public class DateUtil {
  public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm";

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

  /**
   * 获取某一天的开始时间或结束时间
   */
  private Date getDate(Date date, boolean isStart) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    Date start = calendar.getTime();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    calendar.add(Calendar.SECOND, -1);
    Date end = calendar.getTime();
    // if (end.getTime() > new Date().getTime()) {
    // end = new Date();
    // }
    if (isStart) {
      return start;
    } else {
      return end;
    }
  }
}
