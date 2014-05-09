package org.fire.cost.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author liutengfei
 */
public class DateUtil extends DateUtils {
    /**
     * 是否显示秒
     *
     * @param date
     * @param showSecond true：显示
     * @return
     */
    public static String makeDate2Str(Date date, boolean showSecond) {
        if (date == null) {
            throw new RuntimeException("输入正确日期");
        }
        String text = "yyyy-MM-dd";
        if (showSecond) {
            text = "yyyy-MM-dd HH:mm:ss";
        }
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
    public static Date makeStr2Date(String dateStr, boolean showSecond) throws ParseException {
        if (dateStr == null || dateStr.length() == 0) {
            throw new RuntimeException("输入正确日期字符串");
        }
        String text = "yyyy-MM-dd";
        if (showSecond) {
            text = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat format = new SimpleDateFormat(text);
        return format.parse(dateStr);
    }

    /**
     * 获取本周第一天日期
     *
     * @return
     */
    public static Date getFirstDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1;
        int current = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, min - current);
        Date start = calendar.getTime();
        return start;
    }

    /**
     * 获取本周最后一天日期
     *
     * @return
     */
    public static Date getEndDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1;
        int current = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, min - current);
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date end = calendar.getTime();
        return end;
    }

    /**
     * 获取本周第一天日期
     *
     * @return
     */
    public static Date getFirstDayOfMonth() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return cale.getTime();
    }

    /**
     * 获取本周最后一天日期
     *
     * @return
     */
    public static Date getEndDayOfMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getCurrentYear() {
        Calendar cale = Calendar.getInstance();
        int year = cale.get(Calendar.YEAR);
        return year;
    }

    /**
     * 将日期类型转换成字符串
     *
     * @param date
     * @return
     */
    @Deprecated
    public static String makeDate2Str(Date date) {
        if (date == null) {
            throw new RuntimeException("输入正确日期");
        }
        String text = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(text);
        return format.format(date);
    }

    /**
     * 将字符串类型转换成日期
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    @Deprecated
    public static Date makeStr2Date(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.length() == 0) {
            throw new RuntimeException("输入正确日期字符串");
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        return date;
    }
}
