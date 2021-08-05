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
        //List<String> noteIds = Lists.newArrayList("60b5d1800000000000000f94","60d044260000000000004619");//test
        List<String> noteIds = Lists.newArrayList("60fec553000000000100ae7f","610124a90000000001005781",
                "61011df3000000002103d6b7","61000ff300000000210375b4","61013058000000000101f6a8",
                "610138b90000000001025b72","610287310000000001027c07","60f12220000000000100ad58",
                "6102aa2a0000000001025393","610213d6000000002103e677","610229070000000021039070","60f120c2000000000100ad47","6101a3d800000000010032f8","60ffb4350000000001026299","6100ff6e000000000102a3d4","6100d737000000000100a5f9","60f14707000000000101f2db","60ffbe6b0000000001008433","610178c8000000000100bc51","61010c2e000000000101f127","60f9387f000000000100aef0","60ff7fb10000000001025488","60ffc28f000000002103b3b9","60f1213c0000000001005537","60ffe4110000000001029639","6100bac30000000001024972","61001401000000000102c777","61012abe000000000101f5da","61014f8f0000000021036567","60c9b813000000000102f123","6100bc95000000002103d536","61017103000000000100ba1f","60fe66180000000021034753","60fb79ae000000000100924d","61010897000000000100aae6","6100ba170000000021035fb9","60fe6fb4000000000101eb5b","60f0f07c000000000101eae2","60ffc73c000000000101cad5","60fd402b000000000101c0cd","6100c156000000000102de81","610276470000000001029167","60e6a642000000000100b78f","60fd28a2000000000100bafb","60feaed50000000021037a29","610146a4000000000101f89d","6100dd93000000000102d21b","61010530000000000100aa75","60cb13180000000001003b39","60fae6c7000000002103fd34","60ffb220000000002103d1e5","60f123de00000000010014a8","610230e00000000021035b90","61014fb800000000210366d3","61022ff700000000010249bb","6100268b0000000001025c31","60fe75450000000001025fa7","60f1224f0000000001005553","60fd736000000000010080f8","61016933000000002103cf4a","60fba4200000000021039978","6101210600000000010056b1","60ffd2d8000000002103ecdb","60ff7bf700000000210368ce","60ffca5c000000000100303f"
                ,"6108e7b00000000001000402","610573c8000000000100b948","6108a06200000000210345c7","61051c22000000000100122b","6108927a000000002103c0a9","610573e6000000000101fc83","6105987100000000010086c6","610513d9000000000100a973","61059ab50000000001007ebc","61066e7200000000010004c1","6106556d0000000001000319","610667fb0000000001000459","61059bdb0000000001007f2a","6103c5810000000001007435","6103c7b3000000000101d12f","610516fb000000000101ed15","61088bec0000000001024273","6108d541000000000102d7c7","61051b8d00000000010062dc","61050f9a000000000100a8ed","61091194000000002103f5f9","6108a0ec000000000102784d","610610450000000001003fa0","6108017100000000210387bf","61080946000000000101e8c1","61068ba7000000002103e75f","6107fb29000000002103a4c5","6107fd4b000000000102463c","61083fc1000000002103c08c","61080f890000000021038745","61067b4a0000000001009ecf","6108976900000000010257d2","6108f9b7000000002103fd4d","6108be3e00000000010000ca","6108f255000000000102d322","6105179d000000000101ed30","610901c30000000021034cfd","610662460000000001009d1c","6105938b000000000101c84a","6105988e00000000010086cf","61065f0f000000000101e0e6","6106623300000000010003d3","6107fd5d000000000101e20b","6106769e00000000010054d8","61066c3f000000000101e215","61057515000000000101fd05","6105756400000000010021f2","610910bb000000000102550b","61065f2e000000000100039d","610573b80000000001002155","61067e80000000000101e374"
                ,"61095226000000002103ba19","6109eb550000000001024b81","61095e790000000021037ea5","6109f2bc0000000001026341","610994e3000000000102b867","610915310000000001009ed0","61091c27000000000101e53f","61091ac20000000001005a6f","6109150d0000000001000ac7","61091c420000000001005aa5","610915390000000001000acb","61091c53000000000101e543","6109177d00000000010059e9","6109152b00000000010059ac","61091c1d0000000001005aa2","61091ab90000000001005a6d","610919500000000001005a3e","6108ef4d0000000001005433","6108fb4b0000000001000670","6108ef7a0000000001009914","6108ef8e0000000001009918","60fbb5ca00000000010095b7","60ffaecd000000000100828d","6109184a0000000001005a05","61053740000000000100acd4","60fbb5c00000000001007e15","6107aca0000000000101d54c","6104bbb1000000000100a2f3","60fd0cb9000000000100b946","60fbb5ce00000000010095b8","60fbb5c500000000010095b4","60fd0cae000000000100601f","60fd0d5600000000010021c7","6108ef0b000000000101de81","60fe79860000000001004f8c","60fe67b8000000000100a4fc","60fe67c20000000001004cf0","6109187c0000000001005a10","60fff2b8000000000101d25c","60fd0cb600000000010021be","60fe67d9000000000101ea5c","6107af22000000000100392c","610918730000000001009f55","61067b3c0000000001000594","6105370d000000000101efd0","60fff21a00000000010037a9","6109183a000000000101e4c9","60fe67c60000000001001040","6107acb80000000001009195","6107af1c000000000100920c","6107c1b0000000000101d88d","6107acc000000000010038e0","6109f85a000000002103d583","61094fc2000000002103c1ff","610959d80000000021039c0e","6108faca0000000001027db2","6108f7ae000000002103ee27","6108fd6d0000000021037900","610977eb0000000001026706","610a3889000000002103d776","61090cf90000000001009d48","6108e1240000000001000359","61094e98000000002103a97f","610a57690000000021039b21","6104d30f00000000210345df","610a2ffe0000000021034e59","610a533a0000000001002ebb","610931b4000000000100a1ce","6107d3190000000001009709","6107ce500000000001003dfb","6107d0050000000001004e21","6107d28700000000010096fc","6107d227000000000101dabf","6107d1910000000001004e45","6107cfc20000000001003e28","6109fc610000000001028357","610a3d0f000000002103e72f","6109e85d000000000102e32c","6109443d000000000101e98c","6108b196000000000100934d","6108cb870000000001009578","6109222d0000000001000bfe","61091dbe000000000100a01b","6108f3f300000000010099a0","6109e35f000000000102d62e","6109f634000000000102a47c","610a2dcb000000000102a8fb","61092c3c000000002103b2e9","610a39b8000000000100bfa2","6108ff850000000001000718","61091fdb000000000100a05a","61094c4d0000000021036a5a","6109d8b100000000010299b1","610a3ba50000000001025470","610a4210000000002103fc9c","61097360000000000102c595","6108babd000000000102bd90","610a56b7000000000101c953","610a47c0000000002103d0fe","6108bf9200000000010000e3","60fa94c8000000002103f243","610959f5000000002103f5eb","610950e5000000000102d4c6","61094361000000002103d8c9","60fa8c120000000021035ed8","610917080000000021037f70"
        );//prod
        List<String> topics = Lists.newArrayList("605d55f3000000000101e3c7","60c994080000000001000f9f","60d160a1000000000101ef6e","60d2c34c000000000101f6c7",
                "60eea39e00000000010062f6","60eea37c000000000100135c","60eea354000000000100886e","60eea33300000000010062f1","60eea31f000000000101cb2c",
                "60efebcb0000000001006a66","60efebf40000000001006a6d","60efec4c0000000001006a74",
                "595a330ccd30d81f6130ed8e","597b0ee6092b5b381317d74d","5bf4329514ef4800011cb3dc","5bf6b7712f2c080001f804ad",
                "5c097e932798f800012d8255","5c30e9ae00000000080033b1","5c51a2f2000000000d03b779","60c348990000000001002f76","60d2c419000000000100b43e");
        Map<String,String> infoMap = Maps.newHashMap();
        String infoT = "hset hash:danmaku_activity_rule_info '%s' '%s'";
        infoMap.put("danmaku_activity_topics",JSONObject.toJSONString(topics));
        infoMap.put("danmaku_activity_notes",JSONObject.toJSONString(noteIds));
        infoMap.put("danmaku_activity_begin_time","1626845910"); //todo 时间戳 秒为单位
        infoMap.put("danmaku_activity_end_time","1633066710");
        infoMap.put("danmaku_activity_switch","1");
        infoMap.put("danmaku_activity_content_sha1","1");
        infoMap.put("danmaku_activity_effect_v","7000000");//todo 生产环境改为7000000
        infoMap.put("danmaku_activity_special_content","中国加油");
        Set<Map.Entry<String, String>> infoEntries = infoMap.entrySet();
        infoEntries.stream().forEach(entry->{
            String key = entry.getKey();
            String val = entry.getValue();
            System.out.println(String.format(infoT,key,val));
        });
        System.out.println("==================================");
        String styleT = "hset hash:danmaku_activity_style_info '%s' '%s'";
        Map<String,String> contentStyleMap = Maps.newHashMap();
        //图片历史 https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png
        contentStyleMap.put("你好","4294967295,4278190080,0,4294901760,https://fe-video-qc.xhscdn.com/fe-platform/b26a5ecc0d955b22e1e4470c679c29d79a7f6a1e.png,,0,1");
        contentStyleMap.put("hello","4294967295,4278190080,4294901760,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,,15,1");
        contentStyleMap.put("加油！","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,15,1");
        contentStyleMap.put("123@#","4294967295,4278190080,0,0,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,https://fe-video-qc.xhscdn.com/fe-platfrom/6bc7cc85d2e4b0df659574aebb7afae0e2cfa2ab.png,15,1");
        contentStyleMap.put("中国队加油","4294967295,4294911042,0,0,https://fe-video-qc.xhscdn.com/fe-platform/b26a5ecc0d955b22e1e4470c679c29d79a7f6a1e.png,,15,1");
        contentStyleMap.put("中国加油","4294967295,4294911042,0,0,https://fe-video-qc.xhscdn.com/fe-platform/b26a5ecc0d955b22e1e4470c679c29d79a7f6a1e.png,,15,1");
        contentStyleMap.put("中国队","4294967295,4294911042,0,0,https://fe-video-qc.xhscdn.com/fe-platform/b26a5ecc0d955b22e1e4470c679c29d79a7f6a1e.png,,15,1");
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
        contentStyleMap.put("发布弹幕“决绝子”，参与抽周边","4294967295,4278190080,0,1712524302,https://fe-video-qc.xhscdn.com/fe-platform/7621e53bbcca0a3d47691160df24d9dd268bf54c.png,,15,1");
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
}
