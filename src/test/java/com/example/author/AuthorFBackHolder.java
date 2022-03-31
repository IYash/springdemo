package com.example.author;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: shiguang
 * @Date: 2022/3/8
 * @Description: 新作者7日承接状态管理对象
 * 需求文档：https://xiaohongshu.quip.com/xW6uAlEX5qwc/PRD7
 **/
@Component
@Slf4j
public class AuthorFBackHolder {

//    @Resource
//    RedDService redDService;
    @Resource
    AuthorCacheResolver authorCacheResolver = new AuthorCacheResolver();

    /**
     * 判断用户是否为新作者,当审核通过时使用该接口
     *
     * @param uid
     * @return
     */
    public boolean isNewAuthor(String uid) {
        return StringUtils.isEmpty(authorCacheResolver.queryAuthorFeature(uid, AuthorKeys.FIRST_TIME));
    }

    /**
     * 判断用户是否完成新作者任务,目标在于承接行为
     *
     * @param uid
     * @return true, 表示用户未完成承接行为，可以发信通知
     */
    public boolean needFinishAuthorTask(String uid) {
        return isDaySuit(uid) && isFinish(uid);
    }

    /**
     * 设置为新作者
     *
     * @param uid
     */
    public void newAuthor(String uid) {
        authorCacheResolver.setAuthorFeature(uid, AuthorKeys.FIRST_TIME, String.valueOf(System.currentTimeMillis()));
    }

    /**
     * 承接行为处理
     *
     * @param uid
     */
    public void finishAuthorTask(String uid) {
        authorCacheResolver.setAuthorFeature(uid, AuthorKeys.FINISH, "1");
    }

    /**
     * 日期是否匹配
     *
     * @param uid
     * @return
     */
    public boolean isDaySuit(String uid) {
        String verifiedTime = authorCacheResolver.queryAuthorFeature(uid, AuthorKeys.FIRST_TIME);
        if (!StringUtils.isEmpty(verifiedTime)) {
            long firstTime = Long.valueOf(verifiedTime);
            long curTime = System.currentTimeMillis();
            return firstTime < curTime;
        }
        return false;
    }

    /**
     * 缓存判断是否
     *
     * @param uid
     * @return
     */
    public boolean isFinish(String uid) {
        return StringUtils.isEmpty(authorCacheResolver.queryAuthorFeature(uid, AuthorKeys.FINISH));
    }
}
