package com.example.demo;


import com.alibaba.fastjson.JSONObject;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Sets;
import org.junit.Test;
import org.testng.collections.Lists;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class CodeTest  {
    @Test
    public void testAddCode(){
        coverFile("com.example.demo.annotation","BoundInfoInjected");
    }
    private void coverFile(String packagePath, String className) {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass clazz = cp.get(packagePath+"."+className);
            CtMethod m =CtMethod.make("public String getBoundInfo(){ return this.boundInfo;}",clazz);
            clazz.addMethod(m);
            clazz.writeFile(clazz.getClass().getResource("/").getPath());
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }
    @Test
    public void test1(){
        String path = System.getProperty("user.dir");
        StringBuilder sb = new StringBuilder();
        sb.append(path).append(File.separator).append("target").append(File.separator);
        sb.append("com.example.demo.annotation".replace(".", File.separator));
        System.out.println(sb.toString());
         }
    @Test
    public void test2(){
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = System.currentTimeMillis();
        System.out.println(sdf.format(time));
    }
    @Test
    public void test3(){
        String str = "[{\"uInfo\":{\"uid\":\"5fdc6cb90000000000002249\",\"imageS\":\"https://sns-avatar-qc.xhscdn.com/avatar/ae351b5abc78ae2e8d79998354db8440.jpg?imageView2/2/w/80/format/jpg\",\"nickname\":\"小红薯5FDCAD9F\",\"seq\":604},\"rTime\":0,\"pos\":1,\"state\":1,\"content\":\"test0105\",\"likes\":0,\"status\":0,\"userIdStr\":\"1234\"},{\"uInfo\":{\"uid\":\"5fdc6cb90000000000002249\",\"imageS\":\"https://sns-avatar-qc.xhscdn.com/avatar/ae351b5abc78ae2e8d79998354db8440.jpg?imageView2/2/w/80/format/jpg\",\"nickname\":\"小红薯5FDCAD9F\",\"seq\":604},\"rTime\":0,\"pos\":1,\"state\":1,\"content\":\"test0105\",\"likes\":0,\"status\":0,\"userIdStr\":\"2345\"},{\"uInfo\":{\"uid\":\"5fdc6cb90000000000002249\",\"imageS\":\"https://sns-avatar-qc.xhscdn.com/avatar/ae351b5abc78ae2e8d79998354db8440.jpg?imageView2/2/w/80/format/jpg\",\"nickname\":\"小红薯5FDCAD9F\",\"seq\":604},\"rTime\":0,\"pos\":3,\"state\":1,\"content\":\"test0105_0\",\"likes\":0,\"status\":0}]";
        List<WishSelfInfoDto> dtos = JSONObject.parseArray(str,WishSelfInfoDto.class);
        dtos.stream().forEach(dto-> System.out.println(JSONObject.toJSONString(dto)));

    }
    @Test
    public void test4(){
        String str1 = "[{\"userId\":\"123\",\"pos\":2},{\"userId\":\"123\",\"pos\":3},{\"userId\":\"123\",\"pos\":1}]";
        List<WishSelfInfoDto> dtos = JSONObject.parseArray(str1,WishSelfInfoDto.class);
        Set<WishSelfInfoDto> ss = Sets.newHashSet();
        dtos.stream().forEach(e->ss.add(e));
        System.out.println(ss.size());
    }
    @Test
    public void test5(){
        String str = "{\"uid\":\"5fdc6cb90000000000002249\",\"imageS\":\"https://sns-avatar-qc.xhscdn.com/avatar/ae351b5abc78ae2e8d79998354db8440.jpg?imageView2/2/w/80/format/jpg\",\"nickname\":\"小红薯5FDCAD9F\",\"seq\":604}";
        WishBaseResponse response = new WishBaseResponse();
        response.addSuccessData(JSONObject.parseObject(str,WishSelfInfoDto.SimpleUserInfo.class));
        System.out.println(JSONObject.toJSONString(response));
    }
    @Test
    public void test6(){
        List<String> str1 = Lists.newArrayList("a","b","c");
        List<String> str2 = Lists.newArrayList("a","e","f");
//        System.out.println(str1.retainAll(str2));
//        System.out.println(str1);
//        System.out.println(str2);
        System.out.println(str2.retainAll(str1));
        System.out.println(str2);
        System.out.println(str1);
    }
    @Test
    public void test7(){
        List<String> list = Lists.newArrayList("1","2","3","4","5","6","7");
       sublist(list);
    }
    private void sublist(List<String> list){
        Set<String> unique = Sets.newHashSet();
        list.stream().forEach(e->unique.add(e));
        list = unique.stream().collect(Collectors.toList());
        int size = list.size();
        int round = 5;
        int tmp =0;
        List<String> sub;
        while(size>0){
            if(round>size){
                round = size;
            }
            sub = list.subList(tmp,tmp+round);
            tmp += round;
            size -= round;
        }
    }
    @Test
    public void test8(){
        String str = "a?b";
        System.out.println(str.split("\\?")[0]);
    }
    @Test
    public void test9(){
        String df = "yyyyMMdd";
        DateFormat sdf = new SimpleDateFormat(df);
        System.out.println(sdf.format(System.currentTimeMillis()));
    }

    @Test
    public void test10(){
        String msg = "{\"result\":\"REJECT\",\"target_type\":\"ACTIVITY_OTHER_WISH\",\"target_id\":\"207267282668500605-1\",\"detail\":[{\"tag_info\":[],\"action\":\"HIGHYOURSELF\",\"data_field\":\"content\"}],\"callback_params\":{\"user_id\":\"605c7ed5000000000100367d\"},\"process_time\":1642770975055}";
        WishVerifyDto vo = JSONObject.parseObject(msg, WishVerifyDto.class);
        String targetId = vo.getTargetId();
        String[] parts = targetId.split("-");
        String userIdStr = parts[0];
        long userId = Long.valueOf(userIdStr);
        String pos = parts[1];
        String result = vo.getResult();
        int posInt = Integer.parseInt(pos);
        int status = 0;
        if (StringUtils.equals(result, "VERIFIED")) {
            status = 1;
        } else if (StringUtils.equals(result, "REJECT")) {
            status = 2;
            WishVerifyDto.CallbackParam callbackParams = vo.getCallbackParams();
            if(!(StringUtils.equals("review",callbackParams.getTaskType()) || StringUtils.equals("condition",callbackParams.getTaskType()))){
                System.out.println("------");
            }
        }
    }
    @Test
    public void test11(){
        List<String> s1  = Lists.newArrayList("a","b","c");
        List<String> s2  = Lists.newArrayList("a","c");
        System.out.println(s1.retainAll(s2));
        System.out.println(s1);
    }
}
