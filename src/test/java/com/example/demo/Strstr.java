package com.example.demo;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * @Author: shiguang
 * @Date: 2021/7/2
 * @Description: 提取加速匹配信息
 **/
public class Strstr {
    @Test
    public void testStr() {
        String haystack = "aacddaacaaeaacaaa", needle = "aacaaa";
        System.out.println(strStr(haystack, needle));
    }

    private int strStr(String haystack, String needle) {
        int n = haystack.length(), m = needle.length();
        if (m == 0) return 0;
        int[] pi = new int[m];
        for (int i = 1, j = 0; i < m; i++) {//完成pi的初始化
            while (j > 0 && needle.charAt(i) != needle.charAt(j)) {
                j = pi[j - 1]; //回退到某个位置
            }
            if (needle.charAt(i) == needle.charAt(j)) {
                j++;
            }
            pi[i] = j;
        }
        for (int i = 0, j = 0; i < n; i++) {
            while (j > 0 && haystack.charAt(i) != needle.charAt(j)) {//j>0意味着位置上必然存在对某个长度的子串
                j = pi[j - 1]; //回退到某个位置,一直回退到j==0或者字符再次出现匹配
            }
            if (haystack.charAt(i) == needle.charAt(j)) {
                j++; //j移动
            }
            if (j == m) return i - m + 1;
        }
        return -1;
    }

    private int lengthOfLongestSubstring(String s) {//最长无重复子串
        if (s.length() == 0) return 0;
        Map<Character, Integer> map = Maps.newHashMap();
        int max = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {//意味着字符重复了，则需要更新left的值
                left = Math.max(left, map.get(s.charAt(i)) + 1);
            }
            map.put(s.charAt(i), i);
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    //将字符串转数字
    private int myAtoi(String in) {
        int len = in.length();
        if (len == 0) {
            return 0;
        }
        char[] chs = in.toCharArray();
        int flag = 1;

        if (chs[0] == '-') {
            flag = -1;
        }
        int tmp = 0;
        int base = 0;
        char ch;
        for (int i = 1; i < len; i++) {
            ch = chs[i];
            if(!Character.isDigit(ch)){
                return 0;
            }
            tmp = ch - '0';
            if (flag == 1){
                if (base <= Integer.MAX_VALUE/10 || tmp < Integer.MAX_VALUE % 10){
                    base = base * 10 + tmp;
                }
            } else {
                if (base < Integer.MIN_VALUE/10  ){
                    base = base * 10 + tmp;
                }
            }

        }
        return flag * base;
    }
}
