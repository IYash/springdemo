package com.example.demo.js;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;

/**
 * @Author: shiguang
 * @Date: 2021/4/20
 * @Description: 需要支持规则排序
 **/
@Component
@Slf4j
public class LowQualityBulletStrategy {


    //@Resource
    private ExecutorService executorService;


    @Autowired
    private Map<String, LowQualityRule> lowQualityRuleMap;
    private List<LowQualityRule> sortRules = Lists.newArrayList();

    public void reAuditByStrategy(LowQualityParam lowQualityParam){

        //添加策略逻辑，数据库状态修改逻辑，缓存修改逻辑
        executorService.execute(()->{
            if(CollectionUtils.isEmpty(sortRules)) return;
            for(LowQualityRule lowQualityRule:sortRules){
                if(lowQualityRule.match(lowQualityParam)){
                    System.out.println(lowQualityParam.getBulletScreenId());
                    break;
                }
            }
        });
    }
    @Data
    public static class LowQualityParam{
        private Long noteId;
        private Long postMilSec;
        private Long bulletScreenId;
        private Long userId;
        public static LowQualityParam buildParam(long userId,long noteId,long postMilSec,long bulletScreenId){
            LowQualityParam param =  new LowQualityParam();
            param.setUserId(userId);
            param.setBulletScreenId(bulletScreenId);
            param.setNoteId(noteId);
            param.setPostMilSec(postMilSec);
            return param;
        }
    }
    @Data
    public static class SortInfo{
        private List<SortParam> sortParamList;
    }
    @Data
    public static class SortParam{
        private String ruleName;
        private int index;
    }
    //@Value("${sortInfo:{}}")
    public void sortRules(SortInfo sortInfo){
        //解析数据，构建对象，排序
        sortRules = Lists.newArrayList();
        if(Objects.isNull(sortInfo)) return;
        List<SortParam> sortParamList = sortInfo.getSortParamList();
        if(CollectionUtils.isEmpty(sortParamList)) return;
        Collections.sort(sortParamList,Comparator.comparing(SortParam::getIndex));
        sortParamList.stream().forEach(param->{
            LowQualityRule rule = lowQualityRuleMap.get(param.getRuleName());
            if(Objects.nonNull(rule)) {
                sortRules.add(rule);
            }
        });
    }

}
