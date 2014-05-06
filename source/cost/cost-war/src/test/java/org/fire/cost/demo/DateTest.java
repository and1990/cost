package org.fire.cost.demo;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * 注释：
 * 时间：2014-05-02 下午11:48
 * 作者：liutengfei【刘腾飞】
 */
public class DateTest {
    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1; //获取周开始基准
        int current = calendar.get(Calendar.DAY_OF_WEEK); //获取当天周内天数
        calendar.add(Calendar.DAY_OF_WEEK, min - current); //当天-基准，获取周开始日期
        Date start = calendar.getTime();
        calendar.add(Calendar.DAY_OF_WEEK, 6); //开始+6，获取周结束日期
        Date end = calendar.getTime();
        System.out.printf("start=%tF, end=%tF\n", start, end);
    }

    @Test
    public void testEndDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_WEEK) + 1;
        int current = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, 6 - current);
        Date end = calendar.getTime();
        System.out.println(end);
    }

    @Test
    public void testMonth() {
        //获取前月的第一天
        Calendar cal_1 = Calendar.getInstance();//获取当前日期
        //cal_1.add(Calendar.MONTH, 1);
        cal_1.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        System.out.println("-----1------firstDay:" + cal_1.getTime());
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);//设置为1号,当前日期既为本月第一天
        System.out.println("-----2------lastDay:" + cale.getTime());
    }

    @Test
    public void testMD5() {
        /*byte[] bytes = DigestUtils.md5Digest("123".getBytes());
        System.out.println(new String(bytes));*/
        String password = DigestUtils.md5Hex("123");
        System.out.println(password);
    }
}
