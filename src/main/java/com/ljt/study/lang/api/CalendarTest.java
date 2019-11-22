package com.ljt.study.lang.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author LiJingTang
 * @date 2019-11-21 15:18
 */
public class CalendarTest {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Calendar calendar = Calendar.getInstance();
        Calendar calendar = new GregorianCalendar(2014, 0, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String today = sdf.format(calendar.getTime()); // 今天
        Calendar c = (Calendar) calendar.clone();
        c.add(Calendar.WEEK_OF_YEAR, -1);
        String lastWeek = sdf.format(c.getTime()); // 上周
        calendar.add(Calendar.MONTH, -1);
        String lastMonth = sdf.format(calendar.getTime()); // 上个月
        calendar.add(Calendar.MONTH, -2);
        String last3Month = sdf.format(calendar.getTime()); // 上三个月

        System.out.println(today);
        System.out.println(lastWeek);
        System.out.println(lastMonth);
        System.out.println(last3Month);
    }

}
