package com.example.demo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2022/3/8
 * @Description: 新作者7日承接 反馈策略
 **/

public class AuthorFBack {

    public static final String source = "new_author_feedback";
    public static final String tag = "new_author_feedback";
    public static final int ONE_DAY = 1;
    public static final int TWO_DAY = 2;
    public static final String DAY_PRO= "%s/2天";
    public static final String DAY_INTERACTION_PRO = "%s/2天，%s/10赞";

    /**
     * T日通知规范,所有新作者 deadLine T+6  1/2天
     * @return
     */
    public static String TEXP1(String token,String userId,String senderId,AuthorCondition condition){
        String title = "恭喜你，成功在小红书发布了第一篇笔记！";
        String content = String.format("学习创作手册，并在7天内累计2天发布公开笔记（截至%s），即可获得新人专享200流量券哦～当前进度："+DAY_PRO,condition.getDeadLine(),condition.getPublishDay());
        String buttonDesc = "去查看";
        String buttonLink = "";
        return title + "---"+ content;
    }
    /**
     * T日通知规范,所有新作者 deadLine T+6  1/2天，xx/10赞
     * @return
     */
    public static String TEXP2(String token,String userId,String senderId,AuthorCondition condition){
        String title = "恭喜你，成功在小红书发布了第一篇笔记！";
        StringBuilder sb = new StringBuilder();
        sb.append("学习创作手册，并在7天内累计2天发布公开笔记（截至%s），累计获得10赞，即可获得新人专享500流量券～当前进度："+DAY_INTERACTION_PRO);
        String content = build(sb, Lists.newArrayList(condition.getDeadLine(),condition.getPublishDay(),condition.getLike()));
        String buttonDesc = "去查看";
        String buttonLink = "";
        return title + "---"+ content;
    }

    /**
     * T+2日通知规范,所有新作者,真实浏览数>=10,定时任务
     * @return
     */
    public static String T2(String token,String userId,String senderId,AuthorCondition condition) {
        String view = condition.getView();
        if (Integer.valueOf(view) < DiffCondition.REAL_VIEW_LOW.getDiff()) {
            return null;
        }
        String backVisited = condition.getBackVisited();
        String hobbyRation = condition.getHobbyRatio();
        String title = String.format("你的笔记已获得%s次浏览", condition.getExposure());
        String content = "";
        StringBuilder sb = new StringBuilder();
        if (Double.valueOf(hobbyRation) < DiffCondition.HOBBY_RATIO_LOW.getDiff()) {
            if (Double.valueOf(backVisited) < DiffCondition.BACK_VISITED.getDiff()) {
                sb.append("你的笔记吸引了%s次点赞互动，他们都很期待你的再次分享，快来参与他们都爱看的话题吧 >>");
                content = build(sb, Lists.newArrayList(String.valueOf(condition.getLike())));
            } else {
                sb.append("你的笔记吸引了%s次点赞互动，且有%s人回访了你的主页，他们都很期待你的再次分享，快来参与他们都爱看的话题吧 >>");
                content = build(sb, Lists.newArrayList(String.valueOf(condition.getLike()), String.valueOf(backVisited)));
            }
        } else {
            String hobbyNum = condition.getHobbyNum();
            String category = condition.getCategory();
            List<String> params = Lists.newArrayList();
            if (Integer.valueOf(hobbyNum) < DiffCondition.HOBBY_NUM.getDiff()) {
                sb.append("浏览者中有%s%%的%s爱好者，");
                params.add(hobbyRation);
                params.add(category);
            } else {
                if (Double.valueOf(hobbyRation) < DiffCondition.HOBBY_RATIO_MID.getDiff()) {
                    sb.append("其中有%s位%s爱好者，");
                    params.add(String.valueOf(hobbyNum));
                    params.add(category);
                } else {
                    sb.append("其中有%s%%的%s爱好者，");
                    params.add(hobbyRation);
                    params.add(category);
                }
            }
            String ageRatio = condition.getAgeRatio();
            if (Double.valueOf(ageRatio) >= DiffCondition.AGE_RATIO.getDiff()) {
                sb.append("还有%s%%的同龄小红薯[哇R]"); //todo
                params.add(ageRatio);
            }
            sb.append("他们都很期待你的再次分享，快来参与他们都在聊的话题吧 >>");
            content = build(sb, params);
        }
        String buttonDesc = "去查看";
        String buttonLink = "https://www.xiaohongshu.com/picasso_pages/author-center/activity-list?fullscreen=true";

        return title + "---"+ content;
    }


    /**
     * T+5日通知规范,尚未产生留存,定时任务
     * @return
     */
    public static String T5EXP1(String token,String userId,String senderId,AuthorCondition condition){
        String view = condition.getView();
        String viewRatio = condition.getViewRatio();
        String title = "Hi，你马上要错过新人专属流量券啦";
        if (Integer.valueOf(view) >= DiffCondition.REAL_VIEW_MID.getDiff() && Integer.valueOf(viewRatio) > DiffCondition.REAL_VIEW_RATIO.getDiff()){
            title = String.format("你的笔记热度超过了%s%的创作新人",viewRatio);
        }
        String content = String.format("你的笔记已获得%s次浏览，在2天内再发布1条篇公开笔记，即可领取新人专享200流量券啦！加油哦～",view);
        String buttonDesc = "去发布";
        String buttonLink = "";
        return title + "---"+ content;
    }


    /**
     * T+5日通知规范,尚未产生留存,定时任务
     * @return
     */
    public static String T5EXP2(String token,String userId,String senderId,AuthorCondition condition){
        String exposure = condition.getExposure();
        String exposureRatio = condition.getExposureRatio();
        String title = "Hi，你马上要错过新人专属流量券啦";
        if (Integer.valueOf(exposure) >= DiffCondition.REAL_EXPOSURE_MID.getDiff() && Integer.valueOf(exposureRatio) > DiffCondition.REAL_EXPOSURE_RATIO.getDiff()){
            title = String.format("你的笔记热度超过了%s%%的创作新人",exposureRatio);
        }
        String publishDay = condition.getPublishDay();
        String like = condition.getLike();
        String content = "";
        if (Integer.valueOf(publishDay) == ONE_DAY ) {
            if (Integer.valueOf(like) < DiffCondition.INTERACTION.getDiff()) {
                content = String.format("2天内再发布1篇公开笔记，累计获得10赞，就可领取新人专享500流量券啦！当前进度："+DAY_INTERACTION_PRO,publishDay,condition.getLike());
            } else {
                content = String.format("你的笔记已累计获得%s赞，只要在2天内再发布1篇公开笔记，就可领取新人专享500流量券啦！当前进度："+DAY_INTERACTION_PRO,condition.getLike(),publishDay,condition.getLike());
            }
        } else if (Integer.valueOf(publishDay) == TWO_DAY){
            if (Integer.valueOf(like) < DiffCondition.INTERACTION.getDiff()){
                content = String.format("你已累计%s天发布公开笔记，只要在2天内累计获得10赞，就可领取新人专享500流量券啦！当前进度："+DAY_INTERACTION_PRO,publishDay,publishDay,condition.getLike());
            } else {
                // 其他场景无需触达
                return null;
            }
        }
        String buttonDesc = "去发布";
        String buttonLink = "";
        return title + "---"+ content;
    }

    /**
     * 构建参数
     * @param sb
     * @param params
     * @return
     */
    public static String build(StringBuilder sb, List<String> params) {
        String content = sb.toString();
        String[]  arr = params.toArray(new String[params.size()]);
        return String.format(content,arr);
    }

    @Data
    public static class AuthorCondition{
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
    }

    /**
     * 用于描述各种奇怪的条件记录
     * diff表示base的条件值
     */
    @Getter
    enum DiffCondition{
        BACK_VISITED(5), //访客
        HOBBY_RATIO_LOW(10), //同好占比
        HOBBY_RATIO_MID(30),
        AGE_RATIO(10),//同龄占比
        HOBBY_NUM(10),  //同好人数
        INTERACTION(10),//互动 = 点赞+评论pv+收藏

        REAL_VIEW_LOW(10),
        REAL_VIEW_MID(50),
        REAL_VIEW_RATIO(60),
        REAL_EXPOSURE_MID(50),
        REAL_EXPOSURE_RATIO(60),
        ;
        int diff;
        DiffCondition(int diff){
            this.diff= diff;
        }
    }
}
