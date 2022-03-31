package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.NoteIdeaLibDao;
import com.example.demo.entity.NoteIdeaLibInfo;
import com.google.common.reflect.TypeToken;
import lombok.val;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.testng.collections.Lists;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2022/3/28
 * @Description:
 **/
public class HotDataTest {
    @Test
    public void test1(){
        int canal = 4;
        HotDataHolder.HotData data = new HotDataHolder.HotData();
        data.setRelated_name("开心");
        data.setCanal(canal);
        data.setL1_7d_new_p7_total_note_vv_inc(100);
        data.setMax_uv(50);
        data.setL1_7d_total_note_num_inc(200);
        data.setPage_id("12355");
        data.setNew_post_num(300);
        data.setSoar_rate("0.96");
        List<String> keywordRel = Lists.newArrayList();
        String source = JSONObject.toJSONString(data);
        System.out.println(source);
        keywordRel.add(source);
        HotDataHolder1.HotData data1 = JSONObject.parseObject(source,HotDataHolder1.HotData.class);
        System.out.println(JSONObject.toJSONString(data1));
        HotDataHolder1 holder1 = HotDataHolder1.build(canal,source,null);
        String result1 = holder1.descHotData();
        System.out.println(result1);
        System.out.println(JSONObject.toJSONString(keywordRel));
        HotDataHolder1 holder2 = HotDataHolder1.build(canal,null,JSONObject.toJSONString(keywordRel));
        List<JSONObject> result2 = holder2.parseKeywordRel();
        System.out.println(JSONObject.toJSONString(result2));
    }


    @Test
    public void test2(){
        int canal = 1;
        String hotData = "{\"max_uv\":774,\"one_day_growth_rate\":0.9599483204134367}";
        HotDataHolder1 holder1 = HotDataHolder1.build(canal,hotData,null);
        System.out.println(holder1.descHotData());
//        String keywordRel = "[{\"related_name\":\"bkpp\",\"canal\":2,\"l1_7d_new_p7_total_note_vv_inc\":0,\"l1_7d_total_note_num_inc\":0,\"max_uv\":0,\"new_post_num\":913,\"soar_rate\":0.0,\"page_id\":\"5f8a51eb884ea80001a3e611\"},{\"related_name\":\"bkpp\",\"canal\":1,\"l1_7d_new_p7_total_note_vv_inc\":0,\"l1_7d_total_note_num_inc\":0,\"max_uv\":0,\"new_post_num\":913,\"soar_rate\":0.0,\"page_id\":null}]";
//        System.out.println(JSONObject.parseArray(keywordRel,HotDataHolder1.HotData.class));
//        HotDataHolder1 holder2 = HotDataHolder1.build(canal,null,keywordRel);
//        System.out.println(holder2.parseKeywordRel());
    }

    @Test
    public void test3() {
        TypeToken<Map<Integer, List<NoteIdeaLibInfo>>> typeToken = new TypeToken<Map<Integer, List<NoteIdeaLibInfo>>>() {
        };
        String info = "{1:[{\"classic\":1,\"desc\":\"12321\",\"hot\":12,\"id\":201,\"labelId\":1,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[2,1],\"status\":0,\"title\":\"13232\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":1,\"desc\":\"乌拉\",\"hot\":34,\"id\":202,\"labelId\":1,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"乌拉那拉\",\"reason\":[1],\"status\":0,\"title\":\"乌拉乌拉\",\"updateAt\":1637925930,\"updateBy\":\"陈雪娇\"},{\"classic\":2,\"desc\":\"xx\",\"hot\":12,\"id\":203,\"labelId\":1,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"xx\",\"reason\":[1],\"status\":0,\"title\":\"xxx\",\"updateAt\":1637925930,\"updateBy\":\"钟离\"}],2:[{\"classic\":1,\"desc\":\"321321\",\"hot\":1,\"id\":204,\"labelId\":2,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"321\",\"reason\":[2],\"status\":0,\"title\":\"321321321\",\"updateAt\":1637925930,\"updateBy\":\"陈雪娇\"},{\"classic\":1,\"desc\":\"开心\",\"hot\":567,\"id\":205,\"labelId\":2,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[1],\"status\":0,\"title\":\"开心2\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":1,\"desc\":\"123\",\"hot\":312,\"id\":206,\"labelId\":2,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[1],\"status\":0,\"title\":\"金碧辉煌金碧辉煌金碧辉煌金碧辉煌金碧辉煌金碧辉煌\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":2,\"desc\":\"123232\",\"hot\":12,\"id\":207,\"labelId\":2,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"12312\",\"reason\":[1,2],\"status\":0,\"title\":\"321312312\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":2,\"desc\":\"77\",\"hot\":339,\"id\":208,\"labelId\":2,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"77\",\"reason\":[2],\"status\":0,\"title\":\"啊说\",\"updateAt\":1637925930,\"updateBy\":\"oher\"}],3:[{\"classic\":1,\"desc\":\"9999\",\"hot\":99,\"id\":209,\"labelId\":3,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"999\",\"updateAt\":1637925930,\"updateBy\":\"黄转转\"},{\"classic\":0,\"desc\":\"\",\"hot\":12321,\"id\":210,\"labelId\":3,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"1111\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":2,\"desc\":\"开心开心\",\"hot\":234,\"id\":211,\"labelId\":3,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[3],\"status\":0,\"title\":\"开开心心开开心心开开心心开开心心\",\"updateAt\":1637925930,\"updateBy\":\"陈雪娇\"}],4:[{\"classic\":0,\"desc\":\"\",\"hot\":0,\"id\":212,\"labelId\":4,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"出行方式\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"},{\"classic\":2,\"desc\":\"玩滑雪、晒装备、学技巧、就来小红书，分享关于滑雪的一切\",\"hot\":568,\"id\":250,\"labelId\":4,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[1],\"status\":0,\"title\":\"小红书冰雪季2\",\"updateAt\":1637925930,\"updateBy\":\"黄转转\"},{\"classic\":2,\"desc\":\"xxxxx\",\"hot\":214,\"id\":55,\"labelId\":4,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"健身日常\",\"updateAt\":1637925930,\"updateBy\":\"oher\"},{\"classic\":2,\"desc\":\"乌拉\",\"hot\":45,\"id\":215,\"labelId\":4,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"乌拉\",\"reason\":[2],\"status\":0,\"title\":\"那拉那拉\",\"updateAt\":1637925930,\"updateBy\":\"黄转转\"}],5:[{\"classic\":0,\"desc\":\"\",\"hot\":0,\"id\":216,\"labelId\":5,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"兴趣：哈利波特\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"}],6:[{\"classic\":0,\"desc\":\"\",\"hot\":106,\"id\":217,\"labelId\":6,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"天文知识\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"}],7:[{\"classic\":0,\"desc\":\"\",\"hot\":107,\"id\":218,\"labelId\":7,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"健身房健身\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"}],8:[{\"classic\":0,\"desc\":\"\",\"hot\":1,\"id\":219,\"labelId\":8,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"拍摄vlog\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"}],9:[{\"classic\":0,\"desc\":\"\",\"hot\":109,\"id\":220,\"labelId\":9,\"noteIds\":[\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\",\"6142f2f7000000000000d42e\"],\"pageId\":\"\",\"reason\":[],\"status\":0,\"title\":\"more idea\",\"updateAt\":1637925930,\"updateBy\":\"张玉婷\"}]}";
        Map<Integer, List<NoteIdeaLibInfo>> infoMap = JSONObject.parseObject(info, typeToken.getType());
        if (!CollectionUtils.isEmpty(infoMap)) {
            List<NoteIdeaLibInfo> onlineDaos = Lists.newArrayList();
            infoMap.entrySet().stream().forEach(entry -> {
                System.out.println(entry.getValue().size());
                entry.getValue().stream().forEach(info1 -> {
                            onlineDaos.add(info1);
                        }
                );
            });
            System.out.println(onlineDaos.size());
        }
    }
    @Test
    public void test4(){
        Integer labelId = 0;
        NoteIdeaQueryDto query = NoteIdeaQueryDto.buildWeightInfo(labelId, 1, 10);
        System.out.println(JSONObject.toJSONString(query));
    }

    @Test
    public void test5(){

    }
}
