package com.example.demo;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Stack;

/**
 * @Author: shiguang
 * @Date: 2021/6/24
 * @Description: 回溯测试
 **/
public class BacktraceTest {

    //6个括号生成,有效括号生成是在此基础上，添加有效括号的过滤或者另一种处理的思路，回溯做括号和右括号的数量关系
    int n=3;
    @Test
    public void test1(){
        String[] arr = new String[]{"(",")"};
        backtrace(Lists.newArrayList(),new StringBuilder(),3,arr);
    }
    private void backtrace(List<String> resultList,StringBuilder part,int n,String... arr){
        if(part.length() == n*2) {
            System.out.println(part.toString());
            resultList.add(part.toString());
            return ;
        }
        for(int i=0;i<2;i++){
            part.append(arr[i]);
            backtrace(resultList,part,n,arr);
            part.deleteCharAt(part.length()-1);
        }
    }
    @Test
    public void test2()
    {
        backtrace(Lists.newArrayList(),new StringBuilder(),0,0);
    }
    //生成有效括号对
    private void backtrace(List<String> resultList,StringBuilder part,int left,int right){
        if(part.length() == n*2){
            System.out.println(part.toString());
            resultList.add(part.toString());
            return;
        }
        if(left<n){
            part.append("(");
            backtrace(resultList,part,left+1,right);
            part.deleteCharAt(part.length()-1);
        }
        if(right<left){
            part.append(")");
            backtrace(resultList,part,left,right+1);
            part.deleteCharAt(part.length()-1);
        }
    }
    //最长有效括号对
    @Test
    public void test3(){
        String brackets = "((())())(()()()()(())";
        System.out.println(longestBrackets(brackets));
    }
    //通过栈的思路来处理
    private int longestBrackets(String source){
        Stack<Integer> stack = new Stack<>();
        int curMax = 0;
        int len = source.length();
        int i=0;
        while(i<len){
            char c = source.charAt(i);
            if(c=='('){
                stack.push(i);
            }else{
                if(!stack.isEmpty()){
                    stack.pop();
                    if(stack.isEmpty()) curMax = i+1;
                    else{
                        int pos = stack.peek();
                        curMax = Math.max(curMax,i-pos);
                    }
                }
            }
            i++;
        }
        return curMax;
    }
}
