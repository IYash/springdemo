package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.testng.collections.Lists;
import org.testng.collections.Maps;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/9/15
 * @Description:
 **/
public class AutumnActivity {

    @Test
    public void testall(){
        DayUsedKeyTest();
        TriggerLastKeyTest();
        TriggerUserKeyTest();
        mulStyleTest();
    }
    @Test
    public void activityNamesTest(){
        String activityT = "set danmaku_activity_names '%s'";
        List<String> list = Lists.newArrayList("midAutumn_");//如果考虑把其他数据都下线
        System.out.println(String.format(activityT, JSONObject.toJSONString(list)));
    }
    @Test
    public void DayUsedKeyTest(){
        //String MidAutumnDayUsedKey = "string:danmaku_activity_special_remain:%s";
        String activityT = "set string:danmaku_activity_special_remain:%s %s";
        Map<String,Integer> map = Maps.newHashMap();
        map.put("2021-9-19",1);
        map.put("2021-9-16",1);
        map.put("2021-9-21",11);
        map.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            Integer val = entry.getValue();
            System.out.println(String.format(activityT,key,val));
        });
    }
    @Test
    public void TriggerLastKeyTest(){
        //String MidAutumnTriggerLastKey = "hash:danmaku_activity_special_active_time";
        String activityT = "hset hash:danmaku_activity_special_active_time '%s' %s";
        Map<String,Integer> map = Maps.newHashMap();
        map.put("2021-9-19",1631980800);
        map.put("2021-9-16",1631754053);
        map.put("2021-9-15",1631691394);
        map.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            Integer val = entry.getValue();
            System.out.println(String.format(activityT,key,val));
        });
    }
    @Test
    public void TriggerUserKeyTest(){
       // String MidAutumnTriggerUserKey = "set:danmaku_activity_special_user:%s";
        String activityT = "sismember set:danmaku_activity_special_user:%s '%s'";
        Map<String,String> map = Maps.newHashMap();
        map.put("2021-9-13","5dff80a9000000000100a922");
        map.put("2021-9-20","def");
        map.put("2021-9-21","5a92e0d811be105c15a6da71");
        map.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(activityT,key,val));
        });
    }
    private String formatDate(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd");
        return format.format(date);
    }
    @Test
    public void testFormat(){
        System.out.println(formatDate());
    }
    @Test
    public void mulStyleTest(){
        String prefix = "midAutumn_";
        String activityT = "hset "+prefix+"hash:danmaku_activity_mul_style_info '%s' '%s'";
        Map<String,String> map = Maps.newHashMap();
        map.put("midAutumn-common","4294950422,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/1f5e617636a0271ea9d716ab42e6fd3c638551ce.png,,15,1");
        map.put("midAutumn-special","4294950422,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platform/2e8d8791180fe931c0ff04c18768f60f8acf0dd5.png,,15,1");
        map.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(activityT,key,val));
        });
    }
    @Test
    public void topicTest(){
        String prefix = "midAutumn_";
        String activityT = "hset hash:danmaku_activity_topic_info_v '%s' '%s'";
        Map<String,String> map = Maps.newHashMap();//生产pageId:5c0b35584ad7dc00016d9f3a
        map.put("name","夏日"); //中秋节
        map.put("id","2297064851605829875"); //2233788174287913894
        map.put("oid","60d04fea00000000000038f3"); //53f867e6b4c4d66e263343a6
        map.entrySet().stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(activityT,key,val));
        });
    }
}
