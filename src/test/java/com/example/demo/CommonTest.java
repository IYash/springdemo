package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.js.RuleInfo;
import javafx.beans.binding.ObjectExpression;
import org.junit.Test;
import org.testng.collections.Lists;

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
}
