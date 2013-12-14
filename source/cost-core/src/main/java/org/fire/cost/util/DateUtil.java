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
		SimpleDateFormat format = new SimpleDateFormat("mm/dd/yyyy");
		Date date = format.parse(dateStr);
		return date;
	}
}
