package org.fire.cost.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author liutengfei
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
            throw new RuntimeException("输入正确日期");
        }
        String dateStr = date.toString();
        String text = "yyyy-MM-dd";
        if (showSeconed)
        {
            text = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(text);
        return format.format(date);
    }

    /**
     * 将日期类型转换成字符串
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String makeDate2Str(Date date)
    {
        if (date == null)
        {
            throw new RuntimeException("输入正确日期");
        }
        String dateStr = date.toString();
        String text = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(text);
        return format.format(date);
    }


    /**
     * 将字符串类型转换成日期
     *
     * @param dateStr
     * @param showSecond
     * @return
     */
    public static Date makeStr2Date(String dateStr, boolean showSecond) throws ParseException
    {
        if (dateStr == null || dateStr.length() == 0)
        {
            throw new RuntimeException("输入正确日期字符串");
        }
        String text = "yyyy-MM-dd";
        if (showSecond)
        {
            text = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(text);
        return format.parse(dateStr);
    }

    /**
     * 将字符串类型转换成日期
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    @Deprecated
    public static Date makeStr2Date(String dateStr) throws ParseException
    {
        if (dateStr == null || dateStr.length() == 0)
        {
            throw new RuntimeException("输入正确日期字符串");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        return date;
    }
}
