package com.example.demo;

import org.junit.Test;

/**
 * @Author: shiguang
 * @Date: 2021/7/2
 * @Description: 提取加速匹配信息
 **/
public class Strstr {
    @Test
    public void testStr(){
        String haystack = "aacddaacaaeaacaaa",needle="aacaaa";
        System.out.println(strStr(haystack,needle));
    }

    private int strStr(String haystack, String needle) {
        int n=haystack.length(),m=needle.length();
        if(m==0) return 0;
        int[] pi=new int[m];
        for(int i=1,j=0;i<m;i++){//完成pi的初始化
            while(j>0 && needle.charAt(i)!=needle.charAt(j)){
                j = pi[j-1]; //回退到某个位置
            }
            if(needle.charAt(i)==needle.charAt(j)){
                j++;
            }
            pi[i] = j;
        }
        for(int i=0,j=0;i<n;i++){
            while(j>0 && haystack.charAt(i)!=needle.charAt(j)){//j>0意味着位置上必然存在对某个长度的子串
                j = pi[j-1]; //回退到某个位置,一直回退到j==0或者字符再次出现匹配
            }
            if(haystack.charAt(i) == needle.charAt(j)){
                j++; //j移动
            }
            if(j==m) return i-m+1;
        }
        return -1;
    }
}
