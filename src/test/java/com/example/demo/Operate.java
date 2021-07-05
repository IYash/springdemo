package com.example.demo;

import org.junit.Test;
import org.testng.collections.Maps;

import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/7/5
 * @Description: 数的运算
 **/
public class Operate {

    @Test
    public void test1(){
        System.out.println(divide(16,6));
    }
    private int divide(int dividend,int divisor){//还是用到了递归,除法计算
        //左移
        if(divisor == 1) return dividend;
        if(dividend < divisor) return 0;
        int round = 1;
        int originDivisor = divisor;
        while(divisor <= dividend){
            divisor = divisor<<1;
            round = round <<1;
        }
        if(divisor > dividend){
            divisor = divisor>>1;
            round = round >>1;
            dividend =  dividend - divisor;
            round += divide(dividend,originDivisor);
        }
        return round;
    }
    @Test
    public void test2(){
        String source = "abcdefgh";
        String[] subWords = {"ab","cd","ef","gh"};
        int[] result  =combineWords(source,subWords);
        System.out.println(result[0]+"---"+result[1]);
    }
    //字符串串联，使用sub长度的窗口来匹配（类似的全匹配是可以参考窗口的模式）
    private int[] combineWords(String source,String[] sub){
        //记录sub的特征
        int len = sub.length;
        if(len == 0) return new int[]{0,0};
        Map<String,Integer> subMap = Maps.newHashMap();
        for(int i=0;i<len;i++){
            String key = sub[i];
            if(subMap.containsKey(key)) subMap.put(key,subMap.get(key)+1);
            else subMap.put(key,1);
        }
        int partLen = sub[0].length();
        int sublen = partLen * len;
        int endPos = source.length() - sublen+1;
        for(int i=0;i<endPos;i++){
           //逻辑处理
            int[] result = resolve(i,i+sublen,source,subMap,partLen);
            if(result[1]!=0) return result;
        }
        return new int[]{0,0};
    }

    private int[] resolve(int start,int end,String source,Map<String,Integer> subMap,int partLen){
        String subString = source.substring(start,end);
        Map<String,Integer> currentMap = Maps.newHashMap();
        while(start+partLen <= end){
            String key = subString.substring(start,start+partLen);
            if(subMap.containsKey(key)) {
                if(currentMap.containsKey(key)){
                    currentMap.put(key,currentMap.get(key)+1);
                }else currentMap.put(key, 1);
                start += partLen;
            }else{
                return new int[]{0,0};
            }
        }
        //比较两个map的关系
        if(!subMap.equals(currentMap)) return new int[]{0,0};
        return new int[]{end,end};
    }
}
