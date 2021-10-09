package com.example.demo;

import com.example.demo.util.SnowFlakeShortUrl;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
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

}
