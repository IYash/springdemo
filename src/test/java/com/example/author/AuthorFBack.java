package com.example.author;

import com.google.common.collect.Lists;
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
    public static final String DAY_PRO = "%s/2天";
    public static final String DAY_INTERACTION_PRO = "%s/2天，%s/10赞";

    /**
     * T日通知规范,所有新作者 deadLine T+6  1/2天
     *
     * @return
     */
    public static SystemMessageDto TEXP1(String userId, String senderId, AuthorCondition condition) {
        String title = "恭喜你，成功在小红书发布了第一篇笔记！";
        String content = String.format("学习创作手册，并在7天内累计2天发布公开笔记（截至%s），即可获得新人专享200流量券哦～当前进度：" + DAY_PRO, condition.getDeadLine(), condition.getPublishDay());
        String buttonDesc = "去查看";
        String token = "";
        String buttonLink = "";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T日通知规范,所有新作者 deadLine T+6  1/2天，xx/10赞
     *
     * @return
     */
    public static SystemMessageDto TEXP2(String userId, String senderId, AuthorCondition condition) {
        String title = "恭喜你，成功在小红书发布了第一篇笔记！";
        StringBuilder sb = new StringBuilder();
        sb.append("学习创作手册，并在7天内累计2天发布公开笔记（截至%s），累计获得10赞，即可获得新人专享500流量券～当前进度：" + DAY_INTERACTION_PRO);
        String content = build(sb, Lists.newArrayList(condition.getDeadLine(), condition.getPublishDay(), condition.getLike()));
        String buttonDesc = "去查看";
        String token = "";
        String buttonLink = "";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T+1-6日通知规范,完成任务的作者
     *
     * @return
     */
    public static SystemMessageDto T16EXP1(String userId, String senderId) {
        String title = "恭喜你，获得新人专享流量券！";
        String content = "你已累计2天发布公开笔记，获得200流量券，快去推广你的笔记吧！";
        String buttonDesc = "去使用";
        String token = "";
        String buttonLink = "https://www.xiaohongshu.com/picasso_pages/author-center/official-boost?fullscreen=true";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T+1-6日通知规范,完成任务的作者
     *
     * @return
     */
    public static SystemMessageDto T16EXP2(String userId, String senderId) {
        String title = "恭喜你，获得新人专享流量券！";
        String content = "你已累计2天发布公开笔记并达10赞，获得500流量券，快去推广你的笔记吧！";
        String buttonDesc = "去使用";
        String token = "";
        String buttonLink = "https://www.xiaohongshu.com/picasso_pages/author-center/official-boost?fullscreen=true";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T+2日通知规范,所有新作者,真实浏览数>=10,定时任务
     *
     * @return
     */
    public static SystemMessageDto T2(String userId, String senderId, AuthorCondition condition) {
        String view = condition.getView();
        if (Integer.valueOf(view) < DiffCondition.REAL_VIEW_LOW.getDiff()) {
            return null;
        }
        String backVisited = condition.getBackVisited();
        String hobbyRation = condition.getHobbyRatio();
        String title = String.format("你的笔记已获得%s次浏览", condition.getExposure());
        String content = "";
        StringBuilder sb = new StringBuilder();
        if (Integer.valueOf(hobbyRation) < DiffCondition.HOBBY_RATIO_LOW.getDiff()) {
            if (Integer.valueOf(backVisited) < DiffCondition.BACK_VISITED.getDiff()) {
                sb.append("你的笔记吸引了%s次点赞互动，他们都很期待你的再次分享，快来参与他们都爱看的话题吧 >>");
                content = build(sb, Lists.newArrayList(condition.getLike()));
            } else {
                sb.append("你的笔记吸引了%s次点赞互动，且有%s人回访了你的主页，他们都很期待你的再次分享，快来参与他们都爱看的话题吧 >>");
                content = build(sb, Lists.newArrayList(condition.getLike(), condition.getBackVisited()));
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
                if (Integer.valueOf(hobbyRation) < DiffCondition.HOBBY_RATIO_MID.getDiff()) {
                    sb.append("其中有%s位%s爱好者，");
                    params.add(hobbyNum);
                    params.add(category);
                } else {
                    sb.append("其中有%s%%的游戏爱好者，");
                    params.add(hobbyRation);
                    params.add(category);
                }
            }
            String ageRatio = condition.getAgeRatio();
            if (Integer.valueOf(ageRatio) >= DiffCondition.AGE_RATIO.getDiff()) {
                sb.append("还有%s%%的同龄小红薯[小红薯表情包]"); //todo
                params.add(ageRatio);
            }
            sb.append("他们都很期待你的再次分享，快来参与他们都在聊的话题吧 >>");
            content = build(sb, params);
        }
        String buttonDesc = "去查看";
        String token = "";
        String buttonLink = "https://www.xiaohongshu.com/picasso_pages/author-center/activity-list?fullscreen=true";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T+3日通知规范,提供创作灵感,所有新作者,定时任务
     *
     * @return
     */
    public static SystemMessageDto T3(String userId, String senderId) {
        String title = "Hi，小助手精选本周「笔记灵感」";
        String content = "社区热门活动和适合你参与的话题，都在这里 >>";
        String buttonDesc = "去查看";
        String token = "";
        String buttonLink = "https://www.xiaohongshu.com/picasso_pages/author-center/activity-list?fullscreen=true";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * T+5日通知规范,尚未产生留存,定时任务
     *
     * @return
     */
    public static SystemMessageDto T5EXP1(String userId, String senderId, AuthorCondition condition) {
        String view = condition.getView();
        String viewRatio = condition.getViewRatio();
        String title = "Hi，你马上要错过新人专属流量券啦";
        if (Integer.valueOf(view) >= DiffCondition.REAL_VIEW_MID.getDiff() && Integer.valueOf(viewRatio) > DiffCondition.REAL_VIEW_RATIO.getDiff()) {
            title = String.format("你的笔记热度超过了%s%%的创作新人", viewRatio);
        }
        String content = String.format("你的笔记已获得%s次浏览，在2天内再发布1条篇公开笔记，即可领取新人专享200流量券啦！加油哦～", view);
        String buttonDesc = "去发布";
        String token = "";
        String buttonLink = "";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }


    /**
     * T+5日通知规范,尚未产生留存,定时任务
     *
     * @return
     */
    public static SystemMessageDto T5EXP2(String userId, String senderId, AuthorCondition condition) {
        String exposure = condition.getExposure();
        String exposureRatio = condition.getExposureRatio();
        String title = "Hi，你马上要错过新人专属流量券啦";
        if (Integer.valueOf(exposure) >= DiffCondition.REAL_EXPOSURE_MID.getDiff() && Integer.valueOf(exposureRatio) > DiffCondition.REAL_EXPOSURE_RATIO.getDiff()) {
            title = String.format("你的笔记热度超过了%s%%的创作新人", exposureRatio);
        }
        String publishDay = condition.getPublishDay();
        String like = condition.getLike();
        String content = "";
        if (Integer.valueOf(publishDay) == ONE_DAY) {
            if (Integer.valueOf(like) < DiffCondition.INTERACTION.getDiff()) {
                content = String.format("2天内再发布1篇公开笔记，累计获得10赞，就可领取新人专享500流量券啦！当前进度：" + DAY_INTERACTION_PRO, publishDay, like);
            } else {
                content = String.format("你的笔记已累计获得%s赞，只要在2天内再发布1篇公开笔记，就可领取新人专享500流量券啦！当前进度：" + DAY_INTERACTION_PRO, like, publishDay, like);
            }
        } else if (Integer.valueOf(publishDay) == TWO_DAY) {
            if (Integer.valueOf(like) < DiffCondition.INTERACTION.getDiff()) {
                content = String.format("你已累计%s天发布公开笔记，只要在2天内累计获得10赞，就可领取新人专享500流量券啦！当前进度：" + DAY_INTERACTION_PRO, publishDay, publishDay, like);
            } else {
                // 其他场景无需触达
                return null;
            }
        }
        String buttonDesc = "去发布";
        String token = "";
        String buttonLink = "";
        System.out.println(title +"----->"+content);
        return SystemMessageDto.buildSystemDto(title, userId, senderId, content, source, "", tag, buttonDesc, buttonLink, token);
    }

    /**
     * 构建参数
     *
     * @param sb
     * @param params
     * @return
     */
    public static String build(StringBuilder sb, List<String> params) {
        String content = sb.toString();
        String[] arr = params.toArray(new String[params.size()]);
        return String.format(content, arr);
    }


    /**
     * 用于描述各种奇怪的条件记录
     * diff表示base的条件值
     */
    @Getter
    enum DiffCondition {
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

        DiffCondition(int diff) {
            this.diff = diff;
        }
    }


}
