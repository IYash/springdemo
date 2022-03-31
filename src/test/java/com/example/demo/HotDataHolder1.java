package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2022/3/28
 * @Description:
 **/
@Data
public class HotDataHolder1 {

    /**
     * 渠道信息
     */
    private int canal;
    /**
     * 数据描述
     */
    private String hotData;
    /**
     * 关联关键词
     */
    private String keywordRel;

    /**
     * 解析数据,列表展示
     *
     * @return
     */
    public String descHotData() {
        if (canal == NoteIdeaCanal.operator.getVal()){
            return "";
        }
        HotData data = JSONObject.parseObject(hotData, HotData.class);
        JSONObject obj = data.parseByCanal(canal);
        if (Objects.nonNull(obj)){
            StringBuilder sb = new StringBuilder();
            obj.entrySet().stream().forEach(entry->sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\r\n"));
            return sb.toString();
        }
        return "";
    }
    public static HotData parseHotData(String hotData){
        if (StringUtils.isEmpty(hotData)){
            return new HotData();
        }
        return JSONObject.parseObject(hotData,HotData.class);
    }
    public static HotDataHolder1 build(int canal, String hotData, String keywordRel) {
        HotDataHolder1 holder = new HotDataHolder1();
        holder.setCanal(canal);
        holder.setHotData(hotData);
        holder.setKeywordRel(keywordRel);
        return holder;
    }

    /**
     * 灵感详情页关联关键词
     * @return
     */
    public List<JSONObject> parseKeywordRel(){
        List<JSONObject> result = Lists.newArrayList();
        if (canal == NoteIdeaCanal.operator.getVal() && canal == NoteIdeaCanal.competitor.getVal()){
            return result;
        }
        result.add(buildTableHead());
        List<HotData> datas = JSONObject.parseArray(keywordRel, HotData.class);
        if (CollectionUtils.isEmpty(datas)){
            return result;
        }
        datas.stream().forEach(data->result.add(data.buildRow()));
        return result;
    }
    @Data
    public static class HotData {
        /**
         * 关联词
         */
        String relatedName;
        /**
         * 渠道
         */
        int canal;
        /**
         * 近七天相关笔记阅读数，poka热点话题
         */
        int l17dNewP7TotalNoteVvInc;
        /**
         * 近七天新增相关笔记数，poka热点话题
         */
        int l17dTotalNoteNumInc;
        /**
         * 昨天搜索人数，poka搜索热点
         */
        int maxUv;
        /**
         * 昨日增长率，poka搜索热点
         */
        String oneDayGrowthRate;
        /**
         * 昨日发布数，ugc activate｜ugc up
         */
        int newPostNum;
        /**
         * 三日增长率，ugc up
         */
        String soarRate;
        /**
         * 话题id ugc
         */
        String pageId;

        /**
         * 列表数据使用
         *
         * @return
         */
        public JSONObject parseByCanal(int canal) {
            JSONObject obj = new JSONObject();
            //obj.put("关键词",relatedName);
            if (canal == NoteIdeaCanal.poka_search.val) {
                obj.put("昨天搜索人数", maxUv);
                return obj;
            } else if (canal == NoteIdeaCanal.poka_page.val) {
                obj.put("近七天相关笔记阅读数", l17dNewP7TotalNoteVvInc);
                obj.put("近七天新增相关笔记数", l17dTotalNoteNumInc);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_activate.val) {
                obj.put("昨日发布数", newPostNum);
                obj.put("pageId", pageId);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_up.val) {
                obj.put("昨日发布数", newPostNum);
                obj.put("三日增长率", soarRate);
                obj.put("pageId", pageId);
                return obj;
            }
            return null;
        }

        public JSONObject buildRow(){
            JSONObject obj = new JSONObject();
            obj.put("relatedName", relatedName);
            if (canal == NoteIdeaCanal.poka_search.val) {
                obj.put("maxUv", maxUv);
                return obj;
            } else if (canal == NoteIdeaCanal.poka_page.val) {
                obj.put("lastSDayView", l17dNewP7TotalNoteVvInc);
                obj.put("lastSDayPost", l17dTotalNoteNumInc);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_activate.val) {
                obj.put("publishLastDay", newPostNum);
                obj.put("pageId", pageId);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_up.val) {
                obj.put("publishLastDay", newPostNum);
                obj.put("soarRate", soarRate);
                obj.put("pageId", pageId);
                return obj;
            }
            return null;
        }
    }

    /**
     * 详情数据使用
     */
    public JSONObject buildTableHead() {
        JSONObject obj = new JSONObject();
        obj.put("relatedName", "关键词");
        if (canal == NoteIdeaCanal.poka_search.val) {
            obj.put("maxUv", "昨天搜索人数");
            return obj;
        } else if (canal == NoteIdeaCanal.poka_page.val) {
            obj.put("lastSDayView", "近七天相关笔记阅读数");
            obj.put("lastSDayPost", "近七天新增相关笔记数");
            return obj;
        } else if (canal == NoteIdeaCanal.ugc_activate.val) {
            obj.put("publishLastDay", "昨日发布数");
            obj.put("pageId", "pageId");
            return obj;
        } else if (canal == NoteIdeaCanal.ugc_up.val) {
            obj.put("publishLastDay", "昨日发布数");
            obj.put("soarRate", "三日增长率");
            obj.put("pageId", "pageId");
            return obj;
        }
        return null;
    }


}
