package com.example.demo.util;

import java.util.Calendar;

/**
 * @Author: shiguang
 * @Date: 2021/12/6
 * @Description: 用于辅助获取特定时间
 **/
public class TimeUtil {
    public static long getSecondsNextEarlyMorning(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR,1);
        cal.set(Calendar.HOUR_OF_DAY,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.MILLISECOND,0);
        return (cal.getTimeInMillis()-System.currentTimeMillis())/1000;
    }
}
