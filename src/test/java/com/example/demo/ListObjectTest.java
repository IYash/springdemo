package com.example.demo;


import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/4/27
 * @Description:
 **/
public class ListObjectTest {

    @Test
    public void listTest(){
        TrendIntrudeResponse.Art art = new TrendIntrudeResponse.Art();art.setName("art");
        NoteCacheDto dto = NoteCacheDto.from("sport", Lists.list(art));
        List<NoteCacheDto> dtos = Lists.list(dto);
        List<NoteCacheDto<TrendIntrudeResponse.Art>> params = Lists.newArrayList();
        //dtos.stream().forEach(el->params.add(el));
        TrendIntrudeResponse.convertToTrendInfo(params);
    }
    @Test
    public void listTest1(){
        TrendIntrudeRes.Art art = new TrendIntrudeRes.Art();art.setName("art");
        NoteCacheDto dto = NoteCacheDto.from("sport", Lists.list(art));
        NoteCacheDto dto1 = NoteCacheDto.from("art", Lists.list(art));
        List<NoteCacheDto> dtos = Lists.list(dto,dto1);
        TrendIntrudeRes.convertToTrendInfo(dtos);
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
