package com.example.demo;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


/**
 * @Author: shiguang
 * @Date: 2022/3/8
 * @Description:
 **/
public class ATest {
    SimpleDateFormat sdf = new SimpleDateFormat("M月d日");
    @Test
    public void testTEXP1(){
        AuthorFBack.AuthorCondition authorCondition = buildCondition();
        authorCondition.setDeadLine(buildDeadLine(System.currentTimeMillis()));
        authorCondition.setPublishDay("1");
        System.out.println(AuthorFBack.TEXP1("","","",authorCondition));
    }
    @Test
    public void testTEXP2() {
        AuthorFBack.AuthorCondition authorCondition = buildCondition();
        authorCondition.setDeadLine(buildDeadLine(System.currentTimeMillis()));
        authorCondition.setPublishDay("1");
        authorCondition.setLike("16");
        System.out.println(AuthorFBack.TEXP2("", "", "", authorCondition));
    }
//    AuthorCondition condition = new AuthorCondition();
//        if (Objects.nonNull(view)){
//        condition.setView(view);
//    } else {
//        condition.setView(strategy.getReadFeedNum());
//    }
//        condition.setBackVisited(strategy.getImpDetailNum());
//        condition.setExposure(strategy.getReadFeedNum());
//        condition.setLike(strategy.getEngagementNum());
//        condition.setHobbyNum(strategy.getSimilarReadFeedNum());
//        condition.setHobbyRatio(strategy.getSimilarImpRatio());
//        condition.setCategory(strategy.getTransformTaxonomy1());
//        condition.setAgeRatio(condition.ageRatio(strategy.getSimilarAgeNum(),strategy.getReadFeedNum()));
     @Test
    public void testT2() {
        AuthorFBack.AuthorCondition authorCondition = buildCondition();
        authorCondition.setBackVisited("0");
        authorCondition.setView("100");
        authorCondition.setExposure("0");
        authorCondition.setLike("0");
        authorCondition.setHobbyNum("16");
        authorCondition.setHobbyRatio("66");
        authorCondition.setCategory("游戏");
        authorCondition.setAgeRatio(ageRatio(100,800));
        //authorCondition.setDeadLine(buildDeadLine(System.currentTimeMillis()));
//        authorCondition.setPublishDay("1");
//        authorCondition.setView("20");
//        authorCondition.setBackVisited("10");
//        authorCondition.setHobbyRatio("20");
//        authorCondition.setExposure("20");
//        authorCondition.setLike("16");
//        authorCondition.setHobbyNum("20");
//        authorCondition.setCategory("绘画");
//        authorCondition.setAgeRatio("30");
        System.out.println(AuthorFBack.T2("", "", "", authorCondition));
    }

    @Test
    public void testT5EXP1() {
        AuthorFBack.AuthorCondition authorCondition = buildCondition();
        //authorCondition.setDeadLine(buildDeadLine(System.currentTimeMillis()));
        authorCondition.setPublishDay("1");
        authorCondition.setView("20");
        authorCondition.setViewRatio("30");
        System.out.println(AuthorFBack.T5EXP1("", "", "", authorCondition));
    }
    @Test
    public void testT5EXP2() {
        AuthorFBack.AuthorCondition authorCondition = buildCondition();
        //authorCondition.setDeadLine(buildDeadLine(System.currentTimeMillis()));
        authorCondition.setPublishDay("1");
        authorCondition.setExposure("30");
        authorCondition.setExposureRatio("30");
        authorCondition.setLike("16");
        System.out.println(AuthorFBack.T5EXP2("", "", "", authorCondition));
    }
    AuthorFBack.AuthorCondition buildCondition(){
        AuthorFBack.AuthorCondition condition = new AuthorFBack.AuthorCondition();
        return condition;
    }

    public String buildDeadLine(long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(time));
        calendar.add(Calendar.DAY_OF_MONTH,7);
        long nextTime = calendar.getTimeInMillis();
        return sdf.format(nextTime);
    }
    @Test
    public void testCal(){
        System.out.println(getDayByTime(new Date().getTime()));
    }
    public static long getDayByTime(long time){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(time));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    private String ageRatio(long viewNum, long similarAgeNum){
        double ratio = (double) viewNum / similarAgeNum * 100;
        String ratioStr = String.valueOf(ratio);
        int len = ratioStr.length();
        if (len > 4){
            ratioStr = ratioStr.substring(0,4);
        }
        return ratio >= 100 ? String.valueOf(99.9) : ratioStr;
    }
}
