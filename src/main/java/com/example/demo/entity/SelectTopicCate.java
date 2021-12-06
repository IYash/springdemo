package com.example.demo.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.List;

/**
 * @Author: shiguang
 * @Date: 2021/11/26
 * @Description:
 **/
@Data
public class SelectTopicCate {

    /**
     * 垂类id
     */
    private Integer id;

    /**
     * 垂类名称
     */
    private String name;

    /**
     * 当前用户是否能编辑该垂类
     */
    private Boolean canEdit;

    /**
     * 具有编辑权限的人员的邮箱
     */
    private List<String> emails;

    public static List<SelectTopicCate> defaultCate(){
        String categorys = "[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"name\": \"美食\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"name\": \"美妆\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"name\": \"时尚\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 4,\n" +
                "        \"name\": \"出行\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 5,\n" +
                "        \"name\": \"兴趣爱好\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 6,\n" +
                "        \"name\": \"知识\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 7,\n" +
                "        \"name\": \"运动健身\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 8,\n" +
                "        \"name\": \"vlog\",\n" +
                "        \"emails\": []\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 9,\n" +
                "        \"name\": \"更多灵感\",\n" +
                "        \"emails\": []\n" +
                "    }\n" +
                "]";
        return JSONObject.parseArray(categorys,SelectTopicCate.class);
    }
}
