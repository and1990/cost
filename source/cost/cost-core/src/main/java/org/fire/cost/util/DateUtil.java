package org.fire.cost.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 * 
 * @author liutengfei
 * 
 */
public class DateUtil
{
	/**
	 * 是否显示秒
	 * 
	 * @param date
	 * @param showSeconed
	 * @return
	 */
	public static String makeDate2Str(Date date, boolean showSeconed)
	{
		if (date == null)
		{
			return null;
		}
		String dateStr = date.toString();
		if (showSeconed)
		{
			return dateStr.substring(0, dateStr.length() - 2);
		} else
		{
			return dateStr.substring(0, 10);
		}
	}

	/**
	 * 将日期类型转换成字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String makeDate2Str(Date date)
	{
		if (date == null)
		{
			return null;
		}
		String dateStr = date.toString();
		return dateStr.substring(0, dateStr.length() - 2);
	}

	/**
	 * 将字符串类型转换成日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date makeStr2Date(String dateStr) throws ParseException
	{
		if (dateStr == null || dateStr.length() == 0)
		{
			return null;
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dateStr);
		return date;
	}
}
