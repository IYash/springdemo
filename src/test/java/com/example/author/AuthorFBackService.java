package com.example.author;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * @Author: shiguang
 * @Date: 2022/3/9
 * @Description: https://xiaohongshu.quip.com/xW6uAlEX5qwc/PRD7 新作者承接服务对象
 **/
@Service
@Slf4j
public class AuthorFBackService {
    @Resource
    AuthorFBackHolder authorFBackHolder = new AuthorFBackHolder();
//    @Resource
//    ExperimentTools experimentTools;
//    @Resource
//    ViewService viewService;
//    @Resource
//    private NoticeMessageUtil noticeMessageUtil;
//    @Resource
//    UserManager userManager;
    @Value("${author.feed.back.sender:}")
    String senderId;
    @Value("${author.feed.back.exp:}")
    String flagName;
    @Resource
    AuthorConditionBuilder authorConditionBuilder = new AuthorConditionBuilder();
    private static final String AWARD_KEY = "new_author_finish_task";


    /**
     * 笔记审核通过时实时处理逻辑
     *
     * @param uid
     */
    public void resolveNoteVerifiedAuthor(String uid) {
        SystemMessageDto dto = null;
        int exp = 0;
        if (authorFBackHolder.isNewAuthor(uid)) {
            authorFBackHolder.newAuthor(uid);
            //站内信通知
            exp = getUserExpFlag(uid, 0);
            if (exp == 1) {
                dto = AuthorFBack.TEXP1(uid, senderId, authorConditionBuilder.buildCondition(uid));
            } else if (exp == 2) {
                dto = AuthorFBack.TEXP2(uid, senderId, authorConditionBuilder.buildCondition(uid));
            }
            return ;
        }
        if (authorFBackHolder.needFinishAuthorTask(uid)) {
            authorFBackHolder.finishAuthorTask(uid);
            //站内信通知
            exp = getUserExpFlag(uid, 0);
            if (exp == 1) {
                dto = AuthorFBack.T16EXP1(uid, senderId);
                sendCoupon(uid, 200, AWARD_KEY, dto);
            } else if (exp == 2) {
                dto = AuthorFBack.T16EXP2(uid, senderId);
                sendCoupon(uid, 500, AWARD_KEY, dto);
            }
            return;
        }
    }


    /**
     * 定时任务通知 T+2,数据由数仓提供
     *
     * @param uid
     */
    public void enhanceAuthorTaskNotice(String uid) {
        commonTask(uid,()->AuthorFBack.T2(uid, senderId, authorConditionBuilder.buildCondition(uid)));
    }

    /**
     * 定时任务通知 T+5,数据由数仓提供
     *
     * @param uid
     */
    public void finishAuthorTaskNotice(String uid) {
        commonTask(uid,()->{
            int exp = getUserExpFlag(uid, 0);
            SystemMessageDto dto = null;
            if (exp == 1) {
                dto = AuthorFBack.T5EXP1(uid, senderId, authorConditionBuilder.buildCondition(uid));
            } else if (exp == 2) {
                dto = AuthorFBack.T5EXP2(uid, senderId, authorConditionBuilder.buildCondition(uid));
            }
            return dto;
        });
    }

    /**
     * 定时任务通知 T+3,数据由数仓提供
     *
     * @param uid
     */
    public void newAuthorNoteIdeaNotice(String uid) {
        commonTask(uid,()->AuthorFBack.T3(uid, senderId));
    }


    public void commonTask(String uid, Callable<SystemMessageDto> task){
        if (authorFBackHolder.needFinishAuthorTask(uid)) {
//            if (authorFBackHolder.isUserDayBlock(uid)) {
//                return;
//            }
            System.out.println("commonTask...");
            try {
                SystemMessageDto dto = task.call();
                if (Objects.nonNull(dto)){
//                    noticeMessageUtil.systemMessageNoticeV4(dto);
//                    authorFBackHolder.userDayBlock(uid);
                }
            } catch (Exception e) {
                log.warn("task.call error:{}", JSONObject.toJSONString(uid));
            }
        }
    }

    /**
     * @param uid
     * @param award
     * @param dto
     * @param key
     * @throws Exception
     */
    public void sendCoupon(String uid, int award, String key, SystemMessageDto dto) {
//        long userIdMysql = userManager.parseUserId(uid);
//        viewService.receiveOfficialReward(userIdMysql, award, key);
//        noticeMessageUtil.systemMessageNoticeV4(dto);
//        log.info("sendMessage userId:{}, title:{} ", uid, dto.getTitle());
    }

    /**
     * 获取用户的实验标记
     *
     * @param uid
     * @param defVal
     * @return
     */
    private int getUserExpFlag(String uid, int defVal) {
        return 1;
    }
}
