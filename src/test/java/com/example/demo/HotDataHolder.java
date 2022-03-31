package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @Author: shiguang
 * @Date: 2022/3/28
 * @Description:
 **/
@Data
public class HotDataHolder {

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
        if (canal != NoteIdeaCanal.operator.getVal() && canal != NoteIdeaCanal.competitor.getVal()){
            return "";
        }
        HotData data = JSONObject.parseObject(hotData, HotData.class);
        JSONObject obj = data.parseByCanal();
        if (Objects.nonNull(obj)){
            StringBuilder sb = new StringBuilder();
            obj.entrySet().stream().forEach(entry->sb.append(entry.getKey()).append(":").append(entry.getValue()).append("\r\n"));
            return sb.toString();
        }
        return "";
    }

    public static HotDataHolder build(int canal, String hotData,String keywordRel) {
        HotDataHolder holder = new HotDataHolder();
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
        if (canal != NoteIdeaCanal.operator.getVal() && canal != NoteIdeaCanal.competitor.getVal()){
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
        String related_name;
        /**
         * 渠道
         */
        int canal;
        /**
         * 近七天相关笔记阅读数，poka热点话题
         */
        int l1_7d_new_p7_total_note_vv_inc;
        /**
         * 近七天新增相关笔记数，poka热点话题
         */
        int l1_7d_total_note_num_inc;
        /**
         * 昨天搜索人数，poka搜索热点
         */
        int max_uv;
        /**
         * 昨日发布数，ugc activate｜ugc up
         */
        int new_post_num;
        /**
         * 三日增长率，ugc up
         */
        String soar_rate;
        /**
         * 话题id ugc
         */
        String page_id;

        /**
         * 列表数据使用
         *
         * @return
         */
        public JSONObject parseByCanal() {
            JSONObject obj = new JSONObject();
            //obj.put("关键词",relatedName);
            if (canal == NoteIdeaCanal.poka_search.val) {
                obj.put("昨天搜索人数", max_uv);
                return obj;
            } else if (canal == NoteIdeaCanal.poka_page.val) {
                obj.put("近七天相关笔记阅读数", l1_7d_new_p7_total_note_vv_inc);
                obj.put("近七天新增相关笔记数", l1_7d_total_note_num_inc);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_activate.val) {
                obj.put("昨日发布数", new_post_num);
                obj.put("pageId", page_id);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_up.val) {
                obj.put("昨日发布数", new_post_num);
                obj.put("三日增长率", soar_rate);
                obj.put("pageId", page_id);
                return obj;
            }
            return null;
        }

        public JSONObject buildRow(){
            JSONObject obj = new JSONObject();
            obj.put("relatedName", related_name);
            if (canal == NoteIdeaCanal.poka_search.val) {
                obj.put("maxUv", max_uv);
                return obj;
            } else if (canal == NoteIdeaCanal.poka_page.val) {
                obj.put("lastSDayView", l1_7d_new_p7_total_note_vv_inc);
                obj.put("lastSDayPost", l1_7d_total_note_num_inc);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_activate.val) {
                obj.put("publishLastDay", new_post_num);
                obj.put("pageId", page_id);
                return obj;
            } else if (canal == NoteIdeaCanal.ugc_up.val) {
                obj.put("publishLastDay", new_post_num);
                obj.put("soarRate", soar_rate);
                obj.put("pageId", page_id);
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
