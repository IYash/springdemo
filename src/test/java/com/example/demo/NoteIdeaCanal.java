package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * @Author: shiguang
 * @Date: 2022/3/28
 * @Description: 渠道描述
 **/
@AllArgsConstructor
@Getter
public enum NoteIdeaCanal {
    operator("运营",0),
    poka_search("poka搜索侧热点",1),
    poka_page("poka话题侧热点",2),
    ugc_activate("ugc高活话题",3),
    ugc_up("ugc飙升话题",4),
    competitor("爬竞品话题",5),
    ;
    String name;
    int val;

    public static String parseByVal(int val){
        NoteIdeaCanal[] values = NoteIdeaCanal.values();
        return Arrays.stream(values).filter(canal->canal.getVal() == val).findAny().orElse(operator).getName();
    }
}
