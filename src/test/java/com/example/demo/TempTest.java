package com.example.demo;

import com.example.demo.util.SnowFlakeShortUrl;
import com.google.common.collect.Lists;
import com.site.lookup.util.StringUtils;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

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
        o1.setPriority(2);
        ComparingO o2 = new ComparingO();
        o2.setPriority(1);
        List<ComparingO> list = Lists.newArrayList(o1,o2);
        Collections.sort(list, Comparator.comparing(ComparingO::getPriority));
        System.out.println(list.get(0).getPriority());
    }
}
