package com.example.demo.js;

import lombok.Data;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/4/21
 * @Description: 用于封装抵制弹幕判断规则的对象
 **/
@Data
public class RuleInfo {
    private int limited;
    private int size;
    private int sizeType;//0:超过，1:少于
    private List<ScriptParam> scriptParamList;
    private List<String> forbiddenUserIds;//禁止弹幕用户
    private List<GoodFilters> goodsFilters;//商品需要过滤弹幕

    @Data
    public static class ScriptParam {
        private String scriptName;
        private String scriptContent;
    }
    @Data
    public static class GoodFilters{
        private List<String> filters;
    }

}
