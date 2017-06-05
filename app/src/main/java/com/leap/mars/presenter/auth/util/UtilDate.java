package com.leap.mars.presenter.auth.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class UtilDate {

	private static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA);
	
	public static String getFormatedTime(Long timeMillis){
		return sdf.format(timeMillis);
	}
}
