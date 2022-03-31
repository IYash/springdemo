package com.example.demo;


import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/4/27
 * @Description:
 **/
public class ListObjectTest {

    @Test
    public void testContains(){
        List<String> sources = Lists.newArrayList("a","b","c");
        List<String> target = Lists.newArrayList("b","e","f");
        System.out.println(CollectionUtils.containsAny(sources,target));
    }
    @Test
    public void testSwitch(){
        List<String> sources = Lists.list("a","b","d");
        sources.stream().forEach(el-> System.out.println(match(el)));
    }
    public boolean match(String source){
        switch(source){
            case "a":return true;
            case "b":return true;
            case "c":return true;
            default: return false;
        }
    }
}
