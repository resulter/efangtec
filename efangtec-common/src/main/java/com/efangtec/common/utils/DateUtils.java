package com.efangtec.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取时间的前几天或后几天
     * @param date flag时间
     * @param number 正数为后几天，负数为前几天
     * @return
     */
    public static Date getDateByNumber(Date date, int number) {
        Calendar ca = Calendar.getInstance();//得到一个Calendar的实例
        ca.setTime(new Date()); //设置时间为当前时间
        ca.add(Calendar.DATE, -1);
        return ca.getTime();
    }

    /**
     * 获取某年第一天
     * @param year
     * @return
     */
    public static String getYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();//得到一个Calendar的实例
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return DateFormatUtils.format(currYearFirst, "yyyy-MM-dd");
    }

    /**
     * 获取某年第一天
     * @param month
     * @return
     */
    public static String getMonthFrist(int year, int month) {
        Calendar calendar = Calendar.getInstance();//得到一个Calendar的实例
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        Date currYearFirst = calendar.getTime();
        return DateFormatUtils.format(currYearFirst, "yyyy-MM-dd");
    }

    /**
     * 获取某月的最后一天
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        return DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd");
    }
}
