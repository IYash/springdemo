package com.example.demo;

import com.example.demo.biz.MyFunction;
import com.google.common.base.Function;
import lombok.Data;
import net.minidev.json.JSONUtil;
import org.junit.Test;
import org.testng.collections.Lists;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: shiguang
 * @Date: 2021/4/1
 * @Description:
 **/
public class FunctionTest {

    @Test
    public void testFunction(){
        MyFunction<String,String> mf = (a,b)->  a + b;
        System.out.println(mf.apply("hello","world"));
        Function<Comparator<? super String>, MyFunction<String, String>> minBy = MyFunction::minBy;
        System.out.println(minBy.apply(Comparator.comparingInt(String::length)).apply("abcde","defg"));
    }
    @Test
    public void testGroupby(){
        List<Plant> ps = Lists.newArrayList();
        Plant p1= new Plant();p1.setColor("red");
        Plant p2= new Plant();p2.setColor("red");
        Plant p3= new Plant();p3.setColor("red");
        Plant p4 = new Plant();p4.setColor("green");
        Plant p5 = new Plant();p5.setColor("grey");
        ps.add(p1);ps.add(p2);ps.add(p3);ps.add(p4);ps.add(p5);
        Map<String,List<Plant>> colorMap = ps.stream().collect(Collectors.groupingBy(Plant::getColor));
        colorMap.keySet().forEach(key-> System.out.println(key+"--->"+colorMap.get(key).size()));
    }
    @Test
    public void testsort(){
        List<Plant> ps = Lists.newArrayList() ;
        Plant p1= new Plant();p1.setIndex(3);
        Plant p2= new Plant();p2.setIndex(1);
        Plant p3= new Plant();p3.setIndex(4);
        Plant p4 = new Plant();p4.setIndex(5);
        Plant p5 = new Plant();p5.setIndex(2);
        ps.add(p1);ps.add(p2);ps.add(p3);ps.add(p4);ps.add(p5);
        Collections.sort(ps,Comparator.comparing(Plant::getIndex));
        ps.stream().forEach(each-> System.out.println(each.getIndex()));
    }
    @Data
    public static class Plant{
        private String color;
        private int index;
    }
}
