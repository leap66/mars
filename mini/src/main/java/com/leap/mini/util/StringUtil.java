package com.leap.mini.util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串格式化工具
 * <p>
 * </> Created by weiyaling on 2017/3/7.
 */

public class StringUtil {

  private StringUtil() {
  }

  public static String format(String pattern, Object... arguments) {
    if (IsEmpty.object(arguments))
      return null;
    MessageFormat format = new MessageFormat(pattern);
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i] == null) {
        format.setFormat(i, null);
      }
    }
    return format.format(arguments);
  }

  /**
   * 快速判断字符串是否为手机号
   */
  public static boolean isMobileNO(String mobiles) {
    Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
    Matcher m = p.matcher(mobiles);
    return m.matches();
  }

  public static boolean isEmail(String email) {
    String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    Pattern p = Pattern.compile(str);
    Matcher m = p.matcher(email);

    return m.matches();
  }

  // 验证固话
  public static boolean isPhoneNumberValid(String phoneNumber) {
    String expression = "^(\\(\\d{3,4}\\)|\\d{3,4}-)?\\d{7,8}$";
    Pattern pattern = Pattern.compile(expression);
    Matcher m = pattern.matcher(phoneNumber);
    return m.matches();

  }

  // 判断字符串是否为数字
  private static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    if (isNum.matches()) {
      return true;
    } else {
      return false;
    }
  }

  // 判断字符串是否为日期格式
  public static boolean isDate(String strDate) {
    Pattern pattern = Pattern.compile(
        "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
    Matcher m = pattern.matcher(strDate);
    if (m.matches()) {
      return true;
    } else {
      return false;
    }
  }

  // 身份证的有效验证
  public static boolean isIDCard(String idstr) {
    String[] ValCodeArr = {
        "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
    String[] Wi = {
        "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
    String ai = "";
    // ================ 号码的长度15或者18位 ================
    if (idstr.length() != 15 && idstr.length() != 18) {
      return false;
    }
    // ================ 数字 除最后以为都为数字 ================
    if (idstr.length() == 18) {
      ai = idstr.substring(0, 17);
    } else if (idstr.length() == 15) {
      ai = idstr.substring(0, 6) + "19" + idstr.substring(6, 15);
    }
    if (isNumeric(ai) == false) {
      // errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
      return false;
    }
    // ================ 出生年月是否有效 ================
    String strYear = ai.substring(6, 10);// 年份
    String strMonth = ai.substring(10, 12);// 月份
    String strDay = ai.substring(12, 14);// 日
    if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
      // errorInfo = "身份证生日无效。";
      return false;
    }
    GregorianCalendar gc = new GregorianCalendar();
    SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
    try {
      if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150 || (gc.getTime().getTime()
          - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
        // errorInfo = "身份证生日不在有效范围。";
        return false;
      }
    } catch (NumberFormatException e) {
      e.printStackTrace();
    } catch (java.text.ParseException e) {
      e.printStackTrace();
    }
    if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
      // errorInfo = "身份证月份无效";
      return false;
    }
    if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
      // errorInfo = "身份证日期无效";
      return false;
    }
    // ================ 地区码时候有效 ================
    Hashtable h = getAreaCode();
    if (h.get(ai.substring(0, 2)) == null) {
      // errorInfo = "身份证地区编码错误。";
      return false;
    }
    // ================ 判断最后一位的值 ================
    int totalmulAiWi = 0;
    for (int i = 0; i < 17; i++) {
      totalmulAiWi = totalmulAiWi
          + Integer.parseInt(String.valueOf(ai.charAt(i))) * Integer.parseInt(Wi[i]);
    }
    int modValue = totalmulAiWi % 11;
    String strVerifyCode = ValCodeArr[modValue];
    ai = ai + strVerifyCode;

    if (idstr.length() == 18) {
      if (ai.equals(idstr) == false) {
        // errorInfo = "身份证无效，不是合法的身份证号码";
        return false;
      }
    } else {
      return true;
    }
    return true;
  }

  // 设置地区编码
  private static Hashtable getAreaCode() {
    Hashtable hashtable = new Hashtable();
    hashtable.put("11", "北京");
    hashtable.put("12", "天津");
    hashtable.put("13", "河北");
    hashtable.put("14", "山西");
    hashtable.put("15", "内蒙古");
    hashtable.put("21", "辽宁");
    hashtable.put("22", "吉林");
    hashtable.put("23", "黑龙江");
    hashtable.put("31", "上海");
    hashtable.put("32", "江苏");
    hashtable.put("33", "浙江");
    hashtable.put("34", "安徽");
    hashtable.put("35", "福建");
    hashtable.put("36", "江西");
    hashtable.put("37", "山东");
    hashtable.put("41", "河南");
    hashtable.put("42", "湖北");
    hashtable.put("43", "湖南");
    hashtable.put("44", "广东");
    hashtable.put("45", "广西");
    hashtable.put("46", "海南");
    hashtable.put("50", "重庆");
    hashtable.put("51", "四川");
    hashtable.put("52", "贵州");
    hashtable.put("53", "云南");
    hashtable.put("54", "西藏");
    hashtable.put("61", "陕西");
    hashtable.put("62", "甘肃");
    hashtable.put("63", "青海");
    hashtable.put("64", "宁夏");
    hashtable.put("65", "新疆");
    // hashtable.put("71", "台湾");
    // hashtable.put("81", "香港");
    // hashtable.put("82", "澳门");
    // hashtable.put("91", "国外");
    return hashtable;
  }

}
