package com.example.demo;

import com.example.demo.util.JedisUtil;
import com.example.demo.util.SnowFlakeShortUrl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.codehaus.plexus.util.StringUtils;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2021/7/16
 * @Description:
 **/
public class TempTest {
    List<int[]> freq = new ArrayList<>();
    List<List<Integer>> ans = new ArrayList<>();
    List<Integer> sequence = new ArrayList<>();
    public List<List<Integer>> combinationSum2(int[] candidates,int target){
        Arrays.sort(candidates);
        for(int num:candidates){
            int size = freq.size();
            if(freq.isEmpty() || num!=freq.get(size-1)[0]){
                freq.add(new int[]{num,1});
            }else{
                ++freq.get(size-1)[1];
            }
        }
        dfs(0,target);
        return ans;
    }
    public void dfs(int pos,int rest){
        if(rest == 0){
            ans.add(new ArrayList<>(sequence));
            return;
        }
        if(pos == freq.size()||rest<freq.get(pos)[0]){
            return;
        }
        dfs(pos+1,rest);
        int most = Math.min(rest/freq.get(pos)[0],freq.get(pos)[1]);
        for(int i=1;i<=most;i++){
            sequence.add(freq.get(pos)[0]);
            dfs(pos+1,rest-i*freq.get(pos)[0]);
        }
        for(int i=1;i<=most;i++){
            sequence.remove(sequence.size()-1);
        }
    }
    @Test
    public void test1(){
        int[] candidates = {2,2,3,4};
        int target = 7;
        combinationSum2(candidates,target);
    }
    @Test
    public void test2(){
        System.out.println(3&3);
        System.out.println(2|1);
    }
    @Test
    public void test3(){
        List<String> arr1 = Lists.newArrayList();
        String url ="";
        for(int i=0;i<7000;i++){
            url = buildString();
            arr1.add(url);
        }
        long start = System.currentTimeMillis();
        System.out.println(arr1.contains(buildString()));
        System.out.println(System.currentTimeMillis()-start);
    }
    private SnowFlakeShortUrl snowFlake = new SnowFlakeShortUrl(5,6);
    //构建长度为18位的随机数
    private String buildString(){
        return snowFlake.nextId()+"";
    }

    @Test
    public void testLogic(){
        boolean a = true,b=true,c=false;
        System.out.println(a||b&&c);
        int x = 7,y=8;
        System.out.println((x&1));
        System.out.println((x&2));
        System.out.println((x&4));
        System.out.println(y&1);
        System.out.println(y&2);
        System.out.println(y&4);
    }
    @Test
    public void efficiencyTest(){
        List<String> original = org.testng.collections.Lists.newArrayList();
        for (int i=0;i<2000;i++){
            original.add(buildString());
        }
        String content = buildString();
        long start = System.currentTimeMillis();
        for(String v:original){
            if(content.contains(v)) break;
        }
        System.out.println(System.currentTimeMillis()-start);
    }
    @Test
    public void quantileTest(){
        String source = "[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20]";
        int len1 = source.length();
        Double key = 4.0;
        String[] ss = source.substring(1,len1-1).split(",");
        int len = ss.length;
        Double[] nums = new Double[len];
        double rate = 100 / len;
        //将string转成double
        for (int i=0;i<len;i++){
            nums[i] = Double.valueOf(ss[i]);
        }
        int base = 2;
        double inputVal = Double.valueOf(key);
        if (key<nums[0]) {
            System.out.println(rate);
        } else if (key>nums[len-1]) {
            System.out.println(99.9);
        }
        int[] index = closetSearch(nums,inputVal,0,len-2);
//        if ((nums[index[0]] + nums[index[1]] - base * inputVal) < 0) {
//            System.out.println((index[0]+1) * rate);
//        } else {
//            System.out.println((index[1]+1) * rate);
//        }
        System.out.println(index[0]+"--->"+index[1]);
        System.out.println(inputVal-nums[index[1]]);
        System.out.println(nums[index[0]]-nums[index[1]]);
        if (nums[index[0]]-nums[index[1]] == 0) {
            System.out.println((index[1]+1)*rate);
        }else{
        System.out.println((index[1]+1+(inputVal-nums[index[1]])/(nums[index[0]]-nums[index[1]]))*rate);}
    }
    private boolean binarySearch(Double[] nums,double k,int start,int end){
        int mid = 0;
        double cur = 0.0;
        while (start <= end) {
            mid = (start + end) >> 1;
            cur = nums[mid];
            if (cur == k) {
                return true;
            } else {
                if (cur < k) {
                    start = mid + 1;
                } else{
                    end = mid-1;
                }
            }
        }
        return false;
    }
    private int[] closetSearch(Double[] nums,double k,int start,int end){
        int mid = 0;
        double cur = 0.0;
        int left = start;
        int right = end;
        while (start <= end) {
            mid = (start + end) >> 1;
            cur = nums[mid];
            if (cur == k) {
                return new int[]{mid,mid};
            } else {
                if (cur < k) {
                    start = mid + 1;
                    left = start;
                } else{
                    end = mid-1;
                    right = end;
                }
            }
        }
        return new int[]{left,right};
    }

    class ComparingO{
        int priority;

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
    @Test
    public void comparingTest(){
        ComparingO o1 = new ComparingO();
        o1.setPriority(-2);
        ComparingO o2 = new ComparingO();
        o2.setPriority(-3);
        List<ComparingO> list = Lists.newArrayList(o1,o2);
        Collections.sort(list, Comparator.comparing(ComparingO::getPriority));
        System.out.println(list.get(0).getPriority());
    }
    @Test
    public void testDoubleVal(){
//        String input = "0";
//        double inputVal = Double.valueOf(input);
//        System.out.println(inputVal == 0);
        System.out.println(Double.valueOf(10)%5);

    }
    @Test
    public void testFormat() throws Exception{
         String BASE_URI = "xhsdiscover://post_new_note?";
         String SOURCE_TEMP = "{\"extraInfo\":{\"subType\":\"creator_center\",\"page_entrance_type\":\"笔记灵感本周热点\",\"topic_id\":\"%s\"},\"type\":\"personal\",\"ids\":\"%s\"}";
        String ATTACH_TEMP = "{\"topics\":[%s{\"page_id\":\"6016078a525b480001096e28\"}]}";
        String source = String.format(SOURCE_TEMP,1,1);
        String str="";
        String pageId = "123";
        if(StringUtils.isNotEmpty(pageId)){
            str = "{\"page_id\":\""+pageId+"\"},";
        }
        String attach = String.format(ATTACH_TEMP,str);
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URI);
        StringBuilder paramSb = new StringBuilder();
        Map<String,String> params = Maps.newHashMap();
        params.put("source",source);
        params.put("attach",attach);
        for(Map.Entry<String,String> entry:params.entrySet()){
            paramSb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
        }
        sb.append(URLEncoder.encode(paramSb.substring(1)));
        System.out.println(sb.toString());
    }
    @Test
    public void subListTest(){
        List<String> ss = Lists.newArrayList("1","2","3","4");
        ss.subList(0,2);
        System.out.println(ss.size());
        sub(ss);
        System.out.println(ss.size());
    }
    private void sub(List<String> list){
        list = list.subList(0,0);
        System.out.println(list.size());
    }
    @Test
    public void demoTest(){
        System.out.println(ActivityDemo.demoActivity().size());
    }
    @Test
    public void testList(){
        List<String> ss = Lists.newArrayList("a","b");
        ss=ss.stream().filter(e-> StringUtils.equals("a",e)).collect(Collectors.toList());
        System.out.println(ss.size());
        ss.remove("a");
        ss.remove("b");
        ss.remove("c");
    }
    @Test
    public void redisExTest(){

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            calendar.setFirstDayOfWeek(Calendar.FRIDAY);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long currentFridayTime = calendar.getTimeInMillis();
            long expireAt = currentFridayTime/1000 + (7 * 24 * 3600);

            Jedis cache = JedisUtil.manualInitJedis();
            cache.set("exp","11");
            cache.expireAt("exp",expireAt);
    }
}
