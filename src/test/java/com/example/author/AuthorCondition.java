package com.example.author;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2022/3/9
 * @Description:
 **/
@Data
public class AuthorCondition {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
    String deadLine = "";//截止日期
    String like;//点赞数
    String backVisited;//回访人数
    String hobbyRatio;//同好占比占比
    String hobbyNum;//同好数量
    String ageNum;//同龄数量
    String ageRatio;//同龄占比
    String view;//浏览
    String viewRatio;//浏览占比
    String exposure;//曝光量
    String exposureRatio;//曝光量占比
    String publishDay;//发布天数
    String category;

    public static AuthorCondition buildCondition(Long time, Map<String, String> paramMap) {
        AuthorCondition condition = new AuthorCondition();
        if (!Objects.isNull(time)) {
            condition.setDeadLine(buildDeadLine(time));
        }
        condition.setAgeNum(paramMap.getOrDefault("ageNum", "0"));
        condition.setAgeRatio(paramMap.getOrDefault("ageRatio", "0"));
        condition.setCategory(paramMap.getOrDefault("category", ""));
        condition.setPublishDay(paramMap.getOrDefault("publishDay", "1"));
        condition.setExposure(paramMap.getOrDefault("exposure", "0"));
        condition.setExposureRatio(paramMap.getOrDefault("exposureRatio", "0"));
        condition.setBackVisited(paramMap.getOrDefault("backVisited", "0"));
        condition.setHobbyRatio(paramMap.getOrDefault("hobbyRatio", "0"));
        condition.setHobbyNum(paramMap.getOrDefault("hobbyNum", "0"));
        condition.setView(paramMap.getOrDefault("view", "0"));
        condition.setViewRatio(paramMap.getOrDefault("viewRatio", "0"));
        condition.setLike(paramMap.getOrDefault("like", "0"));

        return condition;
    }

    public static String buildDeadLine(long time) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date(time));
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        long nextTime = calendar.getTimeInMillis();
        return sdf.format(nextTime);
    }
}
