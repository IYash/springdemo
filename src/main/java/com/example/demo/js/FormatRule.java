package com.example.demo.js;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/4/21
 * @Description: 内容格式化的一些问题，如只包含标点符号，末尾为逗号，表情等
 **/
@Component
@Slf4j
public class FormatRule implements LowQualityRule{
    ScriptEngineManager engineManager = new ScriptEngineManager();
    private static final String JAVA_SCRIPT = "javascript";
    private RuleInfo ruleInfo;
    @Override
    public boolean match(LowQualityBulletStrategy.LowQualityParam lowQualityParam) {
        return needCompare(lowQualityParam);
    }

    @Override
    public Jedis cacheService() {
        return null;
    }

    @Override
    public boolean needCompare(LowQualityBulletStrategy.LowQualityParam lowQualityParam) {
        log.info("GoodsRule.needCompare work on {}",lowQualityParam.getBulletScreenId());
        //执行js脚本,脚本当成规则来配置
        ScriptEngine engine = engineManager.getEngineByName(JAVA_SCRIPT);
        List<RuleInfo.ScriptParam> scriptParamList = ruleInfo.getScriptParamList();
        if(!CollectionUtils.isEmpty(scriptParamList)){
            String content ="";
            for(RuleInfo.ScriptParam scriptParam:scriptParamList){
                String scriptContent = scriptParam.getScriptContent();
                String scriptName = scriptParam.getScriptName();
                try {
                    engine.eval(scriptContent);
                    Invocable invocable = (Invocable) engine;
                    Object res = invocable.invokeFunction(scriptName,content);
                    if("true".equalsIgnoreCase(res.toString())) return true;
                } catch (Exception e) {
                    log.error("PostMilSecAroundRule.needCompare error:{}",e.getMessage());
                }
            }
        }
        return false;
    }
    //@Value("${formatRule:{\"limited\":\"5000\"}}")
    public void ruleParam(RuleInfo ruleInfo) {
        this.ruleInfo = ruleInfo;
    }
}
