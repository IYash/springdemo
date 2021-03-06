package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.js.RuleInfo;
import com.example.demo.util.JedisUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.site.lookup.util.StringUtils;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import org.testng.collections.Lists;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;
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
    private static final String STYLE_KEY = "hash:danmaku_activity_style_info";
    private static final String RULE_KEY ="hash:danmaku_activity_rule_info";
    private static final String TOPIC = "danmaku_activity_topics";
    private static final String STYLE = "4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1";

    @Test
    public void activityRedisTest(){
        //???????????????????????????map
        Map<String,String> contentMap = Maps.newHashMap();
        contentMap.put("hello world1",STYLE);
        //???????????????????????????
        Set<String> noteSet = Sets.newHashSet();
        noteSet.add("611503f10000000001005095");
        Map<String,String> topicMap = Maps.newHashMap();
        Jedis jedis = JedisUtil.manualInitJedis();
        jedis.hmset(STYLE_KEY,contentMap);
        String oldTopic = jedis.hget(RULE_KEY,TOPIC);
        if(StringUtils.isNotEmpty(oldTopic)){
            List<String> oldTopicArr = JSONObject.parseArray(oldTopic,String.class);
            noteSet.addAll(oldTopicArr);
        }
        topicMap.put(TOPIC,JSONObject.toJSONString(noteSet));
        jedis.hmset(RULE_KEY,topicMap);
    }
    @Test
    public void sourceTest(){
        //List<String> noteIds = Lists.newArrayList("60b5d1800000000000000f94","60d044260000000000004619");//test
        List<String> noteIds = Lists.newArrayList("611503f10000000001005094","611a195e000000000102f3ab","6114fdf4000000000102b78b","611f6f5e000000002103551e","611ccedb000000002103e7d5"
        ,"6121c75f0000000021037e29","6120bfb600000000210367d2","611a3d38000000002103df12","611e291e0000000001025ccc","6121bf07000000000102ee97",
                "6116407c00000000010252f5","611faaac00000000210383c9");
        List<String> topics = Lists.newArrayList();
        Map<String,String> infoMap = Maps.newHashMap();
        String infoT = "hset hash:danmaku_activity_rule_info '%s' '%s'";
        infoMap.put("danmaku_activity_topics",JSONObject.toJSONString(topics));
        infoMap.put("danmaku_activity_notes",JSONObject.toJSONString(noteIds));
        infoMap.put("danmaku_activity_begin_time","1626845910"); //todo ????????? ????????????
        infoMap.put("danmaku_activity_end_time","1633066710");
        infoMap.put("danmaku_activity_switch","1");
        infoMap.put("danmaku_activity_content_sha1","1");
        infoMap.put("danmaku_activity_effect_v","7000000");//todo ??????????????????7000000
        //infoMap.put("danmaku_activity_special_content","????????????");
        Set<Map.Entry<String, String>> infoEntries = infoMap.entrySet();
        infoEntries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(infoT,key,val));
        });
        System.out.println("==================================");
        String styleT = "hset hash:danmaku_activity_style_info '%s' '%s'";
        Map<String,String> contentStyleMap = Maps.newHashMap();
        //???????????? https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png
        //https://fe-video-qc.xhscdn.com/fe-platform/7777e043fd664febe0e24441b7e526b93a11e0c5.png
        //https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png
        contentStyleMap.put("??????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("???????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("?????????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("???????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("??????????????????????????????????????????1???","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("??????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("?????????????????????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("??????????????????yyds????????????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("??????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("?????????????????????????????????...","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("??????????????????1??????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("yyds","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("???????????????????????????????????????????????????1 ?????????2","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("????????????????????????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png,,15,1");
        contentStyleMap.put("???????????????","4294967295,4294911042,0,0,https://fe-video-qc.xhscdn.com/fe-platform/b26a5ecc0d955b22e1e4470c679c29d79a7f6a1e.png,,15,1");
        Set<Map.Entry<String, String>> entries = contentStyleMap.entrySet();
        entries.stream().forEach(entry->{
               String key = entry.getKey();
               String val = entry.getValue();
            System.out.println(String.format(styleT,key,val));
            }
        );


    }
    @Test
    public void testGuide(){
        Map<String,String> infoMap = Maps.newHashMap();
        String infoT = "hset hash:danmaku_operator_rule_info '%s' '%s'";
        //infoMap.put("user_id","5fdc6cb90000000000002249");
        infoMap.put("user_id","5fed6dbd000000000100af97");
        infoMap.put("post_milsec","4000");
        infoMap.put("danmaku_operator_switch","1");
        infoMap.put("danmaku_operator_effect_v","7000000");
        Set<Map.Entry<String, String>> infoEntries = infoMap.entrySet();
        infoEntries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(infoT,key,val));
        });
        System.out.println("==================================");
        String styleT = "hset hash:danmaku_operator_style_info '%s' '%s'";
        Map<String,String> contentStyleMap = Maps.newHashMap();
        contentStyleMap.put("?????????????????????????????????????????????????????????????????????~","4294967295,4278190080,0,1712524302,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        contentStyleMap.put("Afdsa","4294967295,4278190080,0,1712524302,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        contentStyleMap.put("????????????????????????????????????????????????????????????","4294967295,4278190080,0,1712524302,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        Set<Map.Entry<String, String>> entries = contentStyleMap.entrySet();
        entries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(styleT,key,val));
        });
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

    @Test
    public void styleTest(){ //?????????????????????????????????

        List<String> activityNames = Lists.newArrayList(/*"midAutumn_guide_","midAutumn_",*/"author_","guide_");
        String activityT = "set danmaku_activity_names '%s'";
        System.out.println(String.format(activityT,JSONObject.toJSONString(activityNames)));
        String prefix = activityNames.get(0);
        List<String> noteIds = Lists.newArrayList("60dc5d6c0000000000002ce2");
        List<String> topics = Lists.newArrayList( "");//60d04fea00000000000038f3
        Map<String,String> infoMap = Maps.newHashMap();
        String infoT = "hset "+ prefix+"hash:danmaku_activity_rule_info '%s' '%s'";
        infoMap.put("danmaku_activity_topics",JSONObject.toJSONString(topics));
        infoMap.put("danmaku_activity_notes",JSONObject.toJSONString(noteIds));
        infoMap.put("danmaku_activity_condition","1");
        infoMap.put("danmaku_activity_type","1");
        infoMap.put("user_id","5fed6dbd000000000100af97");//5fdc6cb90000000000002249
        infoMap.put("post_milsec","4000");
        infoMap.put("danmaku_activity_begin_time","1631894880"); //todo ????????? ????????????
        infoMap.put("danmaku_activity_end_time","1632240000");
        infoMap.put("danmaku_activity_switch","0");
        infoMap.put("danmaku_activity_effect_v","7000000");//todo ??????????????????7000000
        //infoMap.put("danmaku_activity_special_content","????????????");
        Set<Map.Entry<String, String>> infoEntries = infoMap.entrySet();
        infoEntries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(infoT,key,val));
        });
        System.out.println("==================================");
        String styleT = "hset "+prefix+"hash:danmaku_activity_style_info '%s' '%s'";
        Map<String,String> contentStyleMap = Maps.newHashMap();
        //???????????? https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png
        //https://fe-video-qc.xhscdn.com/fe-platform/7777e043fd664febe0e24441b7e526b93a11e0c5.png
        //https://fe-video-qc.xhscdn.com/fe-platform/8c6986a04f7b1bc0f916cdec91225bd1771b4e52.png
        contentStyleMap.put("???????????????????????????????????????????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        contentStyleMap.put("????????????????????????????????????????????????????????????????????????~","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        contentStyleMap.put("??????????????????????????????????????????????????????????????????8.88???????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        contentStyleMap.put("??????????????????????????????8.88 ???????????????????????????","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
        //contentStyleMap.put("????????????","midAutumn-common;midAutumn-special");//midAutumn-common;midAutumn-special
        Set<Map.Entry<String, String>> entries = contentStyleMap.entrySet();
        entries.stream().forEach(entry->{
                    String key = entry.getKey();
                    String val = entry.getValue();
                    System.out.println(String.format(styleT,key,val));
                }
        );
    }
    @Test
    public void idolDataTest() throws Exception{
        String inputFile = "/Users/shiguang/Desktop/idolinfo";
        Set<String> exists = Sets.newHashSet();
        StringBuilder sb = new StringBuilder();
        try(InputStream is = new FileInputStream(inputFile)) {
            List<String> rows = IOUtils.readLines(is);
            for(String row:rows){
                if (exists.add(row)){
                    sb.append(row).append("\r\n");
                }
            }
        }
        writeFile(sb,"/Users/shiguang/Desktop/idolinfo_check");

    }
    void writeFile(StringBuilder source,String outputPath) throws Exception{
        try(OutputStreamWriter writer = new FileWriter(new File(outputPath),false);){
            writer.write(source.toString());
            writer.flush();
        }
    }
    @Test
    public void authNoteIdTest() throws Exception{
        String inputFile = "/Users/shiguang/Desktop/author_noteId";
        Set<String> exists = Sets.newHashSet();
        StringBuilder sb = new StringBuilder();
        try(InputStream is = new FileInputStream(inputFile)) {
            List<String> rows = IOUtils.readLines(is);
            for(String row:rows){
              String[] arrs = row.split(",");
              for (String arr:arrs){
                  sb.append(arr).append("\r\n");
              }
            }
        }
        writeFile(sb,"/Users/shiguang/Desktop/author");
    }

    @Test
    public void testLongDivide(){
        long a = 4;
        long b = 10;
        System.out.println(a/b);
        System.out.println((double)a/b);
    }
    @Test
    public void subList(){
        List<Integer> nums = Lists.newArrayList(1,3,4,5,6);
        int len = nums.size();
        System.out.println(nums.subList(0,len));
    }
    @Test
    public void testJson1(){
        String json = "{\"new_post_num_d7\":10,\"read_feed_num_d7\":10,\"product_rate_3d\":10}";
        CalQuota cal = JSONObject.parseObject(json,CalQuota.class);
        System.out.println(cal.getNewPostNumD7());
    }
    @Data
    public static class CalQuota{
        String newPostNumD7;
        String readFeedNumD7;
        String productRate3d;
    }

    @Test
    public void testNano(){
        System.out.println(System.nanoTime());
        System.out.println(System.currentTimeMillis());
    }
    @Test
    public void testMap(){
        Map<String,String> map = Maps.newHashMap();
        map.put("a","1");
        map.put("b","2");
        System.out.println(map.values().size());
    }
    @Test
    public void testListOp(){
        int start = 0;
        int size = 10;
        List<String> manual = Lists.newArrayList("12345","2345");
        List<String> recall = Lists.newArrayList("123456","23457");
        List<String> result = Lists.newArrayList();
        result.addAll(orderByList(manual));
        result.addAll(orderByList(recall));
        int len = result.size();
        int end = (start + size) > len ? len : (start + size);
        boolean hasNext = false;
        if (end < len) {
            hasNext = true;
        }
        System.out.println(len +"===="+hasNext);
    }

    private List<String> orderByList(List<String> noteIds) {
        List<String> result = org.assertj.core.util.Lists.newArrayList();
        if (CollectionUtils.isEmpty(noteIds)) {
            return result;
        }
        int len = noteIds.size();
        if (len == 0) {
            return result;
        }
        int start = 0;
        int end = 0;
        int part = 100;
        List<String> sub;
        while (end < len) {
            end = (end + part) >= len ? len : end + part;
            sub = noteIds.subList(start, end);
            start += part;
            result.addAll(sub);
        }
        return result;
    }

}
