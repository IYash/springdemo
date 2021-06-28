package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.js.RuleInfo;
import com.example.demo.util.JedisUtil;
import javafx.beans.binding.ObjectExpression;
import lombok.Data;
import org.junit.Test;
import org.testng.collections.Lists;
import org.testng.collections.Maps;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2021/3/22
 * @Description:
 **/
public class CommonTest {
    @Test
    public void testNull(){
        Long id = null;
        System.out.println(String.valueOf(id));
    }
    @Test
    public void testLong(){
        Map<Long,String> map = new HashMap<>();
        map.put(1l,"adb");
        map.put(2l,"dcd");
        map.put(12344545454545l,"dfdf");
        System.out.println(map.containsKey(12344545454545l));
    }
    @Test
    public void testDouble(){
        double a = 312575;
        double b = 12735;
        System.out.println(Math.floor(b/a*100));
    }
    @Test
    public void testJson(){
        String text = "{\n" +
//                "    \"limited\":10000.05,\n" +
                "    \"size\":1,\n" +
                "    \"sizeType\":1,\n" +
                "    \"scriptParamList\":[\n" +
                "        {\n" +
                "            \"scriptName\":\"checkChinese\",\n" +
                "            \"scriptContent\":\"function checkChinese(s){var reg =new RegExp(\\\"[\\\\\\\\u4E00-\\\\\\\\u9FFF]+\\\",\\\"g\\\");return !reg.test(s);}\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        JSONObject obj = JSONObject.parseObject(text);
        Integer limited = obj.getInteger("limited");
        if(Objects.nonNull(limited)) System.out.println(limited);
        System.out.println(JSONObject.toJSONString(JSONObject.parseObject(text, RuleInfo.class)));

    }

    @Test
    public void testArr(){
        List<String> ss = Lists.newArrayList();
        String s = "[\"aa\"]";
        ss.add(s);
        Set<String> ssss= ss.stream().flatMap(ele->decorateTrigWord(ele).stream()).collect(Collectors.toSet());

        System.out.println(decorateTrigWord(s));
    }
    private static List<String> decorateTrigWord(String trigWord){
        return JSONObject.parseArray(trigWord,String.class);
    }
    @Test
    public void stringListTest(){
        Jedis jedis = JedisUtil.manualInitJedis();
        String s = jedis.get("list");
        System.out.println(s);
    }
    @Test
    public void sourceTest(){
        List<String> noteIds = Lists.newArrayList("60b5d1800000000000000f94","60d044260000000000004619");
        List<String> topics = Lists.newArrayList("60d04fea00000000000038f3");
        Map<String,String> infoMap = Maps.newHashMap();
        String infoT = "hset hash:danmaku_activity_rule_info '%s' '%s'";
        infoMap.put("danmaku_activity_topics",JSONObject.toJSONString(topics));
        infoMap.put("danmaku_activity_notes",JSONObject.toJSONString(noteIds));
        infoMap.put("danmaku_activity_begin_time","1624344074"); //todo 时间戳 秒为单位
        infoMap.put("danmaku_activity_end_time","1630407967");
        infoMap.put("danmaku_activity_switch","1");
        infoMap.put("danmaku_activity_content_sha1","1");
        infoMap.put("danmaku_activity_effect_v","6000000");//todo 生产环境改为7000000
        Set<Map.Entry<String, String>> infoEntries = infoMap.entrySet();
        infoEntries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(infoT,key,val));
        });
        System.out.println("==================================");
        String styleT = "hset hash:danmaku_activity_style_info '%s' '%s'";
        Map<String,String> contentStyleMap = Maps.newHashMap();
        contentStyleMap.put("你好","4294967295,4278190080,0,4294901760,,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,15,1");
        contentStyleMap.put("hello","4294967295,4278190080,4294901760,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,,15,1");
        contentStyleMap.put("加油！","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,15,1");
        contentStyleMap.put("123@#","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,15,1");
        Set<Map.Entry<String, String>> entries = contentStyleMap.entrySet();
        entries.stream().forEach(entry->{
               String key = entry.getKey();
               String val = entry.getValue();
            System.out.println(String.format(styleT,key,val));
            }
        );


    }

    @Data
    static class DanmakuActivityInfo{
        private List<String> danmaku_activity_notes;
        private List<String> danmaku_activity_topics;
        private long danmaku_activity_begin_time;
        private long danmaku_activity_end_time;
        private String danmaku_activity_switch;
    }
    @Data
    static class DanmakuActivityStyleInfo{
        private Map<String,String> contentStyleMap;
    }
}
